//-----------------------------------------------------------------------------
//Api_1_Fvw_Updb_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_updb;

import javacard.framework.*;
import uicc.access.*;
import uicc.access.fileadministration.AdminFileView;
import uicc.access.fileadministration.AdminFileViewBuilder;
import uicc.test.util.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.access.fileadministration.api_4_afv_updb
 */
public class Api_4_Afv_Updb_1 extends TestToolkitApplet implements UICCConstants
{
    private AdminFileView UiccFileView = null;         

    final short DATA_BUFFER_LENGTH = (short)0x14;

    byte[] data = new byte[DATA_BUFFER_LENGTH];
    byte[] compareBuffer = new byte[DATA_BUFFER_LENGTH];
    byte[] resp = new byte[DATA_BUFFER_LENGTH];

    private short respOffset;
    private short respLength;

    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Updb_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_4_Afv_Updb_1 thisApplet = new Api_4_Afv_Updb_1();
        
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
        short dataOffset = 0;
        short dataLength = 10;
        short fileOffset = 0;

        // Result of tests
        boolean bRes = false;
        byte testCaseNb = 0;
        
        // Get the the Uicc FileView and AdminFileView
        UiccFileView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);

        Util.arrayFillNonAtomic(data,(short)0,(short)data.length,(byte)0);
        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /*-----------------------------------------------------------------------------------
             * TEST CASE 01: Errorcase, No EF selected
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x01;
            bRes = false;
            UiccFileView.select(UICCTestConstants.FID_DF_TEST);
            try {
                data[0] = (byte)0x55;
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);

                bRes = false;
            }
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.NO_EF_SELECTED);
            } 
            catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb,bRes);


            /*------------------------------------------------------------------
             * TEST CASE 02: Update transparent file at a given offset
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x02;
            bRes = false;
            UiccFileView.select(UICCTestConstants.FID_EF_TARU);
            try {
                
                fileOffset = (short)0x03;
                dataOffset = (short)0;
                dataLength = (short)1;

                // Initialise data buffer
                data[0] = (byte)0x55;   
                
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);

                // Read the updated file
                respOffset = (short)0;
                respLength = (short)1;
                Util.arrayFillNonAtomic(resp,(short)0,(short)resp.length,(byte)0);
                UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                
                // Initialise the compare buffer and check the readbinary() result
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0);
                compareBuffer[0] = (byte)0x55;
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;

            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 03: fileOffset = 254
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x03;
            bRes = false;
            try {
                fileOffset = (short)0xFE;
                dataOffset = 0;
                dataLength = 3;
                data[0] = (byte)0x55;
                data[1] = (byte)0xAA;
                data[2] = (byte)0x66;
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);
                
                // Read the updated file
                respOffset = (short)0;
                respLength = (short)3;
                Util.arrayFillNonAtomic(resp,(short)0,(short)resp.length,(byte)0);
                UiccFileView.readBinary(fileOffset, resp, respOffset, respLength);
                
                // Initialise the compare buffer and check the readbinary() result
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0);
                compareBuffer[0] = (byte)0x55;
                compareBuffer[1] = (byte)0xAA;
                compareBuffer[2] = (byte)0x66;
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 04: Errorcase, fileOffset < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x04;
            bRes = false;
            try {
                fileOffset = (short)-1;
                dataOffset = 0;
                dataLength = 10;
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.OUT_OF_FILE_BOUNDARIES);
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 05: Errorcase, fileOffset + dataLength > EF length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x05;
            bRes = false;
            try {
                fileOffset = (short)259;
                dataOffset = 0;
                dataLength = 2;

                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);

                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.OUT_OF_FILE_BOUNDARIES);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 06: Errorcase, data is null
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x06;
            bRes = false;
            try {
                fileOffset = 0;
                dataOffset = 0;
                dataLength = 10;
                UiccFileView.updateBinary(fileOffset, null, dataOffset, dataLength);
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
             * TEST CASE 07: Errorcase, dataOffset < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x07;
            bRes = false;
            try {
                fileOffset = 0;
                dataOffset = (short)-1;
                dataLength = 10;
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);
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
             * TEST CASE 08: Errorcase, dataLength < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x08;
            bRes = false;
            try {
                fileOffset = 0;
                dataOffset = 0;
                dataLength = (short)-1;
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);
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
             * TEST CASE 09: Errorcase, dataOffset + dataLength > data.Length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x09;
            bRes = false;
            try {
                fileOffset = 0;
                dataOffset = 0x0A;
                dataLength = 0x0B;
                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);
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
             * TEST CASE 10: Errorcase, EF is not transparent
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0A;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);

                fileOffset = 0;
                dataOffset = 0;
                dataLength = 1;

                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);

                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.COMMAND_INCOMPATIBLE);
            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 11: Errorcase, Access condition not fulfilled
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0B;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_TNU);

                fileOffset = 0;
                dataOffset = 0;
                dataLength = 1;

                UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);

                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 12: Errorcase, EF is invalidated
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0C;
            bRes = false;
            
            UiccFileView.select(UICCTestConstants.FID_EF_TNR);
            UiccFileView.deactivateFile();

            try {
                 UiccFileView.updateBinary(fileOffset, data, dataOffset, dataLength);
                 bRes = false;
            } 
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.REF_DATA_INVALIDATED);
            }
            finally {
                 UiccFileView.activateFile();
            }

            reportTestOutcome(testCaseNb,bRes);

            /*----------------------------------------------------------------------------------
             * Reset the file content
             *---------------------------------------------------------------------------------*/
            fileOffset = (short)0x00;
            dataOffset = (short)0;
            
            UiccFileView.select(UICCTestConstants.FID_DF_TEST);
            UiccFileView.select(UICCTestConstants.FID_EF_TARU);
            // Initialise data buffer
            byte[] toto = new byte[0x0104];   
            Util.arrayFillNonAtomic(toto,(short)0,(short)toto.length,(byte)0xFF);
            // Reset the file content
            UiccFileView.updateBinary(fileOffset, toto, dataOffset, (short)toto.length);
            
        } 
    }
}
