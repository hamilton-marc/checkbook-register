/*
 * Created on Jun 30, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.dialogs.*;

import com.uhills.finance.easetax.ui.main.*;
import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class MaintenanceForm extends Composite
{
    public static final int         BUTTON_NEW      = 1;
    public static final int         BUTTON_EDIT     = 2;
    public static final int         BUTTON_DELETE   = 3;

    protected TableViewer           m_tableViewer;
    protected Button                m_newButton;
    protected Button                m_editButton;
    protected Button                m_deleteButton;
    protected DisplayCriteria       m_displayCriteria;

    public MaintenanceForm(Composite parent, int iStyle)
    {
        super(parent, iStyle);

        initializeForm();
    }

    protected void initializeForm()
    {
        GridLayout          outerGrid = new GridLayout(1, false);

        outerGrid.marginWidth = 0;
        outerGrid.marginHeight = 0;
        outerGrid.horizontalSpacing = 0;
        outerGrid.verticalSpacing = 2;

        setLayout(outerGrid);
        createTable();
        createButtonBar();
        establishDefaultListeners();
    }

    protected void createTable()
    {
        GridData            gridData = new GridData(GridData.FILL_BOTH);
//      TableColumn         tableColumn;

        m_tableViewer = new TableViewer(this, SWT.BORDER | SWT.FLAT | SWT.FULL_SELECTION);
        m_tableViewer.getTable().setLayoutData(gridData);

        m_tableViewer.getTable().setHeaderVisible(true);
        m_tableViewer.getTable().setLinesVisible(false);
    }

    protected void createButtonBar()
    {
        GridData            gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_END);
        Composite           buttonBar = new Composite(this, SWT.NO_FOCUS);
        RowLayout           rowLayout = new RowLayout(SWT.HORIZONTAL);

        gridData.horizontalIndent = 0;

        buttonBar.setLayout(rowLayout);
        buttonBar.setLayoutData(gridData);
        buttonBar.setBackground(getParent().getBackground());

        rowLayout.spacing = 5;
        rowLayout.marginTop = 3;
        rowLayout.marginBottom = 3;
        rowLayout.wrap = false;

        m_newButton = createButton(buttonBar, BUTTON_NEW, "&New");
        m_editButton = createButton(buttonBar, BUTTON_EDIT, "&Edit");
        m_deleteButton = createButton(buttonBar, BUTTON_DELETE, "&Delete");
    }

    protected Button createButton(Composite parent, int iId, String strLabel)
    {
        Button          button = new Button(parent, SWT.PUSH);

        button.setLayoutData(new RowData(100, MainWindow.DEFAULT_BUTTON_HEIGHT));
        button.setText(strLabel);

        return (button);
    }

    protected void establishDefaultListeners()
    {
        m_newButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                newEntry();
            }
        }
        );

        m_editButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                editEntry();
            }
        }
        );

        m_deleteButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                deleteEntry();
            }
        }
        );

        m_tableViewer.getTable().addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent event)
            {
                if (event.character == SWT.DEL)
                {
                    deleteEntry();
                }
            }
        }
        );

        m_tableViewer.addDoubleClickListener(new IDoubleClickListener()
        {
            public void doubleClick(DoubleClickEvent e)
            {
                onDoubleClick();
            }
        }
        );
    }

    protected void sortColumn(int iFieldId)
    {
        SortCriteria[]      sortCriteriaArray = m_displayCriteria.getSortCriteria();

        if (sortCriteriaArray != null && sortCriteriaArray.length > 0)
        {
            if (sortCriteriaArray[0].getField() == iFieldId)
            {
                sortCriteriaArray[0].switchDirection();
            }
            else
            {
                sortCriteriaArray[0].sortAscending(iFieldId);
            }
        }
        else
        {
            sortCriteriaArray = new SortCriteria[1];
            sortCriteriaArray[0] = new SortCriteria(iFieldId, SortCriteria.ASCENDING);

            m_displayCriteria.setSortCriteria(sortCriteriaArray);
        }

        refresh();
    }

    protected void configureNewTableColumn(ColumnDefinition columnDef)
    {
        TableColumn         tableColumn = new TableColumn(m_tableViewer.getTable(), columnDef.orientation);

        tableColumn.setText(columnDef.caption);
        tableColumn.setWidth(columnDef.width);
        tableColumn.setData(new Integer(columnDef.field));
        tableColumn.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                Integer             intFieldId = (Integer) e.widget.getData();

                sortColumn(intFieldId.intValue());
            }
        }
        );
    }

    protected abstract void newEntry();
    protected abstract void editEntry();
    protected abstract void deleteEntry();

    protected void onDoubleClick()
    {
        editEntry();
    }

    public void refresh()
    {
        m_tableViewer.setInput(m_displayCriteria);
    }

    public void printPreview()
    {
        MessageDialog.openError(getShell(), "Operation Not Supported", "Sorry, printing is not supported on this view");
    }

    public void print()
    {
        MessageDialog.openError(getShell(), "Operation Not Supported", "Sorry, printing is not supported on this view");
    }

    public abstract String getText();
    public abstract Image getImage();
}
