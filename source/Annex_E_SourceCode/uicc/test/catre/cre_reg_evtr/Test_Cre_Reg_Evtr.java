//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_reg_evtr;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;


public class Test_Cre_Reg_Evtr extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/catre/cre_reg_evtr";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 50010001";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 50020102";

   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Reg_Evtr() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession("0101000033");


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        // Install Applet1
        test.installInstallApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                            "8008" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "00" + // V Maximum text length for a menu entry
                            "00" + // V Maximum number of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services

        test.status("00", "0C", "00");

        // test script
        response = test.makeSelectableApplet(APPLET_AID_1);
        addResult(response.checkSw("910F"));
        response = test.fetch("0F");
        addResult(response.checkData("D00D8103 01050082 02818299 020004") ||
                   response.checkData("D00D8103 01050082 02818219 020004"));
        test.terminalResponse("81030105 00820282 81830100");

        // test case 1: trigger applet1
        response = test.envelopeEventDownloadUserActivity();
        addResult(response.checkSw("910F"));
        response = test.fetch("0F");
        addResult(response.checkData("D00D8103 01050082 02818299 020003")) ||
                   response.checkData("D00D8103 01050082 02818219 020003"));
        test.terminalResponse("81030105 00820282 81830100");
        
        // Install Applet2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                            "8008" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "00" + // V Maximum text length for a menu entry
                            "00" + // V Maximum number of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services

        test.reset();
        test.terminalProfileSession("0101000033");


        // test case 2: trigger applet2
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("9000"));
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "01CC"));

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "01CC"));

        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
