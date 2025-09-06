/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.company;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompanyTypePage extends WizardPage
{
    private static final int        MAX_WIDTH = 300;

    private Label           m_helpLabel;
    private Label           m_companyTypeLabel;
    private ListViewer      m_companyTypeListViewer;

    /**
     * @param arg0
     */
    public CompanyTypePage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Company Type");
        setDescription("Please choose the appropriate accounting type for your business");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

        GridLayout          outerGrid = new GridLayout(2, false);
        Composite           container = new Composite(parent, SWT.NO_FOCUS);
        GridData            gridData;
        Label               spacerLabel;

        outerGrid.marginWidth = 30;
        outerGrid.marginHeight = 10;
        outerGrid.horizontalSpacing = 10;
        outerGrid.verticalSpacing = 10;

        container.setLayout(outerGrid);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        gridData.horizontalSpan = 2;
        m_helpLabel = new Label(container, SWT.LEFT | SWT.WRAP);
        m_helpLabel.setLayoutData(gridData);
        m_helpLabel.setText("It is important that you select the right " +                            "accounting type for your business.  This will be " +                            "used to set up the correct tax categories for " +                            "your company's transactions");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        gridData.horizontalSpan = 2;
        gridData.heightHint = 10;
        spacerLabel = new Label(container, SWT.LEFT);
        spacerLabel.setLayoutData(gridData);
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        m_companyTypeLabel = new Label(container, SWT.LEFT);
        m_companyTypeLabel.setLayoutData(gridData);
        m_companyTypeLabel.setText(Company.FIELD_TYPE.caption);        

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.heightHint = 100;
        m_companyTypeListViewer = new ListViewer(container, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
        m_companyTypeListViewer.getList().setLayoutData(gridData);
        m_companyTypeListViewer.setContentProvider(new CompanyTypeContentProvider());
        m_companyTypeListViewer.setInput(new Boolean(true));
        m_companyTypeListViewer.addSelectionChangedListener(new ISelectionChangedListener()
        {
            public void selectionChanged(SelectionChangedEvent event)
            {
                setPageComplete(validatePage());
            }
        }
        );


        Company     company = ((CompanyWizard) getWizard()).getCompany();

        fillFields(company);
        setControl(container);
        setPageComplete(validatePage());
    }

    private void fillFields(Company company)
    {
        if (company == null) return;
        if (company.getType() == null) return;

        setCompanyType(company.getType());       
    }

    private CompanyType getCompanyType()
    {
        CompanyType             selectedCompanyType = null;
        IStructuredSelection    selection = (IStructuredSelection) m_companyTypeListViewer.getSelection();

        if (selection != null)
            selectedCompanyType = (CompanyType) selection.getFirstElement();

        return (selectedCompanyType);
    }

    private void setCompanyType(CompanyType companyType)
    {
        m_companyTypeListViewer.setSelection(new StructuredSelection(companyType));
    }

    private boolean validatePage()
    {
        return (getCompanyType() != null);
    }

    public void fillObjectFromForm(Company company)
    {
        company.setType(getCompanyType());
    }
}
