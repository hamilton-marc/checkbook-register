package com.uhills.finance.easetax.main;

/**
 * This class represents the global application settings
 * for the application.  It has the ability to load
 * and save these settings from a Properties file.
 *
 * @author Marc Hamilton
 * @date   September 05, 2003
 *
 */

import java.util.Properties;
import java.io.*;

import com.uhills.util.mail.EMailConnectionProperties;

import com.uhills.finance.easetax.core.*;

public class ApplicationSettings
{
    private static final String         MAIL_PARMS              = ApplicationSettings.class.getName() + ".mail";
    private static final String         MAIL_SMTP               = MAIL_PARMS + ".smtp";
    private static final String         MAIL_POP                = MAIL_PARMS + ".pop";
    private static final String         MAIL_USER               = MAIL_PARMS + ".userName";
    private static final String         MAIL_PASSWORD           = MAIL_PARMS + ".password";

    private static final String         DATA_FILE               = ApplicationSettings.class.getName() + ".dataFile";
    private static final String         REPORT_DIRECTORY        = ApplicationSettings.class.getName() + ".reportDir";

    private static final String         ACCOUNTANT_EMAIL        = ApplicationSettings.class.getName() + ".accountantEmail";
    private static final String         SUPPORT_EMAIL           = ApplicationSettings.class.getName() + ".supportEmail";
    private static final String         DEBUG_MODE              = ApplicationSettings.class.getName() + ".debugMode";
    private static final String         DEMO_MODE               = ApplicationSettings.class.getName() + ".demoMode";

    private static final String         DEFAULTS                = ApplicationSettings.class.getName() + ".defaults";
    private static final String         DEFAULT_COMPANY         = DEFAULTS + ".company";

    private static final String         COMPANY_NAME            = DEFAULT_COMPANY + ".name";
    private static final String         COMPANY_TYPE            = DEFAULT_COMPANY + ".type";
    private static final String         COMPANY_STREET_1        = DEFAULT_COMPANY + ".street1";
    private static final String         COMPANY_STREET_2        = DEFAULT_COMPANY + ".street2";
    private static final String         COMPANY_CITY            = DEFAULT_COMPANY + ".city";
    private static final String         COMPANY_STATE_PROVINCE  = DEFAULT_COMPANY + ".stateProvince";
    private static final String         COMPANY_ZIP_POSTAL      = DEFAULT_COMPANY + ".zipPostal";
    private static final String         COMPANY_COUNTRY         = DEFAULT_COMPANY + ".country";
    private static final String         COMPANY_PHONE           = DEFAULT_COMPANY + ".phone";
    private static final String         COMPANY_FAX             = DEFAULT_COMPANY + ".fax";
    private static final String         COMPANY_EMAIL           = DEFAULT_COMPANY + ".email";
    private static final String         COMPANY_WEBSITE         = DEFAULT_COMPANY + ".website";

    private String                      m_strFilePath;
    private Properties                  m_properties;

    public  String                      dataFile = "";
    public  String                      reportDirectory = "./reportDefs";       // Directory where the report definition files are

    public  boolean                     debugMode;
    public  boolean                     demoMode;

    public  String                      accountantEmail = "";                          // E-mail address for the accountant
    public  String                      supportEmail = "support@easeledger.com";       // E-mail address for customer support

    private UserPreferences             m_defaultUserOptions;
    private Company                     m_defaultCompany;
    private EMailConnectionProperties   m_mailParms;


    public ApplicationSettings()
    {
        m_properties = new Properties();
        m_mailParms = new EMailConnectionProperties();
        m_defaultCompany = new Company();
        m_defaultUserOptions = new UserPreferences();

        m_mailParms.initialize();
        m_defaultCompany.initialize();
        m_defaultUserOptions.initialize();
    }

    /**
     * Constructs a new object, and loads the settings
     * using the given file path.
     *
     * @param strAppSettingsFilePath - settings file path
     */
    public ApplicationSettings(String strAppSettingsFilePath)
    {
        this();
        m_strFilePath = strAppSettingsFilePath;
    }

    /**
     * Sets the file path for the application settings
     * properties file.
     *
     * @param strFilePath - settings file path
     */
    public void setFilePath(String strFilePath)
    {
        m_strFilePath = strFilePath;
    }

    /**
     * Returns the file path for the application settings
     * properties file.
     *
     * @return application settings file path
     */
    public String getFilePath()
    {
        return (m_strFilePath);
    }

    /**
     * Reads the application settings from the
     * properties file.  This version of the method
     * takes the file path as its sole argument
     *
     * @param strFilePath - the file to read
     */
    public void read(String strFilePath) throws FileNotFoundException, IOException
    {
        setFilePath(strFilePath);
        read();
    }

    /**
     * Reads the application settings from the
     * properties file.
     *
     */
    public void read() throws FileNotFoundException, IOException
    {
        m_properties = new Properties();

        m_properties.load(new FileInputStream(m_strFilePath));

        readEMailParms();
        readDefaultCompany();

        dataFile = m_properties.getProperty(DATA_FILE, "");
        reportDirectory = m_properties.getProperty(REPORT_DIRECTORY, "");

        accountantEmail = m_properties.getProperty(ACCOUNTANT_EMAIL, "");
        supportEmail = m_properties.getProperty(SUPPORT_EMAIL, "");

        debugMode = new Boolean(m_properties.getProperty(DEBUG_MODE, "")).booleanValue();
        demoMode = new Boolean(m_properties.getProperty(DEMO_MODE, "")).booleanValue();
    }

    /**
     * Reads the E-mail connection parameters from the properties file.
     *
     */
    private void readEMailParms()
    {
        m_mailParms.SMTPServer    = m_properties.getProperty(MAIL_SMTP, "");
        m_mailParms.POPServer     = m_properties.getProperty(MAIL_POP, "");
        m_mailParms.userName      = m_properties.getProperty(MAIL_USER, "");
        m_mailParms.password      = m_properties.getProperty(MAIL_PASSWORD, "");
    }

    private void readDefaultCompany()
    {
        int                 iTypeId = 0;
        Address             address = new Address();

        m_defaultCompany.name   = m_properties.getProperty(COMPANY_NAME);

        try
        {
            iTypeId = Integer.parseInt(m_properties.getProperty(COMPANY_TYPE));
        }
        catch (NumberFormatException ex)
        {
        }

        if (iTypeId > 0)
        {
            m_defaultCompany.setType(CompanyType.findType(iTypeId));
        }

        address.street[0]       = m_properties.getProperty(COMPANY_STREET_1, "");
        address.street[1]       = m_properties.getProperty(COMPANY_STREET_2, "");
        address.city            = m_properties.getProperty(COMPANY_CITY, "");
        address.stateProvince   = m_properties.getProperty(COMPANY_STATE_PROVINCE, "");
        address.zipPostal       = m_properties.getProperty(COMPANY_ZIP_POSTAL, "");
        address.country         = m_properties.getProperty(COMPANY_COUNTRY, "");
        address.phone           = m_properties.getProperty(COMPANY_PHONE, "");
        address.fax             = m_properties.getProperty(COMPANY_FAX, "");
        address.email           = m_properties.getProperty(COMPANY_EMAIL, "");
        address.webSite         = m_properties.getProperty(COMPANY_WEBSITE, "");

        m_defaultCompany.setAddress(address);
    }

    /**
     * Writes the application settings to the
     * properties file.  This version of the method
     * takes the file path as its sole argument
     *
     * @param strFilePath - the file to read
     */
    public void write(String strFilePath) throws IOException
    {
        setFilePath(strFilePath);
        write();
    }

    /**
     * Writes the application settings to the
     * properties file.
     *
     * @param strFilePath - settings file path
     */
    public void write() throws IOException
    {
        m_properties = new Properties();

        m_properties.put(DATA_FILE, dataFile == null ? "" : dataFile);
        m_properties.put(REPORT_DIRECTORY, reportDirectory == null ? "" : reportDirectory);

        m_properties.put(ACCOUNTANT_EMAIL, accountantEmail == null ? "" : accountantEmail);
        m_properties.put(SUPPORT_EMAIL, supportEmail == null ? "" : supportEmail);
        m_properties.put(DEBUG_MODE, String.valueOf(debugMode));
        m_properties.put(DEMO_MODE, String.valueOf(demoMode));

        writeEMailParms();
        writeDefaultCompany();

        m_properties.store(new FileOutputStream(m_strFilePath), this.getClass().getName());
    }

    /**
     * Reads the E-mail connection parameters from the properties file.
     *
     */
    private void writeEMailParms()
    {
        m_properties.put(MAIL_SMTP, m_mailParms.SMTPServer == null ? "" : m_mailParms.SMTPServer);
        m_properties.put(MAIL_POP, m_mailParms.POPServer == null ? "" : m_mailParms.POPServer);
        m_properties.put(MAIL_USER, m_mailParms.userName == null ? "" : m_mailParms.userName);
        m_properties.put(MAIL_PASSWORD, m_mailParms.password == null ? "" : m_mailParms.password);
    }

    private void writeDefaultCompany()
    {
        Address     address = m_defaultCompany.getAddress();

        m_properties.put(COMPANY_NAME, m_defaultCompany.name);

        if (m_defaultCompany.getType() != null)
        {
            m_properties.put(COMPANY_TYPE, m_defaultCompany.getType().getOID());
        }

        if (address != null)
        {
            m_properties.put(COMPANY_STREET_1, address.street[0]);
            m_properties.put(COMPANY_STREET_2, address.street[1]);
            m_properties.put(COMPANY_CITY, address.city);
            m_properties.put(COMPANY_STATE_PROVINCE, address.stateProvince);
            m_properties.put(COMPANY_ZIP_POSTAL, address.zipPostal);
            m_properties.put(COMPANY_COUNTRY, address.country);
            m_properties.put(COMPANY_PHONE, address.phone);
            m_properties.put(COMPANY_FAX, address.fax);
            m_properties.put(COMPANY_EMAIL, address.email);
            m_properties.put(COMPANY_WEBSITE, address.webSite);
        }
    }

}
