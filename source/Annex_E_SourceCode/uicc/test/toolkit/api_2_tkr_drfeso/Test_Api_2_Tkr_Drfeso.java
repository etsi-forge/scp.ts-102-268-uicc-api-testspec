/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 2
 * Test source for ToolkitRegistry interface
 * deregisterFileEvent() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_drfeso;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Drfeso extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_drfeso";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 20010102";

    static final String AID_ADF_1     = "A0000000 090005FF FFFFFF89 E0000002";
    
    static final String MF          = "3F00";
    static final String ADF         = "7FFF";
    static final String DF_TEST     = "7F4A";
    static final String EF_TARU     = "6F03";
    static final String EF_CARU     = "6F09";
    static final String EF_LARU     = "6F0C";

    
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Drfeso() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        test.initialiseResults();
        
        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applets
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1,
                            "8008" + // TLV UICC Toolkit application specific parameters
                            "01" +   // V Priority Level
                            "00" +   // V Max. number of timers
                            "0A" +   // V Maximum text length for a menu entry
                            "00" +   // V Maximum number of menu entries
                            "00" +   // V Maximum number of channels 
                            "00" +   // LV Minimum Security Level field
                            "00" +   // LV TAR Value(s) 
                            "00" +   // V Maximum number of services
                            "8118" + // TLV UICC Access Application specific parameters
                            "00" +   // LV UICC file system AID
                            "0100" + // LV UICC file system access aomain
                            "00" +   // LV UICC file system access domain DAP
                            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 AID
                            "0100" + // LV ADF1 access domain
                            "00");   // LV ADF1 access domain DAP
                    
        // test script
        test.reset();
        response = test.terminalProfileSession("09030120");
        

        /** Testcase 1 *****************************************
         *  Deregister EF under MF
         *******************************************************/

        test.unrecognizedEnvelope();

        // update binary EF_TARU and check that applet is correctly triggered
        modifyEFandCheck(MF, EF_TARU, true);
        modifyEFandCheck(MF, EF_CARU, true);
        modifyEFandCheck(MF, EF_LARU, true);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(MF, EF_TARU, false);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(MF, EF_CARU, false);
        
        test.unrecognizedEnvelope();
    
        modifyEFandCheck(MF, EF_LARU, false);
            
        test.unrecognizedEnvelope();
        
        
        /** Testcase 2 *****************************************
         *  Deregister DF under MF
         *******************************************************/

        test.unrecognizedEnvelope();

        modifyEFandCheck(MF, EF_TARU, true);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(MF, EF_TARU, true);
        
        
        /** Testcase 3 *****************************************
         *  Deregister EF does not affect parent DF
         *******************************************************/
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(MF, EF_TARU, true);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(MF, EF_TARU, true);
        modifyEFandCheck(MF, EF_LARU, true);
        
        
        /** Testcase 4 *****************************************
         *  Deregister EF under ADF1
         *******************************************************/
        
        test.unrecognizedEnvelope();

        modifyEFandCheck(ADF, EF_TARU, true);
        modifyEFandCheck(ADF, EF_CARU, true);
        modifyEFandCheck(ADF, EF_LARU, true);

        test.unrecognizedEnvelope();

        modifyEFandCheck(ADF, EF_TARU, false);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_CARU, false);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_LARU, false);
        
        
        /** Testcase 5 *****************************************
         *  Deregister DF does not affect child EF (under ADF1)
         *******************************************************/
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_TARU, true);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_TARU, true);
        
        
        /** Testcase 6 *****************************************
         *  Deregister EF does not affect parent DF (under ADF1)
         *******************************************************/
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_TARU, true);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_TARU, true);
        modifyEFandCheck(ADF, EF_LARU, true);


        
        /*******************************************************
         *  Restore EF_CARU under MF and ADF
         *******************************************************/
                
        // restore EF_CARU
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        test.updateRecord("00", "03", "AAAAAA");
        test.updateRecord("00", "03", "555555");

        test.selectApplication(AID_ADF_1);
        test.selectFile(ADF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        test.updateRecord("00", "03", "AAAAAA");
        test.updateRecord("00", "03", "555555");
        
                
        /** Testcase 7 - 12 **********************************
         *  7. NullPointerException Exception
         *  8. EVENT_MENU_SELECTION not allowed
         *  9. EVENT_MENU_SELECTION_HELP_REQUEST not allowed
         *  10. EVENT_TIMER_EXPIRATION not allowed
         *  11. EVENT_STATUS_COMMAND not allowed
         *  12. EVENT_NOT_SUPPORTED Exception 
         *****************************************************/

        test.unrecognizedEnvelope();
                
        
        /*******************************************************
         *  Finish test
         *******************************************************/
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 +
                                     "0CCCCCCC CCCCCCCC CCCCCCCC CC"));
        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return test.getOverallResult();
    }
    
    
    /** 
     * Check the File List which caused the latest EXTERNAL_FILE_UDPATE
     * and compare it whith the File List given in parameter.
     */
    
    private void appletTriggered(String data) {
        response = test.envelopeCallControlByNAA();
        test.addResult(response.checkData(data));
    }
    

    /** 
     * Perform an action on the specified EF in order to test 
     * if the test applet is triggered with an EXTERNAL_FILE_UDPATE event.
     */
    
    private void modifyEFandCheck(String mfadf, String fid, boolean shouldTrigger) {
        
        // select correct file
        if (mfadf == ADF) test.selectApplication(AID_ADF_1);
        test.selectFile(mfadf);
        test.selectFile(DF_TEST);
        test.selectFile(fid);

        // modify the EF  
        if      (fid == EF_TARU) test.updateBinary("0000", "FFFFFF");
        else if (fid == EF_LARU) test.updateRecord("01","04","55555555");
        else if (fid == EF_CARU) test.increase("000001");

        
        // create the string to pass to appletTriggered()
        String checkString = ("01" + MF);
        if (mfadf == ADF) checkString += ADF;

        if      (!shouldTrigger) checkString = "000100";
        else if (fid == EF_TARU) checkString += (DF_TEST + EF_TARU);
        else if (fid == EF_CARU) checkString += (DF_TEST + EF_CARU);
        else if (fid == EF_LARU) checkString += (DF_TEST + EF_LARU);
        
        appletTriggered(checkString);
    }
    
}

