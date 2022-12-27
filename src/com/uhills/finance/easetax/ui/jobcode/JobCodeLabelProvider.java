/*
 * Created on Aug 24, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.jobcode;

import org.eclipse.jface.viewers.LabelProvider;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JobCodeLabelProvider extends LabelProvider
{
    private String              m_strPrefix = "";

    public JobCodeLabelProvider()
    {
    }

    public JobCodeLabelProvider(boolean bIndent)
    {
        m_strPrefix = (bIndent ? " - " : "");
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    public String getText(Object element)
    {
        JobCode         jobCode = (JobCode) element;
        String          strText;

        if (jobCode.getID() > 0)
            strText = m_strPrefix + jobCode.toString();
        else
            strText = jobCode.toString();

        return (strText);
    }

}
