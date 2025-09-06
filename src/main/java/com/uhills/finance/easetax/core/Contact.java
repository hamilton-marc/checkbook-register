package com.uhills.finance.easetax.core;

/**
 * This class holds the properties for a Contact.  A contact
 * is a person or entity associated with a transaction.
 * Some examples are payees, vendors or investors.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.util.validation.*;

public class Contact extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID            = -2543309600988080254L;

    public static final String          CAPTION_CONTACT             = "Contact";

    public static final EntryField      FIELD_NAME                  = new EntryField(1, "Name", 200);
    public static final EntryField      FIELD_DESCRIPTION           = new EntryField(2, "Description", 255);
    public static final EntryField      FIELD_TYPE                  = new EntryField(3, "Type", 255);
    public static final EntryField      FIELD_ACCOUNT_NUM           = new EntryField(4, "Billing Acct Num", 150);

    public String                       name;
    public String                       description;
    public String                       accountNumber;

    private Address                     m_address;
    private ContactType                 m_type;
    private Category                    m_defaultCategory;

    public Contact()
    {
    }

    public Contact(long lId, String strName)
    {
        initialize();

        setID(lId);
        name = strName;
    }

    /**
     * This method sets the id for this contact.
     * For this type of object, the id is
     * represented as a long integer.
     *
     * @param lId - the id for this contact
     */
    public void setID(long lId)
    {
        setOID(new Long(lId));
    }

    /**
     * Retrieves the id for this contact as a
     * long integer.
     *
     * @return the id for this contact
     */
    public long getID()
    {
        Long        longID = (Long) getOID();

        if (longID == null)
            return (0L);

        return (longID.longValue());
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        name                = "";
        description         = "";
        accountNumber       = "";

        m_address           = null;
        m_type              = null;
        m_defaultCategory   = null;
    }

    /**
     * Retrieves the address for this contact.
     *
     * @return the address
     */
    public Address getAddress()
    {
        return (m_address);
    }

    /**
     * Retrieves the type for this contact.
     *
     * @return the contact type
     */
    public ContactType getType()
    {
        // We needed to add this null check because
        // ContactType was added after the original
        // serialization definition of the Contact class.

        if (m_type == null)
            m_type = ContactType.NONE;

        return (m_type);
    }

    /**
     * Sets the contact for this transaction.
     *
     * @param contact - the contact
     */
    public void setType(ContactType type)
    {
        m_type = type;
    }

    /**
     * Retrieves the default category for this contact.
     *
     * @return the default category
     */
    public Category getDefaultCategory()
    {
        return (m_defaultCategory);
    }

    /**
     * Sets the default category for this contact
     *
     * @param category
     */
    public void setDefaultCategory(Category category)
    {
        m_defaultCategory = category;
    }

    /**
     * Sets the address for this contact
     *
     * @param address
     */
    public void setAddress(Address address)
    {
        m_address = address;
    }

    /**
     * Retrieves the phone number for this contact.
     *
     * @return the phone number
     */
    public String getPhoneNumber()
    {
        String      strPhone = "";

        if (m_address != null)
        {
            strPhone = m_address.phone;

            if (strPhone == null)
                strPhone = "";
        }

        return (strPhone);
    }

    /**
     * Retrieves the E-mail address.
     *
     * @return the E-mail address
     */
    public String getEmail()
    {
        String      strEmail = "";

        if (m_address != null)
        {
            strEmail = m_address.email;

            if (strEmail == null)
                strEmail = "";
        }

        return (strEmail);
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(name, FIELD_NAME.caption);

        if (m_address != null)
            m_address.validate();
    }

    /**
     * Overridden from java.lang.Object, this method
     * compares the current object with the object
     * passed in to see if they are equal.  In this
     * implementation, we compare the ids.
     *
     * @param obj - the object to compare
     * @return true if the objects are equal
     */
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Contact)) return false;

        Contact     comparison = (Contact) obj;

        if ((comparison.getOID() == null || ((Long) comparison.getOID()).longValue() == 0) &&
            this.getOID() == null || ((Long) this.getOID()).longValue() == 0)
        {
            return (super.equals(obj));
        }

        return (comparison.getOID().equals(this.getOID()));
    }

    /**
     * Returns the String representation of this object.
     * In this case, the contact name.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (name == null ? "" : name);
    }
}
