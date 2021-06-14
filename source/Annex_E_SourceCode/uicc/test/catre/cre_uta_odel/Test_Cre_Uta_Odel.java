//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_odel;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Uta_Odel extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_uta_odel";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";  
 
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Uta_Odel() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // Install package
        test.loadPackage(CAP_FILE_PATH);
       
        // Install Applet
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
       
       
       
        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/      
        //Object deletion is supported
        test.reset();
        test.terminalProfile("0101");        
        
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 +
                                    "01CC"));

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);        
                    
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                */
        /*********************************************************************/
        /*********************************************************************/

        // delete Applet and package
        test.deleteApplet(APPLET_AID_1);                      
        test.deletePackage(CAP_FILE_PATH);
               
        return getOverallResult();
    }
}   
