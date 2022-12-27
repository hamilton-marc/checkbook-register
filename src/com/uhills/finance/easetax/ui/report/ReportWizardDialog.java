/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import org.eclipse.jface.wizard.*;
import org.eclipse.swt.widgets.*;

import com.uhills.finance.easetax.core.Report;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportWizardDialog extends WizardDialog
{

    /**
     * @param arg0
     * @param arg1
     */
    public ReportWizardDialog(Shell parentShell)
    {
        super(parentShell, new ReportWizard());
    }

    protected void nextPressed()
    {
        ReportWizard        wizard = (ReportWizard) getWizard();
        boolean             bProceed = wizard.performNext(getCurrentPage());

        if (bProceed)
            super.nextPressed();
    }

    public Report getReport()
    {
        return ((ReportWizard) getWizard()).getReport();
    }
}
