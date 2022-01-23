//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_prhd;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Hin_Prhd extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/catre/cre_hin_prhd";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    
    static final String MF            = "3F00";
    static final String FID_DF_TEST   = "7F4A";
    static final String FID_EF_TARU   = "6F03";

    static String declareService;
    static String serviceRecordTLV;
    
    private UiccAPITestCardService test;
    APDUResponse response;
    
    
    public Test_Cre_Hin_Prhd() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/
        /** Testcase 0                                                       */
        /*********************************************************************/
        
        // Install package
        test.loadPackage(CAP_FILE_PATH);
        
        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                "800C" + // TLV UICC Toolkit application specific parameters
                "01" +   // V Priority Level
                "08" +   // V Max. number of timers
                "0A" +   // V Maximum text length for a menu entry
                "02" +   // V Maximum number of menu entries
                "01" +   // V Id of menu entry 1
                "01" +   // V Position of menu entry 1
                "02" +   // V Id of menu entry 2
                "02" +   // V Position of menu entry 2
                "01" +   // V Maximum number of channels
                "00" +   // LV Minimum Security Level field
                "00" +   // LV TAR Value(s)
                "01" );  // V Maximum number of services
        
        
        // -----------------------------------------------------------------------
        // Test Case 1 : Applet registration and ProactiveResponseHandler obtaining
        // -----------------------------------------------------------------------
        // -----------------------------------------------------------------------
        // Test Case 2 : The ProactiveResponseHandler remains unchanged after
        //               send() method invocation until next send() method invocation
        // -----------------------------------------------------------------------
        
        //event EVENT_PROFILE_DOWNLOAD
        test.reset();
        response = test.terminalProfile("A8970101 FEEF0081 010000FF FF");
        
        //Open Channel
        test.addResult(response.checkSw("911A"));
        response  = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                "77883502 03003902 000A"));
        response = test.terminalResponse("81030140 01820282 81830100 38028100" +
                "35020300 3902000A");
        
        //Declare Service
        test.addResult(response.checkSw("9110"));
        response  = test.fetch("10");
        declareService = response.getData();  
        serviceRecordTLV =  declareService.substring(22,32);  //to get ServiceId      
        test.addResult(response.checkData("D00E8103 01470082 028182" + serviceRecordTLV));
        response = test.terminalResponse("81030147 00820282 81830100");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        response = test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_PROACTIVE_HANDLER_AVAILABLE
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        response = test.terminalResponse("81030124 00820282 81030100 900101");
                
        //event EVENT_MENU_SELECTION
        response = test.envelopeMenuSelection("100101", "");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_MENU_SELECTION_HELP_REQUEST
        response = test.envelopeMenuSelection("100102", "1500");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_CALL_CONTROL_BY_NAA
        response = test.envelopeCallControlByNAA();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_TIMER_EXPIRATION
        response = test.envelopeTimerExpiration("240101");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_MT_CALL
        response = test.envelopeEventDownloadMTCall();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_CALL_CONNECTED
        response = test.envelopeEventDownloadCallConnected();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED
        response = test.envelopeEventDownloadCallDisconnected();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_LOCATION_STATUS
        response = test.envelopeEventDownloadLocationStatus();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
        response = test.envelopeEventDownloadUserActivity();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE
        response = test.envelopeEventDownloadIdleScreenAvailable();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
        response = test.envelopeEventDownloadCardReaderStatus();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_STATUS_COMMAND
        response = test.status("00", "0C", "00");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION
        response = test.envelopeEventDownloadLanguageSelection();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION
        response = test.envelopeEventDownloadBrowserTermination();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE
        response = test.envelopeEventDownloadDataAvailable("38028100");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response = test.envelopeEventDownloadChannelStatus("38028100");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE
        response = test.envelopeEventDownloadAccessTechnologyChange();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED
        response = test.envelopeEventDownloadDisplayParametersChanged();
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
        response = test.envelopeEventDownloadLocalConnection(serviceRecordTLV);
        //response = test.envelopeEventDownloadLocalConnection("41 03000000");
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
        response = test.envelopeEventDownloadNetworkSearchModeChange();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EVENT_BROWSING_STATUS
        response = test.envelopeEventDownloadBrowsingStatus();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_EXTERNAL_FILE_UPDATE
        test.selectFile(MF);
        test.selectFile(FID_DF_TEST);
        test.selectFile(FID_EF_TARU);
        response = test.updateBinary("0000", "FFFFFF");
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        //event EVENT_UNRECOGNIZED_ENVELOPE
        response = test.unrecognizedEnvelope();
        
        //Display Text
        test.addResult(response.checkSw("9114"));
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                "58542031"));
        response = test.terminalResponse("81030121 80820282 81030100");
        
        //Select Item
        test.addResult(response.checkSw("910E"));
        response = test.fetch("0E");
        test.addResult(response.checkData("D00C8103 01240082 02818290 0101"));
        test.terminalResponse("81030124 00820282 81030100 900101");
        
        
        
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 +
                "19CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC"));
        
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/
        
        // delete Applet
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return test.getOverallResult();
    }
}
