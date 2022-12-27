/*
 * Created on Sep 19, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.filtersort;

import java.io.Serializable;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SortCriteria implements Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long       serialVersionUID    = 6910108056373688590L;

    public static final int         ASCENDING           = 0;
    public static final int         DESCENDING          = 1;

    private int             m_iFieldId;
    private int             m_iDirection;

    public SortCriteria()
    {
    }

    public SortCriteria(int iFieldId)
    {
        m_iFieldId = iFieldId;
        m_iDirection = ASCENDING;
    }

    public SortCriteria(int iFieldId, int iDirection)
    {
        this(iFieldId);
        m_iDirection = iDirection;
    }

    public int getField()
    {
        return (m_iFieldId);
    }

    public int getDirection()
    {
        return (m_iDirection);
    }

    public void switchDirection()
    {
        if (m_iDirection == ASCENDING)
            m_iDirection = DESCENDING;
        else
            m_iDirection = ASCENDING;
    }

    public void sortDescending(int iFieldId, int iDirection)
    {
        m_iFieldId = iFieldId;
        m_iDirection = iDirection;
    }

    public void sortAscending(int iFieldId)
    {
        m_iFieldId = iFieldId;
        m_iDirection = ASCENDING;
    }

    public void sortDescending(int iFieldId)
    {
        m_iFieldId = iFieldId;
        m_iDirection = DESCENDING;
    }
}
