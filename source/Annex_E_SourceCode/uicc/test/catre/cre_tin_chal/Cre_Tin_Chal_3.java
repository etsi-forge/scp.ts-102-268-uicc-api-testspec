//-----------------------------------------------------------------------------
//    Cre_Tin_Chal_3.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_chal;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Tin_Chal_3 extends TestToolkitApplet {

    // Channel number to open
    final static short CHANNELS_NB = 3;
    
    static byte[] menu = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'3'}; 
    public final static byte QUALIFIER_1 = (byte)0x01;
    public final static byte QUALIFIER_2 = (byte)0x02;
    public static byte[] ADDRESS_VALUE = {(byte)0x81, (byte)0x55, (byte)0x66, (byte)0x77, (byte)0x88};
    public static byte[] BEARER_VALUE = {(byte)0x03, (byte)0x00};
    public static byte[] BUFFER_SIZE_VALUE = {(byte)0x00, (byte)0x0A};

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Chal_3() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        // Create a new applet instance
        Cre_Tin_Chal_3 thisApplet = new Cre_Tin_Chal_3();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    

        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menu, (short)0, (short)menu.length, (byte)0, false, (byte) 0, (short) 0);

        // register instance with the EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS event
        thisApplet.obReg.setEvent((short)EVENT_EVENT_DOWNLOAD_CHANNEL_STATUS);
}
    

    /**
     * Method called by the UICC CRE
     */
    public void processToolkit(short event) {
        
        // Result of each test
        boolean bRes = false ;
        
        // Number of tests
        byte testCaseNb;

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler();

       
	    if (event == EVENT_MENU_SELECTION) {
        	// --------------------------------------------
        	// Test Case 6 : Open 3 channels
        	testCaseNb = (byte) 1 ;

            for (short i=0; i<CHANNELS_NB; i++) {
              	bRes = false ;
                try {
                    proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, (byte)DEV_ID_TERMINAL);
                    proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                    proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                    proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
                    proHdlr.send();
                    bRes = true ;
                }
		        catch (Exception e) {	
			        bRes = false;
		        }
        	    reportTestOutcome(testCaseNb++, bRes) ;
            }

            // Try to Open a 4th channel
            try {
                proHdlr.init(PRO_CMD_OPEN_CHANNEL, QUALIFIER_1, (byte)DEV_ID_TERMINAL);
                proHdlr.appendTLV(TAG_ADDRESS, ADDRESS_VALUE, (short)0, (short)5);
                proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, BEARER_VALUE, (short)0, (short)2);
                proHdlr.appendTLV(TAG_BUFFER_SIZE, BUFFER_SIZE_VALUE, (short)0, (short)2);
                proHdlr.send();
                bRes = true ;
            }
		    catch (Exception e) {	
			    bRes = false;
		    }
        	reportTestOutcome(testCaseNb, bRes) ;

	    }
    }
}
