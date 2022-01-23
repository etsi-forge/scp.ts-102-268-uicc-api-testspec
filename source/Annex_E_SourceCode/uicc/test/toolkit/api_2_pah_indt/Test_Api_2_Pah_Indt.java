//-----------------------------------------------------------------------------
//Test_Api_2_Pah_Indt.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_indt;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_indt
 *
 * @version 0.0.1 - 13 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Pah_Indt extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_pah_indt";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Pah_Indt() {
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
        /** Testcase 1 to 8                                                      */
        /*********************************************************************/

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("9113"));
        
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01210082 0281028D 06045465"
                                   + "787441"));
        
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9113"));

        /*********************************************************************/
        /** Testcase 9                                                      */
        /*********************************************************************/
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01210082 0281028D 06045465"
                                   + "787442"));
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9113"));

        /*********************************************************************/
        /** Testcase 10                                                      */
        /*********************************************************************/
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01210082 0281028D 06045465"
                                   + "787443"));
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9113"));

        /*********************************************************************/
        /** Testcase 11                                                      */
        /*********************************************************************/
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01210082 0281028D 06045465"
                                   + "787444"));
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9113"));

        /*********************************************************************/
        /** Testcase 12                                                      */
        /*********************************************************************/
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01218182 0281028D 06045465"
                                   + "787445"));
        response = test.terminalResponse("81030121 81020282 81030100");
        test.addResult(response.checkSw("9113"));

        /*********************************************************************/
        /** Testcase 13                                                      */
        /*********************************************************************/
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01210082 0281028D 06005465"
                                   + "787446"));
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9113"));

        /*********************************************************************/
        /** Testcase 14                                                      */
        /*********************************************************************/
        response = test.fetch("13");
        test.addResult(response.checkData("D0118103 01210082 0281028D 06085465"
                                   + "787447"));
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9118"));

        /*********************************************************************/
        /** Testcase 15                                                      */
        /*********************************************************************/
        response = test.fetch("18");
        test.addResult(response.checkData("D0168103 01210082 0281028D 0B045465"
                                   + "78744854 65787448"));
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("910D"));

        /*********************************************************************/
        /** Testcase 16                                                      */
        /*********************************************************************/
        response = test.fetch("0D");
        test.addResult(response.checkData("D00B8103 01210082 0281028D 00"));
        
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("918D"));
        
        /*********************************************************************/
        /** Testcase 17,18                                                   */
        /*********************************************************************/
        response = test.fetch("8D");
        test.addResult(response.checkData("D0818A81 03012100 82028102 8D7F0455"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55"));
        
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("918F"));

        /*********************************************************************/
        /** Testcase 19                                                      */
        /*********************************************************************/
        response = test.fetch("8F");
        test.addResult(response.checkData("D0818C81 03012100 82028102 8D818004"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 555555"));
        
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9100"));

        /*********************************************************************/
        /** Testcase 20,21,22                                                */
        /*********************************************************************/
        response = test.fetch("00");
        test.addResult(response.checkData("D081FD81 03012100 82028102 8D81F104"
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
                                   + "55555555 55555555 55555555 55555555"));
        
        response = test.terminalResponse("81030121 00020282 81030100");
        test.addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1
                                  + "16CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"
                                  + "CCCCCCCC CCCCCC"));
        
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
