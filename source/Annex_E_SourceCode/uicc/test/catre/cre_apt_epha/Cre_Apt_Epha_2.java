//-----------------------------------------------------------------------------
//    Cre_Apt_Epha_2.java
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
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_PROACTIVE_HANDLER_AVAILABLE
 *
 *
 *
 *
 *
 */

public class Cre_Apt_Epha_2 extends TestToolkitApplet{
    private byte testCaseNb = (byte) 0x00;
    private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'2'};
    private boolean bRes=false;


    //Constructor of the applet

    public Cre_Apt_Epha_2(){
        }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Epha_2 thisApplet = new Cre_Apt_Epha_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();

        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
    }


    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){
        if(event==EVENT_EVENT_DOWNLOAD_USER_ACTIVITY){
            switch(testCaseNb){
            //TestCase 1: Applet2 is triggered
                    case (byte)0x00:
                       testCaseNb=(byte)0x01;
                       bRes=true;
                       reportTestOutcome(testCaseNb,bRes);
                       testCaseNb=(byte)0x02;
            //TestCase 1: 7- No exception is thrown
                    try{
                        obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
                    
           //TestCase 1: 8- Method returns TRUE
                    testCaseNb=(byte)0x03;
                    bRes=obReg.isEventSet(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                    reportTestOutcome(testCaseNb,bRes);
                    break;
        	}
		}

        if(event==EVENT_MENU_SELECTION ){
            switch(testCaseNb){
        
                case (byte)0x05:
                   testCaseNb=(byte)0x06;
                   try{
                       obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
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
        	//TestCase 2:Applet must be triggered
        	switch(testCaseNb){
        		//TestCase 1: 9- Applet1 is triggered
        	    case (byte)0x03:
        	       testCaseNb=(byte)0x04;
        	       bRes=true;
        	       reportTestOutcome(testCaseNb,bRes);
        	    //TestCase 1: 10- Method returns FALSE
        	       testCaseNb=(byte)0x05;
        	       bRes=!obReg.isEventSet(EVENT_PROACTIVE_HANDLER_AVAILABLE);
        	       reportTestOutcome(testCaseNb,bRes);
        	    break;
        	    case (byte)0x06:
        	       testCaseNb=(byte)0x07;
        	       bRes=true;
        	       reportTestOutcome(testCaseNb,bRes);
        	    break;
        	    
        	}
		}
   }
}