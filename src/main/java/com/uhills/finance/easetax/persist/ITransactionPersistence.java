package com.uhills.finance.easetax.persist;

/**
 * This interface defines persistent operations that
 * can be performed on transactions.  The reason for
 * this interface is to allow us to hide the persistence
 * strategy and implementation for the final product.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;

import com.uhills.util.exception.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.filtersort.*;

public interface ITransactionPersistence
{
    /**
     * Retrieves a collection of transactions from the persistent store.
     * 
     * @return a Collection of transactions
     * @throws PersistenceException
     */
    public Collection getTransactions() throws PersistenceException;

    /**
     * Retrieves a collection of transactions from the persistent store
     * using the search and sort criteria parameters.
     * 
     * @param filterCriteria - criteria to filter the result set
     * @param sortCriteria - criteria to sort the result set
     * @return a Collection of transactions
     * @throws PersistenceException
     */
    public Collection getTransactions(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException;

    /**
     * Retrieves the transaction from our persistent store
     * corresponding to the id.
     * 
     * @param lTransactionId - transaction
     * @return the corresponding transaction
     * @throws PersistenceException
     */
    public Transaction getTransaction(long lTransactionId) throws PersistenceException;

    /**
     * Inserts a new transaction into our persistent store.
     * 
     * @param newTransaction - the new transaction to insert
     * @throws PersistenceException
     */
    public void insertTransaction(Transaction newTransaction) throws PersistenceException;

    /**
     * Updates an existing transaction in our persistent store.
     * 
     * @param transaction - the existing transaction to update
     * @throws PersistenceException
     */
    public void updateTransaction(Transaction transaction) throws PersistenceException;

    /**
     * Deletes a transaction from our persistent store.
     * 
     * @param transaction the transaction to delete
     * @throws PersistenceException
     */
    public void deleteTransaction(Transaction transaction) throws PersistenceException;
    public void deleteTransaction(long lTransactionId) throws PersistenceException;
}