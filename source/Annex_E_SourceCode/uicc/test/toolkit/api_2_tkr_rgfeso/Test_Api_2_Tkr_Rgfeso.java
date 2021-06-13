/**
 * ETSI TS 102 268: UICC API testing
 * uicc.toolkit package part 2
 * Test source for ToolkitRegistry interface
 * registerFileEvent() method
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rgfeso;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;

public class Test_Api_2_Tkr_Rgfeso extends UiccTestModel {
    
    static final String CAP_FILE_PATH = "uicc/test/toolkit/api_2_tkr_rgfeso";
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

    static final String fcp_EF_TARU     = "8202 41218302 6F038A01 058B036F 06018002 0104";
    static final String fcp_EF_TAA      = "8202 41218302 4F108A01 058B036F 06018001 03";
    static final String fcp_DF_SUB_TEST = "8202 78218302 5F108A01 058B036F 06018102 0200C606 90018083 0101";
    
    private UiccAPITestCardService test;
    APDUResponse response;
    
    public Test_Api_2_Tkr_Rgfeso() {
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
        

        /** Testcase 1 *****************************************
         *  Register EF under MF
         *******************************************************/

        test.unrecognizedEnvelope();

        // update binary EF_TARU and check that applet is correctly triggered
        result &= modifyEFandCheck(MF, EF_TARU, true); 

        test.unrecognizedEnvelope();

        result &= modifyEFandCheck(MF, EF_TARU, true);
        result &= modifyEFandCheck(MF, EF_CARU, true);
        result &= modifyEFandCheck(MF, EF_LARU, false); 
        result &= modifyEFandCheck(MF, EF_TAA, false); 
        
        test.unrecognizedEnvelope();

        
        /** Testcase 2 *****************************************
         *  Register DF under MF
         *******************************************************/

        test.unrecognizedEnvelope();

        result &= modifyEFandCheck(MF, EF_TARU, true); 
        result &= modifyEFandCheck(MF, EF_CARU, true); 
        result &= modifyEFandCheck(MF, EF_LARU, true); 
        result &= modifyEFandCheck(MF, EF_TAA, false); 
        
        test.unrecognizedEnvelope();
        
        
        /** Testcase 3 *****************************************
         *  Register EF under ADF1
         *******************************************************/

        test.unrecognizedEnvelope();
        
        result &= modifyEFandCheck(ADF, EF_TARU, true); 
        
        test.unrecognizedEnvelope();
        
        result &= modifyEFandCheck(ADF, EF_TARU, true); 
        result &= modifyEFandCheck(ADF, EF_CARU, true); 
        result &= modifyEFandCheck(ADF, EF_LARU, false); 
        result &= modifyEFandCheck(ADF, EF_TAA, false); 

        test.unrecognizedEnvelope();
        
        /** Testcase 4 *****************************************
         *  Register DF under ADF1
         *******************************************************/
        
        test.unrecognizedEnvelope();
        
        result &= modifyEFandCheck(ADF, EF_TARU, true);
        result &= modifyEFandCheck(ADF, EF_CARU, true);
        result &= modifyEFandCheck(ADF, EF_LARU, true);
        result &= modifyEFandCheck(ADF, EF_TAA, false); 

        test.unrecognizedEnvelope();
        
        /** Testcase 5 - 10 **********************************
         *  5. NullPointerException Exception
         *  6. EVENT_MENU_SELECTION not allowed
         *  7. EVENT_MENU_SELECTION_HELP_REQUEST not allowed
         *  8. EVENT_TIMER_EXPIRATION not allowed
         *  9. EVENT_STATUS_COMMAND not allowed
         *  10. EVENT_NOT_SUPPORTED Exception 
         *****************************************************/

        test.unrecognizedEnvelope();

        
        /** Testcase 11 ****************************************
         *  Register a deleted and recreated EF under ADF
         *******************************************************/

        test.unrecognizedEnvelope();

        result &= modifyEFandCheck(ADF, EF_TARU, true); 
        
        // delete EF_TARU
        test.deleteFile(EF_TARU);
        // create EF_TARU
        test.createFile(fcp_EF_TARU);
        
        result &= modifyEFandCheck(ADF, EF_TARU, true); 
        
        test.unrecognizedEnvelope();

        
        /** Testcase 12 ****************************************
         *  Register a deleted and recreated DF under ADF
         *******************************************************/
        
        test.unrecognizedEnvelope();

        result &= modifyEFandCheck(ADF, EF_TAA, true); 
        
        // delete DF_SUB_TEST
        test.selectFile(ADF);
        test.selectFile(DF_TEST);
        test.deleteFile(DF_SUB_TEST);

        // create DF_SUB_TEST
        test.createFile(fcp_DF_SUB_TEST);
        test.selectFile(DF_SUB_TEST);

        // create EF_TAA
        test.createFile(fcp_EF_TAA);
        
        result &= modifyEFandCheck(ADF, EF_TAA, true); 
        
        test.unrecognizedEnvelope();
        
        
        
        /*****************************************************
         *  Restore modified files
         *****************************************************/

        // restore EF_CARU under MF        
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        test.updateRecord("00", "03", "AAAAAA");
        test.updateRecord("00", "03", "555555");

        // restore EF_CARU under ADF
        test.selectApplication(AID_ADF_1);
        test.selectFile(ADF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        test.updateRecord("00", "03", "AAAAAA");
        test.updateRecord("00", "03", "555555");
        
        // restore EF_TAA under ADF
        test.selectFile(ADF);
        test.selectFile(DF_TEST);
        test.selectFile(DF_SUB_TEST);
        test.selectFile(EF_TAA);
        test.updateBinary("0000", "AAAAAA");
        
        // restore EF_TARU under ADF
        test.selectFile(ADF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_TARU);
        String FFx130 = "";
        for (int i=0; i<=130; i++)
            FFx130 += "FF";
        test.updateBinary("0000", FFx130);
        test.updateBinary("0082", FFx130);
        
        
        
        /*****************************************************
         *  Finish test
         *****************************************************/
                
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 +
                                     "0CCCCCCC CCCCCCCC CCCCCCCC CC");
        
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
        String checkString = "01" + MF;
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

