//-----------------------------------------------------------------------------
// Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_exh_genb;


//-----------------------------------------------------------------------------
// Imports
//-----------------------------------------------------------------------------

import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.* ;



public class Cre_Exh_Genb_2 extends TestToolkitApplet {

    private byte nbCase = 0;

    /**
     * The Constructor registers the application for the Event.
     */

    private Cre_Exh_Genb_2() {
        // Applet initialisation
        nbCase = 0;
    }


    /**
     * This method will be used to install the Applet.
     */

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        Cre_Exh_Genb_2  thisApplet = new Cre_Exh_Genb_2();

        // Register the applet with the JCRE.
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();

        //register to EVENT_EVENT_DOWNLOAD_MT_CALL
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
        //register to EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
    }


    /**
     * This method will be used to trigger the tests.
     * The application will be triggered by an Formatted SMS PP download event.
     * @param event
     */

    public void processToolkit (short event) throws ToolkitException {
        boolean bRes = false;

        if (event == EVENT_EVENT_DOWNLOAD_MT_CALL) {
            switch (nbCase)
            {
                case 0:
                {
                    nbCase = 1;     
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                    break;
                }
                case 1:
                {
                    nbCase = 2;     
                    ISOException busyException = new ISOException((short)0x9300);
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                    throw (busyException);
                }
            }
        }

        if (event == EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) {
            nbCase = 3;
            ISOException busyException = new ISOException((short)0x9300);
            bRes = true;
            reportTestOutcome(nbCase, bRes);
            throw (busyException);
        }
    }
}
