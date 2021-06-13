//-----------------------------------------------------------------------------
// Cre_Mha_Enhd_3
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_enhd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;


public class Cre_Mha_Enhd_3 extends TestToolkitApplet {
	/** result of specific testcase */
	boolean					bRes		= false;
	/** result of test in install method */
	static boolean bInstallRes = false;

	/** number of testcase */
	byte					testCaseNb	= (byte) 0x00;
//	private short			eventList[]	= {
//											EVENT_EVENT_BROWSING_STATUS,
//											EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE,
//										  };


	/**
	 */
	private Cre_Mha_Enhd_3 () {}

	/**
	 * Create an instance of the Applet, the Java Card runtime environment will call this static method first.
	 *
	 * @param bArray the array containing installation parameters
	 * @param bOffset the starting offset in bArray
	 * @param bLength the length in bytes of the parameter data in bArray The maximum value of bLength is 127.
	 * @throws ISOException if the install method failed
	 * @see javacard.framework.Applet
	 */

	    private byte[] baTestsResults = new byte[128];
    	private byte[] baTestAppletId = new byte[17];


	public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException {
		Cre_Mha_Enhd_3 applet = new Cre_Mha_Enhd_3();

		byte aidLen = bArray[bOffset];
		if (aidLen == (byte) 0) {
			applet.register();
		}
		else {
			applet.register(bArray, (short) (bOffset + 1), aidLen);
		}

		//initialize the data of the test applet
		applet.init();

//		//register to the of the events
//		applet.obReg.setEventList(applet.eventList,(short)0,(short)applet.eventList.length);


		// - TC 1.1 (ld 25)
		// - The EnvelopeHandler is not available outside the processToolkit() method
		try{
			bInstallRes=false;
			//get the Proactive Handler
			EnvelopeHandler EnvHdr = EnvelopeHandlerSystem.getTheHandler();
		}
		catch(ToolkitException exp){
			if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
				bInstallRes=true;
			}
		}

	}

	 public void process(APDU apdu) {

		 if (selectingApplet()){
			 //- TC 1.2 (ld 25)
			 //- The EnvelopeHandler is not available outside the processToolkit() method
			 if (testCaseNb==(byte)0x00){
				 	testCaseNb=(byte)0x01;
					bRes=false;
					try{
						EnvelopeHandler EnvHdr = EnvelopeHandlerSystem.getTheHandler();
					}
					catch(ToolkitException exp){
						if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
							//bRes can be only true if the first part of test (1.1) is not failed
							if (bInstallRes==true){bRes=true;}
						}
					}
					this.reportTestOutcome(testCaseNb,bRes);
			 }


			 /* end of th test: result is sent to the Terminal */
             /* Construct and send the results of the tests */
             apdu.setOutgoing();
             apdu.setOutgoingLength((short)((short)(this.baTestAppletId[0] + this.baTestsResults[0]) +
                                    (short)2));
             apdu.sendBytesLong(this.baTestAppletId, (short)0, (short)((short)(this.baTestAppletId[0]) + (short)1));
             apdu.sendBytesLong(this.baTestsResults, (short)0, (short)((short)(this.baTestsResults[0]) + (short)1));

		 }
		 else{
			 //call the implementation from the test applet
			 super.process(apdu);
		 }
	 }


	 /**
     * Method called to initialize the AID
     */
    public void init() throws SystemException {

        // Get the AID value
        this.baTestAppletId[0] = JCSystem.getAID().getBytes(this.baTestAppletId, (short)1);
        Util.arrayFillNonAtomic(this.baTestsResults, (short)0, (short)this.baTestsResults.length, (byte)0x00);
    }

    /**
     * Method called by the test applet to report the result of each test case
     *
     * @param testCaseNumber test case number
     * @param result true if successful, false otherwise
     */
    protected void reportTestOutcome(byte testCaseNumber, boolean testCaseResult) {
        // Update the total number of tests executed
        this.baTestsResults[0] = testCaseNumber;

        // Set the Test Case Result byte to 0xCC (for Card Compliant...) if successful
        if (testCaseResult) {
            this.baTestsResults[testCaseNumber] = (byte)0xCC;
        }
        else {
            this.baTestsResults[testCaseNumber] = (byte)0x00;
        }
    }


	/* (non-Javadoc)
	 * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
	 */
	public void processToolkit(short event) throws ToolkitException {
		//
	}
}



