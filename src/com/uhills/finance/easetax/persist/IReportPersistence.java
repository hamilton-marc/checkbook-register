package com.uhills.finance.easetax.persist;

/**
 * This interface defines persistent operations that
 * can be performed on categories.  The reason for
 * this interface is to allow us to hide the persistence
 * strategy and implementation for the final product.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.util.exception.*;
import com.uhills.finance.easetax.filtersort.*;

public interface IReportPersistence
{
    /**
     * Retrieves a collection of categories from the persistent store.
     *
     * @return a Collection of categories
     * @throws PersistenceException
     */
    public Collection getReports() throws PersistenceException;

    /**
     * Retrieves a collection of categories from the persistent store
     * using the search and sort criteria parameters.
     *
     * @param filterCriteria - criteria to filter the result set
     * @param sortCriteria - criteria to sort the result set
     * @return a Collection of transactions
     * @throws PersistenceException
     */
    public Collection getReports(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException;

    /**
     * Retrieves the report from our persistent store
     * corresponding to the id.
     *
     * @param lReportId - report
     * @return the corresponding report
     * @throws PersistenceException
     */
    public Report getReport(long lReportId) throws PersistenceException;

    /**
     * Inserts a new report into our persistent store.
     *
     * @param newReport - the new report to insert
     * @throws PersistenceException
     */
    public void insertReport(Report newReport) throws PersistenceException;

    /**
     * Updates an existing report in our persistent store.
     *
     * @param report - the existing report to update
     * @throws PersistenceException
     */
    public void updateReport(Report report) throws PersistenceException;

    /**
     * Deletes a report from our persistent store.
     *
     * @param report the report to delete
     * @throws PersistenceException
     */
    public void deleteReport(Report report) throws PersistenceException;
    public void deleteReport(long lReportId) throws PersistenceException;
}