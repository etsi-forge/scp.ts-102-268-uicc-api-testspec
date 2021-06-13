//-----------------------------------------------------------------------------
//    Cre_Tin_Sval_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_sval;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Tin_Sval_2 extends TestToolkitApplet {

    // Service ID array : initially all at '0' which is not a correct service ID
    public static byte[] serviceIdBuffer ;

    static byte[] menu = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'2'}; 
 
    /**
     * Constructor of the applet
     */
    public Cre_Tin_Sval_2() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Cre_Tin_Sval_2 thisApplet = new Cre_Tin_Sval_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    

        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menu, (short)0, (short)menu.length, (byte)0, false, (byte) 0, (short) 0);

        // register instance with the EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent((short)EVENT_UNRECOGNIZED_ENVELOPE);
        
        // Allocate buffer
        serviceIdBuffer = new byte[8];
    }
    

    /**
     * Method called by the UICC CRE
     */
    public void processToolkit(short event) {
        
        // Result of each test
        boolean bRes = false ;
        
        // Number of tests
        byte testCaseNb;

	    if (event == EVENT_MENU_SELECTION) {
               
            // --------------------------------------------
            // Test Case 3 : Allocate 4 services     
            testCaseNb = (byte)0x01 ;
            bRes = false ;

            try {
                serviceIdBuffer[0] = obReg.allocateServiceIdentifier();
                serviceIdBuffer[1] = obReg.allocateServiceIdentifier();
                serviceIdBuffer[2] = obReg.allocateServiceIdentifier();
                serviceIdBuffer[3] = obReg.allocateServiceIdentifier();
                bRes = true ;
            }
            catch (Exception e) {   
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes) ;

            // --------------------------------------------
            // Test Case 4 : Allocate one more service     
            testCaseNb = (byte)0x02 ;
            bRes = false ;

            try {
                    obReg.allocateServiceIdentifier();
                }
            catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.NO_SERVICE_ID_AVAILABLE);
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes) ;
        }
        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE)
        {
            // Release referenced static buffer timerIdBuffer before deletion
            serviceIdBuffer = null;
        }
    }
}
