/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.Action;

import com.uhills.finance.easetax.ui.preferences.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ViewPreferencesAction extends Action
{
    private MainWindow        m_mainWindow;

    public ViewPreferencesAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("Preferences...");
    }

    public void run()
    {
        PreferencesDialog       dialog = new PreferencesDialog(m_mainWindow.getShell());

        dialog.open();
    }
}
