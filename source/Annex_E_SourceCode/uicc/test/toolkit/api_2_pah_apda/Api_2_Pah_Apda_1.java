//-----------------------------------------------------------------------------
//Api_2_Pah_Apda_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_apda;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_apda
 *
 * @version 0.0.1 - 17 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Apda_1 extends TestToolkitApplet
{
    private byte[] buffer5          = new byte[5];
    private byte[] buffer256        = new byte[256] ;
    private byte[] buffer255        = new byte[255] ;
    private byte[] buffer           = new byte[8] ;
    private byte[] copyBuffer       = new byte[32] ;
    private byte[] compareBuffer    = new byte[32] ;
    private byte[] copyBuffer255    = new byte[255] ;
    private byte[] compareBuffer255 = new byte[255] ;
    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Apda_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Apda_1  thisApplet = new Api_2_Pah_Apda_1();

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
        
        short offset = 0 ;
        short length = 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Null buffer        
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)0 ;
                length = (short)1 ;
                
                proHdlr.appendArray(null, offset, length) ;
            } catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 2 : offset>buffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)6 ;
                length = (short)0 ;
                
                proHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 3 : offset < 0
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)-1 ;
                length = (short)1 ;
                
                proHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 4 : length>buffer.length
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)0 ;
                length = (short)6 ;
                
                proHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 5 : offset + length>buffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)3 ;
                length = (short)3 ;
                
                proHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 6 : length < 0
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)0 ;
                length = (short)-1 ;
                
                proHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 7 : handler overflow
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)0 ;
                byte[] TempBuffer = new byte[(short)(proHdlr.getCapacity() + 1)];
                Util.arrayFillNonAtomic(TempBuffer,(short)0,(short)TempBuffer.length,(byte)0);
                proHdlr.appendArray(TempBuffer, offset,(short) TempBuffer.length) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.HANDLER_OVERFLOW) ;
            }
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : A successfull append does not modify the current TLV
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        // Initialise buffer
        for (short i=0; i<(short)buffer.length; i++) {
            buffer[i] = (byte) ((byte)0xFF-i) ;
        }
        
        try {
            // Initialise the handler
            proHdlr.init((byte)0, (byte)0, (byte)0) ;
            
            // Select Command Details TLV
            proHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;
            
            // Append buffer
            offset = (short)0 ;
            length = (short)buffer.length ;
            proHdlr.appendArray(buffer, offset, length) ;
            
            // Verify current TLV
            result = proHdlr.getValueLength() ;
            
            bRes = (result == (short)3) ;
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : Successfull call
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        // Initialise buffers
        for (short i=0; i<(short)buffer.length; i++) {
            buffer[i] = (byte) ((byte)0xFF-i) ;         // FF FE FD FC FB FA F9 F8
            compareBuffer[i] = (byte)((byte)0xFF-i) ;   // FF FE FD FC FB FA F9 F8
        }
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;
            
            // Append buffer
            offset = (short)0 ;
            length = (short)buffer.length ;
            proHdlr.appendArray(buffer, offset, length) ;

            // Copy the handler
            offset = (short) 0 ;
            length = (short) 8 ;
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
        // Test Case 10 : Successfull call
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        // Initialise buffers
        for (short i=0; i<(short)buffer.length; i++) {
            buffer[i] = (byte)i ;               // 00 01 02 03 04 05 06 07
            
            if (i>=(byte)2) {
                compareBuffer[(short)(i+6)] = (byte)i ;  // FF FE FD FC FB FA F9 F8 02 03 04 05 06 07
            }
        }
        
        try {
                        
            // Append buffer
            offset = (short)2 ;
            length = (short)6 ;
            proHdlr.appendArray(buffer, offset, length) ;

            // Copy the handler
            offset = (short)0 ;
            length = (short)14 ;
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
        
        // Initialise buffers
        for (short i=0; i<(short)buffer.length; i++) {
            buffer[i] = (byte) ((i+1)*0x11) ;           // 11 22 33 44 55 66 77 88
            
            if (i>=(byte)2) {
                compareBuffer[(short)(i+12)] = (byte)((i+1)*0x11) ;  // FF FE FD FC FB FA F9 F8 02 03 04 05 06 07 33 44 55 66 
            }
        }
        
        try {
                        
            // Append buffer
            offset = (short)2 ;
            length = (short)4 ;
            proHdlr.appendArray(buffer, offset, length) ;

            // Copy the handler
            offset = (short)0 ;
            length = (short)18 ;
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
        bRes2 = false ;
        
        // Initialise buffers
        for (short i=0; i<(short)buffer255.length; i++) {
            buffer255[i] = (byte)i ;
            compareBuffer255[i] = (byte)i ;
        }
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;
            
            // Append buffer
            offset = (short)0 ;
            length = (short)253 ;
            proHdlr.appendArray(buffer255, offset, length) ;

        // Verify length of the handler
        bRes2 = (proHdlr.getLength() == (short)253) ;
            

            // Copy the handler
            offset = (short) 0 ;
            length = (short) proHdlr.getLength() ;
            proHdlr.copy(copyBuffer255, offset, length) ;
            
            // Compare buffer
            result = javacard.framework.Util.arrayCompare(copyBuffer255, offset, 
                                                        compareBuffer255, offset, length) ;
                                                        
            bRes = (bRes2) && (result == (short)0) ;                                                        
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        
    }
}
