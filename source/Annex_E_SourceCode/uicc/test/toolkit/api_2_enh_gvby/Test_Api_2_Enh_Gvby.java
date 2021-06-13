//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Gvby.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gvby;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;


/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Gvby
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Gvby extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_enh_gvby";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Gvby() {
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
        for(byte i = 0; i <= (byte)8; i++ )
        {        
                /* 1: Send Unrecognized Envelope with Tag 02, length 02
                                                      Tag 06, length 06
                                                      Tag 33, length C8.
                 */
                response = test.sendApdu( "80 C2 00 00 DA"
                                        + "01 81 D7"
                                        + "02 02 83 81"
                                        + "06 06"
                                        + "01020304 0506"
                                        + "33 81 C8"
                                        + "01020304 05060708 090A0B0C 0D0E0F10"
                                        + "11121314 15161718 191A1B1C 1D1E1F20"
                                        + "21222324 25262728 292A2B2C 2D2E2F30"
                                        + "31323334 35363738 393A3B3C 3D3E3F40"
                                        + "41424344 45464748 494A4B4C 4D4E4F50"
                                        + "51525354 55565758 595A5B5C 5D5E5F60"
                                        + "61626364 65666768 696A6B6C 6D6E6F70"
                                        + "71727374 75767778 797A7B7C 7D7E7F80"
                                        + "81828384 85868788 898A8B8C 8D8E8F90"
                                        + "91929394 95969798 999A9B9C 9D9E9FA0"
                                        + "A1A2A3A4 A5A6A7A8 A9AAABAC ADAEAFB0"
                                        + "B1B2B3B4 B5B6B7B8 B9BABBBC BDBEBFC0"
                                        + "C1C2C3C4 C5C6C7C8 ");
                if(i == 0)
                        result = response.checkSw("90 00");
                else
                        result &= response.checkSw("90 00");
        }
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "08CCCCCC CCCCCCCC CC");
        
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
