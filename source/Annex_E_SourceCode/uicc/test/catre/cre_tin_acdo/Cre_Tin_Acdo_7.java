//-----------------------------------------------------------------------------
//    Cre_Tin_Acdo_6.java
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


public class Cre_Tin_Acdo_7 extends TestToolkitApplet
{ 
    private static byte[] MenuInit1 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    private static byte[] MenuInit2 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'2'};
    private static byte[] MenuInit3 = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'3'};
    AID AID1 = null;
    FileView UICCView = null;
    AdminFileView UICCAdminView = null;
    byte bTestCaseNb = 0;
    byte[] abRdBin = null;
    UICCTestConstants testConstants = null;
    

    /**
     * Constructor of the applet
     */
    
    public Cre_Tin_Acdo_7()
    {
        testConstants = new UICCTestConstants();
        AID1 = new AID(testConstants.AID_ADF1, (short)0, (byte)testConstants.AID_ADF1.length);
        abRdBin = new byte[3];
    } 

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength)
    {

        // Create a new applet instance.
        Cre_Tin_Acdo_7 thisApplet = new Cre_Tin_Acdo_7();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();

        thisApplet.obReg.initMenuEntry(MenuInit1, (short) 0, (short)MenuInit1.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(MenuInit2, (short) 0, (short)MenuInit2.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(MenuInit3, (short) 0, (short)MenuInit3.length, (byte) 0, false, (byte) 0, (short) 0);
    }

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event)
    {
        boolean bRes = false;
        byte bMenuId = 0;

        // -----------------------------------------------------------------
        //  Test Case 7 : AdminFileview and Fileview acces domain parameter differenciation
        // -----------------------------------------------------------------

        bMenuId = (byte)EnvelopeHandlerSystem.getTheHandler().getItemIdentifier();

        switch(bMenuId)
        {
            case 1:
                // Fileview access tests
                try 
                {
                    // Get fileviews...
                    UICCView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
                }
                catch(Exception e)
                {
                    return;
                }
                
                // Sub cases 1.1 : successful FileView access
                // ------------------------------------------
                UICCView.select(UICCTestConstants.FID_DF_TEST);
                UICCView.select(UICCTestConstants.FID_EF_TARR1);
                try 
                {
                    UICCView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 1, bRes);

                // Sub cases 1.2 : Unsuccessful FileView access
                // --------------------------------------------
                UICCView.select(UICCTestConstants.FID_DF_TEST);
                UICCView.select(UICCTestConstants.FID_EF_TARR5);
                try 
                {
                    UICCView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = false;
                }
                catch (UICCException e)
                {
                    bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 2, bRes);

                // Sub cases 1.3 : Successful FileView access
                // ------------------------------------------
                UICCView.select(UICCTestConstants.FID_DF_TEST);
                UICCView.select(UICCTestConstants.FID_EF_TARR4);
                try 
                {
                    UICCView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 3, bRes);

                break;

            case 2:
                // Fileview access tests
                try 
                {
                    // Get fileviews...
                    UICCAdminView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
                }
                catch(Exception e)
                {
                    return;
                }
                
                // Sub cases 4.1 : Unsuccessful adminFileView access
                // -------------------------------------------------
                UICCAdminView.select(UICCTestConstants.FID_DF_TEST);
                UICCAdminView.select(UICCTestConstants.FID_EF_TARR1);
                try 
                {
                    UICCAdminView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = false;
                }
                catch (UICCException e)
                {
                    bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 1, bRes);

                // Sub cases 4.2 : successful adminFileView access
                // -----------------------------------------------
                UICCAdminView.select(UICCTestConstants.FID_DF_TEST);
                UICCAdminView.select(UICCTestConstants.FID_EF_TARR5);
                try 
                {
                    UICCAdminView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 2, bRes);


                // Sub cases 4.3 : Successful adminFileView access
                // -----------------------------------------------
                UICCAdminView.select(UICCTestConstants.FID_DF_TEST);
                UICCAdminView.select(UICCTestConstants.FID_EF_TARR4);
                try 
                {
                    UICCAdminView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 3, bRes);
                break;
                
            case 3:
                // Fileview access tests
                try 
                {
                    // Get fileviews...
                    UICCView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
                    UICCAdminView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
                }
                catch(Exception e)
                {
                    return;
                }
                
                // Sub cases 7.1 : successful FileView access
                // ------------------------------------------
                UICCView.select(UICCTestConstants.FID_DF_TEST);
                UICCView.select(UICCTestConstants.FID_EF_TARR1);
                try 
                {
                    UICCView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 1, bRes);

                // Sub cases 7.2 : Unsuccessful adminFileView access
                // -------------------------------------------------
                UICCAdminView.select(UICCTestConstants.FID_DF_TEST);
                UICCAdminView.select(UICCTestConstants.FID_EF_TARR1);
                try 
                {
                    UICCAdminView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = false;
                }
                catch (UICCException e)
                {
                    bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 2, bRes);

                // Sub cases 7.3 : successful adminFileView access
                // -----------------------------------------------
                UICCAdminView.select(UICCTestConstants.FID_DF_TEST);
                UICCAdminView.select(UICCTestConstants.FID_EF_TARR5);
                try 
                {
                    UICCAdminView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 3, bRes);

                // Sub cases 7.2 : Unsuccessful FileView access
                // --------------------------------------------
                UICCView.select(UICCTestConstants.FID_DF_TEST);
                UICCView.select(UICCTestConstants.FID_EF_TARR5);
                try 
                {
                    UICCView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = false;
                }
                catch (UICCException e)
                {
                    bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 4, bRes);

                // Sub cases 7.5 : Successful FileView access
                // ------------------------------------------
                UICCView.select(UICCTestConstants.FID_DF_TEST);
                UICCView.select(UICCTestConstants.FID_EF_TARR4);
                try 
                {
                    UICCView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 5, bRes);

                // Sub cases 7.6 : Successful adminFileView access
                // -----------------------------------------------
                UICCAdminView.select(UICCTestConstants.FID_DF_TEST);
                UICCAdminView.select(UICCTestConstants.FID_EF_TARR4);
                try 
                {
                    UICCAdminView.readBinary((short)0, abRdBin, (short)0, (short)abRdBin.length);
                    bRes = true;
                }
                catch (Exception e)
                {
                    bRes = false;
                }
                reportTestOutcome((byte) 6, bRes);
                break;
        }
    }
}