/*
 * Created on Jun 26, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.task;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.ui.common.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class TaskMaintenanceForm extends MaintenanceForm
{
    public static final int         BUTTON_COMPLETE     = 4;
    private Button                  m_markCompleteButton;

    public TaskMaintenanceForm(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);
    }

    public String getText()
    {
        return ("Task List");
    }

    public Image getImage()
    {
        return (ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_TASKS));
    }

    protected void createTable()
    {
        TableColumn         tableColumn;

        super.createTable();

        tableColumn = new TableColumn(m_tableViewer.getTable(), SWT.LEFT);
        tableColumn.setText("Done");
        tableColumn.setWidth(50);

        tableColumn = new TableColumn(m_tableViewer.getTable(), SWT.LEFT);
        tableColumn.setText("Description");
        tableColumn.setWidth(200);

        tableColumn = new TableColumn(m_tableViewer.getTable(), SWT.LEFT);
        tableColumn.setText("Priority");
        tableColumn.setWidth(100);

        tableColumn = new TableColumn(m_tableViewer.getTable(), SWT.LEFT);
        tableColumn.setText("Due Date");
        tableColumn.setWidth(150);
    }

    protected void createButtonBar()
    {
        super.createButtonBar();

        m_newButton.setText("&New Task");
        m_editButton.setText("&Edit Task");
        m_deleteButton.setText("&Delete Task");
    }

    protected Button createButton(Composite parent, int iId, String strLabel)
    {
        if (iId == BUTTON_DELETE)
        {
            m_markCompleteButton = createButton(parent, BUTTON_COMPLETE, "&Mark Complete");
        }

        return (super.createButton(parent, BUTTON_COMPLETE, strLabel));
    }

    protected void newEntry()
    {
    }

    protected void editEntry()
    {
    }

    protected void deleteEntry()
    {
    }
}
