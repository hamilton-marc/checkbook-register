/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.persist.memory;

import java.util.*;
import java.io.Serializable;

import com.uhills.util.exception.PersistenceException;
import com.uhills.util.collections.*;

import com.uhills.finance.easetax.core.Category;
import com.uhills.finance.easetax.persist.ICategoryPersistence;

import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CategoryTable implements ICategoryPersistence, Serializable
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long   serialVersionUID = -6805908426860153995L;

    private Map                 m_table;
    private long                m_lNextId = 1;

    /**
     * 
     */
    public CategoryTable()
    {
        m_table = new Hashtable();
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#getCategory(long)
     */
    public Collection getCategories() throws PersistenceException
    {
        return (m_table.values());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#getCategories(long)
     */
    public Collection getCategories(FilterCriteria[] filterCriteria, SortCriteria[] sortCriteria) throws PersistenceException
    {
        Collection      filteredCollection;
        ArrayList       categoryList;

        if (filterCriteria == null)
            filteredCollection = m_table.values();
        else 
            filteredCollection = ExtendedCollections.filter(m_table.values(), new CategoryFilter(filterCriteria));
/*
        if (filterCriteria != null)
            throw new UnsupportedOperationException("Filtering is not supported for the Category table");

        filteredCollection = m_table.values();
*/
        categoryList = new ArrayList(filteredCollection);

        if (sortCriteria != null)
            Collections.sort(categoryList, new CategoryComparator(sortCriteria));

        return (categoryList);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#getCategory(long)
     */
    public Category getCategory(long lCategoryId) throws PersistenceException
    {
        Category         category = (Category) m_table.get(new Long(lCategoryId));

        return (category);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#insertCategory(com.uhills.finance.easetax.core.Category)
     */
    public void insertCategory(Category newCategory) throws PersistenceException
    {
        newCategory.setID(m_lNextId);
        m_lNextId++;

        m_table.put(newCategory.getOID(), newCategory);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#updateCategory(com.uhills.finance.easetax.core.Category)
     */
    public void updateCategory(Category category) throws PersistenceException
    {
        m_table.put(category.getOID(), category);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#deleteCategory(com.uhills.finance.easetax.core.Category)
     */
    public void deleteCategory(Category category) throws PersistenceException
    {
        m_table.remove(category.getOID());
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.persist.ICategoryPersistence#deleteCategory(long)
     */
    public void deleteCategory(long lCategoryId) throws PersistenceException
    {
        m_table.remove(new Long(lCategoryId));
    }

}
