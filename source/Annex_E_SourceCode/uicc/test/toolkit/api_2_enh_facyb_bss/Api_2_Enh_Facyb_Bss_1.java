//-----------------------------------------------------------------------------
//Api_2_Enh_Facyb_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_facyb_bss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;


/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Facyb_Bss
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Facyb_Bss_1  extends TestToolkitApplet
{

    /** Registry entry concerning applet instance */
    private final byte[] compBuffer = {(byte)0x81,(byte)0x11,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0xF5};
	
	private final byte[] compBuffer2= {(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x22,(byte)0x33,(byte)0x44,(byte)0xF5,(byte)0x55,(byte)0x55,(byte)0x55,
									   (byte)0x55,(byte)0x55};
	
	private final byte[] compBuffer3= {(byte)0x83,(byte)0x81,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,
									   (byte)0x55,(byte)0x55};
	
	private final byte[] compBuffer4= {(byte)0x22,(byte)0x44,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,(byte)0x55,
									   (byte)0x55,(byte)0x55};
	
    private byte[] compBuffer5;
	private byte[] dstBuffer;
	
    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
	/**
     * Constructor of the applet
     */
    public Api_2_Enh_Facyb_Bss_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
    	Api_2_Enh_Facyb_Bss_1  thisApplet = new Api_2_Enh_Facyb_Bss_1 ();

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
					if(envHdlr.findAndCopyValue((byte)2,(byte)1,(short)0,null,(short)0,(short)1)==0)
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
				dstBuffer =new byte[5];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)5,(short)1)==0)
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
				dstBuffer =new byte[5];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)-1,(short)1)==0)
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
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)0,(short)6)==0)
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
				dstBuffer =new byte[5];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)3,(short)3)==0)
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
				// Test Case 6 : use the findAndCopyValue method with an dstlength < 0
				testCaseNb = (byte) 6 ;
				
				bRes = false ;
				dstBuffer =new byte[5];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)0,(short)-1)==0)
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
				// Test Case 7 : use the findAndCopyValue method with an ValueOffset > buffer length
				testCaseNb = (byte) 7 ;
				
				bRes = false ;
				dstBuffer =new byte[15];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)6,dstBuffer,(short)0,(short)1)==0)
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
				// Test Case 8 : use the findAndCopyValue method with an ValueOffset <0
				testCaseNb = (byte) 8 ;
				
				bRes = false ;
				dstBuffer =new byte[15];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)-1,dstBuffer,(short)0,(short)1)==0)
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
				// Test Case 9 : use the findAndCopyValue method with an dstLength > Value length
				testCaseNb = (byte) 9 ;
				
				bRes = false ;
				dstBuffer =new byte[15];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)0,(short)7)==0)
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
				// Test Case 10 : use the findAndCopyValue method with an ValueOffset + dstlength > ValueLength
				testCaseNb = (byte) 10 ;
				
				bRes = false ;
				dstBuffer =new byte[15];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)2,dstBuffer,(short)0,(short)5)==0)
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
				// Test Case 11 : use the findAndCopyValue method without the researched TAG
				testCaseNb = (byte) 11 ;
				
				bRes = false ;
				dstBuffer =new byte[6];
				try {
					if((envHdlr.findTLV((byte)0x02,(byte)1)==TLV_FOUND_CR_NOT_SET)
					   &&(envHdlr.findAndCopyValue((byte)6,(byte)2,(short)0,dstBuffer,(short)0,(short)1)==0))
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
				testCaseNb = (byte) 12 ;
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
			
				
			case (byte) 12 : 
				// --------------------------------------------
				// Test Case 12 : normal execution of the findAndCopyValue method with the TAG 06
				testCaseNb = (byte) 13 ;
				
				bRes = false ;
				dstBuffer =new byte[6];
				try {
					if(envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)0,(short)6)==6)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;


			case (byte) 13 : 
				// --------------------------------------------
				// Test Case 13 : normal execution of the findAndCopyValue method with the TAG 06 and compare the stored value
				testCaseNb = (byte) 14 ;
				
				bRes = false ;
				dstBuffer =new byte[6];
				try {
					if((envHdlr.findAndCopyValue((byte)6,(byte)1,(short)0,dstBuffer,(short)0,(short)6)==6)
						&&(Util.arrayCompare(dstBuffer,(short)0,compBuffer,(short)0, (short) compBuffer.length)==0))
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 14 : 
				// --------------------------------------------
				// Test Case 14 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) with the TAG 06
				testCaseNb = (byte) 15 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if(envHdlr.findAndCopyValue((byte)0x06,(byte)1,(short)2,dstBuffer,(short)3,(short)4)==7)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 15 : 
				// --------------------------------------------
				// Test Case 15 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) and compare the stored values
				testCaseNb = (byte) 16 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if((envHdlr.findAndCopyValue((byte)0x06,(byte)1,(short)2,dstBuffer,(short)3,(short)4)==7)
						&&(Util.arrayCompare(dstBuffer,(short)0,compBuffer2,(short)0, (short) compBuffer2.length)==0))
							bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;


			case (byte) 16 : 
				// --------------------------------------------
				// Test Case 16 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) with the TAG 02
				testCaseNb = (byte) 17 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if(envHdlr.findAndCopyValue((byte)0x02,(byte)1,(short)0,dstBuffer,(short)0,(short)2)==2)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 17 : 
				// --------------------------------------------
				// Test Case 17 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) and compare the stored values
				testCaseNb = (byte) 18 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if((envHdlr.findAndCopyValue((byte)0x02,(byte)1,(short)0,dstBuffer,(short)0,(short)2)==2)
						&&(Util.arrayCompare(dstBuffer,(short)0,compBuffer3,(short)0, (short) compBuffer3.length)==0))
							bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;


			case (byte) 18 : 
				// --------------------------------------------
				// Test Case 18 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) with the TAG 02 and the occurence 2
				testCaseNb = (byte) 19 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if(envHdlr.findAndCopyValue((byte)0x02,(byte)2,(short)0,dstBuffer,(short)0,(short)2)==2)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 19 : 
				// --------------------------------------------
				// Test Case 19 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) and compare the stored values
				testCaseNb = (byte) 20 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if((envHdlr.findAndCopyValue((byte)0x02,(byte)2,(short)0,dstBuffer,(short)0,(short)2)==2)
						&&(Util.arrayCompare(dstBuffer,(short)0,compBuffer4,(short)0, (short) compBuffer4.length)==0))
							bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 20 : 
				// --------------------------------------------
				// Test Case 20 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) with the TAG 82 and the occurence 1
				testCaseNb = (byte) 21 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if(envHdlr.findAndCopyValue((byte)0x82,(byte)1,(short)0,dstBuffer,(short)0,(short)2)==2)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 21 : 
				// --------------------------------------------
				// Test Case 21 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) and compare the stored values
				testCaseNb = (byte) 22 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if((envHdlr.findAndCopyValue((byte)0x82,(byte)1,(short)0,dstBuffer,(short)0,(short)2)==2)
						&&(Util.arrayCompare(dstBuffer,(short)0,compBuffer3,(short)0, (short) compBuffer3.length)==0))
							bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 22 : 
				// --------------------------------------------
				// Test Case 22 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) with the TAG 82 and the occurence 2
				testCaseNb = (byte) 23 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if(envHdlr.findAndCopyValue((byte)0x82,(byte)2,(short)0,dstBuffer,(short)0,(short)2)==2)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 23 : 
				// --------------------------------------------
				// Test Case 23 : use the findAndCopyValue method (store the TLV value in a fulfilled buffer) and compare the stored values
				testCaseNb = (byte) 24 ;
				
				bRes = false ;
				dstBuffer =new byte[12];
				
				for( byte i=0;i<12;i++)
					dstBuffer[i]=0x55;

				try {
					if((envHdlr.findAndCopyValue((byte)0x82,(byte)2,(short)0,dstBuffer,(short)0,(short)2)==2)
					&&(Util.arrayCompare(dstBuffer,(short)0,compBuffer4,(short)0, (short) compBuffer4.length)==0))
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 24 : 
				// --------------------------------------------
				// Test Case 24 : use the findAndCopyValue method (with the length = 0 and the offset = length of the destination buffer)
				testCaseNb = (byte) 25 ;
				
				bRes = false ;
				try {
					if(envHdlr.findAndCopyValue((byte)0x82,(byte)2,(short)0,dstBuffer,(short)dstBuffer.length,(short)0)==dstBuffer.length)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 25 : 
				// --------------------------------------------
				// Test Case 26 : use the findAndCopyValue method with Invalid Parameter(occurrence = 0)
				testCaseNb = (byte) 26 ;
				
				bRes = false ;
				dstBuffer =new byte[0x10D];
				
				try {
					if(envHdlr.findAndCopyValue((byte)0x0B,(byte)0,(short)0x11,dstBuffer,(short)0,(short)0x10D)==0x10D)
						bRes = false;
				}
				catch (ToolkitException e)    {    
					if(e.getReason()==ToolkitException.BAD_INPUT_PARAMETER)
						bRes = true ;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;

		}
	}
}
