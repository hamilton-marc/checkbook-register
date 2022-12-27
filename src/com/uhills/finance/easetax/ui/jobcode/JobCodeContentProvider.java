/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.jobcode;

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
public class JobCodeContentProvider implements IStructuredContentProvider
{
    private boolean         m_bIncludeAllJobCodes;
    private boolean         m_bIncludeNewJobCode;

    public JobCodeContentProvider()
    {
    }

    public JobCodeContentProvider(boolean bIncludeAllJobCodes)
    {
        m_bIncludeAllJobCodes = bIncludeAllJobCodes;
    }

    public JobCodeContentProvider(boolean bIncludeAllJobCodes, boolean bIncludeNewJobCode)
    {
        this(bIncludeAllJobCodes);
        m_bIncludeNewJobCode = bIncludeNewJobCode;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement)
    {
        JobCodeBusinessLogicBean    jobCodeBean = new JobCodeBusinessLogicBean();

        m_bIncludeAllJobCodes = ((Boolean) inputElement).booleanValue();

        try
        {
            ArrayList                   list = new ArrayList();
            SortCriteria[]              sortCriteria = { new SortCriteria(JobCode.FIELD_CODE.id) };
            DisplayCriteria             displayCriteria = new DisplayCriteria(null, sortCriteria);
            Collection                  jobCodeList = jobCodeBean.getJobCodes(displayCriteria);

            if (m_bIncludeAllJobCodes)
                list.add(JobCode.ALL_JOB_CODES);

            list.addAll(jobCodeList);

            if (m_bIncludeNewJobCode)
                list.add(JobCode.NEW_JOB_CODE);

            return (list.toArray());
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
        }

        return (null);
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
