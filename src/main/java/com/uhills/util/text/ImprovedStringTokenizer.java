package com.uhills.util.text;

/**
 * Class:      ImprovedStringTokenizer
 * Author:     Marc Hamilton
 * Date:       September 13, 2000
 *
 * This current StringTokenizer class that comes with Java in the
 * java.lang package improperly handles the condition where you have
 * a zero-length element.  In other words, if I have a situation where
 * I have more than one delimiter in succession, the standard
 * java.lang.StringTokenizer class treats it as one delimiter.  This
 * bug renders the class all but worthless.
 *
 * This class overrides the StringTokenizer class as a workaround to the
 * bug.
 *
 */

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class ImprovedStringTokenizer extends StringTokenizer
{
    private String      m_strDelimiters;

    /**
     * The only constructor we are supporting is the one that takes
     * the String to parse and the delimiters.  In order for this to
     * work, we must set the "return delimiters" parameter to true.
     *
     * @param strStringToParse - the string to parse
     * @param strDelimiters - the set of delimiters to parse the tokens
     */
    public ImprovedStringTokenizer(String strStringToParse, String strDelimiters)
    {
        super(strStringToParse, strDelimiters, true);

        m_strDelimiters = strDelimiters;
    }

    /**
     * We override the nextToken() method so we can properly treat
     * the condition where there may be 2 or more delimiters that
     * follow each other.  If this is the case, we return an empty
     * String as the next delimiter.
     *
     * @return the next token in the string
     */
    public String nextToken() throws NoSuchElementException
    {
        String      strToken = super.nextToken();

        if (m_strDelimiters.indexOf(strToken) >= 0)
        {
            strToken = "";
        }
        else
        {
            // blow through the next delimiter...
            if (hasMoreTokens())
                super.nextToken();
        }

        return (strToken);
    }
}
