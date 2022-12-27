/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.company;

import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;

import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompanyInfoPage extends WizardPage
{
    private static final int        MAX_TEXT_WIDTH = 250;

    private Label           m_companyNameLabel;
    private Text            m_companyNameText;
    private Label           m_streetLabel;
    private Text            m_streetText;
    private Label           m_cityLabel;
    private Text            m_cityText;
    private Label           m_stateProvinceLabel;
    private Text            m_stateProvinceText;
    private Label           m_zipPostalLabel;
    private Text            m_zipPostalText;
/*
    private Label           m_phoneLabel;
    private Text            m_phoneText;
    private Label           m_faxLabel;
    private Text            m_faxText;
*/
    /**
     * @param arg0
     */
    public CompanyInfoPage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Company Information");
        setDescription("Enter your company's name and address in the fields below");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

        GridLayout          outerGrid = new GridLayout(2, false);
        Composite           container = new Composite(parent, SWT.NO_FOCUS);
        GridData            gridData;

        outerGrid.marginWidth = 30;
        outerGrid.marginHeight = 10;
        outerGrid.horizontalSpacing = 10;
        outerGrid.verticalSpacing = 10;

        container.setLayout(outerGrid);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_companyNameLabel = new Label(container, SWT.LEFT);
        m_companyNameLabel.setLayoutData(gridData);
        m_companyNameLabel.setText(Company.FIELD_NAME.caption);        

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_companyNameText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_companyNameText.setLayoutData(gridData);
        m_companyNameText.setTextLimit(Company.FIELD_NAME.maxLength);
        m_companyNameText.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                setPageComplete(validatePage());
            }
        }
        );

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        m_streetLabel = new Label(container, SWT.LEFT);
        m_streetLabel.setLayoutData(gridData);
        m_streetLabel.setText(Address.FIELD_STREET.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        gridData.heightHint = 30;
        m_streetText = new Text(container, SWT.LEFT | SWT.WRAP | SWT.BORDER);
        m_streetText.setLayoutData(gridData);
        m_streetText.setTextLimit(Address.FIELD_STREET.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_cityLabel = new Label(container, SWT.LEFT);
        m_cityLabel.setLayoutData(gridData);
        m_cityLabel.setText(Address.FIELD_CITY.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_cityText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_cityText.setLayoutData(gridData);
        m_cityText.setTextLimit(Address.FIELD_CITY.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        m_stateProvinceLabel = new Label(container, SWT.LEFT);
        m_stateProvinceLabel.setLayoutData(gridData);
        m_stateProvinceLabel.setText(Address.FIELD_STATE_PROVINCE.caption);        

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH / 4;
        m_stateProvinceText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_stateProvinceText.setLayoutData(gridData);
        m_stateProvinceText.setTextLimit(Address.FIELD_STATE_PROVINCE.maxLength);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        m_zipPostalLabel = new Label(container, SWT.LEFT);
        m_zipPostalLabel.setLayoutData(gridData);
        m_zipPostalLabel.setText(Address.FIELD_ZIP_POSTAL.caption);        

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH / 2;
        m_zipPostalText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_zipPostalText.setLayoutData(gridData);
        m_zipPostalText.setTextLimit(Address.FIELD_ZIP_POSTAL.maxLength);

        Company     company = ((CompanyWizard) getWizard()).getCompany();

        fillFields(company);
        setControl(container);
        setPageComplete(validatePage());
    }

    private void fillFields(Company company)
    {
        if (company == null) return;

        Address     address = company.getAddress();

        m_companyNameText.setText(company.name);
        m_streetText.setText(address.formatStreet());
        m_cityText.setText(address.city);
        m_stateProvinceText.setText(address.stateProvince);
        m_zipPostalText.setText(address.zipPostal);
    }

    public void fillObjectFromForm(Company company)
    {
        Address     address = new Address();

        address.initialize();

        company.name = m_companyNameText.getText();
        address.street = GUIUtils.parseStreet(m_streetText.getText(), m_streetText.getLineDelimiter());
        address.city = m_cityText.getText();
        address.stateProvince = m_stateProvinceText.getText();
        address.zipPostal = m_zipPostalText.getText();

        company.setAddress(address);
    }

    private boolean validatePage()
    {
        boolean     bIsValid = m_companyNameText.getText().trim().length() > 0;

        return (bIsValid);
    }
}
