//-----------------------------------------------------------------------------
//    Api_1_Fvw_Slcts_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_afv_slcts;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.access.* ;
import uicc.access.fileadministration.AdminFileView;
import uicc.access.fileadministration.AdminFileViewBuilder;
import uicc.test.util.* ;

/**
 * Test Area : uicc.catre...
 *
 * Applet is triggered on Install (Install)
 *
 *
 */

public class Api_4_Afv_Slcts_1 extends TestToolkitApplet{


    byte[] data = new byte[4];    
    byte[] resp = new byte[4];
 
    short recNumber;    
    byte mode;  
    short recOffset;
    short dataOffset;
    short dataLength; 
    short respOffset;
    short respLength;
    
    AID AidADF1;
 
    private AdminFileView theUiccView;
    private AdminFileView theFileView;
  
    /**
     * Constructor of the applet
     */
    public Api_4_Afv_Slcts_1(){
        
        UICCTestConstants objectConstants = new UICCTestConstants();
        AidADF1 = new AID(objectConstants.AID_ADF1, (short) 0, (byte) objectConstants.AID_ADF1.length);
    
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
              
        
        // Create a new applet instance.
        Api_4_Afv_Slcts_1 thisApplet = new Api_4_Afv_Slcts_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();    
        
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);   
   
    }


    public void processToolkit(short event){

   
    boolean bRes = false;
    byte    bTestCaseNb = (byte)0;

        if (event == EVENT_UNRECOGNIZED_ENVELOPE) {
            
            //test case 0: Get a FileView object, UICC file system                
            theUiccView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);


            //test case 1: Selection possiblities                     
            try {
                
                theUiccView.select(UICCTestConstants.FID_EF_UICC);   
                theUiccView.select(UICCTestConstants.FID_DF_TEST);          
                theUiccView.select(UICCTestConstants.FID_EF_CNU);    
                theUiccView.select(UICCTestConstants.FID_EF_TAAA);    
                theUiccView.select(UICCTestConstants.FID_DF_SUB_TEST);    
                theUiccView.select(UICCTestConstants.FID_DF_TEST);    
                theUiccView.select(UICCTestConstants.FID_EF_TAAA);    
                theUiccView.select(UICCTestConstants.FID_DF_TEST);    
                theUiccView.select(UICCTestConstants.FID_MF);    
                theUiccView.select(UICCTestConstants.FID_DF_TEST);    
                theUiccView.select(UICCTestConstants.FID_EF_TAAA);    
                theUiccView.select(UICCTestConstants.FID_MF);    
                bRes = true;             

            } catch (Exception e){
                bRes = false;                       
            }
            bTestCaseNb = 0x01;
            reportTestOutcome(bTestCaseNb, bRes);


            //test case 2: Selection possiblities ADF1
            
             try {
            
                theFileView = AdminFileViewBuilder.getTheAdminFileView(AidADF1, JCSystem.CLEAR_ON_RESET);
            
                theFileView.select(UICCTestConstants.FID_EF_UICC);     
                theFileView.select(UICCTestConstants.FID_DF_TEST);     
                theFileView.select(UICCTestConstants.FID_EF_CNU);      
                theFileView.select(UICCTestConstants.FID_EF_TAAA);     
                theFileView.select(UICCTestConstants.FID_DF_SUB_TEST); 
                theFileView.select(UICCTestConstants.FID_DF_TEST);     
                theFileView.select(UICCTestConstants.FID_EF_TAAA);     
                theFileView.select(UICCTestConstants.FID_DF_TEST);     

                bRes = true;                                     
                                                                 
            } catch (Exception e){                               
                bRes = false;                                    
            }                                                    
            bTestCaseNb = 0x02;                                  
            reportTestOutcome(bTestCaseNb, bRes);                
                                                                 

            //test case 3: No selection of unreachable file

            theUiccView = AdminFileViewBuilder.getTheUICCAdminFileView(JCSystem.CLEAR_ON_RESET);
            
            try {           
                theUiccView.select(UICCTestConstants.FID_EF_CNU);    
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.FILE_NOT_FOUND);                                  
            }   
            bTestCaseNb = 0x03;                                  
            reportTestOutcome(bTestCaseNb, bRes);               
            try {           
                theUiccView.select(UICCTestConstants.FID_DF_TEST);    
                bRes = true;                                                                                                      
            } catch (Exception e){                               
                bRes = false;                                  
            }         
            bTestCaseNb = 0x04;                                  
            reportTestOutcome(bTestCaseNb, bRes);                           
            try {           
                theUiccView.select(UICCTestConstants.FID_EF_TAA);    
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.FILE_NOT_FOUND);                                  
            }           
            bTestCaseNb = 0x05;                                  
            reportTestOutcome(bTestCaseNb, bRes);                       
            try {           
                theUiccView.select(UICCTestConstants.FID_EF_CNU);    
                bRes = true;                                                                                                      
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x06;                                  
            reportTestOutcome(bTestCaseNb, bRes);                               
            try {           
                theUiccView.select(UICCTestConstants.FID_DF_SUB_TEST);    
                bRes = true;                                                                                                      
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x07;                                  
            reportTestOutcome(bTestCaseNb, bRes);               
            try {           
                theUiccView.select(UICCTestConstants.FID_EF_TAA);    
                bRes = true;                                                                                                      
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x08;                                  
            reportTestOutcome(bTestCaseNb, bRes);    
            try {    
                theUiccView.select(UICCConstants.FID_DF_TELECOM);    
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.FILE_NOT_FOUND);                                  
            }          
            bTestCaseNb = 0x09;                                  
            reportTestOutcome(bTestCaseNb, bRes);    

            //test case 4: Self selection

            try {
                
                theUiccView.select(UICCTestConstants.FID_MF);     
                theUiccView.select(UICCTestConstants.FID_MF);     
                theUiccView.select(UICCTestConstants.FID_DF_TEST);     
                theUiccView.select(UICCTestConstants.FID_DF_TEST);     
                theUiccView.select(UICCTestConstants.FID_EF_TAAA);     
                theUiccView.select(UICCTestConstants.FID_EF_TAAA);     
                
                theFileView = AdminFileViewBuilder.getTheAdminFileView(AidADF1, JCSystem.CLEAR_ON_RESET);
                                
                theFileView.select(UICCTestConstants.FID_ADF);     
                theFileView.select(UICCTestConstants.FID_ADF);     
                bRes = true;      
                  
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x0A;                                  
            reportTestOutcome(bTestCaseNb, bRes);      



            //test case 5: EF not selected after MF/DF selection     

            recNumber = (short) 1;
            mode = (byte) UICCConstants.REC_ACC_MODE_ABSOLUTE;
            recOffset = (short) 0;
            data[0] = (byte) 0;
            data[1] = (byte) 0;
            data[2] = (byte) 0;
            dataOffset = (short) 0;
            dataLength = (short) 3;
            
            try {           
                theUiccView.select(UICCTestConstants.FID_MF);  
                bRes = true;  
                theUiccView.updateRecord(recNumber, mode, recOffset, data, dataOffset, dataLength);
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.NO_EF_SELECTED);                                  
            }          
            bTestCaseNb = 0x0B;                                  
            reportTestOutcome(bTestCaseNb, bRes);    
            
            try {           
                theUiccView.select(UICCTestConstants.FID_DF_TEST);  
                bRes = true;  
                theUiccView.updateRecord(recNumber, mode, recOffset, data, dataOffset, dataLength);
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.NO_EF_SELECTED);                                  
            }          
            bTestCaseNb = 0x0C;                                  
            reportTestOutcome(bTestCaseNb, bRes);   
                        
            
            //test case 6: No record is selected after selecting linear fixed EF    
  
            try {           
                theUiccView.select(UICCTestConstants.FID_MF);     
                theUiccView.select(UICCTestConstants.FID_DF_TEST);     
                theUiccView.select(UICCTestConstants.FID_EF_LARU);
                bRes = true;                                                                                                      
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x0D;                                  
            reportTestOutcome(bTestCaseNb, bRes);                

            recNumber = (short) 0;
            mode = (byte) UICCConstants.REC_ACC_MODE_CURRENT;
            recOffset = (short) 0;
            respOffset = (short) 0;
            respLength = (short) 4;

            try {          
                theUiccView.readRecord(recNumber, mode, recOffset, resp, respOffset, respLength);
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.RECORD_NOT_FOUND);                                  
            }          
            bTestCaseNb = 0x0E;                                  
            reportTestOutcome(bTestCaseNb, bRes);    
            
            
            try {                                                    
                theUiccView.select(UICCTestConstants.FID_EF_CARU);
                bRes = true;                                                                                                      
            } catch (Exception e){                               
                bRes = false;                                  
            }   
            bTestCaseNb = 0x0F;                                  
            reportTestOutcome(bTestCaseNb, bRes);                

            recNumber = (short) 0;
            mode = (byte) UICCConstants.REC_ACC_MODE_CURRENT;
            recOffset = (short) 0;
            respOffset = (short) 0;
            respLength = (short) 3;

            try {          
                theUiccView.readRecord(recNumber, mode, recOffset, resp, respOffset, respLength);
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.RECORD_NOT_FOUND);                                  
            }          
            bTestCaseNb = 0x10;                                  
            reportTestOutcome(bTestCaseNb, bRes);           

        }    
    }
}
