//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_asid;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.system.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, allocateServiceIdentifier() method
 * applet 1
 */
public class Api_2_Tkr_Asid_1 extends TestToolkitApplet {

    private byte testCaseNb = 0;
    private static boolean bRes;

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'1'};
    
    // Service ID value range
    private static short MIN_SERVICE_ID = 0;
    private static short MAX_SERVICE_ID = 7;

    // Service expiration counter
    short serviceExpiration = (short)(MAX_SERVICE_ID + 1);

    // Service ID array : initially all at '8' which is not a correct Service ID
    public static byte[] Services = {(byte)8,(byte)8,(byte)8,(byte)8,
                                     (byte)8,(byte)8,(byte)8,(byte)8};
    
    private static byte[] abServiceRecord = {(byte)0x00, (byte)0x00, (byte)0x00};

    // Constructor of the applet
    public Api_2_Tkr_Asid_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Asid_1 thisApplet = new Api_2_Tkr_Asid_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short)0,
                                       (short)MenuInit.length, (byte)0, false,
                                       (byte)0, (short)0);
    }

    /**
    * Method called by the processToolkit() method to know if short <temp>
    * has already been added to array <Array>
    */
    public static boolean isInArray(short temp, byte[] Array) {
        boolean isInArray = false;

        for (byte i = 0; (i < (byte)Array.length) && (!isInArray); i++) {
            isInArray = (Array[i] == temp);
        }
        return isInArray;
    }

    /**
    * Method called by the processToolkit() method to know if an ID
    * belongs to the Service ID interval
    */
    public static boolean isServiceID(byte ID) {
        boolean isServiceID;

        isServiceID = (ID > (byte)(MIN_SERVICE_ID - 1)) && (ID < (byte)(MAX_SERVICE_ID + 1));
        return isServiceID;
    }

   /**
    * Method called by the CAT RE
    */
   public void processToolkit(short event) {
        
        byte temp = 0;
        byte TLV;
        byte ServiceID;
        short i;
        
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();

        if (event == EVENT_MENU_SELECTION) {
            switch (testCaseNb) {
            case (byte)0x00:
                // -----------------------------------------------------------------
                // Test Case 1: Allocates up to 8 services
                // -----------------------------------------------------------------
                testCaseNb = (byte)1;
                bRes = true;

                try {
                    for (i=(short)0; (i<(short)8) && (bRes);i++) {
                        temp = obReg.allocateServiceIdentifier();
                        bRes = (isServiceID(temp)) && (!this.isInArray(temp,Services));
                        Services[i] = temp;
                        
                        // DECLARE SERVICE proactive command 
                        abServiceRecord[1] = temp;
                        proHdlr.init(PRO_CMD_DECLARE_SERVICE, (byte)0x00 /*Add*/, (byte)DEV_ID_TERMINAL);
                        proHdlr.appendTLV(TAG_SERVICE_RECORD, abServiceRecord, (short)0x00, (short)abServiceRecord.length);
                        proHdlr.send();

                        bRes &= obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);
                    }
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);                
                break;

            case (byte)0x09:
                // -----------------------------------------------------------------
                // Test Case 3: Allocates up to 5 services 
                // -----------------------------------------------------------------
                testCaseNb = (byte)0x0A;
                bRes = true;
                
                // re-initialize Services[] array, for comparison of service id
                for (byte k = 0; k<(byte)Services.length; k++) {
                    Services[k] = (byte)8;
                }

                // allocate 5 Service ID. Check there are not already allocated.
                try {
                    for (i=(short)0; (i<(short)5) && (bRes);i++) {
                        temp = obReg.allocateServiceIdentifier();
                        bRes = (isServiceID(temp)) && (!this.isInArray(temp,Services));
                        Services[i] = temp;
                    }
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;
                
            case (byte)0x0A:
                // -----------------------------------------------------------------
                // Second triggering: Release all 5 services 
                // -----------------------------------------------------------------
                testCaseNb = (byte)0x0B;
                try {
                    for (i=(short)0; i<(short)5; i++) {
                        obReg.releaseServiceIdentifier(Services[i]);
                    }
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;
            }
        }
        
        /** Test Case 2: Check applet is triggered by envelope
         *               (EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) command
         *               release all Service ID.
         */
        if (event == EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) {
            
            //testCaseNb 0x02...0x09
            testCaseNb++;
            
            bRes  = true;
            // Get the system instance of the ProactiveHandler class
            EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();

            // find Service ID
            TLV = envHdlr.findTLV(TAG_SERVICE_RECORD, (byte)1);
            if(TLV != TLV_NOT_FOUND){
                // get the Service ID
                ServiceID = envHdlr.getValueByte((short)1);

                try {
                    obReg.releaseServiceIdentifier((byte)ServiceID);
                }
                catch (Exception e) {
                    bRes = false;
                }                
                if (--serviceExpiration == 0) {
                    //testCaseNb 0x09
                    bRes = bRes & !obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);
                    reportTestOutcome(testCaseNb, bRes);
                } else {
                    //testCaseNb 0x02...0x08
                    reportTestOutcome(testCaseNb, bRes);
                }                               
            } else {
                reportTestOutcome(testCaseNb, false);
            }   
            
            
                     
        }
    }
}
