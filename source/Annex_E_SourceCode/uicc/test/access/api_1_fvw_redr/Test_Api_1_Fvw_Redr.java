//-----------------------------------------------------------------------------
//Test_Api_1_Fvw_Redr.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_redr;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.access.api_1_fvw_redr
 *
 * @version 0.0.1 - 6 d�c. 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_1_Fvw_Redr extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/access/api_1_fvw_redr";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 10010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 10010102";  

    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_1_Fvw_Redr() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession("0101");

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
                           "00" +  // V Maximum number of services
                           "8118" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = Full Access
                           "00" +   // LV Access Domain DAP field
                           "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                           "0100" + // LV Access Domain for ADF1 file system = Full Access
                           "00"    // LV Access Domain DAP field
                           );  

        test.reset();
        test.terminalProfileSession("0101");
                                   
        /*********************************************************************/
        /** Testcase 1 to 12                                                 */                             
        /*********************************************************************/      
        
        // Trigger Applet envelope unrecognized
        test.unrecognizedEnvelope();               
        
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 +
                                    "19CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                    "CCCCCCCC CCCCCCCC CCCC"));

        test.reset();               
        test.terminalProfileSession("0101");
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/       
        // delete Applet and package
        test.deleteApplet(APPLET_AID_1);                      
        test.deletePackage(CAP_FILE_PATH);
               
        return test.getOverallResult();
    }
}   