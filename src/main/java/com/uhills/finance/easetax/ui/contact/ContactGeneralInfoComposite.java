/*
 * Created on Mar 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.contact;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

import com.uhills.util.validation.*;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ContactGeneralInfoComposite extends Composite
{
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_LABEL_WIDTH = 90;

    private Label                   m_contactNameLabel;
    private Text                    m_contactNameText;
    private Label                   m_descriptionLabel;
    private Text                    m_descriptionText;
    private Button                  m_customerRadioButton;
    private Button                  m_vendorRadioButton;
    private Button                  m_otherRadioButton;

    private Label                   m_accountNumberLabel;
    private Text                    m_accountNumberText;

    public ContactGeneralInfoComposite(Composite parent)
    {
        super(parent, SWT.NONE);
        createControl();
    }

    private void createControl()
    {
        GridLayout          gridLayout = new GridLayout();

        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 8;
        gridLayout.horizontalSpacing = 5;

        setLayout(gridLayout);
        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createContactInfoArea();
        createContactTypeArea();
        createContactReferenceArea();
    }

    private void createContactTypeArea()
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(this, SWT.NO_FOCUS);

        gridLayout = new GridLayout(3, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("Contact Type");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_customerRadioButton = new Button(container, SWT.RADIO);
        m_customerRadioButton.setLayoutData(gridData);
        m_customerRadioButton.setText(ContactType.CUSTOMER.toString());

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_vendorRadioButton = new Button(container, SWT.RADIO);
        m_vendorRadioButton.setLayoutData(gridData);
        m_vendorRadioButton.setText(ContactType.VENDOR.toString());

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_otherRadioButton = new Button(container, SWT.RADIO);
        m_otherRadioButton.setLayoutData(gridData);
        m_otherRadioButton.setText(ContactType.OTHER.toString());
    }

    private void createContactInfoArea()
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(this, SWT.NO_FOCUS);

        gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("Contact Information");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_contactNameLabel = new Label(container, SWT.LEFT);
        m_contactNameLabel.setLayoutData(gridData);
        m_contactNameLabel.setText(Contact.FIELD_NAME.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_contactNameText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_contactNameText.setLayoutData(gridData);
        m_contactNameText.setTextLimit(Contact.FIELD_NAME.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_descriptionLabel = new Label(container, SWT.LEFT);
        m_descriptionLabel.setLayoutData(gridData);
        m_descriptionLabel.setText("Description");        

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_descriptionText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_descriptionText.setLayoutData(gridData);
    }

    private void createContactReferenceArea()
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(this, SWT.NO_FOCUS);

        gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("Reference Information");

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_accountNumberLabel = new Label(container, SWT.LEFT);
        m_accountNumberLabel.setLayoutData(gridData);
        m_accountNumberLabel.setText(Contact.FIELD_ACCOUNT_NUM.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_accountNumberText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_accountNumberText.setLayoutData(gridData);
        m_accountNumberText.setTextLimit(Contact.FIELD_ACCOUNT_NUM.maxLength);
    }

    private void setContactType(ContactType type)
    {
        if (type == null) return;

        if (type.equals(ContactType.CUSTOMER))
            m_customerRadioButton.setSelection(true);
        else if (type.equals(ContactType.VENDOR))
            m_vendorRadioButton.setSelection(true);
        else
            m_otherRadioButton.setSelection(true);
    }

    private ContactType getContactType()
    {
        ContactType         contactType = ContactType.OTHER;

        if (m_customerRadioButton.getSelection())
            contactType = ContactType.CUSTOMER;
        else if (m_vendorRadioButton.getSelection())
            contactType = ContactType.VENDOR;

        return (contactType);
    }

    private void validate() throws ValidationException
    {
        if (m_contactNameText.getText().trim().length() == 0)
        {
            throw new ValidationException ("Please enter a " + Contact.FIELD_NAME.caption + " for your " + Contact.CAPTION_CONTACT);
        }

        if (!m_customerRadioButton.getSelection() &&
            !m_vendorRadioButton.getSelection() &&
            !m_otherRadioButton.getSelection())
        {
            throw new ValidationException ("Please select a " + ContactType.CAPTION_TYPE + " for your " + Contact.CAPTION_CONTACT);
        }
    }

    public void fillFields(Object obj)
    {
        Contact     contact = (Contact) obj;

        m_contactNameText.setText(contact.name);
        m_descriptionText.setText(contact.description);
        setContactType(contact.getType());

        m_accountNumberText.setText(contact.accountNumber);
    }

    public void fillObject(Object obj) throws ValidationException
    {
        validate();

        Contact     contact = (Contact) obj;

        contact.name = m_contactNameText.getText().trim();
        contact.description = m_descriptionText.getText().trim();
        contact.setType(getContactType());

        contact.accountNumber = m_accountNumberText.getText().trim();
    }
}
