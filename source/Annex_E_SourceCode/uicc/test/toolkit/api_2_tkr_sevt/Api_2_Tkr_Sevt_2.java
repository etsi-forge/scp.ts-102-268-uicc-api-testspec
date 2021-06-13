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
 * applet 2
 */
public class Api_2_Tkr_Sevt_2 extends TestToolkitApplet {

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'2'};

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Sevt_2() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Sevt_2 thisApplet = new Api_2_Tkr_Sevt_2();

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

        boolean bRes ;
        byte testCaseNb;

        // -----------------------------------------------------------------
        // Test Case 12: SetEvent(EVENT_CALL_CONTROL_BY_NAA)
        // -----------------------------------------------------------------
        testCaseNb = (byte)1;
        bRes = false;
        try {
            obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
        }
        catch (ToolkitException e) {    
            bRes = (e.getReason() == ToolkitException.EVENT_ALREADY_REGISTERED);
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
    }
}

