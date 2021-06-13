//-----------------------------------------------------------------------------
//    Cre_Tin_Acdo_8.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_acdo;

import javacard.framework.*;
import uicc.toolkit.*;
import uicc.access.*;
import uicc.system.*;
import uicc.access.fileadministration.*;
import uicc.test.util.* ;


public class Cre_Tin_Acdo_8 
{ 
    private byte[] abUpRec = {0x22, 0x22, 0x22};
    private byte[] abUpbin = {0x11, 0x11, 0x11};
    private byte[] abInc = {0x00, 0x00, 0x00};
    private byte[] abRdRec = null;
    private byte[] abRes = null;
    private short[] asSearch = null;
    private byte bTestCaseNb = (byte) 0x00;
    private short[] asCyclicEF = {(short)0, UICCTestConstants.FID_EF_CARR1, UICCTestConstants.FID_EF_CARR2, UICCTestConstants.FID_EF_CARR3, UICCTestConstants.FID_EF_CARR4, UICCTestConstants.FID_EF_CARR5}; 
    private short[] asLinearEF = {(short)0, UICCTestConstants.FID_EF_LARR1, UICCTestConstants.FID_EF_LARR2, UICCTestConstants.FID_EF_LARR3, UICCTestConstants.FID_EF_LARR4, UICCTestConstants.FID_EF_LARR5}; 
    private short[] asTranspEF = {(short)0, UICCTestConstants.FID_EF_TARR1, UICCTestConstants.FID_EF_TARR2, UICCTestConstants.FID_EF_TARR3, UICCTestConstants.FID_EF_TARR4, UICCTestConstants.FID_EF_TARR5}; 
    private short[] asAccessDF = {(short)0, UICCTestConstants.FID_DF_ARR1, UICCTestConstants.FID_DF_ARR2, UICCTestConstants.FID_DF_ARR3, UICCTestConstants.FID_DF_ARR4, UICCTestConstants.FID_DF_ARR5}; 
    private short[] asAccessEF = {(short)0, UICCTestConstants.FID_EF_TAR1T, UICCTestConstants.FID_EF_TAR2T, UICCTestConstants.FID_EF_TAR3T, UICCTestConstants.FID_EF_TAR4T, UICCTestConstants.FID_EF_TAR5T};
    private byte [] abCreateEF = {
        (byte)0x82,(byte)0x02,                                      // Tag, Length - File descriptor
        (byte)0x41,(byte)0x21,                                      // Value - File descriptor (Transparent)
        (byte)0x83,(byte)0x02,                                      // Tag, Length - File Id
        (byte)0xEF,(byte)0x00,                                      // Value - File Id
        (byte)0x8A,(byte)0x01,(byte)0x05,                           // Tag, Length, Value - LCSI (Activated)
        (byte)0x8B,(byte)0x03,                                      // Tag, Length - Security attribute
        (byte)0x2F,(byte)0x06,(byte)0x01,                           // Value - Security attribute (EF Arr, record nb)
        (byte)0x80,(byte)0x01,(byte)0x05,                           // Tag, Length, value - File size (5 bytes)
        (byte)0x88,(byte)0x00};                                     // Tag, Length - SFI (no SFI)
    private byte [] abCreateEF_inADF = {
        (byte)0x82,(byte)0x02,                                      // Tag, Length - File descriptor
        (byte)0x41,(byte)0x21,                                      // Value - File descriptor (Transparent)
        (byte)0x83,(byte)0x02,                                      // Tag, Length - File Id
        (byte)0xEF,(byte)0x00,                                      // Value - File Id
        (byte)0x8A,(byte)0x01,(byte)0x05,                           // Tag, Length, Value - LCSI (Activated)
        (byte)0x8B,(byte)0x03,                                      // Tag, Length - Security attribute
        (byte)0x6F,(byte)0x06,(byte)0x01,                           // Value - Security attribute (EF Arr, record nb)
        (byte)0x80,(byte)0x01,(byte)0x05,                           // Tag, Length, value - File size (5 bytes)
        (byte)0x88,(byte)0x00};                                     // Tag, Length - SFI (no SFI)
    private byte [] abResizeEF = {
        (byte)0x83,(byte)0x02,                                      // Tag, Length - File Id
        (byte)0xEF,(byte)0x00,                                      // Value - File Id
        (byte)0x80,(byte)0x01,(byte)0x0A};                          // Tag, Length, value - File size (5 bytes)
    AID AID1 = null;
    FileView UICCView = null;
    FileView ADF1View = null;
    ViewHandler createEFCmd = null;
    ViewHandler resizeCmd = null;
    byte abTestsResults[] = null;
    
    public Cre_Tin_Acdo_8()
    {
    	abTestsResults = new byte[128];
        Util.arrayFillNonAtomic(abTestsResults, (short)0, (short)abTestsResults.length, (byte)0x00);
        abRdRec = new byte[3];
        abRes = new byte[6];
        asSearch = new short[3];
        bTestCaseNb = 0;
    }
    
    public void SetFileViewRef(FileView theUICCView, FileView theADF1View)
    {
        UICCView = theUICCView;
        ADF1View = theADF1View;
    }

    public void resetCaseNumber()
    {
        bTestCaseNb = 0;
    }

    private void reportTestOutcome(byte testCaseNumber, boolean testCaseResult) {
        // Update the total number of tests executed
        abTestsResults[0] = testCaseNumber;

        // Set the Test Case Result byte to 0xCC (for Card Compliant...) if successful
        if (testCaseResult) {
            abTestsResults[testCaseNumber] = (byte)0xCC;
        }
        else {
            abTestsResults[testCaseNumber] = (byte)0x00;
        }
    }

    public byte[] cyclicEFTests(byte bARRNb, boolean bIsAdminFVTest, boolean bIsSuccess)
    {
        boolean bRes = false;
        try 
        {
            // Common calls to FileView and AdminFileView
            UICCView.select((short) UICCTestConstants.FID_DF_TEST);
            ADF1View.select((short) UICCTestConstants.FID_DF_TEST);
            UICCView.select((short) asCyclicEF[bARRNb]);
            ADF1View.select((short) asCyclicEF[bARRNb]);

            // Deactivate Command
            // ------------------
            try 
            {
                UICCView.deactivateFile();
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
            	bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.deactivateFile();
                bRes &= true;//bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 1-7-13-19-25-
            
            // Activate Command
            // ----------------
            try 
            {
                UICCView.activateFile();
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.activateFile();
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 2-8-14-20-26 -
            
            // Update Record Command
            // ---------------------
            try 
            {
                UICCView.updateRecord((short)0, UICCConstants.REC_ACC_MODE_PREVIOUS, (short)0,
                        abUpRec, (short)0, (short)abUpRec.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.updateRecord((short)0, UICCConstants.REC_ACC_MODE_PREVIOUS, (short)0,
                        abUpRec, (short)0, (short)abUpRec.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 3-9-15-21-27 -
            
            // Read Record Command
            // -------------------
            try 
            {
                UICCView.readRecord((short)0, UICCConstants.REC_ACC_MODE_CURRENT, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.readRecord((short)0, UICCConstants.REC_ACC_MODE_CURRENT, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 4-10-16-22-28 -
 
            // Search Record Command
            // ---------------------
            try 
            {
                UICCView.searchRecord(UICCConstants.SIMPLE_SEARCH_START_FORWARD, (short) 0, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length,
                        asSearch, (short)0, (short)asSearch.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.searchRecord(UICCConstants.SIMPLE_SEARCH_START_FORWARD, (short) 0, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length,
                        asSearch, (short)0, (short)asSearch.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 5-11-17-23-29 -
                                
            // Increase Command
            // ----------------
            try 
            {
                UICCView.increase(abInc, (short)0, (short)abInc.length, abRes, (short)0);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.increase(abInc, (short)0, (short)abInc.length, abRes, (short)0);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 6-12-18-24-30 -
        }
        catch (Exception e)
        {
            return abTestsResults;
        }
        return abTestsResults;
    }
    
    public byte[] linearEFTests(byte bARRNb, boolean bIsAdminFVTest, boolean bIsSuccess)
    {
        boolean bRes = false;
        try 
        {
            // Common calls to FileView and AdminFileView
            UICCView.select((short) UICCTestConstants.FID_DF_TEST);
            ADF1View.select((short) UICCTestConstants.FID_DF_TEST);
            UICCView.select((short) asLinearEF[bARRNb]);
            ADF1View.select((short) asLinearEF[bARRNb]);

            // Deactivate Command
            // ------------------
            try 
            {
                UICCView.deactivateFile();
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.deactivateFile();
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 31-36-41-46-51 -
            
            // Activate Command
            // ----------------
            try 
            {
                UICCView.activateFile();
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.activateFile();
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 32-37-42-47-52 -
            
            // Update Record Command
            // ---------------------
            try 
            {
                UICCView.updateRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                        abUpRec, (short)0, (short)abUpRec.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.updateRecord((short)0, UICCConstants.REC_ACC_MODE_NEXT, (short)0,
                        abUpRec, (short)0, (short)abUpRec.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 33-38-43-48-53 -
            
            // Read Record Command
            // -------------------
            try 
            {
                UICCView.readRecord((short)0, UICCConstants.REC_ACC_MODE_CURRENT, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.readRecord((short)0, UICCConstants.REC_ACC_MODE_CURRENT, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 34-39-44-49-54 -
 
            // Search Record Command
            // ---------------------
            try 
            {
                UICCView.searchRecord(UICCConstants.SIMPLE_SEARCH_START_FORWARD, (short) 0, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length,
                        asSearch, (short)0, (short)asSearch.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.searchRecord(UICCConstants.SIMPLE_SEARCH_START_FORWARD, (short) 0, (short)0,
                        abRdRec, (short)0, (short)abRdRec.length,
                        asSearch, (short)0, (short)asSearch.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 35-40-45-50-55 -
        }
        catch (Exception e)
        {
            return abTestsResults;
        }
        return abTestsResults;
    }
    
    public byte[] transpEFTests(byte bARRNb, boolean bIsAdminFVTest, boolean bIsSuccess)
    {
        boolean bRes = false;
        try 
        {
            // Common calls to FileView and AdminFileView
            UICCView.select((short) UICCTestConstants.FID_DF_TEST);
            ADF1View.select((short) UICCTestConstants.FID_DF_TEST);
            UICCView.select((short) asTranspEF[bARRNb]);
            ADF1View.select((short) asTranspEF[bARRNb]);

            // Deactivate Command
            // ------------------
            try 
            {
                UICCView.deactivateFile();
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.deactivateFile();
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 56-60-64-68-72 -
            
            // Activate Command
            // ----------------
            try 
            {
                UICCView.activateFile();
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.activateFile();
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 57-61-65-69-73 -
            
            // Update Binary Command
            // ---------------------
            try 
            {
                UICCView.updateBinary((short)0, abUpbin, (short)0, (short)abUpbin.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.updateBinary((short)0, abUpbin, (short)0, (short)abUpbin.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 58-62-66-70-74 -
            
            // Read Binary Command
            // -------------------
            try 
            {
                UICCView.readBinary((short)0, abRdRec, (short)0, (short)abRdRec.length);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                ADF1View.readBinary((short)0, abRdRec, (short)0, (short)abRdRec.length);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 59-63-67-71-75 -
        }
        catch (Exception e)
        {
            return abTestsResults;
        }
        return abTestsResults;
    }
    
    public byte[] adminCmdAccessTests(byte bARRNb, boolean bIsSuccess)
    {
        boolean bRes = false;
        try
        {
            AdminFileView adminUICCView = (AdminFileView) UICCView;
            AdminFileView adminADF1View = (AdminFileView) ADF1View;

            // AdminFileView methods call only
            adminUICCView.select(UICCTestConstants.FID_MF);
            adminADF1View.select(UICCTestConstants.FID_ADF);
            adminUICCView.select(UICCTestConstants.FID_DF_TEST);
            adminADF1View.select(UICCTestConstants.FID_DF_TEST);
            adminUICCView.select(asAccessDF[bARRNb]);
            adminADF1View.select(asAccessDF[bARRNb]);

            // Create Command
            // --------------
            //Create EF with access rule bARRNb in EF 0xAC00
            abCreateEF[15] = (byte)bARRNb;
            createEFCmd = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, (short)abCreateEF.length, abCreateEF, (short)0x00, (short)abCreateEF.length);
            try 
            {
                adminUICCView.createFile(createEFCmd);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            abCreateEF_inADF[15] = (byte)bARRNb;
            createEFCmd = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, (short)abCreateEF_inADF.length, abCreateEF_inADF, (short)0x00, (short)abCreateEF_inADF.length);
            try 
            {
                adminADF1View.createFile(createEFCmd);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
				bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 76-79-82-85-88
            
            // Resize Command
            // --------------
            if (bIsSuccess) // file 0xEF00 not created
            {
            	abResizeEF[2] = (byte)0xEF;
            	abResizeEF[3] = (byte)0x00;
            }
            else
            {
            	abResizeEF[2] = (byte)((asAccessEF[bARRNb] >> 8) & 0x00FF);
            	abResizeEF[3] = (byte)(asAccessEF[bARRNb] & 0x00FF);
            }
            resizeCmd = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, (short)abResizeEF.length, abResizeEF, (short)0x00, (short)abResizeEF.length);
            try 
            {
                adminUICCView.resizeFile(resizeCmd);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                adminADF1View.resizeFile(resizeCmd);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 77-80-83-86-89
            
            // Delete Command
            // --------------
            try 
            {
                if (bIsSuccess)
                    adminUICCView.deleteFile((short)0xEF00);
                else
                    adminUICCView.deleteFile(asAccessEF[bARRNb]);
                bRes = bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes = (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            try 
            {
                if (bIsSuccess)
                    adminADF1View.deleteFile((short)0xEF00);
                else
                    adminADF1View.deleteFile(asAccessEF[bARRNb]);
                bRes &= bIsSuccess;
            }
            catch (UICCException e)
            {
                bRes &= (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED) && !bIsSuccess;
            }
            catch (Exception e)
            {
                bRes = false;
            }
            reportTestOutcome((byte)++bTestCaseNb, bRes);   // testcaseNb = 78-81-84-87-90
        }
        catch (Exception e)
        {
            return abTestsResults;
        }
        return abTestsResults;
    }
}
