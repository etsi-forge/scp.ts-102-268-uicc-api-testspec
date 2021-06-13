//-----------------------------------------------------------------------------
//Api_2_Pah_Init_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_pah_init;

import uicc.test.util.*;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_pah_init
 *
 * @version 0.0.1 - 13 juin 2005
 * @author 3GPP T3 SWG API
 */
public class Api_2_Pah_Init_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Pah_Init_1() 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Pah_Init_1  thisApplet = new Api_2_Pah_Init_1();

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
        
        // Temporary buffers
        byte[]  source = new byte[9] ;
        byte[]  destination = new byte[9] ;
        
        // Number of tests
        byte testCaseNb = (byte) 0x00 ;

        // Prepare source buffer for compare
        source[0] = (byte) 0x81 ;       // Command Details tag
        source[1] = (byte) 0x03 ;       // Length
        source[2] = (byte) 0x00 ;       // Command Number
        source[5] = (byte) 0x82 ;       // Device Identities tag
        source[6] = (byte) 0x02 ;       // Length
        source[7] = (byte) 0x81 ;       // Source device identities
        
        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
        // --------------------------------------------
        // Test Case 1 : 
        //      Call the init() method
        //      Verify each simple TLV
        
        testCaseNb = (byte) 1 ;

        try {
            // Call to init method
            //  type = 01h
            //  qualifier = 02h
            //  dstDevice = 03h
            
            proHdlr.init((byte)0x01, (byte)0x02, (byte) 0x03) ;
            
            // Verify length of the handler (=9)
            bRes = (proHdlr.getLength() == (short)9) ;
            
            // Copy the handler in a buffer
            proHdlr.copy(destination, (short)0, (short)9) ;
            
            // Delete Command Number (tested in test #2)
            destination[2] = 0 ;
            
            // Prepare source buffer
            source[3] = (byte) 0x01 ;   // Type of command
            source[4] = (byte) 0x02 ;   // Command qualifier
            source[8] = (byte) 0x03 ;   // Destination device identities
            
            // Compare buffers
            bRes = bRes & (javacard.framework.Util.arrayCompare(source, 
                        (short)0, 
                        destination,
                        (short)0,
                        (short)9) == 0) ;
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        // --------------------------------------------
        // Test Case 2 : 
        //      Verify the command number value
        
        testCaseNb = (byte) 2 ;
        bRes = false ;
        
        try {
            
            // Get command Number
            proHdlr.copy(destination, (short)0, (short)3) ;
            
            short commandNumber = (short) (destination[2] & (short)0xFF) ;
            
            bRes = ((commandNumber>=(short)0x01) && (commandNumber<=(short)0xFE)) ;
        }
        catch (Exception e) {
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 3 : 
        //      Call the init() method
        //      Verify each simple TLV
        
        testCaseNb = (byte) 3 ;
        bRes = false ;
        
        try {
            // Call to init method
            //  type = FFh
            //  qualifier = FEh
            //  dstDevice = FDh
            
            proHdlr.init((byte)0xFF, (byte)0xFE, (byte) 0xFD) ;
            
            // Verify length of the handler (=9)
            bRes = (proHdlr.getLength() == (short)9) ;
            
            // Copy the handler in a buffer
            proHdlr.copy(destination, (short)0, (short)9) ;
            
            // Delete Command Number (tested in test #2)
            destination[2] = 0 ;
            
            // Prepare source buffer
            source[3] = (byte) 0xFF ;   // Type of command
            source[4] = (byte) 0xFE ;   // Command qualifier
            source[8] = (byte) 0xFD ;   // Destination device identities
            
            // Compare buffers
            bRes = bRes & (javacard.framework.Util.arrayCompare(source, 
                        (short)0, 
                        destination,
                        (short)0,
                        (short)9) == 0) ;
            
        }
        catch (Exception e)    {    
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;

        
        // --------------------------------------------
        // Test Case 4 : 
        //      Verify there is no selected TLV
        
        testCaseNb = (byte) 4 ;
        bRes = false ;
        
        try {
            
            // Select 1st TLV
            proHdlr.findTLV((byte)0x01, (byte)0x01) ;
            
            proHdlr.init((byte)0, (byte)0, (byte)0) ;
            
            // Verify there is no selected TLV
            try {
                proHdlr.getValueLength() ;
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT) ;
            }
            
        } catch (Exception e) {
            bRes = false ;
        }
        
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
