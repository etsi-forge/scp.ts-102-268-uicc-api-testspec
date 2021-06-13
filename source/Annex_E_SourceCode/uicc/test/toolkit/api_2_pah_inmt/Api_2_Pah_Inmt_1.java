//-----------------------------------------------------------------------------
//Api_2_Pah_Inmt_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_inmt;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_inmt
 *
 * @version 0.0.1 - 22 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Inmt_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Inmt_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Inmt_1  thisApplet = new Api_2_Pah_Inmt_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {
        
        // Result of each test
        boolean bRes = false ;
       
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
        // --------------------------------------------
        // Test Case 1 : 
        //      Call the init() method
        //      Verify each simple TLV
        
        testCaseNb = (byte) 1 ;

        try {
            // Call to initMoreTime() method
            
            proHdlr.initMoreTime();
            proHdlr.send();
            bRes = true;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 2 : 
        //      Verify there is no selected TLV
        
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Select 1st TLV
            proHdlr.findTLV((byte)0x01, (byte)0x01) ;
            
            proHdlr.initMoreTime() ;
            
            // Verify there is no selected TLV
            try {
                proHdlr.getValueLength() ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
