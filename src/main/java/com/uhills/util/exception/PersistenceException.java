package com.uhills.util.exception;

/**
 * Generic class for use in throwing exceptions for
 * persistence models.  An optional "help context"
 * field exists for providing more help to the user
 * to resolve the issue.
 *
 * @author Marc Hamilton
 * @date   July 3, 2001
 *
 */

public class PersistenceException extends Exception
{
    private String      m_strHelpContext;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param strMessage - the error message
     */
    public PersistenceException(String strMessage)
    {
        super(strMessage);
    }

    /**
     * This version of the constructor calls
     * the main constructor and adds the ability
     * to set our help context field.
     *
     * @param strMessage - the error message
     * @param strHelpContext - help context message
     */
    public PersistenceException(String strMessage, String strHelpContext)
    {
        this(strMessage);
        m_strHelpContext = strHelpContext;
    }

    /**
     * Gets the help context string.
     *
     * @return - help context message
     */
    public String getHelpContext()
    {
        return (m_strHelpContext);
    }
}
