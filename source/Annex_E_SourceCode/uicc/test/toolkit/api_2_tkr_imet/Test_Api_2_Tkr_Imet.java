/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * isEventSet() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_imet;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Imet extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_imet";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Imet() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        boolean result = false;
        
        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "8014" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0F" + // V Maximum text length for a menu entry
                            "06" + // V Maximum number of menu entries
                            "01010202 03030404 05050606" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s)
                            "00"); // V Maximum number of services
                    
        // test script
        test.reset();
        test.terminalProfile("09010020");
        
        // test Case 16
        response = test.fetch("76");
        result = response.checkData("D0748103 01258082 02818285 09554943" +
                                    "43205445 53548F0F 01544F4F 4C4B4954" +
                                    "20544553 5420318F 0F02544F 4F4C4B49" +
                                    "54205445 53542032 8F0F0354 4F4F4C4B" +
                                    "49542054 45535420 338F0F04 544F4F4C" +
                                    "4B495420 54455354 20348F0F 05544F4F" +
                                    "4C4B4954 20544553 5420358F 01061806" +
                                    "00000000 2400");
        result &= response.checkSw("9000");
        
        response = test.terminalResponse("81030125 80820282 81830100");
        result &= response.checkSw("9000");
        
        test.unrecognizedEnvelope();
        result &= response.checkSw("9000");
        
        // test case 17
        response = test.envelopeMenuSelection("900101", "");
        result &= response.checkSw("9000");
        
        // test case 18
        response = test.envelopeMenuSelection("900102", "");
        result &= response.checkSw("9000");
        
        // test case 19
        response = test.envelopeMenuSelection("900103", "");
        result &= response.checkSw("9000");
        
        // test case 20
        response = test.envelopeMenuSelection("900104", "");
        result &= response.checkSw("9000");
        
        // test case 21
        response = test.envelopeMenuSelection("900105", "");
        result &= response.checkSw("9000");
        
        // test case 22
        response = test.envelopeMenuSelection("900103", "9500");
        result &= response.checkSw("9000");
        
        // test case 23
        response = test.envelopeMenuSelection("900106", "");
        result &= response.checkSw("9000");
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 +
                                     "17CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC CCCCCCCC");
        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return result;
    }
}

