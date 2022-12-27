/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.persist.memory;

import java.util.*;
import java.io.Serializable;

import com.uhills.util.exception.PersistenceException;

import com.uhills.finance.easetax.core.Report;
import com.uhills.finance.easetax.persist.IReportPersistence;

import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportTable implements IReportPersistence, Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long   serialVersionUID = 7006162378246568231L;

    private Map                 m_table;
    private long                m_lNextId = 1;

    /**
     *
     */
    public ReportTable()
    {
        m_table = new Hashtable();
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#getReport(long)
     */
    public Collection getReports() throws PersistenceException
    {
        return (m_table.values());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#getReports(long)
     */
    public Collection getReports(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException
    {
        Collection      filteredCollection;
        ArrayList       reportList;

        if (filterCriteria != null)
            throw new UnsupportedOperationException("Filtering is not supported for the Report table");

        filteredCollection = m_table.values();
        reportList = new ArrayList(filteredCollection);

        if (sortCriteria != null)
            Collections.sort(reportList, new ReportComparator(sortCriteria));

        return (reportList);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#getReport(long)
     */
    public Report getReport(long lReportId) throws PersistenceException
    {
        Report         report = (Report) m_table.get(new Long(lReportId));

        return (report);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#insertReport(com.uhills.finance.easetax.core.Report)
     */
    public void insertReport(Report newReport) throws PersistenceException
    {
        newReport.setID(m_lNextId);
        m_lNextId++;

        m_table.put(newReport.getOID(), newReport);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#updateReport(com.uhills.finance.easetax.core.Report)
     */
    public void updateReport(Report report) throws PersistenceException
    {
        m_table.put(report.getOID(), report);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#deleteReport(com.uhills.finance.easetax.core.Report)
     */
    public void deleteReport(Report report) throws PersistenceException
    {
        m_table.remove(report.getOID());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IReportPersistence#deleteReport(long)
     */
    public void deleteReport(long lReportId) throws PersistenceException
    {
        m_table.remove(new Long(lReportId));
    }

}
