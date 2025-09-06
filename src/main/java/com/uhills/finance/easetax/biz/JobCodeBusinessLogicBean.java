package com.uhills.finance.easetax.biz;

/**
 * This class contains the navigation and business logic
 * functionality for managing job codes.
 *
 * It also contains functionality to perform validation
 * on the data entry fields.
 *
 * @author Marc Hamilton
 * @date   June 23, 2006
 *
 */

import java.util.*;

import com.uhills.util.validation.*;
import com.uhills.util.exception.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.filtersort.*;

public class JobCodeBusinessLogicBean extends EaseLedgerBusinessLogicBean
{
    /**
     * Default constructor.
     *
     */
    public JobCodeBusinessLogicBean()
    {
    }

    /**
     * This method calls the appropriate method for validating
     * the JobCode information.
     *
     * @param jobCode - the job code to validate
     */
    public void validate(JobCode jobCode) throws ValidationException
    {
        jobCode.validate();
    }

    /**
     * Retrieves a jobCode from our persistent store.
     *
     * @param lJobCodeId - the id of the job code to retrieve
     * @return the job code corresponding to the id
     */
    public JobCode getJobCode(long lJobCodeId) throws PersistenceException
    {
        IJobCodePersistence         jobCodePersistence = PersistenceFactory.getInstance().getDataRepository().getJobCodeTable();
        JobCode                     jobCode = jobCodePersistence.getJobCode(lJobCodeId);

        return (jobCode);
    }

    public Collection getJobCodes() throws PersistenceException
    {
        return (getJobCodes(null));
    }

    /**
     * Retrieves a list of jobCodes from our persistent store.
     *
     * @param displayCriteria - search and sort criteria
     * @return a Collection object containing the jobCodes
     */
    public Collection getJobCodes(DisplayCriteria displayCriteria) throws PersistenceException
    {
        IJobCodePersistence         jobCodePersistence = PersistenceFactory.getInstance().getDataRepository().getJobCodeTable();
        Collection                  collection;

        if (displayCriteria == null)
            collection = jobCodePersistence.getJobCodes();
        else
            collection = jobCodePersistence.getJobCodes(displayCriteria.getFilterCriteria(), displayCriteria.getSortCriteria());

        return (collection);
    }

    /**
     * Saves a job code to our persistent data store.
     * If there's an id associated with the jobCode,
     * we're updating the jobCode.  If not, we assume
     * it is a new jobCode.
     *
     * @param jobCode - the job code to save
     */
    public void saveJobCode(JobCode jobCode) throws PersistenceException, ValidationException
    {
        IJobCodePersistence         jobCodePersistence = PersistenceFactory.getInstance().getDataRepository().getJobCodeTable();

        validate(jobCode);

        if (jobCode.getID() > 0)
        {
            jobCodePersistence.updateJobCode(jobCode);
        }
        else
        {
            jobCodePersistence.insertJobCode(jobCode);
        }

        save();
    }

    /**
     * Deletes a jobCode from our persistent store.
     *
     * @param jobCode - the job code to delete
     */
    public void deleteJobCode(JobCode jobCode) throws PersistenceException
    {
        IJobCodePersistence         jobCodePersistence = PersistenceFactory.getInstance().getDataRepository().getJobCodeTable();

        jobCodePersistence.deleteJobCode(jobCode);
        save();
    }
}
