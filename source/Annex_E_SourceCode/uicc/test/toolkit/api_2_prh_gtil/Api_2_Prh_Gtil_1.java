//-----------------------------------------------------------------------------
//Api_2_Prh_Gtil_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gtil;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gtil
 *
 * @version 0.0.1 - 23 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gtil_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;
    public final byte DCS_8_BIT_DATA = (byte)0x04;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Gtil_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gtil_1  thisApplet = new Api_2_Prh_Gtil_1();

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

        // System instance of the ProactiveResponseHandler class
        ProactiveResponseHandler proRespHdlr = null ;
        

        // --------------------------------------------
        // Test Case 1 : Build and send a DISPLAY TEXT command
        //               get the result
        
        testCaseNb = (byte) 1 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x0000) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 2 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x0001) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 3 : Build and send a DISPLAY TEXT command
        //               get the result
        
        testCaseNb = (byte) 3 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x0001) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 4 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x0002) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 5 : Build and send a DISPLAY TEXT command
        //               get the result
        
        testCaseNb = (byte) 5 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x007E) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 6 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x007F) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 7 : Build and send a DISPLAY TEXT command
        //               get the result
        
        testCaseNb = (byte) 7 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x007F) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x0080) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 9 : Build and send a DISPLAY TEXT command
        //               get the result
        
        testCaseNb = (byte) 9 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x0080) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 10 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 10 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x0081) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 11 : Build and send a DISPLAY TEXT command
        //               get the result
        
        testCaseNb = (byte) 11 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x00F2) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 12 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 12 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x00F3) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 13 : Build and send a DISPLAY TEXT command
        //               get the result (with 2 Result TLV)
        
        testCaseNb = (byte) 13 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

            // Verify the additional information length
            bRes = (proRespHdlr.getAdditionalInformationLength() == (short)0x0002) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 14 : Verify the Result TLV is selected
        
        testCaseNb = (byte) 14 ;
        bRes = false ;
        
        try {
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x0003) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 15 : ToolkitException.UNAVAILABLE_ELEMENT is thrown if there is no 
        //          General Result TLV in Terminal Response
        
        testCaseNb = 15 ;
        bRes = false ;
        
        try {

            // Build a DISPLAY TEXT command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send the command (no General Result TLV in Response)
            try {
                proHdlr.send() ;
        }
        catch (ToolkitException e) {}
        
        // Get the response
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
        
        // Get Additional Information length
        try {
            
            proRespHdlr.getAdditionalInformationLength() ;
            
        } catch (ToolkitException e) {
            
            if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                bRes = true ;
        }
            
        }
                        
            
    } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
         
    }
}
