//-----------------------------------------------------------------------------
//Api_2_Pah_Facyb_Bs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_facyb_bs;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_facyb_bs
 *
 * @version 0.0.1 - 15 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Facyb_Bs_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;
    
    private byte[] TEXT = new byte[16] ;
    
    private byte[] dstBuffer20 = new byte[20] ;
    private byte[] dstBuffer15 = new byte[15] ;
    private byte[] dstBuffer17 = new byte[17] ;
    
    private byte[] compareBuffer = new byte[20] ;
    
    private byte DCS_8_BIT_DATA = (byte) 0x04;

    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Facyb_Bs_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Facyb_Bs_1  thisApplet = new Api_2_Pah_Facyb_Bs_1();

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
        boolean bRes, bRes2 ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

        // Result of method
        short result = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Null as dstBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
                        
            try {
                proHdlr.findAndCopyValue(TAG_COMMAND_DETAILS, null, (short)0) ;
                
            } catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : dstOffset > dstBuffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)15) ;
                        
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer20, (short)21) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : dstOffset < 0
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer20, (short)-1) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : length > dstBufferLength
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer15, (short)0) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : dstOffset + length > dstBuffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer20, (short)5) ;
                
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
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            // Select a TLV
            proHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1) ;
            
            try {
                proHdlr.findAndCopyValue((byte)0x03, dstBuffer20, (short)0) ;
                
            } catch (ToolkitException e) {
                bRes2 = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
            // Verify there is no current TLV
            try {
                proHdlr.getValueLength() ;
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
        
        // Initialise TEXT
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer17, (short)0) ;
                
            bRes = (result == (short)17) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 8 : Compare buffer
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        // Initialise compareBuffer
        compareBuffer[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)(i-1) ;
        }
        
        try {
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, 
                                                            dstBuffer17, (short)0, (short)17) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : Successful call
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        // Initialise dstBuffer
        for (short i=0; i<dstBuffer20.length; i++) {
            dstBuffer20[i] = (byte)0x55 ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer20, (short)2) ;
                
            bRes = (result == (short)19) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : Compare buffer
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)0x55 ;
        }        
        compareBuffer[2] = DCS_8_BIT_DATA ;
        for (short i=3; i<19; i++) {
            compareBuffer[i] = (byte)(i-3) ;
        }
        
        try {
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, 
                                                            dstBuffer20, (short)0, (short)20) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 11 : Successful call with 2 Text String TLV
        testCaseNb = (byte) 11 ;
        bRes = false ;
                
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            // Add a 2nd Text String
            proHdlr.appendTLV(TAG_TEXT_STRING, TEXT, (short)0, (short)2) ;
            
            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, dstBuffer17, (short)0) ;
                
            bRes = (result == (short)17) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 12 : Compare buffer
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        // Initialise compareBuffer
        compareBuffer[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)(i-1) ;
        }
        
        try {
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, 
                                                            dstBuffer17, (short)0, (short)17) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 13 : Successful call, tag = 8Dh
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        // Initialise TEXT
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            result = proHdlr.findAndCopyValue((byte)0x8D, dstBuffer17, (short)0) ;
                
            bRes = (result == (short)17) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 14 : Compare buffer
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        // Initialise compareBuffer
        compareBuffer[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)(i-1) ;
        }
        
        try {
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, 
                                                            dstBuffer17, (short)0, (short)17) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 15 : Successful call, tag = 8Fh
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        // Initialise TEXT
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        try {
            
            // Append TLV with tag 8Fh
            proHdlr.appendTLV((byte)0x8F, TEXT, (short)0, (short)TEXT.length) ;
            
            // Search tag 8Fh
            result = proHdlr.findAndCopyValue((byte)0x8F, dstBuffer17, (short)0) ;
                
            bRes = (result == (short)16) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 16 : Compare buffer
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)(i) ;
        }
        
        try {
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, 
                                                            dstBuffer17, (short)0, (short)16) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
       
    }
}
