//-----------------------------------------------------------------------------
//Api_2_Bte_Glen_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_glen;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
//

import javacard.framework.ISOException;
import javacard.framework.Util;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

/**
 * @author GHartbrod TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class Api_2_Bte_Glen_1 extends TestToolkitApplet {
    private byte bArray[] = new byte[(short)256];

    /**
     */
    private Api_2_Bte_Glen_1 () {
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
    public static void install(byte[] bParam, short bOffset, byte bLength) throws ISOException {
        Api_2_Bte_Glen_1 applet = new Api_2_Bte_Glen_1();
        byte aidLen = bParam[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bParam, (short) (bOffset + 1), aidLen);
        }
        //initialize the test applet values
        applet.init();
        //initialze byte array with fill value
        Util.arrayFillNonAtomic(applet.bArray,(short)0,(short)256,(byte)0xFF);
        //register applet to event
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }

    /*
     * (non-Javadoc)
     *
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        boolean bRes=false;
        byte testCaseNb=(byte)0x01;
        /// allocate a BERTLVEditHandler
        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);

        bte_handler.setTag((byte)0x01);
        //-------------- TESTCASE 1--------------
        //- Clear the handler
        bte_handler.clear();
        if (bte_handler.getLength()==(short)0){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2--------------
        //- call the appendArry() method with buffer length 9
        bRes=false;
        testCaseNb=(byte)0x02;
        bte_handler.appendArray(bArray,(short)0,(short)9);
        if (bte_handler.getLength()==(short)9){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 3--------------
        //- call the appendArry() method with buffer length 253
        bRes=false;
        bte_handler.clear();
        testCaseNb=(byte)0x03;
        bte_handler.appendArray(bArray,(short)0,(short)253);
        if (bte_handler.getLength()==(short)253){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 4--------------
        //- build a 0x7F handler
        bRes=false;
        testCaseNb=(byte)0x04;
        bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x7F);
        bte_handler.appendArray(bArray,(short)0,(short)0x7F);
        if (bte_handler.getLength()==(short)0x7F){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 5--------------
        //- build a 0x80h handler
        bRes=false;
        testCaseNb=(byte)0x05;
        bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x80);
        bte_handler.appendArray(bArray,(short)0,(short)0x80);
        if (bte_handler.getLength()==(short)0x80){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 6--------------
        //- build a 0x100h handler
        bRes=false;
        testCaseNb=(byte)0x06;
        bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x100);
        bte_handler.appendArray(bArray,(short)0,(short)0x100);
        if (bte_handler.getLength()==(short)0x100){
            bRes=true;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }

}
