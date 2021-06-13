//-----------------------------------------------------------------------------
//Api_2_Pah_Glen_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_glen;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

import javacard.framework.Util;
import uicc.toolkit.*;
import uicc.test.util.*;

/**
 * Test Area : uicc.test.toolkit.api_2_pah_glen
 *
 * @version 0.0.1 - 3 mars 2006
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Glen_1 extends TestToolkitApplet
{

  // Number of tests
  byte testCaseNb = (byte) 0x00 ;

  private byte TYPE = (byte) 0x41 ;
  private byte QUALIFIER = (byte) 0x42 ;
  private byte DST_DEVICE = (byte) 0x43 ;
  private byte DCS_8_BIT_DATA = (byte) 0x04;
  /**
   * Constructor of the applet
   */
  public Api_2_Pah_Glen_1() 
  {
  }


  /**
   * Method called by the JCRE at the installation of the applet
   */
  public static void install(byte bArray[], short bOffset, byte bLength) 
  {
      // Create a new applet instance
      Api_2_Pah_Glen_1  thisApplet = new Api_2_Pah_Glen_1();

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
    byte[] Text = new byte[240];
    Util.arrayFillNonAtomic(Text,(short)0,(short)Text.length,(byte)0);
    
    // Get the system instance of handlers
    ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
    
    switch ( testCaseNb ) 
    {
            case (byte) 0 :
                    // --------------------------------------------
                    // Test Case 1 : Clear the handler
                    testCaseNb = (byte) 1 ;

                    bRes = false ;
                    try {
                            proHdlr.clear();
                            if (proHdlr.getLength()==0)
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
                    break;

            case (byte) 1 :
                    // --------------------------------------------
                    // Test Case 2 : Call the init() method
                    testCaseNb = (byte) 2 ;

                    bRes = false ;
                    try {
                            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE);
                            if (proHdlr.getLength()== (short)0x0009)
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
                    break;

            case (byte) 2 :
                    // --------------------------------------------
                    // Test Case 3 : InitDisplayText with Text length = 240
                    testCaseNb = (byte) 3 ;

                    bRes = false ;
                    try {
                            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, Text, (short)0, (short)0x00F0) ;
                            if (proHdlr.getLength()== (short)0x00FD)
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
                    break;

            case (byte) 3 :
                    // --------------------------------------------
                    // Test Case 4 : Build a 7Fh length proactive command.
                    testCaseNb = (byte) 4 ;

                    bRes = false ;
                    try {
                            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, Text, (short)0, (short)0x0073) ;
                            if (proHdlr.getLength()== (short) 0x007F )
                                    bRes = true;
                    }
                    catch (Exception e)    {    
                            bRes = false ;
                    }
                    reportTestOutcome(testCaseNb, bRes) ;
            break;
            
            case (byte) 4 :
                    // --------------------------------------------
                    // Test Case 5 : Build a 80h length proactive command.
                    testCaseNb = (byte) 5 ;

                    bRes = false ;
                    try {
                            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, Text, (short)0, (short)0x0074) ;
                            if (proHdlr.getLength()== (short) 0x0080 )
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
