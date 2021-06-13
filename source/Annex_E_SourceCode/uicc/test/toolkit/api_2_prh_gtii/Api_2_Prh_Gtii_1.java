//-----------------------------------------------------------------------------
//Api_2_Prh_Gtii_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gtii;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gtii
 *
 * @version 0.0.1 - 24 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gtii_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;

    private byte[] ITEM1 = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'1'} ;
    private byte[] ITEM2 = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'2'} ;
    private byte[] ITEM3 = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'3'} ;
    private byte[] ITEM5 = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'5'} ;
    private byte[] ITEM7 = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'7'} ;
    private byte[] ITEMFD = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'D'} ;
    private byte[] ITEMFE = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'E'} ;
    private byte[] ITEMFF = {(byte)'I',(byte)'t',(byte)'e',(byte)'m',(byte)'F'} ;

    private static final byte ITEM1_ID = (byte) 1 ;
    private static final byte ITEM2_ID = (byte) 2 ;
    private static final byte ITEM3_ID = (byte) 3 ;
    private static final byte ITEM5_ID = (byte) 5 ;
    private static final byte ITEM7_ID = (byte) 7 ;
    private static final byte ITEMFD_ID = (byte) 0xFD ;
    private static final byte ITEMFE_ID = (byte) 0xFE ;
    private static final byte ITEMFF_ID = (byte) 0xFF ;

    private static final byte TAG_ITEM_CR = (byte)(TAG_ITEM | TAG_SET_CR) ;
    public final byte DCS_8_BIT_DATA = (byte)0x04;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Gtii_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gtii_1  thisApplet = new Api_2_Prh_Gtii_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

        ProactiveResponseHandler proRespHdlr = null ;
    
        short result = (short) 0 ;
        
        // --------------------------------------------
        // Test Case 1 : Unavailable Item Identifier TLV
        
        testCaseNb = (byte) 1 ;
        
        bRes = false ;
        try {
            
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // getItemIdentifier() with unavailable Item Identifier TLV
                result = proRespHdlr.getItemIdentifier() ;
                
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) {
                    bRes = true ;
                }
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 2 : Successfull call
        
        testCaseNb = (byte) 2 ;
        
        bRes = false ;
        try {
            
            // Build and send a SELECT ITEM proactive command
            proHdlr.init(PRO_CMD_SELECT_ITEM, (byte)0, DEV_ID_TERMINAL) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEM1_ID, ITEM1, (short)0, (short)ITEM1.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEM2_ID, ITEM2, (short)0, (short)ITEM2.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getItemIdentifier() ;
            
            bRes = (result == ITEM1_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : Verify the TLV selected
        
        testCaseNb = (byte) 3 ;
        
        bRes = false ;
        try {
            
            // Get 1st byte of the current TLV
            result = proRespHdlr.getValueByte((short)0x00) ;
            
            bRes = (result == ITEM1_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 4 : Successfull call
        
        testCaseNb = (byte) 4 ;
        
        bRes = false ;
        try {
            
            // Build and send a SELECT ITEM proactive command
            proHdlr.init(PRO_CMD_SELECT_ITEM, (byte)0, DEV_ID_TERMINAL) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEM3_ID, ITEM3, (short)0, (short)ITEM3.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEM5_ID, ITEM5, (short)0, (short)ITEM5.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEM7_ID, ITEM7, (short)0, (short)ITEM7.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getItemIdentifier() ;
            
            bRes = (result == ITEM5_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Verify the TLV selected
        
        testCaseNb = (byte) 5 ;
        
        bRes = false ;
        try {
            
            // Get 1st byte of the current TLV
            result = proRespHdlr.getValueByte((short)0x00) ;
            
            bRes = (result == ITEM5_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 6 : Successfull call
        
        testCaseNb = (byte) 6 ;
        
        bRes = false ;
        try {
            
            // Build and send a SELECT ITEM proactive command
            proHdlr.init(PRO_CMD_SELECT_ITEM, (byte)0, DEV_ID_TERMINAL) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEMFD_ID, ITEMFD, (short)0, (short)ITEMFD.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEMFE_ID, ITEMFE, (short)0, (short)ITEMFE.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEMFF_ID, ITEMFF, (short)0, (short)ITEMFF.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getItemIdentifier() ;
            
            bRes = (result == ITEMFF_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 7 : Verify the TLV selected
        
        testCaseNb = (byte) 7 ;
        
        bRes = false ;
        try {
            
            // Get 1st byte of the current TLV
            result = proRespHdlr.getValueByte((short)0x00) ;
            
            bRes = (result == ITEMFF_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 8 : Successfull call (Response with 2 Item Identifier TLV)
        
        testCaseNb = (byte) 8 ;
        
        bRes = false ;
        try {
            
            // Build and send a SELECT ITEM proactive command
            proHdlr.init(PRO_CMD_SELECT_ITEM, (byte)0, DEV_ID_TERMINAL) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEMFD_ID, ITEMFD, (short)0, (short)ITEMFD.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEMFE_ID, ITEMFE, (short)0, (short)ITEMFE.length) ;
            proHdlr.appendTLV(TAG_ITEM_CR, ITEMFF_ID, ITEMFF, (short)0, (short)ITEMFF.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            result = proRespHdlr.getItemIdentifier() ;
            
            bRes = (result == ITEMFF_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 9 : Verify the TLV selected
        
        testCaseNb = (byte) 9 ;
        
        bRes = false ;
        try {
            
            // Get 1st byte of the current TLV
            result = proRespHdlr.getValueByte((short)0x00) ;
            
            bRes = (result == ITEMFF_ID) ;
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        
        // --------------------------------------------
        // Test Case 10 : No value in Item Identifier Simple TLV
        
        testCaseNb = (byte) 10 ;
        
        bRes = false ;
        try {
            
            // Build and send a proactive command
            proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, TEXT, (short)0, (short)TEXT.length) ;
        
            proHdlr.send() ;
            
            // Get the response
            proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler() ;
            
            try {
                
                // getItemIdentifier() without item identifier byte
                result = proRespHdlr.getItemIdentifier() ;
                
            } catch (ToolkitException e) {
                
                if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) {
                    bRes = true ;
                }
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
    }
}
