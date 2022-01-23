//-----------------------------------------------------------------------------
//  Package Definition
//  UICC CAT Runtime Environment Applet Triggering
//  EVENT_PROFILE_DOWNLOAD
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_epdw;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Epdw extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_epdw";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String CLASS_AID_2   = "A0000000 090005FF FFFFFF89 50020001";
    static final String CLASS_AID_3   = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2  = "A0000000 090005FF FFFFFF89 50020102";
    static final String APPLET_AID_3  = "A0000000 090005FF FFFFFF89 50030102";

    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Epdw() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        test.initialiseResults();


        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "8008"+ // TLV UICC Toolkit application specific parameters
                            "01"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "00"  + // V Maximum number of menu entries
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                            "8008"+ // TLV UICC Toolkit application specific parameters
                            "02"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "00"  + // V Maximum number of menu entries
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "03"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "01"  + // V Maximum number of menu entries
                            "01"  + // V Position of the first menu entry
                            "01"  + // V Identifier of the first menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services


        // test script
        test.reset();
        //***TEST CASE 1 OF THE APPLETS 1 AND 2 ***
        test.terminalProfileSession("09018000 01");
        //***TEST CASE 1 OF THE APPLET 3 ***
        response = test.envelopeMenuSelection("100101", "");//Help Request not available
        test.addResult(response.checkSw("910B"));
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01010382 028182"));

        //***TEST CASE 2 OF THE APPLETS 1 AND 2 ***
        test.terminalProfileSession("03010000 01");
        //TERMINAL RESPONSE TO THE REFRESH OF THE APPLET3
        test.terminalResponse("81030101 03820282 81030100");
        //***TEST CASE 3 OF THE APPLET 3 ****
        test.terminalProfileSession("03010000 01");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10"+APPLET_AID_1+"03CCCCCC"));
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10"+APPLET_AID_2+"03CCCCCC"));
        response = test.selectApplication(APPLET_AID_3);
        test.addResult(response.checkData("10"+APPLET_AID_3+"03CCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
