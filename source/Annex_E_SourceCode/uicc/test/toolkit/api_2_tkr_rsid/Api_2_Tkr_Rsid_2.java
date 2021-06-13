//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rsid;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, releaseServiceIdentifier() method
 * applet 2
 */
public class Api_2_Tkr_Rsid_2 extends TestToolkitApplet { 
    
    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'2'};
    private byte[] abServiceRecord = {(byte)0x00, (byte)0x01, (byte)0x00};
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Rsid_2() { 
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) { 
        
        // Create a new applet instance
        Api_2_Tkr_Rsid_2 thisApplet = new Api_2_Tkr_Rsid_2();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

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
        
        boolean bRes;
        byte testCaseNb;
        
        if (event == EVENT_MENU_SELECTION) {
            // Test Case 5: Released services can be allocated
            testCaseNb = (byte)0x01;
            
            try {
                bRes = (obReg.allocateServiceIdentifier() == (byte)0x01);
                // Get the system instance of handlers
                ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
                // Send SERVICE ADD
                proHdlr.init(PRO_CMD_DECLARE_SERVICE, (byte)0x00 /*Add*/, (byte)DEV_ID_TERMINAL);
                proHdlr.appendTLV(TAG_SERVICE_RECORD, abServiceRecord, (short)0x00, (short)abServiceRecord.length);
                proHdlr.send();
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
        }
        
        // Test Case 7: check this applet is triggered by envelope(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) command
        if (event == EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION) {
            testCaseNb = (byte)0x02;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
        }
    } 
}
