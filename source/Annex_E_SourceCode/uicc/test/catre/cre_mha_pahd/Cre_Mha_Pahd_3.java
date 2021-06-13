//-----------------------------------------------------------------------------
//Cre_Mha_Pahd
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_pahd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;


public class Cre_Mha_Pahd_3 extends TestToolkitApplet {
	/** result of specific testcase */
	boolean					bRes		= false;
	/** result of test in install method */
	static boolean bIRes = false;

	/** number of testcase */
	byte					testCaseNb	= (byte) 0x00;
//	private short			eventList[]	= {
//											EVENT_EVENT_BROWSING_STATUS,
//											EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE,
//											EVENT_PROACTIVE_HANDLER_AVAILABLE
//										  };


	/**
	 */
	private Cre_Mha_Pahd_3 () {}


	    private byte[] baTestsResults = new byte[128];
    	private byte[] baTestAppletId = new byte[17];
	/**
	 * Create an instance of the Applet, the Java Card runtime environment will call this static method first.
	 *
	 * @param bArray the array containing installation parameters
	 * @param bOffset the starting offset in bArray
	 * @param bLength the length in bytes of the parameter data in bArray The maximum value of bLength is 127.
	 * @throws ISOException if the install method failed
	 * @see javacard.framework.Applet
	 */
	public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException {
		Cre_Mha_Pahd_3 applet = new Cre_Mha_Pahd_3();
		//initialize the data of the test applet
		byte aidLen = bArray[bOffset];
		if (aidLen == (byte) 0) {
			applet.register();
		}
		else {
			applet.register(bArray, (short) (bOffset + 1), aidLen);
		}

		applet.init();

		//register to the resto of the events
//		applet.obReg.setEventList(applet.eventList,(short)0,(short)applet.eventList.length);


		// - TC 1.1 (ld 43)
		// - The ProactiveHandler is not available outside the processToolkit() method
		try{
			bIRes=false;
			//get the Proactive Handler
			ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
		}
		catch(ToolkitException exp){
			if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
				bIRes=true;
			}
		}

	}

	 public void process(APDU apdu) {
		 if (selectingApplet()){
			 //- TC 1.2 (ld 43)
			 //- The ProactiveHandler is not available outside the processToolkit() method
			 if (testCaseNb==(byte)0x00){
				 	testCaseNb=(byte)0x01;
					bRes=false;
					try{
						ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
					}
					catch(ToolkitException exp){
						if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
							//bRes can be only true if the first part of test (1.1) is not failed
							if (bIRes==true){bRes=true;}
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



