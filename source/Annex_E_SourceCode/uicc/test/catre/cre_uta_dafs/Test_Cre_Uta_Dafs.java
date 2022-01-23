//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_dafs;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Uta_Dafs extends UiccTestModel {

    static final String CAP_FILE_PATH_A = "uicc/test/catre/cre_uta_dafs/cre_uta_dafs_a";
    static final String CLASS_AID_A_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_A_1 = "A0000000 090005FF FFFFFF89 50010102";
    
   
    static final String CAP_FILE_PATH_B = "uicc/test/catre/cre_uta_dafs/cre_uta_dafs_b";
    static final String CLASS_AID_B_1 = "A0000000 090005FF FFFFFF89 50110001";
    static final String APPLET_AID_B_1 = "A0000000 090005FF FFFFFF89 50110102";
   
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Uta_Dafs() {
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

        // Install package A
        test.loadPackage(CAP_FILE_PATH_A);
        // Install package B
        test.loadPackage(CAP_FILE_PATH_B);
        
        // Install Applet A1
        test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Position of menu entry 1
                               "01" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        // Install Applet B1
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
        test.terminalProfileSession("09010020");
           
                
        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/



        // 1- Trigger Applet1 2- AppletA1 stores the menu Id
        test.envelopeMenuSelection("100101", "");
            
        // 3- Send an envelope Menu Selection to trigger AppletB1
        // 4- AppletB1 uses the shareable interface of AppletA1 to retrieve the Menu Id that was 
        // used to trigger AppletA1 previously
        test.envelopeMenuSelection("100102", "");
                 
        /*********************************************************************/
        /** Check Applet                                                    */
        /*********************************************************************/
        
        response = test.selectApplication(APPLET_AID_B_1);
        test.addResult(response.checkData("10" + APPLET_AID_B_1 +
                                    "01CC"));

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);                                             
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        // delete AppletA1 and AppletB1
        test.deleteApplet(APPLET_AID_B_1);
        test.deleteApplet(APPLET_AID_A_1);        
        test.deletePackage(CAP_FILE_PATH_B);
        test.deletePackage(CAP_FILE_PATH_A);
        
        
        return test.getOverallResult();
    }
}   
