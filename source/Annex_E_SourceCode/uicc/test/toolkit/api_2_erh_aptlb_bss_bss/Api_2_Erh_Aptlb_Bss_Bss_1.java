//-----------------------------------------------------------------------------
//Api_2_Erh_Aptlb_Bss_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_aptlb_bss_bss;

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_aptlb_bss_bss
 *
 * @version 0.0.1 - 11 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Aptlb_Bss_Bss_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte compareBuffer[]    = new byte[256] ;
    private byte value1[]          = new byte[5] ;
    private byte value2[]          = new byte[5] ;
    private byte buffer256[]        = new byte[256] ;
    private byte buffer[]           = new byte[32] ;        
    private byte copyBuffer[]       = new byte[256] ;        

    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Aptlb_Bss_Bss_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Aptlb_Bss_Bss_1  thisApplet = new Api_2_Erh_Aptlb_Bss_Bss_1();

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
        
        byte tag = 0 ;
        short value1Offset = 0 ;
        short value1Length = 0 ;
        short value2Length = 0 ;
        short value2Offset = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Null value1
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            
            value1Offset = (short)0 ;
            value1Length = (short)1 ;
            value2Offset = (short)0 ;
            value2Length = (short)1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, null, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 1 : Null value1
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            
            value1Offset = (short)0 ;
            value1Length = (short)1 ;
            value2Offset = (short)0 ;
            value2Length = (short)1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,null,value2Offset, value2Length);
            }
            catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 3 : value1Offset >= value1.length
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value1Offset = (short)5 ;
            value1Length = (short)1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : value1Offset < 0
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value1Offset = (short)-1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 5 : value1Length > value1.Length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value1Offset = (short)0 ;
            value1Length = (short)6 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 6 : value1Offset + value1Length > value1.length
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value1Offset = (short)3 ;
            value1Length = (short)3 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 7 : value1Length < 0
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value1Offset = (short)0 ;
            value1Length = (short)-1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 8 : value2Offset >= value2.length
        testCaseNb = (byte) 8 ;
        bRes = false ;
        value1Offset = (short)0 ;
        value1Length = (short)1 ;

        try {
            tag = (byte)1 ;
            value2Offset = (short)5 ;
            value2Length = (short)1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 9 : value2Offset < 0
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value2Offset = (short)-1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 10 : value2Length > value2.Length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value2Offset = (short)0 ;
            value2Length = (short)6 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 11 : value2Offset + value2Length > value2.length
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value2Offset = (short)3 ;
            value2Length = (short)3 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 12 : value2Length < 0
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            value2Offset = (short)0 ;
            value2Length = (short)-1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1, value1Offset,value1Length,value2,value2Offset, value2Length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
 
        // --------------------------------------------
        // Test Case 13 : handler overflow
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            EnvRespHdlr.appendArray(buffer256,(short)0,(short)(EnvRespHdlr.getCapacity() - 1));
            
            try {
                EnvRespHdlr.appendTLV(tag, buffer256,(short)0,(short)1,buffer256,(short)0,(short)1) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.HANDLER_OVERFLOW) ;
            }
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 14 : bad input parameter
        testCaseNb = (byte) 14 ;
        bRes = false ;
        value1 = new byte[256];
        value2 = new byte[256];
        
        try {
            tag = (byte)1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1,(short)0,(short)0x0100,value2,(short)0,(short)1) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 15 : bad input parameter
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        try {
            tag = (byte)1 ;
            
            try {
                EnvRespHdlr.appendTLV(tag, value1,(short)0,(short)1,value2,(short)0,(short)0x100) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 9 : the current TLV is not modified
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        try {
            
            // clear the handler
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

            for (short i=0; i<(short)8; i++) 
                buffer[i] = (byte) ((byte)0xFF-i) ;     // FF FE FD FC FB FA F9 F8
        
            for (short i=8; i<(short)0x11; i++)
                buffer[i] = (byte) ((byte)0xFF-i) ;    
                
            // Select tag 02h
            EnvRespHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;

            // Append TLV
            tag = (byte)0x04 ;
            value1Offset = (short)0 ;
            value1Length = (short)8 ;
            value2Offset = (short)8 ;
            value2Length = (short)8 ;
            EnvRespHdlr.appendTLV(tag, buffer,value1Offset,value1Length,buffer,value2Offset, value2Length) ;
            
            // Verify current TLV
            if(EnvRespHdlr.getValueLength()==3)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 17 : Successfull call
        testCaseNb = (byte) 17 ;
        bRes = false ;
        
        tag = (byte)4 ;
        
        // Initialise buffers
        Util.arrayCopyNonAtomic(buffer,(short)0,compareBuffer,(short)2,(short)0x10);
        compareBuffer[0] = tag ;
        compareBuffer[1] = (byte)0x10 ;
        
        try {
            
            //erase the copybuffer
            Util.arrayFillNonAtomic(copyBuffer,(short)0,(short)copyBuffer.length,(byte)0x00);
        
            // Clear the handler
            EnvRespHdlr.clear() ;
            
            // Append buffer
            value1Offset = (short)0 ;
            value1Length = (short)8 ;
            
            value2Offset = (short)8 ;
            value2Length = (short)8 ;
            EnvRespHdlr.appendTLV(tag, buffer,value1Offset,value1Length,buffer,value2Offset, value2Length) ;

            // Copy the handler
            EnvRespHdlr.copy(copyBuffer,(short)0,(short) EnvRespHdlr.getLength()) ;
            
            // Compare buffer
            if(Util.arrayCompare(copyBuffer,(short)0, compareBuffer, (short)0,(short)0x10)==0)
                bRes=true;
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 18 : Successfull call
        testCaseNb = (byte) 18 ;
        bRes = false ;
        
        tag = (byte)0x85 ;
        
        // Initialise buffers
        for (short i=0; i<(short)0x10; i++) 
            buffer[i] = (byte)i ;               // 00 01 02 03 04 05 06 07

        compareBuffer[0x12] = tag ;
        compareBuffer[0x13] = (byte)0x0C ;
        Util.arrayCopyNonAtomic(buffer,(short)0x02,compareBuffer,(short)0x14,(short)0x06);
        Util.arrayCopyNonAtomic(buffer,(short)0x0A,compareBuffer,(short)0x1A,(short)0x06);
        
        try {
            //erase the copybuffer
            Util.arrayFillNonAtomic(copyBuffer,(short)0,(short)copyBuffer.length,(byte)0x00);
                    
            // Append buffer
            value1 = new byte[8];
            Util.arrayCopyNonAtomic(buffer,(short)0,value1,(short)0,(short)value1.length);
            value1Offset = (short)2 ;
            value1Length = (short)6 ;
            value2 = new byte[8];
            Util.arrayCopyNonAtomic(buffer,(short)8,value2,(short)0,(short)value2.length);
            value2Offset = (short)2 ;
            value2Length = (short)6 ;
            EnvRespHdlr.appendTLV(tag, value1,value1Offset,value1Length,value2,value2Offset,value2Length) ;
            
            // Copy the handler
            EnvRespHdlr.copy(copyBuffer, (short)0,(short) EnvRespHdlr.getLength()) ;

            // Compare buffer
            if(Util.arrayCompare(copyBuffer,(short)0, compareBuffer,(short)0, EnvRespHdlr.getLength())==0)
                bRes=true;
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 19 : Successfull call
        testCaseNb = (byte) 19 ;
        bRes = false ;
        
        tag = (byte) 0x01 ;
        // Initialise buffers
        for (short i=1; i<0x11; i++) 
            buffer[i] = (byte) (i*0x11) ;           // 11 22 33 44 55 66 77 88
        
        compareBuffer[0x20] = tag ;
        compareBuffer[0x21] = (byte) 0x08 ;
        Util.arrayCopyNonAtomic(buffer,(short)0x02,compareBuffer,(short)0x22,(short)0x04);
        Util.arrayCopyNonAtomic(buffer,(short)0x0B,compareBuffer,(short)0x26,(short)0x04);

        try {
            //erase the copybuffer
            Util.arrayFillNonAtomic(copyBuffer,(short)0,(short)copyBuffer.length,(byte)0x00);
            
            // Initialise value1 and value2
            Util.arrayCopyNonAtomic(buffer,(short)0,value1,(short)0,(short)value1.length);
            Util.arrayCopyNonAtomic(buffer,(short)9,value2,(short)0,(short)value1.length);
            value2[7] = (byte)0;
            
            value1Offset = (short)2 ;
            value1Length = (short)4 ;
            
            value2Offset = (short)2 ;
            value2Length = (short)4 ;
            EnvRespHdlr.appendTLV(tag, value1,value1Offset,value1Length,value2,value2Offset, value2Length) ;

            // Copy the handler
            EnvRespHdlr.copy(copyBuffer,(short) 0, EnvRespHdlr.getLength()) ;
     
            // Compare buffer
            if(Util.arrayCompare(copyBuffer,(short)0, compareBuffer, (short)0, EnvRespHdlr.getLength())==0)
                bRes=true;
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 20 : Successfull call
        testCaseNb = (byte) 20 ;
        bRes = false ;
        
        tag = (byte)04 ;
        
        // Initialise buffers
        for (short i=0; i<(short)0x00FD; i++) {
            buffer256[i] = (byte)(i) ;
            compareBuffer[(short)(i+3)] = (byte)(i) ;
        }
        compareBuffer[0] = tag ;
        compareBuffer[1] = (byte)0x81 ;
        compareBuffer[2] = (byte)0xFD ;
        
        try {
            
            //erase the copybuffer
            Util.arrayFillNonAtomic(copyBuffer,(short)0,(short)copyBuffer.length,(byte)0x00);
        
            // Clear the handler
            EnvRespHdlr.clear() ;
            
            // Initialise value1 and value2
            value1 = new byte[128];
            Util.arrayCopyNonAtomic(buffer256,(short)0,value1,(short)0,(short)value1.length);
            value1Offset = (short)0;
            value1Length = (short)0x80;

            value2 = new byte[125];
            Util.arrayCopyNonAtomic(buffer256,(short)0x80,value2,(short)0,(short)value2.length);
            value2Offset = (short)0;
            value2Length = (short)0x7D ;
            EnvRespHdlr.appendTLV(tag, value1,value1Offset,value1Length,value2,value2Offset, value2Length) ;

            // Copy the handler
            EnvRespHdlr.copy(copyBuffer,(short)0, EnvRespHdlr.getLength()) ;

            byte[] toto = new byte[256];
            Util.arrayCopyNonAtomic(compareBuffer,(short)0,toto,(short)0,(short)EnvRespHdlr.getLength());
            
            // Compare buffer
            if(Util.arrayCompare(copyBuffer, (short)0, compareBuffer,(short)0, EnvRespHdlr.getLength())==0)
                bRes=true;
        }
        catch (Exception e) {  
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 21 : ToolkitException HANDLER_NOT_AVAILABLE
        testCaseNb = (byte) 21 ;
        bRes = false ;

        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            // Append buffer
            try {
                value1Offset = (short)0;
                value1Length = (short)0x01;
                value2Offset = (short)0;
                value2Length = (short)0x01;
                EnvRespHdlr.appendTLV(tag, buffer256,value1Offset,value1Length,buffer256,value2Offset, value2Length) ;
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
