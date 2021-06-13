/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * setMenuEntryTextAttribute() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_smta;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Smta extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_smta";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";

   
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Smta() {
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
                            "8010" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0F" + // V Maximum text length for a menu entry
                            "04" + // V Maximum number of menu entries
                            "01010202 03030404" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s)
                            "00"); // V Maximum number of services
                    
        // test script
        test.reset();
        response = test.terminalProfile("09010020 00000000 00000000 00000008" +
                                               "00000000 00000000 0000003F 7F");
        
        result = response.checkSw("913E");
        response = test.fetch("3E");
        test.terminalResponse("81030125 00820282 81830100");

        // testcase 1
        response = test.unrecognizedEnvelope();
        result &= response.checkSw("9150");
        response = test.fetch("50");

        result &= response.checkData("D04E8103 01250082 02818285 09554943" +
                                    "43205445 53548F08 01417070 6C657431" +
                                    response.getData().substring(64,66)+  "08024170 706C6574 31"+ //item2
                                    response.getData().substring(84,86)+  "08034170 706C6574 31"+ //item3    
                                    response.getData().substring(104,106)+"08044170 706C6574 31"+ //item4  
                                    response.getData().substring(124,126)+"10000003 90000C11 02000003 9000000390");//text attribute  
  
        test.terminalResponse("81030125 00820282 81830100");                                
        // testcase 2
        response = test.unrecognizedEnvelope();
        result &= response.checkSw("9150");
        response = test.fetch("50");
        result &= response.checkData("D04E8103 01250082 02818285 09554943" +
                                     "43205445 53548F08 01417070 6C657431" +
                                     response.getData().substring(64,66)+  "08024170 706C6574 31"+ //item2  
                                     response.getData().substring(84,86)+  "08034170 706C6574 31"+ //item3  
                                     response.getData().substring(104,106)+"08044170 706C6574 31"+ //item4  
                                     response.getData().substring(124,126)+"10000003 90000C11 02000003 90000C1003");//text attribute                                                                                                                                            
                                        
        test.terminalResponse("81030125 00820282 81830100");  
        
        // testcase 3->9
        for (int i = 3; i <= 9; i++)
            response = test.unrecognizedEnvelope();
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 +
                                     "09CCCCCC CCCCCCCC CCCC");
        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return result;
    }
}

