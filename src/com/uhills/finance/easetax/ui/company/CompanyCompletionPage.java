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
public class CompanyCompletionPage extends WizardPage
{

    private Label               m_completionLabel;

    /**
     * @param arg0
     */
    public CompanyCompletionPage(String strPageName)
    {
        super(strPageName);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent)
    {
        setTitle("Setup Complete");
        setDescription("Click \"Finish\" to complete the setup process");
        setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(TitleAreaDialog.DLG_IMG_TITLE_BANNER));

//      System.out.println(parent.getLayout());

        GridLayout          outerGrid = new GridLayout(2, false);
        Composite           container = new Composite(parent, SWT.NO_FOCUS);

        outerGrid.marginWidth = 5;
        outerGrid.marginHeight = 5;
        outerGrid.horizontalSpacing = 20;
        outerGrid.verticalSpacing = 0;

        container.setLayout(outerGrid);

        Label               imageLabel;
        GridData            gridData;

        gridData = new GridData(GridData.VERTICAL_ALIGN_FILL);
        imageLabel = new Label(container, SWT.LEFT);
        imageLabel.setLayoutData(gridData);
        imageLabel.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_LEDGER_WIZARD));

        gridData = new GridData(GridData.VERTICAL_ALIGN_FILL);
        gridData.widthHint = 250;
        m_completionLabel = new Label(container, SWT.LEFT | SWT.WRAP);
        m_completionLabel.setLayoutData(gridData);
        m_completionLabel.setText(buildCompletionMessage());

        setControl(container);
        setPageComplete(validatePage());
    }

    private String buildCompletionMessage()
    {
        StringBuffer        strbufCompletionMessage = new StringBuffer();

        strbufCompletionMessage.append("You have successfully completed entering " +                                       "the information for your business.  If you need " +                                       "to modify any of the settings, simply use the " +                                       "\"Back\" button to navigate to the previous pages in " +                                       "the wizard.\n\n");

        strbufCompletionMessage.append("To complete the setup process, click \"Finish\"");

        return (strbufCompletionMessage.toString());
    }

    private boolean validatePage()
    {
        return (true);
    }
}
