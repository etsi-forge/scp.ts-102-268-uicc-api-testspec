//-----------------------------------------------------------------------------
//Api_2_Enh_Facyb_Bs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_facyb_bs;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;


/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Facyb_Bs
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Facyb_Bs_1 extends TestToolkitApplet
{

    /** Registry entry concerning applet instance */
    //private ToolkitRegistry obReg;
        
        private final byte[] compBuffer = {(byte)0x81,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0xF5};
        
        private final byte[] compBuffer2= {(byte)0x55,(byte)0x55,(byte)0x81,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0xF5,(byte)0x55,(byte)0x55,
                                                                           (byte)0x55,(byte)0x55};
        
        private final byte[] compBuffer3= {(byte)0x83,(byte)0x81};
        
        private final byte[] compBuffer4= {(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
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
                                           (byte)0xC1,(byte)0xC2,(byte)0xC3,(byte)0xC4};
                                             
        private byte[] compBuffer5;                 
                                             
        private byte[] dstBuffer;            
                                             
    // Number of tests                   
    byte testCaseNb = (byte) 0x00;       
                                         
        /**                                  
     * Constructor of the applet         
     */                                  
    public Api_2_Enh_Facyb_Bs_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Facyb_Bs_1  thisApplet = new Api_2_Enh_Facyb_Bs_1 ();

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
        boolean bRes ;
        
        // Get the system instance of handlers
        EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
        
        switch ( testCaseNb ) 
        {
                    case (byte) 0 : 
                            // --------------------------------------------
                            // Test Case 1 : try to use the findAndCopyValue method with an destination Buffer null
                            testCaseNb = (byte) 1 ;

                            bRes = false ;
                            try {
                                    if(envHdlr.findAndCopyValue((byte)2,null,(short)0)==0)
                                                    bRes = false;
                            }
                            catch (ToolkitException e)    {
                                    bRes = false;
                            }
                            catch (NullPointerException e)    {    
                                    bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 1 : 
                            // --------------------------------------------
                            // Test Case 2 : use the findAndCopyValue method with an offset > buffer length
                            testCaseNb = (byte) 2 ;

                            bRes = false ;
                            dstBuffer =new byte[6];
                            try {
                                    if(envHdlr.findAndCopyValue((byte)6,dstBuffer,(short)6)==0)
                                            bRes = false;
                            }
                            catch (ArrayIndexOutOfBoundsException e)    {    
                                    bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 2 : 
                            // --------------------------------------------
                            // Test Case 3 : use the findAndCopyValue method with an offset <0
                            testCaseNb = (byte) 3 ;

                            bRes = false ;
                            dstBuffer =new byte[6];
                            try {
                                    if(envHdlr.findAndCopyValue((byte)6,dstBuffer,(short)-1)==0)
                                                    bRes = false;
                            }
                            catch (ArrayIndexOutOfBoundsException e)    {    
                                    bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
            
                    case (byte) 3 : 
                            // --------------------------------------------
                            // Test Case 4 : use the findAndCopyValue method with an length > buffer length
                            testCaseNb = (byte) 4 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[5];
                            try {
                                    if(envHdlr.findAndCopyValue((byte)6,dstBuffer,(short)0)==0)
                                                    bRes = false;
                            }
                            catch (ArrayIndexOutOfBoundsException e)    {    
                                    bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 4 : 
                            // --------------------------------------------
                            // Test Case 5 : use the findAndCopyValue method with an Offset + length > buffer length
                            testCaseNb = (byte) 5 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[6];
                            try {
                                    if(envHdlr.findAndCopyValue((byte)6,dstBuffer,(short)1)==0)
                                                    bRes = false;
                            }
                            catch (ArrayIndexOutOfBoundsException e)    {    
                                    bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    
                    case (byte) 5 : 
                            // --------------------------------------------
                            // Test Case 6 : use the findAndCopyValue method without current tlv 
                            testCaseNb = (byte) 6 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[15];
                            try {
                                    if((envHdlr.findTLV((byte)0x02,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                            &&(envHdlr.findAndCopyValue((byte)3,dstBuffer,(short)0)==0))
                                                    bRes = false;
                            }
                            catch (ToolkitException e)    {    
                                    if(e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT)
                                            bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            
                            // use the method getValueLength() after the unsuccessful of the method findAndCopyValue 
                            testCaseNb = (byte) 7 ;
                            bRes = false;
                            try {
                                    if(envHdlr.getValueLength()==0)
                                                    bRes = false;
                            }
                            catch (ToolkitException e)    {    
                                    if(e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT)
                                            bRes = true ;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
                    

                    case (byte) 7 : 
                            // --------------------------------------------
                            // Test Case 7 : normal execution of the findAndCopyValue() method with the TAG 06
                            testCaseNb = (byte) 8 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[6];
                            try {
                                    if((envHdlr.findAndCopyValue((byte)0x06,dstBuffer,(byte)0)==6))
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
                    
                    case (byte) 8 : 
                            // --------------------------------------------
                            // Test Case 8 : normal execution of the findAndCopyValue() method and compare the buffer value with the TAG 06
                            testCaseNb = (byte) 9 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[6];
                            try {
                                    if((envHdlr.findAndCopyValue((byte)0x06,dstBuffer,(byte)0)==6)
                                            &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer,(short)0, (short) compBuffer.length)==0))
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 9 : 
                            // --------------------------------------------
                            // Test Case 9 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) with the TAG 06
                            testCaseNb = (byte) 10 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[12];
                            
                            for( byte i=0;i<12;i++)
                                    dstBuffer[i]=0x55;

                            try {
                                    if(envHdlr.findAndCopyValue((byte)0x06,dstBuffer,(byte)2)==8)
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 10 : 
                            // --------------------------------------------
                            // Test Case 10 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) and compare the stored values
                            testCaseNb = (byte) 11 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[12];
                            
                            for( byte i=0;i<12;i++)
                                    dstBuffer[i]=0x55;

                            try {
                                    if((envHdlr.findAndCopyValue((byte)0x06,dstBuffer,(byte)2)==8)
                                            &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer2,(short)0, (short) compBuffer2.length)==0))
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 11 : 
                            // --------------------------------------------
                            // Test Case 11 : normal execution of the findAndCopyValue method with the TAG 02
                            testCaseNb = (byte) 12 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[2];
                            
                            try {
                                    if(envHdlr.findAndCopyValue((byte)0x02,dstBuffer,(byte)0)==2)
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
                    
                    case (byte) 12 : 
                            // --------------------------------------------
                            // Test Case 12 : normal execution of the findAndCopyValue method with the TAG 02 and compare the stored Values
                            testCaseNb = (byte) 13 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[2];
                            
                            try {
                                    if((envHdlr.findAndCopyValue((byte)0x02,dstBuffer,(byte)0)==2)
                                            &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer3,(short)0, (short) compBuffer3.length)==0))
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
                    
                    case (byte) 13 : 
                            // --------------------------------------------
                            // Test Case 13 : normal execution of the findAndCopyValue method with the TAG 82
                            testCaseNb = (byte) 14 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[2];
                            
                            try {
                                    if(envHdlr.findAndCopyValue((byte)0x82,dstBuffer,(byte)0)==2)
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
                    
                    case (byte) 14 : 
                            // --------------------------------------------
                            // Test Case 14 : normal execution of the findAndCopyValue method with the TAG 82 and compare the stored Values
                            testCaseNb = (byte) 15 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[2];
                            
                            try {
                                    if((envHdlr.findAndCopyValue((byte)0x82,dstBuffer,(byte)0)==2)
                                            &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer3,(short)0, (short) compBuffer3.length)==0))
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;

                    case (byte) 15 : 
                            // --------------------------------------------
                            // Test Case 16 : normal execution of the findAndCopyValue method with the TAG B3
                            testCaseNb = (byte) 16 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[0xC4];
                            
                            try {
                                    if(envHdlr.findAndCopyValue((byte)0xB3,dstBuffer,(byte)0)==0xC4)
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
                    
                    case (byte) 16 : 
                            // --------------------------------------------
                            // Test Case 17 : normal execution of the findAndCopyValue method with the TAG B3 and compare the stored Values
                            testCaseNb = (byte) 17 ;
                            
                            bRes = false ;
                            dstBuffer =new byte[0xC4];
                            
                            try {
                                    if((envHdlr.findAndCopyValue((byte)0xB3,dstBuffer,(byte)0)==0xC4)
                                            &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer4,(short)0, (short) compBuffer4.length)==0))
                                                    bRes = true;
                            }
                            catch (Exception e)    {    
                                    bRes = false ;
                            }
                            reportTestOutcome(testCaseNb, bRes) ;
                            break;
            }
        }
}

