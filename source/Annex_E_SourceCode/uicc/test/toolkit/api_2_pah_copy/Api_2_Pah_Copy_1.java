//-----------------------------------------------------------------------------
//Api_2_Pah_Copy_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_copy;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_copy
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Copy_1 extends TestToolkitApplet
{
    private byte[] dstBuffer5 = new byte[5] ;
    private byte[] dstBuffer9 = new byte[9] ;
    private byte[] dstBuffer10 = new byte[10] ;
    private byte[] dstBuffer15 = new byte[15] ;
    private byte[] compareBuffer = new byte[15] ;

    private static final byte TYPE = (byte) 0x41 ;
    private static final byte QUALIFIER = (byte) 0x42 ;
    private static final byte DST_DEVICE = (byte) 0x43 ;
    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Copy_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Copy_1  thisApplet = new Api_2_Pah_Copy_1();

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
        // Test Case 1 : Null as dstBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            try {
                proHdlr.copy(null, (short)0, (short)1) ;
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
            
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            try {
                proHdlr.copy(dstBuffer5, (short)6, (short)0) ;
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
                proHdlr.copy(dstBuffer5, (short)-1, (short)1) ;
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
                proHdlr.copy(dstBuffer5, (short)0, (short)6) ;
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
                proHdlr.copy(dstBuffer5, (short)3, (short)3) ;
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
                proHdlr.copy(dstBuffer5, (short)0, (short)-1) ;
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
                proHdlr.copy(dstBuffer10, (short)0, (short)dstBuffer10.length) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 8 : Successful call, dstBuffer is the whole buffer
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            result = proHdlr.copy(dstBuffer9, (short)0, (short)dstBuffer9.length) ;
            
            bRes = (result == (short)9) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 9 : Compare the buffer
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        // Initialise buffer
        compareBuffer[0] = (byte) 0x81 ;
        compareBuffer[1] = (byte) 0x03 ;
        compareBuffer[2] = (byte) 0x01 ;
        compareBuffer[3] = TYPE ;
        compareBuffer[4] = QUALIFIER ;
        compareBuffer[5] = (byte) 0x82 ;
        compareBuffer[6] = (byte) 0x02 ;
        compareBuffer[7] = (byte) 0x81 ;
        compareBuffer[8] = DST_DEVICE ;
        
        try {
                        
            result = javacard.framework.Util.arrayCompare(dstBuffer9, (short)0, compareBuffer, (short)0, (short)9) ;
            
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 10 : Successful call, dstBuffer is part of a buffer
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        // Initialise dstBuffer
        for (short i=0; i<(short)dstBuffer15.length; i++) {
            dstBuffer15[i] = (byte)i ;
        }
        
        try {
            
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            result = proHdlr.copy(dstBuffer15, (short)3, (short)9) ;
            
            bRes = (result == (short)12) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 11 : Compare the buffer
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<(short)compareBuffer.length; i++) {
            compareBuffer[i] = (byte)i ;
        }

        compareBuffer[3] = (byte) 0x81 ;
        compareBuffer[4] = (byte) 0x03 ;
        compareBuffer[5] = (byte) 0x01 ;
        compareBuffer[6] = TYPE ;
        compareBuffer[7] = QUALIFIER ;
        compareBuffer[8] = (byte) 0x82 ;
        compareBuffer[9] = (byte) 0x02 ;
        compareBuffer[10] = (byte) 0x81 ;
        compareBuffer[11] = DST_DEVICE ;
        
        try {
                        
            result = javacard.framework.Util.arrayCompare(dstBuffer15, (short)0, compareBuffer, (short)0, (short)15) ;
            
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 12 : Successful call, dstBuffer is part of a buffer
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        // Initialise dstBuffer
        for (short i=0; i<(short)dstBuffer15.length; i++) {
            dstBuffer15[i] = (byte)i ;
        }
        
        try {
            
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            result = proHdlr.copy(dstBuffer15, (short)3, (short)6) ;
            
            bRes = (result == (short)9) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 13 : Compare the buffer
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        // Initialise compareBuffer
        for (short i=0; i<(short)compareBuffer.length; i++) {
            compareBuffer[i] = (byte)i ;
        }

        // Only 6 bytes
        compareBuffer[3] = (byte) 0x81 ;
        compareBuffer[4] = (byte) 0x03 ;
        compareBuffer[5] = (byte) 0x01 ;
        compareBuffer[6] = TYPE ;
        compareBuffer[7] = QUALIFIER ;
        compareBuffer[8] = (byte) 0x82 ;
        
        try {
                        
            result = javacard.framework.Util.arrayCompare(dstBuffer15, (short)0, compareBuffer, (short)0, (short)15) ;
            
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


    }
}
