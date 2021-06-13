//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_imet;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, initMenuEntry() method
 * applet 1
 */
public class Api_2_Tkr_Imet_1 extends TestToolkitApplet {

    private static byte[] MenuEntry = {(byte)'T', (byte)'o', (byte)'o', (byte)'l',
                                       (byte)'k', (byte)'i', (byte)'t', (byte)'T',
                                       (byte)'e', (byte)'s', (byte)'t'};

    private static byte[] MenuEntryImpossible = {(byte)'T', (byte)'o', (byte)'o', (byte)'l',
                                                 (byte)'k', (byte)'i', (byte)'t', (byte)'T',
                                                 (byte)'e', (byte)'s', (byte)'t', (byte)' ',
                                                 (byte)'I', (byte)'m', (byte)'p', (byte)'o',
                                                 (byte)'s', (byte)'s', (byte)'i', (byte)'b',
                                                 (byte)'l', (byte)'e'};

    private byte[] MenuTest = {(byte)'T', (byte)'O', (byte)'O', (byte)'L',
                               (byte)'K', (byte)'I', (byte)'T', (byte)' ',
                               (byte)'T', (byte)'E', (byte)'S', (byte)'T',
                               (byte)' ', (byte)'1'};

   private static byte[] MenuOffset =  {(byte)'1', (byte)'2', (byte)'3', (byte)'4',
                                        (byte)'5', (byte)'6', (byte)'7', (byte)'T',
                                        (byte)'O', (byte)'O', (byte)'L', (byte)'K',
                                        (byte)'I', (byte)'T', (byte)' ', (byte)'T',
                                        (byte)'E', (byte)'S', (byte)'T', (byte)' ',
                                        (byte)'2'};

    private byte result = (byte)0;
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Imet_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        boolean bRes;
        // Number of tests
        byte testCaseNb = (byte)23;

        // Create a new applet instance
        Api_2_Tkr_Imet_1 thisApplet = new Api_2_Tkr_Imet_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

        // -----------------------------------------------------------------
        // Test Case 1: NULL as parameter to menuEntry
        //              Shall throw a NullPointerException
        // -----------------------------------------------------------------
        testCaseNb = (byte)1;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(null, (short)0, (short)0, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.NullPointerException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 2: Offset = menuEntry.length
        //              MenuEntry = "ToolkitTest"
        //              Offset = 12
        //              Length = 0
        //              Shall throw ArrayIndexOutOfBoundsException
        // -----------------------------------------------------------------
        testCaseNb = (byte)2;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)12, (short)0, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 3: Offset < 0
        //              MenuEntry = "ToolkitTest"
        //              Offset = -1
        //              Shall throw ArrayIndexOutOfBoundsException
        // -----------------------------------------------------------------
        testCaseNb = (byte)3;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)-1, (short)0, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 4: Offset = 255
        //              MenuEntry = "ToolkitTest"
        //              Offset = 255
        //              Length = 11
        //              Shall throw ArrayIndexOutOfBoundsException
        // -----------------------------------------------------------------
        testCaseNb = (byte)4;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)255, (short)11, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 5: Length = menuEntry.length+1
        //              MenuEntry = "ToolkitTest"
        //              Offset = 0
        //              Length = 12
        //              Shall throw ArrayIndexOutOfBoundsException
        // -----------------------------------------------------------------
        testCaseNb = (byte)5;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)0, (short)12, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 6: Length < 0
        //              MenuEntry = "ToolkitTest"
        //              Offset = 0
        //              Length = -1
        //              Shall throw ArrayIndexOutOfBoundsException
        // -----------------------------------------------------------------
        testCaseNb = (byte)6;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)0, (short)-1, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 7: Offset + length > menuEntry.length
        //              MenuEntry = "ToolkitTest"
        //              Offset = 11
        //              Length = 1
        //              Shall throw ArrayIndexOutOfBoundsException
        // -----------------------------------------------------------------
        testCaseNb = (byte)7;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)11, (short)1, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false; 
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 8: MenuEntry.length > size allocated at loading for each menu entry
        //              MenuEntry = "ToolkitTest Impossible"
        //              Offset = 0
        //              Length = 16
        //              ALLOWED_LENGTH_EXCEEDED ToolkitException is thrown
        // -----------------------------------------------------------------
        testCaseNb = (byte)8;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntryImpossible, (short)0, (short)16, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (ToolkitException e) {
            bRes = (e.getReason()==ToolkitException.ALLOWED_LENGTH_EXCEEDED);
        }
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 9: Successful call, MenuEntry is the whole buffer
        //              1-MenuEntry = "TOOLKIT TEST 1"
        //              Offset = 0
        //              Length = 14
        //              Shall return ID 01
        //              2-isEventSet(EVENT_MENU_SELECTION)
        //              Shall return true
        // -----------------------------------------------------------------
        testCaseNb = (byte)9;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(thisApplet.MenuTest, (short)0, (short)14, (byte)0, false,
                                                               (byte)0, (short)0);
            bRes = (thisApplet.result == (byte)1);
        }
        catch (Exception e) {
            bRes = false;
        }

        bRes = bRes && thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION);

        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 10: Successful call, MenuEntry part of a buffer
        //               1-MenuEntry = "1234567TOOLKIT TEST 2"
        //               Offset = 7
        //               Length = 14
        //               Shall return ID 02
        //               2-isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
        //               Shall return false
        // -----------------------------------------------------------------
        testCaseNb = (byte)10;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuOffset, (short)7, (short)14, (byte)0, false,
                                                               (byte)0, (short)0);
            bRes = thisApplet.result == (byte)2;
        }
        catch (Exception e) {
            bRes = false;
        }

        bRes = bRes && !thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

        thisApplet.reportTestOutcome(testCaseNb, bRes);


        // -----------------------------------------------------------------
        // Test Case 11: Successful call, MenuEntry with help supported
        //               1-MenuEntry = "TOOLKIT TEST 3"
        //               Offset = 0
        //               Length = 14
        //               Shall return ID 03
        //               2-isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST)
        //               Shall return true
        // -----------------------------------------------------------------
        testCaseNb = (byte)11;
        bRes = false;

        thisApplet.MenuTest[13] = (byte)'3';

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(thisApplet.MenuTest, (short)0, (short)14, (byte)0, true,
                                                               (byte)0, (short)0);
            bRes = thisApplet.result == (byte)3;
        }    
        catch (Exception e) {
            bRes = false;
        }

        bRes = bRes && thisApplet.obReg.isEventSet(EVENT_MENU_SELECTION_HELP_REQUEST);

        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 12: Successful call, MenuEntry with an icon
        //               MenuEntry = "TOOLKIT TEST 4"
        //               Offset = 0
        //               Length = 14
        //               Shall return ID 04
        // -----------------------------------------------------------------
        testCaseNb = (byte)12;
        bRes = false;

        thisApplet.MenuTest[13] = (byte)'4';

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(thisApplet.MenuTest, (short)0, (short)14, (byte)0, false,
                                                               (byte)1, (short)1);
            bRes = thisApplet.result == (byte)4;
        }    
        catch (Exception e) {
            bRes = false;
        }

        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 13: Successful call, MenuEntry 
        //               with a next action indication
        //               MenuEntry = "TOOLKIT TEST 5"
        //               Offset = 0
        //               Length = 14
        //               NextAction = 24 [Select Item]
        //               Shall return ID 05
        // -----------------------------------------------------------------
        testCaseNb = (byte)13;
        bRes = false;

        thisApplet.MenuTest[13] = (byte)'5';

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(thisApplet.MenuTest, (short)0, (short)14, (byte)0x24, false,
                                                               (byte)0, (short)0);
            bRes = thisApplet.result == (byte)5 ;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 14: Successful call, length = 0 
        //               MenuEntry = "ToolkitTest"
        //               Offset = 0
        //               Length = 0
        //               NextAction = 00
        //               HelpSupported  = false
        //               IconQualifier  = 00
        //               IconIdentifier = 00
        //               Shall return ID 06
        // -----------------------------------------------------------------
        testCaseNb = (byte)14;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)0, (short)0, (byte)0, false,
                                                               (byte)0, (short)0);
            bRes = thisApplet.result == (byte)6;
        }    
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 15: Initialize more entry than allocated at loading
        //               MenuEntry = "ToolkitTest"
        //               Offset = 0
        //               Length = 11
        //               REGISTRY_ERROR ToolkitException is thrown
        // -----------------------------------------------------------------
        testCaseNb = (byte)15;
        bRes = false;

        try {
            thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry, (short)0, (short)11, (byte)0, false,
                                                               (byte)0, (short)0);
        }
        catch (ToolkitException e) {
            bRes = (e.getReason()==ToolkitException.REGISTRY_ERROR);
        }
        catch (Exception e) {
            bRes = false;
        }
        thisApplet.reportTestOutcome(testCaseNb, bRes);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        boolean bRes;
        byte testCaseNb;
        byte TLV; 
        byte ItemID;

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {
            // -----------------------------------------------------------------
            // Test Case 16: Dynamic update of the menu stored by the ME
            //               This test is true by default
            // -----------------------------------------------------------------
            testCaseNb = (byte)16;
            reportTestOutcome(testCaseNb, true);
        } else {
            if (event == EVENT_MENU_SELECTION) {

                // -----------------------------------------------------------------
                // Test Cases 17-21 and 23: Check Applet is triggered by 
                //                          ENVELOPE(MENU_SELECTION) command
                // -----------------------------------------------------------------

                // find item ID
                EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
                TLV = envHdlr.findTLV(TAG_ITEM_IDENTIFIER, (byte)1);
                
                if (TLV != TLV_NOT_FOUND) {
                    ItemID = envHdlr.getValueByte((short)0);
                    
                    if ((ItemID > (byte)0) && (ItemID < (byte)6)) {
                        testCaseNb = (byte)(ItemID + 16);
                        reportTestOutcome(testCaseNb, true);
                    } else if (ItemID == (byte)6) {
                        reportTestOutcome((byte)23, true);
                    }
                }
            } else {
                
                if (event == EVENT_MENU_SELECTION_HELP_REQUEST) {
                    // -----------------------------------------------------------------
                    // Test Case 22 : Check Applet is triggered by 
                    //                ENVELOPE(MENU_SELECTION_HELP_REQUEST) command
                    //                Menu Entry ID = 03
                    // -----------------------------------------------------------------
                    // find item ID
                    EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
                    TLV = envHdlr.findTLV(TAG_ITEM_IDENTIFIER, (byte)1);
                                         
                    if (TLV != TLV_NOT_FOUND) {
                        ItemID = envHdlr.getValueByte((short)0);
                        testCaseNb = (byte)22;
                        reportTestOutcome(testCaseNb, ItemID == (byte)3);
                    }
                }
            }
        }
    }
}
