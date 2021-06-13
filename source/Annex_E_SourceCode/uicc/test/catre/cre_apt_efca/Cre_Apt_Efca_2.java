//-----------------------------------------------------------------------------
//    Cre_Apt_Efca_2.java
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

 public class Cre_Apt_Efca_2 extends TestToolkitApplet {

     private byte testCaseNb = (byte) 0x00;
     private static boolean bRes;
     private byte nbCases =(byte)0x00;

    // Constructor of the applet
    public Cre_Apt_Efca_2() {

    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Apt_Efca_2 thisApplet = new Cre_Apt_Efca_2();
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        // Register to EVENT_PROFILE_DOWNLOAD
        thisApplet.obReg.setEvent(EVENT_PROFILE_DOWNLOAD);
    }


    // Method called by the UICC CAT Runtime Environment
    public void processToolkit(short event) {

        bRes=false;

        if(event==EVENT_PROFILE_DOWNLOAD) {

            switch(nbCases) {
                case (byte)0x00:
                    // Test Case 1: The applet is triggered by EVENT_PROFILE_DOWNLOAD
                    testCaseNb=(byte)0x01;
                    if (Cre_Apt_Efca_1.FirstBetweenApp1AndApp2 == 1) {
                        try{
                            //The applet is deregistered to the event
                            obReg.clearEvent(EVENT_PROFILE_DOWNLOAD);
                            bRes=true;
                        }
                        catch(Exception e){
                            bRes=false;
                        }
                    }
                    reportTestOutcome(testCaseNb,bRes);
                    reportTestOutcome((byte)0x02,true);
                    reportTestOutcome((byte)0x03,true);
                    nbCases++;
                    break;

                case (byte)0x01:
                    // Test Case 2: the applet shall not be triggered
                    testCaseNb=(byte)0x02;
                    bRes=false;
                    reportTestOutcome(testCaseNb,bRes);
                    nbCases++;
                    break;

                case (byte)0x02:
                    // Test Case 3: the applet shall not be triggered
                    testCaseNb=(byte)0x03;
                    bRes=false;
                    reportTestOutcome(testCaseNb,bRes);
                    break;
            }
        }
    }

 }