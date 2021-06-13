//-----------------------------------------------------------------------------
//Api_2_Enh_Gtsz_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gtsz;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_enh_gtsz
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Gtsz_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;
    private final byte[] DisplayText = {(byte)'A',(byte)'P',(byte)'P',(byte)'L',(byte)'E',(byte)'T',(byte)'1'};
    
    /**
    * Constructor of the applet
    */
    public Api_2_Enh_Gtsz_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Gtsz_1  thisApplet = new Api_2_Enh_Gtsz_1();

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
        
        // Get the system instance of handlers
                EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
                
                // --------------------------------------------
                // Test Case 1 : GetSize() after applet triggering
                testCaseNb = (byte) 1 ;
                bRes = false ;
                try {
                        if(envHdlr.getSize() == (byte)0x33)
                                bRes = true;
                        else
                                bRes = false;
                }
                catch (ToolkitException e)
                {    
                        bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes) ;

                // --------------------------------------------
                // Test Case 2 : Getsize() after proactive command
                
                (ProactiveHandlerSystem.getTheHandler()).initDisplayText((byte)00,(byte)0xF6,DisplayText,(short)0,(short)DisplayText.length);
                (ProactiveHandlerSystem.getTheHandler()).send();
                
                testCaseNb = (byte) 2 ;
                bRes = false ;
                try {
                        if(envHdlr.getSize() == (byte)0x33)
                                bRes = true;
                        else
                                bRes = false;
                }
                catch (ToolkitException e)
                {    
                        bRes = false;
                }
                reportTestOutcome(testCaseNb, bRes) ;
        }

}
