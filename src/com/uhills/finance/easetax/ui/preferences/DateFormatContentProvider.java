/*
 * Created on May 23, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.preferences;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.uhills.finance.easetax.main.UserPreferences;

/**
 * @author HamiltonM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateFormatContentProvider implements IStructuredContentProvider
{

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement)
    {
        return (UserPreferences.DATE_FORMAT_ARRAY);
    }

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
