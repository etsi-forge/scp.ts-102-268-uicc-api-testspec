//-----------------------------------------------------------------------------
//Api_2_Prh_Facybbs_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_facybbs_bss;

import javacard.framework.Util;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_facybbs_bss
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Facybbs_Bss_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    
    private byte[] dstBuffer5 = new byte[5] ;
    private byte[] dstBuffer20 = new byte[20] ;
    private byte[] dstBuffer15 = new byte[15] ;
    private byte[] dstBuffer17 = new byte[17] ;
    
    private byte[] compareBuffer = new byte[20] ;

    public final byte DCS_8_BIT_DATA = (byte)0x04;
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Facybbs_Bss_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Facybbs_Bss_1  thisApplet = new Api_2_Prh_Facybbs_Bss_1();

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
        bRes = false ;
        boolean bRes2 = false ;
        
        // Number of tests
        testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        ProactiveResponseHandler proRespHdlr = null ;
        
        // Result of method
        short result = 0 ;

        byte tag = 0 ;
        byte occurence = 0 ;
        short valueOffset = 0 ;
        short dstOffset = 0 ;
        short dstLength = 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Null as dstBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            tag = TAG_COMMAND_DETAILS ;
            occurence = (byte) 1 ;
            valueOffset = (short) 0 ;
            dstOffset = (short) 0 ;
            dstLength = (short) 1 ;

            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, null, dstOffset, dstLength) ;
                
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
            
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)5 ;
            dstLength = (short)1 ;
            
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
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
            
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)-1 ;
            dstLength = (short)1 ;
            
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
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

            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0 ;
            dstLength = (short)6 ;
            
            try {
                
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
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

            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)3 ;
            dstLength = (short)3 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
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
                                    
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0 ;
            dstLength = (short)-1 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer5, dstOffset, dstLength) ;
                
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
                                    
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
                        
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)7 ;
            dstOffset = (short)0 ;
            dstLength = (short)0 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer15, dstOffset, dstLength) ;
                
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
                                    
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)-1 ;
            dstOffset = (short)0 ;
            dstLength = (short)1 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer15, dstOffset, dstLength) ;
                
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
                                    
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0 ;
            dstLength = (short)7 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer15, dstOffset, dstLength) ;
                
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
                                    
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)2 ;
            dstOffset = (short)0 ;
            dstLength = (short)5 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer15, dstOffset, dstLength) ;
                
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
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // Select a TLV
            proRespHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1) ;
            
            tag = TAG_TEXT_STRING ;
            occurence = (byte)2 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0 ;
            dstLength = (short)1 ;            
                                    
            try {
                proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer15, dstOffset, dstLength) ;
                
            } catch (ToolkitException e) {
                bRes2 = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
            // Verify there is no current TLV
            try {
                proRespHdlr.getValueLength() ;
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
            
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0 ;
            dstLength = (short)17 ;            
                                    
            result = proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer17, dstOffset, dstLength) ;
            
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
            
            result = Util.arrayCompare(compareBuffer, (short)0, dstBuffer17, (short)0, (short)17) ;
            
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
            
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)2 ;
            dstOffset = (short)3;
            dstLength = (short)12 ;            
                                    
            result = proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer20, dstOffset, dstLength) ;
            
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
            
            result = Util.arrayCompare(compareBuffer, (short)0, dstBuffer20, (short)0, (short)20) ;
            
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

            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            tag = TAG_TEXT_STRING ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0;
            dstLength = (short)17 ;            
                                    
            result = proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer17, dstOffset, dstLength) ;
            
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
            
            result = Util.arrayCompare(compareBuffer, (short)0, dstBuffer17, (short)0, (short)17) ;
            
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

            tag = TAG_TEXT_STRING ;
            occurence = (byte)2 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0;
            dstLength = (short)6 ;            
                                    
            result = proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer17, dstOffset, dstLength) ;
            
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
            
            result = Util.arrayCompare(compareBuffer, (short)0, dstBuffer17, (short)0, (short)6) ;
            
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
                
        try {
            
            // Send a GET INPUT command
            proHdlr.initGetInput((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length, (short)0, (short)0xFF) ;
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            tag = (byte)0x8D ;
            occurence = (byte)1 ;
            valueOffset = (short)0 ;
            dstOffset = (short)0;
            dstLength = (short)17 ;            
                                    
            result = proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer17, dstOffset, dstLength) ;
                
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
            
            result = Util.arrayCompare(compareBuffer, (short)0,dstBuffer17, (short)0, (short)17) ;
                
            bRes = (result == (short)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 22 : Invalid Parameter
        testCaseNb = (byte) 22 ;
        bRes = false ;
        
        // Occurence = 0
        occurence = 0;
        try {
            result = proRespHdlr.findAndCopyValue(tag, occurence, valueOffset, dstBuffer17, dstOffset, dstLength) ;
            bRes = false;
        }
        catch(ToolkitException e){
            if(e.getReason() == ToolkitException.BAD_INPUT_PARAMETER)
                bRes = true;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
