package com.uhills.finance.easetax.main;

/**
 * This class holds the personal application preferences
 * for the user.
 *
 * @author Marc Hamilton
 * @date   June 16, 2001
 *
 */

import java.util.Properties;
import java.io.*;

import com.uhills.finance.easetax.core.TransactionType;
import com.uhills.util.validation.*;

public class UserPreferences
{
    // Serialization unique identifier.  We really don't need
    // to keep track of this UID because unlike some of the
    // other objects, we are not actually serializing this
    // to the "file database".  It does however get saved to
    // a properties file.
    //
    // static final long serialVersionUID = -2462083880096534953L;

    public static final String      DATE_FORMAT_DEFAULT         = "MM/dd/yyyy";

    public static final String[]    DATE_FORMAT_ARRAY =
    {
        "MM-dd-yy",
        "MM/dd/yy",
        DATE_FORMAT_DEFAULT,
        "MM-dd-yyyy",
        "MMM d, yyyy"
    };

    public static final String      FORMAT_LASTNAME_FIRST       = "L";
    public static final String      FORMAT_FIRSTNAME_FIRST      = "F";

    public static final int         TRANS_DATE_BLANK        = 0;
    public static final int         TRANS_DATE_TODAY        = 1;
    public static final int         TRANS_DATE_PREV         = 2;

    private static final String     USER_NAME               = UserPreferences.class.getName() + ".userName";
    private static final String     DATE_FORMAT             = UserPreferences.class.getName() + ".dateFormat";
    private static final String     CURRENCY_FORMAT         = UserPreferences.class.getName() + ".currencyFormat";
    private static final String     CONFIRM_DELETE          = UserPreferences.class.getName() + ".confirmDelete";
    private static final String     CONFIRM_CANCEL          = UserPreferences.class.getName() + ".confirmCancel";
    private static final String     DEFAULT_TRANS_DATE_TYPE = UserPreferences.class.getName() + ".defaultTransDateType";
    private static final String     DEFAULT_TRANS_TYPE      = UserPreferences.class.getName() + ".defaultTransType";
    private static final String     PROMPT_NEW_CONTACT      = UserPreferences.class.getName() + ".promptNewContact";
//  private static final String     STARTUP_FUNCTION        = UserPreferences.class.getName() + ".startupFunction";

    private String                  m_strFilePath;
    private Properties              m_properties;

    public String                   userName;
    public String                   dateFormat;
    public String                   currencyFormat;
    public boolean                  confirmOnDelete;
    public boolean                  confirmOnCancel;
    public int                      defaultTransDateType;
    public TransactionType          defaultTransType;
    public boolean                  promptOnNewContact;
    public int                      startupFunction;

    public UserPreferences()
    {
        m_properties = new Properties();
        initialize();
    }

    public UserPreferences(String strFilePath)
    {
        this();
        m_strFilePath = strFilePath;
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        userName                    = System.getProperty("user.name", "");
        dateFormat                  = DATE_FORMAT_DEFAULT;
        currencyFormat              = "";
        confirmOnDelete             = true;
        confirmOnCancel             = false;
        defaultTransDateType        = TRANS_DATE_BLANK;
        defaultTransType            = TransactionType.EXPENSE;
        promptOnNewContact          = true;
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        if (dateFormat == null || dateFormat.length() <= 0)
            throw new ValidationException("Please select a Date Format from the list");

        if (defaultTransType == null)
            throw new ValidationException("Please select a " + TransactionType.CAPTION_TYPE + " from the list");
    }

    /**
     * Sets the file path for the user preferences
     * properties file.
     *
     * @param strFilePath - settings file path
     */
    public void setFilePath(String strFilePath)
    {
        m_strFilePath = strFilePath;
    }

    /**
     * Returns the file path for the user preferences
     * properties file.
     *
     * @return prefrences file path
     */
    public String getFilePath()
    {
        return (m_strFilePath);
    }

    /**
     * Reads the user preferences from the
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
     * Reads the user preferences from the
     * properties file.
     *
     */
    public void read() throws FileNotFoundException, IOException
    {
        int     iTransTypeId;

        m_properties = new Properties();

        m_properties.load(new FileInputStream(m_strFilePath));

        dateFormat = m_properties.getProperty(DATE_FORMAT, "");
        currencyFormat = m_properties.getProperty(CURRENCY_FORMAT, "");
        confirmOnDelete = new Boolean(m_properties.getProperty(CONFIRM_DELETE, Boolean.toString(true))).booleanValue();
        confirmOnCancel = new Boolean(m_properties.getProperty(CONFIRM_CANCEL, Boolean.toString(false))).booleanValue();
        defaultTransDateType = getIntegerProperty(DEFAULT_TRANS_DATE_TYPE, TRANS_DATE_BLANK);

        iTransTypeId = getIntegerProperty(DEFAULT_TRANS_TYPE, TransactionType.EXPENSE.getID());

        if ((defaultTransType = TransactionType.findType(iTransTypeId)) == null ||
            defaultTransType.getID() <= TransactionType.ALL_TYPES.getID())
        {
            defaultTransType = TransactionType.EXPENSE;
        }

        promptOnNewContact = new Boolean(m_properties.getProperty(PROMPT_NEW_CONTACT, Boolean.toString(true))).booleanValue();
    }

    private int getIntegerProperty(String strPropertyName, int iDefaultValue)
    {
        String          strValue = m_properties.getProperty(strPropertyName, "");
        int             iValue = iDefaultValue;

        try
        {
            iValue = Integer.parseInt(strValue);
        }
        catch (NumberFormatException ex)
        {
        }

        return (iValue);
    }

    /**
     * Writes the user preferences to the
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
     * Writes the user preferences to the
     * properties file.
     *
     * @param strFilePath - settings file path
     */
    public void write() throws IOException
    {
        m_properties = new Properties();

        m_properties.put(USER_NAME, userName == null ? "" : userName);
        m_properties.put(DATE_FORMAT, dateFormat == null ? "" : dateFormat);
        m_properties.put(CURRENCY_FORMAT, currencyFormat == null ? "" : currencyFormat);
        m_properties.put(CONFIRM_DELETE, String.valueOf(confirmOnDelete));
        m_properties.put(CONFIRM_CANCEL, String.valueOf(confirmOnCancel));
        m_properties.put(DEFAULT_TRANS_DATE_TYPE, String.valueOf(defaultTransDateType));
        m_properties.put(DEFAULT_TRANS_TYPE, String.valueOf(defaultTransType == null ? TransactionType.EXPENSE.getID() : defaultTransType.getID()));
        m_properties.put(PROMPT_NEW_CONTACT, String.valueOf(promptOnNewContact));

        m_properties.store(new FileOutputStream(m_strFilePath), this.getClass().getName());
    }
}
