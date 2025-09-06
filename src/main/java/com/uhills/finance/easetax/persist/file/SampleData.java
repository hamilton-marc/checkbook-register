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

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SampleData
{
    private FileDatabase            m_fileDB;

    public SampleData(FileDatabase fileDB)
    {
        m_fileDB = fileDB;
    }

    public void createData() throws PersistenceException, ParseException
    {
        createCompany();
        createTransactions();
    }

    private void createCompany() throws PersistenceException
    {
        Company             company = new Company();
        Address             address = new Address();

        company.initialize();
        company.name = "ABC and Associates, Inc.";
        company.setType(CompanyType.S_CORPORATION);

        address.initialize();
        address.street[0] = "123 Main St.";
        address.street[1] = "Suite 1A";
        address.city = "Phoenix";
        address.stateProvince = "AZ";
        address.zipPostal = "85002";
        address.phone = "(602) 555-5555";
        address.country = "United States";

        company.setAddress(address);

        m_fileDB.getCompanyTable().updateCompany(company);
    }

    private void createTransactions() throws PersistenceException, ParseException
    {
        SimpleDateFormat    dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        Transaction         transaction;
        Contact             contact;
        Address             address;

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.CONTRIBUTION);
        transaction.description = "Seed capital from investment firm";
        transaction.amount = 200000;
        transaction.transactionDate = dateFormatter.parse("01/01/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(1));

        contact = new Contact();
        contact.initialize();
        contact.name = "Venture Capital Corp";
        contact.setType(ContactType.OTHER);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.numberCode = "101";
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Business forms";
        transaction.amount = 20;
        transaction.transactionDate = dateFormatter.parse("01/17/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(20));

        contact = new Contact();
        contact.initialize();
        contact.name = "Staples";
        contact.setType(ContactType.VENDOR);

        address = new Address();
        address.initialize();
        address.street[0] = "3550 N Goldwater Blvd";
        address.city = "Scottsdale";
        address.stateProvince = "AZ";
        address.zipPostal = "85251-5538";
        address.phone = "(480) 970-1100";
        address.webSite = "http://www.staples.com";

        contact.setAddress(address);
        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.numberCode = "102";
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Office space deposit";
        transaction.amount = 2500;
        transaction.transactionDate = dateFormatter.parse("02/01/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(22));

        contact = new Contact();
        contact.initialize();
        contact.name = "Opus West";
        contact.setType(ContactType.VENDOR);

        address = new Address();
        address.initialize();
        address.street[0] = "2575 E Camelback Rd";
        address.city = "Phoenix";
        address.stateProvince = "AZ";
        address.zipPostal = "85016-4240";
        address.phone = "(602) 954-1672";

        contact.setAddress(address);
        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Office phone";
        transaction.amount = 450;
        transaction.transactionDate = dateFormatter.parse("03/15/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(8));

        contact = new Contact();
        contact.initialize();
        contact.name = "Qwest Communications";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.numberCode = "103";
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Electricity";
        transaction.amount = 320;
        transaction.transactionDate = dateFormatter.parse("03/06/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(16));

        contact = new Contact();
        contact.initialize();
        contact.name = "APS";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Water and Sewage";
        transaction.amount = 120;
        transaction.transactionDate = dateFormatter.parse("03/14/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(16));

        contact = new Contact();
        contact.initialize();
        contact.name = "City of Phoenix";
        contact.setType(ContactType.VENDOR);

        address = new Address();
        address.initialize();
        address.street[0] = "200 W. Washington St";
        address.city = "Phoenix";
        address.stateProvince = "AZ";
        address.zipPostal = "85003";
        address.webSite = "http://www.phoenix.gov";

        contact.setAddress(address);
        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Computer Books";
        transaction.amount = 230;
        transaction.transactionDate = dateFormatter.parse("03/25/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(10));

        contact = new Contact();
        contact.initialize();
        contact.name = "Borders Book Shop";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Team Lunch";
        transaction.amount = 150;
        transaction.transactionDate = dateFormatter.parse("01/03/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(11));

        contact = new Contact();
        contact.initialize();
        contact.name = "Macaroni Grill";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Distribute demo software";
        transaction.amount = 150;
        transaction.transactionDate = dateFormatter.parse("04/02/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(18));

        contact = new Contact();
        contact.initialize();
        contact.name = "UPS";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Flight to TechExpo";
        transaction.amount = 410;
        transaction.transactionDate = dateFormatter.parse("04/19/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(23));

        contact = new Contact();
        contact.initialize();
        contact.name = "America West";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Took prospective customer to dinner";
        transaction.amount = 70;
        transaction.transactionDate = dateFormatter.parse("04/20/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(11));

        contact = new Contact();
        contact.initialize();
        contact.name = "Cheesecake Factory";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Car rental for TechExpo";
        transaction.amount = 285;
        transaction.transactionDate = dateFormatter.parse("04/19/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(23));

        contact = new Contact();
        contact.initialize();
        contact.name = "Avis";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.EXPENSE);
        transaction.description = "Airport parking";
        transaction.amount = 20;
        transaction.transactionDate = dateFormatter.parse("04/21/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(23));

        contact = new Contact();
        contact.initialize();
        contact.name = "Phoenix Sky Harbor";
        contact.setType(ContactType.VENDOR);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.REVENUE);
        transaction.description = "Customer Internet Order";
        transaction.amount = 120;
        transaction.transactionDate = dateFormatter.parse("12/21/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(2));

        contact = new Contact();
        contact.initialize();
        contact.name = "Bill Melater";
        contact.setType(ContactType.CUSTOMER);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.REVENUE);
        transaction.description = "Customer Internet Order";
        transaction.amount = 55;
        transaction.transactionDate = dateFormatter.parse("11/07/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(2));

        contact = new Contact();
        contact.initialize();
        contact.name = "Bob Frapples";
        contact.setType(ContactType.CUSTOMER);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.REVENUE);
        transaction.description = "Customer Internet Order";
        transaction.amount = 86;
        transaction.transactionDate = dateFormatter.parse("11/15/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(2));

        contact = new Contact();
        contact.initialize();
        contact.name = "Amanda Hugginkiss";
        contact.setType(ContactType.CUSTOMER);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);

        transaction = new Transaction();
        transaction.initialize();
        transaction.setType(TransactionType.REVENUE);
        transaction.description = "Customer Internet Order";
        transaction.amount = 186;
        transaction.transactionDate = dateFormatter.parse("12/02/2003");

        transaction.setCategory(m_fileDB.getCategoryTable().getCategory(2));

        contact = new Contact();
        contact.initialize();
        contact.name = "Bonnie Enclyde";
        contact.setType(ContactType.CUSTOMER);

        m_fileDB.getContactTable().insertContact(contact);
        transaction.setContact(contact);
        m_fileDB.getTransactionTable().insertTransaction(transaction);
    }

}
