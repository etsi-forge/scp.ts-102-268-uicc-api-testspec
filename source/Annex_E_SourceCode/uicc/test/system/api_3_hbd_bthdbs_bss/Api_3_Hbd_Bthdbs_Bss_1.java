//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.system.api_3_hbd_bthdbs_bss;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.system.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.system package, HandlerBuilder class, buildTLVHandler() method
 * applet 1
 */
public class Api_3_Hbd_Bthdbs_Bss_1 extends TestToolkitApplet {
    

    private byte testCaseNb = (byte) 0x00;
    private static boolean bRes;
    private static byte[] HandlerBuffer = {(byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9};
    private byte copyBuffer[] = new byte[HANDLER_CAPACITY] ;
    public final static short HANDLER_CAPACITY = (short)10;
    public final static short NEGATIVE_CAPACITY = (short)-10;

	/**
     * Constructor of the applet
     */
    public Api_3_Hbd_Bthdbs_Bss_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // create a new applet instance
        Api_3_Hbd_Bthdbs_Bss_1 thisApplet = new Api_3_Hbd_Bthdbs_Bss_1();

        // register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // initialise the data of the test applet
        thisApplet.init();

        // registration to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        ViewHandler testObject;
        EditHandler testHandler;
        short length = (short)0;
        short offset = (short)0;
        short result = (short)0;

        
        /** test case 1: Call buildTLVHandler() method with EDIT_HANDLER type */
        testCaseNb = (byte)0x01;
        bRes = true;
        offset = (short)0;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                        HandlerBuffer, offset, length);
            if (testObject == null) {
                bRes = false;
            }
        }
        catch (Exception e) {
            bRes=false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 2: Call buildTLVHandler() method with BER_EDIT_HANDLER type */
        testCaseNb = (byte)0x02;
        bRes = true;
        offset = (short)0;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, HANDLER_CAPACITY,
                                                        HandlerBuffer, offset, length);
            if (testObject == null) {
                bRes = false;
            }
        }
        catch (Exception e) {
            bRes=false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 3: Negative capacity */
        testCaseNb = (byte)0x03;
        bRes = false;
        offset = (short)0;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, NEGATIVE_CAPACITY, 
                                                        HandlerBuffer, offset, length);
        }
        catch (SystemException e) {
            if (e.getReason() == SystemException.ILLEGAL_VALUE) {
                bRes = true;
            }
        }
        catch (Exception e) {
            bRes=false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 4: Type does not match with predefined values (0) */
        testCaseNb = (byte)0x04;
        bRes = false;
        offset = (short)0;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler((byte)0, HANDLER_CAPACITY, HandlerBuffer, offset, length);
        }
        catch (SystemException e) {
            if (e.getReason() == SystemException.ILLEGAL_VALUE) {
                bRes = true;
            }
        }
        catch (Exception e) {
            bRes=false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 5: Type does not match with predefined values (3) */
        testCaseNb = (byte)0x05;
        bRes = false;
        offset = (short)0;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler((byte)3, HANDLER_CAPACITY, HandlerBuffer, offset, length);
        }
        catch (SystemException e) {
            if (e.getReason() == SystemException.ILLEGAL_VALUE) {
                bRes = true;
            }
        }
        catch (Exception e) {
            bRes=false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 6: Internal Buffer starts at bOffset */
        testCaseNb = (byte)0x06;
        bRes = false;
        offset = (short)4;
        length = (short)5;
        try {
            testHandler = (EditHandler)HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                                      HandlerBuffer, offset, length);
            // Copy the EditHandler in copyBuffer
            short copyBufferOffset = (short)0 ;
            short copyBufferLength = (short)testHandler.getLength() ;
            testHandler.copy(copyBuffer, copyBufferOffset, copyBufferLength) ;            
            // Compare copyBuffer to HandlerBuffer
            result = javacard.framework.Util.arrayCompare(copyBuffer, copyBufferOffset, HandlerBuffer, offset, length) ;

            bRes = (result == (short)0) ;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 7: Buffer is null */
        testCaseNb = (byte)0x07;
        bRes = false;
        offset = (short)0;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                        null, offset, length);
        }
        catch (NullPointerException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 8: bOffset > Buffer Length */
        testCaseNb = (byte)0x08;
        bRes = false;
        offset = (short)11;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                        HandlerBuffer, offset, length);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 9: bOffset < 0 */
        testCaseNb = (byte)0x09;
        bRes = false;
        offset = (short)-1;
        length = (short)0;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                        HandlerBuffer, offset, length);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 10: bLength < 0 */
        testCaseNb = (byte)0x0A;
        bRes = false;
        offset = (short)0;
        length = (short)-1;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                        HandlerBuffer, offset, length);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 11: bOffset+bLength > buffer length */
        testCaseNb = (byte)0x0B;
        bRes = false;
        offset = (short)7;
        length = (short)8;
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY,
                                                        HandlerBuffer, offset, length);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);
    }
}
