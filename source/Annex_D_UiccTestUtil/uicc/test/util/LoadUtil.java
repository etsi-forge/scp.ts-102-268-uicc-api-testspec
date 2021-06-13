//-----------------------------------------------------------------------------
//    Package Definition
//-----------------------------------------------------------------------------
package uicc.test.util;

//-----------------------------------------------------------------------------
//  Imports
//-----------------------------------------------------------------------------
import org.etsi.scp.wg3.uicc.jcapi.userinterface.*;
import org.etsi.scp.wg3.uicc.jcapi.userclass.*;

public class LoadUtil extends UiccTestModel {

    static final String CAP_FILE_PATH = "uicc/test/util";
    
    private UiccAPITestCardService test;
    APDUResponse response;
      
          
    public LoadUtil() {
        test = UiccAPITestCardService.getTheUiccTestCardService();
    }
    
    public boolean run() {
        APDUResponse data = null;
        boolean result = false;
        
        // test script
        test.reset();

        // install package and applet
        test.loadPackage(CAP_FILE_PATH);
       
        return (true);
    }
}