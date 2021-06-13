//-----------------------------------------------------------------------------
//    Cre_Erp_Eccn_2.java
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
public class Cre_Erp_Eccn_2 extends TestToolkitApplet
{
	private static final byte[] BUFFER = {(byte)'A',(byte)'P',(byte)'P',(byte)'L',(byte)'E',(byte)'T'} ;
	private static final byte WAIT_FOR_USER_TO_CLEAR_MESSAGE = (byte)0x80;
	private static final byte NEXT_ACTION_INDICATOR = (byte)0x00;
	private static final short BEGINNING_OF_STRING = (short)0x00;
	private static byte [] MenuEntry = {(byte) 'T', (byte) 'o', (byte) 'o', (byte) 'l',	(byte) 'k',	(byte) 'i',	(byte) 't',	(byte) 'T', (byte) 'e',	(byte) 's', (byte) 't'};



	//byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Cre_Erp_Eccn_2() {
	}


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {

        // Create a new applet instance
        Cre_Erp_Eccn_2 thisApplet = new Cre_Erp_Eccn_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Triggering on EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuEntry, BEGINNING_OF_STRING, (short)MenuEntry.length, NEXT_ACTION_INDICATOR, false, (byte)0, (short)0);
    }


    /**
       * Method called by the UICC CRE
     */
    public void processToolkit(short event)
    {
		// Result of each test
		boolean bRes = false;

		// Get the system instance of the EnvelopeHandler class
		EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();

        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;

		// EVENT_MENU_SELECTION :
		// --------------------------------------------
		// The applet prepares and send a Display Text Proactive command with the string "APPLET"
		try {
			bRes = false;
			proHdlr.initDisplayText(WAIT_FOR_USER_TO_CLEAR_MESSAGE, (byte)0x04 /*DCS_8_BIT_DATA*/, BUFFER, BEGINNING_OF_STRING, (short)BUFFER.length) ;
			proHdlr.send();
			bRes = true;
			}
		catch (Exception e)    {
		    bRes = false;
		}
		reportTestOutcome((byte)1, bRes);
	}
}
