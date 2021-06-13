/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * isEventSet() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_sevt;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Sevt extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_sevt";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 20020102";
    static final String CLASS_AID_3 =   "A0000000 090005FF FFFFFF89 20030001";
    static final String APPLET_AID_3 =  "A0000000 090005FF FFFFFF89 20030102";
    static final String CLASS_AID_4 =   "A0000000 090005FF FFFFFF89 20040001";
    static final String APPLET_AID_4 =  "A0000000 090005FF FFFFFF89 20040102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Sevt() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        boolean result = true;

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

        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                          "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "0F" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0101" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "00"); // V Maximum number of services

        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                          "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "0F" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0102" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "00"); // V Maximum number of services

        test.installApplet(CAP_FILE_PATH, CLASS_AID_4, APPLET_AID_4,
                          "800A" + // TLV UICC Toolkit application specific parameters
                          "01" + // V Priority Level
                          "00" + // V Max. number of timers
                          "0F" + // V Maximum text length for a menu entry
                          "01" + // V Maximum number of menu entries
                        "0103" + // V Pos./Id. of menu entries
                          "00" + // V Maximum number of channels
                          "00" + // LV Minimum Security Level field
                          "00" + // LV TAR Value(s)
                          "00"); // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("090110");

        // test case 1 to 10: trigger applet1
        test.envelopeEventDownloadUserActivity();

        // test case 11
        test.envelopeCallControlByNAA();

        // test case 12: trigger applet2
        test.envelopeMenuSelection("900101","");

        // test case 13: Set Applet1 in the lock state
        test.lockApplication(APPLET_AID_1);
        
        // trigger applet3
        test.envelopeMenuSelection("900102","");

        // Set Applet1 in selectable state
        test.unlockApplication(APPLET_AID_1);

        // test case 14: trigger applet4
        test.envelopeMenuSelection("900103","");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "0BCCCCCC CCCCCCCC CCCCCCCC");

        response = test.selectApplication(APPLET_AID_2);
        result &= response.checkData("10" + APPLET_AID_2 + "01CC");

        response = test.selectApplication(APPLET_AID_3);
        result &= response.checkData("10" + APPLET_AID_3 + "01CC");

        response = test.selectApplication(APPLET_AID_4);
        result &= response.checkData("10" + APPLET_AID_4 + "03CCCCCC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deleteApplet(APPLET_AID_4);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}

