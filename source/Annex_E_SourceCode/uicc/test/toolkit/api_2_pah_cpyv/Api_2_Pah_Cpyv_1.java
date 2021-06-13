//-----------------------------------------------------------------------------
//Api_2_Pah_Cpyv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_cpyv;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_cpyv
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Cpyv_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;

    private byte[] TEXT = new byte[16] ;
    private byte[] dstBuffer5 = new byte[5] ;
    private byte[] dstBuffer15 = new byte[15] ;
    private byte[] dstBuffer20 = new byte[20] ;
    private byte[] compareBuffer = new byte[20] ;
    
    private byte DCS_8_BIT_DATA = (byte) 0x04;
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Cpyv_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Cpyv_1  thisApplet = new Api_2_Pah_Cpyv_1();

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
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            // Select a TLV
            proHdlr.findTLV((byte)0x01, (byte)1) ;
            
            try {
                proHdlr.copyValue((short)0, null, (short)0, (short)1) ;
                
            } catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : dstOffset >= dstBuffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)15) ;
            
            // Select Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;
            
            try {
                proHdlr.copyValue((short)0, dstBuffer5, (short)6, (short)0) ;
                
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
                proHdlr.copyValue((short)0, dstBuffer5, (short)-1, (short)1) ;
                
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
                proHdlr.copyValue((short)0, dstBuffer5, (short)0, (short)6) ;
                
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
                proHdlr.copyValue((short)0, dstBuffer5, (short)3, (short)3) ;
                
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
                proHdlr.copyValue((short)0, dstBuffer5, (short)3, (short)3) ;
                
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
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            try {
                proHdlr.copyValue((short)7, dstBuffer15, (short)0, (short)0) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
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
                        
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;

            try {
                proHdlr.copyValue((short)-1, dstBuffer15, (short)0, (short)1) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
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
                        
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;

            try {
                proHdlr.copyValue((short)0, dstBuffer15, (short)0, (short)7) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : valueOffset + dstLength > Text String length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
                        
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;

            try {
                proHdlr.copyValue((short)2, dstBuffer15, (short)0, (short)5) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 11 : Unavailable element
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
                        
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
                                   
            try {
                proHdlr.copyValue((short)0, dstBuffer15, (short)0, (short)1) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 12 : Successfull call
        testCaseNb = (byte) 12 ;
        bRes = false ;

        // Initialise Text buffer
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        // Initialise dstBuffer
        for (short i=0; i<dstBuffer20.length; i++) {
            dstBuffer20[i] = (byte)0x55 ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Search Text String TLV
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)0x01) ;
            
            // Copy TLV
            result = proHdlr.copyValue((short)0, dstBuffer20, (short)0, (short)17) ;

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

        // Initialise compare buffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)0x55 ;
        }
        compareBuffer[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<17; i++) {
            compareBuffer[i] = (byte)(i-1) ;
        }
                
        try {
                        
            result = javacard.framework.Util.arrayCompare(dstBuffer20, (short)0, compareBuffer, (short)0, (short)20) ;

            bRes = (result == (byte)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 14 : Successfull call
        testCaseNb = (byte) 14 ;
        bRes = false ;

        // Initialise Text buffer
        for (short i=0; i<TEXT.length; i++) {
            TEXT[i] = (byte)i ;
        }
        
        // Initialise dstBuffer
        for (short i=0; i<dstBuffer20.length; i++) {
            dstBuffer20[i] = (byte)0x55 ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Search Text String TLV
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)0x01) ;
            
            // Copy TLV
            result = proHdlr.copyValue((short)2, dstBuffer20, (short)3, (short)12) ;

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

        // Initialise compare buffer
        for (short i=0; i<compareBuffer.length; i++) {
            compareBuffer[i] = (byte)0x55 ;
        }
        for (short i=(short)3; i<15; i++) {
            compareBuffer[i] = (byte)(i-2) ;
        }
                
        try {
                        
            result = javacard.framework.Util.arrayCompare(dstBuffer20, (short)0, compareBuffer, (short)0, (short)20) ;

            bRes = (result == (byte)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
