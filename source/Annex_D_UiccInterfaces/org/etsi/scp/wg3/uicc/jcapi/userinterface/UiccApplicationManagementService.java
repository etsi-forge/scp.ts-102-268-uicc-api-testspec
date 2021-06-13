/**
 * @author SCP WG3, ETSI
 * @version 0.10
 * UiccApplicationManagementService defines methods to manage applications
 */
package org.etsi.scp.wg3.uicc.jcapi.userinterface;


public interface UiccApplicationManagementService {

    /**
     * load cap file in the card
     * @param relative path of the cap file, as String
     * @return ResponseAPDU Object
     */
    public APDUResponse loadPackage(String capFilePath);

    /**
     * install and makeSelectable an instance in the card
     * @param relative path of the cap file, as String
     * @param Aid of the class, as String
     * @param Aid of the instance, as String
     * @param UICC System Specific TLV as defined in 102 226 specification, as String
     * @return ResponseAPDU Object, corresponding to the GlobalPlatform command response.
     */
    public APDUResponse installApplet(String capFilePath, String classAID, String appAID, String uiccSystemSpecificParameters);

    /**
     * install an applet in installed state
     * @param relative path of the cap file, as String
     * @param AID of the class, as String
     * @param AID of the instance, as String
     * @param UICC System Specific TLV as defined in 102 226 specification, as String
     * @return ResponseAPDU Object, corresponding to the GlobalPlatform command response.
     */
    public APDUResponse installInstallApplet(String capFilePath, String classAID, String appAID, String uiccSystemSpecificParameters);

    /**
     * make an applet selectable (install for make selectable)
     * @param AID of the instance, as String
     * @return ResponseAPDU Object, corresponding to the GlobalPlatform command response.
     */
    public APDUResponse makeSelectableApplet(String appAID);
    
    /**
     * perform a SelectApplication APDU command
     * @param AID of instance, as String
     * @param Logical Channel number, as String
     * @return ResponseAPDU Object, containing the application response
     */
    public APDUResponse selectApplication(String AID);
	public APDUResponse selectApplication(String LogicalChannel, String AID);

    /**
     * lock an application
     * @param AID of application, as String
     * @return ResponseAPDU Object, containing the application response
     */
    public APDUResponse lockApplication(String appletAid);

    /**
     * unlock an application
     * @param AID of application, as String
     * @return ResponseAPDU Object, containing the application response
     */
    public APDUResponse unlockApplication(String appletAid);

    /**
     * delete an applet specified by its AID
     * @param AID of applet, as String
     * @return ResponseAPDU Object
     */
    public APDUResponse deleteApplet(String AID);

    /**
     * delete a package specified by its path
     * @param relative path of package, as String
     * @return ResponseAPDU Object
     */
    public APDUResponse deletePackage(String capFilePath);

    /**
     * delete a package specified by its path, and all its related applications
     * @param relative path of package, as String
     * @return APDUResponse Object
     */
    public APDUResponse deletePackageAndAllRelatedApplications(String capFilePath);
}
