package com.uhills.util.validation;

/**
 * Generic class for use in throwing exceptions for
 * invalid data entry.  An optional "help context"
 * field exists for providing more help to the user
 * to resolve the issue.
 *
 * @author Marc Hamilton
 * @date   July 3, 2001
 *
 */

public class ValidationException extends Exception
{
    private String      m_strHelpContext;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param strMessage - the error message
     */
    public ValidationException(String strMessage)
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
    public ValidationException(String strMessage, String strHelpContext)
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
