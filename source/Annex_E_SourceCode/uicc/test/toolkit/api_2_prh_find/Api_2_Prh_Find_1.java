//-----------------------------------------------------------------------------
//Api_2_Prh_FInd_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_find;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_find
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Find_1 extends TestToolkitApplet
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
    public Api_2_Prh_Find_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Find_1  thisApplet = new Api_2_Prh_Find_1();

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
        
        byte tag = 0 ;
        byte occurence = 0 ;        

        // Result of method
        short result = 0 ;
        
        // --------------------------------------------
        // Test Case 1 : invalid input parameter (occurrence = 0)
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;            
            
            try {
                tag = (byte)1 ;
                occurence = (byte)0 ;
                proRespHdlr.findTLV(tag, occurence) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 2 : Search 1st TLV
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            tag = TAG_COMMAND_DETAILS ;
            occurence = (byte)1 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_FOUND_CR_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 3 : Verify selected TLV
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
                        
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x03) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 4 : Search 2nd TLV
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            tag = TAG_DEVICE_IDENTITIES ;
            occurence = (byte)1 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_FOUND_CR_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 5 : Verify selected TLV
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
                        
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x02) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 6 : Search a wrong tag
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
            
            // Select a correct TLV
            tag = TAG_DEVICE_IDENTITIES ;
            occurence = (byte)1 ;
            proRespHdlr.findTLV(tag, occurence) ;
            
            tag = (byte)4 ;
            occurence = (byte)1 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_NOT_FOUND) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 7 : Verify the current TLV is no longer defined
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            
            try {
                result = proRespHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 8 : Search a tag with a wrong occurence
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            
            tag = TAG_COMMAND_DETAILS ;
            occurence = (byte)2 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_NOT_FOUND) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 9 : Verify the current TLV is no longer defined
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {
            
            try {
                result = proRespHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 10 : Search 3rd TLV
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {

            tag = TAG_RESULT ;
            occurence = (byte)1 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_FOUND_CR_NOT_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 11 : Verify current TLV
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
            
            result = proRespHdlr.getValueLength() ;
            bRes = (result == (short)1) ;
        
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 12 : Search 3rd TLV (2nd occurence)
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {

            tag = TAG_RESULT ;
            occurence = (byte)2 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_FOUND_CR_NOT_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 13 : Verify current TLV
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            
            result = proRespHdlr.getValueLength() ;
            bRes = (result == (short)2) ;
        
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 14 : Search tag 81h
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        try {

            tag = (byte)0x83 ;
            occurence = (byte)1 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_FOUND_CR_NOT_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 15 : Search tag 82h
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        try {

            tag = (byte)0x82 ;
            occurence = (byte)1 ;
            result = proRespHdlr.findTLV(tag, occurence) ;
            
            bRes = (result == TLV_FOUND_CR_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
