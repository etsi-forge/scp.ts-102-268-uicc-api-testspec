//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_tarv;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Tarv extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_tarv";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Tarv() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);


        // Install package
        test.loadPackage(CAP_FILE_PATH);

        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install Applet2 with TAR "020202"
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800B" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                                        // LV TAR Value(s)
                               "03020202" +  
                               "00");   // V Maximum number of services
        
        // Check correct installation
        addResult(response.checkSw("9000"));
        addResult(response.checkData("00"));


        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8013" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                                        //      LV TAR Value(s)
                               "09010101020202030303" +    
                               "00");   // V Maximum number of services

        addResult(response.checkSw("6A80"));

        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800F" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "00" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                                        //      LV TAR Value(s)
                               "050101010303" +    
                               "00");   // V Maximum number of services

        addResult(response.checkSw("6A80"));


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
