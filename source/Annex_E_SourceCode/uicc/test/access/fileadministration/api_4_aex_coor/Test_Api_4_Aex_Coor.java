//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_aex_coor;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Api_4_Aex_Coor extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/access/fileadministration/api_4_aex_coor";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 40010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 40010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_4_Aex_Coor() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
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
                  "00");   // V Maximum number of services

        test.reset();
        test.terminalProfileSession("09030120");

        // Trigger Applet
        response = test.envelopeMenuSelection("100101", "");
        result = response.checkSw("9000");                                              
           

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applet                                                     */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "01" + "CC");
                                     
        /*********************************************************************/
        /*********************************************************************/
        /** Delete Applet and package                                        */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return result;
    }
}   
