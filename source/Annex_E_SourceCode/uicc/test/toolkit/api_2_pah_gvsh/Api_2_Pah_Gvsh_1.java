//-----------------------------------------------------------------------------
//Api_2_Pah_Gvsh_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_gvsh;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_gvsh
 *
 * @version 0.0.1 - 20 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Gvsh_1 extends TestToolkitApplet
{

    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte)0;

    private static final byte DCS_8_BIT_DATA = 0x04;
    
    private static final byte TYPE = (byte) 0xFF ;
    private static final byte QUALIFIER = (byte) 0xFE ;
    private static final byte DST_DEVICE = (byte) 0xFD ;

    private byte[] TEXT = new byte[240] ;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Gvsh_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Gvsh_1  thisApplet = new Api_2_Pah_Gvsh_1();

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

        // Result of method
        short result = 0 ;

        
        // --------------------------------------------
        // Test Case 1 : Unavailable Element
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init(TYPE, QUALIFIER, DST_DEVICE) ;
            
            try {
                proHdlr.getValueShort((short)0) ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : Out of Tlv Boundaries
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Search Command Details TLV
            proHdlr.findTLV((byte)0x01, (byte)1) ;

            try {
                result = proHdlr.getValueShort((short)3) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : Successful call
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {

            // Search Command Details TLV
            proHdlr.findTLV((byte)0x01, (byte)1) ;

            // Get Qualifier
            result = proHdlr.getValueShort((short)1) ;
            
            bRes = (result == (short)0xFFFE) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : Successful call
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {

            // Search Device Identities TLV
            proHdlr.findTLV((byte)0x02, (byte)1) ;

            // Get Source Device
            result = proHdlr.getValueShort((short)0) ;
            
            bRes = (result == (short)0x81FD) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Initialise Text buffer
        for (short i=0; i<(short)TEXT.length; i++) {
            TEXT[i] = (byte) i ;
        }
        
        // --------------------------------------------
        // Test Case 5 : Successful call
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {

            // Initialise handler
            proHdlr.initDisplayText((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)0x7E) ;
            
            // Find Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;

            result = proHdlr.getValueShort((short)0x7D) ;
            
            bRes = (result == (short)0x7C7D) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 6 : Successful call
        testCaseNb = (byte) 6 ;
        bRes = false ;
        
        try {

            // Initialise handler
            proHdlr.initDisplayText((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)0x7F) ;
            
            // Find Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;

            result = proHdlr.getValueShort((short)0x7D) ;
            
            bRes = (result == (short)0x7C7D) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 7 : Successful call
        testCaseNb = (byte) 7 ;
        bRes = false ;
        
        try {

            result = proHdlr.getValueShort((short)0x7E) ;
            
            bRes = (result == (short)0x7D7E) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : Successful call
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {

            // Initialise handler
            proHdlr.initDisplayText((byte)0, DCS_8_BIT_DATA, TEXT, (short)0, (short)0xF0) ;
            
            // Find Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;

            result = proHdlr.getValueShort((short)0xEF) ;
            
            bRes = (result == (short)0xEEEF) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
