/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * changeMenuEntry() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_cmet;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Cmet extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_cmet";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Cmet() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        test.initialiseResults();
        
        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applet
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
        test.terminalProfileSession("09010020");
        
        // test case 1
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("912D"));
        response = test.fetch("2D");
        test.addResult(response.checkData("D02B8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 01496E69 74318F0D" +
                                     "02557365 416C6C42 75666665 72"));
        response = test.terminalResponse("81030125 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // test case 2
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("9134"));
        response = test.fetch("34");
        test.addResult(response.checkData("D0328103 01250082 02818285 09554943" +
                                     "43205445 53548F0D 01506172 744F6642" +
                                     "75666665 728F0D02 55736541 6C6C4275" +
                                     "66666572"));
        response = test.terminalResponse("81030125 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // test case 3
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("911C"));
        response = test.fetch("1C");
        test.addResult(response.checkData("D01A8103 01250082 02818285 09554943" +
                                     "43205445 53548F01 018F0102"));
        response = test.terminalResponse("81030125 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // test case 4
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("912F"));
        response = test.fetch("2F");
        test.addResult(response.checkData("D02D8103 01258082 02818285 09554943" +
                                     "43205445 53548F01 018F1002 4E657874" +
                                     "41637469 6F6E496E 64696318 020010"));
        response = test.terminalResponse("81030125 80820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // test case 5
        response = test.envelopeMenuSelection("900102", "9500");
        test.addResult(response.checkSw("9000"));
        
        // test case 6
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("913C"));
        response = test.fetch("3C");
        test.addResult(response.checkData("D03A8103 01258082 02818285 09554943" +
                                     "43205445 53548F0E 0148656C 70537570" +
                                     "706F7274 65648F10 024E6578 74416374" +
                                     "696F6E49 6E646963 18020010"));
        response = test.terminalResponse("81030125 80820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // test case 7
        response = test.envelopeMenuSelection("900101", "9500");
        test.addResult(response.checkSw("9000"));
        
        // test case 8
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("913B"));
        response = test.fetch("3B");
        test.addResult(response.checkData("D0398103 01250082 02818285 09554943" +
                                     "43205445 53548F0E 0149636F 6E517561" +
                                     "6C696669 65728F0E 0249636F 6E517561" +
                                     "6C696669 65721F03 010102"));
        response = test.terminalResponse("81030125 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // test case 9
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("9134"));
        response = test.fetch("34");
        test.addResult(response.checkData("D0328103 01250082 02818285 09554943" +
                                     "43205445 53548F0C 01456E61 626C6545" +
                                     "6E747279 8F0E0249 636F6E51 75616C69" +
                                     "66696572"));
        response = test.terminalResponse("81030125 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        
        // test case 10..19
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("912B"));
        
        // test case 20
        response = test.fetch("2B");
        test.addResult(response.checkData("D0298103 01250082 02818285 09554943" +
                                     "43205445 53548F0C 01456E61 626C6545" +
                                     "6E747279 8F050249 6E6974"));
        response = test.terminalResponse("81030125 00820282 81830100");
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 +
                                     "14CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC CC"));
        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return test.getOverallResult();
    }
}
