//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_pahd;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Hin_Pahd extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_hin_pahd";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    
      
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Hin_Pahd() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
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
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
               
        // Install Applet2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        
        test.reset();       
        test.terminalProfileSession("01810F00 21000008 01000002");
        
        /*******************************************************************************************/
        /** Testcase 1 At the processToolkit invocation the TLV-List is cleared                    */
        /** Testcase 2 TLV-List change after the init method invocation                            */
        /** Testcase 3 The TLV-List remains unchanged after the send() method invocation           */
        /** Testcase 4 At the processToolkit invocation the TLV-List is cleared                    */
        /** Testcase 5 At the call of its init method the content is cleared and then initialized  */
        /*******************************************************************************************/      
       
                
        // 1- Trigger AppletA1
        test.envelopeEventDownloadUserActivity();                       
        response = test.fetch("0B");      
        test.addResult(response.checkData("D0098103 01218082 028102"));
        test.terminalResponse("81030121 80820282 81830100");                 
        
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 +
                                     "03CCCCCC"));
        
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10" + APPLET_AID_2 +
                                     "08CCCCCC CCCCCCCC CC"));

        test.reset();       
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
                                                     
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        // delete Applet1        
        // delete Applet2
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deletePackage(CAP_FILE_PATH);
        
        return test.getOverallResult();
    }
}   
