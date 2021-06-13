//-----------------------------------------------------------------------------
//Api_2_Pah_Aptlb_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_aptlb_bss;

import javacard.framework.*;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_aptlb_bss
 *
 * @version 0.0.1 - 17 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Aptlb_Bss_1 extends TestToolkitApplet
{
    private byte compareBuffer[]    = new byte[256] ;
    private byte buffer5[]          = new byte[5] ;
    private byte buffer256[]        = new byte[256] ;
    private byte buffer[]           = new byte[32] ;        
    private byte copyBuffer[]       = new byte[256] ;        

    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Aptlb_Bss_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Aptlb_Bss_1  thisApplet = new Api_2_Pah_Aptlb_Bss_1();
        
        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event)
    {
        
        // Result of each test
        boolean bRes = false ;
        boolean bRes2 = false ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;

        short result = (short) 0 ;

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
        byte tag = 0 ;
        short length = 0 ;
        short offset = 0 ;
        short valueLength = 0 ;
        short valueOffset = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Null value
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)0 ;
            valueLength = (short)1 ;
            
            try {
                proHdlr.appendTLV(tag, null, valueOffset, valueLength) ;
            } catch (NullPointerException e) {
                bRes = true ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : valueOffset > value.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)6 ;
            valueLength = (short)0 ;
            
            try {
                proHdlr.appendTLV(tag, buffer5, valueOffset, valueLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : valueOffset < 0
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)-1 ;
            valueLength = (short)1 ;
            
            try {
                proHdlr.appendTLV(tag, buffer5, valueOffset, valueLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 4 : valueLength > value.Length
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)0 ;
            valueLength = (short)6 ;
            
            try {
                proHdlr.appendTLV(tag, buffer5, valueOffset, valueLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 5 : valueOffset + valueLength > value.Length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)3 ;
            valueLength = (short)3 ;
            
            try {
                proHdlr.appendTLV(tag, buffer5, valueOffset, valueLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 6 : valueLength < 0
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)0 ;
            valueLength = (short)-1 ;
            
            try {
                proHdlr.appendTLV(tag, buffer5, valueOffset, valueLength) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 7 : handler overflow
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)0 ;
            byte[] TempBuffer = new byte[(short)(proHdlr.getCapacity() - 1)];
            Util.arrayFillNonAtomic(TempBuffer,(short)0,(short)TempBuffer.length,(byte)0);
            try {
                proHdlr.appendTLV(tag, TempBuffer, valueOffset, (short)TempBuffer.length);
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.HANDLER_OVERFLOW) ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : bad input parameter
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            valueOffset = (short)0 ;
            valueLength = (short)256 ;
            
            try {
                proHdlr.appendTLV(tag, buffer256, valueOffset, valueLength) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER) ;
            }
        
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 9 : the current TLV is not modified
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        for (short i=0; i<(short)8; i++) {
            buffer[i] = (byte) ((byte)0xFF-i) ;     // FF FE FD FC FB FA F9 F8
        }
        
        try {
            
            // Initialise the handler
            proHdlr.init((byte)0, (byte)0, (byte)0) ;
            
            // Select tag 02h
            proHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;

            // Append TLV
            tag = (byte)0x04 ;
            valueOffset = (short)0 ;
            valueLength = (short)8 ;
            proHdlr.appendTLV(tag, buffer, valueOffset, valueLength) ;
            
            // Verify current TLV
            result = proHdlr.getValueLength() ;
            bRes = (result == (short)3) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 10 : Successfull call
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        tag = (byte)4 ;
        
        // Initialise buffers
        for (short i=0; i<(short)8; i++) {
            buffer[i] = (byte) ((byte)0xFF-i) ;         // FF FE FD FC FB FA F9 F8
            compareBuffer[(short)(i+2)] = (byte)((byte)0xFF-i) ;   // 04 08 FF FE FD FC FB FA F9 F8
        }
        compareBuffer[0] = tag ;
        compareBuffer[1] = (byte)8 ;
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;
            
            // Append buffer
            valueOffset = (short)0 ;
            valueLength = (short)8 ;
            proHdlr.appendTLV(tag, buffer, valueOffset, valueLength) ;

            // Copy the handler
            offset = (short) 0 ;
            length = (short) proHdlr.getLength() ;
            proHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            result = javacard.framework.Util.arrayCompare(copyBuffer, offset, 
                                                        compareBuffer, offset, length) ;
                                                        
            bRes = (result == (short)0) ;                                                        
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 11 : Successfull call
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        tag = (byte)0x85 ;
        
        // Initialise buffers
        for (short i=0; i<(short)8; i++) {
            buffer[i] = (byte)i ;               // 00 01 02 03 04 05 06 07
        }
        compareBuffer[10] = tag ;
        compareBuffer[11] = (byte)0x06 ;
        for (short i=0; i<(short)6; i++) {
            compareBuffer[(short)(12+i)] = (byte)(i+2) ;     // 04 08 FF FE FD FC FB FA F9 F8 85 06 02 03 04 05 06 07
        }
        
        
        try {
                        
            // Append buffer
            valueOffset = (short)2 ;
            valueLength = (short)6 ;
            proHdlr.appendTLV(tag, buffer, valueOffset, valueLength) ;

            // Copy the handler
            offset = (short)0 ;
            length = (short)proHdlr.getLength() ;
            proHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            result = javacard.framework.Util.arrayCompare(copyBuffer, offset, 
                                                        compareBuffer, offset, length) ;
                                                        
            bRes = (result == (short)0) ;                                                        
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 12 : Successfull call
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        tag = (byte) 0x01 ;
        // Initialise buffers
        for (short i=0; i<8; i++) {
            buffer[i] = (byte) ((i+1)*0x11) ;           // 11 22 33 44 55 66 77 88
        }
        
        compareBuffer[18] = tag ;
        compareBuffer[19] = (byte) 0x04 ;
        compareBuffer[20] = (byte) 0x33 ;
        compareBuffer[21] = (byte) 0x44 ;
        compareBuffer[22] = (byte) 0x55 ;
        compareBuffer[23] = (byte) 0x66 ;
        
        try {
                        
            // Append buffer
            valueOffset = (short)2 ;
            valueLength = (short)4 ;
            proHdlr.appendTLV(tag, buffer, valueOffset, valueLength) ;

            // Copy the handler
            offset = (short)0 ;
            length = (short)proHdlr.getLength() ;
            proHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            result = javacard.framework.Util.arrayCompare(copyBuffer, offset, 
                                                        compareBuffer, offset, length) ;
                                                        
            bRes = (result == (short)0) ;                                                        
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 13 : Successfull call
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        tag = (byte)04 ;
        
        // Initialise buffers
        for (short i=0; i<(short)81; i++) {
            buffer256[i] = (byte)i ;
            compareBuffer[(short)(i+3)] = (byte)i ;
        }
        compareBuffer[0] = tag ;
        compareBuffer[1] = (byte)0x81 ;
        compareBuffer[2] = (byte)0x80 ;
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;
            
            // Append buffer
            valueOffset = (short)0 ;
            valueLength = (short)0x80 ;
            proHdlr.appendTLV(tag, buffer256, valueOffset, valueLength) ;

            // Copy the handler
            offset = (short) 0 ;
            length = (short)proHdlr.getLength() ;
            proHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            result = javacard.framework.Util.arrayCompare(copyBuffer, offset, 
                                                        compareBuffer, offset, length) ;
                                                        
            bRes = (result == (short)0) ;                                                        
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 14 : Successfull call (limit case)
        testCaseNb = (byte) 14 ;
        bRes = false ;
        bRes2 = false ;
            
        tag = (byte)04 ;
        
        // Initialise buffers
        for (short i=0; i<(short)0xFA; i++) {
            buffer256[i] = (byte)i ;
            compareBuffer[(short)(i+3)] = (byte)i ;
        }
        compareBuffer[0] = tag ;
        compareBuffer[1] = (byte)0x81 ;
        compareBuffer[2] = (byte)0xFA ;
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;
            
            // Append buffer
            valueOffset = (short)0 ;
            valueLength = (short)250 ;
            proHdlr.appendTLV(tag, buffer256, valueOffset, valueLength) ;

           // Verify length of the handler
           bRes2 = (proHdlr.getLength() == (short)253) ;


            // Copy the handler
            offset = (short) 0 ;
            length = (short) proHdlr.getLength() ;
            proHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            result = javacard.framework.Util.arrayCompare(copyBuffer, offset, 
                                                        compareBuffer, offset, length) ;
                                                        
            bRes = (bRes2) && (result == (short)0) ;                                                        
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
