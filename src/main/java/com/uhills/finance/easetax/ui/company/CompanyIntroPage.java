/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.company;

import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;

import com.uhills.finance.easetax.graphics.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompanyIntroPage extends WizardPage
{
    /**
     * @param arg0
     */
    public CompanyIntroPage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Company Setup Wizard");
        setDescription("Use this wizard to setup your company");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

//      System.out.println(parent.getLayout());

        GridLayout          outerGrid = new GridLayout(2, false);
        Composite           container = new Composite(parent, SWT.NO_FOCUS);

        outerGrid.marginWidth = 5;
        outerGrid.marginHeight = 5;
        outerGrid.horizontalSpacing = 20;
        outerGrid.verticalSpacing = 0;

        container.setLayout(outerGrid);

        Label               imageLabel, textLabel;
        GridData            gridData;

        gridData = new GridData(GridData.VERTICAL_ALIGN_FILL);
        imageLabel = new Label(container, SWT.LEFT);
        imageLabel.setLayoutData(gridData);
        imageLabel.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_LEDGER_WIZARD));

        gridData = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gridData.widthHint = 250;
        textLabel = new Label(container, SWT.LEFT | SWT.WRAP);
        textLabel.setLayoutData(gridData);
        textLabel.setText("Welcome to the Company Setup Wizard.  " +
                          "You will be guided through a series of " +
                          "steps to enter the information for your " +
                          "business.\n\n" +
                          "Once you've completed these steps, you " +
                          "may then begin entering the appropriate " +
                          "revenue and expense transactions into " +
                          "the account ledger.\n\n" +
                          "To continue, click the \"Next\" button");

        setControl(container);
        setPageComplete(validatePage());
    }

    private boolean validatePage()
    {
        return (true);
    }
}
