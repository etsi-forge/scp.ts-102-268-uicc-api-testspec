//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_cmet;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;


/**
 * uicc.toolkit package, Toolkit Registry, changeMenuEntry() test
 * applet 1
 */
public class Api_2_Tkr_Cmet_1 extends TestToolkitApplet {

    private static byte[] MenuInit = {(byte)'I', (byte)'n', (byte)'i', (byte)'t', (byte)'1'};

    private static byte[] Init = {(byte)'I', (byte)'n', (byte)'i', (byte)'t'};

    private static byte[] MenuAllBuffer =   {(byte)'U', (byte)'s', (byte)'e', (byte)'A', (byte)'l',
                                             (byte)'l', (byte)'B', (byte)'u', (byte)'f', (byte)'f',
                                             (byte)'e', (byte)'r'    };

    private static byte[] MenuPartBuffer =  {(byte)'U', (byte)'s', (byte)'e', (byte)'P', (byte)'a',
                                             (byte)'r', (byte)'t', (byte)'O', (byte)'f', (byte)'B',
                                             (byte)'u', (byte)'f', (byte)'f', (byte)'e', (byte)'r'};

    private static byte[] LengthEquals0 =   {(byte)'L', (byte)'e', (byte)'n', (byte)'g', (byte)'t',
                                             (byte)'h', (byte)'E', (byte)'q', (byte)'u', (byte)'a',
                                             (byte)'l', (byte)'s', (byte)'0'};

    private static byte[] NextActionIndic = {(byte)'N', (byte)'e', (byte)'x', (byte)'t', (byte)'A',
                                             (byte)'c', (byte)'t', (byte)'i', (byte)'o', (byte)'n',
                                             (byte)'I', (byte)'n', (byte)'d', (byte)'i', (byte)'c'};

    private static byte[] HelpSupported =   {(byte)'H', (byte)'e', (byte)'l', (byte)'p', (byte)'S',
                                             (byte)'u', (byte)'p', (byte)'p', (byte)'o', (byte)'r',
                                             (byte)'t', (byte)'e', (byte)'d'};

    private static byte[] IconQualifier =   {(byte)'I', (byte)'c', (byte)'o', (byte)'n', (byte)'Q',
                                             (byte)'u', (byte)'a', (byte)'l', (byte)'i', (byte)'f',
                                             (byte)'i', (byte)'e', (byte)'r'};

    private static byte[] EnableEntry =     {(byte)'E', (byte)'n', (byte)'a', (byte)'b', (byte)'l',
                                             (byte)'e', (byte)'E', (byte)'n', (byte)'t', (byte)'r',
                                             (byte)'y'};

    private static byte[] Violation =       {(byte)'V', (byte)'i', (byte)'o', (byte)'l', (byte)'a',
                                             (byte)'t', (byte)'i',(byte)'o', (byte)'n'};

    private static byte[] WarningViolation = {(byte)'W', (byte)'a', (byte)'r', (byte)'n', (byte)'i',
                                              (byte)'n', (byte)'g', (byte)'V', (byte)'i', (byte)'o',
                                              (byte)'l', (byte)'a', (byte)'t', (byte)'i', (byte)'o',
                                              (byte)'n'};

    private byte resultat = (byte)0;

    private short CallNB = 0;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Cmet_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Cmet_1 thisApplet = new Api_2_Tkr_Cmet_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();  
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE and EVENT_MENU_SELECTION
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        thisApplet.resultat = thisApplet.obReg.initMenuEntry(MenuInit, (short)0, (short)5, (byte)0, false, 
                                                             (byte)0, (short)0);
        MenuInit [4] = (byte)'2';
        thisApplet.resultat = thisApplet.obReg.initMenuEntry(MenuInit, (short)0, (short)5, (byte)0, false, 
                                                             (byte)0, (short)0);
        
    }

    /**
     *  Method called by the CAT RE
     */
    public void processToolkit(short event) {
        
        EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
        
        // Result of tests
        boolean bRes ;

        // Number of tests
        byte testCaseNb = (byte)0x14;
        
        byte TLV; 
        byte ItemID;

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            CallNB++;
            if ( CallNB < 11) testCaseNb = (byte)CallNB;
            bRes = false;

            switch (CallNB) {

            case 1 :
                // -----------------------------------------------------------------
                // Test Case 1 :    Applet changes the entry's title by menuEntry buffer
                //                    1-    changeMenuEntry()with parameters: 
                //                        Id = 02
                //                        MenuEntry = "UseAllBuffer"
                //                        Offset = 0
                //                        Length = menuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                try {
                    obReg.changeMenuEntry((byte)2, MenuAllBuffer, (short)0, (short)MenuAllBuffer.length, 
                                          (byte)0, false, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }

                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                      !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
                
                break;
                
            case 2 :
                // -----------------------------------------------------------------
                // Test Case 2 :    Changing the title with part of menuEntry buffer
                //                    1-    changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "UsePartOfBuffer"
                //                        Offset = 3
                //                        Length = menuEntry.length - 3
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                try {
                    obReg.changeMenuEntry((byte)1, MenuPartBuffer, (short)3, (short)(MenuPartBuffer.length - 3), 
                                          (byte)0, false, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }

                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
                break;

            case 3 :
                // -----------------------------------------------------------------
                // Test Case 3 :    Length = 0
                //                    1-    changeMenuEntry()with parameters: 
                //                        Id = 01/02
                //                        MenuEntry = "LengthEquals0"
                //                        Offset = 0
                //                        Length = 0
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                try {
                    obReg.changeMenuEntry((byte)1, LengthEquals0, (short)0, (short)0, 
                                          (byte)0, false, (byte)0, (short)0);

                    obReg.changeMenuEntry((byte)2, LengthEquals0, (short)0, (short)0, 
                                          (byte)0, false, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }
                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
                break;

            case 4 :
                // -----------------------------------------------------------------
                // Test Case 4 :    Setting a next action indicator != 0
                //                    1-    changeMenuEntry()with parameters: 
                //                        Id = 02
                //                        MenuEntry = "NextActionIndic"
                //                        Offset = 0
                //                        Length = menuEntry.length
                //                        NextAction = 10 (SETUP CALL)
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                //                    4-    changeMenuEntry()with parameters: 
                //                        Id = 02
                //                        MenuEntry = "NextActionIndic"
                //                        Offset = 0
                //                        Length = menuEntry.length
                //                        NextAction = 10 (SETUP CALL)
                //                        HelpSupported = true
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                // -----------------------------------------------------------------
                try {
                    obReg.changeMenuEntry((byte)2, NextActionIndic, (short)0, (short)NextActionIndic.length, 
                                          (byte)16, false, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }
                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

                try {
                    obReg.changeMenuEntry((byte)2, NextActionIndic, (short)0, (short)NextActionIndic.length, 
                                          (byte)16, true, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }
                break;

            case 6 :

                // -----------------------------------------------------------------
                // Test Case 6 :    help supported=true
                //                    1-    changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "HelpSupported"
                //                        Offset = 0
                //                        Length = menuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = true
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                try {
                    obReg.changeMenuEntry((byte)1, HelpSupported, (short)0, (short)HelpSupported.length, 
                                          (byte)0, true, (byte)0, (short)0);

                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }
                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
                break;

            case 8 :
                // -----------------------------------------------------------------
                // Test Case 8 :    Setting  icons 
                //                    1-    changeMenuEntry()with parameters: 
                //                        Id = 01/02
                //                        MenuEntry = "IconQualifier"
                //                        Offset = 0
                //                        Length = menuEntry.length
                //                        NextAction = 10 (SETUP CALL)
                //                        HelpSupported = false
                //                        IconQualifier = 01
                //                        IconIdentifier = 01/02
                //                        No exception shall be thrown
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                try {
                    obReg.changeMenuEntry((byte)1, IconQualifier, (short)0, (short)IconQualifier.length, 
                                          (byte)0, false, (byte)1, (short)1);

                    obReg.changeMenuEntry((byte)2, IconQualifier, (short)0, (short)IconQualifier.length, 
                                          (byte)0, false, (byte)1, (short)2);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }
                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);
                break;

            case 9 :
                // -----------------------------------------------------------------
                // Test Case 9 :    MenuEntry is disabled
                //                    1-    disableEntry(01)
                //                        No exception shall be thrown
                //                    2-    changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "EnableEntry"
                //                        Offset = 0
                //                        Length = menuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        No exception shall be thrown
                //                    3-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    4-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                try {
                    obReg.disableMenuEntry((byte)1);
                    bRes = true;
                }
                catch (Exception e) {
                        bRes = false;
                        break;
                }
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, EnableEntry, (short)0, (short)EnableEntry.length, 
                                          (byte)0, false, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }

                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

                reportTestOutcome(testCaseNb, bRes) ;

                break;
    
            case 10 :
                // -----------------------------------------------------------------
                // Test Case 10 :    MenuEntry is null 
                //                    Shall throw java.lang.NullPointerException
                // -----------------------------------------------------------------
                testCaseNb = (byte)10;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, null, (short)0, (short)1, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.NullPointerException e) {
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 11:    Offset causes access outside array bounds 
                //                        changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "Violation"
                //                        Offset = menuEntry.length
                //                        Length = 1
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        java.lang.ArrayIndexOutOfBoundsException
                // -----------------------------------------------------------------
                testCaseNb = (byte)11;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, Violation, (short)Violation.length, (short)1, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 12:    Big Offset causes access outside array bounds 
                //                        changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "Violation"
                //                        Offset = 255
                //                        Length = 1
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        java.lang.ArrayIndexOutOfBoundsException
                // -----------------------------------------------------------------
                testCaseNb = (byte)12;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, Violation, (short)255, (short)1, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                
                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 13:    Offset < 0 causes access outside array bounds  
                //                        changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "Violation"
                //                        Offset = -1
                //                        Length = 1
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        java.lang.ArrayIndexOutOfBoundsException
                // -----------------------------------------------------------------
                testCaseNb = (byte)13;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, Violation, (short)-1, (short)1, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;
                
                // -----------------------------------------------------------------
                // Test Case 14:    Length causes access outside array bounds  
                //                        changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "Violation"
                //                        Offset = 0
                //                        Length = MenuEntry.length + 1
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        java.lang.ArrayIndexOutOfBoundsException
                // -----------------------------------------------------------------
                testCaseNb = (byte)14;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, Violation, (short)0, (short)(Violation.length + 1), 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 15:    Length < 0 causes access outside array bounds 
                //                        changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "Violation"
                //                        Offset = 0
                //                        Length = -1
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        java.lang.ArrayIndexOutOfBoundsException
                // -----------------------------------------------------------------
                testCaseNb = (byte)15;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, Violation, (short)0, (short)-1, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 16:    Both offset and length causes access 
                //                    outside array bounds  
                //                        changeMenuEntry()with parameters: 
                //                        Id = 01
                //                        MenuEntry = "Violation"
                //                        Offset = 4
                //                        Length = MenuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        java.lang.ArrayIndexOutOfBoundsException
                // -----------------------------------------------------------------
                testCaseNb = (byte)16;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)1, Violation, (short)4, (short)Violation.length, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 17:    Invalid ID used
                //                        changeMenuEntry()with parameters: 
                //                        Id = 00
                //                        MenuEntry = "Violation"
                //                        Offset = 0
                //                        Length = MenuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        MENU_ENTRY_NOT_FOUND ToolKit Exception
                // -----------------------------------------------------------------
                testCaseNb = (byte)17;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)0, Violation, (short)0, (short)Violation.length, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.MENU_ENTRY_NOT_FOUND);
                }
                catch (Exception e) {
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 18:    ID isn't allocated to a menu entry 
                //                    of this applet instance
                //                        changeMenuEntry()with parameters: 
                //                        Id = 0A
                //                        MenuEntry = "Violation"
                //                        Offset = 0
                //                        Length = MenuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        MENU_ENTRY_NOT_FOUND ToolKit Exception
                // -----------------------------------------------------------------
                testCaseNb = (byte)18;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)10, Violation, (short)0, (short)Violation.length, 
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.MENU_ENTRY_NOT_FOUND);
                }
                catch (Exception e) {
                    bRes = false;
                }

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 19:    The text is bigger than the allocated space
                //                        changeMenuEntry()with parameters: 
                //                        Id = 02 
                //                        MenuEntry = "WarningViolation"
                //                        Offset = 0
                //                        Length = MenuEntry.length + 10
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                        ALLOWED_LENGTH_EXCEEDED ToolKit Exception
                // -----------------------------------------------------------------
                testCaseNb = (byte)19;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)2, WarningViolation, (short)0, (short)(WarningViolation.length ),
                                          (byte)0, false, (byte)0, (short)0);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.ALLOWED_LENGTH_EXCEEDED);
                }
                catch (Exception e) {
                    bRes = false;
                }

            case 11 :

                reportTestOutcome(testCaseNb, bRes) ;

                // -----------------------------------------------------------------
                // Test Case 20:        Applet changes the entry's title by menuEntry 
                //                        buffer, with a smaller length 
                //                        than the initial length
                //                    1-    Id = 02 
                //                        MenuEntry = "Init"
                //                        Offset = 0
                //                        Length = MenuEntry.length
                //                        NextAction = 0
                //                        HelpSupported = false
                //                        IconQualifier = 0
                //                        IconIdentifier = 0
                //                        Shall throw a 
                //                    2-    isEventSet(EVENT_MENU_SELECTION)
                //                        shall return true
                //                    3-    isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
                //                        shall return false
                // -----------------------------------------------------------------
                testCaseNb = (byte)20;
                bRes = false;

                try {
                    obReg.changeMenuEntry((byte)2, Init, (short)0, (short)(Init.length), 
                                          (byte)0, false, (byte)0, (short)0);
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                    break;
                }

                bRes = bRes && obReg.isEventSet(EVENT_MENU_SELECTION) &&
                        !obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

                break;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            if ((CallNB == 4) || (CallNB == 6)) {
                CallNB++;
            }
        }
        else {
            // -----------------------------------------------------------------
            // Test Case 5 :    Checking applet isn't triggered by a 
            //                    MENU_SELECTION_HELP_REQUEST 
            // -----------------------------------------------------------------
            // -----------------------------------------------------------------
            // Test Case 7 :    Checking applet is triggered by a 
            //                    MENU_SELECTION_HELP_REQUEST 
            // -----------------------------------------------------------------
            if (event == EVENT_MENU_SELECTION_HELP_REQUEST) {
                if (CallNB == 5){
                    if (envHdlr.getItemIdentifier() == (byte)0x02) 
                        reportTestOutcome((byte)CallNB, true);
                }
                if (CallNB == 7){
                    if (envHdlr.getItemIdentifier() == (byte)0x01) {
                        reportTestOutcome((byte)CallNB, true);
                    }
                }
            }
        }
    }
}
