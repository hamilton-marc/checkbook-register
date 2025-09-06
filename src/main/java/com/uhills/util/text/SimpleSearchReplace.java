package com.uhills.util.text;

/**
 * This class provides simple search and replace
 * functionality for Strings.
 *
 * @author Marc Hamilton
 * @date   July 3, 2001
 *
 */

public class SimpleSearchReplace
{
    private String          m_strSearchString;
    private String          m_strText;
    private String          m_strReplaceString;

    /**
     * This is the default constructor for the class.
     *
     */
    public SimpleSearchReplace()
    {
    }

    /**
     * This version of the constructor takes the text
     * we are going to search as the sole parameter.
     *
     * @param strText - the text to be searched
     */
    public SimpleSearchReplace(String strText)
    {
        setText(strText);
    }

    /**
     * Sets the String we are looking for in the
     * body of the text.
     *
     * @param strNewValue - the string we are looking for
     */
    public void setSearchString(String strNewValue)
    {
        m_strSearchString = strNewValue;
    }

    /**
     * Sets body of the text to be searched.
     *
     * @param strNewValue - the text to be searched
     */
    public void setText(String strNewValue)
    {
        m_strText = strNewValue;
    }

    /**
     * Sets the String we will use as the replacement
     * text.
     *
     * @param strNewValue - the replacement String
     */
    public void setReplaceString(String strNewValue)
    {
        m_strReplaceString = strNewValue;
    }

    /**
     * Replace all occurances of the search string
     * with the replacement string in the body of
     * the text.
     *
     * @param strSearchString - the search String
     * @param strReplaceString - the replacement String
     */
    public String replaceAll(String strSearchString, String strReplaceString)
    {
        m_strSearchString = strSearchString;
        m_strReplaceString = strReplaceString;

        return (replaceAll());
    }

    /**
     * Replace all occurances of the search string
     * with the replacement string in the body of
     * the text.
     *
     */
    public String replaceAll()
    {
        if (m_strSearchString == null ||
            m_strSearchString.length() == 0 ||
            m_strReplaceString == null ||
            m_strText == null)
        {
            return (m_strText);
        }

        StringBuffer    strbufResult = new StringBuffer();
        int             iPrevPos = 0;
        int             iPos = m_strText.indexOf(m_strSearchString);
        int             iLen = m_strSearchString.length();

        while (iPos >= 0)
        {
            strbufResult.append(m_strText.substring(iPrevPos, iPos));
            strbufResult.append(m_strReplaceString);

            iPrevPos = iPos + iLen;
            iPos = m_strText.indexOf(m_strSearchString, iPrevPos);
        }

        strbufResult.append(m_strText.substring(iPrevPos));

        return (strbufResult.toString());
    }
}
