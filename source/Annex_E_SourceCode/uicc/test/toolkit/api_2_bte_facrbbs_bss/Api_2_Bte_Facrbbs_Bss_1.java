//-----------------------------------------------------------------------------
//Api_2_Bte_Facrbbs_Bss_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_facrbbs_bss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Facrbbs_Bss_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    byte				bArray[]		= null;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer[]		= null;

    /**
     */
    private Api_2_Bte_Facrbbs_Bss_1 () {
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
        Api_2_Bte_Facrbbs_Bss_1 applet = new  Api_2_Bte_Facrbbs_Bss_1();
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
            bArray= new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x82};
            bte_handler.appendArray(bArray,(short)0,(short)9);
            compareBuffer = new byte[5];
            //- FindAndCopyValue() with a null compareBuffer
            try{
                bte_handler.findAndCompareValue((byte)0x01,(byte)0x01,(short)0,null,(short)0,(short)3);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x02;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x10, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E };
            bte_handler.appendArray(bArray,(short)0,(short)27);
            compareBuffer = new byte[5];
            Util.arrayFillNonAtomic(compareBuffer, (short) 0, (short) 5,(byte) 0xFF);
            //- compareOffset > compareBufffer.length
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)6,(short)0);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x03;
            //-compareOffset < 0
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)-1,(short)1);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x04;
            //- compareLength > compareBuffer.length
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)6);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x05;
            //- compareOffset+compareLength > compareBuffer.length
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)3,(short)3);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x06;
            //- compareLength < 0
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)-1);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x07;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x06, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04 };
            bte_handler.appendArray(bArray,(short)0,(short)17);
            compareBuffer = new byte[15];
            Util.arrayFillNonAtomic(compareBuffer, (short) 0, (short) 15,(byte) 0xFF);
            // valueOffset > Text String Length
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)7,compareBuffer,(short)0,(short)0);
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
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x08;
            // valueOffst < 0
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)-1,compareBuffer,(short)0,(short)1);
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
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x09;
            // compareLength > Text Strign length
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)7);
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
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 10 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0A;
            //valueOffset+compareLength > Text String length
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)2,compareBuffer,(short)0,(short)5);
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
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0B;
            //- invalid Parameter
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x00,(short)0,compareBuffer,(short)0,(short)5);
            }
            catch(ToolkitException exp){
                if (exp.getReason()==ToolkitException.BAD_INPUT_PARAMETER){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0C;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F };
            bte_handler.appendArray(bArray,(short)0,(short)28);
            //- select a TLV (tag 0x02)
            bte_handler.findTLV((byte)0x02,(byte)0x01);
            //- findAndCompareValue
            try{
                bte_handler.findAndCompareValue((byte)0x0D,(byte)0x02,(short)0,compareBuffer,(short)0,(short)0);
            }
            catch(ToolkitException ex){
                if (ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                    try{
                        //- Call the getValueLength() method
                        bte_handler.getValueLength();
                    }
                    catch (ToolkitException e){
                        if (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
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
        //-------------- TESTCASE 13 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0D;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F };
            bte_handler.appendArray(bArray,(short)0,(short)28);
            //-initialize compareBuffer
            compareBuffer = new byte[]{
                    (byte) 0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte) 0x05, (byte)0x06,
                    (byte) 0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte) 0x0D,(byte)0x0E,
                    (byte) 0x0F};
            //- findAndCompareValue
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)17)==(short)0){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 14 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0E;
            //-verify current TLV
            if(bte_handler.getValueLength()==(short)17){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 15 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0F;
            //- initialize compareBuffer
            compareBuffer = new byte[]{
                    (byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte) 0x05, (byte)0x06,
                    (byte) 0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte) 0x0D,(byte)0x0E,
                    (byte)0x10};
            //- findAndCompareValue
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)17)==(short)-1){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 16 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x10;
            //- initialize compareBuffer
            compareBuffer = new byte[]{
                    (byte)0x03,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte) 0x05, (byte)0x06,
                    (byte) 0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte) 0x0D,(byte)0x0E,
                    (byte) 0x0F};
            //- findAndCompareValue
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)17)==(short)1){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 17 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x11;
            //- initialize compareBuffer
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,
                    (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x55,
                    (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55 };
            //- compare buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)2,compareBuffer,(short)3,(short)12)==(short)0){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 18 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x12;
            //- initialize compareBuffer
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x02,(byte)0x01,(byte)0x03,(byte)0x04,(byte)0x05,
                    (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x55,
                    (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55 };
            //- compare buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)2,compareBuffer,(short)3,(short)12)==(short)-1){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 19 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x13;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,
                    (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0A,(byte)0x0D,(byte)0x55,
                    (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55 };
            //- compare buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)2,compareBuffer,(short)3,(short)12)==(short)1){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 20 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x14;
            buffer = new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55};
            //- append a Text String TLV
            bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)6);
            //- initialize compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                    (byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                    (byte)0x0F};
            //- compare buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)17)==(short)0){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 21 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x15;
            //- initialize compareBuffer
            compareBuffer = new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55};
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x02,(short)0,compareBuffer,(short)0,(short)6)==(short)0){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 22 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x16;
            //- initialize compareBuffer
            compareBuffer = new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x66};
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x02,(short)0,compareBuffer,(short)0,(short)6)==(short)-1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 23 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x17;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F };
            bte_handler.appendArray(bArray,(short)0,(short)28);
            //-initialize compareBuffer
            compareBuffer = new byte[]{
                    (byte) 0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte) 0x05,(byte)0x06,
                    (byte) 0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte) 0x0D,(byte)0x0E,
                    (byte) 0x0F};
            //- successful call (with tag 0x8D)
            if((short)bte_handler.findAndCompareValue((byte)0x8D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)17)==(short)0){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 24 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x18;
            buffer = new byte[]{
                    (byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                    (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            //- append a TLV (tag 0x0F)
            bte_handler.appendTLV((byte)0x0F,buffer,(short)0,(short)16);
            //- initialize compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                    (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            //- successful call (with tag 0x8F)
            if((short)bte_handler.findAndCompareValue((byte)0x8F,(byte)0x01,(short)0,compareBuffer,(short)0,(short)16)==(short)0){
                bRes=true;
            }

        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 25 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x19;
            //- initialize compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x04,(byte)0x00,(byte)0x99,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                    (byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                    (byte)0x0F};
            if((short)bte_handler.findAndCompareValue((byte)0x0D,(byte)0x01,(short)0,compareBuffer,(short)0,(short)17)==(short)1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }

}
