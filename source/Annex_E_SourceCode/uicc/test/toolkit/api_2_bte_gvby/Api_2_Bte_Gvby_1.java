//-----------------------------------------------------------------------------
//Api_2_Bte_Gvby_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_gvby;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
//
import javacard.framework.*;
import uicc.toolkit.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.ToolkitException;



/**
* Test Area : uicc.toolkit.BERTLVViewHandler.getSize() method
*
* @version 1.0.0 - XX/09/04
*/
public class Api_2_Bte_Gvby_1 extends TestToolkitApplet {

    byte bArray[]= {(byte)0x81,(byte)0x03,(byte)0x01,(byte)0xFF,(byte)0xFE,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0xFD};

    byte baFillValue[]= new byte[240];

    /**
     */
    private Api_2_Bte_Gvby_1 () {
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
        Api_2_Bte_Gvby_1 applet = new Api_2_Bte_Gvby_1();
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


    /**
     *
     * @param event - the type of event to be processed.
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        //Result of test
        boolean bRes = false;
        //test case number
        byte testCaseNb= (byte)0x01;

        for (short i=0; i<(short)240; i++ ){
            baFillValue[i]=(byte)i;
        }

        //allocate a BERTLVEditHandler
        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
        //set BER Tag 0x01
        bte_handler.setTag((byte)0x01);

        //-------------- TESTCASE 1--------------
        //- initialize the handler
        //getValueByte(0)
        try{
            bte_handler.appendArray(bArray,(short)0,(short)9);
            bte_handler.getValueByte((short)0);
        }
        catch(ToolkitException exp){
            if(exp.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2--------------
        //- TLV 1, getValuebyte(3)
        bRes=false;
        testCaseNb=(byte)0x02;
        bte_handler.findTLV((byte)0x01,(byte)0x01);
        try{
            bte_handler.getValueByte((short)3);
        }
        catch (ToolkitException exp){
            if (exp.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 3--------------
        //- TLV 1, getValueByte(2)
        bRes=false;
        testCaseNb=(byte)0x03;
        bte_handler.findTLV((byte)0x01,(byte)0x01);
        if (bte_handler.getValueByte((short)2)==(byte)0xFE){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4--------------
        //- TLV 2, getValueByte(0)
        bRes=false;
        testCaseNb=(byte)0x04;
        bte_handler.findTLV((byte)0x02,(byte)0x01);
        if (bte_handler.getValueByte((short)0)==(byte)0x81){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //---------------------------------------
        //prepare the byte array for test case 5
        bArray= new byte[138];
        Util.arrayCopy(new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,
                    (byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x7F,(byte)0x04},(short)0,bArray,(short)0,(short)12);
        Util.arrayCopy(baFillValue,(short)0,bArray,(short)12,(short)126);
        //-------------- TESTCASE 5--------------
        //- initialize the handler
        bRes=false;
        testCaseNb=(byte)0x05;
        bte_handler.clear();
        bte_handler.setTag((byte)0x01);
        bte_handler.appendArray(bArray,(short)0,(short)138);
        //- TLV 0D, getValueByte(7E)
        bte_handler.findTLV((byte)0x0D,(byte)0x01);
        if(bte_handler.getValueByte((short)0x7E)==(byte)0x7D){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //---------------------------------------
        //prepare the byte array for test case 6
        bArray= new byte[140];
        Util.arrayCopy(new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,
                    (byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x81,(byte)0x80,(byte)0x04},(short)0,bArray,(short)0,(short)13);
        Util.arrayCopy(baFillValue,(short)0,bArray,(short)13,(short)127);
        //-------------- TESTCASE 6--------------
        bRes=false;
        testCaseNb=(byte)0x06;
        bte_handler.clear();
        bte_handler.setTag((byte)0x01);
        bte_handler.appendArray(bArray,(short)0,(short)140);
        //- TLV 0D, getValueByte(7E)
        bte_handler.findTLV((byte)0x0D,(byte)0x01);
        if (bte_handler.getValueByte((short)0x7E)==(byte)0x7D){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 7--------------
        //getValueByte(0x7F)
        bRes=false;
        testCaseNb=(byte)0x07;
        if (bte_handler.getValueByte((short)0x7F)==(byte)0x7E){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //---------------------------------------
        //prepare the byte array for test case 8
        bArray= new byte[253];
        Util.arrayCopy(new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,
                    (byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                    (byte)0x02,(byte)0x0D,(byte)0x81,(byte)0xF1,(byte)0x04},(short)0,bArray,(short)0,(short)13);
        Util.arrayCopy(baFillValue,(short)0,bArray,(short)13,(short)240);
        //-------------- TESTCASE 8--------------
        //- initialize the handler
        bRes=false;
        testCaseNb=(byte)0x08;
        bte_handler.clear();
        bte_handler.setTag((byte)0x01);
        bte_handler.appendArray(bArray,(short)0,(short)253);
//		- TLV 0D, getValueByte(7E)
        bte_handler.findTLV((byte)0x0D,(byte)0x01);
        if (bte_handler.getValueByte((short)0xF0)==(byte)0xEF){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }
}

