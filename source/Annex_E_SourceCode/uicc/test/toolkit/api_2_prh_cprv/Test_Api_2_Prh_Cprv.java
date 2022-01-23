//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Cprv.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cprv;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cprv
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Cprv extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_cprv";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Cprv() {
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
        /** Testcase 1 to 10                                                 */   
        /*********************************************************************/   
                                                                                  
        response = test.unrecognizedEnvelope();                                   
        test.addResult(response.checkSw("9116"));
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        test.addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (Text string length = 5)
        response = test.terminalResponse("81030123 00020282 81030100 0D060401"
                                       + "02030405");
        test.addResult(response.checkSw("9116"));

        /*********************************************************************/   
        /** Testcase 11 to 17                                                */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        test.addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (Text string length = 5)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F ");
        test.addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1
                                   + "11CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"
                                   + "CCCC"));
        
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
