//-----------------------------------------------------------------------------
//    Cre_Apt_Efca_5.java
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

 public class Cre_Apt_Efca_5 extends TestToolkitApplet {

    private byte testCaseNb = (byte) 0x00;
    private static boolean bRes;
    private byte nbCases =(byte)0x00;
    private static byte result = (byte)0x00;

    private static byte [] Menu1 =
                        {   (byte) 'M',
                            (byte) 'e',
                            (byte) 'n',
                            (byte) 'u',
                            (byte) '1'};
    private static byte [] Menu2 =
                        {   (byte) 'M',
                            (byte) 'e',
                            (byte) 'n',
                            (byte) 'u',
                            (byte) '2'};


    // Constructor of the applet
    public Cre_Apt_Efca_5() {

    }


    // Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Apt_Efca_5 thisApplet = new Cre_Apt_Efca_5();
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        // Register to EVENT_FIRST_COMMAND_AFTER_SELECT
        thisApplet.obReg.setEvent(EVENT_FIRST_COMMAND_AFTER_ATR);
        result = thisApplet.obReg.initMenuEntry(Menu1, (short) 0, (short) 5, (byte) 0, false, (byte) 0, (short) 0);
        result = thisApplet.obReg.initMenuEntry(Menu2, (short) 0, (short) 5, (byte) 0, false, (byte) 0, (short) 0);
    }


    // Method called by the UICC CAT Runtime Environment
    public void processToolkit(short event) {

        bRes=false;
        testCaseNb=(byte)0x01;
        if(event==EVENT_FIRST_COMMAND_AFTER_ATR) {
            /* Test Case 1: The applet is triggered by EVENT_FIRST_COMMAND_AFTER_ATR
            *               Applet disables a menu entry
            */
            try{
                //The Menu2 is disabled
                obReg.disableMenuEntry(result);
                bRes=true;
            }
            catch(Exception e){
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);
        } else {
            reportTestOutcome(testCaseNb,bRes);
        }
    }
 }