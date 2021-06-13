//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_rgfes_bss_bsb;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, registerFileEvent() method
 * applet 1
 */
public class Api_2_Tkr_Rgfes_Bss_Bsb_1 extends TestToolkitApplet {

    private boolean bRes = true;
    private byte callNb = 1;
    private boolean hasBeenTriggered = false;
    
    private byte[] adf1Aid;
    private byte[] baADFAid15;
    private byte[] baADFAid18;
    
    private byte[] baFileList;
    private short sFileListLength;
    
    private byte[] baFileList1 =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09
    };

    private byte[] baFileList2 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A
    };

    private byte[] baFileList3 =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09
    };

    private byte[] baFileList4 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A
    };
    
    private byte[] baFileList6 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x5F,(byte)0x16 ,(byte)0x16
    };

    private byte[] baFileList21 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03
    };
    
    private byte[] baFileList22 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x5F,(byte)0x10
    };

    private byte[] baFileList23 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x5F,(byte)0x10, (byte)0x4F,(byte)0x16
    };
    

    private byte[] baFileList24 =  {
            (byte)0x01,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x5F,(byte)0x16
    };

    
    static final byte FILE_LIST_TAG = (byte) 0x92;
    
    
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Rgfes_Bss_Bsb_1() {
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
        Api_2_Tkr_Rgfes_Bss_Bsb_1 thisApplet = new Api_2_Tkr_Rgfes_Bss_Bsb_1();

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
             *  Register EF under MF
             *******************************************************/
            
            case 1:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList1,
                                            (short) 0,
                                            (short) baFileList1.length,
                                            null,
                                            (short) 0,
                                            (byte) 0 );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 2:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList1,
                            (short) 0,
                            (short) baFileList1.length,
                            null,
                            (short) 0,
                            (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 1, bRes);
                break;
                    
            /** Testcase 2 *****************************************
             *  Register DF under MF
             *******************************************************/
                
            case 3:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList2,
                                            (short) 0,
                                            (short) baFileList2.length,
                                            null,
                                            (short) 0,
                                            (byte) 0 );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 4:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList2,
                            (short) 0,
                            (short) baFileList2.length,
                            null,
                            (short) 0,
                            (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 2, bRes);
                break;
                
                
            /** Testcase 3 *****************************************
             *  Register EF under ADF1
             *******************************************************/
            case 5:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList3,
                                            (short) 0,
                                            (short) baFileList3.length,
                                            adf1Aid,
                                            (short) 0,
                                            (byte) adf1Aid.length );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 6:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList3,
                            (short) 0,
                            (short) baFileList3.length,
                            adf1Aid,
                            (short) 0,
                            (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 3, bRes);
                break;
                
            /** Testcase 4 *****************************************
             *  Register DF under ADF1
             *******************************************************/
            case 7:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList4,
                                            (short) 0,
                                            (short) baFileList4.length,
                                            adf1Aid,
                                            (short) 0,
                                            (byte) adf1Aid.length );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;

            case 8:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList4,
                            (short) 0,
                            (short) baFileList4.length,
                            adf1Aid,
                            (short) 0,
                            (byte) adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 4, bRes);
                break;
                
                
            /** Testcase 5 *****************************************
             *  NullPointerException Exception
             *******************************************************/

            case 9:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            null,
                            (short) 0,
                            (short) 0,
                            null,
                            (short) 0,
                            (byte)  0 );
                }
                catch (NullPointerException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 5, bRes);
                break;

                
            /** Testcase 6 *****************************************
             *  sOffset1 >= baFileList.length
             *******************************************************/
                
            case 10:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6,
                            (short) 8,
                            (short) 4,
                            null,
                            (short) 0,
                            (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 6, bRes);
                break;
                
            /** Testcase 7 *****************************************
             *  sOffset1 < 0
             *******************************************************/
                
            case 11:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6,
                            (short) -1,
                            (short) 4,
                            null,
                            (short) 0,
                            (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 7, bRes);
                break;
            
            /** Testcase 8 *****************************************
             *  sLength1 > baFileList.length
             *******************************************************/
            
            case 12:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6,
                            (short) 0,
                            (short) 10,
                            null,
                            (short) 0,
                            (byte)  0 );
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
             *  sOffset1 + sLength1 > baFileList.length
             *******************************************************/
                
            case 13:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6,
                            (short) 5,
                            (short) 4,
                            null,
                            (short) 0,
                            (byte)  0 );
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
             *  sLength1 < 0
             *******************************************************/
                
            case 14:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6,
                            (short) 0,
                            (short) -1,
                            null,
                            (short) 0,
                            (byte)  0 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 10, bRes);
                break;
                
            
            /** Testcase 11 *****************************************
             *  sOffset2 >= baFileList.length
             *******************************************************/
                
            case 15:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList6,
                            (short) 0,
                            (short) baFileList6.length,
                            baADFAid15,
                            (short) 15,
                            (byte)  6 );
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes = true;
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 11, bRes);
                break;
            
                
            /** Testcase 12 *****************************************
             *  sOffset2 < 0
             *******************************************************/
                
            case 16:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             baADFAid15,
                                            (short) -1,
                                            (byte)  6 );
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
             *  sLength2 > baFileList.length
             *******************************************************/

            case 17:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             baADFAid15,
                                            (short) 0,
                                            (byte)  16 );
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
             *  sOffset2 + sLength2 > baFileList.length
             *******************************************************/

            case 18:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             baADFAid15,
                                            (short) 10,
                                            (byte)  6 );
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
             *  ILLEGAL_VALUE Exception
             *******************************************************/
                
            case 19:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             baADFAid18,
                                            (short) 0,
                                            (byte)  4 );
                }
                catch (SystemException e) {
                    bRes = (e.getReason() == SystemException.ILLEGAL_VALUE);
                }
                catch (Exception e) {
                    bRes = false;
                }
                
                try {
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             baADFAid18,
                                            (short) 0,
                                            (byte)  18 );
                }
                catch (SystemException e) {
                    bRes &= (e.getReason() == SystemException.ILLEGAL_VALUE);
                }
                catch (Exception e) {
                    bRes = false;
                }
                
                reportTestOutcome((byte) 15, bRes);
                break;
                
                
            /** Testcase 16 *****************************************
             *  EVENT_MENU_SELECTION not allowed
             *******************************************************/
                
            case 20:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_MENU_SELECTION,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             null,
                                            (short) 0,
                                            (byte)  0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 16, bRes);
                break;
                
                
            /** Testcase 17 *****************************************
             *  EVENT_MENU_SELECTION_HELP_REQUEST not allowed
             *******************************************************/

            case 21:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_MENU_SELECTION_HELP_REQUEST,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             null,
                                            (short) 0,
                                            (byte)  0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 17, bRes);
                break;
                
                
            /** Testcase 18 *****************************************
             *  EVENT_TIMER_EXPIRATION not allowed
             *******************************************************/

            case 22:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_TIMER_EXPIRATION,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             null,
                                            (short) 0,
                                            (byte)  0 );
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
             *  EVENT_STATUS_COMMAND not allowed
             *******************************************************/
                
            case 23:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_STATUS_COMMAND,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             null,
                                            (short) 0,
                                            (byte)  0 );
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
             *  EVENT_NOT_SUPPORTED Exception 
             *******************************************************/
                
            case 24:
                try {
                    bRes = false;
                    obReg.registerFileEvent(EVENT_PROFILE_DOWNLOAD,
                                            baFileList6,
                                            (short) 0,
                                            (short) baFileList6.length,
                                             null,
                                            (short) 0,
                                            (byte)  0 );
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_SUPPORTED);
                }
                catch (Exception e) {
                    bRes = false;
                }
                reportTestOutcome((byte) 20, bRes);
                break;
                
            
            /** Testcase 21 *****************************************
             *  Register a deleted and recreated EF under MF
             *******************************************************/
                
            case 25:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList21,
                                            (short) 0,
                                            (short) baFileList21.length,
                                            null,
                                            (short) 0,
                                            (byte) 0 );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 26:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList21,
                            (short) 0,
                            (short) baFileList21.length,
                            null,
                            (short) 0,
                            (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 21, bRes);
                break;
          
                
            /** Testcase 22 *****************************************
             *  Register a deleted and recreated DF under MF
             *******************************************************/
                
            case 27:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList22,
                                            (short) 0,
                                            (short) baFileList22.length,
                                            null,
                                            (short) 0,
                                            (byte) 0 );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 28:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList22,
                            (short) 0,
                            (short) baFileList22.length,
                            null,
                            (short) 0,
                            (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 22, bRes);
                break;
            
                
            /** Testcase 23 *****************************************
             *  Register a non existant EF under MF
             *******************************************************/
                    
            case 29:
                try {
                    bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                            baFileList23,
                                            (short) 0,
                                            (short) baFileList23.length,
                                            null,
                                            (short) 0,
                                            (byte) 0 );
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 30:
                try {
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList23,
                            (short) 0,
                            (short) baFileList23.length,
                            null,
                            (short) 0,
                            (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 23, bRes);
                break;
                
                
                
                /** Testcase 24 *****************************************
                 *  Register a non existant DF under MF
                 *******************************************************/
                        
                case 31:
                    try {
                        bRes = !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                        obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                                baFileList24,
                                                (short) 0,
                                                (short) baFileList24.length,
                                                null,
                                                (short) 0,
                                                (byte) 0 );
                        bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);

                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    break;
                    
                case 32:
                    try {
                        obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                                baFileList24,
                                (short) 0,
                                (short) baFileList24.length,
                                null,
                                (short) 0,
                                (byte) 0 );
                    }
                    catch (Exception e) {
                        bRes=false;
                    }
                    reportTestOutcome((byte) 24, bRes);
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
