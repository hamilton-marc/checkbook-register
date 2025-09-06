package com.uhills.finance.easetax.core;

/**
 * This class provides generic routines for the base
 * object used in the Promotobot.  The base objects are
 * no more than glorified "structs" (from C++).
 * The methods for these objects are used mostly for
 * formatting and validation.
 *
 * @author Marc Hamilton
 * @date   July 24, 2003
 *
 */

import java.util.Date;
import java.io.Serializable;
import java.text.*;

import com.uhills.util.validation.*;

public abstract class EaseTaxObject implements Serializable
{
    private transient GenericValidator        m_validator;
    private transient InternetValidator       m_internetValidator;

    public abstract void initialize();
    public abstract void validate() throws ValidationException;

    private Object                  m_objectId;

    public Object getOID()
    {
        return (m_objectId);
    }

    protected void setOID(Object obj)
    {
        m_objectId = obj;
    }

    /**
     * Initializes our internal "validator" object and
     * returns it to the calling function.  This is
     * used to perform basic routine validation on
     * user input.
     *
     * @return - a GenericValidator object
     */
    protected GenericValidator getValidator()
    {
        if (m_validator == null)
            m_validator = new GenericValidator();

        return (m_validator);
    }

    /**
     * Initializes our internal "Internet validator" object and
     * returns it to the calling function.  This is
     * used to perform validation on user input items
     * such as E-mail addresses and URLs.
     *
     * @return - an InternetValidator object
     */
    protected InternetValidator getInternetValidator()
    {
        if (m_internetValidator == null)
            m_internetValidator = new InternetValidator();

        return (m_internetValidator);
    }

    /**
     * Formats a date according the the format string.
     *
     * @param strFormat - format string
     */
    protected String formatDate(Date theDate, String strFormat)
    {
        String              strFormattedDate = "";

        if (strFormat == null ||
            strFormat.length() == 0)
        {
            strFormat = "MM/dd/yyyy";
        }

        if (theDate != null)
        {
            SimpleDateFormat    dateFormatter = new SimpleDateFormat(strFormat);

            strFormattedDate = dateFormatter.format(theDate);
        }

        return (strFormattedDate);
    }

    /**
     * Formats currencies with 2 decimal points and a
     * comma for the thousands separator.
     *
     * @param strFormat - format string
     */
    protected String formatCurrency(double dNumber)
    {
        DecimalFormat       decimalFormatter = new DecimalFormat("#,##0.00");

        return (decimalFormatter.format(dNumber));
    }
}
