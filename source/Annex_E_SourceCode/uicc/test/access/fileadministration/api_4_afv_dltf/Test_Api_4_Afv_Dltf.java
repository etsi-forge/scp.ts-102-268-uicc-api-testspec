/**
 * ETSI TS 102 268: UICC API testing
 * uicc.access.fileadministration package part 4
 * Test source for deleteFile tests
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_dltf;


//-----------------------------------------------------------------------------
//	Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;


public class Test_Api_4_Afv_Dltf extends UiccTestModel {
    static final String CAP_FILE_PATH = "uicc/test/access/fileadministration/api_4_afv_dltf";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 40010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 40010102";
    
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_4_Afv_Dltf() {
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

        
        /** test cases 0, 1, 2, 3, 4, 5, 6
         *  0. Initialization
         *  1. Delete EF
         *  2. Delete EF in ADF1
         *  3. Delete DF and its subtree
         *  4. Delete DF and its subtree in ADF1
         *  5. File Not found
         *  6. Security status not found
         */
        
        test.unrecognizedEnvelope();
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
        addResult(response.checkData("10" + APPLET_AID_1 + "06CCCCCC CCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
