//-----------------------------------------------------------------------------
//Api_2_Enh_Gvle_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_gvle;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Gvle
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Gvle_1 extends TestToolkitApplet
{

    // Number of tests
    byte testCaseNb = (byte) 0x00;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Enh_Gvle_1() 
    {
        }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Gvle_1  thisApplet = new Api_2_Enh_Gvle_1();

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
                ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
        
            switch ( testCaseNb ) 
                {
                        case (byte) 0 : 
                                // --------------------------------------------
                                // Test Case 1 : try to use the getValueLength method without current TLV
                                testCaseNb = (byte) 1 ;

                                bRes = false ;
                                try {
                                        short returned_value = envHdlr.getValueLength();
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
                                // Test Case 2 : search the TAG 02 and use the method getValueLength =02
                                testCaseNb = (byte) 2 ;

                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.getValueLength()==0x0002))
                                                        bRes = true;
                                }
                                catch (Exception e)    {    
                                        bRes = false ;
                                }
                                reportTestOutcome(testCaseNb, bRes) ;
                                break;

                        case (byte) 2 : 
                                // --------------------------------------------
                                // Test Case 3 : search the TAG 0B and use the method getValueLength =0x24
                                testCaseNb = (byte) 3 ;

                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)0x0B,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.getValueLength()==0x0024))
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
                                // Test Case 4 : search the TAG 0x33 and use the method getValueLength =0x00C8
                                testCaseNb = (byte) 4 ;

                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)0x33,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.getValueLength()==0x00C8))
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
