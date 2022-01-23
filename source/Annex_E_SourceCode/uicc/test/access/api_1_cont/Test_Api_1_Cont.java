/**
 * ETSI TS 102 268: UICC API testing
 * uicc.access package part 1
 * Test source for context tests
 */
//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_cont;


//-----------------------------------------------------------------------------
//	Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccAPITestCardService;
import org.etsi.scp.wg3.uicc.jcapi.userclass.UiccTestModel;
import org.etsi.scp.wg3.uicc.jcapi.userinterface.APDUResponse;


public class Test_Api_1_Cont extends UiccTestModel {
    static final String CAP_FILE_PATH = "uicc/test/access/api_1_cont";
    static final String CLASS_AID_1   = "A0000000 090005FF FFFFFF89 10010001";
    static final String APPLET_AID_1  = "A0000000 090005FF FFFFFF89 10010102";
    static final String AID_ADF_1     = "A0000000 090005FF FFFFFF89 E0000002";
    static final String DF_TELECOM    = "7F10";
    static final String MF      = "3F00";
    static final String DF_TEST = "7F4A";
    static final String EF_TNR  = "6F01";
    static final String EF_TNU  = "6F02";
    static final String EF_TARU = "6F03";
    static final String EF_CNR  = "6F04";
    static final String EF_CARU = "6F09";
    static final String EF_LNU  = "6F0B";
    static final String EF_LARU = "6F0C";
    static final String EF_TDAC = "6F0F";
    static final String EF_LUPC = "6F18";
    static final String EF_NOSH = "6F19";
    static final String EF_RFU0 = "6F29";
    static final String EF_RFU1 = "6F2A";
    static final String DF_RFU1 = "5F01";
    static final String DF_RFU2 = "5F02";   

    
    private UiccAPITestCardService test;
    APDUResponse response;

    public Test_Api_1_Cont() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }

    public boolean run() {
        test.initialiseResults();

        // start test
        test.reset();
        test.terminalProfileSession("0101");
        
        // install package and applet
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
            "00" +   // LV ADF1 access domain DAP
            "8218" + // TLV UICC Access application specific parameters
            "00" +   // LV UICC File System AID field
            "0100" + // LV Access Domain for UICC file system = Full Access
            "00" +   // LV Access Domain DAP field
            "10A0000000090005FFFFFFFF89E0000002" + // LV ADF1 File System AID field
            "0100" + // LV Access Domain for ADF1 file system = Full Access
            "00");   // LV Access Domain DAP field            

        // test script
        test.reset();
        test.terminalProfileSession("0101");

        /** test case 0
         *  Init
         */
        // select DF_TELECOM
        test.selectFile(MF);
        test.selectFile(DF_TELECOM);
        test.unrecognizedEnvelope();

        /** test case 1
         *  Select and status
         */
        test.unrecognizedEnvelope();
        

        /** test case 2
         *  Select SFI
         */
        test.unrecognizedEnvelope();

        //9
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_TNR);

        //10
        response = test.activate(EF_TNR);
        test.addResult(response.checkSw("9000"));

        //11
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_TARU);

        //12
        response = test.readBinary("0000", "02");
        test.addResult(response.checkData("0101"));

        //13
        test.updateBinary("0000", "FFFFFF");

        //14
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CNR);

        //15
        response = test.activate(EF_CNR);
        test.addResult(response.checkSw("9000"));

        //16
        test.selectFile(EF_TARU);
        
        //17
        response = test.readBinary("0000", "02");
        test.addResult(response.checkData("0202"));

        //18
        test.updateBinary("0000", "FFFFFF");

        /** test case 3
         *  ReadBinary and updateBinary
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //11
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_TARU);
        response = test.readBinary("0000", "04");
        test.addResult(response.checkData("01010202"));

        //12
        test.updateBinary("0000", "FFFFFFFF");

        //13
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.selectFile(EF_TARU);
        response = test.readBinary("0000", "04");
        test.addResult(response.checkData("03030404"));

        //14
        test.updateBinary("0000", "FFFFFFFF");

        /** test case 4
         *  SearchRecord
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");
        
        test.verifyPIN("01","31313131FFFFFFFF");
        //Verify ADM2 
        test.verifyPIN("0B","3232323232323232");
        //2
        test.unrecognizedEnvelope();

        
        
        //13
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_LUPC);
        response = test.readRecord("01", "04", "0A");
        test.addResult(response.checkData("11111111 11111111 1111"));

        //14
        response = test.readRecord("02", "04", "0A");
        test.addResult(response.checkData("33333333 33333333 3333"));

        //15
        test.updateRecord("01", "04", "11111111 11111111 1111");

        //16
        test.updateRecord("02", "04", "22222222 22222222 2222");

        //17
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.selectFile(EF_LUPC);
        response = test.readRecord("01", "04", "0A");
        test.addResult(response.checkData("11111111 11111111 1111"));

        //18
        response = test.readRecord("02", "04", "0A");
        test.addResult(response.checkData("11111111 11111111 1111"));

        //19
        test.updateRecord("01", "04", "11111111 11111111 1111");

        //20
        test.updateRecord("02", "04", "22222222 22222222 2222");

        /** test case 5
         *  readRecord and updateRecord
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //17
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.selectFile(EF_LARU);
        response = test.readRecord("00", "02", "04");
        test.addResult(response.checkData("66666666"));

        //18
        response = test.readRecord("00", "02", "04");
        test.addResult(response.checkData("BBBBBBBB"));

        //19
        test.updateRecord("01", "04", "55555555");

        //20
        test.updateRecord("02", "04", "AAAAAAAA");

        //21
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.selectFile(EF_LARU);
        response = test.readRecord("00", "02", "04");
        test.addResult(response.checkData("44444444"));

        //22
        response = test.readRecord("00", "02", "04");
        test.addResult(response.checkData("99999999"));

        //23
        test.updateRecord("01", "04", "55555555");

        //24
        test.updateRecord("02", "04", "AAAAAAAA");

        /** test case 6
         *  ActivateFile and deactivateFile
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //11
        test.selectFile(DF_TEST);
        response = test.selectFile(EF_TNU);
        test.addResult(response.checkSw("6283"));

        //12
        response = test.activate(EF_TNU);
        test.addResult(response.checkSw("9000"));

        //13
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        response = test.selectFile(EF_TNU);
        test.addResult(response.checkSw("9000"));

        /** test case 7
         *  Increase
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //20
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        response = test.readRecord("00", "04", "03");
        test.addResult(response.checkSw("6A83"));

        //21
        response = test.readRecord("00", "03", "03");
        test.addResult(response.checkData("000001"));

        //22
        response = test.readRecord("00", "03", "03");
        test.addResult(response.checkData("000002"));

        //23
        response = test.updateRecord("00", "03", "AAAAAA");
        test.addResult(response.checkSw("9000"));

        //24
        response = test.updateRecord("00", "03", "555555");
        test.addResult(response.checkSw("9000"));

        //25
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.selectFile(EF_CARU);
        response = test.readRecord("00", "04", "03");
        test.addResult(response.checkSw("6A83"));

        //26
        response = test.readRecord("00", "03", "03");
        test.addResult(response.checkData("000002"));

        //27
        response = test.readRecord("00", "03", "03");
        test.addResult(response.checkData("000004"));

        //28
        response = test.updateRecord("00", "03", "AAAAAA");
        test.addResult(response.checkSw("9000"));

        //29
        response = test.updateRecord("00", "03", "555555");
        test.addResult(response.checkSw("9000"));
        
        

        /** test case 8
         *  CreateFile EF
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //21
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(EF_RFU0).checkSw("9000"));

        //22
        test.addResult(test.selectFile(EF_RFU1).checkSw("9000"));

        //23
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(EF_RFU0).checkSw("9000"));

        //24
        test.addResult(test.selectFile(EF_RFU1).checkSw("9000"));

        /** test case 9
         *  CreateFile DF
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //21
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(DF_RFU1).checkSw("9000"));

        //22
        test.addResult(test.selectFile(DF_RFU2).checkSw("9000"));

        //23
        test.selectApplication(AID_ADF_1);
        test.addResult(test.selectFile(DF_RFU1).checkSw("9000"));

        //24
        test.addResult(test.selectFile(DF_RFU2).checkSw("9000"));

        
        /** test case 10
         *  ResizeFile
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");

        //2
        test.unrecognizedEnvelope();

        //11
        test.selectFile(DF_TEST);
        test.selectFile(EF_TDAC);
        response = test.readBinary("0000", "06");
        test.addResult(response.checkSw("9000")||response.checkSw("6282")||response.checkSw("6700"));
        if (response.checkSw("9000"))
        {
            test.addResult(response.checkData("000000FF"));
        }   
        
        //12
        test.addResult(test.readBinary("0000", "04").checkData("000000FF"));
 
        //13
        test.addResult(test.resizeFile(EF_TDAC, "03").checkSw("9000"));
 
        //14
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.selectFile(EF_LNU);
        response = test.readRecord("00", "04", "04");
        test.addResult(response.checkSw("6A83"));
 
        //15
        response = test.readRecord("00", "03", "04");
        test.addResult(response.checkData("FFFFFFFF"));
 
        //16
        test.addResult(test.resizeFile(EF_LNU, "08").checkSw("9000"));
        
        
 
        /** test cases 11 & 12
         *  Non-shareable files (UICCFileView - UICCFileView)
         *  Non-shareable files (FileView - FileView)
         */
        test.reset();
        test.terminalProfileSession("0101");
        test.unrecognizedEnvelope();
        test.reset();
        test.terminalProfileSession("010101");
        test.unrecognizedEnvelope();
 
        /** test case 13
         *  Non-shareable files (UICCFileView - MF)
         */
        //1
        test.unrecognizedEnvelope();
 
        //4
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(EF_NOSH).checkSw("6985"));
 
        //5
        test.fetch("13");
        response = test.terminalResponse("81030121 80820282 81830100");
 
        /** test case 14
        *  Non-shareable files (FileView - ADF)
        */
        //1
        test.unrecognizedEnvelope();
 
        //4
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(EF_NOSH).checkSw("6985"));
 
        //5
        test.fetch("13");
        response = test.terminalResponse("81030121 80820282 81830100");
 
        /** test case 15
         *  Non-shareable files (MF - UICCFileView)
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");
 
        //2
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(EF_NOSH).checkSw("9000"));
 
        //3
        test.unrecognizedEnvelope();
 
        /** test case 16
         *  Non-shareable files (ADF - FileView)
         */
        //1
        test.reset();
        test.terminalProfileSession("0101");
 
        //2
        test.selectApplication(AID_ADF_1);
        test.selectFile(DF_TEST);
        test.addResult(test.selectFile(EF_NOSH).checkSw("9000"));
 
        //3
        test.unrecognizedEnvelope();

        
        /** test case 17
         *  Terminated EF/DF
         */
        
        test.reset();
        test.terminalProfileSession("0101");
        test.unrecognizedEnvelope();

    		/** Remove the created test EF
         * 
         */
    
        test.selectFile(MF);
        test.selectFile(DF_TEST);
        test.deleteFile(DF_RFU1); 
        test.deleteFile(EF_RFU0);
        test.deleteFile(EF_RFU1);
        

        test.selectApplication(AID_ADF_1);
        test.deleteFile(DF_RFU1);  
        test.selectFile(DF_TEST);
        test.deleteFile(EF_RFU0);
        test.deleteFile(EF_RFU1);
        
         
        
        /** 
         * Check Results and delete packages
         */
        
        // check results
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(response.checkData("10" + APPLET_AID_1 + "11CCCCCC CCCCCCCC CCCCCCCC" +
                                                           "CCCCCCCC CCCC"));

        
        // delete applet and package
        test.reset();
        test.terminalProfileSession("0101");
        test.deleteApplet(APPLET_AID_1);
        test.deletePackage(CAP_FILE_PATH);

        return test.getOverallResult();
    }
}
