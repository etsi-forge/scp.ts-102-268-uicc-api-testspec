//-----------------------------------------------------------------------------
//Api_4_Aex_Coor_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_aex_coor;

import uicc.test.util.* ;
import uicc.toolkit.*;
import uicc.access.fileadministration.*;


public class Api_4_Aex_Coor_1 extends TestToolkitApplet
{
    private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_4_Aex_Coor_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_4_Aex_Coor_1 thisApplet = new Api_4_Aex_Coor_1();
      
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
      
        // Initialise the data of the test applet.
        thisApplet.init();
      
        // toolkit registration
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,(short) MenuInit.length, (byte) 0, false,(byte) 0, (short) 0);
    }

    /**
     * Method called by the Cat Re
     */
    public void processToolkit(short event) 
    {
        boolean bRes = false;
        byte testCaseNb = (byte)0;

        /** Test Case 1 : AdminException with the specified set reason */
        testCaseNb = (byte)1;
        bRes = false ;
        AdminException UiccEx = new AdminException((short)19);
        bRes = (UiccEx.getReason() == (short)19);
        
        reportTestOutcome(testCaseNb, bRes);
    }
}
