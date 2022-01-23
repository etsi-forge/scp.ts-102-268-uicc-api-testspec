//-----------------------------------------------------------------------------
//  Package Definition
//  UICC CAT Runtime Environment Applet Triggering
//  EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_edcs;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Edcs extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_edcs";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";

    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Edcs() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        test.initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH,  CLASS_AID_1, APPLET_AID_1,
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "03"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "01"  + // V Maximum number of menu entries
                            "01"  + // V Position of the first menu entry
                            "01"  + // V Identifier of the first menu entry
                            "02"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services

        // test script
        test.reset();
        //***TEST CASE 1: 1-APPLET 1 IS TRIGGERED BY EVENT_FIRST_COMMAND_AFTER_ATR,
        //***             2-APPLET 2 IS TRIGGERED BY EVENT_PROFILE_DONWLOAD
        //***             3_APPLET 3 IS NOT TRIGGERED
        test.terminalProfileSession("09010020 000C0000 00000003 40");
         //***TEST CASE 1: 1-APPLET 1 IS TRIGGERED BY EVENT MENU SELECTION
        response  = test.envelopeMenuSelection("100101", "");//Help Request not available
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 1: 3-APPLET 1 IS NOT TRIGGERED BY EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response  = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 1: 4-APPLET 1 IS TRIGGERED BY EVENT MENU SELECTION
        response  = test.envelopeMenuSelection("100101", "");//Help Request not available
        test.addResult(response.checkSw("911A"));
        response  = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                                       "77883502 03003902 000A"));
        //TERMINAL RESPONSE WITH PROACTIVE UICC SESSION TERMINATED BY USER
        test.terminalResponse("81030140 01820282 81830110");

        //***TEST CASE 1: 8-APPLET 1 IS NOT TRIGGERED BY EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response  = test.envelopeEventDownloadChannelStatus("38020100");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 1: 9-APPLET 1 IS TRIGGERED BY EVENT MENU SELECTION
        response  = test.envelopeMenuSelection("100101", "");//Help Request not available
        test.addResult(response.checkSw("911A"));
        //APPLET 1 BUILD A PROACTIVE COMMAND OPEN CHANNEL
        response  = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                                       "77883502 03003902 000A"));
        //SUCCESSFUL TERMINAL RESPONSE
        response  = test.terminalResponse("81030140 01820282 81830100 38028100" +
                                          "35020300 3902000A");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 2: 1-APPLET 1 IS TRIGGERED BY EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response  = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 3: 1-APPLET 1 IS TRIGGERED BY EVENT MENU SELECTION
        response  = test.envelopeMenuSelection("100101", "");//Help Request not available
        test.addResult(response.checkSw("911A"));
        //APPLET 1 BUILD A PROACTIVE COMMAND OPEN CHANNEL
        response  = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400282 02818206 05815566" +
                                       "77883502 03003902 000A"));
        //TERMINAL RESPONSE WITH PARTIAL COMPREHENSION
        response  = test.terminalResponse("81030140 02820282 81830101 38028200" +                                           
                                          "35020300 3902000A");
        test.addResult(response.checkSw("910B"));
        //APPLET 1 BUILD A PROACTIVE COMMAND CLOSE CHANNEL
        response  = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028122"));
        //UNSUCCESSFUL TERMINAL RESPONSE: TERMINAL CURRENTLY UNABLE TO PERFORM THE COMMAND
        response  = test.terminalResponse("81030141 00820282 81830120");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 3: 4-APPLET 1 IS TRIGGERED BY EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response  = test.envelopeEventDownloadChannelStatus("38028200");
        test.addResult(response.checkSw("910B"));
        //APPLET 1 BUILD A PROACTIVE COMMAND CLOSE CHANNEL
        response  = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028122"));
        //TERMINAL RESPONSE WITH MISSING INFORMATION
        response  = test.terminalResponse("81030141 00820282 81830102");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 4: 4-APPLET 1 IS NOT TRIGGERED BY EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response  = test.envelopeEventDownloadChannelStatus("38028200");
        test.addResult(response.checkSw("9000"));

        //***TEST CASE 5: 1-APPLET 1 IS TRIGGERED BY EVENT MENU SELECTION
        response  = test.envelopeMenuSelection("100101", "");//Help Request not available
        test.addResult(response.checkSw("911A"));
        //APPLET 1 BUILD A PROACTIVE COMMAND OPEN CHANNEL
        response  = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                                       "77883502 03003902 000A"));
        //SUCCESSFUL TERMINAL RESPONSE
        test.terminalResponse("81030140 01820282 81830100 38028200" +
                              "35020300 3902000A");
        test.addResult(response.checkSw("9000"));
        test.reset();
        test.terminalProfileSession("09010020 000C0000 00000003 40");
        response  = test.envelopeEventDownloadChannelStatus("38028200");
        test.addResult(response.checkSw("9000"));

        // check results
        response  = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10"+APPLET_AID_1+"0DCCCCCC CCCCCCCC CCCCCCCC CCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();

    }
}