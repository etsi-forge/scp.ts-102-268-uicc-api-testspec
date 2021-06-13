//-----------------------------------------------------------------------------
//Api_2_Bte_Gtsz_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_gtsz;

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
public class Api_2_Bte_Gtsz_1 extends TestToolkitApplet {

    private byte ba[] = new byte[(short)272];



    /**
     */
    private Api_2_Bte_Gtsz_1 () {
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
        Api_2_Bte_Gtsz_1 applet = new Api_2_Bte_Gtsz_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialize the test applet values
        applet.init();
        //initialze byte array with fill value
        Util.arrayFillNonAtomic(applet.ba,(short)0,(short)272,(byte)0xFF);
        //register applet to event
        applet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }

    /**
     *
     * @param event - the type of event to be processed.
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        //allocate a BERTLVEditHandler
        BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x114);
        //set BERTag of handler to 0x01
        bte_handler.setTag((byte)0x01);
        //-------------- TESTCASE 1--------------
        //- tag 0x01, data length 0x22
        bte_handler.appendArray(ba, (short)0, (short)0x22);
        if(bte_handler.getSize()==(short)0x24){
            this.reportTestOutcome((byte)0x01,true);
        }
        else{
            this.reportTestOutcome((byte)0x01,false);
        }
        bte_handler.clear();
        //-------------- TESTCASE 2 --------------
        //- tag 0x01, data length 0x7F
        bte_handler.appendArray(ba, (short)0, (short)0x7F);
        if(bte_handler.getSize()==(short)0x81){
            this.reportTestOutcome((byte)0x02,true);
        }
        else{
            this.reportTestOutcome((byte)0x02,false);
        }
        bte_handler.clear();
        //-------------- TESTCASE 3 --------------
        //- tag 0x01, data length 0x80       
        bte_handler.appendArray(ba, (short)0, (short)0x80);        
        if(bte_handler.getSize()==(short)0x83){
            this.reportTestOutcome((byte)0x03,true);
        }
        else{
            this.reportTestOutcome((byte)0x03,false);
        }
        bte_handler.clear();
        //-------------- TESTCASE 4 --------------
        //- tag 0x01, data length 0xFF
        bte_handler.appendArray(ba, (short)0, (short)0xFF);
        if(bte_handler.getSize()==(short)0x102){
            this.reportTestOutcome((byte)0x04,true);
        }
        else{
            this.reportTestOutcome((byte)0x04,false);
        }
        bte_handler.clear();
        //-------------- TESTCASE 5 --------------
        //- tag 0x01, data length 0x100
         bte_handler.appendArray(ba,(short)0,(short)0x100);
        if(bte_handler.getSize()==(short)0x104){
            this.reportTestOutcome((byte)0x05,true);
        }
        else{
            this.reportTestOutcome((byte)0x05,false);
        }
        bte_handler.clear();
        //-------------- TESTCASE 6 --------------
        //- tag 0x01, data length 0x110
        bte_handler.appendArray(ba,(short)0,(short)0x110);
        if(bte_handler.getSize()==(short)0x114){
            this.reportTestOutcome((byte)0x06,true);
        }
        else{
            this.reportTestOutcome((byte)0x06,false);
        }
        bte_handler.clear();
    }

}
