//-----------------------------------------------------------------------------
//Cre_Mha_Enhd_2.java
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
import uicc.access.UICCConstants;


public class Cre_Mha_Enhd_2 extends TestToolkitApplet {

    /** result of specific testcase */
    boolean                    bRes                = false;
    /** number of testcase */
    byte                    testCaseNb             = (byte) 0x00;
    boolean                 handler_available      = true;
    private final byte[]    menuItem1              = {(byte) 'I', (byte) 't', (byte) 'e', (byte) 'm', (byte) ' ', (byte) '1'};
    private final byte[]    menuItem2              = {(byte) 'I', (byte) 't', (byte) 'e', (byte) 'm', (byte) ' ', (byte) '2'};
    protected byte[]        SERVICE_RECORD_VALUE   = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    protected final byte[]  fileEFtaru             = new byte[]{ (byte) 0x01, (byte) 0x3F, (byte) 0x00, (byte) 0x7F, (byte) 0x4A,(byte) 0x6F, (byte) 0x03 };
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
                                                    EVENT_UNRECOGNIZED_ENVELOPE,
                                                    EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE,
                                                    EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS,
                                                    EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE,
                                                    EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED,
                                                    EVENT_APPLICATION_DESELECT,
                                                    EVENT_FIRST_COMMAND_AFTER_ATR,
                                                    EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE,
                                                    EVENT_EVENT_BROWSING_STATUS,
                                                    EVENT_PROFILE_DOWNLOAD,
                                                    EVENT_EVENT_DOWNLOAD_FRAMES_INFORMATION_CHANGED,
                                                    EVENT_EVENT_DOWNLOAD_HCI_CONNECTIVITY
                                                    };

    /**
     */
    private Cre_Mha_Enhd_2 () {}


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
        Cre_Mha_Enhd_2 applet = new Cre_Mha_Enhd_2();
        //initialize the data of the test applet
        byte aidLen = bArray[bOffset];
        if (aidLen == (byte) 0) {
            applet.register();
        }
        else {
            applet.register(bArray, (short) (bOffset + 1), aidLen);
        }

        applet.init();

        //- Applet registration
        //register to EVENT_MENU_SELECTION
        applet.obReg.initMenuEntry(applet.menuItem1,(short)0,(short)applet.menuItem1.length,(byte)0x00,false,(byte)0x00,(short)0);
        //register to EVENT_MENU_SELECTION_HELP_REQUEST
        applet.obReg.initMenuEntry(applet.menuItem1,(short)0,(short)applet.menuItem2.length,(byte)0x00,true ,(byte)0x00,(short)0);
        //register to EVENT_STATUS_COMMAND
        applet.obReg.requestPollInterval((short)1);
        //register to EVENT_TIMER_EXPIRATION
        applet.obReg.allocateTimer();
        //register to the resto of the events
        applet.obReg.setEventList(applet.eventList,(short)0,(short)(applet.eventList.length));
        //register to file EF_TARU
        applet.obReg.registerFileEvent( EVENT_EXTERNAL_FILE_UPDATE,applet.fileEFtaru,(short) 0,(short) applet.fileEFtaru.length,  null, (short) 0, (byte)0x00);
    }


    /* (non-Javadoc)
     * @see uicc.toolkit.ToolkitInterface#processToolkit(short)
     */
    public void processToolkit(short event) throws ToolkitException
    {
        bRes=false;
        testCaseNb+=(byte)0x01;

        if (testCaseNb==(byte)0x01 ||
            testCaseNb==(byte)0x0C || //tc 16
            testCaseNb==(byte)0x10)   //tc 23
                        {handler_available=false;}

        else if (testCaseNb ==(byte)0x03 ||
                 testCaseNb ==(byte)0x0D || //tc 19
                 testCaseNb ==(byte)0x11 )  //tc 24
                          {handler_available=true;}

        switch (event) {
           //-- tc 01
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

           //-- all other test cases
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
