/**
 * ETSI TS 102 268: UICC API testing
 * Cat Runtime Environment part 5
 * Test source for Other parts transferred to framework from API
 * Timer Id between Applets
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_api_tmid;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Cre_Api_Tmid extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_api_tmid";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 50010202";
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Cre_Api_Tmid() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {
        
        test.initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession("0101");

        // install package
        test.loadPackage(CAP_FILE_PATH);
        // install instance 1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                           "800A" + // TLV UICC Toolkit application specific parameters
                             "01" + // V Priority Level
                             "02" + // V Max. number of timers
                             "0A" + // V Maximum text length for a menu entry
                             "01" + // V Maximum number of menu entries
                           "0101" + // V Pos./Id. of menu entries
                             "00" + // V Maximum number of channels 
                             "00" + // LV Minimum Security Level field
                             "00" + // LV TAR Value(s) 
                             "00"); // V Maximum number of services
                             
        // install instance 2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_2,
                          "800A" + // TLV UICC Toolkit application specific parameters
                            "02" + // V Priority Level
                            "02" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0202" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("09010020");


        // test case 1: trigger second instance
        response = test.envelopeMenuSelection("900102", "");
        test.addResult(response.checkSw("9000"));

        // check results
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10" + APPLET_AID_2 + "01CC"));

        // delete applets and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
