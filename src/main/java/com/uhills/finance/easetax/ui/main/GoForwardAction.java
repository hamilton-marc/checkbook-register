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
public class GoForwardAction extends Action
{
    protected MainWindow            m_mainWindow;

    public GoForwardAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("&Forward@Alt+Right Arrow");
        setToolTipText("Go Forward");
        setAccelerator(SWT.ALT | SWT.ARROW_RIGHT);
    }

    public void run()
    {
        m_mainWindow.getApplicationController().goForward();
    }
}
