//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Gcid.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gcid;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gcid
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Gcid extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_gcid";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Gcid() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        test.initialiseResults();
        
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
        test.addResult(response.checkSw("9112"));
        
        // Fetch Display Text
        response = test.fetch("12");
        test.addResult(response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874"));

        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 2                                                       */   
        /*********************************************************************/   
                                                                                  
        // Open channel proactive command
        response = test.fetch("1C");
        test.addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Terminal response with channel status TLV length set to 00
        response = test.terminalResponse("81030140 01820282 81030220 00380035"
                                       + "04010000 00390200 80");
        test.addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 3                                                       */   
        /*********************************************************************/   
                                                                                  
        // Open channel proactive command
        response = test.fetch("1C");
        test.addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Terminal response OK on channel 01
        response = test.terminalResponse("81030140 01820282 81030100 38028100"
                                       + "35040100 00003902 0080");
        test.addResult(response.checkSw("910B"));

        // Close channel proactive command
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028121"));

        // Terminal response OK 
        response = test.terminalResponse("81030141 00820282 81030100");
        test.addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 4                                                       */   
        /*********************************************************************/   
                                                                                  
        // Open channel proactive command
        response = test.fetch("1C");
        test.addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Terminal response with 2 Channel Id TLV 
        response = test.terminalResponse("81030140 01820282 81030100 38028100"
                                       + "38028200 35040100 00003902 0080");
        test.addResult(response.checkSw("910B"));

        // Close channel proactive command
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028121"));

        // Terminal response OK 
        response = test.terminalResponse("81030141 00820282 81030100");
        test.addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 5                                                       */   
        /*********************************************************************/   
                                                                                  
        // Open channel proactive command
        response = test.fetch("1C");
        test.addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Terminal response 
        response = test.terminalResponse("81030140 01820282 81030100 38020305"
                                       + "35040100 00003902 0080");
        test.addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1
                                   + "05CCCCCC CCCC"));
        
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
        
        return test.getOverallResult();
    }
}
