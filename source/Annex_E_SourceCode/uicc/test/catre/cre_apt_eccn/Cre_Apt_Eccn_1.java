//-----------------------------------------------------------------------------
//    Cre_Apt_Eccn_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_eccn;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: CAT Runtime Environment Applet Triggering EVENT_CALL_CONTROL_BY_NAA
 *
 *
 *
 *
 *
 */

public class Cre_Apt_Eccn_1 extends TestToolkitApplet{
    private byte testCaseNb = (byte) 0x00;
    private boolean bRes=false;

    //Constructor of the applet

    public Cre_Apt_Eccn_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Eccn_1 thisApplet = new Cre_Apt_Eccn_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT_CALL_CONTROL_BY_NAA
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
    }


    //Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event){

        switch(testCaseNb){
        //TestCase 1: The applet must be triggered
            case (byte)0x00:
                testCaseNb=(byte)0x01;
                bRes=true;
                reportTestOutcome(testCaseNb,bRes);
           break;

           case (byte)0x01:
        //TestCase 2: clearEvent method is called and applet is unregistered
                testCaseNb=(byte)0x02;
                try{
                    obReg.clearEvent(EVENT_CALL_CONTROL_BY_NAA);
                    bRes=true;
                }
                catch(Exception e){
                    bRes=false;
                }
                reportTestOutcome(testCaseNb,bRes);
                reportTestOutcome((byte)0x03,true);
                break;
           case (byte) 0x02:
        //TestCase 3: Applet has been unregistered and must not be triggered
                testCaseNb=(byte)0x03;
                bRes=false;
                reportTestOutcome(testCaseNb,bRes);
        }

    }


 }