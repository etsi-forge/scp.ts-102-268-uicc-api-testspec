//-----------------------------------------------------------------------------
//    Cre_Uta_Pdel_B_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_pdel.cre_uta_pdel_b;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;
import uicc.test.catre.cre_uta_pdel.cre_uta_pdel_a.*;

       

/**
 * Test Area : uicc.catre...
 *
 * Applet is triggered on Install (Install)
 *
 *
 */

public class Cre_Uta_Pdel_B_1 extends TestToolkitApplet{

    static byte[] menuB1 = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'B', (byte)'1'}; 
  
    static byte[] appletAidA2 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0x50, (byte)0x01, (byte)0x01, (byte)0x02};
    
    AID serverAid;
    
    Cre_Uta_Pdel_A_3 shareInterface;
    
    /**
     * Constructor of the applet
     */
    public Cre_Uta_Pdel_B_1()    {
      
        serverAid = new AID(appletAidA2, (short) 0, (byte) appletAidA2.length);
      
      
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength){
        // Create a new applet instance.
        Cre_Uta_Pdel_B_1 thisApplet = new Cre_Uta_Pdel_B_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
        
        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menuB1, (short)0, (short)menuB1.length, (byte)0, false, (byte) 0, (short) 0);
   
   
   }


    public void processToolkit(short event) {
        
        boolean bRes = false;
        byte  bTestCaseNb = (byte)1;
        byte  menuIdA2 = (byte)0;
      
        if (event == EVENT_MENU_SELECTION) {
          
            shareInterface = (Cre_Uta_Pdel_A_3) JCSystem.getAppletShareableInterfaceObject(serverAid,(byte) 0);
            menuIdA2 = shareInterface.getMenuId();
            
            if (menuIdA2 == 0x01){              
                bRes = true;                
            }
            reportTestOutcome(bTestCaseNb, bRes); 
            
        }
      
    }

}

