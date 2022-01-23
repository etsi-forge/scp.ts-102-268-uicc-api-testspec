//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_itid;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Itid extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_itid";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Itid() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        test.initialiseResults();
        
        String[] menuList   = new String[4];
        String[] menuIdList = new String[4];
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);


        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        // Bad Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800C" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "02" +   // V Maximum number of menu entries
                               "01" +   // V Position of menu entry 1
                               "01" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 2
                               "80" +   // V Id of menu entry 2
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        
        test.addResult(response.checkSw("6A80"));
        
        // Select applet1
        response = test.selectApplication(APPLET_AID_1);
        test.addResult(!(response.checkData("10" + APPLET_AID_1 + "00") && response.checkSw("9000")));
        

        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Good Install Applet1
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800C" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "02" +   // V Maximum number of menu entries
                               "01" +   // V Position of menu entry 1
                               "01" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 2
                               "7F" +   // V ID of menu entry 2
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        

        // Card Initialisation
        test.reset();
        response = test.terminalProfile("09030020");
        test.addResult(response.checkSw("9128"));
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "7F";
        fetchSetUpMenu("UICC TEST", null, (byte)2, menuIdList, menuList, null, null);

        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Bad Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "08" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Position of menu entry 1
                               "7F" +   // V ID of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        

        test.addResult(response.checkSw("6A80"));
        
        // Select applet1
        response = test.selectApplication(APPLET_AID_2);
        test.addResult(!(response.checkData("10" + APPLET_AID_1 + "00") && response.checkSw("9000")));


        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/

        // Good Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Position of menu entry 1
                               "00" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        test.addResult(response.checkSw("9131"));
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "7F";
        menuList[2] = "Menu21"; menuIdList[2] = "80";
        fetchSetUpMenu("UICC TEST", null, (byte)3, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/

        // Good Install Applet3
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "04" +   // V Position of menu entry 1
                               "00" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        test.addResult(response.checkSw("913A"));
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "7F";
        menuList[2] = "Menu21"; menuIdList[2] = "80";
        menuList[3] = "Menu31"; menuIdList[3] = "81";
        fetchSetUpMenu("UICC TEST", null, (byte)4, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/

        // Delete Applet2
        response = test.deleteApplet(APPLET_AID_2);

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        test.addResult(response.checkSw("9131"));
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "7F";
        menuList[2] = "Menu31"; menuIdList[2] = "81";
        fetchSetUpMenu("UICC TEST", null, (byte)3, menuIdList, menuList, null, null);

        // Good Install Applet2
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Position of menu entry 1
                               "00" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        test.addResult(response.checkSw("913A"));
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "7F";
        menuList[2] = "Menu21"; menuIdList[2] = "80";
        menuList[3] = "Menu31"; menuIdList[3] = "81";
        fetchSetUpMenu("UICC TEST", null, (byte)4, menuIdList, menuList, null, null);


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
        
        
        return test.getOverallResult();
    }
    
    // Fetch a sepUpMenu command, check it according to the parameters
    //   and send the Terminal Response.
    // Logs test result indicating whether the received command correspond to the one rebuild using
    //   the paramaters  
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
        test.addResult(response.checkData(setUpMenuCmd));
        
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
}   
