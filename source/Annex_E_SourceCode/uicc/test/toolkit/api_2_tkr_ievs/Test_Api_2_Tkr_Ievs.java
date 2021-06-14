/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * isEventSet() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_ievs;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Ievs extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_ievs";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 20020102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Ievs() {
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
                              "01" + // V Max. number of timers
                              "0F" + // V Maximum text length for a menu entry
                              "01" + // V Maximum number of menu entries
                            "0101" + // V Pos./Id. of menu entries
                              "00" + // V Maximum number of channels 
                              "00" + // LV Minimum Security Level field
                              "00" + // LV TAR Value(s) 
                              "01"); // V Maximum number of services

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
                              "00"); // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("090110");

        // test case 1 to 9: trigger applet1
        test.envelopeMenuSelection("900101","");
        
        // test case 10: trigger applet2
        test.envelopeMenuSelection("900102","");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "09CCCCCC CCCCCCCC CCCC"));

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "01CC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
