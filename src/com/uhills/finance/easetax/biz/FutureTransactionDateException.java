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

import java.util.Date;

public class FutureTransactionDateException extends Exception
{
    private Date        m_transactionDate;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param strMessage - the error message
     */
    public FutureTransactionDateException(String strMessage, Date transactionDate)
    {
        super(strMessage);

        m_transactionDate = transactionDate;
    }

    /**
     * Retrieves the name of the new contact.
     *
     * @return the contact name
     */
    public Date getTransactionDate()
    {
        return (m_transactionDate);
    }
}
