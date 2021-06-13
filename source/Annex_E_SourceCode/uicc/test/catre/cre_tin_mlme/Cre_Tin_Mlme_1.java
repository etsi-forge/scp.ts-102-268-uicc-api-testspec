//-----------------------------------------------------------------------------
//    Cre_Tin_Mlme_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_mlme;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Tin_Mlme_1 extends TestToolkitApplet
{

	private static byte [] MenuEntry1 = 
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) 'E',
						(byte) 'n',
						(byte) 't',
						(byte) 'r',
						(byte) 'y',
						(byte) '1'};

	private static byte [] MenuEntry2 = 
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) 'E',
						(byte) 'n',
						(byte) 't',
						(byte) 'r',
						(byte) 'y',
						(byte) '2'};

	private static byte [] MenuEntry3 = 
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) 'E',
						(byte) 'n',
						(byte) 't',
						(byte) 'r',
						(byte) 'y',
						(byte) '3'};
	
	private static byte [] MenuEntry03 = 
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) 'E',
						(byte) 'n',
						(byte) 't',
						(byte) 'r',
						(byte) 'y',
						(byte) '0',
						(byte) '3'};

	private static byte [] MenuEntry4 = 
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) 'E',
						(byte) 'n',
						(byte) 't',
						(byte) 'r',
						(byte) 'y',
						(byte) '4'};

	private static byte [] MenuEntry05 = 
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) 'E',
						(byte) 'n',
						(byte) 't',
						(byte) 'r',
						(byte) 'y',
						(byte) '0',
						(byte) '5'};

	private byte result = (byte) 0;

	private byte testCaseNb = (byte)5;
	

	
    /**
     * Constructor of the applet
     */
    public Cre_Tin_Mlme_1() 
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) 
    {


        // Create a new applet instance
        Cre_Tin_Mlme_1 thisApplet = new Cre_Tin_Mlme_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

 		// Initialise the data of the test applet
        thisApplet.init();
		
		boolean bRes = true;

		// -----------------------------------------------------------------
		//	Test Case 1 :		1-	initMenuEntry()with parameters: 
		//						MenuEntry = MenuEntry1
		//						Offset = 0
		//						Length = MenuEntry1.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						No exception shall be thrown
		// -----------------------------------------------------------------
		thisApplet.testCaseNb = (byte) 1;
		try {
			thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry1, (short) 0, 
							(short)MenuEntry1.length, (byte) 0, false, (byte) 0, (short) 0);
			bRes = true;
		}
		
		catch (Exception e) {
			bRes = false;
		}

		// -----------------------------------------------------------------
		//						2-	initMenuEntry()with parameters: 
		//						MenuEntry = MenuEntry2
		//						Offset = 0
		//						Length = MenuEntry2.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						No exception shall be thrown
		// -----------------------------------------------------------------

		if (bRes == true) {
			try {
				thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry2, (short) 0, 
								(short)MenuEntry2.length, (byte) 0, false, (byte) 0, (short) 0);
			}
			
			catch (Exception e) {
				bRes = false;
			}
		}

		thisApplet.reportTestOutcome(thisApplet.testCaseNb, bRes) ;
	
		// -----------------------------------------------------------------
		//	Test Case 2 :		1-	initMenuEntry()with parameters: 
		//						MenuEntry = MenuEntry03
		//						Offset = 0
		//						Length = MenuEntry03.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						Shall throw a ToolkitException.ALLOWED_LENGTH_EXCEEDED 
		// -----------------------------------------------------------------

		thisApplet.testCaseNb = (byte)2;
		try {
			thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry03, (short) 0, 
							(short)MenuEntry03.length, (byte) 0, false, (byte) 0, (short) 0);
			bRes = false;
		}

		catch (ToolkitException e) {
			bRes = (e.getReason() == ToolkitException.ALLOWED_LENGTH_EXCEEDED);
		}
		
		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(thisApplet.testCaseNb, bRes) ;

		// -----------------------------------------------------------------
		//	Test Case 3 :		1-	initMenuEntry()with parameters: 
		//						MenuEntry = MenuEntry3
		//						Offset = 0
		//						Length = MenuEntry3.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						No exception shall be thrown
		// -----------------------------------------------------------------

		thisApplet.testCaseNb = (byte)3;
		try {
			thisApplet.result = thisApplet.obReg.initMenuEntry(MenuEntry3, (byte) 0, 
							(short)MenuEntry3.length, (byte) 0, false, (byte) 0, (short) 0);
			bRes = true;
		}
		
		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(thisApplet.testCaseNb, bRes) ;
    }
    


    /**
     * Method called by the UICC CRE
     */
    public void processToolkit(short event) 
    {
		boolean bRes = true;


		if (event == EVENT_MENU_SELECTION) {
            EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
            
            switch (envHdlr.getItemIdentifier())
            {
                case (byte)1:
                    // -----------------------------------------------------------------
                    //  Test Case 4 :       1-  changeMenuEntry()with parameters:
                    //                      Id = 1
                    //                      MenuEntry = MenuEntry4
                    //                      Offset = 0
                    //                      Length = MenuEntry4.length
                    //                      NextAction = 0
                    //                      HelpSupported = false
                    //                      IconQualifier = 0
                    //                      IconIdentifier = 0
                    //                      No exception shall be thrown
                    // -----------------------------------------------------------------

                    if ( testCaseNb == (byte)3 ) {

                        testCaseNb = (byte)4;
                        try {
                            obReg.changeMenuEntry((byte)1, MenuEntry4, (short)0, (short)MenuEntry4.length, 
                                        (byte) 0, false, (byte) 0, (short) 0);
                        bRes = true;
                        }

                        catch (Exception e) {
                            bRes = false;
                        }

                        reportTestOutcome(testCaseNb, bRes) ;
                
                    }
                    break;
                case (byte)2:
                    // -----------------------------------------------------------------
                    //  Test Case 5 :       1-  changeMenuEntry()with parameters:
                    //                      Id = 1
                    //                      MenuEntry = MenuEntry05
                    //                      Offset = 0
                    //                      Length = MenuEntry05.length
                    //                      NextAction = 0
                    //                      HelpSupported = false
                    //                      IconQualifier = 0
                    //                      IconIdentifier = 0
                    //                      Shall throw a ToolkitException.ALLOWED_LENGTH_EXCEEDED
                    // -----------------------------------------------------------------

                    if ( testCaseNb == (byte)4 ) {

                        testCaseNb = (byte)5;
                        bRes = false;

                        try {
                            obReg.changeMenuEntry((byte)1, MenuEntry05, (short)0, (short)MenuEntry05.length, 
                                        (byte) 0, false, (byte) 0, (short) 0);
                        }

                        catch (ToolkitException e) {
                            if (e.getReason() == ToolkitException.ALLOWED_LENGTH_EXCEEDED) {
                                bRes = true;
                            }
                        }   
                
                        catch (Exception e) {
                            bRes = false;
                        }

                        reportTestOutcome(testCaseNb, bRes) ;   
                    }
                    break;
            }
		}
	}
}


