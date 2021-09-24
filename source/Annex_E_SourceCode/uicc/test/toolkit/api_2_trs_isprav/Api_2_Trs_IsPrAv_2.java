//-----------------------------------------------------------------------------
//    Api_2_Trs_IsPrAv_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_trs_isprav;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;
import uicc.toolkit.*;



public class Api_2_Trs_IsPrAv_2 extends TestToolkitApplet
{

	private static byte bNbInstance = (byte)0 ;			 // instance number
	private byte		bMyInstanceId ;

    /**
     * Constructor of the applet
     */
    public Api_2_Trs_IsPrAv_2() 
    {
		Api_2_Trs_IsPrAv_2.bNbInstance ++;
		this.bMyInstanceId = bNbInstance;
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {
        // Create a new applet instance
        Api_2_Trs_IsPrAv_2 thisApplet = new Api_2_Trs_IsPrAv_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

		// Initialise the data of the test applet
        thisApplet.init();

		// register instance with the EVENT_PROACTIVE_HANDLER_AVAILABLE  event
		thisApplet.obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
  }


    /**
     * Method called by the UICC CRE
     */
    public void processToolkit(short event)
    {
		if (event == EVENT_PROACTIVE_HANDLER_AVAILABLE) {

			try {
				switch (bMyInstanceId) {
					// -----------------------------------------------------------------
					//		 Instance 1 execution, Priority Level 1
					// -----------------------------------------------------------------

					case (byte)1:
						if ( ToolkitRegistrySystem.isPrioritizedProactiveHandlerAvailableEventSet() == false) {
							reportTestOutcome((byte)1, true) ;
						}
						else {
							reportTestOutcome((byte)1, false) ;
						}
						break;

					// -----------------------------------------------------------------
					//		 Instance 2 execution, Priority Level 2
					// -----------------------------------------------------------------

					case (byte)2:
						if ( ToolkitRegistrySystem.isPrioritizedProactiveHandlerAvailableEventSet() == true ) {
							reportTestOutcome((byte)1, true) ;
						}
						else {
							reportTestOutcome((byte)1, false) ;
						}
						break;

					default:
						reportTestOutcome ((byte)1, false);
						break;

				}
			}
			catch (Exception e) {
			}
		}
	}
}



