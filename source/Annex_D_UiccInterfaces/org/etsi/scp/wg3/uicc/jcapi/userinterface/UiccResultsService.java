package org.etsi.scp.wg3.uicc.jcapi.userinterface;

/**
 * A service to allow results to be reported.
 */
public interface UiccResultsService
{
    /**
     * Initialises the results, removing all previous results.
     */
    public void initialiseResults();

    /**
     * Adds a new result.
     */
    public void addResult(boolean result);

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
    public boolean getOverallResult();
}
