//-----------------------------------------------------------------------------
//Api_2_Bte_Gcap
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_gcap;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Gcap_1 extends TestToolkitApplet {

    boolean				bRes		= false;
    byte				testCaseNb	= (byte) 0x00;
    BERTLVEditHandler	bte_handler	= null;
    byte				buffer[]	= null;

    /**
     */
    private  Api_2_Bte_Gcap_1 () {
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
        Api_2_Bte_Gcap_1 applet = new  Api_2_Bte_Gcap_1();
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
            bte_handler = (BERTLVEditHandler) HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, (short) 0x10);
            bte_handler.setTag((byte)0x01);
            //- 1.call get Cappacity()
            if (bte_handler.getCapacity() == (short) 0x10) {
                //- 2.fill the handler
                buffer = new byte[0x0E];
                Util.arrayFillNonAtomic(buffer, (short) 0, (short) 0x0E, (byte) 0xFF);
                bte_handler.appendTLV((byte) 0x0D, buffer, (short) 0, (short) 0x0E);
                //- 3.clear()
                bte_handler.clear();
                //- 4. fill the handler with the maximim capacity plus one
                try {
                    buffer = new byte[0x0F];
                    Util.arrayFillNonAtomic(buffer, (short) 0, (short) 0x0F, (byte) 0xFF);
                    bte_handler.appendTLV((byte) 0x0D, buffer, (short) 0, (short) 0x0F);
                }
                catch (ToolkitException ex) {
                    if (ex.getReason() == ToolkitException.HANDLER_OVERFLOW) {
                        bRes = true;
                    }
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
    }
}