/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileCloseCompanyAction extends Action
{
    private ApplicationWindow       m_mainWindow;

    public FileCloseCompanyAction(ApplicationWindow w)
    {
        m_mainWindow = w;
        setText("C&lose Company");
        setToolTipText("Close the company file");
    }

    public void run()
    {
        m_mainWindow.close();
    }
}
