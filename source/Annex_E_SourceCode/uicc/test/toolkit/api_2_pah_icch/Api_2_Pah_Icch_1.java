//-----------------------------------------------------------------------------
//Api_2_Pah_Icch_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_icch;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_icch
 *
 * @version 0.0.1 - 20 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Icch_1 extends TestToolkitApplet
{

    // Result of tests
    boolean bRes ;
    
    public final static byte CHANNEL_ID1 = (byte)0x01;
    public final static byte CHANNEL_ID2 = (byte)0x02;
    public final static byte QUALIFIER_1 = (byte)0x01;
    public static byte[] ADDRESS_VALUE = {(byte)0x81, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
    public static byte[] BEARER_VALUE = {(byte)0x03, (byte)0x00};
    public static byte[] BUFFER_SIZE_VALUE = {(byte)0x00, (byte)0x0A};

    // Number of tests
    byte testCaseNb = (byte)0;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Icch_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Icch_1  thisApplet = new Api_2_Pah_Icch_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

        // Register on EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS);
    }

    public void processToolkit(short event) 
    {
        // Result of each test
        bRes = false ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {
            // Open a channel
            proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
            proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
            proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
            proHdlr.send();
        }

        
        if (event == EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS) {
            switch(testCaseNb) {
                case (byte)0 :
                    // --------------------------------------------
                    // Test Case 1 : initCloseChannel
                    testCaseNb = (byte) 1 ;
                    try {
                        proHdlr.initCloseChannel(CHANNEL_ID1);
                        proHdlr.send();
                        bRes = true;
                    }
                    catch (Exception e) {   
                        bRes = false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    break;
            
                    // --------------------------------------------
                    // Test Case 2 :
                case (byte)1 :
                    testCaseNb = (byte) 2 ;
                    try {
                        proHdlr.initCloseChannel(CHANNEL_ID2);
                        proHdlr.initCloseChannel(CHANNEL_ID1);
                        proHdlr.send();
                        bRes = true;
                    }
                    catch (Exception e) {   
                        bRes = false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    break;

                    // --------------------------------------------
                    // Test Case 3 :
                case (byte)2 :
                    testCaseNb = (byte) 3 ;
                    try {
                        proHdlr.findTLV(TAG_ADDRESS,(byte)1);
                        proHdlr.initCloseChannel(CHANNEL_ID1);
                        short i = proHdlr.getValueLength();
                        bRes = false;
                    }
                    catch (ToolkitException e) {
                        if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                            bRes = true;
                        }
                    }
                    catch (Exception e) {
                        bRes = false;
                    }
                    try {
                        proHdlr.send();
                    }
                    catch (Exception e) {}
                    reportTestOutcome(testCaseNb, bRes);
                    break;

                    // --------------------------------------------
                    // Test Case 4 :
                case (byte)3 :
                    testCaseNb = (byte) 4 ;
                    try {
                        proHdlr.initCloseChannel(CHANNEL_ID1);
                        bRes = true;
                    }
                    catch (Exception e) {   
                        bRes = false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    break;
            }

        }
    }
}
