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
 * applet 1
 */
public class Api_2_Tkr_Atim_1 extends TestToolkitApplet {

    private byte testCaseNb;
    private static boolean bRes;

    private static byte[] MenuInit = {(byte)'A',(byte)'p',(byte)'p',(byte)'l',(byte)'e', (byte)'t',(byte)'1'};
    
    // Timer ID value range
    private static short MIN_TIMER_ID = 1;
    private static short MAX_TIMER_ID = 8;

    // Timer expirations counter
    short j = MAX_TIMER_ID;

    // Timer ID array : initially all at '0' which is not a correct Timer ID
    short[] Timers = {(short)0,(short)0,(short)0,(short)0,(short)0,
                      (short)0,(short)0,(short)0};

    // Constructor of the applet
    public Api_2_Tkr_Atim_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Api_2_Tkr_Atim_1 thisApplet = new Api_2_Tkr_Atim_1();

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
    * has already been added to array <Array>
    */
    private boolean isInArray(short temp, short[] Array) {
        boolean isInArray = false;

        for(short i = ((short) (Array.length-1)); ((i > (short) -1) && (!isInArray)); i--) {
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
     *  Method called by the CAT RE
     */
    public void processToolkit(short event) {
        
        short temp = 0;
        short i;

        byte TLV;
        byte TimerID;


        if (event == EVENT_MENU_SELECTION) {
            // -----------------------------------------------------------------
            // Test Case 1: 8 * allocateTimer()
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;
            bRes = true;

            try {
                for (i=(short)0; (i<(short)8) && (bRes);i++) {
                    temp = obReg.allocateTimer();
                    bRes = (isTimerID(temp)) && (!this.isInArray(temp,Timers));
                    Timers[i] = temp;
                }
            }
            catch (Exception e) {    
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 2: Max timer rushed
            //              The applet allocates 1 more timer
            //              Shall throw a ToolkitException 
            //              with reason NO_TIMER_AVAILABLE
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;

            try {    
                temp = obReg.allocateTimer();
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.NO_TIMER_AVAILABLE);
            }
            catch (Exception e) {
                bRes = false ;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 3: Initialiation of the test case.
            //              shall be set at true only if the applet is triggered
            //              by an 8 EVENT_TIMER_EXPIRATION
            // -----------------------------------------------------------------
            testCaseNb = (byte)3;
            bRes = true;
        }

        // -----------------------------------------------------------------
        // Test Case 3: Call releaseTimer(id) each time a timer expires
        // -----------------------------------------------------------------
        if (event == EVENT_TIMER_EXPIRATION) {
            // Get the system instance of the EnvelopeHandler class
            EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
             
            // find timer ID which has expired
            TLV = envHdlr.findTLV(TAG_TIMER_IDENTIFIER, (byte)1);
            if(TLV != TLV_NOT_FOUND){
                // get the timer ID
                TimerID = envHdlr.getValueByte((short)0);
                
                try {
                    obReg.releaseTimer((byte) TimerID);
                }
                catch (Exception e) {
                    bRes = false;
                }

                if (--j==0) {
                    reportTestOutcome(testCaseNb, bRes);
                }
            } else {
                reportTestOutcome(testCaseNb, false);
            }
        }
    }
}
