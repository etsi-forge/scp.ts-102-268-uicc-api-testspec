//-----------------------------------------------------------------------------
//Api_1_Fvw_Incr_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_incr;

import uicc.system.HandlerBuilder;
import uicc.test.util.* ;
import uicc.toolkit.ViewHandler;
import javacard.framework.*;
import uicc.access.* ;
import uicc.access.fileadministration.*;


public class Api_1_Fvw_Incr_1 extends TestToolkitApplet implements UICCConstants
{
    private FileView UiccFileView = null;         
    private AdminFileView UiccAdminFileView = null;         
    private ViewHandler createEFCmd = null;

    private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    private byte [] abCreateEF_2C00 = {
            (byte)0x82,(byte)0x04,                       // Tag, Length - File descriptor
            (byte)0x46,(byte)0x21,(byte)0x00,(byte)0x00, // Value - File descriptor (Cyclic) and record length (0x00)
            (byte)0x83,(byte)0x02,                       // Tag, Length - File Id
            (byte)0x2C,(byte)0x00,                       // Value - File Id
            (byte)0x8A,(byte)0x01,(byte)0x05,            // Tag, Length, Value - LCSI (Activated)
            (byte)0x8B,(byte)0x03,                       // Tag, Length - Security attribute
            (byte)0x2F,(byte)0x06,(byte)0x01,            // Value - Security attribute (EF Arr, record nb)
            (byte)0x80,(byte)0x02,(byte)0x00,(byte)0x00, // Tag, Length, value - File size (0 bytes => no record)
            (byte)0x88,(byte)0x00};                      // Tag, Length - SFI (no SFI)
    private byte [] abCreateEF_2C7F = {
            (byte)0x82,(byte)0x04,                       // Tag, Length - File descriptor
            (byte)0x46,(byte)0x21,(byte)0x00,(byte)0x7F, // Value - File descriptor (Cyclic) and record length (0x7F)
            (byte)0x83,(byte)0x02,                       // Tag, Length - File Id
            (byte)0x2C,(byte)0x7F,                       // Value - File Id
            (byte)0x8A,(byte)0x01,(byte)0x05,            // Tag, Length, Value - LCSI (Activated)
            (byte)0x8B,(byte)0x03,                       // Tag, Length - Security attribute
            (byte)0x2F,(byte)0x06,(byte)0x01,            // Value - Security attribute (EF Arr, record nb)
            (byte)0x80,(byte)0x02,(byte)0x00,(byte)7F, // Tag, Length, value - File size (0 bytes => no record)
            (byte)0x88,(byte)0x00};                      // Tag, Length - SFI (no SFI)
    byte testCaseNb = (byte) 0x00;
    byte incr[] = null;
    byte resp[] = null;
    byte data[] = null;
    byte comp[] = null;
    short incrOffset = 0;
    short respOffset = 0;
    short incrLength = 0;
    short respLength = 0;

    /**
     * Constructor of the applet
     */
    public Api_1_Fvw_Incr_1()
    {
        incr = new byte[4];
        resp = new byte[4];
        comp = new byte[4];
        data = new byte[3];
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_1_Fvw_Incr_1 thisApplet = new Api_1_Fvw_Incr_1();
        
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
        
        // Initialise the data of the test applet.
        thisApplet.init();
        
        // toolkit registration
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,(short) MenuInit.length, (byte) 0, false,
                                    (byte) 0, (short) 0);
    }

    /**
     * Method called by the Cat Re
     */
    public void processToolkit(short event) 
    {
        // Result of tests
        boolean bRes = false;
        
        // Get the the Uicc FileView and AdminFileView
        UiccFileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
        UiccAdminFileView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
        
        // -----------------------------------------------------------------
        // Test Case 1 : No EF Selected
        // -----------------------------------------------------------------
        testCaseNb = 1;
        UiccFileView.select((short)UICCTestConstants.FID_DF_TEST);
        try 
        {
            incrLength = 2;
            UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == (short)UICCException.NO_EF_SELECTED)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 2 : increase , verify response
        // -----------------------------------------------------------------
        testCaseNb = 2;
        UiccFileView.select((short)UICCTestConstants.FID_EF_CARU);
        try 
        {
            // Set records to 00 00 00
            Util.arrayFillNonAtomic(data, (short)0, (short)data.length, (byte)0);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, data, (short)0, (short)data.length);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, data, (short)0, (short)data.length);
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incr[2] = (byte)1;
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);

            // Verify result
            Util.arrayFillNonAtomic(comp, (short)0, (short)comp.length, (byte)0);
            comp[2] = (byte)1;
            if ((respLength == (short)3) &&
                (Util.arrayCompare(resp, (short)0, comp, (short)0, (short)4) == 0))
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 3 : increase , verify file
        // -----------------------------------------------------------------
        testCaseNb = 3;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incr[3] = (byte)2;
            incrOffset = 1;
            incrLength = 3;
            respOffset = 1;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);

            // Verify result
            Util.arrayFillNonAtomic(resp, (short)0, (short)resp.length, (byte)0);
            Util.arrayFillNonAtomic(comp, (short)0, (short)comp.length, (byte)0);
            comp[2] = (byte)3;
            UiccFileView.readRecord((short)0, REC_ACC_MODE_CURRENT, (short)0, resp, (short)0, (short)3);
            if ((respLength == (short)3) &&
                (Util.arrayCompare(resp, (short)0, comp, (short)0, (short)4) == 0))
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 4 : incr[] is null
        // -----------------------------------------------------------------
        testCaseNb = 4;
        try 
        {
            // increase
            incrOffset = 0;
            incrLength = 1;
            respOffset = 0;
            respLength = UiccFileView.increase(null, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (NullPointerException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 5 : incrLength < 0
        // -----------------------------------------------------------------
        testCaseNb = 5;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incrOffset = 0;
            incrLength = -1;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 6 : incrOffset < 0
        // -----------------------------------------------------------------
        testCaseNb = 6;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incrOffset = -1;
            incrLength = 1;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 7 : IncrOffset + incrLength > incr.length
        // -----------------------------------------------------------------
        testCaseNb = 7;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incrOffset = 1;
            incrLength = 4;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 8 : Reach Maximum Value
        // -----------------------------------------------------------------
        testCaseNb = 8;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0xFF);
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.MAX_VALUE_REACHED)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }

        try 
        {
            // Set records to FF FF FF
            Util.arrayFillNonAtomic(data, (short)0, (short)data.length, (byte)0xFF);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, data, (short)0, (short)data.length);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, data, (short)0, (short)data.length);
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incr[2] = (byte)1;
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.MAX_VALUE_REACHED)
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 9 : resp[] is null
        // -----------------------------------------------------------------
        testCaseNb = 9;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0xFF);
            incrOffset = 0;
            incrLength = 1;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, null, respOffset);
            bRes = false;
        }
        catch (NullPointerException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 10 : incrLength < 0
        // -----------------------------------------------------------------
        testCaseNb = 10;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incrOffset = 0;
            incrLength = 1;
            respOffset = -1;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 11 : respOffset + recordLength > resp.length
        // -----------------------------------------------------------------
        testCaseNb = 11;
        try 
        {
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incrOffset = 0;
            incrLength = 3;
            respOffset = 2;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            bRes = true;   
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 12 : Ef is not cyclic
        // -----------------------------------------------------------------
        testCaseNb = 12;
        try 
        {
            UiccFileView.select((short)UICCTestConstants.FID_EF_TARU);
            incr = new byte[3];
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.COMMAND_INCOMPATIBLE)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        try 
        {
            UiccFileView.select((short)UICCTestConstants.FID_EF_LARU);
            // increase
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.COMMAND_INCOMPATIBLE)
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 13 : Access condition not fulfilled
        // -----------------------------------------------------------------
        testCaseNb = 13;
        try 
        {
            UiccFileView.select((short)UICCTestConstants.FID_EF_CNIC);
            // increase
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.SECURITY_STATUS_NOT_SATISFIED)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 14 : EF is deactivated
        // -----------------------------------------------------------------
        testCaseNb = 14;
        try 
        {
            UiccFileView.select((short)UICCTestConstants.FID_EF_CARU);
            // deactivated
            UiccFileView.deactivateFile();
            // increase
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.REF_DATA_INVALIDATED)
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        try 
        {
            // activate 
            UiccFileView.activateFile();
            // Restore Ef content
            // Set records to AA AA AA
            Util.arrayFillNonAtomic(data, (short)0, (short)data.length, (byte)0xAA);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, data, (short)0, (short)data.length);
            // Set records to 55 55 55
            Util.arrayFillNonAtomic(data, (short)0, (short)data.length, (byte)0x55);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, data, (short)0, (short)data.length);
            bRes &= true;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);

        // -----------------------------------------------------------------
        // Test Case 15 : Record not found
        //
        testCaseNb = 15;
        try
        {
        	incr = new byte[3];
        	resp = new byte[3];
        	createEFCmd = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, (short)abCreateEF_2C00.length, abCreateEF_2C00, (short)0x00, (short)abCreateEF_2C00.length);
            UiccAdminFileView.createFile(createEFCmd);
            UiccAdminFileView.select((short)0x2C00);
            
            incrOffset = 0;
            incrLength = 3;
            respOffset = 0;
            
            respLength = UiccFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.RECORD_NOT_FOUND)
                bRes = true;
            else
                bRes = false;
        }
        catch(Exception e)
        {
        	bRes = false;
        }
        
        UiccAdminFileView.deleteFile((short)0x2C00);
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 16 : incrLength out of range
        // -----------------------------------------------------------------
        testCaseNb = 16;
        try 
        {
            // Create EF 0x2C7F
            incr = new byte[128];
            resp = new byte[127];
            comp = new byte[0xFF];
            createEFCmd = HandlerBuilder.buildTLVHandler(HandlerBuilder.EDIT_HANDLER, (short)abCreateEF_2C7F.length, abCreateEF_2C7F, (short)0x00, (short)abCreateEF_2C7F.length);
            UiccAdminFileView.createFile(createEFCmd);
            UiccAdminFileView.select((short)0x2C7F);

            Util.arrayFillNonAtomic(comp, (short)0, (short)127, (byte)0x00);
            UiccAdminFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, comp, (short)0, (short)0x7F);
            // increase
            Util.arrayFillNonAtomic(incr, (short)0, (short)incr.length, (byte)0);
            Util.arrayFillNonAtomic(resp, (short)0, (short)resp.length, (byte)0);
            Util.arrayFillNonAtomic(comp, (short)0, (short)comp.length, (byte)0);
            incr[127] = (byte)0x01;
            comp[126] = (byte)0x01;
            incrOffset = 1;
            incrLength = 127;
            respOffset = 0;
            respLength = UiccAdminFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            // Check result
            if ((respLength == (short)0x7F) &&
                (Util.arrayCompare(resp, (short)0, comp, (short)0, respLength) == 0))
                bRes = true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        try 
        {
            // increase
            incrOffset = 0;
            incrLength = 128;
            respOffset = 0;
            respLength = UiccAdminFileView.increase(incr, incrOffset, incrLength, resp, respOffset);
            bRes = false;
        }
        catch (Exception e)
        {
            bRes &= true;
        }
        reportTestOutcome(testCaseNb, bRes);
        UiccAdminFileView.deleteFile((short)0x2C7F);
    }
}
