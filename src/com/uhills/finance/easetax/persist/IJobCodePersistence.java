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

public interface IJobCodePersistence
{
    /**
     * Retrieves a collection of categories from the persistent store.
     *
     * @return a Collection of categories
     * @throws PersistenceException
     */
    public Collection getJobCodes() throws PersistenceException;

    /**
     * Retrieves a collection of categories from the persistent store
     * using the search and sort criteria parameters.
     *
     * @param filterCriteria - criteria to filter the result set
     * @param sortCriteria - criteria to sort the result set
     * @return a Collection of transactions
     * @throws PersistenceException
     */
    public Collection getJobCodes(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException;

    /**
     * Retrieves the job code from our persistent store
     * corresponding to the id.
     *
     * @param lJobCodeId - job code
     * @return the corresponding job code
     * @throws PersistenceException
     */
    public JobCode getJobCode(long lJobCodeId) throws PersistenceException;

    /**
     * Inserts a new job code into our persistent store.
     *
     * @param newJobCode - the new job code to insert
     * @throws PersistenceException
     */
    public void insertJobCode(JobCode newJobCode) throws PersistenceException;

    /**
     * Updates an existing job code in our persistent store.
     *
     * @param job code - the existing job code to update
     * @throws PersistenceException
     */
    public void updateJobCode(JobCode jobCode) throws PersistenceException;

    /**
     * Deletes a job code from our persistent store.
     *
     * @param job code the job code to delete
     * @throws PersistenceException
     */
    public void deleteJobCode(JobCode jobCode) throws PersistenceException;
    public void deleteJobCode(long lJobCodeId) throws PersistenceException;
}