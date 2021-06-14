/**
 * ETSI TS 102 268: UICC API testing
 * uicc.system package part 4
 * Test source for HandlerBuilder class
 * buildTLVHandler(byte type, short capacity, byte[] buffer, short bOffset, short bLength) method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.system.api_3_hbd_bthdbs_bss;

//-----------------------------------------------------------------------------
//	Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Api_3_Hbd_Bthdbs_Bss extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/system/api_3_hbd_bthdbs_bss";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 30010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 30010102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_3_Hbd_Bthdbs_Bss() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        initialiseResults();

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
        test.terminalProfileSession("0101");

        // test case 1 to 11: trigger applet1
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("9000"));

        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "0BCCCCCC CCCCCCCC CCCCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
