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
 * Applet2
 */

public class Cre_Reg_Evtr_2 extends TestToolkitApplet {

    private byte testCaseNb;
    private boolean bRes;

    byte[] readData = new byte[10];
    
    private static byte testCounter = 0;
    
    // Constructor of the applet
    public Cre_Reg_Evtr_2() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // create a new applet instance
        Cre_Reg_Evtr_2 thisApplet = new Cre_Reg_Evtr_2();

        // register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // initialise the data of the test applet
        thisApplet.init();
        
        // register to UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) {
        // According to CAT Runtime Environment behaviour for ToolkitInterface object retrieval
        if ((clientAID == null) && (parameter == (byte)0x01)) {
            testCounter++;
			return((Shareable) this);
        } else {
            return(null);
        }
    }
    
    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {
            testCaseNb = 0x01;
            bRes = false;
            if (testCounter == (byte)0x01) {
                bRes = true;
            }        
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
