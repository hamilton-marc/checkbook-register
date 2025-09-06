package com.uhills.finance.easetax.core;

/**
 * This class represents a categorization attribute
 * for a transaction.  Transactions must be categorized
 * for accounting purposes.  In general these fall into
 * either an income or expense bucket.
 *
 * Note that for the purposes of using accounting
 * terminology, a "category" is actually an "account"
 * (part of the Chart of Accounts).
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;
import com.uhills.util.validation.*;

public class Category extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long       serialVersionUID            = -901499820839062486L;

    public static final Category    ALL_CATEGORIES              = new Category(-1, "All Accounts", TransactionType.ALL_TYPES);
    public static final Category    NEW_CATEGORY                = new Category(Integer.MAX_VALUE, "(Enter New Account...)", TransactionType.ALL_TYPES);

    public static final String      CAPTION_CATEGORY            = "Account";

    public static final EntryField  FIELD_NUMBER                = new EntryField( 1, CAPTION_CATEGORY + " Number", 10);
    public static final EntryField  FIELD_NAME                  = new EntryField( 2, "Name", 150);
    public static final EntryField  FIELD_DESCRIPTION           = new EntryField( 3, "Description", 255);
    public static final EntryField  FIELD_TYPE                  = new EntryField( 4, "Type", 255);
    public static final EntryField  FIELD_TAX_FORM              = new EntryField( 5, "Form Name", 255);
    public static final EntryField  FIELD_TAX_LINE              = new EntryField( 6, "Line Number", 10);

    public static final EntryField  FIELD_FORM_1040             = new EntryField( 7, "Form " + TaxCode.FORM_1040, 10);
    public static final EntryField  FIELD_FORM_1120             = new EntryField( 8, "Form " + TaxCode.FORM_1120, 10);
    public static final EntryField  FIELD_FORM_1120S            = new EntryField( 9, "Form " + TaxCode.FORM_1120S, 10);
    public static final EntryField  FIELD_FORM_1065             = new EntryField(10, "Form " + TaxCode.FORM_1065, 10);

    public long                     parent;
    public String                   name;
    public int                      number;
    public String                   description;

    private TransactionType         m_type;
    private Hashtable               m_taxCodes;

    /**
     * Default constructor
     */
    public Category()
    {
    }

    /**
     * This constructor allows us to fill in the relevant fields.
     *
     * @param lId - the id for this category
     * @param strName - the name for this category
     */
    public Category(long lId, String strName, TransactionType type)
    {
        initialize();

        setID(lId);

        name = strName;
        m_type = type;

    }

    /**
     * This constructor allows us to fill in the relevant fields.
     *
     * @param lId - the id for this category
     * @param strName - the name for this category
     */
    public Category(long lId, int iNumber, String strName, TransactionType type)
    {
        initialize();

        setID(lId);

        number = iNumber;
        name = strName;
        m_type = type;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        parent              = 0;
        name                = "";
        number              = 0;
        description         = "";

        m_type              = null;
        m_taxCodes          = new Hashtable();
    }

    /**
     * This sets the id for this category.  For this
     * type of object, the id is represented as a long
     * integer.
     *
     * @param lId - the id for this category
     */
    public void setID(long lId)
    {
        setOID(new Long(lId));
    }

    /**
     * Retrieves the id for this category as a long
     * integer.
     *
     * @return the id for this category
     */
    public long getID()
    {
        Long        longID = (Long) getOID();

        if (longID == null)
            return (0L);

        return (longID.longValue());
    }

    /**
     * Retrieves the contact for this transaction.
     *
     * @return the contact
     */
    public TransactionType getType()
    {
        return (m_type);
    }

    /**
     * Sets the contact for this transaction.
     *
     * @param contact - the contact
     */
    public void setType(TransactionType type)
    {
        m_type = type;
    }


    /**
     * Retrieves the tax code line items associated
     * for the category as an array.  There is one
     * line item entry for each different type of
     * company.
     *
     * @return an array of tax codes
     */
    public Enumeration getAllTaxCodes()
    {
        return (m_taxCodes.elements());
    }

    /**
     * Retrieves the tax code line items associated
     * for the category as an array.  There is one
     * line item entry for each different type of
     * company.
     *
     * @return an array of tax codes
     */
    public TaxCode getTaxCode(String strForm)
    {
        return ((TaxCode) m_taxCodes.get(strForm));
    }

    /**
     * Sets the tax code line items for this category.
     *
     * @param codes - an array of tax code line items
     */
    public void setTaxCode(TaxCode code)
    {
        m_taxCodes.put(code.form, code);
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(name, FIELD_NAME.caption);

        if (m_type == null)
            throw new ValidationException("Please select the " + FIELD_TYPE.caption + " for this account");
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
        if (!(obj instanceof Category)) return false;

        Category    comparison = (Category) obj;

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
        StringBuffer        strbufCaption = new StringBuffer();

        if (number > 0)
            strbufCaption.append(number + " - ");

        if (name != null)
            strbufCaption.append(name);

        return (strbufCaption.toString());
    }
}
