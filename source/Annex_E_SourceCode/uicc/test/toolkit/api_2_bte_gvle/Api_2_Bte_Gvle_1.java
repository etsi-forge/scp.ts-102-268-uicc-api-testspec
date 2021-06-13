//-----------------------------------------------------------------------------
//Api_2_Bte_Gvle_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_gvle;

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
* @version 1.0.0 - XX/07/04
*/

public class Api_2_Bte_Gvle_1 extends TestToolkitApplet {


    /**
     */
    private Api_2_Bte_Gvle_1 () {}

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
        Api_2_Bte_Gvle_1 applet = new Api_2_Bte_Gvle_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        applet.init();
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }




    /* (non-Javadoc)
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        // TODO Auto-generated method stub
        byte bArray[]=new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x82};
        // Result of test
        boolean bRes = false;
        byte testCaseNb= (byte)0x01;

        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
        bte_handler.setTag((byte)0x01);
        //-------------- TESTCASE 1 --------------
        //- initialize the handler & getValueLength
        try{
            bte_handler.appendArray(bArray,(short)0,(short)9);
            bte_handler.getValueLength();
        }
        catch(ToolkitException exp){
            if(exp.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2 --------------
        //- Call the appendTLV() method & search 0x0D tag
        testCaseNb=(byte)0x02;
        bRes=false;
        bte_handler.appendTLV((byte)0x0D,bArray,(short)0,(short)0);
        try {
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            if(bte_handler.getValueLength()==(short)0){
                bRes=true;
            }
        }
        catch (Exception ex) {
           bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 3 --------------
        //- Call the appendTLV() method & search 0x0D tag
        testCaseNb=(byte)0x03;
        bRes=false;
        bArray=new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x82,
                          (byte)0x0D,(byte)0x02,(byte)0x04,(byte)0x00};
        bte_handler.clear();
        bte_handler.appendArray(bArray,(short)0,(short)13);

        try {
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            if(bte_handler.getValueLength()==(short)0x02){
                bRes=true;
            }
        }
        catch (Exception ex) {
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4 --------------
        //- Call the appendTLV() with tag 0x0D ,length 7F
        testCaseNb=(byte)0x04;
        bRes=false;
        bArray=new byte[]{
                (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                (byte)0x82,(byte)0x0D,(byte)0x7F,(byte)0x04};
        byte bFillArray[]=new byte[126];
        Util.arrayFillNonAtomic(bFillArray,(short)0,(short)126,(byte)0x00);
        bte_handler.clear();
        bte_handler.appendArray(bArray,(short)0,(short)12);
        bte_handler.appendArray(bFillArray,(short)0,(short)126);
        try{
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            if(bte_handler.getValueLength()==(short)127){
                bRes=true;
            }
        }
        catch (Exception ex){
            bRes=false;
        }

        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 5 --------------
        //- Call the appendTLV() with tag 0x0D ,length 0x80
        testCaseNb=(byte)0x05;
        bRes=false;
        bArray=new byte[]{
                (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,
                (byte)0x82,(byte)0x0D,(byte)0x81,(byte)0x80,(byte)0x04};
        bFillArray=new byte[127];
        Util.arrayFillNonAtomic(bFillArray,(short)0,(short)127,(byte)0x00);
        bte_handler.clear();
        bte_handler.appendArray(bArray,(short)0,(short)13);
        bte_handler.appendArray(bFillArray,(short)0,(short)127);
        try{
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            if(bte_handler.getValueLength()==(short)128){
                bRes=true;
            }
        }
        catch(Exception ex){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 6 --------------
        //- Call the appendTLV() with tag 0x0D ,length F1
        testCaseNb=(byte)0x06;
        bRes=false;
        bArray=new byte[]{
                (byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x82,
                (byte)0x0D,(byte)0x81,(byte)0xF1,(byte)0x04};
        bFillArray=new byte[240];
        Util.arrayFillNonAtomic(bFillArray,(short)0,(short)240,(byte)0x00);
        bte_handler.clear();
        bte_handler.appendArray(bArray,(short)0,(short)13);
        bte_handler.appendArray(bFillArray,(short)0,(short)240);
        try{
            bte_handler.findTLV((byte)0x0D,(byte)0x01);
            if(bte_handler.getValueLength()==(short)241){
                bRes=true;
            }
        }
        catch(Exception ex){
            bRes=false;
        }

        this.reportTestOutcome(testCaseNb,bRes);



    }

}
