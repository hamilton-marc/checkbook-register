package com.uhills.finance.easetax.core;

/**
 * This class represents the concept of a company in
 * our application.
 *
 * It is derived from a base object which contains basic
 * validation functionality.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.util.validation.*;

public class Company extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long       serialVersionUID    = 8217562001131004732L;

    public static final EntryField  FIELD_NAME          = new EntryField(1, "Company Name", 150);
    public static final EntryField  FIELD_TYPE          = new EntryField(1, "Company Type", 255);

    public String                   name;

    private Address                 m_address;
    private CompanyType             m_type;

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        setOID(new Long(0L));

        name                = "";
        m_address           = null;
        m_type              = null;
    }

    /**
     * Retrieves the address for this company.
     *
     * @return the address
     */
    public Address getAddress()
    {
        return (m_address);
    }

    /**
     * Sets the address for this company.
     *
     * @param address - the new address
     */
    public void setAddress(Address address)
    {
        m_address = address;
    }

    /**
     * Returns the type for this company
     *
     * @return the company type
     */
    public CompanyType getType()
    {
        return (m_type);
    }

    /**
     * Sets the type for this company
     *
     * @param type - the company type
     */
    public void setType(CompanyType type)
    {
        m_type = type;
    }

    /**
     * This method validates the company entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(name, FIELD_NAME.caption);

        if (m_type == null)
            throw new ValidationException("Please select the appropriate " + FIELD_TYPE.caption + " for your business");
    }

}
