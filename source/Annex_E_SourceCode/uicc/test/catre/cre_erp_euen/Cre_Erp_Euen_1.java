//-----------------------------------------------------------------------------
//    Cre_Erp_Euen_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_erp_euen;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.test.util.* ;


public class Cre_Erp_Euen_1 extends TestToolkitApplet
{
	private static final short BEGINNING_OF_STRING = (short)0x00;
	private static final byte[] DATA2POST = {(byte)0x0C, (byte)0xAB} ;
	byte testCaseNb = (byte) 0x00;
	byte[] buffer = new byte[25];

    /**
     * Constructor of the applet
     */
    public Cre_Erp_Euen_1() {
	}


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {

        // Create a new applet instance
        Cre_Erp_Euen_1 thisApplet = new Cre_Erp_Euen_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Triggering on EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }


    /**
       * Method called by the UICC CRE
     */
    public void processToolkit(short event)
    {

		// Result of each test
		boolean bRes = false;

		// Sets the test case number
		testCaseNb++;

		// Get the system instance of the EnvelopeHandler class
		EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();

		// Get the system instance of the EnvelopeResponseHandler class
		EnvelopeResponseHandler envRespHandler = EnvelopeResponseHandlerSystem.getTheHandler();

		switch ( testCaseNb )
			{
				case (byte) 1 :
				// --------------------------------------------
				// The UICC shall post the data issued by the applet
				try {
					bRes = false;
					envRespHandler.appendArray(DATA2POST, (short)0x00, (short)DATA2POST.length);
			        envRespHandler.post(true);
				    bRes = true;
					}
				catch (Exception e)    {
				    bRes = false;
				}
				reportTestOutcome(testCaseNb, bRes);
				break;

				case (byte) 2 :
				// --------------------------------------------
				// The UICC shall post the data issued by the applet
				try {
					bRes = false;
					envRespHandler.appendArray(DATA2POST, (short)0x00, (short)DATA2POST.length);
			        envRespHandler.post(true);
				    bRes = true;
					}
				catch (Exception e)    {
				    bRes = false;
				}
				reportTestOutcome(testCaseNb, bRes);
				break;
			}

	}
}
