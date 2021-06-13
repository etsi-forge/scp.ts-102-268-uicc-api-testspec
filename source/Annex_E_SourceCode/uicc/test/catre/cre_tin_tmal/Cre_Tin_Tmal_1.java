//-----------------------------------------------------------------------------
//    Cre_Tin_Tmal_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_tmal ;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;



public class Cre_Tin_Tmal_1 extends TestToolkitApplet {
                
    /**
     * Constructor of the applet
     */
    public Cre_Tin_Tmal_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Tin_Tmal_1 thisApplet = new Cre_Tin_Tmal_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

    	// Initialise the data of the test applet
   	    thisApplet.init();
    }
    

    /**
     * Method called by the UIC CRE
     */
    public void processToolkit(short event) {                 
    }
}
