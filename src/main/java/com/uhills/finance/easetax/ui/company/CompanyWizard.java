/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.company;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.Composite;

import com.uhills.util.exception.*;
import com.uhills.util.validation.*;

import com.uhills.finance.easetax.biz.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.ImagePool;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompanyWizard extends Wizard
{
    public static final String              PAGE_INTRO = "Intro";
    public static final String              PAGE_COMPANY_INFO = "CompanyInfo";
    public static final String              PAGE_COMPANY_TYPE = "CompanyType";
    public static final String              PAGE_COMPLETE = "Complete";

    private CompanyBusinessLogicBean        m_companyBean;
    private Company                         m_company;

    /**
     * Default constructor.
     */
    public CompanyWizard()
    {
        super();
        setWindowTitle("Company Ledger Setup Wizard");

        m_companyBean = new CompanyBusinessLogicBean();
    }

    public void newCompany()
    {
        m_company = null;
    }

    public void editCompany()
    {
        try
        {
            m_company = m_companyBean.getCompany();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "Error", "An error occurred retrieving company information");
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish()
    {
        boolean     bFinished = false;
        Company     company;

        try
        {
            company = getCompanyFromForm();
            company.validate();

        // if we're editing an existing company, save it...
        // if we're setting up a new company, don't save it,
        // the calling method must do that (after the use chooses
        // the file path).

            if (m_company != null)
            {
                m_companyBean.saveCompany(company);
            }

            m_company = company;

            bFinished = true;
        }
        catch (ValidationException ex)
        {
            MessageDialog.openError(getShell(), "Invalid Entry", ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "Error", ex.getMessage());
        }

        return (bFinished);
    }

    public void addPages()
    {
        addPage(new CompanyIntroPage(PAGE_INTRO));
        addPage(new CompanyInfoPage(PAGE_COMPANY_INFO));
        addPage(new CompanyTypePage(PAGE_COMPANY_TYPE));
        addPage(new CompanyCompletionPage(PAGE_COMPLETE));
    }

    public void createPageControls(Composite pageContainer)
    {
        super.createPageControls(pageContainer);

        getShell().setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
    }

    public Company getCompany()
    {
        return (m_company);
    }

    public CompanyBusinessLogicBean getCompanyBean()
    {
        return (m_companyBean);
    }

    private Company getCompanyFromForm() throws ValidationException
    {
        Company     company = new Company();

        company.initialize();

        CompanyInfoPage     infoPage = (CompanyInfoPage) getPage(PAGE_COMPANY_INFO);
        CompanyTypePage     typePage = (CompanyTypePage) getPage(PAGE_COMPANY_TYPE);

        infoPage.fillObjectFromForm(company);
        typePage.fillObjectFromForm(company);

        return (company);
    }

}
