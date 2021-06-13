//-----------------------------------------------------------------------------
//Api_2_Prh_Gvle_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gvle;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gvle
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gvle_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    
    public final byte DCS_8_BIT_DATA = (byte)0x04;
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Gvle_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gvle_1  thisApplet = new Api_2_Prh_Gvle_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event)
    {

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

        ProactiveResponseHandler proRespHdlr = null ;

        // Result of method
        short result = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Unavailable Element
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Build and send a PRO command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                proRespHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : Successful call
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Search Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            // Get length
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : Successful call
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            
            // Build and send a PRO command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Search Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            // Get length
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)2) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : Successful call
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {

            // Build and send a PRO command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Search Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            // Get length
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x7F) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Successful call
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            
            // Build and send a PRO command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Search Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            // Get length
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x80) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 6 : Successful call
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {

            // Build and send a PRO command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Search Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            // Get length
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0xF0) ;
        } 
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
