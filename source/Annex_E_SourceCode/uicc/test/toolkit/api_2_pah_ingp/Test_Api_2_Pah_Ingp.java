//-----------------------------------------------------------------------------
//Test_Api_2_Ingp.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_ingp;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_ingp
 *
 * @version 0.0.1 - 13 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Pah_Ingp extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_pah_ingp";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Pah_Ingp() {
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
        /** Testcase 1 to 8                                                      */
        /*********************************************************************/

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("9117"));
        
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01230082 0281828D 06045465"
                                  +  "78744191 0200FF"));
        
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9117"));

        /*********************************************************************/
        /** Testcase 9                                                      */
        /*********************************************************************/
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01230082 0281828D 06045465"
                                   + "78744291 0210FF"));
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9117"));

        /*********************************************************************/
        /** Testcase 10                                                      */
        /*********************************************************************/
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01230082 0281828D 06045465"
                                   + "78744391 02FFFF"));
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9117"));

        /*********************************************************************/
        /** Testcase 11                                                      */
        /*********************************************************************/
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01230082 0281828D 06045465"
                                   + "78744491 020000"));
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9117"));

        /*********************************************************************/
        /** Testcase 12                                                      */
        /*********************************************************************/
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01238182 0281828D 06045465"
                                   + "78744591 020010"));
        response = test.terminalResponse("81030123 81020282 81030100 0D020441");
        addResult(response.checkSw("9117"));

        /*********************************************************************/
        /** Testcase 13                                                      */
        /*********************************************************************/
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01230082 0281828D 06005465"
                                   + "78744691 021010"));
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9117"));

        /*********************************************************************/
        /** Testcase 14                                                      */
        /*********************************************************************/
        response = test.fetch("17");
        addResult(response.checkData("D0158103 01230082 0281828D 06085465"
                                   + "78744791 0200FF"));
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("911C"));

        /*********************************************************************/
        /** Testcase 15                                                      */
        /*********************************************************************/
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01230082 0281828D 0B045465"
                                   + "78744854 65787448 91020010"));
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9111"));

        /*********************************************************************/
        /** Testcase 16                                                      */
        /*********************************************************************/
        response = test.fetch("11");
        addResult(response.checkData("D00F8103 01230082 0281828D 00910200"
                                   + "10"));
        
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9191"));
        
        /*********************************************************************/
        /** Testcase 17,18                                                   */
        /*********************************************************************/
        response = test.fetch("91");
        addResult(response.checkData("D0818E81 03012300 82028182 8D7F0455"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55910200"
                                   + "10"));
        
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9193"));

        /*********************************************************************/
        /** Testcase 19                                                      */
        /*********************************************************************/
        response = test.fetch("93");
        addResult(response.checkData("D0819081 03012300 82028182 8D818004"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555555"
                                   + "55555555 55555555 55555555 55555591"
                                   + "020010"));
        
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9100"));

        /*********************************************************************/
        /** Testcase 20,21,22                                                */
        /*********************************************************************/
        response = test.fetch("00");
        addResult(response.checkData("D081FD81 03012300 82028182 8D81ED04"
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
                                   + "55555555 55555555 55555555 91020010"));
        
        response = test.terminalResponse("81030123 00020282 81030100 0D020441");
        addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1
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
        
        return getOverallResult();
    }
}
