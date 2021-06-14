/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 2
 * Test source for ToolkitRegistry interface
 * deregisterFileEvent() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_drfes_bss_bsb;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Drfes_Bss_Bsb extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_drfes_bss_bsb";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 20010102";

    static final String AID_ADF_1     = "A0000000 090005FF FFFFFF89 E0000002";
    
    static final String MF          = "3F00";
    static final String ADF         = "7FFF";
    static final String DF_TEST     = "7F4A";
    static final String DF_SUB_TEST = "5F10";
    static final String EF_TAA      = "4F10";
    static final String EF_TARU     = "6F03";
    static final String EF_CARU     = "6F09";
    static final String EF_LARU     = "6F0C";

     
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Drfes_Bss_Bsb() {
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
        modifyEFandCheck(MF, EF_CARU, false);
        modifyEFandCheck(MF, EF_LARU, true);
        
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
        modifyEFandCheck(ADF, EF_CARU, false);
        modifyEFandCheck(ADF, EF_LARU, true);
        
        test.unrecognizedEnvelope();
        
        modifyEFandCheck(ADF, EF_LARU, false);
        
        test.unrecognizedEnvelope();
        
        
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
        
        
        /** Testcase 7 - 20 **********************************
         *  7. NullPointerException Exception
         *  8. sOffset1 >= baFileList.length
         *  9. sOffset1 < 0
         *  10. sLength1 > baFileList.length
         *  11. sOffset1 + sLength1 > baFileList.length
         *  12. sLength1 < 0
         *  13. sOffset2 >= baFileList.length
         *  14. sOffset2 < 0
         *  15. sLength2 > baFileList.length
         *  16. sOffset2 + sLength2 > baFileList.length
         *  17. ILLEGAL_VALUE Exception
         *  18. EVENT_MENU_SELECTION not allowed
         *  19. EVENT_MENU_SELECTION_HELP_REQUEST not allowed
         *  20. EVENT_TIMER_EXPIRATION not allowed
         *  21. EVENT_STATUS_COMMAND not allowed
         *  22. EVENT_NOT_SUPPORTED Exception 
         *****************************************************/

        for (int i=7; i<=22; i++ ) {
            test.unrecognizedEnvelope();
        }

        
        
        /*****************************************************
         *  Restore modified files
         *****************************************************/
        
        // restore EF_CARU under ADF
        test.selectApplication(AID_ADF_1);
        test.selectFile(ADF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        test.updateRecord("00", "03", "AAAAAA");
        test.updateRecord("00", "03", "555555");

        // restore EF_CARU under MF        
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        test.updateRecord("00", "03", "AAAAAA");
        test.updateRecord("00", "03", "555555");

        
        /*****************************************************
         *  Finish test
         *****************************************************/
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 +
                                     "16CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC CCCCCC"));
        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return getOverallResult();
    }
    

    /** 
     * Check the File List which caused the latest EXTERNAL_FILE_UDPATE
     * and compare it whith the File List given in parameter.
     */
    
    private void appletTriggered(String data) {
        response = test.envelopeCallControlByNAA();
        addResult(response.checkData(data));
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
        if (fid == EF_TAA) test.selectFile(DF_SUB_TEST);
        test.selectFile(fid);

        // modify the EF  
        if      (fid == EF_CARU) test.increase("000001");
        else if (fid == EF_LARU) test.updateRecord("01","04","55555555");
        else    test.updateBinary("0000", "FFFFFF");
        
        // create the string to pass to appletTriggered()
        String checkString = ("01" + MF);
        if (mfadf == ADF) checkString += ADF;

        if      (!shouldTrigger) checkString = "000100";
        else if (fid == EF_TARU) checkString += (DF_TEST + EF_TARU);
        else if (fid == EF_CARU) checkString += (DF_TEST + EF_CARU);
        else if (fid == EF_LARU) checkString += (DF_TEST + EF_LARU);
        else if (fid == EF_TAA)  checkString += (DF_TEST + DF_SUB_TEST + EF_TAA);
        
        appletTriggered(checkString);
    }
}

