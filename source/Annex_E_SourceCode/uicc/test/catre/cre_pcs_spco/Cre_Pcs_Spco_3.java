//-----------------------------------------------------------------------------
//    Cre_Pcs_Spco_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_spco;

import uicc.test.util.* ;

public class Cre_Pcs_Spco_3 extends TestToolkitApplet
{
    private static short FID_EF_SUME = 0x6F54;
    private static byte[] Menu = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'3'};
    private byte result = (byte) 0;
    private byte NbTriggering = 0;
    boolean bRes = false;

    /**
     * Constructor of the applet
     */
    public Cre_Pcs_Spco_3()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Pcs_Spco_3 thisApplet = new Cre_Pcs_Spco_3();
    
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // toolkit registration
        thisApplet.obReg.initMenuEntry(Menu, (short) 0, (short)Menu.length, (byte) 0, false, (byte) 0, (short) 0);
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
                    // Test Case 14-9
                    // -----------------------------------------------------------------
                    byte[] italicTextAttr = {(byte)0x00, (byte)0x00, (byte)0x23, (byte)0x90};
                    obReg.setMenuEntryTextAttribute((byte)3, italicTextAttr, (byte)0, (short)4);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
            case 2:
                try {
                    // -----------------------------------------------------------------
                    // Test Case 14-9
                    // -----------------------------------------------------------------
                    byte[] normalcTextAttr = {(byte)0x00, (byte)0x00, (byte)0x03, (byte)0x90};
                    obReg.setMenuEntryTextAttribute((byte)3, normalcTextAttr, (byte)0, (short)4);
                    bRes = true;
                }
                catch (Exception e){
                    bRes = false;
                }
                break;
        }
        
        reportTestOutcome((byte)NbTriggering, bRes);        
    }
}

