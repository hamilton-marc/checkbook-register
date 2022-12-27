/*
 * Created on Dec 21, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.jface.viewers.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.ui.common.*;

import com.uhills.util.validation.ValidationException;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportTypeComposite extends Composite implements IDataEntryPage
{
    private static final int        MAX_WIDTH = 300;

    private Label                   m_helpLabel;
    private Label                   m_reportTypeLabel;
    private ListViewer              m_reportTypeListViewer;

    public ReportTypeComposite(Composite parent)
    {
        super(parent, SWT.NONE);

        createControl();
    }

    private void createControl()
    {
        GridLayout          outerGrid = new GridLayout(2, false);
        GridData            gridData;
        Label               spacerLabel;

        outerGrid.marginWidth = 30;
        outerGrid.marginHeight = 10;
        outerGrid.horizontalSpacing = 10;
        outerGrid.verticalSpacing = 10;

        setLayout(outerGrid);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        gridData.horizontalSpan = 2;
        m_helpLabel = new Label(this, SWT.LEFT | SWT.WRAP);
        m_helpLabel.setLayoutData(gridData);
        m_helpLabel.setText("Choose one of the templates below to build your report from.");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        gridData.horizontalSpan = 2;
        gridData.heightHint = 10;
        spacerLabel = new Label(this, SWT.LEFT);
        spacerLabel.setLayoutData(gridData);
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        m_reportTypeLabel = new Label(this, SWT.LEFT);
        m_reportTypeLabel.setLayoutData(gridData);
        m_reportTypeLabel.setText(Report.FIELD_TYPE.caption);        

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.heightHint = 100;
        gridData.horizontalIndent = 20;
        m_reportTypeListViewer = new ListViewer(this, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
        m_reportTypeListViewer.getList().setLayoutData(gridData);
        m_reportTypeListViewer.setContentProvider(new ReportTypeContentProvider());
        m_reportTypeListViewer.setInput(new Integer(0));  // the integer is simply to provide a non-null value
    }

    public ListViewer getReportTypeListViewer()
    {
        return (m_reportTypeListViewer);
    }

    public void fillFields(Object obj)
    {
        Report      report = (Report) obj;

        if (report == null) return;
        if (report.type == null) return;

        setReportType(report.type);       
    }

    private ReportType getReportType()
    {
        ReportType              selectedReportType = null;
        IStructuredSelection    selection = (IStructuredSelection) m_reportTypeListViewer.getSelection();

        m_reportTypeListViewer.getSelection();

        if (selection != null)
            selectedReportType = (ReportType) selection.getFirstElement();

        return (selectedReportType);
    }

    private void setReportType(ReportType reportType)
    {
        m_reportTypeListViewer.setSelection(new StructuredSelection(reportType));
    }

    public boolean isPageComplete()
    {
        return (getReportType() != null);
    }

    public void validate() throws ValidationException
    {
        if (getReportType() == null)
        {
            throw new ValidationException("Please select a report type from the list");
        }
    }

    public void fillObject(Object obj) throws ValidationException
    {
        validate();

        Report      report = (Report) obj;

        report.type = getReportType();
    }
}
