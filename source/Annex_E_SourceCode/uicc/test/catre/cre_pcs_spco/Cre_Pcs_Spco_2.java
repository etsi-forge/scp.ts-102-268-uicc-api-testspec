//-----------------------------------------------------------------------------
//    Cre_Pcs_Spco_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_spco;

import javacard.framework.*;
import uicc.access.* ;
import uicc.test.util.* ;
import uicc.toolkit.*;

public class Cre_Pcs_Spco_2 extends TestToolkitApplet
{
    private static short FID_EF_SUME = 0x6F54;
    private static byte[] Menu = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'2'};
    private static byte[] Text21 = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'2',(byte)'1'} ;
    private static byte[] Text22 = {(byte)'T',(byte)'e',(byte)'x',(byte)'t',(byte)'2',(byte)'2'} ;
    private static byte[] boldTextAttr = {(byte)0x00, (byte)0x00, (byte)0x13, (byte)0x90};
    private static byte[] normalTextAttr = {(byte)0x00, (byte)0x00, (byte)0x03, (byte)0x90};
    private byte result = (byte) 0;
    private byte NbTriggering = 0;
    boolean bRes = false;
    private static FileView UiccFileView = null;

    /**
     * Constructor of the applet
     */
    public Cre_Pcs_Spco_2()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Pcs_Spco_2 thisApplet = new Cre_Pcs_Spco_2();
    
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // toolkit registration
        thisApplet.obReg.initMenuEntry(Menu, (short) 0, (short)Menu.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.setEvent((short) EVENT_UNRECOGNIZED_ENVELOPE);

        // Get the the Uicc File View
        UiccFileView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);
    }


    public void processToolkit(short event)
    {
        NbTriggering++;
        bRes = false;                                        
        
        switch (NbTriggering)
        {
            case 1:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 1
                    // -----------------------------------------------------------------
                    UiccFileView.select(UICCConstants.FID_DF_TELECOM);
                    UiccFileView.select(FID_EF_SUME);
                    byte[] ba_Data = {(byte)0x85,(byte)0x09,(byte)0x54,(byte)0x45,
                                      (byte)0x53,(byte)0x54,(byte)0x20,(byte)0x55,
                                      (byte)0x49,(byte)0x43,(byte)0x43,(byte)0xFF,
                                      (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,
                                      (byte)0xFF};
                    UiccFileView.updateBinary((short)0, ba_Data, (short)0x00, (short)ba_Data.length);
                    bRes = true;                                        
                }
                catch (Exception e) {
                    bRes = false;                                        
                }
                break;
            case 2:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 2
                    // -----------------------------------------------------------------
                    UiccFileView.select(UICCConstants.FID_DF_TELECOM);
                    UiccFileView.select(FID_EF_SUME);
                    byte[] ba_Data = {(byte)0x85,(byte)0x09,(byte)0x54,(byte)0x45,
                                      (byte)0x53,(byte)0x54,(byte)0x20,(byte)0x55,
                                      (byte)0x49,(byte)0x43,(byte)0x43,(byte)0x50,
                                      (byte)0x04,(byte)0x00,(byte)0x00,(byte)0x13,
                                      (byte)0x90};
                    UiccFileView.updateBinary((short)0, ba_Data, (short)0x00, (short)ba_Data.length);
                    bRes = true;                                        
                    
                    // Release FileView static reference
                    UiccFileView = null;
                }
                catch (Exception e) {
                    bRes = false;                                        
                }
                break;
            case 3:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 8
                    // -----------------------------------------------------------------
                    obReg.setEvent((short)EVENT_EVENT_DOWNLOAD_LOCATION_STATUS);
                    obReg.setEvent((short)EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION);
                    bRes = true;
                }
                catch(Exception e) {
                    bRes = false;
                }
                break;
            case 4:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 8
                    // -----------------------------------------------------------------
                    if (event != EVENT_EVENT_DOWNLOAD_LOCATION_STATUS)
                    {
                        bRes = false;
                        break;
                    }
                    obReg.clearEvent((short)EVENT_EVENT_DOWNLOAD_LOCATION_STATUS);
                    obReg.clearEvent((short)EVENT_EVENT_DOWNLOAD_LANGUAGE_SELECTION);
                    bRes = true;
                }
                catch(Exception e) {
                    bRes = false;
                }
                break;
            case 6:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 13
                    // -----------------------------------------------------------------
                    if (event != EVENT_UNRECOGNIZED_ENVELOPE)
                    {
                        bRes = false;
                        break;
                    }
                    obReg.setEvent(EVENT_PROACTIVE_HANDLER_AVAILABLE);
                    obReg.disableMenuEntry((byte)2);
                    ProactiveHandler proHdlr  = ProactiveHandlerSystem.getTheHandler() ;
                    proHdlr.initDisplayText((byte)0x00, (byte)0x04 /*DCS_8_BIT_DATA*/, Text21, (short)0, (short)Text21.length) ;
                    proHdlr.send() ;
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 7:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 13
                    // -----------------------------------------------------------------
                    if (event != EVENT_PROACTIVE_HANDLER_AVAILABLE)
                    {
                        bRes = false;
                        break;
                    }
                    ProactiveHandler proHdlr  = ProactiveHandlerSystem.getTheHandler() ;
                    proHdlr.initDisplayText((byte)0x00, (byte)0x04 /*DCS_8_BIT_DATA*/, Text22, (short)0, (short)Text22.length) ;
                    proHdlr.send() ;
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 8:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 14-2
                    // -----------------------------------------------------------------
                    obReg.enableMenuEntry((byte)2);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 9:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 14-5
                    // -----------------------------------------------------------------
                    obReg.setMenuEntryTextAttribute((byte)2, boldTextAttr, (short)0, (short)4);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 10:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 14-7
                    // -----------------------------------------------------------------
                    obReg.disableMenuEntry((byte)2);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 11:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 14-11
                    // -----------------------------------------------------------------
                    obReg.enableMenuEntry((byte)2);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 12:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 14-13
                    // -----------------------------------------------------------------
                    obReg.setMenuEntryTextAttribute((byte)2, normalTextAttr, (short)0, (short)4);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            default:
                if (event == EVENT_UNRECOGNIZED_ENVELOPE)
                {
                    bRes = true;
                    break;
                }
        }
        
        reportTestOutcome((byte)NbTriggering, bRes);        
    }
}

