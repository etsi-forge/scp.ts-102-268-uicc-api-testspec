//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_ufa_view;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;


public class Test_Cre_Ufa_View extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/catre/cre_ufa_view";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 50010001";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 50020102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Ufa_View() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession("0101");


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                            "800A" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                            "0101" + // V Pos./Id. of menu entries    
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00" + // V Maximum number of services
                            "8118" + // TLV UICC Access Application specific parameters
                            "00" + // LV UICC file system AID
                            "0100" + // LV UICC file system access aomain
                            "00" + // LV UICC file system access domain DAP
                            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 AID
                            "0100" + // LV ADF1 access domain
                            "00"); // LV ADF1 access domain DAP

        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                            "8118" + // TLV UICC Access Application specific parameters
                            "00" + // LV UICC file system AID
                            "0100" + // LV UICC file system access aomain
                            "00" + // LV UICC file system access domain DAP
                            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 AID
                            "0100" + // LV ADF1 access domain
                            "00"); // LV ADF1 access domain DAP

        // test script
        test.reset();
        test.terminalProfileSession("09010020");

        // test case 1 to 4: trigger applet1
        test.envelopeMenuSelection("100101","");
        test.envelopeMenuSelection("100101","");
        test.envelopeMenuSelection("100101","");
        test.envelopeMenuSelection("100101","");

        // test case 5: trigger twice applet1
        test.envelopeMenuSelection("100101","");
        test.envelopeMenuSelection("100101","");

        // test case 6: File Context can be transient or persistent.
        // Performed twice (1.with UICC FileView object 2.with ADF1 FileView object) 
        for (byte i=0; i<2; i++) {
            test.envelopeMenuSelection("100101","");
            test.reset();
            test.terminalProfileSession("09010020");
            test.envelopeMenuSelection("100101","");
        }

        // test case 7: File Context integrity
        response = test.envelopeMenuSelection("100101","");
        test.addResult(response.checkSw("9113"));
        response = test.fetch("13");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeCallControlByNAA();
        test.addResult(response.checkSw("9000"));
        response = test.terminalResponse("81030121 80020282 81030100");
        test.addResult(response.checkSw("9000"));

        // test case 8: Applet2 can get a FileView
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkSw("9000"));
        
        // test cases 9 to 13
        for (byte i=0; i<2; i++) {
            response = test.selectApplication(APPLET_AID_2);
            test.addResult(response.checkSw("9000"));
            test.reset();
            test.terminalProfileSession("09010020");
            response = test.selectApplication(APPLET_AID_2);
        }

        // check applet2 results
        test.addResult(response.checkData("10" + APPLET_AID_2 + "0ACCCCCC CCCCCCCC CCCCCC"));
        
        // check applet1 results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 + "0BCCCCCC CCCCCCCC CCCCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
