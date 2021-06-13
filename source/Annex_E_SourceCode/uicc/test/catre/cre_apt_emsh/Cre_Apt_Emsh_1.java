//-----------------------------------------------------------------------------
//    Cre_Apt_Emsh_1.java
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
 *
 */

 public class Cre_Apt_Emsh_1 extends TestToolkitApplet {

     private byte testCaseNb = (byte) 0x00;
     private static byte[] menuEntry1A = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'1',(byte)'A'};
     private static byte[] menuEntry1B = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'1',(byte)'B'};
     private static boolean bRes;


    // Constructor of the applet
    public Cre_Apt_Emsh_1() {

    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instace
        Cre_Apt_Emsh_1 thisApplet = new Cre_Apt_Emsh_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        // register to EVENT_MENU_SELECTION_HELP_REQUEST
        thisApplet.obReg.initMenuEntry(menuEntry1A,(short)0x00,(short)menuEntry1A.length,(byte)0x00,true,(byte)0x00,(short)0x00);
        // register to EVENT_MENU_SELECTION (Help request is not supported)
        thisApplet.obReg.initMenuEntry(menuEntry1B,(short)0x00,(short)menuEntry1B.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        // Test Case 1: Check if the applet is registered to the event
        bRes=thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
    }


    // Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event) {

        if (event == EVENT_MENU_SELECTION_HELP_REQUEST) {

            switch(testCaseNb) {
                case (byte)0x00:
                    // Test Case 1: 1-The applet is registered to EVENT_MENU_SELECTION_HELP_REQUEST
                    testCaseNb= (byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                    //              3-The applet is triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier 01
                    testCaseNb = (byte) 0x02;
                    bRes= true;
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x03;
                    //              4-The applet is not triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier 02
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x04;
                    break;
                case (byte)0x04:
                    //              4-The applet shall not triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier different from 01
                    bRes= false;
                    testCaseNb = (byte) 0x03;
                    reportTestOutcome(testCaseNb,bRes);
                    break;
            }
        }
    }

 }