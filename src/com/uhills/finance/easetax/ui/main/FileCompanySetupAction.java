/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

import com.uhills.finance.easetax.ui.company.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileCompanySetupAction extends Action
{
    private ApplicationWindow       m_mainWindow;

    public FileCompanySetupAction(ApplicationWindow w)
    {
        m_mainWindow = w;
        setText("Company Setup");
        setToolTipText("Setup the parameters for your company");
    }

    public void run()
    {
        CompanyWizardDialog     companySetupWizard = new CompanyWizardDialog(m_mainWindow.getShell());

        companySetupWizard.editCompany();
    }
}
