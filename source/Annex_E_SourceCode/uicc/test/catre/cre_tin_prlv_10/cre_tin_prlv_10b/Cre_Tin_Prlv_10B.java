//-----------------------------------------------------------------------------
//    Cre_Tin_Prlv_10B.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_prlv_10.cre_tin_prlv_10b;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;
import uicc.test.catre.cre_tin_prlv_10.Cre_Tin_Prlv_10 ;



public class Cre_Tin_Prlv_10B extends TestToolkitApplet
{

	private static byte bNbInstance = (byte)0 ; // instance number
	private byte		bMyInstanceId ;

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Prlv_10B() 
    {
		Cre_Tin_Prlv_10B.bNbInstance ++;
		this.bMyInstanceId = bNbInstance;
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {

        // Create a new applet instance
        Cre_Tin_Prlv_10B thisApplet = new Cre_Tin_Prlv_10B();
 
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
			
            Cre_Tin_Prlv_10.bNbInstanceTriggered ++ ;
			switch (bMyInstanceId) {

		// -----------------------------------------------------------------
		//		 Instance 3 execution, Priority Level 1, Execution in 1st place
		// -----------------------------------------------------------------

			case (byte)1:
				if ( Cre_Tin_Prlv_10.bNbInstanceTriggered == (byte)1 ) {
					reportTestOutcome((byte)1, true) ;
				}
				else {
					reportTestOutcome((byte)1, false) ;
				}
				break;

		// -----------------------------------------------------------------
		//		 Instance 4 execution, Priority Level 2, Execution in 3rd place
		// -----------------------------------------------------------------

			case (byte)2:
				if ( Cre_Tin_Prlv_10.bNbInstanceTriggered == (byte)3 ) {
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



