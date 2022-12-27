/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
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
public class ReportNamePage extends WizardPage implements IExtendedWizardPage
{
    private ReportNameComposite     m_reportNameComposite;

    /**
     * @param arg0
     */
    public ReportNamePage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Report Name");
        setDescription("Please choose a name and description for your report");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

        m_reportNameComposite = new ReportNameComposite(parent);

        m_reportNameComposite.getNameText().addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                setPageComplete(m_reportNameComposite.isPageComplete());
            }
        }
        );

        setControl(m_reportNameComposite);
        setPageComplete(m_reportNameComposite.isPageComplete());
    }

    public void validatePage() throws ValidationException
    {
        m_reportNameComposite.validate();
    }
}
