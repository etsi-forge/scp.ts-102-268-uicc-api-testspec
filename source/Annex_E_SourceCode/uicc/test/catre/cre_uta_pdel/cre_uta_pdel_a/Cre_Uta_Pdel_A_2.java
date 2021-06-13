//-----------------------------------------------------------------------------
//    Cre_Uta_Pdel_A_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_pdel.cre_uta_pdel_a;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;

/**
 * Test Area : uicc.catre...
 *
 * Applet is triggered on Install (Install)
 *
 *
 */

//server applet
public class Cre_Uta_Pdel_A_2 extends TestToolkitApplet{


    static byte[] menuA2 = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'A', (byte)'2'}; 
             
    static byte menuIdentifier;
    
    static byte[] appletAidB1 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0x50, (byte)0x11, (byte)0x01, (byte)0x02};
    
   
    
    /**
     * Constructor of the applet
     */
    public Cre_Uta_Pdel_A_2() {    
        
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Uta_Pdel_A_2 thisApplet = new Cre_Uta_Pdel_A_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menuA2, (short)0, (short)menuA2.length, (byte)0, false, (byte) 0, (short) 0);
   
    }

    public byte getMenuId() {  
      
        return(menuIdentifier);
    
    }
    public void processToolkit(short event) {

      // Get the system instance of the EnvelopeHandler class
      EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();

      if (event == EVENT_MENU_SELECTION) {
        
          menuIdentifier = envHdlr.getItemIdentifier();
          
      }

    }
    
    public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) {
      
        if ((clientAID == null) && (parameter == (byte)0x01)) {         
            return((Shareable) this);            
        } else {        
            if(clientAID.equals(appletAidB1, (short)0, (byte) appletAidB1.length) == true) {                          
                return((Shareable) this);           
            } else {        
                return(null);
            }  
        }
    } 
}
