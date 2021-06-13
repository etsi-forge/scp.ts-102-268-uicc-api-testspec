//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_drfes_bss_bsb;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, deregisterFileEvent() method
 * applet 1
 */
public class Api_2_Tkr_Drfes_Bss_Bsb_1 extends TestToolkitApplet {

    private boolean bRes = true;
    private byte callNb = 1;
    private boolean hasBeenTriggered = false;
    
    private byte[] adf1Aid;
    private byte[] baADFAid15;
    private byte[] baADFAid18;
    
    private byte[] baFileList;
    private short sFileListLength;
    
    private byte[] baFileList1a =  {
            (byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x0C
    };
    
    private byte[] baFileList1b =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09
    };
    
    private byte[] baFileList1c =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x0C
    };
    
    private byte[] baFileList2a =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A
    };
    
    private byte[] baFileList2b =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A
    };
    
    private byte[] baFileList3a = baFileList2b;
    
    private byte[] baFileList3b =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03
    };
    
    private byte[] baFileList4a =  {
            (byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x0C
    };

    private byte[] baFileList4b =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09
    };
    
    private byte[] baFileList4c =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x0C
    };
    
    private byte[] baFileList5a =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A
    };
    
    private byte[] baFileList5b =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A
    };
    
    private byte[] baFileList6a =  baFileList5b;
    
    private byte[] baFileList6b =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03
    };
    
    
    static final byte FILE_LIST_TAG = (byte) 0x92;
    
    
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Drfes_Bss_Bsb_1() {
        baFileList = new byte[10];
        adf1Aid = new byte[16];
        baADFAid15 = new byte[15];
        baADFAid18 = new byte[18];
        UICCTestConstants uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1,(short)0,adf1Aid,(short)0,(short)adf1Aid.length);
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Drfes_Bss_Bsb_1 thisApplet = new Api_2_Tkr_Drfes_Bss_Bsb_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialize the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);

        // Register to EVENT_CALL_CONTROL_BY_NAA
        thisApplet.obReg.setEvent(EVENT_CALL_CONTROL_BY_NAA);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            switch (callNb++) {

            /** Testcase 1 *****************************************
             *  Deregister EF under MF
             *******************************************************/
            
            case 1:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                        baFileList1a, (short) 0, (short) baFileList1a.length,
                        null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 2:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1b, (short) 0, (short) baFileList1b.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 3:
                try {
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1c, (short) 0, (short) baFileList1c.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 4:
                try {
                    bRes &= !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 1, bRes);
                break;
            
                
            /** Testcase 2 *****************************************
             *  Deregister DF under MF
             *******************************************************/

            case 5:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList2a, (short) 0, (short) baFileList2a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 6:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList2b, (short) 0, (short) baFileList2b.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 2, bRes);
                break;
                
                
            /** Testcase 3 *****************************************
             *  Deregister EF does not affect parent DF
             *******************************************************/

            case 7:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList3a, (short) 0, (short) baFileList3a.length,
                            null, (short) 0, (byte) 0 );

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 8:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList3b, (short) 0, (short) baFileList3b.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 3, bRes);
                break;
                
                
            /** Testcase 4 *****************************************
             *  Deregister EF under ADF1
             *******************************************************/
                
            case 9:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList3a, (short) 0, (short) baFileList3a.length,
                            null, (short) 0, (byte) 0 );

                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList4a, (short) 0, (short) baFileList4a.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 10:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList4b, (short) 0, (short) baFileList4b.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 11:
                try {
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList4c, (short) 0, (short) baFileList4c.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 12:
                try {
                    bRes &= !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 4, bRes);
                break;

                
            /** Testcase 5 *****************************************
             *  Deregister DF does not affect child EF (under ADF1)
             *******************************************************/
                    
            case 13:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList5a, (short) 0, (short) baFileList5a.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 14:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList5b, (short) 0, (short) baFileList5b.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 5, bRes);
                break;
                
            /** Testcase 6 *****************************************
             *  Deregister EF does not affect parent DF (under ADF1)
             *******************************************************/
                    
            case 15:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6a, (short) 0, (short) baFileList6a.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 16:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6b, (short) 0, (short) baFileList6b.length,
                            adf1Aid, (short) 0, (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 6, bRes);
                break;
                
                
            /** Testcase 7 *****************************************
             *  NullPointerException Exception
             *******************************************************/

            case 17:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            null, (short) 0, (short) baFileList1a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (NullPointerException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 7, bRes);
                break;

                
            /** Testcase 8 *****************************************
             *  sOffset1 >= baFileList.length
             *******************************************************/
                
            case 18:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1c, (short) 8, (short) 4,
                            null, (short) 0, (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 8, bRes);
                break;
                
            /** Testcase 9 *****************************************
             *  sOffset1 < 0
             *******************************************************/
                
            case 19:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) -1, (short) 4,
                            null, (short) 0, (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 9, bRes);
                break;
            
            /** Testcase 10 ****************************************
             *  sLength1 > baFileList.length
             *******************************************************/
            
            case 20:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1c, (short) 0, (short) 10,
                            null, (short) 0, (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 10, bRes);
                break;
                
            
            /** Testcase 11 ****************************************
             *  sOffset1 + sLength1 > baFileList.length
             *******************************************************/
                
            case 21:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1c, (short) 5, (short) 4,
                            null, (short) 0, (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 11, bRes);
                break;
                
            
            /** Testcase 12 ****************************************
             *  sLength1 < 0
             *******************************************************/
                
            case 22:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) -1,
                            null, (short) 0, (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 12, bRes);
                break;
                
            
            /** Testcase 13 *****************************************
             *  sOffset2 >= baFileList.length
             *******************************************************/
                
            case 23:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            baADFAid15, (short) 15, (byte)  6 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 13, bRes);
                break;
            
                
            /** Testcase 14 *****************************************
             *  sOffset2 < 0
             *******************************************************/
                
            case 24:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                             baADFAid15, (short) -1, (byte)  6 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 14, bRes);
                break;
                
            
            /** Testcase 15 *****************************************
             *  sLength2 > baFileList.length
             *******************************************************/

            case 25:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            baADFAid15, (short) 0, (byte)  16 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 15, bRes);
                break;
                
                
            /** Testcase 16 *****************************************
             *  sOffset2 + sLength2 > baFileList.length
             *******************************************************/

            case 26:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            baADFAid15, (short) 10, (byte) 6 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 16, bRes);
                break;
                
                
            /** Testcase 17 *****************************************
             *  ILLEGAL_VALUE Exception
             *******************************************************/
                
            case 27:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            baADFAid18, (short) 0, (byte) 4 );
                }
                catch (SystemException e) {
                    bRes = (e.getReason() == SystemException.ILLEGAL_VALUE);
                }
                catch (Exception e) {
                    bRes = false;
                }
                
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            baADFAid18, (short) 0, (byte) 18 );
                }
                catch (SystemException e) {
                    bRes &= (e.getReason() == SystemException.ILLEGAL_VALUE);
                }
                catch (Exception e) {
                    bRes = false;
                }
                
                reportTestOutcome((byte) 17, bRes);
                break;
                
                
            /** Testcase 18 *****************************************
             *  EVENT_MENU_SELECTION not allowed
             *******************************************************/
                
            case 28:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_MENU_SELECTION,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 18, bRes);
                break;
                
                
            /** Testcase 19 *****************************************
             *  EVENT_MENU_SELECTION_HELP_REQUEST not allowed
             *******************************************************/

            case 29:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_MENU_SELECTION_HELP_REQUEST,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 19, bRes);
                break;
                
                
            /** Testcase 20 *****************************************
             *  EVENT_TIMER_EXPIRATION not allowed
             *******************************************************/

            case 30:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_TIMER_EXPIRATION,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 20, bRes);
                break;
                
                    
            /** Testcase 21 *****************************************
             *  EVENT_STATUS_COMMAND not allowed
             *******************************************************/
                
            case 31:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_STATUS_COMMAND,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 21, bRes);
                break;
                
            
            /** Testcase 22 *****************************************
             *  EVENT_NOT_SUPPORTED Exception 
             *******************************************************/
                
            case 32:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_PROFILE_DOWNLOAD,
                            baFileList1a, (short) 0, (short) baFileList1a.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_SUPPORTED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 22, bRes);
                break;
                
            }           
        }
        
        else if (event == EVENT_EXTERNAL_FILE_UPDATE) {
            // If triggered on EVENT_EXTERNAL_FILE_UPDATE the applet stores the triggering File List in baFileList
            sFileListLength = EnvelopeHandlerSystem.getTheHandler().findAndCopyValue(FILE_LIST_TAG, baFileList, (short)0);
            hasBeenTriggered = true;
        }
        
        else if (event == EVENT_CALL_CONTROL_BY_NAA) {
            // If triggered on EVENT_CALL_CONTROL_BY_NAA the applet returns the latest triggering File List
            EnvelopeResponseHandler envRespHdlr = EnvelopeResponseHandlerSystem.getTheHandler();

            if (hasBeenTriggered) {
                envRespHdlr.appendArray(baFileList, (short) 0, (short) sFileListLength);
            }
            else { 
                envRespHdlr.appendTLV((byte)0x00, (byte)0x00);
            }

            envRespHdlr.post(true);
            
            hasBeenTriggered = false;
        }
        
    }
}
