//-----------------------------------------------------------------------------
//    Cre_Apt_Epdw_3.java
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

 public class Cre_Apt_Epdw_3 extends TestToolkitApplet{

     private static byte testCaseNb = (byte) 0x00;
     private static byte cntCases = (byte)0x00;
     private byte[] TEXT= {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)' ',(byte)'1'};
     private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'3'};
     private boolean bRes;


    //Constructor of the applet


    public Cre_Apt_Epdw_3(){
        //
    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Epdw_3 thisApplet = new Cre_Apt_Epdw_3();

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
        switch(event){

            //event EVENT_PROFILE_DOWNLOAD
            case EVENT_PROFILE_DOWNLOAD:

                  switch(cntCases){
                    case (byte)0x00:
                          testCaseNb=(byte)0x01;
                          //create the table of results
                          reportTestOutcome(testCaseNb,bRes);
                    break;

                    case (byte)0x01:
                         testCaseNb=(byte)0x03;
                         bRes=true;
                         //create the table of results
                         reportTestOutcome(testCaseNb,bRes);
                  }
            break;

            case EVENT_MENU_SELECTION:
                testCaseNb=(byte)0x02;
                cntCases=(byte)0x01;
                try{
                    ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();
                    //build a Proactive Command Refresh
                    proHdlr.init(PRO_CMD_REFRESH,(byte)0x03,DEV_ID_TERMINAL);
                    //send the Refresh command
                    proHdlr.send();
                    //register to EVENT_PROFILE_DOWNLOAD
                    obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
                    bRes=true;
                }
                catch(Exception e){
                    bRes=false;
                }
                //create the table of results
                reportTestOutcome(testCaseNb,bRes);

        }


    }


 }