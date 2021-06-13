//-----------------------------------------------------------------------------
//Api_2_Prh_Gcst_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gcst;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gcst
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gcst_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;
    private byte[] ADDRESS = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
    private byte[] BEARER_DESC = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
    private byte[] menuEntry = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'1'};
    private byte[] TEXT = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'} ;

    public final byte DCS_8_BIT_DATA = (byte)0x04;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Gcst_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gcst_1  thisApplet = new Api_2_Prh_Gcst_1();

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
        short ChannelStatusValue;
                        
        // Get the system instance of handlers
        ProactiveResponseHandler ProRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        
        // ----------------------------------------------------------------
        // Test Case 1 : Get channel Status TLV is not present
        testCaseNb = (byte)1;
        bRes = false ;
        try {
                (ProactiveHandlerSystem.getTheHandler()).initDisplayText((byte)00,DCS_8_BIT_DATA,TEXT,(short)0,(short)TEXT.length);
                (ProactiveHandlerSystem.getTheHandler()).send();
                try{
                    ProRespHdlr.getChannelStatus((byte)0x01);
                   bRes = false;
                }
                catch(ToolkitException e)
                {
                   if(e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT)
                       bRes = true;
                }
        }
        catch (Exception e)
        {    
                bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------------------------------
        // Test Case 2 : Channel status TLV with the identifier is not present
        testCaseNb = (byte)2;
        bRes = false;
        
        // Send an OPEN CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            ProRespHdlr.getChannelStatus((byte)0x02);
        }
        catch(ToolkitException e){
            if(e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT)
                bRes = true;
        }
        catch (Exception e)
        {    
                bRes = false;
        }
        // Send a CLOSE CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).initCloseChannel((byte)0x01);
        (ProactiveHandlerSystem.getTheHandler()).send();
        
        reportTestOutcome(testCaseNb, bRes) ;

        // ------------------------------------------------------
        // Test Case 3 : Channel status TLV with length set to 0.
        testCaseNb = (byte)3;
        bRes = false;
        
        // Send an OPEN CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            ProRespHdlr.getChannelStatus((byte)0x01);
        }
        catch(ToolkitException e){
            if(e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT)
                bRes = true;
        }
        catch (Exception e)
        {    
                bRes = false;
        }

        reportTestOutcome(testCaseNb, bRes) ;
        
        // ------------------------------------------------------
        // Test Case 4 : Channel status TLV with length set to 1.
        testCaseNb = (byte)4;
        bRes = false;
        
        // Send an OPEN CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            ProRespHdlr.getChannelStatus((byte)0x01);
        }
        catch(ToolkitException e){
            if(e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES)
                bRes = true;
        }
        catch (Exception e)
        {    
                bRes = false;
        }

        reportTestOutcome(testCaseNb, bRes) ;
        
        // ------------------------------------------------------
        // Test Case 5 : Get channel status value
        testCaseNb = (byte)5;
        bRes = false;
        
        // Send an OPEN CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            ChannelStatusValue = ProRespHdlr.getChannelStatus((byte)0x01);
            bRes = (ChannelStatusValue == (short)0x8100);
        }
        catch (Exception e)
        {    
                bRes = false;
        }

        reportTestOutcome(testCaseNb, bRes) ;
        
        // ------------------------------------------------------
        // Test Case 6 : Get channel status value with 2 TLVs
        testCaseNb = (byte)6;
        bRes = false;
        
        // Send an GET_CHANNEL_STATUS proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_GET_CHANNEL_STATUS, (byte)0x00, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            ChannelStatusValue = ProRespHdlr.getChannelStatus((byte)0x01);
            bRes = (ChannelStatusValue == (short)0x8100);
        }
        catch (Exception e)
        {    
                bRes = false;
        }

        reportTestOutcome(testCaseNb, bRes) ;
        
        // ------------------------------------------------------
        // Test Case 7 : Get channel status value with 2 TLVs
        testCaseNb = (byte)7;
        bRes = false;
        
        // Send an GET_CHANNEL_STATUS proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_GET_CHANNEL_STATUS, (byte)0x00, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            ChannelStatusValue = ProRespHdlr.getChannelStatus((byte)0x01);
            bRes = (ChannelStatusValue == (short)0x8100);
        }
        catch (Exception e)
        {    
                bRes = false;
        }

        // Send a CLOSE CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).initCloseChannel((byte)0x01);
        (ProactiveHandlerSystem.getTheHandler()).send();

        reportTestOutcome(testCaseNb, bRes) ;
        
        // ---------------------------------------------------------------
        // Test Case 8 : Channel Status TLV is the currently selected TLV
        testCaseNb = (byte)8;
        bRes = false;
        
        // Send an OPEN CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_TERMINAL);
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
        // CSD Bearer Description
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
        // Buffer size
        (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
        (ProactiveHandlerSystem.getTheHandler()).send() ;
        
        try{
            // Select the device identities TLV
            ProRespHdlr.findTLV(TAG_DEVICE_IDENTITIES,(byte)0x01);
            ChannelStatusValue = ProRespHdlr.getChannelStatus((byte)0x03);
            bRes = (ChannelStatusValue == (short)0x8304);
            bRes &= (ChannelStatusValue == ProRespHdlr.getValueShort((short)0));
        }
        catch (Exception e)
        {    
                bRes = false;
        }

        // Send a CLOSE CHANNEL proactive command
        (ProactiveHandlerSystem.getTheHandler()).initCloseChannel((byte)0x03);
        (ProactiveHandlerSystem.getTheHandler()).send();

        reportTestOutcome(testCaseNb, bRes) ;
    }
}
