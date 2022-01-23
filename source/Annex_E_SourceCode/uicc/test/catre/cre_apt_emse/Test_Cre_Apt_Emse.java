//-----------------------------------------------------------------------------
//  Package Definition
//  UICC CAT Runtime Environment Applet Triggering
//  EVENT_MENU_SELECTION
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_emse;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Emse extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_emse";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String CLASS_AID_2   = "A0000000 090005FF FFFFFF89 50020001";

    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2  = "A0000000 090005FF FFFFFF89 50020102";

    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Emse() {
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
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "01"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "01"  + // V Maximum number of menu entries
                            "01"  + // V Position of the first menu entry
                            "01"  + // V Identifier of the first menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "01"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "01"  + // V Maximum number of menu entries
                            "02"  + // V Position of the first menu entry
                            "02"  + // V Identifier of the first menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services


        // test script
        test.reset();
        test.terminalProfileSession("09010020 01");
        //***TEST CASE 2: THE APPLET 1 MUST BE TRIGGERED ***
        //***AND APPLET 2 IS NOT TRIGGERED****
        //ENVELOPE MENU SELECTION WITH IDENTIFIER=01
        test.envelopeMenuSelection("100101", "");//Help Request not available
        //***TEST CASE1: APPLET2 IS TRIGGERED AND APPLET1 IS NOT TRIGGERED***
        //ENVELOPE MENU SELECTION WITH IDENTIFIER=02
        test.envelopeMenuSelection("100102", "");//Help Request not available

        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10"+APPLET_AID_1+"03CCCCCC"));
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10"+APPLET_AID_2+"03CCCCCC"));


        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
