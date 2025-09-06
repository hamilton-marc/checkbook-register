package com.uhills.finance.easetax.core;

/**
 * This class represents the concept of a transaction type.
 * There are 4 different types of transactions:
 *
 * - Revenue
 * - Contribution
 * - Expense
 * - Withdrawl
 *
 * It is derived from a base object which contains basic
 * validation functionality.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.util.validation.*;

public class TransactionType extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long               serialVersionUID    = 5854090171801998229L;

    public static final String              CAPTION_TYPE        = "Transaction Type";

    public static final TransactionType     ALL_TYPES           = new TransactionType(0, "All Transaction Types", "All Transaction Types");
    public static final TransactionType     REVENUE             = new TransactionType(1, "Revenue", "Revenue");
    public static final TransactionType     CONTRIBUTION        = new TransactionType(2, "Contribution", "Contribution");
    public static final TransactionType     EXPENSE             = new TransactionType(3, "Expense", "Expense");
    public static final TransactionType     WITHDRAWL           = new TransactionType(4, "Withdrawl", "Withdrawl");

    private static final TransactionType[]  m_transactionTypes =
    {
        ALL_TYPES,
        REVENUE,
        CONTRIBUTION,
        EXPENSE,
        WITHDRAWL
    };

    public String                   type;
    public String                   description;

    public TransactionType(int iId, String strType, String strDescription)
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
     * This sets the id for this transaction type.
     * For this type of object, the id is
     * represented as an integer.
     *
     * @param iId - the id for this transaction type
     */
    public void setID(int iId)
    {
        setOID(new Integer(iId));
    }

    /**
     * Retrieves the id for this transaction type as an
     * integer.
     *
     * @return the id for this transaction type
     */
    public int getID()
    {
        Integer     intID = (Integer) getOID();

        if (intID == null)
            return (0);

        return (intID.intValue());
    }

    /**
     * Retrieves the set of valid transaction types
     * as an array.
     *
     * @return the set of valid transaction types.
     */
    public static TransactionType[] getTransactionTypes()
    {
        return (getTransactionTypes(true));
    }

    /**
     * Retrieves the set of valid transaction types
     * as an array.
     *
     * @return the set of valid transaction types.
     */
    public static TransactionType[] getTransactionTypes(boolean bIncludeAllTypes)
    {
        if (bIncludeAllTypes)
        {
            return (m_transactionTypes);
        }

        TransactionType[]       transTypes = new TransactionType[m_transactionTypes.length - 1];

        for (int i=0; i < transTypes.length; i++)
        {
            transTypes[i] = m_transactionTypes[i+1];
        }

        return (transTypes);
    }

    /**
     * Given a type id, this method finds the
     * TransactionType object associated with that id.
     *
     * @return the set of valid transaction types.
     */
    public static TransactionType findType(int iTypeId)
    {
        for (int i=0; i < m_transactionTypes.length; i++)
        {
            if (m_transactionTypes[i].getID() == iTypeId)
                return (m_transactionTypes[i]);
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
        if (!(obj instanceof TransactionType)) return false;

        TransactionType     comparison = (TransactionType) obj;

        if ((comparison.getOID() == null || ((Integer) comparison.getOID()).intValue() == 0) &&
            this.getOID() == null || ((Integer) this.getOID()).intValue() == 0)
        {
            return (super.equals(obj));
        }

        return (comparison.getOID().equals(this.getOID()));
    }

    /**
     * This method validates the transaction type entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(type, CAPTION_TYPE);
    }

    /**
     * Returns the String representation of this object.
     * In this case, the type name.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (type);
    }
}
