// -----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.system.api_3_upf_gvba.api_3_upf_gvba2;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.test.system.api_3_upf_gvba.*;

public class Api_GetShareableClientApplet extends javacard.framework.Applet {

	Api_ShareableInterface myShareObject;

	// Constructor of the applet
	public Api_GetShareableClientApplet() {
	}

    /**
     * Method called by the JCRE at the installation of the applet
     */
	public static void install(byte bArray[], short bOffset, byte bLength) {

		// Create a new applet instance
		Api_GetShareableClientApplet thisApplet = new Api_GetShareableClientApplet();

		// Register the new applet instance to the JCRE
		thisApplet.register(bArray, (short)(bOffset + 1), bArray[bOffset]);
	}

    /**
     * Method called by the JCRE at the selection of the applet
     */
	public void process(APDU apdu) {
		byte[] baServerAid = {(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                              (byte) 0x09, (byte) 0x00, (byte) 0x05, (byte) 0xFF,
                              (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x89,
                              (byte) 0x30, (byte) 0x01, (byte) 0x01, (byte) 0x02};

		AID serverAppletAid = new AID(baServerAid, (short) 0,
				(byte) baServerAid.length);

		myShareObject = (Api_ShareableInterface) JCSystem
				.getAppletShareableInterfaceObject(serverAppletAid, (byte) 0x01);
		myShareObject.getVolByteArray();
	}
}