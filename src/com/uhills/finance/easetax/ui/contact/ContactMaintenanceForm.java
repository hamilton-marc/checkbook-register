/*
 * Created on Jun 26, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.contact;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.window.*;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.biz.*;
import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class ContactMaintenanceForm extends MaintenanceForm
{
    public static final ColumnDefinition        COLUMN_NAME     = new ColumnDefinition(0, Contact.FIELD_NAME.caption, 200, SWT.LEFT, Contact.FIELD_NAME.id);
    public static final ColumnDefinition        COLUMN_TYPE     = new ColumnDefinition(1, Contact.FIELD_TYPE.caption, 100, SWT.LEFT, Contact.FIELD_TYPE.id);
    public static final ColumnDefinition        COLUMN_PHONE    = new ColumnDefinition(2, Address.FIELD_PHONE.caption, 100, SWT.LEFT, Address.FIELD_PHONE.id);
    public static final ColumnDefinition        COLUMN_EMAIL    = new ColumnDefinition(3, Address.FIELD_EMAIL.caption, 150, SWT.LEFT, Address.FIELD_EMAIL.id);
    public static final ColumnDefinition        COLUMN_ACCOUNT  = new ColumnDefinition(4, Contact.FIELD_ACCOUNT_NUM.caption, 150, SWT.LEFT, Contact.FIELD_ACCOUNT_NUM.id);

    private static final ColumnDefinition[]     m_columnArray =
    {
        COLUMN_NAME,
        COLUMN_TYPE,
        COLUMN_PHONE,
        COLUMN_EMAIL,
        COLUMN_ACCOUNT
    };

    private ContactBusinessLogicBean            m_contactBean;

    public ContactMaintenanceForm(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);
    }

    public static int getColumnCount()
    {
        return (m_columnArray.length);
    }

    protected void initializeForm()
    {
        m_contactBean = new ContactBusinessLogicBean();

        setInitialDisplayCriteria();
        super.initializeForm();
    }

    private void setInitialDisplayCriteria()
    {
        SortCriteria[]      sortCriteria = { new SortCriteria(Contact.FIELD_NAME.id) };

        m_displayCriteria = new DisplayCriteria(null, sortCriteria);
    }

    public String getText()
    {
        return (Contact.CAPTION_CONTACT + " List");
    }

    public Image getImage()
    {
        return (ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_CONTACTS));
    }

    protected void createTable()
    {
//      TableColumn         tableColumn;

        super.createTable();

        for (int i = 0; i < m_columnArray.length; i++)
        {
            configureNewTableColumn(m_columnArray[i]);
        }

        m_tableViewer.setContentProvider(new ContactTableContentProvider());
        m_tableViewer.setLabelProvider(new ContactTableLabelProvider());

        refresh();
    }

    protected void createButtonBar()
    {
        super.createButtonBar();

        m_newButton.setText("&New " + Contact.CAPTION_CONTACT);
        m_editButton.setText("&Edit " + Contact.CAPTION_CONTACT);
        m_deleteButton.setText("&Delete " + Contact.CAPTION_CONTACT);
    }

    private Contact getSelectedContact()
    {
        Contact                 selectedContact = null;
        IStructuredSelection    selection = (IStructuredSelection) m_tableViewer.getSelection();

        if (selection != null)
            selectedContact = (Contact) selection.getFirstElement();

        if (selectedContact == null)
        {
            MessageDialog.openError(getShell(), "No Selection", "Please select a " + Contact.CAPTION_CONTACT + " from the list");
        }

        return (selectedContact);
    }

    protected void newEntry()
    {
        ContactDialog      contactDialog = new ContactDialog(getShell());
        int                 iRetCode = contactDialog.newItem();

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    protected void deleteEntry()
    {
        Contact             selectedContact = getSelectedContact();

        if (selectedContact == null)
            return;

        if (!GUIUtils.getDeleteConfirmation(getShell(), Contact.CAPTION_CONTACT))
            return;

        try
        {
            m_contactBean.deleteContact(selectedContact);
            m_tableViewer.refresh();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "No Selection", ex.getMessage());
        }
    }

    protected void editEntry()
    {
        Contact             selectedContact = getSelectedContact();

        if (selectedContact == null)
            return;

        ContactDialog       contactDialog = new ContactDialog(getShell());
        int                 iRetCode = contactDialog.openItem(selectedContact);

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }
}
