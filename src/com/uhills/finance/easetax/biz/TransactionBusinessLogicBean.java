package com.uhills.finance.easetax.biz;

/**
 * This class contains the navigation and business logic
 * functionality for managing transactions.
 *
 * It also contains functionality to perform validation
 * on the data entry fields.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;

import com.uhills.util.validation.*;
import com.uhills.util.exception.*;

import com.uhills.finance.easetax.filtersort.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.persist.*;

public class TransactionBusinessLogicBean extends EaseLedgerBusinessLogicBean
{
    /**
     * Default constructor.
     *
     */
    public TransactionBusinessLogicBean()
    {
    }

    /**
     * This method calls the appropriate method for validating
     * the Transaction information.
     *
     * @param transaction - the transaction to validate
     */
    public void validate(Transaction transaction) throws ValidationException
    {
        transaction.validate();
    }

    /**
     * Retrieves a transaction from our persistent store.
     *
     * @param lTransactionId - the id of the transaction to retrieve
     * @return the transaction corresponding to the id
     */
    public Transaction getTransaction(long lTransactionId) throws PersistenceException
    {
        ITransactionPersistence     transactionPersistence = PersistenceFactory.getInstance().getDataRepository().getTransactionTable();
        Transaction                 transaction = transactionPersistence.getTransaction(lTransactionId);

        if (transaction != null)
        {
            joinCategory(transaction);
            joinContact(transaction);
        }

        return (transaction);
    }

    /**
     * Retrieves a list of transactions from our persistent store.
     *
     * @return a Collection object containing the transactions
     */
    public Collection getTransactions() throws PersistenceException
    {
        return (getTransactions(null));
    }

    public boolean isCategoryInUse(Category category) throws PersistenceException
    {
        ITransactionPersistence     transactionPersistence = PersistenceFactory.getInstance().getDataRepository().getTransactionTable();
        boolean                     bInUse = false;
        Collection                  collection;
        FilterCriteria              filterCriteriaArray[] = new FilterCriteria[1];

        filterCriteriaArray[0] = new FilterCriteria(Transaction.FIELD_CATEGORY.id, FilterCriteria.EQUALS, category);
        collection = transactionPersistence.getTransactions(filterCriteriaArray, null);

        bInUse = (collection != null && !collection.isEmpty());

        return (bInUse);
    }

    /**
     * Retrieves a list of transactions from our persistent store.
     *
     * @return a Collection object containing the transactions
     */
    public Collection getTransactions(DisplayCriteria displayCriteria) throws PersistenceException
    {
        ITransactionPersistence     transactionPersistence = PersistenceFactory.getInstance().getDataRepository().getTransactionTable();
        Collection                  collection;

        if (displayCriteria == null)
            collection = transactionPersistence.getTransactions();
        else
            collection = transactionPersistence.getTransactions(displayCriteria.getFilterCriteria(), displayCriteria.getSortCriteria());

        if (collection != null)
        {
            for (Iterator iterator = collection.iterator(); iterator.hasNext(); )
            {
                Transaction     transaction = (Transaction) iterator.next();

                // retrieve the associated contact and category

                joinCategory(transaction);
                joinContact(transaction);
            }
        }

        return (collection);
    }

    /**
     * This method retrieves the associated category for this transaction
     * from the Category table.
     *
     * @param transaction - the transaction to retrieve the category for
     */
    private void joinCategory(Transaction transaction) throws PersistenceException
    {
        ICategoryPersistence        categoryPersistence = PersistenceFactory.getInstance().getDataRepository().getCategoryTable();
        Category                    category = transaction.getCategory();

        category = categoryPersistence.getCategory(category.getID());

        if (category != null)
            transaction.setCategory(category);
    }

    /**
     * This method retrieves the associated contact for this transaction
     * from the Contact table.
     *
     * @param transaction - the transaction to retrieve the category for
     */
    private void joinContact(Transaction transaction) throws PersistenceException
    {
        IContactPersistence         contactPersistence = PersistenceFactory.getInstance().getDataRepository().getContactTable();
        Contact                     contact = transaction.getContact();

        if (contact.getID() > 0)
        {
            contact = contactPersistence.getContact(contact.getID());

            if (contact != null)
                transaction.setContact(contact);
        }
    }

    /**
     * Checks to see if the contact entered for the transaction
     * already exists in the database.
     *
     * @param transaction - the transaction to save
     */
    private void checkForNewContact(Transaction transaction) throws NewContactException
    {
        Long        contactId = (Long) transaction.getContact().getOID();

        if (contactId == null)
        {
            throw new NewContactException("The contact " + transaction.getContact().name + " does not exist.",
                                          transaction.getContact().name);
        }
    }

    /**
     * Checks to see if the transaction date entered for the
     * transaction is a future date.
     *
     * @param transaction - the transaction to save
     */
    private void checkForFutureDate(Transaction transaction) throws FutureTransactionDateException
    {
        if (transaction.transactionDate.after(new Date()))
        {
            throw new FutureTransactionDateException("The transaction date is a date in the future.",
                                                     transaction.transactionDate);
        }
    }

    /**
     * Checks to see if the transaction date entered for the
     * transaction is a future date.
     *
     * @param transaction - the transaction to save
     */
    private void checkTransactionType(Transaction transaction) throws TransactionTypeMismatchException
    {
        if (!transaction.getType().equals(transaction.getCategory().getType()))
        {
            StringBuffer        strbufMessage = new StringBuffer();

            strbufMessage.append("The transaction type (" + transaction.getType() + ")");
            strbufMessage.append(" does not match the type (" + transaction.getCategory().getType() + ")");            strbufMessage.append(" associated with the account (" + transaction.getCategory() + ").");

            throw new TransactionTypeMismatchException(strbufMessage.toString(),
                                                       transaction.getCategory().getType());
        }
    }

    /**
     * Saves a transaction to our persistent data store.
     * An extra flag tells us whether to save the contact if it
     * doesn't already exist in our data store.
     * 
     * If the user enters a contact that is not already
     * in the database, they are given the choice as to
     * whether or not they want to save that contact in
     * the contact table.
     *
     * @param transaction - the transaction to save
     * @param bSaveContact - whether or not to save the contact
     *                       to the Contact table
     */
    public void saveTransaction(Transaction transaction, TransactionSaveRules tranSaveRules) throws PersistenceException, ValidationException, FutureTransactionDateException, NewContactException, TransactionTypeMismatchException
    {
        validate(transaction);

        // check to see if the user wants to be notified of a new contact
        // if not, automatically save the new contact
        if (!ApplicationMain.getUserPreferences().promptOnNewContact)
        {
            tranSaveRules.setSaveContact(true);
        }

        // throws an exception if the trans type mismatches the account type
        if (tranSaveRules.getCategoryOk() == null || !tranSaveRules.getCategoryOk().booleanValue())
            checkTransactionType(transaction);

        // throws an exception if the trans date is in the future
        if (tranSaveRules.getDateOk() == null || !tranSaveRules.getDateOk().booleanValue())
            checkForFutureDate(transaction);

        // throws an exception if the contact does not exist
        if (tranSaveRules.getSaveContact() == null)
            checkForNewContact(transaction);

        ITransactionPersistence     transactionPersistence = PersistenceFactory.getInstance().getDataRepository().getTransactionTable();
        Long                        contactId = (Long) transaction.getContact().getOID();

        if (contactId == null)
        {
            insertContact(transaction, tranSaveRules.getSaveContact().booleanValue());
        }

        if (transaction.getID() > 0)
        {
            transactionPersistence.updateTransaction(transaction);
        }
        else
        {
            transactionPersistence.insertTransaction(transaction);
        }

        save();
    }

    /**
     * Inserts the new contact into the Contact persistent store.
     *
     * @param transaction - the transaction to save
     * @param bSaveContact - whether or not to save the contact
     *                       to the Contact table
     */
    private void insertContact(Transaction transaction, boolean bSaveContact) throws PersistenceException
    {
        IContactPersistence     contactPersistence = PersistenceFactory.getInstance().getDataRepository().getContactTable();
        Contact                 newContact = transaction.getContact();

        // Check what type of transaction is being entered,
        // then set the contact type as appropriate.

        if (transaction.getType().equals(TransactionType.REVENUE))
            newContact.setType(ContactType.CUSTOMER);
        else if (transaction.getType().equals(TransactionType.EXPENSE))
            newContact.setType(ContactType.VENDOR);
        else
            newContact.setType(ContactType.OTHER);

        newContact.setDefaultCategory(transaction.getCategory());

        if (bSaveContact)
        {
            contactPersistence.insertContact(newContact);
        }
        else
        {
            newContact.setID(0L);
        }

        transaction.setContact(newContact);
    }

    /**
     * Saves a transaction to our persistent data store.
     * If there's an id associated with the transaction,
     * we're updating the transaction.  If not, we assume
     * it is a new transaction.
     *
     * @param transaction - the transaction to save
     */
    public void saveTransaction(Transaction transaction) throws PersistenceException, ValidationException, FutureTransactionDateException, NewContactException, TransactionTypeMismatchException
    {
        saveTransaction(transaction, new TransactionSaveRules());
    }

    /**
     * Deletes a transaction from our persistent store.
     *
     * @param transaction - the transaction to delete
     */
    public void deleteTransaction(Transaction transaction) throws PersistenceException
    {
        ITransactionPersistence     transactionPersistence = PersistenceFactory.getInstance().getDataRepository().getTransactionTable();

        transactionPersistence.deleteTransaction(transaction);
        save();
    }
}