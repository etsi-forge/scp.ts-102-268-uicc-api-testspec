//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Gcst.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gcst;

import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.UiccCardManagementService;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Gcst
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Gcst extends UiccTestModel
{
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_enh_gcst";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Gcst() {
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
                           "800A" + // TLV UICC Toolkit application specific parameters
                           "01" +   // V Priority Level
                           "00" +   // V Max. number of timers
                           "10" +   // V Maximum text length for a menu entry
                           "01" +   // V Maximum number of menu entries
                           "0101" + // Position and Id of the first item
                           "01" +   // V Maximum number of channels 
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
        test.terminalProfileSession("1FF0FFFF 00FF0000 010000 1F");
        
        // 0: Send Envelope Menu Selection with Item Identifier set to 01
        //    to open a channel
        response = test.envelopeMenuSelection("10 01 01","");
        
        // Fetch the first OPEN CHANNEL proactive command
        response = test.fetch("1C");
        result = response.checkData("D01A8103 01400182 02818206 05911122"
                                  + "33443504 01000000 39020080");
        
        test.terminalResponse("81030140 01820282 81030100 38028100"
                            + "35040100 00003902 0080");
        
        // 1 : Trig the applet with Unrecognized Envelope with no Channel Status TLV 
        test.sendApdu("80C20000 09010799 010A8202 8281");
        
        // 2 : Trig the applet with Unrecognized envelope with the expected Identifier not present 
        test.sendApdu("80C20000 0D010B99 010A8202 82813802 8200");
        
        // 3 : Send an Unrecognized envelope with channel status TLV length set to 0
        test.sendApdu("80C20000 0B010999 010A8202 82813800");
        
        // 4 : Send an Unrecognized Envelope with channel length set to 01
        test.sendApdu("80C20000 0C010A99 010A8202 82813801 81");
        
        // 5 : Send envelope EvenDownloadChannelStatus with channel status value set to 0x8100
        test.envelopeEventDownloadChannelStatus("38028100");

        // 6 : Send envelope EvenDownloadChannelStatus with two TLV
        test.envelopeEventDownloadChannelStatus("38028100 38028101");
        
        // 7 : Channel Status TLV is the curently selected TLV
        test.envelopeEventDownloadChannelStatus("38028100");
        
        // 8 : Get channel status value after proactive command
        test.envelopeEventDownloadChannelStatus("38028100");
        // Fetch the display text proactive command
        response = test.fetch("15");
        result &= response.checkData("D0138103  01210082  0281028D  08F64150"
                                   + "504C4554  31");
        test.terminalResponse("81030121  00020282  81030100");

        /*********************************************************************/
        /*********************************************************************/
        /** Check result                                                     */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + 
                                     "08CCCCCC CCCCCCCC CC");
        
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
