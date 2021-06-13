//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.util;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import javacard.framework.*;

/**
 * Definition of the super class of all the test applets.
 * The result of each and every test case is stored in an array.
 * The results of the test can be obtained by selecting the test applet :
 * the applet ID and the test cases results are then returned in the response data.
 */
abstract public class TestToolkitApplet extends javacard.framework.Applet implements 
ToolkitInterface, ToolkitConstants {

    /*
     * Generic variables for all test applets
     */
    /**    version          Java Card 2.2 version */
    private final static short VERSION = 0x0202;

    /**    obReg            ToolktRegistry object */
    protected ToolkitRegistry obReg;

    /**
     *    baTestAppletId    byte array to store the identifier of the test applet,
     *                    the first byte is the length of the AID.
     */
    private byte[]    baTestAppletId = new byte[17];

    /**
     *    baTestsResults    byte array containing the result of each and every test case,
     *                    the first byte is the number of test cases.
     */
    private byte[]    baTestsResults = new byte[128];


    /**
     * Constructor of the applet
     */
    public TestToolkitApplet() {
    }

    /**
     * Method called by the sub classes to initialize the AID
     */
    public void init() throws SystemException {

        // Register to the UICC Toolkit Registry. Shall be called after register() method invocation.
        this.obReg = ToolkitRegistrySystem.getEntry();

        if (JCSystem.getVersion() < TestToolkitApplet.VERSION) {
            SystemException.throwIt((short)SystemException.ILLEGAL_VALUE);
        }
        // Get the AID value
        this.baTestAppletId[0] = JCSystem.getAID().getBytes(this.baTestAppletId, (short)1);
        Util.arrayFillNonAtomic(this.baTestsResults, (short)0, (short)this.baTestsResults.length, (byte)0x00);
    }

    /**
     * Method called by the Toolkit Framework
     *
     * @param clientAID the AID of the calling application
     * @param parameter
     */
    public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) {
        // According to CAT Runtime Environment behaviour for ToolkitInterface object retrieval
        if ((clientAID == null) && (parameter == (byte)0x01)) {
            return((Shareable) this);
        } else {
            return(null);
        }
    }

    /**
     * Method called by the test applet to report the result of each test case
     *
     * @param testCaseNumber test case number
     * @param result true if successful, false otherwise
     */
    protected void reportTestOutcome(byte testCaseNumber, boolean testCaseResult) {
        // Update the total number of tests executed
        this.baTestsResults[0] = testCaseNumber;

        // Set the Test Case Result byte to 0xCC (for Card Compliant...) if successful
        if (testCaseResult) {
            this.baTestsResults[testCaseNumber] = (byte)0xCC;
        }
        else {
            this.baTestsResults[testCaseNumber] = (byte)0x00;
        }
    }

    /**
     * Method called by the JCRE, once selected
     * This method allows to retrieve the detailed results of the previous execution
     * may be identical for all tests
     */
    public void process(APDU apdu) {
        if (selectingApplet()) {
            /* Construct and send the results of the tests */
            apdu.setOutgoing();
            apdu.setOutgoingLength((short)((short)(this.baTestAppletId[0] + this.baTestsResults[0]) + 
                                   (short)2));
            apdu.sendBytesLong(this.baTestAppletId, (short)0, (short)((short)(this.baTestAppletId[0]) + (short)1));
            apdu.sendBytesLong(this.baTestsResults, (short)0, (short)((short)(this.baTestsResults[0]) + (short)1));
        }
        else {
            ISOException.throwIt(javacard.framework.ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }
}
