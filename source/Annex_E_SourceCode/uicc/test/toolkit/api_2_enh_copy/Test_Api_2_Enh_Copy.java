//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Copy_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_copy;

import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_enh_copy
 *
 * @version 0.0.1 - 2 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Copy extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_enh_copy";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Copy() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {

        APDUResponse data = null;
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
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // Test cases 1
        response = test.unrecognizedEnvelope();
            addResult(response.checkSw("90 00"));
        
        // Test cases 1 to 7
        for(byte i = 1; i < 7; i++)
        {
                // 1: Send Unrecognized Envelope
                response = test.unrecognizedEnvelope();
                addResult(response.checkSw("90 00"));
        }
        
        // Test case 8
        // Send Unrecognized Envelope with BER TLV length set to 47
        response = test.sendApdu(  "80 C2 00 00 31"
                                 + "01 2F"
                                 + "82 02 83 81"
                                 + "02 29"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 09");
                 
        // Test case 9
        // Send Unrecognized Envelope with BER TLV length set to 47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 31"
                                 + "01 2F"
                                 + "82 02 83 81"
                                 + "02 29"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 09");

        // Test case 10
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 31"
                                 + "01 2F"
                                 + "82 02 83 81"
                                 + "02 29"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 09");
        
        // Test case 11
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 31"
                                 + "01 2F"
                                 + "82 02 83 81"
                                 + "02 29"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 09");

        // Test case 12
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 FF"
                                 + "01 81 FC"
                                 + "82 02 83 81"
                                 + "02 81 F5"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                                                 + "01020304 05");

        // Test case 13
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 FF"
                                 + "01 81 FC"
                                 + "82 02 83 81"
                                 + "02 81 F5"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05");

        // Test case 14
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 FF"
                                 + "01 81 FC"
                                 + "82 02 83 81"
                                 + "02 81 F5"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05");

        // Test case 15
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 FF"
                                 + "01 81 FC"
                                 + "82 02 83 81"
                                 + "02 81 F5"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05");

        // Test case 16
        // Send Unrecognized Envelope with BER TLV set to 0x47 and compare the buffer
        response = test.sendApdu(  "80 C2 00 00 FF"
                                 + "01 81 FC"
                                 + "82 02 83 81"
                                 + "02 81 F5"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05060708 090A0B0C 0D0E0F10"
                                 + "01020304 05");

        
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1
                                   + "10CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC CC"));
        
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
        
        
        return getOverallResult();
    }
}
