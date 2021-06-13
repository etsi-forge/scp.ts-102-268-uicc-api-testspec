//-----------------------------------------------------------------------------
//    Cre_Hin_Pahd_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_pahd;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Hin_Pahd_2 extends TestToolkitApplet{


    byte[] dstBuffer = new byte[20];
     
    public static byte[] init = {(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x80, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x02};
    public static byte[] initCloseChannel = {(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x41, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x21};
    public static byte[] initDisplayText = {(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x21, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x02, (byte) 0x8D, (byte) 0x05, (byte) 0x00, (byte) 0x54, (byte) 0x65, (byte) 0x78, (byte) 0x74};
 	public static byte[] initGetInkey = {(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x22, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x82, (byte) 0x8D, (byte) 0x05, (byte) 0x00, (byte) 0x54, (byte) 0x65, (byte) 0x78, (byte) 0x74};
 	public static byte[] initGetInput = {(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x23, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x82, (byte) 0x8D, (byte) 0x05, (byte) 0x00, (byte) 0x54, (byte) 0x65, (byte) 0x78, (byte) 0x74, (byte) 0x91, (byte) 0x02, (byte) 0x05, (byte) 0x07};
 	public static byte[] initMoreTime = {(byte) 0x81, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x82, (byte) 0x02, (byte) 0x81, (byte) 0x82};
    
    byte qualifier=0x00;  //displayText, getInkey, getInput
    byte dcs=0x00;        //displayText, getInkey, getInput
    public static byte[] buffer = {(byte) 0x54, (byte) 0x65,(byte) 0x78, (byte) 0x74};//Text
    short minRespLength=0x05;
    short maxRespLength=0x07;
    
    /**
     * Constructor of the applet
     */
    public Cre_Hin_Pahd_2() {    
                
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Hin_Pahd_2 thisApplet = new Cre_Hin_Pahd_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // register instance with the EVENT_EVENT_DOWNLOAD_USER_ACTIVITY event
        thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
   
    }

    
    public void processToolkit(short event)
    {
        
                
            boolean bRes = false;
            byte    bTestCaseNb = (byte)0;
            
            // Get the system instance of handlers
            ProactiveHandler proactiveHdlr = ProactiveHandlerSystem.getTheHandler();

        // -----------------------------------------------------------------------
        // Test Case 4 : At the processToolkit invocation the TLV-List is cleared
        // -----------------------------------------------------------------------               
            bTestCaseNb = (byte)1;
            
            try{
                bRes = (proactiveHdlr.getLength() == (short)0);
                proactiveHdlr.getValueLength();
                bRes = false;
            }
			catch (ToolkitException e) {
            	bRes = (e.getReason() == ToolkitException.UNAVAILABLE_ELEMENT);
            }
            catch (Exception e) {
                bRes = false;
            }  
            reportTestOutcome ( bTestCaseNb, bRes );

            
        // -----------------------------------------------------------------------
        // Test Case 5 : At the call of its init method the content is cleared and then initialized
        // -----------------------------------------------------------------------               
			bRes = false;
			bTestCaseNb = (byte)2;
            
            //init
			try{
            	proactiveHdlr.init(PRO_CMD_DISPLAY_TEXT, (byte)0x80, DEV_ID_DISPLAY);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(init, (short)0, dstBuffer, (short)0, (short)init.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes ); 
                      
        
			bRes = false;
			bTestCaseNb = (byte)3;
            
            //initCloseChannel
			try{
            	proactiveHdlr.initCloseChannel((byte) 0x01);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(initCloseChannel, (short)0, dstBuffer, (short)0, (short)initCloseChannel.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );         
        
			bRes = false;
			bTestCaseNb = (byte)4;
            
            //initDisplayText
			try{
            	proactiveHdlr.initDisplayText((byte) qualifier,(byte) dcs,(byte[]) buffer, (short) 0, (short) buffer.length);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(initDisplayText, (short)0, dstBuffer, (short)0, (short)initDisplayText.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );         
         
			bRes = false;
			bTestCaseNb = (byte)5;
            
            //initGetInkey
			try{
            	proactiveHdlr.initGetInkey((byte) qualifier,(byte) dcs,(byte[]) buffer, (short) 0, (short) buffer.length);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(initGetInkey, (short)0, dstBuffer, (short)0, (short)initGetInkey.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );         

			bRes = false;
			bTestCaseNb = (byte)6;
            
            //initGetInput
			try{
            	proactiveHdlr.initGetInput((byte) qualifier,(byte) dcs,(byte[]) buffer, (short) 0,(short) buffer.length,(short) minRespLength,(short) maxRespLength);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(initGetInput, (short)0, dstBuffer, (short)0, (short)initGetInput.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );         

			bRes = false;
			bTestCaseNb = (byte)7;
            
            //initMoreTime
			try{
            	proactiveHdlr.initMoreTime();
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(initMoreTime, (short)0, dstBuffer, (short)0, (short)initMoreTime.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );    
            
			bRes = false;
			bTestCaseNb = (byte)8;
            
            //init
			try{
            	proactiveHdlr.init(PRO_CMD_DISPLAY_TEXT, (byte)0x80, DEV_ID_DISPLAY);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)proactiveHdlr.getLength());
                if (Util.arrayCompare(init, (short)0, dstBuffer, (short)0, (short)init.length)==0) {
                    bRes = true;   
                }    
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );              
        
    }
}
