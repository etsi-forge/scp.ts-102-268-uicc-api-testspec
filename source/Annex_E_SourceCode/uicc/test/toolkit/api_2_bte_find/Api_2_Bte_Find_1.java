//-----------------------------------------------------------------------------
//Api_2_Bte_Find_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_find;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
//

import javacard.framework.ISOException;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

/**
 * @author ghartbrod
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Api_2_Bte_Find_1 extends TestToolkitApplet {

    boolean             bRes        = false;
    byte                testCaseNb  = (byte) 0x00;
    /**
     */
    private Api_2_Bte_Find_1 () {
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
        Api_2_Bte_Find_1 applet = new Api_2_Bte_Find_1();
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
    public void processToolkit(short arg0) throws ToolkitException {
        byte bArray[]= {(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x21,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x83};
        // Result of test


        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
        bte_handler.setTag((byte)0x01);
        //-------------- TESTCASE 1--------------
        //- initialize the handler & bad invalid input parameter
        testCaseNb= (byte)0x01;

        try{
            bte_handler.appendArray(bArray,(short)0,(short)9);
            bte_handler.findTLV((byte)0x81,(byte)0x00);
        }
        catch(ToolkitException exp){
            if(exp.getReason()==ToolkitException.BAD_INPUT_PARAMETER){
                bRes=true;
            }
        }

        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2--------------
        //- Search 1st TLV
        bRes=false;
        testCaseNb=(byte)0x02;

        if(bte_handler.findTLV((byte)0x01,(byte)0x01)==TLV_FOUND_CR_SET){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 3--------------
        //- call the getValueLength()method
        bRes=false;
        testCaseNb=(byte)0x03;
        if(bte_handler.getValueLength()==(short)3){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4--------------
        //- Search 2st TLV
        bRes=false;
        testCaseNb=(byte)0x04;
        if(bte_handler.findTLV((byte)0x02,(byte)0x01)==TLV_FOUND_CR_SET){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 5--------------
        //- call the getValueLength()method
        bRes=false;
        testCaseNb=(byte)0x05;
        if(bte_handler.getValueLength()==(short)2){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 6--------------
        //- Search a wrong tag
        bRes=false;
        testCaseNb=(byte)0x06;
        //select a TLV (tag 02h)
        if (bte_handler.findTLV((byte) 0x02, (byte) 0x01) == TLV_FOUND_CR_SET) {
            if (bte_handler.findTLV((byte) 0x03, (byte) 0x01) == TLV_NOT_FOUND) {
                bRes = true;
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 7--------------
        //- call the getValueLength() method
        bRes=false;
        testCaseNb=(byte)0x07;
        try{
            bte_handler.getValueLength();
        }
        catch(ToolkitException exp){
            if (exp.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 8--------------
        //- Search a tag with wrong occurrence
        bRes=false;
        testCaseNb=(byte)0x08;
        if(bte_handler.findTLV((byte)0x01,(byte)0x02)==TLV_NOT_FOUND){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 9--------------
        //- call the getValueLength() method
        bRes=false;
        testCaseNb=(byte)0x09;
        try{
            bte_handler.getValueLength();
        }
        catch(ToolkitException exp){
            if (exp.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                bRes=true;
            }
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 10--------------
        //- append a TLV with tag 02h & search it
        bRes=false;
        testCaseNb=(byte)0x0A;
        bte_handler.appendTLV((byte)0x02,(byte)0x00);
        if(bte_handler.findTLV((byte)0x02,(byte)0x02)==TLV_FOUND_CR_NOT_SET){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 11--------------
        //- append a TLV with tag 04h & search it
        bRes=false;
        testCaseNb=(byte)0x0B;
        bte_handler.appendTLV((byte)0x04,(byte)0x00);
        if(bte_handler.findTLV((byte)0x04,(byte)0x01)==TLV_FOUND_CR_NOT_SET){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 12--------------
        //- Search tag 0x81h
        bRes=false;
        testCaseNb=(byte)0x0C;
        if(bte_handler.findTLV((byte)0x81,(byte)0x01)==TLV_FOUND_CR_SET){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 13--------------
        //- Search tag 0x84h
        bRes=false;
        testCaseNb=(byte)0x0D;
        if(bte_handler.findTLV((byte)0x84,(byte)0x01)==TLV_FOUND_CR_NOT_SET){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);



    }



}
