//-----------------------------------------------------------------------------
//    Cre_Pcs_Pcco_2.java
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
public class Cre_Pcs_Pcco_2  extends TestToolkitApplet
{
   // Number of tests
   byte testCaseNb = (byte) 0x00;
   private static  byte[] menuItem2 = {(byte)'I', (byte)'t', (byte)'e', (byte)'m', (byte)' ', (byte)'2', (byte)'2', (byte)'2', (byte)'2'};
   
   /**
   * Constructor of the applet
   */
   public Cre_Pcs_Pcco_2 () 
   {
   }
   
   
   /**
   * Method called by the JCRE at the installation of the applet
   */
   public static void install(byte bArray[], short bOffset, byte bLength) 
   {
      // Create a new applet instance
      Cre_Pcs_Pcco_2  thisApplet = new Cre_Pcs_Pcco_2 ();
             
      // Register the new applet instance to the JCRE
      thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
      
      // Initialise the data of the test applet
      thisApplet.init();  
          
      //register to EVENT_MENU_SELECTION
      thisApplet.obReg.initMenuEntry(menuItem2,(short)0,(short)menuItem2.length,(byte)0,false,(byte)0,(short)0);
   }
   
   
   /**
   * Method called by the SIM Toolkit Framework
   */
   public void processToolkit(short event) 
   {
      // Result of tests
      boolean bRes;
      // Miscellaneous
      byte bTimerId;
      byte bChannelNb;
      byte bServiceId;
      final byte[] address = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
      final byte[] CSDBearerDesc = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
      final byte[] GPRSBearerDesc = {(byte)0x02, (byte)0x01, (byte)0x01, (byte)0x02, (byte)0x01, (byte)0x31};
      
      // Get the system instance of handlers
      ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
      
      switch ( testCaseNb ) 
      {
         case (byte) 0 :
            
            // --------------------------------------------
            // Test Case 2 : TIMER MANAGEMENT proactive command
            // --------------------------------------------
            testCaseNb = (byte) 1 ;
            
            try {
               // Applet 2 allocates 8 timers
               // --------------------------------------------
               for (bTimerId=0; bTimerId < 8; bTimerId++)
               {
                  obReg.allocateTimer();
               }
               // Applet 2 releases 3 timers (from id 1 to 3)
               // --------------------------------------------
               for (bTimerId=1; bTimerId < 4; bTimerId++)
               {
                   obReg.releaseTimer(bTimerId);
               }
               bRes = true;
            }
               catch (Exception e) {
               bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes) ;
            
            break;
            
         case (byte) 1 :
            
            // --------------------------------------------
            // Test Case 2 : TIMER MANAGEMENT proactive command
            // --------------------------------------------
            testCaseNb = (byte) 2 ;
            
            try {
               // Applet 2 releases 4 timers (from id 4 to 7)
               // --------------------------------------------
               for (bTimerId=4; bTimerId < 8; bTimerId++)
               {
                   obReg.releaseTimer(bTimerId);
               }
               bRes = true;
            }
               catch (Exception e) {
               bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes) ;
            
            break;
            
         case (byte) 2 :
            // --------------------------------------------
            // Test Case 4 : 4 channels allowed
            // --------------------------------------------
            
            // Build the CSD OPEN CHANNEL command to open channel 1
            // --------------------------------------------
            testCaseNb = (byte) 3 ;
            bRes = false;

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
            
            // Build the GPRS OPEN CHANNEL command to open channel 2
            // --------------------------------------------
            testCaseNb = (byte) 4 ;
            bRes = false;
            proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, (byte)DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, GPRSBearerDesc, (short)0x00, (short)GPRSBearerDesc.length);
            proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
            try {
               proHdlr.send();
               bRes = true;
            }
            catch (Exception e) {
               bRes = false;
            }
            reportTestOutcome(testCaseNb, bRes) ;
            
            // Build and send SEND DATA command on non allowed channels
            // --------------------------------------------
            testCaseNb = (byte) 5 ;
            bRes = true;
            
            for (bChannelNb = 0x23; bChannelNb <= 0x27 ; bChannelNb++)
            {
               proHdlr.init(PRO_CMD_SEND_DATA, (byte)0x01, bChannelNb);
               proHdlr.appendTLV(TAG_CHANNEL_DATA, (byte)0x55, (byte)0xAA);
               try {
                  proHdlr.send();
                  bRes &= false;
               }
               catch (ToolkitException e){    
                  if (e.getReason() == ToolkitException.COMMAND_NOT_ALLOWED) 
                  {
                      bRes &= true ;
                  }
               }
               catch (Exception e)    {    
                  bRes &= false ;
               }
            }				
            reportTestOutcome(testCaseNb, bRes) ;
            
            // Build and send RECEIVE DATA command on non allowed channels
            // --------------------------------------------
            testCaseNb = (byte) 6 ;
            bRes = true;

            for (bChannelNb = 0x23; bChannelNb <= 0x27 ; bChannelNb++)
            {
               proHdlr.init(PRO_CMD_RECEIVE_DATA, (byte)0x01, bChannelNb);
               proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x10);
               try {
                  proHdlr.send();
                  bRes &= false;
               }
               catch (ToolkitException e){    
                  if (e.getReason() == ToolkitException.COMMAND_NOT_ALLOWED) 
                  {
                      bRes &= true ;
                  }
               }
               catch (Exception e)    {    
                  bRes &= false ;
               }
            }				
            reportTestOutcome(testCaseNb, bRes) ;
            
            // Build and send CLOSE CHANNEL command on non allowed channels
            // --------------------------------------------
            testCaseNb = (byte) 7 ;
            bRes = true;

            for (bChannelNb = 0x03; bChannelNb <= 0x07 ; bChannelNb++)
            {
               proHdlr.initCloseChannel(bChannelNb);
               try {
                  proHdlr.send();
                  bRes &= false;
               }
               catch (ToolkitException e){    
                  if (e.getReason() == ToolkitException.COMMAND_NOT_ALLOWED) 
                  {
                      bRes &= true ;
                  }
               }
               catch (Exception e)    {    
                  bRes &= false ;
               }
            }				
            reportTestOutcome(testCaseNb, bRes) ;
            
            // Build a CSD OPEN CHANNEL command to open channel 3
            // --------------------------------------------
            testCaseNb = (byte) 8 ;
            bRes = false;

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
            
            // Send a new CSD OPEN CHANNEL command 
            // --------------------------------------------
            testCaseNb = (byte) 9 ;
            bRes = false;

            try {
               proHdlr.send();
               bRes = false;
            }
            catch (ToolkitException e){    
               if (e.getReason() == ToolkitException.COMMAND_NOT_ALLOWED) 
               {
                   bRes = true ;
               }
            }
            catch (Exception e)    {    
               bRes = false ;
            }
            reportTestOutcome(testCaseNb, bRes) ;
            
            break;

          case (byte) 9 :
            
             // -----------------------------------------------
             // Test Case 5 : DECLARE SERVICE proactive command
             // -----------------------------------------------
             testCaseNb = (byte) 10 ;
            
             try {
                 // Applet 2 allocates 8 services
                 // --------------------------------------------
                 for (bServiceId=0; bServiceId < 8; bServiceId++)
                 {
                     obReg.allocateServiceIdentifier();
                 }
                 // Applet 2 releases 3 services (from id 0 to 2)
                 // --------------------------------------------
                 for (bServiceId=0; bServiceId < 3; bServiceId++)
                 {
                     obReg.releaseServiceIdentifier((byte)bServiceId);
                 }
                 bRes = true;
             }
             catch (Exception e) {
                 bRes = false;
             }
             reportTestOutcome(testCaseNb, bRes) ;
             
             break;
            
          case (byte) 10 :
            
             // --------------------------------------------
             // Test Case 5 : DECLARE SERVICE proactive command
             // --------------------------------------------
             testCaseNb = (byte) 11 ;
            
             try {
                // Applet 2 releases 3 services (from id 5 to 7)
                // --------------------------------------------
                for (bServiceId=4; bServiceId < 8; bServiceId++)
                {
                    obReg.releaseServiceIdentifier(bServiceId);
                }
                bRes = true;
             }
             catch (Exception e) {
                bRes = false;
             }
             reportTestOutcome(testCaseNb, bRes) ;
            
             break;
            
      } // End switch
   }
}
