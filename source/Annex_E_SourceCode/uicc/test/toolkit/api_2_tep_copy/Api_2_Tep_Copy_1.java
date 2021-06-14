//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tep_copy;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;

/**
 * uicc.toolkit package, TerminalProfile class, copy() method
 * applet 1
 */
public class Api_2_Tep_Copy_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] nullBuffer;
    private byte[] dstBuffer = new byte[5];

    /**
     * Constructor of the applet
     */
    public Api_2_Tep_Copy_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Api_2_Tep_Copy_1 thisApplet = new Api_2_Tep_Copy_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_STATUS_COMMAND and EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.requestPollInterval(POLL_SYSTEM_DURATION);
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }
    

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {
        // Result of tests
        boolean bRes;
        byte index;
                
        if (event == EVENT_STATUS_COMMAND) {
            /** Test Case 1: ToolkitException TERMINAL_PROFILE_NOT_AVAILABLE is sent */
            testCaseNb = (byte) 1;
            bRes = false;
            try {
                TerminalProfile.copy((short)0, dstBuffer, (short)0, (short)2);
            }
            catch (ToolkitException e) {
                if (isBufferEmpty(dstBuffer))
                    bRes = (e.getReason() == ToolkitException.TERMINAL_PROFILE_NOT_AVAILABLE);
            }
            catch (Exception e) {
                   bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);
        }

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /** Test Case 2: Null buffer */
            testCaseNb = (byte)2;
            bRes = false;
            try {
                TerminalProfile.copy((short)0, nullBuffer, (short)0, (short)4);
            }
            catch (NullPointerException e) { 
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 3: dstOffset >= dstBuffer.length */
            testCaseNb = (byte)3;
            bRes = false;
            try {
                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0);
                TerminalProfile.copy((short)0, dstBuffer, (short)5, (short)1);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                if (isBufferEmpty(dstBuffer))
                    bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 4: dstOffset < 0 */
            testCaseNb = (byte)4;
            bRes = false;
            try {
                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short) dstBuffer.length, (byte)0);
                TerminalProfile.copy((short)0, dstBuffer, (short)-1, (short)1);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                if (isBufferEmpty(dstBuffer))
                    bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 5: dstLength < 0 */
            testCaseNb = (byte)5;
            bRes = false;
            try {
                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0);
                TerminalProfile.copy((short)0, dstBuffer, (short)0, (short)-1);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                if (isBufferEmpty(dstBuffer))
                    bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 6: dstLength > dstBuffer.length */
            testCaseNb = (byte)6;
            bRes = false;
            try {
                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0);
                TerminalProfile.copy((short)0, dstBuffer, (short)0, (short)6);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                if (isBufferEmpty(dstBuffer))
                    bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 7: dstOffset + dstLength > dstBuffer.length */
            testCaseNb = (byte)7;
            bRes = false;
            try {
                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0);
                TerminalProfile.copy((short)0, dstBuffer, (short)3, (short)3);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                if (isBufferEmpty(dstBuffer))
                    bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 8: Successful call */
            testCaseNb = (byte)8;
            bRes = false;
            try {
                dstBuffer = new byte[6];
                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short) dstBuffer.length, (byte)0x55);

                if (TerminalProfile.copy((short)0, dstBuffer, (short)0, (short)6) == (short)6) {
                    if (   ( (dstBuffer[0]&(byte)0xFD) == (byte) 0xA9)
                        && ( dstBuffer[1] == (byte) 0x01)
                        && ( dstBuffer[2] == (byte) 0xD2)
                        && ( dstBuffer[3] == (byte) 0xF0)
                        && ( dstBuffer[4] == (byte) 0x01)
                        && ( dstBuffer[5] == (byte) 0x02)
                        ) {
                        bRes = true;
                    }
                }
            }
            catch (Exception e)  {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 9: Successful call */
            testCaseNb = (byte)9;
            bRes = false;
            try {
                dstBuffer = new byte[20];

                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0x55);

                if (TerminalProfile.copy((short)1, dstBuffer, (short)3, (short)4) == (short)7) {
                    if (   ( dstBuffer[3] == (byte) 0x01)
                        && ( dstBuffer[4] == (byte) 0xD2)
                        && ( dstBuffer[5] == (byte) 0xF0)
                        && ( dstBuffer[6] == (byte) 0x01)
                       ) {
                        bRes = true;

                        for (index = 0; (index < 3)&&bRes; index++) {
                            bRes = ( dstBuffer[index] == (byte) 0x55 );
                        }                    
                        for (index = 7; (index < dstBuffer.length)&&bRes; index++) {
                            bRes = ( dstBuffer[index] == (byte) 0x55 );
                        }
                    }
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 10: Successful call, copy with length =0 */
            testCaseNb = (byte)10;
            bRes = false;
            try {
                dstBuffer = new byte[20];

                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0x55);

                if (TerminalProfile.copy((short)0, dstBuffer, (short)20, (short)0) == (short)20) {
                    bRes = true;

                    for (index = 0; (index < dstBuffer.length)&&bRes; index++) {
                        bRes = (dstBuffer[index] == (byte) 0x55);
                    }
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            /** Test Case 11: Value outside MEProfile data available */
            testCaseNb = (byte)11;
            bRes = false;
            try {
                dstBuffer = new byte[6];

                Util.arrayFillNonAtomic(dstBuffer, (short)0, (short)dstBuffer.length, (byte)0x55);

                if (TerminalProfile.copy((short)13, dstBuffer, (short)0, (short)6) == (short)6) {
                    if (   ( dstBuffer[0] == (byte) 0x8D)
                        && ( dstBuffer[1] == (byte) 0xFF)
                        && ( dstBuffer[2] == (byte) 0x00)
                        && ( dstBuffer[3] == (byte) 0x00)
                        && ( dstBuffer[4] == (byte) 0x00)
                        && ( dstBuffer[5] == (byte) 0x00)
                        ) {
                        bRes = true;
                    }
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

        }
    }

    boolean isBufferEmpty(byte[] buf) {
        for ( byte i = 0; i<buf.length; i++) {
            if (buf[i] != (byte)0) {
                return false;
            }
        }
        return true;
    }
}
