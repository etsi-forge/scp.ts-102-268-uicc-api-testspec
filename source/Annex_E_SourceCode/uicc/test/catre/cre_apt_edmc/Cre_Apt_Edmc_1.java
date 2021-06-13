//-----------------------------------------------------------------------------
//    Cre_Apt_Edmc_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_edmc;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;



/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_EVENT_DOWNLOAD_MT_CALL
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Edmc_1 extends TestToolkitApplet{

     private byte testCaseNb = (byte) 0x00;
     private static boolean bRes;
     private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};

     //private static ToolkitRegistry obReg;


    //Constructor of the applet

    public Cre_Apt_Edmc_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Edmc_1 thisApplet = new Cre_Apt_Edmc_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT DOWNLOAD MT CALL
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
        //Checks if the applet is registered to the event
        bRes=thisApplet.obReg.isEventSet(EVENT_EVENT_DOWNLOAD_MT_CALL);
    }


    //Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event){



        if(event==EVENT_EVENT_DOWNLOAD_MT_CALL){

            switch(testCaseNb){

                case (byte)0x00:
                    //TEST CASE 1: Store the result of the test if the applet is registered to the event
                    testCaseNb=(byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                    bRes=false;
                    //TEST CASE 2: The applet has been triggered and it's deregistered to the event
                    testCaseNb=(byte)0x02;
                    try{
                        obReg.clearEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
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
                   //if the applet is triggered the the test is falled
                   testCaseNb=(byte)0x03;
                   bRes=false;
                   reportTestOutcome(testCaseNb,bRes);
               break;
               case (byte)0x04:
                   //TEST CASE 5: The applet is triggered
                   testCaseNb=(byte)0x05;
                   bRes=true;
                   reportTestOutcome(testCaseNb,bRes);
            }
        }

        if(event==EVENT_MENU_SELECTION){
            //TEST CASE 4: The applet is registered to the Event Download MT Call event
            testCaseNb=(byte)0x04;
            try{
                obReg.setEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
                bRes=true;
            }
            catch(Exception e){
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);


        }

    }

 }