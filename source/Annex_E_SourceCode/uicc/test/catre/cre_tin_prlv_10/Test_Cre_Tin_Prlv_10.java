//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_prlv_10;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Prlv_10 extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_prlv_10";
    static final String CAP_FILE_PATH_A = "uicc/test/catre/cre_tin_prlv_10/cre_tin_prlv_10a";
    static final String CAP_FILE_PATH_B = "uicc/test/catre/cre_tin_prlv_10/cre_tin_prlv_10b";
    static final String CLASS_AID_A = "A0000000 090005FF FFFFFF89 50110001";
    static final String APPLET_AID_A1 = "A0000000 090005FF FFFFFF89 50110102";
    static final String APPLET_AID_A2 = "A0000000 090005FF FFFFFF89 50110202";
    static final String CLASS_AID_B = "A0000000 090005FF FFFFFF89 50210001";
    static final String APPLET_AID_B1 = "A0000000 090005FF FFFFFF89 50210102";
    static final String APPLET_AID_B2 = "A0000000 090005FF FFFFFF89 50210202";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Prlv_10() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // Install packages
        test.loadPackage(CAP_FILE_PATH);
        test.loadPackage(CAP_FILE_PATH_A);
        test.loadPackage(CAP_FILE_PATH_B);
        
        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install AppletA1
        test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A, APPLET_AID_A1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
        
        // Install AppletA2
        test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A, APPLET_AID_A2, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "02" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
                

         // Card Initialisation
        test.reset();
        test.terminalProfileSession("09030020 21");
        
        // Trigger the applets
        response = test.envelopeEventDownloadUserActivity();
        addResult(response.checkSw("9000"));
        

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_A1);
        addResult(response.checkData("10" + APPLET_AID_A1 + "01" + "CC"));
        response = test.selectApplication(APPLET_AID_A2);
        addResult(response.checkData("10" + APPLET_AID_A2 + "01" + "CC"));


        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // Install AppletB1
        test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B, APPLET_AID_B1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
        
        // Install AppletB2
        test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B, APPLET_AID_B2, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "02" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services

        test.reset();
        test.terminalProfileSession("09030020 21");
        
        // Trigger the applets
        response = test.envelopeEventDownloadUserActivity();
        addResult(response.checkSw("9000"));
        

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_A1);
        addResult(response.checkData("10" + APPLET_AID_A1 + "02" + "CCCC"));
        response = test.selectApplication(APPLET_AID_A2);
        addResult(response.checkData("10" + APPLET_AID_A2 + "02" + "CCCC"));
        response = test.selectApplication(APPLET_AID_B1);
        addResult(response.checkData("10" + APPLET_AID_B1 + "01" + "CC"));
        response = test.selectApplication(APPLET_AID_B2);
        addResult(response.checkData("10" + APPLET_AID_B2 + "01" + "CC"));


        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applets and package
        test.deleteApplet(APPLET_AID_A1);
        test.deleteApplet(APPLET_AID_A2);
        test.deleteApplet(APPLET_AID_B1);
        test.deleteApplet(APPLET_AID_B2);
        test.deletePackage(CAP_FILE_PATH_B);
        test.deletePackage(CAP_FILE_PATH_A);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return getOverallResult();
    }
}   
