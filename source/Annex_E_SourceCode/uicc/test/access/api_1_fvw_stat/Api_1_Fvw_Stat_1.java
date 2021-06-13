//-----------------------------------------------------------------------------
//    Api_1_Fvw_Stat_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_stat;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.access.* ;
import uicc.test.util.* ;


public class Api_1_Fvw_Stat_1 extends TestToolkitApplet {
    
    public static byte[] FDescriptor = {(byte) 0x82, (byte) 0x02, (byte) 0xFF, (byte) 0x21};
    public static byte[] FId_MF = {(byte) 0x83, (byte) 0x02, (byte) 0x3F, (byte) 0x00};
    public static byte[] FId_DFTelecom = {(byte) 0x83, (byte) 0x02, (byte) 0x7F, (byte) 0x10};
    public static byte[] FId_DFTest = {(byte) 0x83, (byte) 0x02, (byte) 0x7F, (byte) 0x4A};
    public static byte[] DFName = {(byte) 0x84, (byte) 0x10, (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x02};
    public static byte[] lifeCycle = {(byte) 0x8A, (byte) 0x01, (byte) 0x05};
    public static byte[] secAttributesARR2a = {(byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x02};
    public static byte[] secAttributesARR2b = {(byte) 0x8B, (byte) 0x06, (byte) 0x6F, (byte) 0x06, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) 0x02};
    public static byte[] secAttributesARR2c = {(byte) 0x8B, (byte) 0x06, (byte) 0x6F, (byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x02};
    public static byte[] secAttributesARR4a = {(byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x04};
    public static byte[] secAttributesARR4b = {(byte) 0x8B, (byte) 0x06, (byte) 0x2F, (byte) 0x06, (byte) 0x00, (byte) 0x04, (byte) 0x01, (byte) 0x04};
    public static byte[] secAttributesARR4c = {(byte) 0x8B, (byte) 0x06, (byte) 0x2F, (byte) 0x06, (byte) 0x01, (byte) 0x04, (byte) 0x00, (byte) 0x04};
                 
    public static byte[] normalBuffer = new byte[127];
    public static byte[] nullBuffer = null;
    public static byte[] fcp;
    AID AidADF1;
    
    private FileView theUiccView;
    private FileView theFileView;
    
    /**
     * The Constructor register the application for the Event.
     */
    public Api_1_Fvw_Stat_1 () {
        
        UICCTestConstants objectConstants = new UICCTestConstants ();
        AidADF1 = new AID (objectConstants.AID_ADF1, (short) 0, (byte) objectConstants.AID_ADF1.length);
        
    }
    
    private short findTLV (byte tag, byte[] buffer, short offset) {
        
        short   index=1;
        short   len;
        
        index = (short)(index + offset);
        
        if (buffer[(byte)index]==(byte)0x81) {
            index++;            
            len = (short) (buffer[(byte)index] + index + (short)1);
        }else if (buffer[(byte)index]==(byte)0x82){
            index = (short)(index + (short)2);
            len = (short) (Util.getShort(buffer,(byte)index) + index);
        }
        else{
        len = (short) (buffer[(byte)index] + index + (short)1);
        }		
        
        
        index++;
        
        while (index < len) {
            
            if ( (buffer[(byte)index] & (byte)0x7F) == (tag & (byte)0x7F) )  {
                return(index);
            }
            index = (short) (index + (short) buffer[(byte)(index + (byte)1)] + (short)2);
        }
        
        return(0);
        
    }
    
    
    
    public static void install (byte[] bArray, short bOffset, byte bLength) {
        
        // Create a new applet instance.
        Api_1_Fvw_Stat_1 thisApplet = new Api_1_Fvw_Stat_1 ();
        
        // Register the new applet instance to the JCRE
        thisApplet.register (bArray, (short)(bOffset+1), bArray[bOffset]);
        
        // Initialise the data of the test applet.
        thisApplet.init ();
        
        thisApplet.obReg.setEvent (EVENT_UNRECOGNIZED_ENVELOPE);
        
    }
    
    public void processToolkit (short event) throws ToolkitException {
        
        boolean bRes = false;
        byte    bTestCaseNb = (byte)0;
        
        EnvelopeHandler envHdlr;
        short fcpOffset = 0;
        short fcpLength = 0;
        short result = 0;
        
        
        short FDOffset = (short)2; //offset of File descriptor TLV in fcp
        short FDByteOffset = (short)4; //offset of File descriptor byte in fcp
        short FIdOffset = (short)6; //offset of File Identifier TLV in fcp
        
        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {
            
            /*------------------------------------------------------------------
             * TEST CASE 01: Status of MF
             *----------------------------------------------------------------*/
            
            theUiccView = UICCSystem.getTheUICCView (JCSystem.CLEAR_ON_RESET);
            
            try {
                fcp = normalBuffer;
                fcpOffset = 3;
                fcpLength = 11;
                fcp[0]=fcp[1]=fcp[2]= (byte)0xCC;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                
                if (fcp[(byte)(0x01 + fcpOffset)] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDByteOffset++;
                    FIdOffset++;
                    FDOffset++;
                }
                fcp[(byte)(FDByteOffset+fcpOffset)]|= 0x40; // Set to 1 the bit7 (shareable file)
                FDescriptor[2]= 0x78;
                if ((result == fcpLength)
                &&(Util.arrayCompare (FDescriptor, (short)0, fcp, (short) (fcpOffset+FDOffset), (short)FDescriptor.length)==0)
                &&(Util.arrayCompare (FId_MF, (short)0, fcp, (short) (fcpOffset+FIdOffset), (short)FId_MF.length)==0)
                &&(fcp[0]==(byte)0xCC) && (fcp[1]==(byte)0xCC) && (fcp[2]==(byte)0xCC)) {
                    
                    bRes = true;
                }
                
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x01;
            reportTestOutcome (bTestCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 02: Status after select EF TARU in MF
             *----------------------------------------------------------------*/
            bRes = false;
            
            try {
                FDOffset = (short)2;
                FDByteOffset = (short)4;
                FIdOffset = (short)6;
                
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                
                
                theUiccView.select (UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);
                theUiccView.select (UICCTestConstants.FID_EF_TARU, fcp, fcpOffset, fcpLength);
                
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDByteOffset++;
                    FIdOffset++;
                    FDOffset++;
                }
                fcp[FDByteOffset]|= 0x40; // Set to 1 the bit7 (shareable file)
                FDescriptor[2]= 0x78;
                if ((result >= 19)
                &&(Util.arrayCompare (FDescriptor, (short)0, fcp, FDOffset, (short)FDescriptor.length)==0)
                &&(Util.arrayCompare (FId_DFTest, (short)0, fcp, FIdOffset, (short)FId_DFTest.length)==0)) {
                    
                    bRes = true;
                }
                
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x02;
            reportTestOutcome (bTestCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 03: Status of DF Telecom
             *----------------------------------------------------------------*/
            bRes = false;
            
            try {
                FDOffset = (short)2;
                FDByteOffset = (short)4;
                FIdOffset = (short)6;
                
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                
                theUiccView.select (UICCConstants.FID_DF_TELECOM, fcp, fcpOffset, fcpLength);
                
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FIdOffset++;
                    FDOffset++;
                }
                if ((result >= 17)
                &&(Util.arrayCompare (FId_DFTelecom, (short)0, fcp, FIdOffset, (short)FId_DFTelecom.length)==0)) {
                    
                    bRes = true;
                }
                
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x03;
            reportTestOutcome (bTestCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 04: Status DF Telecom
             *----------------------------------------------------------------*/
            bRes = false;
            
            try {
                FDOffset = (short)2;
                FDByteOffset = (short)4;
                FIdOffset = (short)6;
                
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 11;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDByteOffset++;
                    FIdOffset++;
                    FDOffset++;
                }
                fcp[FDByteOffset]|= 0x40; // Set to 1 the bit7 (shareable file)
                FDescriptor[2]= 0x78;
                if ((result == fcpLength)
                &&(Util.arrayCompare (FDescriptor, (short)0, fcp, FDOffset, (short)FDescriptor.length)==0)
                &&(Util.arrayCompare (FId_DFTelecom, (short)0, fcp, FIdOffset, (short)FId_DFTelecom.length)==0)) {
                    
                    bRes = true;
                }
                
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x04;
            reportTestOutcome (bTestCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 05: Status ADF1
             *----------------------------------------------------------------*/
            bRes = false;
            
            theFileView = UICCSystem.getTheFileView (AidADF1, JCSystem.CLEAR_ON_RESET);
            
            try {
                FDOffset = (short)2;
                FDByteOffset = (short)4;
                FIdOffset = (short)6;
                
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                result = theFileView.status (fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDByteOffset++;
                    FIdOffset++;
                    FDOffset++;
                }
                fcp[FDByteOffset]|= 0x40; // Set to 1 the bit7 (shareable file)
                FDescriptor[2]= 0x78;
                //File Descriptor
                if ((result >= 27) && (Util.arrayCompare (FDescriptor, (short)0, fcp, FDOffset, (short)FDescriptor.length)==0)) {
                    
                    bRes = true;
                }
                fcpOffset=findTLV ((byte)0x84, fcp, (short)0);//DF Name
                if ((fcpOffset !=0) && (Util.arrayCompare (DFName, (short)0, fcp, fcpOffset, (short)DFName.length)==0)) {
                    
                    bRes &= true;
                } else {
                    bRes = false;
                }
                
                fcpOffset=findTLV ((byte)0x8A, fcp, (short)0);//life cycle
                if ((fcpOffset !=0) && (Util.arrayCompare (lifeCycle, (short)0, fcp, fcpOffset, (short)lifeCycle.length)==0)) {
                    
                    bRes &= true;
                } else {
                    bRes = false;
                }
                
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x05;
            reportTestOutcome (bTestCaseNb, bRes);
            
            
            /*------------------------------------------------------------------
             * TEST CASE 06: fcp is null
             *----------------------------------------------------------------*/
            try {
                fcp = nullBuffer;
                fcpOffset = 0;
                fcpLength = 34;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (NullPointerException npe) {
                bRes = true;
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x06;
            reportTestOutcome (bTestCaseNb, bRes);
            /*------------------------------------------------------------------
             * TEST CASE 07: fcpOffset < 0
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = -1;
                fcpLength = 34;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                bRes = true;
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x07;
            reportTestOutcome (bTestCaseNb, bRes);
            /*------------------------------------------------------------------
             * TEST CASE 08: fcpLength < 0
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = -1;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                bRes = true;
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x08;
            reportTestOutcome (bTestCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 09: fcpOffset + fcpLength > fcp.length
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = (short)(fcp.length - 1);
                fcpLength = 15;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                bRes = true;
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x09;
            reportTestOutcome (bTestCaseNb, bRes);
            
            /*------------------------------------------------------------------
             * TEST CASE 10: fcpOffset + fcpLength > fcp.length
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = (short)(fcp.length+1);
                fcpLength = 0;
                result = theUiccView.status (fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                bRes = true;
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x0A;
            reportTestOutcome (bTestCaseNb, bRes);
            

            /*------------------------------------------------------------------
             * TEST CASE 11: Security attributes
             *----------------------------------------------------------------*/            

            try {
                
                bRes = false;
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                
                //ADF FileView
                theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                theFileView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);     
                theFileView.select(UICCTestConstants.FID_DF_ARR2, fcp, fcpOffset, fcpLength);                  

                fcpOffset=findTLV((byte)0x8B, fcp, fcpOffset); //security attributes
                if ( (fcpOffset !=0) 
                && ((Util.arrayCompare(secAttributesARR2a, (short)0, fcp, fcpOffset, (short)secAttributesARR2a.length)==0)
                     ||(Util.arrayCompare(secAttributesARR2b, (short)0, fcp, fcpOffset, (short)secAttributesARR2b.length)==0)
                     ||(Util.arrayCompare(secAttributesARR2c, (short)0, fcp, fcpOffset, (short)secAttributesARR2c.length)==0)) ) {
                
                    bRes = true;
                }                   
                  
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x0B;                                  
            reportTestOutcome(bTestCaseNb, bRes);      
            

            try {
                
                bRes = false;
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                
                //UICC FileView
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);   
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_DF_ARR4, fcp, fcpOffset, fcpLength);                  
                
                fcpOffset=findTLV((byte)0x8B, fcp, fcpOffset); //security attributes
                if ( (fcpOffset !=0) 
                && ((Util.arrayCompare(secAttributesARR4a, (short)0, fcp, fcpOffset, (short)secAttributesARR4a.length)==0)
                     ||(Util.arrayCompare(secAttributesARR4b, (short)0, fcp, fcpOffset, (short)secAttributesARR4b.length)==0)
                     ||(Util.arrayCompare(secAttributesARR4c, (short)0, fcp, fcpOffset, (short)secAttributesARR4c.length)==0)) ) {
                
                    bRes = true;
                }                   
                  
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x0C;                                  
            reportTestOutcome(bTestCaseNb, bRes);        
            
            
            
        } // end if
    }
}
