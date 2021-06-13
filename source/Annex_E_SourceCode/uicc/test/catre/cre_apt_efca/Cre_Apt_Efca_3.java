//-----------------------------------------------------------------------------
//    Cre_Apt_Efca_3.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_efca;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_FIRST_COMMAND_AFTER_ATR
 *
 *
 *
 *
 */

 public class Cre_Apt_Efca_3 extends TestToolkitApplet {

     private byte testCaseNb = (byte) 0x00;
     private static boolean bRes;
     private byte nbCases =(byte)0x00;
     private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'3'};
     protected static byte FirstBetweenApp3AndApp4=0;


    // Constructor of the applet
    public Cre_Apt_Efca_3() {

    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Apt_Efca_3 thisApplet = new Cre_Apt_Efca_3();
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
    }


    // Method called by the UICC CAT Runtime Environment
    public void processToolkit(short event) {

        bRes=false;

        switch(nbCases) {
            case (byte)0x00:
                // Test Case 1: The applet is triggered by EVENT_MENU_SELECTION
                testCaseNb=(byte)0x01;
                if (event==EVENT_MENU_SELECTION) {
                    try{
                        //The applet is registered to the EVENT_FIRST_COMMAND_AFTER_ATR
                        obReg.setEvent(EVENT_FIRST_COMMAND_AFTER_ATR);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                }
                reportTestOutcome(testCaseNb,bRes);
                bRes=false;
                nbCases++;
                break;

            case (byte)0x01:
                // Test Case 2: the applet is triggered by EVENT_FIRST_COMMAND_AFTER_ATR
                testCaseNb=(byte)0x02;
                if (event==EVENT_FIRST_COMMAND_AFTER_ATR) {
                    try{
                        //The applet is registered to the event
                        obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                }
                reportTestOutcome(testCaseNb,bRes);
                bRes=false;
                nbCases++;
                break;

            case (byte)0x02:
                // Test Case 2: the applet is triggered by EVENT_PROFILE_DOWNLOAD
                testCaseNb=(byte)0x03;
                if (event==EVENT_PROFILE_DOWNLOAD) {
                    bRes=true;
                } else {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb,bRes);
                bRes=false;
                nbCases++;
                break;


            case (byte)0x03:
                // Test Case 3, 1st result: the applet is triggered by EVENT_FIRST_COMMAND_AFTER_ATR
                testCaseNb=(byte)0x04;
                if ((event==EVENT_FIRST_COMMAND_AFTER_ATR) && (FirstBetweenApp3AndApp4 == 1)) {
                    bRes=true;
                    FirstBetweenApp3AndApp4 = 2;
                }
                reportTestOutcome(testCaseNb,bRes);
                bRes=false;
                nbCases++;
                break;

            case (byte)0x04:
                // Test Case 3, 2nd result: the applet is triggered by EVENT_PROFILE_DOWNLOAD
                testCaseNb=(byte)0x05;
                if ((event==EVENT_PROFILE_DOWNLOAD) && (FirstBetweenApp3AndApp4 == 3)) {
                    bRes=true;

                }
                reportTestOutcome(testCaseNb,bRes);
                break;

        }
    }

 }