//-----------------------------------------------------------------------------
//    Cre_Apt_Efca_4.java
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

 public class Cre_Apt_Efca_4 extends TestToolkitApplet {

     private byte testCaseNb = (byte) 0x00;
     private static boolean bRes;
     private byte nbCases =(byte)0x00;


    // Constructor of the applet
    public Cre_Apt_Efca_4() {

    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Apt_Efca_4 thisApplet = new Cre_Apt_Efca_4();
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        // Register to EVENT_FIRST_COMMAND_AFTER_ATR
        thisApplet.obReg.setEvent(EVENT_FIRST_COMMAND_AFTER_ATR);
        // Register to EVENT_PROFILE_DOWNLOAD
        thisApplet.obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
    }


    // Method called by the UICC CAT Runtime Environment
    public void processToolkit(short event) {

        bRes=false;

        switch(nbCases) {
            case (byte)0x00:
                // Test Case 3, 1st result: the applet is triggered by EVENT_FIRST_COMMAND_AFTER_ATR
                testCaseNb = (byte) 0x01;
                if ((event==EVENT_FIRST_COMMAND_AFTER_ATR) && (Cre_Apt_Efca_3.FirstBetweenApp3AndApp4 == 0)) {
                    bRes=true;
                    Cre_Apt_Efca_3.FirstBetweenApp3AndApp4 = 1;
                }
                reportTestOutcome(testCaseNb,bRes);
                bRes=false;
                nbCases++;
                break;

            case (byte)0x01:
                // Test Case 4, 2nd result: the applet is triggered by EVENT_PROFILE_DOWNLOAD
                testCaseNb = (byte) 0x02;
                if ((event==EVENT_PROFILE_DOWNLOAD) && (Cre_Apt_Efca_3.FirstBetweenApp3AndApp4 == 2)) {
                    bRes=true;
                    Cre_Apt_Efca_3.FirstBetweenApp3AndApp4 = 3;
                }
                reportTestOutcome(testCaseNb,bRes);
                break;

        }
    }

 }