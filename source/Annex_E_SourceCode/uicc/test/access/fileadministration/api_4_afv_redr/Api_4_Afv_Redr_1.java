//-----------------------------------------------------------------------------
//Api_4_Afv_Redr_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_redr;

import javacard.framework.*;
import uicc.access.*;
import uicc.access.fileadministration.AdminFileView;
import uicc.access.fileadministration.AdminFileViewBuilder;
import uicc.test.util.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.access.api_4_Afv_redr
 */
public class Api_4_Afv_Redr_1 extends TestToolkitApplet implements UICCConstants
{
    private AdminFileView UiccFileView = null;         

    final short RESP_BUFFER_LENGTH = (short)0x14;

    byte[] resp = new byte[RESP_BUFFER_LENGTH];
    byte[] compareBuffer = new byte[RESP_BUFFER_LENGTH];
    byte[] nullBuffer = null;

    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Redr_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_4_Afv_Redr_1 thisApplet = new Api_4_Afv_Redr_1();
        
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
        short recOffset = 0;
        byte recNumber = 0;
        // Result of tests
        boolean bRes = false;
        byte testCaseNb = 0;
        
        // Get the the Uicc FileView and AdminFileView
        UiccFileView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);

        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /*-----------------------------------------------------------------------------------
             * TEST CASE 01: Errorcase, No EF selected
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x01;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                recNumber = 1;
                respOffset = 0;
                respLength = 0x0A;
                
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);

                bRes = false;
            }
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.NO_EF_SELECTED);
            } 
            catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb,bRes);

            /*----------------------------------------------------------------
             * TEST CASE 02: Read Absolute from a linear fixed file
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x02;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);

                // Test case 1.1: Record pointer not set
                recNumber = 2;
                respOffset = 0;
                respLength = 0x04;

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0);
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)4,(byte)0x55);
                // Initialise resp buffer
                Util.arrayFillNonAtomic(resp,(short)0,(short)resp.length,(byte)0x00);

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

                // Test case 1.2 : read record in next mode
                recNumber = 0;
                
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)4,(byte)0xAA);

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;
                
            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 03: Read current from linear fixed EF
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x03;
            bRes = false;
            try {
                recNumber = 0;
                respOffset = 0;
                respLength = 0x04;

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;
               
            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 04: Read next from a linear fixed file
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x04;
            bRes = false;
            try {
                recNumber = 0;
                respOffset = 0;
                respLength = 0x04;

                // Select EF LARU
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;
               
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)4,(byte)0xAA);

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 05: Read next from linear fixed EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x05;
            bRes = false;
            try {
                recNumber = 0;
                respOffset = 0;
                respLength = 0x04;

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 06: Read previous from linear fixed EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x06;
            bRes = false;
            try {
                recNumber = 0;
                respOffset = 0;
                respLength = 0x04;

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)4,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

                // Select EF LARU
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)4,(byte)0xAA);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;
                
                // Call the readRecord() method to set the record pointer to the first record
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)4,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            
            /*------------------------------------------------------------------
             * TEST CASE 07: Read previous from a linear fixed file
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x07;
            bRes = false;
            try {
                recNumber = 0;
                respOffset = 0;
                respLength = 0x04;

                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            } catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 08: Read absolute from Cyclic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x08;
            bRes = false;
            try {
                // Read record 2
                recNumber = 2;
                respOffset = 0;
                respLength = 0x03;

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0x00);
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0xAA);

                // Initialise the resp buffer
                Util.arrayFillNonAtomic(resp,(short)0,(short)resp.length,(byte)0x00);

                // Select EF CARU
                UiccFileView.select(UICCTestConstants.FID_EF_CARU);
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);
                
                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

                // Read record 1
                recNumber = 1;
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

                // Call the readRecord() method in NEXT mode to set the record pointer
                recNumber = 0;
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 09: Read current from cyclic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x09;
            bRes = false;
            try {
                recNumber = 0;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
                
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 10: Read next from cyclic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0A;
            bRes = false;
            try {
                recNumber = 0;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
            
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 11: Read Next from cyclic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0B;
            bRes = false;
            try {
                recNumber = 0;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0xAA);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
            
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;
                
                // Select EF CARU
                UiccFileView.select(UICCTestConstants.FID_EF_CARU);
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
            
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 12: Read previous from cyclic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0C;
            bRes = false;
            try {
                recNumber = 0;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0xAA);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
            
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 13: Read previous from cyclic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0D;
            bRes = false;
            try {
                recNumber = 0;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0x55);

                if (result == (short)(respOffset + respLength))
                    bRes = true;
            
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;
                
                // Select EF CARU
                UiccFileView.select(UICCTestConstants.FID_EF_CARU);
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,resp,respOffset,respLength);

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)3,(byte)0xAA);

                if (result == (short)(respOffset + respLength))
                    bRes &= true;
            
                if (Util.arrayCompare(resp, (short)0, compareBuffer, (short)0, RESP_BUFFER_LENGTH) == 0)
                    bRes &= true;

            } catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 14: Read absolute from linear fixed beyond record
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0E;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
            respLength = (short)0x04;
            
            try {
                recNumber = (short)-1;
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                recNumber = (short)3;
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 15: Read current from linear fixed file with no current record
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0F;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
          
            try {
                recNumber = (short)0;
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 16: Read absolute with recOffset < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x10;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
          
            try {
                recNumber = (short)1;
                recOffset = (short)-1;
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.OUT_OF_RECORD_BOUNDARIES);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 17: Read absolute with recOffset + respLength > record length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x11;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
          
            try {
                recNumber = (short)0x01;
                recOffset = (short)0x02;
                respLength = (short)0x04;
                
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.OUT_OF_RECORD_BOUNDARIES);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 18: Read with invalid mode
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x12;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
          
            recNumber = (short)0x00;
            recOffset = (short)0x00;
            respLength = (short)0x04;
            
            try {
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,(byte)1,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.INVALID_MODE);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,(byte)5,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.INVALID_MODE);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 19: resp buffer is null
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x13;
            bRes = false;

            try {
                recNumber = 1;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,null,respOffset,respLength);
                bRes = false;
            }
            catch(NullPointerException e){
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 20: respOffset < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x14;
            bRes = false;

            try {
                recNumber = 1;
                respOffset = (short)-1;
                respLength = 4;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            }
            catch(ArrayIndexOutOfBoundsException e){
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 21: respLength < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x15;
            bRes = false;

            try {
                recNumber = 1;
                respOffset = (short)0;
                respLength = (short)-1;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            }
            catch(ArrayIndexOutOfBoundsException e){
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 22: respLength + respOffset > resp.length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x16;
            bRes = false;

            try {
                recNumber = 1;
                respOffset = (short)0x11;
                respLength = (short)4;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            }
            catch(ArrayIndexOutOfBoundsException e){
                bRes = true;
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 23: EF is not a record file
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x17;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_TARU);
            
            try {
                recNumber = 1;
                respOffset = (short)0;
                respLength = (short)4;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,resp,respOffset,respLength);
                bRes = false;
            }
            catch(UICCException e){
                bRes = (e.getReason() == UICCException.COMMAND_INCOMPATIBLE);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 24: Access not fulfilled
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x18;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_CNR);
            
            try {
                recNumber = 1;
                respOffset = (short)0;
                respLength = (short)3;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);
                bRes = false;
            }
            catch(UICCException e){
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 25: EF is deactivated
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x19;
            bRes = false;
            
            UiccFileView.select(UICCTestConstants.FID_EF_CNU);
            UiccFileView.deactivateFile();

            try {
                recNumber = 1;
                respOffset = (short)0;
                respLength = (short)3;
                // Call the readRecord() method
                result = UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,resp,respOffset,respLength);
                bRes = false;
            } 
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.REF_DATA_INVALIDATED);
            }
            finally {
                 UiccFileView.activateFile();
            }
            reportTestOutcome(testCaseNb,bRes);
         
        } 
    }
}
