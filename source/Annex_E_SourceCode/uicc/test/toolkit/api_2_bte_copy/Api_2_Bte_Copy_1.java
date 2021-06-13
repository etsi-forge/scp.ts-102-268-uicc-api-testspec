//-----------------------------------------------------------------------------
//Api_2_Bte_Copy_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_copy;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
//

import javacard.framework.ISOException;
import javacard.framework.Util;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

/**
 * @author GHartbrod TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class Api_2_Bte_Copy_1 extends TestToolkitApplet {
    private byte dstBuffer[] = null;
    private byte bArray[]={(byte)0x81, (byte)0x03, (byte)0x01, (byte)0x41, (byte)0x42, (byte)0x82, (byte)0x02, (byte)0x81, (byte)0x43};

    /**
     * If AID length is not zero register applet with specified AID
     *
     * @param bArray the array constaing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    private Api_2_Bte_Copy_1 () {
    }

    /**
     * Create an instance of the applet, the Java Card runtime environment will call this static method first.
     *
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray The maximum value of bLength is 127.
     * @throws ISOException if the install method failed
     * @see javacard.framework.Applet
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException {
        Api_2_Bte_Copy_1 applet = new Api_2_Bte_Copy_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialise the test applet values
        applet.init();
        //initialze byte array with fill value
        applet.dstBuffer = new byte[15];
        Util.arrayFillNonAtomic(applet.dstBuffer,(short)0,(short)15,(byte)0xFF);
        //register applet to event
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }

    /*
     * (non-Javadoc)
     *
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        boolean bRes=false;
        byte testCaseNb=(byte)0x01;
        /// allocate a BERTLVEditHandler
        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);

        bte_handler.setTag((byte)0x01);
        //-------------- TESTCASE 1--------------
        //- NULL as parameter to dstBuffer
        bte_handler.appendArray(this.bArray,(short)0,(short)9);
        try{
            bte_handler.copy(null,(short)0,(short)1);
        }
        catch(NullPointerException exp){bRes=true;}
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2--------------
        //- dstOffset < dstBuffer
        bRes=false;
        testCaseNb=(byte)0x02;
        try{
            dstBuffer = new byte[5];
            bte_handler.copy(dstBuffer,(short)6,(short)0);
        }
        catch(ArrayIndexOutOfBoundsException exp){bRes=true;}
        catch(Exception exp){bRes=false;}

        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 3--------------
        //- dstOffset < 0
        bRes=false;
        testCaseNb=(byte)0x03;
        try{
            bte_handler.copy(dstBuffer,(short)-1,(short)1);
        }
        catch(ArrayIndexOutOfBoundsException exp){bRes=true;}
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4--------------
        //- dstLength > dstBuffer.length
        bRes=false;
        testCaseNb=(byte)0x04;
        try{
            bte_handler.copy(dstBuffer,(short)0,(short)6);
        }
        catch(ArrayIndexOutOfBoundsException exp){bRes=true;}
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);

        //-------------- TESTCASE 5--------------
        //- dstOffset + dstLength > dstBuffer.length
        bRes=false;
        testCaseNb=(byte)0x05;
        try{
            bte_handler.copy(dstBuffer,(short)3,(short)3);
        }
        catch(ArrayIndexOutOfBoundsException exp){bRes=true;}
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 6--------------
        //- dstLength < 0
        bRes=false;
        testCaseNb=(byte)0x06;
        try{
            bte_handler.copy(dstBuffer,(short)0,(short)-1);
        }
        catch(ArrayIndexOutOfBoundsException exp){bRes=true;}
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 7--------------
        //- dstLength > length of the comprehension TLV list
        bRes=false;
        testCaseNb=(byte)0x07;
        try{
            dstBuffer = new byte[10];
            bte_handler.copy(dstBuffer,(short)0,(short)10);
        }
        catch(ToolkitException exp){
            if (exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) bRes=true;
            }
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8--------------
        //- successfull call, dstBuffer is the whole bufffer
        bRes=false;
        testCaseNb=(byte)0x08;
                
        try{
            dstBuffer = new byte[9];
            if(bte_handler.copy(dstBuffer,(short)0,(short)9)==(short)9){
               bRes=true;
            }
        }
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9--------------
        //- compare the buffer
        bRes=false;
        testCaseNb=(byte)0x09;

        if(Util.arrayCompare(dstBuffer,(short)0,bArray,(short)0,(short)9)==(byte)0x00){
           bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 10--------------
        //- successfull call, dstBuffer is a part of a buffer
        bRes=false;
        testCaseNb=(byte)0x0A;
        try{
            dstBuffer = new byte[15];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)15,(byte)0xFF);
            if(bte_handler.copy(dstBuffer,(short)3,(short)9)==(short)12){
                bRes=true;
            }
        }
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11--------------
        //- compare the whole buffer
        bRes=false;
        testCaseNb=(byte)0x0B;
        byte compareBuffer[]=new byte[15];
        Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)15,(byte)0xFF);
        //compare the copied value
        if(Util.arrayCompare(dstBuffer,(short)3,bArray,(short)0,(short)9)==(byte)0x00){
            //compare the first part of dstBuffer
            if(Util.arrayCompare(dstBuffer,(short)0,compareBuffer,(short)0,(short)3)==(byte)0x00){
                //compare the last part of dstBuffer
                if(Util.arrayCompare(dstBuffer,(short)13,compareBuffer,(short)13,(short)2)==(byte)0x00){
                    bRes=true;
                }
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12--------------
        //- successfull call, dstBuffer is a part of a buffer
        bRes=false;
        testCaseNb=(byte)0x0C;
        try{
            dstBuffer = new byte[15];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)15,(byte)0xFF);
            if(bte_handler.copy(dstBuffer,(short)3,(short)6)==(short)9){
                bRes=true;
            }
        }
        catch(Exception exp){bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 13--------------
        //- compare the whole buffer
        bRes=false;
        testCaseNb=(byte)0x0D;
        compareBuffer=new byte[15];
        Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)15,(byte)0xFF);
        //compare the copied value
        if(Util.arrayCompare(dstBuffer,(short)3,bArray,(short)0,(short)6)==(byte)0x00){
            //compare the first part of dstBuffer
            if(Util.arrayCompare(dstBuffer,(short)0,compareBuffer,(short)0,(short)3)==(byte)0x00){
               //compare the last part of dstBuffer
                if(Util.arrayCompare(dstBuffer,(short)9,compareBuffer,(short)9,(short)6)==(byte)0x00){
                    bRes=true;
                }
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);

    }

}
