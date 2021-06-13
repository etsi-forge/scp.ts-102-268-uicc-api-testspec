//-----------------------------------------------------------------------------
//Api_2_Prh_Copy_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_copy;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_copy
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Copy_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;

    private byte[] dstBuffer5 = new byte[5] ;
    private byte[] dstBuffer13 = new byte[13] ;
    private byte[] dstBuffer12 = new byte[12] ;
    private byte[] dstBuffer20 = new byte[20] ;
    private byte[] compareBuffer = new byte[20] ;

    public final byte DCS_8_BIT_DATA = (byte)0x04;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Copy_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Copy_1  thisApplet = new Api_2_Prh_Copy_1();

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
        
        // Response handler
        ProactiveResponseHandler proRespHdlr = null;
        
        short dstOffset = (short)0 ;
        short dstLength = (short)0 ;
        
        short result = 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Build and send a PRO command / NULL buffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Null as dstBuffer
            try {
                dstOffset = (short)0 ;
                dstLength = (short)1 ;
                proRespHdlr.copy(null, dstOffset, dstLength) ;
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
            
            try {
                dstOffset = (short)6 ;
                dstLength = (short)0 ;
                proRespHdlr.copy(dstBuffer5, dstOffset, dstLength) ;
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
                dstOffset = (short)-1 ;
                dstLength = (short)1 ;
                proRespHdlr.copy(dstBuffer5, dstOffset, dstLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : dstLength > dstBuffer.length
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            try {
                dstOffset = (short)0 ;
                dstLength = (short)6 ;
                proRespHdlr.copy(dstBuffer5, dstOffset, dstLength) ;
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
                dstOffset = (short)3 ;
                dstLength = (short)3 ;
                proRespHdlr.copy(dstBuffer5, dstOffset, dstLength) ;
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
                dstOffset = (short)0 ;
                dstLength = (short)-1 ;
                proRespHdlr.copy(dstBuffer5, dstOffset, dstLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 7 : dstLength > length of the simple TLV list
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            
            try {
                dstOffset = (short)0 ;
                dstLength = (short)13 ;
                proRespHdlr.copy(dstBuffer13, dstOffset, dstLength) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 8 : Successful call, whole buffer
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {

            dstOffset = (short)0 ;
            dstLength = (short)12 ;
            result = proRespHdlr.copy(dstBuffer12, dstOffset, dstLength) ;
            
            bRes = (result == (short)(dstOffset + dstLength)) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : Compare the buffer
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {
            // Initialise the buffer
            compareBuffer[0] = (byte)0x81 ; compareBuffer[1] = (byte)0x03 ; 
            compareBuffer[2] = (byte)0x01 ; compareBuffer[3] = (byte)0x21 ; compareBuffer[4] = (byte)0x00 ;
            compareBuffer[5] = (byte)0x02 ; compareBuffer[6] = (byte)0x02 ;
            compareBuffer[7] = (byte)0x82 ; compareBuffer[8] = (byte)0x81 ;
            compareBuffer[9] = (byte)0x03 ; compareBuffer[10] = (byte)0x01 ; compareBuffer[11] = (byte)0x00 ;

            // Compare buffers
            result = Util.arrayCompare(compareBuffer, (short)0, 
                       dstBuffer12, (short)0, (short)12) ;
    
            bRes = (result == (short)0) ;
        } 
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : Successful call, part of a buffer
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        // Initialise buffers
        for (short i=0; i<(short)20; i++) {
            dstBuffer20[i] = (byte)i ;
            compareBuffer[i] = (byte)i ;
        }
        
        try {

            dstOffset = (short)3 ;
            dstLength = (short)12 ;
            result = proRespHdlr.copy(dstBuffer20, dstOffset, dstLength) ;
            
            bRes = (result == (short)(dstOffset + dstLength)) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 11 : Compare the buffer
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
            // Initialise the buffer
            compareBuffer[3] = (byte)0x81 ; compareBuffer[4] = (byte)0x03 ; 
            compareBuffer[5] = (byte)0x01 ; compareBuffer[6] = (byte)0x21 ; compareBuffer[7] = (byte)0x00 ;
            compareBuffer[8] = (byte)0x02 ; compareBuffer[9] = (byte)0x02 ;
            compareBuffer[10] = (byte)0x82 ; compareBuffer[11] = (byte)0x81 ;
            compareBuffer[12] = (byte)0x03 ; compareBuffer[13] = (byte)0x01 ; compareBuffer[14] = (byte)0x00 ;

            // Compare buffers
            result = Util.arrayCompare(compareBuffer, (short)0, 
                       dstBuffer20, (short)0, (short)20) ;
    
            bRes = (result == (short)0) ;
        } 
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 12 : Successful call, part of a buffer
        testCaseNb = (byte)12 ;
        bRes = false ;
        
        // Initialise buffers
        for (short i=0; i<(short)20; i++) {
            dstBuffer20[i] = (byte)i ;
            compareBuffer[i] = (byte)i ;
        }
        
        try {

            dstOffset = (short)3 ;
            dstLength = (short)9 ;
            result = proRespHdlr.copy(dstBuffer20, dstOffset, dstLength) ;
            
            bRes = (result == (short)(dstOffset + dstLength)) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 13 : Compare the buffer
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            // Initialise the buffer
            compareBuffer[3] = (byte)0x81 ; compareBuffer[4] = (byte)0x03 ; 
            compareBuffer[5] = (byte)0x01 ; compareBuffer[6] = (byte)0x21 ; compareBuffer[7] = (byte)0x00 ;
            compareBuffer[8] = (byte)0x02 ; compareBuffer[9] = (byte)0x02 ;
            compareBuffer[10] = (byte)0x82 ; compareBuffer[11] = (byte)0x81 ;

            // Compare buffers
            result = Util.arrayCompare(compareBuffer, (short)0, 
                       dstBuffer20, (short)0, (short)20) ;
    
            bRes = (result == (short)0) ;
        } 
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
