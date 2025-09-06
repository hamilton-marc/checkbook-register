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
import org.eclipse.swt.events.*;
import org.eclipse.jface.viewers.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.ui.calendar.*;

import com.uhills.util.validation.ValidationException;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportDateRangeComposite extends Composite implements IDataEntryPage
{
    private static final int        MAX_WIDTH = 250;
//  private static final int        SPACER_HEIGHT = 6;

    private Button                  m_allDatesRadioButton;
    private Button                  m_autoRangeRadioButton;
    private Button                  m_manualRangeRadioButton;

    private Label                   m_fromDateLabel;
    private Label                   m_toDateLabel;
    private ComboViewer             m_autoDateRangeComboViewer;
    private DateCombo               m_fromDateCombo;
    private DateCombo               m_toDateCombo;

    public ReportDateRangeComposite(Composite parent)
    {
        super(parent, SWT.NONE);

        createControl();
    }

    private void createControl()
    {
        GridLayout          outerGrid = new GridLayout(2, false);
        GridData            gridData;
//      Label               spacerLabel;

        outerGrid.marginWidth = 30;
        outerGrid.marginHeight = 10;
        outerGrid.horizontalSpacing = 10;
        outerGrid.verticalSpacing = 14;

        setLayout(outerGrid);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        gridData.horizontalSpan = 2;
        m_allDatesRadioButton = new Button(this, SWT.RADIO);
        m_allDatesRadioButton.setLayoutData(gridData);
        m_allDatesRadioButton.setText("Run the report for all transaction dates");
        m_allDatesRadioButton.setSelection(true);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_autoRangeRadioButton = new Button(this, SWT.RADIO);
        m_autoRangeRadioButton.setLayoutData(gridData);
        m_autoRangeRadioButton.setText("For");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_autoDateRangeComboViewer = new ComboViewer(this, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_autoDateRangeComboViewer.getCombo().setLayoutData(gridData);
        m_autoDateRangeComboViewer.setContentProvider(new DateFilterTypeContentProvider());
        m_autoDateRangeComboViewer.setInput(new Boolean(false));

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        gridData.horizontalSpan = 2;
        m_manualRangeRadioButton = new Button(this, SWT.RADIO);
        m_manualRangeRadioButton.setLayoutData(gridData);
        m_manualRangeRadioButton.setText("Use the following date range");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalIndent = 20;
        m_fromDateLabel = new Label(this, SWT.LEFT);
        m_fromDateLabel.setLayoutData(gridData);
        m_fromDateLabel.setText("From");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_fromDateCombo = new DateCombo(this, SWT.LEFT | SWT.BORDER);
        m_fromDateCombo.setLayoutData(gridData);
        m_fromDateCombo.setPattern(ApplicationMain.getUserPreferences().dateFormat);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalIndent = 20;
        m_toDateLabel = new Label(this, SWT.LEFT);
        m_toDateLabel.setLayoutData(gridData);
        m_toDateLabel.setText("To");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_toDateCombo = new DateCombo(this, SWT.LEFT | SWT.BORDER);
        m_toDateCombo.setLayoutData(gridData);
        m_toDateCombo.setPattern(ApplicationMain.getUserPreferences().dateFormat);

        configureEvents();
        toggleDateRangeType();
    }

    private void configureEvents()
    {
        m_allDatesRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleDateRangeType();
            }
        }
        );

        m_autoRangeRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleDateRangeType();
            }
        }
        );

        m_manualRangeRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleDateRangeType();
            }
        }
        );
    }

    private void toggleDateRangeType()
    {
        m_autoDateRangeComboViewer.getCombo().setEnabled(m_autoRangeRadioButton.getSelection());
        m_fromDateLabel.setEnabled(m_manualRangeRadioButton.getSelection());
        m_fromDateCombo.setEnabled(m_manualRangeRadioButton.getSelection());
        m_toDateLabel.setEnabled(m_manualRangeRadioButton.getSelection());
        m_toDateCombo.setEnabled(m_manualRangeRadioButton.getSelection());
        
    }

    private DateFilterType getDateFilterType()
    {
        DateFilterType          dateFilterType = DateFilterType.DATE_FILTER_ALL;

        if (m_autoRangeRadioButton.getSelection())
        {
            IStructuredSelection    selection = (IStructuredSelection) m_autoDateRangeComboViewer.getSelection();

            if (selection != null)
                dateFilterType = (DateFilterType) selection.getFirstElement();
        }
        else if (m_manualRangeRadioButton.getSelection())
        {
            dateFilterType = DateFilterType.DATE_FILTER_CUSTOM;
        }

        return (dateFilterType);
    }

    public boolean isPageComplete()
    {
        boolean     bPageComplete;

        bPageComplete = m_allDatesRadioButton.getSelection() ||
                        m_autoRangeRadioButton.getSelection() ||
                        m_manualRangeRadioButton.getSelection();

        return (bPageComplete);
    }

    public void validate() throws ValidationException
    {
        if (m_autoRangeRadioButton.getSelection())
        {
            IStructuredSelection    selection = (IStructuredSelection) m_autoDateRangeComboViewer.getSelection();

            if (selection == null ||
                selection.size() <= 0)
            {
                throw new ValidationException("You must select a date range from the list");
            }
        }
        else if (m_manualRangeRadioButton.getSelection())
        {
            if (m_fromDateCombo.getDate() == null ||
                m_toDateCombo.getDate() == null)
            {
                throw new ValidationException("Please be sure to enter both a valid \"From\" and valid \"To\" date");
            }

            if (m_fromDateCombo.getDate().after(m_toDateCombo.getDate()))
            {
                throw new ValidationException("You cannot enter a \"From\" date that is after the \"To\" date");
            }
        }
    }

    public void fillFields(Object obj)
    {
        Report      report = (Report) obj;

        m_allDatesRadioButton.setSelection(false);
        m_manualRangeRadioButton.setSelection(false);
        m_autoRangeRadioButton.setSelection(false);

        if (report.dateFilterType.equals(DateFilterType.DATE_FILTER_ALL))
        {
            m_allDatesRadioButton.setSelection(true);
        }
        else if (report.dateFilterType.equals(DateFilterType.DATE_FILTER_CUSTOM))
        {
            m_manualRangeRadioButton.setSelection(true);
            m_fromDateCombo.setDate(report.fromDate);
            m_toDateCombo.setDate(report.toDate);
        }
        else
        {
            m_autoRangeRadioButton.setSelection(true);
            m_autoDateRangeComboViewer.setSelection(new StructuredSelection(report.dateFilterType));
        }

        toggleDateRangeType();
    }

    public void fillObject(Object obj) throws ValidationException
    {
        validate();

        Report      report = (Report) obj;

        report.dateFilterType = getDateFilterType();

        if (report.dateFilterType.equals(DateFilterType.DATE_FILTER_CUSTOM))
        {
            report.fromDate = m_fromDateCombo.getDate();
            report.toDate = m_toDateCombo.getDate();
        }
    }
}
