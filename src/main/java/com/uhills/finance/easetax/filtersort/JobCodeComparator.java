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
public class JobCodeComparator implements Comparator
{
    private SortCriteria[]          m_sortCriteria;

    public JobCodeComparator(SortCriteria[] criteria)
    {
        m_sortCriteria = criteria;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object obj1, Object obj2)
    {
        JobCode             jobCode1;
        JobCode             jobCode2;

        int                 i, iSortField;
        int                 iReturnCode = 0;

        for (i=0; i < m_sortCriteria.length && iReturnCode == 0; i++)
        {
            if (m_sortCriteria[i].getDirection() == SortCriteria.DESCENDING)
            {
                jobCode1 = (JobCode) obj2;
                jobCode2 = (JobCode) obj1;
            }
            else
            {
                jobCode1 = (JobCode) obj1;
                jobCode2 = (JobCode) obj2;
            }

            iSortField = m_sortCriteria[i].getField();

            if (iSortField == JobCode.FIELD_CODE.id)
                iReturnCode = jobCode1.code.compareToIgnoreCase(jobCode2.code);
        }

        return (iReturnCode);
    }
}
