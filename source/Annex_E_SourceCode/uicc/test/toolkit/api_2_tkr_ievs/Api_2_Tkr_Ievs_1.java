//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_ievs;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, isEventSet() method
 * applet 1
 */
public class Api_2_Tkr_Ievs_1 extends TestToolkitApplet {

    // Not allowed events for the setEvent() method
    private static short[] NOT_ALLOWED_SET_EVENTS = {EVENT_MENU_SELECTION, EVENT_MENU_SELECTION_HELP_REQUEST,
                                                     EVENT_TIMER_EXPIRATION, EVENT_STATUS_COMMAND, 
                                                     EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION,
                                                     EVENT_EXTERNAL_FILE_UPDATE};

    // Not supported events for the setEvent() method
    private static short NOT_SUPPORTED_SETEVENT_10 = 10;
    private static short NOT_SUPPORTED_SETEVENT_24 = 24;

    // Event value range
    private	static short MIN_EVENT_NB = 7;
    private	static short MAX_EVENT_NB = 29;

    // Events allowed and supported for isEventSet() method
    private static short MAX_EVENT = 21;                // 27  events - 6 not allowed events
    private short[] EventList = new short[MAX_EVENT];

    private static byte[] MenuEntry1 = {(byte)'T', (byte)'e', (byte)'s', (byte)'t', (byte)'1'};

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Ievs_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Ievs_1 thisApplet = new Api_2_Tkr_Ievs_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION and EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.initMenuEntry(MenuEntry1, (short)0, (short)MenuEntry1.length, (byte)0,
                                       false, (byte)0, (short)0);
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

     /**
      * Method called by the processToolkit() method to know if event
      * is an allowed event for the setEvent() method
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

        boolean bRes;
        byte testCaseNb;
        short j=0;

        // Build the allowed and supported events list
        for (short i = MIN_EVENT_NB; i <= MAX_EVENT_NB; i++) {
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

        // -----------------------------------------------------------------
        // Test Case 1 : Applet calls isEventSet() for each events allowed and supported
        //               except EVENT_UNRECOGNIZED_ENVELOPE and EVENT_MENU_SELECTION.
        // -----------------------------------------------------------------
        testCaseNb = (byte)1;
        bRes = true;

        /* all events from -1, 1, 7 to 9, 11 to 23, 25 to 29, 123, 124, 126, 127 except -1 and 7 */
        for (short i = 0; (i < (short)(MAX_EVENT)) && bRes; i++) {
            if ((EventList[i] != EVENT_UNRECOGNIZED_ENVELOPE) && (EventList[i] != EVENT_MENU_SELECTION)) {
                bRes = !obReg.isEventSet(EventList[i]);
            }
        }
        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 2 : call isEventSet(EVENT_UNRECOGNIZED_ENVELOPE)
        // -----------------------------------------------------------------
        testCaseNb = (byte)2;

        bRes = obReg.isEventSet(EVENT_UNRECOGNIZED_ENVELOPE);

        reportTestOutcome(testCaseNb, bRes);


        // -----------------------------------------------------------------
        // Test Case 3 : call isEventSet(EVENT_MENU_SELECTION)
        // -----------------------------------------------------------------
        testCaseNb = (byte)3;

        bRes = obReg.isEventSet(EVENT_MENU_SELECTION);

        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 4: Call clearEvent() then isEventSet() for EVENT_UNRECOGNIZED_ENVELOPE
        // -----------------------------------------------------------------
        testCaseNb = (byte)4;
        bRes = false;

        try {
            obReg.clearEvent(EVENT_UNRECOGNIZED_ENVELOPE);
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        bRes = bRes && !obReg.isEventSet(EVENT_UNRECOGNIZED_ENVELOPE);

        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 5 : For each SUPPORTED and ALLOWED event for setEvent(),
        //               applet calls setEvent() and isEventSet() methods
        // -----------------------------------------------------------------
        testCaseNb = (byte)5;
        bRes = true;
        try {
            for (short i = 0; (i < MAX_EVENT) && bRes;i++) {

                bRes = false;
                if ((this.isEventAllowed(EventList[i]))
                    && (this.isEventSupported(EventList[i]))) {
                        
                    obReg.setEvent(EventList[i]);
                    bRes = obReg.isEventSet(EventList[i]);

                    // Clear the setEvent
                    obReg.clearEvent(EventList[i]);
                } else {
                    bRes = true;
                }
            }
        }
        catch (Exception e) {   // No exception should be thrown
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 6 :For EVENT_MENU_SELECTION_HELP_REQUEST call:
        //                1 - isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
        //                2 - changeMenuEntry with help supported
        //                3 - isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
        // -----------------------------------------------------------------

        testCaseNb = (byte)6;

        bRes = !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

        try {
            obReg.changeMenuEntry((byte) 1, MenuEntry1, (short)0, (short)MenuEntry1.length,
                                  (byte)0, true, (byte)0, (short)0);
        }
        catch (Exception e) {
            bRes = false;
        }
        bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 7 : For EVENT_TIMER_EXPIRATION, call:
        //                1 - isEventSet(EVENT_TIMER_EXPIRATION)
        //                2 - allocateTimer()
        //                3 - isEventSet(EVENT_TIMER_EXPIRATION)
        // -----------------------------------------------------------------
        testCaseNb = (byte)7;

        bRes = !obReg.isEventSet(EVENT_TIMER_EXPIRATION);

        try {
            obReg.allocateTimer();
        }
        catch (Exception e) {
            bRes = false;
        }
        bRes = bRes && obReg.isEventSet(EVENT_TIMER_EXPIRATION);

        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 8 : For Event EVENT_STATUS_COMMAND, call:
        //                1 - isEventSet(EVENT_STATUS_COMMAND)
        //                2 - requestPollInterval(POLL_SYSTEM_DURATION)
        //                3 - isEventSet(EVENT_STATUS_COMMAND)
        // -----------------------------------------------------------------
        testCaseNb = (byte)8;
        bRes = !obReg.isEventSet(EVENT_STATUS_COMMAND);

        try {
            obReg.requestPollInterval(POLL_SYSTEM_DURATION);
        }
        catch (Exception e) {
            bRes = false;
        }
        bRes = bRes && obReg.isEventSet(EVENT_STATUS_COMMAND);

        reportTestOutcome(testCaseNb, bRes);
            
        // -----------------------------------------------------------------
        // Test Case 9 : For Event EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION , call:
        //                1 - isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION)
        //                2 - allocateServiceIdentifier()
        //                3 - isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION)
        // -----------------------------------------------------------------
        testCaseNb = (byte)9;
        bRes = !obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);

        try {
            obReg.allocateServiceIdentifier();
        }
        catch (Exception e) {
            bRes = false;
        }
        bRes = bRes && obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);

        reportTestOutcome(testCaseNb, bRes);
    }
}
