//-----------------------------------------------------------------------------
//Api_2_Erh_Aptlbs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_aptlbs;

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_erh_aptlbs
 *
 * @version 0.0.1 - 11 avr. 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Aptlbs_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte compareBuffer[] = new byte[10] ;    
    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Aptlbs_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Aptlbs_1  thisApplet = new Api_2_Erh_Aptlbs_1();

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
        
        byte tag = 0 ;
        short value = 0 ;
        short length = 0 ;
        short offset = 0 ;
        byte[] buffer = new byte[256] ;
        
        // --------------------------------------------
        // Test Case 1 : handler overflow
        testCaseNb = (byte) 1 ;
		bRes = false ;
		
		try {
			
			// Initialise the handler
			offset = (short)0 ;
			buffer[1] = (byte) 0x81;
			buffer[2] = (byte) 250;
			length = (short)(EnvRespHdlr.getCapacity() - 1);
			while (length > 255){
				EnvRespHdlr.appendArray(buffer, offset, (short) 255) ;
				length = (short) (length - (short) 255);
			}
			EnvRespHdlr.appendArray(buffer, offset, length) ;
			
			// appendTLV
			try {
				EnvRespHdlr.appendTLV(tag, value) ;
			}
			catch (ToolkitException e) {
				bRes = (e.getReason() == ToolkitException.HANDLER_OVERFLOW) ;
			}
		}
		catch (Exception e) {
			bRes = false ;
		}

        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 2 : the current TLV is not modified
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // clear the handler
            EnvRespHdlr.clear();

            //built the first tlv
            buffer[0]=(byte)0x11;
            buffer[1]=(byte)0x22;
            buffer[3]=(byte)0x33;
            EnvRespHdlr.appendTLV((byte)0x81,buffer,(short) 0x00, (short) 3);
        
            //built the second tlv
            EnvRespHdlr.appendTLV((byte)0x82,(short) 0x9977);

            
            // Select tag 02h
            EnvRespHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;

            // Append TLV
            EnvRespHdlr.appendTLV(tag, value) ;
            
            // Verify current TLV
            if(EnvRespHdlr.getValueLength()==(short)3)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : successful call
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            
            //erase the begin of the array
            for(short i=0;i<10;i++)
                buffer[i]=(byte)0x00;
            
            // Clear the handler
            EnvRespHdlr.clear() ;

            // Append TLV
            tag = (byte)0x84 ;
            value = (short)0x0001 ;
            EnvRespHdlr.appendTLV(tag, value) ;
            
            // Initialise compareBuffer
            compareBuffer[0] = tag ;
            compareBuffer[1] = (byte) 0x02;
            compareBuffer[2] = (byte)(value >> 8);
            compareBuffer[3] = (byte)value;
            
            // Copy the handler
            offset = (short)0 ;
            length = (short)4 ;
            EnvRespHdlr.copy(buffer, offset, length) ;
            
            // Compare the handler
            if(Util.arrayCompare(buffer, offset, compareBuffer, offset, length)==0)
                bRes = true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : successful call
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            //erase the begin of the array
            for(short i=0;i<10;i++)
                buffer[i]=(byte)0x00;
            
            // Append TLV
            tag = (byte)0x01 ;
            value = (short)0xFEFF ;
            EnvRespHdlr.appendTLV(tag, value) ;
            
            // Initialise compareBuffer
            compareBuffer[4] = tag ;
            compareBuffer[5] = (byte) 2;
            compareBuffer[6] = (byte)(value >> 8);
            compareBuffer[7] = (byte)value;
            
            // Copy the handler
            offset = (short)0 ;
            length = (short)8 ;
            EnvRespHdlr.copy(buffer, offset, length) ;
            
            // Compare the handler
            if(Util.arrayCompare(buffer, offset, compareBuffer, offset, length)==0)
                bRes=true;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        // --------------------------------------------
        // Test Case 5 : ToolkitException HANDLER_NOT_AVAILABLE
        testCaseNb = (byte) 5 ;
        bRes = false ;
        
        try {
            EnvRespHdlr.clear();
            EnvRespHdlr.post(true);
            // Append TLV
            try {
                tag = (byte)0x01 ;
                value = (byte)0xFEFF ;
                EnvRespHdlr.appendTLV(tag, value) ;
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
