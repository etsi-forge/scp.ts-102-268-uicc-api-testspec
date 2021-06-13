//-----------------------------------------------------------------------------
//    Cre_Tin_Nbme_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_nbme;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Tin_Nbme_1 extends TestToolkitApplet
{ 
	public static byte [] MenuEntry1 =
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) '1' };

	public static byte [] MenuEntry2 =
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) '2' };

	public static byte [] MenuEntry3 =
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) '3' };

	public static byte [] MenuEntry4 =
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) '4' };

	/**
     * Constructor of the applet
     */
	
	public Cre_Tin_Nbme_1()
	{
	} 

	/**
     * Method called by the JCRE at the installation of the applet
     */
	public static void install(byte bArray[], short bOffset, byte bLength)
	{
		boolean bRes = true;
		byte	result = (byte) 0;
		byte	testCaseNb = (byte)1;


        // Create a new applet instance
		Cre_Tin_Nbme_1 thisApplet = new Cre_Tin_Nbme_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

		// Initialise the data of the test applet
		thisApplet.init();

		// -----------------------------------------------------------------
		//	Test Case 1 :		1-	initMenuEntry()with parameters: 
		//						MenuEntry = Menu1
		//						Offset = 0
		//						Length = Menu1.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						No exception shall be thrown
		// -----------------------------------------------------------------
		try {
			result = thisApplet.obReg.initMenuEntry(MenuEntry1, (byte) 0, 
							(short)MenuEntry1.length, (byte) 0, false, (byte) 0, (short) 0);
		}

		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(testCaseNb, bRes);
		testCaseNb = (byte)2;

		// -----------------------------------------------------------------
		//						2-	initMenuEntry()with parameters: 
		//						MenuEntry = Menu2
		//						Offset = 0
		//						Length = Menu2.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						No exception shall be thrown
		// -----------------------------------------------------------------
		try {
			result = thisApplet.obReg.initMenuEntry(MenuEntry2, (byte) 0, 
							(short)MenuEntry2.length, (byte) 0, false, (byte) 0, (short) 0);
		}

		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(testCaseNb, bRes);
		testCaseNb = (byte)3;

		// -----------------------------------------------------------------
		//						3-	initMenuEntry()with parameters: 
		//						MenuEntry = Menu3
		//						Offset = 0
		//						Length = Menu3.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						No exception shall be thrown
		// -----------------------------------------------------------------
		try {
			result = thisApplet.obReg.initMenuEntry(MenuEntry3, (byte) 0, 
							(short)MenuEntry3.length, (byte) 0, false, (byte) 0, (short) 0);
		}

		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(testCaseNb, bRes);
		testCaseNb = (byte)4;

		// -----------------------------------------------------------------
		//	Test Case 2 :		initMenuEntry()with parameters: 
		//						MenuEntry = Menu4
		//						Offset = 0
		//						Length = Menu4.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						Shall throw a ToolkitException.REGISTRY_ERROR
		// -----------------------------------------------------------------
		try {
			result = thisApplet.obReg.initMenuEntry(MenuEntry4, (byte) 0, 
							(short)MenuEntry4.length, (byte) 0, false, (byte) 0, (short) 0);
		}

		catch (ToolkitException e) {
			bRes = (e.getReason() == ToolkitException.REGISTRY_ERROR);
		}

		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(testCaseNb, bRes);
		testCaseNb++;

	}


    /**
     * Method called by the UICC CRE
     */
	public void processToolkit(short event)
	{
		
	}
 
}