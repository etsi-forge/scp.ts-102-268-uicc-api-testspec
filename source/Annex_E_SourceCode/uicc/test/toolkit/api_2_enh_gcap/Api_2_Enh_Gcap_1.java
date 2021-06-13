//-----------------------------------------------------------------------------
//Api_2_Enh_Gcap_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gcap;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_enh_gcap
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Gcap_1 extends TestToolkitApplet
{

    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
    /**
    * Constructor of the applet
    */
    public Api_2_Enh_Gcap_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Gcap_1  thisApplet = new Api_2_Enh_Gcap_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();   
        
            //register to EVENT_UNRECOGNIZED_ENVELOPE
            thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

    }
    

    /**
       * Method called by the SIM Toolkit Framework
     */
    public void processToolkit(short event) 
    {
        // Result of tests
        boolean bRes;
        
        // results of methods
        short sLength = 0;
        short sCapacity = 0;
                
        // Get the system instance of handlers
        EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
        
        // Get length of the BER TLV
        bRes = false;
        testCaseNb++;
        try {
             sLength = envHdlr.getLength();
             bRes = true;
        }
        catch (Exception e)  {    
              bRes = false;
        }
        
        // get capacity of the handler
        try {
            sCapacity = envHdlr.getCapacity();
            bRes &= true;
        }
        catch (Exception e)  {    
                bRes &= false;
        }
            
        // Capacity must be greater than length of BER TLV
        if (sCapacity < sLength)
        {
               bRes &= false;
        }
        else
        {
               bRes &= true;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
