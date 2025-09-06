/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.category;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;

import com.uhills.finance.easetax.core.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CategoryTableLabelProvider implements ITableLabelProvider
{
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int iColumnIndex)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int iColumnIndex)
    {
        Category        category = (Category) element;
        String          strText = "";

        if (iColumnIndex == CategoryMaintenanceForm.COLUMN_NUMBER.position)
            strText = String.valueOf(category.number);
        else if (iColumnIndex == CategoryMaintenanceForm.COLUMN_NAME.position)
            strText = category.name;
        else if (iColumnIndex == CategoryMaintenanceForm.COLUMN_TYPE.position)
            strText = category.getType().toString();
        else if (iColumnIndex == CategoryMaintenanceForm.COLUMN_FORM_1040.position)
            strText = getTaxLine(category, TaxCode.FORM_1040);
        else if (iColumnIndex == CategoryMaintenanceForm.COLUMN_FORM_1120.position)
            strText = getTaxLine(category, TaxCode.FORM_1120);
        else if (iColumnIndex == CategoryMaintenanceForm.COLUMN_FORM_1120S.position)
            strText = getTaxLine(category, TaxCode.FORM_1120S);
        else if (iColumnIndex == CategoryMaintenanceForm.COLUMN_FORM_1065.position)
            strText = getTaxLine(category, TaxCode.FORM_1065);

        return (strText);
    }

    private String getTaxLine(Category category, String strTaxCode)
    {
        String      strTaxLine = "";
        TaxCode     taxCode = category.getTaxCode(strTaxCode);

        if (taxCode != null)
            strTaxLine = taxCode.line;

        return (strTaxLine);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener)
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose()
    {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener)
    {
    }
}
