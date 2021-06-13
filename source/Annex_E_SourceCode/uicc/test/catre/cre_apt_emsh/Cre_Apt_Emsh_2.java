//-----------------------------------------------------------------------------
//    Cre_Apt_Emsh_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_emsh;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_MENU_SELECTION_HELP_REQUEST
 *
 *
 *
 */

 public class Cre_Apt_Emsh_2 extends TestToolkitApplet {

     private static byte testCaseNb = (byte) 0x00;
     private static byte[] menuEntry2A ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'2',(byte)'A'};
     private static byte[] menuEntry2B ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'2',(byte)'B'};
     private static boolean bRes;

    // Constructor of the applet
    public Cre_Apt_Emsh_2() {

    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Cre_Apt_Emsh_2 thisApplet = new Cre_Apt_Emsh_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // register to EVENT_MENU_SELECTION_HELP_REQUEST
        thisApplet.obReg.initMenuEntry(menuEntry2A,(short)0x00,(short)menuEntry2A.length,(byte)0x00,true,(byte)0x00,(short)0x00);
        thisApplet.obReg.initMenuEntry(menuEntry2B,(short)0x00,(short)menuEntry2B.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        // Test Case 2: Check if the applet is registered to the event
        bRes=thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
    }


    // Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event) {

        if (event == EVENT_MENU_SELECTION_HELP_REQUEST) {

            switch(testCaseNb) {
                case (byte)0x00:
                    // Test Case 1: 1-The applet is registered to EVENT_MENU_SELECTION_HELP_REQUEST
                    testCaseNb=(byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                    //              5-The applet is triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier 03
                    testCaseNb = (byte) 0x02;
                    bRes=true;
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x03;
                    //              6-The applet is not triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier 04
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x04;
                    break;
                case (byte)0x04:
                    //              6-The applet shall not triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier different from 03
                    bRes= false;
                    testCaseNb = (byte) 0x03;
                    reportTestOutcome(testCaseNb,bRes);
                    break;

            }
        }
    }
 }