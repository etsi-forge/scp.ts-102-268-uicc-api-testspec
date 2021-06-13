//-----------------------------------------------------------------------------
//FWK_MHA_PRHD
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//----------------------------------------------------------------------------- 
package uicc.test.catre.cre_mha_prhd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;
import uicc.access.UICCConstants;


public class Cre_Mha_Prhd_3 extends TestToolkitApplet {
	
	/** result of specific testcase */
	boolean					bRes				= false;
	/** result of test in install method */
	static boolean bInstallRes = false;
	/** number of testcase */
	private byte					testCaseNb			= (byte) 0x00;
	/** */
	private boolean					HANDLER_AVAILABLE	= true;
	/** list included registered all events */
//	private short 			eventList[]	= {
//											EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE,
//											EVENT_EVENT_BROWSING_STATUS,
//											EVENT_PROACTIVE_HANDLER_AVAILABLE,
//											};
	
	
	
	/** */
	private Cre_Mha_Prhd_3 () {}
	
	
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
		Cre_Mha_Prhd_3 applet = new Cre_Mha_Prhd_3();
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
//		applet.obReg.setEventList(applet.eventList,(short)0,(short)(applet.eventList.length));
		//register to file EF_TARU
//		applet.obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,new byte[]{(byte) 0x6F03},(short)0,(short)1,null,(short)0,(byte)0x00);

		// - TC 1.1 (ld 43) 
		// - The ProactiveResponseHandler is not available outside the processToolkit() method
		try{
			bInstallRes=false;
			//get the ProactiveResponseHandler 
			ProactiveResponseHandler proRHdr = ProactiveResponseHandlerSystem.getTheHandler();
		}
		catch(ToolkitException exp){
			if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
				bInstallRes=true;
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
						ProactiveResponseHandler proRHdr = ProactiveResponseHandlerSystem.getTheHandler();
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
		bRes=false;
		
	
		
	}


}
	