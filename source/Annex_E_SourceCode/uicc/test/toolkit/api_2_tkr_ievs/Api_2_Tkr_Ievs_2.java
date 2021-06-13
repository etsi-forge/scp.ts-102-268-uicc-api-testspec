//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_ievs;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, isEventSet() method
 * applet 2
 */
public class Api_2_Tkr_Ievs_2 extends TestToolkitApplet {

    private static byte[] MenuEntry2 = {(byte)'T', (byte)'e', (byte)'s', (byte)'t', (byte)'2'};

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Ievs_2() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Ievs_2 thisApplet = new Api_2_Tkr_Ievs_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuEntry2, (short)0, (short)MenuEntry2.length, (byte)0,
                                       false, (byte)0, (short)0);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        
        // Result of tests
        boolean bRes = false;

        // Number of tests
        byte testCaseNb = (byte)0x01;
        
        // test case 10: check applet2 is triggered
        bRes = !obReg.isEventSet(EVENT_UNRECOGNIZED_ENVELOPE);
        reportTestOutcome(testCaseNb, bRes);
    }
}
