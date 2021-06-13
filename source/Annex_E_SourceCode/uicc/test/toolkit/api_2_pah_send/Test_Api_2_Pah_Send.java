//-----------------------------------------------------------------------------
//Test_Api_2_Pah_Send.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_send;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_send
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Pah_Send extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_pah_send";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Pah_Send() {
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
        

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        /*********************************************************************/
        /** Testcase 1,2                                                     */
        /*********************************************************************/

        response = test.unrecognizedEnvelope();
        result = response.checkSw("9112");
        
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030100");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 3,4                                                     */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030101");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 5,6                                                     */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030201 55");
        result &= response.checkSw("9112");
        
        /*********************************************************************/
        /** Testcase 7,8                                                     */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030402 654321");
        result &= response.checkSw("9181");

        /*********************************************************************/
        /** Testcase 9                                                       */
        /*********************************************************************/
        response = test.fetch("81");
        result &= response.checkData("D07F8103 01210082 0281028D 74045555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55");
        
        response = test.terminalResponse("81030121 00020282 81030100");
        result &= response.checkSw("9183");

        /*********************************************************************/
        /** Testcase 10                                                      */
        /*********************************************************************/
        response = test.fetch("83");
        result &= response.checkData("D0818081 03012100 82028102 8D750455"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "555555");
        
        response = test.terminalResponse("81030121 00020282 81030100");
        result &= response.checkSw("9100");

        /*********************************************************************/
        /** Testcase 11                                                      */
        /*********************************************************************/
        response = test.fetch("00");
        result &= response.checkData("D081FD81 03012100 82028102 8D81F104"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555");
        
        response = test.terminalResponse("81030121 00020282 81030100");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 12                                                      */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030100");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 13                                                      */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030100");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 14                                                      */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81030202 12030303"
                                       + "3456");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 15                                                      */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 81");
        result &= response.checkSw("9112");

        /*********************************************************************/
        /** Testcase 16                                                      */
        /*********************************************************************/
        response = test.fetch("12");
        result &= response.checkData("D0108103 01210082 0281028D 05045465"
                                   + "7874");
        
        response = test.terminalResponse("81030121 00020282 810300");
        result &= response.checkSw("9000");

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "10CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"
                                   + "CC");
        
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
