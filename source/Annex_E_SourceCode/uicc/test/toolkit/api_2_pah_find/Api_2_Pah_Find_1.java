//-----------------------------------------------------------------------------
//Api_2_Pah_Find_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_find;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_find
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Find_1 extends TestToolkitApplet
{
    private static final byte TYPE = (byte) 0x21 ;
    private static final byte QUALIFIER = (byte) 0x00 ;
    private static final byte DST_DEVICE = (byte) 0x82 ;
    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Find_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Find_1  thisApplet = new Api_2_Pah_Find_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {
        // Result of tests
        boolean bRes ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

        // Result of method
        short result = 0 ;
        
        // --------------------------------------------
        // Test Case 1 : invalid input parameter (occurrence = 0)
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            try {
                proHdlr.findTLV((byte)0x01, (byte) 0x00) ;
                
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
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            result = proHdlr.findTLV((byte)0x01, (byte) 0x01) ;
            
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
                        
            result = proHdlr.getValueLength() ;
            
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
            
            result = proHdlr.findTLV((byte)0x02, (byte) 0x01) ;
            
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
                        
            result = proHdlr.getValueLength() ;
            
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
            proHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)0x01) ;
            
            result = proHdlr.findTLV((byte)0x03, (byte) 0x01) ;
            
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
                result = proHdlr.getValueLength() ;
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
            
            result = proHdlr.findTLV((byte)0x01, (byte) 0x02) ;
            
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
                result = proHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
          
        
        // --------------------------------------------
        // Test Case 10 : Search a TLV with occurence=2
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
            
            // Append Tag 0x02
            proHdlr.appendTLV((byte)0x02, (byte)0x00) ;
            
            // Search the tag
            result = proHdlr.findTLV((byte)0x02, (byte) 0x02) ;
            
            bRes = (result == TLV_FOUND_CR_NOT_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 11 : Search a TLV
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
            
            // Append Tag 0x04
            proHdlr.appendTLV((byte)0x04, (byte)0x00) ;
            
            // Search the tag
            result = proHdlr.findTLV((byte)0x04, (byte) 0x01) ;
            
            bRes = (result == TLV_FOUND_CR_NOT_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 12 : Search tag 81h
        
        testCaseNb = (byte) 12 ;
        bRes = false ;

        try {
                        
            // Search the tag
            result = proHdlr.findTLV((byte)0x81, (byte) 0x01) ;
            
            bRes = (result == TLV_FOUND_CR_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 13 : Search tag 84h
        
        testCaseNb = (byte) 13 ;
        bRes = false ;

        try {
                        
            // Search the tag
            result = proHdlr.findTLV((byte)0x84, (byte) 0x01) ;
            
            bRes = (result == TLV_FOUND_CR_NOT_SET) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
