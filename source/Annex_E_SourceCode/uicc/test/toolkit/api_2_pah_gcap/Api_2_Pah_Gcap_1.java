//-----------------------------------------------------------------------------
//Api_2_Pah_Gcap_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_gcap;

import uicc.test.util.*;
import uicc.toolkit.*;
import javacard.framework.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_gcap
 *
 * @version 0.0.1 - 20 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Gcap_1 extends TestToolkitApplet
{

    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
    private byte[] Buffer;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Gcap_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Gcap_1  thisApplet = new Api_2_Pah_Gcap_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) 
    {

        // results of methods
        short sLength = 0;
        short sCapacity = 0;

        // Get the system instance of handlers
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();

        // get capacity of the handler
        testCaseNb = (byte)0x01;
        bRes = false;
        try {
            sCapacity = proHdlr.getCapacity();
            bRes = true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // Fill the handler with the maximum capacity
        testCaseNb = (byte)0x02;
        bRes = false;
        Buffer = new byte[sCapacity];
        Util.arrayFillNonAtomic(Buffer,(short)0,(short)Buffer.length,(byte)0x55);
        
        try {
            if (sCapacity <= 0x81)
            {
                proHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 2));
            }
            else if ((sCapacity > 0x81) && (sCapacity <= 0x102))
            {
                proHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 3));
            }
            else if (sCapacity > 0x102)
            {
                proHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 4));
            }
            bRes = true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    
        // clear the handler
        testCaseNb = (byte)0x03;
        bRes = false;
        try {
            proHdlr.clear();
            bRes = true;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    
        // Fill the handler with the maximum capacity + 1
        testCaseNb = (byte)0x04;
        bRes = false;
        try {
            if (sCapacity <= 0x81)
            {
                proHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 1));
            }
            else if ((sCapacity > 0x81) && (sCapacity <= 0x102))
            {
                proHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 2));
            }
            else if (sCapacity > 0x102)
            {
                proHdlr.appendTLV((byte)0xAA, Buffer, (short)0x00, (short)(sCapacity - 3));
            }
            bRes = false;
        }
        catch (ToolkitException e) {
            if(e.getReason()==ToolkitException.HANDLER_OVERFLOW)
                bRes = true ;
        }
        catch (Exception e)  {    
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
