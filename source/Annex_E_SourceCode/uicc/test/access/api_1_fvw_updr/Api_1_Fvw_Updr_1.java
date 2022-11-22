//-----------------------------------------------------------------------------
//Api_1_Fvw_Updr_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_updr;

import javacard.framework.*;
import uicc.access.*;
import uicc.test.util.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.access.api_1_fvw_updr
 */
public class Api_1_Fvw_Updr_1 extends TestToolkitApplet implements UICCConstants
{
    private FileView UiccFileView = null;         

    final short DATA_BUFFER_LENGTH = (short)0x14;

    byte[] data = new byte[DATA_BUFFER_LENGTH];
    byte[] compareBuffer = new byte[DATA_BUFFER_LENGTH];
    byte[] respBuffer = new byte[DATA_BUFFER_LENGTH];

    /**
     * Constructor of the applet
     */
    public Api_1_Fvw_Updr_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_1_Fvw_Updr_1 thisApplet = new Api_1_Fvw_Updr_1();
        
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
        short dataLength = 0;
        short fileOffset = 0;
        short recOffset = 0;
        byte recNumber = 0;
        short respOffset = 0;
        short respLength = 0;
        
        // Result of tests
        boolean bRes = false;
        byte testCaseNb = 0;
        
        // Get the the Uicc FileView and AdminFileView
        UiccFileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);

        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            /*-----------------------------------------------------------------------------------
             * TEST CASE 01: Errorcase, No EF selected
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x01;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                recNumber = 1;
                dataOffset = 0;
                dataLength = 0x0A;
                
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);

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
             * TEST CASE 02: Update Absolute linear fixed file
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x02;
            bRes = false;
            try {
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);

                // Test case 1.1: Record pointer not set
                recNumber = 2;
                recOffset = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0);
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)dataLength,(byte)0x11);
                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,(short)data.length,(byte)0);
                Util.arrayFillNonAtomic(data,(short)0,(short)dataLength,(byte)0x11);

                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);

                // Read the updated file in ABSOLUTE Mode
                respLength = (short)4;
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(data, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
                                
            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 03: Update current linear fixed EF
             *----------------------------------------------------------------*/
            testCaseNb = (byte)0x03;
            bRes = false;
            try {
                recNumber = 0;
                recOffset = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,(short)data.length,(byte)0x00);

                // Set the record pointer to record 1
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,data,dataOffset,dataLength);

                // Call the updateRecord() method in CURRENT mode
                Util.arrayFillNonAtomic(data,(short)0,(short)data.length,(byte)0x22);
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);

                // Read the updated file in CURRENT Mode
                respLength = (short)4;
                // Initialise respBuffer and compareBuffer
                Util.arrayFillNonAtomic(respBuffer,(short)0,(short)respBuffer.length,(byte)0x00);
                Util.arrayFillNonAtomic(compareBuffer,(short)0,dataLength,(byte)0x22);
                
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(respBuffer, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
               
            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*----------------------------------------------------------------------------
             * TEST CASE 04: Update next from a linear fixed file, record pointer not set
             *----------------------------------------------------------------------------*/
            testCaseNb = (byte)0x04;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Select EF LARU
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x33);
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,data,dataOffset,dataLength);

                
                 // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,dataLength,(byte)0x33);

                // Call the updateRecord() method
                respLength = 4;
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(respBuffer, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;

            } catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 05: Update next from linear fixed EF,record pointer set
             *------------------------------------------------------------------*/
            testCaseNb = (byte)0x05;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x44);
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,data,dataOffset,dataLength);
                
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,dataLength,(byte)0x44);

                // Call the updateRecord() method
                respLength = 4;
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(respBuffer, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);

            /*-------------------------------------------------------------------------------
             * TEST CASE 06: Update next linear fixed file, no more record
             *-------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x06;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,data,dataOffset,dataLength);

                bRes = false;
            }
            catch(UICCException e)
            {
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*----------------------------------------------------------------------------------
             * TEST CASE 07: Update previous from a linear fixed file, record pointer not set
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x07;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Select linear fixed file EF LARU
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                
                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x66);
                
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
                
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,dataLength,(byte)0x66);

                // Call the updateRecord() method
                respLength = 4;
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(respBuffer, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*----------------------------------------------------------------------------------
             * TEST CASE 08: Update previous from a linear fixed file, record pointer not set
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x08;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x77);
                
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
                
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,dataLength,(byte)0x77);

                // Call the updateRecord() method
                respLength = 4;
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(respBuffer, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 09: Update previous, no more record
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x09;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x04;

                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x77);
                
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);

            }
            catch(UICCException e)
            {
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch (Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 10: Update previous from cylic EF
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0A;
            bRes = false;
            try {
                recNumber = 0;
                dataOffset = 0;
                dataLength = 0x03;

                // Select cyclic file EF CARU
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_CARU);
                
                // Initialise data buffer
                Util.arrayFillNonAtomic(data,(short)0,(short)data.length,(byte)0x00);
                Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0xFF);
                
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
                
                // Initialise compareBuffer
                Util.arrayFillNonAtomic(compareBuffer,(short)0,(short)compareBuffer.length,(byte)0x00);
                Util.arrayFillNonAtomic(compareBuffer,(short)0,dataLength,(byte)0xFF);

                // Call the updateRecord() method
                recOffset = 0;
                respLength = 3;
                respOffset = 0;
                Util.arrayFillNonAtomic(respBuffer,(short)0,(short)respBuffer.length,(byte)0x00);
                UiccFileView.readRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,respBuffer,respOffset,respLength);
                if (Util.arrayCompare(respBuffer, (short)0, compareBuffer, (short)0, DATA_BUFFER_LENGTH) == 0)
                    bRes = true;
            } 
            catch (Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 11: Update linear fixed file in ABSOLUTE mode beyond record
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0B;
            bRes = false;
            try {
                recNumber = (short)-1;
                dataOffset = 0;
                dataLength = 0x04;

                // Select cyclic file EF LARU
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                
                // Call the updateRecord() method with recNumber set to -1
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
            } 
            catch(UICCException e){
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch(Exception e) {
                bRes = false;
            }
            
            try {
                recNumber = (short)3;
                dataOffset = 0;
                dataLength = 0x04;

                // Select cyclic file EF LARU
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                
                // Call the updateRecord() method with recNumber set to 3
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
            } 
            catch(UICCException e){
                bRes &= (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch(Exception e) {
                bRes = false;
            }
            
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 12: Update linear fixed file in CURRENT mode, record pointer not set
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0C;
            bRes = false;
            try {
                recNumber = (short)0;
                dataOffset = 0;
                dataLength = 0x04;

                // Select cyclic file EF LARU
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                
                // Call the updateRecord() method 
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);
            } 
            catch(UICCException e){
                bRes = (e.getReason() == UICCException.RECORD_NOT_FOUND);
            }
            catch(Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);

            /*-----------------------------------------------------------------------------------
             * TEST CASE 13: recOffset < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0D;
            bRes = false;
            try {
                recNumber = (short)1;
                dataOffset = 0;
                dataLength = 0x04;
                recOffset = (short)-1;

                // Select cyclic file EF LARU
                UiccFileView.select(UICCTestConstants.FID_DF_TEST);
                UiccFileView.select(UICCTestConstants.FID_EF_LARU);
                
                // Call the updateRecord() method with recOffset set to -1
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
            } 
            catch(UICCException e){
                bRes = (e.getReason() == UICCException.OUT_OF_RECORD_BOUNDARIES);
            }
            catch(Exception e) {
                bRes = false;
            }
        
            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 14: recOffset + dataLength > record.length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0E;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
            
            try {
                recNumber = (short)1;
                dataOffset = 0;
                dataLength = 0x04;
                recOffset = (short)2;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
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
             * TEST CASE 15: Invalid mode, linear fixed file
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x0F;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
            
            try {
                recNumber = (short)0;
                dataOffset = 0;
                dataLength = 0x04;
                recOffset = (short)0;
                // Call the updateRecord() method with mode set to 1
                UiccFileView.updateRecord(recNumber,(byte)1,recOffset,data,dataOffset,dataLength);
            } 
            catch (UICCException e) {
                bRes = (e.getReason() == UICCException.INVALID_MODE);
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                recNumber = (short)0;
                dataOffset = 0;
                dataLength = 0x04;
                recOffset = (short)0;
                // Call the updateRecord() method with mode set to 5
                UiccFileView.updateRecord(recNumber,(byte)5,recOffset,data,dataOffset,dataLength);
            } 
            catch (UICCException e) {
                bRes &= (e.getReason() == UICCException.INVALID_MODE);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 16: Invalid mode cyclic file
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x10;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_CARU);
            Util.arrayFillNonAtomic(data,(short)0,(short)data.length,(byte)0);
            try {
                recNumber = (short)0;
                dataOffset = 0;
                dataLength = 0x03;
                recOffset = (short)0;
                // Call the updateRecord() method with mode NEXT
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_NEXT,recOffset,data,dataOffset,dataLength);
            } 
            catch (UICCException e) {
                bRes = ((e.getReason() == UICCException.INVALID_MODE)
                       ||(e.getReason() == UICCException.COMMAND_INCOMPATIBLE));
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                recNumber = (short)0;
                dataOffset = 0;
                dataLength = 0x03;
                recOffset = (short)0;
                // Call the updateRecord() method with mode CURRENT, recNumber set to 0
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);
            } 
            catch (UICCException e) {
                bRes &= ((e.getReason() == UICCException.INVALID_MODE)
                       ||(e.getReason() == UICCException.COMMAND_INCOMPATIBLE));
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                recNumber = (short)2;
                dataOffset = 0;
                dataLength = 0x03;
                recOffset = (short)0;
                // Call the updateRecord() method with mode ABSOLUTE, recNumber set to 2
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
            } 
            catch (UICCException e) {
                bRes &= ((e.getReason() == UICCException.INVALID_MODE)
                       ||(e.getReason() == UICCException.COMMAND_INCOMPATIBLE));
            }
            catch (Exception e) {
                bRes = false;
            }

            try {
                recNumber = (short)0;
                dataOffset = 0;
                dataLength = 0x03;
                recOffset = (short)0;
                // Call the updateRecord() method with mode set to 5, recNumber set to 0
                UiccFileView.updateRecord(recNumber,(byte)5,recOffset,data,dataOffset,dataLength);
            } 
            catch (UICCException e) {
                bRes &= ((e.getReason() == UICCException.INVALID_MODE)
                       ||(e.getReason() == UICCException.COMMAND_INCOMPATIBLE));
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 17: data is null
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x11;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
          
            try {
                recNumber = 1;
                dataOffset = 0;
                dataLength = 4;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,null,dataOffset,dataLength);
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
             * TEST CASE 18: dataOffset < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x12;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
          
            try {
                recNumber = 1;
                dataOffset = (short)-1;
                dataLength = 4;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);
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
             * TEST CASE 19: dataLength < 0
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x13;
            bRes = false;

            try {
                recNumber = 1;
                dataOffset = (short)0;
                dataLength = (short)-1;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);
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
             * TEST CASE 20: dataOffset + dataLength > data.length
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x14;
            bRes = false;

            try {
                recNumber = 1;
                dataOffset = (short)18;
                dataLength = 4;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);
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
             * TEST CASE 21: EF is neither cylic or linear fixed
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x15;
            bRes = false;

            UiccFileView.select(UICCTestConstants.FID_DF_TEST);
            UiccFileView.select(UICCTestConstants.FID_EF_TARU);
            
            try {
                recNumber = 1;
                dataOffset = (short)0;
                dataLength = (short)4;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_CURRENT,recOffset,data,dataOffset,dataLength);
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
             * TEST CASE 22: Access not fulfilled
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x16;
            bRes = false;

            // Select cylic file, Update access set to NEV
            UiccFileView.select(UICCTestConstants.FID_EF_CNU);
            
            try {
                recNumber = 0;
                dataOffset = (short)0;
                dataLength = (short)3;
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
                bRes = false;
            }
            catch(UICCException e){
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            }
            catch (Exception e) {
                bRes = false;
            }

            // Select linear fixed file, Update access set to NEV
            UiccFileView.select(UICCTestConstants.FID_EF_LNU);
            
            try {
                recNumber = 1;
                dataOffset = (short)0;
                dataLength = (short)4;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
                bRes = false;
            }
            catch(UICCException e){
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED);
            }
            catch (Exception e) {
                bRes = false;
            }

            reportTestOutcome(testCaseNb, bRes);
            
            /*-----------------------------------------------------------------------------------
             * TEST CASE 23: EF is deactivated
             *----------------------------------------------------------------------------------*/
            testCaseNb = (byte)0x17;
            bRes = false;
            
            UiccFileView.select(UICCTestConstants.FID_EF_CNR);
            UiccFileView.deactivateFile();

            try {
                recNumber = 0;
                dataOffset = (short)0;
                dataLength = (short)3;
                // Call the updateRecord() method
                UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
                bRes = false;
            } 
            catch (UICCException e) {
                 bRes = (e.getReason() == UICCException.REF_DATA_INVALIDATED);
            }
            finally {
                 UiccFileView.activateFile();
            }
            reportTestOutcome(testCaseNb,bRes);
         
            /*-----------------------------------------
             * Restore the file content
             *-----------------------------------------*/
            // Restore EF_LARU
            UiccFileView.select(UICCTestConstants.FID_EF_LARU);
            recNumber = 1;
            dataOffset = (short)0;
            dataLength = (short)4;
            Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x55);
            UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
            
            Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0xAA);
            recNumber = 2;
            UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_ABSOLUTE,recOffset,data,dataOffset,dataLength);
            
            // Restore EF CARU
            UiccFileView.select(UICCTestConstants.FID_EF_CARU);
            recNumber = 0;
            dataLength = 3;
            UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
            
            Util.arrayFillNonAtomic(data,(short)0,dataLength,(byte)0x55);
            UiccFileView.updateRecord(recNumber,UICCConstants.REC_ACC_MODE_PREVIOUS,recOffset,data,dataOffset,dataLength);
        } 
    }
}
