//-----------------------------------------------------------------------------
//  Package Definition
//  Test Area: UICC CAT Runtime Environment Applet Triggering
//  EVENT_EVENT_DOWNLOAD_LOCAL CONNECTION
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_edlc;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Edlc extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_edlc";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static String ServiceRecordTLV    = "4103000000";


    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Edlc() {
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
                            "08");  // V Maximum number of services

        // test script
        test.reset();
        test.terminalProfileSession("01010000 00010000 000000FF 5F");
        //***TEST CASE 1: 1-APPLET IS TRIGGERED
        test.envelopeMenuSelection("100101", "");//Help Request not available
        //***TEST CASE 1: 2-METHOD isEventSet() RETURNS TRUE
        //***TEST CASE 1: 3-APPLET IS NOT TRIGGERED
        test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);
        //***TEST CASE 1: 4-APPLET IS TRIGGERED
        response = test.envelopeMenuSelection("100101", "");//Help Request not available
        //***TEST CASE 1: 5-DECLARE SERVICE ADD COMMAND IS FETCHED
        result    = response.checkSw("9110");
        response  = test.fetch("10");
        result   &= response.checkData("D00E8103 01470082 028182" + ServiceRecordTLV);
        //UNSUCCESFULL TERMINAL RESPONSE with General Result = 0x20
        test.terminalResponse("81030147 00820282 81830120");
        //***TEST CASE 1: 6-APPLET IS NOT TRIGGERED
        test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);
        //***TEST CASE 1: 7-APPLET IS TRIGGERED
        response = test.envelopeMenuSelection("100101", "");//Help Request not available
        //***TEST CASE1: 8-DECLARE SERVICE ADD COMMAND IS FETCHED
        result   &= response.checkSw("9110");
        response  = test.fetch("10");
        result   &= response.checkData("D00E8103 01470082 028182" + ServiceRecordTLV);
        //SUCCESFULL TERMINAL RESPONSE with General Result = 0x00
        test.terminalResponse("81030147 00820282 81830100");
        //***TEST CASE 2: 1-APPLET IS TRIGGERED
        test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);
        //***TEST CASE 3: 1-APPLET IS TRIGGERED
        response = test.envelopeMenuSelection("100101", "");//Help Request not available
        //***TEST CASE 3: 2-DECLARE SERVICE DELETE COMMAND IS FETCHED
        result   &= response.checkSw("9110");
        response  = test.fetch("10");
        result   &= response.checkData("D00E8103 01470182 028182" + ServiceRecordTLV);
        //UNSUCCESFULL TERMINAL RESPONSE with General Result = 0x20
        test.terminalResponse("81030147 01820282 81830120");
        //***TEST CASE 3: 3-APPLET IS TRIGGERED
        response = test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);
        //***TEST CASE 3: 2-DECLARE SERVICE DELETE COMMAND IS FETCHED
        result   &= response.checkSw("9110");
        response  = test.fetch("10");
        result   &= response.checkData("D00E8103 01470182 028182" + ServiceRecordTLV);
        //SUCCESFULL TERMINAL RESPONSE with General Result = 0x00
        test.terminalResponse("81030147 01820282 81830100");
        //***TEST CASE 4: 1-APPLET IS NOT TRIGGERED
        test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);
        //***TEST CASE 5: 1-APPLET IS TRIGGERED
        response = test.envelopeMenuSelection("100101", "");//Help Request not available
        //***TEST CASE 5: 2-DECLARE SERVICE ADD COMMAND IS FETCHED
        result   &= response.checkSw("9110");
        response  = test.fetch("10");
        result   &= response.checkData("D00E8103 01470082 028182" + ServiceRecordTLV);
        //SUCCESFULL TERMINAL RESPONSE with General Result = 0x00
        test.terminalResponse("81030147 00820282 81830100");
        //***TEST CASE 5: 3-APPLET IS TRIGGERED
        test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);
        test.reset();
        test.terminalProfileSession("01010000 00010000 000000FF 5F");
        //***TEST CASE 5: 5-APPLET IS TRIGGERED
        test.envelopeEventDownloadLocalConnection(ServiceRecordTLV);

        // check results
        response  = test.selectApplication(APPLET_AID_1);
        result    = response.checkData("10"+APPLET_AID_1+"12CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                                         "CCCCCC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}
