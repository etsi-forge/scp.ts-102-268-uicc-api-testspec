//-----------------------------------------------------------------------------
//Api_2_Enh_Find_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_find;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Find
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Find_1 extends TestToolkitApplet
{

	private byte[] dstBuffer;

	// Number of tests
	byte testCaseNb = (byte) 0x00;

	/**
     * Constructor of the applet
     */
    public Api_2_Enh_Find_1() 
    {
	}


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
    	Api_2_Enh_Find_1  thisApplet = new Api_2_Enh_Find_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

	public void processToolkit(short arg0) throws ToolkitException 
	{
        // Result of tests
        boolean bRes ;
        
        // Get the system instance of handlers
		EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
		
	    switch ( testCaseNb ) 
		{
			case (byte) 0 : 
				// --------------------------------------------
				// Test Case 1 : invalid input parametre: the occurence =0
				testCaseNb = (byte) 1 ;

				bRes = false ;
				try {
					envHdlr.findTLV((byte)2,(byte)0);
						bRes = false;
				}
				catch (ToolkitException e)    {
					if(e.getReason()==ToolkitException.BAD_INPUT_PARAMETER)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 1 : 
				// --------------------------------------------
				// Test Case 2 : search the first TVL with the TAG 02 (CR is Set)
				testCaseNb = (byte) 2 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_SET)
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

			case (byte) 2 : 
				// --------------------------------------------
				// Test Case 3 : verify that this TLV is the the current TLV (device identity)
				testCaseNb = (byte) 3 ;

				bRes = false ;
				try {
					if((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_SET)
						&&(envHdlr.getValueLength()==2))
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
		
			case (byte) 3 : 
				// --------------------------------------------
				// Test Case 4 : search the second TLV (06) (CR is set)
				testCaseNb = (byte) 4 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)6,(byte)1)==TLV_FOUND_CR_SET)
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

			case (byte) 4 : 
				// --------------------------------------------
				// Test Case 5 : verify that this TLV is the the current TLV 
				testCaseNb = (byte) 5 ;

				bRes = false ;
				try {
					if((envHdlr.findTLV((byte)6,(byte)1)==TLV_FOUND_CR_SET)
						&&(envHdlr.getValueLength()==0x0E))
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

			case (byte) 5 : 
				// --------------------------------------------
				// Test Case 6 : try to find a bad tag
				testCaseNb = (byte) 6 ;

				bRes = false ;
				try {
					if((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_SET)
						&&(envHdlr.findTLV((byte)3,(byte)1)==TLV_NOT_FOUND))
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

			case (byte) 6 : 
				// --------------------------------------------
				// Test Case 7 : search a bad tag (03) and try to get the length of the current TLV 
				testCaseNb = (byte) 7 ;

				bRes = false ;
				try {
					if((envHdlr.findTLV((byte)3,(byte)1)==TLV_NOT_FOUND)
						&&(envHdlr.getValueLength()==0))
						bRes = false;
				}
				catch (ToolkitException e)    {
					if(e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;
			
			case (byte) 7 : 
				// --------------------------------------------
				// Test Case 8 : search the tag 02 with a bad occurence (3) 
				testCaseNb = (byte) 8 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)2,(byte)3)==TLV_NOT_FOUND)
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

			case (byte) 8 : 
				// --------------------------------------------
				// Test Case 9 : search the tag 02 with a bad occurence (3) and try to get the length 
				testCaseNb = (byte) 9 ;

				bRes = false ;
				try {
					if((envHdlr.findTLV((byte)2,(byte)3)==TLV_NOT_FOUND)
						&&(envHdlr.getValueLength()==0))
						bRes = false;
				}
				catch (ToolkitException e)    {
					if(e.getReason()==ToolkitException.UNAVAILABLE_ELEMENT)
						bRes = true;
				}
				catch (Exception e)    {    
					bRes = false ;
				}
				reportTestOutcome(testCaseNb, bRes) ;
				break;

			case (byte) 9 : 
				// --------------------------------------------
				// Test Case 10 : search the occurence 02 of the tag 02 
				testCaseNb = (byte) 10 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)2,(byte)2)==TLV_FOUND_CR_NOT_SET)
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

			case (byte) 10 : 
				// --------------------------------------------
				// Test Case 11 : search the occurence 01 of the tag 04
				testCaseNb = (byte) 11 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)4,(byte)1)==TLV_FOUND_CR_NOT_SET)
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

			case (byte) 11 : 
				// --------------------------------------------
				// Test Case 12 : search the occurence 01 of the tag 86
				testCaseNb = (byte) 12 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)0x86,(byte)1)==TLV_FOUND_CR_SET)
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
				// Test Case 13 : search the occurence 01 of the tag 84
				testCaseNb = (byte) 13 ;

				bRes = false ;
				try {
					if(envHdlr.findTLV((byte)0x84,(byte)1)==TLV_FOUND_CR_NOT_SET)
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
