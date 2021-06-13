/**
 * ETSI TS 102 268: UICC API testing
 * uicc.access.fileadministration package part 4
 * Test source for getTheAdminFileView tests
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afb_gtaf_bsbb;


//-----------------------------------------------------------------------------
//	Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;


public class Test_Api_4_Afb_Gtaf_Bsbb extends UiccTestModel {
    static final String CAP_FILE_PATH = "uicc/test/access/fileadministration/api_4_afb_gtaf_bsbb";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 40010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 40010102";
    static final String AID_ADF_2     = "A0000000 090005FF FFFFFF89 D0000002";
    
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_4_Afb_Gtaf_Bsbb() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {
        boolean result = true;

        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        /** Test case 1
         *  Method returns null if called before register() 
         */
        
        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
            "800A" + // TLV UICC Toolkit application specific parameters
            "01" +   // V Priority Level
            "00" +   // V Max. number of timers
            "0A" +   // V Maximum text length for a menu entry
            "01" +   // V Maximum number of menu entries
            "01" +   // V Id of menu entry 1
            "01" +   // V Position of menu entry 1
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

        
        /** Test case 1
         *  cf. installation
         *  subpart 2
         */

        response = test.envelopeMenuSelection("100101", "");
        result = response.checkSw("9000");
        
        /** Test case 2
         *  Normal execution 
         */

        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");         
        
        test.reset();
        test.terminalProfileSession("0101");

        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");         

        test.reset();
        test.terminalProfileSession("0101");

        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");         

        response = test.selectApplication(APPLET_AID_1);
        response = test.selectApplication(AID_ADF_2);
        response = test.selectApplication(APPLET_AID_1);

        
        test.reset();
        test.terminalProfileSession("0101");
        
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");         

        
        /** Test case 3
         *  FileView context independency 
         */
        
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");   


        /** Test case 4
         *  ILLEGAL_TRANSIENT SystemException 
         */

        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");   

        
        /** Test case 5
         *  NO_TRANSIENT_SPACE Exception with CLEAR_ON_RESET FileView object 
         */
       
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");  
       
       
        /** Test case 6
         *  NO_TRANSIENT_SPACE Exception with CLEAR_ON_DESELECT FileView object 
         */
       
        response = test.selectApplication(APPLET_AID_1);
        response = test.selectApplication(AID_ADF_2);

        test.reset();
        test.terminalProfileSession("0101");
       
       
        /** Test case 7
         *  ILLEGAL_VALUE SystemException 
         */
       
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");  

        /** Test case 8
         *  NullPointerException 
         */
       
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");  
        
        
        /** Testcase 9
         *  ArrayIndexOutOfBoundsException  
         */
        
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");  

        
        /** Testcase 10
         *  SystemException.ILLEGAL_VALUE  
         */

        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");  

        
        /** 
         * Check Results and delete packages
         */
        
        test.reset();
        test.terminalProfileSession("0101");
       
        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "0ACCCCCC CCCCCCCC CCCCCC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return result;
    }
}
