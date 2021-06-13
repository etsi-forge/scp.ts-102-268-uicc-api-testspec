//-----------------------------------------------------------------------------
//CRE_MHA_ENHD
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_mha_enhd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------


import javacard.framework.*;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;


public class Cre_Mha_Enhd_1 extends TestToolkitApplet
{

    /** result of specific testcase */
    private boolean             bRes                    = false;
    /** number of testcase */
    private byte                testCaseNb              = (byte) 0x00;
    private boolean             handler_available       = true;
    /** */
    private final byte[]        menuItem1               = {(byte) 'I', (byte) 't', (byte) 'e', (byte) 'm', (byte) ' ', (byte) '1'};
    private final byte[]        menuItem2               = {(byte) 'I', (byte) 't', (byte) 'e', (byte) 'm', (byte) ' ', (byte) '2'};
    private final byte          QUALIFIER_1             = (byte) 0x01;
    private final byte[]        ADDRESS_VALUE           = {(byte) 0x81, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88};
    private final byte[]        BEARER_VALUE            = {(byte) 0x03, (byte) 0x00};
    private final byte[]        BUFFER_SIZE_VALUE       = {(byte) 0x00, (byte) 0x01};
    protected byte              service_id              = (byte)0x00;
    protected byte[]            SERVICE_RECORD_VALUE    = {(byte) 0x00, (byte) 0x00, (byte) 0x00};
    protected final byte[]      fileEFtaru              = new byte[]{ (byte) 0x01, (byte) 0x3F, (byte) 0x00, (byte) 0x7F, (byte) 0x4A,(byte) 0x6F, (byte) 0x03 };


    /** list included registered all events */
    private final short         eventList[]    = {
                                            EVENT_EVENT_DOWNLOAD_MT_CALL,
                                            EVENT_EVENT_DOWNLOAD_CALL_CONNECTED,
                                            EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED,
                                            EVENT_EVENT_DOWNLOAD_LOCATION_STATUS,
                                            EVENT_EVENT_DOWNLOAD_USER_ACTIVITY,
                                            EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE,
                                            EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS,
                                            EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION,
                                            EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION,
                                            EVENT_UNRECOGNIZED_ENVELOPE,
                                            EVENT_CALL_CONTROL_BY_NAA,
                                            EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE,
                                            EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS,
                                            EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE,
                                            EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED,
                                            EVENT_APPLICATION_DESELECT,
                                            EVENT_FIRST_COMMAND_AFTER_ATR,
                                            EVENT_PROFILE_DOWNLOAD,
                                            EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE,
                                            EVENT_EVENT_BROWSING_STATUS};




    /**
     */
    private Cre_Mha_Enhd_1 () {    }

    /**
     * Create an instance of the Applet, the Java Card runtime environment will call this static method first.
     *
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray The maximum value of bLength is 127.
     * @throws ISOException if the install method failed
     * @see javacard.framework.Applet
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException
    {
        Cre_Mha_Enhd_1 applet = new Cre_Mha_Enhd_1();

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
        applet.obReg.initMenuEntry(applet.menuItem1,(short)0,(short)applet.menuItem1.length,(byte)0x00,false,(byte)0x00,(short)0);
        //register to EVENT_MENU_SELECTION_HELP_REQUEST
        applet.obReg.initMenuEntry(applet.menuItem1,(short)0,(short)applet.menuItem2.length,(byte)0x00,true ,(byte)0x00,(short)0);
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
        testCaseNb+=(byte)0x01;

        if (testCaseNb==(byte)0x01 ||
            testCaseNb==(byte)0x10 || //tc 16
            testCaseNb==(byte)0x17  ) //tc 23
                         {handler_available=false;}

        else if (testCaseNb==(byte)0x03 ||
                 testCaseNb==(byte)0x11 || //tc 17
                 testCaseNb==(byte)0x13 ||
                 testCaseNb==(byte)0x18)   //tc 24
                              {handler_available=true;}


        switch (event) {
           //-- TC 1
           case EVENT_FIRST_COMMAND_AFTER_ATR :
                    testCaseNb=(byte)0x01;
                    checkHandler();
                    obReg.clearEvent(EVENT_FIRST_COMMAND_AFTER_ATR);
            break;
            //-- TC 2
            case EVENT_PROFILE_DOWNLOAD:
                    checkHandler();
                    obReg.clearEvent(EVENT_PROFILE_DOWNLOAD);
            break;
            //-- TC 16
            case EVENT_STATUS_COMMAND:
                try {
                   EnvelopeHandler envHdr = EnvelopeHandlerSystem.getTheHandler();
                }
                catch (ToolkitException exp){
                    if (exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
                        bRes=true;
                        this.reportTestOutcome(testCaseNb, bRes);
                        // Open Channel(1) proactive command for  test case 17
                        ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
                        proHdr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, DEV_ID_TERMINAL);
                        proHdr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short) 0, (short) 5);
                        proHdr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short) 0, (short) 2);
                        proHdr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short) 0, (short) 2);
                        proHdr.send();
                        bRes=true;
                    }
                }
                catch (Exception exp) {
                    bRes = false;
                    this.reportTestOutcome(testCaseNb, bRes);
                    }

            break;

            //-- TC 19
            case EVENT_UNRECOGNIZED_ENVELOPE:
                try{
                    EnvelopeHandler envHdr = EnvelopeHandlerSystem.getTheHandler();
                    //get the ProactiveHandler
                    ProactiveHandler proHdr = ProactiveHandlerSystem.getTheHandler();
                    proHdr.init(PRO_CMD_DECLARE_SERVICE, (byte)0, DEV_ID_TERMINAL);
                    proHdr.appendTLV(TAG_SERVICE_RECORD, SERVICE_RECORD_VALUE, (short) 0, (short) 3);
                    if (proHdr.send() == (byte) 0x00) {
                        bRes = true;
                        this.reportTestOutcome(testCaseNb, bRes);
                    }

                }
                catch(Exception exp){
                    this.reportTestOutcome(testCaseNb, bRes);
                    bRes=false;
                }

           break;


          //-- TC 22
          case EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED:
             try{
                 obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                 EnvelopeHandler envHdr = EnvelopeHandlerSystem.getTheHandler();
                 bRes=true;
             }
             catch(Exception exp){bRes=false;}
             this.reportTestOutcome(testCaseNb, bRes);
          break;

          //-- TC 24
          case EVENT_APPLICATION_DESELECT:
              checkHandler();
              obReg.clearEvent(EVENT_APPLICATION_DESELECT);
          break;

          //-- All other test cases
          default :
              checkHandler();
          break;
        }
    }

     /**
     * Verify if the envelope handler is available or not
     */

    private boolean checkHandler()
    {
        bRes=false;
        //handler shall be available
        if (handler_available==true){
            try {
                EnvelopeHandler EnvHdr = EnvelopeHandlerSystem.getTheHandler();
                bRes=true;
            }
            catch (Exception e) {
                bRes=false;
            }
        }
        //handler shall not be available
        else {
            try {
                EnvelopeHandler EnvHdr = EnvelopeHandlerSystem.getTheHandler();
            }
            catch(ToolkitException exp){
                if(exp.getReason()==ToolkitException.HANDLER_NOT_AVAILABLE){
                    bRes=true;
                }
            }
            catch(Exception exp) {bRes=false;}
        }

        this.reportTestOutcome(testCaseNb,bRes);
        return bRes;
    }


}