//-----------------------------------------------------------------------------
//Api_1_Fvw_Sear_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_sear;

import javacard.framework.*;
import uicc.toolkit.*;
import uicc.access.* ;
import uicc.access.fileadministration.AdminFileView;
import uicc.access.fileadministration.AdminFileViewBuilder;
import uicc.test.util.* ;


public class Api_4_Afv_Sear_1 extends TestToolkitApplet implements UICCConstants
{
    private AdminFileView UiccFileView = null;         

    private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    private byte[] RecValue1 = {(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,
                                (byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
    private byte[] RecValue2 = {(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x01,(byte)0x02,
                                (byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09};
    private byte[] RecValue3 = {(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,
                                (byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x01,(byte)0x02,(byte)0x03};
    private byte[] RecValue4 = {(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,
                                (byte)0x0F,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06};
    private byte[] RecValue5 = {(byte)0x0A,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x0B,(byte)0x01,(byte)0x02,(byte)0x03,
                                (byte)0x0C,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x0D,(byte)0x01,(byte)0x02};
    private byte[] RecValue6 = {(byte)0x03,(byte)0x02,(byte)0x01,(byte)0x03,(byte)0x02,(byte)0x01,(byte)0x03,(byte)0x02,
                                (byte)0x01,(byte)0x03,(byte)0x02,(byte)0x01,(byte)0x03,(byte)0x02,(byte)0x01};
    
    byte testCaseNb = (byte) 0x00;
    byte patt[] = null;
    short resp[] = null;
    short comp[] = null;
    byte mode = 0;
    short recordNumber = 0;
    short searchIndication = 0;
    short pattOffset = 0;
    short respOffset = 0;
    short pattLength = 0;
    short respLength = 0;
    short nbRecFound = 0;

    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Sear_1()
    {
        comp = new short[5];
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_4_Afv_Sear_1 thisApplet = new Api_4_Afv_Sear_1();
        
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
        UiccFileView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
        
        // -----------------------------------------------------------------
        // Test Case 1 : No EF Selected
        // -----------------------------------------------------------------
        testCaseNb = 1;
        UiccFileView.select((short)UICCTestConstants.FID_DF_TEST);
        try 
        {
            // searchRecord
            patt = new byte[3];
            resp = new short[4];
            Util.arrayFillNonAtomic(patt, (short)0, (short)patt.length, (byte)0xAA);
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // -----------------------------------------------------------------
        // -- Fixed linear EF
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        

        // -----------------------------------------------------------------
        // Test Case 2 : Simple mode search forward
        // -----------------------------------------------------------------
        testCaseNb = 2;
        UiccFileView.select((short)UICCTestConstants.FID_EF_LSEA);
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            patt[0] = (byte)0x10;
            patt[1] = (byte)0x03;
            patt[2] = (byte)0x04;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 4;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 2;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 1;
            respLength = 3;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[1] = (short)2;
            comp[2] = (short)4;
            if ((nbRecFound == (short)2) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 1;
            respLength = 3;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[1] = (short)2;
            comp[2] = (short)4;
            if ((nbRecFound == (short)2) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
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
        // Test Case 3 : Simple mode search backward
        // -----------------------------------------------------------------
        testCaseNb = 3;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_BACKWARD;
            searchIndication = 0;
            patt[0] = (byte)0x08;
            patt[1] = (byte)0x0A;
            patt[2] = (byte)0x0B;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 2;
            respLength = 2;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = SIMPLE_SEARCH_START_BACKWARD;
            searchIndication = 0;
            patt = new byte[4];
            patt[0] = (byte)0x08;
            patt[1] = (byte)0x09;
            patt[2] = (byte)0x0A;
            patt[3] = (byte)0x0B;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            recordNumber = 6;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 1;
            respLength = 3;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[1] = (short)4;
            comp[2] = (short)3;
            comp[3] = (short)1;
            if ((nbRecFound == (short)3) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
                
 // searchRecord
            mode = SIMPLE_SEARCH_START_BACKWARD;
            searchIndication = 0;
            patt = new byte[4];
            patt[0] = (byte)0x08;
            patt[1] = (byte)0x09;
            patt[2] = (byte)0x0A;
            patt[3] = (byte)0x0B;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 1;
            respLength = 3;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[1] = (short)4;
            comp[2] = (short)3;
            comp[3] = (short)1;
            if ((nbRecFound == (short)3) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
                
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // ---------------------------------------------------------------------------------------------------
        // Test Case 4 : Enhanced Mode, search backward from previous record, start search at offset in record
        // ---------------------------------------------------------------------------------------------------
        testCaseNb = 4;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS + (short)0x0009;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            patt[3] = (byte)0x04;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (byte)0x03;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS + (short)0x0000;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 5;
            respOffset = 2;
            respLength = 2;
            patt = new byte[6];
            patt[0] = (byte)0x0C;
            patt[1] = (byte)0x0D;
            patt[2] = (byte)0x0E;
            patt[3] = (byte)0x0F;
            patt[4] = (byte)0x01;
            patt[5] = (byte)0x02;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[2] = (byte)0x02;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // --------------------------------------------------------------------------------------------------
        // Test Case 5 : Enhanced Mode, search backward from previous record, start search at value in record
        // --------------------------------------------------------------------------------------------------
        testCaseNb = 5;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS + (short)0x0810;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt = new byte[4];
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            patt[3] = (byte)0x04;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // 3 readRecord() to set record pointer to record 5
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)4);
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)4);
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)4);
            
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS + (short)0x080E;
            recordNumber = 0;
            pattOffset = 3;
            pattLength = 1;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            patt[3] = (byte)0x04;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (byte)0x04;
            comp[1] = (byte)0x02;
            if ((nbRecFound == (short)2) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // --------------------------------------------------------------------------------------------------
        // Test Case 6 : Enhanced Mode, search backward from previous given record, start at offset in record 
        // --------------------------------------------------------------------------------------------------
        testCaseNb = 6;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS_GR + (short)0x0000;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 4;
            patt = new byte[3];
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (byte)0x01;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS_GR + (short)0x0004;
            recordNumber = 6;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (short)5;
            comp[1] = (short)4;
            comp[2] = (short)3;
            comp[3] = (short)2;
            if ((nbRecFound == (short)4) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -------------------------------------------------------------------------------------------------
        // Test Case 7 : Enhanced Mode, search backward from previous given record, start at value in record 
        // -------------------------------------------------------------------------------------------------
        testCaseNb = 7;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS_GR + (short)0x080D;
            recordNumber = 1;
            pattOffset = 1;
            pattLength = 1;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x0E;
            patt[1] = (byte)0x0E;
            patt[2] = (byte)0x0E;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (byte)0x01;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS_GR + (short)0x0800;
            recordNumber = 6;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // ---------------------------------------------------------------------------------------
        // Test Case 8 : Enhanced Mode, search forward from next record, start at offset in record
        // ---------------------------------------------------------------------------------------
        testCaseNb = 8;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT + (short)0x0003;
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 2;
            respLength = 2;
            patt[0] = (byte)0x00;
            patt[1] = (byte)0x0A;
            patt[2] = (byte)0x0B;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[2] = (short) 3;
            comp[3] = (short) 4;
            if ((nbRecFound == (short)2) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT + (short)0x0003;
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 0;
            respLength = 4;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (short) 4;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // --------------------------------------------------------------------------------------
        // Test Case 9 : Enhanced Mode, search forward from next record, start at value in record
        // --------------------------------------------------------------------------------------
        testCaseNb = 9;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT + (short)0x0804;
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 2;
            respLength = 2;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT + (short)0x0801;
            recordNumber = 0;
            pattOffset = 2;
            pattLength = 1;
            respOffset = 0;
            respLength = 4;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (short) 5;
            comp[1] = (short) 6;
            if ((nbRecFound == (short)2) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // ----------------------------------------------------------------------------------------------
        // Test Case 10 : Enhanced Mode, search forward from next given record, start at offset in record
        // ----------------------------------------------------------------------------------------------
        testCaseNb = 10;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x0007;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 1;
            respLength = 3;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[1] = (short)3;
            comp[2] = (short)4;
            comp[3] = (short)5;
            if ((nbRecFound == (short)3) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x000C;
            recordNumber = 3;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x03;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x01;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (byte)0x06;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // ---------------------------------------------------------------------------------------------
        // Test Case 11 : Enhanced Mode, search forward from next given record, start at value in record
        // ---------------------------------------------------------------------------------------------
        testCaseNb = 11;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x080D;
            recordNumber = 5;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x080C;
            recordNumber = 5;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (byte)0x05;
            if ((nbRecFound == (short)1) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes &= true;
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // ----------------------------------------------------------------------------
        // Test Case 12 : Simple mode, total number of found patterns exceed response[]
        // ----------------------------------------------------------------------------
        testCaseNb = 12;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (short)1;
            comp[1] = (short)2;
            comp[2] = (short)3;
            comp[3] = (short)4;
            if ((nbRecFound == (short)4) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 4;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            resp = new short[5];
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (short)1;
            comp[1] = (short)2;
            comp[2] = (short)3;
            comp[3] = (short)4;
            if ((nbRecFound == (short)4) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
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
        // Test Case 13 : Invalid mode
        // -----------------------------------------------------------------
        testCaseNb = 13;
        try 
        {
            // searchRecord
            mode = (byte)0x14;
            searchIndication = 0;
            recordNumber = 2;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 2;
            respLength = 2;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.INVALID_MODE)
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
        // Test Case 14 : Pattern array is null
        // -----------------------------------------------------------------
        testCaseNb = 14;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 2;
            respLength = 2;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, null, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 15 : Response array is null
        // -----------------------------------------------------------------
        testCaseNb = 15;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, null, respOffset, respLength);
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
        // Test Case 16 : pattOffset<0
        // -----------------------------------------------------------------
        testCaseNb = 16;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = -1;
            pattLength = 1;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 17 : pattLength<0
        // -----------------------------------------------------------------
        testCaseNb = 17;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = -1;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 18 : respOffset<0
        // -----------------------------------------------------------------
        testCaseNb = 18;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 1;
            respOffset = -1;
            respLength = 4;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 19 : respLength<0
        // -----------------------------------------------------------------
        testCaseNb = 19;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = -1;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 20 : PattOffset + pattLength > patt[]
        // -----------------------------------------------------------------
        testCaseNb = 20;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 2;
            respOffset = 1;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 21 : respOffset + respLength > resp[]
        // -----------------------------------------------------------------
        testCaseNb = 21;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 3;
            respLength = 3;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 22 : recordNum < 0
        // -----------------------------------------------------------------
        testCaseNb = 22;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = -1;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.RECORD_NOT_FOUND)
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
        // Test Case 23 : RecordNum >  total number of file records
        // -----------------------------------------------------------------
        testCaseNb = 23;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 7;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.RECORD_NOT_FOUND)
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
        // Test Case 24 : pattlength > record length
        // -----------------------------------------------------------------
        testCaseNb = 24;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            patt = new byte[16];
            Util.arrayFillNonAtomic(patt, (short)0, (short)patt.length, (byte)0x55);
            recordNumber = 3;
            pattOffset = 0;
            pattLength = 16;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.OUT_OF_RECORD_BOUNDARIES)
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
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x000E;
            recordNumber = 3;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 5;
            patt = new byte[3];
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
            bRes = false;
        }
        catch (UICCException e)
        {
            if (e.getReason() == UICCException.OUT_OF_RECORD_BOUNDARIES)
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
        // Test Case 25 : Wrong file structure
        // -----------------------------------------------------------------
        testCaseNb = 25;
        try 
        {
            // Select EF TDAC
            UiccFileView.select((short)UICCTestConstants.FID_EF_TDAC);
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        reportTestOutcome(testCaseNb, bRes);
            
        // -----------------------------------------------------------------
        // Test Case 26 : Security status not satisfied
        // -----------------------------------------------------------------
        testCaseNb = 26;
        try 
        {
            // Select EF LNR
            UiccFileView.select((short)UICCTestConstants.FID_EF_LNR);
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        // Test Case 27 : File deactivated
        // -----------------------------------------------------------------
        testCaseNb = 27;
        try 
        {
            // Select EF LARU and deactivate it
            UiccFileView.select((short)UICCTestConstants.FID_EF_LARU);
            UiccFileView.deactivateFile();
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 0;
            respLength = 5;
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);
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
        reportTestOutcome(testCaseNb, bRes);
        // Re active EF LARU
        UiccFileView.activateFile();
            
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        // -- Cyclic EF
        // -----------------------------------------------------------------
        // -----------------------------------------------------------------
        

        // -----------------------------------------------------------------
        // Test Case 28 : Simple mode search forward
        // -----------------------------------------------------------------
        testCaseNb = 28;
        try 
        {
            // Select EF CSEA
            UiccFileView.select((short)UICCTestConstants.FID_EF_CSEA);
            
            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 1;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 5;
            patt[0] = (byte)0x10;
            patt[1] = (byte)0x03;
            patt[2] = (byte)0x04;
            resp = new short[5];
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
                bRes = true;
            else
                bRes = false;

            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 2;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 2;
            respLength = 3;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)3) 
            {
                comp[2] = (short)2;
                comp[3] = (short)4;
                comp[4] = (short)1;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)2) 
            {
                comp[2] = (short)2;
                comp[3] = (short)4;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else
                bRes = false;
                      
            // Update record in previous mode to set pointer 1 to previous record 6
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, RecValue6, (short)0, (short)RecValue6.length);

            // searchRecord
            mode = SIMPLE_SEARCH_START_FORWARD;
            searchIndication = 0;
            recordNumber = 2;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 2;
            respLength = 3;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[2] = (short)2;
            comp[3] = (short)3;
            comp[4] = (short)5;
            if ((nbRecFound == (short)3) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
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
        // Test Case 29 : Simple mode search backward
        // -----------------------------------------------------------------
        testCaseNb = 29;
        try 
        {
            // searchRecord
            mode = SIMPLE_SEARCH_START_BACKWARD;
            searchIndication = 0;
            recordNumber = 3;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 1;
            respLength = 4;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)3)
            {
                comp[1] = (short)3;
                comp[2] = (short)2;
                comp[3] = (short)5;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)2)
            {
                comp[1] = (short)3;
                comp[2] = (short)2;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            } 
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 30 : Enhanced mode, search forward, start at offset in record
        // -----------------------------------------------------------------
        testCaseNb = 30;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT + (short)0x0009;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 2;
            respLength = 3;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[2] = (short)4;
            comp[3] = (short)5;
            comp[4] = (short)6;
            if ((nbRecFound == (short)3) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
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
        // Test Case 31 : Enhanced mode, search forward, start at value in record
        // -----------------------------------------------------------------
        testCaseNb = 31;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT + (short)0x0810;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 3;
            respOffset = 2;
            respLength = 3;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0x00);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if ((nbRecFound == (short)0) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
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
        // Test Case 32 : Enhanced mode, search forward given record, start at offset in record
        // -----------------------------------------------------------------
        testCaseNb = 32;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x0005;
            recordNumber = 3;
            pattOffset = 0;
            pattLength = 1;
            respOffset = 0;
            respLength = 5;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)5)
            {
                comp[0] = (short)3;
                comp[1] = (short)4;
                comp[2] = (short)5;
                comp[3] = (short)6;
                comp[4] = (short)1;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)4)
            {
                comp[0] = (short)3;
                comp[1] = (short)4;
                comp[2] = (short)5;
                comp[3] = (short)6;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 33 : Enhanced mode, search forward given record, start at value in record
        // -----------------------------------------------------------------
        testCaseNb = 33;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_FORWARD_FROM_NEXT_GR + (short)0x0805;
            recordNumber = 6;
            pattOffset = 0;
            pattLength = 2;
            respOffset = 0;
            respLength = 5;
            patt[0] = (byte)0x0E;
            patt[1] = (byte)0x0F;
            patt[2] = (byte)0x00;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)2)
            {
                comp[0] = (short)2;
                comp[1] = (short)4;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)0)
            {
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else
                bRes = false;

            // 5 Update record in previous mode to set pointer 1 its initial value
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, RecValue5, (short)0, (short)RecValue5.length);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, RecValue4, (short)0, (short)RecValue4.length);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, RecValue3, (short)0, (short)RecValue3.length);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, RecValue2, (short)0, (short)RecValue2.length);
            UiccFileView.updateRecord((short)0, REC_ACC_MODE_PREVIOUS, (short)0, RecValue1, (short)0, (short)RecValue1.length);
            
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 34 : Enhanced mode, search backward, start at offset in record
        // -----------------------------------------------------------------
        testCaseNb = 34;
        try 
        {
            patt = new byte[15];
            // 5 readRecord() to set record pointer to record 6
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)0x0F);
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)0x0F);
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)0x0F);
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)0x0F);
            UiccFileView.readRecord((short)0, REC_ACC_MODE_NEXT, (short)0, patt, (short)0, (short)0x0F);
            
               
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS + (short)0x0003;
            recordNumber = 0;
            pattOffset = 0;
            pattLength = 2;
            respOffset = 3;
            respLength = 2;
            patt[0] = (byte)0x02;
            patt[1] = (byte)0x01;
            patt[2] = (byte)0x00;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)1)
            {
                comp[3] = (short)6;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)0)
            {
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 35 : Enhanced mode, search backward, start at value in record
        // -----------------------------------------------------------------
        testCaseNb = 35;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS + (short)0x0801;
            recordNumber = 0;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 0;
            respLength = 5;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            comp[0] = (short)5;
            comp[1] = (short)4;
            comp[2] = (short)3;
            comp[3] = (short)2;
            comp[4] = (short)1;
            if ((nbRecFound == (short)5) &&
                (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0))
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
        // Test Case 36 : Enhanced mode, search backward given record, start at offset in record
        // -----------------------------------------------------------------
        testCaseNb = 36;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS_GR + (short)0x0003;
            recordNumber = 5;
            pattOffset = 0;
            pattLength = 2;
            respOffset = 3;
            respLength = 2;
            patt[0] = (byte)0x02;
            patt[1] = (byte)0x01;
            patt[2] = (byte)0x00;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)1)
            {
                comp[3] = (short)6;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)0)
            {
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
        
        // -----------------------------------------------------------------
        // Test Case 37 : Enhanced mode, search backward given record, start at value in record
        // -----------------------------------------------------------------
        testCaseNb = 37;
        try 
        {
            // searchRecord
            mode = ENHANCED_SEARCH;
            searchIndication = SEARCH_INDICATION_START_BACKWARD_FROM_PREVIOUS_GR + (short)0x0801;
            recordNumber = 3;
            pattOffset = 1;
            pattLength = 2;
            respOffset = 0;
            respLength = 5;
            patt[0] = (byte)0x01;
            patt[1] = (byte)0x02;
            patt[2] = (byte)0x03;
            shortArrayFill(resp, (short)0, (short)resp.length, (short)0);
            nbRecFound = UiccFileView.searchRecord(mode, recordNumber, searchIndication, patt, pattOffset, pattLength, resp, respOffset, respLength);

            // Verify result
            shortArrayFill(comp, (short)0, (short)comp.length, (short)0);
            if (nbRecFound == (short)5)
            {
                comp[0] = (short)3;
                comp[1] = (short)2;
                comp[2] = (short)1;
                comp[3] = (short)5;
                comp[4] = (short)4;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else if (nbRecFound == (short)3)
            {
                comp[0] = (short)3;
                comp[1] = (short)2;
                comp[2] = (short)1;
                if (shortArrayCompare(resp, (short)0, comp, (short)0, (short)resp.length) == 0)
                    bRes &= true;
                else
                    bRes = false;
            }
            else
                bRes = false;
        }
        catch (Exception e)
        {
            bRes = false;
        }
        reportTestOutcome(testCaseNb, bRes);
    }
    
    private void shortArrayFill(short buff[], short buffOffset, short fillLength, short value)
    {
        for (short i = buffOffset; i < (short)(fillLength + buffOffset); i++)
        {
            buff[i] = value;
        }
    }

    private byte shortArrayCompare(short buff1[], short buff1Offset, short buff2[], short buff2Offset, short compLength)
    {
        byte ret = 0;
        
        for (byte i = 0; ((i < compLength) && (ret == 0)); i++)
        {
            byte j = (byte)(i+buff1Offset);
            byte k = (byte)(i+buff2Offset);
            if (buff1[j] != buff2[k])
                ret = 1;
        }
        return ret;
    }
}
