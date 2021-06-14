//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_exh_genb;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Exh_Genb extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_exh_genb";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Exh_Genb() {
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
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services

        // Install Applet2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        

        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession("09010020 FFFF");

        // Trigger applets
        response = test.envelopeEventDownloadMTCall();
        addResult(response.checkSw("9000"));

        // Trigger applets
        response = test.envelopeEventDownloadMTCall();
        addResult(response.checkSw("9000"));

        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101", "");
        addResult(response.checkSw("9300"));

        // Trigger Applet2
        response = test.envelopeEventDownloadUserActivity();
        addResult(response.checkSw("9300"));

        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101", "");
        addResult(response.checkSw("9000"));

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101", "");
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "05" + "CCCCCCCC CC"));
        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "03" + "CCCCCC"));
                                     
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
        test.deletePackage(CAP_FILE_PATH);
        
        
        return getOverallResult();
    }
}   
