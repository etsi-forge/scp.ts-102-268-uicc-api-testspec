/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * allocateTimer() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_atim;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Atim extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_atim";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 20020102";
    static final String CLASS_AID_3 =   "A0000000 090005FF FFFFFF89 20030001";
    static final String APPLET_AID_3 =  "A0000000 090005FF FFFFFF89 20030102";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Atim() {
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
                            "08" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0101" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services 

        test.reset();
        test.terminalProfileSession("0101");
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                          "800A" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "04" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0102" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services 

        test.reset();
        test.terminalProfileSession("0101");
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                          "800A" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0103" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services 

        // test script
        test.reset();
        test.terminalProfileSession("09010020");

        // test case 1 to 3: trigger applet1
        response = test.envelopeMenuSelection("100101","");
        addResult(response.checkSw("9000"));
        
        // all timers expire
        response = test.envelopeTimerExpiration("240108");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240107");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240106");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240105");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240104");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240103");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240102");
        addResult(response.checkSw("9000"));
        response = test.envelopeTimerExpiration("240101");
        addResult(response.checkSw("9000"));
        
        // test case 4: trigger applet2
        response = test.envelopeMenuSelection("100102","");
        addResult(response.checkSw("9000"));
        
        // test case 5: trigger applet3
        response = test.envelopeMenuSelection("100103","");
        addResult(response.checkSw("9000"));
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "03CCCCCC"));

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "01CC"));

        response = test.selectApplication(APPLET_AID_3);
        addResult(response.checkData("10" + APPLET_AID_3 + "01CC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);
        
        return getOverallResult();
    }
}

