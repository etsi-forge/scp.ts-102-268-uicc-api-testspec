//-----------------------------------------------------------------------------
//    Cre_Pcs_Spco_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_spco;

import javacard.framework.Util;
import uicc.toolkit.* ;
import uicc.test.util.* ;

public class Cre_Pcs_Spco_1 extends TestToolkitApplet
{

  	private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
 	private static byte[] Text1 = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'1'} ;
    private static byte testCaseNb = (byte) 0x00;
    private static boolean bRes = false;
    private static byte nbMenuTriggering = (byte)0;
    private static byte nbEnvTriggering = (byte)0;
    private static boolean firstInstall = true;
    private static byte[] baLocalTestsResults = new byte[128];


	/**
     * Constructor of the applet
     */
    public Cre_Pcs_Spco_1()
    {
    }


    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {

        // Create a new applet instance.
        Cre_Pcs_Spco_1 thisApplet = new Cre_Pcs_Spco_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();

        // -----------------------------------------------------------------
        // Test Case 1 : init MenuEntrys and Send SET UP MENU after Install
        // -----------------------------------------------------------------
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0, (short)MenuInit.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.setEvent((short) EVENT_EVENT_DOWNLOAD_MT_CALL);
        thisApplet.obReg.setEvent((short) EVENT_EVENT_DOWNLOAD_LOCATION_STATUS);
        thisApplet.obReg.requestPollInterval(POLL_SYSTEM_DURATION);
        if (firstInstall)
        {
            firstInstall = false;
            testCaseNb=(byte)0x01;
            Util.arrayFillNonAtomic(baLocalTestsResults, (short)0, (short)baLocalTestsResults.length, (byte)0x00);
            baLocalTestsResults[0] = (byte)testCaseNb;
            baLocalTestsResults[1] = (byte)0xCC;
        }
        else
        {
            thisApplet.obReg.setEvent((short) EVENT_UNRECOGNIZED_ENVELOPE);
        }

    }

    /**
     * Method reportLocalTestOutcome() is used to save result throw applet deletion
     * @param testCaseNumber
     * @param testCaseResult
     */
    protected void reportLocalTestOutcome(byte testCaseNumber, boolean testCaseResult) 
    {
        // Update the total number of tests executed
        baLocalTestsResults[0] = testCaseNumber;

        // Set the Test Case Result byte to 0xCC (for Card Compliant...) if successful
        if (testCaseResult) {
            baLocalTestsResults[testCaseNumber] = (byte)0xCC;
        }
        else {
            baLocalTestsResults[testCaseNumber] = (byte)0x00;
        }
        
        for (byte n = 1; n <= testCaseNumber; n++)
            reportTestOutcome(n, (boolean)(baLocalTestsResults[n] == (byte)0xCC));
    }

    public void processToolkit(short event)
    {
        bRes = false;
        
		switch (event) 
        {
            case EVENT_MENU_SELECTION:
                nbMenuTriggering++;
                switch (nbMenuTriggering)
                {
                    case 1:
                        // -----------------------------------------------------------------
                        // Test Case 4 :    disable menu
                        // -----------------------------------------------------------------
                        obReg.disableMenuEntry((byte)0x01);
                        testCaseNb = (byte)2;
                        bRes = true;
                        break;
                    case 2:
                        // -----------------------------------------------------------------
                        // Test Case 8 :    clear EVENT_EVENT_DOWNLOAD_MT_CALL
                        // -----------------------------------------------------------------
                        obReg.clearEvent((short) EVENT_EVENT_DOWNLOAD_MT_CALL);
                        testCaseNb = (byte)3;
                        bRes = true;
                        break;
                    case 3:
                        // -----------------------------------------------------------------
                        // Test Case 8 :    set EVENT_EVENT_DOWNLOAD_MT_CALL
                        // -----------------------------------------------------------------
                        obReg.setEvent((short) EVENT_EVENT_DOWNLOAD_MT_CALL);
                        testCaseNb = (byte)5;
                        bRes = true;
                        break;
                }
                break;
                
            case EVENT_EVENT_DOWNLOAD_MT_CALL:
                obReg.enableMenuEntry((byte)0x01);
                testCaseNb = (byte)4;
                bRes = true;
                break;
                
            case EVENT_EVENT_DOWNLOAD_LOCATION_STATUS:
                obReg.clearEvent(EVENT_EVENT_DOWNLOAD_LOCATION_STATUS); 
                testCaseNb = (byte)6;
                bRes = true;
                break;
            
            case EVENT_STATUS_COMMAND:
                // -----------------------------------------------------------------
                // Test Case 12 :
                // -----------------------------------------------------------------
                obReg.requestPollInterval(POLL_NO_DURATION);
                testCaseNb = (byte)7;
                bRes = true;
                break;

            case EVENT_UNRECOGNIZED_ENVELOPE:
                nbEnvTriggering++;
                if (nbEnvTriggering == 1)
                {
                    // -----------------------------------------------------------------
                    // Test Case 12 :
                    // -----------------------------------------------------------------
                    obReg.requestPollInterval(POLL_SYSTEM_DURATION);
                    testCaseNb = (byte)8;
                    bRes = true;
                }
                else
                {
                    // -----------------------------------------------------------------
                    // Test Case 13 :
                    // -----------------------------------------------------------------
                    obReg.clearEvent(EVENT_EVENT_DOWNLOAD_MT_CALL);
                    obReg.disableMenuEntry((byte)1);
                    obReg.requestPollInterval(POLL_NO_DURATION);
                    obReg.clearEvent(EVENT_UNRECOGNIZED_ENVELOPE);
                    
                    ProactiveHandler proHdlr  = ProactiveHandlerSystem.getTheHandler() ;
                    proHdlr.initDisplayText((byte)0x00, (byte)0x04 /*DCS_8_BIT_DATA*/, Text1, (short)0, (short)Text1.length) ;
                    proHdlr.send() ;
                    testCaseNb = (byte)9;
                    bRes = true;
                }
                break;
                
            default:
                testCaseNb = (byte)10;
                bRes = false;
        }
        reportLocalTestOutcome(testCaseNb, bRes);
    }

}

