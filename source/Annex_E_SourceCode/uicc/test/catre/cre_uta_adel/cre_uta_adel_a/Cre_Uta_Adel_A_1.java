//-----------------------------------------------------------------------------
//    Cre_Uta_Adel_A_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_adel.cre_uta_adel_a;

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
public class Cre_Uta_Adel_A_1 extends TestToolkitApplet implements Cre_Uta_Adel_A_3 {


    static byte[] menuA1 = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'A', (byte)'1'}; 
             
    static byte menuIdentifier;

    static byte[] appletAidA2 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0x50, (byte)0x02, (byte)0x01, (byte)0x02};

    static byte[] appletAidB1 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0x50, (byte)0x11, (byte)0x01, (byte)0x02};
    
    static byte[] appletAidB2 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0x50, (byte)0x12, (byte)0x01, (byte)0x02};
    
    
    //as the applet is installed and deleted several times an auxiliar static array is used 
    //to check the applet is triggered 
    static byte  staticBTestCaseNbA1 = (byte)0x01;     
    static boolean[]  staticArrayTestCaseNbA1 = {(boolean) false,(boolean) false,(boolean) false,(boolean) false,
                                                 (boolean) false,(boolean) false,(boolean) false,(boolean) false};
    
    
    
    /**
     * Constructor of the applet
     */
    public Cre_Uta_Adel_A_1() {    
                
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Uta_Adel_A_1 thisApplet = new Cre_Uta_Adel_A_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menuA1, (short)0, (short)menuA1.length, (byte)0, false, (byte) 0, (short) 0);
   
    }

    public byte getMenuId() {  
      
        return(menuIdentifier);
    
    }

    public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) {
      
        if ((clientAID == null) && (parameter == (byte)0x01)) {         
            return((Shareable) this);            
        } else {        
            if((clientAID.equals(appletAidA2, (short)0, (byte) appletAidA2.length) == true)
            ||(clientAID.equals(appletAidB1, (short)0, (byte) appletAidB1.length) == true)
            ||(clientAID.equals(appletAidB2, (short)0, (byte) appletAidB2.length) == true)) {                          
                return((Shareable) this);           
            } else {        
                return(null);
            }  
        }
    } 

    public void processToolkit(short event) {

    // Get the system instance of the EnvelopeHandler class
    EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
    boolean bRes = false;
        
        if (event == EVENT_MENU_SELECTION) {
                    
            if (staticBTestCaseNbA1 == 0x06) {
                //It is the sixth trigger
                Cre_Uta_Adel_A_2.shareInterfaceStatic = null;                
                
            } else {
                //staticBTestCaseNbA1 -> 1,2,3,4,5,7,8
                menuIdentifier = envHdlr.getItemIdentifier();                   
            }
            
            bRes = true;
        }
        staticArrayTestCaseNbA1[staticBTestCaseNbA1-1] =  bRes;
        if(staticBTestCaseNbA1 == 0x08){
            
            for (byte i=0; i<8; i++){
                reportTestOutcome((byte) (i+1), staticArrayTestCaseNbA1[i]);               
            }            
        }
        staticBTestCaseNbA1++;
        
    }   
}
