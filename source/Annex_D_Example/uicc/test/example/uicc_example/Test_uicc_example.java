//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.example.uicc_example;

//-----------------------------------------------------------------------------
//	Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_uicc_example extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/example/example/uicc_example";
    static final String CLASS_AID_1 = "A0000000 090002FF FFFFFF89 40010001";
    static final String APPLET_AID_1 = "A0000000 090002FF FFFFFF89 40010101";
    static final String CLASS_AID_2 = "A0000000 090002FF FFFFFF89 40020001";
    static final String APPLET_AID_2 = "A0000000 090002FF FFFFFF89 40020101";
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_uicc_example() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        boolean result = false;

        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, "8008" +
                            // TLV UICC Toolkit application specific parameters
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
        test.terminalProfileSession("03010000 0102");

        // test case 1: trigger applet1
        test.envelopeEventDownloadBrowserTermination();

        // check results
        response = test.selectApplication(APPLET_AID_1);
        result = response.checkData("10" + APPLET_AID_1" +
                                    "01CC");

        // delete applet
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);

        // test case 2: Applet 2 installation
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                            "800A" + // TLV UICC Toolkit application specific parameters
                                "01" + // V Priority Level
                                "00" + // V Max. number of timers
                                "0A" + // V Maximum text length for a menu entry
                                "01" + // V Maximum number of menu entries
                                "01" + // V Id of menu entry 1       
                                "01" + // V Position of menu entry 1 
                                "00" + // V Maximum number of channels 
                                "00" + // LV Minimum Security Level field
                                "00" + // LV TAR Value(s) 
                                "00"); // V Maximum number of services 

        // Fetch setUpMenu proactive command
        response = test.fetch("12");                                              
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");                             

        // Terminal response
        response = test.terminalResponse("81030121 00820282 81030100");   
        result &= response.checkSw("9000");                                       

        // check results
        response = test.selectApplication(APPLET_AID_2);
        result = response.checkData("10" + APPLET_AID_2" +
                                    "01CC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}
