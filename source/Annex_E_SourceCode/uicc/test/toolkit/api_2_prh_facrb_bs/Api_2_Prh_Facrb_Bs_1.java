//-----------------------------------------------------------------------------
//Api_2_Prh_Facrb_Bs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_facrb_bs;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_facrb_bs
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Facrb_Bs_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    
    private byte[] compareBuffer20 = new byte[20] ;
    private byte[] compareBuffer15 = new byte[15] ;
    private byte[] compareBuffer17 = new byte[17] ;
    private byte[] compareBuffer = new byte[20] ;

    public final byte DCS_8_BIT_DATA = (byte)0x04;
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Facrb_Bs_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Facrb_Bs_1  thisApplet = new Api_2_Prh_Facrb_Bs_1();

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
        bRes = false ;
        boolean bRes2 = false ;
        
        // Number of tests
        testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        ProactiveResponseHandler proRespHdlr = null ;
        
        // Result of method
        short result = 0 ;

        byte tag = 0 ;
        short compareOffset = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Null as compareBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
                        
            try {
                tag = TAG_COMMAND_DETAILS ;
                compareOffset = (short)0 ;
                proRespHdlr.findAndCompareValue(tag, null, compareOffset) ;
                
            } catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : compareOffset > compareBuffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
                                    
            try {
                tag = TAG_TEXT_STRING ;
                compareOffset = (short)21 ;
                proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : compareOffset < 0
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
                                    
            try {
                tag = TAG_TEXT_STRING ;
                compareOffset = (short)-1 ;
                proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : length > compareBufferLength
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
                                    
            try {
                tag = TAG_TEXT_STRING ;
                compareOffset = (short)0 ;
                proRespHdlr.findAndCompareValue(tag, compareBuffer15, compareOffset) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : compareOffset + length > compareBuffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
                                    
            try {
                tag = TAG_TEXT_STRING ;
                compareOffset = (short)5 ;
                proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 6 : Unavailable element
        testCaseNb = (byte) 6 ;
        bRes = false ;
        bRes2 = false ;
        
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Select a TLV
            proRespHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1) ;
            
            try {
                tag = (byte)04 ;
                compareOffset = (short)0 ;
                proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            } catch (ToolkitException e) {
                bRes2 = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
            // Verify there is no current TLV
            try {
                proRespHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                bRes = (bRes2) && (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
           
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 7 : Successful call
        testCaseNb = (byte) 7 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer17[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer17.length; i++) {
            compareBuffer17[i] = (byte)(i-1) ;
        }
                
        try {
                        
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)0 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer17, compareOffset) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 8 : Verify current TLV
        testCaseNb = (byte) 8 ;
        bRes = false ;
                
        try {
            
            result = proRespHdlr.getValueLength() ;
                
            bRes = (result == (short)17) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : Successful call
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=3; i<19; i++) {
            compareBuffer20[i] = (byte)(i-3) ;
        }
        compareBuffer20[0x11] = (byte)0x10 ;
        
        try {
            
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)0 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)-1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : Successful call
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }        
        compareBuffer20[0] = (byte)(DCS_8_BIT_DATA-1) ;
        for (short i=3; i<19; i++) {
            compareBuffer20[i] = (byte)(i-3) ;
        }
        
        try {
            
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)0 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 11 : Successful call
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=3; i<19; i++) {
            compareBuffer20[i] = (byte)(i-3) ;
        }
        
        try {
            
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)2 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 12 : Successful call with 2 Text String TLV
        testCaseNb = (byte) 12 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=3; i<19; i++) {
            compareBuffer20[i] = (byte)(i-3) ;
        }
        
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
                        
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)2 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 13 : Successful call with 2 Text String TLV
        testCaseNb = (byte) 13 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=3; i<19; i++) {
            compareBuffer20[i] = (byte)(i-3) ;
        }
        compareBuffer20[3] = (byte)0x01 ;
        
        try {
            
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)2 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)-1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 14 : Successful call with 2 Text String TLV
        testCaseNb = (byte) 14 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=3; i<19; i++) {
            compareBuffer20[i] = (byte)(i-3) ;
        }
        compareBuffer20[17] = (byte)0x0D ;
        compareBuffer20[18] = (byte)0x10 ;
        
        try {
            
            tag = TAG_TEXT_STRING ;
            compareOffset = (short)2 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 15 : Successful call 
        testCaseNb = (byte) 15 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer20[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<19; i++) {
            compareBuffer20[i] = (byte)(i-1) ;
        }
        
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
                        
            tag = (byte)0x8D ;
            compareOffset = (short)0 ;
            result = proRespHdlr.findAndCompareValue(tag, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
