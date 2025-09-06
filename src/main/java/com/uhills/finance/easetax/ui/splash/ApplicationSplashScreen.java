/*
 * Created on Aug 24, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.splash;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.resource.ImageDescriptor;

import com.uhills.finance.easetax.graphics.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApplicationSplashScreen extends Window
{
    private Label           m_imageLabel;

    public ApplicationSplashScreen()
    {
        super((Shell) null);

        setShellStyle(SWT.NO_TRIM | SWT.MODELESS);
        setBlockOnOpen(false);
    }

    protected void configureShell(Shell newShell)
    {
        newShell.setLayout(new FillLayout());
        newShell.forceActive();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent)
    {
        m_imageLabel = new Label(parent, SWT.NO_FOCUS);

        m_imageLabel.setImage(ImageDescriptor.createFromFile(ImagePool.class, ImagePool.IMAGE_FILE_SPLASH).createImage());

        return (m_imageLabel);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
    protected Point getInitialLocation(Point initialSize)
    {
        Rectangle           screenDimensions = Display.getCurrent().getBounds();
        Point               location = new Point(0, 0);

        location.x = screenDimensions.width / 2 - initialSize.x / 2;
        location.y = screenDimensions.height / 2 - initialSize.y / 2;

        return (location);
    }

}
