//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Gvle.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gvle;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;


/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Gvle
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Gvle extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_enh_gvle";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Gvle() {
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
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        for(byte i = 0; i <= (byte)4; i++ )
        {        
                /* 1: Send Unrecognized Envelope with Tag 82, length 02
                                                      Tag 06, length 05
                                                      Tag 0B, length 24
                                                      Tag 33, length C8.
                 */
                response = test.sendApdu( "80 C2 00 00 FF"
                                        + "01 81 FC"
                                        + "82 02 83 81"
                                        + "06 05"
                                        + "01020304 05"
                                        + "8B 24"
                                        + "01020304 05060708 090A0B0C 0D0E0F10"
                                        + "01020304 05060708 090A0B0C 0D0E0F10"
                                        + "01020304" 
                                        + "33 81 C8"
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
                                        + "01020304 05060708");

                if(i == 0)
                        result = response.checkSw("9000");
                else
                        result &= response.checkSw("9000");
        
        }
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "04CCCCCC CC");
        
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
