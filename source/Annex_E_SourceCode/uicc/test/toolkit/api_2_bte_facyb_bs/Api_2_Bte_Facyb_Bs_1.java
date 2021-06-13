//-----------------------------------------------------------------------------
//Ape_2_Bte_Facyb_Bs_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_facyb_bs;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Facyb_Bs_1 extends TestToolkitApplet {

    boolean				bRes		= false;
    byte				testCaseNb	= (byte) 0x00;
    byte				bArray[]	= null;
    BERTLVEditHandler	bte_handler	= null;
    byte				dstBuffer[]	= null;

    /**

     */
    private  Api_2_Bte_Facyb_Bs_1 () {
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
        Api_2_Bte_Facyb_Bs_1 applet = new  Api_2_Bte_Facyb_Bs_1();
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
        //- initialize the handler
        try{
            bRes = false;
            testCaseNb = (byte) 0x02;
            bArray=new byte[]{	(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                                (byte)0x02,(byte)0x0D,(byte)0x10,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                                (byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,
                                (byte)0x0C,(byte)0x0D,(byte)0x0E};

            bte_handler.clear();
            bte_handler.appendArray(bArray,(short)0,(short)27);
            bte_handler.setTag((byte)0x01);
            dstBuffer=new byte[20];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)20,(byte)0xFF);
            //-dstOffset>dstBuffer.length
            try{
                bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)21);
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
        //- dstOffst<0
        try{
            bRes = false;
            testCaseNb = (byte) 0x03;

            //-dstOffset>dstBuffer.length
            try{
                bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)-1);
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
        //- length > dstBuffer.length
        try{
            bRes = false;
            testCaseNb = (byte) 0x04;
            dstBuffer = new byte[15];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)15,(byte)0xFF);
            //-dstOffset>dstBuffer.length
            try{
                bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)0);
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
        //- DstOffset + length > dstBuffer.length
        try{
            bRes=false;
            testCaseNb=(byte)0x05;
            dstBuffer = new byte[20];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)20,(byte)0xFF);
            try{
                bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)5);
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
        try{
            bRes = false;
            testCaseNb = (byte) 0x06;
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x11,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                    (byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,
                    (byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};

            bte_handler.appendArray(bArray,(short)0,(short)28);
            bte_handler.setTag((byte)0x01);
            bte_handler.findTLV((byte)0x02,(byte)0x01);

            try{
                bte_handler.findAndCopyValue((byte)0x03,dstBuffer,(short)0);
            }
            catch(ToolkitException exp){
                if (exp.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                    try{
                        //call the getValueLength Method
                        bte_handler.getValueLength();
                    }
                    catch(ToolkitException ex){
                        if (ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
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
        //-------------- TESTCASE 7 --------------
        //- initialize the handler
        try{
            bRes = false;
            testCaseNb = (byte) 0x07;
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x11,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                    (byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,
                    (byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};

            bte_handler.appendArray(bArray,(short)0,(short)28);
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)17,(byte)0xFF);
            if(bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)0)==(short)17){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8 --------------
        //-compare buffer
        try{
            bRes = true;
            testCaseNb =(byte)0x08;
            for (short i=0;i<17; i++){
                if (bArray[(short)(11+i)]!=dstBuffer[i]){bRes=false;}
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x09;
            dstBuffer = new byte[20];
            Util.arrayFillNonAtomic(dstBuffer,(short)0,(short)20,(byte)0x55);
            //-successful call
            if (bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)2)==(short)19){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 10 --------------
//		- compare the buffer
        try {
            bRes = true;
            testCaseNb = (byte) 0x0A;
            for (short i = 0; i < 17; i++) {
                if (bArray[(short)(11 + i)] != dstBuffer[(short)(i + 2)]) {
                    bRes = false;
                }
            }
            if (dstBuffer[0] != (byte) 0x55 || dstBuffer[1] != (byte) 0x55 || dstBuffer[19] != (byte) 0x55) {
                bRes = false;
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0B;
            bte_handler.clear();
            bArray = new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.appendArray(bArray, (short) 0, (short) 28);
            bte_handler.setTag((byte) 0x01);
            //-append a 2nd Text String TLV
            bte_handler.appendTLV((byte) 0x0D, (byte) 0xFF);
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 17, (byte) 0x55);
            //	-successfull call
            if(bte_handler.findAndCopyValue((byte)0x0D,dstBuffer,(short)0)==(short)17){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12 --------------
        //-compare the buffer
        try {
            bRes = true;
            testCaseNb = (byte) 0x0C;
            for (short i=0;i<17; i++){
                if (bArray[(short)(11+i)]!=dstBuffer[i]){bRes=false;}
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 13 --------------
        //- initialize the handler
        try{
            bRes = false;
            testCaseNb = (byte) 0x0D;
            bte_handler.clear();
            bArray = new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.appendArray(bArray, (short) 0, (short) 28);
            bte_handler.setTag((byte) 0x01);
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 17, (byte) 0x55);
            //- successful call (tag 0x8D)
            if(bte_handler.findAndCopyValue((byte)0x8D,dstBuffer,(short)0)==(short)17){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 14 --------------
        //- compare the buffer
        try{
            bRes = true;
            testCaseNb = (byte) 0x0E;
            for (short i=0;i<17; i++){
                if (bArray[(short)(11+i)]!=dstBuffer[i]){bRes=false;}
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 15 --------------
        //- append tag (0x0F)
        try{
            bRes = false;
            testCaseNb = (byte) 0x0F;
            dstBuffer = new byte[16];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 16, (byte) 0x55);
            byte buffer[]={
                        (byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                        (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            bte_handler.appendTLV((byte)0x0F,buffer,(short)0,(short)16);
            if(bte_handler.findAndCopyValue((byte)0x8F,dstBuffer,(short)0)==(short)16){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 16 --------------
        //- compare the buffer
        try{
            bRes = true;
            testCaseNb = (byte) 0x10;
            for (short i=0;i<16; i++){
                if (bArray[(short)(12+i)]!=dstBuffer[i]){bRes=false;}
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }

}