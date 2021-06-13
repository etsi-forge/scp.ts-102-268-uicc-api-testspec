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
 * applet 1
 */

public class Cre_Api_Tran_1 extends TestToolkitApplet {

    private byte testCaseNb;
    private static boolean bRes;

    /** Registry entry concerning applet instance */
	private static byte[] displayString = {(byte)'H', (byte)'E', (byte)'L', (byte)'L', (byte)'O', (byte)'1'};
	static byte[] testArray = {(byte)0xFF};

    // Constructor of the applet
    public Cre_Api_Tran_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // Create a new applet instance
        Cre_Api_Tran_1 thisApplet = new Cre_Api_Tran_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }


   /**
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {

        switch (testCaseNb) {
        case 0x00:
		    /** Test Case 1: Verify a pending transaction is aborted when a proactive command is sent */
		    testCaseNb = (byte)1;

		    bRes = false;
		    testArray[0] = (byte)0x05;
		    try {
			    JCSystem.beginTransaction();
			    testArray[0] = (byte)0x02;
			    // send a proactive command to have the ProactiveResponseHandler available
			    ProactiveHandlerSystem.getTheHandler().initDisplayText((byte)0x80, (byte)0x04, displayString, (short)0, (short)displayString.length);
			    (ProactiveHandlerSystem.getTheHandler()).send();

                if ((testArray[0] == (byte)5) && (JCSystem.getTransactionDepth() == 0)) {
				    bRes = true;
                }
		    }
		    catch (Exception e) {
		    }

		    reportTestOutcome(testCaseNb, bRes);
			
            break;

        case 0x01:
		    /** Test Case 2: Verify a pending transaction is aborted on the termination of a toolkit applet */
			testCaseNb = (byte)2;
			
		    testArray[0] = (byte)0x05;
		    try {
				bRes = true;
				reportTestOutcome(testCaseNb, bRes);
				bRes = false;
			    JCSystem.beginTransaction();
			    testArray[0] = (byte)0x02;
		    }
		    catch (Exception e) {
		    }
            reportTestOutcome(testCaseNb, bRes);
            
            break;
        }
    }
}
