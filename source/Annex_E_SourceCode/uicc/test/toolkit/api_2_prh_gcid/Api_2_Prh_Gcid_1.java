//-----------------------------------------------------------------------------
//Api_2_Prh_Gcid_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gcid;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gcid
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gcid_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private final byte[] ADDRESS = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
    private final byte[] BEARER_DESC = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
    private final byte textBuffer[] = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'}; 

    private static final byte DCS_8_BIT_DATA = (byte)0x04; 
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Gcid_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gcid_1  thisApplet = new Api_2_Prh_Gcid_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {
        // Miscelaneous
        byte bChannelNb;
                        
        // Get the system instance of handlers
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();
        ProactiveResponseHandler proRespHdlr = null ;

        // Send a Display Text proactive command and get the handler
        // init Display Text command
        proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, textBuffer, (short)0, (short)textBuffer.length);
        // send proactive command
        proHdlr.send();

        // Get the proactive response handler
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        
        // Test Case 1 : getChannelIdentifier(), Channel status TLV is not present
        // -----------------------------------------------------------------------
        testCaseNb = (byte) 1 ;
        bRes = false ;
        try {
            proRespHdlr.getChannelIdentifier();
            bRes = false ;
        }
        catch (ToolkitException e){    
            if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) 
            {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Test Case 2 : getChannelIdentifier(), Channel status TLV with a length equal to 0
        // ---------------------------------------------------------------------------------
        testCaseNb = (byte) 2 ;
        bRes = false ;
        // build an open channel command
        proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        // Send the command
        proHdlr.send();

        // Get the proactive response handler
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        // Get the Channel Id
        try {
            proRespHdlr.getChannelIdentifier();
            bRes = false ;
        }
        catch (ToolkitException e){    
            if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES ) 
            {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Test Case 3 : getChannelIdentifier(), normal test
        // -------------------------------------------------
        testCaseNb = (byte) 3 ;
        bRes = false ;
        // build an open channel command
        proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        // Send the command
        proHdlr.send();

        // Get the proactive response handler
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        // Get the Channel Id
        try {
            if (proRespHdlr.getChannelIdentifier() == 0x01)
            {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        // build an close channel command
        proHdlr.initCloseChannel((byte)0x01);
        // Send the command
        proHdlr.send();
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Test Case 4 : getChannelIdentifier(), with 2 TLV channel id
        // -----------------------------------------------------------
        testCaseNb = (byte) 4 ;
        bRes = false ;
        // build an open channel command
        proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        // Send the command
        proHdlr.send();

        // Get the proactive response handler
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        // Get the Channel Id
        try {
            if (proRespHdlr.getChannelIdentifier() == 0x01)
            {
                bRes = true ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        // build an close channel command
        proHdlr.initCloseChannel((byte)0x01);
        // Send the command
        proHdlr.send();
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Test Case 5 : getChannelIdentifier(), Channel status TLV is currently selected TLV
        // ----------------------------------------------------------------------------------
        testCaseNb = (byte) 5 ;
        bRes = false ;
        // build an open channel command
        proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        proHdlr.appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        // Send the command
        proHdlr.send();

        // Get the proactive response handler
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        // check the currently selected TLV
        try {
            proRespHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte) 0x01);
            if ((proRespHdlr.getChannelIdentifier() == (byte) 0x03) &&
                (proRespHdlr.getChannelIdentifier() == proRespHdlr.getValueByte((byte)0x00)))
                bRes = true;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
