//-----------------------------------------------------------------------------
//Api_2_Erh_Gcap_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_gcap;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_gcap
 *
 * @version 0.0.1 - 11 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Gcap_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte[] Buffer;    
    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Gcap_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Gcap_1  thisApplet = new Api_2_Erh_Gcap_1();

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
        boolean bRes;
        
        // results of methods
        short sLength = 0;
        short sCapacity = 0;

        // Get the system instance of handlers
        EnvelopeResponseHandler envRespHdlr = EnvelopeResponseHandlerSystem.getTheHandler();
        
        
        // get capacity of the handler
        testCaseNb = (byte)0x01;
        bRes = false;
        try {
            sCapacity = envRespHdlr.getCapacity();
            bRes = true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        
        // Fill the handler with the maximum capacity
        Buffer = new byte[sCapacity];
        
        for (short i=0; i<sCapacity; i++)
            Buffer[i] = (byte)0x55;
            
        try {
            if (sCapacity <= 0x81)
            {
                envRespHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 2));
            }
            else if ((sCapacity > 0x81) && (sCapacity <= 0x102))
            {
                envRespHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 3));
            }
            else if (sCapacity > 0x102)
            {
                envRespHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 4));
            }
            bRes &= true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        
        // clear the handler
        try {
            envRespHdlr.clear();
            bRes &= true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
    
        // Fill the handler with the maximum capacity + 1
        try {
            if (sCapacity <= 0x81)
            {
                envRespHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 1));
            }
            else if ((sCapacity > 0x81) && (sCapacity <= 0x102))
            {
                envRespHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 2));
            }
            else if (sCapacity > 0x102)
            {
                envRespHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 3));
            }
            bRes = false;
        }
        catch (ToolkitException e) {
            if(e.getReason()==ToolkitException.HANDLER_OVERFLOW)
                bRes &= true ;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
