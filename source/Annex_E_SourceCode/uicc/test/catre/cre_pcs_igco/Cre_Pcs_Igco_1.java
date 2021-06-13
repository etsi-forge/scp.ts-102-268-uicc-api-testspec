//-----------------------------------------------------------------------------
//    Cre_Pce_Igco_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_igco;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;

/**
 * Test Area : sim.framework...
 *
 * Applet is triggered on Install (Install), EVENT_MENU_SELECTION
 *
 * @version 0.0.1 - 15/07/01
 *
 */

public class Cre_Pcs_Igco_1 extends TestToolkitApplet
{

 	private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};

	private static byte   IDmenu01;
	private static byte   IDmenu02;

	byte[] Test = {(byte)'T',(byte)'e',(byte)'s',(byte)'t',(byte)' ',(byte)'A'} ;

	/**
     * Constructor of the applet
     */
    public Cre_Pcs_Igco_1()
    {
	}

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Pcs_Igco_1 thisApplet = new Cre_Pcs_Igco_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // toolkit registration
        IDmenu01 = thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,
                                            (short) MenuInit.length, (byte) 0, false,
                                            (byte) 0, (short) 0);
        MenuInit [4] = (byte) '2';
        IDmenu02 = thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,
                                            (short) MenuInit.length, (byte) 0, false,
                                            (byte) 0, (short) 0);
   }


    public void processToolkit(short event)
    {
	    // Number of tests
	    byte testCaseNb = (byte)0;
	    // Result of tests
	    boolean bRes = false;


        // Get the system instance of the ProactiveHandler class
        ProactiveHandler proHdlr  = ProactiveHandlerSystem.getTheHandler() ;
		EnvelopeHandler  envHdlr  = EnvelopeHandlerSystem.getTheHandler();

		if (event !=  EVENT_MENU_SELECTION)
          	return;

		   // check menu ID
		   byte menuID = envHdlr.getItemIdentifier();

		  // -----------------------------------------------------------------
		  // Test Case 2 :	 Menu Item 01
		  // -----------------------------------------------------------------
		  if( menuID == IDmenu01 )
		   	{
			testCaseNb = (byte) 1;
			Test[5] = (byte) 'A';
			proHdlr.initDisplayText((byte)0x00, (byte)0x04 /*DCS_8_BIT_DATA*/, Test, (short)0, (short)Test.length) ;
            proHdlr.send() ;

			bRes = true;
			}

		  // -----------------------------------------------------------------
		  // Test Case 3 :	Menu Item 02
		  // -----------------------------------------------------------------
		  if( menuID == IDmenu02 )
			{
			testCaseNb = (byte) 2;
			Test[5] = (byte) 'B';
			proHdlr.initDisplayText((byte)0x00, (byte)0x04 /*DCS_8_BIT_DATA*/, Test, (short)0, (short)Test.length) ;
            proHdlr.send() ;

			Test[5] = (byte) 'C';
			proHdlr.initDisplayText((byte)0x00, (byte)0x04 /*DCS_8_BIT_DATA*/, Test, (short)0, (short)Test.length) ;
            proHdlr.send() ;

			bRes = true;
			}

		reportTestOutcome(testCaseNb, bRes);

		}

}
