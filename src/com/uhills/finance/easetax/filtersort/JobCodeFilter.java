/*
 * Created on June 23, 2006
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
public class JobCodeFilter implements IFilter
{
    private FilterCriteria[]            m_filterCriteria;
    private StringFilterComparator      m_stringComparator;

    public JobCodeFilter(FilterCriteria[] criteria)
    {
        m_filterCriteria = criteria;
        m_stringComparator = new StringFilterComparator();
    }

    /* (non-Javadoc)
     * @see com.uhills.util.collections.IFilter#include(java.lang.Object)
     */
    public boolean include(Object obj)
    {
        JobCode                 jobCode = (JobCode) obj;
        boolean                 bInclude = true;
        int                     iFilterField;

        for (int i=0; i < m_filterCriteria.length && bInclude; i++)
        {
            if (m_filterCriteria[i] == null) continue;

            iFilterField = m_filterCriteria[i].getField();

            if (iFilterField == JobCode.FIELD_CODE.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], new Integer(jobCode.code));
            else if (iFilterField == JobCode.FIELD_DESCRIPTION.id)
                bInclude = m_stringComparator.meetsCriteria(m_filterCriteria[i], jobCode.description);
        }

        return (bInclude);
    }

}
