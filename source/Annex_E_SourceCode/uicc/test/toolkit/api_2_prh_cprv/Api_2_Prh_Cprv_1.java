//-----------------------------------------------------------------------------
//Api_2_Prh_Cprv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cprv;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cprv
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Cprv_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    
    private byte[] compareBuffer5 = new byte[5] ;
    private byte[] compareBuffer15 = new byte[15] ;
    private byte[] compareBuffer17 = new byte[17] ;
    private byte[] compareBuffer20 = new byte[20] ;

    public final byte DCS_8_BIT_DATA = (byte)0x04;
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Cprv_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Cprv_1  thisApplet = new Api_2_Prh_Cprv_1();

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
        
        // Result of method
        short result = 0 ;

        short valueOffset = 0 ;
        short compareOffset = 0 ;
        short compareLength = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Null as compareBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Select Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)1) ;
            
            try {
                valueOffset = (short)0 ;
                compareOffset = (short)0 ;
                compareLength = (short)1 ;
                proRespHdlr.compareValue(valueOffset, null, compareOffset, compareLength) ;
                
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
            
            valueOffset = (short)0 ;
            compareOffset = (short)6 ;
            compareLength = (short)0 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
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
            
            valueOffset = (short)0 ;
            compareOffset = (short)-1 ;
            compareLength = (short)1 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
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

            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)6 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
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
            
            valueOffset = (short)0 ;
            compareOffset = (short)3 ;
            compareLength = (short)3 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
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
            
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)-1 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer5, compareOffset, compareLength) ;
                
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
            
            valueOffset = (short)7 ;
            compareOffset = (short)0 ;
            compareLength = (short)0 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
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
                        
            valueOffset = (short)-1 ;
            compareOffset = (short)0 ;
            compareLength = (short)1 ;
                        
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
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
                        
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)7 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
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
                        
            valueOffset = (short)2 ;
            compareOffset = (short)0 ;
            compareLength = (short)5 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
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
                        
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
                                   
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)1 ;
            
            try {
                proRespHdlr.compareValue(valueOffset, compareBuffer15, compareOffset, compareLength) ;
                
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

        // Initialise compareBuffer
        compareBuffer17[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer17.length; i++) {
            compareBuffer17[i] = (byte)(i-1) ;
        }
        
        try {
                        
            // Search Text String TLV
            proRespHdlr.findTLV(TAG_TEXT_STRING, (byte)0x01) ;
            
            // Compare TLV
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;            
            result = proRespHdlr.compareValue(valueOffset, compareBuffer17, compareOffset, compareLength) ;

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
        compareBuffer17[0] = DCS_8_BIT_DATA ;
        for (short i=1; i<compareBuffer17.length; i++) {
            compareBuffer17[i] = (byte)(i-1) ;
        }
        compareBuffer17[16] = (byte)0x10 ;
        
        try {
            
            // Compare TLV
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;                        
            result = proRespHdlr.compareValue(valueOffset, compareBuffer17, compareOffset, compareLength) ;

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
        compareBuffer17[0] = (byte) (DCS_8_BIT_DATA - 1) ;
        for (short i=1; i<compareBuffer17.length; i++) {
            compareBuffer17[i] = (byte)(i-1) ;
        }
        
        try {
            
            // Compare TLV
            valueOffset = (short)0 ;
            compareOffset = (short)0 ;
            compareLength = (short)17 ;            
            result = proRespHdlr.compareValue(valueOffset, compareBuffer17, compareOffset, compareLength) ;

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
            valueOffset = (short)2 ;
            compareOffset = (short)3 ;
            compareLength = (short)12 ;            
            result = proRespHdlr.compareValue(valueOffset, compareBuffer20, compareOffset, compareLength) ;

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
            valueOffset = (short)2 ;
            compareOffset = (short)3 ;
            compareLength = (short)12 ;            
            result = proRespHdlr.compareValue(valueOffset, compareBuffer20, compareOffset, compareLength) ;

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
            valueOffset = (short)2 ;
            compareOffset = (short)3 ;
            compareLength = (short)12 ;            
            result = proRespHdlr.compareValue(valueOffset, compareBuffer20, compareOffset, compareLength) ;

            bRes = (result == (short)+1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
      
    }
}
