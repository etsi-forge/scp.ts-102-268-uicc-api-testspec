//-----------------------------------------------------------------------------
//    Cre_Apt_Eade_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_eade;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_APPLICATION_DESELECT
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Eade_1 extends TestToolkitApplet{

     private static boolean bRes;
     private byte testCaseNb            = (byte) 0x00;
     private static byte[] menuEntry    ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};
     //private static byte[] ADF1AID      ={(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x09,(byte)0x00 ,(byte)0x05 ,(byte)0xFF ,(byte)0xFF ,(byte)0xFF ,(byte)0xFF ,(byte)0x89 ,(byte)0xE0 ,(byte)0x00 ,(byte)0x00 ,(byte)0x02};
     //private static byte[] ADF2AID      ={(byte)0xA0,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x09,(byte)0x00 ,(byte)0x05 ,(byte)0xFF ,(byte)0xFF ,(byte)0xFF ,(byte)0xFF ,(byte)0x89 ,(byte)0xD0 ,(byte)0x00 ,(byte)0x00 ,(byte)0x02};




    //Constructor of the applet
     public Cre_Apt_Eade_1(){

    }
    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){
        //Create a new applet instace
        Cre_Apt_Eade_1 thisApplet = new Cre_Apt_Eade_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT_APPLICATION_DESELECT
        thisApplet.obReg.setEvent(EVENT_APPLICATION_DESELECT);
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
        try{bRes=thisApplet.obReg.isEventSet(EVENT_APPLICATION_DESELECT);}
        catch(Exception e){bRes=false;}
    }

    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){
        UICCTestConstants UICCTestConstants_i= new UICCTestConstants();
        EnvelopeHandler envHndlr = EnvelopeHandlerSystem.getTheHandler();
        //event EVENT_APPLICATION_DESELECT
         if(event==EVENT_APPLICATION_DESELECT){
         //TEST CASE 1: 1 - METHOD isEventSet() RETURNS TRUE
         switch(testCaseNb){
             case (byte)0x00:
             testCaseNb=(byte)0x01;
             reportTestOutcome(testCaseNb,bRes);
         //TEST CASE 1:3 - THE ENVELOPE HANDLER CONTAINS THE AID OF ADF1
             testCaseNb=(byte)0x02;

             if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF1, (short)0)==0){
                 bRes=true;
             }
             else {bRes=false;}
             reportTestOutcome(testCaseNb,bRes);
             break;

          //TEST CASE 1:4 - APPLET IS TRIGGERED.
          //THE ENVELOPE HANDLER CONTAINS THE AID OF ADF2
             case (byte)0x02:
             testCaseNb=(byte)0x03;
             if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF2, (short)0)==0){
                 bRes=true;
             }
             else {bRes=false;}
             reportTestOutcome(testCaseNb,bRes);
             //TEST CASE 2: 1 - METHOD clearEvent() IS CALLED
             testCaseNb=(byte)0x04;
             try{
                 obReg.clearEvent(EVENT_APPLICATION_DESELECT);
                 bRes=true;
             }
             catch(Exception e){bRes=false;}
             reportTestOutcome(testCaseNb,bRes);
             testCaseNb=(byte)0x05;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             testCaseNb=(byte)0x06;
             bRes=true;
             reportTestOutcome(testCaseNb,bRes);
             testCaseNb=(byte)0x04;
             break;

             case (byte)0x04:
             if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF1, (short)0)==0){
                 testCaseNb=(byte)0x05;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             }
             else if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF2, (short)0)==0){
                 testCaseNb=(byte)0x06;
                 bRes=false;
                 testCaseNb=(byte)0x04;

             }
             else{
                 reportTestOutcome((byte)0x05,false);
                 reportTestOutcome((byte)0x06,false);
                 testCaseNb=(byte)0x05;
             }
             break;

             case (byte)0x05:
             if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF2, (short)0)==0){
                 testCaseNb=(byte)0x06;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             }
             else{
                 reportTestOutcome((byte)0x06,false);

             }
             testCaseNb=(byte)0x04;
             break;

             case (byte)0x08:
             testCaseNb=(byte)0x09;

             //TEST CASE 2:6 - THE ENVELOPE HANDLER CONTAINS THE AID OF ADF1
             if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF1, (short)0)==0){
                 bRes=true;
             }
             else {bRes=false;}
             reportTestOutcome(testCaseNb,bRes);
             break;

             //TEST CASE 2:7 - APPLET IS TRIGGERED THE ENVELOPE HANDLER CONTAINS THE AID OF ADF2
             case (byte)0x09:
             testCaseNb=(byte)0x0A;
             if(envHndlr.findAndCompareValue((byte)0x2F,UICCTestConstants_i.AID_ADF2, (short)0)==0){
                 bRes=true;
             }
             else {bRes=false;}
             reportTestOutcome(testCaseNb,bRes);

        }//end of switch
    }

    //event EVENT_MENU_SELECTION
    if(event==EVENT_MENU_SELECTION){
        switch(testCaseNb){
            //TEST CASE 2:4 - APPLET IS TRIGGERED
            case (byte)0x04:
            testCaseNb=(byte)0x07;
            bRes=true;
            reportTestOutcome(testCaseNb,bRes);
            testCaseNb=(byte)0x08;
            try{
                obReg.setEvent(EVENT_APPLICATION_DESELECT);
                bRes=true;
            }
            catch(Exception e){bRes=false;}
            reportTestOutcome(testCaseNb,bRes);
            break;

            //TEST CASE 2:4 - APPLET IS TRIGGERED. THIS CASE ONLY IF APPLET IS TRIGGERED WRONGLY
            case (byte)0x05:
            testCaseNb=(byte)0x07;
            bRes=true;
            reportTestOutcome(testCaseNb,bRes);
            testCaseNb=(byte)0x08;
            try{
                obReg.setEvent(EVENT_APPLICATION_DESELECT);
                bRes=true;
            }
            catch(Exception e){bRes=false;}
            reportTestOutcome(testCaseNb,bRes);
            break;

        }
   }
 }
}