/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * requestPollInterval() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rpol;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Rpol extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_rpol";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Rpol() {
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
                          "8008" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "00" + // V Maximum text length for a menu entry
                            "00" + // V Maximum number of menu entries
                            "00" + // V Maximum number of channels
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s)
                            "00"); // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("0101");

        // test case 1
        test.unrecognizedEnvelope();

        // test case 2
        test.reset();
        test.terminalProfileSession("0001");
        response = test.status("00", "00", "01");
        result = response.checkSw("9000");

        // test case 3
        test.unrecognizedEnvelope();

        // test case 4
        test.reset();
        test.terminalProfileSession("0001");
        response = test.status("00", "00", "01");
        result &= response.checkSw("9000");

        // test case 5, 6
        test.unrecognizedEnvelope();

        // test case 7
        test.reset();
        test.terminalProfileSession("0001");
        response = test.status("00", "00", "01");
        result &= response.checkSw("9000");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "07CCCCCC CCCCCCCC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}

