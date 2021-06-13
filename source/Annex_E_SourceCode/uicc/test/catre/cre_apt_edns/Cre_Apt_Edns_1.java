//-----------------------------------------------------------------------------
//    Cre_Apt_Edns_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_edns;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Edns_1 extends TestToolkitApplet{

     private static boolean bRes;
     private byte testCaseNb            = (byte) 0x00;
     private static byte[] menuEntry    ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'1'};

    //Constructor of the applet
     public Cre_Apt_Edns_1(){

    }
    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){
        //Create a new applet instace
        Cre_Apt_Edns_1 thisApplet = new Cre_Apt_Edns_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE);
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
        try{bRes=thisApplet.obReg.isEventSet(EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE);}
        catch(Exception e){bRes=false;}
    }

    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){
        EnvelopeHandler envHndlr = EnvelopeHandlerSystem.getTheHandler();
        //event EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE
         if(event==EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE){
         //TEST CASE 1: 1 - METHOD isEventSet() RETURNS TRUE
         switch(testCaseNb){
             case (byte)0x00:
                 testCaseNb=(byte)0x01;
                 reportTestOutcome(testCaseNb,bRes);
        //TEST CASE 1:2 - Applet is triggered
                 testCaseNb=(byte)0x02;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);

       //TEST CASE 2: METHOD clearEvent() IS CALLED
                 testCaseNb=(byte)0x03;
                 try{
                     obReg.clearEvent(EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE);
                     bRes=true;
                 }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
       //TEST CASE 2: 1 - APPLET IS NOT TRIGGERED
                 testCaseNb=(byte)0x04;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
       //TEST CASE 2: 1 - IF APPLET IS TRIGGERED WRONGLY
             case (byte)0x04:
                 testCaseNb=(byte)0x04;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             break;
       //TEST CASE 2: 3 - APPLET IS TRIGGERED
             case (byte)0x06:
                testCaseNb=(byte)0x07;
                bRes=true;
                reportTestOutcome(testCaseNb,bRes);
             break;
         }//end of switch
    }

    //event EVENT_MENU_SELECTION
    if(event==EVENT_MENU_SELECTION){
        switch(testCaseNb){
            //TEST CASE 2:2 - APPLET IS TRIGGERED
            case (byte)0x04:
            testCaseNb=(byte)0x05;
            bRes=true;
            reportTestOutcome(testCaseNb,bRes);
            //setEvent() doesn't throws any exception
            testCaseNb=(byte)0x06;
            try{
                obReg.setEvent(EVENT_EVENT_DOWNLOAD_NETWORK_SEARCH_MODE_CHANGE);
                bRes=true;
            }
            catch(Exception e){bRes=false;}
            reportTestOutcome(testCaseNb,bRes);
            break;
        }
    }
  }
}