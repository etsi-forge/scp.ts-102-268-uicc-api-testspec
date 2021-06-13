//-----------------------------------------------------------------------------
//    Cre_Apt_Epha_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_epha;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: CAT Runtime Environment Applet Triggering EVENT_PROACTIVE_HANDLER_AVAILABLE
 *
 *
 *
 *
 *
 */

public class Cre_Apt_Epha_1 extends TestToolkitApplet{
    private byte        testCaseNb = (byte) 0x00;
    private boolean           bRes = false;
    private        byte[]     TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'1'};
    private static byte[] menuEntry= {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'1'};

    //Constructor of the applet

    public Cre_Apt_Epha_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Epha_1 thisApplet = new Cre_Apt_Epha_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
      }


    //Method called by the UICC CAT Runtime Environment

    public void processToolkit(short event){

        if(event==EVENT_MENU_SELECTION ){
            switch(testCaseNb){
            //TestCase 1: 1- Applet1 is triggered
                case (byte)0x00:
                    testCaseNb=(byte)0x01;
                    bRes=true;
                    reportTestOutcome(testCaseNb,bRes);
            //TestCase 1: 1.1- No exception is thrown
                    testCaseNb=(byte)0x02;
                    try{
                        obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
            //TestCase 1: 1.2- No exception is thrown
            //TestCase 1: 1.3- Method returns TRUE
                    testCaseNb=(byte)0x03;
                    try{
                        obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);  //1.2
                        bRes=obReg.isEventSet(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);  //1.3                      
                    }
                    catch(Exception e){
                        bRes=false;
                    }                    
                    reportTestOutcome(testCaseNb,bRes);
            //TestCase 1: 1.4- Method returns TRUE
                    testCaseNb=(byte)0x04;
                    bRes=obReg.isEventSet(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                    reportTestOutcome(testCaseNb,bRes);
                break;

            //TestCase 2: 1- Applet1 is triggered
                case (byte)0x0B:
                    testCaseNb=(byte)0x0C;
                    bRes=true;
                    reportTestOutcome(testCaseNb,bRes);
                    testCaseNb=(byte)0x0D;
                    try{
                        ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                        prHdlr.initDisplayText((byte)0x80,(byte)0x04,TEXT,(short)0x00,(short)TEXT.length);
                        prHdlr.send();
                        bRes=true;
                     }
                     catch(Exception e){
                        bRes=false;
                     }
                     reportTestOutcome(testCaseNb,bRes);
                break;
           }
        }

        if(event==EVENT_PROACTIVE_HANDLER_AVAILABLE){
            switch(testCaseNb){
            //TestCase 1: 2- Applet1 is triggered
                case (byte)0x04:
                    testCaseNb=(byte)0x05;
                    bRes=true;
                    reportTestOutcome(testCaseNb,bRes);
            //TestCase 1: 3- Method returns FALSE
                    testCaseNb=(byte)0x06;
                    bRes=!obReg.isEventSet(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                    reportTestOutcome(testCaseNb,bRes);
                break;

             //TestCase 1: - Applet1 is triggered
                 case (byte)0x09:
                     testCaseNb=(byte)0x0A;
                     bRes=true;
                     reportTestOutcome(testCaseNb,bRes);
             //TestCase 1: 9- Method returns FALSE
                     testCaseNb=(byte)0x0B;
                     bRes=!obReg.isEventSet(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                     reportTestOutcome(testCaseNb,bRes);
                 break;
        	}
        }

        if(event==EVENT_EVENT_DOWNLOAD_USER_ACTIVITY){
            switch(testCaseNb){
            //TestCase 1: 4- Applet1 is triggered
                case (byte)0x06:
                    testCaseNb=(byte)0x07;
                    bRes=true;
                    reportTestOutcome(testCaseNb,bRes);
            //TestCase 1: 5- No exception is thrown
                    try{
                        obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                        testCaseNb=(byte)0x08;
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
            //TestCase 1: 6- Method returns TRUE
                    testCaseNb=(byte)0x09;
                    bRes=obReg.isEventSet(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                    reportTestOutcome(testCaseNb,bRes);


              break;
        	}
		}
	}
}