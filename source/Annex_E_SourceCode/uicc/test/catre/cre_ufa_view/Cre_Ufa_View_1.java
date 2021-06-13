//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_ufa_view;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.access.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * Cat Runtime Environment, UICC File Access, FileView test
 * Applet1
 */

public class Cre_Ufa_View_1 extends TestToolkitApplet {

    private byte testCaseNb;
    private boolean bRes;
    static FileView classVariable;
    FileView instanceVariable;
    Object[] arrayComponent = new Object[1];
    byte[] readData = new byte[50];
    short offset;
    byte tag;
    
    private FileView FileView1 = null;
    private FileView FileView2 = null;  
    private FileView uiccFileView1 = null;
    private FileView adf1FileView1 = null;

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t'};
    
    private byte[] adf1Aid = new byte[16];
    private UICCTestConstants uiccTestConstants;

    // Constructor of the applet
    public Cre_Ufa_View_1() {    
        uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1,(short)0,adf1Aid,(short)0,(short)adf1Aid.length);
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // create a new applet instance
        Cre_Ufa_View_1 thisApplet = new Cre_Ufa_View_1();

        // register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // initialise the data of the test applet
        thisApplet.init();

        // registration to EVENT_MENU_SELECTION and EVENT_CALL_CONTROL_BY_NAA
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short) MenuInit.length, (byte) 0,
                                       false, (byte) 0, (short) 0);
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
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
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {
        
        byte data1 ;
        byte data2;
        FileView localVariable;
        final byte[] displayString = {(byte)'H', (byte)'E', (byte)'L', (byte)'L', (byte)'O'};

        if (event ==  EVENT_MENU_SELECTION) {
            switch (testCaseNb) {
            /** test case 1: Applet can get a FileView object */
            case (byte)0x00:
                testCaseNb = (byte)0x01;
                try {
                    uiccFileView1 = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                    adf1FileView1 = UICCSystem.getTheFileView(adf1Aid, (short)0, (byte)adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;

            /** test case 2: Applet can only access all files under the MF using the UICC FileView object */
            case (byte)0x01:
                testCaseNb = (byte)0x02;
                try {
                    uiccFileView1.select(UICCTestConstants.FID_MF);
                    uiccFileView1.select(UICCConstants.FID_EF_DIR);
                    uiccFileView1.select(UICCConstants.FID_DF_TELECOM);
                    uiccFileView1.select(UICCTestConstants.FID_MF);
                    uiccFileView1.select(UICCTestConstants.FID_DF_TEST);
                    uiccFileView1.select(UICCTestConstants.FID_DF_SUB_TEST);
                    uiccFileView1.select(UICCTestConstants.FID_EF_TAA);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                try {
                    uiccFileView1.select((short)UICCTestConstants.FID_ADF);
                    bRes = false;
                }
                catch (Exception e) {
                }

                reportTestOutcome(testCaseNb, bRes);
                break;

            /** test case 3: Applet can access all files under the ADF1 using the ADF1 FileView object */    
            case (byte)0x02:
                testCaseNb = (byte)0x03;
                bRes = false;
                // 3-8 successful select() calls
                try {
                    adf1FileView1.select((short)UICCTestConstants.FID_ADF);
                    adf1FileView1.select(UICCConstants.FID_DF_TELECOM);
                    adf1FileView1.select((short)UICCTestConstants.FID_ADF);
                    adf1FileView1.select(UICCTestConstants.FID_DF_TEST);
                    adf1FileView1.select(UICCTestConstants.FID_DF_SUB_TEST);
                    adf1FileView1.select(UICCTestConstants.FID_EF_TAA);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }

                // 9-tries to select the MF
                try {
                    adf1FileView1.select(UICCTestConstants.FID_MF);
                    bRes = false;
                }
                catch (Exception e) {
                }

                // 10-tries to select the EFDIR
                try {
                    adf1FileView1.select(UICCConstants.FID_EF_DIR);
                    bRes = false;
                }
                catch (Exception e) {
                }

                reportTestOutcome(testCaseNb, bRes);
                break;

            /** test case 4: FileView object shall be provided as a permanent JCRE entry point object */
            case (byte)0x03:
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
                    
                    // free static variable
                    classVariable = null;

                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;
                
            /** test case 5: Context independence on a FileView object */
            /** UICC FileView **/
            case (byte)0x04:
            /** ADF1 FileView **/
            case (byte)0x05:
                bRes = false;
                // 2-get another FileView object
                if (testCaseNb == 0x04) {
                    data1 = (byte)0x55;
                    data2 = (byte)0xAA;
                    FileView1 = uiccFileView1;
                    FileView2 = UICCSystem.getTheUICCView(JCSystem.NOT_A_TRANSIENT_OBJECT);
                }
                else {
                    data1 = (byte)0x55;//AA;
                    data2 = (byte)0xAA;//55;
                    FileView1 = adf1FileView1;
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
                    for (byte i = 0; i<3; i++) {
                        if (readData[i] == (byte)data1) {
                            bRes &= true;
                        } else {
                            bRes = false;
                        }
                    }

                    // 5-Read record in next mode
                    FileView1.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                         readData, (short)0, (short)3);
                    // compare read data to 0xAA
                    for (byte i = 0; i<3; i++) {
                        if (readData[i] == (byte)data2) {
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
                    for (byte i = 0; i<3; i++) {
                        if (readData[i] == (byte)data1) {
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
                break;

            /** test case 6: File Context can be transient or persistent */
            /** UICC FileView **/
            case (byte)0x06:
            /** ADF1 FileView **/
            case (byte)0x08:
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
                bRes = true;
                for (byte i = 0; i<3; i++) {
                    if (readData[i] == (byte)data1) {
                        bRes &= true;
                    } else {
                        bRes = false;
                    }
                }
                // call the readRecord() method in the NEXT mode using FileView2 object
                FileView2.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                     readData, (short)0, (short)3);
                // compare read data to 0x55
                for (byte i = 0; i<3; i++) {
                    if (readData[i] == (byte)data1) {
                        bRes &= true;
                    } else {
                        bRes = false;
                    }
                }

                testCaseNb++;
                reportTestOutcome(testCaseNb, bRes);
                break;

            case (byte)0x07:
            case (byte)0x09:
                bRes = false;
                if (testCaseNb == 0x07) {
                    data1 = (byte)0xAA;
                    tag = (byte) 0x83;
                } else {
                    data1 = (byte)0xAA;//55;
                    tag = (byte) 0x84;
                }
                // 7-call the status() command using the FileView1
                FileView1.status(readData, (short)0, (short)readData.length);
                // check the selected DF is MF (3F00) or ADF1 (A0 00.... only 2 first bytes are checked...)
                offset = findTLV(tag, readData, (short)0);
                offset+=2;
                if ( (( readData[offset] == (byte)0x3F) || (readData[offset] == (byte)0xA0)) && (readData[(short)(offset+1)] == (byte)0x00) ) {
                    bRes = true;
                } else {
                    bRes = false;
                }

                // 8-call the status() command using the FileView2
                FileView2.status(readData, (short)0, (short)readData.length);
                offset = findTLV((byte) 0x83, readData, (short)0);
                offset +=2;
                // check the selected DF is DETEST
                if ((readData[offset] == (byte)0x7F) && (readData[(short)(offset+1)] == (byte)0x4A)) {
                    bRes &= true;
                } else {
                    bRes = false;
                }
                // 9-call the readRecord() method in the NEXT mode using the FileView2
                FileView2.readRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                                     readData, (short)0, (short)3);
                // compare read data to 0xAA
                for (byte i = 0; i<3; i++) {
                    if (readData[i] == (byte)data1) {
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
                break;

            /** test case 7: File Context integrity */                
            case (byte)0x0A:
                bRes = false;
                // 2-get a UICC FileViewFileView and select DFTEST/EFCARU
                FileView1 = uiccFileView1;
                FileView1.select(UICCTestConstants.FID_DF_TEST);
                
                // 3-send a Display Text proactive command
                ProactiveHandlerSystem.getTheHandler().initDisplayText((byte)0x80, (byte)0x04, displayString, (short)0, (short)displayString.length);
                ProactiveHandlerSystem.getTheHandler().send();
                
                // 8-call status() command, using the same UICC FileView
                FileView1.status(readData, (short)0, (short)readData.length);
                // check selected DF is DF_SUB_TEST
                offset = findTLV((byte)0x83, readData, (short)0);
                offset+=2;
                if ((readData[offset] == 0x5F) && (readData[(short)(offset+1)] == 0x10)) {
                    bRes = true;
                }
                testCaseNb++;
                reportTestOutcome(testCaseNb, bRes);
                break;
            }
        }
        
        // 6-select DFTEST/DF_SUB_TEST/EF_TAA, using the previous UICC FileView.
        if (event ==  EVENT_CALL_CONTROL_BY_NAA) {
            FileView1.select(UICCTestConstants.FID_DF_TEST);
            FileView1.select(UICCTestConstants.FID_DF_SUB_TEST);
        }
    }
}
