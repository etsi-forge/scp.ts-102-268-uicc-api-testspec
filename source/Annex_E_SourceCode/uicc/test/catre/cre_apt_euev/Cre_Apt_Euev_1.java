//-----------------------------------------------------------------------------
//    Cre_Apt_Euev_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_euev;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_UNRECOGNIZED_ENVELOPE
 *
 *
 *
 *
 *
 */
public class Cre_Apt_Euev_1 extends TestToolkitApplet{

    private byte testCaseNb = (byte) 0x00;
    private static boolean bRes;
    private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};



    //Constructor of the applet

    public Cre_Apt_Euev_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Euev_1 thisApplet = new Cre_Apt_Euev_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to the UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        bRes=thisApplet.obReg.isEventSet(EVENT_UNRECOGNIZED_ENVELOPE);
    }


    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){


        if(event==EVENT_UNRECOGNIZED_ENVELOPE){

            switch(testCaseNb){

                case (byte)0x00:
                    testCaseNb=(byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                    //TEST CASE 2: THE APPLET IS UNREGISTERED TO THE EVENT
                    testCaseNb=(byte)0x02;
                    try{
                        //deregister to the EVENT_UNRECOGNIZED_ENVELOPE event
                        obReg.clearEvent(EVENT_UNRECOGNIZED_ENVELOPE);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
                    reportTestOutcome((byte)0x03,true);
               break;
               case (byte)0x02:
                    testCaseNb=(byte)0x03;
                    bRes=false;
                    reportTestOutcome(testCaseNb,bRes);
               break;
               case (byte)0x04:
                   //TEST CASE 4: THE APPLET IS TRIGGERED
                   testCaseNb=(byte)0x05;
                   bRes=true;
                   reportTestOutcome(testCaseNb,bRes);
            }
        }

        //event EVENT_MENU_SELECTION
        if(event==EVENT_MENU_SELECTION){
            //TEST CASE 3:THE APPLET IS REGISTERED TO THE EVENT
            testCaseNb=(byte)0x04;
            try{
                //register to the EVENT_UNRECOGNIZED_ENVELOPE
                obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
                bRes=true;
            }
            catch(Exception e){
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);


        }

    }

 }