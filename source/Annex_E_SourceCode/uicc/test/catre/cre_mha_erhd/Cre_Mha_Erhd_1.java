//-----------------------------------------------------------------------------
//FWK_MHA_ERHD
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_erhd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;
import uicc.access.UICCConstants;


public class Cre_Mha_Erhd_1 extends TestToolkitApplet {

    /** result of specific testcase */
    private boolean                    bRes                = false;
    /** number of testcase */
    private byte                    testCaseNb            = (byte) 0x00;
//    /** nominal condition of the handlers */
//    private boolean                    HANDLER_AVAILABLE    = true;
    /** */
    private final static byte[]     menuItem1               = {(byte) 'I', (byte) 't', (byte) 'e', (byte) 'm', (byte) ' ', (byte) '1'};
    private final static byte[]     menuItem2               = {(byte) 'I', (byte) 't', (byte) 'e', (byte) 'm', (byte) ' ', (byte) '2'};
    private final static byte[]     TEXT                    = {(byte) 'T', (byte) 'E', (byte) 'X', (byte) 'T', (byte) ' ', (byte) '1'};
    public  final static byte       QUALIFIER_1             = (byte)  0x01;
    public  final static byte[]     ADDRESS_VALUE           = {(byte) 0x81, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88};
    public  final static byte[]     BEARER_VALUE            = {(byte) 0x03, (byte) 0x00};
    public  final static byte[]     BUFFER_SIZE_VALUE       = {(byte) 0x00, (byte) 0x01};
    protected byte                  service_id              = (byte)0x00;
    protected byte[]                SERVICE_RECORD_VALUE    = {(byte) 0x00, (byte) 0x00, (byte) 0x00};
    private byte                    triggerNr               = (byte) 0x00;
    private boolean                 bRes_old                = false;
    protected final byte[]          fileEFtaru              = new byte[]{ (byte) 0x01, (byte) 0x3F, (byte) 0x00, (byte) 0x7F, (byte) 0x4A,(byte) 0x6F, (byte) 0x03 };

    /** list included registered all events */
    private short             eventList[]    = {
                                            EVENT_EVENT_DOWNLOAD_MT_CALL,
                                            EVENT_EVENT_DOWNLOAD_CALL_CONNECTED,
                                            EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED,
                                            EVENT_EVENT_DOWNLOAD_LOCATION_STATUS,
                                            EVENT_EVENT_DOWNLOAD_USER_ACTIVITY,
                                            EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE,
                                            EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS,
                                            EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION,
                                            EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION,
                                            EVENT_PROFILE_DOWNLOAD,
                                            EVENT_FIRST_COMMAND_AFTER_ATR,
                                            EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS,
                                            EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE,
                                            EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED,
                                            EVENT_UNRECOGNIZED_ENVELOPE,
                                            EVENT_CALL_CONTROL_BY_NAA,
                                            EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE,
                                            EVENT_APPLICATION_DESELECT,
                                            EVENT_EVENT_BROWSING_STATUS,
                                            EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE,
                                        };


    /**
     * Create an instance of the Applet, the Java Card runtime environment will call this static method first.
     *
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray The maximum value of bLength is 127.
     * @throws ISOException if the install method failed
     * @see javacard.framework.Applet
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException {
        Cre_Mha_Erhd_1 applet = new Cre_Mha_Erhd_1();

        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }


        //initialize the data of the test applet
        applet.init();

        //- Applet registration
        //register to EVENT_MENU_SELECTION
        applet.obReg.initMenuEntry(menuItem1,(short)0,(short)menuItem1.length,(byte)0x00,false,(byte)0x00,(short)0);
        //register to EVENT_MENU_SELECTION_HELP_REQUEST
        applet.obReg.initMenuEntry(menuItem1,(short)0,(short)menuItem2.length,(byte)0x00,true ,(byte)0x00,(short)0);
        //register to EVENT_STATUS_COMMAND
        applet.obReg.requestPollInterval((short)1);
        //register to EVENT_EVENT_DOWNLLOAD_LOCAL_CONNECTION
        applet.service_id = applet.obReg.allocateServiceIdentifier();
        applet.SERVICE_RECORD_VALUE[(byte)0x01]=applet.service_id;
        //register to the resto of the events
        applet.obReg.setEventList(applet.eventList,(short)0,(short)(applet.eventList.length));
        //register to file EF_TARU
        applet.obReg.registerFileEvent( EVENT_EXTERNAL_FILE_UPDATE,applet.fileEFtaru,(short) 0,(short) applet.fileEFtaru.length,  null, (short) 0, (byte)0x00);
        //register to EVENT_TIMER_EXPIRATION
        for (byte timer=(byte)0x00;timer<(byte)0x08;timer++){
            applet.obReg.allocateTimer();
        }
        for (byte timer=(byte)0x02;timer<=(byte)0x08;timer++){
        //release timer from 2 to 8 to be sure that only timer id 1 is allocated
            applet.obReg.releaseTimer(timer);
        }

    }

    /* (non-Javadoc)
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException {
        bRes=false;
        testCaseNb+=(byte)01;


        switch(event) {
            //-- TC 1
            case(EVENT_FIRST_COMMAND_AFTER_ATR):
                checkHandler();
                obReg.clearEvent(EVENT_FIRST_COMMAND_AFTER_ATR);
            break;

            //--TC 2
            case(EVENT_PROFILE_DOWNLOAD):
                checkHandler();
                obReg.clearEvent(EVENT_PROFILE_DOWNLOAD);
            break;

            //-- TC 16
            case EVENT_STATUS_COMMAND:
               try {
                   EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                }
                catch (ToolkitException exp){
                    if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
                        // Open Channel(1) proactive command for next test case 17
                        ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
                        proHdr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
                        proHdr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short) 0, (short) 5);
                        proHdr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short) 0, (short) 2);
                        proHdr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short) 0, (short) 2);
                        proHdr.send();
                        bRes=true;
                    }
                }
                catch (Exception exp) { bRes = false;}
                this.reportTestOutcome(testCaseNb, bRes);
            break;

            //-- TC 18,20.2
            case EVENT_CALL_CONTROL_BY_NAA:
                //-- TC 18.1
                if (triggerNr==(byte)0x00) {//tc is triggered the first time
                     try {
                        EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                        envRHdr.postAsBERTLV(true, (byte) 0x00);
                        //call all methods including the inherited)
                        bRes = isHandlerNotAvailable(envRHdr);
                        if(bRes) {
                            //next call control by naa belongs to this test also
                            triggerNr+=(byte)0x01;
                            bRes_old=bRes;
                        }
                    }
                    catch (Exception exp) {
                        bRes = false;
                        triggerNr+=(byte)0x01;
                    }
                testCaseNb=(byte)(testCaseNb-(byte)0x01);//next naa trigger belongs to this test case
                }
                //-- TC 18.2
                else if(triggerNr==(byte)0x01)
                {
                     try {
                         //if bRes_old is false the first part of test case is failed
                         if(bRes_old){
                             EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                             ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
                             byte bArray[]= new byte[] {(byte)0x00,(byte)0x00};
                             proHdr.initDisplayText((byte)0x80,(byte)0x04,bArray,(short)0,(short)bArray.length);
                             proHdr.send();
                             //call all method of the EnvelopeResponseHandler including inherited
                             bRes = isHandlerNotAvailable(envRHdr);
                             triggerNr=(byte)0x00;
                         }
                     }
                     catch(Exception exp) {bRes=false;}
                     triggerNr=(byte)0x00;
                 }
                //-- TC 20.2
                else if(testCaseNb==(byte)0x14){
                    try{
                        EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                        envRHdr.postAsBERTLV(true,(byte)0x00);
                        //TC 20.1 shall be executed successfully
                        bRes=true;
                    }
                    catch(Exception exp){bRes=false;}
                }
                this.reportTestOutcome(testCaseNb, bRes);
             break; //call controll by naa

             //-- TC 19,20.1,21
             case EVENT_UNRECOGNIZED_ENVELOPE:
                 //-- TC 19.1
                 if(triggerNr==(byte)0x00){
                     bRes_old=false;
                     testCaseNb=(byte)0x13;
                     try{
                        EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                        envRHdr.postAsBERTLV(true,(byte)0x00);
                        //Calls all methods of the handler (including the inherited methods).
                        bRes=isHandlerNotAvailable(envRHdr);
                        if(bRes){bRes_old=bRes;}
                     }
                     catch(Exception exp){
                         bRes=false;
                         bRes_old=false;
                     }
                     testCaseNb=(byte)(testCaseNb-(byte)0x01);//next unrec. envelope trigger belongs to this case
                     triggerNr+=(byte)0x01;
                 }
                 //-- TC 19.2
                 else if(triggerNr==(byte)0x01){
                     try{
                         //if bRes_old is false the first part of testcase failed
                         if(bRes_old){
                             EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                             ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();

                             byte bArray[] = new byte[]{(byte)0x01,(byte)0x02,(byte)0x03};
                             proHdr.initDisplayText((byte)0x80,(byte)0x04,bArray,(short)0,(short)bArray.length);
                             proHdr.send();
                             //Calls all methods of the handler (including the inherited methods).
                             bRes=isHandlerNotAvailable(envRHdr);
                         }
                     }
                     catch(Exception exp){
                         bRes=false;
                     }
                     triggerNr+=(byte)0x01;
                     this.reportTestOutcome(testCaseNb,bRes);
                 }
                 //-- TC 20.1
                 //test case 20.2 will be triggered by call controll by naa
                 else if(triggerNr==(byte)0x02){
                    bRes_old=false;
                    try{
                        triggerNr+=(byte)0x01;
                        //next case triggered by call controll by NAA belongs to this test.
                        testCaseNb=(byte)(testCaseNb-(byte)0x01);
                        ProactiveHandler prHdr = ProactiveHandlerSystem.getTheHandler();
                        //build a proactive command DISPLAY TEXT
                        prHdr.initDisplayText((byte)0x80,(byte)0x04,TEXT,(short)0,(short)TEXT.length);
                        //send the proactive command DISPLAY TEXT
                        prHdr.send();
                        bRes=true;
                   }
                   catch(Exception exp){
                       bRes=false;
                   }

                 }
                //-- TC 21
                else if (triggerNr==(byte)0x03){
                    try{
                        EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
                        bRes=true;
                    }
                    catch(Exception exp){bRes=false;}
                    triggerNr=(byte)0x00;
                    this.reportTestOutcome(testCaseNb,bRes);
                }


             break;

             //-- TC 23
             case EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED:
                     obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                     checkHandler();
             break;

             //-- TC 24
             case EVENT_PROACTIVE_HANDLER_AVAILABLE:
                try{
                    EnvelopeResponseHandler envRHdr = EnvelopeResponseHandlerSystem.getTheHandler();

                }
                catch (ToolkitException exp){
                    if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
                        //get the ProactiveHandler
                        ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
                        proHdr.init(PRO_CMD_DECLARE_SERVICE, (byte)0, DEV_ID_TERMINAL);
                        proHdr.appendTLV(TAG_SERVICE_RECORD, SERVICE_RECORD_VALUE, (short) 0, (short) 3);
                        if (proHdr.send() == (byte) 0x00) {
                            bRes = true;
                        }
                    }
                }

                 catch(Exception exp){bRes=false;}
                 this.reportTestOutcome(testCaseNb,bRes);
             break;

             //-- TC 25
             case EVENT_APPLICATION_DESELECT:
                 checkHandler();
                 obReg.clearEvent(EVENT_APPLICATION_DESELECT);
             break;

            //-- TC 3,4,5,6,7,8,9,10,11,12,13,14,15,
            default:
                checkHandler();
            break;

        }
    }







    /**
     * Verifies if the envelope response handler is not available
     * @return value of bRes
     */
    private boolean checkHandler()
    {
        bRes=false;
        try {
            EnvelopeResponseHandler EnvRHdr = EnvelopeResponseHandlerSystem.getTheHandler();
        }
        catch(ToolkitException exp) {
            if(exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE) {
                bRes=true;
            }
        }
        catch(Exception exp) {bRes=false;}
        this.reportTestOutcome(testCaseNb,bRes);
        return bRes;

    }



    /**
     * Calls all methods of the Envelope Response Handler (including the
     * inherited methods).
     *
     * @param envRHdr EnvelopeResponseHandler
     * @return true if every called method thows a ToolkitException.HANDLER_NOT_AVAILABLE.
     * Otherwise it returns false
     */
    boolean isHandlerNotAvailable(EnvelopeResponseHandler envRHdr) {
        bRes = true;
        byte bArray[] = new byte[]{(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04};
        byte buffer[] = new byte[10];
        //
        // call all methods inherited from Interface EditHandler
        //
        //method post(boolean value)
        try {
            envRHdr.post(true);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //method postAsBERTLV(boolean value, byte tag)..
        try {
            envRHdr.postAsBERTLV(true,(byte)0x00);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendArray(byte[]buffer,short offset, short length)
        try {
            envRHdr.appendArray(bArray,(short)0,(short)4);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, byte value)
        try {
            envRHdr.appendTLV((byte)0x01,(byte)0x02);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, byte[] value, short valueOffset, short valueLength)
        try {
            envRHdr.appendTLV((byte)0x01,bArray,(short)0,(short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, byte[] value1, short value1Offset, short value1Length, byte[] value2, short value2Offset, short value2Length)
        try {
            envRHdr.appendTLV((byte)0x01,bArray,(short)0,(short)1,bArray,(short)1,(short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, byte value1, byte value2)
        try {
            envRHdr.appendTLV((byte)0x01,(byte)0x02,(byte)0x03);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, byte value1, byte[] value2, short value2Offset, short value2Length)
        try {
            envRHdr.appendTLV((byte)0x01,(byte)0x02,bArray,(short)0,(short)2);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, byte value1, short value2)
        try {
            envRHdr.appendTLV((byte)0x01,(byte)0x02,(short)3);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, short value)
        try {
            envRHdr.appendTLV((byte)0x01,(short)2);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //appendTLV(byte tag, short value1, short value2)
        try {
            envRHdr.appendTLV((byte)0x01,(short)2,(short)3);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //clear()
        try {
            envRHdr.clear();
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //
        // call all methods inherited from Interface ViewHandler
        //

        //compareValue(short valueOffset, byte[] compareuffer, short compareOffset, short compareLength)
        try {
            envRHdr.compareValue((short)0,buffer, (short)0, (short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //copy (byte[] dstBuffer, short dstOffset, short dstLength)
        try {
            envRHdr.copy(buffer,(short)0,(short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //copyValue(short valueOffset, byte[] dstBuffer, short dstOffset, short dstLength)
        try {
            envRHdr.copyValue((short)0,buffer,(short)0,(short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //findAndCompareValue(byte tag,byte[] compareBuffer, short compareOffset)
        try {
            envRHdr.findAndCompareValue((byte)0x01,buffer,(short)0);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //findAndCompareValue(byte tag, byte occurence, short valueOffset, byte[] compareBuffer,  short compareOffset, short compareLength)
        try {
            envRHdr.findAndCompareValue((byte)0x00,(byte)0x01,(short)1,buffer,(short)0,(short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //findAndCopyValue(byte tag, byte[] dstBuffer, short dstOffset)
        try {
            envRHdr.findAndCopyValue((byte)0x00,buffer,(short)0);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //findAndCopyValue(byte tag, byte occurence, short valueOffset, byte[] dstBuffer, short dstOffset, short dstlength)
        try {
            envRHdr.findAndCopyValue((byte)0x00,(byte)0x01,(short)0,buffer,(short)0,(short)1);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //findTLV(byte tag, byte occurrence)
        try {
            envRHdr.findTLV((byte)0x00,(byte)0x01);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //getCapacity()
        try {
            envRHdr.getCapacity();
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //getLength()
        try {
            envRHdr.getLength();
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //getValueByte(short vallueOffset)
        try {
            envRHdr.getValueByte((short)0);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //getValueLength
        try {
            envRHdr.getValueLength();
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }
        //getValueShort(short valueOffset)
        try {
            envRHdr.getValueShort((short)0);
        }
        catch (ToolkitException exp) {
            if (exp.getReason() != ToolkitException.HANDLER_NOT_AVAILABLE) {bRes = false;}
        }
        catch(Exception exp) {
            bRes=false;
        }

        return bRes;
    }




}