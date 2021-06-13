//-----------------------------------------------------------------------------
//Api_2_Erh_Cler_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_cler;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_cler
 *
 * @version 0.0.1 - 11 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Cler_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte compareBuffer[]    = new byte[256] ;
    private byte buffer5[]          = new byte[5] ;
    private byte buffer256[]        = new byte[256] ;
    private byte buffer[]           = new byte[32] ;
    private byte copyBuffer[]       = new byte[256] ;
    
    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Cler_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Cler_1  thisApplet = new Api_2_Erh_Cler_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) {
        
        // Result of each test
        boolean bRes = false ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;

        // Get the system instance of the EnvelopeResponseHandler class
        EnvelopeResponseHandler EnvRespHdlr = EnvelopeResponseHandlerSystem.getTheHandler() ;
        
        // --------------------------------------------
        // Test Case 1 : Successful call
        testCaseNb = (byte) 1 ;
        bRes = false ;
        
        try {
        
            byte[] buffer=new byte[4];
            
            //built the first tlv
            buffer[0]=(byte)0x11;
            buffer[1]=(byte)0x22;
            buffer[3]=(byte)0x33;
            EnvRespHdlr.appendTLV((byte)0x81,buffer,(short) 0x00, (short) 3);
        
            //built the second tlv
            buffer[0]=(byte)0x99;
            buffer[1]=(byte)0x77;
            EnvRespHdlr.appendTLV((byte)0x82,buffer,(short) 0x00, (short) 2);
            
            // Select command Details TLV
            EnvRespHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;
            
            // Verify the length of the handler
            bRes = (EnvRespHdlr.getLength() == 9) ;
            
        }
        catch (Exception e) {
            bRes = false ;
        }
        
        try {
        
            //clear the handler
            EnvRespHdlr.clear() ;
            
            // Verify the length of the handler
            bRes &= (EnvRespHdlr.getLength() == 0) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 2 : A successful call resets the current TLV selected
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            try {
                EnvRespHdlr.getValueLength() ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : ToolkitException HANDLER_NOT_AVAILABLE
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            try {
                EnvRespHdlr.clear();
                bRes=false;
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.HANDLER_NOT_AVAILABLE) {
                    bRes= true;
                } else {
                    bRes=false;
                }
            }
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);


    }
}
