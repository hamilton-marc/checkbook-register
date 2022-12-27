/*
 * Created on June 23, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.persist.memory;

import java.util.*;
import java.io.Serializable;

import com.uhills.util.exception.PersistenceException;
import com.uhills.util.collections.*;

import com.uhills.finance.easetax.core.JobCode;
import com.uhills.finance.easetax.persist.IJobCodePersistence;

import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JobCodeTable implements IJobCodePersistence, Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long   serialVersionUID = -126567180215807725L;

    private Map                 m_table;
    private long                m_lNextId = 1;

    /**
     *
     */
    public JobCodeTable()
    {
        m_table = new Hashtable();
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#getJobCode(long)
     */
    public Collection getJobCodes() throws PersistenceException
    {
        return (m_table.values());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#getJobCodes(long)
     */
    public Collection getJobCodes(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException
    {
        Collection      filteredCollection;
        ArrayList       jobCodeList;

        if (filterCriteria == null)
            filteredCollection = m_table.values();
        else
            filteredCollection = ExtendedCollections.filter(m_table.values(), new JobCodeFilter(filterCriteria));
/*
        if (filterCriteria != null)
            throw new UnsupportedOperationException("Filtering is not supported for the JobCode table");

        filteredCollection = m_table.values();
*/
        jobCodeList = new ArrayList(filteredCollection);

        if (sortCriteria != null)
            Collections.sort(jobCodeList, new JobCodeComparator(sortCriteria));

        return (jobCodeList);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#getJobCode(long)
     */
    public JobCode getJobCode(long lJobCodeId) throws PersistenceException
    {
        JobCode         jobCode = (JobCode) m_table.get(new Long(lJobCodeId));

        return (jobCode);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#insertJobCode(com.uhills.finance.easetax.core.JobCode)
     */
    public void insertJobCode(JobCode newJobCode) throws PersistenceException
    {
        newJobCode.setID(m_lNextId);
        m_lNextId++;

        m_table.put(newJobCode.getOID(), newJobCode);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#updateJobCode(com.uhills.finance.easetax.core.JobCode)
     */
    public void updateJobCode(JobCode jobCode) throws PersistenceException
    {
        m_table.put(jobCode.getOID(), jobCode);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#deleteJobCode(com.uhills.finance.easetax.core.JobCode)
     */
    public void deleteJobCode(JobCode jobCode) throws PersistenceException
    {
        m_table.remove(jobCode.getOID());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IJobCodePersistence#deleteJobCode(long)
     */
    public void deleteJobCode(long lJobCodeId) throws PersistenceException
    {
        m_table.remove(new Long(lJobCodeId));
    }

}
