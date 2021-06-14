//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_enhd;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Hin_Enhd extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_hin_enhd";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";

    static final String MF            = "3F00";
    static final String ADF           = "7FFF";
    static final String FID_DF_TEST   = "7F4A";
    static final String FID_EF_TARU   = "6F03";
    static final String FID_EF_LARU   = "6F0C";

    static String declareService;
    static String serviceRecordTLV;     
      
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Hin_Enhd() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        initialiseResults();
        
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
  
        test.reset();       
        test.terminalProfileSession("A8970121 FEEF0081 010000FF FF");                                  
        // -----------------------------------------------------------------------
        // Test Case 1 : Applet initialization and Envelope Handler integrity 
        //               checks with EVENT_MENU_SELECTION_HELP_REQUEST
        // -----------------------------------------------------------------------               
     
        //event EVENT_MENU_SELECTION_HELP_REQUEST
        response = test.envelopeMenuSelection("100102", "1500");  
        
        //Open Channel
        addResult(response.checkSw("911A"));
        response  = test.fetch("1A");
        addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                "77883502 03003902 000A"));
        response = test.terminalResponse("81030140 01820282 81830100 38028100" +
                "35020300 3902000A");
        
        //Declare Service
        addResult(response.checkSw("9110"));
        response  = test.fetch("10");
        declareService = response.getData();  
        serviceRecordTLV =  declareService.substring(22,32);  //to get ServiceId      
        addResult(response.checkData("D00E8103 01470082 028182" + serviceRecordTLV));
        response = test.terminalResponse("81030147 00820282 81830100");        
            
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");      
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();        
        test.terminalResponse("81030121 80820282 81030100");      

        //event EVENT_MENU_SELECTION
        response = test.envelopeMenuSelection("100101", "");
                
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();         
        response = test.terminalResponse("81030121 80820282 81030100");  
  
        //event EVENT_TIMER_EXPIRATION
        response = test.envelopeTimerExpiration("240101");
        
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_CALL_CONTROL_BY_NAA
        response = test.envelopeCallControlByNAA();  
        
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  
        
        //event EVENT_EVENT_DOWNLOAD_MT_CALL
        response = test.envelopeEventDownloadMTCall();
                
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  
        
        //event EVENT_EVENT_DOWNLOAD_CALL_CONNECTED
        response = test.envelopeEventDownloadCallConnected();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED
        response = test.envelopeEventDownloadCallDisconnected();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_LOCATION_STATUS
        response = test.envelopeEventDownloadLocationStatus();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
        response = test.envelopeEventDownloadUserActivity();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE
        response = test.envelopeEventDownloadIdleScreenAvailable();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
        response = test.envelopeEventDownloadCardReaderStatus();
           
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_UNRECOGNIZED_ENVELOPE
        response = test.unrecognizedEnvelope();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION
        response = test.envelopeEventDownloadLanguageSelection();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION
        response = test.envelopeEventDownloadBrowserTermination();

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE
        response = test.envelopeEventDownloadDataAvailable("38028100");

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        response = test.envelopeEventDownloadChannelStatus("38028100");

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE
        response = test.envelopeEventDownloadAccessTechnologyChange();
 
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED
        response = test.envelopeEventDownloadDisplayParametersChanged();
 
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
        response = test.envelopeEventDownloadLocalConnection(serviceRecordTLV);

        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
        response = test.envelopeEventDownloadNetworkSearchModeChange();
 
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EVENT_BROWSING_STATUS
        response = test.envelopeEventDownloadBrowsingStatus();
 
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  


        //event EVENT_EXTERNAL_FILE_UPDATE UICC
        test.selectFile(MF);
        test.selectFile(FID_DF_TEST);
        test.selectFile(FID_EF_TARU);//EF TARU
        response = test.updateBinary("0000", "FFFFFF");    
 
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  

        //event EVENT_EXTERNAL_FILE_UPDATE ADF
        test.selectApplication("A0000000090005FFFFFFFF89E0000002");
        test.selectFile(ADF);
        test.selectFile(FID_DF_TEST);
        test.selectFile(FID_EF_LARU);//EF LARU
        response = test.updateRecord("01", "04", "55555555");    
 
        //Display Text
        addResult(response.checkSw("9114"));
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01218082 0281028D 07045445" +
                                     "58542031"));
        response = test.envelopeCallControlByNAA();                 
        response = test.terminalResponse("81030121 80820282 81030100");  


      
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 +
                                     "17CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC CCCCCCCC"));

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
        
        return getOverallResult();
    }
}   
