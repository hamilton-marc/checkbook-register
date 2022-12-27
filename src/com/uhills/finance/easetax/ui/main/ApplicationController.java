/*
 * Created on Jun 28, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.main;

import java.util.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.jface.dialogs.*;

import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.ui.transaction.*;
import com.uhills.finance.easetax.ui.category.*;
import com.uhills.finance.easetax.ui.contact.*;
import com.uhills.finance.easetax.ui.report.*;
import com.uhills.finance.easetax.ui.jobcode.*;
/*
import com.uhills.finance.easetax.ui.task.*;
import com.uhills.finance.easetax.ui.calendar.*;
*/

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class ApplicationController
{
    public static final int         FUNCTION_HOME = 0;
    public static final int         FUNCTION_LEDGERS = 1;
    public static final int         FUNCTION_TRANSACTIONS = 2;
    public static final int         FUNCTION_CALENDAR = 3;
    public static final int         FUNCTION_REPORTS = 4;
    public static final int         FUNCTION_CONTACTS = 5;
    public static final int         FUNCTION_CATEGORIES = 6;
    public static final int         FUNCTION_TASKS = 7;
    public static final int         FUNCTION_JOB_CODES = 8;
    public static final int         FUNCTION_PREFERENCES = 9;

    public static final int         NUM_FUNCTIONS = FUNCTION_PREFERENCES + 1;

    private MaintenanceForm[]       m_functionContainers = new MaintenanceForm[NUM_FUNCTIONS];

    private MainWindow              m_mainForm;
    private Composite               m_parentComposite;
    private int                     m_iCurrentFunction;
    private final StackLayout       m_stackLayout = new StackLayout();
    private Stack                   m_backStack, m_forwardStack;
    private static boolean          m_bFirstTime = true;

    public ApplicationController(Composite parent, MainWindow mainForm)
    {
        m_backStack = new Stack();
        m_forwardStack = new Stack();

        m_parentComposite = parent;
        m_mainForm = mainForm;

        m_parentComposite.setLayout(m_stackLayout);
    }

    public void switchFunction(int iFunctionId)
    {
        switchFunction(iFunctionId, true);
    }

    public void switchFunction(int iFunctionId, boolean bAddToStack)
    {
        if (iFunctionId == m_iCurrentFunction &&
            m_functionContainers[iFunctionId] != null)
        {
            return;
        }

        switch (iFunctionId)
        {
            case FUNCTION_HOME:
                MessageDialog.openWarning(m_parentComposite.getShell(), "Under Construction", "Sorry, the Home Page has not yet been developed in this version.");
                iFunctionId = m_iCurrentFunction;
//              m_functionContainers[iFunctionId] = new HomeForm(m_parentComposite);
                break;

            case FUNCTION_TRANSACTIONS:
                m_functionContainers[iFunctionId] = new TransactionMaintenanceForm(m_parentComposite);
                break;

            case FUNCTION_CATEGORIES:
                m_functionContainers[iFunctionId] = new CategoryMaintenanceForm(m_parentComposite);
                break;

            case FUNCTION_CONTACTS:
                m_functionContainers[iFunctionId] = new ContactMaintenanceForm(m_parentComposite);
                break;
/*
            case FUNCTION_CALENDAR:
                m_functionContainers[iFunctionId] = new CalendarForm(m_parentComposite);
                break;
*/
            case FUNCTION_REPORTS:
                m_functionContainers[iFunctionId] = new ReportMaintenanceForm(m_parentComposite);
                break;

            case FUNCTION_JOB_CODES:
                m_functionContainers[iFunctionId] = new JobCodeMaintenanceForm(m_parentComposite);
                break;
/*
            case FUNCTION_TASKS:
                m_functionContainers[iFunctionId] = new TaskMaintenanceForm(m_parentComposite);
                break;
*/

            case FUNCTION_PREFERENCES:
                m_mainForm.showPreferences();
                iFunctionId = m_iCurrentFunction;
                break;

            default:
                iFunctionId = m_iCurrentFunction;
                break;
        }

        if (m_iCurrentFunction != iFunctionId && bAddToStack && !m_bFirstTime)
        {
            m_backStack.push(new Integer(m_iCurrentFunction));

            if (!m_forwardStack.empty())
                m_forwardStack.removeAllElements();
        }

        m_bFirstTime = false;

        m_iCurrentFunction = iFunctionId;

        m_stackLayout.topControl = m_functionContainers[m_iCurrentFunction];

        m_mainForm.getHeaderBar().setText(m_functionContainers[m_iCurrentFunction].getText());
        m_mainForm.getHeaderBar().setImage(m_functionContainers[m_iCurrentFunction].getImage());
        m_mainForm.getHeaderBar().layout();

        m_parentComposite.layout();
    }

    public MaintenanceForm getCurrentForm()
    {
        return (m_functionContainers[m_iCurrentFunction]);
    }

    public void goHome()
    {
        switchFunction(FUNCTION_HOME);
    }

    public void goBack()
    {
        try
        {
            Integer     intFunction = (Integer) m_backStack.pop();

            m_forwardStack.push(new Integer(m_iCurrentFunction));

            switchFunction(intFunction.intValue(), false);
        }
        catch (EmptyStackException ex)
        {
            // No problem if the stack is empty
        }
    }

    public void goForward()
    {
        try
        {
            Integer     intFunction = (Integer) m_forwardStack.pop();

            m_backStack.push(new Integer(m_iCurrentFunction));

            switchFunction(intFunction.intValue(), false);

        }
        catch (EmptyStackException ex)
        {
            // No problem if the stack is empty
        }
    }

    public void closeAll()
    {
        for (int i = FUNCTION_HOME; i < NUM_FUNCTIONS; i++)
        {
            if (m_functionContainers[i] == null)
                continue;

            if (!m_functionContainers[i].isDisposed())
                m_functionContainers[i].dispose();

            m_functionContainers[i] = null;
        }
    }
}
