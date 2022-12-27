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
public class DefaultFilterComparator implements IFilterComparator
{
    protected int compareTo(Comparable item, Object value)
    {
        return (item.compareTo(value));
    }

    protected boolean equals(Comparable item, Object value)
    {
        return (item.equals(value));
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.searchsort.ISearchComparator#meetsCriteria(com.uhills.finance.easetax.searchsort.SearchCriteria, java.lang.Object)
     */
    public boolean meetsCriteria(FilterCriteria criteria, Object obj) throws UnsupportedOperationException
    {
        Comparable      item = (Comparable) obj;
        boolean         bMeetsCriteria = false;

        switch (criteria.getOperator())
        {
            case FilterCriteria.ANY:
                bMeetsCriteria = true;
                break;

            case FilterCriteria.EQUALS:
                bMeetsCriteria = equals(item, criteria.getValue());
                break;

            case FilterCriteria.LESS:
                bMeetsCriteria = (compareTo(item, criteria.getValue()) < 0);
                break;

            case FilterCriteria.GREATER:
                bMeetsCriteria = (compareTo(item, criteria.getValue()) > 0);
                break;

            case FilterCriteria.LESS_EQUAL:
                bMeetsCriteria = (compareTo(item, criteria.getValue()) <= 0);
                break;

            case FilterCriteria.GREATER_EQUAL:
                bMeetsCriteria = (compareTo(item, criteria.getValue()) >= 0);
                break;

            case FilterCriteria.BETWEEN:
                bMeetsCriteria = (compareTo(item, criteria.getValue()) >= 0) &&
                                 (compareTo(item, criteria.getMaxValue()) <= 0);
                break;

            case FilterCriteria.IN_SET:
                Object[] values = criteria.getValues();

                for (int i=0; i < values.length; i++)
                {
                    bMeetsCriteria = equals(item, values[i]);

                    if (bMeetsCriteria)
                        break;
                }

                break;

            default:
                throw new UnsupportedOperationException("This operation is not supported");
        }

        if (criteria.getIsNegated())
            bMeetsCriteria = !bMeetsCriteria;

        return (bMeetsCriteria);
    }

}
