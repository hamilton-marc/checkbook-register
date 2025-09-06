/*
 * Created on Jun 21, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.graphics;

import org.eclipse.jface.resource.*;

/**
 * @author hamiltonm
 *
 * This class uses the JFace ImageRegistry to manage a pool
 * of images.  It uses a Singleton pattern, so in effect,
 * we always have 1 image pool.
 *
 */
public final class ImagePool
{
    public static final String          ICON_MAIN                   = "<icon:main>";
    public static final String          ICON_TRANSACTIONS           = "<icon:transactions>";
    public static final String          ICON_TRANSACTIONS_SMALL     = "<icon:transactions_small>";
    public static final String          ICON_CATEGORIES             = "<icon:categories>";
    public static final String          ICON_PAYEES                 = "<icon:payees>";
    public static final String          ICON_CALENDAR               = "<icon:calendar>";
    public static final String          ICON_REPORTS                = "<icon:reports>";
    public static final String          ICON_CALCULATOR             = "<icon:calculator>";

    public static final String          ICON_FILE_MAIN                  = "icons/money.ico";
    public static final String          ICON_FILE_TRANSACTIONS          = "icons/transactions.ico";
    public static final String          ICON_FILE_TRANSACTIONS_SMALL    = "icons/transactions_small.ico";
    public static final String          ICON_FILE_CATEGORIES            = "icons/categories.ico";
    public static final String          ICON_FILE_PAYEES                = "icons/payees.ico";
    public static final String          ICON_FILE_CALENDAR              = "icons/calendar.ico";
    public static final String          ICON_FILE_REPORTS               = "icons/piechart.ico";
    public static final String          ICON_FILE_CALCULATOR            = "icons/calculator.ico";

    public static final String          IMAGE_HOME                  = "<image:home>";
    public static final String          IMAGE_BACK                  = "<image:back>";
    public static final String          IMAGE_FORWARD               = "<image:forward>";
    public static final String          IMAGE_REFRESH               = "<image:refresh>";
    public static final String          IMAGE_LEDGER                = "<image:ledger>";
    public static final String          IMAGE_TRANSACTIONS          = "<image:transactions>";
    public static final String          IMAGE_CONTACTS              = "<image:contacts>";
    public static final String          IMAGE_CATEGORIES            = "<image:categories>";
    public static final String          IMAGE_CALENDAR              = "<image:calendar>";
    public static final String          IMAGE_REPORTS               = "<image:reports>";
    public static final String          IMAGE_TASKS                 = "<image:tasks>";
    public static final String          IMAGE_JOB_CODES             = "<image:jobCodes>";
    public static final String          IMAGE_PREFERENCES           = "<image:preferences>";
    public static final String          IMAGE_HELP                  = "<image:help>";
    public static final String          IMAGE_LEDGER_WIZARD         = "<image:ledgerWizard>";
    public static final String          IMAGE_SPLASH                = "<image:splash>";
    public static final String          IMAGE_REPORT_WIZARD         = "<image:reportWizard>";

    public static final String          IMAGE_FILE_HOME                 = "icons/home.gif";
    public static final String          IMAGE_FILE_BACK                 = "icons/back.gif";
    public static final String          IMAGE_FILE_FORWARD              = "icons/forward.gif";
    public static final String          IMAGE_FILE_REFRESH              = "icons/refresh.gif";
    public static final String          IMAGE_FILE_LEDGER               = "icons/ledger.gif";
    public static final String          IMAGE_FILE_TRANSACTIONS         = "icons/transactions.gif";
    public static final String          IMAGE_FILE_CONTACTS             = "icons/contacts.gif";
    public static final String          IMAGE_FILE_CATEGORIES           = "icons/hierarchy.gif";
    public static final String          IMAGE_FILE_CALENDAR             = "icons/calendar.ico";
    public static final String          IMAGE_FILE_REPORTS              = "icons/graph.gif";
    public static final String          IMAGE_FILE_TASKS                = "icons/tasks.gif";
    public static final String          IMAGE_FILE_JOB_CODES            = "icons/jobcodes.gif";
    public static final String          IMAGE_FILE_PREFERENCES          = "icons/properties.gif";
    public static final String          IMAGE_FILE_HELP                 = "icons/help.gif";
    public static final String          IMAGE_FILE_LEDGER_WIZARD        = "images/largeDollar.jpg";
    public static final String          IMAGE_FILE_SPLASH               = "images/splash.jpg";
    public static final String          IMAGE_FILE_REPORT_WIZARD        = "images/spreadsheet.jpg";

    private static ImagePool            m_instance;
    private ImageRegistry               m_imageRegistry;

    /**
     * Default constructor.
     *
     */
    private ImagePool()
    {
        loadImages();
    }

    /**
     * This method loads our images and icons into the Image Registry.
     *
     */
    private void loadImages()
    {
        m_imageRegistry = new ImageRegistry();

        m_imageRegistry.put(ICON_MAIN,                  ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_MAIN));
        m_imageRegistry.put(ICON_TRANSACTIONS,          ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_TRANSACTIONS));
        m_imageRegistry.put(ICON_TRANSACTIONS_SMALL,    ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_TRANSACTIONS_SMALL));
        m_imageRegistry.put(ICON_CALENDAR,              ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_CALENDAR));
        m_imageRegistry.put(ICON_CATEGORIES,            ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_CATEGORIES));
        m_imageRegistry.put(ICON_PAYEES,                ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_PAYEES));
        m_imageRegistry.put(ICON_REPORTS,               ImageDescriptor.createFromFile(this.getClass(), ICON_FILE_REPORTS));

        m_imageRegistry.put(IMAGE_HOME,                 ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_HOME));
        m_imageRegistry.put(IMAGE_BACK,                 ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_BACK));
        m_imageRegistry.put(IMAGE_FORWARD,              ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_FORWARD));
        m_imageRegistry.put(IMAGE_REFRESH,              ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_REFRESH));
        m_imageRegistry.put(IMAGE_TRANSACTIONS,         ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_TRANSACTIONS));
        m_imageRegistry.put(IMAGE_LEDGER,               ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_LEDGER));
        m_imageRegistry.put(IMAGE_CONTACTS,             ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_CONTACTS));
        m_imageRegistry.put(IMAGE_CATEGORIES,           ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_CATEGORIES));
        m_imageRegistry.put(IMAGE_CALENDAR,             ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_CALENDAR));
        m_imageRegistry.put(IMAGE_REPORTS,              ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_REPORTS));
        m_imageRegistry.put(IMAGE_TASKS,                ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_TASKS));
        m_imageRegistry.put(IMAGE_JOB_CODES,            ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_JOB_CODES));
        m_imageRegistry.put(IMAGE_PREFERENCES,          ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_PREFERENCES));
        m_imageRegistry.put(IMAGE_HELP,                 ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_HELP));
        m_imageRegistry.put(IMAGE_LEDGER_WIZARD,        ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_LEDGER_WIZARD));
        m_imageRegistry.put(IMAGE_REPORT_WIZARD,        ImageDescriptor.createFromFile(this.getClass(), IMAGE_FILE_REPORT_WIZARD));
    }

    /**
     * Returns the image registry.
     *
     */
    public ImageRegistry getImageRegistry()
    {
        return (m_imageRegistry);
    }

    /**
     * As part of the Singleton pattern, this method
     * returns the static instance of this class.
     *
     * @return the static instance of this class
     */
    public static synchronized ImagePool getInstance()
    {
        if (m_instance == null)
        {
            m_instance = new ImagePool();
        }

        return (m_instance);
    }
}
