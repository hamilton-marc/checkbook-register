package com.uhills.finance.easetax.biz;

/**
 * This exception is used when a transaction is attempted
 * to be recorded and the contact does not yet exist in
 * the database.
 *
 * @author Marc Hamilton
 * @date   January 24, 2004
 *
 */
public class NewContactException extends Exception
{
    private String          m_strContactName;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param strMessage - the error message
     */
    public NewContactException(String strMessage, String strNewContactName)
    {
        super(strMessage);

        m_strContactName = strNewContactName;
    }

    /**
     * Retrieves the name of the new contact.
     *
     * @return the contact name
     */
    public String getContactName()
    {
        return (m_strContactName);
    }
}
