package com.uhills.finance.easetax.biz;

/**
 * This class contains the navigation and business logic
 * functionality for managing companies.
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
import com.uhills.finance.easetax.persist.*;

public class CompanyBusinessLogicBean extends EaseLedgerBusinessLogicBean
{
    /**
     * Default constructor.
     *
     */
    public CompanyBusinessLogicBean()
    {
    }

    /**
     * This method calls the appropriate method for validating
     * the Company information.
     *
     * @param company - the company to validate
     */
    public void validate(Company company) throws ValidationException
    {
        company.validate();
    }

    /**
     * Retrieves a company from our persistent store.
     *
     * @param lCompanyId - the id of the company to retrieve
     * @return the company corresponding to the id
     */
    public Company getCompany() throws PersistenceException
    {
        ICompanyPersistence     companyPersistence = PersistenceFactory.getInstance().getDataRepository().getCompanyTable();
        Company                 company = companyPersistence.getCompany();

        return (company);
    }

    /**
     * Saves a company to our persistent data store.
     * If there's an id associated with the company,
     * we're updating the company.  If not, we assume
     * it is a new company.
     *
     * @param company - the company to save
     */
    public void saveCompany(Company company) throws PersistenceException, ValidationException
    {
        ICompanyPersistence     companyPersistence = PersistenceFactory.getInstance().getDataRepository().getCompanyTable();

        validate(company);

        companyPersistence.updateCompany(company);
        save();
    }

    /**
     * Returns a collection of company types.
     *
     * @return a collection of company types
     */
    public Collection getCompanyTypes() throws PersistenceException
    {
        return (Arrays.asList(CompanyType.getCompanyTypes()));
    }
}