//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_dmet;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, disableMenuEntry() method
 * applet 1
 */
public class Api_2_Tkr_Dmet_1 extends TestToolkitApplet {
       
    private static byte[] MenuInit = {(byte)'I', (byte)'n', (byte)'i', (byte)'t'};
    private static final boolean HELP_REQUESTED = true;
    private static final boolean HELP_NOT_REQUESTED = false;

    boolean bRes = false;
    byte testCaseNb;

    private byte callNb = (byte) 0;
    private byte resultat = (byte) 0;
 
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Dmet_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Dmet_1 thisApplet = new Api_2_Tkr_Dmet_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE and EVENT_MENU_SELECTION
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        thisApplet.obReg.initMenuEntry(MenuInit, (short)0, (short)4, (byte)0, 
                                       HELP_NOT_REQUESTED, (byte)0, (short)0);
        //MenuInit [3] = (byte)'2';
        thisApplet.obReg.initMenuEntry(MenuInit, (short)0, (short)4, (byte)0, 
                                       HELP_NOT_REQUESTED, (byte)0, (short)0);

    }

    /**
     *  method called by the CAT RE at the installation of the applet
     */
    public void processToolkit(short event) {

        // Increasing the number of triggerings
        ++callNb;

        switch(callNb) {
        case (byte)1 :

            short i;
            // -----------------------------------------------------------------
            // Test Case 1 : Check the menu state before disabling a previously
            //                 enabled entry not registered to EVENT_MENU_SELECTION_HELP_REQUEST.
            // -----------------------------------------------------------------
            testCaseNb = (byte)1;

            bRes = obReg.isEventSet(EVENT_MENU_SELECTION) &&
                  !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

            reportTestOutcome(testCaseNb, bRes) ;
            break;

        case (byte)2 :
            // -----------------------------------------------------------------
            // Test case 2 : Check the menu state after disabling a previously 
            //               enabled entry not registered to EVENT_MENU_SELECTION_HELP_REQUEST 
            // -----------------------------------------------------------------
            testCaseNb = (byte)2;
            bRes = false;

            try {
                obReg.disableMenuEntry((byte)0x01);
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

        case (byte)3:

            // -----------------------------------------------------------------
            // Test Case 3 : Check the menu before disabling a previously enabled
            //               entry registered to EVENT_MENU_SELECTION_HELP_REQUEST
            // -----------------------------------------------------------------
            testCaseNb = (byte)3;
            bRes = false;
            
            try {
                obReg.changeMenuEntry((byte)0x02, MenuInit, (short)0, (short)4,
                                      (byte)0, HELP_REQUESTED, (byte)0, (short)0);
                bRes = true;
            }
            catch (Exception e) { 
                bRes = false; 
            }

            bRes = bRes &&
                   obReg.isEventSet(EVENT_MENU_SELECTION) &&
                   obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

            reportTestOutcome(testCaseNb, bRes) ;
            break;

        case (byte)4 :
            // -----------------------------------------------------------------
            // Test case 4 : Check the menu after disabling a previously enabled 
            //               entry registered to EVENT_MENU_SELECTION_HELP_REQUEST
            // -----------------------------------------------------------------
            testCaseNb = (byte)4;
            bRes = false;

            try {
                obReg.disableMenuEntry((byte)02);
                bRes = true;
            }
            catch (Exception e) { 
                bRes = false; 
            }

            bRes = bRes &&
                   obReg.isEventSet(EVENT_MENU_SELECTION) &&
                   obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
                                
            reportTestOutcome(testCaseNb, bRes) ;
            break;

        case (byte) 5 :
            // -----------------------------------------------------------------
            // Test Case 5 : Disabling invalid entries
            // -----------------------------------------------------------------
            testCaseNb = (byte)5;
            bRes = false;

            try {
                obReg.disableMenuEntry((byte)0);
            }
            catch (ToolkitException e) {
                bRes = (e.getReason() == ToolkitException.MENU_ENTRY_NOT_FOUND);
            }
            catch (Exception e) { bRes = false; }

            if (bRes) {
                for (i = 3; (i<256) && bRes;i++){
                    bRes = false;
                    try{
                        obReg.disableMenuEntry((byte)i);
                    }
                    catch (ToolkitException e) {
                        bRes = (e.getReason() == ToolkitException.MENU_ENTRY_NOT_FOUND);
                    }
                    catch (Exception e) {
                        bRes = false;
                    }
                }
            }
            reportTestOutcome(testCaseNb, bRes) ;
        }
    }
}
