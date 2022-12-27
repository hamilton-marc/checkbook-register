/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.category;

import java.util.*;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.uhills.util.exception.*;

import com.uhills.finance.easetax.biz.*;
import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.filtersort.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CategoryContentProvider implements IStructuredContentProvider
{
    private boolean         m_bIncludeAllCategories;
    private boolean         m_bIncludeNewCategory;

    public CategoryContentProvider()
    {
    }

    public CategoryContentProvider(boolean bIncludeAllCategories)
    {
        m_bIncludeAllCategories = bIncludeAllCategories;
    }

    public CategoryContentProvider(boolean bIncludeAllCategories, boolean bIncludeNewCategory)
    {
        this(bIncludeAllCategories);
        m_bIncludeNewCategory = bIncludeNewCategory;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement)
    {
        CategoryBusinessLogicBean    categoryBean = new CategoryBusinessLogicBean();

        m_bIncludeAllCategories = ((Boolean) inputElement).booleanValue();

        try
        {
            ArrayList                   list = new ArrayList();
            SortCriteria[]              sortCriteria = { new SortCriteria(Category.FIELD_NUMBER.id) };
            DisplayCriteria             displayCriteria = new DisplayCriteria(null, sortCriteria);
            Collection                  categoryList = categoryBean.getCategories(displayCriteria);

            if (m_bIncludeAllCategories)
                list.add(Category.ALL_CATEGORIES);

            list.addAll(categoryList);

            if (m_bIncludeNewCategory)
                list.add(Category.NEW_CATEGORY);

            return (list.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
    }
/*
    private ArrayList groupCategoriesByType(Collection categoryList)
    {
        ArrayList                   list = new ArrayList();
        ArrayList[]                 arrayOfCategories = createCategoryArrays();

        for (Iterator i = categoryList.iterator(); i.hasNext();)
        {
            Category        category = (Category) i.next();

            arrayOfCategories[category.type - 1].add(category);
        }

        for (int j = 0; j < arrayOfCategories.length; j++)
        {
            list.addAll(arrayOfCategories[j]);
        }

        return (list);
    }

    private ArrayList[] createCategoryArrays()
    {
        ArrayList[]                 lists = new ArrayList[4];

        for (int i=0; i < lists.length; i++)
        {
            lists[i] = new ArrayList();
            lists[i].add(new Category(0, Transaction.translateType(i+1), i+1));
        }

        return (lists);
    }
*/
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose()
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }
}
