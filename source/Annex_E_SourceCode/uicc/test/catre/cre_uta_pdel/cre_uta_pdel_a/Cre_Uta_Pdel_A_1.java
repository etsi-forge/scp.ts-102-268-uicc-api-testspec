//-----------------------------------------------------------------------------
//    Cre_Uta_Pdel_A_1.java
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

public class Cre_Uta_Pdel_A_1 extends TestToolkitApplet{

  /**
     * Constructor of the applet
     */
    public Cre_Uta_Pdel_A_1(){
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Uta_Pdel_A_1 thisApplet = new Cre_Uta_Pdel_A_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();    
   
   }


    public void processToolkit(short event){

    }
}
