package com.uhills.finance.easetax.persist;

/**
 * This interface defines persistent operations that
 * can be performed on categories.  The reason for
 * this interface is to allow us to hide the persistence
 * strategy and implementation for the final product.
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import java.util.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.util.exception.*;
import com.uhills.finance.easetax.filtersort.*;

public interface ICategoryPersistence
{
    /**
     * Retrieves a collection of categories from the persistent store.
     * 
     * @return a Collection of categories
     * @throws PersistenceException
     */
    public Collection getCategories() throws PersistenceException;

    /**
     * Retrieves a collection of categories from the persistent store
     * using the search and sort criteria parameters.
     * 
     * @param filterCriteria - criteria to filter the result set
     * @param sortCriteria - criteria to sort the result set
     * @return a Collection of transactions
     * @throws PersistenceException
     */
    public Collection getCategories(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException;

    /**
     * Retrieves the category from our persistent store
     * corresponding to the id.
     * 
     * @param lCategoryId - category
     * @return the corresponding category
     * @throws PersistenceException
     */
    public Category getCategory(long lCategoryId) throws PersistenceException;

    /**
     * Inserts a new category into our persistent store.
     * 
     * @param newCategory - the new category to insert
     * @throws PersistenceException
     */
    public void insertCategory(Category newCategory) throws PersistenceException;

    /**
     * Updates an existing category in our persistent store.
     * 
     * @param category - the existing category to update
     * @throws PersistenceException
     */
    public void updateCategory(Category category) throws PersistenceException;

    /**
     * Deletes a category from our persistent store.
     * 
     * @param category the category to delete
     * @throws PersistenceException
     */
    public void deleteCategory(Category category) throws PersistenceException;
    public void deleteCategory(long lCategoryId) throws PersistenceException;
}