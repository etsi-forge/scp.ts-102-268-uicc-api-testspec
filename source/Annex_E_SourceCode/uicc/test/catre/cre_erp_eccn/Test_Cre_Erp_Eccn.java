//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_erp_eccn;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Erp_Eccn extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_erp_eccn";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Erp_Eccn() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);


        /*********************************************************************/
        /** Testcase 1                                                       */
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
                               "00" );  // V Maximum number of services
        // Install Applet2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        

        test.reset();
        test.terminalProfileSession("09030120");

        // Trigger Applet2
        response = test.envelopeMenuSelection("100101", "");
        test.addResult(response.checkSw("9114"));

        // Trigger Applet1
        response = test.envelopeCallControlByNAA();
        test.addResult(response.checkData("02078605 91112233 44"));
        test.addResult(response.checkSw("9114"));
        
        // Fetch Display Text and Terminal Response
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07044150" +
                                     "504C4554"));
        response = test.terminalResponse("81030121 80820282 81830100");
        test.addResult(response.checkSw("9000"));
           

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 + "01" + "CC"));
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10" + APPLET_AID_2 + "01" + "CC"));
                                     
        /*********************************************************************/
        /*********************************************************************/
        /** Delete Applets                                                   */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // delete Applet1 and Applet2
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);


        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/


        // Install Applet3
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services

        test.reset();
        // Profile download
        test.terminalProfileSession("09030120");

        // Trigger Applet3
        response = test.envelopeMenuSelection("100101", "");
        test.addResult(response.checkSw("9114"));

        // Trigger Applet3
        response = test.envelopeCallControlByNAA();
        test.addResult(response.checkData("02078605 91112233 44"));
        test.addResult(response.checkSw("9114"));
        
        // Fetch Display Text and Terminal Response
        response = test.fetch("14");
        test.addResult(response.checkData("D0128103 01218082 0281028D 07044150" +
                                     "504C4554"));
        response = test.terminalResponse("81030121 80820282 81830100");
        test.addResult(response.checkSw("9000"));


        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_3);
        test.addResult(response.checkData("10" + APPLET_AID_3 + "01" + "CC"));
                                     

        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        // delete applets and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return test.getOverallResult();
    }
}   
