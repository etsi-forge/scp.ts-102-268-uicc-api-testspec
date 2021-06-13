//-----------------------------------------------------------------------------
//Api_4_Aex_Thit_1.java
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//Package Definition
//-----------------------------------------------------------------------------
package uicc.test.access.fileadministration.api_4_aex_thit;

import uicc.test.util.* ;
import uicc.toolkit.*;
import uicc.access.*;
import uicc.access.fileadministration.*;
import javacard.framework.* ;


public class Api_4_Aex_Thit_1 extends TestToolkitApplet
{
    private static byte[] MenuInit = {(byte)'M',(byte)'e',(byte)'n',(byte)'u',(byte)'1'};
    byte testCaseNb = (byte) 0x00;

    /**
     * Constructor of the applet
     */
    public Api_4_Aex_Thit_1()
    {
    }

    /**
     * Method called by the JCRE at the installation of the applet.
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        // Create a new applet instance.
        Api_4_Aex_Thit_1 thisApplet = new Api_4_Aex_Thit_1();
      
        // Register the new applet instance to the JCRE
        thisApplet.register(bArray, (short)(bOffset+1), bArray[bOffset]);
      
        // Initialise the data of the test applet.
        thisApplet.init();
      
        // toolkit registration
        thisApplet.obReg.initMenuEntry(MenuInit, (short) 0,(short) MenuInit.length, (byte) 0, false,(byte) 0, (short) 0);
    }

    /**
     * Method called by the Cat Re
     */
    public void processToolkit(short event) 
    {
        // Number of tests
        byte testCaseNb = (byte)0;
        // Result of tests
        boolean bRes = false;
        
        /** Test Case 1 : Throws the JCRE instance of AdminException with the specified reason 0 */
        testCaseNb = (byte)1;
        bRes = false ;
        try {
            AdminException.throwIt((short) 0);
        }
        catch (AdminException AdminEx) {
            bRes=   (AdminEx.getReason() == (short)0);
        }
        catch( Exception e ) {
        // Error, throw not as an AdminException
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        /** Test Case 2 : Throws the JCRE instance of AdminException with the specified reason 1*/
        testCaseNb = (byte)2;
        bRes = false ;
        
        try {
            AdminException.throwIt((short) 1);
        }
        catch (AdminException AdminEx) {
            bRes =  (AdminEx.getReason() == (short)1 );
        }
        catch( Exception e ) {
        // Error, throw not as an AdminException
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        /** Test Case 3 : Throws the JCRE instance of AdminException with the specified reason 0xA55A*/
        testCaseNb = (byte)3;
        bRes = false ;
        
        try {
            AdminException.throwIt((short) 0xA55A);
        }
        catch (AdminException AdminEx) {
            bRes =  (AdminEx.getReason() == (short)0xA55A );
        }
        catch( Exception e ) {
        // Error, throw not as an AdminException
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        /** Test Case 4 : AdminException extends javacard.framework.CardRuntimeException reason 0*/
        testCaseNb = (byte)4;
        bRes = false ;
        
        try {
            AdminException.throwIt((short) 0);
        }
        catch (CardRuntimeException CardEx) {
            bRes =  (CardEx.getReason() == (short)0 );
        }
        catch( Exception e ) {
        // Error, throw not as an AdminException
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        /** Test Case 5 : AdminException extends javacard.framework.CardRuntimeException reason 1*/
        testCaseNb = (byte)5;
        bRes = false ;
        
        try {
            AdminException.throwIt((short) 1);
        }
        catch (CardRuntimeException CardEx) {
            bRes =  (CardEx.getReason() == (short)1 );
        }
        catch( Exception e ) {
        // Error, throw not as an AdminException
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
        
        /** Test Case 6 : AdminException extends javacard.framework.CardRuntimeException reason 0xA55A*/
        testCaseNb = (byte)6;
        bRes = false ;
        
        try {
            AdminException.throwIt((short) 0xA55A);
        }
        catch (CardRuntimeException CardEx) {
            bRes =  (CardEx.getReason() == (short)0xA55A );
        }
        catch( Exception e ) {
        // Error, throw not as an AdminException
            bRes = false ;
        }
        reportTestOutcome(testCaseNb, bRes) ;
    }
}
