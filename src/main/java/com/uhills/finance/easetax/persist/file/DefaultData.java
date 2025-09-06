/*
 * Created on Dec 22, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.persist.file;

import java.text.*;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.core.*;
//import com.uhills.finance.easetax.filtersort.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DefaultData
{
    private FileDatabase            m_fileDB;

    public DefaultData(FileDatabase fileDB)
    {
        m_fileDB = fileDB;
    }

    public void createData() throws PersistenceException, ParseException
    {
        createCategories();
        createReports();
    }

    private void createReports() throws PersistenceException
    {
//      DisplayCriteria             reportCriteria;
        Report                      report;

        report = new Report(0, "Profit & Loss", ReportType.INCOME_STATEMENT,
                               "This report shows a basic income statement: Revenue minus Expenses to produce a Net Income");
        m_fileDB.getReportTable().insertReport(report);

        report = new Report(0, "Profit & Loss Detail", ReportType.DETAILED_INCOME_STATEMENT,
                               "This report shows a detailed income statement, complete with individual transaction line items");
        m_fileDB.getReportTable().insertReport(report);
    }

    private void createCategories() throws PersistenceException
    {
        Category            category;

        // 1
        category = new Category(0, 3005, "Contributions", TransactionType.CONTRIBUTION);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  ""));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 2
        category = new Category(0, 4000, "Sales", TransactionType.REVENUE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-1"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "1A"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "1A"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "1A"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 3
        category = new Category(0, 4050, "Returns-Allowances", TransactionType.REVENUE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-2"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "1B"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "1B"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "1B"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 4
        category = new Category(0, 5025, "Officer Salaries", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-4a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "12"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "7"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "10"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 5
        category = new Category(0, 5100, "Materials", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-4b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "2a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "2a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "2a"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 6
        category = new Category(0, 5200, "Contractors", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-4c"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "2b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "2b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "2b"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 7
        category = new Category(0, 6025, "Advertising", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-8"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "23"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "16"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20b"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 8
        category = new Category(0, 6215, "Communications", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-27a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20c"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 9
        category = new Category(0, 6275, "Dues-Publications", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-27b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20d"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 10
        category = new Category(0, 6325, "Education-Training", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-27c"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27c"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19c"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20e"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 11
        category = new Category(0, 6375, "Entertainment-Meals", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-24B"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27d"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19d"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20a"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 12
        category = new Category(0, 6400, "Insurance", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-15"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27e"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19e"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20f"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 13
        category = new Category(0, 6675, "Interest", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-16"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "18"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "13"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "15"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 14
        category = new Category(0, 6770, "Tax-Licenses", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-23"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "17"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "12"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "14"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 15
        category = new Category(0, 6850, "Mileage Reimbursement", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-10a"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27f"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19f"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20g"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 16
        category = new Category(0, 6900, "Office", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-26"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27g"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19g"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20h"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 17
        category = new Category(0, 6950, "Other", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-27"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27h"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19h"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 18
        category = new Category(0, 6975, "Postage-Freight", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-27d"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27i"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19i"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20i"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 19
        category = new Category(0, 7000, "Professional Fees", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-17"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27j"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19j"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20j"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 20
        category = new Category(0, 7005, "Supplies", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-22"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27k"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19k"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20k"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 21
        category = new Category(0, 7010, "Suspense", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  ""));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 22
        category = new Category(0, 7025, "Rent", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-20B"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "16"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "11"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "13"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 23
        category = new Category(0, 7025, "Travel", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-24A"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27l"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19l"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20l"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 24
        category = new Category(0, 7250, "Vehicle Expense", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-10b"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "27m"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "19m"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "20m"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 25
        category = new Category(0, 7300, "Wages", TransactionType.EXPENSE);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  "C-26"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  "13"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, "8"));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  "9"));
        m_fileDB.getCategoryTable().insertCategory(category);

        // 26
        category = new Category(0, 3000, "Withdrawls", TransactionType.WITHDRAWL);
        category.setTaxCode(new TaxCode(TaxCode.FORM_1040,  ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120,  ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1120S, ""));
        category.setTaxCode(new TaxCode(TaxCode.FORM_1065,  ""));
        m_fileDB.getCategoryTable().insertCategory(category);
    }

}
