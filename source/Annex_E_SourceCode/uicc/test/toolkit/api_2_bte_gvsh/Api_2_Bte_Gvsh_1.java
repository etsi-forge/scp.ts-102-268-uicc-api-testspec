//-----------------------------------------------------------------------------
//Api_2_Bte_Gvsh_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_gvsh;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Gvsh_1 extends TestToolkitApplet {

    boolean				bRes		= false;
    byte				testCaseNb	= (byte) 0x00;
    BERTLVEditHandler	bte_handler	= null;
    byte				bArray[]	= null;

    /**
     */
    private  Api_2_Bte_Gvsh_1 () {
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
        Api_2_Bte_Gvsh_1 applet = new Api_2_Bte_Gvsh_1();
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
            bArray=new byte[]{	(byte)0x81,(byte)0x03,(byte)0x01,(byte)0xFF,(byte)0xFE,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0xFD};
            //-initialize the handler.
            bte_handler.appendArray(bArray,(short)0,(short)9);
            try{
                bte_handler.getValueShort((short)0);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2 --------------
        //- search TLV 0x01 (command details TLV)
        try{
            bRes = false;
            testCaseNb = (byte) 0x02;
            bte_handler.findTLV((byte)0x01,(byte)0x1);
            try{
                bte_handler.getValueShort((short)3);
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
        //-------------- TESTCASE 3 --------------
        //- search TLV 0x01 (command details TLV)
        try{
            bRes = false;
            testCaseNb = (byte) 0x03;
            bte_handler.findTLV((byte)0x01,(byte)0x1);
            if(bte_handler.getValueShort((short)1)==(short)0xFFFE){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4 --------------
        //- search TLV 0x02 (Device Identities TLV)
        try{
            bRes = false;
            testCaseNb = (byte) 0x04;
            bte_handler.findTLV((byte)0x02,(byte)0x1);
            if(bte_handler.getValueShort((short)0)==(short)0x81FD){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 5 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x05;
            bArray =new byte[138];
            byte temp[]=new byte[]{
                (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                (byte)0x02,(byte)0x0D,(byte)0x7F,(byte)0x04};
            for (short i=0; i<12;i++){
                bArray[i]=temp[i];
            }
            for (short i=0; i<126;i++){
                bArray[(short)(i+12)]=(byte)i;
            }
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bte_handler.appendArray(bArray,(short)0,(short)138);
            //-search TLV 0x0D (Text String TLV)
            bte_handler.findTLV((byte)0x0D,(byte)0x1);
            if(bte_handler.getValueShort((short)0x7D)==(short)0x7C7D){
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
            bArray =new byte[140];
            byte temp[]=new byte[]{
                (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                (byte)0x02,(byte)0x0D,(byte)0x81,(byte)0x80,(byte)0x04};
            for (short i=0; i<13;i++){
                bArray[i]=temp[i];
            }
            for (short i=0; i<127;i++){
                bArray[(short)(i+13)]=(byte)i;
            }
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bte_handler.appendArray(bArray,(short)0,(short)140);
            //-search TLV 0x0D (Text String TLV)
            bte_handler.findTLV((byte)0x0D,(byte)0x1);
            if(bte_handler.getValueShort((short)0x7D)==(short)0x7C7D){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 7 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x07;
            if(bte_handler.getValueShort((short)0x7E)==(short)0x7D7E){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x08;
            bArray =new byte[253];
            byte temp[]=new byte[]{
                (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                (byte)0x02,(byte)0x0D,(byte)0x81,(byte)0xF1,(byte)0x04};
            for (short i=0; i<13;i++){
                bArray[i]=temp[i];
            }
            for (short i=0; i<240;i++){
                bArray[(short)(i+13)]=(byte)i;
            }
            //-initialize the handler
            bte_handler.clear();
            bte_handler.setTag((byte)0x01);
            bte_handler.appendArray(bArray,(short)0,(short)253);
            //-search TLV 0x0D (Text String TLV)
            bte_handler.findTLV((byte)0x0D,(byte)0x1);
            if(bte_handler.getValueShort((short)0xEF)==(short)0xEEEF){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);


    }

}
