//-----------------------------------------------------------------------------
//Api_2_Enh_Copy_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.toolkit.api_2_enh_copy;

import javacard.framework.Util;
import uicc.test.util.TestToolkitApplet;
import uicc.toolkit.*;

//-----------------------------------------------------------------------------
//Imports
//-----------------------------------------------------------------------------

/**
 * Test Area : uicc.test.toolkit.api_2_enh_copy
 *
 * @version 0.0.1 - 2 nov. 2004
 * @author 3GPP T3 SWG API
 */
public class Api_2_Enh_Copy_1 extends TestToolkitApplet
{

                  // Number of tests
                  byte testCaseNb = (byte) 0x00 ;

                  private byte[] CmpBuffer = {(byte)0x82,(byte)0x02,(byte)0x83,(byte)0x81, 
                                              (byte)0x02,(byte)0x29, 
                                              (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                              (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                              (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09};

                  private byte[] CmpBuffer2 = {(byte)0x00 ,(byte)0x00 ,(byte)0x00 ,
                                              (byte)0x82,(byte)0x02,(byte)0x83,(byte)0x81, 
                                              (byte)0x02,(byte)0x29, 
                                              (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                              (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                              (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09};
  
                  private byte[] CmpBuffer3 = {(byte)0x00 ,(byte)0x00 ,(byte)0x00 ,
                                               (byte)0x82,(byte)0x02,(byte)0x83,(byte)0x81, 
                                               (byte)0x02,(byte)0x81,(byte)0xF5,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F,(byte)0x10,
                                               (byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05};

                  private byte[] CmpBuffer4 = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                                               (byte)0x00,(byte)0x82,(byte)0x02,(byte)0x83};
  
                  private byte[] dstBuffer;

                  /**
                  * Constructor of the applet
                  */
                  public Api_2_Enh_Copy_1() 
                  {
                  }


                  /**
                   * Method called by the JCRE at the installation of the applet
                   */
                    public static void install(byte bArray[], short bOffset, byte bLength) 
                    {
                        // Create a new applet instance
                        Api_2_Enh_Copy_1  thisApplet = new Api_2_Enh_Copy_1();

                        // Register the new applet instance to the JCRE            
                        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

                        // Initialise the data of the test applet
                        thisApplet.init();
                      
                      // Register on UNRECOGNIZED ENVELOPE
                    thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
                  }
                  

                  /**
                     * Method called by the SIM Toolkit Framework
                   */
                  public void processToolkit(short event) 
                  {
                       // Result of tests
                        boolean bRes ;
                        
                        // Get the system instance of handlers
                        EnvelopeHandler envHdlr = EnvelopeHandlerSystem.getTheHandler();
                        
                        switch ( testCaseNb ) 
                        {
                                case (byte) 0 : 
                                        // --------------------------------------------
                                        // Test Case 1 : the destination buffer is null
                                        testCaseNb = (byte) 1 ;
                                        
                                        bRes = false ;
                                        try {
                                                envHdlr.copy(null,(short)0,envHdlr.getLength());
                                                      bRes = false;
                                        }
                                        catch (ToolkitException e)    {
                                               bRes = false;
                                        }
                                        catch (NullPointerException e)    {
                                               bRes = true;
                                        }
                                        catch (Exception e)    {    
                                               bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                        
                                case (byte) 1 :
                                        // --------------------------------------------
                                        // Test Case 2 : the destination offset is > to the buffer length
                                        testCaseNb = (byte) 2 ;
                                        
                                        dstBuffer = new byte[5];
                                        bRes = false ;
                                        try {
                                                envHdlr.copy(dstBuffer,(short)5,(short)1);
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (ArrayIndexOutOfBoundsException e)    {
                                                bRes = true;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                                       
                                case (byte) 2 :
                                         // --------------------------------------------
                                         // Test Case 3 : the destination offset is <0
                                         testCaseNb = (byte) 3 ;
                                        
                                         dstBuffer = new byte[5];
                                         bRes = false ;
                                         try {
                                                 envHdlr.copy(dstBuffer,(short)(-1),(short)1);
                                                         bRes = false;
                                         }
                                         catch (ToolkitException e)    {
                                                 bRes = false;
                                         }
                                         catch (ArrayIndexOutOfBoundsException e)    {
                                                 bRes = true;
                                         }
                                         catch (Exception e)    {    
                                                 bRes = false ;
                                         }
                                         reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 3 :
                                        // --------------------------------------------
                                        // Test Case 4 : the destination length is bigger to the buffer length
                                        testCaseNb = (byte) 4 ;
                                        
                                        dstBuffer = new byte[5];
                                        bRes = false ;
                                        try {
                                                envHdlr.copy(dstBuffer,(short)0,(short)6);
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (ArrayIndexOutOfBoundsException e)    {
                                                bRes = true;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 4 :
                                        // --------------------------------------------
                                        // Test Case 5 : the destination offset + length is bigger than the buffer length
                                        testCaseNb = (byte) 5 ;
                                        
                                        dstBuffer = new byte[5];
                                        bRes = false ;
                                        try {
                                                envHdlr.copy(dstBuffer,(short)3,(short)3);
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (ArrayIndexOutOfBoundsException e)    {
                                                bRes = true;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 5 :
                                        // --------------------------------------------
                                        // Test Case 6 : the destination length is <0
                                        testCaseNb = (byte) 6 ;
                                        
                                        dstBuffer = new byte[5];
                                        bRes = false ;
                                        try {
                                                envHdlr.copy(dstBuffer,(short)0,(short)(-1));
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (ArrayIndexOutOfBoundsException e)    {
                                                bRes = true;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 6 :
                                        // --------------------------------------------
                                        // Test Case 7 : the destination length is > TLV length
                                        testCaseNb = (byte) 7 ;
                                        
                                        dstBuffer = new byte[48];
                                        bRes = false ;
                                        try {
                                                envHdlr.copy(dstBuffer,(short)0,(short)dstBuffer.length);
                                                        bRes = false;
                                        }
                                        catch (ToolkitException e)    {
                                                if(e.getReason()==ToolkitException.OUT_OF_TLV_BOUNDARIES)
                                                        bRes = true;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;                
                                        
                                case (byte) 7 :
                                        // --------------------------------------------
                                        // Test Case 8 : 
                                        testCaseNb = (byte) 8 ;
                                        
                                        dstBuffer = new byte[47];
                                        bRes = false ;
                                        try {
                                                if(envHdlr.copy(dstBuffer,(short)0,(short)47)==0x002F)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 8 :
                                        // --------------------------------------------
                                        // Test Case 9 : compare the buffer
                                        testCaseNb = (byte) 9 ;
                                        
                                        dstBuffer = new byte[47];
                                        bRes = false ;
                                        try {
                                                if((envHdlr.copy(dstBuffer,(short)0,(short)47)==0x002F)
                                                        &&(Util.arrayCompare(dstBuffer,(short)0,CmpBuffer,(short)0,(short)CmpBuffer.length))==0)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                        
                                case (byte) 9 :
                                        // --------------------------------------------
                                        // Test Case 10 : copy in a part of the buffer
                                        testCaseNb = (byte) 10 ;
                                        
                                        dstBuffer = new byte[50];
                                        bRes = false ;
                                        try {
                                                if(envHdlr.copy(dstBuffer,(short)3,(short)47)==0x0032)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 10 :
                                        // --------------------------------------------
                                        // Test Case 11 : compare the whole buffer
                                        testCaseNb = (byte) 11 ;
                                        
                                        dstBuffer = new byte[50];
                                        bRes = false ;
                                        try {
                                                if((envHdlr.copy(dstBuffer,(short)3,(short)47)==0x0032)
                                                        &&(Util.arrayCompare(dstBuffer,(short)0,CmpBuffer2,(short)0,(short)CmpBuffer2.length))==0)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                case (byte) 11 :
                                        // --------------------------------------------
                                        // Test Case 12 : copy in a part of the buffer
                                        testCaseNb = (byte) 12 ;
                                        
                                        dstBuffer = new byte[252];
                                        bRes = false ;
                                        try {
                                                if(envHdlr.copy(dstBuffer,(short)0,(short)dstBuffer.length)==0x00FC)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 12 :
                                        // --------------------------------------------
                                        // Test Case 13 : compare the whole buffer
                                        testCaseNb = (byte) 13 ;
                                        
                                        dstBuffer = new byte[255];
                                        bRes = false ;
                                        try {
                                                if((envHdlr.copy(dstBuffer,(short)3,(short)252)==0x00FF)
                                                        &&(Util.arrayCompare(dstBuffer,(short)0,CmpBuffer3,(short)0,(short)CmpBuffer3.length))==0)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 13 :
                                        // --------------------------------------------
                                        // Test Case 14 : copy in a part of the buffer
                                        testCaseNb = (byte) 14 ;
                                        
                                        dstBuffer = new byte[260];
                                        bRes = false ;
                                        try {
                                                if(envHdlr.copy(dstBuffer,(short)257,(short)3)==0x0104)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 14 :
                                        // --------------------------------------------
                                        // Test Case 15 : compare the whole buffer
                                        testCaseNb = (byte) 15 ;
                                        
                                        dstBuffer = new byte[260];
                                        bRes = false ;
                                        try {
                                                if((envHdlr.copy(dstBuffer,(short)257,(short)3)==0x0104)
                                                        &&(Util.arrayCompare(dstBuffer,(short)0,CmpBuffer4,(short)0,(short)CmpBuffer4.length))==0)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                case (byte) 15 :
                                        // --------------------------------------------
                                        // Test Case 16 : successful call with the length=0 and the offset = length of the destination buffer
                                        testCaseNb = (byte) 16 ;
                                        
                                        bRes = false ;
                                        try {
                                                if(envHdlr.copy(dstBuffer,(short)dstBuffer.length,(short)0)==dstBuffer.length)
                                                        bRes = true;
                                        }
                                        catch (ToolkitException e)    {
                                                bRes = false;
                                        }
                                        catch (Exception e)    {    
                                                bRes = false ;
                                        }
                                        reportTestOutcome(testCaseNb, bRes) ;
                                        break;
                                        
                                default :
                                        break;
                        }
                }
                
}
