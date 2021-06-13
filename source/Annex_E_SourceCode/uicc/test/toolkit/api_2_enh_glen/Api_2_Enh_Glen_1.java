//-----------------------------------------------------------------------------
//Api_2_Enh_Glen_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------

package uicc.test.toolkit.api_2_enh_glen;

import uicc.toolkit.*;
import uicc.test.util.*;;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------


/**
* Test Area : uicc.test.toolkit.Api_2_Enh_Glen
*
* @version 0.0.1 - 28 oct. 2004
* @author 3GPP T3 SWG API
*/

public class Api_2_Enh_Glen_1 extends TestToolkitApplet
{

  // Number of tests
  byte testCaseNb = (byte) 0x00 ;

  /**
   * Constructor of the applet
   */
  public Api_2_Enh_Glen_1() 
  {
  }


  /**
   * Method called by the JCRE at the installation of the applet
   */
  public static void install(byte bArray[], short bOffset, byte bLength) 
  {
      // Create a new applet instance
          Api_2_Enh_Glen_1  thisApplet = new Api_2_Enh_Glen_1();

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
    
    // Get the system instance of handlers
    EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
    ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
    
    switch ( testCaseNb ) 
    {
            case (byte) 0 :
                    // --------------------------------------------
                    // Test Case 1 : 
                    testCaseNb = (byte) 1 ;

                    bRes = false ;
                    try {
                            if (envHdlr.getLength()==0x31 )
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
                    break;

            case (byte) 1 :
                    // --------------------------------------------
                    // Test Case 2 : 
                    testCaseNb = (byte) 2 ;

                    bRes = false ;
                    try {
                            if (envHdlr.getLength()==0x7F )
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
                    break;

            case (byte) 2 :
                    // --------------------------------------------
                    // Test Case 3 : 
                    testCaseNb = (byte) 3 ;

                    bRes = false ;
                    try {
                            if (envHdlr.getLength()==0x80 )
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
                    break;

            case (byte) 3 :
                    // --------------------------------------------
                    // Test Case 4 : 
                    testCaseNb = (byte) 4 ;

                    bRes = false ;
                    try {
                            if (envHdlr.getLength()==0xFC )
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
