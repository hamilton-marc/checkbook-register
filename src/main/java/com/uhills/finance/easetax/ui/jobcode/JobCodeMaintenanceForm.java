/*
 * Created on Jun 26, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.jobcode;

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
public final class JobCodeMaintenanceForm extends MaintenanceForm
{
    public static final ColumnDefinition        COLUMN_CODE         = new ColumnDefinition(0, JobCode.FIELD_CODE.caption, 100, SWT.LEFT, JobCode.FIELD_CODE.id);
    public static final ColumnDefinition        COLUMN_DESCRIPTION  = new ColumnDefinition(1, JobCode.FIELD_DESCRIPTION.caption, 300, SWT.LEFT, JobCode.FIELD_DESCRIPTION.id);

    private static final ColumnDefinition[]     m_columnArray =
    {
        COLUMN_CODE,
        COLUMN_DESCRIPTION
    };

    private JobCodeBusinessLogicBean            m_jobCodeBean;

    public JobCodeMaintenanceForm(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);
    }

    public static int getColumnCount()
    {
        return (m_columnArray.length);
    }

    protected void initializeForm()
    {
        m_jobCodeBean = new JobCodeBusinessLogicBean();

        setInitialDisplayCriteria();
        super.initializeForm();
    }

    private void setInitialDisplayCriteria()
    {
        SortCriteria[]      sortCriteria = { new SortCriteria(JobCode.FIELD_CODE.id) };

        m_displayCriteria = new DisplayCriteria(null, sortCriteria);
    }

    public String getText()
    {
        return (JobCode.CAPTION_JOB_CODE + " List");
    }

    public Image getImage()
    {
        return (ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_JOB_CODES));
    }

    protected void createTable()
    {
//      TableColumn         tableColumn;

        super.createTable();

        for (int i = 0; i < m_columnArray.length; i++)
        {
            configureNewTableColumn(m_columnArray[i]);
        }

        m_tableViewer.setContentProvider(new JobCodeTableContentProvider());
        m_tableViewer.setLabelProvider(new JobCodeTableLabelProvider());

        refresh();
    }

    protected void createButtonBar()
    {
        super.createButtonBar();

        m_newButton.setText("&New " + JobCode.CAPTION_JOB_CODE);
        m_editButton.setText("&Edit " + JobCode.CAPTION_JOB_CODE);
        m_deleteButton.setText("&Delete " + JobCode.CAPTION_JOB_CODE);
    }

    private JobCode getSelectedJobCode()
    {
        JobCode                 selectedJobCode = null;
        IStructuredSelection    selection = (IStructuredSelection) m_tableViewer.getSelection();

        if (selection != null)
            selectedJobCode = (JobCode) selection.getFirstElement();

        if (selectedJobCode == null)
        {
            MessageDialog.openError(getShell(), "No Selection", "Please select a " + JobCode.CAPTION_JOB_CODE + " from the list");
        }

        return (selectedJobCode);
    }

    protected void newEntry()
    {
        JobCodeDialog       jobCodeDialog = new JobCodeDialog(getShell());
        int                 iRetCode = jobCodeDialog.newItem();

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }

    protected void deleteEntry()
    {
        JobCode             selectedJobCode = getSelectedJobCode();

        if (selectedJobCode == null)
            return;

        if (!GUIUtils.getDeleteConfirmation(getShell(), JobCode.CAPTION_JOB_CODE))
            return;

        try
        {
            m_jobCodeBean.deleteJobCode(selectedJobCode);
            m_tableViewer.refresh();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "No Selection", ex.getMessage());
        }
    }

    protected void editEntry()
    {
        JobCode             selectedJobCode = getSelectedJobCode();

        if (selectedJobCode == null)
            return;

        JobCodeDialog       jobCodeDialog = new JobCodeDialog(getShell());
        int                 iRetCode = jobCodeDialog.openItem(selectedJobCode);

        if (iRetCode == Window.OK)
            m_tableViewer.refresh();
    }
}
