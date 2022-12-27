/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.ui.help.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class HelpAboutAction extends Action
{
    private ApplicationWindow       m_mainWindow;

    public HelpAboutAction(ApplicationWindow w)
    {
        m_mainWindow = w;
        setText("About " + ApplicationMain.getAppName() + "...");
    }

    public void run()
    {
        AboutDialog     dialog = new AboutDialog(m_mainWindow.getShell());

//      w.setBlockOnOpen(true);
        dialog.open();
    }
}
