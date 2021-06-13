//-----------------------------------------------------------------------------
//Api_2_Bte_Sttg_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_sttg;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
//
import javacard.framework.*;
import uicc.toolkit.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;

/**
 * Test Area : uicc.toolkit.BERTLVEditHandler.setTag(byte bBERTag) method
 *
 * @version 1.0.0 - XX/07/04
 */
public class Api_2_Bte_Sttg_1 extends TestToolkitApplet {

    /**
     */
    private Api_2_Bte_Sttg_1 () {

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
        Api_2_Bte_Sttg_1 applet = new Api_2_Bte_Sttg_1();
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

    /*
     * // Get the system instance of the EnvelopeHandler class
     */
    public void processToolkit(short event) throws ToolkitException {

        try {
            //allocate a BERTLVEditHandler
            BERTLVEditHandler bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x10);
            //Test Case 1
            bte_handler.setTag((byte) 0x01);
            if (bte_handler.getTag() == (byte) 0x01) {
                this.reportTestOutcome((byte) 0x01, true);
            }
            else {
                this.reportTestOutcome((byte) 0x01, false);//test failed
            }
        }
        catch (SystemException sysexp) {
            this.reportTestOutcome((byte) 0x01, false);//test failed
        }
    }
}