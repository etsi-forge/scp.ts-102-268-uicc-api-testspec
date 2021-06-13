//-----------------------------------------------------------------------------
//Api_2_Bte_Aptlbbs_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_aptlbbs;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Aptlbbs_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer[]		= null;

    /**
     */
    private Api_2_Bte_Aptlbbs_1 () {
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
        Api_2_Bte_Aptlbbs_1 applet = new Api_2_Bte_Aptlbbs_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //initialize the test applet values
        applet.init();
        //register applet
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
            buffer = new byte[(short)(bte_handler.getCapacity()-1)];
            //cause Handler Overflow
            bte_handler.appendArray(buffer,(short)0,(short)(bte_handler.getCapacity()-1));
            try{
                bte_handler.appendTLV((byte)0x0D,(byte)0x00,(short)1);
            }
            catch(ToolkitException ex){
                if(ex.getReason()==ToolkitException.HANDLER_OVERFLOW){
                    bRes=true;
                }
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
                //-------------- TESTCASE 2 --------------
                try{
                    bRes = false;
                    testCaseNb = (byte) 0x02;
                    buffer=new byte[]{(byte)0x81,(byte)0x03,(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x82,(byte)0x02,(byte)0x81,(byte)0x00};
                    bte_handler.clear();
                    //initialize the handler
                    bte_handler.appendArray(buffer,(short)0,(short)9);
                    //-select Command Details TLV
                    bte_handler.findTLV((byte)0x81,(byte)0x01);
                    //-call appendTLV()
                    bte_handler.appendTLV((byte)0x0E,(byte)0x00,(short)1);
                    //-verify current TLV
                    if(bte_handler.getValueLength()==(short)3){
                        bRes=true;
                    }
                }
                catch(Exception exp){
                    bRes=false;
                }
                this.reportTestOutcome(testCaseNb,bRes);
                //-------------- TESTCASE 3 --------------
                try{
                    bRes = false;
                    testCaseNb = (byte) 0x03;
                    bte_handler.clear();
                    bte_handler.appendTLV((byte)0x84,(byte)0x00,(short)Util.makeShort((byte)0x01,(byte)0x02));
                    buffer= new byte[]{(byte)0x84,(byte)0x03,(byte)0x00,(byte)0x01,(byte)0x02};
                    compareBuffer=new byte[10];
                    Util.arrayCopy(buffer,(short)0,compareBuffer,(short)0,(short)5);
                    bte_handler.copy(buffer,(short)0,(short)5);
                    //compare the arrays
                    if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)5)==(byte)0x00){
                        bRes=true;
                    }
                }
                catch(Exception exp){
                    bRes=false;
                }
                this.reportTestOutcome(testCaseNb,bRes);
                //-------------- TESTCASE 4 --------------
                try{
                    bRes = false;
                    testCaseNb = (byte) 0x04;
                    bte_handler.appendTLV((byte)0x01,(byte)0xFE,(short)Util.makeShort((byte)0xFD,(byte)0xFC));
                    Util.arrayCopy( new byte[]{(byte)0x01,(byte)0x03,(byte)0xFE,(byte)0xFD,(byte)0xFC },(short)0,compareBuffer,(short)5,(short)5);
                    buffer=new byte[10];
                    bte_handler.copy(buffer,(short)0,(short)10);
                    //compare the arrays
                    if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)10)==(byte)0x00){
                        bRes=true;
                    }
                }
                catch(Exception exp){
                    bRes=false;
                }
                this.reportTestOutcome(testCaseNb,bRes);
//				-------------- TESTCASE 5 --------------
                try{
                    bRes = false;
                    testCaseNb = (byte) 0x05;
                    bte_handler.clear();
                    buffer=new byte[248];
                    buffer[0]=(byte)0x00;
                    buffer[1]=(byte)0x81;
                    buffer[2]=(byte)0xF5;
                    for (short i=3;i<248;i++){
                        buffer[i]=(byte)i;
                    }
                    bte_handler.appendArray(buffer,(short)0,(short)248);
                    compareBuffer=new byte[253];
                    Util.arrayCopy(buffer,(short)0,compareBuffer,(short)0,(short)248);
                    compareBuffer[248]=(byte)0x84;
                    compareBuffer[249]=(byte)0x03;
                    compareBuffer[250]=(byte)0x00;
                    compareBuffer[251]=(byte)0x01;
                    compareBuffer[252]=(byte)0x02;
                    bte_handler.appendTLV((byte)0x84,(byte)0x00,Util.makeShort((byte)0x01,(byte)0x02));
                    buffer=new byte[253];
                    bte_handler.copy(buffer,(short)0,(short)253);
                    //compare the arrays
                    if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)253)==(byte)0x00){
                        bRes=true;
                    }
                }
                catch(Exception exp){
                    bRes=false;
                }
                this.reportTestOutcome(testCaseNb,bRes);
    }
}
