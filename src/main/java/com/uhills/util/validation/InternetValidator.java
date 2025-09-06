package com.uhills.util.validation;

/**
 * This is a useful class for performing generic validations on
 * input fields.
 *
 * @author Marc Hamilton
 * @date   July 3, 2001
 *
 */

//import java.text.ParseException;
import java.net.URL;
import java.net.MalformedURLException;
import gnu.regexp.RE;
import gnu.regexp.REException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;

public class InternetValidator
{
    public static final String  HTTP_PREFIX = "http://";

    private GenericValidator    m_genericValidator;
    private String              m_strReason;
    private boolean             m_bThrowExceptionOnError = true;

    public String getReason()
    {
        return (m_strReason);
    }

    public void setThrowExceptionOnError(boolean bNewValue)
    {
        m_bThrowExceptionOnError = bNewValue;
    }

    public boolean getThrowExceptionOnError()
    {
        return (m_bThrowExceptionOnError);
    }

    public InternetValidator()
    {
        m_genericValidator = new GenericValidator();
    }

    public InternetValidator(boolean bThrowExceptionOnError)
    {
        m_bThrowExceptionOnError = bThrowExceptionOnError;

        m_genericValidator = new GenericValidator(bThrowExceptionOnError);
    }

    /**
     * This method uses the gnu regular expression parser to ensure
     * that the user has typed in a valid E-mail address.
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     * @param bRequired - whether or not this is a required field
     */
    public boolean isValidEmail(String strTextBody, String strCaption, boolean bRequired) throws ValidationException
    {
        boolean     bRC = true;
        RE          regularExpressionProcessor;

        if (bRequired)
            bRC = m_genericValidator.exists(strTextBody, strCaption);

        if (bRC && strTextBody != null && strTextBody.length() > 0)
        {
            try
            {
                regularExpressionProcessor = new RE("\\S+@\\S+\\.\\S+", RE.REG_ICASE);

                bRC = regularExpressionProcessor.isMatch(strTextBody);
            }
            catch (REException ex)
            {
                bRC = false;
                ex.printStackTrace(System.err);
            }
        }

        if (bRC && strTextBody != null && strTextBody.length() > 0)
        {
            try
            {
                new InternetAddress(strTextBody);
            }
            catch (AddressException ex)
            {
                bRC = false;
            }
        }

        if (!bRC)
        {
            m_strReason = "\"" + strTextBody + "\"" + " is not a valid E-mail address";
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    public boolean isValidEmail(String strTextBody, String strCaption) throws ValidationException
    {
        return (isValidEmail(strTextBody, strCaption, false));
    }

    /**
     * This procedure checks to see if the URL that the user entered
     * is a valid HTTP formatted URL.  We first check to see if it
     * is a valid URL in general.  Then we make sure it starts with
     * "http://".  It uses the java.net.URL class to conveniently
     * handle most of the validation.
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     * @param bRequired - whether or not this is a required field
     */
    public boolean isValidWebAddress(String strTextBody, String strCaption, boolean bRequired) throws ValidationException
    {
        boolean         bRC = true;
        RE              regularExpressionProcessor;

        if (bRequired)
            bRC = m_genericValidator.exists(strTextBody, strCaption);

        if (bRC && strTextBody != null && strTextBody.length() > 0)
        {
            try
            {
                regularExpressionProcessor = new RE(HTTP_PREFIX + "\\S+\\.\\S+", RE.REG_ICASE);

                bRC = regularExpressionProcessor.isMatch(strTextBody);
            }
            catch (REException ex)
            {
                bRC = false;
                ex.printStackTrace(System.err);
            }
        }

        if (bRC && strTextBody != null && strTextBody.length() > 0)
        {
            try
            {
                new URL(strTextBody);

                if (strTextBody.trim().equalsIgnoreCase(HTTP_PREFIX))
                    throw new MalformedURLException();
            }
            catch (MalformedURLException ex)
            {
                bRC = false;
            }
        }

        if (!bRC)
        {
            m_strReason = "\"" + strTextBody + "\" is not a valid Web site address";
        }

        if (bRC && strTextBody != null && !strTextBody.toLowerCase().startsWith(HTTP_PREFIX))
        {
            m_strReason = strCaption + " must start with \"" + HTTP_PREFIX + "\"";
            bRC = false;
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    public boolean isValidWebAddress(String strTextBody, String strCaption) throws ValidationException
    {
        return (isValidWebAddress(strTextBody, strCaption, false));
    }
}
