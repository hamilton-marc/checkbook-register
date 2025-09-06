/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.uhills.finance.easetax.graphics.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ToolBarHelpAction extends HelpContentsAction
{
    public ToolBarHelpAction(ApplicationWindow w)
    {
        super(w);

        setToolTipText("View on-line help");
        setImageDescriptor(ImageDescriptor.createFromFile(ImagePool.class, ImagePool.IMAGE_FILE_HELP));
    }
}
