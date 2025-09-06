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
import com.uhills.finance.easetax.ui.common.GUIUtils;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ContactDetailInfoComposite extends Composite
{
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_LABEL_WIDTH = 90;

    private Label                   m_streetLabel;
    private Text                    m_streetText;
    private Label                   m_cityLabel;
    private Text                    m_cityText;
    private Label                   m_stateProvinceLabel;
    private Text                    m_stateProvinceText;
    private Label                   m_zipPostalLabel;
    private Text                    m_zipPostalText;
    private Label                   m_countryLabel;
    private Text                    m_countryText;
    private Label                   m_phoneLabel;
    private Text                    m_phoneText;
    private Label                   m_faxLabel;
    private Text                    m_faxText;
    private Label                   m_webSiteLabel;
    private Text                    m_webSiteText;
    private Label                   m_emailLabel;
    private Text                    m_emailText;
/*
    private Label                   m_accountNumberLabel;
    private Text                    m_accountNumberText;
*/
    public ContactDetailInfoComposite(Composite parent)
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

        createContactAddressArea();
//      createContactReferenceArea();
    }

    private void createContactAddressArea()
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

        container.setText("Address Information");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_streetLabel = new Label(container, SWT.LEFT);
        m_streetLabel.setLayoutData(gridData);
        m_streetLabel.setText(Address.FIELD_STREET.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        gridData.heightHint = 30;
        m_streetText = new Text(container, SWT.LEFT | SWT.WRAP | SWT.BORDER);
        m_streetText.setLayoutData(gridData);
        m_streetText.setTextLimit(Address.FIELD_STREET.maxLength * Address.NUM_STREET_LINES);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_cityLabel = new Label(container, SWT.LEFT);
        m_cityLabel.setLayoutData(gridData);
        m_cityLabel.setText(Address.FIELD_CITY.caption);        

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_cityText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_cityText.setLayoutData(gridData);
        m_cityText.setTextLimit(Address.FIELD_CITY.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_stateProvinceLabel = new Label(container, SWT.LEFT);
        m_stateProvinceLabel.setLayoutData(gridData);
        m_stateProvinceLabel.setText(Address.FIELD_STATE_PROVINCE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH / 8;
        m_stateProvinceText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_stateProvinceText.setLayoutData(gridData);
        m_stateProvinceText.setTextLimit(Address.FIELD_STATE_PROVINCE.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_zipPostalLabel = new Label(container, SWT.LEFT);
        m_zipPostalLabel.setLayoutData(gridData);
        m_zipPostalLabel.setText(Address.FIELD_ZIP_POSTAL.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH / 2;
        m_zipPostalText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_zipPostalText.setLayoutData(gridData);
        m_zipPostalText.setTextLimit(Address.FIELD_ZIP_POSTAL.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_countryLabel = new Label(container, SWT.LEFT);
        m_countryLabel.setLayoutData(gridData);
        m_countryLabel.setText(Address.FIELD_COUNTRY.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_countryText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_countryText.setLayoutData(gridData);
        m_countryText.setTextLimit(Address.FIELD_COUNTRY.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_phoneLabel = new Label(container, SWT.LEFT);
        m_phoneLabel.setLayoutData(gridData);
        m_phoneLabel.setText(Address.FIELD_PHONE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH / 2;
        m_phoneText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_phoneText.setLayoutData(gridData);
        m_phoneText.setTextLimit(Address.FIELD_PHONE.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_faxLabel = new Label(container, SWT.LEFT);
        m_faxLabel.setLayoutData(gridData);
        m_faxLabel.setText(Address.FIELD_FAX.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH / 2;
        m_faxText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_faxText.setLayoutData(gridData);
        m_faxText.setTextLimit(Address.FIELD_FAX.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_emailLabel = new Label(container, SWT.LEFT);
        m_emailLabel.setLayoutData(gridData);
        m_emailLabel.setText(Address.FIELD_EMAIL.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_emailText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_emailText.setLayoutData(gridData);
        m_emailText.setTextLimit(Address.FIELD_EMAIL.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_webSiteLabel = new Label(container, SWT.LEFT);
        m_webSiteLabel.setLayoutData(gridData);
        m_webSiteLabel.setText(Address.FIELD_WEBSITE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_webSiteText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_webSiteText.setLayoutData(gridData);
        m_webSiteText.setTextLimit(Address.FIELD_WEBSITE.maxLength);
    }
/*    
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
*/
    private void validate() throws ValidationException
    {
    }

    public void fillFields(Object obj)
    {
        Contact     contact = (Contact) obj;
        Address     address = contact.getAddress();

        if (address != null)
        {
            m_streetText.setText(address.formatStreet());
            m_cityText.setText(address.city);
            m_stateProvinceText.setText(address.stateProvince);
            m_zipPostalText.setText(address.zipPostal);
            m_countryText.setText(address.country);
            m_phoneText.setText(address.phone);
            m_faxText.setText(address.fax);
            m_webSiteText.setText(address.webSite);
            m_emailText.setText(address.email);
        }

//      m_accountNumberText.setText(contact.accountNumber);
    }

    public void fillObject(Object obj) throws ValidationException
    {
        validate();

        Contact         contact = (Contact) obj;
        Address         address = new Address();

        address.initialize();

        address.street = GUIUtils.parseStreet(m_streetText.getText().trim(), m_streetText.getLineDelimiter());
        address.city = m_cityText.getText().trim();
        address.stateProvince = m_stateProvinceText.getText().trim();
        address.zipPostal = m_zipPostalText.getText().trim();
        address.country = m_countryText.getText().trim();
        address.phone = m_phoneText.getText().trim();
        address.fax = m_faxText.getText().trim();
        address.webSite = m_webSiteText.getText().trim();
        address.email = m_emailText.getText().trim();

        contact.setAddress(address);

//      contact.accountNumber = m_accountNumberText.getText().trim();
    }
}
