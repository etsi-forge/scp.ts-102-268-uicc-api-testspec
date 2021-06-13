//-----------------------------------------------------------------------------
//Api_2_Pah_Facrb_Bs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_facrb_bs;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_facrb_bs
 *
 * @version 0.0.1 - 15 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Facrb_Bs_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;
    
    private byte[] TEXT = new byte[16] ;
    
    private byte[] compareBuffer20 = new byte[20] ;
    private byte[] compareBuffer15 = new byte[15] ;
    private byte[] compareBuffer17 = new byte[17] ;
     
    private byte DCS_8_BIT_DATA = (byte) 0x04;

    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Facrb_Bs_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Facrb_Bs_1  thisApplet = new Api_2_Pah_Facrb_Bs_1();

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
        boolean bRes2 = false ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

        // Result of method
        short result = 0 ;

        short compareOffset = (short) 0;
        
        // --------------------------------------------
        // Test Case 1 : Null as compareBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;

            compareOffset = (short) 0 ;
            try {
                proHdlr.findAndCompareValue(TAG_COMMAND_DETAILS, null, compareOffset) ;
                
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
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)15) ;

            compareOffset = (short)21 ;
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
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

            compareOffset = (short)-1 ;
            
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
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
            
            compareOffset = (short) 0 ;
            
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer15, compareOffset) ;
                
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
            
            compareOffset = (short) 5 ;
            
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
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
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            // Select a TLV
            proHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1) ;
            
            // Search a wrong TLV
            compareOffset = (short) 0 ;
            try {
                proHdlr.findAndCompareValue((byte)0x03, compareBuffer20, compareOffset) ;
                
            } catch (ToolkitException e) {
                bRes2 = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
            // Verify there is no selected TLV
            try {
                proHdlr.getValueLength() ;
            }
            catch (ToolkitException e) {
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
        
        // Initialise TEXT and compare buffer
        compareBuffer17[0] = DCS_8_BIT_DATA ;
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
            compareBuffer17[(short)(i+1)] = (byte)i ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            compareOffset = (short)0 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer17, compareOffset) ;
                
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
                        
            result = proHdlr.getValueLength() ;
                
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
        
        // Initialise compare buffer
        compareBuffer17[0] = TAG_TEXT_STRING ;
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer17[(short)(i+1)] = (byte)i ;
        }
        compareBuffer17[16] = (byte)0x10;
        
        try {
                        
            compareOffset = (short)0 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer17, compareOffset) ;
                
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
        
        // Initialise compare buffer
        compareBuffer17[0] = TAG_TEXT_STRING ;
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer17[(short)(i+1)] = (byte)i ;
        }
        compareBuffer17[0] = (byte)0x03;
        
        try {
                        
            compareOffset = (short)0 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer17, compareOffset) ;
                
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
        
        // Initialise compare buffer
        Util.arrayFillNonAtomic(compareBuffer20,(short)0,(short)compareBuffer20.length,(byte)0x55);
        
        compareBuffer20[2] = DCS_8_BIT_DATA;
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer20[(short)(i+3)] = (byte)i ;
        }
        
        try {
            compareOffset = (short)2 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
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
        
        // Initialise compare buffer
        for (short i=0; i<(short)20; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer20[(short)(i+3)] = (byte)i ;
        }
        
        try {
                    
            // Append a Text String TLV
            TEXT[0] = (byte) 0x00 ;
            TEXT[1] = (byte) 0x11 ;
            TEXT[2] = (byte) 0x22 ;
            TEXT[3] = (byte) 0x33 ;
            TEXT[4] = (byte) 0x44 ;
            TEXT[5] = (byte) 0x55 ;
            
            proHdlr.appendTLV(TAG_TEXT_STRING, TEXT, (short)0, (short)6) ;
            
                        
            compareOffset = (short)2 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
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
        
        // Initialise compare buffer
        for (short i=0; i<(short)20; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        
        compareBuffer20[2] = DCS_8_BIT_DATA ;
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer20[(short)(i+3)] = (byte)i ;
        }
        compareBuffer20[3] = (byte)1;
        
        try {
                    
            compareOffset = (short)2 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
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
        
        // Initialise compare buffer
        for (short i=0; i<(short)20; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        
        compareBuffer20[2] = TAG_TEXT_STRING ;
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer20[(short)(i+2)] = (byte)i ;
        }
        compareBuffer20[17] = (byte)0x0D;
        compareBuffer20[18] = (byte)0x10;
        
        try {
                    
            compareOffset = (short)2 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer20, compareOffset) ;
                
            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 15 : Successful call : search tag 8Dh
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        // Initialise TEXT and compare buffer
        compareBuffer17[0] = DCS_8_BIT_DATA ;
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
            compareBuffer17[(short)(i+1)] = (byte)i ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            // Search tag 8Dh
            compareOffset = (short)0 ;
            result = proHdlr.findAndCompareValue((byte)0x8D, compareBuffer17, compareOffset) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 16 : Successful call : search tag 8Fh
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        try {
            
            // Append tag 0Fh
            proHdlr.appendTLV((byte)0x0F, TEXT, (short)0, (short)TEXT.length) ;

            // Search tag 8Fh
            compareOffset = (short)0 ;
            result = proHdlr.findAndCompareValue((byte)0x8F, TEXT, compareOffset) ;
                
            bRes = (result == (short)0) ;

        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        
        // --------------------------------------------
        // Test Case 17 : Successful call
        testCaseNb = (byte) 17 ;
        bRes = false ;
        
        // Initialise TEXT and compare buffer
        compareBuffer17[0] = DCS_8_BIT_DATA ;
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)(i+0xF0) ;
            compareBuffer17[(short)(i+1)] = (byte)(i+0xF0) ;
        }
        try {
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            compareOffset = (short)0 ;
            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, compareBuffer17, compareOffset) ;
               
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
    }
}
