/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 2
 * Test source for ToolkitRegistry interface
 * registerFileEvent() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rgfes_bss_bsb;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Rgfes_Bss_Bsb extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_rgfes_bss_bsb";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 20010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 20010102";

    static final String AID_ADF_1     = "A0000000 090005FF FFFFFF89 E0000002";
    
    static final String MF          = "3F00";
    static final String ADF         = "7FFF";
    static final String DF_TEST     = "7F4A";
    static final String DF_SUB_TEST = "5F10";
    static final String DF_NEW      = "5F16";
    static final String EF_TAA      = "4F10";
    static final String EF_TNEW     = "4F16";
    static final String EF_TARU     = "6F03";
    static final String EF_CARU     = "6F09";
    static final String EF_LARU     = "6F0C";

    static final String fcp_EF_TARU     = "8202 41218302 6F038A01 058B032F 06018002 0104";
    static final String fcp_EF_TAA      = "8202 41218302 4F108A01 058B032F 06018001 03";
    static final String fcp_EF_TNEW     = "8202 41218302 4F168A01 058B032F 06018001 03";
                                                                            
    static final String fcp_DF_NEW      = "8202 78218302 5F168A01 058B032F 06018102 0200C606 90018083 0101";
    static final String fcp_DF_SUB_TEST = "8202 78218302 5F108A01 058B032F 06018102 0200C606 90018083 0101";
    
    
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Rgfes_Bss_Bsb() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        
        boolean result = true;
        
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
        

        /** Testcase 1 & 2 *************************************
         *  Register EF under MF
         *  Register DF under MF
         *******************************************************/

        for (int i=0; i < 2; i ++) {
            test.unrecognizedEnvelope();
    
            result &= modifyEFandCheck(MF, EF_TARU, true); 
            result &= modifyEFandCheck(MF, EF_CARU, true); 

            if (i == 0)
                result &= modifyEFandCheck(MF, EF_LARU, false); 
            else
                result &= modifyEFandCheck(MF, EF_LARU, true); 
            
            result &= modifyEFandCheck(MF, EF_TAA, false); 
            
            test.unrecognizedEnvelope();
        }
        

        
        /** Testcase 3 & 4 *************************************
         *  Register EF under ADF1
         *  Register DF under ADF1
         *******************************************************/
        
        for (int i=0; i < 2; i ++) {
            test.unrecognizedEnvelope();
            
            result &= modifyEFandCheck(ADF, EF_TARU, true); 
            result &= modifyEFandCheck(ADF, EF_CARU, true); 
            
            if (i == 0)
                result &= modifyEFandCheck(ADF, EF_LARU, false); 
            else
                result &= modifyEFandCheck(ADF, EF_LARU, true); 
            
            result &= modifyEFandCheck(ADF, EF_TAA, false); 
    
            test.unrecognizedEnvelope();
        }
        
        /** Testcase 5 - 20 **********************************
         *  5. NullPointerException Exception
         *  6. sOffset1 >= baFileList.length
         *  7. sOffset1 < 0
         *  8. sLength1 > baFileList.length
         *  9. sOffset1 + sLength1 > baFileList.length
         *  10. sLength1 < 0
         *  11. sOffset2 >= baFileList.length
         *  12. sOffset2 < 0
         *  13. sLength2 > baFileList.length
         *  14. sOffset2 + sLength2 > baFileList.length
         *  15. ILLEGAL_VALUE Exception
         *  16. EVENT_MENU_SELECTION not allowed
         *  17. EVENT_MENU_SELECTION_HELP_REQUEST not allowed
         *  18. EVENT_TIMER_EXPIRATION not allowed
         *  19. EVENT_STATUS_COMMAND not allowed
         *  20. EVENT_NOT_SUPPORTED Exception 
         *****************************************************/

        for (int i=5; i<=20; i++ ) {
            test.unrecognizedEnvelope();
        }

        
        /** Testcase 21 **************************************
         *  Register a deleted and recreated EF under MF
         *****************************************************/
        
        test.unrecognizedEnvelope();
        
        result &= modifyEFandCheck(MF, EF_TARU, true); 

        // delete EF_TARU
        test.deleteFile(EF_TARU);
        // create EF_TARU
        test.createFile(fcp_EF_TARU);

        result &= modifyEFandCheck(MF, EF_TARU, true); 
        
        test.unrecognizedEnvelope();


        
        /** Testcase 22 **************************************
         *  Register a deleted and recreated DF under MF
         *****************************************************/

        test.unrecognizedEnvelope();
        
        result &= modifyEFandCheck(MF, EF_TAA, true); 
        
        // delete DF_SUB_TEST
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.deleteFile(DF_SUB_TEST);

        // create DF_SUB_TEST
        test.createFile(fcp_DF_SUB_TEST);

        // create EF_TAA
        test.selectFile(DF_SUB_TEST);
        test.createFile(fcp_EF_TAA);
        
        result &= modifyEFandCheck(MF, EF_TAA, true); 
        
        test.unrecognizedEnvelope();
        
        
        /** Testcase 23 **************************************
         *  Register a non existant EF under MF 
         *****************************************************/

        test.unrecognizedEnvelope();

        // create MF/DF_TEST/DF_SUB_TEST/EF_TNEW
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(DF_SUB_TEST);
        test.createFile(fcp_EF_TNEW);
        
        // update binary EF_TNEW
        test.selectFile(EF_TNEW);
        test.updateBinary("0000", "FFFFFF");
        result &= appletTriggered("01" + MF + DF_TEST + DF_SUB_TEST + EF_TNEW);

        test.unrecognizedEnvelope();

        
        /** Testcase 24 **************************************
         *  Register a non existant DF under MF
         *****************************************************/

        test.unrecognizedEnvelope();
        
        // create DF_NEW
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.createFile(fcp_DF_NEW);
        
        // create EF_TNEW
        test.selectFile(DF_NEW);
        test.createFile(fcp_EF_TNEW);

        // update binary EF_TNEW
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(DF_NEW);
        test.selectFile(EF_TNEW);
        test.updateBinary("0000", "FFFFFF");
        result &= appletTriggered("01" + MF + DF_TEST + DF_NEW + EF_TNEW);
        
        test.unrecognizedEnvelope();

        // delete DF_NEW
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.deleteFile(DF_NEW);
        

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
        
        // restore EF_TAA under MF
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(DF_SUB_TEST);
        test.selectFile(EF_TAA);
        test.updateBinary("0000", "AAAAAA");
        
        // restore EF_TARU under MF
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_TARU);
        String FFx130 = "";
        for (int i=0; i<130; i++)
            FFx130 += "FF";
        test.updateBinary("0000", FFx130);
        test.updateBinary("0082", FFx130);

        
        /*****************************************************
         *  Finish test
         *****************************************************/
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 +
                                     "18CCCCCC CCCCCCCC CCCCCCCC CCCCCCCC" +
                                     "CCCCCCCC CCCCCCCC CC");
        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);
        
        return result;
    }
    

    /** 
     * Check the File List which caused the latest EXTERNAL_FILE_UDPATE
     * and compare it whith the File List given in parameter.
     * If the applet has not been triggered it will return "000100"
     */
    
    private boolean appletTriggered(String data) {
        response = test.envelopeCallControlByNAA();
        return response.checkData(data);
    }
    
    
    /** 
     * Perform an action on the specified EF in order to test 
     * if the test applet is triggered with an EXTERNAL_FILE_UDPATE event.
     */
    
    private boolean modifyEFandCheck(String mfadf, String fid, boolean shouldTrigger) {
        
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
        
        // return the result of the triggering
        return appletTriggered(checkString);
    }
    
    
}

