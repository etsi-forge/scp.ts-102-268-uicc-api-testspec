/**
 * uicc.access package, getTheFileView tests
 * applet 1
 */

package uicc.test.access.api_1_usy_getf_bsbb;


import javacard.framework.*;

import uicc.access.*;
import uicc.system.*;
import uicc.test.util.*;
import uicc.toolkit.*;


public class Api_1_Usy_Getf_Bsbb_1 extends TestToolkitApplet implements UICCConstants {

    private boolean        bRes;
    private static boolean bRes1;
    private short          counter;
    private short          event_code;
    private byte           testCaseNb       = 1;
    private byte           nbProcessCalls   = 0;
    private byte           nbProcessTKCalls = 0;
    private short          transientAvailable;
    private FileView       fileView_1  = null;
    private FileView       fileView_2  = null;
    private FileView       fileView_3  = null;
    private short          fcpLen;
    private byte[]         fcp              = new byte[TLVHANDLER_MAX_SIZE];
    private byte[]         adf1Aid          = new byte[16];
    private static byte[]  buffer           = new byte[20];
    private byte[]         unknownAid       = {(byte)'U',(byte)'n',(byte)'k',(byte)'n',(byte)'o',(byte)'w',(byte)'n',(byte)'A',(byte)'I',(byte)'D'};
    private byte[]         comp             = new byte[10];
    private byte[]         checkFCP         = new byte[2];
    private static byte[]  MenuInit         = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    private UICCTestConstants  uiccTestConstants;
    private EditHandler    editHandler;
    private static final short DATA_SIZE           = 10;
    private static byte[]    data = new byte[DATA_SIZE];
    private static final short TLVHANDLER_MAX_SIZE = 50;


    /**
     * Constructor of the applet
     */
    public Api_1_Usy_Getf_Bsbb_1() {
        //check this
        editHandler = (EditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER,
                (short) TLVHANDLER_MAX_SIZE);
        uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1, (short) 0, adf1Aid, (short) 0,
                (short) adf1Aid.length);
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1, (short) 0, buffer, (short) 0,
                (short) 16);
        //check this

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
        Api_1_Usy_Getf_Bsbb_1 thisApplet = new Api_1_Usy_Getf_Bsbb_1();


        /** Test case 1
         *  Method returns null if called before register
         */

        try {
            bRes1 = (UICCSystem.getTheFileView(buffer, (short) 0, (byte) 16, JCSystem.CLEAR_ON_RESET) == null);
        }
        catch(Exception e) {
            bRes1 = false;
        }


        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short) (bOffset + 1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Toolkit registration
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,(short) MenuInit.length, (byte) 0, false,
                                    (byte) 0, (short) 0);
    }



    /**
     * Method called by the JCRE, once selected
     * This method allows to retrieve the detailed results of the previous execution
     * may be identical for all tests
     */
    public void process(APDU apdu) {
        if (selectingApplet()) {
            switch (++nbProcessCalls) {


            /** Testcase 2
             *  subpart 5
             */
            case 1:


                try {
                    fileView_3 = UICCSystem.getTheFileView(buffer, (short) 0, (byte) 16, JCSystem.CLEAR_ON_DESELECT);
                    fcpLen = fileView_3.status(fcp, (short) 0, (short) fcp.length);
                    bRes &= checkFCP_adf1();

                    fileView_3.select(UICCTestConstants.FID_DF_TEST);
                    fileView_3.select(UICCTestConstants.FID_EF_TARU);
                    fileView_3.readBinary((short)0, data, (short)0, (short) 3);
                    comp[0] = (byte) 0xFF;
                    comp[1] = (byte) 0xFF;
                    comp[2] = (byte) 0xFF;
                    bRes &= (Util.arrayCompare(comp, (short) 0, data, (short) 0, (short) 3) == 0);
                }
                catch (Exception e) {
                    bRes = false;
                }
                break;


            /** Testcase 2
             *  subpart 6
             */
            case 2:

                try {
                    fcpLen = fileView_3.status(fcp, (short) 0, (short) fcp.length);
                    bRes &= checkFCP_adf1();
                }
                catch (Exception e) {
                    bRes = false;
                }

                try {
                    fileView_3.select(UICCTestConstants.FID_MF);
                }
                catch (UICCException e) {
                    bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
                }
                break;


            /** Testcase 6
             *  NO_TRANSIENT_SPACE SystemException with CLEAR_ON_DESELECT FileView object
             */

            case 3:
                bRes = false;

                transientAvailable = JCSystem.getAvailableMemory(JCSystem.MEMORY_TYPE_TRANSIENT_DESELECT);
                data = JCSystem.makeTransientByteArray( transientAvailable, JCSystem.CLEAR_ON_DESELECT);

                try {
                    UICCSystem.getTheFileView(adf1Aid, (short) 0, (byte) 16, JCSystem.CLEAR_ON_DESELECT);
                }
                catch (SystemException e) {
                    bRes = (e.getReason() == SystemException.NO_TRANSIENT_SPACE);
                }

                data = null;
                JCSystem.requestObjectDeletion();

                reportTestOutcome(testCaseNb++, bRes);
                break;

            default:
                super.process(apdu);
            }
        }
        else {
            ISOException.throwIt(javacard.framework.ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }



    /**
     * Method called by the CAT RE
     *
     * @param event
     */
    public void processToolkit(short event) {
        switch (++nbProcessTKCalls) {


        /** Testcase 1 : Report result
         *  cf. install(...)
         */

        case 1:
            bRes = false;

            try {
                bRes = (UICCSystem.getTheFileView(unknownAid,(short) 0, (byte) 10, JCSystem.NOT_A_TRANSIENT_OBJECT) == null);
            }
            catch(Exception e) {
                bRes = false;
            }

            bRes &= bRes1;

            reportTestOutcome(testCaseNb++, bRes1);
            break;



        /** Testcase 2
         *  Normal execution
         */

        case 2:

            bRes = true;

            try {
                fileView_1 = UICCSystem.getTheFileView(adf1Aid,(short) 0, (byte) 16, JCSystem.NOT_A_TRANSIENT_OBJECT);
                fcpLen = fileView_1.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP_adf1();

                fileView_1.select(UICCTestConstants.FID_DF_TEST);
                fileView_1.select(UICCTestConstants.FID_EF_TARU);
                fileView_1.readBinary((short)0, data, (short)0, (short) 3);
                comp[0] = (byte) 0xFF;
                comp[1] = (byte) 0xFF;
                comp[2] = (byte) 0xFF;
                bRes &= (Util.arrayCompare(comp, (short) 0, data, (short) 0, (short) 3) == 0);
            }
            catch (Exception e) {
                bRes = false;
            }
            break;


        case 3:

            try {
                fcpLen = fileView_1.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP(UICCTestConstants.FID_DF_TEST);
                fileView_1.readBinary((short)0, data, (short)0, (short) 3);
                bRes &= (Util.arrayCompare(comp, (short) 0, data, (short) 0, (short) 3) == 0);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                fileView_1.select(UICCTestConstants.FID_MF);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            }


            try {
                fileView_2 = UICCSystem.getTheFileView(adf1Aid,(short) 0, (byte) 16, JCSystem.CLEAR_ON_RESET);
                fcpLen = fileView_2.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP_adf1();

                fileView_2.select(UICCTestConstants.FID_DF_TEST);
                fileView_2.select(UICCTestConstants.FID_EF_TARU);
                fileView_2.readBinary((short)0, data, (short)0, (short) 3);
                comp[0] = (byte) 0xFF;
                comp[1] = (byte) 0xFF;
                comp[2] = (byte) 0xFF;
                bRes &= (Util.arrayCompare(comp, (short) 0, data, (short) 0, (short) 3) == 0);
            }
            catch (Exception e) {
                bRes = false;
            }
            break;


        case 4:

            try {
                fcpLen = fileView_2.status(fcp, (short) 0, (short) fcp.length);
                bRes &= checkFCP_adf1();
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                fileView_2.readBinary((short)0, data, (short)0, (short) 3);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.NO_EF_SELECTED);
            }

            try {
                fileView_2.select(UICCTestConstants.FID_DF_TEST);
                fileView_2.select(UICCTestConstants.FID_MF);
            }
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.FILE_NOT_FOUND);
            }
            break;


        case 5:


            reportTestOutcome(testCaseNb++, bRes);
            break;


        /** Testcase 3
         *  FileView context independancy
         */

        case 6:
            bRes = true;

            try {
                bRes &= (fileView_1 != fileView_2);
                bRes &= (fileView_2 != fileView_3);
                bRes &= (fileView_3 != fileView_1);

                fileView_1.select(UICCTestConstants.FID_ADF);
                fileView_1.select(UICCTestConstants.FID_DF_TEST);
                fileView_1.select(UICCTestConstants.FID_EF_LARU);

                fileView_2.select(UICCTestConstants.FID_ADF);
                fileView_2.select(UICCTestConstants.FID_DF_TEST);
                fileView_2.select(UICCTestConstants.FID_EF_CARU);

                fileView_1.readRecord((short) 1, REC_ACC_MODE_ABSOLUTE, (short)0, data, (short)0, (short) 4);
                comp[0] = (byte) 0x55;
                comp[1] = (byte) 0x55;
                comp[2] = (byte) 0x55;
                comp[3] = (byte) 0x55;
                bRes &= (Util.arrayCompare(comp, (short) 0, data, (short) 0, (short) 4) == 0);

                fileView_2.readRecord((short) 2, REC_ACC_MODE_ABSOLUTE, (short)0, data, (short)0, (short) 3);
                comp[0] = (byte) 0xAA;
                comp[1] = (byte) 0xAA;
                comp[2] = (byte) 0xAA;
                bRes &= (Util.arrayCompare(comp, (short) 0, data, (short) 0, (short) 3) == 0);
            }
            catch (Exception e) {
                bRes = false;
            }
            try {
                fileView_3.select(UICCTestConstants.FID_ADF);
                fileView_3.select(UICCTestConstants.FID_DF_TEST);
                fileView_3.select(UICCTestConstants.FID_EF_CARU);
                bRes = false;

             }
             catch (SecurityException e) {
			     bRes &= true;
			 }
			 catch (Exception e) {
			    bRes &= false;
            }
            reportTestOutcome(testCaseNb++, bRes);
            break;



        /** Testcase 4
         *  ILLEGAL_TRANSIENT SystemException
         */

        case 7:
            bRes = false;

            try {
                UICCSystem.getTheFileView(adf1Aid,(short) 0, (byte) 16, JCSystem.CLEAR_ON_DESELECT);
            }
            catch (SystemException e) {
                bRes = (e.getReason() == SystemException.ILLEGAL_TRANSIENT);
            }

            reportTestOutcome(testCaseNb++, bRes);
            break;


        /** Testcase 5
         *  NO_TRANSIENT_SPACE SystemException with CLEAR_ON_RESET FileView object
         */

        case 8:
            bRes = false;

            transientAvailable = JCSystem.getAvailableMemory(JCSystem.MEMORY_TYPE_TRANSIENT_RESET);
            data = JCSystem.makeTransientByteArray( transientAvailable, JCSystem.CLEAR_ON_RESET);

            try {
                UICCSystem.getTheFileView(adf1Aid,(short) 0, (byte) 16, JCSystem.CLEAR_ON_RESET);
            }
            catch (SystemException e) {
                bRes = (e.getReason() == SystemException.NO_TRANSIENT_SPACE);
            }

            data = null;
            JCSystem.requestObjectDeletion();

            reportTestOutcome(testCaseNb++, bRes);
            break;


        /** Testcase 7
         *  ILLEGAL_VALUE SystemException
         */

        case 9:
            bRes = false;

            counter = (short)0;

            for (event_code=(short)3; event_code <= (short)0xFF; event_code++) {
                try {
                    UICCSystem.getTheFileView(adf1Aid,(short) 0, (byte) 16, (byte) event_code);
                }
                catch (SystemException e) {
                    if (e.getReason() == SystemException.ILLEGAL_VALUE) {
                        counter++;
                    }
                }
            }

            if (counter == (short)253) {
                bRes = true;
            }

            reportTestOutcome(testCaseNb++, bRes);
            break;



        /** Testcase 8
         *  NullPointerException
         */

        case 10:
            bRes = false;

            try {
                UICCSystem.getTheFileView(null, (short) 0, (byte) 16, JCSystem.NOT_A_TRANSIENT_OBJECT);
            }
            catch (NullPointerException e) {
                bRes = true;
            }

            reportTestOutcome(testCaseNb++, bRes);
            break;


        /** Testcase 9
         *  ArrayIndexOutOfBoundsException
         */

        case 11:
            bRes = false;
            short counter = 0;

            try {
                UICCSystem.getTheFileView(adf1Aid, (short) 5, (byte) 16, JCSystem.CLEAR_ON_RESET);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                counter++;
            }

            try {
                UICCSystem.getTheFileView(adf1Aid, (short) -1, (byte) 16, JCSystem.CLEAR_ON_RESET);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                counter++;
            }

            bRes = (counter == (short)2);

            reportTestOutcome(testCaseNb++, bRes);
            break;


        /** Testcase 10
         *  SystemException.ILLEGAL_VALUE
         */

        case 12:
            bRes = false;
            counter = (short)0;

            try {
                UICCSystem.getTheFileView(adf1Aid, (short) 0, (byte) 4, JCSystem.CLEAR_ON_RESET);
            }
            catch (SystemException e) {
                if (e.getReason() == SystemException.ILLEGAL_VALUE) {
                    counter ++;
                }
            }

            try {
                UICCSystem.getTheFileView(buffer, (short) 0, (byte) 17, JCSystem.CLEAR_ON_RESET);
            }
            catch (SystemException e) {
                if (e.getReason() == SystemException.ILLEGAL_VALUE) {
                    counter++;
                }
            }

            bRes = (counter == (short)2);

            reportTestOutcome(testCaseNb++, bRes);

            break;



        }
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


    /**
     * Check that the FCP present in the global array fcp[] of
     * length fcpLen corresponds to the adf1 AID.
     *
     * @param fid
     * @return true if the fid corresponds to the DF present in fcp[]
     */

    private boolean checkFCP_adf1() {

        //fcp contains BERTL and length can be 1 or 2 bytes
        byte offsetFcpTLVList=2;

        editHandler.clear();
        if (fcp[1]==0x81){
            offsetFcpTLVList=3;
        }
        editHandler.appendArray(fcp, (short) offsetFcpTLVList, (short) (fcpLen-offsetFcpTLVList));
        return (editHandler.findAndCompareValue((byte) 0x84, adf1Aid, (short) 0) == 0);
    }


}
