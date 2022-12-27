/*
 * Created on Sep 19, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.filtersort;

import java.util.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DisplayCriteria
{
    private List        m_filterCriteriaList;
    private List        m_sortCriteriaList;

    public DisplayCriteria()
    {
    }

    public DisplayCriteria(FilterCriteria[] filterCriteriaArray, SortCriteria[] sortCriteriaArray)
    {
        setFilterCriteria(filterCriteriaArray);
        setSortCriteria(sortCriteriaArray);
    }

    public void setFilterCriteria(FilterCriteria[] filterCriteriaArray)
    {
        if (filterCriteriaArray != null)
            m_filterCriteriaList = Arrays.asList(filterCriteriaArray);
        else
            m_filterCriteriaList = null;
    }

    public void setSortCriteria(SortCriteria[] sortCriteriaArray)
    {
        if (sortCriteriaArray != null)
            m_sortCriteriaList = Arrays.asList(sortCriteriaArray);
        else
            m_sortCriteriaList = null;
    }

    public void addFilterCriteria(FilterCriteria filterCriteria)
    {
        if (m_filterCriteriaList == null)
            m_filterCriteriaList = new Vector();

        m_filterCriteriaList.add(filterCriteria);
    }

    public void addSortCriteria(SortCriteria sortCriteria)
    {
        if (m_sortCriteriaList == null)
            m_sortCriteriaList = new Vector();

        m_sortCriteriaList.add(sortCriteria);
    }

    public FilterCriteria[] getFilterCriteria()
    {
        if (m_filterCriteriaList == null)
            return (null);

        return ((FilterCriteria[]) m_filterCriteriaList.toArray());
    }

    public SortCriteria[] getSortCriteria()
    {
        if (m_sortCriteriaList == null)
            return (null);

        return ((SortCriteria[]) m_sortCriteriaList.toArray());
    }
}
