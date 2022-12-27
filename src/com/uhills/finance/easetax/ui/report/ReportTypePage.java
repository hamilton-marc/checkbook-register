/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import org.eclipse.swt.widgets.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;

import com.uhills.util.validation.*;

import com.uhills.finance.easetax.ui.common.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportTypePage extends WizardPage implements IExtendedWizardPage
{
    private ReportTypeComposite     m_reportTypeComposite;

    /**
     * @param arg0
     */
    public ReportTypePage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Report Type");
        setDescription("Please choose the appropriate type for your report");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

        m_reportTypeComposite = new ReportTypeComposite(parent);

        m_reportTypeComposite.getReportTypeListViewer().addSelectionChangedListener(new ISelectionChangedListener()
        {
            public void selectionChanged(SelectionChangedEvent event)
            {
                setPageComplete(m_reportTypeComposite.isPageComplete());
            }
        }
        );

        setControl(m_reportTypeComposite);
        setPageComplete(m_reportTypeComposite.isPageComplete());
    }

    public void validatePage() throws ValidationException
    {
        m_reportTypeComposite.validate();
    }
}
