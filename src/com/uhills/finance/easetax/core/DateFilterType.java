package com.uhills.finance.easetax.core;

/**
 * This class represents the concept of a date filter type
 * our application.  This is used to give the user
 * pre-set choices for a date range for the transactions
 * they are viewing or reporting on.  For example, there are
 * pre-set date ranges for:
 *
 * - Year to Date
 * - Month to Date
 * - Past Year
 * - Past 6 Months
 *
 * In addition, there is a "custom" DateFilterType that
 * allows the user to choose the from and to date range
 * themselves
 *
 * It is derived from a base object which contains basic
 * validation functionality.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;
import com.uhills.util.validation.*;

public class DateFilterType extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long               serialVersionUID    = 7897646342865399497L;

    public static final String              CAPTION_TYPE        = "Date Filter";

    public static final DateFilterType      DATE_FILTER_ALL     = new DateFilterType(1, "All Dates");
    public static final DateFilterType      DATE_FILTER_YTD     = new DateFilterType(2, "Year-to-date");
    public static final DateFilterType      DATE_FILTER_MTD     = new DateFilterType(3, "Month-to-date");
    public static final DateFilterType      DATE_FILTER_LYR     = new DateFilterType(4, "Last Year");
    public static final DateFilterType      DATE_FILTER_LMO     = new DateFilterType(5, "Last Month");
    public static final DateFilterType      DATE_FILTER_3MO     = new DateFilterType(6, "Past 3 months");
    public static final DateFilterType      DATE_FILTER_6MO     = new DateFilterType(7, "Past 6 months");
    public static final DateFilterType      DATE_FILTER_1YR     = new DateFilterType(8, "Past year");
    public static final DateFilterType      DATE_FILTER_2YR     = new DateFilterType(9, "Past 2 years");

    public static final DateFilterType      DATE_FILTER_CUSTOM  = new DateFilterType(999, "Custom");

    private static final DateFilterType[]   m_dateFilterTypes =
    {
        DATE_FILTER_ALL,
        DATE_FILTER_YTD,
        DATE_FILTER_MTD,
        DATE_FILTER_LYR,
        DATE_FILTER_LMO,
        DATE_FILTER_3MO,
        DATE_FILTER_6MO,
        DATE_FILTER_1YR,
        DATE_FILTER_2YR
    };

    public String                   type;
    public String                   description;

    public DateFilterType(int iId, String strDescription)
    {
        setID(iId);
        type = strDescription;
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
     * This method examines the date filter type
     * and is able to compute the "from" date
     * relative to the current date.
     *
     * @return the id for this report type
     */
    public Date getFromDate()
    {
        Calendar    calendar = getCalendar();

        if (this.equals(DATE_FILTER_YTD))
        {
//          calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_YEAR, 1);
        }
        else if (this.equals(DATE_FILTER_MTD))
        {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        else if (this.equals(DATE_FILTER_LYR))
        {
            calendar.set(Calendar.DAY_OF_YEAR, 1);
            calendar.add(Calendar.YEAR, -1);
        }
        else if (this.equals(DATE_FILTER_LMO))
        {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, -1);
        }
        else if (this.equals(DATE_FILTER_3MO))
        {
            calendar.add(Calendar.MONTH, -3);
        }
        else if (this.equals(DATE_FILTER_6MO))
        {
            calendar.add(Calendar.MONTH, -6);
        }
        else if (this.equals(DATE_FILTER_1YR))
        {
            calendar.add(Calendar.YEAR, -1);
        }
        else if (this.equals(DATE_FILTER_2YR))
        {
            calendar.add(Calendar.YEAR, -2);
        }
        else
        {
            return (null);
        }

        return (calendar.getTime());
    }

    /**
     * This method examines the date filter type
     * and is able to compute the "to" date
     * relative to the current date.
     *
     * @return the id for this report type
     */
    public Date getToDate()
    {
        Calendar    calendar = getCalendar();

        if (this.equals(DATE_FILTER_LYR))
        {
            calendar.set(Calendar.DAY_OF_YEAR, 1);
            calendar.add(Calendar.DATE, -1);
        }
        else if (this.equals(DATE_FILTER_LMO))
        {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        }
        else
        {
            return (null);
        }
        
        return (calendar.getTime());
    }

    /**
     * This convenience method gets the instance
     * of the default calendar and sets the
     * time fields to zeros.  This allows for
     * accurrate date comparisons without the time
     * fields "getting in the way".
     *
     * @return the id for this report type
     */
    private Calendar getCalendar()
    {
        Calendar            calendar = Calendar.getInstance();

        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        return (calendar);
    }

    /**
     * Retrieves the set of valid report types
     * as an array.
     *
     * @return the set of valid report types.
     */
    public static DateFilterType[] getDateFilterTypes()
    {
        return (m_dateFilterTypes);
    }

    /**
     * Given a type id, this method finds the
     * ReportType object associated with that id.
     *
     * @return the set of valid report types.
     */
    public static DateFilterType findType(int iTypeId)
    {
        for (int i=0; i < m_dateFilterTypes.length; i++)
        {
            if (m_dateFilterTypes[i].getID() == iTypeId)
                return (m_dateFilterTypes[i]);
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
        if (!(obj instanceof DateFilterType)) return false;

        DateFilterType      comparison = (DateFilterType) obj;

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
