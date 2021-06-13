//-----------------------------------------------------------------------------
//Api_2_Enh_Cprv_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_cprv;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

/**
 * Test Area : uicc.test.toolkit.Api_2_Enh_Cprv
 *
 * @version 0.0.1 - 3 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Cprv_1 extends TestToolkitApplet
{


    private byte[] compBuffer;
        
    // Number of tests
    byte testCaseNb = (byte) 0x00;
        
    /**
     * Constructor of the applet
     */
    public Api_2_Enh_Cprv_1 () 
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
            Api_2_Enh_Cprv_1  thisApplet = new Api_2_Enh_Cprv_1 ();

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
                                // Test Case 1 : try to use the compareValue method with a null compare Buffer 
                                testCaseNb = (byte) 1 ;

                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)2,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.compareValue((short)0,null,(short)0,(short)1)==0))
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
                                // Test Case 2 : use the compareValue method with an offset > buffer length
                                testCaseNb = (byte) 2 ;

                                bRes = false ;
                                compBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x0B,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)5,(short)1)==0))
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
                                // Test Case 3 : use the compareValue method with an offset = -1
                                testCaseNb = (byte) 3 ;

                                bRes = false ;
                                compBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x0B,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)-1,(short)1)==0))
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
                                // Test Case 4 : use the compareValue method with an length > buffer length
                                testCaseNb = (byte) 4 ;
                                
                                bRes = false ;
                                compBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x0B,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)0,(short)6)==0))
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
                                // Test Case 5 : use the compareValue method with an Offset + length > buffer length
                                testCaseNb = (byte) 5 ;
                                
                                bRes = false ;
                                compBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x0B,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)3,(short)3)==0))
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
                                // Test Case 6 : use the compareValue method with an compare length < 0
                                testCaseNb = (byte) 6 ;
                                
                                bRes = false ;
                                compBuffer =new byte[5];
                                try {
                                        if((envHdlr.findTLV((byte)0x0B,(byte)1)==TLV_FOUND_CR_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)0,(short)-1)==0))
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
                                // Test Case 7 : use the compareValue method with ValueOffset  > TLV length
                                testCaseNb = (byte) 7 ;
                                
                                bRes = false ;
                                compBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)6,compBuffer,(short)0,(short)1)==0))
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
                                // Test Case 8 : use the compareValue method with ValueOffset  <0
                                testCaseNb = (byte) 8 ;
                                
                                bRes = false ;
                                compBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)-1,compBuffer,(short)0,(short)1)==0))
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
                                // Test Case 9 : use the compareValue method with dstlength  > TLV length
                                testCaseNb = (byte) 9 ;
                                
                                bRes = false ;
                                compBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)0,(short)7)==0))
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
                                // Test Case 10 : use the compareValue method with ValueOffset+dstlength  > TLV length
                                testCaseNb = (byte) 10 ;
                                
                                bRes = false ;
                                compBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)2,compBuffer,(short)0,(short)5)==0))
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
                                // Test Case 11 : use the compareValue method without current TVL
                                testCaseNb = (byte) 11 ;
                                
                                bRes = false ;
                                compBuffer =new byte[15];
                                try {
                                        if((envHdlr.findTLV((byte)0x01,(byte)1)==TLV_NOT_FOUND)
                                                &&(envHdlr.compareValue((short)2,compBuffer,(short)0,(short)5)==0))
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
                                // Test Case 12 : use the compareValue method (normal execution comparaison buffer = TLV)
                                testCaseNb = (byte) 12 ;
                                
                                bRes = false ;
                                compBuffer =new byte[6];
                                
                                //intialize the array
                                compBuffer [0]= (byte)0x81;
                                compBuffer [1]= (byte)0x11;
                                compBuffer [2]= (byte)0x22;
                                compBuffer [3]= (byte)0x33;
                                compBuffer [4]= (byte)0x44;
                                compBuffer [5]= (byte)0xF5;

                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)0,(short)6)==0))
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
                                // Test Case 13 : use the compareValue method (normal execution comparaison buffer > TLV)
                                testCaseNb = (byte) 13 ;
                                
                                bRes = false ;
                                compBuffer =new byte[6];
                                
                                //intialize the array
                                compBuffer [0]= (byte)0x80;
                                compBuffer [1]= (byte)0x11;
                                compBuffer [2]= (byte)0x22;
                                compBuffer [3]= (byte)0x33;
                                compBuffer [4]= (byte)0x44;
                                compBuffer [5]= (byte)0xF5;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)0,(short)6)==1))
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
                                // Test Case 14 : use the compareValue method (normal execution comparaison buffer < TLV)
                                testCaseNb = (byte) 14 ;
                                
                                bRes = false ;
                                
                                //intialize the array
                                compBuffer [0]= (byte)0x83;
                                compBuffer [1]= (byte)0x11;
                                compBuffer [2]= (byte)0x22;
                                compBuffer [3]= (byte)0x33;
                                compBuffer [4]= (byte)0x44;
                                compBuffer [5]= (byte)0xF5;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)0,compBuffer,(short)0,(short)6)==-1))
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
                                // Test Case 15 : use the compareValue method with differents parameters(normal execution comparaison buffer = TLV)
                                testCaseNb = (byte) 15 ;
                                
                                bRes = false ;
                                compBuffer =new byte[13];
                                
                                //intialize the array
                                for(byte i=0;i<13;i++)
                                    compBuffer[i]=(byte)0x55;

                                compBuffer [3]= (byte)0x81;
                                compBuffer [4]= (byte)0x11;
                                compBuffer [5]= (byte)0x22;
                                compBuffer [6]= (byte)0x33;
                                compBuffer [7]= (byte)0x44;
                                compBuffer [8]= (byte)0xF5;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)1,compBuffer,(short)4,(short)5)==0))
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
                                // Test Case 16 : use the compareValue method with differents parameters(normal execution comparaison buffer > TLV)
                                testCaseNb = (byte) 16 ;
                                
                                bRes = false ;
                                compBuffer =new byte[13];
                                
                                //intialize the array
                                for(byte i=0;i<13;i++)
                                        compBuffer[i]=(byte)0x55;

                                compBuffer [3]= (byte)0x81;
                                compBuffer [4]= (byte)0x10;
                                compBuffer [5]= (byte)0x23;
                                compBuffer [6]= (byte)0x33;
                                compBuffer [7]= (byte)0x44;
                                compBuffer [8]= (byte)0xF5;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)1,compBuffer,(short)4,(short)5)==+1))
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
                
                        case (byte) 16 : 
                                // --------------------------------------------
                                // Test Case 17 : use the compareValue method with differents parameters(normal execution comparaison buffer < TLV)
                                testCaseNb = (byte) 17 ;
                                
                                bRes = false ;
                                compBuffer =new byte[13];
                                
                                //intialize the array
                                for(byte i=0;i<13;i++)
                                        compBuffer[i]=(byte)0x55;

                                compBuffer [3]= (byte)0x81;
                                compBuffer [4]= (byte)0x12;
                                compBuffer [5]= (byte)0x21;
                                compBuffer [6]= (byte)0x33;
                                compBuffer [7]= (byte)0x44;
                                compBuffer [8]= (byte)0xF5;
                                
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)1,compBuffer,(short)4,(short)5)==-1))
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

                        case (byte) 17 : 
                                // --------------------------------------------
                                // Test Case 18 : use successfully the compareValue method with the length of the copy =0
                                testCaseNb = (byte) 18 ;
                        
                                bRes = false ;
                                try {
                                        if((envHdlr.findTLV((byte)0x06,(byte)1)==TLV_FOUND_CR_NOT_SET)
                                                &&(envHdlr.compareValue((short)1,compBuffer,(short)compBuffer.length,(short)0)==0))
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
