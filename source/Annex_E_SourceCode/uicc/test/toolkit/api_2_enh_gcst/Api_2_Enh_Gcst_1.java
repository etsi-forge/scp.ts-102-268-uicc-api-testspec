//-----------------------------------------------------------------------------
//Api_2_Enh_Gcst_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gcst;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Gcst
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Gcst_1 extends TestToolkitApplet
{
    private final byte[] ADDRESS = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
    private final byte[] BEARER_DESC = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
    private static byte[] menuEntry = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'1'};
    private final byte[] DisplayText = {(byte)'A',(byte)'P',(byte)'P',(byte)'L',(byte)'E',(byte)'T',(byte)'1'};
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;
    private static final byte DEV_ID_ME = (byte)0x82;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Enh_Gcst_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Gcst_1  thisApplet = new Api_2_Enh_Gcst_1 ();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    

        // Set Event Download Channel Status as an activation event for the applet
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS);
                // Set Unrecognized Envelope as an activation event for the applet
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        //register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(menuEntry,(short)0,(short)menuEntry.length,(byte)0,false,(byte)0,(short)0);
    }
    

    /**
       * Method called by the SIM Toolkit Framework
     */
    public void processToolkit(short event) 
    {
        // Result of tests
        boolean bRes;
        // Miscelaneous
        byte bChannelNb;
                        
        // Get the system instance of handlers
        EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
        
        if (event == EVENT_MENU_SELECTION)
        {
            // The Applet send an open channel proactive command
                (ProactiveHandlerSystem.getTheHandler()).init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_ME);
                (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
            // CSD Bearer Description
                (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
            // Buffer size
                (ProactiveHandlerSystem.getTheHandler()).appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
                (ProactiveHandlerSystem.getTheHandler()).send() ;
            
        }
        else
        {
                switch ( testCaseNb ) 
                    {
                            case (byte) 0 :
                                    // --------------------------------------------
                                    // Test Case 1 : getChannelStatus(), with no Channel Status TLV
                                    testCaseNb = (byte) 1 ;
                                    bRes = false ;
                                    try {
                                            envHdlr.getChannelStatus((byte)0x01);
                                    }
                                    catch (ToolkitException e)
                                        {    
                                            if(e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT)
                                                    bRes = true;
                                            else
                                                    bRes = false;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
                            
                            case (byte) 1 :
                                    // --------------------------------------------
                                    // Test Case 2 : getChannelStatus(), with no wrong identifier
                                    testCaseNb = (byte) 2 ;
                                    bRes = false ;
                                    try {
                                            envHdlr.getChannelStatus((byte)0x01);
                                    }
                                    catch (ToolkitException e)
                                        {    
                                            if(e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT)
                                                    bRes = true;
                                            else
                                                    bRes = false;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
    
                            case (byte) 2 :
                                    // --------------------------------------------
                                    // Test Case 3 : Channel Status TLV with length set to 00
                                    testCaseNb = (byte) 3 ;
                                    bRes = false ;
                                        try {
                                                envHdlr.getChannelStatus((byte)0x01);
                                        }
                                        catch (ToolkitException e)
                                        {    
                                                if(e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT)
                                                        bRes = true;
                                                else
                                                        bRes = false;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
    
                            case (byte) 3 :
                                    // --------------------------------------------
                                    // Test Case 4 : getChannelStatus() with length set to 01
                                    testCaseNb = (byte) 4 ;
                                    bRes = false ;
                                        try {
                                                envHdlr.getChannelStatus((byte)0x01);
                                        }
                                        catch (ToolkitException e)
                                        {    
                                                if(e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                        bRes = true;
                                                else
                                                        bRes = false;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
    
                            case (byte) 4 :
                                    // --------------------------------------------
                                    // Test Case 5 : getChannelStatus() value
                                    testCaseNb = (byte) 5 ;
                                    bRes = false ;
                                        try {
                                                if(envHdlr.getChannelStatus((byte)0x01)== (short)0x8100)
                                                        bRes =true;
                                        }
                                        catch (ToolkitException e)
                                        {    
                                                bRes = false;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
    
                            case (byte) 5 :
                                    // --------------------------------------------
                                    // Test Case 6 : getChannelStatus() with two TLV
                                    testCaseNb = (byte) 6 ;
                                    bRes = false ;
                                        try {
                                                if(envHdlr.getChannelStatus((byte)0x01)== (short)0x8100)
                                                        bRes =true;
                                        }
                                        catch (ToolkitException e)
                                        {    
                                                bRes = false;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;

                            case (byte) 6 :
                                    // --------------------------------------------
                                    // Test Case 7 : Channel Status TLV is the currently selected TLV
                                    testCaseNb = (byte) 7 ;
                                    bRes = false ;
                                        try {
                                                // Find the Device ID TLV anc compare to the result of getChannelStatus
                                                envHdlr.findTLV(DEV_ID_TERMINAL,(byte)0x01); // Set the current TLV to Device Id TLV
                                                short ChannelStatusValue = envHdlr.getChannelStatus((byte)0x01);
                                                if((envHdlr.getChannelStatus((byte)0x01)== envHdlr.getValueShort((short)0))
                                                && (ChannelStatusValue== (short)0x8100))
                                                        bRes = true;
                                                else
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)
                                        {    
                                                bRes = false;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;

                            case (byte) 7 :
                                    // --------------------------------------------
                                    // Test Case 8 : Get channel Status value after proactive command
                                    testCaseNb = (byte) 8 ;
                                    bRes = false ;
                                        try {
                                                short ChannelStatusValue = envHdlr.getChannelStatus((byte)0x01);
                                                (ProactiveHandlerSystem.getTheHandler()).initDisplayText((byte)00,(byte)0xF6,DisplayText,(short)0,(short)DisplayText.length);
                                                (ProactiveHandlerSystem.getTheHandler()).send();
                                                if((envHdlr.getChannelStatus((byte)0x01)) == ChannelStatusValue)
                                                        bRes = true;
                                                else
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)
                                        {    
                                                bRes = false;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                    } // End switch
            }
    }
}
