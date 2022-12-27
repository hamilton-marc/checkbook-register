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
import com.uhills.util.collections.*;

import com.uhills.finance.easetax.core.Transaction;
import com.uhills.finance.easetax.persist.ITransactionPersistence;

import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TransactionTable implements ITransactionPersistence, Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long   serialVersionUID = -8981313428816825700L;

    private Map                 m_table;
    private long                m_lNextId = 1;

    /**
     * 
     */
    public TransactionTable()
    {
        m_table = new Hashtable();
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#getTransaction(long)
     */
    public Collection getTransactions() throws PersistenceException
    {
        return (m_table.values());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#getTransaction(long)
     */
    public Collection getTransactions(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException
    {
        Collection      filteredCollection;
        ArrayList       transList;

        if (filterCriteria == null)
            filteredCollection = m_table.values();
        else 
            filteredCollection = ExtendedCollections.filter(m_table.values(), new TransactionFilter(filterCriteria));

        transList = new ArrayList(filteredCollection);

        if (sortCriteria != null)
            Collections.sort(transList, new TransactionComparator(sortCriteria));

        return (transList);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#getTransaction(long)
     */
    public Transaction getTransaction(long lTransactionId) throws PersistenceException
    {
        Transaction         transaction = (Transaction) m_table.get(new Long(lTransactionId));

        return (transaction);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#insertTransaction(com.uhills.finance.easetax.core.Transaction)
     */
    public void insertTransaction(Transaction newTransaction) throws PersistenceException
    {
        newTransaction.setID(m_lNextId);
        m_lNextId++;

        m_table.put(newTransaction.getOID(), newTransaction);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#updateTransaction(com.uhills.finance.easetax.core.Transaction)
     */
    public void updateTransaction(Transaction transaction) throws PersistenceException
    {
        m_table.put(transaction.getOID(), transaction);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#deleteTransaction(com.uhills.finance.easetax.core.Transaction)
     */
    public void deleteTransaction(Transaction transaction) throws PersistenceException
    {
        m_table.remove(transaction.getOID());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ITransactionPersistence#deleteTransaction(long)
     */
    public void deleteTransaction(long lTransactionId) throws PersistenceException
    {
        m_table.remove(new Long(lTransactionId));
    }

}
