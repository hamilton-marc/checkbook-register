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

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.ui.common.*;

import com.uhills.util.validation.ValidationException;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportNameComposite extends Composite implements IDataEntryPage
{
    private static final int        MAX_WIDTH = 230;
    private static final int        MIN_LABEL_WIDTH = 80;
/*
    private static final int        SPACER_HEIGHT = 6;
    private static final int        INDENT_WIDTH = 20;
*/
    private Label                   m_nameLabel;
    private Label                   m_descriptionLabel;
    private Text                    m_nameText;
    private Text                    m_descriptionText;
    private Label                   m_reportTypeLabel;
    private Label                   m_reportTypeValueLabel;

    private boolean                 m_bEditMode;

    public ReportNameComposite(Composite parent)
    {
        this(parent, false);
    }

    public ReportNameComposite(Composite parent, boolean bEditMode)
    {
        super(parent, SWT.NONE);

        createControl();
        setEditMode(bEditMode);
    }

    public void setEditMode(boolean bEditMode)
    {
        m_bEditMode = bEditMode;
        m_reportTypeLabel.setVisible(m_bEditMode);
        m_reportTypeValueLabel.setVisible(m_bEditMode);
    }

    public boolean getEditMode()
    {
        return (m_bEditMode);
    }

    public Text getNameText()
    {
        return (m_nameText);
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
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_nameLabel = new Label(this, SWT.LEFT);
        m_nameLabel.setLayoutData(gridData);
        m_nameLabel.setText("Report Name");

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        m_nameText = new Text(this, SWT.LEFT | SWT.BORDER);
        m_nameText.setLayoutData(gridData);
        m_nameText.setTextLimit(Report.FIELD_NAME.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_descriptionLabel = new Label(this, SWT.LEFT);
        m_descriptionLabel.setLayoutData(gridData);
        m_descriptionLabel.setText(Report.FIELD_DESCRIPTION.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        gridData.heightHint = 45;
        m_descriptionText = new Text(this, SWT.LEFT | SWT.BORDER | SWT.WRAP);
        m_descriptionText.setLayoutData(gridData);
        m_descriptionText.setTextLimit(Report.FIELD_DESCRIPTION.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_reportTypeLabel = new Label(this, SWT.LEFT);
        m_reportTypeLabel.setLayoutData(gridData);
        m_reportTypeLabel.setText(Report.FIELD_TYPE.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_WIDTH;
        m_reportTypeValueLabel = new Label(this, SWT.LEFT);
        m_reportTypeValueLabel.setLayoutData(gridData);
    }

    public void validate() throws ValidationException
    {
        if (m_nameText.getText().trim().length() == 0)
        {
            throw new ValidationException ("Please enter a " + Report.FIELD_NAME.caption + " for your " + Report.CAPTION_REPORT);
        }
    }

    public boolean isPageComplete()
    {
        return (m_nameText.getText().trim().length() > 0);
    }

    public void fillFields(Object obj)
    {
        Report      report = (Report) obj;

        m_nameText.setText(report.name);
        m_descriptionText.setText(report.description);
        m_reportTypeValueLabel.setText(report.type.toString());
    }

    public void fillObject(Object obj) throws ValidationException
    {
        validate();

        Report      report = (Report) obj;

        report.name = m_nameText.getText().trim();
        report.description = m_descriptionText.getText().trim();
    }
}
