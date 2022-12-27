/*
 * Created on Jun 21, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.help;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;

import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.main.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class AboutDialog extends TitleAreaDialog
{
    public AboutDialog(Shell parent)
    {
        super(parent);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
    }

    protected Point getInitialSize()
    {
        return (new Point(400, 260));
    }

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText("About " + ApplicationMain.getAppName());
    }

    protected Control createDialogArea(Composite parent)
    {
        Composite           dialogContainer = (Composite) super.createDialogArea(parent);
        Composite           container = new Composite(dialogContainer, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout();
        GridData            gridData = new GridData();

        gridLayout.numColumns = 2;
        gridLayout.marginHeight = 15;

        container.setLayout(gridLayout);

        final Label         imagePlaceHolderLabel = new Label(container, SWT.LEFT | SWT.TOP);
        final Label         aboutCaptionLabel = new Label(container, SWT.LEFT | SWT.WRAP | SWT.TOP);

        imagePlaceHolderLabel.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.horizontalIndent = 10;

        imagePlaceHolderLabel.setLayoutData(gridData);

        aboutCaptionLabel.setText(getAboutCaption());

        gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalIndent = 10;

        aboutCaptionLabel.setLayoutData(gridData);

        setTitle(ApplicationMain.getAppName());
        setMessage("Revenue and expense tracking system for small business");

        return (container);
    }

    private String getAboutCaption()
    {
        Date                expirationDate = ApplicationMain.getExpirationDate();
        StringBuffer        strbufAboutCaption = new StringBuffer();

        strbufAboutCaption.append(ApplicationMain.getAppName());
        strbufAboutCaption.append(" - " + ApplicationMain.getAppEdition() + "\n\n");
        strbufAboutCaption.append("Version: " + ApplicationMain.getAppVersion() + "\n\n");
        strbufAboutCaption.append("Copyright © 2003-2009 " + ApplicationMain.getAppVendor() + " - All rights reserved.\n");

        if (expirationDate != null)
        {
            strbufAboutCaption.append("\nThis version expires on ");
            strbufAboutCaption.append(new SimpleDateFormat("MMMM d, yyyy").format(expirationDate));
        }

        return (strbufAboutCaption.toString());
    }

    protected Button createButton(Composite parent, int iId, String strLabel, boolean bDefaultButton)
    {
        if (iId == IDialogConstants.OK_ID)
        {
            return (super.createButton(parent, iId, "&Close", bDefaultButton));
        }

//      return (super.createButton(parent, iId, strLabel, bDefaultButton));
        return (null);
    }
}
