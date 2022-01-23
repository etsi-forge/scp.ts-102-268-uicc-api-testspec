/**
 * ETSI TS 102 268: UICC API testing
 * Cat Runtime Environment part 5
 * Test source for Other parts transferred to framework from API
 * Transaction
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_api_tran;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Cre_Api_Tran extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_api_tran";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 50020102";
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Cre_Api_Tran() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        test.initialiseResults();

         // start test
         test.reset();
         test.terminalProfileSession("098101");

         // install package
         test.loadPackage(CAP_FILE_PATH);
         // install applet1
         test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                              "8008" + // TLV UICC Toolkit application specific parameters
                                "01" + // V Priority Level
                                "00" + // V Max. number of timers
                                "0A" + // V Maximum text length for a menu entry
                                "00" + // V Maximum number of menu entries
                                "00" + // V Maximum number of channels 
                                "00" + // LV Minimum Security Level field
                                "00" + // LV TAR Value(s) 
                                "00"); // V Maximum number of services
                

         // test case 1: trigger applet1
         response = test.unrecognizedEnvelope();
         test.addResult(response.checkSw("9114"));
         response = test.fetch("14");
         test.addResult(response.checkData("D0128103 01218082 0281028D 07044845" +
                                              "4C4C4F31"));
         response = test.terminalResponse("81030121 80020282 81030100");
         test.addResult(response.checkSw("9000"));

         // install applet2
         test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                            "800A" + // TLV UICC Toolkit application specific parameters
                              "01" + // V Priority Level
                              "00" + // V Max. number of timers
                              "0A" + // V Maximum text length for a menu entry
                              "01" + // V Maximum number of menu entries
                            "0101" + // V Pos./Id. of menu entries				
                              "00" + // V Maximum number of channels 
                              "00" + // LV Minimum Security Level field
                              "00" + // LV TAR Value(s) 
                              "00"); // V Maximum number of services

         test.reset();
         test.terminalProfileSession("098101");

         // test case 2: trigger applet2
         response = test.envelopeMenuSelection("900101", "");
         test.addResult(response.checkSw("9114"));

         // trigger applet1
         response = test.unrecognizedEnvelope();
         test.addResult(response.checkSw("9114"));

         // resume applet2
         response = test.fetch("14");
         test.addResult(response.checkData("D0128103 01218082 0281028D 07044845" +
                                      "4C4C4F32"));
         response = test.terminalResponse("81030121 80020282 81030100");
         test.addResult(response.checkSw("9000"));

         // check results
         response = test.selectApplication(APPLET_AID_1);
         test.addResult(response.checkData("10" + APPLET_AID_1 + "02CCCC"));
         response = test.selectApplication(APPLET_AID_2);
         test.addResult(response.checkData("10" + APPLET_AID_2 + "01CC"));

         // delete applets and package
         test.reset();
         test.terminalProfileSession("0101");
         test.deleteApplet(APPLET_AID_1);
         test.deleteApplet(APPLET_AID_2);
         test.deletePackage(CAP_FILE_PATH);

         return test.getOverallResult();
    }
}
