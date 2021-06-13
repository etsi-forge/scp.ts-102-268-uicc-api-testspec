//-----------------------------------------------------------------------------
//    Cre_Apt_Edcr_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_edcr;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_DOWNLOAD CARD READER STATUS
 *
 *
 *
 *
 *
 */

public class Cre_Apt_Edcr_1 extends TestToolkitApplet{
    private byte testCaseNb = (byte) 0x00;
    private static boolean bRes;
    private static byte[] menuEntry ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)' ',(byte)'1'};

    //private static ToolkitRegistry obReg;

    //Constructor of the applet
    public Cre_Apt_Edcr_1(){

    }


    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){

        //Create a new applet instace
        Cre_Apt_Edcr_1 thisApplet = new Cre_Apt_Edcr_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT_DOWNLOAD CARD READER STATUS
        //obReg =ToolkitRegistry.getEntry();
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS);
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);

        bRes=thisApplet.obReg.isEventSet(EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS);
    }


    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){

        //event EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
        if(event==EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS){

            switch(testCaseNb){

                case (byte)0x00:
                    testCaseNb=(byte)0x01;
                    reportTestOutcome(testCaseNb,bRes);
                   //TEST CASE 2:UNREGISTER THE APPLET TO THE EVENT
                    testCaseNb=(byte)0x02;
                    try{
                        //deregister to EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
                        obReg.clearEvent(EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS);
                        bRes=true;
                    }
                    catch(Exception e){
                        bRes=false;
                    }
                    reportTestOutcome(testCaseNb,bRes);
            reportTestOutcome((byte)0x03,true);
               break;
               case (byte)0x02:
                    testCaseNb=(byte)0x03;
                    bRes=false;
                    reportTestOutcome(testCaseNb,bRes);
               break;
               case (byte)0x04:
                   //TEST CASE 5:THE APPLET MUST BE TRIGGERED
                   testCaseNb=(byte)0x05;
                   bRes=true;
                   reportTestOutcome(testCaseNb,bRes);
            }
        }

        //event EVENT_MENU_SELECTION
        if(event==EVENT_MENU_SELECTION){
        //TEST CASE 4:REGISTER THE APPLET TO THE EVENT
            testCaseNb=(byte)0x04;
            try{
                //register to EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS
                obReg.setEvent(EVENT_EVENT_DOWNLOAD_CARD_READER_STATUS);
                bRes=true;
            }
            catch(Exception e){
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);


        }

    }

 }