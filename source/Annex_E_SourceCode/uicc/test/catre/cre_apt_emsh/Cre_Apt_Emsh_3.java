//-----------------------------------------------------------------------------
//    Cre_Apt_Emsh_3.java
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

 public class Cre_Apt_Emsh_3 extends TestToolkitApplet {

     private byte testCaseNb = (byte) 0x00;
     private static byte[] menuEntry3A = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'3',(byte)'A'};
     private static byte[] menuEntry3B = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'3',(byte)'B'};
     private static byte[] menuEntry3C = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'3',(byte)'C'};
     private static boolean bRes;


    // Constructor of the applet
    public Cre_Apt_Emsh_3() {


    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instace
        Cre_Apt_Emsh_3 thisApplet = new Cre_Apt_Emsh_3();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        // register to EVENT_MENU_SELECTION_HELP_REQUEST
        thisApplet.obReg.initMenuEntry(menuEntry3A,(short)0x00,(short)menuEntry3A.length,(byte)0x00,true,(byte)0x00,(short)0x00);
        thisApplet.obReg.initMenuEntry(menuEntry3B,(short)0x00,(short)menuEntry3B.length,(byte)0x00,true,(byte)0x00,(short)0x00);
        thisApplet.obReg.initMenuEntry(menuEntry3C,(short)0x00,(short)menuEntry3C.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        // Test Case 1: Check if the applet is registered to the event
        bRes=thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
    }


    // Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event) {

        if (event == EVENT_MENU_SELECTION_HELP_REQUEST) {

            switch(testCaseNb) {
                case (byte)0x00:
                    // Test Case 2: 2-The applet is triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier 05
                    testCaseNb= (byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x02;
                    //              3-Call disableMenuEntry() method for Item Identifier 05
                    try {
                        obReg.disableMenuEntry((byte)0x05);
                        bRes= true;
                    }
                    catch (ToolkitException e) {
                        bRes= false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x03;
                    break;

                case (byte)0x03:
                    //              4-The applet is triggered by EVENT_MENU_SELECTION_HELP_REQUEST with Item identifier 06
                    bRes= true;
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb = (byte) 0x04;
                    //              5-Call disableMenuEntry() method for Item Identifier 06
                    try {
                        obReg.disableMenuEntry((byte)0x06);
                        bRes= true;
                    }
                    catch (ToolkitException e) {
                        bRes= false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
                    break;
            }
        }
    }

 }