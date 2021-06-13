//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_api_tran;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Cat Runtime Environment, Others parts transferred from API, Transaction test
 * applet 2
 */

public class Cre_Api_Tran_2 extends TestToolkitApplet {

    private byte testCaseNb;
    private static boolean bRes;

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'1'};
	private static byte[] displayString = {(byte)'H', (byte)'E', (byte)'L', (byte)'L', (byte)'O', (byte)'2'};

    // Constructor of the applet
    public Cre_Api_Tran_2() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install (byte bArray[], short bOffset, byte bLength){

        // Create a new applet instance
        Cre_Api_Tran_2 thisApplet = new Cre_Api_Tran_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,
                                       (short) MenuInit.length, (byte) 0, false,
                                       (byte) 0, (short) 0);
    }

   /**
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {

		/** Test Case 2: Verify a pending transaction is aborted on the termination of a toolkit applet */
		testCaseNb = (byte)1;

		bRes = false;
		try {
			// send a proactive command to have the ProactiveResponseHandler available
			ProactiveHandlerSystem.getTheHandler().initDisplayText((byte)0x80, (byte)0x04, displayString, (short)0, (short)displayString.length);
			(ProactiveHandlerSystem.getTheHandler()).send();

            // applet2 is resumed
            if ((Cre_Api_Tran_1.testArray[0] == (byte)5) && (JCSystem.getTransactionDepth() == 0)) {
				bRes = true;
            }
		}
		catch (Exception e) {
		}
		reportTestOutcome(testCaseNb, bRes);
    }
}
