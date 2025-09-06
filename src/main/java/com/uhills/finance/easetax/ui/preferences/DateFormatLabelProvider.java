/*
 * Created on May 23, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.preferences;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * @author HamiltonM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateFormatLabelProvider extends LabelProvider
{
    private Date            m_today = new Date();

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    public String getText(Object element)
    {
        String              strDateFormat = element.toString();
        SimpleDateFormat    dateFormatter = new SimpleDateFormat(strDateFormat);
        String              strFormattedDate = dateFormatter.format(m_today);

        return (strFormattedDate);
    }

}
