package com.uhills.finance.easetax.biz;

/**
 * This class contains the navigation and business logic
 * functionality for managing transactions.
 *
 * It also contains functionality to perform validation
 * on the data entry fields.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;

import com.uhills.util.validation.*;
import com.uhills.util.exception.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.persist.*;
import com.uhills.finance.easetax.filtersort.*;

public class ReportBusinessLogicBean extends EaseLedgerBusinessLogicBean
{
    /**
     * Default constructor.
     *
     */
    public ReportBusinessLogicBean()
    {
    }

    /**
     * This method calls the appropriate method for validating
     * the Report information.
     *
     * @param report - the report to validate
     */
    public void validate(Report report) throws ValidationException
    {
        report.validate();
    }

    /**
     * Retrieves a report from our persistent store.
     *
     * @param lReportId - the id of the report to retrieve
     * @return the report corresponding to the id
     */
    public Report getReport(long lReportId) throws PersistenceException
    {
        IReportPersistence        reportPersistence = PersistenceFactory.getInstance().getDataRepository().getReportTable();
        Report                    report = reportPersistence.getReport(lReportId);

        return (report);
    }

    /**
     * Retrieves a list of reports from our persistent store.
     *
     * @param lReportId - the id of the report to retrieve
     * @return the report corresponding to the id
     */
    public Collection getReports() throws PersistenceException
    {
        return (getReports(null));
    }

    /**
     * Retrieves a list of reports from our persistent store.
     *
     * @param displayCriteria - the filter and sort criteria
     * @return a Collection object containing the reports
     */
    public Collection getReports(DisplayCriteria displayCriteria) throws PersistenceException
    {
        IReportPersistence        reportPersistence = PersistenceFactory.getInstance().getDataRepository().getReportTable();
        Collection                  collection;

        if (displayCriteria == null)
            collection = reportPersistence.getReports();
        else
            collection = reportPersistence.getReports(displayCriteria.getFilterCriteria(), displayCriteria.getSortCriteria());

        return (collection);
    }

    /**
     * Saves a report to our persistent data store.
     * If there's an id associated with the report,
     * we're updating the report.  If not, we assume
     * it is a new report.
     *
     * @param report - the report to save
     */
    public void saveReport(Report report) throws PersistenceException, ValidationException
    {
        IReportPersistence        reportPersistence = PersistenceFactory.getInstance().getDataRepository().getReportTable();

        validate(report);

        if (report.getID() > 0)
        {
            reportPersistence.updateReport(report);
        }
        else
        {
            reportPersistence.insertReport(report);
        }

        save();
    }

    /**
     * Deletes a report from our persistent store.
     *
     * @param report - the report to delete
     */
    public void deleteReport(Report report) throws PersistenceException
    {
        IReportPersistence        reportPersistence = PersistenceFactory.getInstance().getDataRepository().getReportTable();

        reportPersistence.deleteReport(report);
        save();
    }

    /**
     * Returns a collection of report types.
     *
     * @return a collection of report types
     */
    public Collection getReportTypes() throws PersistenceException
    {
        return (Arrays.asList(ReportType.getReportTypes()));
    }

    /**
     * Takes a report definition as input and returns
     * a DisplayCriteria object that has the filter and
     * sorting criteria based on the report definition.
     *
     * @return search and sort criteria for report
     */
    public DisplayCriteria getReportCriteria(Report report)
    {
        DisplayCriteria         displayCriteria;
        List                    sortCriteriaList = new ArrayList();
        List                    filterCriteriaList = new ArrayList();
        FilterCriteria[]        filterCriteriaArray;
        SortCriteria[]          sortCriteriaArray;
        FilterCriteria          filterCriteria;
//      List                    filterList = new ArrayList();
        TransactionType[]       validValues;
        SortCriteria            typeSortCriteria;

        // We need to make sure the Income Statements use
        // Revenues and Expenses.
        // The Equity Statements should contain 
        // Contributions and Withdrawls.

        validValues = new TransactionType[2];

        if (report.type.equals(ReportType.DETAILED_EQUITY_STATEMENT))
        {
            validValues[0] = TransactionType.CONTRIBUTION;
            validValues[1] = TransactionType.WITHDRAWL;
            typeSortCriteria = new SortCriteria(Transaction.FIELD_TYPE.id); 
        }
        else
        {
            validValues[0] = TransactionType.REVENUE;
            validValues[1] = TransactionType.EXPENSE;
            typeSortCriteria = new SortCriteria(Transaction.FIELD_TYPE.id, SortCriteria.DESCENDING); 
        }

        sortCriteriaList.add(typeSortCriteria);
        sortCriteriaList.add(new SortCriteria(Transaction.FIELD_CATEGORY.id));

        filterCriteriaList.add(new FilterCriteria(Transaction.FIELD_TYPE.id, FilterCriteria.IN_SET, validValues));

        if ((filterCriteriaArray = getDateCriteria(report.dateFilterType, report.fromDate, report.toDate)) != null)
            filterCriteriaList.addAll(Arrays.asList(filterCriteriaArray));

        if ((filterCriteria = getCategoryCriteria(report.getCategories())) != null)
            filterCriteriaList.add(filterCriteria);

        sortCriteriaArray = new SortCriteria[sortCriteriaList.size()];
        sortCriteriaList.toArray(sortCriteriaArray);

        filterCriteriaArray = new FilterCriteria[filterCriteriaList.size()];
        filterCriteriaList.toArray(filterCriteriaArray);

        displayCriteria = new DisplayCriteria(filterCriteriaArray, sortCriteriaArray);

        return (displayCriteria);
    }

    /**
     * This method takes an array of categories and
     * creates a FilterCriteria object based on the
     * inclusion of those categories.
     *
     * This is used to filter the accounts that the
     * user sees in the report.
     *
     * @return category filter criteria for the report
     */
    public FilterCriteria getCategoryCriteria(Category[] categories)
    {
        if (categories == null ||
            categories.length == 0) return (null);

        FilterCriteria      filterCriteria;

        if (categories.length > 0)
            filterCriteria = new FilterCriteria(Transaction.FIELD_CATEGORY.id, FilterCriteria.IN_SET, categories);
        else
            filterCriteria = new FilterCriteria(Transaction.FIELD_CATEGORY.id, FilterCriteria.EQUALS, categories[0]);

        return (filterCriteria);
    }

    /**
     * This method is used to create a series of FilterCriteria
     * objects used to set the data range for the report.
     *
     * @param dateFilterType - the type of date filter being applied
     * @param customFromDate - the (optional) start date criteria
     * @param customToDate - the (optional) end date criteria
     * @return date criteria as an array of FilterCriteria objects
     */
    public FilterCriteria[] getDateCriteria(DateFilterType dateFilterType, java.util.Date customFromDate, java.util.Date customToDate)
    {
        List                    filterCriteriaList = new ArrayList();
        FilterCriteria[]        filterCriteriaArray = null;

        if (dateFilterType.equals(DateFilterType.DATE_FILTER_CUSTOM))
        {
            filterCriteriaList.add(new FilterCriteria(Transaction.FIELD_DATE.id, FilterCriteria.GREATER_EQUAL, customFromDate));
            filterCriteriaList.add(new FilterCriteria(Transaction.FIELD_DATE.id, FilterCriteria.LESS_EQUAL, customToDate));
        }
        else  // we must retrieve the from and to dates from the dateFilterType if not custom
        {
            java.util.Date      fromDate = dateFilterType.getFromDate();
            java.util.Date      toDate = dateFilterType.getToDate();

            if (fromDate != null)
            {
                filterCriteriaList.add(new FilterCriteria(Transaction.FIELD_DATE.id, FilterCriteria.GREATER_EQUAL, fromDate));
            }

            if (toDate != null)
            {
                filterCriteriaList.add(new FilterCriteria(Transaction.FIELD_DATE.id, FilterCriteria.LESS_EQUAL, toDate));
            }
        }

        if (filterCriteriaList.size() > 0)
        {
            filterCriteriaArray = new FilterCriteria[filterCriteriaList.size()];
            filterCriteriaList.toArray(filterCriteriaArray);
        }

        return (filterCriteriaArray);
    }

    /**
     * This method allows us to set the appropriate "report properties"
     * that are not actually part of the data for the report, but are
     * still needed for headers and footers (for example).
     *
     * @param report - the Report definition
     * @return a hashtable of report properties
     */
    public Hashtable getReportProperties(Report report) throws PersistenceException
    {
        Hashtable                       properties = new Hashtable();
        CompanyBusinessLogicBean        companyBean = new CompanyBusinessLogicBean();
        Company                         company = companyBean.getCompany();

        properties.put(Report.PROPERTY_COMPANY_NAME, company.name);
        properties.put(Report.PROPERTY_TITLE, report.name);
        properties.put(Report.PROPERTY_DATE_RANGE, report.getReportDateRangeDescription(UserPreferences.DATE_FORMAT_DEFAULT));

        return (properties);
    }
}