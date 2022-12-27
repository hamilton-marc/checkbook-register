package com.uhills.finance.easetax.biz;

/**
 * This exception is used when the user attempts to
 * delete or change a Category that is in use in a
 * at least 1 transaction.
 *
 * @author Marc Hamilton
 * @date   January 24, 2004
 *
 */
public class CategoryInUseException extends Exception
{
    private int          	m_iCategoryNumber;

    /**
     * Main constructor for this class.  It takes
     * an error message as input and simply calls
     * the default Exception constructor.
     *
     * @param strMessage - the error message
     */
    public CategoryInUseException(String strMessage, int iCategoryNumber)
    {
        super(strMessage);

        m_iCategoryNumber = iCategoryNumber;
    }

    /**
     * Retrieves the number of the Category in question.
     *
     * @return the category number
     */
    public int getCategoryNumber()
    {
        return (m_iCategoryNumber);
    }
}
