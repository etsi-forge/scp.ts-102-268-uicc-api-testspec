//-----------------------------------------------------------------------------
//Api_2_Erh_Apda_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_apda_bss;

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_apda_bss
 *
 * @version 0.0.1 - 11 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Apda_Bss_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] buffer5          = new byte[5];
    private byte[] buffer           = new byte[8] ;
    private byte[] copyBuffer       = new byte[32] ;
    private byte[] compareBuffer    = new byte[32] ;
    private byte[] large_buffer;
    
    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Apda_Bss_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Apda_Bss_1  thisApplet = new Api_2_Erh_Apda_Bss_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) {
        
        // Result of each test
        boolean bRes = false ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;

        // Get the system instance of the EnvelopeResponseHandler class
        EnvelopeResponseHandler EnvRespHdlr = EnvelopeResponseHandlerSystem.getTheHandler() ;
        
        short offset = 0 ;
        short length = 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Null buffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            EnvRespHdlr.appendTLV((byte) 1, (byte) 1);
            
            try {
                offset = (short)0 ;
                length = (short)1 ;
                
                EnvRespHdlr.appendArray(null, offset, length) ;
            } catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 2 : offset>buffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            try {
                offset = (short)5 ;
                length = (short)1 ;
                
                EnvRespHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
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
                
                EnvRespHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
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
                
                EnvRespHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
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
                
                EnvRespHdlr.appendArray(buffer5, offset, length) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
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
                
                EnvRespHdlr.appendArray(buffer5, offset, length) ;
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
                length = (short)(EnvRespHdlr.getCapacity() + 1) ;
                large_buffer = new byte[length];
                Util.arrayFillNonAtomic(large_buffer,(short)0,(short)large_buffer.length,(byte)0);
                EnvRespHdlr.appendArray(large_buffer, offset, length) ;
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
        
        
        
        try {
            
            EnvRespHdlr.clear();

            //built the first tlv
            buffer[0]=(byte)0x11;
            buffer[1]=(byte)0x22;
            buffer[3]=(byte)0x33;
            EnvRespHdlr.appendTLV((byte)0x81,buffer,(short) 0x00, (short) 3);
        
            //built the second tlv
            buffer[0]=(byte)0x99;
            buffer[1]=(byte)0x77;
            EnvRespHdlr.appendTLV((byte)0x82,buffer,(short) 0x00, (short) 2);
        
            
            // Initialise buffer
            for (short i=0; i<(short)buffer.length; i++) 
                buffer[i] = (byte) ((byte)0xFF-i) ;
            
            // Select Command Details TLV
            EnvRespHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;
            
            // Append buffer
            offset = (short)0 ;
            length = (short)buffer.length ;
            EnvRespHdlr.appendArray(buffer, offset, length) ;
            
            // Verify current TLV
            if(EnvRespHdlr.getValueLength()==(short)3)
                bRes=true;
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
            EnvRespHdlr.clear() ;
            
            // Append buffer
            offset = (short)0 ;
            length = (short)buffer.length ;
            EnvRespHdlr.appendArray(buffer, offset, length) ;

            // Copy the handler
            offset = (short) 0 ;
            length = (short) 8 ;
            EnvRespHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            if(Util.arrayCompare(copyBuffer, offset,compareBuffer, offset, length)==0)
                bRes=true;
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
        for (short i=0; i<(short)buffer.length; i++)
        {
            buffer[i] = (byte)i ;               // 00 01 02 03 04 05 06 07
            if (i>=(byte)2) 
                compareBuffer[(short)(i+6)] = (byte)i ;  // FF FE FD FC FB FA F9 F8 02 03 04 05 06 07
        }
        
        try {
                        
            // Append buffer
            offset = (short)2 ;
            length = (short)6 ;
            EnvRespHdlr.appendArray(buffer, offset, length) ;

            // Copy the handler
            offset = (short)0 ;
            length = (short)14 ;
            EnvRespHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            if(Util.arrayCompare(copyBuffer, offset, compareBuffer, offset, length)==0)
                bRes=true;
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
        for (short i=0; i<(short)buffer.length; i++) 
        {
            buffer[i] = (byte) ((i+1)*0x11) ;           // 11 22 33 44 55 66 77 88
            if (i>=(byte)2) 
                compareBuffer[(short)(i+12)] = (byte)((i+1)*0x11) ;  // FF FE FD FC FB FA F9 F8 02 03 04 05 06 07 33 44 55 66 
        }
        
        try {
                        
            // Append buffer
            offset = (short)2 ;
            length = (short)4 ;
            EnvRespHdlr.appendArray(buffer, offset, length) ;

            // Copy the handler
            offset = (short)0 ;
            length = (short)18 ;
            EnvRespHdlr.copy(copyBuffer, offset, length) ;
            
            // Compare buffer
            if(Util.arrayCompare(copyBuffer, offset,compareBuffer, offset, length)==0)
                bRes=true;
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 12 : ToolkitException HANDLER_NOT_AVAILABLE
        testCaseNb = (byte) 12 ;
        bRes = false ;

        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            // Append buffer
            try {
                offset = (short)2 ;
                length = (short)4 ;
                EnvRespHdlr.appendArray(buffer, offset, length) ;
                bRes=false;
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.HANDLER_NOT_AVAILABLE) {
                    bRes= true;
                } else {
                    bRes=false;
                }
            }
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);

    }
}
