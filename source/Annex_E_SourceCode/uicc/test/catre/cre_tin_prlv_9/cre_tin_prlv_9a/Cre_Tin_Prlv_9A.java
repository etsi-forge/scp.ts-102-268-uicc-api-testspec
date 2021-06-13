//-----------------------------------------------------------------------------
//    Cre_Tin_Prlv_9A.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_prlv_9.cre_tin_prlv_9a;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import uicc.test.util.* ;
import uicc.test.catre.cre_tin_prlv_9.Cre_Tin_Prlv_9 ;

  

public class Cre_Tin_Prlv_9A extends TestToolkitApplet
{

    /**
     * Constructor of the applet
     */
    public Cre_Tin_Prlv_9A() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Cre_Tin_Prlv_9A thisApplet = new Cre_Tin_Prlv_9A();

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
			
            Cre_Tin_Prlv_9.bNbInstanceTriggered ++ ;

		// -----------------------------------------------------------------
		//		 Instance 1 execution, Priority Level 1, Execution in 2nd place
		// -----------------------------------------------------------------

			if ( Cre_Tin_Prlv_9.bNbInstanceTriggered == (byte)2 ) {
				reportTestOutcome((byte)1, true) ;
			}
			else {
				reportTestOutcome((byte)1, false) ;
			}
		}
	}
}



