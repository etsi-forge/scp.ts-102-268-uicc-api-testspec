//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Glen.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_glen;

import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Glen
 *
 * @version 0.0.1 - 2 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Glen extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_enh_glen";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Glen() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
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
        test.installApplet(CAP_FILE_PATH,CLASS_AID_1, APPLET_AID_1, 
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

        // 1: Send Unrecognized Envelope with BER TLV Length set to 31
        response = test.sendApdu( "80 C2 00 00 33"
                                + "01 31"
                                + "82 02 83 81"
                                + "FF 2B"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B");
        
        test.addResult(response.checkSw("90 00"));

        // 2 : Send Unrecognized Envelope with BER TLV Length set to 7F
        response = test.sendApdu( "80 C2 00 00 81"
                                + "01 7F"
                                + "82 02 83 81"
                                + "FF 79"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 09");
        test.addResult(response.checkSw("9000"));

        // 2 : Send Unrecognized Envelope with BER TLV Length set to 80
        response = test.sendApdu( "80 C2 00 00 83"
                                + "01 81 80"
                                + "82 02 83 81"
                                + "FF 7A"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A0B0C 0D0E0F10"
                                + "01020304 05060708 090A");
        test.addResult(response.checkSw("9000"));
        

        // 3 : Send Unrecognized Envelope with BER TLV Length set to FC
        response = test.sendApdu( "80 C2 00 00 FF"
                                + "01 81 FC"
                                + "82 02 83 81"
                                + "FF 81 F5"
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
        test.addResult(response.checkSw("90 00"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1
                                   + "04CCCCCC CC"));
        
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
        
        return test.getOverallResult();
    }
}
