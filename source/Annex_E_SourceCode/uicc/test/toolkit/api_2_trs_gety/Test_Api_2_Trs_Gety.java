/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistrySystem class
 * getEntry() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_trs_gety;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Trs_Gety extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_trs_gety";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Trs_Gety() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession("0101");

        // install package
        test.loadPackage(CAP_FILE_PATH);
        // test cases 1 and 2: install applet
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
        test.terminalProfileSession("0901");

        // test case 3: trigger applet1
        test.unrecognizedEnvelope();

        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "03CCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}

