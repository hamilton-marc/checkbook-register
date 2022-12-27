package com.uhills.finance.easetax.core;

/**
 * This class represents the concept of a company type in
 * our application.  Examples are: Sole Proprietorship,
 * Partnership, S Corporation, LLC, LLP, etc.
 *
 * Each company type has records the accounts under differnt
 * line numbers of the tax forms, so it is important to
 * distinguish the type of company that the user is operating.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.util.validation.*;

public class CompanyType extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID    = -894277480337336717L;

    public static final String          CAPTION_TYPE        = "Company Type";

    public static final CompanyType     SOLE_PROPRIETORSHIP = new CompanyType(1, "Sole Proprietorship", "Sole Proprietorship", false);
    public static final CompanyType     PARTNERSHIP         = new CompanyType(2, "Partnership", "Partnership", false);
    public static final CompanyType     S_CORPORATION       = new CompanyType(3, "S Corporation", "S Corporation", false);
    public static final CompanyType     C_CORPORATION       = new CompanyType(4, "C Corporation", "C Corporation", false);
    public static final CompanyType     LL_PARTNERSHIP      = new CompanyType(5, "Limited Liability Partnership", "Limited Liability Partnership", true);
    public static final CompanyType     LL_S_CORPORATION    = new CompanyType(6, "Limited Liability S Corporation", "Limited Liability S Corporation", true);
    public static final CompanyType     LL_C_CORPORATION    = new CompanyType(7, "Limited Liability C Corporation", "Limited Liability C Corporation", true);

    private static final CompanyType[]  m_companyTypes =    {   SOLE_PROPRIETORSHIP,
                                                                PARTNERSHIP,
                                                                S_CORPORATION,
                                                                C_CORPORATION,
                                                                LL_PARTNERSHIP,
                                                                LL_S_CORPORATION,
                                                                LL_C_CORPORATION
                                                            };
    public String                   type;
    public String                   description;
    public boolean                  isLLC;

    /**
     * A constructor which takes all of the properties except for the
     * LLC flag as parameters.
     *
     * @param iId - the id for this object
     * @param strType - the type name
     * @param strDescription - the type description
     */
    public CompanyType(int iId, String strType, String strDescription)
    {
        setID(iId);
        type = strType;
        description = strDescription;
    }

    /**
     * A constructor which takes all of the properties as parameters.
     *
     * @param iId - the id for this object
     * @param strType - the type name
     * @param strDescription - the type description
     * @param bIsLLC - whether or not this is a limited liability company
     */
    public CompanyType(int iId, String strType, String strDescription, boolean bIsLLC)
    {
        this(iId, strType, strDescription);
        isLLC = bIsLLC;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        type                = "";
        description         = "";
        isLLC               = false;
    }

    /**
     * This sets the id for this company type.
     * For this type of object, the id is
     * represented as an integer.
     *
     * @param iId - the id for this company type
     */
    public void setID(int iId)
    {
        setOID(new Integer(iId));
    }

    /**
     * Retrieves the id for this company type as an
     * integer.
     *
     * @return the id for this company type
     */
    public int getID()
    {
        Integer     intID = (Integer) getOID();

        if (intID == null)
            return (0);

        return (intID.intValue());
    }

    /**
     * Retrieves the set of valid company types
     * as an array.
     *
     * @return the set of valid company types.
     */
    public static CompanyType[] getCompanyTypes()
    {
        return (m_companyTypes);
    }

    /**
     * Given a type id, this method finds the
     * CompanyType object associated with that id.
     *
     * @return the set of valid company types.
     */
    public static CompanyType findType(int iTypeId)
    {
        for (int i=0; i < m_companyTypes.length; i++)
        {
            if (m_companyTypes[i].getID() == iTypeId)
                return (m_companyTypes[i]);
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
        if (!(obj instanceof CompanyType)) return false;

        CompanyType     comparison = (CompanyType) obj;

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
     * Overridden from java.lang.Object, this method returns
     * the description as the default String output for this object.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (description);
    }
}
