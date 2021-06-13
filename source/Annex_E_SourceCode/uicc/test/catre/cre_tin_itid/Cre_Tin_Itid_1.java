//-----------------------------------------------------------------------------
//    Cre_Tin_Itid_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_itid ;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;



public class Cre_Tin_Itid_1 extends TestToolkitApplet
{
    static byte [] menuInit = {(byte) 'M', (byte) 'e', (byte) 'n', (byte) 'u', (byte) '1', (byte) '1'};
    
    /**
     * Constructor of the applet
     */
    public Cre_Tin_Itid_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {

        // Create a new applet instance
        Cre_Tin_Itid_1 thisApplet = new Cre_Tin_Itid_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    

        // toolkit registration
        thisApplet.obReg.initMenuEntry(menuInit, (short) 0, (short) 6, (byte) 0, false, (byte) 0, (short) 0);
        menuInit [5] = (byte) '2';
        thisApplet.obReg.initMenuEntry(menuInit, (short) 0, (short) 6, (byte) 0, false, (byte) 0, (short) 0);
    }
    

    /**
       * Method called by the UICC CRE
     */
    public void processToolkit(short event) 
    {        
    }
}
