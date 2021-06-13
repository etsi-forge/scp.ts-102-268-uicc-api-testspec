//-----------------------------------------------------------------------------
//Ape_2_Bte_Cprv_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_cprv;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Cprv_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    byte				bArray[]		= null;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;

    /**
     */
    private  Api_2_Bte_Cprv_1 () {
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
        Api_2_Bte_Cprv_1 applet = new  Api_2_Bte_Cprv_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialise the test applet values
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
            bte_handler.findTLV((byte)0x81,(byte)0x01);//select TLV structure 0x81

            //- compareValue() with a null compareBuffer
            try{
                bte_handler.compareValue((short)0,null,(short)0,(short)1);
            }
            catch(NullPointerException exp){
                bRes=true;
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2 --------------
        //- initialize the handler
        try{
            bRes=false;
            testCaseNb=(byte)0x02;
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray= new byte[]{ (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                                (byte)0x02,(byte)0x0D,(byte)0x0F,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                                (byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0C,
                                (byte)0x0D,(byte)0x0E};

                        bte_handler.appendArray(bArray,(short)0,(short)bArray.length);
                        bte_handler.findTLV((byte)0x0D,(byte)0x01);
            compareBuffer = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
            //- compareOffset>compareBuffer.length
            try{
                bte_handler.compareValue((short)0,compareBuffer,(short)6,(short)0);
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
        //- compareOffset < 0
        try{
            bRes=false;
            testCaseNb=0x03;
            bte_handler.compareValue((short)0,compareBuffer,(short)-1,(short)1);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4 --------------
        //- compareLength>compareBuffer.length
        try{
            bRes=false;
            testCaseNb=0x04;
            bte_handler.compareValue((short)0,compareBuffer,(short)0,(short)6);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 5 --------------
        //- compareOffset+compareLength>compareBuffer.length
        try{
            bRes=false;
            testCaseNb=0x05;
            bte_handler.compareValue((short)0,compareBuffer,(short)3,(short)3);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 6--------------
        //- compareLength<0
        try{
            bRes=false;
            testCaseNb=0x06;
            bte_handler.compareValue((short)0,compareBuffer,(short)0,(short)-1);
        }
        catch(ArrayIndexOutOfBoundsException exp){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 7--------------
        //- initialize the handler
        try{
            bRes=false;
            testCaseNb=(byte)0x07;
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bArray= new byte[]{ (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                                (byte)0x02,(byte)0x0D,(byte)0x06,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,
                                (byte)0x04};
                      bte_handler.appendArray(bArray,(short)0, (short)17);
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            compareBuffer = new byte[15];
            Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)15,(byte)0xFF);
            //- valueOffset>Text String Length
            try{
                bte_handler.compareValue((short)7,compareBuffer,(short)0,(short)0);
            }
            catch(ToolkitException exp){
                if(exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                    bRes=true;
                }
            }
        }
        catch (Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8--------------
        //- valueOffset < 0
        try {
            bRes = false;
            testCaseNb = (byte) 0x08;
            bte_handler.findTLV((byte) 0x0D, (byte) 0x01);
            try {
                bte_handler.compareValue((short) -1, compareBuffer, (short) 0, (short) 1);
            }
            catch (ToolkitException exp) {
                if (exp.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true;
                }
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 9--------------
        //- compareLength > Text String Length
        try {
            bRes = false;
            testCaseNb = (byte) 0x09;
            bte_handler.findTLV((byte) 0x0D, (byte) 0x01);
            try {
                bte_handler.compareValue((short) 0, compareBuffer, (short) 0, (short) 7);
            }
            catch (ToolkitException exp) {
                if (exp.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true;
                }
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 10--------------
        //- valueOffset + compareLength > Text String Length
        try {
            bRes = false;
            testCaseNb = (byte) 0x0A;
            bte_handler.findTLV((byte) 0x0D, (byte) 0x01);
            try {
                bte_handler.compareValue((short) 2, compareBuffer, (short) 0, (short) 5);
            }
            catch (ToolkitException exp) {
                if (exp.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true;
                }
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 11--------------
        //- initialize the handler
        try {
            bRes = false;
            testCaseNb = (byte) 0x0B;
            bte_handler.clear();
            bte_handler.setTag((byte) 0x01);
            bArray = new byte[]{(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x82};
                        bte_handler.appendArray(bArray,(short)0, (short)9);
            try {
                bte_handler.compareValue((short) 0, compareBuffer, (short) 0, (short) 0);
            }
            catch (ToolkitException exp) {
                if (exp.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                    bRes = true;
                }
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 12--------------
        //- initialize the handler
        try {
            bRes = false;
            testCaseNb = (byte) 0x0C;
            bte_handler.clear();
            bte_handler.setTag((byte) 0x01);
            bArray = new byte[]{(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x02,
                    (byte) 0x0D, (byte) 0x11, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06,
                    (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            bte_handler.appendArray(bArray,(short)0, (short)28);
            bte_handler.findTLV((byte) 0x0D, (byte) 0x01);
            //- initialize compareBuffer
            compareBuffer = new byte[]{(byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06,
                                       (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
            //- compare buffers
            if (bte_handler.compareValue((short) 0, compareBuffer, (short) 0, (short) 17)==(byte)0x00){
                bRes=true;
            }
        }
        catch (Exception exp) {
            bRes = false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 13--------------
        //- initialize compare Buffer
        try{
            bRes=false;
            testCaseNb=(byte)0x0D;
            compareBuffer= new byte[]{	(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                                        (byte)0x07,(byte)0x08,(byte)0x0F,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                                        (byte)0x10};
            //compare the buffer
            if ((short)bte_handler.compareValue((short) 0, compareBuffer, (short) 0, (short) 17)==(short)-1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
//		-------------- TESTCASE 14--------------
        //- initialize compare Buffer
        try{
            bRes=false;
            testCaseNb=(byte)0x0E;
            compareBuffer= new byte[]{	(byte)0x03,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,
                                        (byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                                        (byte)0x0F};
            //compare the buffer
            if ((short)bte_handler.compareValue((short) 0, compareBuffer, (short) 0, (short) 17)==(short)1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);


        //-------------- TESTCASE 15--------------
        //- initialize compare Buffer
        try{
            bRes=false;
            testCaseNb=(byte)0x0F;
            compareBuffer= new byte[]{	(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,
                                        (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x55,
                                        (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55};
            //compare the buffer
            if (bte_handler.compareValue((short) 2, compareBuffer, (short) 3, (short) 12)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 16--------------
        //- initialize compare Buffer
        try{
            bRes=false;
            testCaseNb=(byte)0x10;
            compareBuffer= new byte[]{	(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x02,(byte)0x01,(byte)0x03,(byte)0x04,(byte)0x05,
                                        (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x55,
                                        (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55};
            //compare the buffer
            if ((short)bte_handler.compareValue((short) 2, compareBuffer, (short) 3, (short) 12)==(short)-1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 17--------------
        //- initialize compare Buffer
        try{
            bRes=false;
            testCaseNb=(byte)0x11;
            compareBuffer= new byte[]{	(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,
                                        (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0A,(byte)0x0D,(byte)0x55,
                                        (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55};
            //compare the buffer
            if ((short)bte_handler.compareValue((short) 2, compareBuffer, (short) 3, (short) 12)==(short)1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
        //-------------- TESTCASE 18--------------
        //- initialize compare Buffer
        try{
            bRes=false;
            testCaseNb=(byte)0x12;
            compareBuffer= new byte[]{	(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x99,(byte)0x03,(byte)0x03,(byte)0x04,(byte)0x05,
                                        (byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x55,
                                        (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55};
            //compare the buffer
            if ((short)bte_handler.compareValue((short) 2, compareBuffer, (short) 3, (short) 12)==(short)1){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb, bRes);
    }
}//end of class