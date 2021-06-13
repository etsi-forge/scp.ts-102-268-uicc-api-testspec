//-----------------------------------------------------------------------------
//Ape_2_Bte_Cpyv_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_cpyv;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Cpyv_1 extends TestToolkitApplet {

    boolean	bRes		= false;
    byte	testCaseNb	= (byte) 0x00;
    byte	bArray[]	= null;
    byte	dstBuffer[]	= null;

    /**
     */
    private Api_2_Bte_Cpyv_1 () {
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
        Api_2_Bte_Cpyv_1 applet = new Api_2_Bte_Cpyv_1();
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
        //

        boolean bRes = false;

        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
        try {
            bte_handler.setTag((byte) 0x01);
            //-------------- TESTCASE 1 --------------
            //- initialize the handler & select a TLV
            byte testCaseNb = (byte) 0x01;
            bArray = new byte[]{(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82,(byte) 0x02, (byte) 0x81,(byte)0x82};
            bte_handler.appendArray(bArray, (short) 0, (short) 9);
            bte_handler.findTLV((byte) 0x81, (byte) 0x01);
            //copyValue() with null dstBuffer
            try {
                bte_handler.copyValue((short)0,null,(short)0,(short)1);
            }
            catch (NullPointerException exp) {
                bRes = true;
            }
            this.reportTestOutcome(testCaseNb, bRes);
        }
        catch (Exception exp) {
            bRes = false;
            this.reportTestOutcome(testCaseNb, bRes);
        }
        //-------------- TESTCASE 2 --------------
        //- dstOffset > dstBuffer.length
        try{
            bRes=false;
            testCaseNb=(byte)0x02;
            bArray = new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                                (byte)0x02,(byte)0x0D,(byte)0x0D,(byte)0x10,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,
                                (byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,
                                (byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E};
            bte_handler.clear();
            // - initialize the handler
            bte_handler.appendArray(bArray,(short)0,(short)24);
            bte_handler.setTag((byte)0x01);
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            dstBuffer = new byte[]{(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04};
            //- dstOffset > dstBuffer.length
            try{
            bte_handler.copyValue((short)0,dstBuffer,(short)6,(short)0);
            }
            catch(ArrayIndexOutOfBoundsException exp){
                bRes=true;
            }

        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 3 --------------
        //-dstOffset < 0
        try{
            bRes=false;
            testCaseNb=(byte)0x03;
            bte_handler.copyValue((short)0,dstBuffer,(short)-1,(short)1);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 4 --------------
        //-dstLength>dstBuffer.length
        try{
            bRes=false;
            testCaseNb=(byte)0x04;
            bte_handler.copyValue((short)0,dstBuffer,(short)0,(short)6);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 5 --------------
        //-dstOffset + dstLength>dstBuffer.length
        try{
            bRes=false;
            testCaseNb=(byte)0x05;
            bte_handler.copyValue((short)0,dstBuffer,(short)3,(short)3);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 6 --------------
        //-dstLength < 0
        try{
            bRes=false;
            testCaseNb=(byte)0x06;
            bte_handler.copyValue((short)0,dstBuffer,(short)0,(short)-1);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 7 --------------
        //-valueOffset > Text String Length
        try{
            bRes=false;
            testCaseNb=(byte)0x07;
            bArray = new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                                (byte)0x02,(byte)0x0D,(byte)0x06,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                                (byte)0x04};
            bte_handler.clear();
            // - initialize the handler
            bte_handler.appendArray(bArray,(short)0,(short)17);
            bte_handler.setTag((byte)0x01);
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            dstBuffer = new byte[15];
            for (short i=0; i<(short)15; i++){dstBuffer[i]=(byte)i;}
            try{
                bte_handler.copyValue((short)7,dstBuffer,(short)0,(short)0);
            }
            catch(ToolkitException exp){
                if (exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 8 --------------
        //- valueOffset < 0
        try{
            bRes=false;
            testCaseNb=(byte)0x08;
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            bte_handler.copyValue((short)-1,dstBuffer,(short)0,(short)1);
        }
        catch(ToolkitException exp){
            if (exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 9 --------------
        //- dstLength> Text String length
        try{
            bRes=false;
            testCaseNb=(byte)0x09;
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            bte_handler.copyValue((short)-1,dstBuffer,(short)0,(short)7);
        }
        catch(ToolkitException exp){
            if (exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 10 --------------
        //- valueOffset + dstLength > Text String
        try{
            bRes=false;
            testCaseNb=(byte)0x0A;
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            bte_handler.copyValue((short)2,dstBuffer,(short)0,(short)5);
        }
        catch(ToolkitException exp){
            if (exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 11 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0B;
            bArray = new byte[]{(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82,
                    (byte) 0x02, (byte) 0x81, (byte) 0x82};
            bte_handler.clear();
            // - initialize the handler
            bte_handler.appendArray(bArray, (short) 0, (short) 9);
            bte_handler.setTag((byte) 0x01);
            try{
                            bte_handler.copyValue((short)2,dstBuffer,(short)0,(short)5);
            }
            catch(ToolkitException exp){
                if (exp.getReason()== ToolkitException.UNAVAILABLE_ELEMENT){
                    bRes=true;
                }
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 12 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0C;

            bArray = new byte[]{(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                                (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                                (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                                (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.clear();
            // - initialize the handler
            bte_handler.appendArray(bArray, (short) 0, (short) 28);
            bte_handler.setTag((byte) 0x01);



            bte_handler.findTLV((byte) 0x0D, (byte) 0x01);
            dstBuffer = new byte[17];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 17, (byte) 0x55);
            if (bte_handler.copyValue((short) 0, dstBuffer, (short) 0, (short) 17) == (short) 17) {
                bRes = true;
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 13 --------------
        //- Compare Buffer
        try {
            bRes = false;
            testCaseNb = (byte) 0x0D;
            if (Util.arrayCompare(bArray,(short)11,dstBuffer,(short)0,(short)17)==(byte)0x00){
                bRes=true;
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 14 --------------
        try{
            bRes=false;
            testCaseNb=(byte)0x0E;
            dstBuffer= new byte[20];
            Util.arrayFillNonAtomic(dstBuffer, (short) 0, (short) 20, (byte) 0x55);
            if(bte_handler.copyValue((short)2,dstBuffer,(short)3,(short)12)==(short) 15){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 15 --------------
        //- compare buffer
        try {
            bRes = false;
            testCaseNb = (byte) 0x0F;
            byte compareBuff[] = new byte[]{(byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x01,
                    (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08,
                    (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
                    (byte) 0x55, (byte) 0x55};

            if(Util.arrayCompare(compareBuff,(short)0,dstBuffer,(short)0,(short)20)==(byte)0x00){
                bRes=true;
            }

        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);

    }
}



