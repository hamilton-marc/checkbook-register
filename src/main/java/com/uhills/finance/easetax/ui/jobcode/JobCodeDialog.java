/*
 * Created on Jun 21, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.jobcode;

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
 * @author HamiltonM
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class JobCodeDialog extends TitleAreaDialog
{
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_LABEL_WIDTH = 90;

    private Label                           m_codeLabel;
    private Text                            m_codeText;
    private Label                           m_descriptionLabel;
    private Text                            m_descriptionText;

    private JobCodeBusinessLogicBean        m_jobCodeBean;
    private JobCode                         m_jobCode;
    private boolean                         m_bFillFields;

    public JobCodeDialog(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
        m_jobCodeBean = new JobCodeBusinessLogicBean();
    }

    public int newItem()
    {
        return (open());
    }

    public int openItem(Object entry)
    {
        if (entry == null) return (CANCEL);

        JobCode      jobCode = (JobCode) entry;

        return (openItem(jobCode.getID()));
    }

    public int openItem(long lJobCodeId)
    {
        m_bFillFields = true;

        try
        {
            getJobCode(lJobCodeId);
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
            return (CANCEL);
        }
        catch (ItemNotFoundException ex)
        {
            MessageDialog.openError(getParentShell(), "Account Entry", ex.getMessage());
            return (CANCEL);
        }

        return (open());
    }

    public JobCode getJobCode()
    {
        return (m_jobCode);
    }

    private void getJobCode(long lJobCodeId) throws PersistenceException, ItemNotFoundException
    {
        m_jobCode = m_jobCodeBean.getJobCode(lJobCodeId);

        if (m_jobCode == null)
        {
            throw new ItemNotFoundException("Unable to find that job code in the database.  " +
                                            "It may have already been deleted");
        }
    }

    private void fillFields()
    {
        m_codeText.setText(m_jobCode.code);
        m_descriptionText.setText(m_jobCode.description);
    }

    private JobCode getJobCodeFromForm() throws ValidationException
    {
        JobCode          jobCode = new JobCode();

        jobCode.initialize();

        if (m_jobCode != null)
        {
            jobCode.setID(m_jobCode.getID());
        }

        jobCode.code = m_codeText.getText();
        jobCode.description = m_descriptionText.getText();

        return (jobCode);
    }

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText(JobCode.CAPTION_JOB_CODE + " Entry");
    }

    protected Control createDialogArea(Composite parent)
    {
        String              strTitle, strMessage;
        Composite           dialogContainer = (Composite) super.createDialogArea(parent);
        Composite           container = new Composite(dialogContainer, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout();

        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 8;
        gridLayout.horizontalSpacing = 5;
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        createJobCodeInfoArea(container);

        if (m_bFillFields)
        {
            strTitle = "Edit " + JobCode.CAPTION_JOB_CODE;
            strMessage = "You can modify the job code in the fields below";

            fillFields();
        }
        else
        {
            strTitle = "New " + JobCode.CAPTION_JOB_CODE;
            strMessage = "Enter the information for the new job code in the fields below";
        }

        setTitle(strTitle);
        setMessage(strMessage);

        return (container);
    }

    private void createJobCodeInfoArea(Composite parent)
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(parent, SWT.NO_FOCUS);

        gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_BOTH);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText(JobCode.CAPTION_JOB_CODE + " Information");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_codeLabel = new Label(container, SWT.LEFT);
        m_codeLabel.setLayoutData(gridData);
        m_codeLabel.setText(JobCode.FIELD_CODE.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = (int) (MAX_TEXT_WIDTH * 0.4);
        m_codeText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_codeText.setLayoutData(gridData);
        m_codeText.setTextLimit(JobCode.FIELD_CODE.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_descriptionLabel = new Label(container, SWT.LEFT);
        m_descriptionLabel.setLayoutData(gridData);
        m_descriptionLabel.setText(JobCode.FIELD_DESCRIPTION.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_descriptionText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_descriptionText.setLayoutData(gridData);
        m_descriptionText.setTextLimit(JobCode.FIELD_DESCRIPTION.maxLength);
    }

    protected void okPressed()
    {
        try
        {
            m_jobCode = getJobCodeFromForm();
            m_jobCodeBean.saveJobCode(m_jobCode);

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
