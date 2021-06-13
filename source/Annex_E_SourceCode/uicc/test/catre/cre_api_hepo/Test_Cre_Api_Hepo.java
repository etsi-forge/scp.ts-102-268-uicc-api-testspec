/**
 * ETSI TS 102 268: UICC API testing
 * Cat Runtime Environment part 5
 * Test source for Other parts transferred to framework from API
 * A handler is a temporary JCRE Entry Point object
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_api_hepo;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Cre_Api_Hepo extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_api_hepo";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 50010102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Api_Hepo() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        boolean result = false;

        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
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

        // test script
        test.reset();
        test.terminalProfileSession("01010000 0102");

        // test case 1 to 8: trigger applet1
        response = test.unrecognizedEnvelope();
        result = response.checkSw("9113");
        
        // fetch display text proactive command
        response = test.fetch("13");
        result = result & response.checkData("D0118103 01218082 0281028D 06044845" +
                                             "4C4C4F");
        response = test.terminalResponse("81030121 80020282 81030100");
        result = result & response.checkSw("9000");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        result = result & response.checkData("10" + APPLET_AID_1 + "08CCCCCC CCCCCCCC CC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}

