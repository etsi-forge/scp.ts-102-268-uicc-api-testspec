//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_genb;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Genb extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_genb";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Genb() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // Install package
        test.loadPackage(CAP_FILE_PATH);


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
        
        // Select applet1
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("4F4B2031 31")); // "OK 11"
        
        //delete Applet1
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        

        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" +   // V Maximum number of services
                               "0000"); // Unused bytes 
        
        // Select applet1
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("4F4B2031 31")); // "OK 11"
        

        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Install Applet2
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                                      "8104" + // TLV UICC Access application specific parameters
                                          "00" +   // LV UICC File System AID field
                                          "0100" + // LV Access Domain for UICC file system = ALWAYS
                                          "00" );  // LV Access Domain DAP field       
        
        // Select applet2
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("4F4B2032")); // "OK 2"
        

        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applets and package
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return test.getOverallResult();
    }
}   
