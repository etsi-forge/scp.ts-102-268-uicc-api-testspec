//-----------------------------------------------------------------------------
//Test_Api_2_Prh_Cchd.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cchd;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cchd
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Prh_Cchd extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_prh_cchd";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Prh_Cchd() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
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
                           "8008" + // TLV UICC Toolkit application specific parameters
                           "01" +   // V Priority Level
                           "00" +   // V Max. number of timers
                           "00" +   // V Maximum text length for a menu entry
                           "00" +   // V Maximum number of menu entries
                           "01" +   // V Maximum number of channels 
                           "00" +   // LV Minimum Security Level field
                           "00" +   // LV TAR Value(s) 
                           "00" +   // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                           "00" +   // LV UICC File System AID field
                           "0100" + // LV Access Domain for UICC file system = ALWAYS
                           "00" );  // LV Access Domain DAP field       
        

        // Card Initialisation
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        
        /*********************************************************************/   
        /** Testcase 1                                                       */   
        /*********************************************************************/   
                                                                                  
        response = test.unrecognizedEnvelope();                                   
        result = response.checkSw("911A");                                        
        
        // Fetch the open channel proactive command
        response = test.fetch("1A");
        result &= response.checkData("D0188103 01400182 02818206 05815566"
                                   + "77883502 03003902 000A");                                     
        // Successful Terminal Response with Channel Id 1
        response = test.terminalResponse("81030140 01820282 81830100 38028100"
                                       + "35020300 3902000A");
        result &= response.checkSw("910E");

        /*********************************************************************/   
        /** Testcase 2                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the received data proactive command
        response = test.fetch("0E");
        result &= response.checkData("D00C8103 01420182 02812137 0102");                                     
        // Terminal Response with not empty Channel Data TLV is issued ('AA')
        response = test.terminalResponse("81030142 01820282 81830100 36024142"
                                       + "370102");
        result &= response.checkSw("910E");

        /*********************************************************************/   
        /** Testcase 3 to 8                                                  */   
        /*********************************************************************/   
                                                                                  
        // Fetch the received data proactive command
        response = test.fetch("0E");
        result &= response.checkData("D00C8103 01420182 02812137 0106");                                     
        // Terminal Response with 6 bytes avalaible (‘Hello1’)
        response = test.terminalResponse("81030142 01820282 81830100 36064865"
                                       + "6C6C6F31 370106");
        result &= response.checkSw("910E");

        /*********************************************************************/   
        /** Testcase 9                                                       */   
        /*********************************************************************/   
                                                                                  
        // Fetch the received data proactive command
        response = test.fetch("0E");
        result &= response.checkData("D00C8103 01420182 02812137 0106");          
        // Terminal Response without ChannelData TLV element
        response = test.terminalResponse("81030142 01820282 81830100 370106");
        result &= response.checkSw("910E");

        /*********************************************************************/   
        /** Testcase 10 to 16                                                */   
        /*********************************************************************/   
                                                                                  
        // Fetch the received data proactive command
        response = test.fetch("0E");
        result &= response.checkData("D00C8103 01420182 02812137 0106");          
        // Terminal Response with 6 bytes avalaible (‘Hello2’)
        response = test.terminalResponse("81030142 01820282 81830100 36064865"
                                       + "6C6C6F32 370106");
        result &= response.checkSw("910E");

        /*********************************************************************/   
        /** Testcase 17 & 18                                                 */   
        /*********************************************************************/   
                                                                                  
        // Fetch the received data proactive command
        response = test.fetch("0E");
        result &= response.checkData("D00C8103 01420182 02812137 010C");          
        // Terminal Response with 12 bytes avalaible (‘Hello3’ & 'Hello4')
        response = test.terminalResponse("81030142 01820282 81830100 36064865"
                                       + "6C6C6F33 36064865 6C6C6F34 37010C");
        result &= response.checkSw("9000");

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "12CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"
                                   + "CCCCCC");
        
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
        
        return result;
    }
}
