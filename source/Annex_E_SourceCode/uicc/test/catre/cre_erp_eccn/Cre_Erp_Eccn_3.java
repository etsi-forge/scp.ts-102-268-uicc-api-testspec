//-----------------------------------------------------------------------------
//    Cre_Erp_Eccn_3.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_erp_eccn;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.test.util.*;

/**
 * Test Area : uicc.toolkitframework
 *
 * Chapter: Envelope Response Posting
 * Test Area inside the chapter: EVENT_CALL_CONTROL_BY_NAA
 *
 *
 * @version 0.0.1 - 11/12/01
 * @author 3GPP T3 SWG API
 *
 */
public class Cre_Erp_Eccn_3 extends TestToolkitApplet
{
	private static final byte[] BUFFER = {(byte)'A',(byte)'P',(byte)'P',(byte)'L',(byte)'E',(byte)'T'} ;
	private static final byte WAIT_FOR_USER_TO_CLEAR_MESSAGE = (byte)0x80;
	private static final byte NEXT_ACTION_INDICATOR = (byte)0x00;
	private static final short BEGINNING_OF_STRING = (short)0x00;
	private static final byte[] DATA2POST = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
	private static byte [] MenuEntry = {(byte) 'T', (byte) 'o', (byte) 'o', (byte) 'l',	(byte) 'k',	(byte) 'i',	(byte) 't',	(byte) 'T', (byte) 'e',	(byte) 's', (byte) 't'};


    /**
     * Constructor of the applet
     */
    public Cre_Erp_Eccn_3() {
	}


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {

        // Create a new applet instance
        Cre_Erp_Eccn_3 thisApplet = new Cre_Erp_Eccn_3();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Triggering on EVENT_CALL_CONTROL_BY_NAA
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
        // Triggering on EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuEntry, BEGINNING_OF_STRING, (short)MenuEntry.length, NEXT_ACTION_INDICATOR, false, (byte)0, (short)0);
    }


    /**
       * Method called by the SIM Toolkit Framework
     */
    public void processToolkit(short event)
    {
		// Result of each test
		boolean bRes = false;

		// Get the system instance of the EnvelopeHandler class
		EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();

		switch ( event )
			{
				case EVENT_MENU_SELECTION :
				// --------------------------------------------
				// The applet prepares and send a Display Text Proactive command with the string "APPLET"
				try {
			        // Get the system instance of the ProactiveHandler class
			        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
					bRes = false;
					proHdlr.initDisplayText(WAIT_FOR_USER_TO_CLEAR_MESSAGE, (byte)0x04 /*DCS_8_BIT_DATA*/, BUFFER, BEGINNING_OF_STRING, (short)BUFFER.length) ;
					proHdlr.send();
					bRes = true;
				}
				catch (Exception e)    {
				    bRes = false;
				}
				reportTestOutcome((byte)1, bRes);
				break;

				case EVENT_CALL_CONTROL_BY_NAA :
					// --------------------------------------------
					// The applet changes any incoming dialling number into + 11 22 33 44
					EnvelopeResponseHandler envRespHandler = EnvelopeResponseHandlerSystem.getTheHandler();
					envRespHandler.appendTLV((byte)(TAG_ADDRESS | TAG_SET_CR), DATA2POST, (short)0x00, (short)DATA2POST.length);
                    envRespHandler.postAsBERTLV(true, (byte)0x02);

				break;


			}

	}
}
