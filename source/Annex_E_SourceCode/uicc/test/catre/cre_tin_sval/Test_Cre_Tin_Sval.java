//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_sval;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Sval extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_Sval";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Sval() {
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
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "09");   // V Maximum number of services
        
        addResult(response.checkSw("6A80"));
        
        // Select applet1
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.getStatusWord().substring(1,3).compareTo("61") != 0);
        

        /*********************************************************************/
        /** Testcase 2-4                                                     */
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                                        "800A" + // TLV UICC Toolkit application specific parameters
                                            "FF" +   // V Priority Level
                                            "00" +   // V Max. number of timers
                                            "10" +   // V Maximum text length for a menu entry
                                            "01" +   // V Maximum number of menu entries
                                            "02" +   // V Id of menu entry 1
                                            "02" +   // V Position of menu entry 1
                                            "00" +   // V Maximum number of channels 
                                            "00" +   // LV Minimum Security Level field
                                            "00" +   // LV TAR Value(s) 
                                            "04");   // V Maximum number of services
        
        addResult(response.checkData("00"));
        addResult(response.checkSw("9000"));

        test.reset();
        test.terminalProfileSession("09030120");
        
        // Trigger Applet2
        response = test.envelopeMenuSelection("100102", "");
        addResult(response.checkSw("9000"));

        /*********************************************************************/
        /** Testcase 5-8                                                     */
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // Install Applet3
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                                        "800A" + // TLV UICC Toolkit application specific parameters
                                            "FF" +   // V Priority Level
                                            "00" +   // V Max. number of timers
                                            "10" +   // V Maximum text length for a menu entry
                                            "01" +   // V Maximum number of menu entries
                                            "03" +   // V Id of menu entry 1
                                            "03" +   // V Position of menu entry 1
                                            "00" +   // V Maximum number of channels 
                                            "00" +   // LV Minimum Security Level field
                                            "00" +   // LV TAR Value(s) 
                                            "08");   // V Maximum number of services
        addResult(response.checkData("00"));
        addResult(response.checkSw("9000"));
        
        test.reset();
        test.terminalProfileSession("09030120");

        // Trigger Applet3
        response = test.envelopeMenuSelection("100103", "");
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "02" + "CCCC"));
        response = test.selectApplication(APPLET_AID_3);
        addResult(response.checkData("10" + APPLET_AID_3 + "03" + "CCCCCC"));
                                     

        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // Send an unrecognized envelope to release static field in applet 2
        //    before deletion.
        test.unrecognizedEnvelope();
        // delete applets and package
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return getOverallResult();
    }
}   
