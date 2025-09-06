/*
 * Created on Sep 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.filtersort;

import java.util.Comparator;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TransactionComparator implements Comparator
{
    private SortCriteria[]          m_sortCriteria;

    public TransactionComparator(SortCriteria[] criteria)
    {
        m_sortCriteria = criteria;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object obj1, Object obj2)
    {
        Transaction         trans1;
        Transaction         trans2;

        int                 i, iSortField;
        int                 iReturnCode = 0;

        for (i=0; i < m_sortCriteria.length && iReturnCode == 0; i++)
        {
            if (m_sortCriteria[i].getDirection() == SortCriteria.DESCENDING)
            {
                trans1 = (Transaction) obj2;
                trans2 = (Transaction) obj1;
            }
            else
            {
                trans1 = (Transaction) obj1;
                trans2 = (Transaction) obj2;
            }

            iSortField = m_sortCriteria[i].getField();

            if (iSortField == Transaction.FIELD_CATEGORY.id)
                iReturnCode = trans1.getCategory().toString().compareToIgnoreCase(trans2.getCategory().toString());
            else if (iSortField == Transaction.FIELD_CONTACT.id)
                iReturnCode = trans1.getContact().toString().compareToIgnoreCase(trans2.getContact().toString());
            else if (iSortField == Transaction.FIELD_DATE.id)
                iReturnCode = trans1.transactionDate.compareTo(trans2.transactionDate);
            else if (iSortField == Transaction.FIELD_NUMBER_CODE.id)
                iReturnCode = trans1.numberCode.compareToIgnoreCase(trans2.numberCode);
            else if (iSortField == Transaction.FIELD_TYPE.id)
                iReturnCode = trans1.getType().toString().compareToIgnoreCase(trans2.getType().toString());
            else if (iSortField == Transaction.FIELD_AMOUNT.id)
                iReturnCode = new Double(trans1.amount).compareTo(new Double(trans2.amount));
            else if (iSortField == Transaction.FIELD_JOB_CODE.id)
                iReturnCode = trans1.getJobCode().toString().compareToIgnoreCase(trans2.getJobCode().toString());
            else if (iSortField == Transaction.FIELD_DESCRIPTION.id)
                iReturnCode = trans1.description.compareToIgnoreCase(trans2.description);
        }

        return (iReturnCode);
    }

}
