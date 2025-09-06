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
public class ReportComparator implements Comparator
{
    private SortCriteria[]          m_sortCriteria;

    public ReportComparator(SortCriteria[] criteria)
    {
        m_sortCriteria = criteria;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object obj1, Object obj2)
    {
        Report              report1;
        Report              report2;

        int                 i, iSortField;
        int                 iReturnCode = 0;

        for (i=0; i < m_sortCriteria.length && iReturnCode == 0; i++)
        {
            if (m_sortCriteria[i].getDirection() == SortCriteria.DESCENDING)
            {
                report1 = (Report) obj2;
                report2 = (Report) obj1;
            }
            else
            {
                report1 = (Report) obj1;
                report2 = (Report) obj2;
            }

            iSortField = m_sortCriteria[i].getField();

            if (iSortField == Report.FIELD_NAME.id)
                iReturnCode = report1.name.compareToIgnoreCase(report2.name);
            else if (iSortField == Report.FIELD_TYPE.id)
                iReturnCode = report1.type.toString().compareTo(report2.type.toString());
            else if (iSortField == Report.FIELD_DESCRIPTION.id)
                iReturnCode = report1.description.compareToIgnoreCase(report2.description);
        }

        return (iReturnCode);
    }
}
