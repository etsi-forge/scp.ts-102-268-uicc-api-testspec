/**
 * ETSI TS 102 268: UICC API testing
 * uicc.system package part 4
 * Test source for UICCPlatform class
 * getTheVolatileByteArray() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.system.api_3_upf_gvba;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

// Interface UICCPlatform, Method getTheVolatileByteArray()

public class Test_Api_3_Upf_Gvba extends UiccTestModel {

    static final String CAP_FILE_PATH =  "uicc/test/system/api_3_upf_gvba";
    static final String CAP_FILE_PATH2 = "uicc/test/system/api_3_upf_gvba/api_3_upf_gvba2";
    static final String CLASS_AID_1 =    "A0000000 090005FF FFFFFF89 30010001";
    static final String APPLET_AID_1 =   "A0000000 090005FF FFFFFF89 30010102";
    static final String CLASS_AID_2 =    "A0000000 090005FF FFFFFF89 30110001";
    static final String APPLET_AID_2 =   "A0000000 090005FF FFFFFF89 30110102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_3_Upf_Gvba() {
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
                          "8008" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "00" + // V Maximum number of menu entries
                            "00" + // V Maximum number of channels
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s)
                            "00"); // V Maximum number of services

        // install client package and applet for shareable test case
        test.loadPackage(CAP_FILE_PATH2);
        test.installApplet(CAP_FILE_PATH2, CLASS_AID_2, APPLET_AID_2, "");

        // test script
        test.reset();
        test.terminalProfileSession("0101");

        // test case 1, 3 to 5: trigger applet1
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("9000"));
        
        // test case 2: select client applet. It calls applet1 shared method.
        test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkSw("9000"));

        // test case 1: select applet1
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkSw("9000"));

        
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 + "06CCCC CCCCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH2);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
