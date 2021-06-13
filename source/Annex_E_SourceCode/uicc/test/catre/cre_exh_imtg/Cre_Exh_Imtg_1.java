//-----------------------------------------------------------------------------
// Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_exh_imtg;


//-----------------------------------------------------------------------------
// Imports
//-----------------------------------------------------------------------------

import uicc.toolkit.*;
import uicc.test.util.* ;


public class Cre_Exh_Imtg_1 extends TestToolkitApplet {


    /**
     * The Constructor registers the application for the Event.
     */

    private Cre_Exh_Imtg_1() {
    }


    /**
     * This method will be used to install the Applet.
     */

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        Cre_Exh_Imtg_1  thisApplet = new Cre_Exh_Imtg_1();

        // Register the applet with the JCRE.
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();

        //register to EVENT_STATUS_COMMAND
        thisApplet.obReg.requestPollInterval((short)POLL_SYSTEM_DURATION);
        //register to EVENT_PROFILE_DOWNLOAD
        thisApplet.obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
        //register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        //register to EVENT_EVENT_DOWNLOAD_MT_CALL
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
    }


    /**
     * This method will be used to trigger the tests.
     * The application will be triggered by an Formatted SMS PP download event.
     * @param event
     */

    public void processToolkit (short event) throws ToolkitException {
        byte testCaseNb = (byte) 0x00;
        boolean bRes = false;


        if (event == EVENT_PROFILE_DOWNLOAD) {

            /*------------------------------------------------------------------
             * TC 01: Throw NullPointerException for EVENT_PROFILE_DOWNLOAD
             *----------------------------------------------------------------*/
            testCaseNb=(byte)0x01;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
            throw new NullPointerException();
        }

        if (event == EVENT_STATUS_COMMAND) {

            /*------------------------------------------------------------------
             * TC 02: Throw NullPointerException for EVENT_STATUS_COMMAND
             *----------------------------------------------------------------*/
            testCaseNb=(byte)0x02;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
            throw new NullPointerException();
        }

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /*------------------------------------------------------------------
             * TC 03: Throw NullPointerException for EVENT_UNRECOGNIZED_ENVELOPE
             *----------------------------------------------------------------*/
            testCaseNb=(byte)0x03;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
            throw new NullPointerException();
        }

        if (event == EVENT_EVENT_DOWNLOAD_MT_CALL) {

            /*------------------------------------------------------------------
             * TC 04: Throw NullPointerException for EVENT_EVENT_DOWNLOAD_MT_CALL
             *----------------------------------------------------------------*/
            testCaseNb=(byte)0x04;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
            throw new NullPointerException();
        }

    }
}
