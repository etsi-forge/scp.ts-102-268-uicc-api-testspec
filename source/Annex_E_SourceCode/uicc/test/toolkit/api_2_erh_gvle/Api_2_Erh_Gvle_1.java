//-----------------------------------------------------------------------------
//Api_2_Erh_Gvle_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_gvle;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_gvle
 *
 * @version 0.0.1 - 6 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Gvle_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Gvle_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Gvle_1  thisApplet = new Api_2_Erh_Gvle_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event) {
        // Result of tests
        boolean bRes ;

        // Number of tests
        byte testCaseNb = (byte) 0x00 ;
        
        // Get the system instance of the EnvelopeResponseHandler class
        EnvelopeResponseHandler EnvRespHdlr = EnvelopeResponseHandlerSystem.getTheHandler() ;

        // --------------------------------------------
        // Test Case 1 : Unavailable Element
        testCaseNb = (byte) 1 ;
        bRes = false ;

        // prepare the TLV before updating 
        byte[] data=new byte[0xFF];
        for( short i=0;i<(short)0xFF;i++)
            data[i]=(byte)2;

        try {

            //append tlv and find a bad TLV
            EnvRespHdlr.appendTLV((byte)0x33,data,(short) 0x00, (short) 0x04);
            EnvRespHdlr.findTLV((byte)0x03,(byte)1) ;
            EnvRespHdlr.getValueLength() ;
        }
        catch (ToolkitException e) {
            bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 2 : Successful call with length=0
        testCaseNb = (byte) 2 ;
        bRes = false ;

        try {
            //append tlv with a length of 0
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0x00);
            // Search the TLV
            if( (EnvRespHdlr.findTLV((byte)0x0D, (byte)1)==TLV_FOUND_CR_NOT_SET)
             && (EnvRespHdlr.getValueLength()==0))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : Successful call with length=2
        testCaseNb = (byte) 3 ;
        bRes = false ;

        try {
            // clear the handler and append TLV with the new
            EnvRespHdlr.clear();
            //append tlv with a length of 02
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0x02);
            // Search Text String TLV
            EnvRespHdlr.findTLV((byte)0x0D, (byte)1) ;
            // Get length
            if(EnvRespHdlr.getValueLength()==2)
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 4 : Successful call with length=0x7F
        testCaseNb = (byte) 4 ;
        bRes = false ;

        try {
            // clear the handler and append TLV with the new
            EnvRespHdlr.clear();

            //append tlv with a length of 7F
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0x7F);

            // Search Text String TLV
            EnvRespHdlr.findTLV((byte)0x0D, (byte)1) ;

            // Get length
            if(EnvRespHdlr.getValueLength()==0x7F)
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Successful call with length=0x80
        testCaseNb = (byte) 5 ;
        bRes = false ;

        try {
            // clear the handler and append TLV with the new
            EnvRespHdlr.clear();

            //append tlv with a length of 80
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0x80);

            // Search Text String TLV
            EnvRespHdlr.findTLV((byte)0x0D, (byte)1) ;

            // Get length
            if(EnvRespHdlr.getValueLength()==0x80)
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 6 : Successful call with length=0xF1
        testCaseNb = (byte) 6 ;
        bRes = false ;

        try {
            // clear the handler and append TLV with the new
            EnvRespHdlr.clear();

            //append tlv with a length of FA
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0xFA);

            // Search Text String TLV
            EnvRespHdlr.findTLV((byte)0x0D, (byte)1) ;

            // Get length
            if(EnvRespHdlr.getValueLength()==0xFA)
                bRes = true ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 7 : ToolkitException HANDLER_NO_AVAILABLE
        testCaseNb = (byte) 7 ;
        bRes = false ;

        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            try {
                EnvRespHdlr.getValueLength();
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
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
