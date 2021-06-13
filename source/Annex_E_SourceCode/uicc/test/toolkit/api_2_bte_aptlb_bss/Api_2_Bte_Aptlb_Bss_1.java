//-----------------------------------------------------------------------------
//api_2_bte_aptlb_bss_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_aptlb_bss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Aptlb_Bss_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer[]		= null;

    /**
     */
    private Api_2_Bte_Aptlb_Bss_1 () {}
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
        Api_2_Bte_Aptlb_Bss_1 applet = new Api_2_Bte_Aptlb_Bss_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {applet.register();}
        else {applet.register(bArray, (short) (bOffset + 1), aidLen);}
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
            //null value
            try{
                bte_handler.appendTLV((byte)0x0D,null,(short)0,(short)0);
            }
            catch(NullPointerException ex){
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
                //- valueOffset > value.length
                bte_handler.appendTLV((byte)0x0D,buffer,(short)6,(short)0);
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
                //- valueOffset < 0
                bte_handler.appendTLV((byte)0x0D,buffer,(short)-1,(short)1);
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
                //- valueOffset < 0
                bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)6);
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
                //- valueOffset + valueLength > value.length
                bte_handler.appendTLV((byte)0x0D,buffer,(short)3,(short)3 );
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
                //- valueLength < 0
                bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)-1 );
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            buffer = new byte[(short)(bte_handler.getCapacity()-1)];
            Util.arrayFillNonAtomic(buffer,(short)0,(short)(bte_handler.getCapacity()-1),(byte)0xFF);
            bte_handler.appendArray(buffer,(short)0,(short)(bte_handler.getCapacity()-1));
            buffer = new byte[254];
            try{
                //- Handler overflow exception
                bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)254);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.HANDLER_OVERFLOW){
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
            bte_handler.clear();

            buffer = new byte[256];
            bte_handler.appendArray(buffer,(short)0,(short)256);
            try{
                //- Bad parameter exception
                bte_handler.appendTLV((byte)0x0D,buffer,(short)0,(short)256);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.BAD_INPUT_PARAMETER){
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
            //- initialize the handler
            buffer=new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x00};
            bte_handler.clear();
            bte_handler.appendArray(buffer,(short)0,(short)9);
            //- select Command Details TLV
            bte_handler.findTLV((byte)0x81,(byte)0x01);
            //- successful call
            buffer= new byte[]{(byte)0xFF,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB,(byte)0xFA,(byte)0xF9,(byte)0xF8};
            bte_handler.appendTLV((byte)0x04,buffer,(short)0,(short)8);
            //- verify current TLV
            if(bte_handler.getValueLength()==(short)3){
                bRes=true;
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
            //- clear the handler
            bte_handler.clear();
            //- successful call
            buffer= new byte[]{(byte)0xFF,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB,(byte)0xFA,(byte)0xF9,(byte)0xF8};
            compareBuffer=new byte[24];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x08;
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)2,(short)8);
            bte_handler.appendTLV((byte)0x04,buffer,(short)0,(short)8);
            buffer=new byte[10];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)10);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)10)==(byte)0x00){
                bRes=true;
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
            //- successful call
            buffer= new byte[]{(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07};
            compareBuffer[10]=(byte)0x85;
            compareBuffer[11]=(byte)0x06;
            Util.arrayCopy(buffer,(short)2,compareBuffer,(short)12,(short)6);
            bte_handler.appendTLV((byte)0x85,buffer,(short)2,(short)6);
            buffer=new byte[18];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)18);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)18)==(byte)0x00){
                bRes=true;
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
            //- successful call
            buffer= new byte[]{(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55,(byte)0x66,(byte)0x77,(byte)0x88};
            compareBuffer[18]=(byte)0x01;
            compareBuffer[19]=(byte)0x04;
            Util.arrayCopy(buffer,(short)2,compareBuffer,(short)20,(short)4);
            bte_handler.appendTLV((byte)0x01,buffer,(short)2,(short)4);
            buffer=new byte[24];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)24);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)24)==(byte)0x00){
                bRes=true;
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
            //- clear the handler
            bte_handler.clear();
            //- successful call
            buffer= new byte[128];
            for (short i=0; i<(short)128;i++){
                buffer[i]=(byte)i;
            }
            compareBuffer=new byte[131];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x81;
            compareBuffer[2]=(byte)0x80;
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)3,(short)128);
            bte_handler.appendTLV((byte)0x04,buffer,(short)0,(short)128);
            buffer=new byte[131];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)131);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)131)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 14 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0E;
            //- clear the handler
            bte_handler.clear();
            //- successful call
            buffer= new byte[250];
            for (short i=0; i<(short)250;i++){
                buffer[i]=(byte)i;
            }
            compareBuffer=new byte[253];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x81;
            compareBuffer[2]=(byte)0xFA;
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)3,(short)250);
            bte_handler.appendTLV((byte)0x04,buffer,(short)0,(short)250);
            buffer=new byte[253];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)253);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)253)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);


    }
}