//-----------------------------------------------------------------------------
//    API_2_TKR_SEVL_BSS_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_sevl;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, setEventList() method
 * applet 2
 */
public class Api_2_Tkr_Sevl_2 extends TestToolkitApplet {

    private static byte[] MenuEntry = {(byte)'A', (byte)'p', (byte)'p', (byte)'l',
                                       (byte)'e', (byte)'t', (byte)'2'};

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Sevl_2() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Api_2_Tkr_Sevl_2 thisApplet = new Api_2_Tkr_Sevl_2();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuEntry, (short)0, (short)MenuEntry.length, (byte)0,
                                       false, (byte)0, (short)0);        
    }
    

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        boolean bRes;
        byte testCaseNb;
        short[] EventList = new short[2];

        if (EnvelopeHandlerSystem.getTheHandler().getItemIdentifier() == (byte)0x02) {
            // -----------------------------------------------------------------
            // Test Case 20: event already assigned
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;
            bRes = false;

            EventList[(short)0] = EVENT_CALL_CONTROL_BY_NAA;

            try {
                obReg.setEventList(EventList, (short)0, (short)1);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.EVENT_ALREADY_REGISTERED);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 21: Atomicity
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;

            EventList[(short)1] = EVENT_EVENT_DOWNLOAD_CALL_CONNECTED;

            try {
                obReg.setEventList(EventList, (short)0, (short)2);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.EVENT_ALREADY_REGISTERED);
            }
            catch (Exception e) {
                bRes = false;
            }
                
            bRes &= !obReg.isEventSet(EVENT_EVENT_DOWNLOAD_CALL_CONNECTED);
                
            reportTestOutcome(testCaseNb, bRes);
                
            // -----------------------------------------------------------------
            // Test Case 22: Multiple registration to the same event
            // -----------------------------------------------------------------
            testCaseNb = (byte)3;
            bRes = false;

            EventList[(short)0] = EVENT_EVENT_DOWNLOAD_MT_CALL;
            EventList[(short)1] = EVENT_EVENT_DOWNLOAD_MT_CALL;
                
            try {
                obReg.setEventList(EventList, (short)0, (short)2);
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            bRes &= obReg.isEventSet(EVENT_EVENT_DOWNLOAD_MT_CALL);
                
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
