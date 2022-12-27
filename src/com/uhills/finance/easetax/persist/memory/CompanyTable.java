/*
 * Created on Aug 11, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.persist.memory;

import java.io.Serializable;

import com.uhills.finance.easetax.core.Company;
import com.uhills.finance.easetax.persist.ICompanyPersistence;
import com.uhills.util.exception.PersistenceException;

/**
 * @author HamiltonM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompanyTable implements ICompanyPersistence, Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long   serialVersionUID = -6555370849819656878L;

    private Company             m_company;

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICompanyPersistence#getCompany()
     */
    public Company getCompany() throws PersistenceException
    {
        return (m_company);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICompanyPersistence#updateCompany(com.uhills.finance.easetax.core.Company)
     */
    public void updateCompany(Company company) throws PersistenceException
    {
        m_company = company;
    }

}
