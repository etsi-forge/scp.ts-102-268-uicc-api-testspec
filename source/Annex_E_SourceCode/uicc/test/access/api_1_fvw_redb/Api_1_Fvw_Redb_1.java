//-----------------------------------------------------------------------------
//Api_1_Fvw_Redb_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_redb;

import javacard.framework.*;
import uicc.access.*;
import uicc.test.util.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.access.api_1_fvw_redb
 */

public class Api_1_Fvw_Redb_1 extends TestToolkitApplet implements UICCConstants
{
    private FileView UiccFileView = null;         

    final short RESP_BUFFER_LENGTH = (short)0x0104;

    byte[] resp = new byte[RESP_BUFFER_LENGTH];
    byte[] compareBuffer = new byte[RESP_BUFFER_LENGTH];
    byte[] nullBuffer = null;

    /**
     * Constructor of the applet
     */
    public Api_1_Fvw_Redb_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_1_Fvw_Redb_1 thisApplet = new Api_1_Fvw_Redb_1();
        
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        
        // Initialise the data of the test applet.
        thisApplet.init();
        
        // toolkit registration
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
    }

    /**
     * Method called by the Cat Re
     */
    public void processToolkit(short event) 
    {
        short result = 0;
        short respOffset = 0;
        short respLength = 0;
        short fileOffset = 0;

        // Result of tests
        boolean bRes = false;
        byte testCaseNb = 0;
        
        // Get the the Uicc FileView and AdminFileView
        UiccFileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);

        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /*------------------------------------------------------------------
             * TEST CASE 01: Read from a transparent file at offset 0
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x01;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_TARU);

                fileOffset = 0;
                respOffset = 10;
                respLength = 250;
                
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0xFF);
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)10,(byte)0x55);
                // Initialise resp buffer
                Util.arrayFillNonAtomic(resp,(short)0,(short)resp.length,(byte)0x55);

                // Call the readBinary() method
                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 02: Read from a transparent file at a given offset
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x02;
            bRes = false;
            try {
                
                fileOffset = (short)0x80;
                respOffset = (short)0x80;
                respLength = 5;

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)respOffset,(short)respLength,(byte)0xFF);

                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 03: Errorcase, fileOffset is negative
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x03;
            bRes = false;
            try {
                fileOffset = (short)-1;
                respOffset = 0;
                respLength = 10;
                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.OUT_OF_FILE_BOUNDARIES);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 04: Errorcase, fileOffset + respLength > EF length
             *------------------------------------------------------------------*/
            testCaseNb = (byte)0x04;
            bRes = false;
            try {
                fileOffset = (short)259;
                respOffset = 0;
                respLength = 2;

                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);

                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.OUT_OF_FILE_BOUNDARIES);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 05: Errorcase, resp is null
             *------------------------------------------------------------------*/
            testCaseNb = (byte)0x05;
            bRes = false;
            try {
                fileOffset = 0;
                respOffset = 0;
                respLength = 10;
                result = UiccFileView.readBinary(fileOffset, nullBuffer, respOffset, respLength);
                bRes = false;
            }
            catch (NullPointerException npe) {
                 bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 06: Errorcase, respOffset < 0
             *-------------------------------------------------------------------*/
            testCaseNb = (byte)0x06;
            bRes = false;
            try {
                fileOffset = 0;
                respOffset = (short)-1;
                respLength = 10;
                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                bRes = false;
            } 
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 07: Errorcase, respLength < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x07;
            bRes = false;
            try {
                fileOffset = 0;
                respOffset = 0;
                respLength = (short)-1;
                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                bRes = false;
            } 
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 08: Errorcase, respOffset + respLength > resp.Length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x08;
            bRes = false;
            try {
                fileOffset = 0;
                respOffset = 0xFA;
                respLength = 0x0B;
                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                bRes = false;
            } 
            catch (ArrayIndexOutOfBoundsException e) {
                bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 09: Errorcase, EF is not transparent
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x09;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);

                fileOffset = 0;
                respOffset = 0;
                respLength = 1;

                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);

                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.COMMAND_INCOMPATIBLE);
            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 10: Errorcase, Access condition not fulfilled
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0A;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_TNR);

                fileOffset = 0;
                respOffset = 0;
                respLength = 1;

                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);

                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 11: Errorcase, EF is invalidated
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0B;
            bRes = false;
            
            UiccFileView.select(UICCTestConstants.FID_EF_TNU);
            UiccFileView.deactivateFile();

            try {
                 result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                 bRes = false;
            } 
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.REF_DATA_INVALIDATED);
            }
            finally {
                 UiccFileView.activateFile();
            }

            reportTestOutcome(testCaseNb,bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 12: Errorcase, No EF selected
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0C;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);

                result = UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);

                bRes = false;
            }
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.NO_EF_SELECTED);
            } 
            catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb,bRes);
            
        } 
    }
}
