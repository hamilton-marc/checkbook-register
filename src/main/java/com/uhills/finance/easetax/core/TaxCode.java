package com.uhills.finance.easetax.core;

/**
 * This class holds the tax form and line items.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.io.Serializable;

public class TaxCode implements Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long       serialVersionUID = -2119018251303836875L;

    public static final String      FORM_1040   = "1040";
    public static final String      FORM_1120   = "1120";
    public static final String      FORM_1120S  = "1120S";
    public static final String      FORM_1065   = "1065";

    public static final String      TAX_FORMS[] =
    {
        FORM_1040,
        FORM_1120,
        FORM_1120S,
        FORM_1065
    };

    public String                   form;
    public String                   line;

    /**
     * Default constructor
     */
    public TaxCode()
    {
    }

    /**
     * This constructor initializes the TaxCode object
     * with the form name and line number.
     *
     * @param strForm - the form name
     * @param strLine - the line number
     */
    public TaxCode(String strForm, String strLine)
    {
        form = strForm;
        line = strLine;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        form                = "";
        line                = "";
    }
}
