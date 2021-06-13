//-----------------------------------------------------------------------------
//    Cre_Hin_Enhd_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_enhd;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Hin_Enhd_1 extends TestToolkitApplet{


    private byte testCaseNb = (byte) 0x00;
    private boolean bRes;

    private static byte[]bufferCC={(byte)0x82,(byte)0x02,(byte)0x82,(byte)0x81,(byte)0x86,(byte)0x02,
                                   (byte)0x81,(byte)0x01,(byte)0x93,(byte)0x07,(byte)0x81,(byte)0xF2,
                                   (byte)0xA3,(byte)0x34,(byte)0x05,(byte)0x76,(byte)0x67};                                 
    private byte value;

    private static byte[] menuEntry1={(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'1'};
    private static byte[] menuEntry2={(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'2'};
    private byte[] TEXT = {(byte)'T',(byte)'E',(byte)'X',(byte)'T',(byte)' ',(byte)'1'};
    private static byte[] buffer1=new byte[(byte)0x7F];
    private static byte[] buffer2=new byte[(byte)0x7F];
        
    private boolean secondTrigger=false;


    private static short[] eventList ={(byte)12,(byte)13,(byte)14,(byte)15,(byte)16,(byte)17,(byte)18, (byte)20, (byte)21, (byte)22, (byte)23, (byte)25, (byte)26, (byte)28, (byte)29};

    private static byte[] ADDRESS_VALUE = {(byte)0x81, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
    private static byte[] BEARER_VALUE = {(byte)0x03, (byte)0x00};
    private static byte[] BUFFER_SIZE_VALUE = {(byte)0x00, (byte)0x0A};    
    private static byte[] SERVICE_RECORD = {(byte)0x00, (byte)0x00, (byte)0x00};     
        
    //External File Update TLV Values
    private static byte[] devIdentity = {(byte)0x82, (byte)0x81}; 
    //EF TARU
    private static byte[] baFileList = {(byte)0x01, (byte)0x3F, (byte)0x00, (byte)0x7F, (byte)0x4A, (byte)0x6F, (byte)0x03};
    //EF LARU
    private static byte[] baFileListADF = {(byte)0x01, (byte)0x3F, (byte)0x00, (byte)0x7F, (byte)0xFF, (byte)0x7F, (byte)0x4A, (byte)0x6F, (byte)0x0C};
    private static byte[] aid = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                 (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x02};
    private static byte[] aidNull = null;
    private static byte[] fileUpdateInfoTARU = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x03};
    private static byte[] fileUpdateInfoLARU = {(byte)0x00, (byte)0x01, (byte)0x00, (byte)0x04};
    private static byte serviceId = (byte) 0;
    private static byte a;
    
    /**
     * Constructor of the applet
     */
    public Cre_Hin_Enhd_1() {    
               
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Hin_Enhd_1 thisApplet = new Cre_Hin_Enhd_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);   
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);        
        //register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry1,(short)0,(short)menuEntry1.length,(byte)0,false,(byte)0,(short)0);
        //register to EVENT_MENU_SELECTION_HELP_REQUEST
        thisApplet.obReg.initMenuEntry(menuEntry2,(short)0,(short)menuEntry2.length,(byte)0,true,(byte)0,(short)0);
        //register to EVENT_TIMER_EXPIRATION      
        for(a=0; a<(byte)8; a++){
            thisApplet.obReg.allocateTimer();
        }
        for(a=2; a<(byte)9; a++){
            thisApplet.obReg.releaseTimer((byte)a);
        }  
        //register to all event download
        thisApplet.obReg.setEventList(eventList,(short)0,(short)eventList.length);
        //register to EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
        serviceId = thisApplet.obReg.allocateServiceIdentifier();
        SERVICE_RECORD[1]= (byte) serviceId;  
        //register to EVENT_EXTERNAL_FILE_UPDATE
        thisApplet.obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,(byte[]) baFileListADF,(short) 0, (short) baFileListADF.length, (byte[]) aid, (short) 0, (byte) aid.length);        
        thisApplet.obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,(byte[]) baFileList,(short) 0, (short) baFileList.length, (byte[]) aidNull, (short) 0, (byte) 0);
        
    }

    
    public void processToolkit(short event)
    {
        
        bRes=true;

        //the handler contents is stored in buffer 1
        EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
        
        //for each event a tlv is selected the first byte is stored
        if(!secondTrigger && bRes){

            //the handler contents is stored in buffer 1
            try{
                 envHdlr.copy(buffer1,(short)0,envHdlr.getLength());
            }
            catch(Exception e){
                bRes=false;
            }
            
            switch(event){

                //event EVENT_MENU_SELECTION_HELP_REQUEST
                case EVENT_MENU_SELECTION_HELP_REQUEST:
                    if(envHdlr.findTLV((byte)(TAG_HELP_REQUEST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }                                    
                    value = (byte)0x00;                    
                    if(envHdlr.getTag()!=(byte)0xD3){
                        bRes=false;                 
                    }
                    ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
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
                    
                break;

                //event EVENT_MENU_SELECTION
                case EVENT_MENU_SELECTION:
                    if(envHdlr.findTLV((byte)(TAG_ITEM_IDENTIFIER|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value = (byte)0x01;
                break;

                //event EVENT_TIMER_EXPIRATION
                case EVENT_TIMER_EXPIRATION:
                   if(envHdlr.findTLV((byte)(TAG_TIMER_IDENTIFIER|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                    bRes=false;
                   }
                   value = (byte)0x01;
                break;

                //event EVENT_CALL_CONTROL_BY_NAA
                case EVENT_CALL_CONTROL_BY_NAA:
                    if(envHdlr.findTLV((byte)(TAG_ADDRESS|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value = (byte) 0x81;
                break;

                //event EVENT_EVENT_DOWNLOAD_MT_CALL
                case EVENT_EVENT_DOWNLOAD_MT_CALL:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value =(byte)0x00;
                break;

                //event EVENT_EVENT_DOWNLOAD_CALL_CONNECTED
                case EVENT_EVENT_DOWNLOAD_CALL_CONNECTED:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value = (byte)0x01;
                break;

                //event EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED
               case EVENT_EVENT_DOWNLOAD_CALL_DISCONNECTED:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value =(byte)0x02;
                break;

                //event EVENT_EVENT_DOWNLOAD_LOCATION_STATUS
                case EVENT_EVENT_DOWNLOAD_LOCATION_STATUS:
                    if(envHdlr.findTLV((byte)(TAG_LOCATION_STATUS|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value =(byte)0x01;
                break;

                //event EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
                case EVENT_EVENT_DOWNLOAD_USER_ACTIVITY:
                    if(envHdlr.findTLV((byte)(TAG_DEVICE_IDENTITIES|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value =(byte)0x82;
                break;

                //event EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE
                case EVENT_EVENT_DOWNLOAD_IDLE_SCREEN_AVAILABLE:
                    if(envHdlr.findTLV((byte)(TAG_DEVICE_IDENTITIES|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value =(byte)0x02;
                break;

                //event EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
                case EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS:
                    if(envHdlr.findTLV((byte)(TAG_CARD_READER_STATUS|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0xFF;
                break;
                
                //event EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION
                case EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x07;
                break;

                //event EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION
                case EVENT_EVENT_DOWNLOAD_BROWSER_TERMINATION:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x08;
                break;
  
               //event EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE
               case EVENT_EVENT_DOWNLOAD_DATA_AVAILABLE:            
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x09;
               break;
                
               //event EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS
               case EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS:          
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x0A;
               break;

               //event EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE
               case EVENT_EVENT_DOWNLOAD_ACCESS_TECHNOLOGY_CHANGE:            
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x0B;
               break;

               //event EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED
               case EVENT_EVENT_DOWNLOAD_DISPLAY_PARAMETER_CHANGED:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x0C;
               break;

               //event EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
               case EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x0D;
               break;

               //event EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
               case EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE:
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x0E;
               break;

               //event EVENT_EVENT_BROWSING_STATUS
               case EVENT_EVENT_BROWSING_STATUS: 
                    if(envHdlr.findTLV((byte)(TAG_EVENT_LIST|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x0F;
               break;

               //event EVENT_EXTERNAL_FILE_UPDATE
               case EVENT_EXTERNAL_FILE_UPDATE:
                    if(envHdlr.findTLV((byte)(TAG_DEVICE_IDENTITIES|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                        bRes=false;
                    }
                    value=(byte)0x82;
               break;
               
 
             
                
             }

             if(bRes){
                //A Display text is sent
                 secondTrigger=true;
                 ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();                 
                 prHdlr.initDisplayText((byte)0x80,(byte)0x04,TEXT,(short)0,(short)TEXT.length);
                 prHdlr.send();
                 //the handler contents is stored in buffer 2
                 if(bRes)
                 {
                     if(event==EVENT_MENU_SELECTION_HELP_REQUEST){
                         //Copy the envelope contents in buffer 2
                         envHdlr.copy(buffer2,(short)0,envHdlr.getLength());
                         //the contents of buffer1 and buffer2 must be equals
                         if((byte)Util.arrayCompare(buffer1,(short)0,buffer2,(short)0,(short)envHdlr.getLength())==0){
                               bRes=true;
                         }
                         else{
                               bRes=false;
                         }

                     }
                     else{
                       try{
                                //the selected tlv is the same before the call control execution
                                if(envHdlr.getValueByte((short)0)==(byte)value){

                                    //Copy the envelope contents in buffer 2
                                    envHdlr.copy(buffer2,(short)0,envHdlr.getLength());
                                    //the contents of buffer1 and buffer2 must be equals
                                    if((byte)Util.arrayCompare(buffer1,(short)0,buffer2,(short)0,(short)envHdlr.getLength())==0){
                                        bRes=true;
                                    }
                                    else{
                                        bRes=false;
                                    }
                                }
                                else{
                                    bRes=false;
                                }
                                
                                if (event==EVENT_EXTERNAL_FILE_UPDATE){                                
                                    
                                    //Device Identity
                                    if(envHdlr.findAndCompareValue((byte) (TAG_DEVICE_IDENTITIES|TAG_SET_CR), 
                                    (byte[]) devIdentity, (short) 0)!=0){
                                        bRes=false;
                                    }
                                    //AID                   
                                    //if Tag AID does not exist the file belongs to the UICC
                                    if(envHdlr.findTLV((byte)(TAG_AID|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                                                    
                                        if(envHdlr.findAndCompareValue((byte) (TAG_FILE_LIST |TAG_SET_CR), 
                                        (byte[]) baFileList, (short) 0)!=0){
                                            bRes=false;
                                        }                
                                        //File Update Information. Transparent
                                        if(envHdlr.findAndCompareValue((byte) (0x3B|TAG_SET_CR), 
                                        (byte[]) fileUpdateInfoTARU, (short) 0)!=0){
                                            bRes=false;
                                        }       
                                    
                                    }else{ 
                                        //if exist Tag AID the file belongs to an ADF 
                                        if(envHdlr.compareValue((short) 0, (byte[]) aid, (short) 0, (short) aid.length)!=0){                                
                                            bRes=false;
                                        }                                           
                                                                      
                                        if(envHdlr.findAndCompareValue((byte) (TAG_FILE_LIST |TAG_SET_CR), 
                                        (byte[]) baFileListADF, (short) 0)!=0){
                                            bRes=false;
                                        }                                   
                                        //File Update Information. Linear fixed
                                        if(envHdlr.findAndCompareValue((byte) (0x3B|TAG_SET_CR), 
                                        (byte[]) fileUpdateInfoLARU, (short) 0)!=0){
                                            bRes=false;
                                        }                       
                                    }                                   
                                                                 
                                }

                         }
                         catch(ToolkitException e){
                            //if the event is an UNRECOGNIZED envelope no TLV is selected
                            if(event==EVENT_UNRECOGNIZED_ENVELOPE && e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT ){
                                bRes=true;
                            }
                            else{
                                bRes=false;
                            }
                        }
                        catch(Exception e){
                            bRes=false;
                        }
                 }
               }

             }
             testCaseNb++;
             //create the table of results
             reportTestOutcome(testCaseNb,bRes);

        }
        //The applet is triggered when a EVENT CALL CONTROL is sent
        else{
             secondTrigger=false;
             try{
                 EnvelopeHandler envHdlrCC = EnvelopeHandlerSystem.getTheHandler();

                 //the handler contents is stored
                 envHdlrCC.copy(buffer2,(short)0,(short)envHdlrCC.getLength());

                 //is checked that the envelope is a call control
             if(Util.arrayCompare(buffer2,(short)0x00,bufferCC,(short)0x00,(short)envHdlrCC.getLength())!=0){
                    bRes=false;
                 }
                 //the device identities TLV is selected
                 if(envHdlrCC.findTLV((byte)(TAG_DEVICE_IDENTITIES|TAG_SET_CR),(byte)1)==TLV_NOT_FOUND){
                     bRes=false;
                 }
             }
         catch(Exception e){
             bRes=false;
         }
        }
   }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
