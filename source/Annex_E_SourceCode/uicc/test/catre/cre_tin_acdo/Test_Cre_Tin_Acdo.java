//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_acdo;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Acdo extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_acdo";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
    static final String CLASS_AID_4 = "A0000000 090005FF FFFFFF89 50040001";
    static final String APPLET_AID_4 = "A0000000 090005FF FFFFFF89 50040102";
    static final String CLASS_AID_5 = "A0000000 090005FF FFFFFF89 50050001";
    static final String APPLET_AID_5 = "A0000000 090005FF FFFFFF89 50050102";
    static final String CLASS_AID_6 = "A0000000 090005FF FFFFFF89 50060001";
    static final String APPLET_AID_6 = "A0000000 090005FF FFFFFF89 50060102";
    static final String CLASS_AID_7 = "A0000000 090005FF FFFFFF89 50070001";
    static final String APPLET_AID_7 = "A0000000 090005FF FFFFFF89 50070102";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      

    public Test_Cre_Tin_Acdo() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // install package and applet
        test.loadPackage(CAP_FILE_PATH);


        /*********************************************************************/
        /** Testcase 1 : Full access Applet                                  */
        /*********************************************************************/

        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800C" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "02" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "02" +   // V Id of menu entry 2
                               "02" +   // V Position of menu entry 2
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
                               "00" +   // LV Access Domain DAP field
                           "8218" + // TLV UICC Access application specific parameters
                               "00" +   // LV UICC File System AID field
                               "0100" + // LV Access Domain for UICC file system = Full Access
                               "00" +   // LV Access Domain DAP field
                               "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                               "0100" + // LV Access Domain for ADF1 file system = Full Access
                               "00");   // LV Access Domain DAP field
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        

        // Trigger Applet1 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result = response.checkSw("9000");
        
        /** Check Applet first results                                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "4B" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCC");
                                     
        test.reset();
        test.terminalProfileSession("09010020");
        
        // Trigger Applet1 on its menu 2 to launch admin fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "5A" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        // delete applet
        test.deleteApplet(APPLET_AID_1);
        

        /*********************************************************************/
        /** Testcase 2 : No access Applet                                    */
        /*********************************************************************/

        test.installApplet( CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                            "800C" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "02" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "8118" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "01FF" + // LV Access Domain for UICC file system = No Access
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "01FF" + // LV Access Domain for ADF1 file system = No Access
                                "00" +   // LV Access Domain DAP field
                            "8218" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "01FF" + // LV Access Domain for UICC file system = No Access
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "01FF" + // LV Access Domain for ADF1 file system = No Access
                                "00");   // LV Access Domain DAP field
            // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");

        // Trigger Applet2 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");
        
        /** Check Applet first results                                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_2);
        result &= response.checkData("10" + APPLET_AID_2 + "4B" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        // Trigger Applet2 on its menu 2 to launch admin fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_2);
        result &= response.checkData("10" + APPLET_AID_2 + "5A" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");

        // delete applet
        test.deleteApplet(APPLET_AID_2);

        
        /*********************************************************************/
        /** Testcase 3 : Always access right Applet                          */
        /*********************************************************************/

        test.installApplet( CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                            "800C" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "02" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "811E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402000004" + // LV Access Domain for UICC file system = Always access right 
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402000004" + // LV Access Domain for ADF1 file system = Always access right 
                                "00" +   // LV Access Domain DAP field
                            "821E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402000004" + // LV Access Domain for UICC file system = Always access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402000004" + // LV Access Domain for ADF1 file system = Always access right 
                                "00" );  // LV Access Domain DAP field
            // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        

        // Trigger Applet3 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");
        
        /** Check Applet first results                                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_3);
        result &= response.checkData("10" + APPLET_AID_3 + "4B" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        // Trigger Applet3 on its menu 2 to launch admin fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_3);
        result &= response.checkData("10" + APPLET_AID_3 + "5A" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC");
                                     
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applet
        test.deleteApplet(APPLET_AID_3);

        
        /*********************************************************************/
        /** Testcase 4 : Global PIN1 access right Applet                     */
        /*********************************************************************/

        test.installApplet( CAP_FILE_PATH, CLASS_AID_4, APPLET_AID_4, 
                            "800C" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "02" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "811E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402010000" + // LV Access Domain for UICC file system = Global PIN1 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402010000" + // LV Access Domain for ADF1 file system = Global PIN1 access right
                                "00" +   // LV Access Domain DAP field
                            "821E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402010000" + // LV Access Domain for UICC file system = Global PIN1 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402010000" + // LV Access Domain for ADF1 file system = Global PIN1 access right
                                "00" );  // LV Access Domain DAP field
            // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        

        // Trigger Applet4 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");
        
        /** Check Applet first results                                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_4);
        result &= response.checkData("10" + APPLET_AID_4 + "4B" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        // Trigger Applet4 on its menu 2 to launch admin fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_4);
        result &= response.checkData("10" + APPLET_AID_4 + "5A" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC");
                                     
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applet
        test.deleteApplet(APPLET_AID_4);

        
        /*********************************************************************/
        /** Testcase 5 : Global PIN1 & ADM1 access right Applet              */
        /*********************************************************************/

        test.installApplet( CAP_FILE_PATH, CLASS_AID_5, APPLET_AID_5, 
                            "800C" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "02" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "811E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402010100" + // LV Access Domain for UICC file system = Global PIN1 & ADM1 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402010100" + // LV Access Domain for ADF1 file system = Global PIN1 & ADM1 access right
                                "00" +   // LV Access Domain DAP field
                            "821E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402010100" + // LV Access Domain for UICC file system = Global PIN1 & ADM1 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402010100" + // LV Access Domain for ADF1 file system = Global PIN1 & ADM1 access right
                                "00" );  // LV Access Domain DAP field
            // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        

        // Trigger Applet5 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");
        
        /** Check Applet first results                                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_5);
        result &= response.checkData("10" + APPLET_AID_5 + "4B" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        // Trigger Applet5 on its menu 2 to launch admin fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_5);
        result &= response.checkData("10" + APPLET_AID_5 + "5A" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC");
                                     
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applet
        test.deleteApplet(APPLET_AID_5);

        
        /*********************************************************************/
        /** Testcase 6 : Local PIN & ADM2 access right Applet                */
        /*********************************************************************/

        test.installApplet( CAP_FILE_PATH, CLASS_AID_6, APPLET_AID_6, 
                            "800C" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "02" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "811E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402000208" + // LV Access Domain for UICC file system = Local PIN & ADM2 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402000208" + // LV Access Domain for ADF1 file system = Local PIN & ADM2 access right
                                "00" +   // LV Access Domain DAP field
                            "821E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402000208" + // LV Access Domain for UICC file system = Local PIN & ADM2 access right
                                "00" +   // LV UICC File System AID field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402000208" + // LV Access Domain for ADF1 file system = Local PIN & ADM2 access right
                                "00" );  // LV Access Domain DAP field
            // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        

        // Trigger Applet6 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");
        
        /** Check Applet first results                                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_6);
        result &= response.checkData("10" + APPLET_AID_6 + "4B" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCC");
                                     
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        // Trigger Applet6 on its menu 2 to launch admin fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_6);
        result &= response.checkData("10" + APPLET_AID_6 + "5A" + 
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                "CCCCCCCC CCCCCCCC CCCC");
                                     
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applet
        test.deleteApplet(APPLET_AID_6);

        
        
        /*********************************************************************/
        /** Testcase 7 : Acces domain parameter differenciation              */
        /*********************************************************************/

        /** Subcase 1 **/
        /***************/
        
        test.installApplet( CAP_FILE_PATH, CLASS_AID_7, APPLET_AID_7, 
                            "800E" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "03" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "03" +   // V Id of menu entry 3
                                "04" +   // V Position of menu entry 3
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "811E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402000104" + // LV Access Domain for UICC file system = Always & ADM1 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402000104" + // LV Access Domain for ADF1 file system = Always & ADM1 access right
                                "00" );  // LV Access Domain DAP field
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        
        // Trigger Applet7 on its menu 2 to launch fileview access tests
        response = test.envelopeMenuSelection("100101", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/
        
        response = test.selectApplication(APPLET_AID_7);
        result &= response.checkData("10" + APPLET_AID_7 + "03" + "CCCCCC");
                                 
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // delete applet
        test.deleteApplet(APPLET_AID_7);
        
        /** Subcase 2 **/
        /***************/
        
        test.installApplet( CAP_FILE_PATH, CLASS_AID_7, APPLET_AID_7, 
                            "800E" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "03" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "03" +   // V Id of menu entry 3
                                "04" +   // V Position of menu entry 3
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "821E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402010200" + // LV Access Domain for UICC file system = Global PIN1 & ADM2 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402010200" + // LV Access Domain for ADF1 file system = Global PIN1 & ADM2 access right
                                "00" );  // LV Access Domain DAP field
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        
        
        // Trigger Applet7 on its menu 2 to launch fileview access tests
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/
        
        response = test.selectApplication(APPLET_AID_7);
        result &= response.checkData("10" + APPLET_AID_7 + "03" + "CCCCCC");
                                 
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applet
        test.deleteApplet(APPLET_AID_7);
        
        /** Subcase 3 **/
        /***************/
        test.installApplet(CAP_FILE_PATH, CLASS_AID_7, APPLET_AID_7, 
                            "800E" + // TLV UICC Toolkit application specific parameters
                                "FF" +   // V Priority Level
                                "00" +   // V Max. number of timers
                                "0F" +   // V Maximum text length for a menu entry
                                "03" +   // V Maximum number of menu entries
                                "01" +   // V Id of menu entry 1
                                "01" +   // V Position of menu entry 1
                                "02" +   // V Id of menu entry 2
                                "02" +   // V Position of menu entry 2
                                "03" +   // V Id of menu entry 3
                                "03" +   // V Position of menu entry 3
                                "00" +   // V Maximum number of channels 
                                "00" +   // LV Minimum Security Level field
                                "00" +   // LV TAR Value(s) 
                                "00" +  // V Maximum number of services
                            "811E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402000104" + // LV Access Domain for UICC file system = Always & ADM1 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402000104" + // LV Access Domain for ADF1 file system = Always & ADM1 access right
                                "00" +   // LV Access Domain DAP field
                            "821E" + // TLV UICC Access application specific parameters
                                "00" +   // LV UICC File System AID field
                                "0402010200" + // LV Access Domain for UICC file system = Global PIN1 & ADM2 access right
                                "00" +   // LV Access Domain DAP field
                                "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                                "0402010200" + // LV Access Domain for ADF1 file system = Global PIN1 & ADM2 access right
                                "00" );  // LV Access Domain DAP field
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfileSession("09010020");
        

        // Trigger Applet7 on its menu 1 to launch fileview access tests
        response = test.envelopeMenuSelection("100103", "");
        result &= response.checkSw("9000");
        
        /** Check Applet second results then delete it                       */
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_7);
        result &= response.checkData("10" + APPLET_AID_7 + "06" + 
                                     "CCCCCCCC CCCC");
                                     
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applet
        test.deleteApplet(APPLET_AID_7);

        // Delete package
        test.deletePackage(CAP_FILE_PATH);
        
        return result;
    }
}
