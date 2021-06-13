//-----------------------------------------------------------------------------
//Api_2_Prh_Cpts_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cpts;

import javacard.framework.*;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cpts
 *
 * @version 0.0.1 - 23 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Cpts_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    private byte[] ARRAY04 = new byte[0x04] ;
    private byte[] ARRAY7E = new byte[0x7E] ;
    private byte[] ARRAYFF = new byte[0xFF] ;

    private byte[] dstBuffer = null ;
    private byte[] src = new byte[0x100] ;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Cpts_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Cpts_1  thisApplet = new Api_2_Prh_Cpts_1();

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

        ProactiveResponseHandler proRespHdlr = null ;
    
        short result = (short) 0 ;
        
        
        // --------------------------------------------
        // Test Case 1 : Null dstBuffer
        
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // CopyTextString() with a null dstBuffer
                proRespHdlr.copyTextString(null, (short)0) ;
                
            } catch (NullPointerException e) {
                bRes = true ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 2 : dstOffset + text length > dstBuffer.length
        
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // CopyTextString() with a dstOffset + text length > dstBuffer.length
                proRespHdlr.copyTextString(ARRAY04, (short)2) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 3 : dstOffset < 0
        
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
                        
            try {
                // CopyTextString() with dstOffset < 0
                proRespHdlr.copyTextString(ARRAY04, (short)-1) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        

        
        
        // --------------------------------------------
        // Test Case 4 : unavailable Text String TLV
        
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            // Build and send a DISPLAY TEXT command
            proHdlr.initDisplayText((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                proRespHdlr.copyTextString(ARRAY04, (short)0) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 5 : successfull call with null Text String TLV
        
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        // Initialise dstBuffer and src
        dstBuffer = ARRAY04 ;
        
        for (short i=0; i<4; i++) {
            dstBuffer[i] = (byte)(i+0xF0) ;
            src[i] = (byte)(i+0xF0) ;
        }
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)02) ;
            
            bRes = (result == (short)2) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 6 : compare buffer
        
        testCaseNb = (byte) 6 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)4) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        
        
        // --------------------------------------------
        // Test Case 7 : successfull call with text length = 1
        
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        // Initialise dstBuffer
        dstBuffer = ARRAY04 ;
        
        for (short i=0; i<4; i++) {
            dstBuffer[i] = (byte)i ;
        }
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)00) ;
            
            bRes = (result == (short)1) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : compare buffer
        
        // Initialise src
        src[0] = (byte) 0x41 ;
        for (short i=1; i<4; i++) {
            src[i] = (byte)i ;
        }
        
        testCaseNb = (byte) 8 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)4) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 9 : successfull call with text length = 2
        
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        // Initialise dstBuffer
        dstBuffer = ARRAY04 ;
        
        for (short i=0; i<4; i++) {
            dstBuffer[i] = (byte)i ;
        }
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)2) ;
            
            bRes = (result == (short)4) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 10 : compare buffer
        
        // Initialise src
        src[2] = (byte) 0x42 ;
        src[3] = (byte) 0x43 ;
        for (short i=0; i<2; i++) {
            src[i] = (byte)i ;
        }
        
        testCaseNb = (byte) 10 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)4) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 11 : verify the TLV selected
        
        testCaseNb = (byte) 11 ;
        bRes = false ;
                
        try {

            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x03) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 12 : successfull call with text length = 7Eh
        
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        // Initialise dstBuffer
        dstBuffer = ARRAY7E ;
        
        for (short i=0; i<(short)0x7E; i++) {
            dstBuffer[i] = 0 ;
        }
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)0) ;
            
            bRes = (result == (short)0x7E) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 13 : compare buffer
        
        // Initialise src
        for (short i=0; i<(short)0x7E; i++) {
            src[i] = (byte)(i+1) ;
        }
        
        testCaseNb = (byte) 13 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)0x7E) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 14 : verify the TLV selected
        
        testCaseNb = (byte) 14 ;
        bRes = false ;
                
        try {

            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x7F) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 15 : successfull call with text length = 7Fh
        
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        // Initialise dstBuffer
        dstBuffer = ARRAYFF ;
        
        for (short i=0; i<(short)0xFF; i++) {
            dstBuffer[i] = (byte)i ;
        }
        
        try {
            
            // Build and send a GET INPUT command.
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)0x10) ;
            
            bRes = (result == (short)0x8F) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 16 : compare buffer
        
        // Initialise src
        for (short i=0; i<(short)0xFF; i++) {
            src[i] = (byte)i ;
        }
        
        for (short i=1; i<(short)0x80; i++) {
            src[(short)(i+0x0F)] = (byte)i;
        }
        
        testCaseNb = (byte) 16 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)0xFF) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 17 : successfull call with text length = EFh
        
        testCaseNb = (byte) 17 ;
        bRes = false ;
        
        // Initialise dstBuffer
        dstBuffer = ARRAYFF ;
        
        for (short i=0; i<(short)0xFF; i++) {
            dstBuffer[i] = 0 ;
        }
        
        try {
            
            // Build and send a GET INPUT command.
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)0) ;
            
            bRes = (result == (short)0xEF) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 18 : compare buffer
        
        // Initialise src
        for (short i=0; i<(short)0xFF; i++) {
            src[i] = 0 ;
        }
        for (short i=0; i<(short)0xEF; i++) {
            src[i] = (byte)(i+1) ;
        }
                
        testCaseNb = (byte) 18 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)0xFF) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // ---------------------------------------------------------------------------
        // Test Case 19 : successfull call with text length = 2 and 2 text string TLV
        
        testCaseNb = (byte) 19 ;
        bRes = false ;
        
        // Initialise dstBuffer
        dstBuffer = ARRAY04 ;
        
        for (short i=0; i<4; i++) {
            dstBuffer[i] = (byte)i ;
        }
        
        try {
            
            // Build and send a GET INPUT command
            proHdlr.initGetInput((byte)0, (byte)4, TEXT, (short)0, (short)TEXT.length, (short)0x00, (short)0xFF) ;
            proHdlr.send() ;
            
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Copy text string
            result = proRespHdlr.copyTextString(dstBuffer, (short)2) ;
            
            bRes = (result == (short)4) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 20 : compare buffer
        
        // Initialise src
        src[2] = (byte) 0x42 ;
        src[3] = (byte) 0x43 ;
        for (short i=0; i<2; i++) {
            src[i] = (byte)i ;
        }
        
        testCaseNb = (byte) 20 ;
        bRes = false ;
                
        try {

            result = Util.arrayCompare(src, (short)0, dstBuffer, (short)0, (short)4) ;
            
            bRes = (result == 0) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 21 : verify the TLV selected
        
        testCaseNb = (byte) 21 ;
        bRes = false ;
                
        try {

            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short)0x03) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
