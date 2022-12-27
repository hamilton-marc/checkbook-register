/*
 * Created on Oct 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.contact;

import java.util.*;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.filtersort.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.biz.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContactDataContentProvider implements IDataContentProvider
{
    private DisplayCriteria         m_displayCriteria;

    public ContactDataContentProvider(DisplayCriteria displayCriteria)
    {
        m_displayCriteria = displayCriteria;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getContent()
     */
    public Object[] getContent()
    {
        ContactBusinessLogicBean        contactBean = new ContactBusinessLogicBean();

        try
        {
            Collection                  contactList = contactBean.getContacts(m_displayCriteria);

            return (contactList.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
    }

    public int getColumnCount()
    {
        return (ContactMaintenanceForm.getColumnCount());
    }

    public Class getColumnClass(int iColumnIndex)
    {
        return (String.class);
    }


    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnName(int)
     */
    public String getColumnName(int iColumnIndex)
    {
        String              strText = "";

        if (iColumnIndex == ContactMaintenanceForm.COLUMN_NAME.position)
            strText = ContactMaintenanceForm.COLUMN_NAME.toString();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_TYPE.position)
            strText = ContactMaintenanceForm.COLUMN_TYPE.toString();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_PHONE.position)
            strText = ContactMaintenanceForm.COLUMN_PHONE.toString();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_EMAIL.position)
            strText = ContactMaintenanceForm.COLUMN_EMAIL.toString();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_ACCOUNT.position)
            strText = ContactMaintenanceForm.COLUMN_ACCOUNT.toString();

        return (strText);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnObject(java.lang.Object, int)
     */
    public Object getColumnObject(Object element, int iColumnIndex)
    {
        Contact             contact = (Contact) element;
        Object              value = null;

        if (iColumnIndex == ContactMaintenanceForm.COLUMN_NAME.position)
            value = contact.name;
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_TYPE.position)
            value = contact.getType();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_PHONE.position)
            value = contact.getPhoneNumber();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_EMAIL.position)
            value = contact.getEmail();
        else if (iColumnIndex == ContactMaintenanceForm.COLUMN_ACCOUNT.position)
            value = contact.accountNumber;

        return (value);
    }
}
