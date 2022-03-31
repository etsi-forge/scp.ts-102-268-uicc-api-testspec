/**
 * @author SCP WG3, ETSI
 * @version 0.10
 * UiccTestModel defines mandatory common methods for all tests 
 */
package org.etsi.scp.wg3.uicc.jcapi.userclass;


public abstract class UiccTestModel  {

    public abstract boolean run();

    /**
     * Initialises the results, removing all previous results.
     */
    protected final void initialiseResults()
    {
        UiccAPITestCardService.getTheUiccTestCardService().initialiseResults();
    }

    /**
     * Adds a new result.
     */
    protected final void addResult(boolean result)
    {
        UiccAPITestCardService.getTheUiccTestCardService().addResult(result);
    }

    /**
     * Returns the overall result, based on all results submitted via {@link #addResult(boolean)}
     * since the last call to {@link #initialiseResults()}.
     * <p>
     * The following logic is used:
     * <ul>
     * <li> if no results have been submitted, <code>false</code> will be returned; </li>
     * <li> if any <code>false</code> results have been submitted, <code>false</code> will be returned; </li>
     * <li> otherwise, <code>true</code> will be returned. </li>
     * </ul>
     */
    protected final boolean getOverallResult()
    {
        return UiccAPITestCardService.getTheUiccTestCardService().getOverallResult();
    }
}
