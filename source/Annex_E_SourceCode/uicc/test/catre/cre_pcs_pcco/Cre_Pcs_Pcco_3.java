//-----------------------------------------------------------------------------
//    Cre_Pcs_Pcco_3.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_pcs_pcco;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;

/**
 * Test Area : sim.tookit.EnvelopeHandler.getSecuredDataLength()
 * 
 * Applet is triggered on Install (Install), EVENT_FORMATTED_SMS_PP_ENV
 *
 */
public class Cre_Pcs_Pcco_3  extends TestToolkitApplet
{
   // Number of tests
   byte testCaseNb = (byte) 0x00;
   private static  byte[] menuItem3 = {(byte)'I', (byte)'t', (byte)'e', (byte)'m', (byte)' ', (byte)'3'};
   
   /**
   * Constructor of the applet
   */
   public Cre_Pcs_Pcco_3 () 
   {
   }
   
   
   /**
   * Method called by the JCRE at the installation of the applet
   */
   public static void install(byte bArray[], short bOffset, byte bLength) 
   {
      // Create a new applet instance
      Cre_Pcs_Pcco_3  thisApplet = new Cre_Pcs_Pcco_3 ();
      
      // Register the new applet instance to the JCRE
      thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
      
      // Initialise the data of the test applet
      thisApplet.init();    

      //register to EVENT_MENU_SELECTION
      thisApplet.obReg.initMenuEntry(menuItem3,(short)0,(short)menuItem3.length,(byte)0,false,(byte)0,(short)0);
   }
   
   
   /**
   * Method called by the SIM Toolkit Framework
   */
   public void processToolkit(short event) 
   {
      // Result of tests
      boolean bRes;
      // Miscelaneous
      final byte[] address = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
      final byte[] CSDBearerDesc = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
               
      
      // Get the system instance of handlers
      ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
      
      // --------------------------------------------
      // Test Case 4 : 4 channels allowed
      // --------------------------------------------
      testCaseNb = (byte) 1 ;
      bRes = false;
      
      // Build the CSD OPEN CHANNEL command to open channel 7
      // --------------------------------------------
      proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, (byte)DEV_ID_TERMINAL);
      proHdlr.appendTLV(TAG_ADDRESS, address, (short)0x00, (short)address.length);
      proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, CSDBearerDesc, (short)0x00, (short)CSDBearerDesc.length);
      proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
      try {
         proHdlr.send();
         bRes = true;
      }
      catch (Exception e) {
         bRes = false;
      }
   
      reportTestOutcome(testCaseNb, bRes) ;
   }
}
