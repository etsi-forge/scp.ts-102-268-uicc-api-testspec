//-----------------------------------------------------------------------------
//  Package Definition
//  Test Area: UICC CAT Runtime Environment Applet Triggering
//  EVENT_MENU_SELECTION_HELP_REQUEST
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_emsh;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Emsh extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_emsh";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String CLASS_AID_2   = "A0000000 090005FF FFFFFF89 50020001";
    static final String CLASS_AID_3   = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2  = "A0000000 090005FF FFFFFF89 50020102";
    static final String APPLET_AID_3  = "A0000000 090005FF FFFFFF89 50030102";

    private UiccAPITestCardService test;
    APDUResponse response;


    public Test_Cre_Apt_Emsh() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        test.initialiseResults();


        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                           "800C"+ // TLV UICC Toolkit application specific parameters
                           "01"  + // V Priority Level
                           "00"  + // V Max. number of timers
                           "0B"  + // V Maximum text length for a menu entry
                           "02"  + // V Maximum number of menu entries
                           "01"  + // V Position of the first menu entry
                           "01"  + // V Identifier of the first menu entry
                           "02"  + // V Position of the second menu entry
                           "02"  + // V Identifier of the second menu entry
                           "00"  + // V Maximum number of channels
                           "00"  + // LV Minimum Security Level field
                           "00"  + // LV TAR Value(s)
                           "00");  // V Maximum number of services
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                            "800C"+ // TLV UICC Toolkit application specific parameters
                            "03"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0B"  + // V Maximum text length for a menu entry
                            "02"  + // V Maximum number of menu entries
                            "03"  + // V Position of the third menu entry
                            "03"  + // V Identifier of the third menu entry
                            "04"  + // V Position of the fourth menu entry
                            "04"  + // V Identifier of the fourth menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services



        // test script
        test.reset();
        test.terminalProfileSession("09010020 01");
        //*** Test Case 1: 3-Applet1 is triggered, Applet2 is not triggered ****
        test.envelopeMenuSelection("100101", "1500");//Help Request
        //*** Test Case 1: 4-Applet1, Applet2 are not triggered ****
        test.envelopeMenuSelection("100102", "1500");//Help Request
        //*** Test Case 1: 5-Applet2 is triggered, Applet1 is not triggered ****
        test.envelopeMenuSelection("100103", "1500");//Help Request
        //*** Test Case 1: 6-Applet2, Applet1 are not triggered ****
        test.envelopeMenuSelection("100104", "1500");//Help Request
        // check results of Applet 1 and Applet2.
        response  = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10"+APPLET_AID_1+"03CCCCCC"));
        response  = test.selectApplication(APPLET_AID_2);
        test.addResult(response.checkData("10"+APPLET_AID_2+"03CCCCCC"));
        //Delete Applet 1 and Applet2.
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        //Install Applet 3
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                                    "800E"+ // TLV UICC Toolkit application specific parameters
                                    "03"  + // V Priority Level
                                    "00"  + // V Max. number of timers
                                    "0B"  + // V Maximum text length for a menu entry
                                    "03"  + // V Maximum number of menu entries
                                    "05"  + // V Position of the fifth menu entry
                                    "05"  + // V Identifier of the fifth menu entry
                                    "06"  + // V Position of the sixth menu entry
                                    "06"  + // V Identifier of the sixth menu entry
                                    "07"  + // V Position of the seventh menu entry
                                    "07"  + // V Identifier of the seventh menu entry
                                    "00"  + // V Maximum number of channels
                                    "00"  + // LV Minimum Security Level field
                                    "00"  + // LV TAR Value(s)
                                    "00");  // V Maximum number of services
        test.reset();
        test.terminalProfile("09010020 01");
        //UICC proactive command SET UP MENU, Menu Entry ID 05, 06, 07, Help Request supported
        response = test.fetch("37");
        test.addResult(response.checkData("D0358103 01258082 02818285 09554943" +
                                      "43205445 53548F09 05417070 6C657433" +
                                      "418F0906 4170706C 65743342 8F090741" +
                                      "70706C65 743343"));
        test.terminalResponse("81030125 80820282 81830100");
        //*** Test Case 2: 2-Applet3 is triggered ****
        test.envelopeMenuSelection("100105", "1500");//Help Request
        response = test.fetch("2C");
        test.addResult(response.checkData("D02A8103 01258082 02818285 09554943" +
                                      "43205445 53548F09 06417070 6C657433" +
                                      "428F0907 4170706C 65743343"));
        test.terminalResponse("81030125 80820282 81830100");
        //*** Test Case 2: 4-Applet3 is triggered ****
        test.envelopeMenuSelection("100106", "1500");//Help Request
        response = test.fetch("21");
        test.addResult(response.checkData("D01F8103 01250082 02818285 09554943" +
                                      "43205445 53548F09 07417070 6C657433" +
                                      "43"));
        response = test.terminalResponse("81030125 00820282 81830100");                                        
        test.addResult(response.checkSw("9000"));


        // check results
        response = test.selectApplication(APPLET_AID_3);
        test.addResult(response.checkData("10"+APPLET_AID_3+"04CCCCCC CC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
