//-----------------------------------------------------------------------------
//Api_2_Bte_Aptlbss_1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_bte_aptlbss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.system.HandlerBuilder;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.BERTLVEditHandler;
import uicc.toolkit.ToolkitException;

public class Api_2_Bte_Aptlbss_1 extends TestToolkitApplet {

    boolean				bRes			= false;
    byte				testCaseNb		= (byte) 0x00;
    BERTLVEditHandler	bte_handler		= null;
    byte				compareBuffer[]	= null;
    byte				buffer[]		= null;

    /**
     * If AID length is not zero register applet with specified AID
     * @param bArray the array constaing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    private Api_2_Bte_Aptlbss_1 () {
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
        Api_2_Bte_Aptlbss_1 applet = new Api_2_Bte_Aptlbss_1();
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }
        //intialise the test applet values
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
            buffer=new byte[(short)(bte_handler.getCapacity()-1)];
            bte_handler.appendArray(buffer,(short)0,(short)(bte_handler.getCapacity()-1));
            //cause Handler Overflow
            try{
                bte_handler.appendTLV((byte)0x0D,(short)0,(short)1);
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
            bte_handler.appendTLV((byte)0x0E,(short)0,(short)1);
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
            //- clear the handler
            bte_handler.clear();
            bte_handler.appendTLV((byte)0x84,Util.makeShort((byte)0x00,(byte)0x01),Util.makeShort((byte)0x02,(byte)0x03));
            buffer= new byte[6];
            compareBuffer=new byte[]{(byte)0x84,(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03};
            bte_handler.copy(buffer,(short)0,(short)6);
            //compare the arrays
            if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)6)==(byte)0x00){
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
            //-succesful call
            bte_handler.appendTLV((byte)0x01,Util.makeShort((byte)0xFE,(byte)0xFD),Util.makeShort((byte)0xFC,(byte)0xFB));
            buffer= new byte[12];
            compareBuffer=new byte[]{(byte)0x84,(byte)0x04,(byte)0x00,(byte)01,(byte)0x02,(byte)0x03,(byte)0x01,(byte)0x04,(byte)0xFE,(byte)0xFD,(byte)0xFC,(byte)0xFB};
            //-call copy method
            bte_handler.copy(buffer,(short)0,(short)12);
            //compare the arrays
            if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)12)==(byte)0x00){
                bRes=true;
            }
        }
        catch(Exception exp){
            bRes=false;
        }
        this.reportTestOutcome(testCaseNb,bRes);
//		-------------- TESTCASE 5 --------------
        try{
            bRes = false;
            testCaseNb = (byte) 0x05;
            buffer = new byte[247];
            compareBuffer=new byte[253];
            bte_handler.clear();
            buffer[0]=(byte)0x00;
            buffer[1]=(byte)0x81;
            buffer[2]=(byte)0xF4;
            for(short i=3;i<247;i++){
                buffer[3]=(byte)i;
            }
            Util.arrayCopy(buffer,(short)0,compareBuffer,(short)0,(short)247);
            compareBuffer[247]=(byte)0x84;
            compareBuffer[248]=(byte)0x04;
            compareBuffer[249]=(byte)0x00;
            compareBuffer[250]=(byte)0x01;
            compareBuffer[251]=(byte)0x02;
            compareBuffer[252]=(byte)0x03;
            //-call appendArray
            bte_handler.appendArray(buffer,(short)0,(short)247);
            //-successful call
            bte_handler.appendTLV((byte)0x84,Util.makeShort((byte)0x00,(byte)0x01),Util.makeShort((byte)0x02,(byte)0x03));
            //-call getLength()
            if(bte_handler.getLength()==(short)253){
                buffer= new byte[253];
                bte_handler.copy(buffer,(short)0,(short)253);
                if(Util.arrayCompare(buffer,(short)0,compareBuffer,(short)0,(short)253)==(byte)0x00){
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
