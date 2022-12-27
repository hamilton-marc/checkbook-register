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
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.ui.category.*;

import com.uhills.util.validation.ValidationException;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportCategoryComposite extends Composite implements IDataEntryPage
{
    private static final int            MAX_WIDTH = 300;
//  private static final int            SPACER_HEIGHT = 6;

    private Label                       m_multiSelectInstructionLabel;
    private Button                      m_allCategoryRadioButton;
    private Button                      m_selectedCategoryRadioButton;
    private ListViewer                  m_categoryListViewer;

    public ReportCategoryComposite(Composite parent)
    {
        super(parent, SWT.NONE);

        createControl();
    }

    private void createControl()
    {
        GridLayout          outerGrid = new GridLayout(1, false);
        GridData            gridData;
//      Label               spacerLabel;

        outerGrid.marginWidth = 30;
        outerGrid.marginHeight = 10;
        outerGrid.horizontalSpacing = 10;
        outerGrid.verticalSpacing = 14;

        setLayout(outerGrid);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        m_allCategoryRadioButton = new Button(this, SWT.RADIO);
        m_allCategoryRadioButton.setLayoutData(gridData);
        m_allCategoryRadioButton.setText("Run the report for all accounts");
        m_allCategoryRadioButton.setSelection(true);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_selectedCategoryRadioButton = new Button(this, SWT.RADIO);
        m_selectedCategoryRadioButton.setLayoutData(gridData);
        m_selectedCategoryRadioButton.setText("Show me only the accounts I select");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalIndent = 20;
        gridData.widthHint = 300;
        m_multiSelectInstructionLabel = new Label(this, SWT.LEFT | SWT.WRAP);
        m_multiSelectInstructionLabel.setLayoutData(gridData);
        m_multiSelectInstructionLabel.setText("(To make multiple selections, hold down the CTRL key while clicking the left mouse button)");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.heightHint = 155;
        gridData.horizontalIndent = 20;
        m_categoryListViewer = new ListViewer(this, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
        m_categoryListViewer.getList().setLayoutData(gridData);
        m_categoryListViewer.setContentProvider(new CategoryContentProvider(false));
        m_categoryListViewer.setInput(new Boolean(false));

        configureEvents();
        toggleCategory();
    }

    private void configureEvents()
    {
        m_allCategoryRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleCategory();
            }
        }
        );

        m_selectedCategoryRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleCategory();
            }
        }
        );
    }

    private Category[] getSelectedCategories()
    {
        Category[]              selectedCategories = null;
        IStructuredSelection    selection = (IStructuredSelection) m_categoryListViewer.getSelection();

        if (selection != null &&
            selection.size() > 0)
        {
            selectedCategories = new Category[selection.size()];
            selection.toList().toArray(selectedCategories);
        }

        return (selectedCategories);
    }

    private void toggleCategory()
    {
        m_categoryListViewer.getList().setEnabled(m_selectedCategoryRadioButton.getSelection());
    }

    public void validate() throws ValidationException
    {
        if (m_selectedCategoryRadioButton.getSelection())
        {
            Category[]          selectedCategories = getSelectedCategories();

            if (selectedCategories == null ||
                selectedCategories.length <= 0)
            {
                throw new ValidationException ("Please select one or more accounts from the list");
            }
        }
    }

    public boolean isPageComplete()
    {
        return (m_allCategoryRadioButton.getSelection() ||
                m_selectedCategoryRadioButton.getSelection());
    }

    public void fillFields(Object obj)
    {
        Report      report = (Report) obj;
        Category[]  categories = report.getCategories();

        m_allCategoryRadioButton.setSelection(false);
        m_selectedCategoryRadioButton.setSelection(false);

        if (categories == null ||
            categories.length == 0)
        {
            m_allCategoryRadioButton.setSelection(true);
        }
        else
        {
            m_selectedCategoryRadioButton.setSelection(true);
            m_categoryListViewer.setSelection(new StructuredSelection(categories));
        }

        toggleCategory();
    }

    public void fillObject(Object obj) throws ValidationException
    {
        validate();

        Report      report = (Report) obj;

        if (m_selectedCategoryRadioButton.getSelection())
        {
            Category[]          selectedCategories = getSelectedCategories();

            report.setCategories(selectedCategories);
        }
    }
}
