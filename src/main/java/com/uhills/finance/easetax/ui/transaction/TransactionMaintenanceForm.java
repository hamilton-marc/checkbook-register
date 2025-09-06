/*
 * Created on Jun 26, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.transaction;

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.custom.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.*;
import org.eclipse.jface.dialogs.*;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.ui.category.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.ui.main.MainWindow;
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
public final class TransactionMaintenanceForm extends MaintenanceForm
{
    public static final ColumnDefinition        COLUMN_NUMBER = new ColumnDefinition(0, Transaction.FIELD_NUMBER_CODE.caption, 50, SWT.LEFT, Transaction.FIELD_NUMBER_CODE.id);
    public static final ColumnDefinition        COLUMN_DATE = new ColumnDefinition(1, Transaction.FIELD_DATE.caption, 80, SWT.LEFT, Transaction.FIELD_DATE.id);
    public static final ColumnDefinition        COLUMN_CONTACT = new ColumnDefinition(2, Transaction.FIELD_CONTACT.caption, 170, SWT.LEFT, Transaction.FIELD_CONTACT.id);
    public static final ColumnDefinition        COLUMN_CATEGORY = new ColumnDefinition(3, Transaction.FIELD_CATEGORY.caption, 120, SWT.LEFT, Transaction.FIELD_CATEGORY.id);
    public static final ColumnDefinition        COLUMN_DEBIT = new ColumnDefinition(4, Transaction.CAPTION_DEBIT, 80, SWT.RIGHT, Transaction.FIELD_AMOUNT.id);
    public static final ColumnDefinition        COLUMN_CREDIT = new ColumnDefinition(5, Transaction.CAPTION_CREDIT, 80, SWT.RIGHT, Transaction.FIELD_AMOUNT.id);
    public static final ColumnDefinition        COLUMN_JOB_CODE = new ColumnDefinition(6, Transaction.FIELD_JOB_CODE.caption, 80, SWT.LEFT, Transaction.FIELD_JOB_CODE.id);
    public static final ColumnDefinition        COLUMN_DESCRIPTION = new ColumnDefinition(7, Transaction.FIELD_DESCRIPTION.caption, 190, SWT.LEFT, Transaction.FIELD_DESCRIPTION.id);

    public static final String                  TRANSACTION_REPORT_DEFINITION_FILE = "TransactionPrintReport.xml";

    public static final int                     BUTTON_CLONE        = 10;

    protected Button                            m_cloneButton;

    private Label                               m_quickSearchLabel;
    private ComboViewer                         m_dateFilterComboViewer;
    private ComboViewer                         m_transTypeFilterComboViewer;
    private ComboViewer                         m_categoryFilterComboViewer;
    private Button                              m_refreshButton;
    private TransactionBusinessLogicBean        m_transactionBean;
//  private CategoryBusinessLogicBean           m_categoryBean;

    private static final ColumnDefinition[]     m_columnArray =
    {
        COLUMN_NUMBER,
        COLUMN_DATE,
        COLUMN_CONTACT,
        COLUMN_CATEGORY,
        COLUMN_DEBIT,
        COLUMN_CREDIT,
        COLUMN_JOB_CODE,
        COLUMN_DESCRIPTION
    };

    public TransactionMaintenanceForm(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);
    }

    private void setInitialDisplayCriteria()
    {
        SortCriteria[]      sortCriteria = { new SortCriteria(Transaction.FIELD_DATE.id, SortCriteria.DESCENDING) };

        m_displayCriteria = new DisplayCriteria(null, sortCriteria);
    }

    public static int getColumnCount()
    {
        return (m_columnArray.length);
    }

    protected void initializeForm()
    {
        m_transactionBean = new TransactionBusinessLogicBean();
//      m_categoryBean = new CategoryBusinessLogicBean();

        setInitialDisplayCriteria();
        super.initializeForm();
        createQuickSearchBar();
    }

    public String getText()
    {
        return (Transaction.CAPTION_TRANSACTION + " Ledger");
    }

    public Image getImage()
    {
        return (ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_TRANSACTIONS));
    }

    private void createQuickSearchBar()
    {
        GridData            gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        Composite           quickSearchBar = new Composite(this, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout(5, false);

        quickSearchBar.setLayoutData(gridData);
        quickSearchBar.setLayout(gridLayout);
        quickSearchBar.setBackground(getParent().getBackground());
        quickSearchBar.moveAbove(m_tableViewer.getTable());

        gridLayout.horizontalSpacing = 5;
        gridLayout.verticalSpacing = 5;

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.horizontalIndent = 1;
        m_quickSearchLabel = new Label(quickSearchBar, SWT.LEFT);
        m_quickSearchLabel.setLayoutData(gridData);
        m_quickSearchLabel.setText("Show Me:  ");
        m_quickSearchLabel.setBackground(getParent().getBackground());

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.widthHint = 80;
        m_dateFilterComboViewer = new ComboViewer(quickSearchBar, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_dateFilterComboViewer.getCombo().setLayoutData(gridData);
        m_dateFilterComboViewer.setContentProvider(new DateFilterContentProvider());
        m_dateFilterComboViewer.setInput(new Boolean(true));
        m_dateFilterComboViewer.getCombo().select(0);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.widthHint = 100;
        m_transTypeFilterComboViewer = new ComboViewer(quickSearchBar, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_transTypeFilterComboViewer.getCombo().setLayoutData(gridData);
        m_transTypeFilterComboViewer.setContentProvider(new TransactionTypeContentProvider());
        m_transTypeFilterComboViewer.setInput(new Boolean(true));
        m_transTypeFilterComboViewer.getCombo().select(0);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.widthHint = 120;
        m_categoryFilterComboViewer = new ComboViewer(quickSearchBar, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_categoryFilterComboViewer.getCombo().setLayoutData(gridData);
        m_categoryFilterComboViewer.setContentProvider(new CategoryContentProvider());
        m_categoryFilterComboViewer.setInput(new Boolean(true));
        m_categoryFilterComboViewer.getCombo().select(0);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
        m_refreshButton = new Button(quickSearchBar, SWT.PUSH);
        gridData.widthHint = 80;
        m_refreshButton.setSize(30, MainWindow.DEFAULT_BUTTON_HEIGHT - 10);
        m_refreshButton.setLayoutData(gridData);
        m_refreshButton.setText("Re&fresh");
        m_refreshButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                m_displayCriteria.setFilterCriteria(getQuickFilterCriteria());
                refresh();
            }
        }
        );
    }

    protected void createTable()
    {
        super.createTable();

        for (int i=0; i < m_columnArray.length; i++)
        {
            configureNewTableColumn(m_columnArray[i]);
        }

        m_tableViewer.setContentProvider(new TransactionTableContentProvider());
        m_tableViewer.setLabelProvider(new TransactionTableLabelProvider());

        m_tableViewer.getTable().setHeaderVisible(true);
        m_tableViewer.getTable().setLinesVisible(true);

        refresh();
    }

    protected void createButtonBar()
    {
        super.createButtonBar();

        m_newButton.setText("&New " + Transaction.CAPTION_TRANSACTION);
        m_editButton.setText("E&dit " + Transaction.CAPTION_TRANSACTION);

        ((RowData) m_deleteButton.getLayoutData()).width = 110;
        m_deleteButton.setText("&Delete " + Transaction.CAPTION_TRANSACTION);

        m_cloneButton.setText("&Clone " + Transaction.CAPTION_TRANSACTION);
        m_cloneButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                cloneTransaction();
            }
        }
        );
    }

    protected Button createButton(Composite parent, int iId, String strLabel)
    {
        Button      button = super.createButton(parent, iId, strLabel);

        if (iId == BUTTON_NEW)
        {
            m_cloneButton = createButton(parent, BUTTON_CLONE, "&Clone " + Transaction.CAPTION_TRANSACTION);
        }

        return (button);
    }

    private Transaction getSelectedTransaction()
    {
        Transaction             selectedTransaction = null;
        IStructuredSelection    selection = (IStructuredSelection) m_tableViewer.getSelection();

        if (selection != null)
        {
            RowWrapper  row = (RowWrapper) selection.getFirstElement();

            if (row != null)
                selectedTransaction = (Transaction) row.getData();
        }

        if (selectedTransaction == null)
        {
            MessageDialog.openError(getShell(), "No Selection", "Please select a " + Transaction.CAPTION_TRANSACTION + " from the list");
        }

        return (selectedTransaction);
    }

    protected void newEntry()
    {
        TransactionDialog       transactionDialog = new TransactionDialog(getShell());
        int                     iRetCode = transactionDialog.newItem();

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    protected void deleteEntry()
    {
        Transaction             selectedTransaction = getSelectedTransaction();

        if (selectedTransaction == null)
            return;

        if (!GUIUtils.getDeleteConfirmation(getShell(), Transaction.CAPTION_TRANSACTION))
            return;

        try
        {
            m_transactionBean.deleteTransaction(selectedTransaction);
            m_tableViewer.refresh();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "No Selection", ex.getMessage());
        }
    }

    protected void editEntry()
    {
        Transaction             selectedTransaction = getSelectedTransaction();

        if (selectedTransaction == null)
            return;

        TransactionDialog       transactionDialog = new TransactionDialog(getShell());
        int                     iRetCode = transactionDialog.openItem(selectedTransaction);

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    protected void cloneTransaction()
    {
        Transaction             selectedTransaction = getSelectedTransaction();

        if (selectedTransaction == null)
            return;

        TransactionDialog       transactionDialog = new TransactionDialog(getShell());
        int                     iRetCode = transactionDialog.newItem(selectedTransaction);

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    private FilterCriteria[] getQuickFilterCriteria()
    {
        ArrayList               filterList = new ArrayList();
        FilterCriteria[]        filterCriteriaArray = null;
        IStructuredSelection    selection;
//      FilterCriteria          filterCriteria;

        selection = (IStructuredSelection) m_dateFilterComboViewer.getSelection();
        DateFilterType          dateFilterType = (DateFilterType) selection.getFirstElement();

        if (!dateFilterType.equals(DateFilterType.DATE_FILTER_ALL))
        {
            ReportBusinessLogicBean         reportBean = new ReportBusinessLogicBean();
            FilterCriteria[]                dateCriteria = reportBean.getDateCriteria(dateFilterType, null, null); 
            filterList.addAll(Arrays.asList(dateCriteria));
        }

        selection = (IStructuredSelection) m_transTypeFilterComboViewer.getSelection();
        TransactionType     transactionType = (TransactionType) selection.getFirstElement();

        if (!transactionType.equals(TransactionType.ALL_TYPES))
        {
            filterList.add(new FilterCriteria(Transaction.FIELD_TYPE.id, FilterCriteria.EQUALS, transactionType));
        }

        selection = (IStructuredSelection) m_categoryFilterComboViewer.getSelection();
        Category            category = (Category) selection.getFirstElement();

        if (!category.equals(Category.ALL_CATEGORIES))
        {
            filterList.add(new FilterCriteria(Transaction.FIELD_CATEGORY.id, FilterCriteria.EQUALS, category));
        }

        if (filterList.size() > 0)
        {
            filterCriteriaArray = new FilterCriteria[filterList.size()];
            filterList.toArray(filterCriteriaArray);
        }

        return (filterCriteriaArray);
    }

    public void printPreview()
    {
        PrintEngineRunnableWrapper  peRunnable;
        TransactionPrintData        printData = new TransactionPrintData(m_displayCriteria);
        Hashtable                   reportProperties = new Hashtable();
        CompanyBusinessLogicBean    companyBean = new CompanyBusinessLogicBean();
        Company                     company;

        try
        {
            company = companyBean.getCompany();

            reportProperties.put(Report.PROPERTY_COMPANY_NAME, company.name);
            reportProperties.put(Report.PROPERTY_TITLE, "Transaction Ledger");

            // All this nonsense with the "Runnable Wrapper" is to
            // allow us to show an hourglass (or the equivalent icon)
            // while the Report Engine is running the report.

            peRunnable = new PrintEngineRunnableWrapper(printData, TRANSACTION_REPORT_DEFINITION_FILE, reportProperties);
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

    public void print()
    {
        printPreview();
    }
}
