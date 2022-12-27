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
public class CategoryComparator implements Comparator
{
    private SortCriteria[]          m_sortCriteria;

    public CategoryComparator(SortCriteria[] criteria)
    {
        m_sortCriteria = criteria;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object obj1, Object obj2)
    {
        Category            category1;
        Category            category2;

        int                 i, iSortField;
        int                 iReturnCode = 0;

        for (i=0; i < m_sortCriteria.length && iReturnCode == 0; i++)
        {
            if (m_sortCriteria[i].getDirection() == SortCriteria.DESCENDING)
            {
                category1 = (Category) obj2;
                category2 = (Category) obj1;
            }
            else
            {
                category1 = (Category) obj1;
                category2 = (Category) obj2;
            }

            iSortField = m_sortCriteria[i].getField();
            
            if (iSortField == Category.FIELD_NAME.id)
                iReturnCode = category1.name.compareToIgnoreCase(category2.name);
            else if (iSortField == Category.FIELD_NUMBER.id)
                iReturnCode = new Integer(category1.number).compareTo(new Integer(category2.number));
            else if (iSortField == Category.FIELD_TYPE.id)
                iReturnCode = category1.getType().toString().compareToIgnoreCase(category2.getType().toString());
        }

        return (iReturnCode);
    }
}
