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
public class FilePrintPreviewAction extends Action
{
    private MainWindow          m_mainWindow;

    public FilePrintPreviewAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("Print Preview@Ctrl+Shift+P");
        setToolTipText("Print Preview");
    }

    public void run()
    {
        m_mainWindow.getApplicationController().getCurrentForm().printPreview();
    }
}
