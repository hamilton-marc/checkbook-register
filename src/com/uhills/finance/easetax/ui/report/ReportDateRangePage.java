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

import com.uhills.util.validation.*;

import com.uhills.finance.easetax.ui.common.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportDateRangePage extends WizardPage implements IExtendedWizardPage
{
    private ReportDateRangeComposite    m_reportDateRangeComposite;

    /**
     * @param arg0
     */
    public ReportDateRangePage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Date Range");
        setDescription("Please select the date range for your report");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

        m_reportDateRangeComposite = new ReportDateRangeComposite(parent);

        setControl(m_reportDateRangeComposite);
        setPageComplete(m_reportDateRangeComposite.isPageComplete());
    }

    public void validatePage() throws ValidationException
    {
        m_reportDateRangeComposite.validate();
    }
}
