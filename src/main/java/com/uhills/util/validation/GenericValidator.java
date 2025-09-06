package com.uhills.util.validation;

/**
 * This is a useful class for performing generic validations on
 * input fields.
 *
 * @author Marc Hamilton
 * @date   July 3, 2001
 *
 */

import java.text.DateFormat;
import java.text.ParseException;
import gnu.regexp.RE;
import gnu.regexp.REException;

public class GenericValidator
{
    private String          m_strReason;
    private boolean         m_bThrowExceptionOnError = true;

    public GenericValidator()
    {
    }

    public GenericValidator(boolean bThrowExceptionOnError)
    {
        m_bThrowExceptionOnError = bThrowExceptionOnError;
    }

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

    /**
     * This procedure checks to see if a value exists for the
     * sText parameter.  If not, the reason is returned in the
     * sReason parameter
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     */
    public boolean exists(String strTextBody, String strCaption) throws ValidationException
    {
        boolean     bRC = true;

        if (strTextBody == null ||
            strTextBody.trim().length() <= 0)
        {
            bRC = false;
            m_strReason = "Please enter a value for " + strCaption;
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    /**
     * This procedure checks to see make sure the String the user
     * entered is the appropriate length.
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     * @param iMinLength - the minimum length for the text
     * @param iMaxLength - the maximum length for the text
     */
    public boolean isValidLength(String strTextBody, String strCaption,
                                 int iMinLength, int iMaxLength) throws ValidationException
    {
        boolean     bRC = true;

        if (strTextBody == null ||
            strTextBody.trim().length() < iMinLength)
        {
            bRC = false;
            m_strReason = strCaption + " must be at least " + iMinLength + " character" + (iMinLength > 1 ? "s" : "") + " long";
        }

        if (strTextBody == null ||
            strTextBody.trim().length() > iMaxLength)
        {
            bRC = false;
            m_strReason = strCaption + " cannot be longer than " + iMinLength + " character" + (iMinLength > 1 ? "s" : "");
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    /**
     * This procedure checks to see if a value exists for the
     * sText parameter.  If not, the reason is returned in the
     * sReason parameter
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     */
    public boolean isValidInteger(String strTextBody, String strCaption) throws ValidationException
    {
        boolean     bRC = true;

        try
        {
            Integer.parseInt(strTextBody);
        }
        catch (NumberFormatException ex)
        {
            bRC = false;
            m_strReason = strCaption + " must be a numeric value";
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    /**
     * This procedure checks to see if a value exists for the
     * sText parameter.  If not, the reason is returned in the
     * sReason parameter
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     */
    public boolean isValidLong(String strTextBody, String strCaption) throws ValidationException
    {
        boolean     bRC = true;

        try
        {
            Long.parseLong(strTextBody);
        }
        catch (NumberFormatException ex)
        {
            bRC = false;
            m_strReason = strCaption + " must be a numeric value";
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    /**
     *  This procedure checks to see if the value sTextBody is a
     *  valid date.  If not, the reason is returned in the
     * sReason parameter
     *
     * @param strTextBody - the contents of the value to be validated
     * @param strCaption - the ui caption for the field
     * @param bRequired - whether or not this is a required field
     */
    public boolean isValidDate(String strTextBody, String strCaption, boolean bRequired) throws ValidationException
    {
        boolean     bRC = true;

        if (bRequired)
            bRC = exists(strTextBody, strCaption);

        if (bRC && bRequired)
        {
            try
            {
                DateFormat.getDateInstance().parse(strTextBody);
            }
            catch (ParseException ex)
            {
                bRC = false;
                m_strReason = "Please enter a valid date for " + strCaption;
            }
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    public boolean isValidDate(String strTextBody, String strCaption) throws ValidationException
    {
        return (isValidDate(strTextBody, strCaption, false));
    }

    public boolean isValidAlphaNumeric(String strTextBody, String strCaption) throws ValidationException
    {
        boolean             bRC = true;
        RE                  regularExpressionProcessor;

        try
        {
            regularExpressionProcessor = new RE("\\w+", RE.REG_ICASE);

            bRC = regularExpressionProcessor.isMatch(strTextBody);
        }
        catch (REException ex)
        {
            bRC = false;
            ex.printStackTrace(System.err);
        }

        if (!bRC)
        {
            m_strReason = strCaption + " can only contain letters or numbers and no spaces are allowed";
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    public boolean isValidNonWhitespace(String strTextBody, String strCaption) throws ValidationException
    {
        boolean             bRC = true;
        RE                  regularExpressionProcessor;

        try
        {
            regularExpressionProcessor = new RE("\\S+", RE.REG_ICASE);

            bRC = regularExpressionProcessor.isMatch(strTextBody);
        }
        catch (REException ex)
        {
            bRC = false;
            ex.printStackTrace(System.err);
        }

        if (!bRC)
        {
            m_strReason = "The " + strCaption + " you entered contains invalid characters.  " +
                          "Please make sure your entry does not contain any spaces, tabs or other " +
                          "similar characters.  If you are unsure, limit your entry to letters " +
                          "or numbers only";
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }

    public boolean startsWithAlpha(String strTextBody, String strCaption) throws ValidationException
    {
        boolean             bRC = true;
//      RE                  regularExpressionProcessor;

        if (!(bRC = Character.isLetter(strTextBody.charAt(0))))
        {
            m_strReason = strCaption + " must start with a letter";
        }

        if (!bRC && m_bThrowExceptionOnError)
        {
            throw new ValidationException(m_strReason);
        }

        return (bRC);
    }
}
