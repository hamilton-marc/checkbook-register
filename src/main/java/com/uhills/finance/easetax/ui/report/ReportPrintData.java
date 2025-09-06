/*
 * Created on Oct 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.report;

import javax.swing.table.AbstractTableModel;

import com.uhills.finance.easetax.filtersort.*;
import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportPrintData extends AbstractTableModel
{
    private Object[]                            m_data;
    private DisplayCriteria                     m_displayCriteria;
    private ReportDataContentProvider           m_contentProvider;

    public ReportPrintData(DisplayCriteria displayCriteria)
    {
        m_displayCriteria = displayCriteria;
        m_contentProvider = new ReportDataContentProvider(m_displayCriteria);

        refresh();
    }

    public void refresh()
    {
        m_data = m_contentProvider.getContent();
    }

    public String getColumnName(final int iColumnIndex)
    {
        return (m_contentProvider.getColumnName(iColumnIndex));
    }

    public Class getColumnClass(final int iColumnIndex)
    {
        return (m_contentProvider.getColumnClass(iColumnIndex));
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return (m_contentProvider.getColumnCount());
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {
        return (m_data.length);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int iRowIndex, int iColumnIndex)
    {
        Report             report = (Report) m_data[iRowIndex];

        return (m_contentProvider.getColumnObject(report, iColumnIndex));
    }

}
