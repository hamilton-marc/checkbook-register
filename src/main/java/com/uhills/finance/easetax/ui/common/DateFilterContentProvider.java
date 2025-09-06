/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

import java.util.*;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateFilterContentProvider implements IStructuredContentProvider
{
    public Object[] getElements(Object inputElement)
    {
        Boolean                 boolIncludeAllDates = (Boolean) inputElement;
        DateFilterType[]        dateFilters = DateFilterType.getDateFilterTypes();
        List                    filterList = new ArrayList();

        for (int i=0; i < dateFilters.length; i++)
        {
            if (!boolIncludeAllDates.booleanValue() &&
                dateFilters[i].equals(DateFilterType.DATE_FILTER_ALL))
            {
                continue;
            }

            filterList.add(dateFilters[i]);
        }

        return (filterList.toArray());
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose()
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }

}
