//-----------------------------------------------------------------------------
//    FWK_ERP_ECCN_1.java
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
public class Cre_Erp_Eccn_1 extends TestToolkitApplet
{
	private static final byte[] DATA2POST = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;


	//	byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Cre_Erp_Eccn_1() {
	}


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {
        // Create a new applet instance
        Cre_Erp_Eccn_1 thisApplet = new Cre_Erp_Eccn_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Triggering on EVENT_CALL_CONTROL_BY_NAA
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
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

		// Get the system instance of the EnvelopeResponseHandler class
		EnvelopeResponseHandler envRespHandler = EnvelopeResponseHandlerSystem.getTheHandler();

		// EVENT_CALL_CONTROL_BY_NAA :
		// --------------------------------------------
		// The applet changes any incoming dialling number into + 11 22 33 44
		try {
			bRes = false;
			envRespHandler.appendTLV((byte)(TAG_ADDRESS | TAG_SET_CR), DATA2POST, (short)0x00, (short)DATA2POST.length);
	        envRespHandler.postAsBERTLV(true, (byte)0x02);
			bRes = true;
			}
		catch (Exception e)    {
		    bRes = false;
		}
		reportTestOutcome((byte)1, bRes);
	}
}
