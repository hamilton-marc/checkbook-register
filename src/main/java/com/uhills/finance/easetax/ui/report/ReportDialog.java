/*
 * Created on Jul 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

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
public final class ReportDialog extends TitleAreaDialog
{
    public static final int                 RUN_REPORT_NOW_BUTTON_ID = 100;
/*
    private static final int                MAX_TEXT_WIDTH = 250;
    private static final int                MIN_LABEL_WIDTH = 90;
*/
    private Button                          m_runReportNowButton;

    private TabFolder                       m_tabFolder;
    private TabItem                         m_reportNameTab;
    private TabItem                         m_dateRangeTab;
    private TabItem                         m_categoryTab;
    private ReportDateRangeComposite        m_dateRangePage;
    private ReportNameComposite             m_reportNamePage;
    private ReportCategoryComposite         m_categoryPage;

    private ReportBusinessLogicBean         m_reportBean;
    private Report                          m_report;
    private boolean                         m_bFillFields;

    /**
     * @param parentShell
     */
    public ReportDialog(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
        m_reportBean = new ReportBusinessLogicBean();
    }

    public int newItem()
    {
        return (open());
    }

    public int openItem(Object entry)
    {
        if (entry == null) return (CANCEL);

        Report     report = (Report) entry;

        return (openItem(report.getID()));
    }

    public int openItem(long lReportId)
    {
        m_bFillFields = true;

        try
        {
            getReport(lReportId);
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
            return (CANCEL);
        }
        catch (ItemNotFoundException ex)
        {
            MessageDialog.openError(getParentShell(), "Report Entry", ex.getMessage());
            return (CANCEL);
        }

        return (open());
    }

    private void getReport(long lReportId) throws PersistenceException, ItemNotFoundException
    {
        m_report = m_reportBean.getReport(lReportId);

        if (m_report == null)
        {
            throw new ItemNotFoundException("Unable to find that report in the database.  " +
                                            "It may have already been deleted");
        }
    }

    private void fillFields()
    {
        m_reportNamePage.fillFields(m_report);
        m_dateRangePage.fillFields(m_report);
        m_categoryPage.fillFields(m_report);
    }

    private Report getReportFromForm() throws ValidationException
    {
        Report          report = new Report();

        report.initialize();

        if (m_report != null)
        {
            report.setID(m_report.getID());
            report.type = m_report.type;
            report.setTransactionTypes(m_report.getTransactionTypes());  // there is no tab for this
        }

        m_reportNamePage.fillObject(report);
        m_dateRangePage.fillObject(report);
        m_categoryPage.fillObject(report);

        return (report);
    }

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText("Report Entry");
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

        m_reportNamePage = new ReportNameComposite(m_tabFolder, true);
        m_reportNameTab = new TabItem(m_tabFolder, 0);
        m_reportNameTab.setControl(m_reportNamePage);
        m_reportNameTab.setText("General");

        m_dateRangePage = new ReportDateRangeComposite(m_tabFolder);
        m_dateRangeTab = new TabItem(m_tabFolder, 0);
        m_dateRangeTab.setControl(m_dateRangePage);
        m_dateRangeTab.setText("Date Range");

        m_categoryPage = new ReportCategoryComposite(m_tabFolder);
        m_categoryTab = new TabItem(m_tabFolder, 0);
        m_categoryTab.setControl(m_categoryPage);
        m_categoryTab.setText("Accounts");

        if (m_bFillFields)
        {
            strTitle = "Edit Report";
            strMessage = "You can modify your report in the fields below";

            fillFields();
        }
        else
        {
            strTitle = "New Report";
            strMessage = "Enter the information for your report in the fields below";
        }

        setTitle(strTitle);
        setMessage(strMessage);

        return (container);
    }

    /**
     * Adds buttons to this dialog's button bar.
     * <p>
     * Overridden from Dialog.createButtonsForButtonBar().
     * 
     * We had to steal most of the source from the <code>Dialog</code> class
     * in JFace in order to make our own custom button bar.  This is necessary
     * so we can have an additional button for running the report.
     * </p>
     * 
     * @param parent - the button bar composite
     */
/*
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                     true);
        m_runReportNowButton = createButton(parent, RUN_REPORT_NOW_BUTTON_ID,
                                            "Run " + Report.CAPTION_REPORT, false);
        createButton(parent, IDialogConstants.CANCEL_ID,
                     IDialogConstants.CANCEL_LABEL, false);
    }
*/
    protected void okPressed()
    {
        try
        {
            m_report = getReportFromForm();
            m_reportBean.saveReport(m_report);

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
