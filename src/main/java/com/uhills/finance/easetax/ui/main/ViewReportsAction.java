/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.Action;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ViewReportsAction extends Action
{
    private MainWindow        m_mainWindow;

    public ViewReportsAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("Reports");
        setToolTipText("View reports");
    }

    public void run()
    {
        m_mainWindow.getApplicationController().switchFunction(ApplicationController.FUNCTION_REPORTS);
    }
}
