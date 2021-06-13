 /**
 * uicc.access.fileadministration package, deleteFile tests
 * applet 1
 */

package uicc.test.access.fileadministration.api_4_afv_dltf;


import javacard.framework.*;

import uicc.access.*;
import uicc.access.fileadministration.*;
import uicc.system.*;
import uicc.test.util.*;
import uicc.toolkit.*;


public class Api_4_Afv_Dltf_1 extends TestToolkitApplet implements UICCConstants {
    private boolean bRes;
    private byte testCaseNb = -1;

    private AdminFileView uiccAdminFileView = null;
    private AdminFileView adf1AdminFileView = null;

    private EditHandler editHandler;
    private final short    TLVHANDLER_SIZE = 50;
    private byte[]  data       = new byte[10];
    private byte[]  adf1Aid    = new byte[16];
    private UICCTestConstants uiccTestConstants;


    private byte[] createFile_EF_RFU0 = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x41, (byte) 0x21,                 // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x29,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x10,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
    };

    private byte[] createFile_EF_RFU1 = new byte[] {
            (byte) 0x82, (byte) 0x04, (byte) 0x42, (byte) 0x21, (byte) 0x00, (byte) 0x01, // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x2A,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
    };

    
    private byte[] createFile_EF_RFU2 = new byte[] {
            (byte) 0x82, (byte) 0x04, (byte) 0x46 , (byte) 0x21, (byte) 0x00, (byte) 0x03, // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x2B,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x03,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
    };

        private byte[] createFile_EF_RFU0_inADF = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x41, (byte) 0x21,                 // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x29,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x10,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
    };

    private byte[] createFile_EF_RFU1_inADF = new byte[] {
            (byte) 0x82, (byte) 0x04, (byte) 0x42, (byte) 0x21, (byte) 0x00, (byte) 0x01, // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x2A,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x01,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
    };

    
    private byte[] createFile_EF_RFU2_inADF = new byte[] {
            (byte) 0x82, (byte) 0x04, (byte) 0x46 , (byte) 0x21, (byte) 0x00, (byte) 0x03, // File Descriptor
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x2B,                 // File ID
            (byte) 0x8A, (byte) 0x01, (byte) 0x05,                              // Life Cycle Status Information
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,    // Security Attributes
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x03,                 // File Size
            (byte) 0x88, (byte) 0x00                                            // Short File Identifier
    };

    
    
    private byte[] createFile_DF_RFU1 = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x5F, (byte) 0x01,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x50,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01,(byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };
    
    private byte[] createFile_DF_RFU2 = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x5F, (byte) 0x02,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x50,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01,(byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };
    
    private byte[] createFile_DF_RFU1_inADF = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x5F, (byte) 0x01,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x50,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01,(byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };
    
    private byte[] createFile_DF_RFU2_inADF = new byte[] {
            (byte) 0x82, (byte) 0x02, (byte) 0x78, (byte) 0x21,
            (byte) 0x83, (byte) 0x02, (byte) 0x5F, (byte) 0x02,
            (byte) 0x8A, (byte) 0x01, (byte) 0x05, 
            (byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01,
            (byte) 0x81, (byte) 0x02, (byte) 0x00, (byte) 0x50,
            (byte) 0xC6, (byte) 0x06, (byte) 0x90, (byte) 0x01,(byte) 0x80, (byte) 0x83, (byte) 0x01, (byte) 0x01
    };
    
    
    
    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Dltf_1() {
        editHandler = (EditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER,
                (short) TLVHANDLER_SIZE);
        uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1, (short) 0, adf1Aid, (short) 0,
                (short) adf1Aid.length);
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
        Api_4_Afv_Dltf_1 thisApplet = new Api_4_Afv_Dltf_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short) (bOffset + 1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }


    
    /** 
     * Method called by the CAT RE
     * 
     * @param event
     */
    public void processToolkit(short event) {
        switch (++testCaseNb) {

        /** Testcase 0
         *  Initialization
         */
        case 0:
            
            // Create Files to test deletion in MF
            
            uiccAdminFileView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
            uiccAdminFileView.select(UICCTestConstants.FID_MF);
            uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
            
            // Create MF/DF_TEST/DF_RFU1
            editHandler.clear();
            editHandler.appendArray(createFile_DF_RFU1, (short) 0,(short) createFile_DF_RFU1.length);
            uiccAdminFileView.createFile(editHandler);
            
            uiccAdminFileView.select(UICCTestConstants.FID_DF_RFU1);

            // Create MF/DF_TEST/DF_RFU1/EF_RFU1
            editHandler.clear();
            editHandler.appendArray(createFile_EF_RFU1, (short) 0,(short) createFile_EF_RFU1.length);
            uiccAdminFileView.createFile(editHandler);
            
            // Create MF/DF_TEST/DF_RFU1/EF_RFU2
            editHandler.clear();
            editHandler.appendArray(createFile_EF_RFU2, (short) 0,(short) createFile_EF_RFU2.length);
            uiccAdminFileView.createFile(editHandler);
            

            // Create MF/DF_TEST/DF_RFU1/DF_RFU2
            editHandler.clear();
            editHandler.appendArray(createFile_DF_RFU2, (short) 0,(short) createFile_DF_RFU2.length);
            uiccAdminFileView.createFile(editHandler);

            uiccAdminFileView.select(UICCTestConstants.FID_DF_RFU2);
            
            // Create MF/DF_TEST/DF_RFU1/DF_RFU2/EF_RFU0
            editHandler.clear();
            editHandler.appendArray(createFile_EF_RFU0, (short) 0,(short) createFile_EF_RFU0.length);
            uiccAdminFileView.createFile(editHandler);

            
            // Create Files to test deletion in ADF
            
            adf1AdminFileView = AdminFileViewBuilder.getTheAdminFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.CLEAR_ON_RESET);
            adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);

            
            // Create MF/DF_TEST/DF_RFU1
            editHandler.clear();
            editHandler.appendArray(createFile_DF_RFU1_inADF, (short) 0,(short) createFile_DF_RFU1_inADF.length);
            adf1AdminFileView.createFile(editHandler);
            adf1AdminFileView.select(UICCTestConstants.FID_DF_RFU1);
            
            // Create MF/DF_TEST/DF_RFU1/EF_RFU1
            editHandler.clear();
            editHandler.appendArray(createFile_EF_RFU1_inADF, (short) 0,(short) createFile_EF_RFU1_inADF.length);
            adf1AdminFileView.createFile(editHandler);
            
            // Create MF/DF_TEST/DF_RFU1/EF_RFU2
            editHandler.clear();
            editHandler.appendArray(createFile_EF_RFU2_inADF, (short) 0,(short) createFile_EF_RFU2_inADF.length);
            adf1AdminFileView.createFile(editHandler);
            
            // Create MF/DF_TEST/DF_RFU1/DF_RFU2
            editHandler.clear();
            editHandler.appendArray(createFile_DF_RFU2_inADF, (short) 0,(short) createFile_DF_RFU2_inADF.length);
            adf1AdminFileView.createFile(editHandler);
            
            adf1AdminFileView.select(UICCTestConstants.FID_DF_RFU2);

            // Create MF/DF_TEST/DF_RFU1/DF_RFU2/EF_RFU0
            editHandler.clear();
            editHandler.appendArray(createFile_EF_RFU0_inADF, (short) 0,(short) createFile_EF_RFU0_inADF.length);
            adf1AdminFileView.createFile(editHandler);
            
        break;
        
        
        /** Testcase 1
         *  Delete EF
         */
        case 1:
            bRes = true;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_RFU1);
                uiccAdminFileView.deleteFile(UICCTestConstants.FID_EF_RFU1);
            }
            catch (Exception e) {
                bRes = false;
            }
 
            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_RFU1);
                uiccAdminFileView.select(UICCTestConstants.FID_EF_RFU1);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;


        /** Testcase 2
         *  Delete EF in ADF1
         */
        case 2:
            bRes = true;

            try {
                adf1AdminFileView.select(UICCTestConstants.FID_ADF);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_RFU1);
                adf1AdminFileView.deleteFile(UICCTestConstants.FID_EF_RFU1);
            }
            catch (Exception e) {
                bRes = false;
            }
 
            try {
                adf1AdminFileView.select(UICCTestConstants.FID_MF);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_RFU1);
                adf1AdminFileView.select(UICCTestConstants.FID_EF_RFU1);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;
            
            
        /** Testcase 3
         *  Delete DF and its subtree
         */
        case 3:
            bRes = true;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.deleteFile(UICCTestConstants.FID_DF_RFU1);
            }
            catch (Exception e) {
                bRes = false;
            }
 
            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_RFU1);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;
          
            
        /** Testcase 4
         *  Delete DF and its subtree in ADF1
         */
        case 4:
            bRes = true;

            try {
                adf1AdminFileView.select(UICCTestConstants.FID_ADF);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView.deleteFile(UICCTestConstants.FID_DF_RFU1);
            }
            catch (Exception e) {
                bRes = false;
            }
 
            try {
                adf1AdminFileView.select(UICCTestConstants.FID_MF);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_RFU1);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;
            
            
        /** Testcase 5
         *  File not found
         */
        case 5:
            bRes = false;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.deleteFile(UICCTestConstants.FID_DF_RFU1);
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                adf1AdminFileView.select(UICCTestConstants.FID_MF);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);
                adf1AdminFileView.deleteFile(UICCTestConstants.FID_DF_RFU1);
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;
            
        
        /** Testcase 6
         *  Security status not satisfied
         */
        case 6:
            bRes = false;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_ARR2);
                uiccAdminFileView.deleteFile(UICCTestConstants.FID_EF_TAR2T);
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            break;
        
        }
    }
}
