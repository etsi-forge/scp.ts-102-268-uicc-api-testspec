//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_trs_isprav;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Api_2_Trs_IsPrAv extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_trs_isprav";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 20020001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 20010202";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Trs_IsPrAv() {
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

        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
        
		addResult(response.checkSw("9000"));
		
        // Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "02" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
							   
	    addResult(response.checkSw("9000"));
        
        // Card Initialisation
        test.reset();
        test.terminalProfileSession("09030020 21");
        
        // Trigger the applets
        response = test.envelopeEventProactiveHandlerAvailable();
        addResult(response.checkSw("9000"));
        

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "01" + "CC"));
        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "01" + "CC"));


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
	    addResult(response.checkSw("9000"));
        
        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
							   
 	    addResult(response.checkSw("9000"));    
		
        // Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services
 
	    addResult(response.checkSw("9000"));
		
        // Card Initialisation
        test.reset();
        test.terminalProfileSession("09030020 21");
        
        // Trigger the applets
        response = test.envelopeEventProactiveHandlerAvailable();
        addResult(response.checkSw("9000"));
        

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "01" + "CC"));
        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "01" + "CC"));


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
 	    addResult(response.checkSw("9000"));
        
		
        return getOverallResult();
    }
}   
