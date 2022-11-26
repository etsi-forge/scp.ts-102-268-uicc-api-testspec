//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_chal;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Chal extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_chal";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Chal() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "08" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
        
        addResult(response.checkSw("6A80"));
        
        // Select applet1
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.getStatusWord().substring(1,3).compareTo("61") != 0);
        

        // Card Initialisation
        test.reset();
        test.terminalProfileSession("09030120 00000000 0000000F");


        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 1
                               "04" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
                           true);
        addResult(response.checkSw("9000"));

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        test.status("00","0C","00");

        // Fetch SetUpMenu
        test.fetch("20");
        test.terminalResponse("81030125 00820282 81830100");
        

        /*********************************************************************/
        /** Testcase 3-4                                                       */
        /*********************************************************************/

        // Trigger Applet2
        response = test.envelopeMenuSelection("100102", "");
        // Fetch 4 Open Channel send the Terminal Response OK on channel 1 to 4
        for (byte i = 1; i < 5; i++)
        {
            addResult(response.checkSw("911A"));
            response = test.fetch("1A");
            addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                                         "77883502 03003902 000A"));
            response = test.terminalResponse("81030140 01820282 81830100 3802" +
                                             "8" + String.valueOf(i) + "00" +
                                             "35020300 3902000A");
        }
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/

        // Install Applet3
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 1
                               "07" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
        
	addResult(response.checkSw("9000"));

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        test.status("00","0C","00");

        // Fetch SetUpMenu
        test.fetch("2A");
        test.terminalResponse("81030125 00820282 81830100");

        
        /*********************************************************************/
        /** Testcase 6-7                                                       */
        /*********************************************************************/

        // Trigger Applet3
        response = test.envelopeMenuSelection("100103", "");
        // Fetch 3 Open Channel send the Terminal Response OK on channel 5 to 7
        for (byte i = 5; i < 8; i++)
        {
            addResult(response.checkSw("911A"));
            response = test.fetch("1A");
            addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                                         "77883502 03003902 000A"));
            response = test.terminalResponse("81030140 01820282 81830100 3802" +
                                             "8" + String.valueOf(i) + "00" +
                                             "35020300 3902000A");
        }
        addResult(response.checkSw("911A"));
        // Fetch last Open channel and send a NOK Terminal Response
        response = test.fetch("1A");
        addResult(response.checkData("D0188103 01400182 02818206 05815566" +
                                     "77883502 03003902 000A"));
        test.terminalResponse("81030140 01820282 8183023A 01");


        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "05" + "CCCCCCCC CC"));
        response = test.selectApplication(APPLET_AID_3);
        addResult(response.checkData("10" + APPLET_AID_3 + "04" + "CCCCCCCC"));
                                     

        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applets and package
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return getOverallResult();
    }
}   
