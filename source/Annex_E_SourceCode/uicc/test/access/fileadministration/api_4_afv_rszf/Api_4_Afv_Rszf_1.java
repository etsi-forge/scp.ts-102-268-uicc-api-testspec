/**
 * uicc.access.fileadministration package, resizeFile tests
 * applet 1
 */

package uicc.test.access.fileadministration.api_4_afv_rszf;


import javacard.framework.*;

import uicc.access.*;
import uicc.access.fileadministration.*;
import uicc.system.*;
import uicc.test.util.*;
import uicc.toolkit.*;


public class Api_4_Afv_Rszf_1 extends TestToolkitApplet implements UICCConstants {
    private boolean bRes;
    private byte testCaseNb = 0;

    private AdminFileView uiccAdminFileView = null;
    private AdminFileView adf1AdminFileView = null;

    private EditHandler editHandler;
    private final short    TLVHANDLER_SIZE = 50;
    private byte[]         adf1Aid    = new byte[16];
    private UICCTestConstants uiccTestConstants;


    private byte[] resizeFile_EF_TARU = new byte[] {
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x03,
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x0A
    };
    
    private byte[] resizeFile_EF_LARU = new byte[] {
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x0C,
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x10
    };
    
    private byte[]  incorrectParameters = new byte[] {
            (byte) 0x42, (byte) 0x42, (byte) 0x42, (byte) 0x42
    };
    
    private byte[] resizeFile_FileNotFound = new byte[] {
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x2A,
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x0A
    };
    
    private byte[] resizeFile_SecurityStatusNotSatisfied = new byte[] {
            (byte) 0x83, (byte) 0x02, (byte) 0x4F, (byte) 0x12,
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x0A
    };

    private byte[] resizeFile_CommandIncompatible = new byte[] {
            (byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x09,
            (byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x10
    };

    
    
    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Rszf_1() {
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
        Api_4_Afv_Rszf_1 thisApplet = new Api_4_Afv_Rszf_1();

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

        /** Testcase 1
         *  Resize a transparent EF
         */
        case 1:
            bRes = true;

            try {
                uiccAdminFileView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
               
                editHandler.clear();
                editHandler.appendArray(resizeFile_EF_TARU, (short) 0,(short) resizeFile_EF_TARU.length);
                uiccAdminFileView.resizeFile(editHandler);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;


        /** Testcase 2
         *  Resize a linear fixed EF
         */
        case 2:
            bRes = true;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                
                editHandler.clear();
                editHandler.appendArray(resizeFile_EF_LARU, (short) 0,(short) resizeFile_EF_LARU.length);
                uiccAdminFileView.resizeFile(editHandler);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;
            

        /** Testcase 3
         *  Call resizeFile with a null viewHandler
         */
        case 3:
            bRes = false;

            try {
                uiccAdminFileView.resizeFile(null);
            }
            catch (NullPointerException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;

            
        /** Testcase 4
         *  Call resizeFile with incorrect parameters
         */
        case 4:
            bRes = false;

            try {
                editHandler.clear();
                editHandler.appendArray(incorrectParameters, (short) 0,(short) incorrectParameters.length);
                uiccAdminFileView.resizeFile(editHandler);
            }
            catch (AdminException e) {
                bRes = (e.getReason() == AdminException.INCORRECT_PARAMETERS);
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

                editHandler.clear();
                editHandler.appendArray(resizeFile_FileNotFound, (short) 0,(short) resizeFile_FileNotFound.length);
                uiccAdminFileView.resizeFile(editHandler);
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            try {
                adf1AdminFileView = AdminFileViewBuilder.getTheAdminFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.CLEAR_ON_RESET);
                adf1AdminFileView.select(UICCTestConstants.FID_DF_TEST);
                
                editHandler.clear();
                editHandler.appendArray(resizeFile_FileNotFound, (short) 0,(short) resizeFile_FileNotFound.length);
                adf1AdminFileView.resizeFile(editHandler);
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
         *  Security Status not satisfied
         */
        case 6:
            bRes = false;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_ARR2);

                editHandler.clear();
                editHandler.appendArray(resizeFile_SecurityStatusNotSatisfied, (short) 0,(short) resizeFile_SecurityStatusNotSatisfied.length);
                uiccAdminFileView.resizeFile(editHandler);
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;
            


        /** Testcase 7
         *  Command incompatible
         */
        case 7:
            bRes = false;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);

                editHandler.clear();
                editHandler.appendArray(resizeFile_CommandIncompatible, (short) 0,(short) resizeFile_CommandIncompatible.length);
                uiccAdminFileView.resizeFile(editHandler);
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.COMMAND_INCOMPATIBLE);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);    
            break;

            
        /** Testcase 8
         *  Invalidated data
         */
        case 8:
            bRes = false;

            try {
                uiccAdminFileView.select(UICCTestConstants.FID_MF);
                uiccAdminFileView.select(UICCTestConstants.FID_DF_TEST);
                uiccAdminFileView.select(UICCTestConstants.FID_EF_TARU);
                uiccAdminFileView.deactivateFile();

                editHandler.clear();
                editHandler.appendArray(resizeFile_EF_TARU, (short) 0,(short) resizeFile_EF_TARU.length);
                uiccAdminFileView.resizeFile(editHandler);
                bRes = false;
            }
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.REF_DATA_INVALIDATED);
                uiccAdminFileView.activateFile();
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            break;
            
        }
    }
}
