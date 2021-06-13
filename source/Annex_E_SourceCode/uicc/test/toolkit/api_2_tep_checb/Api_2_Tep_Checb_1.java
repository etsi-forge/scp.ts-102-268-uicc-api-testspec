//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tep_checb;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, TerminalProfile class, check(byte) method
 * applet 1
 */
public class Api_2_Tep_Checb_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
    /**
     * Constructor of the applet
     */
    public Api_2_Tep_Checb_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tep_Checb_1 thisApplet = new Api_2_Tep_Checb_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_STATUS_COMMAND and EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.requestPollInterval(POLL_SYSTEM_DURATION);
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        // Result of tests
        boolean bRes = false;

        if (event == EVENT_STATUS_COMMAND) {
            /** Test Case 1: ToolkitException TERMINAL_PROFILE_NOT_AVAILABLE is sent */
            testCaseNb = (byte) 1;
            bRes = false;
            try {
                TerminalProfile.check((byte)1);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.TERMINAL_PROFILE_NOT_AVAILABLE);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }


        if (event == EVENT_UNRECOGNIZED_ENVELOPE)  {
            /** Test Case 2: Facility is supported */
            testCaseNb = (byte)2;
            bRes = false;
            try {
                bRes = TerminalProfile.check((byte)0);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 3: Facility is not supported */
            testCaseNb = (byte)3;
            bRes = false;

            try {
                if (TerminalProfile.check((byte)15) == false) {
                    bRes = true;
                } else {
                    bRes = false;
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 4: Facility index is outside TerminalProfile data */
            testCaseNb = (byte)4;
            bRes = false;
            
            try {
                if (TerminalProfile.check((byte)0x7F) == false) {
                    bRes = true;
                } else {
                    bRes = false;
                }
            }
            catch (Exception e) {
                bRes =false;
            }
            reportTestOutcome(testCaseNb, bRes);
            
            /** Test Case 5: ToolkitException BAD_INPUT_PARAMETER is sent */
            testCaseNb = (byte)5;
            bRes = false;
            try {
                TerminalProfile.check((byte)0x80);
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER) {
                    bRes = true;
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
