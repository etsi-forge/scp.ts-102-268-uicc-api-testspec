//-----------------------------------------------------------------------------
//    Cre_Hin_Pahd_1.java
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



public class Cre_Hin_Pahd_1 extends TestToolkitApplet{


    byte[] dstBuffer = new byte[9];
    byte[] data = new byte[9];    
    
    short dataOffset;
    short dataLength; 
    short dstLength; 
    
    /**
     * Constructor of the applet
     */
    public Cre_Hin_Pahd_1() {    
                
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Hin_Pahd_1 thisApplet = new Cre_Hin_Pahd_1();

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
        // Test Case 1 : At the processToolkit invocation the TLV-List is cleared
        // -----------------------------------------------------------------------               
            bTestCaseNb = (byte)1;
            
            try{
                bRes = (proactiveHdlr.getLength() == (short)0);
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes );

            
        // -----------------------------------------------------------------------
        // Test Case 2 : TLV-List change after the init method invocation
        // -----------------------------------------------------------------------               

            bTestCaseNb = (byte)2;
            
            try{
                proactiveHdlr.init(PRO_CMD_DISPLAY_TEXT, (byte)0x80, DEV_ID_DISPLAY);
                bRes = ((dstLength=proactiveHdlr.getLength()) == (short)9);
                proactiveHdlr.copy((byte[]) dstBuffer, (short) 0, (short)dstLength); 
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes ); 
                      
        // -----------------------------------------------------------------------
        // Test Case 3 : The TLV-List remains unchanged after the send() method invocation
        // -----------------------------------------------------------------------               
            bRes = false; 
            bTestCaseNb = (byte)3;
            
            try{                
                proactiveHdlr.send();                 
                bRes = ((dstLength=proactiveHdlr.getLength()) == (short)9);              
                proactiveHdlr.copy((byte[]) data, (short) 0, (short)dstLength); 
                if (Util.arrayCompare(data, (short)0, dstBuffer, (short)0, (short)9)==0) {
                    bRes = true;   
                }                                                                                                                                               
            }
            catch (Exception e) {
                bRes = false;
            }   
            reportTestOutcome ( bTestCaseNb, bRes ); 
    }
}
