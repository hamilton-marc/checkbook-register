/*
 * Created on Oct 5, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

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
public class ReportDataContentProvider implements IDataContentProvider
{
    private DisplayCriteria         m_displayCriteria;

    public ReportDataContentProvider(DisplayCriteria displayCriteria)
    {
        m_displayCriteria = displayCriteria;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getContent()
     */
    public Object[] getContent()
    {
        ReportBusinessLogicBean        reportBean = new ReportBusinessLogicBean();

        try
        {
            Collection                  reportList = reportBean.getReports(m_displayCriteria);

            return (reportList.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
    }

    public int getColumnCount()
    {
        return (ReportMaintenanceForm.getColumnCount());
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

        if (iColumnIndex == ReportMaintenanceForm.COLUMN_NAME.position)
            strText = ReportMaintenanceForm.COLUMN_NAME.toString();
        else if (iColumnIndex == ReportMaintenanceForm.COLUMN_TYPE.position)
            strText = ReportMaintenanceForm.COLUMN_TYPE.toString();
        else if (iColumnIndex == ReportMaintenanceForm.COLUMN_DESCRIPTION.position)
            strText = ReportMaintenanceForm.COLUMN_DESCRIPTION.toString();

        return (strText);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnObject(java.lang.Object, int)
     */
    public Object getColumnObject(Object element, int iColumnIndex)
    {
        Report              report = (Report) element;
        Object              value = null;

        if (iColumnIndex == ReportMaintenanceForm.COLUMN_NAME.position)
            value = report.name;
        else if (iColumnIndex == ReportMaintenanceForm.COLUMN_TYPE.position)
            value = report.type.toString();
        else if (iColumnIndex == ReportMaintenanceForm.COLUMN_DESCRIPTION.position)
            value = report.description;

        return (value);
    }

}
