
//-----------------------------------------------------------------------------
//Test_Cre_Mha_Enhd.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_enhd;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;

public class Test_Cre_Mha_Enhd extends UiccTestModel {
    /** relative path of the package */
    private static String            CAP_FILE_PATH       = "uicc/test/catre/cre_mha_enhd";
    /** test applet 1 class AID */
    private static String            CLASS_AID_1         = "A0000000 090005FF FFFFFF89 50010001";
    /** test applet 1 instance aid */
    private static String            APPLET_AID_1        = "A0000000 090005FF FFFFFF89 50010102";
    /** test applet 1 class AID */
    private static String            CLASS_AID_2         = "A0000000 090005FF FFFFFF89 50020001";
    /** test applet 1 instance aid */
    private static String            APPLET_AID_2        = "A0000000 090005FF FFFFFF89 50020102";
    /** */
    private static String            CLASS_AID_3         = "A0000000 090005FF FFFFFF89 50030001";
    /** test applet 1 instance aid */
    private static String            APPLET_AID_3        = "A0000000 090005FF FFFFFF89 50030102";

    /**  */
    private UiccAPITestCardService    test               = null;
    /** contains the response from the executed command */
    private APDUResponse            response             = null;


    /**
     *
     */
    public Test_Cre_Mha_Enhd(){
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    /**
     * Installs the applet, runs the tests and checks the test result.
     */
    public boolean run(){
        initialiseResults();

        // test script
        test.reset();
        test.terminalProfileSession("0301");

        // Install Applet
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "800C" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "08" +   // V Max. number of timers
                            "20" +   // V Maximum text length for a menu entry
                            "02" +   // V Maximum number of menu entries
                            "01010202" + // V Pos./Id. of menu entries
                            "01" +   // V Maximum number of channels
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s)
                            "01" );  // V Maximum number of services

        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                            "800C" + // TLV UICC Toolkit application specific parameters
                            "02" +   // V Priority Level
                            "01" +   // V Max. number of timers
                            "20" +   // V Maximum text length for a menu entry
                            "02" +   // V Maximum number of menu entries
                            "03030404" + // V Pos./Id. of menu entries
                            "00" +   // V Maximum number of channels
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s)
                            "00" );  // V Maximum number of services

        test.reset();

        // test script
        //TC 1 - select MF
        response = test.selectFile("3F00");
        //TC 2 - send Terminal Profile except SET_EVENT_LIST,POLL_INTERVALL,SETUP_IDLE_MODE_TEXT,SET_UP_MENU
        response = test.terminalProfileSession("FFFFDFD FFEFF1FEF FF0000FF FF9FFFEF" +
                                               "03FF000 0007FE300 01");
        //TC 3
        response = test.terminalProfileSession("FFFFFFF FFEFF1FFF FF0000FF FF9FFFEF" +
                                               "03FF000 0007FE300 01");
        response = test.envelopeMenuSelection("900102","9500");
        addResult(response.checkSw("9000"));
        //TC 4
        response = test.envelopeMenuSelection("900101","");
        addResult(response.checkSw("9000"));
        //TC 5
        response = test.envelopeTimerExpiration("A40101");
        //TC 6
        response = test.envelopeCallControlByNAA();
        addResult(response.checkSw("9000"));
        //TC 7
        response = test.envelopeEventDownloadMTCall();
        addResult(response.checkSw("9000"));
        //TC 8
        response = test.envelopeEventDownloadCallConnected();
        addResult(response.checkSw("9000"));
        //TC 9
        response = test.envelopeEventDownloadCallDisconnected();
        addResult(response.checkSw("9000"));
        //TC 10
        response = test.envelopeEventDownloadLocationStatus();
        addResult(response.checkSw("9000"));
        //TC 11
        response = test.envelopeEventDownloadUserActivity();
        addResult(response.checkSw("9000"));
        //TC 12
        response = test.envelopeEventDownloadIdleScreenAvailable();
        addResult(response.checkSw("9000"));
        //TC 13
        response = test.envelopeEventDownloadCardReaderStatus();
        addResult(response.checkSw("9000"));
        //TC 14
        response = test.envelopeEventDownloadLanguageSelection();
        addResult(response.checkSw("9000"));
        //TC 15
        response = test.envelopeEventDownloadBrowserTermination();
        addResult(response.checkSw("9000"));
        //TC 16
        response = test.status("00","0C","00");
        addResult(response.checkSw("911A"));
        //TC 17
        response = test.fetch("1A");
        response = test.terminalResponse("81030140 01820282 8183010038 0281003502 " +
                                         "03003902 000A");
        addResult(response.checkSw("9000"));
        response = test.envelopeEventDownloadDataAvailable("B8028100");
        addResult(response.checkSw("9000"));
        //TC 18
        response = test.envelopeEventDownloadChannelStatus("B8028100");
        addResult(response.checkSw("9000"));
        //TC 19
        response = test.unrecognizedEnvelope();
        response = test.fetch("10");//fetch declare service
        addResult(response.checkSw("9000"));
        //get the service record tlv from declare service command
        String servrectlv = getServiceRecordTLV(response.getData());
        response = test.terminalResponse("81030147 0082028281 830100");
        addResult(response.checkSw("9000"));
        //TC 20
        response = test.envelopeEventDownloadLocalConnection("C1040000FFFF");
        addResult(response.checkSw("9000"));
        //TC 21
        response = test.envelopeEventDownloadAccessTechnologyChange();
        addResult(response.checkSw("9000"));
        //TC 22
        response = test.envelopeEventDownloadDisplayParametersChanged();
        addResult(response.checkSw("9000"));
        //TC 23
        //envelope handler availability with
        //event proactive handler available
        //TC 24 APPLICATION DESELECT
        response = test.selectApplication ("A0000000 090005FF FFFFFF89 E0000002");
        response = test.sendApdu ("00A4044C 10A00000 00090005 FFFFFFFF 89E00000 02");
        addResult(response.checkSw("9000"));
        //TC 25
        response = test.envelopeEventDownloadNetworkSearchModeChange();
        addResult(response.checkSw("9000"));
        //TC 26
        response = test.envelopeEventDownloadBrowsingStatus();
        addResult(response.checkSw("9000"));
        //TC 27
        test.reset();
        response = test.terminalProfileSession("FFFFFFF FFEFF1FFF FF0000FF FF9FFFEF" +
                                               "03FF000 0007FE300 01");
        // Install Applet
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                            "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "00" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries
                            "00" +   // V Maximum number of channels
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s)
                            "00" );  // V Maximum number of services
        response = test.selectApplication(APPLET_AID_3);
        addResult(response.checkSw("9000"));
        //TC 28
        // reset, because Applet3 is now selected on channel 0, which we need to use for toolkit activity
        test.reset();
        response = test.terminalProfileSession("FFFFFFF FFEFF1FFF FF0000FF FF9FFFEF" +
            "03FF000 0007FE300 01");
        response = test.envelopeEventDownloadFramesInformationChanged();
        addResult(response.checkSw("9000"));
        response = test.envelopeEventDownloadHCIConnectivity();
        addResult(response.checkSw("9000"));

        //CHECK TEST RESULT
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" +APPLET_AID_1 +
                                        "1CCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"+
                                        "CCCCCCCC CCCCCCCC CCCCCC CCCC"));

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" +APPLET_AID_2 +
                                        "15CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"+
                                        "CCCCCCCC CCCC"));

        response = test.selectApplication(APPLET_AID_3);
        addResult(response.checkData("10" +APPLET_AID_3 + "01CC"));

        //  delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }

    /**
     * Parse the service record TLV from a proactive declare service command.
     * @param declareServiceCommand proactive declare service command
     * @return service record TLV service record TLV
     */
   public String getServiceRecordTLV(String declareServiceCommand){
        String temp = declareServiceCommand.replaceAll(" ",""); //don't bother with formatted strings
        if (temp.length()>22){
            temp=temp.substring(22);//cut off BER TLV, len command details and  dev id
            int len = Integer.valueOf(temp.substring(2,4),16).intValue();
            if (len==temp.length()){
                return temp;
            }
            else{
                // suppress UICC/terminal interface TLV
                return temp.substring(0,(Integer.valueOf(temp.substring(2,4),16).intValue())*2+4);
            }
        }
        else return "";

    }

}