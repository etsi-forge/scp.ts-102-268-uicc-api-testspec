//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_drfeso;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.access.*;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, registerFileEvent() method
 * applet 1
 */
public class Api_2_Tkr_Drfeso_1 extends TestToolkitApplet {

    private boolean bRes = true;
    private byte callNb  = 1;

    private byte[] adf1Aid        = null;
    private FileView uiccFileView = null;
    private FileView adf1FileView = null; 

    private boolean hasBeenTriggered = false;
    private byte[] baFileList        = null;
    private short sFileListLength    = 0;
    
    private byte[] baFileList1 =  {
            (byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x0C
    };

    private byte[] baFileList2 =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0x4A
    };

    private byte[] baFileList4 =  {
            (byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x09,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x0C
    };

    private byte[] baFileList5 =  {
            (byte)0x02,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A, (byte)0x6F,(byte)0x03,
            (byte)0x3F,(byte)0x00, (byte)0x7F,(byte)0xFF, (byte)0x7F,(byte)0x4A
    };
    
    
    
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Drfeso_1() {
        baFileList = new byte[16];
        adf1Aid = new byte[16];
        UICCTestConstants uiccTestConstants = new UICCTestConstants();
        Util.arrayCopyNonAtomic(uiccTestConstants.AID_ADF1,(short)0,adf1Aid,(short)0,(short)adf1Aid.length);
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Drfeso_1 thisApplet = new Api_2_Tkr_Drfeso_1();

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
                            baFileList1,
                            (short) 0,
                            (short) baFileList1.length,
                            null,
                            (short) 0,
                            (byte)  0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                    
            case 2:
                try {
                    uiccFileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
                    uiccFileView.select(UICCTestConstants.FID_DF_TEST);
                    uiccFileView.select(UICCTestConstants.FID_EF_TARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, uiccFileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 3:
                try {
                    uiccFileView.select(UICCTestConstants.FID_EF_CARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, uiccFileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 4:
                try {
                    bRes &= obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                    uiccFileView.select(UICCTestConstants.FID_EF_LARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, uiccFileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 5:
                try {
                    bRes &= !obReg.isEventSet(EVENT_EXTERNAL_FILE_UPDATE);
                }
                catch (Exception e) {
                    bRes=false;
                }
                
                reportTestOutcome((byte) 1, bRes);
                break;
                
                
            /** Testcase 2 *****************************************
             *  Deregister DF does not affect child EF
             *******************************************************/
                
            case 6:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList2, (short) 0, (short) baFileList2.length,
                            null, (short) 0, (byte) 0 );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 7:
                try {
                    uiccFileView.select(UICCTestConstants.FID_MF);
                    uiccFileView.select(UICCTestConstants.FID_DF_TEST);
                    uiccFileView.select(UICCTestConstants.FID_EF_TARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, uiccFileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
            
                reportTestOutcome((byte) 2, bRes);
                break;  
                
                
            /** Testcase 3 *****************************************
             *  Deregister EF does not affect parent DF
             *******************************************************/
                
            case 8:
                try {
                    bRes = true;
                    uiccFileView.select(UICCTestConstants.FID_MF);
                    uiccFileView.select(UICCTestConstants.FID_DF_TEST);
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE, uiccFileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
           
            case 9:
                try {
                    uiccFileView.select(UICCTestConstants.FID_MF);
                    uiccFileView.select(UICCTestConstants.FID_DF_TEST);
                    uiccFileView.select(UICCTestConstants.FID_EF_TARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, uiccFileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                
                reportTestOutcome((byte) 3, bRes);
                break; 
                
                
            /** Testcase 4 *****************************************
             *  Deregister EF under ADF1
             *******************************************************/
                
            case 10:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList4, (short) 0, (short) baFileList4.length,
                            adf1Aid,     (short) 0, (byte)  adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                    
            case 11:
                try {
                    adf1FileView = UICCSystem.getTheFileView(adf1Aid, (short) 0, (byte) adf1Aid.length, JCSystem.NOT_A_TRANSIENT_OBJECT);
                    adf1FileView.select(UICCTestConstants.FID_ADF);
                    adf1FileView.select(UICCTestConstants.FID_DF_TEST);
                    adf1FileView.select(UICCTestConstants.FID_EF_TARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, adf1FileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 12:
                try {
                    adf1FileView.select(UICCTestConstants.FID_EF_CARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, adf1FileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
            case 13:
                try {
                    adf1FileView.select(UICCTestConstants.FID_EF_LARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, adf1FileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                
                reportTestOutcome((byte) 4, bRes);
                break; 
                
                
            /** Testcase 5 *****************************************
             *  Deregister DF does not affect child EF (under ADF1)
             *******************************************************/
            case 14:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE,
                            baFileList5, (short) 0, (short) baFileList5.length,
                            adf1Aid,     (short) 0, (byte)  adf1Aid.length );
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
                
            case 15:
                try {
                    adf1FileView.select(UICCTestConstants.FID_ADF);
                    adf1FileView.select(UICCTestConstants.FID_DF_TEST);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, adf1FileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                
                reportTestOutcome((byte) 5, bRes);
                break;
                
                
            /** Testcase 6 *****************************************
             *  Deregister EF does not affect parent DF (under ADF1)
             *******************************************************/
            
            case 16:
                try {
                    bRes = true;
                    obReg.registerFileEvent(EVENT_EXTERNAL_FILE_UPDATE, adf1FileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                break;
                
                
            case 17:
                try {
                    adf1FileView.select(UICCTestConstants.FID_ADF);
                    adf1FileView.select(UICCTestConstants.FID_DF_TEST);
                    adf1FileView.select(UICCTestConstants.FID_EF_TARU);
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, adf1FileView);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome((byte) 6, bRes);
                break;
                
                    
            /** Testcase 7 *****************************************
             *  NullPointerException Exception
             *******************************************************/
                
            case 18:
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_EXTERNAL_FILE_UPDATE, null);
                }
                catch (NullPointerException e) {
                    bRes = true;
                }
                catch (Exception e) {}

                reportTestOutcome((byte) 7, bRes);

                
            /** Testcase 8 *****************************************
             *  EVENT_MENU_SELECTION not allowed
             *******************************************************/
            
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_MENU_SELECTION, uiccFileView);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {}

                reportTestOutcome((byte) 8, bRes);

            /** Testcase 9 *****************************************
             *  EVENT_MENU_SELECTION_HELP_REQUEST not allowed
             *******************************************************/
            
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_MENU_SELECTION_HELP_REQUEST, uiccFileView);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {}

                reportTestOutcome((byte) 9, bRes);
            
            /** Testcase 10 ****************************************
             *  EVENT_TIMER_EXPIRATION not allowed
             *******************************************************/
            
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_TIMER_EXPIRATION, uiccFileView);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {}

                reportTestOutcome((byte) 10, bRes);
                    
                    
            /** Testcase 11 *****************************************
             *  EVENT_STATUS_COMMAND not allowed
             *******************************************************/
            
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_STATUS_COMMAND, uiccFileView);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_ALLOWED);
                }
                catch (Exception e) {}

                reportTestOutcome((byte) 11, bRes);
                
                
            /** Testcase 12 *****************************************
             *  EVENT_NOT_SUPPORTED Exception
             *******************************************************/
            
                try {
                    bRes = false;
                    obReg.deregisterFileEvent(EVENT_PROFILE_DOWNLOAD, uiccFileView);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.EVENT_NOT_SUPPORTED);
                }
                catch (Exception e) {}

                reportTestOutcome((byte) 12, bRes);
                    
            }           
        }
        
        else if (event == EVENT_EXTERNAL_FILE_UPDATE) {
            sFileListLength = EnvelopeHandlerSystem.getTheHandler().findAndCopyValue((byte)0x92, baFileList, (short)0);
            hasBeenTriggered = true;
        }
        
        else if (event == EVENT_CALL_CONTROL_BY_NAA) {
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
