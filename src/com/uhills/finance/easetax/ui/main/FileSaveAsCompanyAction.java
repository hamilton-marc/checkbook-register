/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import java.io.File;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.uhills.util.exception.PersistenceException;

import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.persist.file.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileSaveAsCompanyAction extends Action
{
    private MainWindow          m_mainWindow;

    public FileSaveAsCompanyAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("&Save As@Ctrl+S");
        setToolTipText("Save company file as...");
    }

    public void run()
    {
        String                      strFilePath = "";
        FilePersistenceManager      fileDB = PersistenceFactory.getInstance().getPersistenceManager();

        if (fileDB.getFile() == null)
        {
            if ((strFilePath = promptForFilePath()) == null)
                return;

            fileDB.setFile(new File(strFilePath));
        }

        m_mainWindow.setFileName(fileDB.getFile());

        try
        {
            fileDB.save();
  
            ApplicationMain.getAppSettings().dataFile = strFilePath;
            m_mainWindow.setFileName(fileDB.getFile());
            m_mainWindow.refresh();
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(m_mainWindow.getShell(), ApplicationMain.getAppName() + " Error", ex.getMessage());
        }
    }

    private String promptForFilePath()
    {
        String[]            extensions = { ApplicationMain.APPLICATION_DATA_FILE_EXTENSION };               
        FileDialog          openDialog = new FileDialog(m_mainWindow.getShell(), SWT.SAVE);
        String              strFilePath;

        openDialog.setFilterExtensions(extensions);
        strFilePath = openDialog.open();

        return (strFilePath);
    }
}
