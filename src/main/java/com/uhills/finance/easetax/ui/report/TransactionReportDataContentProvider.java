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
public class TransactionReportDataContentProvider implements IDataContentProvider
{
    public static final int         COLUMN_NUMBER           = 0;
    public static final int         COLUMN_TYPE             = 1;
    public static final int         COLUMN_DATE             = 2;
    public static final int         COLUMN_DESCRIPTION      = 3;
    public static final int         COLUMN_CONTACT          = 4;
    public static final int         COLUMN_CATEGORY         = 5;
    public static final int         COLUMN_AMOUNT           = 6;
    public static final int         COLUMN_CREDIT           = 7;
    public static final int         COLUMN_DEBIT            = 8;
    /** positive amount for credits, negative for debits: (necessary for totalling) */
    public static final int         COLUMN_ACCOUNTING_VALUE = 9;

    public boolean                  m_bCombineNumberWithContact;

    public static final int[]       DATA_COLUMNS =
    {
        COLUMN_NUMBER,
        COLUMN_TYPE,
        COLUMN_DATE,
        COLUMN_DESCRIPTION,
        COLUMN_CONTACT,
        COLUMN_CATEGORY,
        COLUMN_AMOUNT,
        COLUMN_CREDIT,
        COLUMN_DEBIT,
        COLUMN_ACCOUNTING_VALUE
    };

    private DisplayCriteria         m_displayCriteria;

    public TransactionReportDataContentProvider(DisplayCriteria displayCriteria)
    {
        m_displayCriteria = displayCriteria;
    }

    public boolean getCombineNumberWithContact()
    {
        return (m_bCombineNumberWithContact);
    }

    public void setCombineNumberWithContact(boolean bNewValue)
    {
        m_bCombineNumberWithContact = bNewValue;
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

            return (transactionList.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
    }

    public int getColumnCount()
    {
        return (DATA_COLUMNS.length);
    }

    public Class getColumnClass(int iColumnIndex)
    {
        Class       columnClass = String.class;

        switch (iColumnIndex)
        {
            case COLUMN_AMOUNT:
            case COLUMN_CREDIT:
            case COLUMN_DEBIT:
            case COLUMN_ACCOUNTING_VALUE:
                columnClass = Double.class;
                break;

            case COLUMN_DATE:
                columnClass = Date.class;
                break;
        }

        return (columnClass);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnName(int)
     */
    public String getColumnName(int iColumnIndex)
    {
        String              strText = "";

        switch (iColumnIndex)
        {
            case COLUMN_NUMBER:
                strText = "Number/Code";
                break;

            case COLUMN_TYPE:
                strText = "Type";
                break;

            case COLUMN_DATE:
                strText = "Date";
                break;

            case COLUMN_DESCRIPTION:
                strText = "Memo";
                break;

            case COLUMN_CONTACT:
                strText = "Contact";
                break;

            case COLUMN_CATEGORY:
                strText = "Account";
                break;

            case COLUMN_AMOUNT:
                strText = "Amount";
                break;

            case COLUMN_CREDIT:
                strText = "Credit";
                break;

            case COLUMN_DEBIT:
                strText = "Debit";
                break;

            case COLUMN_ACCOUNTING_VALUE:
                strText = "AccountingValue";
                break;

        }

        return (strText);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.common.IDataContentProvider#getColumnObject(java.lang.Object, int)
     */
    public Object getColumnObject(Object element, int iColumnIndex)
    {
        Transaction         transaction = (Transaction) element;
        Object              value = null;

        switch (iColumnIndex)
        {
            case COLUMN_NUMBER:
                value = transaction.numberCode;
                break;

            case COLUMN_TYPE:
                value = transaction.getType().toString();
                break;

            case COLUMN_DATE:
                value = transaction.transactionDate;
                break;

            case COLUMN_DESCRIPTION:
                value = transaction.description;
                break;

            case COLUMN_CONTACT:
                value = transaction.getContact().toString();

                if (m_bCombineNumberWithContact &&
                        transaction.numberCode != null &&
                        transaction.numberCode.length() > 0)
                {
                    value = transaction.numberCode + " - " + value;
                }
                break;

            case COLUMN_CATEGORY:
                value = transaction.getCategory().toString();
                break;

            case COLUMN_AMOUNT:
                value = new Double(transaction.amount);
                break;

            case COLUMN_CREDIT:
                if (transaction.getType().equals(TransactionType.CONTRIBUTION) ||
                    transaction.getType().equals(TransactionType.REVENUE))
                {
                    value = new Double(transaction.amount);
                }
                break;

            case COLUMN_DEBIT:
                if (transaction.getType().equals(TransactionType.EXPENSE) ||
                    transaction.getType().equals(TransactionType.WITHDRAWL))
                {
                    value = new Double(transaction.amount);
                }
                break;

            case COLUMN_ACCOUNTING_VALUE:
                if (transaction.getType().equals(TransactionType.EXPENSE) ||
                    transaction.getType().equals(TransactionType.WITHDRAWL))
                {
                    value = new Double(-transaction.amount);
                }
                else
                {
                    value = new Double(transaction.amount);
                }
                break;
        }

        return (value);
    }
}
