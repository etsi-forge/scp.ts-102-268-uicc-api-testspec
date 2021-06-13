/**
 * @author SCP WG3, ETSI
 * @version 0.10
 * ResponseAPDU Class creates Object handling with APDU responses
 */
package org.etsi.scp.wg3.uicc.jcapi.userinterface;


public interface APDUResponse {

    /**
     * Gives the Status Word returned by the card
     * @return Status Word, as String
     */
    public String getStatusWord();

    /**
     * Gives Data returned by the card
     * @return Data, as String
     */
    public String getData();

    /**
     * Check the received Status Word with expected Status Word
     * @param expectedSw expected StatusWord, as String
     * @return true or false, as boolean
     */
    public boolean checkSw(String expectedSw);

    /**
     * compare APDUResponse Object data with expected data
     * @param expectedData, as String
     * @return true or false, as boolean
     */
    public boolean checkData(String expectedData);

}
