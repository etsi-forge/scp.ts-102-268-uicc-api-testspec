//-----------------------------------------------------------------------------
//  Package Definition
//  Test Area: UICC CAT Runtime Environment Applet Triggering
//  EVENT_APPLICATION_DESELECT
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_eade;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Eade extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_eade";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static final String ADF1          = "A0000000 090005FF FFFFFF89 E0000002";
    static final String ADF2          = "A0000000 090005FF FFFFFF89 D0000002";
    
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Eade() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        boolean result;

        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "01"  + // V Priority Level
                            "08"  + // V Max. number of timers
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
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        test.selectForActivation(ADF1);
        //***TEST CASE 1: 2-APPLET IS TRIGGERED HANDLER CONTAINS THE AID OF ADF1
        test.selectForActivation(ADF2);
        //***TEST CASE 1: 3-APPLET IS TRIGGERED HANDLER CONTAINS THE AID OF ADF2
        test.selectForTermination(ADF2);
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.selectForActivation(ADF1);
        //***TEST CASE 2: 2-APPLET IS NOT TRIGGERED
        test.selectForActivation(ADF2);
        //***TEST CASE 2: 3-APPLET IS NOT TRIGGERED
        test.selectForTermination(ADF2);
        //***TEST CASE 2: 4-APPLET IS TRIGGERED
        test.envelopeMenuSelection("100101", "");//Help Request not available
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.selectForActivation(ADF1);
        //***TEST CASE 2: 6-APPLET IS TRIGGERED HANDLER CONTAINS THE AID OF ADF1
        test.selectForActivation(ADF2);
        //***TEST CASE 2: 7-APPLET IS TRIGGERED HANDLER CONTAINS THE AID OF ADF2
        test.selectForTermination(ADF2);


        // check results
        response  = test.selectApplication(APPLET_AID_1);
        result    = response.checkData("10"+APPLET_AID_1+"0ACCCCCC CCCCCCCC CCCCCC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}
