//-----------------------------------------------------------------------------
//Api_2_Pah_Facrbbs_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_facrbbs_bss;

import javacard.framework.Util;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_facrbbs_bss
 *
 * @version 0.0.1 - 17 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Facrbbs_Bss_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;
    
    private byte[] TEXT = new byte[16] ;
   
    private byte[] compareBuffer5 = new byte[5] ;
    private byte[] compareBuffer15 = new byte[15] ;
    private byte[] compareBuffer16 = new byte[16] ;
    private byte[] compareBuffer17 = new byte[17] ;
    private byte[] compareBuffer20 = new byte[20] ;
    private byte[] compareBuffer = new byte[20] ;
    
    private byte DCS_8_BIT_DATA = (byte) 0x04;

    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Facrbbs_Bss_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Facrbbs_Bss_1  thisApplet = new Api_2_Pah_Facrbbs_Bss_1();

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

        byte occurence = (byte) 0;
        short valueOffset = (short) 0 ;
        short compareOffset = (short) 0 ;
        short compareLength = (short) 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Null as compareBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;

            occurence = (byte) 1 ;
            valueOffset = (short) 0 ;
            compareOffset = (short) 0 ;
            compareLength = (short) 1 ;

            try {
                proHdlr.findAndCompareValue(TAG_COMMAND_DETAILS, occurence, valueOffset, null, compareOffset, compareLength) ;
                
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

            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)5 ;
            compareLength = (short)1 ;
            
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer5, compareOffset, compareLength) ;
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
            
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)-1 ;
            compareLength = (short)1 ;                                    

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : compareLength > compareBufferLength
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {

            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)6 ;    
            
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : compareOffset + compareLength > compareBuffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)3 ;
            compareLength = (short)3 ;    
            
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 6 : compareLength < 0
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {

            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)-1 ;

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 7 : valueOffset > Text String Length
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
                                    
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)5) ;
                        
            occurence = (byte)1 ;
            valueOffset = (short)6 ;
            compareOffset = (short)0 ;
            compareLength = (short)1 ;

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 8 : valueOffset < 0
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
                                    
            occurence = (byte)1 ;
            valueOffset = (short)-1 ;
            compareOffset = (short)0 ;
            compareLength = (short)1 ;

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : compareLength > Text String length
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {
                                    
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)7 ;

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : valueOffset + compareLength > Text String Length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
                                    
            occurence = (byte)1 ;
            valueOffset = (short)2 ;
            compareOffset = (short)0 ;
            compareLength = (short)5 ;

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 11 : Invalid parameter
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
                                    
            occurence = (byte)0 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)1 ;

            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.BAD_INPUT_PARAMETER) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 12 : Unavailable Element
        testCaseNb = (byte) 12 ;
        bRes = false ;
        bRes2 = false ;
        
        try {
                                    
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)5) ;
                        
            // Select a TLV
            proHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1) ;
                        
            occurence = (byte)2 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)1 ;

            // FindAndCompareValue
            try {
                proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
            } catch (ToolkitException e) {
                bRes2 = (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
            // Verify there is no selected TLV
            try {
                proHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                bRes = (bRes2) && (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 13 : Successful call
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            
            // Initialise buffers
            compareBuffer17[0] = DCS_8_BIT_DATA ;
            for (short i=0; i<(short)TEXT.length; i++) {
                TEXT[i] = (byte)i ;
                compareBuffer17[(short)(i+1)] = (byte)i ;
            }
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)16) ;
                        
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer17, compareOffset, compareLength) ;
            
            bRes = (result == (short)00) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 14 : Verify current TLV
        testCaseNb = (byte) 14 ;
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
        // Test Case 15 : Successful call
        testCaseNb = (byte) 15;
        bRes = false ;
        
        try {
            
            // Initialise buffers
            compareBuffer17[0] = DCS_8_BIT_DATA ;
            for (short i=0; i<(short)TEXT.length; i++) {
                compareBuffer17[(short)(i+1)] = (byte)i ;
            }
            compareBuffer17[16] = 0x10 ;
                                    
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer17, compareOffset, compareLength) ;
            
            bRes = (result == (short)-1) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 16 : Successful call
        testCaseNb = (byte) 16;
        bRes = false ;
        
        try {
            
            // Initialise buffers
            compareBuffer17[0] = DCS_8_BIT_DATA ;
            for (short i=0; i<(short)TEXT.length; i++) {
                compareBuffer17[(short)(i+1)] = (byte)i ;
            }
            compareBuffer17[0] = 0x03 ;
                                    
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer17, compareOffset, compareLength) ;
            
            bRes = (result == (short)+1) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 17 : Successful call
        testCaseNb = (byte) 17;
        bRes = false ;
        
        try {
            
            // Initialise buffers
            Util.arrayFillNonAtomic(compareBuffer20,(short)0,(short)compareBuffer20.length,(byte)0x55);
            for (short i=(short)3; i<(short)15; i++) {
                compareBuffer20[i] = (byte)(i-2) ;
            }
                                    
            occurence = (byte)1 ;
            valueOffset = (short)2 ;
            compareOffset = (short)3 ;
            compareLength = (short)12 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer20, compareOffset, compareLength) ;
            
            bRes = (result == (short)0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 18 : Successful call
        testCaseNb = (byte) 18;
        bRes = false ;
        
        try {
            
            // Initialise buffers
            Util.arrayFillNonAtomic(compareBuffer20,(short)0,(short)compareBuffer20.length,(byte)0x55);
            for (short i=(short)3; i<(short)15; i++) {
                compareBuffer20[i] = (byte)(i-2) ;
            }
            compareBuffer20[3] = (byte)0x02 ;
            compareBuffer20[4] = (byte)0x01 ;
                                    
            occurence = (byte)1 ;
            valueOffset = (short)2 ;
            compareOffset = (short)3 ;
            compareLength = (short)12 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer20, compareOffset, compareLength) ;
            
            bRes = (result == (short)-1) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 19 : Successful call
        testCaseNb = (byte) 19;
        bRes = false ;
        
        try {
            
            // Initialise buffers
            Util.arrayFillNonAtomic(compareBuffer20,(short)0,(short)compareBuffer20.length,(byte)0x55);
            for (short i=(short)3; i<(short)15; i++) {
                compareBuffer20[i] = (byte)(i-2) ;
            }
            compareBuffer20[13] = (byte)0x0A ;
            compareBuffer20[14] = (byte)0x0D ;
                                    
            occurence = (byte)1 ;
            valueOffset = (short)2 ;
            compareOffset = (short)3 ;
            compareLength = (short)12 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer20, compareOffset, compareLength) ;
            
            bRes = (result == (short)+1) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 20 : Successful call
        testCaseNb = (byte)20 ;
        bRes = false ;
        
        try {

            // Append a 2nd Text String TLV
            TEXT[0] = (byte)0x00 ;
            TEXT[1] = (byte)0x11 ;
            TEXT[2] = (byte)0x22 ;
            TEXT[3] = (byte)0x33 ;
            TEXT[4] = (byte)0x44 ;
            TEXT[5] = (byte)0x55 ;
            
            proHdlr.appendTLV(TAG_TEXT_STRING, TEXT, (short)0, (short)6) ;
            
            // Initialise buffers
            compareBuffer17[0] = DCS_8_BIT_DATA ;
            for (short i=0; i<(short)TEXT.length; i++) {
                compareBuffer17[(short)(i+1)] = (byte)i ;
            }
                                    
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer17, compareOffset, compareLength) ;
            
            bRes = (result == (short)0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 21 : Successful call
        testCaseNb = (byte)21 ;
        bRes = false ;
        
        try {

            // Initialise buffers
            compareBuffer[0] = (byte)0x00 ;
            compareBuffer[1] = (byte)0x11 ;
            compareBuffer[2] = (byte)0x22 ;
            compareBuffer[3] = (byte)0x33 ;
            compareBuffer[4] = (byte)0x44 ;
            compareBuffer[5] = (byte)0x55 ;
                                    
            occurence = (byte)2 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)6 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer, compareOffset, compareLength) ;
            
            bRes = (result == (short)0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 22 : Successful call
        testCaseNb = (byte)22 ;
        bRes = false ;
        
        try {

            // Initialise buffers
            compareBuffer[0] = (byte)0x00 ;
            compareBuffer[1] = (byte)0x11 ;
            compareBuffer[2] = (byte)0x22 ;
            compareBuffer[3] = (byte)0x33 ;
            compareBuffer[4] = (byte)0x44 ;
            compareBuffer[5] = (byte)0x66 ;
                                    
            occurence = (byte)2 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)6 ;

            result = proHdlr.findAndCompareValue(TAG_TEXT_STRING, occurence, valueOffset, compareBuffer, compareOffset, compareLength) ;
            
            bRes = (result == (short)-1) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 23 : Successful call : search tag 8Dh
        testCaseNb = (byte) 23 ;
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
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;

            result = proHdlr.findAndCompareValue((byte)0x8D, occurence, valueOffset, compareBuffer17, compareOffset, compareLength) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 24 : Successful call : search tag 8Fh
        testCaseNb = (byte) 24 ;
        bRes = false ;
        
        try {
            
            // Append tag 0Fh
            proHdlr.appendTLV((byte)0x0F, TEXT, (short)0, (short)TEXT.length) ;

            // Search tag 8Fh
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)16 ;

            result = proHdlr.findAndCompareValue((byte)0x8F, occurence, valueOffset, TEXT, compareOffset, compareLength) ;
                
            bRes = (result == (short)0) ;

        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 25 : Successful call
        testCaseNb = (byte) 25 ;
        bRes = false ;
        
        // Initialise compare buffer
        for (short i=0; i<TEXT.length; i++) {
            compareBuffer16[(short)(i)] = (byte)i ;
        }
        compareBuffer16[1] = (byte)(0x99) ;
 
         try {
            // Search tag 8Fh
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)16 ;
            result = proHdlr.findAndCompareValue((byte)0x8F, occurence, valueOffset, compareBuffer16, compareOffset, compareLength) ;
            
            bRes = (result == (short)1) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        
    }
}
