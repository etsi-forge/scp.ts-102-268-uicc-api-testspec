//-----------------------------------------------------------------------------
//Api_2_Prh_Gcap_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_prh_gcap;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_prh_gcap
 *
 * @version 0.0.1 - 27 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Prh_Gcap_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private final byte textBuffer[] = {(byte)'T',(byte)'e',(byte)'x',(byte)'t'};

    private static final byte DCS_8_BIT_DATA = (byte)0x04; 
    
    /**
     * Constructor of the applet
     */
    public Api_2_Prh_Gcap_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Prh_Gcap_1  thisApplet = new Api_2_Prh_Gcap_1();

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
        bRes = false;
        
        // results of methods
        short sLength = 0;
        short sCapacity = 0;
                
        // Get the system instance of handlers
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();
        ProactiveResponseHandler proRespHdlr = null ;
       
        // Init and send a Display Test proactive command
        proHdlr.initDisplayText((byte)0x00, DCS_8_BIT_DATA, textBuffer, (short)0, (short)textBuffer.length);
        proHdlr.send();
        
        // Get the response handler
        proRespHdlr = ProactiveResponseHandlerSystem.getTheHandler();
        
        // Get length of the proactive response handler
        bRes = false;
        testCaseNb = (byte)0x01;
        try {
            sLength = proRespHdlr.getLength();
            bRes = true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // get capacity of the proactive response handler
        bRes = false;
        testCaseNb = (byte)0x02;
        try {
            sCapacity = proRespHdlr.getCapacity();
            bRes = true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Capacity must be greater than length of proactive response handler
        bRes = false;
        testCaseNb = (byte)0x03;
        if (sCapacity >= sLength)
        {
            bRes = true;
        }
        else
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
