/*
 * Created on Jun 21, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import org.eclipse.jface.action.*;

/**
 * @author hamiltonm
 *
 * This method contains all of the menu options
 * for the application.
 */
public final class ApplicationMenuProvider
{
    private MainWindow          m_mainWindow;
    private MenuManager         m_mainMenu = new MenuManager("");

    public MenuManager          fileMenu = new MenuManager("&File");
//  public MenuManager          editMenu = new MenuManager("&Edit");
    public MenuManager          viewMenu = new MenuManager("&View");
    public MenuManager          goMenu = new MenuManager("&Go");
//  public MenuManager          reportMenu = new MenuManager("&Report");
    public MenuManager          helpMenu = new MenuManager("&Help");

    public Action               fileCompanySetupAction;
    public Action               fileNewAction;
    public Action               fileOpenAction;
//  public Action               fileSaveAction;
    public Action               filePrintAction;
    public Action               filePrintPreviewAction;
    public Action               fileExitAction;
/*
    public Action               editCutAction;
    public Action               editCopyAction;
    public Action               editPasteAction;
*/

    public Action               goHomeAction;
    public Action               goBackAction;
    public Action               goForwardAction;

// corresponds to the various "functions" available
    public Action               viewTransactionsAction;
    public Action               viewReportsAction;
    public Action               viewContactsAction;
    public Action               viewCategoriesAction;
    public Action               viewCalendarAction;
    public Action               viewTasksAction;
    public Action               viewJobCodesAction;
    public Action               viewPreferencesAction;
/*
    public Action               reportRevenueExpenseAction;
    public Action               reportProfitLossAction;
    public Action               reportBalanceSheetAction;
    public Action               reportCashFlowAction;
*/
    public Action               helpContentsAction;
    public Action               helpAboutAction;

    public ApplicationMenuProvider(MainWindow w)
    {
        m_mainWindow = w;
        initMenus();
    }

    public MenuManager getMenuManager()
    {
        return (m_mainMenu);
    }

    private void initMenus()
    {
        fileCompanySetupAction = new FileCompanySetupAction(m_mainWindow);
        fileNewAction = new FileNewCompanyAction(m_mainWindow);
        fileOpenAction = new FileOpenCompanyAction(m_mainWindow);
//      fileSaveAction = new FileSaveCompanyAction(m_mainWindow);
        filePrintAction = new FilePrintAction(m_mainWindow);
        filePrintPreviewAction = new FilePrintPreviewAction(m_mainWindow);
        fileExitAction = new FileExitAction(m_mainWindow);

        fileMenu.add(fileNewAction);
        fileMenu.add(fileOpenAction);
//      fileMenu.add(fileSaveAction);
        fileMenu.add(new Separator());
        fileMenu.add(fileCompanySetupAction);
        fileMenu.add(new Separator());
        fileMenu.add(filePrintAction);
        fileMenu.add(filePrintPreviewAction);
        fileMenu.add(new Separator());
        fileMenu.add(fileExitAction);
/*
        editCutAction = new EditCutAction(m_mainWindow);
        editCopyAction = new EditCopyAction(m_mainWindow);
        editPasteAction = new EditPasteAction(m_mainWindow);

        editMenu.add(editCutAction);
        editMenu.add(editCopyAction);
        editMenu.add(editPasteAction);
*/
        goHomeAction = new GoHomeAction(m_mainWindow);
        goBackAction = new GoBackAction(m_mainWindow);
        goForwardAction = new GoForwardAction(m_mainWindow);

        goMenu.add(goHomeAction);
        goMenu.add(goBackAction);
        goMenu.add(goForwardAction);

        viewTransactionsAction = new ViewTransactionsAction(m_mainWindow);
        viewContactsAction = new ViewContactsAction(m_mainWindow);
        viewCategoriesAction = new ViewCategoriesAction(m_mainWindow);
//      viewCalendarAction = new ViewCalendarAction(m_mainWindow);
        viewJobCodesAction = new ViewJobCodesAction(m_mainWindow);
        viewReportsAction = new ViewReportsAction(m_mainWindow);
//      viewTasksAction = new ViewTasksAction(m_mainWindow);
        viewPreferencesAction = new ViewPreferencesAction(m_mainWindow);

        viewMenu.add(viewTransactionsAction);
//      viewMenu.add(viewCalendarAction);
        viewMenu.add(viewCategoriesAction);
        viewMenu.add(viewContactsAction);
        viewMenu.add(viewJobCodesAction);
//      viewMenu.add(viewTasksAction);
        viewMenu.add(viewReportsAction);
        viewMenu.add(new Separator());
        viewMenu.add(viewPreferencesAction);
/*
        reportRevenueExpenseAction = new ReportRevenueExpenseAction(m_mainWindow);
        reportProfitLossAction = new ReportProfitLossAction(m_mainWindow);
        reportBalanceSheetAction = new ReportBalanceSheetAction(m_mainWindow);
        reportCashFlowAction = new ReportCashFlowAction(m_mainWindow);

        reportMenu.add(reportRevenueExpenseAction);
        reportMenu.add(reportProfitLossAction);
        reportMenu.add(reportBalanceSheetAction);
        reportMenu.add(reportCashFlowAction);
*/
        helpContentsAction = new HelpContentsAction(m_mainWindow);
        helpAboutAction = new HelpAboutAction(m_mainWindow);

        helpMenu.add(helpContentsAction);
        helpMenu.add(new Separator());
        helpMenu.add(helpAboutAction);

        m_mainMenu.add(fileMenu);
//      m_mainMenu.add(editMenu);
        m_mainMenu.add(viewMenu);
        m_mainMenu.add(goMenu);
//      m_mainMenu.add(reportMenu);
        m_mainMenu.add(helpMenu);
    }

    public void enableForOpenFile(boolean bEnable)
    {
        goHomeAction.setEnabled(bEnable);
        goBackAction.setEnabled(bEnable);
        goForwardAction.setEnabled(bEnable);

        viewTransactionsAction.setEnabled(bEnable);
        viewContactsAction.setEnabled(bEnable);
        viewCategoriesAction.setEnabled(bEnable);
//      viewCalendarAction.setEnabled(bEnable);
        viewJobCodesAction.setEnabled(bEnable);
        viewReportsAction.setEnabled(bEnable);
//      viewTasksAction.setEnabled(bEnable);

        fileCompanySetupAction.setEnabled(bEnable);
//      fileSaveAction.setEnabled(bEnable);
        filePrintAction.setEnabled(bEnable);
        filePrintPreviewAction.setEnabled(bEnable);
    }
}
