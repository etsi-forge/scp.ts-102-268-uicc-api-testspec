
//-----------------------------------------------------------------------------
//Test_Cre_Mha_Erhd.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_erhd;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;

public class Test_Cre_Mha_Erhd extends UiccTestModel {
	/** relative path of the package */
	private static String			CAP_FILE_PATH		= "uicc/test/catre/cre_mha_erhd";
	/** test applet 1 class AID */
	private static String			CLASS_AID_1			= "A0000000 090005FF FFFFFF89 50010001";
	/** test applet 1 instance aid */
	private static String			APPLET_AID_1		= "A0000000 090005FF FFFFFF89 50010102";
	/** test applet 1 class AID */
	private static String			CLASS_AID_2			= "A0000000 090005FF FFFFFF89 50020001";
	/** test applet 1 instance aid */
	private static String			APPLET_AID_2		= "A0000000 090005FF FFFFFF89 50020102";
	/** */
	private static String			CLASS_AID_3			= "A0000000 090005FF FFFFFF89 50030001";
	/** test applet 1 instance aid */
	private static String			APPLET_AID_3		= "A0000000 090005FF FFFFFF89 50030102";
	/**  */
	private UiccAPITestCardService	test				= null;
	/** contains the response from the executed command */
	private APDUResponse			response			= null;
	/** stores the test result */
	private boolean					testresult			= false;

	/**
	 *
	 */
	public Test_Cre_Mha_Erhd(){
		test = UiccAPITestCardService.getTheUiccTestCardService();
	}
	/**
	 * Installs the applet, runs the tests and checks the test result.
	 */
	public boolean run(){
		// test script
        test.reset();
        test.terminalProfileSession("16FF");
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
        //response = test.selectFile("3F02");
        response = test.selectFile("3F00");
        //TC 2 - send Terminal Profile except SET_EVENT_LIST,POLL_INTERVALL,SETUP_IDLE_MODE_TEXT,SET_UP_MENU
        response = test.terminalProfileSession("FFFFDFDF FEFF1FEF FF0000FF FF9FFFEF" +
                                               "03FF0000 007FE300 01");
        //TC 3 (TP with all facilities except SET_UP_EVENT_LIST)
        response = test.terminalProfileSession("FFFFFFFF FEFFFFFF FFFFFFFF FFFFFFFF " +
        		                               "FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF " +
        		                               "FFFF");
        response = test.envelopeMenuSelection("900102","9500");
        testresult = response.checkSw("9000");
        //TC 4
        response = test.envelopeMenuSelection("900101","");
        testresult &= response.checkSw("9000");
        //TC 5
        response = test.envelopeTimerExpiration("A40101");
        //TC 6
        response = test.envelopeEventDownloadMTCall();
        testresult &= response.checkSw("9000");
        //TC 7
        response = test.envelopeEventDownloadCallConnected();
        testresult &= response.checkSw("9000");
        //TC 8
        response = test.envelopeEventDownloadCallDisconnected();
        testresult &= response.checkSw("9000");
        //TC 9
        response = test.envelopeEventDownloadLocationStatus();
        testresult &= response.checkSw("9000");
        //TC 10
        response = test.envelopeEventDownloadUserActivity();
        testresult &= response.checkSw("9000");
        //TC 11
        response = test.envelopeEventDownloadIdleScreenAvailable();
        testresult &= response.checkSw("9000");
        //TC 12
        response = test.envelopeEventDownloadCardReaderStatus();
        testresult &= response.checkSw("9000");
        //TC 13
        response = test.envelopeEventDownloadLanguageSelection();
        testresult &= response.checkSw("9000");
        //TC 14
        response = test.envelopeEventDownloadBrowserTermination();
        testresult &= response.checkSw("9000");
        //TC 15
        response = test.status("00","0C","00");
        testresult &= response.checkSw("911A");
        //TC 16
        response = test.fetch("1A");
        response = test.terminalResponse("81030140 01820282 8183010038 0281003502 " +
        		                         "03003902 000A");
        testresult &= response.checkSw("9000");
        response = test.envelopeEventDownloadDataAvailable("B8028100");//channel id 1
        testresult &= response.checkSw("9000");
        //TC 17
        response = test.envelopeEventDownloadChannelStatus("B8028100");
        testresult &= response.checkSw("9000");
        //TC 18
        response = test.envelopeCallControlByNAA();
        testresult &= response.checkSw("9000");
        response = test.envelopeCallControlByNAA();
        testresult &= response.checkSw("9110");
        response = test.fetch("10");
        testresult &= response.checkData("D00E8103 01218082 0281028D 03040000");
        response   = test.terminalResponse("81030110 00820282 81830100");
        testresult &= response.checkSw("9000");
        //TC 19
        response = test.unrecognizedEnvelope();
        testresult &= response.checkSw("9000");
        response = test.unrecognizedEnvelope();
        testresult &= response.checkSw("9111");
        response = test.fetch("11");
        testresult &= response.checkData("D00F8103 01218082 0281028D 0404010203");
        response   = test.terminalResponse("81030110 00820282 81830100");
        testresult &= response.checkSw("9000");
        //TC 20
        response = test.unrecognizedEnvelope();
        testresult &= response.checkSw("9114");
        response = test.envelopeCallControlByNAA();
        testresult &= response.checkSw("9114");
        response = test.fetch("14");
        testresult &= response.checkData("D0128103 01218082 0281028D 07045445 58542031");
        response = test.terminalResponse("81030121 80820282 81030100");
        testresult &= response.checkSw("9000");
        //TC 21
        response = test.unrecognizedEnvelope();
        testresult &= response.checkSw("9000");
        //TC 22
        response = test.envelopeEventDownloadAccessTechnologyChange();
        testresult &= response.checkSw("9000");
        //TC 23
        response = test.envelopeEventDownloadDisplayParametersChanged();
        testresult &= response.checkSw("9110");
        //TC 24
        //fetch declare service
        response = test.fetch("10");//fetch declare service
        //get the service record tlv from declare service command
        String servrectlv = getServiceRecordTLV(response.getData());
        response = test.terminalResponse("81030147 0082028281 830100");
        testresult &= response.checkSw("9000");
        //TC 25
        response = test.envelopeEventDownloadLocalConnection("C1040000FFFF");
        testresult &= response.checkSw("9000");
        //TC 26
        response = test.selectApplication ("A0000000 090005FF FFFFFF89 E0000002");
        response = test.sendApdu ("00A4044C 10A00000 00090005 FFFFFFFF 89E00000 02");
        testresult &= response.checkSw("9000");
   	    //TC 27
   	 	response = test.envelopeEventDownloadNetworkSearchModeChange();
	    testresult &= response.checkSw("9000");
   	 	//TC 28
   	 	response = test.envelopeEventDownloadBrowsingStatus();
   	    testresult &= response.checkSw("9000");
        //TC 27
        test.reset();
        response = test.terminalProfileSession("13");

        //test.loadPackage(CAP_FILE_PATH);
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

//        test.reset();
//        response = test.terminalProfileSession("13");


   	    //check results of the tests
        //test.reset();
   		response = test.selectApplication(APPLET_AID_1);
   		testresult &=  response.checkData("10" +APPLET_AID_1 +
      									  "1CCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"+
      									  "CCCCCCCC CCCCCCCC CCCCCCCC CC");

   		response = test.selectApplication(APPLET_AID_2);
   		testresult &=  response.checkData("10" +APPLET_AID_2 +
   										  "02CCCC");

   		response = test.selectApplication(APPLET_AID_3);
   		testresult &=  response.checkData("10" +APPLET_AID_3 + "01CC");

   		//  delete applet and package
		test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
		test.deleteApplet(APPLET_AID_1);
		test.deleteApplet(APPLET_AID_2);
		test.deleteApplet(APPLET_AID_3);
		test.deletePackage(CAP_FILE_PATH);

    	return testresult;
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