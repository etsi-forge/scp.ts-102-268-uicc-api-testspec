//-----------------------------------------------------------------------------
//    Cre_Uta_Odel_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_odel;

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

public class Cre_Uta_Odel_1 extends TestToolkitApplet{

    /**
     * Constructor of the applet
     */
    public Cre_Uta_Odel_1(){
        }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
              
        
        // Create a new applet instance.
        Cre_Uta_Odel_1 thisApplet = new Cre_Uta_Odel_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();    
        
        thisApplet.obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
        
       
   
    }


    public void processToolkit(short event){
                
        boolean bRes = false;
        byte    bTestCaseNb = (byte)1;

        if (JCSystem.isObjectDeletionSupported()){
            bRes =true;
        }        
        reportTestOutcome(bTestCaseNb, bRes);

    }
}
