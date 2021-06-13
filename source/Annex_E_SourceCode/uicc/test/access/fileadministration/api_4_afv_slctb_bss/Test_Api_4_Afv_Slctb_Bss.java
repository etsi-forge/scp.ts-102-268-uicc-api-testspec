//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_slctb_bss;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Api_4_Afv_Slctb_Bss extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/access/fileadministration/api_4_afv_slctb_bss";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 40010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 40010102";  

    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_4_Afv_Slctb_Bss() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        boolean result = false;
        
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
                           "8218" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = Full Access
                           "00" +   // LV Access Domain DAP field
                           "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                           "0100" + // LV Access Domain for ADF1 file system = Full Access
                           "00");   // LV Access Domain DAP field 
        
        test.reset(); 
        test.terminalProfileSession("0101");
                                   
        /*********************************************************************/
        /** Testcase 1 Select EFTARU in MF (Transparent EF)                  */
        /** Testcase 2 Select EFTaru in MF (Transparent EF)                  */
        /** Testcase 3 Select DF Test in MF                                  */  
        /** Testcase 4 Select EF CARU in DF Test(Cyclic EF)                  */  
        /** Testcase 5 Select ADF1                                           */  
        /** Testcase 6 Select MF                                             */  
        /** Testcase 7 Select DF TELECOM in MF                               */    
        /** Testcase 8 Select EF LARU in DF TEST (Linear FixedEF)            */                          
        /** Testcase 9 fcp is null                                           */         
        /** Testcase 10 fcpOffset < 0                                        */                          
        /** Testcase 11 fcpLength < 0                                        */         
        /** Testcase 12 fcpOffset + fcpLength > fcp.length                   */                          
        /** Testcase 13 fcpOffset + fcpLength > fcp.length                   */         
        /** Testcase 14 Selection possiblities                               */                          
        /** Testcase 15 EF not selected after MF/DF selection                */         
        /** Testcase 16 No selection of non-reachable file                   */                          
        /** Testcase 17 No record is selected afterselecting linear fixed EF */         
        /** Testcase 18 Record pointer in selected cyclic EF                 */                          
        /** Testcase 19 EF not selected after ADF/DF selection               */         
        /** Testcase 20 Reselection                                          */                                      
        /** Testcase 21 Security attributes                                  */      
        /*********************************************************************/      
        
        // Trigger Applet envelope unrecognized
        test.unrecognizedEnvelope();               
        
        response = test.selectApplication(APPLET_AID_1);
        result = response.checkData("10" + APPLET_AID_1 + 
                                     "16CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC CCCCCC");

        test.reset();               
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);        
                    
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        //Select EF CARU
        test.selectFile("3F00");
        test.selectFile("7F4A");
        test.selectFile("6F09");
        test.updateRecord("00", "03", "AAAAAA");//Mode previous
        test.updateRecord("00", "03", "555555");//Mode previous        
        
        // delete Applet and package
        test.deleteApplet(APPLET_AID_1);                      
        test.deletePackage(CAP_FILE_PATH);
               
        return result;
    }
}   
