/*
 * Created on Jun 26, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.category;


import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.window.*;

import com.uhills.util.exception.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.biz.*;
import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class CategoryMaintenanceForm extends MaintenanceForm
{
    public static final ColumnDefinition        COLUMN_NUMBER = new ColumnDefinition(0, Category.FIELD_NUMBER.caption, 100, SWT.LEFT, Category.FIELD_NUMBER.id);
    public static final ColumnDefinition        COLUMN_NAME = new ColumnDefinition(1, Category.FIELD_NAME.caption, 200, SWT.LEFT, Category.FIELD_NAME.id);
    public static final ColumnDefinition        COLUMN_TYPE = new ColumnDefinition(2, Category.FIELD_TYPE.caption, 120, SWT.LEFT, Category.FIELD_TYPE.id);
//  public static final ColumnDefinition        COLUMN_DESCRIPTION = new ColumnDefinition(3, Category.FIELD_DESCRIPTION.caption, 100, SWT.LEFT, Category.FIELD_DESCRIPTION.id);
    public static final ColumnDefinition        COLUMN_FORM_1040 = new ColumnDefinition(3, Category.FIELD_FORM_1040.caption, 70, SWT.LEFT, Category.FIELD_FORM_1040.id);
    public static final ColumnDefinition        COLUMN_FORM_1120 = new ColumnDefinition(4, Category.FIELD_FORM_1120.caption, 70, SWT.LEFT, Category.FIELD_FORM_1120.id);
    public static final ColumnDefinition        COLUMN_FORM_1120S = new ColumnDefinition(5, Category.FIELD_FORM_1120S.caption, 70, SWT.LEFT, Category.FIELD_FORM_1120S.id);
    public static final ColumnDefinition        COLUMN_FORM_1065 = new ColumnDefinition(6, Category.FIELD_FORM_1065.caption, 70, SWT.LEFT, Category.FIELD_FORM_1065.id);

    private static final ColumnDefinition[]     m_columnArray =
    {
        COLUMN_NUMBER,
        COLUMN_NAME,
        COLUMN_TYPE,
/*
        For normal users, we probably don't want them to
        see the line items for the tax forms.  This is
        the job of the accountant.
*/
        COLUMN_FORM_1040,
        COLUMN_FORM_1120,
        COLUMN_FORM_1120S,
        COLUMN_FORM_1065
    };

    private CategoryBusinessLogicBean           m_categoryBean;

    public CategoryMaintenanceForm(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);
    }

    protected void initializeForm()
    {
        m_categoryBean = new CategoryBusinessLogicBean();

        setInitialDisplayCriteria();
        super.initializeForm();
    }

    private void setInitialDisplayCriteria()
    {
        SortCriteria[]      sortCriteria = { new SortCriteria(Category.FIELD_NUMBER.id) };

        m_displayCriteria = new DisplayCriteria(null, sortCriteria);
    }

    public String getText()
    {
        return ("Chart of Accounts");
    }

    public Image getImage()
    {
        return (ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_CATEGORIES));
    }

    protected void createTable()
    {
//      TableColumn         tableColumn;

        super.createTable();

        for (int i = 0; i < m_columnArray.length; i++)
        {
            configureNewTableColumn(m_columnArray[i]);
        }

        m_tableViewer.setContentProvider(new CategoryTableContentProvider());
        m_tableViewer.setLabelProvider(new CategoryTableLabelProvider());
/*
        Note:

        For normal users, we probably don't want them to
        we probably don't want them to manipulate the
        chart of accounts
*/
        refresh();
    }

/*
    protected void createButtonBar()
    {
        For normal users, we probably don't want them to
        we probably don't want them to manipulate the
        chart of accounts
    }
*/

    protected void createButtonBar()
    {
        super.createButtonBar();

        m_newButton.setText("&New " + Category.CAPTION_CATEGORY);
        m_editButton.setText("&Edit " + Category.CAPTION_CATEGORY);
        m_deleteButton.setText("&Delete " + Category.CAPTION_CATEGORY);
    }

    private Category getSelectedCategory()
    {
        Category                selectedCategory = null;
        IStructuredSelection    selection = (IStructuredSelection) m_tableViewer.getSelection();

        if (selection != null)
            selectedCategory = (Category) selection.getFirstElement();

        if (selectedCategory == null)
        {
            MessageDialog.openError(getShell(), "No Selection", "Please select a " + Category.CAPTION_CATEGORY + " from the list");
        }

        return (selectedCategory);
    }

    protected void newEntry()
    {
        CategoryDialog      categoryDialog = new CategoryDialog(getShell());
        int                 iRetCode = categoryDialog.newItem();

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    protected void deleteEntry()
    {
        Category            selectedCategory = getSelectedCategory();

        if (selectedCategory == null)
            return;

        if (!GUIUtils.getDeleteConfirmation(getShell(), Category.CAPTION_CATEGORY))
            return;

        try
        {
            m_categoryBean.deleteCategory(selectedCategory);
            m_tableViewer.refresh();
        }
        catch (CategoryInUseException ex)
        {
            MessageDialog.openError(getShell(), "Category In Use", ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "No Selection", ex.getMessage());
        }
    }

    protected void editEntry()
    {
        Category             selectedCategory = getSelectedCategory();

        if (selectedCategory == null)
            return;

        CategoryDialog      categoryDialog = new CategoryDialog(getShell());
        int                 iRetCode = categoryDialog.openItem(selectedCategory);

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }
}
