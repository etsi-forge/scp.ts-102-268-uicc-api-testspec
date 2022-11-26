//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_spco;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;
import java.lang.*;

public class Test_Cre_Pcs_Spco extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_pcs_spco";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
    static final String DF_TELECOM = "7F10";
    static final String EF_SUME = "6F54";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
        
    // Fetch a sepUpMenu command, check it according to the parameters
    //   and send the Terminal Response.
    // Logs test result indicating whether the received command corresponds to the one rebuilt using
    //   the parameters
    private void fetchSetUpMenu(
        String alphaId,             // AlphaId TLV Value
        String alphaIdTextAtt,      // AlphaId Text Attribute TLV Value
        byte nbMenu,                // Number of menus
        String[] menuId,            // Menu Id List table
        String[] menu,              // Text Menu List table (in readable text!)
        String nextActionTLV,          // Next Action List TLV
        String itemTextAttListTLV)     // Item Text Attribute List TLV
    {
        String setUpMenuCmd = "";
        String endOfCmd = "";
        String Cmd = "";
        
        // Build the Set Up Menu
        if (nextActionTLV != null)
        {
            endOfCmd = nextActionTLV;
        }
        if (alphaIdTextAtt != null)
        {
            endOfCmd += "50" + ToString((byte)(alphaIdTextAtt.length()/2)) + alphaIdTextAtt;
        }
        if (itemTextAttListTLV != null)
        {
            endOfCmd += itemTextAttListTLV;
        }

        String menuItem = "";
        String menuList = "";
        for (byte i = 0; i < nbMenu; i++)
        {
            menuItem = menuId[i];
            menuItem += ByteToString(menu[i].getBytes());
            menuList += "8F" + ToString((byte)(menuItem.length()/2)) + menuItem;
        }
        Cmd = menuList + endOfCmd;
        
        String alpId = ByteToString(alphaId.getBytes());
        setUpMenuCmd = "85" + ToString((byte)(alpId.length()/2)) + alpId;
        setUpMenuCmd += Cmd;
        Cmd = setUpMenuCmd;
        setUpMenuCmd = "810301250082028182" + Cmd;
        Cmd = setUpMenuCmd;
        setUpMenuCmd = "D0" + ToString((byte)(Cmd.length()/2)) + Cmd;
        
        response = test.fetch(ToString((byte)(setUpMenuCmd.length()/2)));
        addResult(response.checkData(setUpMenuCmd));
        
        test.terminalResponse("81030125 00820282 81830100"); 
    }
    
    private String ByteToString( byte tab[] )
    {
        byte[] tab2;
        byte c;
        int i, j, k;
    
        j = tab.length;
        tab2 = new byte[ j*2];

        k = 0;
        for ( i=0; i<j; i++ )
        {
            c  = (byte)((tab[ i ]>>4)&15);
            tab2[ k++ ] = (c>9) ? (byte)(c-10+'A') : (byte)(c+'0');

            c = (byte)(tab[ i ]&15);
            tab2[ k++ ] = (c>9) ? (byte)(c-10+'A') : (byte)(c+'0');
        }

        return new String( tab2 );
    }

    // byte => String
    private String ToString( byte myByte )
    {
        byte[] tab;
        byte c;
        int i, j, k;

        tab = new byte[1];
        tab[0] = myByte;

        return ByteToString( tab );
    }

    public Test_Cre_Pcs_Spco() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "02" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" +  // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                               "00" +   // LV UICC File System AID field
                               "0100" + // LV Access Domain for UICC file system = ALWAYS
                               "00" );  // LV Access Domain DAP field
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        test.terminalProfile("09010020 00000000 00000000 00000008" +
                             "00000000 00000000 0000003F 7F");
        
        // Fetch the SetUpMenu with main menu "UICC TEST" and no text attribute
        String[] menuList = new String[6];
        String[] menuIdList = new String[6];
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        String DefaultAttr = "00000390";
        String BoldAttr    = "00001390";
        String ItalicAttr  = "00002390";
        fetchSetUpMenu("UICC TEST", null, (byte)1, menuIdList, menuList, null, null);
        
        // Update EFsume with the new menu title "TEST MENU" in bold
        test.selectFile(DF_TELECOM);
        response = test.selectFile(EF_SUME);
        addResult(response.checkSw("9000"));
        test.sendApdu("00D60000 11850954 45535420 4D454E55" +
                      "50040000 1390");
        
        // Fetch The SetUpMenu with main menu "TEST MENU" and text attribute "00001390" (bold)
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        fetchSetUpMenu("TEST MENU", BoldAttr, (byte)1, menuIdList, menuList, null, null);

        // Send an unrecognized envelope
        test.unrecognizedEnvelope();
        // Fetch the SetUpMenu with main menu "TEST UICC" and no text attribute
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        fetchSetUpMenu("TEST UICC", null, (byte)1, menuIdList, menuList, null, null);

        // Send an unrecognized envelope
        test.unrecognizedEnvelope();
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        fetchSetUpMenu("TEST UICC", BoldAttr, (byte)1, menuIdList, menuList, null, null);
        
        
        // restaure EFsume with the menu title "UICC TEST" and no attribute
        test.selectFile(DF_TELECOM);
        response = test.selectFile(EF_SUME);
        addResult(response.checkSw("9000"));
        test.sendApdu("00D60000 11850955 49434320 54455354" +
                      "FFFFFFFF FFFF");

        // Fetch the SetUpMenu with main menu "UICC TEST" and no text attribute
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        fetchSetUpMenu("UICC TEST", null, (byte)1, menuIdList, menuList, null, null);

        
        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" +   // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                               "00" +   // LV UICC File System AID field
                               "0100" + // LV Access Domain for UICC file system = ALWAYS
                               "00" );  // LV Access Domain DAP field       
        
        // Fetch the SetUpMenu with the menus
        menuList[0] = "Menu1";
        menuIdList[0] = "01";
        menuList[1] = "Menu2";
        menuIdList[1] = "02";
        fetchSetUpMenu("UICC TEST", null, (byte)2, menuIdList, menuList, null, null);
        
        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Lock Applet1
        test.lockApplication(APPLET_AID_1);
        // Fetch the SetUpMenu with the menus
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        fetchSetUpMenu("UICC TEST", null, (byte)1, menuIdList, menuList, null, null);

        
        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/

        // Make selectable Applet1
        test.unlockApplication(APPLET_AID_1);
        // Fetch the SetUpMenu with the menus
        menuList[0] = "Menu1";
        menuIdList[0] = "01";
        menuList[1] = "Menu2";
        menuIdList[1] = "02";
        fetchSetUpMenu("UICC TEST", null, (byte)2, menuIdList, menuList, null, null);
        
        // Send an envelope menu selection - Applet1 disables its menu 
        test.envelopeMenuSelection("100101", "");
        // Fetch the SetUpMenu with the menus
        menuList[0] = "Menu2";
        menuIdList[0] = "02";
        fetchSetUpMenu("UICC TEST", null, (byte)1, menuIdList, menuList, null, null);
        
        // Send an envelope event download MT Call - Applet1 enables its menu 
        test.envelopeEventDownloadMTCall();
        // Fetch the SetUpMenu with the menus
        menuList[0] = "Menu1";
        menuIdList[0] = "01";
        menuList[1] = "Menu2";
        menuIdList[1] = "02";
        fetchSetUpMenu("UICC TEST", null, (byte)2, menuIdList, menuList, null, null);
        
        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/

        // Terminal Profile with Set Up Event List and EventDownload facilities 
        test.reset();
        test.terminalProfile("09010000 FFFF");

        // Fetch SetUpEventList command
        response = test.fetch("0F");
        addResult(response.checkData("D00D8103 01050082 02818219 020003") ||
                  response.checkData("D00D8103 01050082 02818299 020003"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        

        /*********************************************************************/
        /** Testcase 6                                                       */
        /*********************************************************************/
        
        // Lock Applet1
        test.lockApplication(APPLET_AID_1);
        // Fetch SetUpEventList command
        response = test.fetch("0D");
        addResult(response.checkData("D00B8103 01050082 02818219 00") ||
                  response.checkData("D00B8103 01050082 02818299 00"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 7                                                       */
        /*********************************************************************/
        
        // Make selectable Applet1
        test.unlockApplication(APPLET_AID_1);
        // Fetch SetUpEventList command
        response = test.fetch("0F");
        addResult(response.checkData("D00D8103 01050082 02818219 020003") ||
                  response.checkData("D00D8103 01050082 02818299 020003"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 8                                                       */
        /*********************************************************************/
        
        // Trigger Applet1
        test.envelopeMenuSelection("100101", "");
        // Fetch SetUpEventList command
        response = test.fetch("0E");
        addResult(response.checkData("D00C8103 01050082 02818219 0103") ||
                  response.checkData("D00C8103 01050082 02818299 0103"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        // Trigger Applet2
        test.unrecognizedEnvelope();
        // Fetch SetUpEventList command
        response = test.fetch("0F");
        addResult(response.checkData("D00D8103 01050082 02818219 020307") ||
                  response.checkData("D00D8103 01050082 02818299 020307"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        // Trigger Applets
        test.envelopeEventDownloadLocationStatus();
        // Fetch SetUpEventList command
        response = test.fetch("0D");
        addResult(response.checkData("D00B8103 01050082 02818219 00") ||
                  response.checkData("D00B8103 01050082 02818299 00"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));

        // Trigger Applet1
        test.envelopeMenuSelection("100101", "");
        // Fetch SetUpEventList command
        response = test.fetch("0E");
        addResult(response.checkData("D00C8103 01050082 02818219 0100") ||
                  response.checkData("D00C8103 01050082 02818299 0100"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));

        // Delete Applet1
        test.deleteApplet(APPLET_AID_1);
        // Fetch SetUpEventList command
        response = test.fetch("0D");
        addResult(response.checkData("D00B8103 01050082 02818219 00") ||
                  response.checkData("D00B8103 01050082 02818299 00"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        // Fetch SetUpEventList command
        response = test.fetch("0F");
        addResult(response.checkData("D00D8103 01050082 02818219 020003") ||
                  response.checkData("D00D8103 01050082 02818299 020003"));
        response = test.terminalResponse("81030105 00820282 81830100");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 9                                                       */
        /*********************************************************************/
        
        // Terminal Profile with polling facilities 
        test.reset();
        test.terminalProfile("290160");
        // Fetch Poll Interval command
        response = test.fetch("0F");
        String pollInterCmd = response.getData();
        addResult(pollInterCmd.regionMatches(0, "D00D8103010300820281820402",
                                             0, "D00D8103010300820281820402".length()) ||
                  pollInterCmd.regionMatches(0, "D00D8103010300820281828402",
                                             0, "D00D8103010300820281828402".length()));
        response = test.terminalResponse("81030103 00820282 81830100 84020010");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 10                                                      */
        /*********************************************************************/
        
        // Lock Applet1
        test.lockApplication(APPLET_AID_1);
        // Fetch Polling Off command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01040082 028182"));
        response = test.terminalResponse("81030104 00820282 81830100");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 11                                                      */
        /*********************************************************************/
        
        // Make selectable Applet1
        test.unlockApplication(APPLET_AID_1);
        // Fetch Poll Interval command
        response = test.fetch("0F");
        addResult(response.checkData(pollInterCmd));
        response = test.terminalResponse("81030103 00820282 81830100 84020010");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 12                                                      */
        /*********************************************************************/
        
        // Trigger Applet1
        test.status("00", "00", "16");
        // Fetch Polling Off command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01040082 028182"));
        response = test.terminalResponse("81030104 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
         
        // Trigger Applet1
        test.unrecognizedEnvelope();
        // Fetch Poll Interval command
        response = test.fetch("0F");
        addResult(response.checkData(pollInterCmd));
        response = test.terminalResponse("81030103 00820282 81830100 84020010");                                    
        addResult(response.checkSw("9000"));

        // Delete Applet1
        test.deleteApplet(APPLET_AID_1);
        // Fetch Polling Off command
        response = test.fetch("0B");
        addResult(response.checkData("D0098103 01040082 028182"));
        response = test.terminalResponse("81030104 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "01" +   // V Id of menu entry 1
                               "01" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        // Fetch Poll Interval command
        response = test.fetch("0F");
        addResult(response.checkData(pollInterCmd));
        response = test.terminalResponse("81030103 00820282 81830100 84020010");                                    
        addResult(response.checkSw("9000"));


        /*********************************************************************/
        /** Testcase 13                                                      */
        /*********************************************************************/
        
        test.reset();
        test.terminalProfileSession("29016120 FFFF");
        
        // Trigger Applet1
        test.unrecognizedEnvelope();
        // Fetch Diplay text
        response = test.fetch("13");
        addResult(response.checkData("D0118103 01210082 0281028D 06045465" +
                                     "787431"));
        response = test.terminalResponse("81030121 00820282 81830100");                                    
        addResult(response.checkSw("9114"));

        // Fetch Diplay text
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01210082 0281028D 07045465" +
                                     "78743231"));
        response = test.terminalResponse("81030121 00820282 81830100");                                    
        addResult(response.checkSw("9114"));

        // Fetch Diplay text
        response = test.fetch("14");
        addResult(response.checkData("D0128103 01210082 0281028D 07045465" +
                                     "78743232"));
        response = test.terminalResponse("81030121 00820282 81830100");                                    
        
        // local variables so that we detect each expected proactive command only once
        boolean setUpMenuReceived = false;
        boolean setUpEventListReceived = false;
        boolean pollingOffReceived = false;
        // loop three times to try to fetch the three expected proactive commands
        for (byte i = 0; i < 3; i++) {
            // check for a pending proactive command
            if (response.getStatusWord().startsWith("91")) {
                // fetch the proactive command
                response = test.fetch(response.getStatusWord().substring(2));
                // find the command details (by searching for "8103"), so that we can extract the type of command
                int commandDetailsIndex = response.getData().indexOf("8103");
                // if the proactive command is long enough to contain the type of command (it should be!) ...
                if ((commandDetailsIndex > 0) && (response.getData().length() >= (commandDetailsIndex + 8))) {
                    // extract the type of command (3 bytes after the command details starts)
                    String typeOfCommand = response.getData().substring(commandDetailsIndex + 6, commandDetailsIndex + 8);
                    // check for the expected types (if not received already)
                    // 25: SET UP MENU
                    if (!setUpMenuReceived && typeOfCommand.equals("25")) {
                        addResult(response.checkData("D0168103 01250082 02818285 09554943" +
                            "43205445 53548F00"));
                        setUpMenuReceived = true;
                    }
                    // 05: SET UP EVENT LIST
                    else if (!setUpEventListReceived && typeOfCommand.equals("05")) {
                        addResult(response.checkData("D00C8103 01050082 02818219 0103") ||
                            response.checkData("D00C8103 01050082 02818299 0103"));
                        setUpEventListReceived = true;
                    }
                    // 04: POLLING OFF
                    else if (!pollingOffReceived && typeOfCommand.equals("04")) {
                        addResult(response.checkData("D0098103 01040082 028182"));
                        pollingOffReceived = true;
                    }
                    // other
                    else {
                        addResult(false);
                    }
                    // send the terminal response
                    response = test.terminalResponse("810301" + typeOfCommand + " 00820282 81830100");
                }
                else {
                    // the proactive command is too short for a type of command, so this is not an expected proactive command
                    addResult(false);
                    // return a dummy terminal response
                    response = test.terminalResponse("81030105 00820282 81830100");
                }
            }
            else {
                // no proactive command; we always expect three proactive commands, so this is an error
                // add number of failures corresponding to the number of missing proactive commands
                for (int j = i; j < 3; j++) {
                    addResult(false);
                }
                break;
            }
        }

        /*********************************************************************/
        /** Testcase 14                                                      */
        /*********************************************************************/

        test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0F" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        
        // test script
        test.reset();
        // Terminal Profile with Set Up Menu
        response = test.terminalProfile("09010020 00000000 00000000 00000008" +
                                        "00000000 00000000 0000003F 7F");
        // Fetch the SetUpMenu
        response = test.fetch(response.getStatusWord().substring(2));
        addResult(response.checkData("D01C8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 7533") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 75335104" +
                                     "00000390") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 7533D104" +
                                     "00000390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));

        //Trigger Applet2 (testcase 14-2)
        response = test.unrecognizedEnvelope();
        // Fetch the SetUpMenu
        response = test.fetch(response.getStatusWord().substring(2));
        addResult(response.checkData("D0248103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 75335108 00000390 00000390") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "024D656E 7533D108 00000390 00000390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Trigger Applet2 (testcase 14-4)
        test.envelopeMenuSelection("100102", "");
        // Fetch the SetUpMenu
        response = test.fetch("30");
        addResult(response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 75335108 00001390 00000390") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533D108 00001390 00000390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Trigger Applet2 (testcase 14-6)
        response = test.envelopeMenuSelection("100102", "");
        // Fetch the SetUpMenu
        response = test.fetch(response.getStatusWord().substring(2));
        addResult(response.checkData("D01C8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 7533") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 75335104" +
                                     "00000390") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 7533D104" +
                                     "00000390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Trigger Applet3 (testcase 14-8)
        test.envelopeMenuSelection("100103", "");
        // Fetch the SetUpMenu
        response = test.fetch("24");
        addResult(response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 75335104" +
                                     "00002390") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 034D656E 7533D104" +
                                     "00002390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));

        //Trigger Applet2 (testcase 14-10)
        test.unrecognizedEnvelope();
        // Fetch the SetUpMenu
        response = test.fetch("30");
        addResult(response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 75335108 00001390 00002390") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533D108 00001390 00002390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Trigger Applet2 (testcase 14-12)
        test.envelopeMenuSelection("100102", "");
        // Fetch the SetUpMenu
        response = test.fetch("30");
        addResult(response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 75335108 00000390 00002390") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533D108 00000390 00002390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Lock Applet3 (testcase 14-14)
        response = test.lockApplication(APPLET_AID_3);
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        // Fetch the SetUpMenu
        response = test.fetch(response.getStatusWord().substring(2));
        addResult(response.checkData("D01C8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 7532") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75325104" +
                                     "00000390") ||
                  response.checkData("D0228103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 7532D104" +
                                     "00000390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Make selectable Applet3 (testcase 14-15)
        test.unlockApplication(APPLET_AID_3);
        // Fetch the SetUpMenu
        response = test.fetch("30");
        addResult(response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 75335108 00000390 00002390") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533D108 00000390 00002390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));
        
        //Trigger Applet3 (testcase 14-16)
        response = test.envelopeMenuSelection("100103", "");
        // Fetch the SetUpMenu
        response = test.fetch(response.getStatusWord().substring(2));
        addResult(response.checkData("D0248103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 75335108 00000390 00000390") ||
                  response.checkData("D02E8103 01250082 02818285 09554943" +
                                     "43205445 53548F06 024D656E 75328F06" +
                                     "034D656E 7533D108 00000390 00000390"));
        response = test.terminalResponse("81030125 00820282 81830100");                                    
        addResult(response.checkSw("9000"));

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        addResult(response.checkData("10" + APPLET_AID_1 + "09" +
                                     "CCCCCCCC CCCCCCCC CC"));
        response = test.selectApplication(APPLET_AID_2);
        addResult(response.checkData("10" + APPLET_AID_2 + "0C" +
                                     "CCCCCCCC CCCCCCCC CCCCCCCC"));
        response = test.selectApplication(APPLET_AID_3);
        addResult(response.checkData("10" + APPLET_AID_3 + "02" +
                                     "CCCC"));
                                     
        /*********************************************************************/
        /*********************************************************************/
        /** Restore  card                                                    */
        /*********************************************************************/
        /*********************************************************************/

        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);
        // delete applets and package
        test.deleteApplet(APPLET_AID_1);
        test.deleteApplet(APPLET_AID_2);
        test.deleteApplet(APPLET_AID_3);
        test.deletePackage(CAP_FILE_PATH);
        
        return getOverallResult();
    }
}