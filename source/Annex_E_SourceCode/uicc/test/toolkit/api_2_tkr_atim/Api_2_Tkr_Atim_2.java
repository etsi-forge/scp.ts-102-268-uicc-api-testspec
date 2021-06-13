//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_atim;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.system.*;
import javacard.framework.*;
import uicc.test.util.*;


/**
 * uicc.toolkit package, ToolkitRegistry interface, allocateTimer() method
 * applet 2
 */
public class Api_2_Tkr_Atim_2 extends TestToolkitApplet {

    private byte testCaseNb;
    private static boolean bRes;

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'2'};

	//Timer ID value range
	private	static short MIN_TIMER_ID = 1;
	private	static short MAX_TIMER_ID = 8;

	// Timer ID array : initially all at '0' which is not a correct Timer ID
	short[] Timers = {(short)0, (short)0, (short)0, (short)0};

    // Constructor of the applet
    public Api_2_Tkr_Atim_2() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Atim_2 thisApplet = new Api_2_Tkr_Atim_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_MENU_SELECTION
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,
                                       (short) MenuInit.length, (byte) 0, false,
                                       (byte) 0, (short) 0);
    }


    /**
    * Method called by the processToolkit() method to know if short <temp>
    * has alread been added to an array <Array>
    */
    private boolean isInArray(short temp, short[] Array) {
        boolean isInArray = false;

        for(short i = ((short) (Array.length-1)); ((i > (short) -1) && (!isInArray));i--) {
            isInArray = (Array[i] == temp);
        }
        return isInArray;
    }

    /**
    * Method called by the processToolkit() method to know if an ID
    * belongs to the timer ID interval
    */
    private boolean isTimerID(short ID) {
        boolean isTimerID;

        isTimerID = (ID>(short)(MIN_TIMER_ID-1)) && (ID<(short)(MAX_TIMER_ID+1));
        return isTimerID;
    }

    /**
    * Method called by the CAT RE
    */
    public void processToolkit(short event) {
        
        short temp = 0;
        short i;
        // -----------------------------------------------------------------
        // Test Case 4:     4 * allocateTimer()
        //                  No exception shall be thrown.
        //                  Timer ID returned shall be between 
        //                  0x01 and 0x08 inclusive. 
        //                  It shall be different after each call.
        // -----------------------------------------------------------------
        testCaseNb = (byte) 1;
        bRes = true;

        try {
            for (i=(short)0; (i<(short)4) && (bRes); i++) {
                temp = obReg.allocateTimer();
                bRes = (isTimerID(temp)) && (!this.isInArray(temp,Timers));
                Timers[i] = temp;
            }
        }
        catch (Exception e) {    
           bRes = false;
        }

        for (i=(short)0; (i<(short)4) && (bRes); i++) {
            if (isTimerID(Timers[i])) {
                try {
                    obReg.releaseTimer((byte) Timers[i]);
                }
                catch (Exception e) {
                }
            }
        }
        reportTestOutcome(testCaseNb, bRes);
    }
}
