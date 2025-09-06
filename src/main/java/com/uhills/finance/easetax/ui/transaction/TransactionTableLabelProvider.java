/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.transaction;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.ui.common.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TransactionTableLabelProvider implements ITableLabelProvider, IColorProvider
{
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int iColumnIndex)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int iColumnIndex)
    {
        RowWrapper          row = (RowWrapper) element;
        Transaction         transaction = (Transaction) row.getData();
        String              strText = "";

        if (iColumnIndex == TransactionMaintenanceForm.COLUMN_NUMBER.position)
            strText = transaction.numberCode;
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DATE.position)
            strText = transaction.formatTransactionDate(ApplicationMain.getUserPreferences().dateFormat);
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CONTACT.position)
            strText = transaction.getContact().toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CATEGORY.position)
            strText = transaction.getCategory().toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DEBIT.position)
        {
            if (transaction.getType().equals(TransactionType.EXPENSE) ||
                transaction.getType().equals(TransactionType.WITHDRAWL))
            {
                strText = transaction.formatAmount();
            }
        }
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CREDIT.position)
        {
            if (transaction.getType().equals(TransactionType.CONTRIBUTION) ||
                transaction.getType().equals(TransactionType.REVENUE))
            {
                strText = transaction.formatAmount();
            }
        }
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_JOB_CODE.position)
            strText = transaction.getJobCode().toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DESCRIPTION.position)
            strText = transaction.description;

        return (strText);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener)
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose()
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener)
    {
    }

    public Color getBackground(Object element)
    {
        RowWrapper       row = (RowWrapper) element;

        if (row.getRow() % 2 == 0)
            return (Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));

        return  (Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
    }

    public Color getForeground(Object element)
    {
        return  (Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
    }
}
