//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tep_gval;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, TerminalProfile class, getValue() method
 * applet 1
 */
public class Api_2_Tep_Gval_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
    /**
     * Constructor of the applet
     */
    public Api_2_Tep_Gval_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tep_Gval_1 thisApplet = new Api_2_Tep_Gval_1();

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
        boolean bRes;

        if (event == EVENT_STATUS_COMMAND) {
            /** Test Case 1: ToolkitException TERMINAL_PROFILE_NOT_AVAILABLE is sent */
            testCaseNb = (byte) 1;
            bRes = false;
            try {
                TerminalProfile.getValue((short)1, (short)0);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.TERMINAL_PROFILE_NOT_AVAILABLE);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /** Test Case 2: Characters down Terminal Display */
            testCaseNb = (byte)2;
            bRes = false;
            try {
                bRes = (TerminalProfile.getValue((short)108, (short)104) == 13);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 3: Retrieve byte 3 and 4 */
            testCaseNb = (byte)3;
            bRes = false;

            try {
                bRes = (TerminalProfile.getValue((short)31, (short)16) == (short)0xF0D2);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 4: indexMSB < 0 */
            testCaseNb = (byte) 4;
            bRes = false;
            try {
                TerminalProfile.getValue((short)0xFFFF, (short)0xFFFD);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 5: indexLSB < 0 */
            testCaseNb = (byte) 5;
            bRes = false;
            try {
                TerminalProfile.getValue((short)0x0002, (short)0xFFFD);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 6: indexMSB < indexLSB */
            testCaseNb = (byte) 6;
            bRes = false;
            try {
                TerminalProfile.getValue((short)0x0002, (short)0x0003);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 7: indexMSB > indexLSB + 16 */
            testCaseNb = (byte) 7;
            bRes = false;
            try {
                TerminalProfile.getValue((short)0x0021, (short)0x0010);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 8: indexMSB = indexLSB + 16 */
            testCaseNb = (byte) 8;
            bRes = false;
            try {
                TerminalProfile.getValue((short)0x0020, (short)0x0010);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 9: indexMSB is outside data available */
            testCaseNb = (byte)9;
            bRes = false;

            try {
                bRes = (TerminalProfile.getValue((short)121, (short)115) == (short)0x001F);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
