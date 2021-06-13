//-----------------------------------------------------------------------------
//    Cre_Apt_Edcs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_edcs;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;



/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
 *
 *
 *
 *
 */
public class Cre_Apt_Edcs_1 extends TestToolkitApplet {

    private byte testCaseNb = (byte) 0x01;
    private static boolean bRes;
    private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};

    public final static byte QUALIFIER_1 = (byte)0x01;
    public final static byte QUALIFIER_2 = (byte)0x02;
    public static byte[] ADDRESS_VALUE = {(byte)0x81, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
    public static byte[] BEARER_VALUE = {(byte)0x03, (byte)0x00};
    public static byte[] BUFFER_SIZE_VALUE = {(byte)0x00, (byte)0x0A};

    /* Constructor of the applet */
    public Cre_Apt_Edcs_1() {

    }


    /* Method called by the JCRE at the installation of the applet */
    public static void install (byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instace
        Cre_Apt_Edcs_1 thisApplet = new Cre_Apt_Edcs_1();
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
    }


     /*   Method called by the UICC CAT Runtime Environment*/
    public void processToolkit(short event) {

        bRes=false;

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();

        if (event==EVENT_MENU_SELECTION) {

            switch(testCaseNb) {
                /* Test Case 1: Applet Registration to EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x01:
                    //              2-The applet is registered to EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
                    try {
                        obReg.setEvent(EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS);
                        bRes=true;
                    }
                    catch(Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x02;

                    //              3- The applet shall not be triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
                    bRes=true;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x03;
                    break;

                case (byte)0x03:
                    /*              5- Open a channel with init() method
                                        type = Open Channel
                                        qualifier = 01
                                        dstDevice = ME                  */
                    try {
                        proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
                        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                        proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
                    //              6- send() method is called
                        proHdlr.send();
                        bRes=true;
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x04;

                    //              8- The applet shall not be triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
                    bRes=true;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x05;
                    break;

                case (byte)0x05:
                    /*              10- Open a channel with init() method
                                        type = Open Channel
                                        qualifier = 01
                                        dstDevice = ME                  */
                    try {
                        proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
                        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                        proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);

                    //              11- send() method is called
                        proHdlr.send();
                        bRes=true;
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x06;
                    break;
                /* Test Case 3: Applet Deregistration to EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x07:
                    /*              1- Open a channel with init() method
                                        type = Open Channel
                                        qualifier = 02
                                        dstDevice = ME                  */
                    try {
                        proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_2, DEV_ID_TERMINAL);
                        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                        proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
                    //                  send() method is called
                        proHdlr.send();
                        bRes=true;
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x08;
                    //              2- Close the opened channel unsuccessfully
                    try {
                        proHdlr.initCloseChannel((byte)0x02);
                        proHdlr.send();
                        bRes=true;
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x09;
                    break;
                /* Test Case 5: The applet is not triggered after a reset */
                case (byte)0x0B:
                    /*              1- Open a channel with init() method
                                        type = Open Channel
                                        qualifier = 01
                                        dstDevice = ME                  */
                    try {
                        proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
                        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                        proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
                    //              2- send() method is called
                        proHdlr.send();
                        bRes=true;
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x0C;
                    //              3- isEventSet()
                    bRes = obReg.isEventSet(EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS);
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x0D;
                    //              5- The applet is not triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
                    bRes=true;
                    reportTestOutcome(testCaseNb, bRes);
                    break;

            }
        }

        if (event==EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS) {

            switch(testCaseNb) {
                /* Test Case 1: 3/8- The applet shall not be triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x03:
                    bRes=false;
                    testCaseNb = (byte) 0x02;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x03;
                    break;
                case (byte)0x05:
                    bRes=false;
                    testCaseNb = (byte) 0x04;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x05;
                    break;
                /* Test Case 2: The applet is triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x06 :
                    bRes=true;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x07;
                    break;
                /* Test Case 3: Applet Deregistration to EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x09:
                    //              4- Close the opened channel successfully
                    try {
                        proHdlr.initCloseChannel((byte)0x02);
                        proHdlr.send();
                        bRes=true;
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x0A;
                /* Test Case 4: 1- The applet is not triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                    bRes=true;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x0B;
                    break;
                /* Test Case 4: 1- The applet shall not be triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x0B:
                    bRes=false;
                    testCaseNb = (byte) 0x0A;
                    reportTestOutcome(testCaseNb, bRes);
                    testCaseNb = (byte) 0x0B;
                    break;
                /* Test Case 5: 5- The applet is not triggered by EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS */
                case (byte)0x0D:
                    bRes=false;
                    reportTestOutcome(testCaseNb, bRes);
                    break;
            }
        }
    }
 }