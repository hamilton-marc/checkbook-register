/*
 * Created on Sep 21, 2003
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
public class RowWrapper
{
    private Object                  m_data;
    private long                    m_lRowNumber;

    public RowWrapper()
    {
    }

    public RowWrapper(long lRowNumber, Object data)
    {
        m_lRowNumber = lRowNumber;
        m_data = data;
    }

    public void setData(Object data)
    {
        m_data = data;
    }

    public Object getData()
    {
        return (m_data);
    }

    public void setRow(long lRowNumber)
    {
        m_lRowNumber = lRowNumber;
    }

    public long getRow()
    {
        return (m_lRowNumber);
    }
}
