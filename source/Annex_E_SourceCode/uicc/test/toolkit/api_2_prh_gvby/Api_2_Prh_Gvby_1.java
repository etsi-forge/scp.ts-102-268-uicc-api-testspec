//-----------------------------------------------------------------------------
//Api_2_Prh_Gvby_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gvby;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gvby
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gvby_1 extends TestToolkitApplet
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
    public Api_2_Prh_Gvby_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gvby_1  thisApplet = new Api_2_Prh_Gvby_1();

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
        short valueOffset = 0 ;
        byte tag = 0 ;
        byte occurence = 0 ;
        
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
                valueOffset = (short)0 ;
                proHdlr.getValueByte(valueOffset) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 2 : Out of Tlv Boundaries
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Search Command Details TLV
            tag = TAG_COMMAND_DETAILS ;
            occurence = (byte)1 ;
            proRespHdlr.findTLV(tag, occurence) ;

            try {
                valueOffset = (short)3 ;
                result = proRespHdlr.getValueByte(valueOffset) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
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

            // Search Command Details TLV
            tag = TAG_COMMAND_DETAILS ;
            occurence = (byte)1 ;
            proRespHdlr.findTLV(tag, occurence) ;

            // Get value
            valueOffset = (short)2 ;
            result = proRespHdlr.getValueByte(valueOffset) ;
            
            bRes = (result == (byte)0x00) ;
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

            // Search Command Details TLV
            tag = TAG_DEVICE_IDENTITIES ;
            occurence = (byte)1 ;
            proRespHdlr.findTLV(tag, occurence) ;

            // Get value
            valueOffset = (short)0 ;
            result = proRespHdlr.getValueByte(valueOffset) ;
            
            bRes = (result == (byte)0x82) ;
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

            // Search Command Details TLV
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            proRespHdlr.findTLV(tag, occurence) ;

            // Get value
            valueOffset = (short)0x7E ;
            result = proRespHdlr.getValueByte(valueOffset) ;
            
            bRes = (result == (byte)0x7E) ;
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

            // Search Command Details TLV
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            proRespHdlr.findTLV(tag, occurence) ;

            // Get value
            valueOffset = (short)0x7E ;
            result = proRespHdlr.getValueByte(valueOffset) ;
            
            bRes = (result == (byte)0x7E) ;
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 7 : Successful call
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {

            // Get value
            valueOffset = (short)0x7F ;
            result = proRespHdlr.getValueByte(valueOffset) ;
            
            bRes = (result == (byte)0x7F) ;
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 8 : Successful call
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {

            // Get value
            valueOffset = (short)0xEF ;
            result = proRespHdlr.getValueByte(valueOffset) ;
            
            bRes = (result == (byte)0xEF) ;
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
