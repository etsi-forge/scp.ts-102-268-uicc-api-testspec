//-----------------------------------------------------------------------------
//Api_2_Pah_Cpai_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cpai;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cpai
 *
 * @version 0.0.1 - 22 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Cpai_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;

    private byte[] dstBuffer5 = new byte[5] ;
    private byte[] dstBuffer6 = new byte[6] ;
    private byte[] dstBuffer7 = new byte[7] ;
    private byte[] dstBuffer9 = new byte[9] ;
    private byte[] dstBuffer10 = new byte[10] ;
    private byte[] dstBuffer20 = new byte[20] ;
    private byte[] dstBufferF2 = new byte[0xF2] ;

    private byte[] src = new byte[256] ;
    public final byte DCS_8_BIT_DATA = (byte)0x04;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Cpai_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Cpai_1  thisApplet = new Api_2_Prh_Cpai_1();

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
        // Test Case 1 : NULL as parameter to dstBuffer
        
        testCaseNb = (byte) 1 ;
        
        bRes = false ;
        try {
            
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                // copyAdditionalInformation() with null buffer
                proRespHdlr.copyAdditionalInformation(null, (short)0, (short)1) ;
                
            } catch (NullPointerException e) {
                bRes = true ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : dstOffset > dstBuffer.length
        
        testCaseNb = (byte) 2 ;
        bRes = false ;
        try {
                proRespHdlr.copyAdditionalInformation(dstBuffer10, (short)11, (short)0) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : dstOffset < 0
        
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
                proRespHdlr.copyAdditionalInformation(dstBuffer10, (short)-1, (short)1) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : dstLength > dstBuffer.length
        
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
                proRespHdlr.copyAdditionalInformation(dstBuffer10, (short)0, (short)11) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : dstOffset + dstLength > dstBuffer.length
        
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
                proRespHdlr.copyAdditionalInformation(dstBuffer10, (short)6, (short)5) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 6 : dstLength < 0
        
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
                proRespHdlr.copyAdditionalInformation(dstBuffer10, (short)6, (short)-1) ;
         }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 7 : Successfull call, dstBuffer is the whole buffer
        
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Successfull call, dstBuffer is the whole buffer
            result = proRespHdlr.copyAdditionalInformation(dstBuffer5, (short)0, (short)5) ;
            
            // Verify the result
            bRes = (result == (short)5) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : Compare dstBuffer
        
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            // Response from the mobile
            src[0] = (byte) 0x01 ;
            src[1] = (byte) 0x23 ;
            src[2] = (byte) 0x45 ;
            src[3] = (byte) 0x67 ;
            src[4] = (byte) 0x89 ;
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBuffer5, (short)0, (short)5) ;
            
            bRes = (result == (short) 0) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 9 : Verify the selected TLV
        
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {

            // Verify the TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short) 0x06) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 10 : Successfull call, dstBuffer is part of a buffer
        
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Successfull call, dstBuffer is the whole buffer
            result = proRespHdlr.copyAdditionalInformation(dstBuffer7, (short)2, (short)5) ;
            
            // Verify the result
            bRes = (result == (short)7) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 11 : Compare dstBuffer
        
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {

            // Response from the mobile
            src[0] = (byte) 0xAB ;
            src[1] = (byte) 0xCD ;
            src[2] = (byte) 0xEF ;
            src[3] = (byte) 0xFE ;
            src[4] = (byte) 0xDC ;
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBuffer7, (short)2, (short)5) ;
            
            bRes = (result == (short) 0) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 12 : Successfull call, dstBuffer is part of a buffer
        
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Successfull call, dstBuffer is the whole buffer
            result = proRespHdlr.copyAdditionalInformation(dstBuffer7, (short)0, (short)5) ;
            
            // Verify the result
            bRes = (result == (short)5) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 13 : Compare dstBuffer
        
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {

            // Response from the mobile
            src[0] = (byte) 0xFE ;
            src[1] = (byte) 0xDC ;
            src[2] = (byte) 0xBA ;
            src[3] = (byte) 0x98 ;
            src[4] = (byte) 0x76 ;
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBuffer7, (short)0, (short)5) ;
            
            bRes = (result == (short) 0) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 14 : Successfull call, dstBuffer is part of a buffer
        
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Successfull call, dstBuffer is the whole buffer
            result = proRespHdlr.copyAdditionalInformation(dstBuffer9, (short)2, (short)5) ;
            
            // Verify the result
            bRes = (result == (short)7) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 15 : Compare dstBuffer
        
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        try {

            // Response from the mobile
            src[0] = (byte) 0x00 ;
            src[1] = (byte) 0x11 ;
            src[2] = (byte) 0x22 ;
            src[3] = (byte) 0x33 ;
            src[4] = (byte) 0x44 ;
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBuffer9, (short)2, (short)5) ;
            
            bRes = (result == (short) 0) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 16 : Successfull call, F2h bytes in the response
        
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Successfull call, dstBuffer is the whole buffer
            result = proRespHdlr.copyAdditionalInformation(dstBufferF2, (short)0, (short)0xF2) ;
            
            // Verify the result
            bRes = (result == (short)0xF2) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 17 : Compare dstBuffer
        
        testCaseNb = (byte) 17 ;
        bRes = false ;
        
        try {

            // Response from the mobile
            for (short i=(short)0; i<(short)dstBufferF2.length; i++) {
                src[i] = (byte)i ;
            }
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBufferF2, (short)0, (short)0xF2) ;
            
            bRes = (result == (short) 0) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 18 : Verify the selected TLV
        
        testCaseNb = (byte) 18 ;
        bRes = false ;
        
        try {

            // Verify the TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short) 0xF3) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 19 : dstLength > data available
        
        testCaseNb = (byte) 19 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response (5 additional bytes)
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // dstLength > data available ==> ToolkitException.OUT_OF_TLV_BOUNDARIES
                proRespHdlr.copyAdditionalInformation(dstBuffer6, (short)0, (short)6) ;
                
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true ;
                }
            }
                        
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 20 : Verify whole dstBuffer
        
        testCaseNb = (byte) 20 ;
        bRes = false ;
        
        try {

            // Initialise dstBuffer
            for (short i=(short)0; i<(short)dstBuffer20.length; i++) {
                dstBuffer20[i] = (byte)i ;
            }
                        
            // Response from the mobile
            for (short i=(short)0; i<(short)dstBuffer20.length; i++) {
                src[i] = (byte)i ;
            }
            
            src[5] = (byte) 0x00 ;
            src[6] = (byte) 0x11 ;
            src[7] = (byte) 0x22 ;
            src[8] = (byte) 0x33 ;
            src[9] = (byte) 0x44 ;
            
                   
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Successfull call
            proRespHdlr.copyAdditionalInformation(dstBuffer20, (short)5, (short)5) ;
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBuffer20, (short)0, (short)20) ;
            
            bRes = (result == (short) 0) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 21 : Successfull call, Terminal Response with 2 Result TLV
        
        testCaseNb = (byte) 21 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Clear the buffer
            for (short i=0; i<(short)5; i++) {
                dstBuffer5[i] = 0 ;
            }
            
            result = proRespHdlr.copyAdditionalInformation(dstBuffer5, (short)0, (short)5) ;
            
            // Verify the result
            bRes = (result == (short)5) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 22 : Compare dstBuffer
        
        testCaseNb = (byte) 22 ;
        bRes = false ;
        
        try {

            // Response from the mobile
            src[0] = (byte) 0x01 ;
            src[1] = (byte) 0x23 ;
            src[2] = (byte) 0x45 ;
            src[3] = (byte) 0x67 ;
            src[4] = (byte) 0x89 ;
            
            // Compare the buffer
            result = Util.arrayCompare(src, (short)0, dstBuffer5, (short)0, (short)5) ;
            
            bRes = (result == (short) 0) ;
            
        } 
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 23 : Verify the selected TLV
        
        testCaseNb = (byte) 23 ;
        bRes = false ;
        
        try {

            // Verify the TLV
            result = proRespHdlr.getValueLength() ;
            
            bRes = (result == (short) 0x06) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 24 : No Result TLV in Response
        
        testCaseNb = (byte) 24 ;
        bRes = false ;
        
        try {
                        
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            // An exception is thrown by send() as there is no General Result TLV
            try {
                proHdlr.send() ;
            } 
            catch (Exception e) {
            }
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            try {                        
                result = proRespHdlr.copyAdditionalInformation(dstBuffer5, (short)0, (short)1) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;        
    }
}
