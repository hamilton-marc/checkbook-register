/*
 * Created on Jun 26, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.custom.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.window.*;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.biz.*;
import com.uhills.finance.easetax.filtersort.*;
import com.uhills.finance.easetax.print.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class ReportMaintenanceForm extends MaintenanceForm
{
    public static final int                     BUTTON_RUN      = 10;

    public static final ColumnDefinition        COLUMN_NAME = new ColumnDefinition(0, Report.FIELD_NAME.caption, 200, SWT.LEFT, Report.FIELD_NAME.id);
    public static final ColumnDefinition        COLUMN_TYPE = new ColumnDefinition(1, Report.FIELD_TYPE.caption, 100, SWT.LEFT, Report.FIELD_TYPE.id);
    public static final ColumnDefinition        COLUMN_DESCRIPTION = new ColumnDefinition(2, Report.FIELD_DESCRIPTION.caption, 150, SWT.LEFT, Report.FIELD_DESCRIPTION.id);

    protected Button                            m_runButton;

    private static final ColumnDefinition[]     m_columnArray =
    {
        COLUMN_NAME,
        COLUMN_TYPE,
        COLUMN_DESCRIPTION
    };

    private ReportBusinessLogicBean            m_reportBean;

    public ReportMaintenanceForm(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);
    }

    public static int getColumnCount()
    {
        return (m_columnArray.length);
    }

    protected void initializeForm()
    {
        m_reportBean = new ReportBusinessLogicBean();

        setInitialDisplayCriteria();
        super.initializeForm();
    }

    private void setInitialDisplayCriteria()
    {
        SortCriteria[]      sortCriteria = { new SortCriteria(Report.FIELD_NAME.id) };

        m_displayCriteria = new DisplayCriteria(null, sortCriteria);
    }

    public String getText()
    {
        return (Report.CAPTION_REPORT + "s");
    }

    public Image getImage()
    {
        return (ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_REPORTS));
    }

    protected void createTable()
    {
//      TableColumn         tableColumn;

        super.createTable();

        for (int i = 0; i < m_columnArray.length; i++)
        {
            configureNewTableColumn(m_columnArray[i]);
        }

        m_tableViewer.setContentProvider(new ReportTableContentProvider());
        m_tableViewer.setLabelProvider(new ReportTableLabelProvider());

        refresh();
    }

    protected void createButtonBar()
    {
        super.createButtonBar();

        m_newButton.setText("&Create a " + Report.CAPTION_REPORT);
        m_editButton.setText("&Edit " + Report.CAPTION_REPORT);
        m_deleteButton.setText("&Delete " + Report.CAPTION_REPORT);

        m_runButton.setText("&Run " + Report.CAPTION_REPORT);
        m_runButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                runReport();
            }
        }
        );
    }

    protected Button createButton(Composite parent, int iId, String strLabel)
    {
        Button      button = super.createButton(parent, iId, strLabel);

        if (iId == BUTTON_EDIT)
        {
            m_runButton = createButton(parent, BUTTON_RUN, "&Run " + Report.CAPTION_REPORT);
        }

        return (button);
    }

    private Report getSelectedReport()
    {
        Report                  selectedReport = null;
        IStructuredSelection    selection = (IStructuredSelection) m_tableViewer.getSelection();

        if (selection != null)
            selectedReport = (Report) selection.getFirstElement();

        if (selectedReport == null)
        {
            MessageDialog.openError(getShell(), "No Selection", "Please select a " + Report.CAPTION_REPORT + " from the list");
        }

        return (selectedReport);
    }

    protected void onDoubleClick()
    {
        runReport();
    }

    protected void newEntry()
    {
        ReportWizardDialog      reportWizard = new ReportWizardDialog(getShell());
        int                     iRetCode = reportWizard.open();

        if (iRetCode != Window.OK) return;

        m_tableViewer.refresh();
        runReport(reportWizard.getReport());
    }

    protected void deleteEntry()
    {
        Report              selectedReport = getSelectedReport();

        if (selectedReport == null)
            return;

        if (!GUIUtils.getDeleteConfirmation(getShell(), Report.CAPTION_REPORT))
            return;

        try
        {
            m_reportBean.deleteReport(selectedReport);
            m_tableViewer.refresh();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "No Selection", ex.getMessage());
        }
    }

    protected void editEntry()
    {
        Report              selectedReport = getSelectedReport();

        if (selectedReport == null)
            return;

        ReportDialog        reportDialog = new ReportDialog(getShell());
        int                 iRetCode = reportDialog.openItem(selectedReport);

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    public void runReport()
    {
        Report              selectedReport = getSelectedReport();

        if (selectedReport == null)
            return;

        runReport(selectedReport);
    }

    public void runReport(Report report)
    {
        PrintEngineRunnableWrapper      peRunnable;
        DisplayCriteria                 reportCriteria;
        Hashtable                       reportProperties;
        TransactionReportData           printData;

        try
        {
            reportCriteria = m_reportBean.getReportCriteria(report);
            reportProperties = m_reportBean.getReportProperties(report);
            printData = new TransactionReportData(reportCriteria);
            printData.setCombineNumberWithContact(true);

            // All this nonsense with the "Runnable Wrapper" is to
            // allow us to show an hourglass (or the equivalent icon)
            // while the Report Engine is running the report.

            peRunnable = new PrintEngineRunnableWrapper(printData, report.type.template, reportProperties);
            BusyIndicator.showWhile(getDisplay(), peRunnable);
            peRunnable.checkForException();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "File Error", ex.getMessage());
        }
        catch (PrintingException ex)
        {
            MessageDialog.openError(getShell(), "Reporting Error", ex.getMessage());
        }
    }

    public void printPreview()
    {
        runReport();
    }

    public void print()
    {
        printPreview();
    }
}
