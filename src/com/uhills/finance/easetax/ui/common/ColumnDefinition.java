/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ColumnDefinition
{
    public int              position;
    public String           caption;
    public int              width;
    public int              orientation;
    public int              field;

    public ColumnDefinition()
    {
    }

    public ColumnDefinition(int iPos, String strCaption, int iWidth, int iOrientation)
    {
        position = iPos; 
        caption = strCaption;
        width = iWidth;
        orientation = iOrientation;
    }

    public ColumnDefinition(int iPos, String strCaption, int iWidth, int iOrientation, int iFieldId)
    {
        this(iPos, strCaption, iWidth, iOrientation);
        field = iFieldId;
    }

    public String toString()
    {
        return (caption);
    }
}
