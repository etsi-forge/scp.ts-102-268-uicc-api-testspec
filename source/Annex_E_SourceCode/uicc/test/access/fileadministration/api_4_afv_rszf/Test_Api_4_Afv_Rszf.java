/**
 * ETSI TS 102 268: UICC API testing
 * uicc.access.fileadministration package part 4
 * Test source for resizeFile tests
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_rszf;


//-----------------------------------------------------------------------------
//	Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;


public class Test_Api_4_Afv_Rszf extends UiccTestModel {
    static final String CAP_FILE_PATH = "uicc/test/access/fileadministration/api_4_afv_rszf";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 40010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 40010102";
    static final String AID_ADF_1     = "A0000000 090005FF FFFFFF89 E0000002";
    static final String DF_TELECOM    = "7F10";
    static final String MF      = "3F00";
    static final String DF_TEST = "7F4A";
    static final String EF_TNR  = "6F01";
    static final String EF_TNU  = "6F02";
    static final String EF_TARU = "6F03";
    static final String EF_CNR  = "6F04";
    static final String EF_CARU = "6F09";
    static final String EF_LNU  = "6F0B";
    static final String EF_LARU = "6F0C";
    static final String EF_TDAC = "6F0F";
    static final String EF_LUPC = "6F18";
    static final String EF_NOSH = "6F1B";
    static final String EF_RFU0 = "6F29";
    static final String EF_RFU1 = "6F2A";
    static final String EF_RFU2 = "6F2B";
    static final String DF_RFU1 = "5F01";
    static final String DF_RFU2 = "5F02";
    static final String DF_RFU3 = "5F03";

    
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_4_Afv_Rszf() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {
        initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
            "8008" + // TLV UICC Toolkit application specific parameters
            "01" +   // V Priority Level
            "00" +   // V Max. number of timers
            "0A" +   // V Maximum text length for a menu entry
            "00" +   // V Maximum number of menu entries
            "00" +   // V Maximum number of channels 
            "00" +   // LV Minimum Security Level field
            "00" +   // LV TAR Value(s) 
            "00" +   // V Maximum number of services
            "8118" + // TLV UICC Access Application specific parameters
            "00" +   // LV UICC file system AID
            "0100" + // LV UICC file system access aomain
            "00" +   // LV UICC file system access domain DAP
            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 AID
            "0100" + // LV ADF1 access domain
            "00" +   // LV ADF1 access domain DAP
            "8218" + // TLV UICC Access application specific parameters
            "00" +   // LV UICC File System AID field
            "0100" + // LV Access Domain for UICC file system = Full Access
            "00" +   // LV Access Domain DAP field
            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
            "0100" + // LV Access Domain for ADF1 file system = Full Access
            "00");   // LV Access Domain DAP field            

        test.reset();
        test.terminalProfileSession("0101");

        
        /** test case 1
         *  Resize a transparent EF
         */
        
        test.unrecognizedEnvelope();
        
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        response = test.selectFile(EF_TARU);
        
        addResult(response.getData().indexOf("8002000A") != -1);
        
        test.resizeFile(EF_TARU, "0104");
        
                
        /** test case 2
         *  Resize a Linear Fixed EF
         */
        
        test.unrecognizedEnvelope();

        test.selectFile(MF);
        test.selectFile(DF_TEST);
        response = test.selectFile(EF_LARU);

        addResult(response.getData().indexOf("80020010") != -1);

        test.resizeFile(EF_LARU, "08");

        
        /** test cases 3, 4, 5, 6, 7, 8, 9
         *  3. Call resizeFile with a null viewHandler
         *  4. Call resizeFile with incorrect parameters
         *  5. File not found
         *  6. Security status not satisfied
         *  7. Command incompatible
         *  8. Invalidated data
         */
        
        test.unrecognizedEnvelope();
        test.unrecognizedEnvelope();
        test.unrecognizedEnvelope();
        test.unrecognizedEnvelope();
        test.unrecognizedEnvelope();
        test.unrecognizedEnvelope();

        
        /** 
         * Check Results and delete packages
         */
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "08CCCCCC CCCCCCCC CC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
