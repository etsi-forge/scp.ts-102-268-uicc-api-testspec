//-----------------------------------------------------------------------------
//Test_Api_2_Bte_Aptlbbb.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_aptlbbb;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;


public class Test_Api_2_Bte_Aptlbbb extends UiccTestModel{
    /** relative path of the package */
    private static String			CAP_FILE_PATH		= "uicc/test/toolkit/api_2_bte_aptlbbb";
    /** test applet 1 class AID */
    private static String			CLASS_AID_1			= "A0000000 090005FF FFFFFF89 20010001";
    /** test applet 1 instance aid */
    private static String			APPLET_AID_1		= "A0000000 090005FF FFFFFF89 20010102";
    /** */
    private static String			TERMINAL_PROFILE	= "09030120 00000000 00000000 FF";
    /**  */
    private UiccAPITestCardService	test				= null;
    /** contains the response from the executed command */
    private APDUResponse			response			= null;
    /** stores the test result */
    private boolean					testresult			= false;

    /**
     *
     */
    public Test_Api_2_Bte_Aptlbbb(){
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    /**
     * Installs the applet, runs the tests and checks the test result.
     */
    public boolean run(){
         test.reset();
         test.terminalProfileSession("13");
        //Install package
        test.loadPackage(CAP_FILE_PATH);
        //install applet 1
        response = test.installApplet(
                            CAP_FILE_PATH,
                            CLASS_AID_1,
                            APPLET_AID_1,
                               "8008" 	+ 	// TLV UICC Toolkit application specific parameters
                               "01" 	+   // V Priority Level
                               "00" 	+   // V Max. number of timers
                               "0A" 	+   // V Maximum text length for a menu entry
                               "00" 	+   // V Maximum number of menu entries
                               "00" 	+   // V Maximum number of channels
                               "00" 	+   // LV Minimum Security Level field
                               "00" 	+   // LV TAR Value(s)
                               "00" 	); 	// V Maximum number of services


        test.reset();
        test.terminalProfileSession(TERMINAL_PROFILE);
         // unrecognizeEnvelope trigger test case 1 to 12 of applet 1
        test.unrecognizedEnvelope();
        // check test results
        response = test.selectApplication(APPLET_AID_1);
        testresult =  response.checkData("10"+APPLET_AID_1+
                                            "05CCCCCCCC CC");
         // delete applet and package
        test.reset();
        test.terminalProfileSession("13");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return testresult;
    }

}