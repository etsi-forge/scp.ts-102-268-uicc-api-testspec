//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_reg_evtr;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * Cat Runtime Environment, Registration, Event Registration
 * Applet1
 */

public class Cre_Reg_Evtr_1 extends TestToolkitApplet {

    private byte testCaseNb;
    private boolean bRes;

    // Constructor of the applet
    public Cre_Reg_Evtr_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // create a new applet instance
        Cre_Reg_Evtr_1 thisApplet = new Cre_Reg_Evtr_1();

        // register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // initialise the data of the test applet
        thisApplet.init();

        // registration to EVENT_EVENT_DOWNLOAD_USER_ACTIVITY and EVENT_EVENT_DOWNLOAD_MT_CALL
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
    }


    /**
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {
        
        if (event == EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) {
            testCaseNb = 0x01;
            try {
                obReg.setEvent(EVENT_EVENT_DOWNLOAD_LOCATION_STATUS);
                obReg.clearEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
                bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }        
    }
}
