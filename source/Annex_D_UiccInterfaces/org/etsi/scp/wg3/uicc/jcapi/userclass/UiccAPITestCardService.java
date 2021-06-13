/**
 * @author SCP WG3, ETSI
 * @version 0.01
 * UiccAPITestCardService defines method to get an object implementing interfaces
 * This class has to be adapted to proprietary environment...
 */
 
package org.etsi.scp.wg3.uicc.jcapi.userclass;



public class UiccAPITestCardService {

    public static UiccAPITestCardService TestReference = null;

    /**
     * static method to get a reference to UiccAPITestCardService 
     * @returns reference to the UiccAPITestCardService implementation class
     */
    public static UiccAPITestCardService getTheUiccTestCardService() {
        if (TestReference == null) {
            return new UiccAPITestCardService();
        }
        else {
            return TestReference;
        }
    }

}
