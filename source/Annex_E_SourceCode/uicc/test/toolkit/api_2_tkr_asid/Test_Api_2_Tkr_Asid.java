/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * allocateServiceIdentifier() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_asid;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Asid extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_asid";
    static final String CLASS_AID_1 =   "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 =  "A0000000 090005FF FFFFFF89 20010102";
    static final String CLASS_AID_2 =   "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_2 =  "A0000000 090005FF FFFFFF89 20020102";
    static final String CLASS_AID_3 =   "A0000000 090005FF FFFFFF89 20030001";
    static final String APPLET_AID_3 =  "A0000000 090005FF FFFFFF89 20030102";
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Asid() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    
    public boolean run() {

        boolean result = true;

        // start test
        test.reset();
        test.terminalProfileSession("0101");

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                          "800A" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0101" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "08"); // V Maximum number of services 

        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                          "800A" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0102" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "04"); // V Maximum number of services 

        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                          "800A" + // TLV UICC Toolkit application specific parameters
                            "01" + // V Priority Level
                            "00" + // V Max. number of timers
                            "0A" + // V Maximum text length for a menu entry
                            "01" + // V Maximum number of menu entries
                          "0103" + // V Pos./Id. of menu entries
                            "00" + // V Maximum number of channels 
                            "00" + // LV Minimum Security Level field
                            "00" + // LV TAR Value(s) 
                            "00"); // V Maximum number of services 

        // test script
        test.reset();
        test.terminalProfileSession("09010000 0040");

        // test case 1: trigger applet1
        response = test.envelopeMenuSelection("100101","");
        
        // fetch 8 DECLARE SERVICE
        for (int i=0; i<8 ;i++) {
            char serviceID;
            String check, data;
            
            result &= response.checkSw("9110");
            response = test.fetch("10");
    
            // check that we received a ADD SERVICE command
            data = response.getData();
            serviceID = data.charAt(data.lastIndexOf("4103000") + 7);
            check = "D00E8103 01470082 02818241 03000X00".replace('X', serviceID);
            result &= response.checkData(check);
            
            response = test.terminalResponse("81030147 00820282 81830100");
        } 

        // test case 2: trigger applet1 with EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION event
        response = test.envelopeEventDownloadLocalConnection("41030000 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030002 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030006 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030003 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030007 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030005 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030001 00");
        result &= response.checkSw("9000");
        response = test.envelopeEventDownloadLocalConnection("41030004 00");
        result &= response.checkSw("9000");

        // test case 3: trigger applet1, applet2 then applet1
        response = test.envelopeMenuSelection("100101","");
        result &= response.checkSw("9000");
        response = test.envelopeMenuSelection("100102","");
        result &= response.checkSw("9000");
        response = test.envelopeMenuSelection("100101","");
        result &= response.checkSw("9000");

        // test case 4: trigger applet3 then applet2
        response = test.envelopeMenuSelection("100103","");
        result &= response.checkSw("9000");
        response = test.envelopeMenuSelection("100102","");
        result &= response.checkSw("9000");

        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "0BCCCCCC CCCCCCCC CCCCCCCC");

        response = test.selectApplication(APPLET_AID_2);
        result &= response.checkData("10" + APPLET_AID_2 + "02CCCC");

        response = test.selectApplication(APPLET_AID_3);
        result &= response.checkData("10" + APPLET_AID_3 + "01CC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}

