//-----------------------------------------------------------------------------
//Test_Api_2_Pah_Icch.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_icch;

import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_icch
 *
 * @version 0.0.1 - 20 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Pah_Icch extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_pah_icch";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
          
    public Test_Api_2_Pah_Icch() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        test.initialiseResults();
        
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
        test.addResult(response.checkSw("911A"));
        
        // Fetch the Open Channel proactive command
        response = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566"
                                  + "77883502 03003902 000A"));
        // Successful terminal response
        test.terminalResponse("81030140 01820282 81830100 38028100" 
                            + "35020300 3902000A");

        // Send an EVENT_DOWNLOAD_CHANNEL_STATUS Envelope
        response = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("910B"));
        // Fetch the close channel proactive command
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028121"));
        
        // Successful terminal response
        response = test.terminalResponse("81030141 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // Send an EVENT_DOWNLOAD_CHANNEL_STATUS Envelope
        response = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("9000"));
        
        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("911A"));
        
        // Fetch the open channel proactive command
        response = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566"
                                   + "77883502 03003902 000A"));
        // Successful terminal response
        test.terminalResponse("81030140 01820282 81830100 38028100" 
                            + "35020300 3902000A");

        // Send an EVENT_DOWNLOAD_CHANNEL_STATUS Envelope
        response = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("910B"));
        // Fetch the close channel proactive command
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028121"));

        // Successful terminal response
        response = test.terminalResponse("81030141 00820282 81830100");
        test.addResult(response.checkSw("9000"));
        
        // Send an EVENT_DOWNLOAD_CHANNEL_STATUS Envelope
        response = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("9000"));

        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("911A"));
        
        // Fetch the open channel proactive command
        response = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566"
                                   + "77883502 03003902 000A"));
        // Successful terminal response
        test.terminalResponse("81030140 01820282 81830100 38028100" 
                            + "35020300 3902000A");

        // Send an EVENT_DOWNLOAD_CHANNEL_STATUS Envelope
        response = test.envelopeEventDownloadChannelStatus("38028100");
        test.addResult(response.checkSw("910B"));
        // Fetch the close channel proactive command
        response = test.fetch("0B");
        test.addResult(response.checkData("D0098103 01410082 028121"));

        // Successful terminal response
        response = test.terminalResponse("81030141 00820282 81830100");
        test.addResult(response.checkSw("9000"));

        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/

        response = test.unrecognizedEnvelope();
        test.addResult(response.checkSw("911A"));
        
        // Fetch the open channel proactive command
        response = test.fetch("1A");
        test.addResult(response.checkData("D0188103 01400182 02818206 05815566"
                                   + "77883502 03003902 000A"));
        // Successful terminal response
        test.terminalResponse("81030140 01820282 81830100 38028100" 
                            + "35020300 3902000A");

        // Send an EVENT_DOWNLOAD_CHANNEL_STATUS Envelope
        response = test.envelopeEventDownloadChannelStatus("38028100");
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
