package com.uhills.finance.easetax.core;

/**
 * This class represents some type of geographical address such as
 * a billing or mailing address.  It includes all of the standard
 * fields that an address would have including a country field for
 * International customers.
 *
 * @author Marc Hamilton
 * @date   July 24, 2003
 *
 */

import com.uhills.util.validation.*;

public class Address extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long       serialVersionUID            = -5628996440593203446L;

    public static final int         NUM_STREET_LINES            = 2;

    public static final String      COUNTRY_UNITED_STATES       = "United States";

    public static final EntryField  FIELD_STREET                = new EntryField(100, "Street", 50);
    public static final EntryField  FIELD_CITY                  = new EntryField(101, "City", 50);
    public static final EntryField  FIELD_STATE_PROVINCE        = new EntryField(102, "State/Province", 50);
    public static final EntryField  FIELD_ZIP_POSTAL            = new EntryField(103, "Zip/Postal Code", 30);
    public static final EntryField  FIELD_COUNTRY               = new EntryField(104, "Country", 100);
    public static final EntryField  FIELD_PHONE                 = new EntryField(105, "Phone", 30);
    public static final EntryField  FIELD_FAX                   = new EntryField(106, "Fax", 30);
    public static final EntryField  FIELD_EMAIL                 = new EntryField(107, "E-Mail", 150);
    public static final EntryField  FIELD_WEBSITE               = new EntryField(108, "Web Site", 255);

    public String                   street[] = new String[NUM_STREET_LINES];
    public String                   city;
    public String                   stateProvince;
    public String                   zipPostal;
    public String                   country;
    public String                   phone;
    public String                   fax;
    public String                   email;
    public String                   webSite;

    public void initialize()
    {
        for (int i=0; i < street.length; i++)
        {
            street[i]   = "";
        }

        city            = "";
        stateProvince   = "";
        zipPostal       = "";
        country         = "";
        phone           = "";
        fax             = "";
        email           = "";
        webSite         = "";
    }

    /**
     * Formats a multi-line street into a
     * readable string.
     *
     */
    public String formatStreet()
    {
        StringBuffer        strbufStreet = new StringBuffer(street[0]);

        if (street[1].length() > 0)
        {
            strbufStreet.append("\n");
            strbufStreet.append(street[1]);
        }

        return (strbufStreet.toString());
    }

    /**
     * Validates the street portion of the address.
     *
     */
    public void validateStreet() throws ValidationException
    {
        getValidator().exists(street[0], FIELD_STREET.caption);
    }

    /**
     * Validates the city portion of the address.
     *
     */
    public void validateCity() throws ValidationException
    {
        getValidator().exists(city, FIELD_CITY.caption);
    }

    /**
     * Validates the state/province portion of the address.
     *
     */
    public void validateStateProvince() throws ValidationException
    {
        getValidator().exists(stateProvince, FIELD_STATE_PROVINCE.caption);
    }

    /**
     * Validates the zip code portion of the address.
     *
     */
    public void validateZipPostal() throws ValidationException
    {
        getValidator().exists(zipPostal, FIELD_ZIP_POSTAL.caption);
    }

    /**
     * Validates the country portion of the address.
     *
     */
    public void validateCountry() throws ValidationException
    {
        try
        {
            getValidator().exists(country, FIELD_COUNTRY.caption);
        }
        catch (ValidationException ex)
        {
            throw new ValidationException ("Please choose a " + FIELD_COUNTRY.caption + " from the list");
        }
    }

    /**
     * Validates the day time phone number.
     *
     */
    public void validatePhone() throws ValidationException
    {
        getValidator().exists(phone, FIELD_PHONE.caption);
    }

    /**
     * This method validates the fields to ensure we
     * have a valid address.
     *
     */
    public void validate() throws ValidationException
    {
/*
        validateStreet();
        validateCity();
        validateStateProvince();
        validateZipPostal();
        validateCountry();
*/
    }

    /**
     * Checks to see if this is an "empty" address.
     * We test all of the fields to see if they
     * are empty Strings.
     *
     * @return true if this address is empty
     */
    public boolean isEmpty()
    {
        long        lTotalStringLength = 0;

        for (int i=0; i < street.length; i++)
        {
            lTotalStringLength += street[i].trim().length();
        }

        lTotalStringLength += city.trim().length();
        lTotalStringLength += stateProvince.trim().length();
        lTotalStringLength += zipPostal.trim().length();
        lTotalStringLength += country.trim().length();
        lTotalStringLength += phone.trim().length();
        lTotalStringLength += fax.trim().length();
        lTotalStringLength += email.trim().length();
        lTotalStringLength += webSite.trim().length();

        return (lTotalStringLength == 0);
    }
}
