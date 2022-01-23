//-----------------------------------------------------------------------------
//  Package Definition
//  Test Area: UICC CAT Runtime Environment Applet Triggering
//  EVENT_EXTERNAL_FILE_UPDATE
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_apt_eefu;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;

public class Test_Cre_Apt_Eefu extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_apt_eefu";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 50010102";
    static final String FID_DF_TEST   = "7F4A";
    static final String FID_EF_TARU   = "6F03";
    static final String FID_EF_CARU   = "6F09";
    static final String FID_EF_LARU   = "6F0C";
    static final String data3_1       = "111213";
    static final String data3_2       = "212223";
    static final String data4_1       = "A1A2A3A4";
    static final String data4_2       = "B1B2B3B4";
    static final String data5_1       = "A1A2A3A4A5";
    static final String data5_2       = "B1B2B3B4B5";
    static final String data5_3       = "C1C2C3C4C5";





    private UiccAPITestCardService test;
    APDUResponse response;
    String data5_1_ToRestore;
    String data4_1_ToRestore;
    String data3_1_ToRestore;
    String data3_2_ToRestore;



    public Test_Cre_Apt_Eefu() {
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
                            "800A"+ // TLV UICC Toolkit application specific parameters
                            "01"  + // V Priority Level
                            "08"  + // V Max. number of timers
                            "0A"  + // V Maximum text length for a menu entry
                            "01"  + // V Maximum number of menu entries
                            "01"  + // V Position of the first menu entry
                            "01"  + // V Identifier of the first menu entry
                            "00"  + // V Maximum number of channels
                            "00"  + // LV Minimum Security Level field
                            "00"  + // LV TAR Value(s)
                            "00"  +  // V Maximum number of services                           
                            "8118" + // TLV UICC Access application specific parameters
                            "00" +   // LV UICC File System AID field
                            "0100" + // LV Access Domain for UICC file system = Full Access
                            "00" +   // LV Access Domain DAP field
                            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
                            "0100" + // LV Access Domain for ADF1 file system = Full Access
                            "00" );   // LV Access Domain DAP field


        // test script
        test.reset();
        test.terminalProfileSession("09010000");

        //***TEST CASE 1: 1-APPLET IS TRIGGERED
        test.envelopeMenuSelection("100101", "");//Help Request not available
        test.selectFile(FID_DF_TEST);

        //***TEST CASE 1: 6-APPLET IS TRIGGERED
        test.selectFile(FID_EF_TARU);
        data5_1_ToRestore=test.readBinary("0000","05").getData();
        test.updateBinary("0000", data5_1);

        //***TEST CASE 1: 7-APPLET IS TRIGGERED
        test.selectFile(FID_EF_LARU);
        data4_1_ToRestore=test.readRecord("01","04","04").getData();
        test.updateRecord("01", "04", data4_1);

        //***TEST CASE 1: 8-APPLET IS TRIGGERED
        test.selectFile(FID_EF_CARU);
        data3_1_ToRestore=test.readRecord("00","03","03").getData();
        data3_2_ToRestore=test.readRecord("00","03","03").getData();


        //Must be correct in increase
        //test.updateRecord("01", "03", data3);

        test.sendApdu("8032000003" + data3_1);

        //***TEST CASE 2: 1-APPLET IS TRIGGERED
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_1);

         //***TEST CASE 2: 4-APPLET IS NOT TRIGGERED
        test.selectFile(FID_EF_CARU);
        //Must be correct in increase
        //test.updateRecord("01", "03", data3);
        test.sendApdu("8032000003" + data3_2);

        //***TEST CASE 2: 5-APPLET IS TRIGGERED
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_2);

        //***TEST CASE 2: 8-APPLET IS NOT TRIGGERED
        test.selectFile(FID_EF_LARU);
        test.updateRecord("01", "04", data4_2);

        //***TEST CASE 2: 9-APPLET IS TRIGGERED
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_3);

        //***TEST CASE 2: 12-APPLET IS NOT TRIGGERED
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_3);

         //***TEST CASE 3: 1-APPLET IS TRIGGERED
        test.envelopeMenuSelection("100101", "");//Help Request not available

        //***TEST CASE 3: 4-APPLET IS TRIGGERED
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_1);

        //***TEST CASE 3: 5-APPLET IS TRIGGERED
        test.selectFile(FID_EF_LARU);
        test.updateRecord("01", "04", data4_1);

        //***TEST CASE 3: 6-APPLET IS TRIGGERED
        test.selectFile(FID_EF_CARU);
        //Must be correct in increase
        //test.updateRecord("01", "03", data3);
        test.sendApdu("8032000003" + data3_1);

        //***TEST CASE 3: 9-APPLET IS NOT TRIGGERED
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_1);

        //***TEST CASE 3: 10-APPLET IS NOT TRIGGERED
        test.selectFile(FID_EF_LARU);
        test.updateRecord("01", "04", data4_1);

        //***TEST CASE 3: 11-APPLET IS NOT TRIGGERED
        test.selectFile(FID_EF_CARU);
        //Must be correct in increase
        //test.updateRecord("01", "03", data3);
        test.sendApdu("8032000003" + data3_1);


        //RESTORE EF_TARU, EF_LARU, EF_CARU
        test.selectFile(FID_EF_TARU);
        test.updateBinary("0000", data5_1_ToRestore);

        test.selectFile(FID_EF_LARU);
        test.updateRecord("01", "04", data4_1_ToRestore);

        test.selectFile(FID_EF_CARU);
        test.updateRecord("00", "03", data3_1_ToRestore);
        test.updateRecord("00", "03", data3_2_ToRestore);







        // check results
        response  = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10"+APPLET_AID_1+"1FCCCCCC CCCCCCCC"+
                                       "CCCCCCCC CCCCCCCC CCCCCCCC CCCCCCCC"+
                                       "CCCCCCCC CCCCCCCC"));

        // delete applet and package
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}

