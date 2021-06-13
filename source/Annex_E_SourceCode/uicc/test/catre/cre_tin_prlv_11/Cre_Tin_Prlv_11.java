//-----------------------------------------------------------------------------
//    Cre_Tin_Prlv_11.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_prlv_11;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;



public class Cre_Tin_Prlv_11 extends TestToolkitApplet
{

	private static byte bNbInstance = (byte)0 ;			 // instance number
	private static byte bNbInstanceTriggered = (byte)0 ; // number of times the applet is triggered
	private static byte bNbCall = (byte)1 ;				 // number of triggering calls of the applet
	private byte		bMyInstanceId ;

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Prlv_11() 
    {
		Cre_Tin_Prlv_11.bNbInstance ++;
		this.bMyInstanceId = bNbInstance;
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Cre_Tin_Prlv_11 thisApplet = new Cre_Tin_Prlv_11();
 
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

				// ---------------------------------------------------------
				//		For the 1st call, this instance is triggered 4th
				// ---------------------------------------------------------
				if ( (bNbInstanceTriggered == (byte)4) && (bNbCall == (byte)1) ) {
					reportTestOutcome(bNbCall, true) ;
				}
				else {

					// ---------------------------------------------------------
					//		For the 2nd call, this instance is triggered 3th
					// ---------------------------------------------------------
					if ( (bNbInstanceTriggered == (byte)3) && (bNbCall == (byte)2) ) {
						reportTestOutcome(bNbCall, true) ;
					}
					else {				

						// ---------------------------------------------------------
						//		For the 3rd call, this instance is triggered 2nd
						// ---------------------------------------------------------
						if ( (bNbInstanceTriggered == (byte)2) && (bNbCall == (byte)3) ) {
							reportTestOutcome(bNbCall, true) ;
						}
						else {
							reportTestOutcome(bNbCall, false) ;
						}
					}
				}

				bNbCall ++ ;
				bNbInstanceTriggered = (byte)0 ;

				break;

		// -----------------------------------------------------------------
		//		 Instance 2 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)2:

				// ---------------------------------------------------------
				//		For the 1st call, this instance is triggered 3rd
				// ---------------------------------------------------------
				if ( (bNbInstanceTriggered == (byte)3) && (bNbCall == (byte)1) ) {
					reportTestOutcome(bNbCall, true) ;
				}
				else {
					
					// ---------------------------------------------------------
					//		For the 2nd call, this instance is triggered 2nd
					// ---------------------------------------------------------
					if ( (bNbInstanceTriggered == (byte)2) && (bNbCall == (byte)2) ) {
						reportTestOutcome(bNbCall, true) ;
					}
					else {
						
						// ---------------------------------------------------------
						//		For the 3rd call, this instance is triggered 1st
						// ---------------------------------------------------------
						if ( (bNbInstanceTriggered == (byte)1) && (bNbCall == (byte)3) ) {
							reportTestOutcome(bNbCall, true) ;
						}
						else {
							reportTestOutcome(bNbCall, false) ;
						}
					}
				}
				
				break;

		// -----------------------------------------------------------------
		//		 Instance 3 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)3:
				// ---------------------------------------------------------
				//		For the 1st call, this instance is triggered 2nd
				// ---------------------------------------------------------
				if ( (bNbInstanceTriggered == (byte)2) && (bNbCall == (byte)1) ) {
					reportTestOutcome(bNbCall, true) ;
				}
				else {

					// ---------------------------------------------------------
					//		For the 2nd call, this instance is triggered 1st
					// ---------------------------------------------------------
					if ( (bNbInstanceTriggered == (byte)1) && (bNbCall == (byte)2) ) {
						reportTestOutcome(bNbCall, true) ;
					}
					else {
						reportTestOutcome(bNbCall, false) ;
					}
				}

				break;

		// -----------------------------------------------------------------
		//		 Instance 4 execution, Priority Level 1
		// -----------------------------------------------------------------

			case (byte)4:
				// ---------------------------------------------------------
				//		For the 1st call, this instance is triggered 1st
				// ---------------------------------------------------------
				if ( (bNbInstanceTriggered == (byte)1) && (bNbCall == (byte)1) ) {
					reportTestOutcome(bNbCall, true) ;
				}
				else {
					reportTestOutcome(bNbCall, false) ;
				}

				break;

			default:
				reportTestOutcome ((byte)1, false);
				break;

			}
		}
	}
}



