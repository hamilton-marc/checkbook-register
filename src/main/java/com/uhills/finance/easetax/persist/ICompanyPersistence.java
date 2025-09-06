package com.uhills.finance.easetax.persist;

/**
 * This interface defines persistent operations that
 * can be performed on the company.  The reason for
 * this interface is to allow us to hide the persistence
 * strategy and implementation for the final product.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.finance.easetax.core.*;
import com.uhills.util.exception.*;

public interface ICompanyPersistence
{
    /**
     * Retrieves the company information from our
     * persistent store.
     * 
     * @return a company object
     * @throws PersistenceException
     */
    public Company getCompany() throws PersistenceException;

    /**
     * Updates the company info in our persistent store.
     * 
     * @param company - the company object
     * @throws PersistenceException
     */
    public void updateCompany(Company company) throws PersistenceException;
}