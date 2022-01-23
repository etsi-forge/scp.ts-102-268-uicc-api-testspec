//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_stat;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Api_1_Fvw_Stat extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/access/api_1_fvw_stat";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 10010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 10010102";  

    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_1_Fvw_Stat() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // Install package
        test.loadPackage(CAP_FILE_PATH);
       
        // Install Applet
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                           "01" +   // V Priority Level
                           "00" +   // V Max. number of timers
                           "00" +   // V Maximum text length for a menu entry
                           "00" +   // V Maximum number of menu entries
                           "00" +   // V Maximum number of channels 
                           "00" +   // LV Minimum Security Level field
                           "00" +   // LV TAR Value(s) 
                           "00" +  // V Maximum number of services
                           "8118" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = Full Access
                           "00" +   // LV Access Domain DAP field
                           "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                           "0100" + // LV Access Domain for ADF1 file system = Full Access
                           "00"    // LV Access Domain DAP field
                           ); 
                           
        test.reset();    
        test.terminalProfileSession("0101");
                                   
        /*********************************************************************/
        /** Testcase 1  Status of MF                                         */
        /** Testcase 2  Status after select EF TARU in MF                    */
        /** Testcase 3  Status of DF Telecom                                 */  
        /** Testcase 4  Status DF Telecom                                    */  
        /** Testcase 5  Status ADF1                                          */  
        /** Testcase 6  fcp is null                                          */  
        /** Testcase 7  fcpOffset < 0                                        */                              
        /** Testcase 8  fcpLength < 0                                        */       
        /** Testcase 9  fcpOffset + fcpLength > fcp.length                   */       
        /** Testcase 10 fcpOffset + fcpLength > fcp.length                   */   
        /*********************************************************************/      
        
        // Trigger Applet envelope unrecognized
        test.unrecognizedEnvelope();               
        
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 +
                                     "0CCCCCCC CCCCCCCC CCCCCCCC CC"));

        test.reset();               
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
                    
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/
        
        // delete Applet and package
        test.deleteApplet(APPLET_AID_1);                      
        test.deletePackage(CAP_FILE_PATH);
               
        return test.getOverallResult();
    }
}   
