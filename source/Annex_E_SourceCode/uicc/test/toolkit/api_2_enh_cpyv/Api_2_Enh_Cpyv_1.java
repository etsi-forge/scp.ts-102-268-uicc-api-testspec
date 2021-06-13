//-----------------------------------------------------------------------------
//Api_2_Enh_Cpyv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_cpyv;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;
/**
 * Test Area : uicc.test.toolkit.Api_2_enh_Cpyv
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Cpyv_1 extends TestToolkitApplet
{
        private final byte[] compBuffer = {(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06};
        private final byte[] compBuffer2= {(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x55,
                                           (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,
                                           (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,
                                           (byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55};
        
        private byte[] dstBuffer;

    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
        /**
     * Constructor of the applet
     */
    public Api_2_Enh_Cpyv_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Cpyv_1  thisApplet = new Api_2_Enh_Cpyv_1 ();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init(); 

        // Register on UNRECOGNIZED ENVELOPE
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
                                // Test Case 1 : try to use the copyValue method with an destination Buffer null
                                testCaseNb = (byte) 1 ;

                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.copyValue((short)0,null,(short)0,(short)1)==0))
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
                                // Test Case 2 : use the copyValue method with an offset > buffer length
                                testCaseNb = (byte) 2 ;

                                bRes = false ;
                                dstBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)5,(short)1)==0))
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
                                // Test Case 3 : use the copyValue method with an offset = -1
                                testCaseNb = (byte) 3 ;

                                bRes = false ;
                                dstBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)-1,(short)1)==0))
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
                                // Test Case 4 : use the copyValue method with an length > buffer length
                                testCaseNb = (byte) 4 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)0,(short)6)==6))
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
                                // Test Case 5 : use the copyValue method with an Offset + length > buffer length
                                testCaseNb = (byte) 5 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)3,(short)3)==6))
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
                                // Test Case 6 : use the copyValue method with an dstLength < 0
                                testCaseNb = (byte) 6 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)0,(short)-1)==0))
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

                        case (byte) 6 : 
                                // --------------------------------------------
                                // Test Case 7 : use the copyValue method with ValueOffset  > TLV length
                                testCaseNb = (byte) 7 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)6,dstBuffer,(short)0,(short)1)==0))
                                                        bRes = false;
                                }
                                catch (ToolkitException e)    {    
                                        if(e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                bRes = true ;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;
                        
                        case (byte) 7 : 
                                // --------------------------------------------
                                // Test Case 8 : use the copyValue method with ValueOffset  < 0
                                testCaseNb = (byte) 8 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)-1,dstBuffer,(short)0,(short)1)==0))
                                                        bRes = false;
                                }
                                catch (ToolkitException e)    {    
                                        if(e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                bRes = true ;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;
                        
                        case (byte) 8 : 
                                // --------------------------------------------
                                // Test Case 9 : use the copyValue method with dstlength  > TLV length
                                testCaseNb = (byte) 9 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)0,(short)7)==0))
                                                        bRes = false;
                                }
                                catch (ToolkitException e)    {    
                                        if(e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                bRes = true ;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 9 : 
                                // --------------------------------------------
                                // Test Case 10 : use the copyValue method with ValueOffset+dstlength  > TLV length
                                testCaseNb = (byte) 10 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)2,dstBuffer,(short)0,(short)5)==0))
                                                        bRes = false;
                                }
                                catch (ToolkitException e)    {    
                                        if(e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                bRes = true ;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 10 : 
                                // --------------------------------------------
                                // Test Case 11 : use the copyValue method without current TVL
                                testCaseNb = (byte) 11 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x01,(byte)1)==TLV_NOT_FOUND)
                                                &&(envHdlr.copyValue((short)2,dstBuffer,(short)0,(short)5)==0))
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

                        case (byte) 11 : 
                                // --------------------------------------------
                                // Test Case 12 : use successfully the copyValue method 
                                testCaseNb = (byte) 12 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[6];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)0,(short)6)==6))
                                                        bRes = true;
                                }
                                catch (ToolkitException e)    {    
                                        bRes = false;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 12 : 
                                // --------------------------------------------
                                // Test Case 13 : use successfully the copyValue method and compare the received data
                                testCaseNb = (byte) 13 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[6];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)0,(short)6)==6)
                                                &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer,(short)0, (short) compBuffer.length)==0)) 
                                                bRes = true;
                                }
                                catch (ToolkitException e)    {    
                                        bRes = false;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 13 : 
                                // --------------------------------------------
                                // Test Case 14 : use successfully the copyValue
                                testCaseNb = (byte) 14 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[20];
                                
                                // initialize the buffer
                                for(byte i=0;i<20;i++)
                                        dstBuffer[i] =(byte)0x55;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)1,dstBuffer,(short)3,(short)4)==7))
                                                bRes = true;
                                }
                                catch (ToolkitException e)    {    
                                        bRes = false;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 14 : 
                                // --------------------------------------------
                                // Test Case 15 : use successfully the copyValue method and compare the received data
                                testCaseNb = (byte) 15 ;
                                
                                bRes = false ;
                                dstBuffer =new byte[32];
                                
                                // initialize the buffer
                                for(byte i=0;i<32;i++)
                                        dstBuffer[i] =(byte)0x55;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)1,dstBuffer,(short)3,(short)4)==7)
                                                &&(Util.arrayCompare(dstBuffer,(short)0,compBuffer2,(short)0, (short) compBuffer2.length)==0)) 
                                                bRes = true;
                                }
                                catch (ToolkitException e)    {    
                                        bRes = false;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 15 : 
                                // --------------------------------------------
                                // Test Case 16 : use successfully the copyValue method with the length of the copy =0
                                testCaseNb = (byte) 16 ;
                                
                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.copyValue((short)0,dstBuffer,(short)dstBuffer.length,(short)0)==dstBuffer.length))
                                                        bRes = true;
                                }
                                catch (ToolkitException e)    {    
                                        bRes = false;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;
                                                
                }
        }
}
