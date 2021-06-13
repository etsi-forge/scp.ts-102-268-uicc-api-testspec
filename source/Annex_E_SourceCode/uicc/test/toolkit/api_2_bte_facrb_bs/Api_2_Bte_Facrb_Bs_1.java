//-----------------------------------------------------------------------------
//Api_2_Bte_Facrb_Bs_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_facrb_bs;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Facrb_Bs_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    byte				bArray[]		= null;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer[]		= null;

    /**
     * If AID length is not zero register applet with specified AID
     * @param bArray the array constaing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    private  Api_2_Bte_Facrb_Bs_1 (byte[] bArray, short bOffset, byte bLength) {

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
        Api_2_Bte_Facrb_Bs_1 applet = new  Api_2_Bte_Facrb_Bs_1(bArray, bOffset, bLength);
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialize the test applet values
        applet.init();
        //register applet
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
            //- FindAndCopyValue() with a null
            try{
                bte_handler.findAndCompareValue((byte)0x81,null,(short)0x00);
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
            compareBuffer = new byte[20];
            Util.arrayFillNonAtomic(compareBuffer, (short) 0, (short) 20,(byte) 0xFF);
            try{
                bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)21);
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
        //- compareOfffset < 0
        try {
            bRes = false;
            testCaseNb = (byte) 0x03;
            try{
                bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)-1);
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
        //- length> compareBuffer.length
        try {
            bRes = false;
            testCaseNb = (byte) 0x04;
            compareBuffer = new byte[15];
            Util.arrayFillNonAtomic(compareBuffer, (short)0, (short)15,(byte)0xFF);
            try{
                bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)0);
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
        //- compareOffset + length > compareBuffer
        try {
            bRes = false;
            testCaseNb = (byte) 0x05;
            compareBuffer = new byte[20];
            Util.arrayFillNonAtomic(compareBuffer, (short)0, (short)20,(byte)0xFF);
            try{
                bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)5);
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
        //- initialize the handler
        try {
            bRes = false;
            testCaseNb = (byte) 0x06;
            //- initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F };
            bte_handler.appendArray(bArray,(short)0,(short)28);
            //select a tlv tag 0x02
            bte_handler.findTLV((byte)0x02,(byte)0x01);
            //find and compare value with tlv tag 0x03
            try{
                bte_handler.findAndCompareValue((byte)0x03,compareBuffer,(short)5);
            }
            catch(ToolkitException ex){
                if (ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                    try{
                        //call the getValueLength() method
                        bte_handler.getValueLength();
                    }
                    catch(ToolkitException e){
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
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F };
            bte_handler.appendArray(bArray,(short)0,(short)28);
            //- initialize the compareBuffer
            compareBuffer = new byte[]{
                    (byte)0x04, (byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                    (byte)0x07, (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                    (byte)0x0F};
            //- compare buffers
            if(bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)0)==(byte)0x00){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8 --------------
        //- verify current TLV
        try{
            bRes = false;
            testCaseNb = (byte) 0x08;
            if (bte_handler.getValueLength()==(short)17){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x09;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                    (byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                    (byte)0x10};
            //-compare the buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)0)==(short)-1){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 10 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0A;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x03,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                    (byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                    (byte)0x0F};
            //-compare the buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)0)==(short)1){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0B;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,
                    (byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,
                    (byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x55};
            //-compare the buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)2)==(short)0){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0C;
            //append a Text String TLV
            buffer = new byte[]{(byte)0x00,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55};
            bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)6);
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,
                    (byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,
                    (byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x55};
            //-compare the buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)2)==(short)0){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 13 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0D;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x04,(byte)0x10,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,
                    (byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,
                    (byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x55};
            //-compare the buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)2)==(short)-1){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 14 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0E;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x55,(byte)0x55,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,
                    (byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,
                    (byte)0x0D,(byte)0x0D,(byte)0x10,(byte)0x55};
            //-compare the buffers
            if((short)bte_handler.findAndCompareValue((byte)0x0D,compareBuffer,(short)2)==(short)1){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 15 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x0F;
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{
                    (byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81,
                    (byte) 0x02, (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
                    (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                    (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F };
            bte_handler.appendArray(bArray,(short)0,(short)28);
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                    (byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                    (byte)0x0F};
            //-successful call (tag 0x8D)
            if((short)bte_handler.findAndCompareValue((byte)0x8D,compareBuffer,(short)0)==(short)0){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 16 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x10;
            buffer=new byte[]{
                        (byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                        (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            //-append tag 0x0F
            bte_handler.appendTLV((byte)0x0F,buffer,(short)0,(short)16);
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                    (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            //-successful call (tag 0x8F)
            if((short)bte_handler.findAndCompareValue((byte)0x8F,compareBuffer,(short)0)==(short)0){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 17 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x11;
            //-initialize the compareBuffer
            compareBuffer=new byte[]{
                    (byte)0x00,(byte)0x99,(byte)0x01,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,
                    (byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            //-successful call (tag 0x8F)
            if((short)bte_handler.findAndCompareValue((byte)0x8F,compareBuffer,(short)0)==(short)1){
                bRes=true;
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }


}