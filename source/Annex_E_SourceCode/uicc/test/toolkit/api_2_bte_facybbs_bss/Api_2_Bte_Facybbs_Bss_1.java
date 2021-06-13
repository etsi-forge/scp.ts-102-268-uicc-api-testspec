//-----------------------------------------------------------------------------
//Api_2_Bte_Facybbs_Bss_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_facybbs_bss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Facybbs_Bss_1 extends TestToolkitApplet {

    boolean				bRes		= false;
    byte				testCaseNb	= (byte) 0x00;
    byte				bArray[]	= null;
    BERTLVEditHandler	bte_handler	= null;
    byte				dstBuffer[]	= null;

    /**
     */
    private  Api_2_Bte_Facybbs_Bss_1 () {
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
        Api_2_Bte_Facybbs_Bss_1 applet = new  Api_2_Bte_Facybbs_Bss_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialize the test applet values
        applet.init();
        //register applet to event
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }


    /* (non-Javadoc)
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        //-------------- TESTCASE 1 --------------
        //- initialize the handler
        try {
            bRes = false;
            testCaseNb = (byte) 0x01;
            bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
            bte_handler.setTag((byte)0x01);
            bArray= new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x02};
            bte_handler.appendArray(bArray,(short)0,(short)9);
            //- FindAndCopyValue() with a null dstBuffer
            try{
                bte_handler.findAndCopyValue((byte)0x01,null,(byte)0x00);
            }
            catch(NullPointerException exp){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2 --------------
        //-dstOffset > dstBuffer.length
        try{
            bRes = false;
            testCaseNb = (byte) 0x02;
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x10,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                    (byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,
                    (byte)0x0C,(byte)0x0D,(byte)0x0E};
            bte_handler.appendArray(bArray,(short)0,(short)27);
            dstBuffer = new byte[5];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 5, (byte) 0xFF);
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)6,(short)0);
            }
            catch(ArrayIndexOutOfBoundsException exp){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 3 --------------
        //-dstOffset > 0
        try{
            bRes = false;
            testCaseNb = (byte) 0x03;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)-1,(short)1);
            }
            catch(ArrayIndexOutOfBoundsException exp){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4 --------------
        //dstLength>dstBuffer.length
        try{
            bRes = false;
            testCaseNb = (byte) 0x04;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)0,(short)6);
            }
            catch(ArrayIndexOutOfBoundsException exp){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 5 --------------
        //dstOffset+dstBuffer.length
        try{
            bRes = false;
            testCaseNb = (byte) 0x05;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)3,(short)3);
            }
            catch(ArrayIndexOutOfBoundsException exp){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 6 --------------
        //dstLength<0
        try{
            bRes = false;
            testCaseNb = (byte) 0x06;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)0,(short)-1);
            }
            catch(ArrayIndexOutOfBoundsException exp){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 7 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x07;
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x06,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                    (byte)0x04};
            bte_handler.appendArray(bArray,(short)0,(short)17);
            dstBuffer = new byte[15];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 15, (byte) 0xFF);
            //- valueOffset > Text String Length
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)7,dstBuffer,(short)0,(short)0);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8 --------------
        //- valueOffset < 0
        try{
            bRes = false;
            testCaseNb = (byte) 0x08;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)-1,dstBuffer,(short)0,(short)1);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9 --------------
        //- dstLength> Text String Length
        try{
            bRes = false;
            testCaseNb = (byte) 0x09;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)0,(short)7);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 10 --------------
        //- valueOffset + dstLength > Text String length
        try{
            bRes = false;
            testCaseNb = (byte) 0x0A;
            try{
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)2,dstBuffer,(short)0,(short)5);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11 --------------
        //- valueOffset + dstLength > Text String length
        try{
            bRes = false;
            testCaseNb = (byte) 0x0B;
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.appendArray(bArray,(short)0,(short)28);

            //- findAndCopyValue()
            try{
                bte_handler.findTLV((byte)0x02,(byte)0x01);
                bte_handler.findAndCopyValue((byte)0x0D,(byte)0x02,(short)2,dstBuffer,(short)0,(short)0);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                    //- call the getValueLength() method
                    try{
                        bte_handler.getValueLength();
                    }
                    catch(ToolkitException tlkexp){
                        if(ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                            bRes=true;
                        }
                    }
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0C;
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.appendArray(bArray,(short)0,(short)28);
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 17, (byte) 0xFF);
            if(	bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)0,(short)17)==(short)17){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 13 --------------
        //- compare buffer
        try{
            bRes = true;
            testCaseNb = (byte) 0x0D;
            for (short i=0;i<17; i++){
                if (bArray[(short)(11+i)]!=dstBuffer[i]){bRes=false;}
            }
        }
        catch(Exception exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 14 --------------
        try{
            bRes=false;
            testCaseNb=(byte)0x0E;
            //-initialize dstBuffer
            dstBuffer = new byte[20];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 20, (byte) 0x55);
            //-successful call
            if(bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)2,dstBuffer,(short)3,(short)12)==(short)15){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 15 --------------
        //-compare the buffer
        try{
            bRes=true;
            testCaseNb=(byte)0x0F;
            for (short i = 0; i < 12; i++) {
                if (bArray[(short)(13 + i)] != dstBuffer[(short)(i + 3)]) {
                    bRes = false;
                }
            }
            for (short i=0; i<3; i++){
                if(dstBuffer[i]!=(byte)0x55){bRes=false;}
            }
            for (short i=19; i>14; i--){
                if(dstBuffer[i]!=(byte)0x55){bRes=false;}
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 16 --------------
        try{
            bRes=false;
            testCaseNb=(byte)0x10;
            byte[] buffer={(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55};
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 17, (byte) 0xFF);
            //-append a text string TLV
            bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)6);

            //-successful call
            if(bte_handler.findAndCopyValue((byte)0x0D,(byte)0x01,(short)0,dstBuffer,(short)0,(short)17)==(short)17){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 17 --------------
        //-compare the buffer
        try{
            bRes=true;
            testCaseNb=(byte)0x11;
            for (short i = 0; i < 17; i++) {
                if (bArray[(short)(11 + i)] != dstBuffer[(short)(i)]) {
                    bRes = false;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 18 --------------
        try{
            bRes=false;
            testCaseNb=(byte)0x12;
            dstBuffer = new byte[6];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 6, (byte) 0xFF);
            //-successful call
            if(bte_handler.findAndCopyValue((byte)0x0D,(byte)0x02,(short)0,dstBuffer,(short)0,(short)6)==(short)6){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 19 --------------
        //-compare the buffer
        try{
            byte[] buffer={(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55};
            bRes=true;
            testCaseNb=(byte)0x13;
            for (short i = 0; i < 6; i++) {
                if (buffer[(short)i] != dstBuffer[(short)(i)]) {
                    bRes = false;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 20 --------------
        try{
            bRes=false;
            testCaseNb=(byte)0x14;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.appendArray(bArray,(short)0,(short)28);
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 17, (byte) 0xFF);
            if(	bte_handler.findAndCopyValue((byte)0x8D,(byte)0x01,(short)0,dstBuffer,(short)0,(short)17)==(short)17){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 21 --------------
        //-compare the buffer
        try{
            bRes=true;
            testCaseNb=(byte)0x15;
            for (short i = 0; i < 17; i++) {
                if (bArray[(short)(11 + i)] != dstBuffer[(short)(i)]) {
                    bRes = false;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 22 --------------
        try{
            bRes=false;
            testCaseNb=(byte)0x16;
            byte buffer[] = {(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                    (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            //-append tag 0x0F
            bte_handler.appendTLV((byte)0x0F,buffer,(short)0,(short)16);
            dstBuffer = new byte[16];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 16, (byte) 0xFF);
            //-successful call(with tag 0x8F);
            if(	bte_handler.findAndCopyValue((byte)0x8F,(byte)0x01,(short)0,dstBuffer,(short)0,(short)16)==(short)16){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 23 --------------
        //-compare the buffer
        try{
            bRes=true;
            testCaseNb=(byte)0x17;
            for (short i = 0; i < 16; i++) {
                if (bArray[(short)(12 + i)] != dstBuffer[(short)(i)]) {
                    bRes = false;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }

}