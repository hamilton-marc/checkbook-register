package com.uhills.finance.easetax.biz;

/**
 * This class contains the navigation and business logic
 * functionality for managing contacts.
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

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.filtersort.*;

public class ContactBusinessLogicBean extends EaseLedgerBusinessLogicBean
{
    /**
     * Default constructor.
     *
     */
    public ContactBusinessLogicBean()
    {
    }

    /**
     * This method calls the appropriate method for validating
     * the Contact information.
     *
     * @param contact - the contact to validate
     */
    public void validate(Contact contact) throws ValidationException
    {
        contact.validate();
    }

    /**
     * Retrieves a contact from our persistent store.
     *
     * @param lContactId - the id of the contact to retrieve
     * @return the contact corresponding to the id
     */
    public Contact getContact(long lContactId) throws PersistenceException
    {
        IContactPersistence         contactPersistence = PersistenceFactory.getInstance().getDataRepository().getContactTable();
        Contact                     contact = contactPersistence.getContact(lContactId);

        return (contact);
    }

    public Collection getContacts() throws PersistenceException
    {
        return (getContacts(null));
    }

    /**
     * Retrieves a list of contacts from our persistent store.
     *
     * @param displayCriteria - search and sort criteria
     * @return a Collection object containing the contacts
     */
    public Collection getContacts(DisplayCriteria displayCriteria) throws PersistenceException
    {
        IContactPersistence         contactPersistence = PersistenceFactory.getInstance().getDataRepository().getContactTable();
        Collection                  collection;

        if (displayCriteria == null)
            collection = contactPersistence.getContacts();
        else
            collection = contactPersistence.getContacts(displayCriteria.getFilterCriteria(), displayCriteria.getSortCriteria());

        return (collection);
    }

    /**
     * Saves a contact to our persistent data store.
     * If there's an id associated with the contact,
     * we're updating the contact.  If not, we assume
     * it is a new contact.
     *
     * @param contact - the contact to save
     */
    public void saveContact(Contact contact) throws PersistenceException, ValidationException
    {
        IContactPersistence         contactPersistence = PersistenceFactory.getInstance().getDataRepository().getContactTable();

        validate(contact);

        Address     address = contact.getAddress();

        // if the address fields are empty, just
        // null it out.

        if (address != null && address.isEmpty())
            contact.setAddress(null);

        if (contact.getID() > 0)
        {
            contactPersistence.updateContact(contact);
        }
        else
        {
            contactPersistence.insertContact(contact);
        }

        save();
    }

    /**
     * Deletes a contact from our persistent store.
     *
     * @param contact - the contact to delete
     */
    public void deleteContact(Contact contact) throws PersistenceException
    {
        IContactPersistence         contactPersistence = PersistenceFactory.getInstance().getDataRepository().getContactTable();

        contactPersistence.deleteContact(contact);
        save();
    }
}