//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Gtii.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gtii;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gtii
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Gtii extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_gtii";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Gtii() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/
        /** Testcase 0                                                       */
        /*********************************************************************/

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                           "01" +   // V Priority Level
                           "00" +   // V Max. number of timers
                           "00" +   // V Maximum text length for a menu entry
                           "00" +   // V Maximum number of menu entries
                           "01" +   // V Maximum number of channels 
                           "00" +   // LV Minimum Security Level field
                           "00" +   // LV TAR Value(s) 
                           "00" +   // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = ALWAYS
                           "00" );  // LV Access Domain DAP field       
        

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/   
        /** Testcase 1                                                       */   
        /*********************************************************************/   
                                                                                  
        response = test.unrecognizedEnvelope();                                   
        addResult(response.checkSw("9112"));
                                                                                  
        // Fetch the proactive command                                            
        response = test.fetch("12");                                              
        addResult(response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874"));

        // Terminal response with no additional information
        response = test.terminalResponse("81030121 00020282 81030100");   
        addResult(response.checkSw("911B"));
                                                                          

        /*********************************************************************/   
        /** Testcase 2 & 3                                                   */   
        /*********************************************************************/   
                                                                                  
        // Fetch the proactive command                                            
        response = test.fetch("1B");                                              
        addResult(response.checkData("D0198103 01240082 0281828F 06014974"
                                   + "656D318F 06024974 656D32"));

        // Terminal response (SELECT ITEM)
        response = test.terminalResponse("81030124 00020282 81030100 100101");  
        addResult(response.checkSw("9123"));
                                                                          
        /*********************************************************************/   
        /** Testcase 4 & 5                                                   */   
        /*********************************************************************/   
                                                                                  
        // Fetch the proactive command                                            
        response = test.fetch("23");                                              
        addResult(response.checkData("D0218103 01240082 0281828F 06034974"
                                   + "656D338F 06054974 656D358F 06074974"
                                   + "656D37"));

        // Terminal response (SELECT ITEM)
        response = test.terminalResponse("81030124 00020282 81030100 100105");  
        addResult(response.checkSw("9123"));

        /*********************************************************************/   
        /** Testcase 6 & 7                                                   */   
        /*********************************************************************/   
                                                                                  
        // Fetch the proactive command                                            
        response = test.fetch("23");                                              
        addResult(response.checkData("D0218103 01240082 0281828F 06FD4974"
                                   + "656D448F 06FE4974 656D458F 06FF4974"
                                   + "656D46"));

        // Terminal response (SELECT ITEM)
        response = test.terminalResponse("81030124 00020282 81030100 1001FF");  
        addResult(response.checkSw("9123"));

        /*********************************************************************/   
        /** Testcase 8 & 9                                                   */   
        /*********************************************************************/   
                                                                                  
        // Fetch the proactive command                                            
        response = test.fetch("23");                                              
        addResult(response.checkData("D0218103 01240082 0281828F 06FD4974"
                                   + "656D448F 06FE4974 656D458F 06FF4974"
                                   + "656D46"));

        // Terminal response (SELECT ITEM)
        response = test.terminalResponse("81030124 00020282 81030100 1001FF10"
                                       + "01FE");  
        addResult(response.checkSw("9112"));

        /*********************************************************************/   
        /** Testcase 10                                                      */   
        /*********************************************************************/   
                                                                                  
        // Fetch the proactive command                                            
        response = test.fetch("12");                                              
        addResult(response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874"));

        // Terminal response with no item identifier
        response = test.terminalResponse("81030121 00020282 81030100 1000");      
        addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1
                                   + "0ACCCCCC CCCCCCCC CCCCCC"));
        
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // delete applets and package
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return getOverallResult();
    }
}
