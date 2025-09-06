/*
 * Created on Aug 29, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.persist.file;

import java.io.Serializable;

import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.persist.memory.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FileDatabase implements Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID = 3557407361459322418L;

    private CompanyTable                m_companyTable;
    private CategoryTable               m_categoryTable;
    private ContactTable                m_contactTable;
    private TransactionTable            m_transactionTable;
    private ReportTable                 m_reportTable;
    private JobCodeTable                m_jobCodeTable;

    public FileDatabase()
    {
    }

    public void initialize()
    {
        m_companyTable      = new CompanyTable();
        m_categoryTable     = new CategoryTable();
        m_contactTable      = new ContactTable();
        m_transactionTable  = new TransactionTable();
        m_reportTable       = new ReportTable();
        m_jobCodeTable      = new JobCodeTable();
    }

    public ITransactionPersistence getTransactionTable()
    {
        return (m_transactionTable);
    }

    public ICompanyPersistence getCompanyTable()
    {
        return (m_companyTable);
    }

    public IContactPersistence getContactTable()
    {
        return (m_contactTable);
    }

    public ICategoryPersistence getCategoryTable()
    {
        return (m_categoryTable);
    }

    public IReportPersistence getReportTable()
    {
        return (m_reportTable);
    }

    public IJobCodePersistence getJobCodeTable()
    {
        // The Job Code Table was added at a later date, therefore it will
        // not be in the existing file database. Therefore we need to create
        // one if one does not exist.

        if (m_jobCodeTable == null)
            m_jobCodeTable = new JobCodeTable();

        return (m_jobCodeTable);
    }
}
