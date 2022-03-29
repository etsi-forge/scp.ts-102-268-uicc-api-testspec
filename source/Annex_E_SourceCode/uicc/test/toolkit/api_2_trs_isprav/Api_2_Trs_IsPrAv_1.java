//-----------------------------------------------------------------------------
//    Api_2_Trs_IsPrAv_1.java
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



public class Api_2_Trs_IsPrAv_1 extends TestToolkitApplet
{

	private static byte bNbInstance = (byte)0 ;			 // instance number
	private byte		bMyInstanceId ;

    /**
     * Constructor of the applet
     */
    public Api_2_Trs_IsPrAv_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Trs_IsPrAv_1 thisApplet = new Api_2_Trs_IsPrAv_1();
 
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
				if ( ToolkitRegistrySystem.isPrioritizedProactiveHandlerAvailableEventSet() == true ) {
					reportTestOutcome((byte)1, true) ;
				}
				else {
					reportTestOutcome((byte)1, false) ;
				}				
			} 
			catch (Exception e) {
				reportTestOutcome((byte)1, false) ;
			}
		}
	}
}



