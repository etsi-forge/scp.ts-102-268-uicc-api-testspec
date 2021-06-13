//-----------------------------------------------------------------------------
//    Cre_Uta_Adel_B_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_adel.cre_uta_adel_b;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;
import uicc.test.catre.cre_uta_adel.cre_uta_adel_a.*;

       

/**
 * Test Area : uicc.catre...
 *
 * Applet is triggered on Install (Install)
 *
 *
 */

public class Cre_Uta_Adel_B_2 extends TestToolkitApplet implements MultiSelectable{

    static byte[] menuB2 = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'B', (byte)'2'}; 
  
    static byte[] appletAidA1 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0x50, (byte)0x01, (byte)0x01, (byte)0x02};
    
    AID serverAid;
    
    byte  bTestCaseNbB2 = (byte)0x01;
    
    Cre_Uta_Adel_A_3 shareInterfaceB2;
    
    static Cre_Uta_Adel_A_3 shareInterfaceStatic;
    
    
    /**
     * Constructor of the applet
     */
    public Cre_Uta_Adel_B_2()    {
      
        serverAid = new AID(appletAidA1, (short) 0, (byte) appletAidA1.length);
      
      
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength){
        // Create a new applet instance.
        Cre_Uta_Adel_B_2 thisApplet = new Cre_Uta_Adel_B_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
        
        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menuB2, (short)0, (short)menuB2.length, (byte)0, false, (byte) 0, (short) 0);
   
   
   }


    public void processToolkit(short event) {
  
        byte  menuIdA1 = (byte)0;
      
        if (event == EVENT_MENU_SELECTION) {
          
           switch(bTestCaseNbB2){
            
                case 0x01:
                    shareInterfaceB2 = (Cre_Uta_Adel_A_3) JCSystem.getAppletShareableInterfaceObject(serverAid,(byte) 0);
                    menuIdA1 = shareInterfaceB2.getMenuId();         
                break; 
               
                case 0x02:
                    shareInterfaceB2 = null;         
                break; 
                
                case 0x03:
                    shareInterfaceStatic = (Cre_Uta_Adel_A_3) JCSystem.getAppletShareableInterfaceObject(serverAid,(byte) 0);
                    menuIdA1 = shareInterfaceStatic.getMenuId();      
                break;         			
            }
            bTestCaseNbB2++;
        }      
    }

    /* (non-Javadoc)
     * @see javacard.framework.MultiSelectable#select(boolean)
     */
    public boolean select(boolean arg0) {
        return true;
    }

    /* (non-Javadoc)
     * @see javacard.framework.MultiSelectable#deselect(boolean)
     */
    public void deselect(boolean arg0) {
    }

   
}

