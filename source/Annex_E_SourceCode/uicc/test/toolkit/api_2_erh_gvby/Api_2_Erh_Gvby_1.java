//-----------------------------------------------------------------------------
//Api_2_Erh_Gvby_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_gvby;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_gvby
 *
 * @version 0.0.1 - 6 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Gvby_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Gvby_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Gvby_1  thisApplet = new Api_2_Erh_Gvby_1();

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
        byte[] data = new byte[0xFF];

        try {
            //preparation of the append TLV
            data[0]=(byte)0x81;
            data[1]=(byte)0x82;
            //built the first tlv
            EnvRespHdlr.appendTLV((byte)0x82,data,(short) 0x00, (short) 0x02);

            data[0]=(byte)0x11;
            data[1]=(byte)0x22;
            data[2]=(byte)0xFE;
            //built the second TLV
            EnvRespHdlr.appendTLV((byte)0x81,data,(short) 0x00, (short) 0x03);

            // Find Text String TLV
            if((EnvRespHdlr.findTLV((byte)0x03, (byte)1)==TLV_NOT_FOUND)&&
                (EnvRespHdlr.getValueByte((short)0)==0))
                    bRes=false;
        }
        catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 2 : Out of TLV Boundaries
        testCaseNb = (byte) 2 ;
        bRes = false ;

        try {
            // Find String TLV
            if((EnvRespHdlr.findTLV((byte)0x01, (byte)1)==TLV_FOUND_CR_SET)&&
                (EnvRespHdlr.getValueByte((short)3)==0))
                    bRes=false;
        }
        catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : Successful call
        testCaseNb = (byte) 3 ;
        bRes = false ;

        try {
            // Find String TLV
            if((EnvRespHdlr.findTLV((byte)0x01, (byte)1)==TLV_FOUND_CR_SET)&&
                (EnvRespHdlr.getValueByte((short)2)==(byte)0xFE))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 4 : Successful call
        testCaseNb = (byte) 4 ;
        bRes = false ;

        try {
            // Find String TLV
            if((EnvRespHdlr.findTLV((byte)0x02, (byte)1)==TLV_FOUND_CR_SET)&&
                (EnvRespHdlr.getValueByte((short)0)==(byte)0x81))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Successful call
        testCaseNb = (byte) 5 ;
        bRes = false ;

        try {

            //preparation of the append TLV
            for(short i=0;i<0x00FF;i++)
                data[i]=(byte)i;

            //built the third TLV
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0x7E);

            // get the value byte
            if((EnvRespHdlr.findTLV((byte)0x0D, (byte)1)==TLV_FOUND_CR_NOT_SET)&&
                (EnvRespHdlr.getValueByte((short)0x7D)==(byte)0x7D))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 6 : Successful call
        testCaseNb = (byte) 6 ;
        bRes = false ;

        try {
            //built the first TLV
            EnvRespHdlr.clear();
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0x80);

            // get the value byte
            if((EnvRespHdlr.findTLV((byte)0x0D, (byte)1)==TLV_FOUND_CR_NOT_SET)&&
                (EnvRespHdlr.getValueByte((short)0x7E)==(byte)0X7E))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 7 : Successful call
        testCaseNb = (byte) 7 ;
        bRes = false ;

        try {
            // get the value byte
            if((EnvRespHdlr.findTLV((byte)0x0D, (byte)1)==TLV_FOUND_CR_NOT_SET)&&
                (EnvRespHdlr.getValueByte((short)0x7F)==(byte)0x7F))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 8 : Successful call
        testCaseNb = (byte) 8 ;
        bRes = false ;

        try {
            //preparation of the append TLV
            //built the first TLV
            EnvRespHdlr.clear();
            EnvRespHdlr.appendTLV((byte)0x0D,data,(short) 0x00, (short) 0xF1);

            // get the value byte
            if((EnvRespHdlr.findTLV((byte)0x0D, (byte)1)==TLV_FOUND_CR_NOT_SET)&&
                (EnvRespHdlr.getValueByte((short)0xF0)==(byte)0xF0))
                    bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 9 : Successful call
        testCaseNb = (byte) 9 ;
        bRes = false ;

        try {
            //preparation of the append TLV
            //built the first TLV
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            try {
                EnvRespHdlr.getValueByte((short)0);
                bRes= false;
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
