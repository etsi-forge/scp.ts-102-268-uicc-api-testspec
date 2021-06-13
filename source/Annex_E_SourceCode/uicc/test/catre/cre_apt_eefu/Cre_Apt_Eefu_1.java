//-----------------------------------------------------------------------------
//    Cre_Apt_Eefu_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------

package uicc.test.catre.cre_apt_eefu;


//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------


import uicc.access.* ;
import uicc.toolkit.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * Test Area: UICC CAT Runtime Environment Applet Triggering EVENT_EXTERNAL_FILE_UPDATE
 *
 *
 *
 *
 *
 */

 public class Cre_Apt_Eefu_1 extends TestToolkitApplet{

     private static boolean bRes;
     private byte testCaseNb            = (byte) 0x00;
     private static byte[] menuEntry    ={(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e',(byte)'t',(byte)'1'};
     private static byte[] fileList1    ={(byte)0x02,(byte)0x3F, (byte)0x00, (byte)0x7F, (byte)0x4A, (byte)0x6F, (byte)0x0C,
                                          (byte)0x3F, (byte)0x00, (byte)0x7F, (byte)0x4A, (byte)0x6F, (byte)0x09};
     private static byte[] fileList2    ={(byte)0x01,(byte)0x3F, (byte)0x00, (byte)0x7F, (byte)0x4A, (byte)0x6F, (byte)0x0C};
     private static byte[] baADFAid     =null;
     private static byte[] resp1        ={(byte)0, (byte)0,(byte)0,(byte)0,(byte)0,};
     private static byte[] resp2        ={(byte)0, (byte)0,(byte)0,(byte)0,(byte)0,};
     private static byte[] resp3        ={(byte)0, (byte)0,(byte)0,(byte)0,(byte)0,};
     private FileView theUiccView;



    //Constructor of the applet
     public Cre_Apt_Eefu_1(){

    }
    //Method called by the JCRE at the installation of the applet
    public static void install (byte bArray[], short bOffset, byte bLength){
        //Create a new applet instace
        Cre_Apt_Eefu_1 thisApplet = new Cre_Apt_Eefu_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        // Initialise the data of the test applet
        thisApplet.init();
        //register to EVENT MENU SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0x00,(short)menuEntry.length,(byte)0x00,false,(byte)0x00,(short)0x00);


    }

    //Method called by the UICC Toolkit Framework

    public void processToolkit(short event){
        theUiccView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
        //event EVENT_MENU_SELECTION
        if(event==EVENT_MENU_SELECTION){
         //TEST CASE 1: 1 - Applet is triggered
         switch(testCaseNb){
             case (byte)0x00:
                 testCaseNb=(byte)0x01;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             //TEST CASE 1:2 - METHOD isEventSet() RETURNS FALSE
                 testCaseNb=(byte)0x02;
                 try{
                     bRes=!obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                  }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
             //TEST CASE 1:3 - No exception is thrown
                 testCaseNb=(byte)0x03;
                 if(theUiccView!=null){
                     theUiccView.select(UICCTestConstants.FID_DF_TEST);
                     theUiccView.select(UICCTestConstants.FID_EF_TARU);
                     try{
                         obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,theUiccView);
                         bRes=true;
                     }
                     catch(Exception e){bRes=false;}
                    }
                 else{bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
             //TEST CASE 1:4 - METHOD isEventSet() RETURNS TRUE
                 testCaseNb=(byte)0x04;
                 try{bRes=obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);}
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
             //TEST CASE 1:5 - No exception is thrown
                 testCaseNb=(byte)0x05;
                 try{
                     obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,fileList1, (short)0x00,(short)fileList1.length,
                     baADFAid, (short)0x00, (byte)0x00);
                     bRes=true;
                    }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
             break;
             //TEST CASE 3: 1 - Applet is triggered
             case (byte)0x14:
                 testCaseNb=(byte)0x15;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
            //TEST CASE 3:2 - No exception is thrown
                 testCaseNb=(byte)0x16;
                 if(theUiccView!=null){
                     theUiccView.select(UICCTestConstants.FID_DF_TEST);
                     theUiccView.select(UICCTestConstants.FID_EF_TARU);
                     try{
                         obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,theUiccView);
                         bRes=true;
                         }
                     catch(Exception e){bRes=false;}
                    }
                 else{bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
            //TEST CASE 3:3 - No exception is thrown
                 testCaseNb=(byte)0x17;
                 try{
                     obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,fileList1, (short)0x00,
                                                 (short)fileList1.length, baADFAid, (short)0x00, (byte)0x00);
                    bRes=true;
                    }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
             break;


         }//end of switch
    }


    //event EVENT_EXTERNAL_FILE_UPDATE
        if(event==EVENT_EXTERNAL_FILE_UPDATE){
        switch(testCaseNb){
          //TEST CASE 1:6 - APPLET IS TRIGGERED on UPDATE EFtaru
             case (byte)0x05:
                 testCaseNb=(byte)0x06;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
          //TEST CASE 1:7 - APPLET IS TRIGGERED on UPDATE EFlaru
            case (byte)0x06:
                 testCaseNb=(byte)0x07;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
            break;
         //TEST CASE 1:8 - APPLET IS TRIGGERED on INCREASE EFcaru
             case (byte)0x07:
                 testCaseNb=(byte)0x08;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
         //TEST CASE 2: 1 - APPLET IS TRIGGERED on UPDATE EFtaru
             case (byte)0x08:
                 testCaseNb=(byte)0x09;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
                 theUiccView.select(UICCTestConstants.FID_DF_TEST);
                 theUiccView.select(UICCTestConstants.FID_EF_TARU);
                 theUiccView.readBinary((short) 0, resp1,(short) 0,(short) resp1.length);

         //TEST CASE 2: 2 - No exception is thrown
                 testCaseNb=(byte)0x0A;
                 theUiccView.select(UICCTestConstants.FID_DF_TEST);
                 theUiccView.select(UICCTestConstants.FID_EF_CARU);
                 try{
                     obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,theUiccView);
                     bRes=true;
                     }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 2:3 - METHOD isEventSet() RETURNS TRUE
                 testCaseNb=(byte)0x0B;
                 try{bRes=obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);}
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 2:4 - APPLET IS NOT TRIGGERED on INCREASE EFcaru
                 testCaseNb=(byte)0x0C;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
          //TEST CASE 2:5 - APPLET IS TRIGGERED on UPDATE EFtaru
             case (byte) 0x0C:
                 testCaseNb=(byte)0x0D;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
         //Check to see if the triggering is on this event or on the previous one
                 theUiccView.select(UICCTestConstants.FID_DF_TEST);
                 theUiccView.select(UICCTestConstants.FID_EF_TARU);
                 theUiccView.readBinary((short) 0, resp2,(short) 0,(short) resp2.length);                 
                 if(Util.arrayCompare(resp1, (short)0, resp2, (short)0, (short)resp2.length)==0){
                    testCaseNb=(byte)0x0C;
                    bRes=false;
                    reportTestOutcome(testCaseNb,bRes);
                 }

          //TEST CASE 2:6 - No Exception is thrown
                 testCaseNb=(byte)0x0E;
                 try{obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,fileList2, (short)0x00,
                                                 (short)fileList2.length, baADFAid, (short)0x00, (byte)0x00);
                    bRes=true;
                    }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
           //TEST CASE 2:7 - METHOD isEventSet() RETURNS TRUE
                 testCaseNb=(byte)0x0F;
                 try{bRes=obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);}
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
            //TEST CASE 2:8 - APPLET IS NOT TRIGGERED on UPDATE EFlaru
                 testCaseNb=(byte)0x10;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
            //TEST CASE 2:9 - APPLET IS TRIGGERED on UPDATE EFtaru
             case (byte)0x10:
                 testCaseNb=(byte)0x11;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
         //Check to see if the triggering is on this event or on the previous one
                 theUiccView.select(UICCTestConstants.FID_DF_TEST);
                 theUiccView.select(UICCTestConstants.FID_EF_TARU);
                 theUiccView.readBinary((short) 0, resp3,(short) 0,(short) resp3.length);                 
                 if(Util.arrayCompare(resp2, (short)0, resp3, (short)0, (short)resp3.length)==0){
                    testCaseNb=(byte)0x10;
                    bRes=false;
                    reportTestOutcome(testCaseNb,bRes);
                 }                                 
                 
            //TEST CASE 2: 10 - No exception is thrown
                 testCaseNb=(byte)0x12;
                 theUiccView.select(UICCTestConstants.FID_DF_TEST);
                 theUiccView.select(UICCTestConstants.FID_EF_TARU);
                 try{obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,theUiccView);
                 bRes=true;
                 }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
            //TEST CASE 2:11 - METHOD isEventSet() RETURNS FALSE
                 testCaseNb=(byte)0x13;
                 try{bRes=!obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);}
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
            //TEST CASE 2:12 - APPLET IS NOT TRIGGERED on UPDATE EFtaru
                 testCaseNb=(byte)0x14;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
            //TEST CASE 2:12 - APPLET IS TRIGGERED on UPDATE EFtaru WRONGLY
             case (byte)0x14:
                 testCaseNb=(byte)0x14;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             break;
            //TEST CASE 3:4 - APPLET IS TRIGGERED on UPDATE EFtaru
             case (byte)0x17:
                 testCaseNb=(byte)0x18;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
             break;
             //TEST CASE 3:5 - APPLET IS TRIGGERED on UPDATE EFlaru
            case (byte)0x18:
                 testCaseNb=(byte)0x19;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
            break;
            //TEST CASE 3:6 - APPLET IS TRIGGERED on INCREASE EFcaru
             case (byte)0x19:
                 testCaseNb=(byte)0x1A;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 3:7 - No Exception is thrown
                 testCaseNb=(byte)0x1B;
                 try{obReg.clearEvent(EVENT_EXTERNAL_FILE_UPDATE);
                 bRes=true;
                 }
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 3:8 - METHOD isEventSet() RETURNS FALSE
                 testCaseNb=(byte)0x1C;
                 try{bRes=!obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);}
                 catch(Exception e){bRes=false;}
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 3:9 - APPLET IS NOT TRIGGERED on UPDATE EFtaru
                 testCaseNb=(byte)0x1D;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 3:10 - APPLET IS NOT TRIGGERED on UPDATE EFlaru
                 testCaseNb=(byte)0x1E;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
          //TEST CASE 3:11 - APPLET IS NOT TRIGGERED on UPDATE EFcaru
                 testCaseNb=(byte)0x1F;
                 bRes=true;
                 reportTestOutcome(testCaseNb,bRes);
                 testCaseNb=(byte)0x1C;
             break;
          //TEST CASE 3:9 - APPLET IS TRIGGERED on UPDATE EFtaru WRONGLY
             case (byte)0x1C:
                 testCaseNb=(byte)0x1D;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             break;
          //TEST CASE 3:10 - APPLET IS TRIGGERED on UPDATE EFlaru WRONGLY
             case (byte)0x1D:
                 testCaseNb=(byte)0x1E;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             break;
           //TEST CASE 3:11 - APPLET IS TRIGGERED on UPDATE EFcaru WRONGLY
             case (byte)0x1E:
                 testCaseNb=(byte)0x1F;
                 bRes=false;
                 reportTestOutcome(testCaseNb,bRes);
             break;

        }
    }
  }
}