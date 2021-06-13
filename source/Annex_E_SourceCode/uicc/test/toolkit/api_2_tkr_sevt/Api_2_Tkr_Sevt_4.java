//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_sevt;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, setEvent() method
 * applet 4
 */
public class Api_2_Tkr_Sevt_4 extends TestToolkitApplet {

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'4'};
    private byte testCaseNb;

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Sevt_4() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Api_2_Tkr_Sevt_4 thisApplet = new Api_2_Tkr_Sevt_4();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short) MenuInit.length, 
                                       (byte) 0, false, (byte) 0, (short) 0);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        // Result of tests
        boolean bRes = false;

        // -----------------------------------------------------------------
        // Test Case 14: applet registers more than once to the same event
        // -----------------------------------------------------------------
        testCaseNb = (byte) 0x01;

        // 2- setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL)
        try {
            obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
            bRes=true;
        }
        catch (ToolkitException e) {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        bRes=false;

        // 3- setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL)
        testCaseNb = (byte) 0x02;
        try {
            obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
            bRes=true;
        }
        catch (ToolkitException e) {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
            
        // 4- isEventSet(EVENT_EVENT_DOWNLOAD_MT_CALL)
        testCaseNb = (byte) 0x03;
        try {
            obReg.isEventSet(EVENT_EVENT_DOWNLOAD_MT_CALL);
            bRes=true;
        }
        catch (ToolkitException e) {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
    }
}
