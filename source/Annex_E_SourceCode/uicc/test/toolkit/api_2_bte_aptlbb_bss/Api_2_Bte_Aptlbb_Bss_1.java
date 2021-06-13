//-----------------------------------------------------------------------------
//api_2_bte_aptlbb_bss_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_aptlbb_bss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Aptlbb_Bss_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer[]		= null;

    /**
     * If AID length is not zero register applet with specified AID
     * @param bArray the array constaing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    private Api_2_Bte_Aptlbb_Bss_1 () {

    }
    /**
     * Create an instance of the BaService, the Java Card runtime environment will call this static method first.
     *
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray The maximum value of bLength is 127.
     * @throws ISOException if the install method failed
     * @see javacard.framework.Applet
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException {
        Api_2_Bte_Aptlbb_Bss_1 applet = new Api_2_Bte_Aptlbb_Bss_1();
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

        ;
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
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,null,(short)0,(short)1);
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
                //- value2Offset > value2.length
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)6,(short)0);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                    bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 3 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x03;
            try{
                //- value2Offset < 0
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)-1,(short)1);
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
                //- value2Offset < 0
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)0,(short)6);
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
                //- value2Offset + value2Length > value2.length
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)3,(short)3 );
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
                //- value2Length < 0
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)0,(short)-1 );
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
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)0,(short)254);
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
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,buffer,(short)0,(short)256);
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
//		-------------- TESTCASE 9 --------------
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
            bte_handler.appendTLV((byte)0x04,(byte)0x05,buffer,(short)0,(short)8);
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
            compareBuffer=new byte[27];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x09;
            compareBuffer[2]=(byte)0x05;
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)3,(short)8);
            bte_handler.appendTLV((byte)0x04,(byte)0x05,buffer,(short)0,(short)8);
            buffer=new byte[11];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)11);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)11)==(byte)0x00){
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
            compareBuffer[11]=(byte)0x85;
            compareBuffer[12]=(byte)0x07;
            compareBuffer[13]=(byte)0x55;
            Util.arrayCopy(buffer,(short)2,compareBuffer,(short)14,(short)6);
            bte_handler.appendTLV((byte)0x85,(byte)0x55,buffer,(short)2,(short)6);
            buffer=new byte[20];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)20);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)20)==(byte)0x00){
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
            compareBuffer[20]=(byte)0x01;
            compareBuffer[21]=(byte)0x05;
            compareBuffer[22]=(byte)0x44;
            Util.arrayCopy(buffer,(short)2,compareBuffer,(short)23,(short)4);
            bte_handler.appendTLV((byte)0x01,(byte)0x44,buffer,(short)2,(short)4);
            buffer=new byte[27];
            //- call copy() method
            bte_handler.copy(buffer,(short)0,(short)27);
            if (Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)27)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 13 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0D;
            //- clear the handler
            bte_handler.clear();
            //- successful call
            buffer= new byte[127];
            for (short i=1; i<(short)128;i++){
                buffer[(short)(i-1)]=(byte)i;
            }
            compareBuffer=new byte[131];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x81;
            compareBuffer[2]=(byte)0x80;
            compareBuffer[3]=(byte)0x00;
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)4,(short)127);
            bte_handler.appendTLV((byte)0x04,(byte)0x00,buffer,(short)0,(short)127);
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
        //-------------- TESTCASE 14 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0E;
            //- clear the handler
            bte_handler.clear();
            //- successful call
            buffer= new byte[249];
            for (short i=1; i<(short)250;i++){
                buffer[(short)(i-1)]=(byte)i;
            }
            compareBuffer=new byte[253];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x81;
            compareBuffer[2]=(byte)0xFA;
            compareBuffer[3]=(byte)0x00;
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)4,(short)249);
            bte_handler.appendTLV((byte)0x04,(byte)0x00,buffer,(short)0,(short)249);
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