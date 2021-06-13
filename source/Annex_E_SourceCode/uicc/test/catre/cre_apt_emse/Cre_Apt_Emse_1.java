//-----------------------------------------------------------------------------
//    Cre_Apt_Emse_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_emse;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_MENU_SELECTION
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Emse_1 extends TestToolkitApplet{

     private static byte testCaseNb = (byte) 0x00;
     private static byte[] menuEntry1 ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};
     private static boolean bRes;
     //private static ToolkitRegistry obReg;

    //Constructor of the applet

    public Cre_Apt_Emse_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Emse_1 thisApplet = new Cre_Apt_Emse_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry1,(short)0x00,(short)menuEntry1.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        bRes= thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION);
    }


    //Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event){


        switch(testCaseNb){
            case (byte)0x00:
                //TEST CASE 1: THE APPLET IS REGISTERED TO EVENT_MENU_SELECTION EVENT
            testCaseNb=(byte)0x01;
            reportTestOutcome(testCaseNb,bRes);
            //TESTCASE 2: THE APPLET IS TRIGGERED
                bRes=false;
                testCaseNb=(byte)0x02;
                bRes=true;
                reportTestOutcome(testCaseNb,bRes);
                reportTestOutcome((byte)0x03,bRes);
            break;

            case (byte)0x02:
                bRes=false;
                testCaseNb=(byte)0x03;
                reportTestOutcome(testCaseNb,bRes);
        }

    }


 }