//-----------------------------------------------------------------------------
//Api_2_Pah_Facybbs_Bss.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_facybbs_bss;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_facybbs_bss
 *
 * @version 0.0.1 - 15 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Facybbs_Bss_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;
    
    private byte[] TEXT = new byte[16] ;
   
    private byte[] dstBuffer5 = new byte[5];
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
    public Api_2_Pah_Facybbs_Bss_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Facybbs_Bss_1  thisApplet = new Api_2_Pah_Facybbs_Bss_1();

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

        byte occurence = (byte) 0;
        short valueOffset = (short) 0 ;
        short dstOffset = (short) 0 ;
        short dstLength = (short) 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Null as dstBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;

            occurence = (byte) 1 ;
            valueOffset = (short) 0 ;
            dstOffset = (short) 0 ;
            dstLength = (short) 1 ;

            try {
                proHdlr.findAndCopyValue(TAG_COMMAND_DETAILS, occurence, valueOffset, null, dstOffset, dstLength) ;
                
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

            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)6 ;
            dstLength = (short)0 ;
            
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
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
            
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)-1 ;
            dstLength = (short)1 ;
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : dstLength > dstBufferLength
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)0, dstBuffer5, (short)0, (short)6) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : dstOffset + dstLength > dstBuffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)0, dstBuffer5, (short)3, (short)3) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 6 : dstLength < 0
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)0, dstBuffer5, (short)0, (short)-1) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 7 : valueOffset >= Text String Length
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
                                    
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)5) ;
                        
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)7, dstBuffer15, (short)0, (short)0) ;
                
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
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)-1, dstBuffer15, (short)0, (short)1) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : dstLength > Text String length
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)0, dstBuffer15, (short)0, (short)7) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : valueOffset + dstLength > Text String Length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
                                    
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)2, dstBuffer15, (short)0, (short)5) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 11 : Unavailable Element
        testCaseNb = (byte) 11 ;
        bRes = false ;
        bRes2 = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            // Select a TLV
            proHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1) ;
            
            try {
                proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)2, (short)0, dstBuffer15, (short)0, (short)1) ;
                
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
        // Test Case 12 : Successful call
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {
            
            // Initialise buffer
            for (short i=0; i<(short)TEXT.length; i++) {
                TEXT[i] = (byte)i ;
            }
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)16) ;
                        
            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)0, dstBuffer17, (short)0, (short)17) ;
            
            bRes = (result == (short)17) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 13 : Compare buffer
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            
            // Initialise compare buffer
            compareBuffer[0] = DCS_8_BIT_DATA ;
            for (short i=0; i<(short)16; i++) {
                compareBuffer[(short)(i+1)] = (byte)i ;
            }
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, dstBuffer17, (short)0, (short)17) ;
            
            bRes = (result == 0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 14 : Successful call
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        try {
                                    
            for (short i=0; i<(short)20; i++) {
                dstBuffer20[i] = (byte)0x55 ;
            }
            
            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)2, dstBuffer20, (short)3, (short)12) ;
            
            bRes = (result == (short)15) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 15 : Compare buffer
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        try {
            
            // Initialise compare buffer
            for (short i=0; i<(short)20; i++) {
                compareBuffer[i] = (byte)0x55 ;
            }
            for (short i=3; i<(short)15; i++) {
                compareBuffer[i] = (byte)(i-2) ;
            }
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, dstBuffer20, (short)0, (short)20) ;
            
            bRes = (result == 0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 16 : Successful call with 2 Text String TLV
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        try {

            // Append a Text String TLV
            TEXT[0] = (byte) 0x00 ;
            TEXT[1] = (byte) 0x11 ;
            TEXT[2] = (byte) 0x22 ;
            TEXT[3] = (byte) 0x33 ;
            TEXT[4] = (byte) 0x44 ;
            TEXT[5] = (byte) 0x55 ;
            
            proHdlr.appendTLV(TAG_TEXT_STRING, TEXT, (short)0, (short)6) ;
            
            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)1, (short)0, dstBuffer17, (short)0, (short)17) ;
            
            bRes = (result == (short)17) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 17 : Compare buffer
        testCaseNb = (byte) 17 ;
        bRes = false ;
        
        try {
            
            // Initialise compare buffer
            compareBuffer[0] = DCS_8_BIT_DATA ;
            for (short i=0; i<(short)16; i++) {
                compareBuffer[(short)(i+1)] = (byte)i ;
            }
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, dstBuffer17, (short)0, (short)17) ;
            
            bRes = (result == 0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 18 : Successful call with 2 Text String TLV
        testCaseNb = (byte) 18 ;
        bRes = false ;
        
        try {

            result = proHdlr.findAndCopyValue(TAG_TEXT_STRING, (byte)2, (short)0, dstBuffer17, (short)0, (short)6) ;
            
            bRes = (result == (short)6) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 19 : Compare buffer
        testCaseNb = (byte) 19 ;
        bRes = false ;
        
        try {

            compareBuffer[0] = (byte) 0x00 ;
            compareBuffer[1] = (byte) 0x11 ;
            compareBuffer[2] = (byte) 0x22 ;
            compareBuffer[3] = (byte) 0x33 ;
            compareBuffer[4] = (byte) 0x44 ;
            compareBuffer[5] = (byte) 0x55 ;
            
            result = javacard.framework.Util.arrayCompare(compareBuffer, (short)0, dstBuffer17, (short)0, (short)6) ;
            
            bRes = (result == 0) ;
                
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;



        // --------------------------------------------
        // Test Case 20 : Successful call, tag = 8Dh
        testCaseNb = (byte) 20 ;
        bRes = false ;
        
        // Initialise TEXT
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short) TEXT.length) ;
            
            result = proHdlr.findAndCopyValue((byte)0x8D, (byte)1, (short)0, dstBuffer17, (short)0, (short)17) ;
                
            bRes = (result == (short)17) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 21 : Compare buffer
        testCaseNb = (byte) 21 ;
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
        // Test Case 22 : Successful call, tag = 8Fh
        testCaseNb = (byte) 22 ;
        bRes = false ;
        
        // Initialise TEXT
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        try {
            
            // Append TLV with tag 8Fh
            proHdlr.appendTLV((byte)0x8F, TEXT, (short)0, (short)TEXT.length) ;
            
            // Search tag 8Fh
            result = proHdlr.findAndCopyValue((byte)0x8F, (byte)1, (short)0, dstBuffer17, (short)0, (short)16) ;                
            bRes = (result == (short)16) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 23 : Compare buffer
        testCaseNb = (byte) 23 ;
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

        // --------------------------------------------
        // Test Case 24 : Invalid Parameter
        testCaseNb = (byte) 24;
        bRes = false ;
        
        try {
            // Search tag 8Fh and occurence set to 0
            result = proHdlr.findAndCopyValue((byte)0x8F, (byte)0, (short)0, dstBuffer17, (short)0, (short)16) ;
            bRes = false;
        }
        catch (ToolkitException e)    {    
            if(e.getReason() == ToolkitException.BAD_INPUT_PARAMETER)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
