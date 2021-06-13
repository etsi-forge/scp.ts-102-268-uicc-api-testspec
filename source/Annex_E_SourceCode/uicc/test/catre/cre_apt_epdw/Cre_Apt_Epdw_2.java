//-----------------------------------------------------------------------------
//    Cre_Apt_Epdw_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_epdw;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_PROFILE_DOWNLOAD
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Epdw_2 extends TestToolkitApplet{

     private byte testCaseNb = (byte) 0x00;
     private boolean bRes;


    //Constructor of the applet

    public Cre_Apt_Epdw_2(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Epdw_2 thisApplet = new Cre_Apt_Epdw_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT PROFILE DOWNLOAD
        thisApplet.obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
    }


    //Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event){

      if(event==EVENT_PROFILE_DOWNLOAD){
        bRes=false;
        switch(testCaseNb){

            case (byte)0x00:
                //TEST CASE 1: THE APPLET IS TRIGGERED
                testCaseNb=(byte)0x01;
                bRes=true;
                //create the table of results
                reportTestOutcome(testCaseNb,bRes);
           break;

           case (byte)0x01:

               //TEST CASE 2: THE APPLET IS UNREGISTERED TO THE EVENT
               testCaseNb=(byte)0x02;
               try{
                    obReg.clearEvent(EVENT_PROFILE_DOWNLOAD);
                    bRes=true;
               }
               catch(Exception e){
                    bRes=false;
               }
               //create the table of results
               reportTestOutcome(testCaseNb,bRes);
               //This case is only to put 0xCC in the test case 3.
               reportTestOutcome((byte)0x03,true);

           break;

           case (byte)0x02:
                //if the applet is triggered, wrongly, then 0x00 is stored in the test case 3
                testCaseNb=(byte)0x03;
                //create the table of results
                reportTestOutcome(testCaseNb,false);
        }
      }
    }

 }