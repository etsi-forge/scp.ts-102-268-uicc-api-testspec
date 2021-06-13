//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.system.api_3_hbd_bthdbs;

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
public class Api_3_Hbd_Bthdbs_1 extends TestToolkitApplet {

    private byte testCaseNb = (byte) 0x00;
    private static boolean bRes;
    private static byte[] HandlerBuffer = {(byte)0x02, (byte)0x04, (byte)0xAA, (byte)0xBB, (byte)0x11, (byte)0x22, (byte)0x33 };
    public final static short HANDLER_CAPACITY = (short)10;
    public final static short NEGATIVE_CAPACITY = (short)-10;


    // Constructor of the applet
    public Api_3_Hbd_Bthdbs_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // Create a new applet instance
        Api_3_Hbd_Bthdbs_1 thisApplet = new Api_3_Hbd_Bthdbs_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        ViewHandler testObject;

        /** test case 1: Call buildTLVHandler() method with EDIT_HANDLER type */
        testCaseNb = (byte)0x01;
        bRes = true;
        try {
            testObject = (EditHandler)HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, HANDLER_CAPACITY);
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
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.BER_EDIT_HANDLER, HANDLER_CAPACITY);
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
        try {
            testObject = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, NEGATIVE_CAPACITY);
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

        /** test case 4: Type does not match with predefined values (3) */
        testCaseNb = (byte)0x04;
        bRes = false;
        try {
            testObject = HandlerBuilder.buildTLVHandler((byte)3, HANDLER_CAPACITY);
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

        /** test case 5: Type does not match with predefined values (0) */
        testCaseNb = (byte)0x05;
        bRes = false;
        try {
            testObject = HandlerBuilder.buildTLVHandler((byte)0, HANDLER_CAPACITY);
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
    }
}
