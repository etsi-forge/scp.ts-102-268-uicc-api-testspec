//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Gcid.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gcid;

import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Gcid
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Gcid extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_enh_gcid";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Gcid() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession("1FF0FFFF 00FF0000 010000 1F40");

        /*********************************************************************/
        /** Testcase 0                                                       */
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
                           "0101" + // Position and Id of the first item
                           "07" +   // V Maximum number of channels 
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
        test.terminalProfileSession("1FF0FFFF 00FF0000 010000 1F40");

        // 0: Send Envelope Menu Selection with Item Identifier set to 01
        //    to open 7 channels
        response = test.envelopeMenuSelection("10 01 01","");

        // Fetch the first OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                  + "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028100"
                            + "35040100 00003902 0080");
        
        // Fetch the second OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                  +  "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028200"
                           +  "35040100 00003902 0080");        
        
        // Fetch the third OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                  + "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028300"
                           +  "35040100 00003902 0080");        

        // Fetch the fourth OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028400"
                            + "35040100 00003902 0080");        

        // Fetch the fifth OPEN CHANNEL proactive command
        response = test.fetch("1C");        
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028500"
                            + "35040100 00003902 0080");        

        // Fetch the sixth OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                   + "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028600"
                           +  "35040100 00003902 0080");        
        
        // Fetch the seventh OPEN CHANNEL proactive command
        response = test.fetch("1C");
        addResult(response.checkData("D01A8103 01400182 02818206 05911122"
                                  +  "33443504 01000000 39020080"));
        test.terminalResponse("81030140 01820282 81030100 38028700"
                           +  "35040100 00003902 0080");        
        
        // 1 : Trig the applet with EventDownloadChannelStatus with channel status value = 0x8100 
        test.envelopeEventDownloadChannelStatus("38028100");
        
        // 2 : Trig the applet with EventDownloadChannelStatus with channel status value = 0x8400 and 0x8500 
        test.envelopeEventDownloadChannelStatus("38028400 38028500");
        
        // 3 : Send envelope EvenDownloadChannelStatus with channel status value = 0x0605
        test.envelopeEventDownloadChannelStatus("38020605");
        
        // 4 : Send unrecognized envelope with no channel status TLV
        test.unrecognizedEnvelope();
        
        // 5 : Send envelope EvenDownloadChannelStatus with channel status value = 0x0600
        test.envelopeEventDownloadChannelStatus("38020600");
        
        // 5 : Send unrecognized envelope with channel status TLV length set to 0
        test.sendApdu("80C20000 08010682 02828138 00");
              
        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1
                                   + "06CCCCCC CCCCCC"));

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
        
        return getOverallResult();
    }

}
