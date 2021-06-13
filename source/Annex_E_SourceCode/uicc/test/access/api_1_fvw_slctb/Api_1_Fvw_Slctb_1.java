//-----------------------------------------------------------------------------
//    Api_1_Fvw_Slctb_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.api_1_fvw_slctb;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.access.* ;
import uicc.test.util.* ;


public class Api_1_Fvw_Slctb_1 extends TestToolkitApplet{

		private final byte SFI_EFTnr =  0x01;
		private final byte SFI_EFTnu =  0x02;
		private final byte SFI_EFTaru = 0x03;		
		private final byte SFI_EFCnu =  0x05;
		
    byte[] data = new byte[4];    
    byte[] resp = new byte[4];
 
    short fileOffset; 
    short respOffset;
    short respLength;

      
    AID AidADF1;
 
    private FileView theUiccView;
    private FileView theFileView;
  
    /**
     * Constructor of the applet
     */
    public Api_1_Fvw_Slctb_1(){
        
        UICCTestConstants objectConstants = new UICCTestConstants();
        AidADF1 = new AID(objectConstants.AID_ADF1, (short) 0, (byte) objectConstants.AID_ADF1.length);
    
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
              
        
        // Create a new applet instance.
        Api_1_Fvw_Slctb_1 thisApplet = new Api_1_Fvw_Slctb_1();

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



            //test case 1: Selection possiblities, UICC file system                     
            try {
                theUiccView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);  
                theUiccView.select(UICCTestConstants.FID_DF_TEST);          
                theUiccView.select(SFI_EFTnr);   //EFTNR 
                theUiccView.select(SFI_EFTnu);   //EFTNU 
                theUiccView.select(SFI_EFCnu);   //EFCNU   
                bRes = true;             

            } catch (Exception e){
                bRes = false;                       
            }
            bTestCaseNb = 0x01;
            reportTestOutcome(bTestCaseNb, bRes);

            //test case 2: Selection possiblities, ADF1
            
             try {            
                theFileView = UICCSystem.getTheFileView(AidADF1, JCSystem.CLEAR_ON_RESET);
                theFileView.select(UICCTestConstants.FID_DF_TEST);          
                theFileView.select(SFI_EFTnr);   //EFTNR 
                theFileView.select(SFI_EFTnu);   //EFTNU 
                theFileView.select(SFI_EFCnu);   //EFCNU     
                bRes = true;                                     
                                                                 
            } catch (Exception e){                               
                bRes = false;                                    
            }                                                    
            bTestCaseNb = 0x02;                                  
            reportTestOutcome(bTestCaseNb, bRes);                

            //test case 3: Current EF it self can be selected

            try {
                theUiccView = UICCSystem.getTheUICCView(JCSystem.CLEAR_ON_RESET);  
                theUiccView.select(UICCTestConstants.FID_DF_TEST);          
                theUiccView.select(SFI_EFTnr);   //EFTNR 
                theUiccView.select(SFI_EFTnr);   //EFTNR 
                bRes = true;             

            } catch (Exception e){
                bRes = false;                       
            }
            bTestCaseNb = 0x03;
            reportTestOutcome(bTestCaseNb, bRes);  

            //test case 4: FILE_NOT_FOUND 

            try {           
                theUiccView.select((byte) 0x55); 
                bRes = false;                                                                                                      
            } catch (UICCException uicce){                               
                bRes = (uicce.getReason() == UICCException.FILE_NOT_FOUND);                                  
            }   
            bTestCaseNb = 0x04;                                  
            reportTestOutcome(bTestCaseNb, bRes);  


            //test case 5: File context changed

            fileOffset = (short) 0;           
            respOffset = (short) 0;
            respLength = (short) 3;

            try {                   
                
                data[0] = (byte) 0xFF; 
                data[1] = (byte) 0xFF; 
                data[2] = (byte) 0xFF;             
                theUiccView.select(SFI_EFTaru);   //EFTARU
                theUiccView.readBinary((short) fileOffset, (byte[]) resp, (short) respOffset, (short) respLength);
                
                bRes = false;    
                if (Util.arrayCompare(data, (short)0, resp, (short)0, (short)3) == 0) {
                    bRes = true; 
                }                              

            } catch (Exception e){
                bRes = false;                       
            }
            bTestCaseNb = 0x05;
            reportTestOutcome(bTestCaseNb, bRes);  

            try {  
                
                data[0] = (byte) 0x55; 
                data[1] = (byte) 0x55;
                data[2] = (byte) 0x55;
                theUiccView.select(SFI_EFTnu);   //EFTNU 
                theUiccView.readBinary((short) fileOffset, (byte[]) resp, (short) respOffset, (short) respLength);
                
                bRes = false;   
                if (Util.arrayCompare(data, (short)0, resp, (short)0, (short)3) == 0) {
                    bRes = true; 
                }                                    

            } catch (Exception e){
                bRes = false;                       
            }
            bTestCaseNb = 0x06;
            reportTestOutcome(bTestCaseNb, bRes);  

        }    
    }
}
