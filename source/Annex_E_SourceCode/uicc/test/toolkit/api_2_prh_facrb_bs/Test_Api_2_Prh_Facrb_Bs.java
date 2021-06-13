//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Facrb_Bs.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_facrb_bs;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_facrb_bs
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Facrb_Bs extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_facrb_bs";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Facrb_Bs() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
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
        /** Testcase 1 to 5                                                  */   
        /*********************************************************************/   
                                                                                  
        response = test.unrecognizedEnvelope();                                   
        result = response.checkSw("9116");                                        
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        result &= response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF");                             

        // Terminal response (Text string length = 15)
        response = test.terminalResponse("81030123 00020282 81030100 0D100401"
                                       + "02030405 06070809 0A0B0C0D 0E0F");
        result &= response.checkSw("9116");                                       

        /*********************************************************************/   
        /** Testcase 6 to 11                                                 */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        result &= response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF");                             

        // Terminal response (Text string length = 16)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F");
        result &= response.checkSw("9116");                                       

        /*********************************************************************/   
        /** Testcase 12 to 14                                                */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        result &= response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF");                             

        // Terminal response (2 Text string TLV)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F0D"
                                       + "06001122 334455");
        result &= response.checkSw("9116");                                       

        /*********************************************************************/   
        /** Testcase 15                                                      */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command                                            
        response = test.fetch("16");                                              
        result &= response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF");                             

        // Terminal response (Text String Length = 16)
        response = test.terminalResponse("81030123 00020282 81030100 0D110400"
                                       + "01020304 05060708 090A0B0C 0D0E0F");
        result &= response.checkSw("9000");                                       

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "0FCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC");
        
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
        
        return result;
    }
}
