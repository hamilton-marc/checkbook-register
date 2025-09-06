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
public final class ApplicationToolBarProvider
{
    private MainWindow            m_mainWindow;
    private ToolBarManager      m_mainToolBar;

// corresponds to the various "functions" available
    public Action               goHomeAction;
    public Action               goBackAction;
    public Action               goForwardAction;
    public Action               transactionsAction;
    public Action               reportsAction;
    public Action               contactsAction;
    public Action               categoriesAction;
    public Action               calendarAction;
    public Action               tasksAction;
    public Action               jobCodesAction;
    public Action               preferencesAction;
    public Action               helpAction;

    public ApplicationToolBarProvider(MainWindow w, int iStyle)
    {
        m_mainWindow = w;
        m_mainToolBar = new ToolBarManager(iStyle);

        initToolBar();
    }

    public ToolBarManager getToolBarManager()
    {
        return (m_mainToolBar);
    }

    private void initToolBar()
    {
        goHomeAction = new ToolBarGoHomeAction(m_mainWindow);
        goBackAction = new ToolBarGoBackAction(m_mainWindow);
        goForwardAction = new ToolBarGoForwardAction(m_mainWindow);
        transactionsAction = new ToolBarTransactionsAction(m_mainWindow);
        contactsAction = new ToolBarContactsAction(m_mainWindow);
        categoriesAction = new ToolBarCategoriesAction(m_mainWindow);
        jobCodesAction = new ToolBarJobCodesAction(m_mainWindow);
//      calendarAction = new ToolBarCalendarAction(m_mainWindow);
        reportsAction = new ToolBarReportsAction(m_mainWindow);
//      tasksAction = new ToolBarTasksAction(m_mainWindow);
        helpAction = new ToolBarHelpAction(m_mainWindow);

        m_mainToolBar.add(goBackAction);
        m_mainToolBar.add(goForwardAction);
        m_mainToolBar.add(goHomeAction);

        m_mainToolBar.add(new Separator());

        m_mainToolBar.add(transactionsAction);
//      m_mainToolBar.add(calendarAction);
        m_mainToolBar.add(reportsAction);

        m_mainToolBar.add(new Separator());

        m_mainToolBar.add(categoriesAction);
        m_mainToolBar.add(contactsAction);
        m_mainToolBar.add(jobCodesAction);
//      m_mainToolBar.add(tasksAction);

        m_mainToolBar.add(new Separator());

        m_mainToolBar.add(helpAction);
    }

    public void enableForOpenFile(boolean bEnable)
    {
        goHomeAction.setEnabled(bEnable);
        goBackAction.setEnabled(bEnable);
        goForwardAction.setEnabled(bEnable);
        transactionsAction.setEnabled(bEnable);
        contactsAction.setEnabled(bEnable);
        categoriesAction.setEnabled(bEnable);
        jobCodesAction.setEnabled(bEnable);
//      calendarAction.setEnabled(bEnable);
        reportsAction.setEnabled(bEnable);
//      tasksAction.setEnabled(bEnable);
    }
}
