//-----------------------------------------------------------------------------
//Api_2_Pah_Gvle_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_gvle;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_gvle
 *
 * @version 0.0.1 - 14 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Gvle_1 extends TestToolkitApplet
{
    private byte TYPE = (byte) 0x21 ;
    private byte QUALIFIER = (byte) 0x00 ;
    private byte DST_DEVICE = (byte) 0x82 ;

    private byte[] TEXT = new byte[240] ;
    
    private byte[] buffer1          = new byte[1];
    private byte DCS_8_BIT_DATA = (byte) 0x04;

    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Gvle_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Gvle_1  thisApplet = new Api_2_Pah_Gvle_1();

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

        byte tag = 0 ;
        short ValueOffset = 0 ;
        short ValueLength = 0 ;
        
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
                proHdlr.getValueLength() ;
                
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 2 : Successful call
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            tag = (byte) (0x0D) ;
            ValueOffset = (short)0 ;
            ValueLength = (short)0 ;
                          
            proHdlr.appendTLV(tag, buffer1, ValueOffset, ValueLength) ;

            // Search Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;
            
            // Get length
            result = proHdlr.getValueLength() ;
            
            bRes = (result == (short)0) ;
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
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)1) ;

            // Search Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;
            
            // Get length
            result = proHdlr.getValueLength() ;
            
            bRes = (result == (short)2) ;
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
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)0x7E) ;

            // Search Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;
            
            // Get length
            result = proHdlr.getValueLength() ;
            
            bRes = (result == (short)0x7F) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Successful call
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)0x7F) ;

            // Search Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;
            
            // Get length
            result = proHdlr.getValueLength() ;
            
            bRes = (result == (short)0x80) ;
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
            
            // Initialise the handler
            proHdlr.initDisplayText(QUALIFIER, DCS_8_BIT_DATA, TEXT, (short)0, (short)0xF0) ;

            // Search Text String TLV
            proHdlr.findTLV((byte)0x0D, (byte)1) ;
            
            // Get length
            result = proHdlr.getValueLength() ;
            
            bRes = (result == (short)0xF1) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
