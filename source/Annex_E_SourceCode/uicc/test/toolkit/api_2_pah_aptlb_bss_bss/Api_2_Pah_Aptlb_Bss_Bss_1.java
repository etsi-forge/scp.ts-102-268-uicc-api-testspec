//-----------------------------------------------------------------------------
//Api_2_Pah_Aptlb_Bss_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_aptlb_bss_bss;

import javacard.framework.Util;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_aptlb_bss_bss
 *
 * @version 0.0.1 - 21 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Aptlb_Bss_Bss_1 extends TestToolkitApplet
{
    private byte buffer5[]          = new byte[5];
    private byte buffer256[]        = new byte[256];
    private byte buffer[]           = new byte[32];        
    private byte copyBuffer[]       = new byte[256];        
    private byte compareBuffer[]    = new byte[256];
    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Aptlb_Bss_Bss_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Aptlb_Bss_Bss_1  thisApplet = new Api_2_Pah_Aptlb_Bss_Bss_1();
        
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
       
        short value1Length = 0 ;
        short value1Offset = 0 ;
        short value2Length = 5 ;
        short value2Offset = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Null value1
        testCaseNb = (byte) 1 ;
        bRes = false ;
        tag = (byte)1 ;
        value1Offset = (short)0;
        value1Length = (short)5;
        value2Offset = (short)0 ;
        value2Length = (short)5 ;

        try {
               proHdlr.appendTLV(tag, null,value1Offset,value1Length,buffer5, value2Offset, value2Length) ;
            }
        catch (NullPointerException e) {
               bRes = true ;
        }
        catch (Exception e) {
               bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 2 : Null value2
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
               proHdlr.appendTLV(tag,buffer5,value1Offset,value1Length,null,value2Offset, value2Length) ;
            }
        catch (NullPointerException e) {
               bRes = true ;
        }
        catch (Exception e) {
               bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 3 : value1Offset >= value1.length
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        tag = (byte)1 ;
        value1Offset = (short)5 ;
        value1Length = (short)1 ;
        value2Offset = (short)0 ;
        value2Length = (short)1 ;
            
        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : value1Offset < 0
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        tag = (byte)1 ;
        value1Offset = (short)(-1) ;
        value1Length = (short)1 ;
            
        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 5 : value1length > value1.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        tag = (byte)1 ;
        value1Offset = (short)0 ;
        value1Length = (short)6 ;
            
        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ----------------------------------------------------------
        // Test Case 6 : value1Length + value1Offset > value1.length
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        tag = (byte)1 ;
        value1Offset = (short)3 ;
        value1Length = (short)3 ;
            
        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // -------------------------------
        // Test Case 7 : value1Length < 0
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        tag = (byte)1 ;
        value1Offset = (short)0 ;
        value1Length = (short)(-1) ;
            
        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 8 : value2Offset >= value2.length 
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        tag = (byte)1 ;
        value1Offset = (short)0 ;
        value1Length = (short)1 ;
        value2Offset = (short)5 ;
        value2Length = (short)1 ;

        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 9 : value2Offset < 0
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        value2Offset = (short)(-1) ;
        value2Length = (short)1 ;

        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 10 : value2Length > value2.length
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        value2Offset = (short)0 ;
        value2Length = (short)6 ;

        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 11 : value2Offset + value2Length > value2.length
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        value2Offset = (short)3 ;
        value2Length = (short)3 ;

        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------
        // Test Case 12 : value2Length < 0
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        value2Offset = (short)0 ;
        value2Length = (short)(-1) ;

        try {
                proHdlr.appendTLV(tag,buffer5, value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------
        // Test Case 13 : Handler overflow
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        value1Offset = (short)0;
        value2Offset = (short)0 ;
        value2Length = (short)1 ;
        
        try {
                byte[] TempBuffer = new byte[(short)(proHdlr.getCapacity()-1)];
                value1Length = (short)TempBuffer.length; 
                Util.arrayFillNonAtomic(TempBuffer,(short)0,(short)TempBuffer.length,(byte)0);
                proHdlr.appendTLV(tag,TempBuffer,value1Offset,value1Length,buffer5,value2Offset,value2Length) ;
        }
        catch (ToolkitException e) {
               if(e.getReason() == ToolkitException.HANDLER_OVERFLOW)
                      bRes = true;
               else 
                      bRes = false;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 14 : Bad Parameter exception
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        value1Offset = (short)0;
        value1Length = (short)256 ;
        value2Offset = (short)0 ;
        value2Length = (short)1 ;
        
        try {
                proHdlr.appendTLV(tag,buffer256,value1Offset,value1Length,buffer256,value2Offset,value2Length) ;
        }
        catch (ToolkitException e) {
               if(e.getReason() == ToolkitException.BAD_INPUT_PARAMETER)
                      bRes = true;
               else 
                      bRes = false;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 15 : Bad Parameter exception
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        value1Offset = (short)0;
        value1Length = (short)1 ;
        value2Offset = (short)0 ;
        value2Length = (short)256 ;
        
        try {
                proHdlr.appendTLV(tag,buffer256,value1Offset,value1Length,buffer256,value2Offset,value2Length) ;
        }
        catch (ToolkitException e) {
               if(e.getReason() == ToolkitException.BAD_INPUT_PARAMETER)
                      bRes = true;
               else 
                      bRes = false;
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 16 : successful call does not modify the current TLV
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        buffer5[0] = (byte)0x11;
        buffer5[1] = (byte)0x22;
        buffer5[2] = (byte)0x33;
        buffer5[3] = (byte)0x99;
        buffer5[4] = (byte)0x77;
       
        for(byte i = 0; i <= 0x0F ; i++)
           buffer[i] = (byte)(0xFF-i);
        
        try {
                proHdlr.clear();
                proHdlr.appendTLV((byte)0x81,buffer5,(short)0,(short)3);
                proHdlr.appendTLV((byte)0x82,buffer5,(short)3,(short)2);
                proHdlr.findTLV((byte)0x81,(byte)1); // Select the first TLV
                
                value1Offset = (short)0;
                value1Length = (short)8;
                value2Offset = (short)8;
                value2Length = (short)8;
                proHdlr.appendTLV((byte)0x04,buffer,value1Offset,value1Length,buffer,value2Offset,value2Length);
                bRes = (proHdlr.getValueLength() == (short)3); // Check the current TLV
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 17 : successful call
        testCaseNb = (byte) 17 ;
        bRes = false ;
        // Init compareBuffer
        compareBuffer = new byte[0x12];
        Util.arrayCopyNonAtomic(buffer,(short)0,compareBuffer,(short)2,(short)0x10);
        compareBuffer[0] = (byte)0x04;
        compareBuffer[1] = (byte)0x10;
        
        copyBuffer = new byte[0x12];
        
        try {
                proHdlr.clear();
                proHdlr.appendTLV((byte)0x04,buffer,value1Offset,value1Length,buffer,value2Offset,value2Length);
                proHdlr.copy(copyBuffer,(short)0,(short)copyBuffer.length);
                bRes = (Util.arrayCompare(copyBuffer,(short)0,compareBuffer,(short)0,(short)compareBuffer.length)== 0);
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 18 : successful call
        testCaseNb = (byte) 18 ;
        bRes = false ;
        
        buffer = new byte[0x10];
        for(byte i = 0; i < (byte)buffer.length ; i++)
            buffer[i] = i;
        
        // Init compareBuffer
        compareBuffer = new byte[0x20];
        short offset = Util.arrayCopyNonAtomic(copyBuffer,(short)0,compareBuffer,(short)0,(short)copyBuffer.length);
        compareBuffer[offset++] = (byte)0x85;
        compareBuffer[offset++] = (byte)0x0C;
        offset = Util.arrayCopyNonAtomic(buffer,(short)0x02,compareBuffer,offset++,(short)6);
        Util.arrayCopyNonAtomic(buffer,(short)0x0A,compareBuffer,offset,(short)6);
        
        // Init copyBuffer
        copyBuffer = new byte[0x20];
        try {
                value1Offset = (byte)0x02;
                value1Length = (byte)0x06;
                value2Offset = (byte)0x0A;
                value2Length = (byte)0x06;
                proHdlr.appendTLV((byte)0x85,buffer,value1Offset,value1Length,buffer,value2Offset,value2Length);
                proHdlr.copy(copyBuffer,(short)0,(short)copyBuffer.length);
                bRes = (Util.arrayCompare(copyBuffer,(short)0,compareBuffer,(short)0,(short)compareBuffer.length)== 0);
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 19 : successful call
        testCaseNb = (byte) 19 ;
        bRes = false ;
        
        for(byte i = 1; i <= (byte)(buffer.length -1); i++)
            buffer[(byte)(i-1)] = (byte)(i * 0x11);
        buffer[0x0F] = (byte)0;
        
        // Init compareBuffer
        compareBuffer = new byte[0x2A];
        offset = Util.arrayCopyNonAtomic(copyBuffer,(short)0,compareBuffer,(short)0,(short)copyBuffer.length);
        compareBuffer[offset++] = (byte)0x01;
        compareBuffer[offset++] = (byte)0x08;
        offset = Util.arrayCopyNonAtomic(buffer,(short)0x02,compareBuffer,offset++,(short)4);
        Util.arrayCopyNonAtomic(buffer,(short)0x0A,compareBuffer,offset,(short)4);
        
        // Init copyBuffer
        copyBuffer = new byte[0x2A];
        try {
                value1Offset = (byte)0x02;
                value1Length = (byte)0x04;
                value2Offset = (byte)0x0A;
                value2Length = (byte)0x04;
                proHdlr.appendTLV((byte)0x01,buffer,value1Offset,value1Length,buffer,value2Offset,value2Length);
                proHdlr.copy(copyBuffer,(short)0,(short)copyBuffer.length);
                bRes = (Util.arrayCompare(copyBuffer,(short)0,compareBuffer,(short)0,(short)compareBuffer.length)== 0);
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // ---------------------------------------
        // Test Case 20 : successful call
        testCaseNb = (byte) 20 ;
        bRes = false ;
        
        buffer = new byte[0xFA];
        for(short i = 0; i < (short)buffer.length ; i++)
            buffer[i] = (byte)i;
        
        // Init compareBuffer
        compareBuffer = new byte[0xFD];
        compareBuffer[0] = (byte)0x04;
        compareBuffer[1] = (byte)0x81;
        compareBuffer[2] = (byte)0xFA;
        Util.arrayCopyNonAtomic(buffer,(short)0,compareBuffer,(short)3,(short)buffer.length);
        
        // Init copyBuffer
        copyBuffer = new byte[0xFD];
        try {
                proHdlr.clear();
                value1Offset = (short)0;
                value1Length = (short)0x0080;
                value2Offset = (short)0x0080;
                value2Length = (short)0x007A;
                proHdlr.appendTLV((byte)0x04,buffer,value1Offset,value1Length,buffer,value2Offset,value2Length);
                proHdlr.copy(copyBuffer,(short)0,(short)copyBuffer.length);
                bRes = (Util.arrayCompare(copyBuffer,(short)0,compareBuffer,(short)0,(short)compareBuffer.length)== 0);
        }
        catch (Exception e) {
              bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
