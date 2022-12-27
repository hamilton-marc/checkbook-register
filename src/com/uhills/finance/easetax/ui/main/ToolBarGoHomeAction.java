/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.resource.ImageDescriptor;

import com.uhills.finance.easetax.graphics.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ToolBarGoHomeAction extends GoHomeAction
{
    public ToolBarGoHomeAction(MainWindow w)
    {
        super(w);
        setImageDescriptor(ImageDescriptor.createFromFile(ImagePool.class, ImagePool.IMAGE_FILE_HOME));
    }

    public void run()
    {
        m_mainWindow.getApplicationController().goHome();
    }
}
