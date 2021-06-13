//-----------------------------------------------------------------------------
//Api_2_Pah_Aptlbbs_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_aptlbbs;

import javacard.framework.*;
import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_aptlbbs
 *
 * @version 0.0.1 - 20 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Aptlbbs_1 extends TestToolkitApplet
{
    private byte compareBuffer[] = new byte[256] ;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Aptlbbs_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Aptlbbs_1  thisApplet = new Api_2_Pah_Aptlbbs_1();
        
        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    public void processToolkit(short event)
    {
        
        // Result of each test
        boolean bRes = false ;
        boolean bRes2 = false ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;

        short result = (short) 0 ;

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
        byte tag = 0 ;
        byte value1 = 0 ;
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
            byte[] TmpBuffer = new byte[(short)(proHdlr.getCapacity()-1)];
            Util.arrayFillNonAtomic(TmpBuffer,(short)0,(short)TmpBuffer.length,(byte)0);
            proHdlr.appendArray(TmpBuffer,offset,(short)TmpBuffer.length) ;
            
            // appendTLV
            try {
                proHdlr.appendTLV(tag, value1, value2) ;
            } catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.HANDLER_OVERFLOW) ;
            }
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        

        // --------------------------------------------
        // Test Case 2 : the current TLV is not modified
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Initialise the handler
            proHdlr.init((byte)0, (byte)0, (byte)0) ;
            
            // Select tag 02h
            proHdlr.findTLV(TAG_COMMAND_DETAILS, (byte)1);
            byte command_details_value = proHdlr.getValueByte((short)0);

            // Append TLV
            proHdlr.appendTLV(tag, value1, value2) ;
            
            // Verify current TLV
            bRes = (proHdlr.getValueByte((short)0) == command_details_value) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : successful call
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;

            // Append TLV
            tag = (byte)0x84 ;
            value1 = (byte)0x00 ;
            value2 = (short)0x0102 ;
            proHdlr.appendTLV(tag, value1, value2) ;
            
            // Initialise compareBuffer
            compareBuffer[0] = tag ;
            compareBuffer[1] = (byte) 3;
            compareBuffer[2] = value1 ;
            compareBuffer[3] = (byte)(value2>>8 & (short)0x00FF);
            compareBuffer[4] = (byte)(value2 & (short)0x00FF) ;
            
            // Copy the handler
            offset = (short)0 ;
            length = (short)5 ;
            proHdlr.copy(buffer, offset, length) ;
            
            // Compare the handler
            result = Util.arrayCompare(buffer, offset, compareBuffer, offset, length) ;
            bRes = (result == 0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : successful call
        testCaseNb = (byte) 4;
        bRes = false ;
        
        try {
            
            // Append TLV
            tag = (byte)0x01 ;
            value1 = (byte)0xFE ;
            value2 = (short)0xFDFC ;
            proHdlr.appendTLV(tag, value1, value2) ;
            
            // Initialise compareBuffer
            compareBuffer[5] = tag ;
            compareBuffer[6] = (byte) 3;
            compareBuffer[7] = value1 ;
            compareBuffer[8] = (byte)(value2>>8 & (short)0x00FF);
            compareBuffer[9] = (byte)(value2 & (short)0x00FF);
            
            // Copy the handler
            offset = (short)0 ;
            length = (short)10 ;
            proHdlr.copy(buffer, offset, length) ;

            // Compare the handler
            result = Util.arrayCompare(buffer, offset, compareBuffer, offset, length) ;
            bRes = (result == 0) ;
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 5 : successful call
        testCaseNb = (byte) 5 ;
        bRes = false ;
        bRes2 = false ;
        
        try {
            
            // Clear the handler
            proHdlr.clear() ;

            // Initialise buffers
            for (short i=0; i<250; i++) {
                buffer[i] = (byte)i ;
                compareBuffer[i] = (byte)i ;
            }
            buffer[1] = (byte)0x81 ;
            buffer[2] = (byte)0xF5 ;
            compareBuffer[1] = (byte)0x81 ;
            compareBuffer[2] = (byte)0xF5 ;
            tag = (byte)0x84 ;
            value1 = (byte)0x00 ;
            value2 = (short)0x0102 ;
            compareBuffer[248] = tag ;
            compareBuffer[249] = (byte)3 ;
            compareBuffer[250] = value1 ;
            compareBuffer[251] = (byte)(value2>>8 & (short)0x00FF);
            compareBuffer[252] = (byte)(value2 & (short)0x00FF);
                    
            // Initialise the handler
            offset = (short)0 ;
            length = (short)248 ;
            proHdlr.appendArray(buffer, offset, length) ;
            
            // Append TLV
            proHdlr.appendTLV(tag, value1, value2) ;
            
            // Verify length of the handler
            bRes2 = (proHdlr.getLength() == (short)253) ;
                            
            // Copy the handler
            offset = (short)0 ;
            length = (short)proHdlr.getLength() ;
            proHdlr.copy(buffer, offset, length) ;
            
            // Compare the handler
            result = Util.arrayCompare(buffer, offset, compareBuffer, offset, length) ;
            bRes = (bRes2) && (result == (short)0) ;                                                        
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;             
    }
}
