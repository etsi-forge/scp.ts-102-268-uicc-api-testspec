//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Cpai.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cpai;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cpai
 *
 * @version 0.0.1 - 22 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Cpai extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_cpai";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Cpai() {
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
        /** Testcase 1 to 6                                                  */
        /*********************************************************************/
        
        response = test.unrecognizedEnvelope();
        result = response.checkSw("9112");
        
        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        // Terminal response with 11 additional bytes
        response = test.terminalResponse("81030121 00020282 81030C01 01234567"
                                       + "89ABCDEF 012345");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 7 to 9                                                  */
        /*********************************************************************/
        
        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");

        // Terminal response with 5 additional bytes
        response = test.terminalResponse("81030121 00020282 81030601 01234567"
                                       + "89");
        result &= response.checkSw("9112");
        
        /*********************************************************************/
        /** Testcase 10 & 11                                                  */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with 6 additional bytes
        response = test.terminalResponse("81030121 00020282 81030701 ABCDEFFE"
                                       + "DCBA");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 12 & 13                                                  */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with 7 additional bytes
        response = test.terminalResponse("81030121 00020282 81030801 FEDCBA98"
                                       + "765432");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 14 & 15                                                  */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with 8 additional bytes
        response = test.terminalResponse("81030121 00020282 81030901 00112233"
                                       + "44556677");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 16 to 18                                                  */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with F2 additional bytes
        response = test.terminalResponse("81030121 00020282 810381F3 01000102"
                                       + "03040506 0708090A 0B0C0D0E 0F101112"
                                       + "13141516 1718191A 1B1C1D1E 1F202122"
                                       + "23242526 2728292A 2B2C2D2E 2F303132"
                                       + "33343536 3738393A 3B3C3D3E 3F404142"
                                       + "43444546 4748494A 4B4C4D4E 4F505152"
                                       + "53545556 5758595A 5B5C5D5E 5F606162"
                                       + "63646566 6768696A 6B6C6D6E 6F707172"
                                       + "73747576 7778797A 7B7C7D7E 7F808182"
                                       + "83848586 8788898A 8B8C8D8E 8F909192"
                                       + "93949596 9798999A 9B9C9D9E 9FA0A1A2"
                                       + "A3A4A5A6 A7A8A9AA ABACADAE AFB0B1B2"
                                       + "B3B4B5B6 B7B8B9BA BBBCBDBE BFC0C1C2"
                                       + "C3C4C5C6 C7C8C9CA CBCCCDCE CFD0D1D2"
                                       + "D3D4D5D6 D7D8D9DA DBDCDDDE DFE0E1E2"
                                       + "E3E4E5E6 E7E8E9EA EBECEDEE EFF0F1");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 19                                                      */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with 5 additional bytes
        response = test.terminalResponse("81030121 00020282 81030601 00112233"
                                       + "44");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 20                                                      */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with 5 additional bytes
        response = test.terminalResponse("81030121 00020282 81030601 00112233"
                                       + "44");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 21 to 23                                                */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with 2 results TLV
        response = test.terminalResponse("81030121 00020282 81030601 01234567"
                                       + "89030100");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 24                                                      */
        /*********************************************************************/

        // Fetch the proactive command
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        // Terminal response with no result TLV
        response = test.terminalResponse("81030121 00020282 81");
        result &= response.checkSw("9000");

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                  + "18CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"
                                  + "CCCCCCCC CCCCCCCC CC");
        
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
