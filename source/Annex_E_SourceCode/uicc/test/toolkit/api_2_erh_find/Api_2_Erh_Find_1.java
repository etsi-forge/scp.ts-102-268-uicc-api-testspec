//-----------------------------------------------------------------------------
//Api_2_Erh_Find_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_find;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_find
 *
 * @version 0.0.1 - 6 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Find_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Find_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Find_1  thisApplet = new Api_2_Erh_Find_1();

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
        // Test Case 1 : invalid input parameter (occurrence = 0)
        testCaseNb = (byte) 1 ;
        bRes = false ;

        byte[] data = new byte[0x03];

        //initialize the buffer
        data[0]=(byte)11;
        data[1]=(byte)22;
        data[2]=(byte)33;

        //built the first tlv
        EnvRespHdlr.appendTLV((byte)0x81,data,(short) 0x00, (short) 0x03);


        //initialize the buffer
        data[0]=(byte)99;
        data[1]=(byte)77;
        
        //built the second tlv
        EnvRespHdlr.appendTLV((byte)0x82,data,(short) 0x00, (short) 0x02);

        try {
            EnvRespHdlr.findTLV((byte)0x01, (byte) 0x00) ;
        }
        catch (ToolkitException e) {
            bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 2 : Search 1st TLV
        testCaseNb = (byte) 2 ;
        bRes = false ;

        try {
            if(EnvRespHdlr.findTLV((byte)0x01, (byte) 0x01)==TLV_FOUND_CR_SET)
                bRes =true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : Verify selected TLV
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            if((short)0x03==EnvRespHdlr.getValueLength())
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 4 : Search 2nd TLV
        testCaseNb = (byte) 4 ;
        bRes = false ;

        try {
            if(EnvRespHdlr.findTLV((byte)0x02, (byte) 0x01)==TLV_FOUND_CR_SET)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : Verify selected TLV
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            if((short)0x02==EnvRespHdlr.getValueLength())
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 6 : Search a wrong tag
        testCaseNb = (byte) 6 ;
        bRes = false ;

        try {
            // Select a correct TLV
            EnvRespHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)0x02) ;
            if(EnvRespHdlr.findTLV((byte)0x03, (byte) 0x01)==TLV_NOT_FOUND)
                bRes = true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 7 : Verify the current TLV is no longer defined
        testCaseNb = (byte) 7 ;
        bRes = false ;

        try {
            if(EnvRespHdlr.getValueLength()==0)
                bRes=false;
        }
        catch (ToolkitException e) {
            bRes = (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        
        // --------------------------------------------
        // Test Case 8 : Search a tag with a wrong occurence
        testCaseNb = (byte) 8 ;
        bRes = false ;
        
        try {
            
            if(EnvRespHdlr.findTLV((byte)0x01, (byte) 0x02)==TLV_NOT_FOUND)
            bRes = true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 9 : Verify the current TLV is no longer defined
        testCaseNb = (byte) 9 ;
        bRes = false ;

        try {
            if(EnvRespHdlr.getValueLength()==0)
                bRes=false;
        }
        catch (ToolkitException e) {
            bRes = (e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 10 : Search a TLV with occurence=2
        testCaseNb = (byte) 10 ;
        bRes = false ;

        try {
            // Append Tag 0x02
            EnvRespHdlr.appendTLV((byte)0x02, (byte)0x00) ;
            // Search the tag
            if(EnvRespHdlr.findTLV((byte)0x02, (byte) 0x02)==TLV_FOUND_CR_NOT_SET)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 11 : Search a TLV
        testCaseNb = (byte) 11 ;
        bRes = false ;
        try {
            // Append Tag 0x04
            EnvRespHdlr.appendTLV((byte)0x04, (byte)0x00) ;

            // Search the tag
            if(EnvRespHdlr.findTLV((byte)0x04, (byte) 0x01)==TLV_FOUND_CR_NOT_SET)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 12 : Search tag 81h
        testCaseNb = (byte) 12 ;
        bRes = false ;

        try {
            // Search the tag
            if(EnvRespHdlr.findTLV((byte)0x81, (byte) 0x01)==TLV_FOUND_CR_SET)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 13 : Search tag 84h

        testCaseNb = (byte) 13 ;
        bRes = false ;

        try {
            // Search the tag
            if(EnvRespHdlr.findTLV((byte)0x84, (byte) 0x01)==TLV_FOUND_CR_NOT_SET)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 14 : ToolkitException HANDLER_NOT_AVAILABLE

        testCaseNb = (byte) 14 ;
        bRes = false ;

        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            try {
                EnvRespHdlr.findTLV((byte)0x81, (byte) 0x01);
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
