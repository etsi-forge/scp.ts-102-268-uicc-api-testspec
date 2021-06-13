//-----------------------------------------------------------------------------
//Api_2_Erh_Cprv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_cprv;

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_cprv
 *
 * @version 0.0.1 - 6 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Cprv_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    byte[] ref_data = {(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                       (byte)0x11,(byte)0x12,(byte)0x13,(byte)0x14,(byte)0x15,(byte)0x16,(byte)0x17,(byte)0x18,(byte)0x19,(byte)0x1A,(byte)0x1B,(byte)0x1C,(byte)0x1D,(byte)0x1E,(byte)0x1F,(byte)0x20,
                       (byte)0x21,(byte)0x22,(byte)0x23,(byte)0x24,(byte)0x25,(byte)0x26,(byte)0x27,(byte)0x28,(byte)0x29,(byte)0x2A,(byte)0x2B,(byte)0x2C,(byte)0x2D,(byte)0x2E,(byte)0x2F,(byte)0x30,
                       (byte)0x31,(byte)0x32,(byte)0x33,(byte)0x34,(byte)0x35,(byte)0x36,(byte)0x37,(byte)0x38,(byte)0x39,(byte)0x3A,(byte)0x3B,(byte)0x3C,(byte)0x3D,(byte)0x3E,(byte)0x3F,(byte)0x40,
                       (byte)0x41,(byte)0x42,(byte)0x43,(byte)0x44,(byte)0x45,(byte)0x46,(byte)0x47,(byte)0x48,(byte)0x49,(byte)0x4A,(byte)0x4B,(byte)0x4C,(byte)0x4D,(byte)0x4E,(byte)0x4F,(byte)0x50,
                       (byte)0x51,(byte)0x52,(byte)0x53,(byte)0x54,(byte)0x55,(byte)0x56,(byte)0x57,(byte)0x58,(byte)0x59,(byte)0x5A,(byte)0x5B,(byte)0x5C,(byte)0x5D,(byte)0x5E,(byte)0x5F,(byte)0x60,
                       (byte)0x61,(byte)0x62,(byte)0x63,(byte)0x64,(byte)0x65,(byte)0x66,(byte)0x67,(byte)0x68,(byte)0x69,(byte)0x6A,(byte)0x6B,(byte)0x6C,(byte)0x6D,(byte)0x6E,(byte)0x6F,(byte)0x70,
                       (byte)0x71,(byte)0x72,(byte)0x73,(byte)0x74,(byte)0x75,(byte)0x76,(byte)0x77,(byte)0x78,(byte)0x79,(byte)0x7A,(byte)0x7B,(byte)0x7C,(byte)0x7D,(byte)0x7E,(byte)0x7F,(byte)0x80,
                       (byte)0x81,(byte)0x82,(byte)0x83,(byte)0x84,(byte)0x85,(byte)0x86,(byte)0x87,(byte)0x88,(byte)0x89,(byte)0x8A,(byte)0x8B,(byte)0x8C,(byte)0x8D,(byte)0x8E,(byte)0x8F,(byte)0x90,
                       (byte)0x91,(byte)0x92,(byte)0x93,(byte)0x94,(byte)0x95,(byte)0x96,(byte)0x97,(byte)0x98,(byte)0x99,(byte)0x9A,(byte)0x9B,(byte)0x9C,(byte)0x9D,(byte)0x9E,(byte)0x9F,(byte)0xA0,
                       (byte)0xA1,(byte)0xA2,(byte)0xA3,(byte)0xA4,(byte)0xA5,(byte)0xA6,(byte)0xA7,(byte)0xA8,(byte)0xA9,(byte)0xAA,(byte)0xAB,(byte)0xAC,(byte)0xAD,(byte)0xAE,(byte)0xAF,(byte)0xB0,
                       (byte)0xB1,(byte)0xB2,(byte)0xB3,(byte)0xB4,(byte)0xB5,(byte)0xB6,(byte)0xB7,(byte)0xB8,(byte)0xB9,(byte)0xBA,(byte)0xBB,(byte)0xBC,(byte)0xBD,(byte)0xBE,(byte)0xBF,(byte)0xC0,
                       (byte)0xC1,(byte)0xC2,(byte)0xC3,(byte)0xC4,(byte)0xC5,(byte)0xC6,(byte)0xC7,(byte)0xC8,(byte)0xC9,(byte)0xCA,(byte)0xCB,(byte)0xCC,(byte)0xCD,(byte)0xCE,(byte)0xCF,(byte)0xD0,
                       (byte)0xD1,(byte)0xD2,(byte)0xD3,(byte)0xD4,(byte)0xD5,(byte)0xD6,(byte)0xD7,(byte)0xD8,(byte)0xD9,(byte)0xDA,(byte)0xDB,(byte)0xDC,(byte)0xDD,(byte)0xDE,(byte)0xDF,(byte)0xE0,
                       (byte)0xE1,(byte)0xE2,(byte)0xE3,(byte)0xE4,(byte)0xE5,(byte)0xE6,(byte)0xE7,(byte)0xE8,(byte)0xE9,(byte)0xEA,(byte)0xEB,(byte)0xEC,(byte)0xED,(byte)0xEE,(byte)0xEF,(byte)0xF0,
                       (byte)0xF1,(byte)0xF2,(byte)0xF3,(byte)0xF4,(byte)0xF5,(byte)0xF6,(byte)0xF7,(byte)0xF8,(byte)0xF9,(byte)0xFA,(byte)0xFB,(byte)0xFC,(byte)0xFD,(byte)0xFE,(byte)0xFF,(byte)0x00
                      };

    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Cprv_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Cprv_1  thisApplet = new Api_2_Erh_Cprv_1();

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
        // Test Case 1 : Null as compareBuffer
        testCaseNb = (byte) 1 ;
        bRes = false ;

        try {
            byte[] TLVBuffer = new byte[16];

            //initialize the buffer
            for(short i=0;i<16;i++)
                TLVBuffer[i]=(byte)i;

            //built the first TLV
            EnvRespHdlr.appendTLV((byte)0x0D,TLVBuffer,(short) 0x00, (short) 16);

            // Select the TLV
            EnvRespHdlr.findTLV((byte)0x0D, (byte)1) ;

            try {
                EnvRespHdlr.compareValue((short)0, null, (short)0, (short)1) ;
            }
            catch (NullPointerException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 2 : compareOffset >= compareBuffer.length
        testCaseNb = (byte) 2 ;
        bRes = false ;
        byte[] compareBuffer = new byte[5];

        try {
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)5, (short)1) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 3 : compareOffset < 0
        testCaseNb = (byte) 3 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)-1, (short)1) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 4 : compareLength > compareBuffer.length
        testCaseNb = (byte) 4 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)6) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 5 : compareOffset + compareLength > compareBuffer.length
        testCaseNb = (byte) 5 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)3, (short)3) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 6 : compareLength < 0

        testCaseNb = (byte) 6 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)-1) ;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 7 : valueOffset >= TLV Length
        testCaseNb = (byte) 7 ;
        bRes = false ;

        compareBuffer=new byte[15];

        try {
            //initialize the buffer
            for(short i=0;i<15;i++)
                compareBuffer[i]=(byte)i;

            EnvRespHdlr.clear();
            //built the first tlv
            EnvRespHdlr.appendTLV((byte)0x0D,compareBuffer,(short) 0x00, (short) 6);
            
            // Select Text String TLV
            EnvRespHdlr.findTLV((byte)0x0D, (byte)1) ;

            try {
                EnvRespHdlr.compareValue((short)6, compareBuffer, (short)0, (short)1) ;

            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 8 : valueOffset < 0
        testCaseNb = (byte) 8 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)-1, compareBuffer, (short)0, (short)1) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 9 : compareLength > TLV length
        testCaseNb = (byte) 9 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)7) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 10 : valueOffset + compareLength > Text String length
        testCaseNb = (byte) 10 ;
        bRes = false ;

        try {
            try {
                EnvRespHdlr.compareValue((short)2, compareBuffer, (short)0, (short)5) ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES) ;
            }
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 11 : Unavailable element
        testCaseNb = (byte) 11 ;
        bRes = false ;

        try {
            //clear the handler
            EnvRespHdlr.clear();
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)1) ;
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
        // Test Case 12 : Successfull call
        testCaseNb = (byte) 12 ;
        bRes = false ;

        compareBuffer = new byte[17] ;

        // Initialise compare buffer
        compareBuffer[0] = 4;
        for (short i=1; i<17; i++)
            compareBuffer[i] = (byte)(i-1) ;

        EnvRespHdlr.clear();

        //built the first tlv
        EnvRespHdlr.appendTLV((byte)0x0D,compareBuffer,(short) 0x00, (short) 17);

        // Search Text String TLV
        EnvRespHdlr.findTLV((byte)0x0D, (byte)0x01) ;

        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)17)==0)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 13 : Successfull call
        testCaseNb = (byte) 13 ;
        bRes = false ;

        compareBuffer[16] = (byte)0x10 ;

        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)17)==-1)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 14 : Successfull call
        testCaseNb = (byte) 14 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer[0] = (byte)0x03;
        compareBuffer[16] = (byte)0x0F;

        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)0, compareBuffer, (short)0, (short)17)==+1)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 15 : Successfull call
        testCaseNb = (byte) 15 ;
        bRes = false ;

        compareBuffer = new byte[134];
        // Initialise compareBuffer
        Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0x55);
        Util.arrayCopyNonAtomic(ref_data, (short)2, compareBuffer, (short)3, (short)0x81);
        
        //Initialise the TLV
        EnvRespHdlr.clear();
        EnvRespHdlr.appendTLV((byte)0x0D,ref_data,(short) 0x00, (short)0x83);

        // Search Text String TLV
        EnvRespHdlr.findTLV((byte)0x0D, (byte)0x01) ;

        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)2, compareBuffer, (short)3, (short)0x81)==0)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 16 : Successfull call
        testCaseNb = (byte) 16 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer[131] = (byte) 0x84 ;

        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)2, compareBuffer, (short)3, (short)0x81)==-1)
                bRes = true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;


        // --------------------------------------------
        // Test Case 17 : Successfull call
        testCaseNb = (byte) 17 ;
        bRes = false ;

        // Initialise compareBuffer
        compareBuffer[131] = (byte) 0x82 ;
        
        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)2, compareBuffer, (short)3, (short)0x81)==+1)
                bRes = true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 18 : Successfull call (with the length = 0 and the offset = length of the comparaison buffer)
        testCaseNb = (byte) 18 ;
        bRes = false ;

        try {
            // Compare TLV
            if(EnvRespHdlr.compareValue((short)2, compareBuffer, (short)compareBuffer.length, (short)0)==0)
                bRes = true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 19 : ToolkitException HANDLER_NOT_AVAILABLE
        testCaseNb = (byte) 19 ;
        bRes = false ;

        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            try {
                EnvRespHdlr.compareValue((short)0, compareBuffer, (short)compareBuffer.length, (short)0);
            }
            catch (ToolkitException e) {
                if (e.getReason() == ToolkitException.HANDLER_NOT_AVAILABLE) {
                    bRes = true;
                } else {
                    bRes = false;
                }
            }
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes) ;

    }
}
