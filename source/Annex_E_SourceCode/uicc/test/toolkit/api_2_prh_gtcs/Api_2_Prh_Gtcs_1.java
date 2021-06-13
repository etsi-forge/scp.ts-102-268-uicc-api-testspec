//-----------------------------------------------------------------------------
//Api_2_Prh_Gtcs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gtcs;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gtcs
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gtcs_1 extends TestToolkitApplet
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
    public Api_2_Prh_Gtcs_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gtcs_1  thisApplet = new Api_2_Prh_Gtcs_1();

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
    
        short result = (short) 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Unavailable Text String TLV
        
        testCaseNb = (byte) 1 ;
        
        bRes = false ;
        try {
            
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // getTextStringCodingScheme() with unavailable Text String TLV
                result = proRespHdlr.getTextStringCodingScheme() ;
                
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                    bRes = true ;
                }
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 2 : Null Text String TLV
        
        testCaseNb = (byte) 2 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // getTextStringCodingScheme() with null Text String TLV
                result = proRespHdlr.getTextStringCodingScheme() ;
                
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true ;
                }
            }
                
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : Successfull call
        
        testCaseNb = (byte) 3 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getTextStringCodingScheme() ;
            
            bRes = (result == (short)0x04) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 4 : Verify the TLV selected
        
        testCaseNb = (byte) 4 ;
        
        bRes = false ;
        try {
            
            // Get length of current TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x02) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Successfull call
        
        testCaseNb = (byte) 5 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getTextStringCodingScheme() ;
            
            bRes = (result == (short)0x00) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 6 : Verify the TLV selected
        
        testCaseNb = (byte) 6 ;
        
        bRes = false ;
        try {
            
            // Get length of current TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x03) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 7 : Successfull call
        
        testCaseNb = (byte) 7 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getTextStringCodingScheme() ;
            
            bRes = (result == (short)0x08) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 8 : Verify the TLV selected
        
        testCaseNb = (byte) 8 ;
        
        bRes = false ;
        try {
            
            // Get length of current TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x7F) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 9 : Successfull call
        
        testCaseNb = (byte) 9 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getTextStringCodingScheme() ;
            
            bRes = (result == (short)0x04) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 10 : Verify the TLV selected
        
        testCaseNb = (byte) 10 ;
        
        bRes = false ;
        try {
            
            // Get length of current TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x80) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 11 : Successfull call
        
        testCaseNb = (byte) 11 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getTextStringCodingScheme() ;
            
            bRes = (result == (short)0x08) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 12 : Verify the TLV selected
        
        testCaseNb = (byte) 12 ;
        
        bRes = false ;
        try {
            
            // Get length of current TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0xF0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 13 : Successfull call
        // Terminal Response with 2 Text String TLV
        
        testCaseNb = (byte) 13 ;
        
        bRes = false ;
        try {
            
            // Build and send a GET INPUT proactive command
            proHdlr.initGetInput((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getTextStringCodingScheme() ;
            
            bRes = (result == (short)0x04) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 14 : Verify the TLV selected
        
        testCaseNb = (byte) 14 ;
        
        bRes = false ;
        try {
            
            // Get length of current TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x02) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
