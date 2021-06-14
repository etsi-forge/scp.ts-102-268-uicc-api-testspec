//-----------------------------------------------------------------------------
//  Package Definition
//  Test Area: UICC CAT Runtime Environment Applet Triggering
//  General behaviuour
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_genb;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Genb extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_genb";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";


    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Genb() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        
        //***TEST CASE 1: 2-APPLET 1 IS REGISTERED TO EVENT_EVENT_DOWNLOAD_USER_ACTIVITY ***
        //***THE APPLET ISN'T IN SELECTABLE STATE: APPLET ISN'T TRIGGERED        
        response = test.installInstallApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,

                                        "8008"+ // TLV UICC Toolkit application specific parameters
                                        "01"  + // V Priority Level
                                        "08"  + // V Max. number of timers
                                        "0A"  + // V Maximum text length for a menu entry
                                        "00"  + // V Maximum number of menu entries
                                        "00"  + // V Maximum number of channels
                                        "00"  + // LV Minimum Security Level field
                                        "00"  + // LV TAR Value(s)
                                        "00");  // V Maximum number of services
        addResult(response.checkSw("9000"));
        
        // test script
        test.reset();
        test.terminalProfileSession("01010000 20");

        response = test.selectApplication(APPLET_AID_1);        
        addResult(!response.checkSw("9000"));

        test.reset();
        test.terminalProfileSession("01010000 20");

        response = test.envelopeEventDownloadUserActivity();        


        //***TEST CASE 2: 2-APPLET IS IN SELECTABLE STATE:APPLET IS TRIGGERED ***
        //***
        //**** INSTALL THE APPLET IN SELECTABLE STATE
        response = test.makeSelectableApplet(APPLET_AID_1);
        addResult(response.checkSw("9000"));
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkSw("9000"));

		// test script
        test.reset();
        test.terminalProfileSession("01010000 20");

        response = test.envelopeEventDownloadUserActivity();

        //***TEST CASE 3: 2-THE APPLET IS IN LOCK STATE: APPLET ISN'T TRIGGERED ***
        response = test.lockApplication(APPLET_AID_1);
        addResult(response.checkSw("9000"));
        response = test.selectApplication(APPLET_AID_1);
        
        addResult(!response.checkSw("9000"));
        
        test.reset();
        test.terminalProfileSession("01010000 20");
        
        response = test.envelopeEventDownloadUserActivity();

        //***TEST CASE 4: 2-APPLET 1 IS REGISTERED TO EVENT_EVENT_DOWNLOAD_USER_ACTIVITY ***
        //***APPLET IS IN UNLOCK STATE:APPLET IS TRIGGERED

        response = test.unlockApplication(APPLET_AID_1);
        addResult(response.checkSw("9000"));
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkSw("9000"));
        
		// test script
        test.reset();
        test.terminalProfileSession("01010000 20");        
        
        response = test.envelopeEventDownloadUserActivity();

        // check results
        response  = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10"+APPLET_AID_1+"04CCCCCC CC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
