/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * releaseServiceIdentifier() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rsid;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Rsid extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_rsid";
    static final String CLASS_AID_1  =  "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    static final String CLASS_AID_2  =  "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 20020102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Rsid() {
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
                          "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "0F" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0101" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "08"); // V Maximum number of services

        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                          "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "0F" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0102" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "01"); // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("09010000 0040");

        // test case 1 to 5: trigger Applet1 with EVENT_MENU_SELECTION (item = '01')
        response = test.envelopeMenuSelection("900101","");
        test.addResult(response.checkSw("9000"));

        // test case 5: trigger Applet2 with EVENT_MENU_SELECTION (item = '02')
        response = test.envelopeMenuSelection("900102","");
        test.addResult(response.checkSw("9110"));
        response = test.fetch("10");
        response = test.terminalResponse("81030147 00820282 81830100");
        

        // test case 6: trigger Applet1 with EVENT_MENU_SELECTION (item = '01')
        response = test.envelopeMenuSelection("900101","");
        test.addResult(response.checkSw("9000"));

        // test case 7: trigger Applet1 with EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
        response = test.envelopeEventDownloadLocalConnection("4103000100");
        test.addResult(response.checkSw("9000"));

        // test case 8: trigger Applet1 with EVENT_MENU_SELECTION (item = '01')
        response = test.envelopeMenuSelection("900101","");
        test.addResult(response.checkSw("9000"));

        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 + "08CCCCCC CCCCCCCC CC"));

        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10" + APPLET_AID_2 + "02CCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}

