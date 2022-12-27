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
public class CategoryFilter implements IFilter
{
    private FilterCriteria[]            m_filterCriteria;
//  private DefaultFilterComparator     m_defaultComparator;
    private StringFilterComparator      m_stringComparator;

    public CategoryFilter(FilterCriteria[] criteria)
    {
        m_filterCriteria = criteria;
//      m_defaultComparator = new DefaultFilterComparator();
        m_stringComparator = new StringFilterComparator();
    }

    /* (non-Javadoc)
     * @see com.uhills.util.collections.IFilter#include(java.lang.Object)
     */
    public boolean include(Object obj)
    {
        Category                category = (Category) obj;
        boolean                 bInclude = true;
        int                     iFilterField;

        for (int i=0; i < m_filterCriteria.length && bInclude; i++)
        {
            if (m_filterCriteria[i] == null) continue;

            iFilterField = m_filterCriteria[i].getField();

            if (iFilterField == Category.FIELD_NUMBER.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], new Integer(category.number));
            else if (iFilterField == Category.FIELD_NAME.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], category.name);
            else if (iFilterField == Category.FIELD_DESCRIPTION.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], category.description);
            else if (iFilterField == Category.FIELD_TYPE.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], category.getType());
        }

        return (bInclude);
    }

}
