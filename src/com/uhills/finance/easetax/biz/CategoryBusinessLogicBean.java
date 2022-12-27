package com.uhills.finance.easetax.biz;

/**
 * This class contains the navigation and business logic
 * functionality for managing categories.
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
import com.uhills.finance.easetax.filtersort.*;

public class CategoryBusinessLogicBean extends EaseLedgerBusinessLogicBean
{
    /**
     * Default constructor.
     *
     */
    public CategoryBusinessLogicBean()
    {
    }

    /**
     * This method calls the appropriate method for validating
     * the Category information.
     *
     * @param category - the category to validate
     */
    public void validate(Category category) throws ValidationException, PersistenceException
    {
        category.validate();

        Category        duplicateCategory = getCategoryByNumber(category.number);

        // make sure we're not simply not trying to edit
        // an existing category.
        if (duplicateCategory != null &&
            !category.equals(duplicateCategory))
        {
            throw new ValidationException(Category.FIELD_NUMBER.caption + " " + duplicateCategory.number +
                                          " is already in use by the following " + Category.CAPTION_CATEGORY + ":\n\n" +
                                          duplicateCategory);
        }
    }

    /**
     * This method searches the list of categories to retrieve the one
     * that exists with the category number passed in as a parameter.
     *
     * @param category number - the category number to search for
     * @return - the associated category (if found, otherwise null)
     */
    public Category getCategoryByNumber(int iCategoryNumber) throws PersistenceException
    {
        ICategoryPersistence        categoryPersistence = PersistenceFactory.getInstance().getDataRepository().getCategoryTable();
        Collection                  collection;
        FilterCriteria[]            filterCriteriaArray = new FilterCriteria[1];
        Category                    category = null;

        filterCriteriaArray[0] = new FilterCriteria(Category.FIELD_NUMBER.id, FilterCriteria.EQUALS, new Integer(iCategoryNumber));
        collection = categoryPersistence.getCategories(filterCriteriaArray, null);

        if (!collection.isEmpty())
            category = (Category) collection.iterator().next();

        return (category);
    }

    /**
     * Retrieves a category from our persistent store.
     *
     * @param lCategoryId - the id of the category to retrieve
     * @return the category corresponding to the id
     */
    public Category getCategory(long lCategoryId) throws PersistenceException
    {
        ICategoryPersistence        categoryPersistence = PersistenceFactory.getInstance().getDataRepository().getCategoryTable();
        Category                    category = categoryPersistence.getCategory(lCategoryId);

        return (category);
    }

    /**
     * Retrieves a list of categorys from our persistent store.
     *
     * @param lCategoryId - the id of the category to retrieve
     * @return the category corresponding to the id
     */
    public Collection getCategories() throws PersistenceException
    {
        return (getCategories(null));
    }

    /**
     * Retrieves a list of categorys from our persistent store.
     *
     * @param displayCriteria - the filter and sort criteria
     * @return a Collection object containing the categorys
     */
    public Collection getCategories(DisplayCriteria displayCriteria) throws PersistenceException
    {
        ICategoryPersistence        categoryPersistence = PersistenceFactory.getInstance().getDataRepository().getCategoryTable();
        Collection                  collection;

        if (displayCriteria == null)
            collection = categoryPersistence.getCategories();
        else
            collection = categoryPersistence.getCategories(displayCriteria.getFilterCriteria(), displayCriteria.getSortCriteria());

        return (collection);
    }

    /**
     * Saves a category to our persistent data store.
     * If there's an id associated with the category,
     * we're updating the category.  If not, we assume
     * it is a new category.
     *
     * @param category - the category to save
     */
    public void saveCategory(Category category) throws PersistenceException, ValidationException
    {
        ICategoryPersistence        categoryPersistence = PersistenceFactory.getInstance().getDataRepository().getCategoryTable();

        validate(category);

        if (category.getID() > 0)
        {
            categoryPersistence.updateCategory(category);
        }
        else
        {
            categoryPersistence.insertCategory(category);
        }

        save();
    }

    /**
     * Checks to see if the category is in use by at least 1 transaction.
     * If so, we throw an exception.
     * 
     * @param category - the category to check
     * @throws PersistenceException, CategoryInUseException
     */
    private void checkCategoryInUse(Category category) throws PersistenceException, CategoryInUseException
    {
        TransactionBusinessLogicBean        transactionBean = new TransactionBusinessLogicBean();

        if (transactionBean.isCategoryInUse(category))
        {
            throw new CategoryInUseException("You cannot delete that category because it is already" +
                                             " in use by 1 or more transactions", category.number);
        }
    }

    /**
     * Deletes a category from our persistent store.
     *
     * @param category - the category to delete
     */
    public void deleteCategory(Category category) throws PersistenceException, CategoryInUseException
    {
        ICategoryPersistence        categoryPersistence = PersistenceFactory.getInstance().getDataRepository().getCategoryTable();

        // Check to make sure the category isn't being
        // used in a transaction already.
        checkCategoryInUse(category);

        categoryPersistence.deleteCategory(category);
        save();
    }
}