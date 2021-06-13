//-----------------------------------------------------------------------------
//Api_1_Fvw_Actf_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_actf;

import uicc.test.util.* ;
import javacard.framework.*;
import uicc.access.* ;


public class Api_1_Fvw_Actf_1 extends TestToolkitApplet
{
    private FileView UiccFileView = null;         

    private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_1_Fvw_Actf_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_1_Fvw_Actf_1 thisApplet = new Api_1_Fvw_Actf_1();
        
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        
        // Initialise the data of the test applet.
        thisApplet.init();
        
        // toolkit registration
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,(short) MenuInit.length, (byte) 0, false,
                                    (byte) 0, (short) 0);
    }

    /**
     * Method called by the Cat Re
     */
    public void processToolkit(short event) 
    {
        // Result of tests
        boolean bRes = false;
        
        // Get the the Uicc FileView and AdminFileView
        UiccFileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
        
        // -----------------------------------------------------------------
        // Test Case 1 : No EF Selected
        // -----------------------------------------------------------------
        testCaseNb = 1;
        UiccFileView.select((short)UICCTestConstants.FID_DF_TEST);
        try 
        {
            UiccFileView.activateFile();
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == (short)UICCException.NO_EF_SELECTED)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 2 : Activate a deactivated file
        // -----------------------------------------------------------------
        testCaseNb = 2;
        bRes = false;
        UiccFileView.select((short)UICCTestConstants.FID_MF);
        UiccFileView.select((short)UICCTestConstants.FID_EF_UICC);
        // Points 2 and 3
        try 
        {
            byte rdBuff[] = new byte [1];
            UiccFileView.readBinary((short)0, rdBuff, (short)0, (short)1);
            UiccFileView.deactivateFile();
            bRes = true;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        // Point 4
        try 
        {
            byte rdBuff[] = new byte [1];
            UiccFileView.readBinary((short)0, rdBuff, (short)0, (short)1);
        }
        catch (UICCException e)
        {
            if (e.getReason() == (short)UICCException.REF_DATA_INVALIDATED)
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        // Point 5 and 6
        try 
        {
            byte rdBuff[] = new byte [1];
            UiccFileView.activateFile();
            UiccFileView.readBinary((short)0, rdBuff, (short)0, (short)1);
            bRes &= true;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 3 : Activate an activated file
        // -----------------------------------------------------------------
        testCaseNb = 3;
        bRes = false;
        try 
        {
            UiccFileView.activateFile();
            bRes = true;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 4 : Access condition not fulfilled
        // -----------------------------------------------------------------
        testCaseNb = 4;
        bRes = false;
        UiccFileView.select((short)UICCTestConstants.FID_DF_TEST);
        UiccFileView.select((short)UICCTestConstants.FID_EF_LADA);
        try 
        {
            UiccFileView.activateFile();
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == (short)UICCException.SECURITY_STATUS_NOT_SATISFIED)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
    }
}
