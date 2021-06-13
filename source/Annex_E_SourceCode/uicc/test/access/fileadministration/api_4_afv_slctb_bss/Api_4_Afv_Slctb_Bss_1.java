//-----------------------------------------------------------------------------
//    Api_4_Afv_Slctb_Bss_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_slctb_bss;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.access.* ;
import uicc.access.fileadministration.AdminFileView;
import uicc.access.fileadministration.AdminFileViewBuilder;
import uicc.test.util.* ;


public class Api_4_Afv_Slctb_Bss_1 extends TestToolkitApplet{
    
    public static byte[] FDescriptor = {(byte) 0x82, (byte) 0xFF, (byte) 0xFF, (byte) 0x21, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    public static byte[] FId_MF = {(byte) 0x83, (byte) 0x02, (byte) 0x3F, (byte) 0x00};
    public static byte[] FId_DFTelecom = {(byte) 0x83, (byte) 0x02, (byte) 0x7F, (byte) 0x10};
    public static byte[] FId_DFTest = {(byte) 0x83, (byte) 0x02, (byte) 0x7F, (byte) 0x4A};
    public static byte[] FId_EFTaru = {(byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x03};    
    public static byte[] FId_EFLaru = {(byte) 0x83, (byte) 0x02, (byte) 0x6F, (byte) 0x0C};    
    public static byte[] DFName = {(byte) 0x84, (byte) 0x10, (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89, (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x02};
    public static byte[] lifeCycle = {(byte) 0x8A, (byte) 0x01, (byte) 0x05};
    public static byte[] fileSize = {(byte) 0x80, (byte) 0x02, (byte) 0x01, (byte) 0x04};
    public static byte[] secAttributesLARR1a = {(byte) 0x8B, (byte) 0x03, (byte) 0x6F, (byte) 0x06, (byte) 0x01};
    public static byte[] secAttributesLARR1b = {(byte) 0x8B, (byte) 0x06, (byte) 0x6F, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x01};
    public static byte[] secAttributesLARR1c = {(byte) 0x8B, (byte) 0x06, (byte) 0x6F, (byte) 0x06, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x01};
    public static byte[] secAttributesTARR3a = {(byte) 0x8B, (byte) 0x03, (byte) 0x2F, (byte) 0x06, (byte) 0x03};
    public static byte[] secAttributesTARR3b = {(byte) 0x8B, (byte) 0x06, (byte) 0x2F, (byte) 0x06, (byte) 0x00, (byte) 0x03, (byte) 0x01, (byte) 0x03};
    public static byte[] secAttributesTARR3c = {(byte) 0x8B, (byte) 0x06, (byte) 0x2F, (byte) 0x06, (byte) 0x01, (byte) 0x03, (byte) 0x00, (byte) 0x03};
      
    public static byte[] fcp;
    public static byte[] normalBuffer = new byte[132];
    public static byte[] nullBuffer = null;
    
    
    byte[] resp = new byte[4];
    byte[] data = new byte[4];    
    
    short dataOffset;
    short dataLength; 
    short fileOffset; 
    short respOffset;
    short respLength;   
    short recNumber;    
    byte mode;  
    short recOffset;
    
    AID AidADF1;
 
    private AdminFileView theUiccView;
    private AdminFileView theFileView;

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


  
    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Slctb_Bss_1(){
        
        UICCTestConstants objectConstants = new UICCTestConstants();
        AidADF1 = new AID(objectConstants.AID_ADF1, (short) 0, (byte) objectConstants.AID_ADF1.length);
    
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
              
        
        // Create a new applet instance.
        Api_4_Afv_Slctb_Bss_1 thisApplet = new Api_4_Afv_Slctb_Bss_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();    
        
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);   
   
    }


    public void processToolkit(short event){

   
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
             * TEST CASE 0:Get a FileView object, UICC file system      
             *----------------------------------------------------------------*/         
            theUiccView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);

            /*------------------------------------------------------------------
             * TEST CASE 1: Select EFTARU in MF (Transparent EF)
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;

                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);
                result = theUiccView.select(UICCTestConstants.FID_EF_TARU, fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FIdOffset++;
                    FDOffset++;
                }
                FDescriptor[1] = 0x02;
                FDescriptor[2] = 0x41;
                if ((result >= 19)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, FDOffset, (short)4)==0) 
                    &&(Util.arrayCompare(FId_EFTaru, (short)0, fcp, FIdOffset, (short)FId_EFTaru.length)==0)) {
                    
                    bRes = true;
                }
                
                fcpOffset=findTLV((byte)0x8A, fcp, (short)0);//life cycle
                if ((fcpOffset !=0) && (Util.arrayCompare(lifeCycle, (short)0, fcp, fcpOffset, (short)lifeCycle.length)==0)) {
                
                    bRes &= true;
                }
                else {
                    bRes = false;
                }     
                fcpOffset=findTLV((byte)0x80, fcp, (short)0);//file size
                if ((fcpOffset !=0) && (Util.arrayCompare(fileSize, (short)0, fcp, fcpOffset, (short)fileSize.length)==0)) {
                
                    bRes &= true;
                }
                else {
                    bRes = false;
                }                
                   
            } catch (Exception e) {
                bRes = false;      
            }
            bTestCaseNb = 0x01;
            reportTestOutcome(bTestCaseNb, bRes);    
            
            /*------------------------------------------------------------------
             * TEST CASE 2: Select EFTaru in MF (Transparent EF)
             *----------------------------------------------------------------*/
            bRes = false; 
            
            try {
                FDOffset = (short)2;
                FIdOffset = (short)6;  
                
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 7;
                result = theUiccView.select(UICCTestConstants.FID_EF_TARU, fcp, fcpOffset, fcpLength);
               
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FIdOffset++;
                    FDOffset++;
                }
                FDescriptor[1] = 0x02;
                FDescriptor[2] = 0x41;
                if ((result == fcpLength)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, FDOffset, (short)4)==0)) {                    
                    bRes = true;
                }    
            } catch (Exception e) {
                bRes = false;      
            }
            bTestCaseNb = 0x02;
            reportTestOutcome(bTestCaseNb, bRes);



            /*------------------------------------------------------------------
             * TEST CASE 3: Select DF Test in MF
             *----------------------------------------------------------------*/
            bRes = false; 
            
            try {
                FDOffset = (short)2;
                FIdOffset = (short)6;  

                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                result = theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FIdOffset++;
                    FDOffset++;
                }
                FDescriptor[1] = 0x02;
                FDescriptor[2] = 0x78;
                if ((result >= 17)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, FDOffset, (short)4)==0) 
                    &&(Util.arrayCompare(FId_DFTest, (short)0, fcp, FIdOffset, (short)FId_DFTest.length)==0)) {
                    
                    bRes = true;
                }                
                fcpOffset=findTLV((byte)0x8A, fcp, (short)0);//life cycle
                if ((fcpOffset !=0) && (Util.arrayCompare(lifeCycle, (short)0, fcp, fcpOffset, (short)lifeCycle.length)==0)) {
                
                    bRes &= true;
                }
                else {
                    bRes = false;
                }     
                
            } catch (Exception e) {
                bRes = false;      
            }
            bTestCaseNb = 0x03;
            reportTestOutcome(bTestCaseNb, bRes);
  
            /*------------------------------------------------------------------
             * TEST CASE 4: Select EF CARU in DF Test(Cyclic EF)
             *----------------------------------------------------------------*/
            bRes = false; 
            try {
                FDOffset = (short)2;
                FIdOffset = (short)6;  

                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 11;
                result = theUiccView.select(UICCTestConstants.FID_EF_CARU, fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDOffset++;
                }
                FDescriptor[1] = 0x05;
                FDescriptor[2] = 0x46;
                FDescriptor[4] = 0x00;//  record
                FDescriptor[5] = 0x03;//  length
                FDescriptor[6] = 0x02;//  number of records
                if ((result == fcpLength)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, FDOffset, (short)7)==0)) {                    
                    bRes = true;
                }               
            } catch (Exception e) {
                bRes = false;      
            }
            bTestCaseNb = 0x04;
            reportTestOutcome(bTestCaseNb, bRes);            
            



            /*------------------------------------------------------------------
             * TEST CASE 5: Select ADF1
             *----------------------------------------------------------------*/
            bRes = false; 
            
            try {
                FDOffset = (short)2;
                FIdOffset = (short)6;  

                fcp = normalBuffer;
                fcpOffset = 5;
                fcpLength = 127;
                theFileView = AdminFileViewBuilder.getTheAdminFileView(AidADF1, JCSystem.CLEAR_ON_RESET);
                result = theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                
                fcp[0]=fcp[1]=fcp[2]=fcp[3]=fcp[4]=0x00;
                
                if (fcp[(byte)(1 + fcpOffset)] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FIdOffset++;
                    FDOffset++;
                }
                FDescriptor[1] = 0x02;
                FDescriptor[2] = 0x78;
                if ((result >= 27)
                    &&(fcp[0]==(byte)0x00) && (fcp[1]==(byte)0x00) && (fcp[2]==(byte)0x00) && (fcp[3]==(byte)0x00) && (fcp[4]==(byte)0x00)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, (short)(FDOffset + fcpOffset), (short)4)==0)) {
                    
                    bRes = true;
                }                
                fcpOffset=findTLV((byte)0x84, fcp, fcpOffset); //DF Name
                if ((fcpOffset !=0) && (Util.arrayCompare(DFName, (short)0, fcp, fcpOffset, (short)DFName.length)==0)) {
                
                    bRes &= true;
                }
                else {
                    bRes = false;
                }
                fcpOffset = 5;
                fcpOffset=findTLV((byte)0x8A, fcp, fcpOffset);//life cycle
                if ((fcpOffset !=0) && (Util.arrayCompare(lifeCycle, (short)0, fcp, fcpOffset, (short)lifeCycle.length)==0)) {
                
                    bRes &= true;
                }
                else {
                    bRes = false;
                }     
                
            } catch (Exception e) {
                bRes = false;      
            }
            bTestCaseNb = 0x05;
            reportTestOutcome(bTestCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 6: Select MF
             *----------------------------------------------------------------*/
            bRes = false; 

            try {
                FDOffset = (short)2;
                FIdOffset = (short)6;  

                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 11;                
                result = theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDByteOffset++;
                    FIdOffset++;
                    FDOffset++;
                }   
                fcp[(byte)(FDByteOffset+fcpOffset)]|= 0x40; // Set to 1 the bit7 (shareable file)
                FDescriptor[1] = 0x02;
                FDescriptor[2] = 0x78;
                if ((result == fcpLength)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, (short) (fcpOffset+FDOffset), (short)4)==0) 
                    &&(Util.arrayCompare(FId_MF, (short)0, fcp, (short) (fcpOffset+FIdOffset), (short)FId_MF.length)==0)) {
                    
                    bRes = true;
                }
            } catch (Exception e) {
                bRes = false;  
            }
            bTestCaseNb = 0x06;
            reportTestOutcome(bTestCaseNb, bRes);     

            /*------------------------------------------------------------------
             * TEST CASE 7: Select DF TELECOM in MF
             *----------------------------------------------------------------*/
            bRes = false; 
            try {
                FDOffset = (short)2;
                FDByteOffset = (short)4;
                FIdOffset = (short)6;  

                fcp = normalBuffer;
                fcp[0] = fcp[1] = (byte)0x05;
                fcpOffset = 2;
                fcpLength = 13;
                result = theUiccView.select(UICCConstants.FID_DF_TELECOM, fcp, fcpOffset, fcpLength);
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDByteOffset++;
                    FIdOffset++;
                    FDOffset++;
                }   
                fcp[(byte)(FDByteOffset+fcpOffset)]|= 0x40; // Set to 1 the bit7 (shareable file)
                FDescriptor[1] = 0x02;
                FDescriptor[2] = 0x78;
                if ((result == fcpLength)
                    &&(fcp[0]==(byte)0x05) && (fcp[1]==(byte)0x05)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, (short) (fcpOffset+FDOffset), (short)4)==0) 
                    &&(Util.arrayCompare(FId_DFTelecom, (short)0, fcp, (short) (fcpOffset+FIdOffset), (short)FId_DFTelecom.length)==0)) {
                    
                    bRes = true;
                } 
            } catch (Exception e) {
                bRes = false;  
            }
            bTestCaseNb = 0x07;
            reportTestOutcome(bTestCaseNb, bRes);    
            /*------------------------------------------------------------------
             * TEST CASE 8: Select EF LARU in DF TEST (Linear FixedEF)
             *----------------------------------------------------------------*/
            bRes = false; 
            try {
                FDOffset = (short)2;
                FIdOffset = (short)9; 
                
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 14;
                theUiccView.select(UICCTestConstants.FID_DF_TEST);   
                result = theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);
                
                if (fcp[1] == (byte)0x81)//if length is coded in 2 bytes, increase the offsets
                {
                    FDOffset++;
                    FIdOffset++;
                }
                FDescriptor[1] = 0x05;
                FDescriptor[2] = 0x42;
                FDescriptor[4] = 0x00;//  record
                FDescriptor[5] = 0x04;//  length
                FDescriptor[6] = 0x02;//  number of records
                if ((result == fcpLength)
                    &&(Util.arrayCompare(FDescriptor, (short)0, fcp, FDOffset, (short)7)==0)
                    &&(Util.arrayCompare(FId_EFLaru, (short)0, fcp, (short) FIdOffset, (short)FId_EFLaru.length)==0)) {                    
                    bRes = true;
                }                   
            } catch (Exception e) {
                bRes = false;      
            }
            bTestCaseNb = 0x08;
            reportTestOutcome(bTestCaseNb, bRes);            
                            
                

            /*------------------------------------------------------------------
             * TEST CASE 9: fcp is null
             *----------------------------------------------------------------*/
            try {
                fcp = nullBuffer;
                fcpOffset = 0;
                fcpLength = 15;
                result = theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (NullPointerException npe) {
                    bRes = true;
            } catch (Exception e) {
                    bRes = false;
            }
            bTestCaseNb = 0x09;
            reportTestOutcome(bTestCaseNb, bRes);
            

            /*------------------------------------------------------------------
             * TEST CASE 10: fcpOffset < 0
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = -1;
                fcpLength = 15;
                result = theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                    bRes = true;
            } catch (Exception e) {
                    bRes = false;
            }
            bTestCaseNb = 0x0A;
            reportTestOutcome(bTestCaseNb, bRes);
            /*------------------------------------------------------------------
             * TEST CASE 11: fcpLength < 0
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = -1;
                result = theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                    bRes = true;
            } catch (Exception e) {
                    bRes = false;
            }
            bTestCaseNb = 0x0B;
            reportTestOutcome(bTestCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 12: fcpOffset + fcpLength > fcp.length
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = 115;
                fcpLength = 18;
                result = theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                    bRes = true;
            } catch (Exception e) {
                    bRes = false;
            }
            bTestCaseNb = 0x0C;
            reportTestOutcome(bTestCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 13: fcpOffset + fcpLength > fcp.length
             *----------------------------------------------------------------*/
            try {
                fcp = normalBuffer;
                fcpOffset = (short) (fcp.length+1);
                fcpLength = 0;
                result = theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);
                bRes = false;
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                bRes = true;
            } catch (Exception e) {
                bRes = false;
            }
            bTestCaseNb = 0x0D;
            reportTestOutcome(bTestCaseNb, bRes);            
          
            /*------------------------------------------------------------------
             * TEST CASE 14: Selection possiblities      
             *----------------------------------------------------------------*/            
            
            
            try {
                fcpOffset = 0;
                
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);
                
                theUiccView.select(UICCTestConstants.FID_EF_UICC, fcp, fcpOffset, fcpLength);   
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);          
                theUiccView.select(UICCTestConstants.FID_EF_CNU, fcp, fcpOffset, fcpLength);    
                theUiccView.select(UICCTestConstants.FID_EF_TAAA, fcp, fcpOffset, fcpLength);    
                theUiccView.select(UICCTestConstants.FID_DF_SUB_TEST, fcp, fcpOffset, fcpLength);    
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);  
                theUiccView.select(UICCTestConstants.FID_EF_TAAA, fcp, fcpOffset, fcpLength);   
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);   
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);   
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);    
                theUiccView.select(UICCTestConstants.FID_EF_TAAA, fcp, fcpOffset, fcpLength);
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);   
                bRes = true;             

            } catch (Exception e){
                bRes = false;                       
            }
            bTestCaseNb = 0x0E;
            reportTestOutcome(bTestCaseNb, bRes);

            /*------------------------------------------------------------------
             * TEST CASE 15: EF not selected after MF/DF selection      
             *----------------------------------------------------------------*/            
            
             try {
            
                fileOffset = (short) 0;           
                respOffset = (short) 0;
                respLength = (short) 3;

                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 20;                
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);
                theUiccView.select(UICCConstants.FID_EF_ICCID, fcp, fcpOffset, fcpLength); 
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);
                
                theUiccView.readBinary((short) fileOffset, (byte[]) resp, (short) respOffset, (short) respLength);  

                bRes = false;                                     
                                                                 
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.NO_EF_SELECTED);                                                  
            }          
            bTestCaseNb = 0x0F;                                  
            reportTestOutcome(bTestCaseNb, bRes);          
                      
                      
            /*------------------------------------------------------------------
             * TEST CASE 16: No selection of non-reachable file      
             *----------------------------------------------------------------*/            
                      
            try {
            
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 20;                
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);
                theUiccView.select(UICCTestConstants.FID_EF_CARU, fcp, fcpOffset, fcpLength);
                bRes = false;                                     
                                                                 
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.FILE_NOT_FOUND);                                                  
            }          
            bTestCaseNb = 0x10;                                  
            reportTestOutcome(bTestCaseNb, bRes);                         
                   
            /*---------------------------------------------------------------------
             * TEST CASE 17: No record is selected afterselecting linear fixed EF
             *--------------------------------------------------------------------*/            
            
             try {            
            
                recNumber = (short) 0;
                mode = (byte) UICCConstants.REC_ACC_MODE_CURRENT;
                recOffset = (short) 0;
                respOffset = (short) 0;
                respLength = (short) 4;             

                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 20;                
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);                
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);
                theUiccView.select(UICCTestConstants.FID_EF_LARU, fcp, fcpOffset, fcpLength);                
                theUiccView.readRecord(recNumber, mode, recOffset, resp, respOffset, respLength); 
                bRes = false;                                     
                                                                 
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.RECORD_NOT_FOUND);                                                  
            }          
            bTestCaseNb = 0x11;                                  
            reportTestOutcome(bTestCaseNb, bRes);                                          
                      
                      
            /*---------------------------------------------------------------------
             * TEST CASE 18: Record pointer in selected cyclic EF
             *--------------------------------------------------------------------*/            
            bRes = false;            

            recNumber = (short) 0;
            recOffset = (short) 0;
            dataOffset = (short) 0;
            dataLength = (short) 3;
            respOffset = (short) 0;
            respLength = (short) 3;
            
            try {           
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);            
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);             
                theUiccView.select(UICCTestConstants.FID_EF_CARU, fcp, fcpOffset, fcpLength);              

                data[0] = 1; 
                data[1] = 2;
                data[2] = 3;
                mode = UICCConstants.REC_ACC_MODE_PREVIOUS;
                theUiccView.updateRecord(recNumber, mode, recOffset, data, dataOffset, dataLength);

                theUiccView.select(UICCTestConstants.FID_EF_CARU, fcp, fcpOffset, fcpLength);      
                
                mode = UICCConstants.REC_ACC_MODE_PREVIOUS;
                theUiccView.readRecord(recNumber, mode, recOffset, resp, respOffset, respLength);                
                theUiccView.readRecord(recNumber, mode, recOffset, resp, respOffset, respLength);
                                    
                if (Util.arrayCompare(data, (short)0, resp, (short)0, (short)3)==0) {
                    bRes = true; 
                }                                                                                                                     
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x12;                                  
            reportTestOutcome(bTestCaseNb, bRes);   

            /*------------------------------------------------------------------
             * TEST CASE 19: EF not selected after ADF/DF selection      
             *----------------------------------------------------------------*/            
            
             try {
            
                fileOffset = (short) 0;           
                respOffset = (short) 0;
                respLength = (short) 3;

                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 20;
                theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                theFileView.select(UICCTestConstants.FID_EF_UICC, fcp, fcpOffset, fcpLength); 
                theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                
                theFileView.readBinary((short) fileOffset, (byte[]) resp, (short) respOffset, (short) respLength);  

                bRes = false;                                     
                                                                 
            } catch (UICCException uicce){
                bRes = (uicce.getReason() == UICCException.NO_EF_SELECTED);
            }          
            bTestCaseNb = 0x13;                                  
            reportTestOutcome(bTestCaseNb, bRes);
                      

            /*------------------------------------------------------------------
             * TEST CASE 20: Reselection
             *----------------------------------------------------------------*/            
            

            try {
                
                //ADF FileView
                theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                
                //UICC FileView
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_EF_TAAA, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_EF_TAAA, fcp, fcpOffset, fcpLength);     
                            
                bRes = true;      
                  
            } catch (Exception e){    
                bRes = false;                                  
            }   
            bTestCaseNb = 0x14;                                  
            reportTestOutcome(bTestCaseNb, bRes);      

            /*------------------------------------------------------------------
             * TEST CASE 21: Security attributes
             *----------------------------------------------------------------*/            

            try {
                
                bRes = false;
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                
                //ADF FileView
                theFileView.select(UICCTestConstants.FID_ADF, fcp, fcpOffset, fcpLength);
                theFileView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);     
                theFileView.select(UICCTestConstants.FID_EF_LARR1, fcp, fcpOffset, fcpLength);                  

                fcpOffset=findTLV((byte)0x8B, fcp, fcpOffset); //security attributes
                if ( (fcpOffset !=0) 
                && ((Util.arrayCompare(secAttributesLARR1a, (short)0, fcp, fcpOffset, (short)secAttributesLARR1a.length)==0)
                     ||(Util.arrayCompare(secAttributesLARR1b, (short)0, fcp, fcpOffset, (short)secAttributesLARR1b.length)==0)
                     ||(Util.arrayCompare(secAttributesLARR1c, (short)0, fcp, fcpOffset, (short)secAttributesLARR1c.length)==0)) ){
                
                    bRes = true;
                }                   
                  
            } catch (Exception e){   
                bRes = false;                                  
            }   
            bTestCaseNb = 0x15;                                  
            reportTestOutcome(bTestCaseNb, bRes);      
            

            try {
                
                bRes = false;
                fcp = normalBuffer;
                fcpOffset = 0;
                fcpLength = 127;
                
                //UICC FileView
                theUiccView.select(UICCTestConstants.FID_MF, fcp, fcpOffset, fcpLength);
                theUiccView.select(UICCTestConstants.FID_DF_TEST, fcp, fcpOffset, fcpLength);     
                theUiccView.select(UICCTestConstants.FID_EF_TARR3, fcp, fcpOffset, fcpLength);                  
                
                fcpOffset=findTLV((byte)0x8B, fcp, fcpOffset); //security attributes
                if ( (fcpOffset !=0) 
                && ((Util.arrayCompare(secAttributesTARR3a, (short)0, fcp, fcpOffset, (short)secAttributesTARR3a.length)==0) 
                     ||(Util.arrayCompare(secAttributesTARR3b, (short)0, fcp, fcpOffset, (short)secAttributesTARR3b.length)==0)
                     ||(Util.arrayCompare(secAttributesTARR3c, (short)0, fcp, fcpOffset, (short)secAttributesTARR3c.length)==0)) ){
                
                    bRes = true;
                }                   
                  
            } catch (Exception e){ 
                bRes = false;                                  
            }   
            bTestCaseNb = 0x16;                                  
            reportTestOutcome(bTestCaseNb, bRes);        

        }    
    }
}
