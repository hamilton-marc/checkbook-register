/*
 * Created on Oct 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.transaction;

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
public class TransactionDataContentProvider implements IDataContentProvider
{
    private DisplayCriteria         m_displayCriteria;

    public TransactionDataContentProvider(DisplayCriteria displayCriteria)
    {
        m_displayCriteria = displayCriteria;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getContent()
     */
    public Object[] getContent()
    {
        TransactionBusinessLogicBean    transactionBean = new TransactionBusinessLogicBean();

        try
        {
            Collection      transactionList = transactionBean.getTransactions(m_displayCriteria);

            transactionList = wrapTransactions(transactionList);

            return (transactionList.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
    }

    private Collection wrapTransactions(Collection originalList)
    {
        ArrayList       newList = new ArrayList();
        long            lRow = 0;

        for (Iterator i = originalList.iterator(); i.hasNext();)
        {
            Transaction     transaction = (Transaction) i.next();

            newList.add(new RowWrapper(++lRow, transaction));
        }

        return (newList);
    }

    public int getColumnCount()
    {
        return (TransactionMaintenanceForm.getColumnCount());
    }

    public Class getColumnClass(int iColumnIndex)
    {
        if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CREDIT.position ||
            iColumnIndex == TransactionMaintenanceForm.COLUMN_DEBIT.position)
        {
            return (Double.class);
        }
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DATE.position)
        {
            return (Date.class);
        }
        else
        {
            return (String.class);
        }
    }


    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnName(int)
     */
    public String getColumnName(int iColumnIndex)
    {
        String              strText = "";

        if (iColumnIndex == TransactionMaintenanceForm.COLUMN_NUMBER.position)
            strText = TransactionMaintenanceForm.COLUMN_NUMBER.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DATE.position)
            strText = TransactionMaintenanceForm.COLUMN_DATE.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CONTACT.position)
            strText = TransactionMaintenanceForm.COLUMN_CONTACT.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CATEGORY.position)
            strText = TransactionMaintenanceForm.COLUMN_CATEGORY.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DEBIT.position)
            strText = TransactionMaintenanceForm.COLUMN_DEBIT.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CREDIT.position)
            strText = TransactionMaintenanceForm.COLUMN_CREDIT.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_JOB_CODE.position)
            strText = TransactionMaintenanceForm.COLUMN_JOB_CODE.toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DESCRIPTION.position)
            strText = TransactionMaintenanceForm.COLUMN_DESCRIPTION.toString();

        return (strText);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnObject(java.lang.Object, int)
     */
    public Object getColumnObject(Object element, int iColumnIndex)
    {
        RowWrapper          row = (RowWrapper) element;
        Transaction         transaction = (Transaction) row.getData();
        Object              value = null;

        if (iColumnIndex == TransactionMaintenanceForm.COLUMN_NUMBER.position)
            value = transaction.numberCode;
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DATE.position)
            value = transaction.transactionDate;
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CONTACT.position)
            value = transaction.getContact().toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CATEGORY.position)
            value = transaction.getCategory().toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DEBIT.position)
        {
            if (transaction.getType().equals(TransactionType.EXPENSE) ||
                transaction.getType().equals(TransactionType.WITHDRAWL))
            {
                value = new Double(transaction.amount);
            }
        }
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_CREDIT.position)
        {
            if (transaction.getType().equals(TransactionType.CONTRIBUTION) ||
                transaction.getType().equals(TransactionType.REVENUE))
            {
                value = new Double(transaction.amount);
            }
        }
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_JOB_CODE.position)
            value = transaction.getJobCode().toString();
        else if (iColumnIndex == TransactionMaintenanceForm.COLUMN_DESCRIPTION.position)
            value = transaction.description;

        return (value);
    }

}
