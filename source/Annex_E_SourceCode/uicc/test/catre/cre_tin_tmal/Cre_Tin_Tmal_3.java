//-----------------------------------------------------------------------------
//    Cre_Tin_Tmal_3.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.catre.cre_tin_tmal;

//-----------------------------------------------------------------------------
//    Imports
//-----------------------------------------------------------------------------
import uicc.toolkit.* ;
import uicc.test.util.* ;


public class Cre_Tin_Tmal_3 extends TestToolkitApplet {

      
    //Timer ID value range
    short MIN_TIMER_ID = 1;
    short MAX_TIMER_ID = 8;
    static byte[] menu = {(byte)'A', (byte)'p', (byte)'p', (byte)'l', (byte)'e', (byte)'t', (byte)'3'}; 
    
    /**
     * Constructor of the applet
     */
    public Cre_Tin_Tmal_3() {
    }


    /**
     * Method called by the JCRE at the installation of the applet
     */
    public static void install(byte bArray[], short bOffset, byte bLength) {

        // Create a new applet instance
        Cre_Tin_Tmal_3 thisApplet = new Cre_Tin_Tmal_3();

        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);

        // Initialise the data of the test applet
        thisApplet.init();    

        // register instance with the EVENT_MENU_SELECTION event
        thisApplet.obReg.initMenuEntry(menu, (short)0, (short)menu.length, (byte)0, false, (byte) 0, (short) 0);
    }
    

    /**
       * Method called by the UICC CRE
     */
    public void processToolkit(short event) {
        
        // Result of each test
        boolean bRes = false ;
        
        // Number of tests
        byte testCaseNb;

	    short i, j;
        
	    if (event == EVENT_MENU_SELECTION) {
       
        	// --------------------------------------------
        	// Test Case 6 : Allocate 4 timers     
        	testCaseNb = (byte)0x01 ;
        	bRes = false ;

        	try {
                Cre_Tin_Tmal_2.timerIdBuffer[4] = obReg.allocateTimer();
                Cre_Tin_Tmal_2.timerIdBuffer[5] = obReg.allocateTimer();
                Cre_Tin_Tmal_2.timerIdBuffer[6] = obReg.allocateTimer();
                Cre_Tin_Tmal_2.timerIdBuffer[7] = obReg.allocateTimer();
            	bRes = true ;
        	}
		    catch (Exception e) {	
			    bRes = false;
		    }
        	reportTestOutcome(testCaseNb, bRes) ;

        	// --------------------------------------------
        	// Test Case 7 : Allocate one more timer     
        	testCaseNb = (byte)0x02 ;
        	bRes = false ;

        	try {
        	    	obReg.allocateTimer();
            	}
		    catch (ToolkitException e) {
			    bRes = (e.getReason()==ToolkitException.NO_TIMER_AVAILABLE);
		    }
        	reportTestOutcome(testCaseNb, bRes) ;
        	
        	// --------------------------------------------
        	// Test Case 8 : each timerId shall be between 1 and 8 and shall be different from each other
        	testCaseNb = (byte)0x03 ;
        	bRes = true ;
   
		    for(i = 0; i < MAX_TIMER_ID ;i++) {
			
			    // each timerId shall be between 1 and 8
			    if ((short)(Cre_Tin_Tmal_2.timerIdBuffer[i]) < MIN_TIMER_ID || 
					    (short)(Cre_Tin_Tmal_2.timerIdBuffer[i]) > MAX_TIMER_ID ) {
				    bRes = false;
			    }
			    
			    // each timerId shall be different from each other
			    for(j = (short)(i+1); j < MAX_TIMER_ID ;j++) {
				    if (Cre_Tin_Tmal_2.timerIdBuffer[i] == Cre_Tin_Tmal_2.timerIdBuffer[j] ) {
					    bRes = false;
                    }
			    }
	
		    }
	
		    reportTestOutcome(testCaseNb, bRes) ;
	    }
    }
}
