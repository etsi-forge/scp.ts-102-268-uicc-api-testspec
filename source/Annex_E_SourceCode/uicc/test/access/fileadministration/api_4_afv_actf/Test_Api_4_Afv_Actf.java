//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_actf;

//---------------------------------------1--------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Api_4_Afv_Actf extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/access/fileadministration/api_4_Afv_actf";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 40010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 40010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_4_Afv_Actf() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
        // test script
        test.reset();
				test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        /*********************************************************************/
        /** Testcase 1 to 4                                                  */
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
                  "00" +   // V Maximum number of services
                  "821E" + // TLV UICC Access application specific parameters
                  "00" +   // LV UICC File System AID field
                  "0402FFFDFF" + // LV Access Domain for UICC file system = All except ADM2 
                  "00" +   // LV Access Domain DAP field
                  "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                  "0402FFFDFF" + // LV Access Domain for ADF1 file system = All except ADM22 
                  "00" ); //+   // LV Access Domain DAP field
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
        result &= response.checkData("10" + APPLET_AID_1 + "04" + "CCCCCCCC");
                                     
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
