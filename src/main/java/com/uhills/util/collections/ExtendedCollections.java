/*
 * Created on Sep 20, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.util.collections;

import java.util.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtendedCollections
{
    public static Collection filter(Collection collection, IFilter filter)
    {
        ArrayList           filteredList = new ArrayList();

        for (Iterator i = collection.iterator(); i.hasNext();)
        {
            Object      obj = i.next();

            if (filter.include(obj))
            {
                filteredList.add(obj);
            }
        }

        return (filteredList);
    }
}
