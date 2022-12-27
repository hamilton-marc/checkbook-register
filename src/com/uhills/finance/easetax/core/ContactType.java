package com.uhills.finance.easetax.core;

/**
 * This class represents a report type in
 * our application.  Each report type has an associated
 * report definition file.  This is used to format the
 * report.
 *
 * It is derived from a base object which contains basic
 * validation functionality.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.util.validation.*;

public class ContactType extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID = 865360427675980525L;

    public static final String          CAPTION_TYPE        = "Contact Type";

    public static final ContactType     NONE                = new ContactType(0, "None", "---");
    public static final ContactType     CUSTOMER            = new ContactType(1, "Customer", "Customer");
    public static final ContactType     VENDOR              = new ContactType(2, "Vendor", "Vendor");
    public static final ContactType     OTHER               = new ContactType(3, "Other", "Other");

    private static final ContactType[]  m_contactTypes =
    {
        CUSTOMER,
        VENDOR,
        OTHER
    };

    public String                   type;
    public String                   description;

    /**
     * This constructor takes the Id, type, description and template file location
     *
     * @param lId - the id for this object
     * @param strType - the type for this report
     * @param strDescription - the description of this report
     * @param strTemplate - the template file name
     */
    public ContactType(int iId, String strType, String strDescription)
    {
        setID(iId);
        type = strType;
        description = strDescription;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        type                = "";
        description         = "";
    }

    /**
     * This sets the id for this report type.
     * For this type of object, the id is
     * represented as an integer.
     *
     * @param iId - the id for this report type
     */
    public void setID(int iId)
    {
        setOID(new Integer(iId));
    }

    /**
     * Retrieves the id for this report type as an
     * integer.
     *
     * @return the id for this report type
     */
    public int getID()
    {
        Integer     intID = (Integer) getOID();

        if (intID == null)
            return (0);

        return (intID.intValue());
    }

    /**
     * Retrieves the set of valid report types
     * as an array.
     *
     * @return the set of valid report types.
     */
    public static ContactType[] getContactTypes()
    {
        return (m_contactTypes);
    }

    /**
     * Given a type id, this method finds the
     * ContactType object associated with that id.
     *
     * @return the set of valid report types.
     */
    public static ContactType findType(int iTypeId)
    {
        for (int i=0; i < m_contactTypes.length; i++)
        {
            if (m_contactTypes[i].getID() == iTypeId)
                return (m_contactTypes[i]);
        }

        return (null);
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
        if (!(obj instanceof ContactType)) return false;

        ContactType     comparison = (ContactType) obj;

        if ((comparison.getOID() == null || ((Integer) comparison.getOID()).intValue() == 0) &&
            this.getOID() == null || ((Integer) this.getOID()).intValue() == 0)
        {
            return (super.equals(obj));
        }

        return (comparison.getOID().equals(this.getOID()));
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(type, CAPTION_TYPE);
    }

    /**
     * Returns the String representation of this object.
     * In this case, the description.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (description);
    }
}
