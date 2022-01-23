//-----------------------------------------------------------------------------
//Test_Api_2_Ers_Gthd.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_ers_gthd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

/**
 * Test Area : uicc.test.toolkit.api_2_ers_gthd
 *
 * @version 0.0.1 - 2 mars 2006
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Ers_Gthd extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_ers_gthd";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Ers_Gthd() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {

        APDUResponse data = null;
        test.initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        /*********************************************************************/
        /** Applet installation                                              */
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
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // Test cases 1,2,3
        for (byte i = 0; i< 3; i++)
        {
             response = test.unrecognizedEnvelope();
        }
        
        // Test case 4
        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("910B"));
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01020082 028182"));
        response = test.terminalResponse("81030102 00020282 81030100");
        test.addResult(response.checkSw("9000"));
        
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1
                                   + "04CCCCCC CC"));
        
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
        
        
        return test.getOverallResult();
    }
}
