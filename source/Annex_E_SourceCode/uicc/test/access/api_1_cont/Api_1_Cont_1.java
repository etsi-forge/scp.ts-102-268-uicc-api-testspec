/**
 * uicc.access package, context tests
 * applet 1
 */

package uicc.test.access.api_1_cont;


import javacard.framework.*;

import uicc.access.*;
import uicc.access.fileadministration.*;
import uicc.system.*;
import uicc.test.util.*;
import uicc.toolkit.*;


public class Api_1_Cont_1 extends TestToolkitApplet implements UICCConstants {
    private boolean bRes;
    private byte testCaseNb = 0;
    private short i;

    private final byte SFI_EF_TNR  = 0x01;
    private final byte SFI_EF_TARU = 0x03;
    private final byte SFI_EF_CNR  = 0x04;
    private final byte SFI_NA1     = 0x06;
    private final byte SFI_NA2     = 0x08;
    
    private FileView uiccFileView1 = null;
    private FileView uiccFileView2 = null;
    private FileView adf1FileView1 = null;
    private FileView adf1FileView2 = null;
    private AdminFileView uiccAdminFileView1 = null;
    private AdminFileView uiccAdminFileView2 = null;
    private AdminFileView adf1AdminFileView1 = null;
    private AdminFileView adf1AdminFileView2 = null;
    
    private static final short TLVHANDLER_MAX_SIZE = 50;
    private EditHandler editHandler;
    private byte[]  data       = null;
    private byte[]  resp       = null;
    private byte[]  abExpected = null;
    private byte[]  abPatt     = null;
    private short[] asResp     = null;
    private byte[]  checkFCP   = null;
    private short   fcpLen;
    private byte[]  fcp        = new byte[TLVHANDLER_MAX_SIZE];
    private byte[]  adf1Aid    = new byte[16];
    private UICCTestConstants uiccTestConstants;


    private byte[] createFileEFBuffer = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x41, (byte) 0x21,                 // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x20,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
        };

    private byte[] createFileEFBuffer_inADF = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x41, (byte) 0x21,                 // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x20,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
        };
        
    private final short EF_FID_OFFSET = 6;
    
    private byte[] createFileDFBuffer = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x30,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01, (byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };

    private byte[] createFileDFBuffer_inADF = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x30,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01, (byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };
    
    private byte[] createFileDFBuffer2 = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x01,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01, (byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };
    
    private byte[] createFileDFBuffer2_inADF = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x01,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01, (byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };

    
    private final short DF_FID_OFFSET = 6;
    
    private byte[] resizeFileBuffer = new byte[] {
            (byte) 0x83, (byte) 0x02, (byte) 0x00, (byte) 0x00,
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x00
        };
    
    private final short RESIZE_FID_OFFSET  = 2;
    private final short RESIZE_SIZE_OFFSET = 7;
    
    private final byte[] TEXT = new byte[] {
            (byte) 'C', (byte) 'o', (byte) 'n', (byte) 't', (byte) '1'
        };


    
    /**
     * Constructor of the applet
     */
    public Api_1_Cont_1() {
        editHandler = (EditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER,
                (short) TLVHANDLER_MAX_SIZE);
        uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1, (short) 0, adf1Aid, (short) 0,
                (short) adf1Aid.length);
        
        data = new byte[10];
        resp = new byte[10];
        abExpected = new byte[10];

        abPatt = new byte[10];
        asResp = new short[10];

        checkFCP = new byte[2];
    }

    
    /**
     * Method called by the JCRE at the installation of the applet
     * 
     * @param bArray
     * @param bOffset
     * @param bLength
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {

        // Create a new applet instance
        Api_1_Cont_1 thisApplet = new Api_1_Cont_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short) (bOffset + 1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    
    /**
     * Check that the current DF present in the global array fcp[] of
     * length fcpLen corresponds to the fid given as parameter.
     * 
     * @param fid
     * @return true if the fid corresponds to the DF present in fcp[]  
     */
    
    private boolean checkFCP(short fid) {
        
        //fcp contains BERTL and length can be 1 or 2 bytes
        byte offsetFcpTLVList=2;
        
        editHandler.clear();
        if (fcp[1]==0x81){       
            offsetFcpTLVList=3;            
        }        
        editHandler.appendArray(fcp, (short) offsetFcpTLVList, (short) (fcpLen-offsetFcpTLVList));
        checkFCP[0] = (byte) ((fid >> 8) & 0x00FF);
        checkFCP[1] = (byte) (fid & 0x00FF);
        return (editHandler.findAndCompareValue((byte) 0x83, checkFCP, (short) 0) == 0);
    }

    
    private void prepareHandler(EditHandler handler, byte[] array, short offset, short fid) {
        array[offset] = (byte) ((fid >> 8) & 0x00FF);
        array[(short)(offset+1)] = (byte) ((fid) & 0x00FF);
        handler.clear();
        handler.appendArray(array, (short) 0,(short) array.length);
    }

    
    /** 
     * Method called by the CAT RE
     * 
     * @param event
     */
    public void processToolkit(short event) {
        switch (testCaseNb) {
        /** Testcase 0
         *
         */
        case 0:
            uiccFileView1 = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
            uiccFileView2 = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
            adf1FileView1 = UICCSystem.getTheFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.CLEAR_ON_RESET);
            adf1FileView2 = UICCSystem.getTheFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.CLEAR_ON_RESET);

            uiccAdminFileView1 = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
            uiccAdminFileView2 = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
            adf1AdminFileView1 = AdminFileViewBuilder.getTheAdminFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.CLEAR_ON_RESET);
            adf1AdminFileView2 = AdminFileViewBuilder.getTheAdminFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.CLEAR_ON_RESET);
            testCaseNb++;
            break;

        /** Testcase 1
         *  Select and status
         */
        case 1:
            bRes = true;
            
            //1
            uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
            uiccFileView1.select(UICCTestConstants.FID_EF_TARU);

            //2
            try {
                uiccFileView1.select(UICCTestConstants.FID_ADF);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //3
            uiccFileView2.select(UICCTestConstants.FID_EF_UICC);

            //4
            try {
                uiccFileView2.select(UICCTestConstants.FID_ADF);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //5
            adf1FileView1.select(UICCConstants.FID_DF_TELECOM);

            //6
            try {
                adf1FileView1.select(UICCTestConstants.FID_MF);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //7
            adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
            adf1FileView2.select(UICCTestConstants.FID_DF_SUB_TEST);
 
            //8
            try {
                adf1FileView2.select(UICCTestConstants.FID_MF);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }
            
            //9,10,11,12        
            try {
                fcpLen = uiccFileView1.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP(UICCTestConstants.FID_DF_TEST);
                fcpLen = uiccFileView2.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP(UICCTestConstants.FID_MF);
                fcpLen = adf1FileView1.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP(UICCConstants.FID_DF_TELECOM);
                fcpLen = adf1FileView2.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP(UICCTestConstants.FID_DF_SUB_TEST);
            } catch (Exception e) {
                bRes = false;
            }            
            reportTestOutcome(testCaseNb, bRes);    
            testCaseNb++;

            break;

        /** test case 2
         *  Select SFI
         */
        case 2:
            bRes = true;

            //1
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(SFI_EF_TNR);
                uiccFileView1.deactivateFile();
            } catch (Exception e) {
                bRes = false;
            }

            //2
            try {
                uiccFileView1.select(SFI_NA1);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //3
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(SFI_EF_TARU);

                // update EF TARU with 01 01
                data[0] = (byte) 0x01;
                data[1] = (byte) 0x01;
                uiccFileView2.updateBinary((short) 0, data, (short) 0, (short) 2);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                uiccFileView2.select(SFI_NA2);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //5
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(SFI_EF_CNR);
                adf1FileView1.deactivateFile();
            } catch (Exception e) {
                bRes = false;
            }

            //6
            try {
                adf1FileView1.select(SFI_NA1);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //7
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(SFI_EF_TARU);

                // update EF TARU with 02 02
                data[0] = (byte) 0x02;
                data[1] = (byte) 0x02;
                adf1FileView2.updateBinary((short) 0, data, (short) 0, (short) 2);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                adf1FileView2.select(SFI_NA2);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 3
         *  ReadBinary and updateBinary
         */
        case 3:
            bRes = true;

            //3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_TARU);

                // update EF TARU with 01 01 01 01
                data[0] = (byte) 0x01;
                data[1] = (byte) 0x01;
                data[2] = (byte) 0x01;
                data[3] = (byte) 0x01;
                uiccFileView1.updateBinary((short) 0, data, (short) 0, (short) 4);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                uiccFileView1.readBinary((short) 0, resp, (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //5
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(UICCTestConstants.FID_EF_TARU);

                // update EF TARU with 02 02 at offset 2 
                data[2] = (byte) 0x02;
                data[3] = (byte) 0x02;
                uiccFileView2.updateBinary((short) 2, data, (short) 2, (short) 2);
            } catch (Exception e) {
                bRes = false;
            }

            //6
            try {
                uiccFileView2.readBinary((short) 0, resp, (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //7
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_TARU);

                // update EF TARU with 03 03 03 03 
                data[0] = (byte) 0x03;
                data[1] = (byte) 0x03;
                data[2] = (byte) 0x03;
                data[3] = (byte) 0x03;
                adf1FileView1.updateBinary((short) 0, data, (short) 0, (short) 4);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                adf1FileView1.readBinary((short) 0, resp, (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //9
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(UICCTestConstants.FID_EF_TARU);

                // update EF TARU with 04 04 at offset 2 
                data[2] = (byte) 0x04;
                data[3] = (byte) 0x04;
                adf1FileView2.updateBinary((short) 2, data, (short) 2, (short) 2);
            } catch (Exception e) {
                bRes = false;
            }

            //10
            try {
                adf1FileView2.readBinary((short) 0, resp, (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 4
         *  SearchRecord
         */
        case 4:
            bRes = true;

            //3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_LUPC);

                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x22;

                uiccFileView1.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length);
                bRes &= (asResp[0] == 2);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x33;

                uiccFileView1.updateRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, abPatt,
                    (short) 0, (short) abPatt.length);
            } catch (Exception e) {
                bRes = false;
            }
            
            //5
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(UICCTestConstants.FID_EF_LUPC);
                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, abPatt,
                    (short) 0, (short) abPatt.length);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }
            
            //6
            try {
                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x22;

                bRes &= (uiccFileView2.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length) == 0);                        
            } catch (Exception e) {
                bRes = false;
            }

            //7
            try {
                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x33;

                uiccFileView2.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length);
                bRes &= (asResp[0] == 2);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_LUPC);

                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x33;

                 bRes &= (adf1FileView1.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length)== 0);
            } catch (Exception e) {
                bRes = false;
            }

            //9
            try {
                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x22;

                adf1FileView1.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length);
                bRes &= (asResp[0] == 2);
            } catch (Exception e) {
                bRes = false;
            }

            //10
            try {
                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x11;

                adf1FileView1.updateRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, abPatt,
                    (short) 0, (short) abPatt.length);
            } catch (Exception e) {
                bRes = false;
            }

            //11
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(UICCTestConstants.FID_EF_LUPC);

                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x22;

                 bRes &= (adf1FileView2.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length) == 0);               
            } catch (Exception e) {
                bRes = false;
            }
            
            //12
            try {
                for (i = 0; i < 10; i++)
                    abPatt[i] = (byte) 0x11;

                adf1FileView2.searchRecord(SIMPLE_SEARCH_START_FORWARD, (short) 1, (short) 0,
                    abPatt, (short) 0, (short) abPatt.length, asResp, (short) 0,
                    (short) asResp.length);
                bRes &= (asResp[0] == 1);
                bRes &= (asResp[1] == 2);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 5
         *  readRecord and updateRecord
         */
        case 5:
            bRes = true;

            //3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_LARU);

                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0x66;

                uiccFileView1.updateRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, abPatt,
                    (short) 0, (short) 4);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                uiccFileView1.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //5
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0xAA;

                uiccFileView1.readRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, resp, (short) 0,
                    (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            
            //6
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(UICCTestConstants.FID_EF_LARU);
                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 4);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //7
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0xBB;

                uiccFileView2.updateRecord((short) 2, REC_ACC_MODE_ABSOLUTE, (short) 0, abPatt,
                    (short) 0, (short) 4);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0x66;

                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, resp, (short) 0,
                    (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //9
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0xBB;

                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, resp, (short) 0,
                    (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            
            //10
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_LARU);

                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0x44;

                adf1FileView1.updateRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, abPatt,
                    (short) 0, (short) 4);
            } catch (Exception e) {
                bRes = false;
            }

            //11
            try {
                adf1FileView1.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //12
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0xAA;

                adf1FileView1.readRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, resp, (short) 0,
                    (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //13
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(UICCTestConstants.FID_EF_LARU);
                adf1FileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 4);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //14
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0x99;

                adf1FileView1.updateRecord((short) 2, REC_ACC_MODE_ABSOLUTE, (short) 0, abPatt,
                    (short) 0, (short) 4);
            } catch (Exception e) {
                bRes = false;
            }

            //15
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0x44;

                adf1FileView2.readRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, resp, (short) 0,
                    (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //16
            try {
                for (i = 0; i < 4; i++)
                    abPatt[i] = (byte) 0x99;

                adf1FileView2.readRecord((short) 0, REC_ACC_MODE_NEXT, (short) 0, resp, (short) 0,
                    (short) 4);
                bRes &= (Util.arrayCompare(abPatt, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;                       
            

            break;

        /** test case 6
         *  ActivateFile and deactivateFile
         */
        case 6:
            bRes = true;

            //3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_TNU);
                uiccFileView1.deactivateFile();
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(UICCTestConstants.FID_EF_TNU);
                uiccFileView2.readBinary((short) 0, resp, (short) 0, (short) 3);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.REF_DATA_INVALIDATED);
            } catch (Exception e) {
                bRes = false;
            }

            //5
            try {
                uiccFileView2.activateFile();
                uiccFileView2.readBinary((short) 0, resp, (short) 0, (short) 3);

                for (i = 0; i < 3; i++)
                    data[i] = (byte) 0x55;

                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //6
            try {
                uiccFileView2.deactivateFile();
            } catch (Exception e) {
                bRes = false;
            }
           
            //7
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_TNU);
                adf1FileView1.readBinary((short) 0, resp, (short) 0, (short) 3);

                for (i = 0; i < 3; i++)
                    data[i] = (byte) 0x55;

                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                adf1FileView1.deactivateFile();
            } catch (Exception e) {
                bRes = false;
            }

            //9
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(UICCTestConstants.FID_EF_TNU);
                adf1FileView2.readBinary((short) 0, resp, (short) 0, (short) 3);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.REF_DATA_INVALIDATED);
            } catch (Exception e) {
                bRes = false;
            }

            //10
            try {
                adf1FileView2.activateFile();
                adf1FileView2.readBinary((short) 0, resp, (short) 0, (short) 3);

                for (i = 0; i < 3; i++)
                    data[i] = (byte) 0x55;

                bRes &= (Util.arrayCompare(data, (short) 0, resp, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 7
         *  Increase
         */
        case 7:
            bRes = true;
            data[0] = (byte) 0;
            data[1] = (byte) 0;
            data[2] = (byte) 1;

            //3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_CARU);

                for (i = 0; i < 3; i++)
                    abPatt[i] = (byte) 0x00;

                uiccFileView1.updateRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, abPatt,
                    (short) 0, (short) 3);
                uiccFileView1.updateRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, abPatt,
                    (short) 0, (short) 3);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                uiccFileView1.increase(data, (short) 0, (short) 3, resp, (short) 0);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 1;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //5
            try {
                uiccFileView1.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 1;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //6
            try {
                uiccFileView1.readRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 0;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //7
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(UICCTestConstants.FID_EF_CARU);
                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                uiccFileView2.increase(data, (short) 0, (short) 3, resp, (short) 0);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 2;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //9
            try {
                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 2;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //10
            try {
                uiccFileView2.readRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 1;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //11
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_CARU);
                adf1FileView1.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //12
            try {
                adf1FileView1.updateRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, abPatt,
                    (short) 0, (short) 3);
                adf1FileView1.updateRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, abPatt,
                    (short) 0, (short) 3);
            } catch (Exception e) {
                bRes = false;
            }

            //13
            try {
                data[0] = (byte) 0;
                data[1] = (byte) 0;
                data[2] = (byte) 2;
                adf1FileView1.increase(data, (short) 0, (short) 3, resp, (short) 0);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 2;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //14
            try {
                adf1FileView1.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 2;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //15
            try {
                adf1FileView1.readRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 0;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //16
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(UICCTestConstants.FID_EF_CARU);
                adf1FileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //17
            try {
                data[0] = (byte) 0;
                data[1] = (byte) 0;
                data[2] = (byte) 2;
                adf1FileView2.increase(data, (short) 0, (short) 3, resp, (short) 0);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 4;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //18
            try {
                adf1FileView2.readRecord((short) 0, REC_ACC_MODE_CURRENT, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 4;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //19
            try {
                adf1FileView2.readRecord((short) 0, REC_ACC_MODE_PREVIOUS, (short) 0, resp,
                    (short) 0, (short) 3);
                abExpected[0] = (byte) 0;
                abExpected[1] = (byte) 0;
                abExpected[2] = (byte) 2;
                bRes &= (Util.arrayCompare(resp, (short) 0, abExpected, (short) 0, (short) 3) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 8
         *  CreateFile EF
         */
        case 8:
            bRes = true;
            

            //3
            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_RFU0);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //4 & 5
            try {
                prepareHandler(editHandler, createFileEFBuffer, EF_FID_OFFSET, UICCTestConstants.FID_EF_RFU0);
                uiccAdminFileView1.createFile(editHandler);
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_RFU0);
            } catch (Exception e) {
                bRes = false;
            }
            
            //6
            try {
                uiccAdminFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView2.select(UICCTestConstants.FID_EF_RFU0);
            } catch (Exception e) {
                bRes = false;
            }

            //7
            try {
                uiccAdminFileView2.select(UICCTestConstants.FID_EF_RFU1);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                prepareHandler(editHandler, createFileEFBuffer, EF_FID_OFFSET, UICCTestConstants.FID_EF_RFU1);
                uiccAdminFileView1.createFile(editHandler);
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }

            //9 & 10
            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_RFU1);
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_RFU0);
            } catch (Exception e) {
                bRes = false;
            }

            //11
            try {
                adf1AdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView1.select(UICCTestConstants.FID_EF_RFU0);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //12 & 13
            try {
                prepareHandler(editHandler, createFileEFBuffer_inADF, EF_FID_OFFSET, UICCTestConstants.FID_EF_RFU0);
                adf1AdminFileView1.createFile(editHandler);
                adf1AdminFileView1.select(UICCTestConstants.FID_EF_RFU0);
            } catch (Exception e) {
                bRes = false;
            }

            //14
            try {
                adf1AdminFileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView2.select(UICCTestConstants.FID_EF_RFU0);
            } catch (Exception e) {
                bRes = false;
            }

            //15
            try {
                adf1AdminFileView2.select(UICCTestConstants.FID_EF_RFU1);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //16 & 17 & 18
            try {
                prepareHandler(editHandler, createFileEFBuffer_inADF, EF_FID_OFFSET, UICCTestConstants.FID_EF_RFU1);
                adf1AdminFileView2.createFile(editHandler);
                adf1AdminFileView2.select(UICCTestConstants.FID_EF_RFU1);
                adf1AdminFileView2.select(UICCTestConstants.FID_EF_RFU0);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 9
         *  CreateFile DF
         */
        case 9:
            bRes = true;

            //3
            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_RFU1);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //4 & 5
            try {
                prepareHandler(editHandler, createFileDFBuffer, DF_FID_OFFSET, UICCTestConstants.FID_DF_RFU1);
                uiccAdminFileView1.createFile(editHandler);
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }

            
            //6
            try {
                uiccAdminFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView2.select(UICCTestConstants.FID_DF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }

            
            //7
            try {
                uiccAdminFileView2.select(UICCTestConstants.FID_DF_RFU2);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //8
            try {
                prepareHandler(editHandler, createFileDFBuffer2, DF_FID_OFFSET, UICCTestConstants.FID_DF_RFU2);
                uiccAdminFileView2.createFile(editHandler);
                uiccAdminFileView2.select(UICCTestConstants.FID_DF_RFU2);
            } catch (Exception e) {
                bRes = false;
            }

            
            //9 & 10
            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_RFU2);
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }

            //11
            try {
                adf1AdminFileView1.select(UICCTestConstants.FID_DF_RFU1);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //12 & 13
            try {
                prepareHandler(editHandler, createFileDFBuffer_inADF, DF_FID_OFFSET, UICCTestConstants.FID_DF_RFU1);
                adf1AdminFileView1.createFile(editHandler);
                adf1AdminFileView1.select(UICCTestConstants.FID_DF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }

            //14
            try {
                adf1AdminFileView2.select(UICCTestConstants.FID_DF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }

            //15
            try {
                adf1AdminFileView2.select(UICCTestConstants.FID_DF_RFU2);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            //16 & 17 & 18
            try {
                prepareHandler(editHandler, createFileDFBuffer2_inADF, DF_FID_OFFSET, UICCTestConstants.FID_DF_RFU2);
                adf1AdminFileView2.createFile(editHandler);
                adf1AdminFileView2.select(UICCTestConstants.FID_DF_RFU2);
                adf1AdminFileView2.select(UICCTestConstants.FID_DF_RFU1);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 10
         *  ResizeFile
         */
        case 10:
            bRes = true;
            abExpected[0] = (byte) 0x00;
            abExpected[1] = (byte) 0x00;
            abExpected[2] = (byte) 0x00;
            abExpected[3] = (byte) 0xFF;
            abExpected[4] = (byte) 0xFF;
            abExpected[5] = (byte) 0xFF;

            //3
            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_TDAC);
                uiccAdminFileView1.readBinary((short) 0, resp, (short) 0, (short) 6);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.OUT_OF_FILE_BOUNDARIES);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                resizeFileBuffer[RESIZE_FID_OFFSET] = (byte) ((UICCTestConstants.FID_EF_TDAC >> 8) & 0x00FF);
                resizeFileBuffer[RESIZE_FID_OFFSET+1] = (byte) (UICCTestConstants.FID_EF_TDAC & 0x00FF);
                resizeFileBuffer[RESIZE_SIZE_OFFSET]= (byte) 0x06; // new size: add 3
                prepareHandler(editHandler, resizeFileBuffer, RESIZE_FID_OFFSET, UICCTestConstants.FID_EF_TDAC);
                uiccAdminFileView1.resizeFile(editHandler);
                uiccAdminFileView1.readBinary((short) 0, resp, (short) 0, (short) 6);
                bRes &= (Util.arrayCompare(abExpected, (short) 0, resp, (short) 0, (short) 6) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            
            //5
            try {
                uiccAdminFileView2.select(UICCTestConstants.FID_MF);
                uiccAdminFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView2.select(UICCTestConstants.FID_EF_TDAC);
                uiccAdminFileView2.readBinary((short) 0, resp, (short) 0, (short) 6);
                bRes &= (Util.arrayCompare(abExpected, (short) 0, resp, (short) 0, (short) 6) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //6
            try {
                resizeFileBuffer[RESIZE_SIZE_OFFSET] = (byte) 0x04; // new size: remove 2
                prepareHandler(editHandler, resizeFileBuffer, RESIZE_FID_OFFSET, UICCTestConstants.FID_EF_TDAC);
                uiccAdminFileView1.resizeFile(editHandler);
            } catch (Exception e) {
                bRes = false;
            }
            
            //7
            try {
                adf1AdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView1.select(UICCTestConstants.FID_EF_LNU);
                adf1AdminFileView1.readRecord((short) 4, REC_ACC_MODE_ABSOLUTE, (short) 0, abPatt,
                    (short) 0, (short) 4);
                bRes = false;
            } catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }
            
            //8
            try {
                resizeFileBuffer[RESIZE_FID_OFFSET] = (byte) ((UICCTestConstants.FID_EF_LNU >> 8) & 0x00FF);
                resizeFileBuffer[RESIZE_FID_OFFSET+1] = (byte) (UICCTestConstants.FID_EF_LNU & 0x00FF);
                resizeFileBuffer[RESIZE_SIZE_OFFSET] = (byte) 0x10; // new size: add 2 records
                prepareHandler(editHandler, resizeFileBuffer, RESIZE_FID_OFFSET, UICCTestConstants.FID_EF_LNU);
                adf1AdminFileView1.resizeFile(editHandler);
                adf1AdminFileView1.readRecord((short) 4, REC_ACC_MODE_ABSOLUTE, (short) 0, resp,
                    (short) 0, (short) 4);
                abExpected[0] = (byte) 0xFF;
                abExpected[1] = (byte) 0xFF;
                abExpected[2] = (byte) 0xFF;
                abExpected[3] = (byte) 0xFF;
                bRes &= (Util.arrayCompare(abExpected, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }
            
            //9
            try {
                adf1AdminFileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView2.select(UICCTestConstants.FID_EF_LNU);
                adf1AdminFileView2.readRecord((short) 4, REC_ACC_MODE_ABSOLUTE, (short) 0, resp,
                    (short) 0, (short) 4);
                bRes &= (Util.arrayCompare(abExpected, (short) 0, resp, (short) 0, (short) 4) == 0);
            } catch (Exception e) {
                bRes = false;
            }

            //10
            try {
                resizeFileBuffer[RESIZE_SIZE_OFFSET] = (byte) 0x0C;  // new size: remove 1 record
                prepareHandler(editHandler, resizeFileBuffer, RESIZE_FID_OFFSET, UICCTestConstants.FID_EF_LNU);
                adf1AdminFileView2.resizeFile(editHandler);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

            

        /** test case 11
         *  Non-shareable files (UICCFileView - UICCFileView)
         */
        case 11:
            bRes = true;

            //3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_NOSH);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView2.select(UICCTestConstants.FID_EF_NOSH);
                bRes = false;
            } catch (UICCException e) {
                bRes =true;//&= ((e.getReason() == UICCException.FILE_NOT_FOUND)||(e.getReason() == UICCException.INTERNAL_ERROR)); // TODO: clarification Cf AP22 -> NOT_FOUND OR INTERNAL_ERROR
            } catch (Exception e) {
                bRes = false;
            }
            
            //5 & 6
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_NOSH);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 12
         *  Non-shareable files (FileView - FileView)
         */
        case 12:
            bRes = true;

            //3
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_NOSH);
            } catch (Exception e) {
                bRes = false;
            }

            //4
            try {
                adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView2.select(UICCTestConstants.FID_EF_NOSH);
                bRes = false;
            } catch (UICCException e) {
                bRes &= ((e.getReason() == UICCException.FILE_NOT_FOUND)||(e.getReason() == UICCException.INTERNAL_ERROR)); // TODO: clarification Cf AP22 -> NOT_FOUND OR INTERNAL_ERROR
            } catch (Exception e) {
                bRes = false;
            }

            //5 & 6
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_NOSH);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 13
         *  Non-shareable files (UICCFileView - MF)
         */
        case 13:
            bRes = true;

            //2 & 3
            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_NOSH);

                ProactiveHandler proactiveHandler = ProactiveHandlerSystem.getTheHandler();
                proactiveHandler.initDisplayText((byte) 0x80, (byte) 0x04, TEXT, (short) 0x00,
                    (short) TEXT.length);
                proactiveHandler.send();
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 14
         *  Non-shareable files (FileView - ADF)
         */
        case 14:
            bRes = true;

            //2 & 3
            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_NOSH);

                ProactiveHandler proactiveHandler = ProactiveHandlerSystem.getTheHandler();
                proactiveHandler.initDisplayText((byte) 0x80, (byte) 0x04, TEXT, (short) 0x00,
                    (short) TEXT.length);
                proactiveHandler.send();
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 15
         *  Non-shareable files (MF - UICCFileView)
         */
        case 15:
            bRes = true;

            try {
                uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccFileView1.select(UICCTestConstants.FID_EF_NOSH);
                bRes = false;
            } catch (UICCException e) {
                bRes &= ((e.getReason() == UICCException.FILE_NOT_FOUND)||(e.getReason() == UICCException.INTERNAL_ERROR)); // TODO: clarification Cf AP22 -> NOT_FOUND OR INTERNAL_ERROR
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
            testCaseNb++;

            break;

        /** test case 16
         *  Non-shareable files (ADF - FileView)
         */
        case 16:
            bRes = true;

            try {
                adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                adf1FileView1.select(UICCTestConstants.FID_EF_NOSH);
                bRes = false;
            } catch (UICCException e) {
                bRes &= ((e.getReason() == UICCException.FILE_NOT_FOUND)||(e.getReason() == UICCException.INTERNAL_ERROR)); // TODO: clarification Cf AP22 -> NOT_FOUND OR INTERNAL_ERROR
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);            
            testCaseNb++;
            break;
            
            
        /** test case 17
         *  Terminated EF/DF
         */
        case 17:
            bRes = true;

            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView1.select(UICCTestConstants.FID_EF_TERM);
            } catch (Exception e) {
                bRes = false;
            }
            
            try {
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView1.select(UICCTestConstants.FID_DF_TERM);
            } catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);            

            break;
        
        }
    }
}
