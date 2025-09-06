/*
 * Created on Sep 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.filtersort;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StringFilterComparator extends DefaultFilterComparator
{
    protected int compareTo(Comparable item, Object value)
    {
        return (item.toString().compareToIgnoreCase(value.toString()));
    }

    protected boolean equals(Comparable item, Object value)
    {
        return (item.toString().equalsIgnoreCase(value.toString()));
    }

    public boolean meetsCriteria(FilterCriteria criteria, Object obj) throws UnsupportedOperationException
    {
        String          strItem = obj.toString();

        if (criteria.getOperator() != FilterCriteria.CONTAINS &&
            criteria.getOperator() != FilterCriteria.STARTS_WITH)
        {
            return (super.meetsCriteria(criteria, strItem));
        }

        boolean         bMeetsCriteria = false;

        switch (criteria.getOperator())
        {
            case FilterCriteria.CONTAINS:
                bMeetsCriteria = (strItem.toUpperCase().indexOf(criteria.getValue().toString().toUpperCase()) >= 0);
                break;

            case FilterCriteria.STARTS_WITH:
                bMeetsCriteria = strItem.toUpperCase().startsWith(criteria.getValue().toString().toUpperCase());
                break;
        }

        return (bMeetsCriteria);
    }
}
