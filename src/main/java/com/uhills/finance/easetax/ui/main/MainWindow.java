package com.uhills.finance.easetax.ui.main;

import java.io.*;

import org.eclipse.jface.window.*;
import org.eclipse.jface.action.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;

import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.ui.splash.ApplicationSplashController;

/**
 * @author hamiltonm
 *
 * This is the main form for the application and serves as a
 * container for the various application "functions".
 */
public class MainWindow extends ApplicationWindow
{
    public static final int                 DEFAULT_BUTTON_HEIGHT = 24;
    public static final int                 NAVIGATION_BAR_WIDTH = 105;

    private ApplicationMenuProvider         m_appMenu;
    private ApplicationToolBarProvider      m_appToolBar;
    private ApplicationController           m_appController;
    private ApplicationHeaderBar            m_appHeaderBar;
    private NavigationCommandBar            m_navBar;
    private Composite                       m_functionContainer;
    private ApplicationSplashController     m_splashScreenController;

    private String                          m_strFileName;

    public MainWindow()
    {
        super(null);

        addMenuBar();
        addToolBar(SWT.FLAT | SWT.HORIZONTAL | SWT.WRAP);
        addStatusLine();
    }

    public void setFileName(String strFileName)
    {
        m_strFileName = strFileName;
    }

    public void setFileName(File file)
    {
        m_strFileName = file.getName();
    }

    public ApplicationHeaderBar getHeaderBar()
    {
        return (m_appHeaderBar);
    }

    public ApplicationController getApplicationController()
    {
        return (m_appController);
    }

    protected Control createContents(Composite parent)
    {
        Shell                       shell = getShell();

        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText(ApplicationMain.getAppName());

        Composite                   contentArea = new Composite(shell, SWT.NO_FOCUS);
        GridData                    gridData;
        GridLayout                  gridLayout = new GridLayout(2, false);

        gridLayout.horizontalSpacing = 3;
        gridLayout.verticalSpacing = 3;
        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 3;
        contentArea.setLayout(gridLayout);

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        m_appHeaderBar = new ApplicationHeaderBar(contentArea);
        m_appHeaderBar.setLayoutData(gridData);

        gridData = new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL);
        gridData.widthHint = NAVIGATION_BAR_WIDTH;
        m_navBar = new NavigationCommandBar(contentArea, SWT.BORDER, this);
        m_navBar.setLayoutData(gridData);

        gridData = new GridData(GridData.FILL_BOTH);
        m_functionContainer = new Composite(contentArea, SWT.NO_FOCUS);
        m_functionContainer.setLayoutData(gridData);

        m_appController = new ApplicationController(m_functionContainer, this);

        resetApplicationState();
        closeSplashScreen();

        return (m_appController.getCurrentForm());
    }

    private void closeSplashScreen()
    {
        if (m_splashScreenController != null)
            m_splashScreenController.setCanClose(true);
    }

    protected MenuManager createMenuManager()
    {
        m_appMenu = new ApplicationMenuProvider(this);

        return (m_appMenu.getMenuManager());
    }

    protected ToolBarManager createToolBarManager(int iStyle)
    {
        m_appToolBar = new ApplicationToolBarProvider(this, iStyle);

        return (m_appToolBar.getToolBarManager());
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
    protected Point getInitialLocation(Point initialSize)
    {
        Rectangle           screenDimensions = Display.getCurrent().getBounds();
        Point               location = new Point(0, 0);

        location.x = screenDimensions.width / 2 - initialSize.x / 2;
        location.y = screenDimensions.height / 2 - initialSize.y / 2;

        return (location);
    }

    public void setSplashController(ApplicationSplashController splashController)
    {
        m_splashScreenController = splashController;
    }

    private void showFileName()
    {
        StringBuffer        strbufWindowHeader = new StringBuffer(ApplicationMain.getAppName());

        if (m_strFileName != null &&
            m_strFileName.length() > 0)
        {
            strbufWindowHeader.append(" - ");
            strbufWindowHeader.append(m_strFileName);
        }

        getShell().setText(strbufWindowHeader.toString());
    }

    public boolean isFileOpen()
    {
        return (m_strFileName != null && m_strFileName.length() > 0);
    }

    private void enableNavigationComponents()
    {
        boolean     bIsFileOpen = isFileOpen();

        m_appMenu.enableForOpenFile(bIsFileOpen);
        m_appToolBar.enableForOpenFile(bIsFileOpen);
        m_navBar.setVisible(bIsFileOpen);
    }

    public void refresh()
    {
        showFileName();
        enableNavigationComponents();
        setStatus(ApplicationMain.getAppName());
    }

    public void resetApplicationState(String strFileName)
    {
        setFileName(strFileName);
        resetApplicationState();
    }

    public void resetApplicationState()
    {
        refresh();
        m_appController.closeAll();  // close all of the functions

        if (isFileOpen())
            m_appController.switchFunction(ApplicationController.FUNCTION_TRANSACTIONS);
    }

    public void showPreferences()
    {
        m_appMenu.viewPreferencesAction.run();
    }
}
