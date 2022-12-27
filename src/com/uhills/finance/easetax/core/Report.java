package com.uhills.finance.easetax.core;

/**
 * This class represents a Report and has all the properties
 * that define a report.
 *
 * @author Marc Hamilton
 * @date   November 26, 2003
 *
 */

import java.util.*;
import java.text.SimpleDateFormat;
import com.uhills.util.validation.*;

public class Report extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID            = 9135413111911729758L;

    public static final String          CAPTION_REPORT              = "Report";

    public static final String          CAPTION_INCOME_STATEMENT    = "Income Statement";
    public static final String          CAPTION_EXPENSE             = "Expense Report";
    public static final String          CAPTION_REVENUE             = "Revenue Report";
    public static final String          CAPTION_SALES               = "Sales Report";

    public static final EntryField      FIELD_NAME                  = new EntryField(1, "Name", 200);
    public static final EntryField      FIELD_TYPE                  = new EntryField(2, "Type", 255);
    public static final EntryField      FIELD_DESCRIPTION           = new EntryField(3, "Description", 255);
    public static final EntryField      FIELD_DATE_RANGE_TYPE       = new EntryField(4, "Date Range", 255);
    public static final EntryField      FIELD_START_DATE            = new EntryField(5, "Start Date", 20);
    public static final EntryField      FIELD_END_DATE              = new EntryField(6, "End Date", 20);

    public static final String          PROPERTY_TITLE              = "report.title";
    public static final String          PROPERTY_COMPANY_NAME       = "company.name";
    public static final String          PROPERTY_DATE_RANGE         = "report.date_range";

    public String                       name;
    public String                       description;
    public ReportType                   type;

    public DateFilterType               dateFilterType;
    public Date                         fromDate;
    public Date                         toDate;

    private List                        m_categoryList;
    private List                        m_transactionTypeList;

    transient private Hashtable         m_reportProperties;

    /**
     * Default constructor
     */
    public Report()
    {
    }

    /**
     * This constructor takes the Id, name, type and description
     *
     * @param lId - the id for this object
     * @param strName - the name of this report
     * @param reportType - the type for this report
     * @param strDescription - the description of this report
     */
    public Report(long lId, String strName, ReportType reportType, String strDescription)
    {
        initialize();

        setID(lId);
        name = strName;
        description = strDescription;
        type = reportType;
        dateFilterType = DateFilterType.DATE_FILTER_ALL;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        name                    = "";
        description             = "";
        type                    = null;
        dateFilterType          = DateFilterType.DATE_FILTER_ALL;
        fromDate                = null;
        toDate                  = null;
        m_transactionTypeList   = null;
        m_reportProperties      = null;
    }

    /**
     * This method adds a transaction type criterion for the report.
     * This criteria is later used to filter the types of transactions
     * shown in the report.  For example: Income and Expenses only
     * for an Income Statement.
     *
     * @param transactionType - a transaction type
     */
    public void addTransactionType(TransactionType transactionType)
    {
        if (m_transactionTypeList == null) m_transactionTypeList = new ArrayList();

        m_transactionTypeList.add(transactionType);
    }

    /**
     * Retrieves the transaction types that will be used to filter
     * the transactions for the report.
     *
     * @return an array of transaction types
     */
    public TransactionType[] getTransactionTypes()
    {
        if (m_transactionTypeList == null) return (null);

        TransactionType[]       transactionTypes = new TransactionType[m_transactionTypeList.size()];

        m_transactionTypeList.toArray(transactionTypes);

        return (transactionTypes);
    }

    /**
     * Sets the transaction types that will be used to filter
     * the transactions for the report.
     *
     * @param transactionTypes - an array of transaction types
     */
    public void setTransactionTypes(TransactionType[] transactionTypes)
    {
        if (transactionTypes == null)
            m_transactionTypeList = null;
        else
            m_transactionTypeList = new ArrayList(Arrays.asList(transactionTypes));
    }

    /**
     * This method adds a category criterion for the report.
     * This criteria is later used to filter the types of transactions
     * shown in the report.
     *
     * @param category - a category
     */
    public void addCategory(Category category)
    {
        if (m_categoryList == null) m_categoryList = new ArrayList();

        m_categoryList.add(category);
    }

    /**
     * Retrieves the categories that will be used to filter
     * the transactions for the report.
     *
     * @return an array of categories
     */
    public Category[] getCategories()
    {
        if (m_categoryList == null) return (null);

        Category[]       categories = new Category[m_categoryList.size()];

        m_categoryList.toArray(categories);

        return (categories);
    }

    /**
     * Sets the categories that will be used to filter
     * the transactions for the report.
     *
     * @return an array of categories
     */
    public void setCategories(Category[] categories)
    {
        if (categories == null)
            m_categoryList = null;
        else
            m_categoryList = new ArrayList(Arrays.asList(categories));
    }

    public void setProperties(Hashtable reportProperties)
    {
        m_reportProperties = reportProperties;
    }

    /**
     * Retrieves the Hashtable of properties that are
     * associated with this report.
     *
     * @return a Hashtable of properties
     */
    public Hashtable getProperties()
    {
        return (m_reportProperties);
    }

    /**
     * Sets a property to be associated with this report.
     * Properties such as the report title, comany name,
     * etc. are used to display this information in the
     * header for the report.
     *
     * @param strKey - the key for the property
     * @param value - the value for the property
     */
    public void setProperty(String strKey, Object value)
    {
        if (m_reportProperties == null)
            m_reportProperties = new Hashtable();

        m_reportProperties.put(strKey, value);
    }

    /**
     * Retrieves a property associated with this report.
     * We take the key and look it up in our internal
     * Hashtable.
     *
     * @param strKey - the key for the property
     * @return the value for the key
     */
    public Object getProperty(String strKey)
    {
        if (m_reportProperties == null)
            return (null);

        return (m_reportProperties.get(strKey));
    }

    /**
     * Retrieves a property associated with this report.
     * We take the key and look it up in our internal
     * Hashtable.
     *
     * @param strKey - the key for the property
     * @return the value for the key
     */
    public String getReportDateRangeDescription(String strDateFormat)
    {
        String      strDescription = "";

        if (dateFilterType == null) return (strDescription);

        if (dateFilterType.equals(DateFilterType.DATE_FILTER_CUSTOM))
        {
            SimpleDateFormat        dateFormatter = new SimpleDateFormat(strDateFormat);

            strDescription = dateFormatter.format(fromDate) +
                             " to " +
                            dateFormatter.format(toDate);
        }
        else
        {
            strDescription = dateFilterType.description;
        }

        return (strDescription);
    }

    /**
     * This method sets the id for this report.
     * For this type of object, the id is
     * represented as a long integer.
     *
     * @param lId - the id for this report
     */
    public void setID(long lId)
    {
        setOID(new Long(lId));
    }

    /**
     * Retrieves the id for this report as a
     * long integer.
     *
     * @return the id for this company type
     */
    public long getID()
    {
        Long        longID = (Long) getOID();

        if (longID == null)
            return (0L);

        return (longID.longValue());
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(name, FIELD_NAME.caption);
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
        if (!(obj instanceof Report)) return false;

        Report      comparison = (Report) obj;

        if ((comparison.getOID() == null || ((Long) comparison.getOID()).longValue() == 0) &&
            this.getOID() == null || ((Long) this.getOID()).longValue() == 0)
        {
            return (super.equals(obj));
        }

        return (comparison.getOID().equals(this.getOID()));
    }

    /**
     * Returns the String representation of this object.
     * In this case, the report name.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (name == null ? "" : name);
    }
}
