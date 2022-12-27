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

public class ReportType extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID = 417336433392067889L;

    public static final String          CAPTION_TYPE        = "Report Type";

    public static final ReportType      INCOME_STATEMENT                    = new ReportType(1, "Income Statement", "Income Statement", "IncomeStatementReport.xml");
    public static final ReportType      DETAILED_INCOME_STATEMENT           = new ReportType(2, "Detailed Income Statement", "Detailed Income Statement", "DetailedIncomeStatementReport.xml");
    public static final ReportType      DETAILED_INCOME_STATEMENT_WITH_MEMO = new ReportType(3, "Detailed Income Statement with Memo", "Detailed Income Statement with Memo", "DetailedIncomeStatementWithMemoReport.xml");
    public static final ReportType      DETAILED_EQUITY_STATEMENT           = new ReportType(4, "Detailed Equity Statement", "Detailed Equity Statement", "DetailedEquityStatementReport.xml");
    public static final ReportType      TAX_PREPARATION                     = new ReportType(5, "Tax Preparation Report", "Tax Preparation Report", "TaxPreparationReport.xml");

    private static final ReportType[]   m_reportTypes =
    {
        INCOME_STATEMENT,
        DETAILED_INCOME_STATEMENT,
        DETAILED_INCOME_STATEMENT_WITH_MEMO,
        DETAILED_EQUITY_STATEMENT
//      TAX_PREPARATION
    };

    public String                   type;
    public String                   description;
    public String                   template;

    /**
     * This constructor takes the Id, type, description and template file location
     *
     * @param lId - the id for this object
     * @param strType - the type for this report
     * @param strDescription - the description of this report
     * @param strTemplate - the template file name
     */
    public ReportType(int iId, String strType, String strDescription, String strTemplate)
    {
        setID(iId);
        type = strType;
        description = strDescription;
        template = strTemplate;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        type                = "";
        description         = "";
        template            = "";
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
    public static ReportType[] getReportTypes()
    {
        return (m_reportTypes);
    }

    /**
     * Given a type id, this method finds the
     * ReportType object associated with that id.
     *
     * @return the set of valid report types.
     */
    public static ReportType findType(int iTypeId)
    {
        for (int i=0; i < m_reportTypes.length; i++)
        {
            if (m_reportTypes[i].getID() == iTypeId)
                return (m_reportTypes[i]);
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
        if (!(obj instanceof ReportType)) return false;

        ReportType     comparison = (ReportType) obj;

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
