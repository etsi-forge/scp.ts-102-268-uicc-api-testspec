//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Gvsh.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gvsh;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gvsh
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Gvsh extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_gvsh";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Gvsh() {
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
                           "01" +   // V Maximum number of channels 
                           "00" +   // LV Minimum Security Level field
                           "00" +   // LV TAR Value(s) 
                           "00" +   // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = ALWAYS
                           "00" );  // LV Access Domain DAP field       
        

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/   
        /** Testcase 1 to 5                                                  */   
        /*********************************************************************/   
                                                                                  
        response = test.unrecognizedEnvelope();                                   
        result = response.checkSw("9116");                                        
        
        // Fetch the GET INPUT proactive command
        response = test.fetch("16");
        result &= response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF");
        // Terminal Response (Text String length = 7Eh)
        response = test.terminalResponse("81030123 00020282 81030100 0D7F0401"
                                       + "02030405 06070809 0A0B0C0D 0E0F1011"
                                       + "12131415 16171819 1A1B1C1D 1E1F2021"
                                       + "22232425 26272829 2A2B2C2D 2E2F3031"
                                       + "32333435 36373839 3A3B3C3D 3E3F4041"
                                       + "42434445 46474849 4A4B4C4D 4E4F5051"
                                       + "52535455 56575859 5A5B5C5D 5E5F6061"
                                       + "62636465 66676869 6A6B6C6D 6E6F7071"
                                       + "72737475 76777879 7A7B7C7D 7E");
        result &= response.checkSw("9116");

        /*********************************************************************/   
        /** Testcase 6 to 8                                                  */   
        /*********************************************************************/   
                                                                                  
        // Fetch the GET INPUT proactive command
        response = test.fetch("16");
        result &= response.checkData("D0148103 01230082 0281828D 05045465"
                                   + "78749102 00FF");
        // Terminal Response (Text String length = EFh)
        response = test.terminalResponse("81030123 00020282 81030100 0D81F004"
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
                                       + "C1C2C3C4 C5C6C7C8 C9CACBCC CDCECFD0"
                                       + "D1D2D3D4 D5D6D7D8 D9DADBDC DDDEDFE0"
                                       + "E1E2E3E4 E5E6E7E8 E9EAEBEC EDEEEF");
        result &= response.checkSw("9000");

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
