//-----------------------------------------------------------------------------
//    Cre_Apt_Genb_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_genb;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering General behaviour
 *
 *
 *
 *
 *
 */
public class Cre_Apt_Genb_1 extends TestToolkitApplet{

    private byte testCaseNb = (byte) 0x00;
    private static byte nIndex     = (byte) 0x00;
    private static boolean bRes;
    private static boolean bRes1=true;

    //Constructor of the applet

    public Cre_Apt_Genb_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Genb_1 thisApplet = new Cre_Apt_Genb_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to the EVENT_EVENT_DOWNLOAD_USER_ACTIVITY
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
        bRes=true;

        nIndex=(byte)0x01;
       }


    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){


        if(event==EVENT_EVENT_DOWNLOAD_USER_ACTIVITY){
                switch (nIndex){

                        case(byte)0x01:
                        nIndex=(byte)0x01;
                        bRes1=false;
                        break;

                        case(byte)0x02:
                        testCaseNb=(byte)0x02;
                        bRes=true;
                        reportTestOutcome(testCaseNb,bRes);                        
                        nIndex=(byte)0x03;
                        testCaseNb=(byte)0x03;
                        reportTestOutcome(testCaseNb,bRes);
                        break;

                        case(byte)0x03:
                        testCaseNb=(byte)0x03;
                        bRes=false;
                        reportTestOutcome(testCaseNb,bRes);
                        nIndex=(byte)0x03;
                        break;

                        case(byte)0x04:
                        testCaseNb=(byte)0x04;
                        bRes=true;                        
                        nIndex=(byte)0x04;
                        reportTestOutcome((byte)0x01,bRes1);
                        reportTestOutcome(testCaseNb,bRes);
                        break;

                        }
        }
    }

    public void process(APDU apdu) {
            if (selectingApplet()) {
                /* update the counter */
                super.process(apdu);
                nIndex++;
            }
            else {
              ISOException.throwIt(javacard.framework.ISO7816.SW_INS_NOT_SUPPORTED);
            }

 }
}