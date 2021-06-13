//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_trs_gety;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistrySystem class, getEntry() method
 * applet 1
 */
public class Api_2_Trs_Gety_1 extends TestToolkitApplet { 

    boolean bRes;
    byte testCaseNb;

	private ToolkitRegistry Ref;

    /**
     * Constructor of the applet
     * Test Case 1: Call before register() method
     */
    public Api_2_Trs_Gety_1() { 

        testCaseNb = (byte)0x01;
		try {
			Ref = ToolkitRegistrySystem.getEntry();
            if (Ref == null) {
                bRes = true;
            } else {
                bRes = false;
            }
		}
		catch (Exception e) {
            bRes = false;
		}
    }

    
    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) { 
        
        // Create a new applet instance
        Api_2_Trs_Gety_1 thisApplet = new Api_2_Trs_Gety_1();

        // Register the new applet instance to the JCRE            
        thisApplet.register(bArray, (short)(bOffset+1), (byte) bArray[bOffset]);

        // Initialise the data of the test applet
        // test case 2
        thisApplet.init();
        
        // Register test case 1 result
        thisApplet.reportTestOutcome(thisApplet.testCaseNb, thisApplet.bRes);
        
        // check applet ToolkitRegistry reference is not null
        thisApplet.testCaseNb = 0x02;
        if (thisApplet.obReg == null) {
            thisApplet.bRes = false;
        } else {
            thisApplet.bRes = true;
        }
        thisApplet.reportTestOutcome(thisApplet.testCaseNb, thisApplet.bRes);
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }
    

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) { 
        
		// -----------------------------------------------------------------
		// Test Case 3: The applet calls the getEntry() method again
		// -----------------------------------------------------------------
        testCaseNb = (byte)3;
		bRes = false;
		try {            
			bRes = (obReg == ToolkitRegistrySystem.getEntry());
		} 
		catch (Exception e) {
			bRes = false ;
		}
		reportTestOutcome(testCaseNb, bRes) ;
	}
}
