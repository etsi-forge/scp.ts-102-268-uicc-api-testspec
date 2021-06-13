//-----------------------------------------------------------------------------
//    Cre_Tin_Prlv_10A.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_prlv_10.cre_tin_prlv_10a;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;
import uicc.test.catre.cre_tin_prlv_10.Cre_Tin_Prlv_10 ;



public class Cre_Tin_Prlv_10A extends TestToolkitApplet
{

	private static byte bNbInstance = (byte)0 ;	// instance number
	private byte		bMyInstanceId ;
	private static byte	bNbCall = (byte)1;		// number of triggering calls of the applet

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Prlv_10A() 
    {
		Cre_Tin_Prlv_10A.bNbInstance ++;
		this.bMyInstanceId = bNbInstance;
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {

        // Create a new applet instance
        Cre_Tin_Prlv_10A thisApplet = new Cre_Tin_Prlv_10A();
 
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
		//		 Instance 1 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)1:
				// ---------------------------------------------------------
				//		 First call with only this package applets installed, Execution in 1st place
				// ---------------------------------------------------------
				switch ( bNbCall ) {

				case (byte)1:
					if ( Cre_Tin_Prlv_10.bNbInstanceTriggered == (byte)1 ) {
						reportTestOutcome((byte)1, true) ;
					}
					else {
						reportTestOutcome((byte)1, false) ;
					}
					break;

				// ---------------------------------------------------------
				//		 Second call with all applets installed, Execution in 2nd place
				// ---------------------------------------------------------
				case (byte)2:
					if ( Cre_Tin_Prlv_10.bNbInstanceTriggered == (byte)2 ) {
						reportTestOutcome((byte)2, true) ;
					}
					else {
						reportTestOutcome((byte)2, false) ;
					}
					break;

				default:
					break;
				}
				break;

		// -----------------------------------------------------------------
		//		 Instance 2 execution, Priority Level 2
		// -----------------------------------------------------------------

			case (byte)2:
				// ---------------------------------------------------------
				//		 First call with only this package applets installed, Execution in 2nd place
				// ---------------------------------------------------------
				switch ( bNbCall ) {

				case (byte)1:

					if ( Cre_Tin_Prlv_10.bNbInstanceTriggered == (byte)2 ) {				
						reportTestOutcome((byte)1, true) ;
					}
					else {
						reportTestOutcome((byte)1, false) ;
					}

					bNbCall = (byte)2 ;
                    Cre_Tin_Prlv_10.bNbInstanceTriggered = (byte)0 ;
					
					break;

				// ---------------------------------------------------------
				//		 Second call with all applets installed, Execution in 4th place
				// ---------------------------------------------------------
				case (byte)2:

					if ( Cre_Tin_Prlv_10.bNbInstanceTriggered == (byte)4 ) {
						reportTestOutcome((byte)2, true) ;
					}
					else {
						reportTestOutcome((byte)2, false) ;
					}
					break;

				default:
					break;
				}

				break;

			default:
				reportTestOutcome ((byte)1, false);
				break;
			}
		}
	}
}



