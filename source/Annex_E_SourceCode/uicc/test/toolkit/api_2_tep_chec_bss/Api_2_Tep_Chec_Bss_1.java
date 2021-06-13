//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tep_chec_bss;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, TerminalProfile class, check(byte[], short, short) method
 * applet 1
 */
public class Api_2_Tep_Chec_Bss_1 extends TestToolkitApplet {

    private static final byte[] COMPARE = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
                                           (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
                                           (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
                                           (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xA9,
                                           (byte)0x77};

    /**
     * Constructor of the applet
     */
    public Api_2_Tep_Chec_Bss_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tep_Chec_Bss_1 thisApplet = new Api_2_Tep_Chec_Bss_1();

        // Register the new applet instance to the JCRE.
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
        
        // Register to unrecognized envelope
        thisApplet.obReg.requestPollInterval(POLL_SYSTEM_DURATION);
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        
        byte testCaseNb = (byte)0x00 ;
        // Result of tests
        boolean bRes = false;

        if (event == EVENT_STATUS_COMMAND) {
            /** Test Case 1: ToolkitException ME_PROFILE_NOT_AVAILABLE is sent */
            testCaseNb = (byte)1;
            bRes = false;

            try {
                TerminalProfile.check(COMPARE, (short)0, (short)16);
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

            /** Test Case 2: NULL as parameter to check */
            testCaseNb = (byte)2;
            bRes = false;

            try {
                TerminalProfile.check(null, (short)0, (short)1);
            }
            catch (NullPointerException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 3: Offset > COMPARE.length */
            testCaseNb = (byte)3;
            bRes = false;

            try {
                TerminalProfile.check(COMPARE, (short)17, (short)1);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 4: Offset < 0 */
            testCaseNb = (byte)4;
            bRes = false;

            try {
                TerminalProfile.check(COMPARE, (short)-1, (short)1);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 5: Length > COMPARE.length */
            testCaseNb = (byte)5;
            bRes = false;

            try {
                TerminalProfile.check(COMPARE, (short)0, (short)18);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 6: Offset+Length > COMPARE.length */
            testCaseNb = (byte)6;
            bRes = false;

            try {
                TerminalProfile.check(COMPARE, (short)9, (short)9);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            
            /** Test Case 7: length < 0 */
            testCaseNb = (byte)7;
            bRes = false;
            
            try {
                TerminalProfile.check(COMPARE, (short)0, (short)-1);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 8: length = 0 */
            testCaseNb = (byte)8;
            bRes = false;

            try {
                 bRes = TerminalProfile.check(COMPARE, (short)0, (short)0);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 9: Check all the Terminal Profile */
            testCaseNb = (byte)9;
            bRes = false;

            try {
                if (TerminalProfile.check(COMPARE, (short)0, (short)16) == false) {
                    bRes = true;
                } else {
                    bRes = false;
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 10: Check 2 first bytes of the Terminal Profile */
            testCaseNb = (byte)10;
            bRes = false;

            try {
                bRes = TerminalProfile.check(COMPARE,(short)15, (short)2);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 11: Check facility 15 of the Terminal Profile */
            testCaseNb = (byte)11;
            bRes = false;
            byte[] COMPARE2 = {(byte)0x00,(byte)0x80};

            try {
                if (TerminalProfile.check(COMPARE2, (short)0, (short)2) == false) {
                    bRes = true;
                } else {
                    bRes = false;
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
