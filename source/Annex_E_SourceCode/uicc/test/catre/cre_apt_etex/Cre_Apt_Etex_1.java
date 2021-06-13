//-----------------------------------------------------------------------------
//    Cre_Apt_Etex_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_etex;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_TIMER_EXPIRATION
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Etex_1 extends TestToolkitApplet{

     private byte testCaseNb = (byte) 0x00;
     private static boolean bRes;
     private static byte a;
     private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};

    //Constructor of the applet

    public Cre_Apt_Etex_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Etex_1 thisApplet = new Cre_Apt_Etex_1();
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT TIMER EXPIRATION
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
                //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        //Checks if the applet is registered to the event
        bRes=thisApplet.obReg.isEventSet(EVENT_TIMER_EXPIRATION);

    }


    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){


        if(event==EVENT_TIMER_EXPIRATION){

            switch(testCaseNb){

                case (byte)0x00:
                    //TEST CASE 1: Store the result of the test if the applet is registered to the event
                    testCaseNb=(byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                    bRes=false;
                    //TEST CASE 2: The applet has been triggered and it's deregistered to the event
                    testCaseNb=(byte)0x02;
                    try{
                        obReg.releaseTimer((byte)0x01);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
                    reportTestOutcome((byte)0x03,true);
                break;
               case (byte)0x02:
           //TEST CASE 3: The applet must not be triggered in this case
           //if the applet is triggered then the test is failed
                   testCaseNb=(byte)0x03;
                   bRes=false;
                   reportTestOutcome(testCaseNb,bRes);
               break;
               case (byte)0x04:
                   //TEST CASE 5: The applet is triggered after the registration to the event
                   testCaseNb=(byte)0x05;
                   bRes=true;
                   reportTestOutcome(testCaseNb,bRes);
            }
        }

        if(event==EVENT_MENU_SELECTION){
            //TEST CASE 4: The applet is registered to the Timer Expiration event
            testCaseNb=(byte)0x04;
            try{
                for(a=0; a<(byte)8; a++){
                obReg.allocateTimer();
            }
            for(a=2; a<(byte)9; a++){
                obReg.releaseTimer((byte)a);
            }
                bRes=true;
            }
            catch(Exception e){
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);


        }

    }

 }