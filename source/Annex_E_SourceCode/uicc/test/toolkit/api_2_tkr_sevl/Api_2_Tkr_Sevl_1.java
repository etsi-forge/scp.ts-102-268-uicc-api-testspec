//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_sevl;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, ToolkitRegistry interface, setEventList() method
 * applet 1
 */
public class Api_2_Tkr_Sevl_1 extends TestToolkitApplet {

    // Not allowed events for the setEvent() method
    private static short[] NOT_ALLOWED_SET_EVENTS = {EVENT_MENU_SELECTION, EVENT_MENU_SELECTION_HELP_REQUEST,
                                                     EVENT_TIMER_EXPIRATION,EVENT_STATUS_COMMAND,
                                                     EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION, EVENT_EXTERNAL_FILE_UPDATE};

    // Not supported events for the setEvent() method
    private static short NOT_SUPPORTED_SETEVENT_10 = 10;
    private static short NOT_SUPPORTED_SETEVENT_24 = 24;

    // Event value range
    private static short MIN_EVENT_NB = 7;
    private static short MAX_EVENT_NB = 29;

    // Events allowed and supported
    private static short OFFSET = 5;
    private static short MAX_EVENT = 21;                // 27 events - 6 not allowed events
    private short[] EventList = new short[MAX_EVENT];

    // Event allocated only for 1 applet
    private short[] List = {EVENT_CALL_CONTROL_BY_NAA};

    private static byte[] MenuEntry = {(byte)'A', (byte)'p', (byte)'p', (byte)'l',
                                       (byte)'e', (byte)'t', (byte)'1'};

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Sevl_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Api_2_Tkr_Sevl_1 thisApplet = new Api_2_Tkr_Sevl_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    
        
        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuEntry, (short)0, (short)MenuEntry.length, (byte)0,
                                       false, (byte)0, (short)0);        
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

        boolean bRes;
        byte testCaseNb;
        short[] MonoEventList = new short[1];
        short i;
        short j;

        if ((event == EVENT_MENU_SELECTION) && (EnvelopeHandlerSystem.getTheHandler().getItemIdentifier() == (byte)0x01)) {

            j = 0;
            
            // Build the allowed and supported events list
            for(i = MIN_EVENT_NB; i <= MAX_EVENT_NB;i++) {
            
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
            // Test Case 1: Applet1 registering all eventList buffer
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;
            bRes = false; 

            // -----------------------------------------------------------------
            //              1 - For each event in EventList, clearEvent(event)
            //                  No exception shall be thrown
            // -----------------------------------------------------------------
            try {
                for(j = (short)0; (j < (short)EventList.length); j++) {
                    obReg.clearEvent(EventList[j]);
                }
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }

            // -----------------------------------------------------------------
            //              2 - setEventList(eventList)
            //                  Offset = 0
            //                  Length = eventList.length
            //                  No exception shall be thrown
            // -----------------------------------------------------------------
            try {
                obReg.setEventList(EventList, (short)0, (short)EventList.length);
            }
            catch (Exception e) {
                bRes = false;
            }

            // -----------------------------------------------------------------
            //              3 - For all events in eventList isEventSet(event)
            //                  Each time shall return true
            // -----------------------------------------------------------------
            for(j = (short)0; (j < MAX_EVENT) && bRes; j++) {
                bRes = obReg.isEventSet(EventList[j]);
            }
            
            // -----------------------------------------------------------------
            //              4 - For each event in EventList clearEvent(event)
            //                  No exception shall be thrown
            // -----------------------------------------------------------------
            try {
                for(j = (short)0; (j < MAX_EVENT) && bRes; j++) {
                    obReg.clearEvent(EventList[j]);
                }
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            

            // -----------------------------------------------------------------
            // Test Case 2 : Registering part of eventList buffer
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;    
            bRes = true;

            // -----------------------------------------------------------------
            //              1 - setEventList(eventList)
            //                  Offset > 0
            //                  Length = eventList.lentgh - OFFSET
            //                  No exception shall be thrown
            // -----------------------------------------------------------------
            try {
                obReg.setEventList(EventList, OFFSET, (short)(MAX_EVENT-OFFSET));
            }
            catch (Exception e) {
                bRes = false;
            }

            // -----------------------------------------------------------------
            //              2 - For all events in eventList isEventSet(event)
            //                  Each time shall return true for events
            //                  ranging from OFFSET to OFFSET+length
            //                  else shall return false
            // -----------------------------------------------------------------
            for (j = (short)0; (j < OFFSET) && bRes; j++) {
                bRes &= !obReg.isEventSet(EventList[j]);
            }

            for(j = (short)OFFSET; (j < MAX_EVENT) && bRes; j++) {
                bRes &= obReg.isEventSet(EventList[j]);
            }

            // -----------------------------------------------------------------
            //              4 - For each event in EventList clearEvent(event)
            //                  No exception shall be thrown
            // -----------------------------------------------------------------
            try {
                for(j = (short)OFFSET; (j < MAX_EVENT) && bRes; j++) {
                    bRes = false;
                    obReg.clearEvent(EventList[j]);
                    bRes = true;
                }
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 3: Null buffer
            //              EventList = null 
            //              Shall throw a java.lang.NullPointerException exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)3;
            bRes = false;

            try {
                obReg.setEventList(null, (short)0, (short)1);
            }
            catch (java.lang.NullPointerException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 4: Out of bounds offset
            //              Offset = eventList.length
            //              Length = 1
            //              Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)4;
            bRes = false;

            try {
                obReg.setEventList(EventList, MAX_EVENT, (short)1);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------        
            // Test Case 5: Out of bounds and big offset
            //              Offset = 255
            //              Length = 1
            //              Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)5;
            bRes = false;

            try {
                obReg.setEventList(EventList, (short)255, (short)1);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------                                    
            // Test Case 6: Offset < 0
            //              Offset = -1
            //              Length = 1
            //              Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)6;
            bRes = false;

            try {
                obReg.setEventList(EventList, (short)-1, (short)1);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 7: Out of bounds length
            //              Offset = 0
            //              Length = eventList.length + 1
            //              Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)7;
            bRes = false;

            try {
                obReg.setEventList(EventList, (short)0, (short)(MAX_EVENT + 1));
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 8: Out of bounds and big length
            //              Offset = 0
            //              Length = 255
            //              Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)8;
            bRes = false;

            try {
                obReg.setEventList(EventList, (short)0, (short)255);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 9: Length < 0
            //              Offset = 0
            //              Length = -1
            //              Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)9;
            bRes = false;

            try {
                obReg.setEventList(EventList, (short)0, (short)-1);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------    
            // Test Case 10: Out of bounds offset + Length 
            //               Offset + length > eventList.length + 1
            //               Shall throw a java.lang.ArrayIndexOutOfBounds exception
            // -----------------------------------------------------------------
            testCaseNb = (byte)10;
            bRes = false;

            try {
                obReg.setEventList(EventList, OFFSET, MAX_EVENT);
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 11: Event 0
            //               Call setEventList with eventList indicating event 0
            //               Shall throw a ToolkitException with EVENT_NOT_SUPPORTED reason code
            // -----------------------------------------------------------------
            
            testCaseNb = (byte)11;
            bRes = false;
            MonoEventList[(short)0] = (byte)0;

            try {
                obReg.setEventList(MonoEventList, (short)0, (short)1);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.EVENT_NOT_SUPPORTED);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Cases 12 to 17
            //
            // Test Case 12: EVENT_MENU_SELECTION
            //               Shall throw a ToolkitException 
            //               with EVENT_NOT_ALLOWED reason code
            //
            // Test Case 13: EVENT_MENU_SELECTION_HELP_REQUEST
            //               Shall throw a ToolkitException 
            //               with EVENT_NOT_ALLOWED reason code    
            //
            // Test Case 14: EVENT_TIMER_EXPIRATION
            //               Shall throw a ToolkitException 
            //               with EVENT_NOT_ALLOWED reason code
            //
            // Test Case 15: EVENT_STATUS_COMMAND
            //               Shall throw a ToolkitException 
            //               with EVENT_NOT_ALLOWED reason code
            //
            // Test Case 16: EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
            //               Shall throw a ToolkitException 
            //               with EVENT_NOT_ALLOWED reason code
            //
            // Test Case 17: EVENT_EXTERNAL_FILE_UPDATE 
            //               Shall throw a ToolkitException 
            //               with EVENT_NOT_ALLOWED reason code
            //
            // -----------------------------------------------------------------
            for (j = (short)0; (j < (short)NOT_ALLOWED_SET_EVENTS.length); j++) {

                MonoEventList[(short)0] = NOT_ALLOWED_SET_EVENTS[j];
                bRes = false;
                try {
                    obReg.setEventList(MonoEventList, (short)0, (short)1);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                testCaseNb = (byte)(testCaseNb + 1); // Test Cases : 12-17
                reportTestOutcome(testCaseNb, bRes);
            }

            // -----------------------------------------------------------------
            // Test Case 18: Set EVENT_CALL_CONTROL_BY_NAA
            //               Shall not throw an exception             
            // -----------------------------------------------------------------
            testCaseNb = (byte)18;
            bRes = false;

            try {
                obReg.setEventList(List, (short)0, (short)1);
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Cases 19: by default    
            // -----------------------------------------------------------------
            testCaseNb = (byte)19;
            bRes = false;
            reportTestOutcome(testCaseNb, bRes);

        } else {
            if (event == EVENT_CALL_CONTROL_BY_NAA) {
            // -----------------------------------------------------------------
            // Test Case 19: Check applet is triggered by an 
            //               ENVELOPE(EVENT_CALL_CONTROL_BY_NAA)
            // -----------------------------------------------------------------
                testCaseNb = (byte)19;
                bRes = true;
                reportTestOutcome(testCaseNb, bRes);
            }
        }
    }
}
