//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Facrbbs_Bss.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_facrbbs_bss;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_facrbbs_bss
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Facrbbs_Bss extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_facrbbs_bss";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Facrbbs_Bss() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();

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
        /** Testcase 1 to 6                                                  */   
        /*********************************************************************/   
                                                                                  
        response = test.unrecognizedEnvelope();                                   
        addResult(response.checkSw("9116"));
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (Text string length = 15)
        response = test.terminalResponse("81030123 00020282 81030100 0D100401"
                                       + "02030405 06070809 0A0B0C0D 0E0F");
        addResult(response.checkSw("9116"));

        /*********************************************************************/   
        /** Testcase 7 to 11                                                 */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (Text string length = 5)
        response = test.terminalResponse("81030123 00020282 81030100 0D060401"
                                       + "02030405");
        addResult(response.checkSw("9116"));

        /*********************************************************************/   
        /** Testcase 12 to 19                                                */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (Text String Length = 16)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F");
        addResult(response.checkSw("9116"));

        /*********************************************************************/   
        /** Testcase 20 to 22                                                */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (2 Text String TLV)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F0D"
                                       + "06001122 334455");
        addResult(response.checkSw("9116"));

        /*********************************************************************/   
        /** Testcase 23                                                      */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        addResult(response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF"));

        // Terminal response (Text String Length = 16)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F");
        addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1
                                   + "17CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"
                                   + "CCCCCCCC CCCCCCCC"));
        
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
