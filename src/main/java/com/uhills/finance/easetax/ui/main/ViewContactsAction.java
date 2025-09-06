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
public class ViewContactsAction extends Action
{
    private MainWindow        m_mainWindow;

    public ViewContactsAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("Contacts");
        setToolTipText("View contacts");
    }

    public void run()
    {
        m_mainWindow.getApplicationController().switchFunction(ApplicationController.FUNCTION_CONTACTS);
    }
}
