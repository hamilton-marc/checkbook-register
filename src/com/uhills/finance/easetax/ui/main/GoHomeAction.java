/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.swt.SWT;
import org.eclipse.jface.action.Action;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GoHomeAction extends Action
{
    protected MainWindow        m_mainWindow;

    public GoHomeAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("&Home@Alt+Home");
        setToolTipText("Go Home");
        setAccelerator(SWT.ALT | SWT.HOME);
    }

    public void run()
    {
        m_mainWindow.getApplicationController().goHome();
    }
}
