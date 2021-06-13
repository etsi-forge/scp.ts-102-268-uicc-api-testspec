//-----------------------------------------------------------------------------
//    Cre_Apt_Eccn_2.java
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
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_CALL_CONTROL_BY_NAA
 *
 *
 *
 *
 *
 */

public class Cre_Apt_Eccn_2 extends TestToolkitApplet{
    private byte testCaseNb = (byte) 0x00;
    private byte[] TEXT ={(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)' ',(byte)'1'};
    private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'2'};

    private boolean bRes;
    private byte nbCases =(byte)0x00;

    //Constructor of the applet

    public Cre_Apt_Eccn_2(){
        }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Eccn_2 thisApplet = new Cre_Apt_Eccn_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);
        //Prepare result table for test that the applet is no triggered when
        //is deregistered to the event
        thisApplet.reportTestOutcome((byte) 0x01,true);
    }


    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){
        bRes=false;
        if(event==EVENT_MENU_SELECTION ){
        //TestCase 1:displayText command is sent and Applet is registered to
        //EVENT_CALL_CONTROL_BY_NAA
            testCaseNb=(byte)0x02;
            try{
                ProactiveHandler prHdlr = ProactiveHandlerSystem.getTheHandler();
                prHdlr.initDisplayText((byte)0x80,(byte)0x04,TEXT,(short)0x00,(short)TEXT.length);
                prHdlr.send();
                obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
                nbCases=(byte)0x01;
                bRes=true;
             }
             catch(Exception e){
                bRes=false;
             }
             reportTestOutcome(testCaseNb,bRes);
       }

     if(event==EVENT_CALL_CONTROL_BY_NAA){
    //TestCase 2:Applet must be triggered
        switch(nbCases){
            case (byte)0x00:
               testCaseNb=(byte)0x01;
               bRes=false;
               reportTestOutcome(testCaseNb,bRes);
            break;
            case (byte)0x01:
               testCaseNb=0x03;
               bRes=true;
               reportTestOutcome(testCaseNb,bRes);
        }
     }
   }
 }