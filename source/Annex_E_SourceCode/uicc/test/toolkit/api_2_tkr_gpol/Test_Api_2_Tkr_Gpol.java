/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 3
 * Test source for ToolkitRegistry interface
 * getPollInterval() method
 */

package uicc.test.toolkit.api_2_tkr_gpol;

import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;


public class Test_Api_2_Tkr_Gpol extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_gpol";
    static final String CLASS_AID_1  = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 20010102";
   
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_2_Tkr_Gpol() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {
        initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession("0101");

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
            "8008" + // TLV UICC Toolkit application specific parameters
              "01" + // V Priority Level
              "00" + // V Max. number of timers
              "0F" + // V Maximum text length for a menu entry
              "00" + // V Maximum number of menu entries
              "00" + // V Maximum number of channels 
              "00" + // LV Minimum Security Level field
              "00" + // LV TAR Value(s) 
              "00"); // V Maximum number of services 

        // test script
        test.reset();
        test.terminalProfileSession("09017020");

        /** Test case 1
         *  Applet isn't registered to EVENT_STATUS_COMMAND.
         */
        test.unrecognizedEnvelope();

        /** Test case 2
         *  Requesting max duration.
         */
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("910F"));
        response = test.fetch("0F");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030103 00820282 81830100 840200FF");
        test.reset();
        response = test.terminalProfile("09017020");
        addResult(response.checkSw("910F"));
        response = test.fetch("0F");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030103 00820282 81830100 840200FF");
        test.unrecognizedEnvelope();

        /** Test case 3
         *  Requesting system duration.
         */
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("910F"));
        response = test.fetch("0F");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030103 00820282 81830100 8402011E");
        test.reset();
        response = test.terminalProfile("09017020");
        addResult(response.checkSw("910F"));
        response = test.fetch("0F");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030103 00820282 81830100 84020175");
        test.unrecognizedEnvelope();

        /** Test case 4
         *  Requesting no Duration.
         */
        response = test.unrecognizedEnvelope();
        addResult(response.checkSw("910B"));
        response = test.fetch("0B");
        addResult(response.checkSw("9000"));
        test.terminalResponse("81030104 00820282 81830100");
        test.reset();
        response = test.terminalProfile("09017020");
        addResult(response.checkSw("9000"));
        test.unrecognizedEnvelope();

        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "04CCCCCC CC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return getOverallResult();
    }
}
