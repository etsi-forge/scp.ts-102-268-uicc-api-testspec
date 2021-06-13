//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_api_hepo;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Cat Runtime Environment, Others parts transferred from API, a Handler is JCRE temporary entry point object
 * applet 1
 */
public class Cre_Api_Hepo_1 extends TestToolkitApplet {

    private byte testCaseNb = (byte)0x00;
    private static boolean bRes;

    /** Registry entry concerning applet instance */
    private static byte[] displayString = {(byte)'H', (byte)'E', (byte)'L', (byte)'L', (byte)'O'};

    static Object staticObject;
    Object object;

    // Constructor of the applet
    public Cre_Api_Hepo_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Cre_Api_Hepo_1 thisApplet = new Cre_Api_Hepo_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }

    /**
     *  Method called by the CAT RE
     */
    public void processToolkit(short event) {

        /** Test Case 1: EnvelopeHandler with static field */
        testCaseNb = (byte)1;

        bRes = false ;
        try {
            staticObject = EnvelopeHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 2: EnvelopeHandler with field */
        testCaseNb = (byte)2;

        bRes = false ;
        try {
            object = EnvelopeHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 3: EnvelopeResponseHandler with static field */
        testCaseNb = (byte)3;

        bRes = false ;
        try {
            staticObject = EnvelopeResponseHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 4: EnvelopeResponseHandler with field */
        testCaseNb = (byte)4;

        bRes = false ;
        try {
            object = EnvelopeResponseHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 5: ProactiveHandler with static field */
        testCaseNb = (byte)5;

        bRes = false ;
        try {
            staticObject = ProactiveHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 6: ProactiveHandler with field */
        testCaseNb = (byte) 6;

        bRes = false ;
        try {
            object = ProactiveHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 7: ProactiveResponseHandler with static field */
        testCaseNb = (byte)7;
        // send proactive command to have the ProactiveResponseHandler available
        ProactiveHandlerSystem.getTheHandler().initDisplayText((byte)0x80, (byte)0x04, displayString, (short)0, (short)displayString.length);
        (ProactiveHandlerSystem.getTheHandler()).send();

        bRes = false ;
        try {
            staticObject = ProactiveResponseHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);

        /** Test Case 8: ProactiveResponseHandler with field */
        testCaseNb = (byte)8;
        bRes = false ;
        try {
            object = ProactiveResponseHandlerSystem.getTheHandler();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
        }
        reportTestOutcome(testCaseNb, bRes);
    }
}
