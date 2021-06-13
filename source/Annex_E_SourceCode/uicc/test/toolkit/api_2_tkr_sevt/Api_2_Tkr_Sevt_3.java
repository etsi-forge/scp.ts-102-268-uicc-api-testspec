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
 * applet 3
 */
public class Api_2_Tkr_Sevt_3 extends TestToolkitApplet {

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'3'};
    protected static boolean bRes = false;


    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Sevt_3() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Sevt_3 thisApplet = new Api_2_Tkr_Sevt_3();

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

        byte testCaseNb = (byte) 0x01;

        // -----------------------------------------------------------------
        // Test Case 13: registers to CALL_CONTROL_BY_NAA but it is already 
        //               assigned to another applet in not selectable state
        // -----------------------------------------------------------------
        testCaseNb = (byte) 0x01;
        try {
            obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
            bRes=false;
        }
        catch (ToolkitException e) {
            bRes = (e.getReason() == ToolkitException.EVENT_ALREADY_REGISTERED);
        }
        reportTestOutcome(testCaseNb, bRes);
    }
}

