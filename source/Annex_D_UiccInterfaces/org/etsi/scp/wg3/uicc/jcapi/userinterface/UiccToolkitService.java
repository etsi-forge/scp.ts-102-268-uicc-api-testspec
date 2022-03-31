/**
 * @author SCP WG3, ETSI
 * @version 0.10.1
 * UiccToolkitService defines Toolkit methods, among them ETSI TS 102 223 specification
 */
package org.etsi.scp.wg3.uicc.jcapi.userinterface;


public interface UiccToolkitService {

    /**
     * perform Terminal Profile APDU command and all fetch/Terminal
     * Response APDU until (90 00) status word
     * @param Terminal Profile command parameters, as String
     * @return APDUResponse Object
     */
    public APDUResponse terminalProfileSession(String tpStr);

    /**
     * perform a Terminal Profile APDU command
     * @param Terminal Profile command parameters, as String
     * @return APDUResponse Object
     */
    public APDUResponse terminalProfile(String tpStr);

    /**
     * perform a fetch APDU command
     * @param length of expected data, as String
     * @return APDUResponse Object
     */
    public APDUResponse fetch(String expDataLength);

    /**
     * perform a terminalResponse
     * @param Terminal Response APDU Data, as String
     * @return APDUResponse Object
     */
    public APDUResponse terminalResponse(String cmd);

    /**
     * perform an Envelope Menu Selection
     * @param Item Identifier TLV, as String
     * @param Help Request TLV, as String
     * @return APDUResponse Object
     *  Send the following string :
     *  "D3 XX 82 02 01 81" +  itemIdTLV + HelpRequestTLV
     */
    public APDUResponse envelopeMenuSelection(
        String itemIdTLV,            // Item Identifier TLV
        String HelpRequestTLV);      // Help request TLV

    /**
     * perform an Envelope Call Control by NAA
     * @return APDUResponse Object
     *  Send the following string :
     *  "D4 11 82 02 82 81 86 02 81 01 93 07 81 F2 A3 34 05 76 67"
     */
    public APDUResponse envelopeCallControlByNAA(); 

    /**
     * perform an Envelope Timer Expiration
     * @param Timer Identifier TLV, as String
     * @return APDUResponse Object
     *  Send the following string :
     *  "D7 0C 82 02 82 81" + TimerIdTLV + "25 03 00 00 00"
     */
    public APDUResponse envelopeTimerExpiration(
        String TimerIdTLV);         // Timer Identifier TLV

    /**
     * perform an Unrecognized Envelope
     * @return APDUResponse Object
     *  Send the following string :
     *  "01 07 82 02 83 81 FF 01 01"
     */
    public APDUResponse unrecognizedEnvelope();

    /**
     * perform an Envelope Event Download MT Call
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 00 82 02 83 81 9C 01 01"
     */
    public APDUResponse envelopeEventDownloadMTCall(); 

    /**
     * perform an Envelope Event Download Call Connected
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 01 82 02 82 81 9C 01 01"
     */
    public APDUResponse envelopeEventDownloadCallConnected(); 

    /**
     * perform an Envelope Event Download Call Disconnected
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 02 82 02 82 81 9C 01 01"
     */
    public APDUResponse envelopeEventDownloadCallDisconnected(); 

    /**
     * perform an Envelope Event Download Location Status
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 03 82 02 82 81 1B 01 01"
     */
    public APDUResponse envelopeEventDownloadLocationStatus(); 

    /**
     * perform an Envelope Event Download User Activity
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 07 99 01 04 82 02 82 81"
     */
    public APDUResponse envelopeEventDownloadUserActivity(); 

    /**
     * perform an Envelope Event Download Idle Screen Available
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 07 99 01 05 82 02 02 81"
     */
    public APDUResponse envelopeEventDownloadIdleScreenAvailable(); 

    /**
     * perform an Envelope Event Download Card Reader Status
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 06 82 02 82 81 20 01 FF"
     */
    public APDUResponse envelopeEventDownloadCardReaderStatus(); 

    /**
     * perform an Envelope Event Download Language Selection
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0B 99 01 07 82 02 82 81 2D 02 46 52"
     */
    public APDUResponse envelopeEventDownloadLanguageSelection(); 

    /**
     * perform an Envelope Event Download Browser Termination
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 08 82 02 82 81 34 01 00"
     */
    public APDUResponse envelopeEventDownloadBrowserTermination(); 

    /**
     * perform an Envelope Event Download Data Available
     * @param Channel Status TLV, as String
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0E 99 01 09 82 02 82 81" + ChannelStatusTLV +  "37 01 0A"
     */
    public APDUResponse envelopeEventDownloadDataAvailable(
        String ChannelStatusTLV);   // Channel Status TLV

    /**
     * perform an Envelope Event Download Channel Status
     * @param Channel Status TLV, as String
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0B 99 01 0A 82 02 82 81" + ChannelStatusTLV
     */
    public APDUResponse envelopeEventDownloadChannelStatus(
        String ChannelStatusTLV);   // Channel Status TLV

    /**
     * perform an Envelope Event Download Access Technology Change
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 0B 82 02 82 81 BF 01 00"
     */
    public APDUResponse envelopeEventDownloadAccessTechnologyChange(); 

    /**
     * perform an Envelope Event Download Display Parameters Changed
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0C 99 01 0C 82 02 82 81 C0 03 10 10 01"
     */
    public APDUResponse envelopeEventDownloadDisplayParametersChanged();

    /**
     * perform an Envelope Event Download Local Connection
     * @param Service Record TLV, as String
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 XX 99 01 0D 82 02 83 81" + ServiceRecordTLV
     */
    public APDUResponse envelopeEventDownloadLocalConnection(
        String ServiceRecordTLV);   // Service Record TLV

    /**
     * perform an Envelope Event Download Network Search Change Mode
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 0E 82 02 82 81 E5 01 00"
     */
    public APDUResponse envelopeEventDownloadNetworkSearchModeChange(); 

    /**
     * perform an Envelope Event Download Browsing Status
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0A 99 01 0F 82 02 82 81 E4 01 00"
     */
    public APDUResponse envelopeEventDownloadBrowsingStatus();
    
    /**
     * perform an Envelope Event Download Frames Inforamation Changed
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 0C 99 01 10 82 02 83 81 E7 03 01 02 03"
     */
    public APDUResponse envelopeEventDownloadFramesInformationChanged();
    
    /**
     * perform an Envelope Event Download HCI Connectivity
     * @return APDUResponse Object
     *  Send the following string :
     *  "D6 07 99 01 13 82 02 82 81"
     */
    public APDUResponse envelopeEventDownloadHCIConnectivity();

    /**
     * perform an Envelope Event to trigger EVENT_PROACTIVE_HANDLER_AVAILABLE.
     * @return APDUResponse Object
     *  Send the following string :
     *  FFS - define the value to be sent
     */
    public APDUResponse envelopeEventProactiveHandlerAvailable();
}
