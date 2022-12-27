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
public class FilterCriteria implements Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long serialVersionUID      = 2463871877976562992L;

    public static final int         ANY             = 0;
    public static final int         EQUALS          = 1;
    public static final int         GREATER         = 2;
    public static final int         LESS            = 3;
    public static final int         GREATER_EQUAL   = 4;
    public static final int         LESS_EQUAL      = 5;
    public static final int         BETWEEN         = 6;
    public static final int         IN_SET          = 7;
    public static final int         CONTAINS        = 8;
    public static final int         STARTS_WITH     = 9;

    private int             m_iFieldId;
    private int             m_iOperator;
    private Object          m_value;
    private Object          m_maxValue;
    private boolean         m_bNegate;
    private String          m_strDescription;

    public FilterCriteria()
    {
        m_iOperator = ANY;
    }

    public FilterCriteria(int iFieldId)
    {
        this();
        m_iFieldId = iFieldId;
    }

    public FilterCriteria(int iFieldId, int iOperator, Object value)
    {
        this(iFieldId);
        m_iOperator = iOperator;
        m_value = value;
    }

    public FilterCriteria(int iFieldId, int iOperator, Object minValue, Object maxValue)
    {
        this(iFieldId, iOperator, minValue);
        m_maxValue = maxValue;
    }

    public void setDescription(String strDescription)
    {
        m_strDescription = strDescription;
    }

    public String getDescription()
    {
        return (m_strDescription == null ? "" : m_strDescription);
    }

    public void negate(boolean bNegate)
    {
        m_bNegate = bNegate;
    }

    public boolean getIsNegated()
    {
        return (m_bNegate);
    }

    public int getField()
    {
        return (m_iFieldId);
    }

    public int getOperator()
    {
        return (m_iOperator);
    }

    public Object getValue()
    {
        return (m_value);
    }

    public Object[] getValues()
    {
        return ((Object[]) m_value);
    }

    public Object getMinValue()
    {
        return (getValue());
    }

    public Object getMaxValue()
    {
        return (m_maxValue);
    }

    public void setEqualTo(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = EQUALS;
    }

    public void setGreaterThan(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = GREATER;
    }

    public void setLessThan(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = LESS;
    }

    public void setGreaterThanEqualTo(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = GREATER_EQUAL;
    }

    public void setLessThanEqualTo(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = LESS_EQUAL;
    }

    public void setBetween(int iFieldId, Object minValue, Object maxValue)
    {
        m_iFieldId = iFieldId;
        m_value = minValue;
        m_maxValue = maxValue;
        m_iOperator = BETWEEN;
    }

    public void setInSet(int iFieldId, Object[] values)
    {
        m_iFieldId = iFieldId;
        m_value = values;
        m_iOperator = IN_SET;
    }

    public void setContains(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = CONTAINS;
    }

    public void setStartsWith(int iFieldId, Object value)
    {
        m_iFieldId = iFieldId;
        m_value = value;
        m_iOperator = STARTS_WITH;
    }

    public String toString()
    {
        return (getDescription());
    }
}
