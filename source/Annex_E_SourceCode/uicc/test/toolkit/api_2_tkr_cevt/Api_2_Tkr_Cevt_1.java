//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_cevt;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.access.*;
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, clearEvent() method
 * applet 1
 */
public class Api_2_Tkr_Cevt_1 extends TestToolkitApplet {

    // Not allowed events for the ClearEvent() method
    private static short[] NOT_ALLOWED_CLEAR_EVENTS = { EVENT_MENU_SELECTION, EVENT_MENU_SELECTION_HELP_REQUEST,
                                                        EVENT_TIMER_EXPIRATION, EVENT_STATUS_COMMAND, 
                                                        EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION};

    // Not supported events for the setEvent() method
    private static short NOT_SUPPORTED_SETEVENT_10 = 10;
    private static short NOT_SUPPORTED_SETEVENT_24 = 24;

    // Event value range
    private	static short MIN_EVENT_NB = 7;
    private	static short MAX_EVENT_NB = 29;

    // Events allowed and supported for clearEvent() method
    private static short MAX_EVENT = 22;                // 27  events - 5 not allowed events
    private short[] EventList = new short[MAX_EVENT];

    // Indicate if that applet has already been triggered once
    // -->all events have been cleared
    private	boolean eventsCleared = false;

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Cevt_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Cevt_1 thisApplet = new Api_2_Tkr_Cevt_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }
    

    /**
     * Method called by the processToolkit() method to know if event
     * is an allowed event for the clearEvent() method
     */
    private boolean isEventAllowed(short event) {
        boolean allowed = true;

        for(short i = 0; (i < NOT_ALLOWED_CLEAR_EVENTS.length) && (allowed); i++) {
            allowed = (NOT_ALLOWED_CLEAR_EVENTS[i] != event);
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

        boolean bRes ;
        byte testCaseNb ;
        short j = 0;

        // Build the allowed and supported events list to be set AND clear 
        for(short i = MIN_EVENT_NB; i <= MAX_EVENT_NB;i++) {

            if ((this.isEventAllowed(i)) && (this.isEventSupported(i))) {
                EventList[j] = i;
                j++;
            }
        }
        EventList[j++] = EVENT_UNRECOGNIZED_ENVELOPE;
        EventList[j++] = EVENT_PROFILE_DOWNLOAD;
        EventList[j++] = EVENT_PROACTIVE_HANDLER_AVAILABLE;
        EventList[j++] = EVENT_APPLICATION_DESELECT;
        EventList[j++] = EVENT_FIRST_COMMAND_AFTER_ATR;
        EventList[j]   = EVENT_EXTERNAL_FILE_UPDATE;


        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            // -----------------------------------------------------------------
            // Test Case 1 : Clear ALLOWED unregistered events
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;

            bRes = false;

            for(short i=0; i < (short)(EventList.length); i++) {
                bRes = false;

                if ((this.isEventAllowed(EventList[i])) && (this.isEventSupported(i))) {
                    try {
                        obReg.clearEvent(EventList[i]);
                        bRes = true;
                    }
                    catch (Exception e) { 
                        bRes = false;
                    }

                    if (obReg.isEventSet(EventList[i])) {
                    // A cleared event should not be set anymore
                        bRes = false;
                        break;
                    }
                }
            }
            reportTestOutcome(testCaseNb,bRes);

            // -----------------------------------------------------------------
            // Test Case 2 : Clear registered events
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;

            // set all allowed and supported events

            try {
                // Do not perform setEvent() with EVENT_EXTERNAL_FILE_UPDATE
                for(short i=0; i < (short)(EventList.length - 1 ); i++) {

                    if ((this.isEventAllowed(EventList[i])) && (this.isEventSupported(EventList[i]))) {
                        obReg.setEvent(EventList[i]);
                    }
                }
                // Register to EVENT_EXTERNAL_FILE_UPDATE
                obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE, UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT));
                bRes = true;
            }
            catch (Exception e) {   // No exception should be thrown
                bRes = false ;
            }
    
            // clear all set events
            if (bRes) {
                try {
                    for(short i=0; i < (short)(MAX_EVENT); i++) {

                        if (this.isEventAllowed(EventList[i])) {
                            obReg.clearEvent(EventList[i]);

                            // check events are not set anymore
                            if(obReg.isEventSet(EventList[i])) {
                                bRes = false;
                                break;
                            }
                        }
                    }
                }
                catch (Exception e) {
                    bRes = false ;
                }
            }

            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 3 : Clear NOT ALLOWED events
            // -----------------------------------------------------------------
            testCaseNb = (byte)3;
            bRes = true;

            for (short i = 0; (i < NOT_ALLOWED_CLEAR_EVENTS.length) && (bRes); i++) {
                bRes = false;
                try {
                    obReg.clearEvent(NOT_ALLOWED_CLEAR_EVENTS[i]);
                }
                catch (ToolkitException e) {    // Only EVENT_NOT_ALLOWED should be thrown
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------------------------------
            // Test Case 4 : Check applet is not triggered by an 
            //               envelope(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) command
            // --------------------------------------------------------------------
            testCaseNb = (byte)4;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
        }
        if (event == EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) {
            testCaseNb = (byte)4;
            reportTestOutcome(testCaseNb, false);
        }
    }
}
