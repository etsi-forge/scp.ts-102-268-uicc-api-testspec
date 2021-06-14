//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Gcst.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gcst;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gcst
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Gcst extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_gcst";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Gcst() {
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
        
        // Fetch the DISPLAY TEXT proactive command
        response = test.fetch("12");                                              
        addResult(response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874"));

        // Terminal response with no CHANNEL STATUS TLV
        response = test.terminalResponse("81030121 00020282 81030100");   
        addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 2                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Successful terminal response with channel status value set to 0x8100
        response = test.terminalResponse("81030140 01820282 81830100 38028100" 
                                       + "35020300 3902000A");
        addResult(response.checkSw("910B"));

        // Fetch the CLOSE CHANNEL proactive command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01410082 028121"));

        // Successful terminal response
        response = test.terminalResponse("81030141 00820282 81830100");
        addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 3                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Unsuccessful terminal response with channel status TLV length set to 0
        response = test.terminalResponse("81030140 01820282 81830130 38003502"
                                       + "03003902 000A");
        addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 4                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Unsuccessful terminal response with channel status TLV length set to 1
        response = test.terminalResponse("81030140 01820282 81830130 38018135"
                                       + "02030039 02000A");
        addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 5                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Successful terminal response with channel status TLV value set to 0x8100
        response = test.terminalResponse("81030140 01820282 81830100 38028100"
                                       + "35020300 3902000A");
        addResult(response.checkSw("910B"));

        /*********************************************************************/   
        /** Testcase 6                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET CHANNEL STATUS proactive command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01440082 028182"));

        // Successful terminal response with 2 channel status TLVs 
        response = test.terminalResponse("81030144 01820282 81830100 38028100"
                                       + "38028101 35020300 3902000A");
        addResult(response.checkSw("910B"));

        /*********************************************************************/   
        /** Testcase 7                                                       */   
        /*********************************************************************/   
                                                                                  
         // Fetch the GET CHANNEL STATUS proactive command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01440082 028182"));

        // Successful terminal response with 2 channel status TLVs 
        response = test.terminalResponse("81030144 01820282 81830100 38028200"
                                       + "38028100 35020300 3902000A");
        addResult(response.checkSw("910B"));

        // Fetch the CLOSE CHANNEL proactive command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01410082 028121"));

        // Successful terminal response
        response = test.terminalResponse("81030141 00820282 81830100");
        addResult(response.checkSw("911C"));

        /*********************************************************************/   
        /** Testcase 8                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));

        // Successful terminal response with 1 channel status TLVs 
        response = test.terminalResponse("81030140 01820282 81830100 38028304"
                                       + "35020300 3902000A");
        addResult(response.checkSw("910B"));

        // Fetch the CLOSE CHANNEL proactive command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01410082 028123"));

        // Successful terminal response
        response = test.terminalResponse("81030141 00820282 81830100");
        addResult(response.checkSw("9000"));
        
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1
                                   + "08CCCCCC CCCCCCCC CC"));
        
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
