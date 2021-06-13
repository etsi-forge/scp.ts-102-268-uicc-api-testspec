//-----------------------------------------------------------------------------
//    Cre_Tin_Acdo_2.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_acdo;

import javacard.framework.* ;
import uicc.toolkit.*;
import uicc.access.*;
import uicc.access.fileadministration.*;
import uicc.test.util.* ;


public class Cre_Tin_Acdo_2 extends TestToolkitApplet
{ 
    private static byte[] MenuInit1 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1',(byte)'1'};
    private static byte[] MenuInit2 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1',(byte)'2'};
    final boolean bSecurityException = false;
    final boolean bSuccessExecution = true;
    AID AID1 = null;
    FileView UICCView = null;
    FileView ADF1View = null;
    byte bTestCaseNb = 0;
    byte[] abTestResults = null;
    UICCTestConstants testConstants = null;
    Cre_Tin_Acdo_8 commonTests = null;

    /**
     * Constructor of the applet
     */
    
    public Cre_Tin_Acdo_2()
    {
        testConstants = new UICCTestConstants();
        AID1 = new AID(testConstants.AID_ADF1, (short)0, (byte)testConstants.AID_ADF1.length);
        commonTests = new Cre_Tin_Acdo_8();
    } 

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {

        // Create a new applet instance.
        Cre_Tin_Acdo_2 thisApplet = new Cre_Tin_Acdo_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();

        thisApplet.obReg.initMenuEntry(MenuInit1, (short) 0, (short)MenuInit1.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(MenuInit2, (short) 0, (short)MenuInit2.length, (byte) 0, false, (byte) 0, (short) 0);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event)
    {
        boolean bRes = true;
        boolean bIsAdminFVTest = false;

        // -----------------------------------------------------------------
        //  Test Case 2 : No access Applet
        // -----------------------------------------------------------------

        if (event == EVENT_MENU_SELECTION) {
            byte bMenuId = 0;

            try 
            {
                bMenuId = (EnvelopeHandlerSystem.getTheHandler().getItemIdentifier());
            }
            catch (Exception e)
            {
                // Debug purpose: only testcase 0x01 Ok if handler not available
                reportTestOutcome((byte)0x01, true);
                return;
            }
            
            // Fileview access tests
            try 
            {
                if (bMenuId == 1)
                {
                    // Get fileviews...
                    UICCView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
                    ADF1View = UICCSystem.getTheFileView(AID1, JCSystem.CLEAR_ON_RESET);
                    bIsAdminFVTest = false;
                }
                else if (bMenuId == 2)
                {
                    // Get admin fileviews...
                    UICCView = (FileView)AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
                    ADF1View = (FileView)AdminFileViewBuilder.getTheAdminFileView(AID1, JCSystem.CLEAR_ON_RESET);
                    bIsAdminFVTest = true;
                    
                    // Reset local testCaseNumber
                    commonTests.resetCaseNumber();
                }

                commonTests.SetFileViewRef(UICCView, ADF1View);
            }
            catch(Exception e)
            {
                // Debug purpose: only testcase 2 (menuID1) or 3 (menuId2) OK, if fileview not available
                reportTestOutcome((byte)(bMenuId + 1), true);
                return;
            }
            
            // Sub cases 1.1 & 2.1 : Access tests on cyclic EF methods access
            // -------------------------------------------------------------
            for (byte bARRNb = 1; bARRNb < 6; bARRNb++)
            {
                // Security exception is expected
                abTestResults = commonTests.cyclicEFTests(bARRNb, bIsAdminFVTest, bSecurityException);
            }                    

            // Sub cases 1.2 & 2.2 : Access tests on linear fixed EF methods access
            // --------------------------------------------------------------------
            for (byte bARRNb = 1; bARRNb < 6; bARRNb++)
            {
                // Security exception is expected
                abTestResults = commonTests.linearEFTests(bARRNb, bIsAdminFVTest, bSecurityException);
            }                    
            
            // Sub cases 1.3 & 2.3 : Access tests on transparent EF methods access
            // -------------------------------------------------------------------
            for (byte bARRNb = 1; bARRNb < 6; bARRNb++)
            {
                // Security exception is expected
                abTestResults = commonTests.transpEFTests(bARRNb, bIsAdminFVTest, bSecurityException);
            }                    

            // Sub cases 2.4 to 2.6 : Access tests administrative commands
            // -----------------------------------------------------------
            if (bMenuId == 2)
            {
                for (byte bARRNb = 1; bARRNb < 6; bARRNb++)
                {
                    // Security exception is expected
                    abTestResults = commonTests.adminCmdAccessTests(bARRNb, bSecurityException);
                }
            }

            // Update Outcome report with result table
            // ---------------------------------------
            for (byte n = 1; n <= abTestResults[0]; n++)
            {
                reportTestOutcome(n, (abTestResults[n] == (byte)0xCC));
            }
        }
    }
}