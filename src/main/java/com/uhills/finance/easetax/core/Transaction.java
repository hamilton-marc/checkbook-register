package com.uhills.finance.easetax.core;

/**
 * This class represents the concept of a transaction in
 * our application.  A transaction represents a financial
 * activity that has occurred in a company.
 * 
 * It is derived from a base object which contains basic
 * validation functionality.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.Date;

import com.uhills.finance.easetax.main.*;
import com.uhills.util.validation.*;

public class Transaction extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long       serialVersionUID            = 4401115158523463797L;

    public static final String      CAPTION_TRANSACTION         = "Transaction";

    public static final String      CAPTION_PAYEE               = "Payee";
    public static final String      CAPTION_FROM                = "From";
    public static final String      CAPTION_DEBIT               = "Debit";
    public static final String      CAPTION_CREDIT              = "Credit";

    public static final String      CAPTION_ALL_TYPES           = "All " + TransactionType.CAPTION_TYPE + "s";
    public static final String      CAPTION_REVENUE             = "Revenue";
    public static final String      CAPTION_CONTRIBUTION        = "Contribution";
    public static final String      CAPTION_WITHDRAWL           = "Withdrawl";
    public static final String      CAPTION_EXPENSE             = "Expense";

    public static final EntryField  FIELD_NUMBER_CODE           = new EntryField(1, "Ref Num", 10);
    public static final EntryField  FIELD_CONTACT               = new EntryField(2, Contact.CAPTION_CONTACT, Contact.FIELD_NAME.maxLength);
    public static final EntryField  FIELD_CATEGORY              = new EntryField(3, Category.CAPTION_CATEGORY, 255);
    public static final EntryField  FIELD_TYPE                  = new EntryField(4, TransactionType.CAPTION_TYPE, 255);
    public static final EntryField  FIELD_DATE                  = new EntryField(5, "Date", 10);
    public static final EntryField  FIELD_DESCRIPTION           = new EntryField(6, "Memo", 255);
    public static final EntryField  FIELD_AMOUNT                = new EntryField(7, "Amount", 10);
    public static final EntryField  FIELD_JOB_CODE              = new EntryField(8, JobCode.CAPTION_JOB_CODE, 255);

    public String                   numberCode;
    public Date                     transactionDate;
    public Date                     entryDate;
    public String                   description;
    public double                   amount;

    private TransactionType         m_type;
    private Contact                 m_contact;
    private Category                m_category;
    private JobCode                 m_jobCode;

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        numberCode          = "";
        transactionDate     = null;
        entryDate           = new Date();
        description         = "";
        amount              = 0;

        m_type              = null;
        m_contact           = null;
        m_category          = null;
        m_jobCode           = null;
    }

    /**
     * This method sets the id for this transaction.
     * For this type of object, the id is
     * represented as a long integer.
     *
     * @param lId - the id for this transaction
     */
    public void setID(long lId)
    {
        setOID(new Long(lId));
    }

    /**
     * Retrieves the id for this transaction as a
     * long integer.
     *
     * @return the id for this transaction
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
     * Retrieves the contact for this transaction.
     * 
     * @return the contact
     */
    public Contact getContact()
    {
        return (m_contact);
    }

    /**
     * Sets the contact for this transaction.
     * 
     * @param contact - the contact
     */
    public void setContact(Contact contact)
    {
        m_contact = contact;
    }

    /**
     * Retrieves the category for this transaction.
     * 
     * @return the category
     */
    public Category getCategory()
    {
        return (m_category);
    }

    /**
     * Sets the category for this transaction.
     * 
     * @param category - the category
     */
    public void setCategory(Category category)
    {
        m_category = category;
    }

    /**
     * Retrieves the job code for this transaction.
     * 
     * @return the job code
     */
    public JobCode getJobCode()
    {
        if (m_jobCode == null)
        {
            return(JobCode.NULL_JOB_CODE);
        }

        return (m_jobCode);
    }

    /**
     * Sets the job code for this transaction.
     * 
     * @param job code - the job code
     */
    public void setJobCode(JobCode jobCode)
    {
        m_jobCode = jobCode;
    }

    /**
     * Format the transaction date in a reasonable fashion.
     *
     * @param strFormat - format string
     */
    public String formatTransactionDate(String strFormat)
    {
        return (formatDate(transactionDate, strFormat));
    }

    public String formatTransactionDate()
    {
        return (formatTransactionDate(UserPreferences.DATE_FORMAT_DEFAULT));
    }

    /**
     * Format the amount using a currency format.
     *
     * @param strFormat - format string
     */
    public String formatAmount()
    {
        return (formatCurrency(amount));
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        if (m_contact == null)
            throw new ValidationException("Please enter or select a " + FIELD_CONTACT.caption);

        getValidator().exists(m_contact.name, FIELD_CONTACT.caption);

        if (m_category == null)
            throw new ValidationException("Please select an " + FIELD_CATEGORY.caption);

        if (m_type == null)
            throw new ValidationException("Please select the " + FIELD_TYPE.caption + " for this transaction");

        if (amount == 0)
            throw new ValidationException("Please enter an " + FIELD_AMOUNT.caption + " for this transaction");
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
        if (!(obj instanceof Transaction)) return false;

        Transaction     comparison = (Transaction) obj;

        if ((comparison.getOID() == null || ((Long) comparison.getOID()).longValue() == 0) &&
            this.getOID() == null || ((Long) this.getOID()).longValue() == 0)
        {
            return (super.equals(obj));
        }

        return (comparison.getOID().equals(this.getOID()));
    }
}
