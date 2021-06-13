//-----------------------------------------------------------------------------
//    Cre_Apt_Edlg_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_edlc;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_EVENT_DOWNLOAD_LOCAL CONNECTION
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Edlc_1 extends TestToolkitApplet{

     private static boolean bRes;
     private byte testCaseNb            = (byte) 0x00;
     private static byte servId         = (byte) 0x00;

     private static byte[] menuEntry    ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};
     private static byte[] serviceRecord={(byte)0x00,(byte)0x00,(byte)0x00};


    //Constructor of the applet
     public Cre_Apt_Edlc_1(){

    }
    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){
        //Create a new applet instace
        Cre_Apt_Edlc_1 thisApplet = new Cre_Apt_Edlc_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT_EVENT_DOWNLOAD_LOCAL CONNECTION
        for (byte sId=0; sId < 8; sId++)
        {
            thisApplet.obReg.allocateServiceIdentifier();
        }
        for (byte sId=1; sId < 8; sId++)
        {
            thisApplet.obReg.releaseServiceIdentifier(sId);
        }
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
    }

    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){



        //event EVENT_MENU_SELECTION
         if(event==EVENT_MENU_SELECTION){
         //TEST CASE 1: 1 - Applet is triggered
         switch(testCaseNb){
             case (byte)0x00:
             testCaseNb=(byte)0x01;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 1:2 - Method returns true
             testCaseNb=(byte)0x02;
             try{
                //check if registered to the event EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION
                bRes=obReg.isEventSet(EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION);
             }
             catch(Exception e){
                     bRes=false;
                     }
             reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 1:3 - Applet is not triggered
             testCaseNb=(byte)0x03;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             break;
             case (byte)0x03:
          //TEST CASE 1:4 - Applet is triggered by the envelope menu selection
             testCaseNb=(byte)0x04;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             try{
          //TEST CASE 1:5 - Declare service (add) proactive command
                testCaseNb=(byte)0x05;
                ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                prHdlr.init(PRO_CMD_DECLARE_SERVICE,(byte)0x00,(byte)0x82);
                prHdlr.appendTLV(TAG_SERVICE_RECORD,serviceRecord,(short) 0,(short) serviceRecord.length);
                prHdlr.send();
                bRes=true;
             }
             catch(Exception e){
                bRes=false;
             }
             reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 1:6 - Applet is not triggered
             testCaseNb=(byte)0x06;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             break;
             case(byte)0x06:
          //TEST CASE 1:7 - Applet is triggered by the envelope menu selection
             testCaseNb=(byte)0x07;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             try{
          //TEST CASE 1:8 - Declare service (add) proactive command
                testCaseNb=(byte)0x08;
                ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                prHdlr.init(PRO_CMD_DECLARE_SERVICE,(byte)0x00,(byte)0x82);
                prHdlr.appendTLV(TAG_SERVICE_RECORD,serviceRecord,(short) 0,(short) serviceRecord.length);
                prHdlr.send();
                bRes=true;
             }
             catch(Exception e){
                bRes=false;
             }
             reportTestOutcome(testCaseNb,bRes);
             break;
             case(byte)0x09:
          //TEST CASE 3:1 - Applet is triggered
                testCaseNb=(byte)0x0A;
                bRes=true;
                reportTestOutcome(testCaseNb,bRes);
                try{
          //TEST CASE 3:2 - Declare service (delete) proactive command
                    testCaseNb=(byte)0x0B;
                    ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                    prHdlr.init(PRO_CMD_DECLARE_SERVICE,(byte)0x01,(byte)0x82);
                    prHdlr.appendTLV(TAG_SERVICE_RECORD,serviceRecord,(short)0,(short)serviceRecord.length);
                    prHdlr.send();
                    bRes=true;
                }
             catch(Exception e){
                bRes=false;
             }
             reportTestOutcome(testCaseNb,bRes);
             break;
             case (byte)0x0E:
          //TEST CASE 5:1 - Applet is triggered
             testCaseNb=(byte)0x0F;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             try{
          //TEST CASE 5:2 - Declare service (add) proactive command
                testCaseNb=(byte)0x10;
                ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                prHdlr.init(PRO_CMD_DECLARE_SERVICE,(byte)0x00,(byte)0x82);
                prHdlr.appendTLV(TAG_SERVICE_RECORD,serviceRecord,(short)0,(short)serviceRecord.length);
                prHdlr.send();
                bRes=true;
              }
              catch(Exception e){
                bRes=false;
                }
             reportTestOutcome(testCaseNb,bRes);
             break;
         }
    }

    //event EVENT_EVENT_DOWNLOAD_LOCAL CONNECTION
    if(event==EVENT_EVENT_DOWNLOAD_LOCAL_CONNECTION){
        switch(testCaseNb){
            case (byte)0x03:
            bRes=false;
            reportTestOutcome(testCaseNb,bRes);
            testCaseNb=(byte)0x03;
            break;
            case (byte)0x06:
            bRes=false;
            reportTestOutcome(testCaseNb,bRes);
            testCaseNb=(byte)0x06;
            break;
            case (byte)0x08:
        //TEST CASE 2:1 - Applet is triggered
            testCaseNb=(byte)0x09;
            bRes=true;
            reportTestOutcome(testCaseNb,bRes);
            break;
            case (byte)0x0B:
        //TEST CASE 3:3 - Applet is triggered
            testCaseNb=(byte)0x0C;
            bRes=true;
            reportTestOutcome(testCaseNb,bRes);
            try{
        //TEST CASE 3:4 - Declare service (delete) proactive command
                testCaseNb=(byte)0x0D;
                ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                prHdlr.init(PRO_CMD_DECLARE_SERVICE,(byte)0x01,(byte)0x82);
                prHdlr.appendTLV(TAG_SERVICE_RECORD,serviceRecord,(short)0,(short)serviceRecord.length);
                prHdlr.send();
                bRes=true;
                     }
              catch(Exception e){
                bRes=false;
                }
            reportTestOutcome(testCaseNb,bRes);
        //TEST CASE 4:1 - Applet is not triggered
            testCaseNb=(byte)0x0E;
            bRes=true;
            reportTestOutcome(testCaseNb,bRes);
            break;
            case (byte)0x0E:
                testCaseNb=(byte)0x0E;
                bRes=false;
                reportTestOutcome(testCaseNb,bRes);
            break;
            case (byte)0x10:
         //TEST CASE 5:3 - Applet is triggered
                testCaseNb=(byte)0x11;
                bRes=true;
                reportTestOutcome(testCaseNb,bRes);
         //TEST CASE 5:5 - Applet is not triggered
                testCaseNb=(byte)0x12;
                bRes=true;
                reportTestOutcome(testCaseNb,bRes);
            break;
            case (byte)0x12:
                testCaseNb=(byte)0x12;
                bRes=false;
                reportTestOutcome(testCaseNb,bRes);
            break;
        }

      }
    }
 }