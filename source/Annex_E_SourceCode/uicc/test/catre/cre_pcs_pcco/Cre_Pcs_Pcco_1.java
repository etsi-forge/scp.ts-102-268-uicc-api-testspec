//-----------------------------------------------------------------------------
//    Cre_Pcs_Pcco_1.java
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
public class Cre_Pcs_Pcco_1  extends TestToolkitApplet
{
   private static  byte[] menuItem1  = {(byte) 0x01, (byte)'I', (byte)'t', (byte)'e', (byte)'m', (byte)' ', (byte)'1'};
   // Number of tests
   byte testCaseNb = (byte) 0x00;
   byte CaseNb = (byte) 0x00;
   
   /**
   * Constructor of the applet
   */
   public Cre_Pcs_Pcco_1 () 
   {
   }


   /**
   * Method called by the JCRE at the installation of the applet
   */
   public static void install(byte bArray[], short bOffset, byte length) 
   {
      // Create a new applet instance
      Cre_Pcs_Pcco_1  thisApplet = new Cre_Pcs_Pcco_1 ();
        
      // Register the new applet instance to the JCRE
      thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
      
      // Initialise the data of the test applet
      thisApplet.init();    

      //register to EVENT_MENU_SELECTION
      thisApplet.obReg.initMenuEntry(menuItem1,(short)1,(short)(menuItem1.length - 1),(byte)0,false,(byte)0,(short)0);
   }
   
   
   /**
   * Method called by the SIM Toolkit Framework
   */
   public void processToolkit(short event) 
   {
      // Result of tests
      boolean bRes = false;
      // Miscellaneous
      byte bTimerNb = 0;
      byte bServiceNb = 0;
      final byte PRO_CMD_POLL_INTERVAL     = (byte)0x03;
      final byte PRO_CMD_POLLING_OFF       = (byte)0x04;
      final byte PRO_CMD_SET_UP_EVENT_LIST = (byte)0x05;
      final byte PRO_CMD_SET_UP_MENU       = (byte)0x25;
      final byte[] menuTitle  = {(byte)'M', (byte)'e', (byte)'n', (byte)'u'};
      final byte[] eventList  = {(byte)0x01, (byte)0x02, (byte)0x03};
      final byte[] address = {(byte)0x91, (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44} ;
      final byte[] CSDBearerDesc = {(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00};
      final byte[] GPRSBearerDesc = {(byte)0x02, (byte)0x01, (byte)0x01, (byte)0x02, (byte)0x01, (byte)0x31};
      final byte[] timerValue = {(byte)0x00, (byte)0x01, (byte)0x00};
      final byte[] ServiceRecordValue = {(byte)0x00, (byte)0x00, (byte)0x00};
      
      // Get the system instance of handlers
      ProactiveHandler proHdlr = ProactiveHandlerSystem.getTheHandler() ;
      
      switch ( testCaseNb ) 
      {
         case (byte) 0 :
            // --------------------------------------------
            // Test Case 1 : TK Proactive Commands
            // --------------------------------------------
            
            //Sub Test Case 1 : SET UP MENU 
            // --------------------------------------------
            testCaseNb = (byte) 1 ;
            CaseNb = (byte) 1 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_SET_UP_MENU, (byte)0x00, (byte)DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_ALPHA_IDENTIFIER, menuTitle, (short)0x00, (short)menuTitle.length);
            proHdlr.appendTLV(TAG_ITEM_IDENTIFIER, menuItem1, (short)0x00, (short)menuItem1.length);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 2 : SET UP EVENT LIST 
            // --------------------------------------------
            CaseNb = (byte) 2 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_SET_UP_EVENT_LIST, (byte)0x00, (byte)DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_EVENT_LIST, eventList, (short)0x00, (short)eventList.length);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 3 : POLL INTERVAL 
            // --------------------------------------------
            CaseNb = (byte) 3 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_POLL_INTERVAL, (byte)0x00, (byte)DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_DURATION, (byte)0x01, (byte)0x02);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 4 : POLLING OFF 
            // --------------------------------------------
            CaseNb = (byte) 4 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_POLLING_OFF, (byte)0x00, (byte)DEV_ID_TERMINAL);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            break;
            
         case (byte) 1 :
            // --------------------------------------------
            // Test Case 2 : TIMER MANAGEMENT proactive command
            // --------------------------------------------
            testCaseNb = (byte) 2 ;
            
            // Applet 3 allocates 3 timers (Id 1 to 3)
            // --------------------------------------------
            CaseNb = (byte) 5 ;
            bRes = false ;

            try {
               obReg.allocateTimer();
               obReg.allocateTimer();
               obReg.allocateTimer();
               bRes = true;
            }
            catch (Exception e) {
               bRes = false;
            }
            reportTestOutcome(CaseNb, bRes) ;

            break;
            
         case (byte) 2 :
            // --------------------------------------------
            // Test Case 2 : TIMER MANAGEMENT proactive command
            // --------------------------------------------
            testCaseNb = (byte) 3 ;
            
            
            // TIMER MANAGEMENT commands on allowed timers
            // --------------------------------------------
            CaseNb = (byte) 6 ;
            bRes = true ;

            for (bTimerNb = 1; bTimerNb < 4; bTimerNb++)
            {
               proHdlr.init(PRO_CMD_TIMER_MANAGEMENT, (byte)0x00, (byte)DEV_ID_TERMINAL);
               proHdlr.appendTLV(TAG_TIMER_IDENTIFIER, bTimerNb);
               proHdlr.appendTLV(TAG_TIMER_VALUE, timerValue, (short)0x00, (short)timerValue.length);
               try {
                  proHdlr.send();
                  bRes &= true;
               }
               catch (Exception e)    {    
                  bRes &= false ;
               }
            }
            reportTestOutcome(CaseNb, bRes) ;
            
            // TIMER MANAGEMENT commands on not allowed timers
            // --------------------------------------------
            CaseNb = (byte) 7 ;
            bRes = true ;

            for (bTimerNb = 4; bTimerNb < 9; bTimerNb++)
            {
               proHdlr.init(PRO_CMD_TIMER_MANAGEMENT, (byte)0x00, (byte)DEV_ID_TERMINAL);
               proHdlr.appendTLV(TAG_TIMER_IDENTIFIER, bTimerNb);
               proHdlr.appendTLV(TAG_TIMER_VALUE, timerValue, (short)0x00, (short)timerValue.length);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            break;
            
         case (byte) 3 :
            // --------------------------------------------
            // Test Case 3 : No channel allowed
            // --------------------------------------------
            testCaseNb = (byte) 4 ;
            
            //Sub Test Case 1 : CSD OPEN CHANNEL 
            // --------------------------------------------
            CaseNb = (byte) 8 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, (byte)DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_ADDRESS, address, (short)0x00, (short)address.length);
            proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, CSDBearerDesc, (short)0x00, (short)CSDBearerDesc.length);
            proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 2 : GPRS OPEN CHANNEL 
            // --------------------------------------------
            CaseNb = (byte) 9 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_OPEN_CHANNEL, (byte)0x01, (byte)DEV_ID_TERMINAL);
            proHdlr.appendTLV(TAG_BEARER_DESCRIPTION, GPRSBearerDesc, (short)0x00, (short)GPRSBearerDesc.length);
            proHdlr.appendTLV(TAG_BUFFER_SIZE, (byte)0x00, (byte)0x80);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 3 : SEND DATA 
            // --------------------------------------------
            CaseNb = (byte) 10 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_SEND_DATA, (byte)0x01, (byte)0x21);
            proHdlr.appendTLV(TAG_CHANNEL_DATA, (byte)0x55, (byte)0xAA);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 4 : RECEIVE DATA 
            // --------------------------------------------
            CaseNb = (byte) 11 ;
            bRes = false ;

            proHdlr.init(PRO_CMD_RECEIVE_DATA, (byte)0x00, (byte)0x21);
            proHdlr.appendTLV(TAG_CHANNEL_DATA_LENGTH, (byte)0x10);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            //Sub Test Case 5 : CLOSE CHANNEL 
            // --------------------------------------------
            CaseNb = (byte) 12 ;
            bRes = false ;

            proHdlr.initCloseChannel((byte)0x01);
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
            reportTestOutcome(CaseNb, bRes) ;
            
            break;

          case (byte) 4 :
             // --------------------------------------------
             // Test Case 5 : DECLARE SERVICE proactive command
             // --------------------------------------------
             testCaseNb = (byte) 5 ;
            
             // Applet 3 allocates 3 services (Id 0 to 2)
             // --------------------------------------------
             CaseNb = (byte) 13 ;
             bRes = false ;

             try {
                obReg.allocateServiceIdentifier();
                obReg.allocateServiceIdentifier();
                obReg.allocateServiceIdentifier();
                bRes = true;
             }
             catch (Exception e) {
                bRes = false;
             }
             reportTestOutcome(CaseNb, bRes) ;

             break;
            
          case (byte) 5 :
             // --------------------------------------------
             // Test Case 5 : DECLARE SERVICE proactive command
             // --------------------------------------------
             testCaseNb = (byte) 6 ;
            
            
             // DECLARE SERVICE "Add" commands on allowed services
             // --------------------------------------------
             CaseNb = (byte) 14 ;
             bRes = true ;

             for (bServiceNb = 0; bServiceNb < 3; bServiceNb++)
             {
                ServiceRecordValue[1] = bServiceNb;
                proHdlr.init(PRO_CMD_DECLARE_SERVICE, (byte)0x00 /*Add*/, (byte)DEV_ID_TERMINAL);
                proHdlr.appendTLV(TAG_SERVICE_RECORD, ServiceRecordValue, (short)0x00, (short)ServiceRecordValue.length);
                try {
                   proHdlr.send();
                   bRes &= true;
                }
                catch (Exception e)    {    
                   bRes &= false ;
                }
             }
             reportTestOutcome(CaseNb, bRes) ;
            
             // DECLARE SERVICE "Add" commands on not allowed servives
             // --------------------------------------------
             CaseNb = (byte) 15 ;
             bRes = true ;

             for (bServiceNb = 3; bServiceNb < 8; bServiceNb++)
             {
                ServiceRecordValue[1] = bServiceNb;
                proHdlr.init(PRO_CMD_DECLARE_SERVICE, (byte)0x00 /*Add*/, (byte)DEV_ID_TERMINAL);
                proHdlr.appendTLV(TAG_SERVICE_RECORD, ServiceRecordValue, (short)0x00, (short)ServiceRecordValue.length);
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
             reportTestOutcome(CaseNb, bRes) ;
            
          // DECLARE SERVICE "Delete" commands on allowed services
          // --------------------------------------------
          CaseNb = (byte) 16 ;
          bRes = true ;

          for (bServiceNb = 0; bServiceNb < 3; bServiceNb++)
          {
             ServiceRecordValue[1] = bServiceNb;
             proHdlr.init(PRO_CMD_DECLARE_SERVICE, (byte)0x01 /*Delete*/, (byte)DEV_ID_TERMINAL);
             proHdlr.appendTLV(TAG_SERVICE_RECORD, ServiceRecordValue, (short)0x00, (short)ServiceRecordValue.length);
             try {
                proHdlr.send();
                bRes &= true;
             }
             catch (Exception e)    {    
                bRes &= false ;
             }
          }
          reportTestOutcome(CaseNb, bRes) ;
            
          // DECLARE SERVICE "Delete" commands on not allowed servives
          // --------------------------------------------
          CaseNb = (byte) 17 ;
          bRes = true ;

          for (bServiceNb = 3; bServiceNb < 8; bServiceNb++)
          {
             ServiceRecordValue[1] = bServiceNb;
             proHdlr.init(PRO_CMD_DECLARE_SERVICE, (byte)0x01 /*Delete*/, (byte)DEV_ID_TERMINAL);
             proHdlr.appendTLV(TAG_SERVICE_RECORD, ServiceRecordValue, (short)0x00, (short)ServiceRecordValue.length);
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
          reportTestOutcome(CaseNb, bRes) ;
            
          break;

      case (byte) 6 :
          // --------------------------------------------
          // Test Case 6 : Unknown proactive command
          // --------------------------------------------
          testCaseNb = (byte) 7 ;
          CaseNb = (byte) 18 ;
          bRes = true;
          proHdlr.init((byte)0x00, (byte)0x00, (byte)0x00);
          proHdlr.appendTLV((byte)0x00,(byte)0x00, (byte)0x00);
          try {
              proHdlr.send();
              bRes &= true;
           }
           catch (Exception e)    {    
              bRes &= false ;
           }
        reportTestOutcome(CaseNb, bRes) ;
          
        break;
      
      } // End switch
   }
}
