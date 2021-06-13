package uicc.test.toolkit.api_2_tkr_gpol;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;


/**
 * uicc.toolkit package, ToolkitRegistry interface, getPollInterval() method
 * applet 1
 */
public class Api_2_Tkr_Gpol_1 extends TestToolkitApplet {
    boolean bRes;
    byte testCaseNb;

    // Trigger number
    byte callNb = (byte) 0;

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Gpol_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance
        Api_2_Tkr_Gpol_1 thisApplet = new Api_2_Tkr_Gpol_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short) (bOffset + 1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        short i;
        short pollInterval;

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {
            callNb++;

            switch (callNb) {
            /** Test Case 1 :
            *  Applet isn't registered to EVENT_STATUS_COMMAND
            */
            case (byte) 1:
                testCaseNb = (byte) 1;
                reportTestOutcome(testCaseNb, obReg.getPollInterval() == 0);

                break;

            /** Test Case 2 :
             *  Requesting max duration
             */
            case (byte) 2:
                testCaseNb = (byte) 2;
                bRes = false;

                try {
                    obReg.requestPollInterval((short) 15300);
                    bRes = true;
                } catch (Exception e) {
                    bRes = false;
                }

                break;

            case (byte) 3:
                pollInterval = obReg.getPollInterval();
                reportTestOutcome(testCaseNb, bRes && (pollInterval > 0) && (pollInterval < 15301));

                break;

            /** Test Case 3 :
            *   Requesting System Duration
            */
            case (byte) 4:
                testCaseNb = (byte) 3;
                bRes = false;

                try {
                    obReg.requestPollInterval(POLL_SYSTEM_DURATION);
                    bRes = true;
                } catch (Exception e) {
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes);

                break;

            case (byte) 5:
                pollInterval = obReg.getPollInterval();
                reportTestOutcome(testCaseNb, bRes && (pollInterval > 0) && (pollInterval < 15301));

                break;

            /** Test Case 4 :
             *  Requesting no Duration
             */
            case (byte) 6:
                testCaseNb = (byte) 4;
                bRes = false;

                try {
                    obReg.requestPollInterval(POLL_NO_DURATION);
                    bRes = true;
                } catch (Exception e) {
                    bRes = false;
                }

                break;

            case (byte) 7:
                reportTestOutcome(testCaseNb, bRes && (obReg.getPollInterval() == 0));
            }
        }
    }
}
