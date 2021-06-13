//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_nbme;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class Test_Cre_Tin_Nbme extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/catre/cre_tin_nbme";
    static final String CLASS_AID_1 = "A0000000 090005FF FFFFFF89 50010001";
    static final String APPLET_AID_1 = "A0000000 090005FF FFFFFF89 50010102";
    static final String CLASS_AID_2 = "A0000000 090005FF FFFFFF89 50020001";
    static final String APPLET_AID_2 = "A0000000 090005FF FFFFFF89 50020102";
   
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public Test_Cre_Tin_Nbme() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
        String[] menuList   = new String[3];
        String[] menuIdList = new String[3];
        
        // test script
        test.reset();
        test.terminalProfileSession(UiccCardManagementService.DEFAULT_TERMINAL_PROFILE);

        // Install package
        test.loadPackage(CAP_FILE_PATH);
        
        // Card Initialisation
        test.reset();
        test.terminalProfile("09030020");

        /*********************************************************************/
        /** Testcase 1-2                                                     */
        /*********************************************************************/

        // Install Applet1
        test.installApplet(CAP_FILE_PATH, CLASS_AID_1, APPLET_AID_1, 
                           "800E" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "03" +   // V Maximum number of menu entries
                               "01" +   // V Position of menu entry 1
                               "01" +   // V Id of menu entry 1
                               "02" +   // V Position of menu entry 2
                               "02" +   // V Id of menu entry 2
                               "03" +   // V Position of menu entry 3
                               "03" +   // V Id of menu entry 3
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00" );  // V Maximum number of services
        
        // Fetch & Terminal response
        menuList[0] = "Menu1"; menuIdList[0] = "01";
        menuList[1] = "Menu2"; menuIdList[1] = "02";
        menuList[2] = "Menu3"; menuIdList[2] = "03";
        result = fetchSetUpMenu("UICC TEST", null, (byte)3, menuIdList, menuList, null, null);
        

        /*********************************************************************/
        /** Testcase 3                                                       */
        /*********************************************************************/

        // Install Applet2
        test.installApplet(CAP_FILE_PATH, CLASS_AID_2, APPLET_AID_2, 
                           "8008" + // TLV UICC Toolkit application specific parameters
                               "FF" +   // V Priority Level
                               "00" +   // V Max. number of timers
                               "0A" +   // V Maximum text length for a menu entry
                               "00" +   // V Maximum number of menu entries
                               "00" +   // V Maximum number of channels 
                               "00" +   // LV Minimum Security Level field
                               "00" +   // LV TAR Value(s) 
                               "00");   // V Maximum number of services

        // Card Initialisation
        test.reset();
        test.terminalProfile("09030020");

        // Fetch & Terminal response
        menuList[0] = "Menu1"; menuIdList[0] = "01";
        menuList[1] = "Menu2"; menuIdList[1] = "02";
        menuList[2] = "Menu3"; menuIdList[2] = "03";
        result &= fetchSetUpMenu("UICC TEST", null, (byte)3, menuIdList, menuList, null, null);
        

        /*********************************************************************/
        /*********************************************************************/
        /** Check Applets                                                    */
        /*********************************************************************/
        /*********************************************************************/

        response = test.selectApplication(APPLET_AID_1);
        result &= response.checkData("10" + APPLET_AID_1 + "04" + "CCCCCCCC");
        response = test.selectApplication(APPLET_AID_2);
        result &= response.checkData("10" + APPLET_AID_2 + "01" + "CC");


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
        test.deletePackage(CAP_FILE_PATH);
        
        
        return result;
    }
    
    // Fetch a sepUpMenu command, check it according to the parameters
    //   and send the Terminal Response.
    // Return true if the recieved command correspond to the one rebuild using 
    //   the paramaters  
    private boolean fetchSetUpMenu(
            String alphaId,             // AlphaId TLV Value
            String alphaIdTextAtt,      // AlphaId Text Attribute TLV Value
            byte nbMenu,                // Number of menus
            String[] menuId,            // Menu Id List table
            String[] menu,              // Text Menu List table (in readable text!)
            String nextActionTLV,          // Next Action List TLV
            String itemTextAttListTLV)     // Item Text Attribute List TLV
    {
        boolean result;
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
        result = response.checkData(setUpMenuCmd);
        
        test.terminalResponse("81030125 00820282 81830100"); 
        
        return result;
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
