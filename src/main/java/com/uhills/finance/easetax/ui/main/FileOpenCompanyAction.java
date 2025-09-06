/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.persist.file.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileOpenCompanyAction extends Action
{
    private MainWindow          m_mainWindow;

    public FileOpenCompanyAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("&Open Company File@Ctrl+O");
        setToolTipText("Open a company file");
    }

    public void run()
    {
        String[]                extensions = { "*." + ApplicationMain.APPLICATION_DATA_FILE_EXTENSION };
        FileDialog              openDialog = new FileDialog(m_mainWindow.getShell(), SWT.OPEN);
        String                  strFilePath;

        openDialog.setFilterExtensions(extensions);
        strFilePath = openDialog.open();

        if (strFilePath == null)
            return;

        FilePersistenceManager  fileDB = PersistenceFactory.getInstance().getPersistenceManager();

        try
        {
            fileDB.load(strFilePath);
            ApplicationMain.getAppSettings().dataFile = strFilePath;

            m_mainWindow.resetApplicationState(fileDB.getFile().getName());
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(m_mainWindow.getShell(), "Error", ex.getMessage());
        }
    }
}
