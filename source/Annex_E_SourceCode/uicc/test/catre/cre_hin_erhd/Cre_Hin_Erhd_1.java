//-----------------------------------------------------------------------------
//    Cre_Hin_Erhd_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_hin_erhd;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import javacard.framework.*;
import uicc.toolkit.* ;
import uicc.test.util.* ;



public class Cre_Hin_Erhd_1 extends TestToolkitApplet{


 
    /**
     * Constructor of the applet
     */
    public Cre_Hin_Erhd_1() {    
                
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Cre_Hin_Erhd_1 thisApplet = new Cre_Hin_Erhd_1();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet.
        thisApplet.init();
    
        // register instance with the EVENT_EVENT_DOWNLOAD_USER_ACTIVITY event
        thisApplet.obReg.setEvent(EVENT_UNRECOGNIZED_ENVELOPE);
   
    }

    
    public void processToolkit(short event)
    {
      
		boolean bRes = false;
        byte    bTestCaseNb = (byte)1;
                
        try{
        	EnvelopeResponseHandler envRespHdlr = EnvelopeResponseHandlerSystem.getTheHandler();
        	if (envRespHdlr.getLength()==(short)0x0000){
        		bRes=true;	
        	}        	
        }
        catch (Exception e)	{
        	bRes=false;
        }
        
            reportTestOutcome ( bTestCaseNb, bRes );	
    }
}
