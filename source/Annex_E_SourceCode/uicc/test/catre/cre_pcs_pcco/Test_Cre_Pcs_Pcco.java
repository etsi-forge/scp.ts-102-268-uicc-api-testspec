//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_pcco;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Pcs_Pcco extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_pcs_pcco";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Pcs_Pcco() {
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

        // Load package
        test.loadPackage(CAP_FILE_PATH);
        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "04" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "04" );  // V Maximum number of services
        // Install Applet2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "08" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 1
                               "03" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "08" );  // V Maximum number of services
        // Install Applet3
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 1
                               "01" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("01016020 00000003 0000000F E3");


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        // SW = 9000
        result = response.checkSw("9000");


        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Trigger Applet2
        response = test.envelopeMenuSelection("100102","");
        // SW = 9000
        result &= response.checkSw("9000");
        
        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        // SW = 9000
        result &= response.checkSw("9000");
        
        // Trigger Applet2
        response = test.envelopeMenuSelection("100102","");
        // SW = 9000
        result &= response.checkSw("9000");
        
        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        // SW = 9000
        result &= response.checkSw("9113");
        
        // Fecth 3 Timer Management proactive commands
        response = test.fetch("13");
        result &= response.checkData("D0118103 01270082 02818224 01012503" +
                                     "000100");
        response = test.terminalResponse("81030127 00820282 81030100 24010125" +
                                         "03000100");
        result &= response.checkSw("9113");
        response = test.fetch("13");
        result &= response.checkData("D0118103 01270082 02818224 01022503" +
                                     "000100");
        response = test.terminalResponse("81030127 00820282 81030100 24010125" +
                                         "03000100");
        result &= response.checkSw("9113");
        response = test.fetch("13");
        result &= response.checkData("D0118103 01270082 02818224 01032503" +
                                     "000100");
        response = test.terminalResponse("81030127 00820282 81030100 24010125" +
                                         "03000100");
        result &= response.checkSw("9000");


        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        // SW = 9000
        result &= response.checkSw("9000");


        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/

        // Trigger Applet3
        response = test.envelopeMenuSelection("100103","");
        // SW = 911C
        result &= response.checkSw("911C");
        // Fetch the Open Channel
        response = test.fetch("1C");
        result &= response.checkData("D01A8103 01400182 02818206 05911122" +
                                     "33443504 01000000 39020080");
        // Terminal Response OK on channel 7
        response = test.terminalResponse("81030140 01820282 81030100 38028700" +
                                         "35040100 00003902 0080");
        result &= response.checkSw("9000");
                                         
        // Trigger Applet2
        response = test.envelopeMenuSelection("100102","");
        // SW = 911C
        result &= response.checkSw("911C");
        // Fetch the Open Channel CSD
        response = test.fetch("1C");
        result &= response.checkData("D01A8103 01400182 02818206 05911122" +
                                     "33443504 01000000 39020080");
        // Terminal Response OK on channel 1
        response = test.terminalResponse("81030140 01820282 81030100 38028100" +
                                         "35040100 00003902 0080");
        result &= response.checkSw("9117");
        // Fetch the Open Channel GPRS
        response = test.fetch("17");
        result &= response.checkData("D0158103 01400182 02818235 06020101" +
                                     "02013139 020080");
        // Terminal Response OK on channel 2
        response = test.terminalResponse("81030140 01820282 81030100 38028200" +
                                         "35060201 01020131 39020080");
        result &= response.checkSw("911C");
        // Fetch the Open Channel CSD
        response = test.fetch("1C");
        result &= response.checkData("D01A8103 01400182 02818206 05911122" +
                                     "33443504 01000000 39020080");
        // Terminal Response OK on channel 3
        response = test.terminalResponse("81030140 01820282 81030100 38028300" +
                                         "35040100 00003902 0080");
        result &= response.checkSw("9000");


        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/

        // Trigger Applet2
        response = test.envelopeMenuSelection("100102","");

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        result &= response.checkSw("9000");

        // Trigger Applet2
        response = test.envelopeMenuSelection("100102","");
        result &= response.checkSw("9000");

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        result &= response.checkSw("9110");
        
        // Fecth 3 Declare Service "Add" proactive commands
        response = test.fetch("10");
        result &= response.checkData("D00E8103 01470082 02818241 03000000");
        response = test.terminalResponse("81030147 00820282 81030100");
        result &= response.checkSw("9110");
        response = test.fetch("10");
        result &= response.checkData("D00E8103 01470082 02818241 03000100");
        response = test.terminalResponse("81030147 00820282 81030100");
        result &= response.checkSw("9110");
        response = test.fetch("10");
        result &= response.checkData("D00E8103 01470082 02818241 03000200");
        response = test.terminalResponse("81030147 00820282 81030100");
        result &= response.checkSw("9110");
        
        // Fecth 3 Declare Service "Delete" proactive commands
        response = test.fetch("10");
        result &= response.checkData("D00E8103 01470182 02818241 03000000");
        response = test.terminalResponse("81030147 01820282 81030100");
        result &= response.checkSw("9110");
        response = test.fetch("10");
        result &= response.checkData("D00E8103 01470182 02818241 03000100");
        response = test.terminalResponse("81030147 01820282 81030100");
        result &= response.checkSw("9110");
        response = test.fetch("10");
        result &= response.checkData("D00E8103 01470182 02818241 03000200");
        response = test.terminalResponse("81030147 01820282 81030100");
        result &= response.checkSw("9000");
        


        /*********************************************************************/
        /** Testcase 6                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        result &= response.checkSw("910F");

        // Fecth 3 unknown proactive commande
        response = test.fetch("0F");
        result &= response.checkData("D00D8103 01000082 02810000 020000");
        response = test.terminalResponse("81030100 00820282 81030100");
        result &= response.checkSw("9000");

        
        
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "12" + 
            "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
            "CCCC");
        response = test.selectApplication(APPLET_AID_2);
        result &= response.checkData("10" + APPLET_AID_2 + "0B" + 
            "CCCCCCCC CCCCCCCC CCCCCC");
        response = test.selectApplication(APPLET_AID_3);
        result &= response.checkData("10" + APPLET_AID_3 + "01" + "CC");
                                     
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applets and package
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return result;
    }
}   
