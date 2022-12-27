/*
 * Created on Oct 5, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface IDataContentProvider
{
    public Object[] getContent();
    public int getColumnCount();
    public String getColumnName(int iColumnIndex);
    public Class getColumnClass(int iColumnIndex);
    public Object getColumnObject(Object element, int iColumnIndex);
}
