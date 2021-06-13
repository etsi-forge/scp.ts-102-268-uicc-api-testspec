//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_tkr_smta;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import javacard.framework.* ;
import uicc.test.util.* ;

/**
 * uicc.toolkit package, ToolkitRegistry interface, setMenuEntryTextAttribute() method
 * applet 1
 */
public class Api_2_Tkr_Smta_1 extends TestToolkitApplet {

    private static byte[] menu1 = {
        (byte) 'A', (byte) 'p', (byte) 'p', (byte) 'l', (byte) 'e', (byte)'t',(byte)'1'
    };
    
    private byte[] textAttribute = {
            (byte) 0x00, (byte) 0x0C, (byte) 0x11, (byte) 0x02,
            (byte) 0x00, (byte) 0x0C, (byte) 0x10, (byte) 0x03
        };
    
    private byte[] shortTextAttribute = {
            (byte) 0x00, (byte) 0x0C, (byte) 0x11
        };
    
    private boolean bRes = true;
    private byte testCaseNb = 1;

 
    /**
     * Constructor of the applet
     */
    public Api_2_Tkr_Smta_1() {
    }

    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {
        
        // Create a new applet instance
        Api_2_Tkr_Smta_1 thisApplet = new Api_2_Tkr_Smta_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), (byte)bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();
        
        // Register to EVENT_UNRECOGNIZED_ENVELOPE
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
        
        thisApplet.obReg.initMenuEntry(menu1, (short) 0, (short) menu1.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(menu1, (short) 0, (short) menu1.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(menu1, (short) 0, (short) menu1.length, (byte) 0, false, (byte) 0, (short) 0);
        thisApplet.obReg.initMenuEntry(menu1, (short) 0, (short) menu1.length, (byte) 0, false, (byte) 0, (short) 0);
    }


    /**
     * Method called by the CAT RE
     */
    public void processToolkit(short event) {


        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {

            switch (testCaseNb) {

            /** Testcase 1:
             *  Text attribute update 1
             *  No exception shall be thrown.
             */
            case 1:
                try {
                    bRes=true;
                    obReg.setMenuEntryTextAttribute((byte)0x02, textAttribute, (short)0, (short)4);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;

                
            /** Testcase 2
             *  Text attribute update 2 
             *  No exception shall be thrown.
             */
            case 2:
                try {
                    bRes=true;
                    obReg.setMenuEntryTextAttribute((byte)0x04, textAttribute, (short)4, (short)4);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;

                
            /** Testcase 3
             *  Call setMenuEntryTextAttribute() with null textAttribute
             *  Shall throw a NullPointerException
             */
            case 3:
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x04, null, (short)4, (short)4);
                }
                catch (NullPointerException e) {
                    bRes=true;
                }
                catch (Exception e) {
                    bRes=false;
                }               
                reportTestOutcome(testCaseNb, bRes);
                break;
            
                
            /** Testcase 4
             *  DstOffset >= dstBuffer.length
             *  Shall throw a ArrayIndexOutOfBoundsException
             */
            case 4: 
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x04, textAttribute, (short)8, (short)4);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes=true;
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;

                
            /** Testcase 5
             *  dstOffset < 0
             *  Shall throw a ArrayIndexOutOfBoundsException
             */
            case 5:
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x04, textAttribute, (short)-1, (short)4);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes=true;
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;


            /** Testcase 6
             *  DstLength > dstBuffer.length
             *  Shall throw a ArrayIndexOutOfBoundsException
             */
            case 6: 
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x04, shortTextAttribute, (short)0, (short)4);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes=true;
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;


            /** Testcase 7
             *  dstOffset + dstLength > dstBuffer.length
             *  Shall throw a ArrayIndexOutOfBoundsException
             */
            case 7:
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x04, textAttribute, (short)5, (short)4);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    bRes=true;
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;


            /** Testcase 8
             *  Call setMenuEntryTextAttribute() with Id = 08
             *  Shall throw a ToolkitException with MENU_ENTRY_NOT_FOUND  reason code 
             */
           case 8:                 
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x08, textAttribute, (short)0, (short)4);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.MENU_ENTRY_NOT_FOUND);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;
                

            /** Testcase 9
             *  Call setMenuEntryTextAttribute() with length = 2
             *  Shall throw a ToolkitException with BAD_INPUT_PARAMETER reason code
             */
            case 9: 
                try {
                    bRes=false;
                    obReg.setMenuEntryTextAttribute((byte)0x01, textAttribute, (short)0, (short)2);
                }
                catch (ToolkitException e) {
                    bRes = (e.getReason() == ToolkitException.BAD_INPUT_PARAMETER);
                }
                catch (Exception e) {
                    bRes=false;
                }
                reportTestOutcome(testCaseNb, bRes);
                break;
        
            }           
            testCaseNb++;
        }
    }
}
