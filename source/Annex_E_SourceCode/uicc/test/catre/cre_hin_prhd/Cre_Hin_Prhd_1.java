//-----------------------------------------------------------------------------
//    Cre_Hin_Prhd_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_prhd;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Hin_Prhd_1 extends TestToolkitApplet{
    
    
    private byte[] TEXT = {(byte)'T',(byte)'E',(byte)'X',(byte)'T',(byte)' ',(byte)'1'};
    private static byte[] menuEntry1 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'1'};
    private static byte[] menuEntry2 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'2'};
    private static short[] eventList ={(byte)12,(byte)13,(byte)14,(byte)15,(byte)16,(byte)17,(byte)18, (byte)20, (byte)21, (byte)22, (byte)23, (byte)25, (byte)26, (byte)28, (byte)29};
    
    private static byte[] baFileList = {(byte)0x01, (byte)0x3F, (byte)0x00, (byte)0x7F, (byte)0x4A, (byte)0x6F, (byte)0x03};
    private static byte[] aid=null;
    
    private boolean bRes;
    private byte bTestCaseNb = (byte) 0;
    private boolean firstTrigger = true;
    
    private static byte[] ADDRESS_VALUE = {(byte)0x81, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
    private static byte[] BEARER_VALUE = {(byte)0x03, (byte)0x00};
    private static byte[] BUFFER_SIZE_VALUE = {(byte)0x00, (byte)0x0A};
    private static byte[] SERVICE_RECORD = {(byte)0x00, (byte)0x00, (byte)0x00};
    private static byte serviceId = (byte) 0;
    
    private static byte a;
    
    /**
     * Constructor of the applet
     */
    public Cre_Hin_Prhd_1() {
        
    }
    
    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Hin_Prhd_1 thisApplet = new Cre_Hin_Prhd_1();
        
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        
        // Initialise the data of the test applet.
        thisApplet.init();
        
        thisApplet.obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        //register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry1,(short)0,(short)menuEntry1.length,(byte)0,false,(byte)0,(short)0);
        //register to EVENT_MENU_SELECTION_HELP_REQUEST
        thisApplet.obReg.initMenuEntry(menuEntry2,(short)0,(short)menuEntry2.length,(byte)0,true,(byte)0,(short)0);
        //register to EVENT_TIMER_EXPIRATION
        try{

           for(a=0; a<(byte)8; a++){
            thisApplet.obReg.allocateTimer();
           }
           for(a=2; a<(byte)9; a++){
            thisApplet.obReg.releaseTimer((byte)a);
           }
         }
         catch(Exception e){
         } 
        //register to all event download
        thisApplet.obReg.setEventList(eventList,(short)0,(short)eventList.length);
        //register to EVENT_STATUS_COMMAND
        thisApplet.obReg.requestPollInterval((short)POLL_SYSTEM_DURATION);
        //register to EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
        serviceId = thisApplet.obReg.allocateServiceIdentifier();
        SERVICE_RECORD[1]= (byte) serviceId;
        // register to EVENT_EXTERNAL_FILE_UPDATE on update of EF Taru of UICC file system
        thisApplet.obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,(byte[]) baFileList,(short) 0, (short) baFileList.length, (byte[]) aid, (short) 0, (byte) 0);
        
    }
    
    
    public void processToolkit(short event) {
        ProactiveHandler prHdlr;
        ProactiveResponseHandler prRespHdlr;
        
        switch(event){
            
            //event EVENT_PROFILE_DOWNLOAD
            case EVENT_PROFILE_DOWNLOAD:
                //event EVENT_PROACTIVE_HANDLER_AVAILABLE
            case EVENT_PROACTIVE_HANDLER_AVAILABLE:
                //event EVENT_MENU_SELECTION
            case EVENT_MENU_SELECTION:
                //event EVENT_MENU_SELECTION_HELP_REQUEST
            case EVENT_MENU_SELECTION_HELP_REQUEST:
                //event EVENT_CALL_CONTROL_BY_NAA
            case EVENT_CALL_CONTROL_BY_NAA:
                //event EVENT_TIMER_EXPIRATION
            case EVENT_TIMER_EXPIRATION:
                //event EVENT_EVENT_DOWNLOAD_MT_CALL
            case EVENT_EVENT_DOWNLOAD_MT_CALL:
                //event EVENT_EVENT_DOWNLOAD_CALL_CONNECTED
            case EVENT_EVENT_DOWNLOAD_CALL_CONNECTED:
                //event EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED
            case EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED:
                //event EVENT_EVENT_DOWNLOAD_LOCATION_STATUS
            case EVENT_EVENT_DOWNLOAD_LOCATION_STATUS:
                //event EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
            case EVENT_EVENT_DOWNLOAD_USER_ACTIVITY:
                //event EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE
            case EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE:
                //event EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
            case EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS:
                //event EVENT_STATUS_COMMAND
            case EVENT_STATUS_COMMAND:
                //event EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION
            case EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION:
                //event EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION
            case EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION:
                //event EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE
            case EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE:
                //event EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
            case EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS:
                //event EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE
            case EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE:
                //event EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED
            case EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED:
                //event EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
            case EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION:
                //event EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
            case EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE:
                //event EVENT_EVENT_BROWSING_STATUS
            case EVENT_EVENT_BROWSING_STATUS:
                //event EVENT_EXTERNAL_FILE_UPDATE
            case EVENT_EXTERNAL_FILE_UPDATE:
                //event EVENT_UNRECOGNIZED_ENVELOPE
            case EVENT_UNRECOGNIZED_ENVELOPE:
                
                // -----------------------------------------------------------------------
                // Test Case 1 : Applet registration and ProactiveResponseHandler obtaining
                // -----------------------------------------------------------------------
                // -----------------------------------------------------------------------
                // Test Case 2 : The ProactiveResponseHandler remains unchanged after
                //               send() method invocation until next send() method invocation
                // -----------------------------------------------------------------------
                bRes = false;
                bTestCaseNb++;
                try{
                    try{
                        
                        // Get the system instance of handlers
                        prRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
                        prHdlr = ProactiveHandlerSystem.getTheHandler();
                        bRes = (prRespHdlr.getLength() == (short)0);
                        
                        
                        
                        if (firstTrigger) {
                            obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                            
                            /*  Open a channel    qualifier = 01 */
                            prHdlr.init(PRO_CMD_OPEN_CHANNEL,(byte) 0x01, DEV_ID_TERMINAL);
                            prHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)ADDRESS_VALUE.length);
                            prHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)BEARER_VALUE.length);
                            prHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)BUFFER_SIZE_VALUE.length);
                            prHdlr.send();
                            
                            /* Declare service  add new service*/                              
                            prHdlr.init(PRO_CMD_DECLARE_SERVICE,(byte) 0x00, DEV_ID_TERMINAL);
                            prHdlr.appendTLV(TAG_SERVICE_RECORD, SERVICE_RECORD, (short)0, (short) SERVICE_RECORD.length);
                            prHdlr.send();
                            
                            firstTrigger = false;
                        }
                        
                        
                        
                    } catch (Exception e) {
                        bRes = false;
                    }
                    
                    if(bRes){
                        //get the Proactive Handler and the Proactive Response Handler
                        prHdlr = ProactiveHandlerSystem.getTheHandler();
                        //build a Proactive Command Display Text
                        prHdlr.initDisplayText((byte)0x80,(byte)0x04,TEXT,(short)0,(short)TEXT.length);
                        //send the command Display Text
                        if(prHdlr.send()==0){
                            //The ProactiveResponseHandler contains the terminal response
                            prRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
                            //the handler length should be 12
                            if(prRespHdlr.getLength()==(byte)12){
                                //No exception is thrown and the Proactive Response Handler remains unchanged
                                prHdlr.init(PRO_CMD_SELECT_ITEM,(byte)0x00,DEV_ID_TERMINAL);
                                prHdlr.appendTLV((byte)(TAG_ITEM_IDENTIFIER|TAG_SET_CR),(byte)0x01);
                                if(prHdlr.send()==0){
                                    prRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
                                    //The ProactiveResponseHandler contains the terminal response of the second proactive command
                                    //the handler length should be 15
                                    if(prRespHdlr.getLength()!=(byte)15){
                                        bRes=false;
                                    }
                                } else{
                                    bRes=false;
                                }
                            } else{
                                bRes=false;
                            }
                        } else{
                            bRes=false;
                        }
                    }
                    
                } catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome( bTestCaseNb, bRes );
        }
        
    }
}
