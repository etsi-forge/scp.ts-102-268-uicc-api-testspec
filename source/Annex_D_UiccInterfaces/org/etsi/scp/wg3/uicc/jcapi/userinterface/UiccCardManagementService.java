/**
 * @author SCP WG3, ETSI
 * @version 0.10
 * UiccCardManagementService defines Toolkit methods, among them ETSI TS 102 221 specification
 */
package org.etsi.scp.wg3.uicc.jcapi.userinterface;


public interface UiccCardManagementService {

    static final String SW_OK = "9000";                     // normal ending of the command
    static final String DEFAULT_TERMINAL_PROFILE = "0901";  // Default Terminal Profile
    public final static String BASIC_CHANNEL = "00";
    public final static String LOGICAL_CHANNEL_1 = "01";
    public final static String LOGICAL_CHANNEL_2 = "02";
    public final static String LOGICAL_CHANNEL_3 = "03";
    
    public final static String APPLICATION_PIN_1 = "01";
    public final static String APPLICATION_PIN_2 = "02";
    public final static String APPLICATION_PIN_3 = "03";
    public final static String APPLICATION_PIN_4 = "04";
    public final static String APPLICATION_PIN_5 = "05";
    public final static String APPLICATION_PIN_6 = "06";
    public final static String APPLICATION_PIN_7 = "07";
    public final static String APPLICATION_PIN_8 = "08";
    public final static String ADM1 = "0A";
    public final static String ADM2 = "0B";
    public final static String ADM3 = "0C";
    public final static String ADM4 = "0D";
    public final static String ADM5 = "0E";
    public final static String LOCAL_PIN_1 = "81";
    public final static String LOCAL_PIN_2 = "82";
    public final static String LOCAL_PIN_3 = "83";
    public final static String LOCAL_PIN_4 = "84";
    public final static String LOCAL_PIN_5 = "85";
    public final static String LOCAL_PIN_6 = "86";
    public final static String LOCAL_PIN_7 = "87";
    public final static String LOCAL_PIN_8 = "88";
    public final static String ADM6  = "8A";
    public final static String ADM7  = "8B";
    public final static String ADM8  = "8C";
    public final static String ADM9  = "8D";
    public final static String ADM10 = "8E";


    /**
     * resets and powers the card
     * @return ATR, as String
     */
    public String reset();

    /**
     * select a file
     * @param File Id, as String
     * @param Logical Channel number, as String
     * @return FCP, as APDUResponse Object
     */
    public APDUResponse selectFile(String FileId);
    public APDUResponse selectFile(String LogicalChannel, String FileId);

     /**
      * select an application for activation
      * @param DFname, as String
      * @param LogicalChannel number, as String
      * @return FCP, as APDUResponse Object
     */
    public APDUResponse selectForActivation(String DFname);
    public APDUResponse selectForActivation(String LogicalChannel, String DFname);

     /**
      * select an application for termination
      * @param DFname, as String
      * @param Logical Channel number, as String
      * @return FCP, as APDUResponse Object
     */
    public APDUResponse selectForTermination(String DFname);
    public APDUResponse selectForTermination(String LogicalChannel, String DFname);

    /**
     * activate a file
     * @param file Id, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse activate(String FileId);
    public APDUResponse activate(String LogicalChannel, String FileId);

    /**
     * deactivate a file
     * @param File Id, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse deactivate(String FileId);
    public APDUResponse deactivate(String LogicalChannel, String FileId);

    /**
     * open and close logical channels
     * @param Operation Code (open/close logical channel), as String
     * @param ChannelNb (0 to 3), as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse manageChannel(String OperationCode, String ChannelNb);
    public APDUResponse manageChannel(String LogicalChannel, String OperationCode, String ChannelNb);

    /**
     * send a status APDU
     * @param Application Status P1 , as String
     * @param Returned Data P2, as String
     * @param Length of the expected data, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse status(String AppStatus, String ReturnedData, String DataLength);
    public APDUResponse status(String LogicalChannel, String AppStatus, String ReturnedData, String DataLength);

    /**
     * reads the data bytes of the current transparent EF
     * @param offset, as short
     * @param length to be read, as short
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse readBinary(String sfiOffset, String length);
    public APDUResponse readBinary(String logicalChannel, String sfiOffset, String length);

    /**
     * reads a record of the current linear fixed/cyclic EF
     * @param record number, as short
     * @param record offset, as short
     * @param length to be read, as short
     * @param mode for reading record
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse readRecord(String recordNb, String mode, String length);
    public APDUResponse readRecord(String logicalChannel, String recordNb, String mode, String length);


    /**
     * updates the data bytes of the current transparent EF
     * @param offset, as short
     * @param length to be updated, as short
     * @param source data to update, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse updateBinary(String sfiOffset, String data);
    public APDUResponse updateBinary(String logicalChannel, String sfiOffset, String data);


    /**
     * updates the data bytes of the record of the current linear fixed/cyclic EF
     * @param record number
     * @param record offset, as short
     * @param lenght to be updated, as short
     * @param mode for updating record, as byte
     * @param source data to update, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse updateRecord(String recordNb, String mode, String data);
    public APDUResponse updateRecord(String logicalChannel, String recordNb, String mode, String data);

    /**
     * increases the current record of the current cyclic EF
     * @param increase value
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse increase(String incValue);
    public APDUResponse increase(String logicalChannel, String incValue);
    
     /**
     * verify a PIN key
     * @param Key reference, as byte
     * @param value of the PIN key, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse verifyPIN(String keyRef, String PINValue);
    public APDUResponse verifyPIN(String logicalChannel, String keyRef, String PINValue);
    
    /**
     * Change the value of a PIN key
     * @param Key reference, as byte
     * @param old value of the PIN key, as String
     * @param new value of the PIN key, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse changePIN(String keyRef, String OldPINValue, String NewPINValue);
    public APDUResponse changePIN(String logicalChannel, String keyRef, String OldPINValue, String NewPINValue);

    /**
     * Enable a PIN key
     * @param Key reference, as byte
     * @param value of the PIN key, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse enablePIN(String keyRef, String PINValue);
    public APDUResponse enablePIN(String logicalChannel, String keyRef, String PINValue);
    
    /**
     * Disable a PIN key
     * @param Key reference, as byte
     * @param value of the PIN key, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse disablePIN(String keyRef, String PINValue);
    public APDUResponse disablePIN(String logicalChannel, String keyRef, String PINValue);
    
    /**
     * Unblock a PIN key
     * @param Key reference, as byte
     * @param unblock value of the PIN key, as String
     * @param value of the PIN key, as String
     * @param Logical Channel number, as String
     * @return APDUResponse Object
     */
    public APDUResponse unblockPIN(String keyRef, String unblockPINValue, String PINValue);
    public APDUResponse unblockPIN(String logicalChannel, String keyRef, String unblockPINValue, String PINValue);
    

    /**
     * sends any kind of APDU command
     * @param apdu command, as String
     * @return APDUResponse Object
     */
    public APDUResponse sendApdu(String apduCommand);

}
