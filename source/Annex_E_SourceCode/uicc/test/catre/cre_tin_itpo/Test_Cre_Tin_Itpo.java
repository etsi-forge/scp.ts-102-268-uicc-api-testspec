//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_itpo;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Itpo extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_itpo";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
    static final String CLASS_AID_3 = "A0000000 090005FF FFFFFF89 50030001";
    static final String APPLET_AID_3 = "A0000000 090005FF FFFFFF89 50030102";
    static final String CLASS_AID_4 = "A0000000 090005FF FFFFFF89 50040001";
    static final String APPLET_AID_4 = "A0000000 090005FF FFFFFF89 50040102";
    static final String CLASS_AID_5 = "A0000000 090005FF FFFFFF89 50050001";
    static final String APPLET_AID_5 = "A0000000 090005FF FFFFFF89 50050102";
    static final String CLASS_AID_6 = "A0000000 090005FF FFFFFF89 50060001";
    static final String APPLET_AID_6 = "A0000000 090005FF FFFFFF89 50060102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Itpo() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        initialiseResults();
        
        String[] menuList   = new String[12];
        String[] menuIdList = new String[12];
        
        // test script
        test.reset();
        test.terminalProfileSession("09030020");

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        
        /*********************************************************************/
        /** Testcase 1                                                       */
        /*********************************************************************/

        // Install Applet1; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "8010" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "04" +   // V Maximum number of menu entries
                               "01" +   // V Position of menu entry 1
                               "01" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 2
                               "02" +   // V Id of menu entry 2
                               "03" +   // V Position of menu entry 3
                               "03" +   // V Id of menu entry 3
                               "04" +   // V Position of menu entry 4
                               "04" +   // V Id of menu entry 4
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
                           true);

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "02";
        menuList[2] = "Menu13"; menuIdList[2] = "03";
        menuList[3] = "Menu14"; menuIdList[3] = "04";
        fetchSetUpMenu("UICC TEST", null, (byte)4, menuIdList, menuList, null, null);
        

        /*********************************************************************/
        /** Testcase 2                                                       */
        /*********************************************************************/

        // Install Applet2; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Position of menu entry 1
                               "05" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
            true);
        

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu12"; menuIdList[1] = "02";
        menuList[2] = "Menu21"; menuIdList[2] = "05";
        menuList[3] = "Menu13"; menuIdList[3] = "03";
        menuList[4] = "Menu14"; menuIdList[4] = "04";
        fetchSetUpMenu("UICC TEST", null, (byte)5, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Install Applet3; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_3, APPLET_AID_3, 
                           "800C" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "02" +   // V Maximum number of menu entries
                               "02" +   // V Position of menu entry 1
                               "06" +   // V Id of menu entry 1
                               "03" +   // V Position of menu entry 2
                               "07" +   // V Id of menu entry 2
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" +   // V Maximum number of services
                           "8104" + // TLV UICC Access application specific parameters
                               "00" +   // LV UICC File System AID field
                               "0100" + // LV Access Domain for UICC file system = ALWAYS
                               "00",    // LV Access Domain DAP field
                           true);
        
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu31"; menuIdList[1] = "06";
        menuList[2] = "Menu32"; menuIdList[2] = "07";
        menuList[3] = "Menu12"; menuIdList[3] = "02";
        menuList[4] = "Menu21"; menuIdList[4] = "05";
        menuList[5] = "Menu13"; menuIdList[5] = "03";
        menuList[6] = "Menu14"; menuIdList[6] = "04";
        fetchSetUpMenu("UICC TEST", null, (byte)7, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 4                                                       */
        /*********************************************************************/

        // Install Applet4; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_4, APPLET_AID_4, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "00" +   // V Position of menu entry 1
                               "08" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
                           true);
        
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu31"; menuIdList[1] = "06";
        menuList[2] = "Menu32"; menuIdList[2] = "07";
        menuList[3] = "Menu12"; menuIdList[3] = "02";
        menuList[4] = "Menu21"; menuIdList[4] = "05";
        menuList[5] = "Menu13"; menuIdList[5] = "03";
        menuList[6] = "Menu14"; menuIdList[6] = "04";
        menuList[7] = "Menu41"; menuIdList[7] = "08";
        fetchSetUpMenu("UICC TEST", null, (byte)8, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 5                                                       */
        /*********************************************************************/

        // Install Applet5; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_5, APPLET_AID_5, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "20" +   // V Position of menu entry 1
                               "09" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
                           true);

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu11"; menuIdList[0] = "01";
        menuList[1] = "Menu31"; menuIdList[1] = "06";
        menuList[2] = "Menu32"; menuIdList[2] = "07";
        menuList[3] = "Menu12"; menuIdList[3] = "02";
        menuList[4] = "Menu21"; menuIdList[4] = "05";
        menuList[5] = "Menu13"; menuIdList[5] = "03";
        menuList[6] = "Menu14"; menuIdList[6] = "04";
        menuList[7] = "Menu41"; menuIdList[7] = "08";
        menuList[8] = "Menu51"; menuIdList[8] = "09";
        fetchSetUpMenu("UICC TEST", null, (byte)9, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 6                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100101","");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu31"; menuIdList[0] = "06";
        menuList[1] = "Menu32"; menuIdList[1] = "07";
        menuList[2] = "Menu12"; menuIdList[2] = "02";
        menuList[3] = "Menu21"; menuIdList[3] = "05";
        menuList[4] = "Menu13"; menuIdList[4] = "03";
        menuList[5] = "Menu14"; menuIdList[5] = "04";
        menuList[6] = "Menu41"; menuIdList[6] = "08";
        menuList[7] = "Menu51"; menuIdList[7] = "09";
        fetchSetUpMenu("UICC TEST", null, (byte)8, menuIdList, menuList, null, null);

        // Lock Applet2; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.lockApplication(APPLET_AID_2, true);
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu31"; menuIdList[0] = "06";
        menuList[1] = "Menu32"; menuIdList[1] = "07";
        menuList[2] = "Menu12"; menuIdList[2] = "02";
        menuList[3] = "Menu13"; menuIdList[3] = "03";
        menuList[4] = "Menu14"; menuIdList[4] = "04";
        menuList[5] = "Menu41"; menuIdList[5] = "08";
        menuList[6] = "Menu51"; menuIdList[6] = "09";
        fetchSetUpMenu("UICC TEST", null, (byte)7, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 7                                                       */
        /*********************************************************************/

        // Install Applet6; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_6, APPLET_AID_6, 
                           "800E" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "03" +   // V Maximum number of menu entries
                               "01" +   // V Position of menu entry 1
                               "10" +   // V Id of menu entry 1
                               "04" +   // V Position of menu entry 2
                               "11" +   // V Id of menu entry 2
                               "15" +   // V Position of menu entry 3
                               "12" +   // V Id of menu entry 3
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
                           true);

        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0] = "Menu61"; menuIdList[0] = "10";
        menuList[1] = "Menu31"; menuIdList[1] = "06";
        menuList[2] = "Menu62"; menuIdList[2] = "11";
        menuList[3] = "Menu32"; menuIdList[3] = "07";
        menuList[4] = "Menu12"; menuIdList[4] = "02";
        menuList[5] = "Menu13"; menuIdList[5] = "03";
        menuList[6] = "Menu14"; menuIdList[6] = "04";
        menuList[7] = "Menu41"; menuIdList[7] = "08";
        menuList[8] = "Menu51"; menuIdList[8] = "09";
        menuList[9] = "Menu63"; menuIdList[9] = "12";
        fetchSetUpMenu("UICC TEST", null, (byte)10, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 8                                                       */
        /*********************************************************************/

        // Trigger Applet1
        response = test.envelopeMenuSelection("100102","");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0]  = "Menu61"; menuIdList[0]  = "10";
        menuList[1]  = "Menu11"; menuIdList[1]  = "01";
        menuList[2]  = "Menu31"; menuIdList[2]  = "06";
        menuList[3]  = "Menu62"; menuIdList[3]  = "11";
        menuList[4]  = "Menu32"; menuIdList[4]  = "07";
        menuList[5]  = "Menu12"; menuIdList[5]  = "02";
        menuList[6]  = "Menu13"; menuIdList[6]  = "03";
        menuList[7]  = "Menu14"; menuIdList[7]  = "04";
        menuList[8]  = "Menu41"; menuIdList[8]  = "08";
        menuList[9]  = "Menu51"; menuIdList[9]  = "09";
        menuList[10] = "Menu63"; menuIdList[10] = "12";
        fetchSetUpMenu("UICC TEST", null, (byte)11, menuIdList, menuList, null, null);

        // Unlock Applet2; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.unlockApplication(APPLET_AID_2, true);
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0]  = "Menu61"; menuIdList[0]  = "10";
        menuList[1]  = "Menu11"; menuIdList[1]  = "01";
        menuList[2]  = "Menu31"; menuIdList[2]  = "06";
        menuList[3]  = "Menu62"; menuIdList[3]  = "11";
        menuList[4]  = "Menu32"; menuIdList[4]  = "07";
        menuList[5]  = "Menu12"; menuIdList[5]  = "02";
        menuList[6]  = "Menu21"; menuIdList[6]  = "05";
        menuList[7]  = "Menu13"; menuIdList[7]  = "03";
        menuList[8]  = "Menu14"; menuIdList[8]  = "04";
        menuList[9]  = "Menu41"; menuIdList[9]  = "08";
        menuList[10] = "Menu51"; menuIdList[10] = "09";
        menuList[11] = "Menu63"; menuIdList[11] = "12";
        fetchSetUpMenu("UICC TEST", null, (byte)12, menuIdList, menuList, null, null);


        /*********************************************************************/
        /** Testcase 9                                                       */
        /*********************************************************************/

        // Delete Applet2; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.deleteApplet(APPLET_AID_2, true);
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0]  = "Menu61"; menuIdList[0]  = "10";
        menuList[1]  = "Menu11"; menuIdList[1]  = "01";
        menuList[2]  = "Menu31"; menuIdList[2]  = "06";
        menuList[3]  = "Menu62"; menuIdList[3]  = "11";
        menuList[4]  = "Menu32"; menuIdList[4]  = "07";
        menuList[5]  = "Menu12"; menuIdList[5]  = "02";
        menuList[6]  = "Menu13"; menuIdList[6]  = "03";
        menuList[7]  = "Menu14"; menuIdList[7]  = "04";
        menuList[8]  = "Menu41"; menuIdList[8]  = "08";
        menuList[9]  = "Menu51"; menuIdList[9]  = "09";
        menuList[10] = "Menu63"; menuIdList[10] = "12";
        fetchSetUpMenu("UICC TEST", null, (byte)11, menuIdList, menuList, null, null);
        
        
        /*********************************************************************/
        /** Testcase 10                                                      */
        /*********************************************************************/

        // Install Applet2; note that we don't want this method to consume any unnecessary 91XX -> true
        response = test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "800A" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "10" +   // V Maximum text length for a menu entry
                               "01" +   // V Maximum number of menu entries
                               "03" +   // V Position of menu entry 1
                               "05" +   // V Id of menu entry 1
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00",    // V Maximum number of services
                           true);
        
        // Send a status command to be sure to retrieve the correct status word in the RAPDU
        response = test.status("00","0C","00");
        addResult(response.getStatusWord().substring(0,2).compareTo("91") == 0);
        // Fetch & Terminal response
        menuList[0]  = "Menu61"; menuIdList[0]  = "10";
        menuList[1]  = "Menu11"; menuIdList[1]  = "01";
        menuList[2]  = "Menu21"; menuIdList[2]  = "05";
        menuList[3]  = "Menu31"; menuIdList[3]  = "06";
        menuList[4]  = "Menu62"; menuIdList[4]  = "11";
        menuList[5]  = "Menu32"; menuIdList[5]  = "07";
        menuList[6]  = "Menu12"; menuIdList[6]  = "02";
        menuList[7]  = "Menu13"; menuIdList[7]  = "03";
        menuList[8]  = "Menu14"; menuIdList[8]  = "04";
        menuList[9]  = "Menu41"; menuIdList[9]  = "08";
        menuList[10] = "Menu51"; menuIdList[10] = "09";
        menuList[11] = "Menu63"; menuIdList[11] = "12";
        fetchSetUpMenu("UICC TEST", null, (byte)12, menuIdList, menuList, null, null);
        

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
        test.deleteApplet(APPLET_AID_4);
        test.deleteApplet(APPLET_AID_5);
        test.deleteApplet(APPLET_AID_6);
        test.deletePackage(CAP_FILE_PATH);
        
        
        return getOverallResult();
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
        if ((Cmd.length()/2) > 0x7F)
        {
            setUpMenuCmd = "D081" + ToString((byte)(Cmd.length()/2)) + Cmd;
        }
        else
        {
            setUpMenuCmd = "D0" + ToString((byte)(Cmd.length()/2)) + Cmd;
        }
        
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
}   
