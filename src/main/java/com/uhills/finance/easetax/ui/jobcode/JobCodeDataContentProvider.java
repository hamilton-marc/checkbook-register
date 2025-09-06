/*
 * Created on Oct 5, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.jobcode;

import java.util.*;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.filtersort.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.biz.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JobCodeDataContentProvider implements IDataContentProvider
{
    private DisplayCriteria         m_displayCriteria;

    public JobCodeDataContentProvider(DisplayCriteria displayCriteria)
    {
        m_displayCriteria = displayCriteria;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getContent()
     */
    public Object[] getContent()
    {
        JobCodeBusinessLogicBean        jobCodeBean = new JobCodeBusinessLogicBean();

        try
        {
            Collection                  jobCodeList = jobCodeBean.getJobCodes(m_displayCriteria);

            return (jobCodeList.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
    }

    public int getColumnCount()
    {
        return (JobCodeMaintenanceForm.getColumnCount());
    }

    public Class getColumnClass(int iColumnIndex)
    {
        return (String.class);
    }


    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnName(int)
     */
    public String getColumnName(int iColumnIndex)
    {
        String              strText = "";

        if (iColumnIndex == JobCodeMaintenanceForm.COLUMN_CODE.position)
            strText = JobCodeMaintenanceForm.COLUMN_CODE.toString();
        else if (iColumnIndex == JobCodeMaintenanceForm.COLUMN_DESCRIPTION.position)
            strText = JobCodeMaintenanceForm.COLUMN_DESCRIPTION.toString();

        return (strText);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnObject(java.lang.Object, int)
     */
    public Object getColumnObject(Object element, int iColumnIndex)
    {
        JobCode             jobCode = (JobCode) element;
        Object              value = null;

        if (iColumnIndex == JobCodeMaintenanceForm.COLUMN_CODE.position)
            value = jobCode.code;
        else if (iColumnIndex == JobCodeMaintenanceForm.COLUMN_DESCRIPTION.position)
            value = jobCode.description;

        return (value);
    }
}
