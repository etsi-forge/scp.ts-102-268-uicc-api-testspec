//-----------------------------------------------------------------------------
//Test_Api_2_Enh_Giid.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_giid;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Giid
 *
 * @version 0.0.1 - 28 oct. 2004
 * @author 3GPP T3 SWG API
 */
public class Test_Api_2_Enh_Giid extends UiccTestModel
{

    static final String CAP_FILE_PATH = "uicc/test/toolkit/Api_2_Enh_Giid";
    static final String CLASS_AID_1 =  "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Api_2_Enh_Giid() {
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
                           "8012" + // TLV UICC Toolkit application specific parameters
                           "01" +   // V Priority Level
                           "00" +   // V Max. number of timers
                           "10" +   // V Maximum text length for a menu entry
                           "05" +   // V Maximum number of menu entries
                           "0101" + // Position and Id of the first item
                           "0202" + // Position and Id of the first item
                           "0303" + // Position and Id of the first item
                           "0404" + // Position and Id of the first item
                           "0566" + // Position and Id of the first item
                           "00" +   // V Maximum number of channels 
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
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // 1: Send Envelope Menu Selection with Item Identifier set to 03
        response = test.envelopeMenuSelection("100103","");
        
        // 2 : Send Envelope Menu Selection with two Item Identifier TLV
        response = test.envelopeMenuSelection("10010210 0101","");
        
        // 3 : Send Envelope Menu Selection with two Item Identifier TLV
        response = test.envelopeMenuSelection("90010410 0101","");
        
        // 4 : Send Envelope Menu Selection Item Identifier set to 66
        response = test.envelopeMenuSelection("900166","");
        
        // 5 : Send Unrecognized Envelope
        test.unrecognizedEnvelope();
        
        // 6 : Send Envelope Menu Selection with item identifier  set to 66
        response = test.envelopeMenuSelection("100166","");
        
        // Fetch the proactive command
        response = test.fetch("13");
        result = response.checkData("D0118103 01218082 0281028D 06044845"
                                  + "4C4C4F");
        test.terminalResponse("81030121 80020282 81030100");
        
        // 7 : Send Unrecognized envelope with item Identifier TLV but without Item Number
        response = test.sendApdu("80C20000 08010682 02018110 00");
        result &= response.checkSw("9000");

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 
                                   + "07CCCCCC CCCCCCCC");
        
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
