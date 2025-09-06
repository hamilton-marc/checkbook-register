/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import org.eclipse.jface.wizard.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.Composite;

import com.uhills.util.exception.*;
import com.uhills.util.validation.*;

import com.uhills.finance.easetax.biz.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.ImagePool;
import com.uhills.finance.easetax.ui.common.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportWizard extends Wizard
{
    public static final String              PAGE_INTRO              = "Intro";
    public static final String              PAGE_REPORT_TYPE        = "ReportType";
    public static final String              PAGE_REPORT_DATE_RANGE  = "ReportDateRange";
    public static final String              PAGE_REPORT_CATEGORY    = "ReportCategory";
    public static final String              PAGE_REPORT_NAME        = "ReportName";
    public static final String              PAGE_COMPLETE           = "Complete";

    private ReportBusinessLogicBean         m_reportBean;
    private Report                          m_report;

    /**
     * Default constructor.
     */
    public ReportWizard()
    {
        super();
        setWindowTitle("New Report Setup Wizard");

        m_reportBean = new ReportBusinessLogicBean();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish()
    {
        boolean     bFinished = false;
        Report      report;

        try
        {
            report = getReportFromForm();
            m_reportBean.saveReport(report);
            m_report = report;

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

    public boolean performNext(IWizardPage currentPage)
    {
        boolean     bCanProceed = true;

        try
        {
            IExtendedWizardPage     extendedPage = (IExtendedWizardPage) currentPage;

            extendedPage.validatePage();
        }
        catch (ValidationException ex)
        {
            bCanProceed = false;
            MessageDialog.openError(getShell(), "Invalid Entry", ex.getMessage());
        }

        return (bCanProceed);
    }

    public void addPages()
    {
        addPage(new ReportIntroPage(PAGE_INTRO));
        addPage(new ReportTypePage(PAGE_REPORT_TYPE));
        addPage(new ReportDateRangePage(PAGE_REPORT_DATE_RANGE));
        addPage(new ReportCategoryPage(PAGE_REPORT_CATEGORY));
        addPage(new ReportNamePage(PAGE_REPORT_NAME));
        addPage(new ReportCompletionPage(PAGE_COMPLETE));
    }

    public void createPageControls(Composite pageContainer)
    {
        super.createPageControls(pageContainer);

        getShell().setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
    }

    public Report getReport()
    {
        return (m_report);
    }

    public ReportBusinessLogicBean getReportBean()
    {
        return (m_reportBean);
    }

    private Report getReportFromForm() throws ValidationException
    {
        Report     report = new Report();

        report.initialize();

        ReportTypePage                  typePage = (ReportTypePage) getPage(PAGE_REPORT_TYPE);
        ReportDateRangePage             dateRangePage = (ReportDateRangePage) getPage(PAGE_REPORT_DATE_RANGE);
        ReportCategoryPage              categoryPage = (ReportCategoryPage) getPage(PAGE_REPORT_CATEGORY);
        ReportNamePage                  namePage = (ReportNamePage) getPage(PAGE_REPORT_NAME);

        ((IDataEntryPage) typePage.getControl()).fillObject(report);
        ((IDataEntryPage) dateRangePage.getControl()).fillObject(report);
        ((IDataEntryPage) categoryPage.getControl()).fillObject(report);
        ((IDataEntryPage) namePage.getControl()).fillObject(report);

        return (report);
    }

}
