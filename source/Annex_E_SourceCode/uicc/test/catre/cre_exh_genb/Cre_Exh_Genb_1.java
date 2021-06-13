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


public class Cre_Exh_Genb_1 extends TestToolkitApplet {


    private static byte[] Menu = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    private byte nbCase = 0;
    
    /**
     * The Constructor registers the application for the Event.
     */

    private Cre_Exh_Genb_1() {
        // Applet initialisation
        nbCase = 0;
    }


    /**
     * This method will be used to install the Applet.
     */

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        Cre_Exh_Genb_1  thisApplet = new Cre_Exh_Genb_1();

        // Register the applet with the JCRE.
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();

        //register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(Menu, (short) 0, (short)Menu.length, (byte) 0, false, (byte) 0, (short) 0);
        //register to EVENT_EVENT_DOWNLOAD_MT_CALL
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
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
                    ISOException busyException = new ISOException((short)0x9300);
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                    throw (busyException);
                }

                case 1:
                {
                    nbCase = 2;     
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                }
            }
        }

        if (event == EVENT_MENU_SELECTION) {
            switch (nbCase)
            {
                case 2:
                {
                    nbCase = 3;     
                    ISOException busyException = new ISOException((short)0x9300);
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                    throw (busyException);
                }

                case 3:
                {
                    nbCase = 4;     
                    ISOException notBusyException = new ISOException((short)0x6F00);
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                    throw (notBusyException);
                }

                case 4:
                {
                    nbCase = 5;     
                    bRes = true;
                    reportTestOutcome(nbCase, bRes);
                    throw (new ToolkitException(ToolkitException.REGISTRY_ERROR));
                }
            }
        }
    }
}
