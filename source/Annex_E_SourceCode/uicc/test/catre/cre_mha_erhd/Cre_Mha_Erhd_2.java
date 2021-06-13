//-----------------------------------------------------------------------------
//FWK_MHA_ERHD
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_erhd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;
import uicc.access.UICCConstants;


public class Cre_Mha_Erhd_2 extends TestToolkitApplet {

	/** result of specific testcase */
	private boolean					bRes				= false;
	/** number of testcase */
	private byte					testCaseNb			= (byte)0x00;
	/** use for counting the triggering of the unrecognize envelope*/
	private byte					triggerNr			= (byte)0x00;
	;
	/**stores the value of the result */
	private boolean					bRes_old			= false;
	/** list included registered all events */
	private short 			eventList[]	= {EVENT_UNRECOGNIZED_ENVELOPE};



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
		Cre_Mha_Erhd_2 applet = new Cre_Mha_Erhd_2();
		byte aidLen = bArray[bOffset];
		if (aidLen == (byte) 0) {
			applet.register();
		}
		else {
			applet.register(bArray, (short) (bOffset + 1), aidLen);
		}

		//initialize the data of the test applet
		applet.init();

		//register to the resto of the events
		applet.obReg.setEventList(applet.eventList,(short)0,(short)(applet.eventList.length));
	}


	/* (non-Javadoc)
	 * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
	 */
	public void processToolkit(short event) throws ToolkitException {
		bRes=false;
		testCaseNb+=(byte)0x01;
		switch (event) {
		    //-- TC 19.1
            case EVENT_UNRECOGNIZED_ENVELOPE:
		        if(triggerNr==(byte)0x00 ){//TC 19.1
		            try{
		                EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
		            }
		            catch(ToolkitException exp){
		                if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
		                    bRes=true;
		                }
		            }
		            bRes_old=bRes;
		            //next trigger belongs to this test case
		            triggerNr+=(byte)0x01;
                    testCaseNb=(byte)(testCaseNb-(byte)0x01);
		        }

                //-- TC 19.2
    	        else if(triggerNr==(byte)0x01){
//    	        	testCaseNb=(byte)0x01;
    	            //if bRes_old is false the first part of this test case failed
    	            if(bRes_old){
    	                try{
    		                EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
    		            }
    		            catch(ToolkitException exp){
    		                if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
    		                    bRes=true;
    		                }
    		            }
    	            }
    	            triggerNr+=(byte)0x01;
                    this.reportTestOutcome(testCaseNb,bRes);
    	        }

                //-- TC 21
    	        else if(triggerNr==(byte)0x02) {
    	        	try{
    	                //set tc to 2 because tc 20 use an unrec. envelope also
                        testCaseNb=(byte)0x02;
                        EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
    	                bRes=true;
    	            }
    	            catch(Exception exp){bRes=false;}
    	            //triggerNr=(byte)0x00;
                    this.reportTestOutcome(testCaseNb,bRes);
    	        }


	        break;

			default:
		    break;
		}
	}
//	/**
//	 * If the EnvelopeResponseHandler <b>is available</b>, the test
//	 * case will be reported as successful. Otherwise as false.
//	 *
//	 * @param testCaseNb Number of current test case
//	 * @return @return true if the EnvelopeResponseHandler is available.
//	 * Otherwise false.
//	 */
//	boolean isHandlerNotAvailable(byte testCaseNb) {
//	    try {
//			EnvelopeResponseHandler EnvRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
//		}
//	    catch(ToolkitException exp) {
//	    	if(exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE) {
//	    		bRes=true;
//	    	}
//	    }
//		catch(Exception exp) {bRes=false;}
//		this.reportTestOutcome(testCaseNb,bRes);
//		return bRes;
//	}

}
