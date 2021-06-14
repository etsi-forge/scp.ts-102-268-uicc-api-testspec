//-----------------------------------------------------------------------------
//api_2_bte_aptlb_bss_bss_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_aptlb_bss_bss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Aptlb_Bss_Bss_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer1[]		= null;
    byte				buffer2[]		= null;

    /**
     */
    private Api_2_Bte_Aptlb_Bss_Bss_1 () {}
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
        Api_2_Bte_Aptlb_Bss_Bss_1 applet = new Api_2_Bte_Aptlb_Bss_Bss_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {applet.register();}
        else {applet.register(bArray, (short) (bOffset + 1), aidLen);}
         //initialise the inherited test applet values
        applet.init();
        //register applet to event
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);


    }


    /* (non-Javadoc)
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        //-------------- TESTCASE 1 --------------
        bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
        try {
            bRes = false;
            testCaseNb = (byte) 0x01;
            bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
            bte_handler.setTag((byte)0x01);
            buffer1 = new byte[5];
            buffer2 = new byte[5];
            try{
                //- null value1
                bte_handler.appendTLV((byte)0x0D,null,(short)0,(short)5,buffer2,(short)0,(short)5);
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
            try{
                //- null value2
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)5,null,(short)0,(short)5);
            }
            catch(NullPointerException ex){
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
                //-value1Offset >= value1.length
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)5,(short)1,buffer2,(short)0,(short)1);
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
                //-value1Offset < 0
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)-1,(short)1,buffer2,(short)0,(short)1);
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
                //-value1Length > value1.length
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)6,buffer2,(short)0,(short)1);
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
                //-value1Offset + value1Length > value1.length
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)3,(short)3,buffer2,(short)0,(short)1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //		-------------- TESTCASE 7 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x07;
            try{
                //-value1Length < 0
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)-1,buffer2,(short)0,(short)1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //		-------------- TESTCASE 8 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x08;
            try{
                //-value2Offset >= value2.length
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)1,buffer2,(short)5,(short)1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                bRes=true;
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
            try{
                //-value2Ofsset < 0
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)1,buffer2,(short)5,(short)-1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //-value2Length > value2.length
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)1,buffer2,(short)0,(short)6);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //-value2Offset + value2Length > value2.length
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)1,buffer2,(short)3,(short)3);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            try{
                //-value2Length <0
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)1,buffer2,(short)0,(short)-1);
            }
            catch(ArrayIndexOutOfBoundsException ex){
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
            buffer1=new byte[(short)(bte_handler.getCapacity()-1)];
            bte_handler.appendArray(buffer1,(short)0,(short)(bte_handler.getCapacity()-1));
            buffer1= new byte[256];
            buffer2= new byte[256];

            try{
                //-Handler overflow exeption
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)253,buffer2,(short)0,(short)1);
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
        //-------------- TESTCASE 14 --------------
        try {
            bRes = false;
            testCaseNb = (byte) 0x0E;
            bte_handler.clear();
            buffer1= new byte[256];
            buffer2= new byte[256];
            try{
                //-Bad parameter exception
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)256,buffer2,(short)0,(short)1);
            }
            catch(ToolkitException ex){
                if (ex.getReason()==ToolkitException.BAD_INPUT_PARAMETER){
                    bRes=true;
                }
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
            bte_handler.clear();
            buffer1= new byte[256];
            buffer2= new byte[256];
            try{
                //-Bad parameter exception
                bte_handler.appendTLV((byte)0x0D,buffer1,(short)0,(short)1,buffer2,(short)0,(short)256);
            }
            catch(ToolkitException ex){
                if (ex.getReason()==ToolkitException.BAD_INPUT_PARAMETER){
                    bRes=true;
                }
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
            bte_handler.clear();
            bte_handler.appendTLV((byte)0x81, new byte[]{(byte)0x11,(byte)0x22,(byte)0x33}, (byte)0, (byte)3);
            bte_handler.appendTLV((byte)0x82, new byte[]{(byte)0x99,(byte)0x77}, (byte)0, (byte)2);
            //-select Command details TLV
            bte_handler.findTLV((byte)0x81, (byte)0x01);
			bte_handler.findTLV((byte)0x82, (byte)0x01);
			bRes = true;
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
		
		//-----------------TESTCASE 17----------------- 
        try {
            bRes = false;
            testCaseNb = (byte) 0x11; 
            bte_handler.clear();
            //-successful call
            buffer1 = new byte[]{(byte)0xFF,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB,(byte)0xFA,(byte)0xF9,(byte)0xF8};
            buffer2 = new byte[]{(byte)0xF7,(byte)0xF6,(byte)0xF5,(byte)0xF4,(byte)0xF3,(byte)0xF2,(byte)0xF1,(byte)0xF0};
            bte_handler.appendTLV((byte)0x04, buffer1, (short)0, (short)8, buffer2, (short)0, (short)8);
			bte_handler.findTLV((byte)0x04, (byte)0x01);
            if(bte_handler.getValueLength() == 16) {
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
            bte_handler.clear();
            //-successful call
            buffer1= new byte[]{(byte)0xFF,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB,(byte)0xFA,(byte)0xF9,(byte)0xF8};
            buffer2= new byte[]{(byte)0xF7,(byte)0xF6,(byte)0xF5,(byte)0xF4,(byte)0xF3,(byte)0xF2,(byte)0xF1,(byte)0xF0};
            compareBuffer = new byte[42];
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x10;
            Util.arrayCopy(buffer1,(short)0,compareBuffer,(short)2,(short)8);
            Util.arrayCopy(buffer2,(short)0,compareBuffer,(short)10,(short)8);
            bte_handler.appendTLV((byte)0x04,buffer1,(short)0,(short)8,buffer2,(short)0,(short)8);
            buffer1= new byte[18];
            bte_handler.copy(buffer1,(short)0,(short)18);
            //-compare buffer
            if(Util.arrayCompare(buffer1,(short)0,compareBuffer,(short)0,(short)18)==(byte)0x00){
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

            //-successful call
            buffer1= new byte[]{(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07};
            buffer2= new byte[]{(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
            compareBuffer[18]=(byte)0x85;
            compareBuffer[19]=(byte)0x0C;
            Util.arrayCopy(buffer1,(short)2,compareBuffer,(short)20,(short)6);
            Util.arrayCopy(buffer2,(short)2,compareBuffer,(short)26,(short)6);
            bte_handler.appendTLV((byte)0x85,buffer1,(short)2,(short)6,buffer2,(short)2,(short)6);
            buffer1= new byte[32];
            bte_handler.copy(buffer1,(short)0,(short)32);
            //-compare buffer
            if(Util.arrayCompare(buffer1,(short)0,compareBuffer,(short)0,(short)32)==(byte)0x00){
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
            //-successful call
            buffer1= new byte[]{(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0x55,(byte)0x66,(byte)0x77,(byte)0x88};
            buffer2= new byte[]{(byte)0x99,(byte)0xAA,(byte)0xBB,(byte)0xCC,(byte)0xDD,(byte)0xEE,(byte)0xFF,(byte)0x00};
            compareBuffer[32]=(byte)0x01;
            compareBuffer[33]=(byte)0x08;
            Util.arrayCopy(buffer1,(short)2,compareBuffer,(short)34,(short)4);
            Util.arrayCopy(buffer2,(short)2,compareBuffer,(short)38,(short)4);
            bte_handler.appendTLV((byte)0x01,buffer1,(short)2,(short)4,buffer2,(short)2,(short)4);
            buffer1= new byte[42];
            bte_handler.copy(buffer1,(short)0,(short)42);
            //-compare buffer
            if(Util.arrayCompare(buffer1,(short)0,compareBuffer,(short)0,(short)42)==(byte)0x00){
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
            //-successful call
            bte_handler.clear();
            buffer1 = new byte[128];
            buffer2 = new byte[125];
            compareBuffer= new byte[256];
            for (short i=0;i<128;i++){
                buffer1[i]=(byte)i;
            }
            for (short i=128;i<253;i++){
                buffer2[(short)(i-128)]=(byte)i;
            }
            compareBuffer[0]=(byte)0x04;
            compareBuffer[1]=(byte)0x81;
            compareBuffer[2]=(byte)0xFD;
            Util.arrayCopy(buffer1,(short)0,compareBuffer,(short)3,(short)128);
            Util.arrayCopy(buffer2,(short)0,compareBuffer,(short)131,(short)125);
            //-successful call
            bte_handler.appendTLV((byte)0x04,buffer1,(short)0,(short)128,buffer2,(short)0,(short)125);
            buffer1= new byte[256];
            bte_handler.copy(buffer1,(short)0,(short)256);
            //-compare buffer
            if(Util.arrayCompare(buffer1,(short)0,compareBuffer,(short)0,(short)256)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }
}