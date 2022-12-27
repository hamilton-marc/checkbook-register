package com.uhills.finance.easetax.core;

/**
 * This class represents a reference type in
 * our application.  Each reference type has an associated
 * reference definition file.  This is used to format the
 * reference.
 *
 * It is derived from a base object which contains basic
 * validation functionality.
 *
 * Note: As of now, this class does not need to be
 * serialized.
 *
 * @author Marc Hamilton
 * @date   November 19, 2005
 *
 */

import com.uhills.util.validation.*;

public class ReferenceType extends EaseTaxObject
{
    public static final String              CAPTION_REFERENCE_NUMBER    = "Reference Number";

    public static final ReferenceType       DEBIT               = new ReferenceType(1, "DB",  "Debit");
    public static final ReferenceType       CHECK               = new ReferenceType(2, "CK",  "Check");
    public static final ReferenceType       SERVICE_CHARGE      = new ReferenceType(3, "SC",  "Service Charge");
    public static final ReferenceType       WITHDRAWL           = new ReferenceType(4, "WD",  "Withdrawl");
    public static final ReferenceType       CONTRIBUTION        = new ReferenceType(5, "CNT", "Contribution");
    public static final ReferenceType       OTHER               = new ReferenceType(6, "OTH", "Other");

    private static final ReferenceType[]    m_referenceTypes =
    {
        DEBIT,
        CHECK,
        SERVICE_CHARGE,
        WITHDRAWL,
        CONTRIBUTION,
        OTHER
    };

    public String                   type;
    public String                   description;

    /**
     * This constructor takes the Id, type, description and template file location
     *
     * @param lId - the id for this object
     * @param strType - the type for this reference
     * @param strDescription - the description of this reference
     */
    public ReferenceType(int iId, String strType, String strDescription)
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
     * This sets the id for this reference type.
     * For this type of object, the id is
     * represented as an integer.
     *
     * @param iId - the id for this reference type
     */
    public void setID(int iId)
    {
        setOID(new Integer(iId));
    }

    /**
     * Retrieves the id for this reference type as an
     * integer.
     *
     * @return the id for this reference type
     */
    public int getID()
    {
        Integer     intID = (Integer) getOID();

        if (intID == null)
            return (0);

        return (intID.intValue());
    }

    /**
     * Retrieves the set of valid reference types
     * as an array.
     *
     * @return the set of valid reference types.
     */
    public static ReferenceType[] getReferenceTypes()
    {
        return (m_referenceTypes);
    }

    /**
     * Given a type id, this method finds the
     * ReferenceType object associated with that id.
     *
     * @return the set of valid reference types.
     */
    public static ReferenceType findType(int iTypeId)
    {
        for (int i=0; i < m_referenceTypes.length; i++)
        {
            if (m_referenceTypes[i].getID() == iTypeId)
                return (m_referenceTypes[i]);
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
        if (!(obj instanceof ReferenceType)) return false;

        ReferenceType   comparison = (ReferenceType) obj;

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
        // No validation necessary.
    }

    /**
     * Returns the String representation of this object.
     * In this case, the description.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (type + " - " + description);
    }
}
