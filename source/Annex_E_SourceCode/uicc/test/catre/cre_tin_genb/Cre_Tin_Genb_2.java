//-----------------------------------------------------------------------------
//    Cre_Tin_Tmal_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_genb;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.access.*;



public class Cre_Tin_Genb_2 extends Applet {
       
    private static FileView fileView;         

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Genb_2() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Tin_Genb_2 thisApplet = new Cre_Tin_Genb_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Get an UICC view
        fileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);

    }
    

    /**
     * Method called by the JCRE
     */
    public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) {
        // According to CAT Runtime Environment behaviour for ToolkitInterface object retrieval
        if ((clientAID == null) && (parameter == (byte)0x01)) {
            return((Shareable) this);
        } else {
            return(null);
        }
    }

    /**
     * Method called by the JCRE
     */
    public void process(APDU arg0) throws ISOException {
        if (selectingApplet()) {
            /* Construct and send the answer to select */
            arg0.setOutgoing();
            byte[] buffer = {(byte)'O', (byte)'K', (byte)' ', (byte)'2'};
            arg0.setOutgoingLength((short)4);
            arg0.sendBytesLong(buffer, (short)0, (short)4);

            // Release static variable before deleting the applet
            fileView = null;
        }
    }
}
