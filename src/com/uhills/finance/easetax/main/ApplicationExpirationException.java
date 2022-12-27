package com.uhills.finance.easetax.main;

/**
 * This exception is used to denote the fact that
 * the use of this software program has expired.
 * This is primarily used in the functionality to
 * prevent beta test users from continuing to use
 * the software past the beta period.
 *
 * @author Marc Hamilton
 * @date   January 31, 2004
 *
 */

import java.util.Date;

public class ApplicationExpirationException extends Exception
{
    private Date        m_expirationDate;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param expirationDate - the expiration date
     */
    public ApplicationExpirationException(Date expirationDate)
    {
        super("As of " + expirationDate + ", this software has expired.");

        m_expirationDate = expirationDate;
    }

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param expirationDate - the expiration date
     */
    public ApplicationExpirationException(String strMessage, Date expirationDate)
    {
        super(strMessage);

        m_expirationDate = expirationDate;
    }

    /**
     * Retrieves the name of the new contact.
     *
     * @return the contact name
     */
    public Date getExpirationDate()
    {
        return (m_expirationDate);
    }
}
