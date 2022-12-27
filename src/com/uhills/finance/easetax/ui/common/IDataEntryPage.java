/*
 * Created on Dec 14, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

import com.uhills.util.validation.ValidationException;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IDataEntryPage
{
    public void validate() throws ValidationException;
    public void fillObject(Object obj) throws ValidationException;
    public void fillFields(Object obj);
    public boolean isPageComplete();
}
