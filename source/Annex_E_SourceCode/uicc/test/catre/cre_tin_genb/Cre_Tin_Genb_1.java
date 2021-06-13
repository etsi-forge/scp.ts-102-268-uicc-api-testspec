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
import uicc.toolkit.*;



public class Cre_Tin_Genb_1 extends Applet implements ToolkitInterface {
                
    private static ToolkitRegistry oReg;
    private byte nbSelect = 0;;
    
    /**
     * Constructor of the applet
     */
    public Cre_Tin_Genb_1() {
        nbSelect = 0;
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Tin_Genb_1 thisApplet = new Cre_Tin_Genb_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Register to the Toolkit Registry
        oReg = ToolkitRegistrySystem.getEntry();
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
     * Method called by the UICC CRE
     */
    public void processToolkit(short event) {                 
    }


    /**
     * Method called by the JCRE
     */
    public void process(APDU arg0) throws ISOException {
        if (selectingApplet()) {
            /* Construct and send the answer to select */
            arg0.setOutgoing();
            byte[] buffer = {(byte)'O', (byte)'K', (byte)' ', (byte)'1', (byte)'1'};
            if (nbSelect == 1)
            {
                nbSelect = 1;
                buffer[4] = (byte)'2';
            }
            arg0.setOutgoingLength((short)5);
            arg0.sendBytesLong(buffer, (short)0, (short)5);
            
            // Release static variable before deleting the applet
            oReg = null;
        }
    }
}
