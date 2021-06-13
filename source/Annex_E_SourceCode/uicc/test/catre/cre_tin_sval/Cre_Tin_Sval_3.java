//-----------------------------------------------------------------------------
//    Cre_Tin_Sval_3.java
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



public class Cre_Tin_Sval_3 extends TestToolkitApplet {

    //Service ID value range
    short MIN_SERVICE_ID = 0;
    short MAX_SERVICE_ID = 7;
    
    static byte[] menu = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'3'}; 
    public final static byte QUALIFIER_1 = (byte)0x01;
    public static byte[] SERVICE_RECORD_VALUE = {(byte)0x00, (byte)0x00};

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Sval_3() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Tin_Sval_3 thisApplet = new Cre_Tin_Sval_3();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    

        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menu, (short)0, (short)menu.length, (byte)0, false, (byte) 0, (short) 0);
    }
    

    /**
     * Method called by the UICC CRE
     */
    public void processToolkit(short event) {
        
        // Result of each test
        boolean bRes = false ;
        
        // Number of tests
        byte testCaseNb;

        short i, j;

	    if (event == EVENT_MENU_SELECTION) {
            // --------------------------------------------
            // Test Case 6 : Allocate 4 services     
            testCaseNb = (byte)0x01 ;
            bRes = false ;

            try {
                Cre_Tin_Sval_2.serviceIdBuffer[4] = obReg.allocateServiceIdentifier();
                Cre_Tin_Sval_2.serviceIdBuffer[5] = obReg.allocateServiceIdentifier();
                Cre_Tin_Sval_2.serviceIdBuffer[6] = obReg.allocateServiceIdentifier();
                Cre_Tin_Sval_2.serviceIdBuffer[7] = obReg.allocateServiceIdentifier();
                bRes = true ;
            }
            catch (Exception e) {   
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes) ;

            // --------------------------------------------
            // Test Case 7 : Allocate one more service     
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
            
            // --------------------------------------------
            // Test Case 8 : each service id shall be between 0 and 7 and shall be different from each other
            testCaseNb = (byte)0x03 ;
            bRes = true ;
   
            for(i = 0; i < MAX_SERVICE_ID ;i++) {
            
                // each timerId shall be between 1 and 8
                if ((short)(Cre_Tin_Sval_2.serviceIdBuffer[i]) < MIN_SERVICE_ID || 
                        (short)(Cre_Tin_Sval_2.serviceIdBuffer[i]) > MAX_SERVICE_ID ) {
                    bRes = false;
                }
                
                // each timerId shall be different from each other
                for(j = (short)(i+1); j < MAX_SERVICE_ID ;j++) {
                    if (Cre_Tin_Sval_2.serviceIdBuffer[i] == Cre_Tin_Sval_2.serviceIdBuffer[j] ) {
                        bRes = false;
                    }
                }
    
            }
    
            reportTestOutcome(testCaseNb, bRes) ;
        }
    }
}
