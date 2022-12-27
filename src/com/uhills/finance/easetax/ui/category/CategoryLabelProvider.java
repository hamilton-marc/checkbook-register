/*
 * Created on Aug 24, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.category;

import org.eclipse.jface.viewers.LabelProvider;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CategoryLabelProvider extends LabelProvider
{
    private String              m_strPrefix = "";

    public CategoryLabelProvider()
    {
    }

    public CategoryLabelProvider(boolean bIndent)
    {
        m_strPrefix = (bIndent ? " - " : "");
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    public String getText(Object element)
    {
        Category        category = (Category) element;
        String          strText;

        if (category.getID() > 0)
            strText = m_strPrefix + category.toString();
        else
            strText = category.toString();

        return (strText);
    }

}
