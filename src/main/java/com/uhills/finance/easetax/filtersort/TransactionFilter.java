/*
 * Created on Sep 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.filtersort;

import com.uhills.util.collections.IFilter;
import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TransactionFilter implements IFilter
{
    private FilterCriteria[]            m_filterCriteria;
    private DefaultFilterComparator     m_defaultComparator;
    private StringFilterComparator      m_stringComparator;

    public TransactionFilter(FilterCriteria[] criteria)
    {
        m_filterCriteria = criteria;
        m_defaultComparator = new DefaultFilterComparator();
        m_stringComparator = new StringFilterComparator();
    }

    /* (non-Javadoc)
     * @see com.uhills.util.collections.IFilter#include(java.lang.Object)
     */
    public boolean include(Object obj)
    {
        Transaction             transaction = (Transaction) obj;
        boolean                 bInclude = true;
        int                     iFilterField;

        for (int i=0; i < m_filterCriteria.length && bInclude; i++)
        {
            if (m_filterCriteria[i] == null) continue;

            iFilterField = m_filterCriteria[i].getField();

            if (iFilterField == Transaction.FIELD_CATEGORY.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], transaction.getCategory());
            else if (iFilterField == Transaction.FIELD_CONTACT.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], transaction.getContact());
            else if (iFilterField == Transaction.FIELD_DATE.id)
                bInclude = m_defaultComparator.meetsCriteria(m_filterCriteria[i], transaction.transactionDate);
            else if (iFilterField == Transaction.FIELD_DESCRIPTION.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], transaction.description);
            else if (iFilterField == Transaction.FIELD_NUMBER_CODE.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], transaction.numberCode);
            else if (iFilterField == Transaction.FIELD_TYPE.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], transaction.getType());
            else if (iFilterField == Transaction.FIELD_AMOUNT.id)
                bInclude = m_defaultComparator.meetsCriteria(m_filterCriteria[i], new Double(transaction.amount));
        }

        return (bInclude);
    }

}
