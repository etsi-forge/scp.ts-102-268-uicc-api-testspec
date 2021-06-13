//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_asid;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.system.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * uicc.toolkit package, ToolkitRegistry interface, allocateServiceIdentifier() method
 * applet 2
 */
public class Api_2_Tkr_Asid_2 extends TestToolkitApplet {

    private byte testCaseNb;
    private static boolean bRes;
    

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'2'};

    // Constructor of the applet
    public Api_2_Tkr_Asid_2() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Asid_2 thisApplet = new Api_2_Tkr_Asid_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short) MenuInit.length, (byte) 0, 
                                       false, (byte) 0, (short) 0);
    }

    /**
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {
        byte temp = 0;
        short i;

        switch (testCaseNb) {
        case 0:
        // -----------------------------------------------------------------
        // Test Case 4: call 3 times allocateServiceIdentifier() method
        // -----------------------------------------------------------------
            testCaseNb = (byte) 1;
            bRes = true;

            try {
                for (i=(short)5; (i<(short)8) && (bRes); i++) {
                    temp = obReg.allocateServiceIdentifier();
                    bRes = (!Api_2_Tkr_Asid_1.isInArray(temp,Api_2_Tkr_Asid_1.Services));
                    Api_2_Tkr_Asid_1.Services[i] = temp;
                }
                    
                // call 1 more allocateServiceIdentifier() method
                obReg.allocateServiceIdentifier();
                bRes = false;
            }
            catch (ToolkitException e) {
                bRes = bRes && (e.getReason() == ToolkitException.NO_SERVICE_ID_AVAILABLE);
            }
            catch (Exception e) {
               bRes = false;
            }            
            reportTestOutcome(testCaseNb, bRes);
            break;

        case 1:
        // -----------------------------------------------------------------
        // Test Case 5: Allocate services more than the maximum to this applet
        // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;
            try {
                obReg.allocateServiceIdentifier();
                bRes = true;
                obReg.allocateServiceIdentifier();
                bRes = false;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.NO_SERVICE_ID_AVAILABLE);
            }
            reportTestOutcome(testCaseNb, bRes);
            break;
        }
    }
}
