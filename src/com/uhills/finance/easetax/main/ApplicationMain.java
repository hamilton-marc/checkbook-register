/*
 * Created on Aug 24, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.main;

import java.io.*;
import java.util.*;
import java.text.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.jface.dialogs.*;

import com.uhills.util.exception.*;
import com.uhills.util.exec.*;

import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.ui.main.*;
import com.uhills.finance.easetax.ui.splash.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class ApplicationMain
{
    public static final String              APPLICATION_DATA_FILE_EXTENSION = "lgr";
    public static final String              APPLICATION_LOCK_FILE_EXTENSION = "lck";

    private static final String             APPLICATION_NAME                = "EaseLedger";
//  private static final String             APPLICATION_EDITION_ACCOUNTANT  = "Accountant Edition";
    private static final String             APPLICATION_EDITION_BIZ_OWNER   = "Business Owner Edition";
    private static final String             APPLICATION_VERSION             = "0.9.3.0";
    private static final String             APPLICATION_RELEASE_TYPE        = "Beta 3";
    private static final String             APPLICATION_CONFIG_FILE         = "easeledger.cfg";
    private static final String             APPLICATION_EXPIRATION_DATE     = null; // "12/31/2199";
    private static final String             APPLICATION_VENDOR              = "EaseTax, LLC";
    private static final String             APPLICATION_USER_PREFS_SUBDIR   = ".easeledger";
    private static final String             APPLICATION_USER_PREFS_FILE     = "userprefs.cfg";

    private static ApplicationSettings      m_appSettings = new ApplicationSettings();
    private static UserPreferences          m_userPreferences = new UserPreferences();
    private MainWindow                      m_mainWindow;
    private ApplicationSplashController     m_splashController;
    private String[]                        m_commandLine;
    private File                            m_configFile;
    private File                            m_prefsFile;
    private boolean                         m_dbFileOpened;

    private InstanceLocker                  m_instanceLocker;

    /**
     * Default constructor.  We set up the default configuration file.
     *
     */
    public ApplicationMain()
    {
        StringBuffer        strbufUserPrefsFilePath = new StringBuffer();
        String              strPathSeparator = System.getProperty("file.separator", "/");

        m_instanceLocker = new InstanceLocker(getAppName() + "." + APPLICATION_LOCK_FILE_EXTENSION);
        m_configFile = new File(APPLICATION_CONFIG_FILE);

        strbufUserPrefsFilePath.append(System.getProperty("user.home"));
        strbufUserPrefsFilePath.append(strPathSeparator);
        strbufUserPrefsFilePath.append(APPLICATION_USER_PREFS_SUBDIR);
        
        m_prefsFile = new File(strbufUserPrefsFilePath.toString(), APPLICATION_USER_PREFS_FILE);
    }

    /**
     * This version of the constructor expects the command line
     * arguments as its sole parameter.
     *
     * @param args - the command line arguments
     */
    public ApplicationMain(String[] args)
    {
        this();
        m_commandLine = args;
    }

    /**
     * This method returns the settings for this application.
     * It is static because there should be only one set of
     * settings and to allow the settings to be accessed
     * globally from other classes.
     *
     * @return the settings for the application
     */
    public static ApplicationSettings getAppSettings()
    {
        return (m_appSettings);
    }

    /**
     * This method returns the user's preferences.
     * It is static because there should be only one set of
     * settings and to allow the settings to be accessed
     * globally from other classes.
     *
     * @return the settings for the application
     */
    public static UserPreferences getUserPreferences()
    {
        return (m_userPreferences);
    }

    /**
     * This method returns the user's preferences.
     * It is static because there should be only one set of
     * settings and to allow the settings to be accessed
     * globally from other classes.
     *
     * @return the settings for the application
     */
    public static void setUserPreferences(UserPreferences newPreferences)
    {
        m_userPreferences = newPreferences;
    }

    /**
     * Returns the name of this application.
     *
     * @return the name the application
     */
    public static String getAppName()
    {
        return (APPLICATION_NAME);
    }

    /**
     * Returns the version of this application.
     *
     * @return the version of the application
     */
    public static String getAppVersion()
    {
        return (APPLICATION_VERSION + " " + APPLICATION_RELEASE_TYPE).trim();
    }

    /**
     * Returns the edition of this application.
     *
     * @return the edition of the application
     */
    public static String getAppEdition()
    {
        return (APPLICATION_EDITION_BIZ_OWNER);
    }

    /**
     * Returns the vendor of this application.
     *
     * @return the vendor of the application
     */
    public static String getAppVendor()
    {
        return (APPLICATION_VENDOR);
    }

    /**
     * Returns the full application name which
     * includes the edition and version.
     *
     * @return the full application name
     */
    public static String getFullAppName()
    {
        return (getAppName() + " " + getAppEdition() + " " + getAppVersion());
    }

    /**
     * Returns the expiration date of the application.
     * This is applicable for beta testing to prevent
     * beta users from continuing to use a beta version
     * of the product after the "real" version has been
     * released.
     *
     * @return application expiration date
     */
    public static Date getExpirationDate()
    {
        Date                expirationDate = null;

        if (APPLICATION_EXPIRATION_DATE == null ||
            APPLICATION_EXPIRATION_DATE.length() == 0)
        {
            return (expirationDate);
        }

        final String        strFormat = "MM/dd/yyyy";

        try
        {
            expirationDate = new SimpleDateFormat(strFormat).parse(APPLICATION_EXPIRATION_DATE);
        }
        catch (ParseException ex)
        {
            throw new RuntimeException("Internal Error:\nInvalid Expiration Date: " + APPLICATION_EXPIRATION_DATE + "\n" + ex.getMessage());
        }

        return (expirationDate);
    }

    /**
     * Sets the command line parameters for the
     * application.
     *
     * @return the settings for the application
     */
    public void setCommandLine(String[] args)
    {
        m_commandLine = args;
    }

    /**
     * This method displays the opening splash screen.
     *
     */
    private void showSplashScreen()
    {
        m_splashController = new ApplicationSplashController();
        m_splashController.open();

        Display.getDefault().asyncExec(m_splashController);
    }

    private void closeSplashScreen()
    {
        if (m_splashController == null) return;

        m_splashController.close();
    }

    /**
     * This method displays the opening splash screen.
     *
     */
    private void checkExpiration() throws ApplicationExpirationException
    {
        Date        today = new Date();
        Date        expirationDate = getExpirationDate();

        if (expirationDate == null ||
            today.before(expirationDate))
            return;

        StringBuffer        strbufMessage = new StringBuffer();

        strbufMessage.append("Thank you for using " + getFullAppName() + ".\n\n");
        strbufMessage.append("As of " + APPLICATION_EXPIRATION_DATE + ", this software program is now expired.  ");
        strbufMessage.append("Please contact your software provider or " + getAppVendor() +
                             " to obtain the latest version of " + getAppName() + ".");

        throw new ApplicationExpirationException(strbufMessage.toString(), expirationDate);
    }

    /**
     * This method parses the arguments on the command line.
     *
     */
    private void parseCommandLine()
    {
    }

    /**
     * This method reads the application settings from
     * the application settings file.
     *
     */
    private void readApplicationSettings() throws IOException
    {
        try
        {
            m_appSettings.read(m_configFile.getAbsolutePath());
        }
        catch (FileNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * This method saves the current application settings
     * to the application settings file.
     *
     */
    private void saveApplicationSettings() throws IOException
    {
        m_appSettings.write();
    }

    private void readUserPreferences() throws IOException
    {
        try
        {
            m_userPreferences.read(m_prefsFile.getAbsolutePath());
        }
        catch (FileNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private void saveUserPreferences() throws IOException
    {
        createUserPrefsDir();
        m_userPreferences.write(m_prefsFile.getAbsolutePath());
    }

    private void createUserPrefsDir() throws IOException
    {
        File        userPrefsDir = m_prefsFile.getParentFile();

        if (userPrefsDir.exists()) return;

        if (!userPrefsDir.mkdir())
        {
            // if we can't create the user preferences directory,
            // then just store the user preferences file in the
            // current working directory.

            m_prefsFile = new File(APPLICATION_USER_PREFS_FILE);
        }
    }

    /**
     * Loads the "database" into memory from the persistence
     * store.
     *
     */
    private void loadDatabase() throws PersistenceException
    {
        if (m_appSettings.dataFile == null ||
            m_appSettings.dataFile.length() <= 0)
            return;

        try
        {
            PersistenceFactory.getInstance().getPersistenceManager().load(m_appSettings.dataFile);
            m_dbFileOpened = true;
        }
        catch (PersistenceException ex)
        {
            String      strErrorMessage = "An Error occurred trying to open the database file " +
                                          "\"" + m_appSettings.dataFile + "\":\n\n" +
                                          ex.getMessage();

            m_appSettings.dataFile = "";
            showError(strErrorMessage);
        }
    }

    /**
     * This method launches and displays the main application window.
     *
     */
    private void showMainWindow()
    {
        m_mainWindow = new MainWindow();

        if (m_dbFileOpened)
        {
            m_mainWindow.setFileName(PersistenceFactory.getInstance().getPersistenceManager().getFile().getName());
        }

        m_mainWindow.setSplashController(m_splashController);
        m_mainWindow.setBlockOnOpen(true);

        m_mainWindow.open();
    }

    private void lockInstance() throws ExecutionException, InstanceLockingException
    {
        m_instanceLocker.lock();
    }

    private void unlockInstance()
    {
        try
        {
            m_instanceLocker.unlock();
        }
        catch (ExecutionException ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * This method handles the launching, running and closing
     * of the application.
     *
     */
    public int launch()
    {
        int     iReturnCode = 0;

        try
        {
            showSplashScreen();
            lockInstance();
            parseCommandLine();
//          checkExpiration();
            readApplicationSettings();
            readUserPreferences();
            loadDatabase();
            showMainWindow();  // this method returns after the
                               // the application window closes.
            saveApplicationSettings();
            saveUserPreferences();
        }
        catch (InstanceLockingException ex)
        {
            showError(ex.getMessage());
        }
/*
        catch (ApplicationExpirationException ex)
        {
            showError(ex.getMessage());
        }
*/
        catch (Exception ex)
        {
            String      strMessage = ex.getMessage();

            iReturnCode = 1;    // default error code

            if (ex instanceof NullPointerException)
            {
                strMessage = "Internal program error: NullPointerException\n" +                             "Please contact your system administrator or EaseLedger support";
                
            }

            ex.printStackTrace(System.err);
            showError(strMessage);
        }
        finally
        {
            unlockInstance();
            closeSplashScreen();
        }

        Display.getCurrent().dispose();

        return (iReturnCode);
    }

    private void showError(String strMessage)
    {
        Shell       shell = (m_mainWindow == null ? null : m_mainWindow.getShell());

        closeSplashScreen();
        MessageDialog.openError(shell, getAppName(), strMessage);
    }

    /**
     * This is the application entry point.  So as not to
     * complicate things, we simply create an instance
     * of ApplicationMain, pass it the command line parameters
     * and call launch() to let it do all the work.
     *
     * @param args - the command line parameters
     */
    public static void main(String[] args)
    {
        int                 iExitCode;
        ApplicationMain     appMain = new ApplicationMain(args);

        iExitCode = appMain.launch();

        System.exit(iExitCode);
    }
}
