package com.uhills.util.mail;

/**
 *
 * This class contains properties necessary to make a
 * connection to an SMTP E-mail server.
 *
 * @author Marc Hamilton
 * @date   June 16, 2001
 */

import java.io.Serializable;
import java.util.Properties;

import javax.mail.*;

import com.uhills.util.io.FileUtils;

public class EMailConnectionProperties implements Serializable
{
    public String       SMTPServer;
    public String       POPServer;
    public String       userName;
    public String       password;

    /**
     * The constructor for this class can optionally take all 4 property
     * values as arguments.
     *
     */
    public EMailConnectionProperties()
    {
    }

    public EMailConnectionProperties(String strSMTPServer, String strPOPServer, String strUserName, String strPassword)
    {
        setEMailConnectionParms(strSMTPServer, strPOPServer, strUserName, strPassword);
    }

    /**
     * Initializes the properties for this class.
     *
     */
    public void initialize()
    {
        SMTPServer = "";
        POPServer = "";
        userName = "";
        password = "";
    }

    /**
     * Sets all 4 property values.
     *
     */
    public void setEMailConnectionParms(String strSMTPServer, String strPOPServer, String strUserName, String strPassword)
    {
        SMTPServer      = strSMTPServer;
        POPServer       = strPOPServer;
        userName        = strUserName;
        password        = strPassword;
    }

    public String toString()
    {
        return("JDBCDriver\t= "     + SMTPServer    + FileUtils.LINE_SEPARATOR +
               "databaseURL\t= "    + POPServer     + FileUtils.LINE_SEPARATOR +
               "userId\t\t= "       + userName      + FileUtils.LINE_SEPARATOR +
               "password\t= "       + password      + FileUtils.LINE_SEPARATOR);
    }

    /**
     * Creates a new connection to our SMTP server.
     *
     * @return a new Transport object
     */
    public Transport createEMailConnection() throws NoSuchProviderException, MessagingException
    {
        Session                 mailSession;
        Transport               mailTransport;

        mailSession = Session.getDefaultInstance(new Properties(), null);

        mailTransport = mailSession.getTransport("smtp");
        mailTransport.connect(SMTPServer, userName, password);

        return (mailTransport);
    }
}
