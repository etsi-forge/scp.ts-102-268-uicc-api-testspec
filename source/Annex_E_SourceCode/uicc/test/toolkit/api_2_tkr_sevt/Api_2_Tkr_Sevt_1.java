//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_sevt;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, setEvent() method
 * applet 1
 */
public class Api_2_Tkr_Sevt_1 extends TestToolkitApplet {

    // Not allowed events for the setEvent() method
    private static short[] NOT_ALLOWED_SET_EVENTS = {EVENT_MENU_SELECTION, EVENT_MENU_SELECTION_HELP_REQUEST,
                                                     EVENT_TIMER_EXPIRATION, EVENT_STATUS_COMMAND, 
                                                     EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION,
                                                     EVENT_EXTERNAL_FILE_UPDATE};

    // Not supported events for the setEvent() method
    private static short NOT_SUPPORTED_SETEVENT_10 = 10;
    private static short NOT_SUPPORTED_SETEVENT_24 = 24;

    // Event value range
    private static short MIN_EVENT_NB = 7;
    private static short MAX_EVENT_NB = 29;

    // Events allowed and supported for setEvent() method
    private static short MAX_EVENT = 21;                // 27  events - 6 not allowed events
    private short[] EventList = new short[MAX_EVENT];

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Sevt_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Api_2_Tkr_Sevt_1 thisApplet = new Api_2_Tkr_Sevt_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
    }


    /**
     * Method called by the processToolkit() method to know if event
     * is an allowed event for the clearEvent() or setEvent() method
     */
    private boolean isEventAllowed(short event) {
        boolean allowed = true;

        for(short i = 0; (i < NOT_ALLOWED_SET_EVENTS.length) && (allowed); i++) {
            allowed = (NOT_ALLOWED_SET_EVENTS[i] != event);
        }
        return allowed;
    }

    /**
     * Method called by the processToolkit() method to know if event
     * is a supported event for the setEvent() method
     */
    private boolean isEventSupported(short event) {
        return  (((event != NOT_SUPPORTED_SETEVENT_10)
                &&(event != NOT_SUPPORTED_SETEVENT_24)
                && (event >= MIN_EVENT_NB)
                && (event <= MAX_EVENT_NB))
                || (event == EVENT_UNRECOGNIZED_ENVELOPE)
                || (event == EVENT_PROFILE_DOWNLOAD)
                || (event == EVENT_PROACTIVE_HANDLER_AVAILABLE)
                || (event == EVENT_EXTERNAL_FILE_UPDATE)
                || (event == EVENT_APPLICATION_DESELECT)
                || (event == EVENT_FIRST_COMMAND_AFTER_ATR));
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        // Result of tests
        boolean bRes;

        // Number of tests
        byte testCaseNb = (byte) 0x0B;

        short j = 0;
        short eventID;

        // Build the allowed and supported events list
        for(short i = MIN_EVENT_NB; i <= MAX_EVENT_NB; i++) {

            if ((this.isEventAllowed(i)) && (this.isEventSupported(i))) {
                EventList[j] = i;
                j++;
            }
        }
        EventList[j++] = EVENT_UNRECOGNIZED_ENVELOPE;
        EventList[j++] = EVENT_PROFILE_DOWNLOAD;
        EventList[j++] = EVENT_PROACTIVE_HANDLER_AVAILABLE;
        EventList[j++] = EVENT_APPLICATION_DESELECT;
        EventList[j]   = EVENT_FIRST_COMMAND_AFTER_ATR;

        if (event == EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) {

            // -----------------------------------------------------------------
            // Test Case 1: Applet1 is triggered by envelope(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) command
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;
            reportTestOutcome(testCaseNb, true);

            // -----------------------------------------------------------------
            // Test Case 2: For all events defined in ETSI TS 102 241 and allowed,
            //              clearEvent(event), isEventSet(event), setEvent(event)
            //              isEventSet(event), clearEvent(event)
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;

            try {
                for(short i = 0; i < (short)EventList.length; i++) {
                    bRes = false;

                    if ((this.isEventAllowed(EventList[i]))
                        && (this.isEventSupported(EventList[i]))) {

                        obReg.clearEvent(EventList[i]);
                        if (obReg.isEventSet(EventList[i])) {
                            bRes = false;
                            break;
                        }

                        obReg.setEvent(EventList[i]);

                        if (!obReg.isEventSet(EventList[i])) {
                            bRes = false;
                            break;
                        }

                        obReg.clearEvent(EventList[i]);
                        bRes = true;
                    } else {
                        bRes = true;
                    }
                }
            }
            catch (Exception e) {    // No exception shall be thrown
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 3: setEvent 0
            // -----------------------------------------------------------------
            testCaseNb = (byte) 3;
            bRes = false;

            try {
                eventID = (byte) 0;
                obReg.setEvent(eventID);
            }
            catch (ToolkitException e) {    // Only EVENT_NOT_SUPPORTED should be thrown
                bRes = (e.getReason()==ToolkitException.EVENT_NOT_SUPPORTED);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 4: Set EVENT_MENU_SELECTION
            // Test Case 5: Set EVENT_MENU_SELECTION_HELP_REQUEST
            // Test Case 6: Set EVENT_TIMER_EXPIRATION
            // Test Case 7: Set EVENT_STATUS_COMMAND
            // Test Case 8: Set EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
            // Test Case 9: Set EVENT_EXTERNAL_FILE_UPDATE
            // -----------------------------------------------------------------
            for (short i = (short)(NOT_ALLOWED_SET_EVENTS.length-1); (i > (short) -1); i--) {

                bRes = false;
                try {
                    obReg.setEvent(NOT_ALLOWED_SET_EVENTS[i]);
                }
                catch (ToolkitException e) {    // Only EVENT_NOT_ALLOWED should be thrown
                    bRes = (e.getReason()==ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }

                testCaseNb = (byte)(testCaseNb + 1); // Test Cases: 4-9
                reportTestOutcome(testCaseNb, bRes);
            }
            reportTestOutcome(testCaseNb, bRes);


            // -----------------------------------------------------------------
            // Test Case 10: Set EVENT_CALL_CONTROL_BY_NAA
            // -----------------------------------------------------------------
            testCaseNb = (byte)10;
            bRes = false;

            try {
                obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Cases 11: by default
            // -----------------------------------------------------------------
            testCaseNb = (byte)11;
            reportTestOutcome(testCaseNb, false);
        }
        else {
            if (event == EVENT_CALL_CONTROL_BY_NAA) {
                // -----------------------------------------------------------------
                // Test Case 10: Check applet is triggered by an envelope(EVENT_CALL_CONTROL_BY_NAA)
                // -----------------------------------------------------------------
                testCaseNb = (byte)11;
                reportTestOutcome(testCaseNb, true);
            }
        }
    }
}
