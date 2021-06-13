//-----------------------------------------------------------------------------
//Api_2_Enh_Gvsh_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gvsh;

import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_enh_gvsh
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Gvsh_1 extends TestToolkitApplet
{
    // Result of tests
    boolean bRes ;
    
    // Number of tests
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_2_Enh_Gvsh_1() 
    {
        }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Gvsh_1  thisApplet = new Api_2_Enh_Gvsh_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register on UNRECOGNIZED ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

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
                                // Test Case 1 : use the method getValueShort with the offset parameter = 0
                                testCaseNb = (byte) 1 ;

                                bRes = false ;
                                try {
                                        if (envHdlr.getValueShort((short)0)==0)
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

                        case (byte) 1 :
                                // --------------------------------------------
                                // Test Case 2 : use the method getValueByte() with the offset parameter out of the tlv
                                testCaseNb = (byte) 2 ;

                                bRes = false ;
                                try {
                                        if ((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)2)==(short)0x8381 ))
                                                bRes = false;
                                }
                                catch (ToolkitException e)    {    
                                        if(e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                bRes = true;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 2 :
                                // --------------------------------------------
                                // Test Case 3 : normal execution of the getValueByte() method on TAG 02 and offset 0
                                testCaseNb = (byte) 3 ;

                                bRes = false ;
                                try {
                                        if ((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)0)==(short)0x8381 ))
                                                bRes = true;
                                        else
                                                bRes = false;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 3 :
                                // --------------------------------------------
                                // Test Case 4 :  normal execution of the getValueByte() method on TAG 06 and offset 01
                                testCaseNb = (byte) 4 ;

                                bRes = false ;
                                try {
                                        if ((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)1)==(short)0x1122 ))
                                                bRes = true;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 4 :
                                // --------------------------------------------
                                // Test Case 5 :  normal execution of the getValueByte() method on TAG 33 and offset 7E
                                testCaseNb = (byte) 5 ;

                                bRes = false ;
                                try {
                                        if ((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)0x007E)==(short)0x7F80 ))
                                                bRes = true;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 5 :
                                // --------------------------------------------
                                // Test Case 6 : normal execution of the getValueByte() method on TAG 33 and offset 80
                                testCaseNb = (byte) 6 ;

                                bRes = false ;
                                try {
                                        if ((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)0x0080)==(short)0x8182 ))
                                                bRes = true;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 6 :
                                // --------------------------------------------
                                // Test Case 7 : normal execution of the getValueByte() method on TAG 33 and offset 7F
                                testCaseNb = (byte) 7 ;

                                bRes = false ;
                                try {
                                        if ((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)0x007F)==(short)0x8081 ))
                                                bRes = true;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 7 :
                                // --------------------------------------------
                                // Test Case 8 : normal execution of the getValueByte() method on TAG B3 and offset C7
                                testCaseNb = (byte) 8 ;
                                bRes = false ;

                                try {
                                        if ((envHdlr.findTLV((byte)0xB3,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueShort((short)0x00C7)==(short)0xC8C9 ))
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
