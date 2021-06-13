//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rtim;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, releaseTimer() method
 * applet 1
 */
public class Api_2_Tkr_Rtim_1 extends TestToolkitApplet { 
    
    // Timer ID value range
    private static short MIN_TIMER_ID = 1;
    private static short MAX_TIMER_ID = 8;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Rtim_1() { 
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {
        // Create a new applet instance
        Api_2_Tkr_Rtim_1 thisApplet = new Api_2_Tkr_Rtim_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }
    

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) { 
        
        boolean bRes ;
        byte testCaseNb;
        

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            // -----------------------------------------------------------------
            // Test Case 1: Release not allocated timers
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;
            bRes = false;
        
            try {
                obReg.releaseTimer((byte)0);
            }
            catch (ToolkitException e) {
                    bRes = (e.getReason()==ToolkitException.INVALID_TIMER_ID);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                for (short i = ((short)1); (i < ((short)256)) && (bRes);i++) {
                    bRes = false;
                    obReg.releaseTimer((byte)i);
                }
            }
            catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.INVALID_TIMER_ID);
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 2: Release allocated timers
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;

            try {
                for (short i = MIN_TIMER_ID; (i < (short) (MAX_TIMER_ID + 1)); i++){
                    obReg.allocateTimer();
                }
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                obReg.releaseTimer((byte)MIN_TIMER_ID);
                bRes = bRes && obReg.isEventSet(EVENT_TIMER_EXPIRATION);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                for (short i = (short)(MIN_TIMER_ID + 1); 
                    (i < MAX_TIMER_ID ) && (bRes);i++) {
                    obReg.releaseTimer((byte) i);

                    bRes = obReg.isEventSet(EVENT_TIMER_EXPIRATION);
                }
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 3: Release invalid timer ID
            // -----------------------------------------------------------------

            testCaseNb = (byte)3;
            bRes = false;
            
            try {
                obReg.releaseTimer((byte)255);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.INVALID_TIMER_ID);    
            }
            catch (Exception e) {
                bRes = false;
            }
            bRes = bRes && obReg.isEventSet(EVENT_TIMER_EXPIRATION);
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 4: Release last timer
            // -----------------------------------------------------------------
            testCaseNb = (byte)4;
            bRes = false;
            
            try {
                obReg.releaseTimer((byte)MAX_TIMER_ID);
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;    
            }
            bRes = bRes && !obReg.isEventSet(EVENT_TIMER_EXPIRATION);
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 5: Check we can allocate timers
            // -----------------------------------------------------------------
            testCaseNb = (byte)5;
            bRes = false;
    
            try {
                for (short i = MIN_TIMER_ID; (i < (short)(MAX_TIMER_ID + 1));i++) {
                    obReg.allocateTimer();
                }
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 6: Release all timers
            // -----------------------------------------------------------------
            testCaseNb = (byte)6;
            bRes = false;
    
            try {
                for (short i = MIN_TIMER_ID; (i < (short)(MAX_TIMER_ID+1));i++) {
                    obReg.releaseTimer((byte)i);
                }
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes);

            // -----------------------------------------------------------------
            // Test Case 7: Initialiation of the test case
            // -----------------------------------------------------------------
            testCaseNb = (byte)7;
            bRes = true;
            reportTestOutcome(testCaseNb, bRes);
        }
        else {
            if (event == EVENT_TIMER_EXPIRATION) {

                // -----------------------------------------------------------------
                // Test Case 7: Check applet is not triggered 
                //              by ENVELOPE(TIMER_EXPIRATION) command any more
                // -----------------------------------------------------------------
                testCaseNb = (byte)7;    
                reportTestOutcome(testCaseNb, false);
            }
        }
    } 
}
