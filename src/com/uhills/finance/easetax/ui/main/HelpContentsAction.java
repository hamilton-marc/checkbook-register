/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HelpContentsAction extends Action
{
    private ApplicationWindow       m_mainWindow;

    public HelpContentsAction(ApplicationWindow w)
    {
        m_mainWindow = w;
        setText("Help Contents@Ctrl+H");
        setToolTipText("View on-line help contents");
    }

    public void run()
    {
        MessageDialog.openWarning(m_mainWindow.getShell(), "Under Construction", "Sorry, on-line help is not available in this version.");
    }
}
