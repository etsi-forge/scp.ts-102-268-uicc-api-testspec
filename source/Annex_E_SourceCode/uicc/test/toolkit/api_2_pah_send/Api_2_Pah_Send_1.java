//-----------------------------------------------------------------------------
//Api_2_Pah_Send_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_send;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_send
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Send_1 extends TestToolkitApplet
{
    private byte[] text = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;

    private byte[] LONGTEXT = new byte[256] ;

    private byte[] source = {(byte)0x81, (byte)0x03, (byte)0x01, (byte)0x21, (byte)0x00,
                             (byte)0x82, (byte)0x02, (byte)0x81, (byte)0x02,
                             (byte)0x8D, (byte)0x05, (byte)0x04, (byte)'T', (byte)'e', (byte)'x', (byte)'t'} ;

    private byte[] destination = new byte[16] ;
    private byte DCS_8_BIT_DATA = (byte) 0x04;
    /**
    * This boolean is used to know there is no invocation of select() or deselect() method by the
    * send() method.
    */

    private boolean testingSelectDeselect = false ;
    
    /**
    * Used to know there has been an invocation of select() or deselect() method.
    */
    
    private boolean selectDeselectCalled = false ;

    
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Send_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Send_1  thisApplet = new Api_2_Pah_Send_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {
        // Result of send()
        byte result ; 

        // Result of tests
        boolean bRes ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
        // --------------------------------------------
        // Test Case 1 : Build a DISPLAY TEXT command
        testCaseNb = (byte) 1 ;

        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
            
            reportTestOutcome(testCaseNb,true) ;
        }
        catch (Exception e)    {    
            reportTestOutcome(testCaseNb,false) ;
        }
        
        // --------------------------------------------
        // Test Case 2 : send the command and verify the result
        testCaseNb = (byte) 2 ;

        bRes = false ;
        try {
            result = proHdlr.send() ;
            
            // RES_CMD_PERF = 00 (Command performed successfully)
            bRes = (result == RES_CMD_PERF) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 3 : Build a DISPLAY TEXT command
        testCaseNb = (byte) 3 ;

        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
            
            reportTestOutcome(testCaseNb,true) ;
        }
        catch (Exception e)    {    
            reportTestOutcome(testCaseNb,false) ;
        }
        
        // --------------------------------------------
        // Test Case 4 : send the command and verify the result
        testCaseNb = (byte) 4 ;

        bRes = false ;
        try {
            result = proHdlr.send() ;
            
            // RES_CMD_PERF_PARTIAL_COMPR = 01 (Partial comprehension)
            bRes = (result == RES_CMD_PERF_PARTIAL_COMPR) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 5 : Build a DISPLAY TEXT command
        testCaseNb = (byte) 5 ;

        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
            
            reportTestOutcome(testCaseNb,true) ;
        }
        catch (Exception e)    {    
            reportTestOutcome(testCaseNb,false) ;
        }
        
        // --------------------------------------------
        // Test Case 6 : send the command and verify the result
        testCaseNb = (byte) 6 ;

        bRes = false ;
        try {
            result = proHdlr.send() ;
            
            // RES_CMD_PERF_PARTIAL_COMPR = 01 (Partial comprehension)
            bRes = (result == RES_CMD_PERF_PARTIAL_COMPR) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 7 : Build a DISPLAY TEXT command
        testCaseNb = (byte) 7 ;

        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
            
            reportTestOutcome(testCaseNb,true) ;
        }
        catch (Exception e)    {    
            reportTestOutcome(testCaseNb,false) ;
        }
        
        // --------------------------------------------
        // Test Case 8 : send the command and verify the result
        testCaseNb = (byte) 8 ;

        bRes = false ;
        try {
            result = proHdlr.send() ;
            
            // RES_CMD_PERF_MISSING_INFO = 02
            bRes = (result == RES_CMD_PERF_MISSING_INFO) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // Initialize the long text array
        for (short i=0; i<(short)LONGTEXT.length; i++) {
            LONGTEXT[i] = (byte)'U' ;
        }
        
        
        // --------------------------------------------
        // Test Case 9 : send a 7Fh byte command
        testCaseNb = (byte) 9 ;
        
        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)0x0073) ;
            proHdlr.send() ;
            reportTestOutcome(testCaseNb,true) ;
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false) ;
        }
        
        
        // --------------------------------------------
        // Test Case 10 : send a 80h byte command
        testCaseNb = (byte) 10 ;
        
        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)0x0074) ;
            proHdlr.send() ;
            reportTestOutcome(testCaseNb,true) ;
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false) ;
        }
        
        
        // --------------------------------------------
        // Test Case 11 : send a FDh byte command (Status 9100h)
        testCaseNb = (byte) 11 ;
        
        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, LONGTEXT, (short)0, (short)240) ;
            proHdlr.send() ;
            reportTestOutcome(testCaseNb,true) ;
            
        } catch (Exception e) {
            reportTestOutcome(testCaseNb,false) ;
        }


        // --------------------------------------------
        // Test Case 12 : Verify ProactiveHandler was not modified
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {
            
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;

            // Copy the content of the handler
            proHdlr.copy(source, (short)0, (short)16) ;

            // Build and send a DISPLAY TEXT command
            proHdlr.send() ;
            
            // Copy the content of the handler
            proHdlr.copy(destination, (short)0, (short)16) ;
                        
            // Compare the content of handler
            result = javacard.framework.Util.arrayCompare(source, (short)0, destination, (short)0, (short)16) ;
            
            bRes = (result == (byte)0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

                
        // --------------------------------------------
        // Test Case 13 : Verify there is no invocation of select() or deselect() method.
                
        testCaseNb = (byte) 13 ;
        bRes = false ;
        
        try {
            // Build a DISPLAY TEXT command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
        
            selectDeselectCalled = false ;
        
            // Initialize a boolean
            testingSelectDeselect = true ;
            
            // Send the command
            proHdlr.send() ;
            
            testingSelectDeselect = false ;
            
            if (selectDeselectCalled) {
                
                reportTestOutcome(testCaseNb,false) ;
            } else {
                
                reportTestOutcome(testCaseNb,true) ;
            }
        }
        catch (Exception e)    {    
            reportTestOutcome(testCaseNb,false) ;
        }


        // --------------------------------------------
        // Test Case 14 : Send a command. Terminal Response with 2 Result TLV
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        try {
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
            result = proHdlr.send() ;

            bRes = (result == (short) 0x02) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 15 : Terminal Response without Result Simple TLV
        // testCaseNb = (byte) 16 ;
        testCaseNb = (byte) 15 ;
        bRes = false ;  
        
        try {
            
            // Build and send a command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
           
            try {               
                proHdlr.send() ;
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                    bRes = true ;
                }
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 16 : Terminal Response without general result byte in Result Simple TLV
        // testCaseNb = (byte) 17 ;
        testCaseNb = (byte) 16 ;
        bRes = false ;
        
        try {
            
            // Build and send a command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, text, (short)0, (short)text.length) ;
            
            try {               
                proHdlr.send() ;
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true ;
                }
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


    }
    
    /**
    * This implementation of deselect() method is used to test there is no invocation 
    * by the send() method.
    */
    public void deselect() {
        
        if (testingSelectDeselect) {
            selectDeselectCalled = true ;
        }
    }
    
    /**
    * This implementation of select() method is used to test there is no invocation 
    * by the send() method.
    * If we are not testing this specific point, we call the super method.
    */
    public boolean select() {
        
        if (testingSelectDeselect) {
            selectDeselectCalled = true ;
            return true ;
        }
        return super.select() ;
    }
    
}
