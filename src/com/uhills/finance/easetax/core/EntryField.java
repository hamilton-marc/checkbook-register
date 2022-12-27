package com.uhills.finance.easetax.core;

/**
 * This class is something of a convient way to store
 * information about the entry fields used on the user
 * interface.  It allows us to define the caption and
 * maximum number of characters the user can enter
 * (if applicable).
 *
 * @author Marc Hamilton
 * @date   July 24, 2003
 *
 */

public class EntryField
{
    public int              id;
    public String           caption;
    public int              maxLength;

    /**
     * Default constructor.
     */
    public EntryField()
    {
    }

    /**
     * This constructor takes as arguments the id and the caption.
     *
     * @param iId - the id for this object
     * @param strCaption - the caption for this entry field
     */
    public EntryField(int iId, String strCaption)
    {
        id = iId;
        caption = strCaption;
    }

    /**
     * This constructor takes as arguments the id, caption
     * and maximum length.
     *
     * @param iId - the id for this object
     * @param strCaption - the caption for this entry field
     * @param iMaxLength - the maximum length for this entry field
     */
    public EntryField(int iId, String strCaption, int iMaxLength)
    {
        this(iId, strCaption);
        maxLength = iMaxLength;
    }

    /**
     * Overridden from java.lang.Object, this method returns
     * the caption as the default String output for this object.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (caption);
    }
}
