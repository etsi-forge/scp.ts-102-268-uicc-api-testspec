//-----------------------------------------------------------------------------
//api_2_bte_apda_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_apda;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.*;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Apda_1 extends TestToolkitApplet {

    boolean           bRes            = false;
    byte              testCaseNb      = (byte) 0x00;
    byte              bArray[]        = null;
    BERTLVEditHandler bte_handler     = null;
    byte              buffer[]        = null;
    byte              compareBuffer[] = null;

    /**
     * If AID length is not zero register applet with specified AID
     * @param bArray the array constaing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    private  Api_2_Bte_Apda_1 () {
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
        Api_2_Bte_Apda_1 applet = new  Api_2_Bte_Apda_1();
        //register applet
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {applet.register();}
        else {applet.register(bArray, (short) (bOffset + 1), aidLen);}
        applet.init();
        //register event unrecognize envelope
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }


    /* (non-Javadoc)
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        //-------------- TESTCASE 1 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x01;
            bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
            bte_handler.setTag((byte)0x01);
            //- NULL buffer
            try{
                bte_handler.appendArray(null,(short)0,(short)0);
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
        try {
            bRes = false;
            testCaseNb = (byte) 0x02;
            buffer = new byte[5];
            Util.arrayFillNonAtomic(buffer,(short)0,(short)5,(byte)0xFF);

            try{
                //-offset > buffer.length
                bte_handler.appendArray(buffer,(short)6,(short)0);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //-offset < 0
                bte_handler.appendArray(buffer,(short)-1,(short)1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //-length > buffer.length
                bte_handler.appendArray(buffer,(short)0,(short)6);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //-offset + length > buffer.length
                bte_handler.appendArray(buffer,(short)3,(short)3);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //- length < 0
                bte_handler.appendArray(buffer,(short)0,(short)-1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 7--------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x07;
            buffer = new byte[(short)(bte_handler.getCapacity()+1)];
            try{
                //- Handler overflow exception
                bte_handler.appendArray(buffer,(short)0,(short)(bte_handler.getCapacity()+1));
            }
            catch(ToolkitException ex){
                if (ex.getReason()==ToolkitException.HANDLER_OVERFLOW){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8--------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x08;
            bte_handler.clear();
            bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
            bte_handler.setTag((byte)0x01);
            bArray=new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x00};
            bte_handler.appendArray(bArray,(short)0,(short)9);
            //- select command details TLV (0x81)
            bte_handler.findTLV((byte)0x81,(byte)0x01);
            //- successfull call
            buffer = new byte[]{(byte)0xFF,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB,(byte)0xFA,(byte)0xF9,(byte)0xF8};
            bte_handler.appendArray(buffer,(short)0,(short)8);
            //-verify current TLV
            if(bte_handler.getValueLength()==(short)3){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9--------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x09;
            //-clear the handler
            bte_handler.clear();
            compareBuffer = new byte[18];
            buffer = new byte[]{(byte)0xFF,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB,(byte)0xFA,(byte)0xF9,(byte)0xF8};
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)0,(short)8);
            //-successful call
            bte_handler.appendArray(buffer,(short)0,(short)8);
            //-call copy() method
            bte_handler.copy(buffer,(short)0,(short)8);
            //-compare the array
            if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)8)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 10--------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0A;
            //-initialize compare buffer
            buffer = new byte[]{(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07};
            Util.arrayCopy(buffer,(short)2,compareBuffer,(short)8,(short)6);
            //-successful call
            bte_handler.appendArray(buffer,(short)2,(short)6);
            //-call copy() method
            buffer=new byte[14];
            bte_handler.copy(buffer,(short)0,(short)14);
            //-compare the array
            if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)14)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11--------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0B;
            //-initialize compare buffer
            buffer = new byte[]{(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55,(byte)0x66,(byte)0x77,(byte)0x88};
            Util.arrayCopy(buffer,(short)2,compareBuffer,(short)14,(short)4);
            //-successful call
            bte_handler.appendArray(buffer,(short)2,(short)4);
            //-call copy() method
            buffer=new byte[18];
            bte_handler.copy(buffer,(short)0,(short)18);
            //-compare the array
            if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)18)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12--------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0C;
            bte_handler.clear();
            bArray=new byte[(short)253];
            compareBuffer = new byte[(short)253];
            buffer=new byte[253];
            for(short i=0;i<(short)253;i++){
                bArray[i]=(byte)i;
            }
            Util.arrayCopy(bArray,(short)0,compareBuffer,(short)0,(short)253);
            bte_handler.appendArray(bArray,(short)0,(short)253);
            if((short)bte_handler.getLength()==(short)253){
                bte_handler.copy(buffer,(short)0,(short)253);
                if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)253)==(byte)0x00){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);

    }
}