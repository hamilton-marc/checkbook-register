/*
 * Created on June 20, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.uhills.util.exception.*;
import com.uhills.util.validation.*;
import com.uhills.util.text.SimpleSearchReplace;

import com.uhills.finance.easetax.core.Company;
import com.uhills.finance.easetax.biz.CompanyBusinessLogicBean;
import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.persist.file.*;
import com.uhills.finance.easetax.ui.company.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileNewCompanyAction extends Action
{
//  private static int          m_iNewFileCount;
    private MainWindow          m_mainWindow;

    public FileNewCompanyAction(MainWindow w)
    {
        m_mainWindow = w;
        setText("New Company File@Ctrl+N");
        setToolTipText("Create a new company file");
    }

    public void run()
    {
//      int                         iReturnCode;
        String                      strFilePath;
        Company                     company;
        CompanyBusinessLogicBean    companyBean;
        SimpleSearchReplace         searchReplace;
        String                      strDefaultFileName;
        FilePersistenceManager      fileDB = PersistenceFactory.getInstance().getPersistenceManager();

        if ((company = showLedgerWizard()) == null)
            return;

        searchReplace = new SimpleSearchReplace(company.name.trim());
        strDefaultFileName = searchReplace.replaceAll(" ", "");

        if ((strFilePath = promptForFilePath(strDefaultFileName)) == null)
            return;

        try
        {
            fileDB.setFile(new File(strFilePath));
            fileDB.createNewDataRepository();

            // This has to be done outside the wizard because
            // we're capturing the filename after the wizard is complete

            companyBean = new CompanyBusinessLogicBean();
            companyBean.saveCompany(company);    // the call to saveCompany() will save the entire database

            ApplicationMain.getAppSettings().dataFile = strFilePath;

            m_mainWindow.resetApplicationState(fileDB.getFile().getName());
        }
        catch (ValidationException ex)
        {
            MessageDialog.openError(m_mainWindow.getShell(), "Invalid Entry", ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(m_mainWindow.getShell(), "Error", ex.getMessage());
        }

    }

    private Company showLedgerWizard()
    {
//      int                         iReturnCode;
        CompanyWizardDialog         newLedgerWizard;
        Company                     company = null;

        newLedgerWizard = new CompanyWizardDialog(m_mainWindow.getShell());
        newLedgerWizard.setBlockOnOpen(true);

        if (newLedgerWizard.newCompany() == MessageDialog.OK)
            company = newLedgerWizard.getCompany();

        return (company);
    }

    private String promptForFilePath(String strDefaultFileBaseName)
    {
        final               int MAX_SUGGESTED_FILENAME_LENGTH = 25;
        String[]            extensions = { "*." + ApplicationMain.APPLICATION_DATA_FILE_EXTENSION };
        FileDialog          openDialog = new FileDialog(m_mainWindow.getShell(), SWT.SAVE);
        String              strFilePath = null;
        boolean             bDone = false;
        File                file;

        if (strDefaultFileBaseName.length() > MAX_SUGGESTED_FILENAME_LENGTH)
            strDefaultFileBaseName = strDefaultFileBaseName.substring(0, MAX_SUGGESTED_FILENAME_LENGTH);

        openDialog.setFilterExtensions(extensions);
        openDialog.setFileName(strDefaultFileBaseName + "." + ApplicationMain.APPLICATION_DATA_FILE_EXTENSION);


        while (!bDone)
        {
            strFilePath = openDialog.open();
            bDone = true;

            if (strFilePath != null)
            {
                file = new File(strFilePath);

                if (file.exists())
                {
                    bDone = MessageDialog.openQuestion(m_mainWindow.getShell(), "File Already Exists",
                                                       "That file already exists.  Are you sure you wish to overwrite it?");
                }
            }
        }

        return (strFilePath);
    }
}
