/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * isEventSet() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_sevl;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Sevl extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_sevl";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 20020102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Sevl() {
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
                        "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "10" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0101" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "00"); // V Maximum number of services

        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                        "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "10" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0202" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "00"); // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("09010020");

        // test case 1 to 18: trigger Applet1
        response = test.envelopeMenuSelection("900101", "");
        addResult(response.checkSw("9000"));

        // test case 19: trigger Applet1 with EVENT_CALL_CONTROL_BY_NAA
        test.reset();
        test.terminalProfileSession("09010020 03");
        response = test.envelopeCallControlByNAA();
        addResult(response.checkSw("9000"));

        // test case 20 to 22: trigger Applet2
        response = test.envelopeMenuSelection("900102", "");
        addResult(response.checkSw("910E"));
        response = test.fetch("0E");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 +
                                     "13CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC"));

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "03CCCCCC"));

         // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
