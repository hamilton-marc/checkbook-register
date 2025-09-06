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

import com.uhills.finance.easetax.core.Contact;
import com.uhills.finance.easetax.persist.IContactPersistence;

import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ContactTable implements IContactPersistence, Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long   serialVersionUID = -6864940595631393853L;

    private Map                 m_table;
    private long                m_lNextId = 1;

    /**
     * 
     */
    public ContactTable()
    {
        m_table = new Hashtable();
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#getContact(long)
     */
    public Collection getContacts() throws PersistenceException
    {
        return (m_table.values());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#getContact(long)
     */
    public Collection getContacts(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException
    {
        Collection      filteredCollection;
        ArrayList       contactList;

        if (filterCriteria != null)
            throw new UnsupportedOperationException("Filtering is not supported for the Contact table");

        filteredCollection = m_table.values();
        contactList = new ArrayList(filteredCollection);

        if (sortCriteria != null)
            Collections.sort(contactList, new ContactComparator(sortCriteria));

        return (contactList);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#getContact(long)
     */
    public Contact getContact(long lContactId) throws PersistenceException
    {
        Contact         contact = (Contact) m_table.get(new Long(lContactId));

        return (contact);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#insertContact(com.uhills.finance.easetax.core.Contact)
     */
    public void insertContact(Contact newContact) throws PersistenceException
    {
        newContact.setID(m_lNextId);
        m_lNextId++;

        m_table.put(newContact.getOID(), newContact);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#updateContact(com.uhills.finance.easetax.core.Contact)
     */
    public void updateContact(Contact contact) throws PersistenceException
    {
        m_table.put(contact.getOID(), contact);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#deleteContact(com.uhills.finance.easetax.core.Contact)
     */
    public void deleteContact(Contact contact) throws PersistenceException
    {
        m_table.remove(contact.getOID());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.IContactPersistence#deleteContact(long)
     */
    public void deleteContact(long lContactId) throws PersistenceException
    {
        m_table.remove(new Long(lContactId));
    }

}
