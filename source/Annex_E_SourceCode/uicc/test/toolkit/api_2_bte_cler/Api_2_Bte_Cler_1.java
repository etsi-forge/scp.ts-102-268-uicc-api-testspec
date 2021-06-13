//-----------------------------------------------------------------------------
//Api_2_Bte_Cler_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_cler;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Cler_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    BERTLVEditHandler	bte_handler		= null;
    byte				buffer[]		= null;

    /**
     * If AID length is not zero register applet with specified AID
     * @param bArray the array constaing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    private Api_2_Bte_Cler_1 () {
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
        Api_2_Bte_Cler_1 applet = new Api_2_Bte_Cler_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialise the test applet values
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
            buffer=new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x00};
            bte_handler.appendArray(buffer,(short)0,(short)9);
            //-select Command Details TLV
            bte_handler.findTLV((byte)0x81,(byte)0x01);
            //-getLength()
            if(bte_handler.getLength()>(short)0){
                bte_handler.clear();
                if(bte_handler.getLength()==(short)0){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
        //-------------- TESTCASE 2 --------------
        //- initialize the handler
        try {
            bRes = false;
            testCaseNb = (byte) 0x02;
            try{
                bte_handler.getValueLength();
            }
            catch(ToolkitException ex){
                if (ex.getReason()==ToolkitException.UNAVAILABLE_ELEMENT){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);


    }
}