//-----------------------------------------------------------------------------
//Api_2_Enh_Gcid_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gcid;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.test.util.*;

/**
 * Test Area : uicc.test.toolkit.api_2_enh_gcid
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Gcid_1  extends TestToolkitApplet
{
    private final byte[] ADDRESS = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
    private final byte[] BEARER_DESC = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
    private static byte[] menuEntry = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)' ',(byte)'1'};
    // Number of tests
    byte testCaseNb = (byte) 0x00;
    private static final byte DEV_ID_ME = (byte)0x82;
    
    /**
    * Constructor of the applet
    */
    public Api_2_Enh_Gcid_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Gcid_1  thisApplet = new Api_2_Enh_Gcid_1 ();

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
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

        if (event == EVENT_MENU_SELECTION)
        {
            // The Applet open all the 7 channels by sending 7 Open Channel commands
            proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, DEV_ID_ME);
            proHdlr.appendTLV(TAG_ADDRESS, ADDRESS, (short)0x00, (short)ADDRESS.length);
            // CSD Bearer Description
            proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_DESC, (short)0x00, (short)BEARER_DESC.length);
            // Buffer size
            proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
            for (bChannelNb = 0; bChannelNb < 7; bChannelNb++)
            {
                proHdlr.send() ;
            }
        }
        else
        {
                switch ( testCaseNb ) 
                    {
                            case (byte) 0 :
                                    // --------------------------------------------
                                    // Test Case 1 : getChannelIdentifier(), normal execution
                                    testCaseNb = (byte) 1 ;
                                    bRes = false ;
                                    try {
                                            if (envHdlr.getChannelIdentifier() == 0x01)
                                                    bRes = true;
                                    }
                                    catch (Exception e)    {    
                                            bRes = false ;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
                            
                            case (byte) 1 :
                                    // --------------------------------------------
                                    // Test Case 2 : Twice calling of getChannelIdentifier() with 2 
                                    // Channel Status TLV
                                    testCaseNb = (byte) 2 ;
                                    bRes = false ;
                                    try {
                                            if (envHdlr.getChannelIdentifier() == 0x04)
                                                if (envHdlr.getChannelIdentifier() == 0x04)
                                                        bRes = true;
                                    }
                                    catch (Exception e)    {    
                                            bRes = false ;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
    
                            case (byte) 2 :
                                    // --------------------------------------------
                                    // Test Case 3 : Channel Status TLV is selected after the call
                                    //  of getChannelIdentifier()
                                    testCaseNb = (byte) 3 ;
                                    bRes = false ;
                                    try {
                                         envHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte) 0x01);
                                            if (envHdlr.getChannelIdentifier() == 
                                                envHdlr.getValueByte((byte)0x00))
                                                     bRes = true;
                                    }
                                    catch (Exception e)    {    
                                            bRes = false ;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
    
                            case (byte) 3 :
                                    // --------------------------------------------
                                    // Test Case 4 : getChannelIdentifier() with no Channel Status TLV
                                    testCaseNb = (byte) 4 ;
                                    bRes = false ;
                                    try {
                                        envHdlr.getChannelIdentifier();
                                        bRes = false;
                                    }
                                    catch (ToolkitException e){    
                                       if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) 
                                      {
                                         bRes = true ;
                                      }
                                    }
                                    catch (Exception e) {    
                                            bRes = false ;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
    
                            case (byte) 4 :
                                    // --------------------------------------------
                                    // Test Case 5 : getChannelIdentifier() on a clased channel
                                    testCaseNb = (byte) 5 ;
                                    bRes = false ;
                                    try {
                                            if (envHdlr.getChannelIdentifier() == 0x06)
                                            bRes = true;
                                    }
                                    catch (Exception e) {    
                                            bRes = false ;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
    
                            case (byte) 5 :
                                    // --------------------------------------------
                                    // Test Case 5 : getChannelIdentifier() with an empty Channel Status TLV
                                    testCaseNb = (byte) 6 ;
                                    bRes = false ;
                                    try {
                                        envHdlr.getChannelIdentifier();
                                        bRes = false;
                                    }
                                    catch (ToolkitException e){    
                                       if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) 
                                       {
                                         bRes = true ;
                                       }
                                    }
                                    catch (Exception e) {    
                                            bRes = false ;
                                    }
                                    reportTestOutcome(testCaseNb, bRes) ;
                                    break;
            } // End switch
        }
    }
}
