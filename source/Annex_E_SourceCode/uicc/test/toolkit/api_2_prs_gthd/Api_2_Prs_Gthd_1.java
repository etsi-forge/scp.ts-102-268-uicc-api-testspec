//-----------------------------------------------------------------------------
//Api_2_Prs_Gthd_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prs_gthd;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.ProactiveResponseHandler;
import uicc.toolkit.ProactiveResponseHandlerSystem;

/**
 * Test Area : uicc.test.toolkit.api_2_prs_gthd
 *
 * @version 0.0.1 - 3 mars 2006
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prs_Gthd_1 extends TestToolkitApplet
{

    // Number of tests
    byte testCaseNb = (byte) 0x00 ;

    /**
    * Constructor of the applet
    */
    public Api_2_Prs_Gthd_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
         // Create a new applet instance
         Api_2_Prs_Gthd_1  thisApplet = new Api_2_Prs_Gthd_1();

         // Register the new applet instance to the JCRE            
         thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

         // Initialise the data of the test applet
         thisApplet.init();
        
         // Register on UNRECOGNIZED ENVELOPE
         thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }
    

    /**
       * Method called by the SIM Toolkit Framework
     */
    public void processToolkit(short event) 
    {
         // Result of tests
          boolean bRes ;
          
          switch ( testCaseNb ) 
          {
                 case (byte) 0 :
                     // --------------------------------------------
                     // Test Case 1 : use th methode getTheHandler() Twice 
                     testCaseNb = (byte) 1 ;
                 
                     bRes = false ;
                     try {
                         ProactiveResponseHandler ProRespHdlr1 = ProactiveResponseHandlerSystem.getTheHandler();
                         ProactiveResponseHandler ProRespHdlr2 = ProactiveResponseHandlerSystem.getTheHandler();
                 
                         if (ProRespHdlr1==ProRespHdlr2 )
                             bRes = true;
                     }
                     catch (Exception e)    {    
                         bRes = false ;
                     }
                     reportTestOutcome(testCaseNb, bRes) ;
                     break;
                 
                 case (byte) 1 :
                     // --------------------------------------------
                     // Test Case 2 : verify that the methode getTheHandler return ProactiveResponseHandler reference
                     testCaseNb = (byte) 2 ;
                 
                     bRes = false ;
                     try {
                         ProactiveResponseHandler ProRespHdlr1 = ProactiveResponseHandlerSystem.getTheHandler();
                         
                         if (ProRespHdlr1 instanceof ProactiveResponseHandler)
                             bRes = true;
                     }
                     catch (Exception e)    {    
                         bRes = false ;
                     }
                     reportTestOutcome(testCaseNb, bRes) ;
                     break;
                 
                 case (byte) 2 :
                     // --------------------------------------------
                     // Test Case 3 : verify that the methode getTheHandler does not return NULL
                     testCaseNb = (byte) 3 ;
                 
                     bRes = false ;
                     try {
                         ProactiveResponseHandler ProRespHdlr1 = ProactiveResponseHandlerSystem.getTheHandler();
                         
                         if (ProRespHdlr1!=null)
                             bRes = true;
                     }
                     catch (Exception e)    {    
                         bRes = false ;
                     }
                     reportTestOutcome(testCaseNb, bRes) ;
                  break;
          }
    }
}
  

