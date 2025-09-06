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
public class ReportCategoryPage extends WizardPage implements IExtendedWizardPage
{
    private ReportCategoryComposite     m_reportCategoryComposite;

    /**
     * @param arg0
     */
    public ReportCategoryPage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Account Criteria");
        setDescription("You can optionally filter the accounts that are shown in the report");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

        m_reportCategoryComposite = new ReportCategoryComposite(parent);

        setControl(m_reportCategoryComposite);
        setPageComplete(m_reportCategoryComposite.isPageComplete());
    }

    public void validatePage() throws ValidationException
    {
        m_reportCategoryComposite.validate();
    }
}
