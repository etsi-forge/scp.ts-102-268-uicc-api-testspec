//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_ufa_view;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
//import uicc.toolkit.*;

import uicc.access.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * Cat Runtime Environment, UICC File Access, FileView test
 * Applet2, non toolkit applet
 */

public class Cre_Ufa_View_2 extends javacard.framework.Applet {

    private static byte triggeringNumber = 0;
    private byte testCaseNb;
    private boolean bRes;
    private short offset;
    private byte tag;
    
    static FileView classVariable;
    FileView instanceVariable;
    Object[] arrayComponent = new Object[1];
    byte[] readData = new byte[50];

    FileView FileView1 = null;
    FileView FileView2 = null;
    FileView uiccFileView2 = null;
    FileView adf1FileView2 = null;

    static private byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t'};
    private byte[] baTestsResults = new byte[128];
    private byte[] baTestAppletId = new byte[17];
    private byte[] adf1Aid = new byte[16];
    private UICCTestConstants uiccTestConstants;
   
    // Constructor of the applet
    public Cre_Ufa_View_2() {       
        uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1,(short)0,adf1Aid,(short)0,(short)adf1Aid.length);
        uiccTestConstants = null;
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){
        
        // create a new applet instance
        Cre_Ufa_View_2 thisApplet = new Cre_Ufa_View_2();

        // register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // initialise the data of the test applet
        thisApplet.init();
    }

    private short findTLV (byte tag, byte[] buffer, short offset) {
        
        short   index=1;
        short   len;
        
        index = (short)(index + offset);
        
        if (buffer[(byte)index]==(byte)0x81) {
            index++;            
            len = (short) (buffer[(byte)index] + index + (short)1);
        }else if (buffer[(byte)index]==(byte)0x82){
            index = (short)(index + (short)2);
            len = (short) (Util.getShort(buffer,(byte)index) + index);
        }
        else{
            len = (short) (buffer[(byte)index] + index + (short)1);
        }       
        
        index++;
        
        while (index < len) {
            
            if ( (buffer[(byte)index] & (byte)0x7F) == (tag & (byte)0x7F) )  {
                return(index);
            }
            index = (short) (index + (short) buffer[(byte)(index + (byte)1)] + (short)2);
        }
        
        return(0);
        
    }
    
    /**
     * Method called by the JCRE, once selected
     * This method allows to retrieve the detailed results of the previous execution
     * may be identical for all tests
     */
    public void process(APDU apdu) {
        
        FileView localVariable;
        byte data1;
        byte data2;
        
        if (selectingApplet()) {
            switch (triggeringNumber) {
            case (byte)0x00:
        
                /** test case 1: Applet2 can get a FileView object */
                testCaseNb = (byte)0x01;
                try {
                    uiccFileView2 = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                    adf1FileView2 = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);


                /** test case 2: Applet2 can only access all files under the MF using the UICC FileView object */

                testCaseNb = (byte)0x02;
                try {
                    uiccFileView2.select(UICCTestConstants.FID_MF);
                    uiccFileView2.select(UICCConstants.FID_EF_DIR);
                    uiccFileView2.select(UICCConstants.FID_DF_TELECOM);
                    uiccFileView2.select(UICCTestConstants.FID_MF);
                    uiccFileView2.select(UICCTestConstants.FID_DF_TEST);
                    uiccFileView2.select(UICCTestConstants.FID_DF_SUB_TEST);
                    uiccFileView2.select(UICCTestConstants.FID_EF_TAA);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                try {
                    uiccFileView2.select(UICCTestConstants.FID_ADF);
                    bRes = false;
                }
                catch (Exception e) {
                }

                reportTestOutcome(testCaseNb, bRes);


                /** test case 3: Applet2 can access all files under the ADF1 using the ADF1 FileView object */    
                testCaseNb = (byte)0x03;
                bRes = false;
                // 3-8 successful select() calls
                try {
                    adf1FileView2.select((short)UICCTestConstants.FID_ADF);
                    adf1FileView2.select(UICCConstants.FID_DF_TELECOM);
                    adf1FileView2.select((short)UICCTestConstants.FID_ADF);
                    adf1FileView2.select(UICCTestConstants.FID_DF_TEST);
                    adf1FileView2.select(UICCTestConstants.FID_DF_SUB_TEST);
                    adf1FileView2.select(UICCTestConstants.FID_EF_TAA);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }

                // 9-tries to select the MF
                try {
                    adf1FileView2.select(UICCTestConstants.FID_MF);
                    bRes = false;
                }
                catch (Exception e) {
                }

                // 10-tries to select the EFDIR
                try {
                    adf1FileView2.select(UICCConstants.FID_EF_DIR);
                    bRes = false;
                }
                catch (Exception e) {
                }

                reportTestOutcome(testCaseNb, bRes);

                /** test case 4: FileView object shall be provided as a permanent JCRE entry point object */
                testCaseNb = (byte)0x04;
                bRes = false;
                try {
                    classVariable = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                    instanceVariable = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                    localVariable = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                    arrayComponent[0] = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                        
                    classVariable = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    instanceVariable = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    localVariable = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    arrayComponent[0] = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    
                    // free variables
                    classVariable = null;
                        
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
                        
                /** test case 5: Context independence on a FileView object */
                /** UICC FileView then ADF1 FileView **/
                for (byte i=0; i <2; i++) {
                    
                    bRes = false;
                    // 2-get another FileView object
                    if (testCaseNb == 0x04) {
                        data1 = (byte)0x55;
                        data2 = (byte)0xAA;
                        FileView1 = uiccFileView2;
                        FileView2 = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                    }
                    else {
                        data1 = (byte)0x55;//AA;
                        data2 = (byte)0xAA;//55;
                        FileView1 = adf1FileView2;
                        FileView2 = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    }

                    try {
                        // 3-select DFTEST
                        FileView1.select(UICCTestConstants.FID_DF_TEST);
                        // select EFCARU
                        FileView1.select(UICCTestConstants.FID_EF_CARU);
                        // 4-Read record in next mode
                        FileView1.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                             readData, (short)0, (short)3);
                        // compare read data to 0x55
                        bRes = true;
                        for (byte j = 0; j<3; j++) {
                            if (readData[j] == data1) {
                                bRes &= true;
                            } else {
                                bRes = false;
                            }
                        }

                        // 5-Read record in next mode
                        FileView1.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                             readData, (short)0, (short)3);
                        // compare read data to 0xAA
                        for (byte j = 0; j<3; j++) {
                            if (readData[j] == data2) {
                                bRes &= true;
                            } else {
                                bRes = false;
                            }
                        }
                    }
                    catch (Exception e) {
                        bRes =false;
                    }

                    // 6-Read record with FileView2 object in next mode (throws UICCException.NO_EF_SELECTED)
                    try {                
                        FileView2.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                             readData, (short)0, (short)3);
                    }
                    catch (UICCException e) {
                        if (e.getReason() != UICCException.NO_EF_SELECTED) {
                        bRes = false;
                        }
                    }
                    catch (Exception e) {
                        bRes = false;
                    }
                    // 7-select EFLARU using the FileView2 object (throws UICCException.FILE_NOT_FOUND)
                    try {
                        FileView2.select(UICCTestConstants.FID_EF_LARU);
                    }
                    catch (UICCException e) {
                        if (e.getReason() != UICCException.FILE_NOT_FOUND) {
                        bRes = false;
                        }
                    }
                    catch (Exception e) {
                        bRes = false;
                    }

                    try {
                        // 8-select DFTEST using the FileView2 object
                        FileView2.select(UICCTestConstants.FID_DF_TEST);
                        // select EFCARU
                        FileView2.select(UICCTestConstants.FID_EF_CARU);
                        // 9-Read record in next mode
                        FileView2.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                             readData, (short)0, (short)3);
                        // compare read data to 0x55
                        for (byte j = 0; j<3; j++) {
                            if (readData[j] == data1) {
                                bRes &= true;
                            } else {
                                bRes = false;
                            }
                        }
                    }
                    catch (Exception e) {
                        bRes = false;
                    }

                    testCaseNb++;
                    reportTestOutcome(testCaseNb, bRes);
                }
                triggeringNumber++;
                break;
                
            /** test case 6: File Context can be transient or persistent */
            /** UICC FileView **/
            case (byte)0x01:
            /** ADF1 FileView **/
            case (byte)0x03:
                bRes = false;
                // 2-get transient/persistent FileView object
                if (testCaseNb == 0x06) {
                    data1 = (byte)0x55;
                    data2 = (byte)0xAA;
                    FileView1 = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
                    FileView2 = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                }
                else {
                    data1 = (byte)0x55;//AA;
                    data2 = (byte)0xAA;//55;
                    FileView1 = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.CLEAR_ON_RESET);
                    FileView2 = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                }

                // 3-select DFTEST/EFCARU using FileView1 object then FileView2 object
                FileView1.select(UICCTestConstants.FID_DF_TEST);
                FileView1.select(UICCTestConstants.FID_EF_CARU);
                FileView2.select(UICCTestConstants.FID_DF_TEST);
                FileView2.select(UICCTestConstants.FID_EF_CARU);

                // 4-call the readRecord() method in the NEXT mode using FileView1 object
                FileView1.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                     readData, (short)0, (short)3);
                // compare read data to 0x55
                for (byte j = 0; j<3; j++) {
                    bRes = true;
                    if (readData[j] == data1) {
                        bRes &= true;
                    } else {
                        bRes = false;
                    }
                }
                // call the readRecord() method in the NEXT mode using FileView2 object
                FileView2.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                     readData, (short)0, (short)3);
                // compare read data to 0x55
                for (byte j = 0; j<3; j++) {
                    if (readData[j] == data1) {
                        bRes &= true;
                    } else {
                        bRes = false;
                    }
                }
                testCaseNb++;
                reportTestOutcome(testCaseNb, bRes);
                triggeringNumber++;
                break;
            
            /** a Reset is performed */
            /** UICC FileView **/
            case (byte)0x02:
            /** ADF1 FileView **/
            case (byte)0x04:

                bRes = false;
                if (testCaseNb == 0x07) {
                    data1 = (byte)0xAA;
                    tag = (byte) 0x83;
                } else {
                    data1 = (byte)0xAA;
                    tag = (byte) 0x84;
                }
                // 7-call the status() command using the FileView1
                try {
                    FileView1.status(readData, (short)0, (short)readData.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                // check the selected DF is MF (3F00) or ADF1 (A0 00.... only 2 first bytes are checked...)
                offset = findTLV(tag, readData, (short)0);
                offset+=2;
                if (((readData[offset] == (byte)0x3F) || (readData[offset] == (byte)0xA0)) && (readData[(short)(offset+1)] == (byte)0x00) ) {
                    bRes &= true;
                } else {
                    bRes = false;
                }

                // 8-call the status() command using the FileView2
                FileView2.status(readData, (short)0, (short)readData.length);
                // check the selected DF is DF_TEST
                offset = findTLV((byte) 0x83, readData, (short)0);
                offset +=2;
                if ((readData[offset] == (byte)0x7F) && (readData[(short)(offset+1)] == (byte)0x4A)) {
                    bRes &= true;
                } else {
                    bRes = false;
                }
                // 9-call the readRecord() method in the NEXT mode using the FileView2
                FileView2.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                     readData, (short)0, (short)3);
                // compare read data to 0xAA
                for (byte j = 0; j<3; j++) {
                    if (readData[j] == data1) {
                        bRes &= true;
                    } else {
                        bRes = false;
                    }
                }
                // 10-call the readRecord() method in the NEXT mode using the FileView1 (throws UICCException.NO_EF_SELECTED)
                try {
                    FileView1.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                         readData, (short)0, (short)3);
                    bRes = false;
                }
                catch (UICCException e) {
                    if (e.getReason() == UICCException.NO_EF_SELECTED) {
                        bRes &= true;
                    } else {
                        bRes = false;
                    }
                }
                catch (Exception e) {
                    bRes = false;
                }

                testCaseNb++;
                reportTestOutcome(testCaseNb, bRes);
                
                /* end of th test: result is sent to the Terminal */
                if (triggeringNumber == (byte)0x04) {
           
                    /* Construct and send the results of the tests */
                    apdu.setOutgoing();
                    apdu.setOutgoingLength((short)((short)(this.baTestAppletId[0] + this.baTestsResults[0]) + 
                                           (short)2));
                    apdu.sendBytesLong(this.baTestAppletId, (short)0, (short)((short)(this.baTestAppletId[0]) + (short)1));
                    apdu.sendBytesLong(this.baTestsResults, (short)0, (short)((short)(this.baTestsResults[0]) + (short)1));
                }
                triggeringNumber++;
                break;
            }
        }
        else {
            ISOException.throwIt(javacard.framework.ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }
    
    /**
     * Method called to initialize the AID
     */
    public void init() throws SystemException {

        // Get the AID value
        this.baTestAppletId[0] = JCSystem.getAID().getBytes(this.baTestAppletId, (short)1);
        Util.arrayFillNonAtomic(this.baTestsResults, (short)0, (short)this.baTestsResults.length, (byte)0x00);
    }
    
    /**
     * Method called by the test applet to report the result of each test case
     *
     * @param testCaseNumber test case number
     * @param result true if successful, false otherwise
     */
    protected void reportTestOutcome(byte testCaseNumber, boolean testCaseResult) {
        // Update the total number of tests executed
        this.baTestsResults[0] = testCaseNumber;

        // Set the Test Case Result byte to 0xCC (for Card Compliant...) if successful
        if (testCaseResult) {
            this.baTestsResults[testCaseNumber] = (byte)0xCC;
        }
        else {
            this.baTestsResults[testCaseNumber] = (byte)0x00;
        }
    }
}
