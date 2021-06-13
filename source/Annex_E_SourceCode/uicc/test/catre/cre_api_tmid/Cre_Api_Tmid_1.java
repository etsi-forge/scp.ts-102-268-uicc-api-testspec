//-----------------------------------------------------------------------------
//    Cre_Api_Tmid_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_api_tmid;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * Cat Runtime Environment, Other parts transferred from API, Timer Identifier test
 * applet 1
 */
public class Cre_Api_Tmid_1 extends TestToolkitApplet {

	static byte instanceNumber = (byte)0x00;
	static byte firstInstanceTimerId = (byte)0x00;
    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'1'};
	
    // Number of tests
    byte testCaseNb = (byte)0x00;
        
	/**
     * Constructor of the applet
     */
    public Cre_Api_Tmid_1() {
	}


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Cre_Api_Tmid_1 thisApplet = new Cre_Api_Tmid_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short)0, (short)MenuInit.length, (byte)0, 
                                       false, (byte)0, (short)0);
		
		// Allocate a timer
		byte timerId = thisApplet.obReg.allocateTimer();

        if (instanceNumber == 0) {
			firstInstanceTimerId = timerId;
		}
		instanceNumber++;
    }
    

    /**
     *  method called by the CAT RE
     */
    public void processToolkit(short event) {
        // Result of tests
        boolean bRes = false ;

		// --------------------------------------------
		// Test Case 1 : Verify the impossibility of releasing another applet timer
		testCaseNb = (byte)1;
		
		bRes = false;


		try {
		    obReg.releaseTimer(firstInstanceTimerId);
		}
		catch (ToolkitException e)    {
		    // Check the exception is the good one and the timer has been allocated
		    if ((e.getReason() == ToolkitException.INVALID_TIMER_ID)
		        && (firstInstanceTimerId != 0))
		       bRes = true;
		}
		catch (Exception e) {
		}
		reportTestOutcome(testCaseNb, bRes);
	}
}
