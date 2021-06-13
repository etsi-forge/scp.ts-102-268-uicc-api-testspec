//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rpol;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, requestPollInterval() method
 * applet 1
 */
public class Api_2_Tkr_Rpol_1 extends TestToolkitApplet {

	private short CallNB = 0;
	private short [] AllowedDurationValues = {(short) 1, (short) 255, (short) 256, (short) 15300};
	private short [] ForbiddenDurationValues = {(short) 15301, (short) 32767, (short) -2, (short)-32768 };


	/**
     * Constructor of the applet
     */
    public Api_2_Tkr_Rpol_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Rpol_1 thisApplet = new Api_2_Tkr_Rpol_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        boolean bRes ;
        byte testCaseNb = (byte) 7;
		short i;

		if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

			CallNB++;
			switch (CallNB) {

			case (short)1:
				// -----------------------------------------------------------------
				// Test Case 1:	Request a value between 1 and 15300 s 
				// -----------------------------------------------------------------
				testCaseNb = (byte)1;

				bRes = !obReg.isEventSet(EVENT_STATUS_COMMAND);
				if (bRes) {
					for (i = 0;bRes && (i < AllowedDurationValues.length);i++) {
						try {
							bRes = false;
							obReg.requestPollInterval(AllowedDurationValues[i]);
							bRes = true;
						}
						catch (Exception e) {
							bRes = false;
						}
					}
				}
				bRes = bRes && obReg.isEventSet(EVENT_STATUS_COMMAND);

				reportTestOutcome(testCaseNb, bRes);

				// -----------------------------------------------------------------
				// Test Case 2 :	Check Applet is triggered by a STATUS command
				// -----------------------------------------------------------------
				reportTestOutcome((byte)2, false);
				break;

			case (short)2:
				// -----------------------------------------------------------------
				// Test Case 3 :	Requesting POLL SYSTEM DURATION
				// -----------------------------------------------------------------
				testCaseNb = (byte) 3;
		
				bRes = obReg.isEventSet(EVENT_STATUS_COMMAND);
				if (bRes) {
				bRes = false;
					try {
						obReg.requestPollInterval(POLL_SYSTEM_DURATION);
						bRes = true;
					}
					catch (Exception e) {
						bRes = false;
					}
				}
				bRes = bRes && obReg.isEventSet(EVENT_STATUS_COMMAND);

				reportTestOutcome(testCaseNb, bRes);

				// -----------------------------------------------------------------
				// Test Case 4 :	Check Applet is triggered by a STATUS command
				// -----------------------------------------------------------------
				reportTestOutcome((byte)4, false);
				break;

			case (short)3:
				// -----------------------------------------------------------------
				// Test Case 5 :	requestPollInterval(duration) for following
				//					values: 15301, 32767, -2, -32768
				// -----------------------------------------------------------------
				testCaseNb = (byte)5;
				bRes = true;

				for (i = (short) 0;bRes && (i < ForbiddenDurationValues.length);i++) {
				
					bRes = false;			
					try {
						obReg.requestPollInterval(ForbiddenDurationValues[i]);
					}
					catch (ToolkitException e) {
						bRes = (e.getReason()==ToolkitException.REGISTRY_ERROR);
					}
					catch (Exception e) {
					bRes = false;
					}
				}
				reportTestOutcome(testCaseNb, bRes);

				// -----------------------------------------------------------------
				// Test Case 6:	Requesting POLL_NO_DURATION
				// -----------------------------------------------------------------
				testCaseNb = (byte)6;
				bRes = obReg.isEventSet(EVENT_STATUS_COMMAND);
				
				if (bRes) {
				bRes = false;
					try {
						obReg.requestPollInterval(POLL_NO_DURATION);
						bRes = true;
					}
					catch (Exception e) {
						bRes = false;
					}
				}
				bRes = bRes && !obReg.isEventSet(EVENT_STATUS_COMMAND);

				reportTestOutcome(testCaseNb, bRes);
                
				// -----------------------------------------------------------------
				// Test Case 7:	Check Applet isn't triggered by a STATUS command
				// -----------------------------------------------------------------
				reportTestOutcome((byte) 7, true);
	
			}  // End switch
		} 
		else {
			if (event == EVENT_STATUS_COMMAND) {

				switch (CallNB) {

				case (short)1:
					// -----------------------------------------------------------------
					// Test Case 2:	Check Applet is triggered by a STATUS command
					// -----------------------------------------------------------------
					reportTestOutcome((byte) 2, true);
					break;

				case (short)2:
					// -----------------------------------------------------------------
					// Test Case 4:	Check Applet is triggered by a STATUS command
					// -----------------------------------------------------------------

					reportTestOutcome((byte) 4, true);
					break;

				case (short)3:
					// -----------------------------------------------------------------
					// Test Case 7 :	Check Applet isn't triggered by an STATUS command
					// -----------------------------------------------------------------
					reportTestOutcome((byte) 7, false);
				}
			}
		}
	}
}
