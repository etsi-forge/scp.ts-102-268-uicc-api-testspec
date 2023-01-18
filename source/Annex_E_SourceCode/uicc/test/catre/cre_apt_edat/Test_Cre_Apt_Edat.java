//-----------------------------------------------------------------------------
//  Package Definition
//  UICC CAT Runtime Environment Applet Triggering
//  EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_edat;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Edat extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_edat";
    static final String CLASS_AID_1  = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";

    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Edat() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        initialiseResults();



        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH,  CLASS_AID_1, APPLET_AID_1,
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
        test.terminalProfileSession("09010000 0110");
        //***TEST CASE 1: 1-APPLET 1 IS REGISTERED TO EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE***
        //***TEST CASE 1: 2-APPLET 1 IS TRIGGERED***
        test.envelopeEventDownloadAccessTechnologyChange();
        response = test.fetch("0D");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030105 00820282 81830100");
        //***TEST CASE 2: 1-APPLET 1 IS NOT TRIGGERED***
        test.envelopeEventDownloadAccessTechnologyChange();
        //***TEST CASE 2: 1 IS REGISTERED TO EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE***
        test.envelopeMenuSelection("100101", "");//Help Request not available
        response = test.fetch("0E");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030105 00820282 81830100");
        //***TEST CASE 2: 2-APPLET 1 IS TRIGGERED***
        test.envelopeEventDownloadAccessTechnologyChange();


        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10"+APPLET_AID_1+"05CCCCCC CCCC"));



        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
