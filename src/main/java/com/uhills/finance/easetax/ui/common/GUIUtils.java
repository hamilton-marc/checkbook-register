/*
 * Created on Aug 13, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

import java.util.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.jface.dialogs.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.main.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GUIUtils
{
    public static String[] parseStreet(String strTextBoxContents, String strDelimiter)
    {
        String[]            strStreet = new String[Address.NUM_STREET_LINES];
        StringTokenizer     tokenizer = new StringTokenizer(strTextBoxContents, strDelimiter);

        for (int i=0; i < strStreet.length; i++)
        {
            if (tokenizer.hasMoreTokens())
                strStreet[i] = tokenizer.nextToken();
            else
                strStreet[i] = "";
        }

        return (strStreet);
    }

    public static boolean getDeleteConfirmation(Shell shell, String strSubject)
    {
        boolean     bConfirmDelete = true;

        if (!ApplicationMain.getUserPreferences().confirmOnDelete)
            return (bConfirmDelete);

        bConfirmDelete = MessageDialog.openQuestion(shell, "Confirm Delete", "WARNING! This operation CANNOT be undone!\n\n" +
                                                           "Are you sure you wish to PERMANENTLY delete this " +
                                                           strSubject + "?");

        return (bConfirmDelete);
    }

    public static boolean getCancelConfirmation(Shell shell, String strSubject)
    {
        boolean     bConfirmCancel = true;

        if (!ApplicationMain.getUserPreferences().confirmOnDelete)
            return (bConfirmCancel);

        bConfirmCancel = MessageDialog.openQuestion(shell, "Confirm Cancel", "You have made changes to this entry.\n\n" +
                                                           "Are you sure you wish to cancel and lose your changes to this " +
                                                           strSubject + "?");

        return (bConfirmCancel);
    }
}
