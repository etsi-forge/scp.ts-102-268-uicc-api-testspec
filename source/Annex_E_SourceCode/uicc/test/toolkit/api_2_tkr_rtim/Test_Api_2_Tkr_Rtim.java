/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * releaseTimer() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rtim;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Rtim extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_rtim";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Rtim() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        test.initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession("0101");

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                          "8008" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "08" + // V Max. number of timers
                            "00" + // V Maximum text length for a menu entry
                            "00" + // V Maximum number of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services 

        // test script
        test.reset();
        test.terminalProfileSession("2101");

        // test case 1 to 6
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("9000"));

        // test case 7
        response = test.envelopeTimerExpiration("240101");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240102");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240103");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240104");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240105");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240106");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240107");
        test.addResult(response.checkSw("9000"));

        response = test.envelopeTimerExpiration("240108");
        test.addResult(response.checkSw("9000"));

        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 + "07CCCCCC CCCCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
