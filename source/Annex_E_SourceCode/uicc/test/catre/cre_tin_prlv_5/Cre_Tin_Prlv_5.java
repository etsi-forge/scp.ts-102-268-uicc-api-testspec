//-----------------------------------------------------------------------------
//    Cre_Tin_Prlv_5.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_prlv_5;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;



public class Cre_Tin_Prlv_5 extends TestToolkitApplet
{

	private static byte bNbInstance = (byte)0 ;			 // instance number
	private static byte bNbInstanceTriggered = (byte)0 ; // number of times the applet is triggered
	private byte		bMyInstanceId ;

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Prlv_5() 
    {
 		Cre_Tin_Prlv_5.bNbInstance ++;
		this.bMyInstanceId = bNbInstance;
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Cre_Tin_Prlv_5 thisApplet = new Cre_Tin_Prlv_5();
 
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

		// Initialise the data of the test applet
        thisApplet.init();

		// register instance with the EVENT_EVENT_DOWNLOAD_USER_ACTIVITY event
		thisApplet.obReg.setEvent(EVENT_EVENT_DOWNLOAD_USER_ACTIVITY);
  }


    /**
     * Method called by the UICC CRE
     */
    public void processToolkit(short event) 
    {
		if (event == EVENT_EVENT_DOWNLOAD_USER_ACTIVITY) {
			
			bNbInstanceTriggered ++ ;
			switch (bMyInstanceId) {

		// -----------------------------------------------------------------
		//		 Instance 1 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)1:
				if ( bNbInstanceTriggered == (byte)3 ) {
					reportTestOutcome((byte)1, true) ;
				}
				else {
					reportTestOutcome((byte)1, false) ;
				}
				break;

		// -----------------------------------------------------------------
		//		 Instance 2 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)2:
				if ( bNbInstanceTriggered == (byte)2 ) {
					reportTestOutcome((byte)1, true) ;
				}
				else {
					reportTestOutcome((byte)1, false) ;
				}
				break;

		// -----------------------------------------------------------------
		//		 Instance 3 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)3:
				if ( bNbInstanceTriggered == (byte)1 ) {
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
	}
}



