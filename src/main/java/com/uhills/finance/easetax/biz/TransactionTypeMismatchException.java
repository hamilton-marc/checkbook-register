package com.uhills.finance.easetax.biz;

/**
 * This exception is used when a transaction is being
 * recorded and the category (account) does not match
 * the transaction type entered.
 * 
 * Example: User is entering in an "Expense", but
 *          the Category chosen actually corresponds to
 *          a "Revenue" entry.
 *
 * @author Marc Hamilton
 * @date   January 24, 2004
 *
 */

import com.uhills.finance.easetax.core.TransactionType;

public class TransactionTypeMismatchException extends Exception
{
    private TransactionType         m_transactionType;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param strMessage - the error message
     */
    public TransactionTypeMismatchException(String strMessage, TransactionType transactionType)
    {
        super(strMessage);

        m_transactionType = transactionType;
    }

    /**
     * Retrieves the name of the new contact.
     *
     * @return the contact name
     */
    public TransactionType getTransactionType()
    {
        return (m_transactionType);
    }
}
