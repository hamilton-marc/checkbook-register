/*
 * Created on Jul 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import org.eclipse.jface.resource.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;

import com.uhills.util.validation.*;

import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.ui.common.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportIntroPage extends WizardPage implements IExtendedWizardPage
{
    /**
     * @param arg0
     */
    public ReportIntroPage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Report Setup Wizard");
        setDescription("Use this wizard to setup your report");
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
        imageLabel.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_REPORT_WIZARD));

        gridData = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gridData.widthHint = 250;
        textLabel = new Label(container, SWT.LEFT | SWT.WRAP);
        textLabel.setLayoutData(gridData);
        textLabel.setText("Welcome to the Report Setup Wizard.  " +
                          "You will be guided through a series of " +
                          "steps to enter the information for your " +
                          "report.\n\n" +
                          "Once you've completed these steps, you " +
                          "will be given the opportunity to run the " +
                          "report.  You can also optionally save the " +
                          "settings for your new report so you can quickly " +
                          "recall them at a later time.\n\n" +
                          "To continue, click the \"Next\" button");

        setControl(container);
        setPageComplete(verifyPageComplete());
    }

    private boolean verifyPageComplete()
    {
        return (true);
    }

    public void validatePage() throws ValidationException
    {
    }
}
