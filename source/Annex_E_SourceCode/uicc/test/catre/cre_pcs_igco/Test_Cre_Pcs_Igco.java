//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_igco;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Pcs_Igco extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_pcs_igco";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Pcs_Igco() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
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
                               "00" );  // V Maximum number of services
        
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        response = test.terminalProfile("09010020");
        // SW1 shall be 91
        result = response.checkSw("9126");
        
        // select MF
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9126");

        // select failed
        response = test.selectFile("03FF");
        // SW = 6A82
        result &= response.checkSw("6A82");
        
        // Fetch the Set Up Menu
        response = test.fetch("26");
        result &= response.checkData("D0248103 01250082 02818285 09554943" +
                                     "43205445 53548F06 014D656E 75318F06" +
                                     "024D656E 7532");
        
        // select MF
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9000");
       
        // Terminal Response
        response = test.terminalResponse("81030125 00820282 81830100");
        result &= response.checkSw("9000");


        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        // SW1 shall be 91
        result = response.checkSw("9114");
        
        // select MF
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9114");

        // select failed
        response = test.selectFile("03FF");
        // SW = 6A82
        result &= response.checkSw("6A82");
        
        // Fetch display Text
        response = test.fetch("14");
        result &= response.checkData("D0128103 01210082 0281028D 07045465" +
                                     "73742041");
        
        // select MF
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9000");
       
        // Terminal Response
        response = test.terminalResponse("81030121 00820282 81830100");
        result &= response.checkSw("9000");

        
        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Trigger Applet1 
        response = test.envelopeMenuSelection("100102","");

        // select MF (Testcase 3-1)
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9114");
        // Fetch display Text
        response = test.fetch("14");
        result &= response.checkData("D0128103 01210082 0281028D 07045465" +
                                     "73742042");
        
        // select MF (Testcase 3-4)
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9000");
        // select failed
        response = test.selectFile("03FF");
        // SW = 6A82
        result &= response.checkSw("6A82");
        // Terminal Response
        response = test.terminalResponse("81030121 00820282 81830100");
        result &= response.checkSw("9114");

        // select MF (Testcase 3-8)
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9114");
        // select failed
        response = test.selectFile("03FF");
        // SW = 6A82
        result &= response.checkSw("6A82");
        // Fetch display Text
        response = test.fetch("14");
        result &= response.checkData("D0128103 01210082 0281028D 07045465" +
                                     "73742043");

        // select MF (Testcase 3-12)
        response = test.selectFile("3F00");
        result &= response.getData().regionMatches(16, "3F00", 0, 4);
        result &= response.checkSw("9000");
        // Terminal Response
        response = test.terminalResponse("81030121 00820282 81830100");
        result &= response.checkSw("9000");


        /*********************************************************************/
        /*********************************************************************/
        /** Check Applet                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "02" + "CCCC");
                                     
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
