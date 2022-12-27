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
public class ContactComparator implements Comparator
{
    private SortCriteria[]          m_sortCriteria;

    public ContactComparator(SortCriteria[] criteria)
    {
        m_sortCriteria = criteria;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object obj1, Object obj2)
    {
        Contact             contact1;
        Contact             contact2;

        int                 iSortField;
        int                 i, iReturnCode = 0;

        for (i=0; i < m_sortCriteria.length && iReturnCode == 0; i++)
        {
            if (m_sortCriteria[i].getDirection() == SortCriteria.DESCENDING)
            {
                contact1 = (Contact) obj2;
                contact2 = (Contact) obj1;
            }
            else
            {
                contact1 = (Contact) obj1;
                contact2 = (Contact) obj2;
            }

            iSortField = m_sortCriteria[i].getField();

            if (iSortField == Contact.FIELD_NAME.id)
                iReturnCode = contact1.name.compareToIgnoreCase(contact2.name);
            else if (iSortField == Contact.FIELD_TYPE.id)
                iReturnCode = contact1.getType().toString().compareToIgnoreCase(contact1.getType().toString());
            else if (iSortField == Address.FIELD_PHONE.id)
                iReturnCode = contact1.getPhoneNumber().compareToIgnoreCase(contact2.getPhoneNumber());
            else if (iSortField == Address.FIELD_EMAIL.id)
                iReturnCode = contact1.getEmail().compareTo(contact2.getEmail());
            else if (iSortField == Contact.FIELD_ACCOUNT_NUM.id)
                iReturnCode = contact1.accountNumber.compareToIgnoreCase(contact2.accountNumber);
        }

        return (iReturnCode);
    }
}
