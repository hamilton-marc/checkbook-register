package com.uhills.finance.easetax.persist;

/**
 * This interface defines persistent operations that
 * can be performed on contacts.  The reason for
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

public interface IContactPersistence
{
    /**
     * Retrieves a collection of contacts from the persistent store.
     * 
     * @return a Collection of contacts
     * @throws PersistenceException
     */
    public Collection getContacts() throws PersistenceException;

    /**
     * Retrieves a collection of contacts from the persistent store
     * using the search and sort criteria parameters.
     * 
     * @param filterCriteria - criteria to filter the result set
     * @param sortCriteria - criteria to sort the result set
     * @return a Collection of transactions
     * @throws PersistenceException
     */
    public Collection getContacts(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException;

    /**
     * Retrieves the contact from our persistent store
     * corresponding to the id.
     * 
     * @param lContactId - contact
     * @return the corresponding contact
     * @throws PersistenceException
     */
    public Contact getContact(long lContactId) throws PersistenceException;

    /**
     * Inserts a new contact into our persistent store.
     * 
     * @param newContact - the new contact to insert
     * @throws PersistenceException
     */
    public void insertContact(Contact newContact) throws PersistenceException;

    /**
     * Updates an existing contact in our persistent store.
     * 
     * @param contact - the existing contact to update
     * @throws PersistenceException
     */
    public void updateContact(Contact contact) throws PersistenceException;

    /**
     * Deletes a contact from our persistent store.
     * 
     * @param contact the contact to delete
     * @throws PersistenceException
     */
    public void deleteContact(Contact contact) throws PersistenceException;
    public void deleteContact(long lContactId) throws PersistenceException;
}