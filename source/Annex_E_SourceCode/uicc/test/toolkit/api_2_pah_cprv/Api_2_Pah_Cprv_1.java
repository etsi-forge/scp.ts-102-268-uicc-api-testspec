//-----------------------------------------------------------------------------
//Api_2_Pah_Cprv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_cprv;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_cprv
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Cprv_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;

    private byte[] TEXT = new byte[16] ;
    private byte[] compareBuffer5 = new byte[5] ;
    private byte[] compareBuffer15 = new byte[15] ;
    private byte[] compareBuffer20 = new byte[20] ;
    
    private byte DCS_8_BIT_DATA = (byte) 0x04;
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Cprv_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Cprv_1  thisApplet = new Api_2_Pah_Cprv_1();

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
        // Test Case 1 : Null as compareBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            // Select a TLV
            proHdlr.findTLV((byte)0x01, (byte)1) ;
            
            try {
                proHdlr.compareValue((short)0, null, (short)0, (short)1) ;
                
            } catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : compareOffset >= compareBuffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)15) ;
            
            // Select Text String TLV
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            try {
                proHdlr.compareValue((short)0, compareBuffer5, (short)6, (short)0) ;
                
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
                proHdlr.compareValue((short)0, compareBuffer5, (short)-1, (short)1) ;
                
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : compareLength > compareBuffer.length
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            try {
                proHdlr.compareValue((short)0, compareBuffer5, (short)0, (short)6) ;
                
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
            
            try {
                proHdlr.compareValue((short)0, compareBuffer5, (short)3, (short)3) ;
                
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
            
            try {
                proHdlr.compareValue((short)0, compareBuffer5, (short)3, (short)3) ;
                
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
                proHdlr.compareValue((short)7, compareBuffer15, (short)0, (short)0) ;
                
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
                proHdlr.compareValue((short)-1, compareBuffer15, (short)0, (short)1) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
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
                        
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;

            try {
                proHdlr.compareValue((short)0, compareBuffer15, (short)0, (short)7) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : valueOffset + compareLength > Text String length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
                        
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;

            try {
                proHdlr.compareValue((short)2, compareBuffer15, (short)0, (short)5) ;
                
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
                proHdlr.compareValue((short)0, compareBuffer15, (short)0, (short)1) ;
                
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
        
        // Initialise compareBuffer
        compareBuffer20[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)(i-1) ;
        }
        
        try {
            
            // Initialise handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Search Text String TLV
            proHdlr.findTLV(TAG_TEXT_STRING, (byte)0x01) ;
            
            // Compare TLV
            result = proHdlr.compareValue((short)0, compareBuffer20, (short)0, (short)17) ;

            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 13 : Successfull call
        testCaseNb = (byte) 13 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer20[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)(i-1) ;
        }
        compareBuffer20[16] = (byte)0x10 ;
        
        try {
            
            // Compare TLV
            result = proHdlr.compareValue((short)0, compareBuffer20, (short)0, (short)17) ;

            bRes = (result == (short)-1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 14 : Successfull call
        testCaseNb = (byte) 14 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer20[0] = (byte) (DCS_8_BIT_DATA - 1) ;
        for (short i=1; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)(i-1) ;
        }
        
        try {
            
            // Compare TLV
            result = proHdlr.compareValue((short)0, compareBuffer20, (short)0, (short)17) ;

            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 15 : Successfull call
        testCaseNb = (byte) 15 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        for (short i=3; i<15; i++) {
            compareBuffer20[i] = (byte)(i-2) ;
        }
        
        try {
            
            // Compare TLV
            result = proHdlr.compareValue((short)2, compareBuffer20, (short)3, (short)12) ;

            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 16 : Successfull call
        testCaseNb = (byte) 16 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        for (short i=3; i<15; i++) {
            compareBuffer20[i] = (byte)(i-2) ;
        }
        compareBuffer20[3] = (byte) 0x02 ;
        compareBuffer20[4] = (byte) 0x01 ;
        
        try {
            
            // Compare TLV
            result = proHdlr.compareValue((short)2, compareBuffer20, (short)3, (short)12) ;

            bRes = (result == (short)-1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 17 : Successfull call
        testCaseNb = (byte) 17 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        for (short i=3; i<15; i++) {
            compareBuffer20[i] = (byte)(i-2) ;
        }
        compareBuffer20[13] = (byte) 0x0A ;
        compareBuffer20[14] = (byte) 0x0D ;
        
        try {
            
            // Compare TLV
            result = proHdlr.compareValue((short)2, compareBuffer20, (short)3, (short)12) ;

            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 18 : Successfull call
        testCaseNb = (byte) 18 ;
        bRes = false ;

        // Initialise compareBuffer
        for (short i=0; i<compareBuffer20.length; i++) {
            compareBuffer20[i] = (byte)0x55 ;
        }
        for (short i=3; i<15; i++) {
            compareBuffer20[i] = (byte)(i-2) ;
        }
        compareBuffer20[3] = (byte) 0x99 ;
        compareBuffer20[4] = (byte) 0x03 ;
        
        try {
            
            // Compare TLV
            result = proHdlr.compareValue((short)2, compareBuffer20, (short)3, (short)12) ;

            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
    }
}
