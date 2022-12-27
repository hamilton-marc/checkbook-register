/*
 * Created on Jul 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.contact;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

import com.uhills.util.exception.*;
import com.uhills.util.validation.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.biz.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class ContactDialog extends TitleAreaDialog
{
/*
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_LABEL_WIDTH = 90;
*/
    private TabFolder                       m_tabFolder;
    private TabItem                         m_generalInfoTab;
    private TabItem                         m_detailInfoTab;
    private ContactGeneralInfoComposite     m_generalInfoPage;
    private ContactDetailInfoComposite      m_detailInfoPage;

    private ContactBusinessLogicBean        m_contactBean;
    private Contact                         m_contact;
    private boolean                         m_bFillFields;

    /**
     * @param parentShell
     */
    public ContactDialog(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
        m_contactBean = new ContactBusinessLogicBean();
    }

    public int newItem()
    {
        return (open());
    }

    public int openItem(Object entry)
    {
        if (entry == null) return (CANCEL);

        Contact     contact = (Contact) entry;

        return (openItem(contact.getID()));
    }

    public int openItem(long lContactId)
    {
        m_bFillFields = true;

        try
        {
            getContact(lContactId);
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
            return (CANCEL);
        }
        catch (ItemNotFoundException ex)
        {
            MessageDialog.openError(getParentShell(), "Contact Entry", ex.getMessage());
            return (CANCEL);
        }

        return (open());
    }

    private void getContact(long lContactId) throws PersistenceException, ItemNotFoundException
    {
        m_contact = m_contactBean.getContact(lContactId);

        if (m_contact == null)
        {
            throw new ItemNotFoundException("Unable to find that contact in the database.  " +
                                            "It may have already been deleted");
        }
    }

    private void fillFields()
    {
        m_generalInfoPage.fillFields(m_contact);
        m_detailInfoPage.fillFields(m_contact);
    }

    private Contact getContactFromForm() throws ValidationException
    {
        Contact         contact = new Contact();

        contact.initialize();

        if (m_contact != null)
        {
            contact.setID(m_contact.getID());
        }

        m_generalInfoPage.fillObject(contact);
        m_detailInfoPage.fillObject(contact);

        return (contact);
    }

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText("Contact Entry");
    }

    protected Control createDialogArea(Composite parent)
    {
        String              strTitle, strMessage;
        Composite           dialogContainer = (Composite) super.createDialogArea(parent);
        Composite           container = new Composite(dialogContainer, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout();
        GridData            gridData;

        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 8;
        gridLayout.horizontalSpacing = 5;
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        gridData = new GridData(GridData.FILL_BOTH);
        m_tabFolder = new TabFolder(container, SWT.TOP);
        m_tabFolder.setLayoutData(gridData);

        m_generalInfoPage = new ContactGeneralInfoComposite(m_tabFolder);
        m_generalInfoTab = new TabItem(m_tabFolder, 0);
        m_generalInfoTab.setControl(m_generalInfoPage);
        m_generalInfoTab.setText("General");

        m_detailInfoPage = new ContactDetailInfoComposite(m_tabFolder);
        m_detailInfoTab = new TabItem(m_tabFolder, 0);
        m_detailInfoTab.setControl(m_detailInfoPage);
        m_detailInfoTab.setText("Details");

        if (m_bFillFields)
        {
            strTitle = "Edit Contact";
            strMessage = "You can modify your contact in the fields below";

            fillFields();
        }
        else
        {
            strTitle = "New Contact";
            strMessage = "Enter the information for your contact in the fields below";
        }

        setTitle(strTitle);
        setMessage(strMessage);

        return (container);
    }

    protected void okPressed()
    {
        try
        {
            m_contact = getContactFromForm();
            m_contactBean.saveContact(m_contact);

            super.okPressed();
        }
        catch (ValidationException ex)
        {
            MessageDialog.openError(getShell(), "Invalid Entry", ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "Unexpected Error", ex.getMessage());
        }
    }
}
