//-----------------------------------------------------------------------------
//Api_2_Pah_Gtgr_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gtgr;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gtgr
 *
 * @version 0.0.1 - 23 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gtgr_1 extends TestToolkitApplet
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
    public Api_2_Prh_Gtgr_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gtgr_1  thisApplet = new Api_2_Prh_Gtgr_1();

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
            
            // RES_CMD_PERF = 00 (Command performed successfully)
            bRes = (proRespHdlr.getGeneralResult() == RES_CMD_PERF) ;
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
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x01) ;
            
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
            
            // RES_CMD_PERF_PARTIAL_COMPR = 01 (Command performed successfully)
            bRes = (proRespHdlr.getGeneralResult() == RES_CMD_PERF_PARTIAL_COMPR) ;
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
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x01) ;
            
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
            
            // RES_CMD_PERF_PARTIAL_COMPR = 01 (Partial comprehension)
            bRes = (proRespHdlr.getGeneralResult() == RES_CMD_PERF_PARTIAL_COMPR) ;
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
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x02) ;
            
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
            
            // RES_CMD_PERF_MISSING_INFO = 02
            bRes = (proRespHdlr.getGeneralResult() == RES_CMD_PERF_MISSING_INFO) ;
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
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x04) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 9 : Build and send a DISPLAY TEXT command
        //               get the result (7Fh additional bytes)
        
        testCaseNb = (byte) 9 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // RES_CMD_PERF_MISSING_INFO = 02
            bRes = (proRespHdlr.getGeneralResult() == RES_CMD_PERF_MISSING_INFO) ;
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
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x80) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 11 : Build and send a DISPLAY TEXT command
        //               get the result (2 Result TLV)
        
        testCaseNb = (byte) 11 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
            // Send
            proHdlr.send() ;
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            // RES_CMD_PERF_MISSING_INFO = 02
            bRes = (proRespHdlr.getGeneralResult() == RES_CMD_PERF_MISSING_INFO) ;
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
            
            bRes = (proRespHdlr.getValueLength() == (short) 0x02) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 13 : No Result Simple TLV in Terminal Response
        
        testCaseNb = (byte) 13 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
        try {
                // Send
                proHdlr.send() ;
            }
            catch (Exception e) {
            }
        
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

        // UNAVAILABLE_ELEMENT ToolkitException
        
        try {
            
            short result = proRespHdlr.getGeneralResult() ;
            
        } catch (ToolkitException e) {
            bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
        }
        
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 14 : General Result Byte missing in Result Simple TLV
        
        testCaseNb = (byte) 14 ;        
        bRes = false ;
        
        try {
            
            // Build the command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
            
        try {
                // Send
                proHdlr.send() ;
            }
            catch (Exception e) {
            }
            
            // Get the result
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;

        // OUT_OF_TLV_BOUNDARIES ToolkitException
        
        try {
            
            short result = proRespHdlr.getGeneralResult() ;
            
        } catch (ToolkitException e) {
            bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
        }
        
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        



    }
}
