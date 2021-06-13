//-----------------------------------------------------------------------------
//    Cre_Tin_Nbme_2.java
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



public class Cre_Tin_Nbme_2 extends TestToolkitApplet
{ 
	public static byte [] MenuEntry1 =
						{(byte) 'M',
						(byte) 'e',
						(byte) 'n',
						(byte) 'u',
						(byte) '5' };
	/**
     * Constructor of the applet
     */
	
	public Cre_Tin_Nbme_2()
	{
	} 

	/**
     * Method called by the JCRE at the installation of the applet
     */
	public static void install(byte bArray[], short bOffset, byte bLength)
	{
		boolean bRes = false;
		byte	result = (byte) 0;
		byte	testCaseNb = (byte)1;


        // Create a new applet instance
		Cre_Tin_Nbme_2 thisApplet = new Cre_Tin_Nbme_2();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

		// Initialise the data of the test applet
		thisApplet.init();

		// -----------------------------------------------------------------
		//	Test Case 3 :		initMenuEntry()with parameters: 
		//						MenuEntry = Menu1
		//						Offset = 0
		//						Length = Menu1.length
		//						NextAction = 0
		//						HelpSupported = false
		//						IconQualifier = 0
		//						IconIdentifier = 0
		//						Shall throw a ToolkitException.REGISTRY_ERROR
		// -----------------------------------------------------------------
		try {
			result = thisApplet.obReg.initMenuEntry(Cre_Tin_Nbme_2.MenuEntry1, (byte) 0, 
							(short)Cre_Tin_Nbme_2.MenuEntry1.length, (byte) 0, false, (byte) 0, (short) 0);
		}

		catch (ToolkitException e) {
			bRes = (e.getReason() == ToolkitException.REGISTRY_ERROR);
		}

		catch (Exception e) {
			bRes = false;
		}

		thisApplet.reportTestOutcome(testCaseNb, bRes);
	
	}


    /**
     * Method called by the SIM Toolkit Framework
     */
	public void processToolkit(short event)
	{

	}
 
}