//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.system.api_3_upf_gvba;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.*;
import uicc.system.*;
import javacard.framework.*;
import uicc.test.util.*;


public class Api_3_Upf_Gvba_1 extends TestToolkitApplet 
                              implements Api_ShareableInterface {

    private byte testCaseNb;
    private static boolean bRes;
    static byte[] classVariable;
    byte[] instanceVariable;
    Object[] arrayComponent = new Object[1];

    // Constructor of the applet
    public Api_3_Upf_Gvba_1() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength){

        // Create a new applet instance
        Api_3_Upf_Gvba_1 thisApplet = new Api_3_Upf_Gvba_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();

        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }
    
    
    /** test case 2: Method invoked from a different context */
    public Shareable getShareableInterfaceObject(AID clientAid, byte parameter) {
        byte[] baServerAid = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00,
                              (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                              (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89,
                              (byte)0x30, (byte)0x11, (byte)0x01, (byte)0x02};

        if (((clientAid == null) && (parameter == (byte)0x01)) ||
                ((clientAid != null) && (clientAid.equals(baServerAid, (short)0, (byte)(baServerAid.length)) == true))) {
            return this;
        } else {
            return null;
        }
    }

    public boolean getVolByteArray() {
        byte[] localVariable;
        boolean bRes;
        testCaseNb = (byte)0x05;
        
        bRes= false;        
        try {
            localVariable = UICCPlatform.getTheVolatileByteArray();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);
        return (bRes); 
    }
    

    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        byte[] localVariable;
 
        /** test case 1: Call getTheVolatileByteArray() method */
        testCaseNb = (byte)0x01;
        bRes = false;
        try {
            localVariable = UICCPlatform.getTheVolatileByteArray();
            if (localVariable.length >= 256) {
                bRes = true;
            }
        }
        catch (Exception e) {
            bRes=false;
        }
        reportTestOutcome(testCaseNb,bRes);
       
        /** test case 3: Store the instance in a class variable */
        testCaseNb = (byte)0x02;
        bRes = false;
        try {
            classVariable = UICCPlatform.getTheVolatileByteArray();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 4: Store the instance in an instance variable */
        testCaseNb = (byte)0x03;
        bRes = false;
        try {
            instanceVariable = UICCPlatform.getTheVolatileByteArray();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);

        /** test case 5: Store the instance in an array component */
        testCaseNb = (byte)0x04;
        bRes = false;
        try {
            arrayComponent[0] = UICCPlatform.getTheVolatileByteArray();
        }
        catch (SecurityException e) {
            bRes = true;
        }
        catch (Exception e) {
            bRes = false;
        }
        reportTestOutcome(testCaseNb,bRes);
    }

    private short callNumber = 0;
    
    public void process(APDU apdu) {

        byte[] localVariable;

        if (callNumber++ == 0){
            testCaseNb = (byte)0x06;
            bRes = false;
            try {
                localVariable = UICCPlatform.getTheVolatileByteArray();
                if (localVariable.length >= 256) {
                    bRes = true;
                }
            }
            catch (Exception e) {
                bRes=false;
            }
            reportTestOutcome(testCaseNb,bRes);
        }
        else
            super.process(apdu);
    }
}
