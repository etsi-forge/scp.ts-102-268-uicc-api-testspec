//-----------------------------------------------------------------------------
//    Cre_Apt_Estc_3.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_estc;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_STATUS_COMMAND
 *
 * Applet is triggered on Install (Install, all events
 *
 * @version 0.0.1 - 03/08/04
 *
 */

 public class Cre_Apt_Estc_3 extends TestToolkitApplet{

     private byte testCaseNb = (byte) 0x00;
     private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)' ',(byte)'1'};
     private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};
     private static byte cntCases = (byte)0x00;
     private boolean bRes;


    //Constructor of the applet

    public Cre_Apt_Estc_3(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Estc_3 thisApplet = new Cre_Apt_Estc_3();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        thisApplet.reportTestOutcome((byte)0x01,true);
    }


    //Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event){
        bRes=false;
        //event EVENT_MENU_SELECTION
        if(event==EVENT_MENU_SELECTION){
            //TEST CASE 1: A DISPLAY TEXT IS SENT AND THE APPLET IS REGISTERED TO THE EVENT
            testCaseNb=(byte)0x02;
            cntCases=(byte)0x01;
            try{
                //get the Proactive Handler
                ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();
                //build the proactive Command Display Text
                proHdlr.initDisplayText((byte)0x80,(byte)0x04,TEXT,(short)0x00,(short)TEXT.length);
                //send the command
                proHdlr.send();
                //register to STATUS_COMMAND
                obReg.requestPollInterval(POLL_SYSTEM_DURATION);
                bRes=true;
            }
            catch(Exception e){
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);

        }
        //event STATUS_COMMAND
        if(event==EVENT_STATUS_COMMAND)
        {
        switch(cntCases){
               case (byte)0x00:
                     testCaseNb=(byte)0x01;
                     reportTestOutcome(testCaseNb,bRes);
               break;
               case (byte)0x01:
                     testCaseNb=(byte)0x03;
                     bRes=true;
                    reportTestOutcome(testCaseNb,bRes);
            }

        }

    }


 }