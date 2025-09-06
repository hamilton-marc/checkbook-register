/**
 * This is the base class for our Business Logic beans.
 * It serves as a placeholder for functionality that
 * is common to all business logic beans.
 *
 * It also contains functionality to perform validation
 * on the data entry fields.
 *
 * @author Marc Hamilton
 * @date   January 24, 2004
 *
 */
package com.uhills.finance.easetax.biz;

import com.uhills.util.exception.PersistenceException;

import com.uhills.finance.easetax.persist.PersistenceFactory;

public abstract class EaseLedgerBusinessLogicBean
{
    /**
     * This method retrieves the PersistenceManager and
     * calls the appropriate method to save the data to
     * persistent storage.
     *
     * @param contact - the contact to validate
     */
    protected void save() throws PersistenceException
    {
        PersistenceFactory.getInstance().getPersistenceManager().save();
    }
}
