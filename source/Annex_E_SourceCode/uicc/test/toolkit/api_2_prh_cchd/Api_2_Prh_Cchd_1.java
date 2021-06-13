//-----------------------------------------------------------------------------
//Api_2_Prh_Cchd_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cchd;

import javacard.framework.*;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cchd
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Cchd_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    public final static byte CHANNEL_ID1 = (byte)0x01;
    public final static byte QUALIFIER_1 = (byte)0x01;
    public static byte[] ADDRESS_VALUE = {(byte)0x81, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
    public static byte[] BEARER_VALUE = {(byte)0x03, (byte)0x00};
    public static byte[] BUFFER_SIZE_VALUE = {(byte)0x00, (byte)0x0A};
    public static byte[] RES_BUFFER2 = {(byte)'H', (byte)'e', (byte)'l', (byte)'l', (byte)'o', (byte)'2'};
    public static byte[] RES_BUFFER3 = {(byte)'H', (byte)'e', (byte)'l', (byte)'l', (byte)'o', (byte)'3'};
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Cchd_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Cchd_1  thisApplet = new Api_2_Prh_Cchd_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {

        // destination buffer declaration
        byte[] dstBuffer = new byte[8];
        short dstOffset;
        short dstLength;

        // Result of each test
        boolean bRes = true ;

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            testCaseNb = (byte) 1;
            // build proactive commands OPEN CHANNEL
            try {
                proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
                proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
                proHdlr.send();
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 2 : Build and send a RECEIVE DATA command
            testCaseNb = (byte) 2;
            try {
                proHdlr.init(PRO_CMD_RECEIVE_DATA, QUALIFIER_1, DEV_ID_CHANNEL_1);
                proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x02);
                proHdlr.send();
                // copyChannelData() with NULL dstBuffer
                dstOffset = (short) 0;
                dstLength = (short) 1;
                try {
                    (ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(null, dstOffset, dstLength);
                    bRes = false;
                }
                catch (NullPointerException e) {
                    bRes = true;
                }
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 3 : Build and send a RECEIVE DATA command
            testCaseNb = (byte) 3;
            try {
                proHdlr.init(PRO_CMD_RECEIVE_DATA, QUALIFIER_1, DEV_ID_CHANNEL_1);
                proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x06);
                proHdlr.send();
                // copyChannelData() with negative dstOffset
                dstOffset = (short) -1;
                dstLength = (short) 1;
                try {
                    (ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength);
                    bRes = false;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 3 : copyChannelData() with negative dstLength
            testCaseNb = (byte) 4;

            dstOffset = (short) 0;
            dstLength = (short) -1;
            try {
                (ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength);
                bRes = false;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // check dstBuffer is empty
            testCaseNb = (byte) 5;
            bRes = true;
            for (short i=0; i<(short)(dstBuffer.length-1); i++) {
                if (dstBuffer[i] != (byte)0) {
                    bRes &= false;
                }
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 4 : copyChannelData() with dstOffset+dstLength greater than dstBuffer.length
            testCaseNb = (byte) 6;

            dstOffset = (short) 5;
            dstLength = (short) 5;
            try {
                (ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength);
                bRes = false;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // check dstBuffer is empty
            testCaseNb = (byte) 7;
            bRes = true;
            for (short i=0; i<(short)(dstBuffer.length-1); i++) {
                if (dstBuffer[i] != (byte)0) {
                    bRes &= false;
                }
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 5 : copyChannelData() with dstLength too large
            testCaseNb = (byte) 8;
            bRes = false;
            dstOffset = (short) 0;
            dstLength = (short) 7;
            try {
                (ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength);
                bRes = false;
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true;
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 6 : Build and send a RECEIVE DATA command
            testCaseNb = (byte) 9;
            bRes = false;
            try {
                proHdlr.init(PRO_CMD_RECEIVE_DATA, QUALIFIER_1, DEV_ID_CHANNEL_1);
                proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x06);
                proHdlr.send();
                // copyChannelData() without Channel Data TLV element
                dstOffset = (short) 0;
                dstLength = (short) 6;
                try {
                    (ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength);
                    bRes = false;
                }
                catch (ToolkitException e) {
                    if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                        bRes = true;
                    }
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 7 : Successful copyChannelData()
            //               Build and send a RECEIVE DATA command
            testCaseNb = (byte) 10;
            try {
                proHdlr.init(PRO_CMD_RECEIVE_DATA, QUALIFIER_1, DEV_ID_CHANNEL_1);
                proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x06);
                proHdlr.send();
                // Call findTLV() with TAG of DEVICE IDENTITY
                proHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1);
                // copyChannelData()
                dstOffset = (short) 0;
                dstLength = (short) 6;
                bRes = ((ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength) == (short)(dstOffset + dstLength));
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 8 : Compare copied Buffer
            testCaseNb = (byte) 11;
            bRes = true;
            if (Util.arrayCompare(dstBuffer, (short)0, RES_BUFFER2, (short)0, dstLength) != 0) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 9 : Check the Channel Data TLV is selected
            testCaseNb = (byte) 12;
            bRes = true;
            if ((ProactiveResponseHandlerSystem.getTheHandler()).getValueByte((short)0) != 'H') {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 10: Successful copyChannelData()
            testCaseNb = (byte) 13;
            bRes = true;
            dstOffset = (short) 2;
            dstLength = (short) 3;
            if ((ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength) != (short)(dstOffset+dstLength)) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 11: Compare copied Buffer
            testCaseNb = (byte) 14;
            bRes = true;
            if (Util.arrayCompare(dstBuffer, dstOffset, RES_BUFFER2, (short)0, dstLength) != 0) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 12: Successful copyChannelData() with initialized buffer
            testCaseNb = (byte) 15;
            for (short i=0; i<dstBuffer.length; i++) {
                dstBuffer[i] = (byte)i;
            }

            bRes = true;
            dstOffset = (short) 2;
            dstLength = (short) 3;
            if ((ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength) != (short)(dstOffset+dstLength)) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 13: Compare copied Buffer
            testCaseNb = (byte) 16;
            bRes = true;
            if ((Util.arrayCompare(dstBuffer, dstOffset, RES_BUFFER2, (short)0, dstLength) != 0)
                && (dstBuffer[0] != (byte)0)
                && (dstBuffer[1] != (byte)1)
                && (dstBuffer[5] != (byte)5)) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 14: Successful copyChannelData(), with 2 TLV
            testCaseNb = (byte) 17;
            try {
                proHdlr.init(PRO_CMD_RECEIVE_DATA, QUALIFIER_1, DEV_ID_CHANNEL_1);
                proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x0C);
                proHdlr.send();
                // copyChannelData()
                dstOffset = (short) 0;
                dstLength = (short) 6;
                bRes = ((ProactiveResponseHandlerSystem.getTheHandler()).copyChannelData(dstBuffer, dstOffset, dstLength) == (short)(dstOffset + dstLength));
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // --------------------------------------------
            // Test Case 15: Compare copied Buffer
            testCaseNb = (byte) 18;
            bRes = true;
            if (Util.arrayCompare(dstBuffer, dstOffset, RES_BUFFER3, (short)0, dstLength) != 0) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
