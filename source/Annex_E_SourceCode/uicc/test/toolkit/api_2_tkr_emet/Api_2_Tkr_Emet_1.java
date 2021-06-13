//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_emet;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, enableMenuEntry() method
 * applet 1
 */
public class Api_2_Tkr_Emet_1 extends TestToolkitApplet {

    boolean bRes = false;
    byte testCaseNb = (byte) 5;

    private static byte [] MenuInit = {(byte) 'I', (byte) 'n', (byte) 'i', (byte) 't'};
    private byte callNb = (byte) 0;
    private byte resultat = (byte) 0;        

    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Emet_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Emet_1 thisApplet = new Api_2_Tkr_Emet_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE and EVENT_MENU_SELECTION
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short)4, (byte) 0, false, 
                                       (byte) 0, (short) 0);

        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short)4, (byte) 0, false, 
                                       (byte) 0, (short) 0);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        short i;

        // Increasing the trigger counter.
        ++callNb;

        switch(callNb) {
        case (byte)1:
            // -----------------------------------------------------------------
            // Test Case 1 : Check menu state before enabling a previously disabled 
            //               entry not registered to EVENT_MENU_SELECTION_HELP_REQUEST
            // -----------------------------------------------------------------

            testCaseNb = (byte)1;
            bRes = false;

            bRes = obReg.isEventSet(EVENT_MENU_SELECTION) &&
                  !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

            if (bRes) {
                bRes = false;
                try {
                    obReg.disableMenuEntry((byte)0x01);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
            }

            reportTestOutcome(testCaseNb, bRes);
            break;

        case (byte)2:
            // -----------------------------------------------------------------
            // Test case 2 : Check menu state after enabling a previously disabled
            //               entry not registered to EVENT_MENU_SELECTION_HELP_REQUEST
            // -----------------------------------------------------------------

            testCaseNb = (byte)2;
            bRes = false;


            try {
                obReg.enableMenuEntry((byte)0x01);
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }

            bRes = bRes &&
                   obReg.isEventSet(EVENT_MENU_SELECTION) &&
                  !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

            reportTestOutcome(testCaseNb, bRes);
            break;

        case (byte) 3 :
            // -----------------------------------------------------------------
            // Test Case 3 : Check menu state before enabling a previously enabled
            //               entry registered to EVENT_MENU_SELECTION_HELP_REQUEST
            // -----------------------------------------------------------------

            testCaseNb = (byte)3;
            bRes = false;

            try {
                obReg.changeMenuEntry((byte)0x02, MenuInit, (short)0, (short)4,
                                      (byte)0, true, (byte)0, (short)0);
                bRes = true;
            }
            catch(Exception e) {
                bRes = false;
            }

            bRes = bRes &&
                   obReg.isEventSet(EVENT_MENU_SELECTION) &&
                   obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

            if (bRes) {
                bRes = false;
                try {
                    obReg.disableMenuEntry((byte)0x02);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
            }

            reportTestOutcome(testCaseNb, bRes);
            break;

        case (byte)4:
            // -----------------------------------------------------------------
            //    Test case 4 : Check menu state after enabling a previously enabled
            //                  entry registered to EVENT_MENU_SELECTION_HELP_REQUEST
            // -----------------------------------------------------------------

            testCaseNb = (byte)4;
            bRes = false;

            try {
                obReg.enableMenuEntry((byte)0x02);
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }

            bRes = bRes &&
                   obReg.isEventSet(EVENT_MENU_SELECTION) &&
                   obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

            reportTestOutcome(testCaseNb, bRes);
            break;

        case (byte)5:
            // -----------------------------------------------------------------
            // Test Case 5 : Enabling invalid entries
            // -----------------------------------------------------------------

            testCaseNb = (byte)5;
            bRes = false;

            try { 
                obReg.enableMenuEntry((byte)0);
            }            
            catch (ToolkitException e) {
                bRes = (e.getReason()==ToolkitException.MENU_ENTRY_NOT_FOUND);
            }            
            catch (Exception e) {
                bRes = false;
            }

            if (bRes) {
                for (i = 3; (i<256) && bRes;i++) {
                    bRes = false;
                    try {
                        obReg.enableMenuEntry((byte)i);
                    }
                    catch (ToolkitException e) {
                        bRes = (e.getReason() == ToolkitException.MENU_ENTRY_NOT_FOUND);
                    }
                    catch (Exception e) {
                        bRes = false;
                    }
                }
            }
            reportTestOutcome(testCaseNb, bRes);
        }
    }
}
