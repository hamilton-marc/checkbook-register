/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.company;

import org.eclipse.jface.wizard.*;
import org.eclipse.swt.widgets.*;

import com.uhills.finance.easetax.core.Company;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompanyWizardDialog extends WizardDialog
{

    /**
     * @param arg0
     * @param arg1
     */
    public CompanyWizardDialog(Shell parentShell)
    {
        super(parentShell, new CompanyWizard());
    }

    public Company getCompany()
    {
        CompanyWizard       wizard = (CompanyWizard) getWizard();

        return (wizard.getCompany());
    }

    public int newCompany()
    {
        CompanyWizard       wizard = (CompanyWizard) getWizard();

        wizard.newCompany();
        return (open());
    }

    public int editCompany()
    {
        CompanyWizard       wizard = (CompanyWizard) getWizard();

        wizard.editCompany();
        return (open());
    }
}
