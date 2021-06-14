//-----------------------------------------------------------------------------
//Api_2_Prh_Cpyv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_cpyv;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_cpyv
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Cpyv_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
  
    private byte[] dstBuffer5 = new byte[5] ;
    private byte[] dstBuffer15 = new byte[15] ;
    private byte[] dstBuffer17 = new byte[17] ;
    private byte[] dstBuffer20 = new byte[20] ;
    
    private byte[] compareBuffer = new byte[20] ;

    public final byte DCS_8_BIT_DATA = (byte)0x04;
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Cpyv_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Cpyv_1  thisApplet = new Api_2_Prh_Cpyv_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);      
    }

    public void processToolkit(short event)
    {
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
        
        try 
        {        	
        	proHdlr.initGetInput( (byte)1, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)5 );
        	proHdlr.send();      	
        	
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;          
            proRespHdlr.findTLV( ToolkitConstants.TAG_TEXT_STRING, (byte)1 );
            
            // Null as dstBuffer
            try 
            {
                dstOffset = (short)0 ;
                dstLength = (short)1 ;
                proRespHdlr.copyValue( (short)0, null, dstOffset, dstLength) ;
            } 
            catch (NullPointerException e) 
            {
                bRes = true ;
            }
            
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : dstOffset > dstBuffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try 
        {    
            try 
            {
                dstOffset = (short)6 ;
                dstLength = (short)0 ;
                proRespHdlr.copyValue( (short)0, dstBuffer5, dstOffset, dstLength) ;
            } 
            catch (ArrayIndexOutOfBoundsException e) 
            {
                bRes = true ;
            }
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : dstOffset < 0
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try 
        {           
            try 
            {
                dstOffset = (short)-1 ;
                dstLength = (short)1 ;
                proRespHdlr.copyValue( (short)0, dstBuffer5, dstOffset, dstLength) ;
            } 
            catch (ArrayIndexOutOfBoundsException e) 
            {
                bRes = true ;
            }
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : dstLength > dstBuffer.length
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try 
        {          
            try 
            {
                dstOffset = (short)0 ;
                dstLength = (short)6 ;
                proRespHdlr.copyValue( (short)0, dstBuffer5, dstOffset, dstLength) ;
            } 
            catch (ArrayIndexOutOfBoundsException e) 
            {
                bRes = true ;
            }
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : dstOffset + dstLength > dstBuffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try 
        {        
            try 
            {
                dstOffset = (short)3 ;
                dstLength = (short)3 ;
                proRespHdlr.copyValue( (short)0, dstBuffer5, dstOffset, dstLength ) ;
            } 
            catch (ArrayIndexOutOfBoundsException e) 
            {
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
                proRespHdlr.copyValue( (short)0, dstBuffer5, dstOffset, dstLength) ;
            } 
            catch (ArrayIndexOutOfBoundsException e) 
            {
                bRes = true ;
            }
        }
        catch (Exception e)   
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
     // --------------------------------------------
        // Test Case 7 : valueOffset > Text String Length
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            
            try {
                dstOffset = (short)0 ;
                dstLength = (short)0 ;
                proRespHdlr.copyValue( (short)7, dstBuffer15, dstOffset, dstLength) ;
            } 
            catch (ToolkitException e) 
            {
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
        
        try 
        {           
            try 
            {
                dstOffset = (short)0 ;
                dstLength = (short)1 ;
                proRespHdlr.copyValue( (short)-1, dstBuffer15, dstOffset, dstLength) ;
            } 
            catch (ToolkitException e) 
            {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
     // --------------------------------------------
        // Test Case 9 : dstLength > Text String length
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try 
        {           
            try 
            {
                dstOffset = (short)0 ;
                dstLength = (short)7 ;
                proRespHdlr.copyValue( (short)0, dstBuffer15, dstOffset, dstLength) ;
            } 
            catch (ToolkitException e) 
            {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
     // --------------------------------------------
        // Test Case 10 : ValueOffset + dstLength > Text String length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try 
        {           
            try 
            {
                dstOffset = (short)0 ;
                dstLength = (short)5 ;
                proRespHdlr.copyValue( (short)2, dstBuffer15, dstOffset, dstLength) ;
            } 
            catch (ToolkitException e) 
            {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
     // --------------------------------------------
        // Test Case 11 : TLV is not selected
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try 
        {   
        	proHdlr.initGetInput( (byte)1, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)16 );
        	proHdlr.send();      	
        	
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;          
            
            // Null as dstBuffer
            try 
            {
                dstOffset = (short)0 ;
                dstLength = (short)17 ;
                proRespHdlr.copyValue( (short)0, dstBuffer17, dstOffset, dstLength) ;
            } 
            catch (ToolkitException e) 
            {
                bRes = ( e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT );
            }
            
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
     // --------------------------------------------
        // Test Case 12 : Succesfull call
        testCaseNb = (byte) 12 ;
        bRes = true ;
        
        try 
        { 
        	proRespHdlr.findTLV( ToolkitConstants.TAG_TEXT_STRING, (byte)1 );
        	dstOffset = (short)0 ;
            dstLength = (short)17 ;
            proRespHdlr.copyValue( (short)0, dstBuffer17, dstOffset, dstLength) ;
            
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
     // --------------------------------------------
        // Test Case 13 : Compare the buffer
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try 
        {
            // Initialise the buffer
            compareBuffer[0] = (byte)0x04 ; 
            for ( short i = (short)1; i < (short)17; i++ )
            	compareBuffer[i] = (byte)( i - 1 );
            
            // Compare buffers
            result = Util.arrayCompare(compareBuffer, (short)0, 
                       dstBuffer17, (short)0, (short)17) ;
    
            bRes = (result == (short)0) ;
        } 
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
     // --------------------------------------------
        // Test Case 14 : Succesfull call
        testCaseNb = (byte) 14 ;
        bRes = true ;
        
        try 
        {         	
        	for ( short i = (short)0; i < (short)20; i++ )
        		dstBuffer20[i] = (byte)0x55;
        	
        	dstOffset = (short)3 ;
            dstLength = (short)12 ;
            proRespHdlr.copyValue( (short)2, dstBuffer20, dstOffset, dstLength) ;
            
        }
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
     // --------------------------------------------
        // Test Case 15 : Compare the buffer
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        //55 55 55 01 02
        //03 04 05 06 07
        //08 09 0A 0B 0C
        //55 55 55 55 55
        
        try 
        {
            // Initialise the buffer             
            for ( short i = (short)0; i < (short)20; i++ )
            	compareBuffer[i] = (byte)0x55;
            
            for ( short i = (short)3; i < (short)15; i++ )
            	compareBuffer[i] = (byte)( i - 2 );
            
            // Compare buffers
            result = Util.arrayCompare(compareBuffer, (short)0, 
                       dstBuffer20, (short)0, (short)20) ;
    
            bRes = (result == (short)0) ;
        } 
        catch (Exception e)    
        {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
