//-----------------------------------------------------------------------------
//  Package Definition
//  Test Area: UICC CAT Runtime Environment Applet Triggering
//  EVENT_FIRST_COMMAND_AFTER_ATR
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_efca;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Efca extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_efca";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String CLASS_AID_2   = "A0000000 090005FF FFFFFF89 50020001";
    static final String CLASS_AID_3   = "A0000000 090005FF FFFFFF89 50030001";
    static final String CLASS_AID_4   = "A0000000 090005FF FFFFFF89 50040001";
    static final String CLASS_AID_5   = "A0000000 090005FF FFFFFF89 50050001";

    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static final String APPLET_AID_2  = "A0000000 090005FF FFFFFF89 50020102";
    static final String APPLET_AID_3  = "A0000000 090005FF FFFFFF89 50030102";
    static final String APPLET_AID_4  = "A0000000 090005FF FFFFFF89 50040102";
    static final String APPLET_AID_5  = "A0000000 090005FF FFFFFF89 50050102";

    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Cre_Apt_Efca() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {

        boolean result;

        // start test
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "8008"+ // TLV UICC Toolkit application specific parameters
                            "01"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "00"  + // V Maximum number of menu entries
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2,
                            "8008"+ // TLV UICC Toolkit application specific parameters
                            "02"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "00"  + // V Maximum number of menu entries
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services
        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3,
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "03"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "01"  + // V Maximum number of menu entries
                            "01"  + // V Position of the first menu entry
                            "01"  + // V Identifier of the first menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services

        // test script
        test.reset();
        //***TEST CASE 1: 1-APPLET 1 IS TRIGGERED BY EVENT_FIRST_COMMAND_AFTER_ATR,
        //***             2-APPLET 2 IS TRIGGERED BY EVENT_PROFILE_DONWLOAD
        //***             3_APPLET 3 IS NOT TRIGGERED
        test.terminalProfileSession("09010020 01");

        //***TEST CASE 1: 4-APPLET 3 IS TRIGGERED
        response   = test.envelopeMenuSelection("100101", "");//Help Request not available
        result     = response.checkSw("9000");

        test.reset();
        //***TEST CASE 2: 1-APPLET 3 IS TRIGGERED, APPLET 1, APPLET 2 ARE NOT TRIGGERED
        test.terminalProfileSession("09010020 01");
        test.installApplet(CAP_FILE_PATH, CLASS_AID_4, APPLET_AID_4,
                            "8008"+ // TLV UICC Toolkit application specific parameters
                            "03"  + // V Priority Level as Applet 3
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "00"  + // V Maximum number of menu entries
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services

        test.reset();
        //***TEST CASE 3: 1-APPLET 4 IS TRIGGERED BY THE EVENT_FIRST_COMMAND_AFTER_ATR
        //***             2-APPLET 3 IS TRIGGERED BY THE EVENT_FIRST_COMMAND_AFTER_ATR
        //***             3-APPLET 4 IS TRIGGERED BY THE EVENT_PROFILE_DONWLOAD
        //***             4-APPLET 3 IS TRIGGERED BY THE EVENT_PROFILE_DONWLOAD

        test.terminalProfileSession("09010020 01");
        response = test.selectApplication(APPLET_AID_1);
        result  &= response.checkData("10"+APPLET_AID_1+"03CCCCCC");
        response = test.selectApplication(APPLET_AID_2);
        result  &= response.checkData("10"+APPLET_AID_2+"03CCCCCC");
        response = test.selectApplication(APPLET_AID_3);
        result  &= response.checkData("10"+APPLET_AID_3+"05CCCCCC CCCC");
        response = test.selectApplication(APPLET_AID_4);
        result  &= response.checkData("10"+APPLET_AID_4+"02CCCC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deleteApplet(APPLET_AID_4);

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        //TEST CASE 4: INSTALL APPLET 5
        test.installApplet(CAP_FILE_PATH, CLASS_AID_5, APPLET_AID_5,
                            "800C"+ // TLV UICC Toolkit application specific parameters
                            "04"  + // V Priority Level
                            "00"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "02"  + // V Maximum number of menu entries
                            "01"  + // V Position of the first menu entry
                            "01"  + // V Identifier of the second menu entry
                            "02"  + // V Position of the second menu entry
                            "02"  + // V Identifier of the second menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00");  // V Maximum number of services

        test.reset();
        //***TEST CASE 4: 1-APPLET 5 IS TRIGGERED BY THE EVENT_FIRST_COMMAND_AFTER_ATR
        response = test.terminalProfile("09010020 01");
        result  &= response.checkSw("911E");
        //***TEST CASE 4: 1-APPLET 5 DISABLES A MENU ENTRY, THE FETCH OF SET UP MENU CONTAIN ONLY ONE ITEM
        response = test.fetch("1E");
        result  &= response.checkData("D01C8103 01250082 02818285 09554943" +
                                      "43205445 53548F06 014D656E 7531");

        // check results
        response = test.selectApplication(APPLET_AID_5);
        result  &= response.checkData("10"+APPLET_AID_5+"01CC");

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_5);
        test.deletePackage(CAP_FILE_PATH);

        return result;

    }
}