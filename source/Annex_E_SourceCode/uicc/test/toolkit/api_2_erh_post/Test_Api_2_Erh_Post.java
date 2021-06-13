//-----------------------------------------------------------------------------
//Test_Api_2_Erh_Post.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_post;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_post
 *
 * @version 0.0.1 - 24 mars 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Erh_Post extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_erh_post";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Erh_Post() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
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
                           "8008" + // TLV UICC Toolkit application specific parameters
                           "01" +   // V Priority Level
                           "00" +   // V Max. number of timers
                           "00" +   // V Maximum text length for a menu entry
                           "00" +   // V Maximum number of menu entries
                           "00" +   // V Maximum number of channels 
                           "00" +   // LV Minimum Security Level field
                           "00" +   // LV TAR Value(s) 
                           "00" +   // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = ALWAYS
                           "00" );  // LV Access Domain DAP field       
        

        /*********************************************************************/
        /** Testcases                                                       */
        /*********************************************************************/
        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // Test case 1
        response = test.unrecognizedEnvelope();
        result = response.checkSw("9000");
        
        // Test case 2
        response = test.unrecognizedEnvelope();
        result &= response.checkData("0181FA01 02030405 06070809 0A0B0C0D" 
                                   + "0E0F1011 12131415 16171819 1A1B1C1D" 
                                   + "1E1F2021 22232425 26272829 2A2B2C2D" 
                                   + "2E2F3031 32333435 36373839 3A3B3C3D" 
                                   + "3E3F4041 42434445 46474849 4A4B4C4D" 
                                   + "4E4F5051 52535455 56575859 5A5B5C5D" 
                                   + "5E5F6061 62636465 66676869 6A6B6C6D" 
                                   + "6E6F7071 72737475 76777879 7A7B7C7D" 
                                   + "7E7F8081 82838485 86878889 8A8B8C8D" 
                                   + "8E8F9091 92939495 96979899 9A9B9C9D" 
                                   + "9E9FA0A1 A2A3A4A5 A6A7A8A9 AAABACAD" 
                                   + "AEAFB0B1 B2B3B4B5 B6B7B8B9 BABBBCBD" 
                                   + "BEBFC0C1 C2C3C4C5 C6C7C8C9 CACBCCCD" 
                                   + "CECFD0D1 D2D3D4D5 D6D7D8D9 DADBDCDD" 
                                   + "DEDFE0E1 E2E3E4E5 E6E7E8E9 EAEBECED" 
                                   + "EEEFF0F1 F2F3F4F5 F6F7F8F9 FA");
        
        result &= response.checkSw("9000");
        
        // Test case 3
        response = test.unrecognizedEnvelope();
        result &= response.checkData("01100102 03040506 0708090A 0B0C0D0E 0F10");
        result &= response.checkSw("9000");
        
        // Test case 4
        response = test.unrecognizedEnvelope();
        result &= response.checkData("01100102 03040506 0708090A 0B0C0D0E 0F10");
        result &= response.checkSw("6200");
        response = test.envelopeEventDownloadUserActivity(); // Dummy command to get the 91XX status word
        result &= response.checkSw("9115");
        response = test.fetch("15");
        result &= response.checkData("D0138103  01218082  0281028D  08044150"
                                   + "504C4554  31");
        test.terminalResponse("81030121  00020282  81030100");

        // Test case 5
        response = test.unrecognizedEnvelope();
        result &= response.checkSw("9115");
        response = test.fetch("15");
        result &= response.checkData("D0138103  01218082  0281028D  08044150"
                                   + "504C4554  31");
        test.terminalResponse("81030121  00020282  81030100");

        // Test case 6
        response = test.unrecognizedEnvelope();
        result &= response.checkSw("9115");
        response = test.fetch("15");
        result &= response.checkData("D0138103  01218082  0281028D  08044150"
                                   + "504C4554  31");
        response = test.terminalResponse("81030121  00020282  81030100");
        result &= response.checkSw("9000");
        
        // Test case 7
        response = test.unrecognizedEnvelope();
        result &= response.checkData("01100102 03040506 0708090A 0B0C0D0E 0F10");
        result &= response.checkSw("6200");
        
        // Test case 8
        response = test.envelopeCallControlByNAA();
        result &= response.checkData("01100102 03040506 0708090A 0B0C0D0E 0F10");
        result &= response.checkSw("9000");
        
        // Test case 9
        response = test.envelopeCallControlByNAA();
        result &= response.checkData("01101112 13141516 1718191A 1B1C1D1E 1F20");
        result &= response.checkSw("9000");
        
        // Test case 10
        response = test.unrecognizedEnvelope();
        result &= response.checkSw("9000");
        
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "0ACCCCCC CCCCCCCC CCCCCC");
        
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // delete applets and package
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return result;
    }
}

