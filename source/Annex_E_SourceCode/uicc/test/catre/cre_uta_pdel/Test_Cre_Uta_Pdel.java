//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_pdel;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Uta_Pdel extends UiccTestModel {

    static final String CAP_FILE_PATH_A = "uicc/test/catre/cre_uta_pdel/cre_uta_pdel_a";
    static final String CLASS_AID_A_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_A_1 = "A0000000 090005FF FFFFFF89 50010102";
    
    static final String CLASS_AID_A_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_A_2 = "A0000000 090005FF FFFFFF89 50020102";
    
    
    static final String CAP_FILE_PATH_B = "uicc/test/catre/cre_uta_pdel/cre_uta_pdel_b";
    static final String CLASS_AID_B_1 = "A0000000 090005FF FFFFFF89 50110001";
    static final String APPLET_AID_B_1 = "A0000000 090005FF FFFFFF89 50110102";
   
    static final String CLASS_AID_B_2 = "A0000000 090005FF FFFFFF89 50120001";
    static final String APPLET_AID_B_2 = "A0000000 090005FF FFFFFF89 50120102";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Uta_Pdel() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        /*********************************************************************/
        /** Testcase 0                                                       */
        /*********************************************************************/

        // Install packageA
        test.loadPackage(CAP_FILE_PATH_A);
        // Install packageB
        test.loadPackage(CAP_FILE_PATH_B);
        
        // Install AppletB1
        test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_1, APPLET_AID_B_1, 
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Position of menu entry 1
                               "02" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services       

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/      
        // The package deletion is unsuccessful if a reachable instance 
        // of a class belonging to the package exists on the card
        
        
        // Delete PackageB        
        response = test.deletePackage(CAP_FILE_PATH_B);             
        test.addResult(!response.checkSw("9000"));         //response shall be different from 90 00
           
        
        // Install AppletB2        
        response = test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_2, APPLET_AID_B_2, 
                        "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "00" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries                              
                            "00" +   // V Maximum number of channels 
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s) 
                            "00" );  // V Maximum number of services     
        test.addResult(response.checkData("00"));
        test.addResult(response.checkSw("9000"));
     
        // Delete AppletB1 and AppletB2        
        test.deleteApplet(APPLET_AID_B_1);
        test.deleteApplet(APPLET_AID_B_2);
        
             
        
        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/      
        // The package deletion is unsuccessful if another package on the card depends on this package
        
        
        // Delete PackageA        
        response = test.deletePackage(CAP_FILE_PATH_A);             
        test.addResult(!response.checkSw("9000"));         //response shall be different from 90 00
        
        // Install AppletA1        
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
                        "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "00" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries                              
                            "00" +   // V Maximum number of channels 
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s) 
                            "00" );  // V Maximum number of services        
        test.addResult(response.checkData("00"));
        test.addResult(response.checkSw("9000"));
        // Delete AppletA1   
        test.deleteApplet(APPLET_AID_A_1);
        
        
        
        
        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/      
        // The installation of a package which depends on a deleted package shall fail
        
        
        // Delete PackageB
        response = test.deletePackage(CAP_FILE_PATH_B);             
        test.addResult(response.checkSw("9000"));
        
        // Delete PackageA        
        response = test.deletePackage(CAP_FILE_PATH_A);         
        test.addResult(response.checkSw("9000"));
        
        // Install packageB
        response = test.loadPackage(CAP_FILE_PATH_B);
        test.addResult(!response.checkSw("9000"));         //response shall be different from 90 00
        
        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/ 
        //Once a package is deleted, it shall not be possible to install an applet from this package       
        
        
        // Install AppletA1        
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
                        "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "00" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries                              
                            "00" +   // V Maximum number of channels 
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s) 
                            "00" );  // V Maximum number of services
        test.addResult(!response.checkSw("9000"));         //response shall be different from 90 00
                            
        // Install AppletB2        
        response = test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_2, APPLET_AID_B_2, 
                        "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "00" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries                              
                            "00" +   // V Maximum number of channels 
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s) 
                            "00" );  // V Maximum number of services                   
        test.addResult(!response.checkSw("9000"));         //response shall be different from 90 00
        
        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/ 
        //This test checks that it is possible to re-install the same package
        
        // Install packageA
        test.loadPackage(CAP_FILE_PATH_A);       
      
        // Install AppletA1        
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
                        "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "00" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries                              
                            "00" +   // V Maximum number of channels 
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s) 
                            "00" );  // V Maximum number of services        
        test.addResult(response.checkData("00"));
        test.addResult(response.checkSw("9000"));
        
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                */
        /*********************************************************************/
        /*********************************************************************/
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // delete AppletA1 and packageA
        test.deleteApplet(APPLET_AID_A_1);                
        test.deletePackage(CAP_FILE_PATH_A);
        
        return test.getOverallResult();
    }
}   
