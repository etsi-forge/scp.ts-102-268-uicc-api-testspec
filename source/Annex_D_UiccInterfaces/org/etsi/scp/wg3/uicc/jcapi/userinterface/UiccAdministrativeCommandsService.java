/**
 * @author SCP WG3, ETSI
 * @version 0.10
 * UiccAdministrativeCommandsService defines administrative methods from ETSI TS 102 222 specification 
 */
package org.etsi.scp.wg3.uicc.jcapi.userinterface;


public interface UiccAdministrativeCommandsService {

    /**
     * create a new file under the current DF or ADF, as described in TS 102 222.
     * @param Value field of the File control parameters TLV, as String
     * @param Logical Channel number, as String
     * @return ResponseAPDU Object
     */
    public APDUResponse createFile(String fcpValue);
    public APDUResponse createFile(String logicalChannel, String fcpValue);
    
    
    /**
     * initiate the deletion of an EF immediately under the current DF,
     * or a DF with its complete subtree, as described in TS 102 222.
     * @param File Id, as String
     * @param Logical Channel number, as String
     * @return ResponseAPDU Object
     */
    public APDUResponse deleteFile(String fileId);
    public APDUResponse deleteFile(String logicalChannel, String fileId);
    
    /**
     * resize a file under the current DF or ADF, as described in TS 102 222.
     * If used with BER TLV file, mode 0 shall be used.
     * @param File Id, as String
     * @param New size, as String
     * @param Logical Channel number, as String
     * @return ResponseAPDU Object
     */
    public APDUResponse resizeFile(String fileId, String size);
    public APDUResponse resizeFile(String logicalChannel, String fileId, String size);
    
}
