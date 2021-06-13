//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_uta_adel;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Uta_Adel extends UiccTestModel {

    static final String CAP_FILE_PATH_A = "uicc/test/catre/cre_uta_adel/cre_uta_adel_a";
    static final String CLASS_AID_A_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_A_1 = "A0000000 090005FF FFFFFF89 50010102";
    
    static final String CLASS_AID_A_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_A_2 = "A0000000 090005FF FFFFFF89 50020102";
    
    
    static final String CAP_FILE_PATH_B = "uicc/test/catre/cre_uta_adel/cre_uta_adel_b";
    static final String CLASS_AID_B_1 = "A0000000 090005FF FFFFFF89 50110001";
    static final String APPLET_AID_B_1 = "A0000000 090005FF FFFFFF89 50110102";
   
    static final String CLASS_AID_B_2 = "A0000000 090005FF FFFFFF89 50120001";
    static final String APPLET_AID_B_2 = "A0000000 090005FF FFFFFF89 50120102";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Uta_Adel() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        boolean result = false;
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        /*********************************************************************/
        /** Testcase 0                                                       */
        /*********************************************************************/

        // Install packageA
        test.loadPackage(CAP_FILE_PATH_A);
        // Install packageB
        test.loadPackage(CAP_FILE_PATH_B);
        
        
                
        // Install AppletA1
        test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
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
               
        // Install AppletA2
        test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_2, APPLET_AID_A_2, 
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
               
        // Install AppletB1
        test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_1, APPLET_AID_B_1, 
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services      
        
        
        // Install AppletB2
        test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_2, APPLET_AID_B_2, 
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "04" +   // V Id of menu entry 1
                               "04" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services      
        
        
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/      
        // The deletion shall fail if any object owned by the applet instance 
        // is referenced from an object owned by another applet instance on the card
        
                
        // 1- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");
         
        // 3- Trigger AppletA2
        test.envelopeMenuSelection("100102", "");
         
        // 5- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result = !response.checkSw("9000");         //response shall be different from 90 00

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // 6- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");
         
        // 7- Trigger AppletA2
        response = test.envelopeMenuSelection("100102", "");
        result &= response.checkSw("9000");
        
        // 9- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= response.checkSw("9000");
              
        // 10- Install AppletA1
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
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
              
        result &= response.checkData("00");
        result &= response.checkSw("9000");

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
   
        // 11- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");
        
        // 13- Trigger AppletB2
        test.envelopeMenuSelection("100104", "");
        
        // 15- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= !response.checkSw("9000");         //response shall be different from 90 00

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // 16- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");

        // 17- Trigger AppletB2
        response = test.envelopeMenuSelection("100104", "");
        result &= response.checkSw("9000");
 
        // 19- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= response.checkSw("9000");
              
        // 20- Install AppletA1
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
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
              
        result &= response.checkData("00");
        result &= response.checkSw("9000");
 
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
              
        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/      
        // The deletion shall failed if any object owned by the applet instance 
        // is referenced from a static field on any package on the card
        
        
        // 1- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");
         
        // 3- Trigger AppletA2
        test.envelopeMenuSelection("100102", "");
         
        // 5- Delete AppletA2
        response = test.deleteApplet(APPLET_AID_A_2);
        result &= response.checkSw("9000");

        // 6- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= !response.checkSw("9000");         //response shall be different from 90 00

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // 7- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");
         
        // 9- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= response.checkSw("9000");
              
        // 10- Install AppletA1
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
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
              
        result &= response.checkData("00");
        result &= response.checkSw("9000");

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
   
        // 11- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");
        
        // 13- Trigger AppletB2
        test.envelopeMenuSelection("100104", "");
        
        // 15- Delete AppletB2
        response = test.deleteApplet(APPLET_AID_B_2);
        result &= response.checkSw("9000");

        // 16- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= !response.checkSw("9000");         //response shall be different from 90 00

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // 17- Trigger AppletA1
        test.envelopeMenuSelection("100101", "");

        // 18- Trigger AppletB1
        test.envelopeMenuSelection("100103", "");        
 
        //check data
        response = test.selectApplication(APPLET_AID_A_1);
        result &= response.checkData("10" + APPLET_AID_A_1 + 
                                     "08CCCCCC CCCCCCCC CC"); 
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // 20- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= response.checkSw("9000");
              
        // 21- Install AppletA1
        response = test.installApplet(CAP_FILE_PATH_A, CLASS_AID_A_1, APPLET_AID_A_1, 
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
              
        result &= response.checkData("00");
        result &= response.checkSw("9000");
         
       
        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/      
        // Deletion of an active applet instance

         // 1- Delete AppletB1
        response = test.deleteApplet(APPLET_AID_B_1);
        result &= response.checkSw("9000");
 
        // 2- Install AppletB1                
        response = test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_1, APPLET_AID_B_1, 
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services      
                               
        result &= response.checkData("00");
        result &= response.checkSw("9000");

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // 3- Select AppletB1 on a different channel 
        response = test.manageChannel("00", "00");//Open channel if P1=00 P2 is reserved                                  
        response = test.selectApplication(response.getData(), APPLET_AID_B_1);
        result &= response.checkData("10" + APPLET_AID_B_1 + 
                                     "00"); 
        result &= response.checkSw("9000");                               
    
        // 4- Delete AppletB1
        response = test.deleteApplet(APPLET_AID_B_1);
        result &= !response.checkSw("9000");         //response shall be different from 90 00
 
 
        // 5- Select AppletB1
        response = test.selectApplication(APPLET_AID_B_1);
        result &= response.checkData("10" + APPLET_AID_B_1 + 
                                     "00"); 
        result &= response.checkSw("9000");
       
        // 6- reset
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/ 
        // Selection of a deleted applet instance
       
       
       
         // 1- Delete AppletB1
        response = test.deleteApplet(APPLET_AID_B_1);
        result &= response.checkSw("9000");
 
        // 2- Select AppletB1
        response = test.selectApplication(APPLET_AID_B_1);       
        result &= !response.checkSw("9000");         //response shall be different from 90 00
        
        // 3- Install AppletB1                
        response = test.installApplet(CAP_FILE_PATH_B, CLASS_AID_B_1, APPLET_AID_B_1, 
                               "800A" + // TLV UICC Toolkit application specific parameters
                               "01" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services      
                               
        result &= response.checkData("00");
        result &= response.checkSw("9000");
        
       
        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/ 
        // Object owned by a deleted applet can’t be accessed by other applets
        
        
        // 1- Delete AppletA1
        response = test.deleteApplet(APPLET_AID_A_1);
        result &= response.checkSw("9000");

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        // 2- Trigger AppletB1
        test.envelopeMenuSelection("100103", "");      
        
        response = test.selectApplication(APPLET_AID_B_1);
        result &= response.checkData("10" + APPLET_AID_B_1 + 
                                     "01CC"); 
        
        
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);        
                                                     
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        // delete AppletB1
        test.deleteApplet(APPLET_AID_B_1);    
        
        test.deletePackage(CAP_FILE_PATH_B);
        test.deletePackage(CAP_FILE_PATH_A);
        
        return result;
    }
}   
