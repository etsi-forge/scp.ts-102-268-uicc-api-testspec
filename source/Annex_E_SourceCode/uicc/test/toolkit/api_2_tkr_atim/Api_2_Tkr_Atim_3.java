//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_atim;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.system.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * uicc.toolkit package, ToolkitRegistry interface, allocateTimer() method
 * applet 3
 */
public class Api_2_Tkr_Atim_3 extends TestToolkitApplet {
    
    private byte testCaseNb;
    private static boolean bRes;

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'3'};

    // Constructor of the applet
    public Api_2_Tkr_Atim_3() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // create a new applet instance
        Api_2_Tkr_Atim_3 thisApplet = new Api_2_Tkr_Atim_3();

        // register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // initialise the data of the test applet
        thisApplet.init();

        // registration to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,
                                       (short) MenuInit.length, (byte) 0, false,
                                       (byte) 0, (short) 0);
    }


    /**
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {

        // -----------------------------------------------------------------
        // Test Case 5: The applet allocates 1 timer
        //              Shall throw a ToolkitException 
        //              with reason NO_TIMER_AVAILABLE
        // -----------------------------------------------------------------
        testCaseNb = (byte)0x01;
        bRes = false;

        try {    
            obReg.allocateTimer();
        }
        catch (ToolkitException e) {
            bRes = e.getReason() == ToolkitException.NO_TIMER_AVAILABLE;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
    }
}