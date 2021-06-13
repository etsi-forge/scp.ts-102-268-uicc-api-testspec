//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rsid;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, releaseServiceIdentifier() method
 * applet 1
 */
public class Api_2_Tkr_Rsid_1 extends TestToolkitApplet { 

    boolean bRes;
    byte testCaseNb = (byte)0x00;

    // Service ID value range
    private static byte MIN_SERVICE_ID = 0;
    private static byte MAX_SERVICE_ID = 7;
    
    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'1'};

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Rsid_1() { 
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Rsid_1 thisApplet = new Api_2_Tkr_Rsid_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short) MenuInit.length, (byte) 0,
                                       false, (byte) 0, (short) 0);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        if (event == EVENT_MENU_SELECTION) {
            
            switch (testCaseNb) {
            case (byte)0x00:
                // Test Case 1: Release not allocated services
                testCaseNb = (byte)0x01;
                bRes = true;

                for (byte i = (byte)0x80; (i < (byte)0x7F) && (bRes); i++) {
                    try {
                        obReg.releaseServiceIdentifier((byte)i);
                        bRes = false;
                    }
                    catch (ToolkitException e) {
                        bRes = (e.getReason() == ToolkitException.INVALID_SERVICE_ID);
                    }
                    catch (Exception e) {
                        bRes = false;
                    }
                }
                reportTestOutcome(testCaseNb, bRes);

                // Test Case 2: Release allocated services
                testCaseNb = (byte)0x02;
                try {
                    // allocate 8 service id.
                    for (byte i = MIN_SERVICE_ID; i < (MAX_SERVICE_ID + 1); i++) {
                        obReg.allocateServiceIdentifier();
                    }

                    // release 7 service id.
                    for (byte i = MIN_SERVICE_ID; i < MAX_SERVICE_ID; i++) {
                        obReg.releaseServiceIdentifier(i);
                    }

                    // check applet is registered to EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
                    bRes = obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);


                // Test Case 3: Release invalid service ID
                testCaseNb = (byte)0x03;
                try {
                    obReg.releaseServiceIdentifier((byte)0xFF);
                    bRes = false;
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.INVALID_SERVICE_ID);
                }
                catch (Exception e) {
                    bRes = false;
                }
                // check applet is still registered to EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
                bRes = bRes && obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);

                reportTestOutcome(testCaseNb, bRes);

                // Test Case 4: Release last service
                testCaseNb = (byte)0x04;
                try {
                    obReg.releaseServiceIdentifier((byte)7);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                // check applet is not registered to EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
                bRes = bRes && !obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);

                reportTestOutcome(testCaseNb, bRes);

                // Test Case 5: Released services can be allocated
                testCaseNb = (byte)0x05;
                try {
                    for (byte i = MIN_SERVICE_ID; i < (MAX_SERVICE_ID + 1); i++) {
                        obReg.allocateServiceIdentifier();
                    }
                    obReg.releaseServiceIdentifier((byte)0x01);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;

            case (byte)0x05:
                // Test Case 6: Release all services for this applet
                testCaseNb = (byte)0x06;
                try {
                    obReg.releaseServiceIdentifier((byte)0x00);
                    for (byte i = 2; i < (MAX_SERVICE_ID + 1); i++) {
                        obReg.releaseServiceIdentifier(i);
                    }
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);

                // Test Case 7: Check Applet1 is not triggered by envelope(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) command
                testCaseNb = (byte)0x07;
                reportTestOutcome(testCaseNb, true);
                break;

            case (byte)0x07:
                // Test Case 8: Release invalid service ID
                testCaseNb = (byte)0x08;
                bRes = true;
                
                try {
                    for (byte i = MIN_SERVICE_ID; (i < MAX_SERVICE_ID) && bRes; i++) {
                        bRes = bRes && !(obReg.allocateServiceIdentifier() == (byte)0x01);
                    }
                    obReg.releaseServiceIdentifier((byte)0x01);
                    bRes = false;
                }
                catch (ToolkitException e) {
                    bRes = bRes && (e.getReason() == ToolkitException.INVALID_SERVICE_ID);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
            }
        }

        if (event == EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) {
            // Test Case 7: Check Applet1 is not triggered by envelope(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) command
            testCaseNb = 0x07;
            bRes = false;
            reportTestOutcome(testCaseNb, bRes);
        }
    } 
}
