//-----------------------------------------------------------------------------
//Api_2_Enh_Giid_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------

package uicc.test.toolkit.api_2_enh_giid;

import uicc.toolkit.*;
import uicc.test.util.*;;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------


/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Giid
 *
 * @version 0.0.1 - 28 oct. 2004
 * @author 3GPP T3 SWG API
 */

public class Api_2_Enh_Giid_1 extends TestToolkitApplet
{

    /** Registry entry concerning applet instance */
    private static byte[] displayString = {(byte)'H', (byte)'E', (byte)'L', (byte)'L', (byte)'O'};
    private static byte[] Menu1 = {(byte)'M', (byte)'E', (byte)'N', (byte)'U', (byte)'1'};
    private static byte[] Menu2 = {(byte)'M', (byte)'E', (byte)'N', (byte)'U', (byte)'2'};
    private static byte[] Menu3 = {(byte)'M', (byte)'E', (byte)'N', (byte)'U', (byte)'3'};
    private static byte[] Menu4 = {(byte)'M', (byte)'E', (byte)'N', (byte)'U', (byte)'4'};
    private static byte[] Menu66 = {(byte)'M', (byte)'E', (byte)'N', (byte)'U', (byte)'6', (byte)'6'};
    
    // Number of tests
    byte testCaseNb = (byte) 0x00 ;

    private static final byte DCS_8_BIT_DATA = (byte) 0x04;


    /**
     * Constructor of the applet
     */
    public Api_2_Enh_Giid_1() 
    {
        
                
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Giid_1  thisApplet = new Api_2_Enh_Giid_1();
        
        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        
        // Initialise the data of the test applet
        thisApplet.init();
        
        // toolkit registration
        thisApplet.obReg.initMenuEntry(Menu1, (short) 0, (short) Menu1.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(Menu2, (short) 0, (short) Menu2.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(Menu3, (short) 0, (short) Menu3.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(Menu4, (short) 0, (short) Menu4.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(Menu66, (short) 0, (short) Menu66.length, (byte) 0, false, (byte) 0, (short) 0);
        
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
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
        switch ( testCaseNb ) {
        
                case (byte) 0 :
                        // --------------------------------------------
                        // Test Case 1 : get item identifier value is 03
                        testCaseNb = (byte) 1 ;

                        bRes = false ;
                        try {
                                if (envHdlr.getItemIdentifier() == (byte) 0x03)
                                        bRes = true;
                        }
                        catch (Exception e)    {    
                                bRes = false ;
                        }
                        reportTestOutcome(testCaseNb, bRes) ;

                break;

                case (byte) 1 :
                        // --------------------------------------------
                        // Test Case 2 : Receive envelope with two item identifier TLV with first value 02 and second 01
                        //               method must return 02
                        testCaseNb = (byte) 2 ;

                        bRes = false ;
                        try {
                                if (envHdlr.getItemIdentifier() == (byte) 0x02)
                                        bRes = true;
                        }
                        catch (Exception e)    {    
                                bRes = false ;
                        }
                        reportTestOutcome(testCaseNb, bRes) ;

                break;

                case (byte) 2 :
                        // --------------------------------------------
                        // Test Case 3 : Receive envelope with two item identifier TLV with first value 04 and second 01, call twice the method getItemIdentifier
                        //               method must always return 04
                        testCaseNb = (byte) 3 ;

                        bRes = false ;
                        try {
                                if ((envHdlr.getItemIdentifier() == (byte) 0x04) && (envHdlr.getItemIdentifier() == (byte) 0x04) )
                                        bRes = true;
                        }
                        catch (Exception e)    {    
                                bRes = false ;
                        }
                        reportTestOutcome(testCaseNb, bRes) ;

                break;

                case (byte) 3 :
                        // --------------------------------------------
                        // Test Case 4 : Receive envelope with item identifier TLV and value of 66. FindTLV with TAG 02. getItemIdentifier and after getValueByte with offset 0
                        //               getItemIdentifier returned value = getValueByte returned value
                        testCaseNb = (byte) 4 ;

                        bRes = false ;
                        try {
                                envHdlr.findTLV(TAG_DEVICE_IDENTITIES, (byte)1);
                                if ( envHdlr.getItemIdentifier() ==  envHdlr.getValueByte((short)0) )
                                        bRes = true;
                        }
                        catch (Exception e)    {    
                                bRes = false ;
                        }
                        reportTestOutcome(testCaseNb, bRes) ;

                break;

                case (byte) 4 :
                        // --------------------------------------------
                        // Test Case 5 : Receive envelope without item identifier TLV and getItemIdentifier
                        //               getItemIdentifier must throw ToolkitException (UNAVAILABLE_ELEMENT)
                        testCaseNb = (byte) 5 ;

                        bRes = false ;
                        try {
                                envHdlr.getItemIdentifier();
                                bRes = false;
                        }
                        catch (ToolkitException e)    {    
                                if (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT )
                                        bRes = true;
                                
                        }
                        catch (Exception e)    {    
                                bRes = false ;
                        }
                        reportTestOutcome(testCaseNb, bRes) ;

                break;

                case (byte) 5 :
                        // --------------------------------------------
                        // Test Case 6 : Receive envelope with item identifier TLV (66), send proactive command. Then getItemIdentifier
                        //               method must return 66
                        testCaseNb = (byte) 6 ;

                        bRes = false ;
                        try {
                                proHdlr.initDisplayText((byte)0x80, DCS_8_BIT_DATA, displayString, (short) 0, (short) displayString.length);
                                proHdlr.send();
                                if (envHdlr.getItemIdentifier() == (byte) 0x66)
                                        bRes = true;
                        }
                        catch (Exception e)    {    
                                bRes = false ;
                        }
                        reportTestOutcome(testCaseNb, bRes) ;

                break;

                case (byte) 6 :
                        // --------------------------------------------
                        // Test Case 7 : Receive envelope with item identifier TLV but without item number, send proactive command.
                        testCaseNb = (byte) 7 ;

                        bRes = false ;
                        try {
                                if (envHdlr.getItemIdentifier() == (byte) 0x00)
                                        bRes = false;
                        }
                        catch (ToolkitException e)    {    
                                if (e.getReason() == ToolkitException.OUT_OF_TLV_BOUNDARIES)
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
