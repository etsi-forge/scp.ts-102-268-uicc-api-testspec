//-----------------------------------------------------------------------------
//Api_2_Erh_Aptlbss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_erh_aptlbss;
//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

/**
 * Test Area : uicc.test.toolkit.api_2_erh_aptlbss
 *
 * @version 0.0.1 - 3 mars 2006
 * @author 3GPP T3 SWG API
 */
public class Api_2_Erh_Aptlbss_1 extends TestToolkitApplet {

    // Number of tests
    byte testCaseNb = (byte) 0x00;

    private byte compareBuffer[] = new byte[12] ;    
    /**
    * Constructor of the applet
    */
    public Api_2_Erh_Aptlbss_1() 
    {
    }

    /**
    * Method called by the JCRE at the installation of the applet
    */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Erh_Aptlbss_1  thisApplet = new Api_2_Erh_Aptlbss_1();

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
        short value1 = 0 ;
        short value2 = 0 ;
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
			length = (short)(EnvRespHdlr.getCapacity() - 1) ;
			while (length > 255){
				EnvRespHdlr.appendArray(buffer, offset, (short) 255) ;
				length = (short) (length - (short) 255);
			}
			EnvRespHdlr.appendArray(buffer, offset, length) ;
			
			// appendTLV
			try {
				EnvRespHdlr.appendTLV(tag, value1, value2) ;
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
            EnvRespHdlr.appendTLV((byte)0x81,(byte)0x11,(short)0x2233);
        
            //built the second tlv
            EnvRespHdlr.appendTLV((byte)0x82,(byte)0x99,(byte)0x77);
            
            // Select tag 02h
            EnvRespHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1) ;

            // Append TLV
            EnvRespHdlr.appendTLV(tag, value1, value2) ;
            
            // Verify current TLV
            if(EnvRespHdlr.getValueLength()==3)
                bRes =true;
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
            value1 = (short)0x0001 ;
            value2 = (short)0x0203 ;
            EnvRespHdlr.appendTLV(tag, value1, value2) ;
            
            // Initialise compareBuffer
            compareBuffer[0] = tag ;
            compareBuffer[1] = (byte) 4;
            compareBuffer[2] = (byte)(value1 >> 8);
            compareBuffer[3] = (byte) value1;
            compareBuffer[4] = (byte)(value2 >> 8);
            compareBuffer[5] = (byte)value2;
            
            // Copy the handler
            offset = (short)0 ;
            length = (short)6 ;
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
        // Test Case 4 : successful call
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            // Append TLV
            tag = (byte)0x01 ;
            value1 = (short)0xFEFD ;
            value2 = (short)0xFCFB ;
            EnvRespHdlr.appendTLV(tag, value1, value2) ;
            
            // Initialise compareBuffer
            compareBuffer[6] = tag ;
            compareBuffer[7] = (byte) 4;
            compareBuffer[8] = (byte)(value1 >> 8);
            compareBuffer[9] = (byte) value1 ;
            compareBuffer[10] = (byte)(value2 >> 8);
            compareBuffer[11] = (byte)value2;
            
            // Copy the handler
            offset = (short)0 ;
            length = (short)0x0C ;
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
                value1 = (byte)0xFEFD ;
                value2 = (byte)0xFCFB ;
                EnvRespHdlr.appendTLV(tag, value1, value2) ;

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
