//-----------------------------------------------------------------------------
//Api_2_Pah_Ingk.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_ingk;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_ingk
 *
 * @version 0.0.1 - 13 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Ingk_1 extends TestToolkitApplet
{
    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    private byte[] TEXTA = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'A'} ;
    private byte[] TEXTB = {(byte)'1',(byte)'2',(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'B'} ;
    private byte[] TEXTC = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'C',(byte)'1',(byte)'2'} ;
    private byte[] TEXTD = {(byte)'1',(byte)'2',(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'D',(byte)'3',(byte)'4'} ;
    private byte[] TEXTE = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'E'} ;
    private byte[] TEXTF = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'F'} ;
    private byte[] TEXTG = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'G'} ;
    private byte[] TEXTH = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'H',(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'H'} ;
    private byte[] LONGTEXT = new byte[256] ;

    private static final short MAXIMUM_SIZE = (short) (241) ;

    private byte[] destination = new byte[9] ;

    private byte DCS_8_BIT_DATA = (byte) 0x04;
    private byte DCS_DEFAULT_ALPHABET = (byte)0;
    private byte DCS_UCS2 = (byte)0x08;
    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Ingk_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Ingk_1  thisApplet = new Api_2_Pah_Ingk_1();

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
        
        // --------------------------------------------
        // Test Case 1 : NULL as parameter to buffer
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            try {
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, null, (short)0, (short)1) ;
            }
            catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 2 : offset > buffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            try {
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)5, (short)0) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
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
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)-1, (short)1) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 4 : length > buffer.length
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            try {
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)5) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 5 : offset + length > buffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            try {
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)3, (short)2) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
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
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)3, (short)-1) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 7 : Successful call, whole buffer
        //  Verify the command number
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTA, (short)0, (short)5) ;
            
            // Get command number
            proHdlr.copy(destination, (short)0, (short)9) ;
            short commandNumber = (short) (destination[2] & (short)0xFF) ;
            
            bRes = ((commandNumber>=(short)0x01) && (commandNumber<=(short)0xFE)) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 8 : Send the command
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            proHdlr.send() ;
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 9 : Successful call, part of a buffer
        testCaseNb = (byte) 9 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTB, (short)2, (short)5) ;
            proHdlr.send() ;
            bRes = true ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 10 : Successful call, part of a buffer
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTC, (short)0, (short)5) ;
            proHdlr.send() ;
            bRes = true;
        }
        catch (Exception e)    {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 11 : Successful call, part of a buffer
        testCaseNb = (byte) 11 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTD, (short)2, (short)5) ;
            proHdlr.send() ;
            bRes = true ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 12 : Successful call, qualifier = 81
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x81, DCS_8_BIT_DATA, TEXTE, (short)0, (short)5) ;
            proHdlr.send() ;
            bRes = true ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 13 : Successful call, DCS = 0
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_DEFAULT_ALPHABET , TEXTF, (short)0, (short)5) ;
            proHdlr.send() ;
            bRes = true ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 14 : Successful call, DCS = 8
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_UCS2 , TEXTG, (short)0, (short)5) ;
            proHdlr.send() ;
            bRes = true;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 15 : Test initialisation of the handler
        testCaseNb = (byte) 15 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTA, (short)0, (short)5) ;
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTH, (short)0, (short)10) ;
            proHdlr.send() ;
            bRes = true ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 16 : Successful call, text length is zero
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        try {
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTH, (short)0, (short)0) ;
            proHdlr.send() ;
            bRes = true ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb,bRes);
        
        // --------------------------------------------
        // Test Case 17 : no TLV is selected after the method invocation
        testCaseNb = (byte) 17 ;
        bRes = false ;
        
        try {
            
            // Select 1st TLV of the handler
            proHdlr.findTLV((byte)1, (byte)1) ;
            
            // Method invocation
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, TEXTA, (short)0, (short)5) ;
            
            try {
                short length = proHdlr.getValueLength() ;
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                    bRes = true ;
                } 
            }
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        
        reportTestOutcome(testCaseNb, bRes) ;

        // Prepare buffer
        
        for (short i=(short)0; i<(short)LONGTEXT.length; i++) {
            LONGTEXT[i] = (byte) 0x55 ;
        }
                
        // --------------------------------------------
        // Test Case 18 : initGetInkey with buffer length = 7Eh
        testCaseNb = (byte) 18 ;
        bRes = false ;
        
        try {
            // Initialize the command
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)0x7E) ;
            
            // Send the command
            proHdlr.send() ;
            
            // Command successfull
            reportTestOutcome(testCaseNb,true); ;
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false); ;
        }

        // --------------------------------------------
        // Test Case 19 : initGetInkey with buffer length = 7Fh
        testCaseNb = (byte) 19 ;
        bRes = false ;
        
        try {
            // Initialize the command
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)0x7F) ;
            
            // Send the command
            proHdlr.send() ;
            
            // Command successfull
            reportTestOutcome(testCaseNb,true); ;
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false); ;
        }
        
        // --------------------------------------------
        // Test Case 20 : initGetInkey with buffer length = 240
        testCaseNb = (byte) 20 ;
        bRes = false ;
        
        try {
            // Initialize the command
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)240) ;
            
            // Send the command
            proHdlr.send() ;
            
            // Command successfull
            reportTestOutcome(testCaseNb,true); ;
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false); ;
        }
        
        // --------------------------------------------
        // Test Case 21 : initGetInkey with a too long buffer
        testCaseNb = (byte) 21 ;
        bRes = false ;
        
        try {
            try {
                proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, MAXIMUM_SIZE) ;
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.HANDLER_OVERFLOW) {
                    bRes = true ;
                }
            }                
        } catch (Exception e) {
            bRes = false ;
        }
        
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 22 : initGetInkey without sending the command
        testCaseNb = (byte) 22 ;
        bRes = false ;
        
        try {
            // Initialize the command
            proHdlr.initGetInkey((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)10) ;
            
            // Command successfull
            reportTestOutcome(testCaseNb,true); 
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false);
        }        
    }
}
