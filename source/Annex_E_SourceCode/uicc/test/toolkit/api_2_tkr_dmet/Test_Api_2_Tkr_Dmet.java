/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * disableMenuEntry() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_dmet;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Dmet extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_dmet";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Dmet() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        initialiseResults();
        
        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "800C" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0F" + // V Maximum text length for a menu entry
                            "02" + // V Maximum number of menu entries
                            "01010202" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services 
        
        // test script
        test.reset();
        response = test.terminalProfile("09010020");
        addResult(response.checkSw("9124"));
        response = test.fetch("24");
        addResult(response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F05 01496E69 748F0502" +
                                     "496E6974"));
        response = test.terminalResponse("81030125 00820282 81830100");
        addResult(response.checkSw("9000"));

        // test case 1
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("9000"));

        // test case 2
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("911D"));
        response = test.fetch("1D");
        addResult(response.checkData("D01B8103 01250082 02818285 09554943" +
                                     "43205445 53548F05 02496E69 74"));
        response = test.terminalResponse("81030125 00820282 81830100");
        addResult(response.checkSw("9000"));

        // test case 3
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("911D"));
        response = test.fetch("1D");
        addResult(response.checkData("D01B8103 01258082 02818285 09554943" +
                                     "43205445 53548F05 02496E69 74"));
        response = test.terminalResponse("81030125 80820282 81830100");
        addResult(response.checkSw("9000"));

        // test case 4
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("9118"));
        response = test.fetch("18");
        addResult(response.checkData("D0168103 01250082 02818285 09554943" +
                                     "43205445 53548F00"));
        response = test.terminalResponse("81030125 00820282 81830100");
        addResult(response.checkSw("9000"));

        // test case 5
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("9000"));

        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "05CCCCCC CCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return getOverallResult();
    }
}

