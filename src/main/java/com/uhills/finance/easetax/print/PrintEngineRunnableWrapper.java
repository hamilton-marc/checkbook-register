/*
 * Created on Jan 31, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.print;

import java.util.Hashtable;
import java.io.File;

import javax.swing.table.TableModel;

import com.uhills.finance.easetax.main.ApplicationMain;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PrintEngineRunnableWrapper implements Runnable
{
    private TableModel              m_reportData;
    private String                  m_strReportDefinitionFileName;
    private Hashtable               m_reportProperties;

    private PrintingException       printingException;

    public PrintEngineRunnableWrapper()
    {
    }

    public PrintEngineRunnableWrapper(TableModel reportData, String strReportDefinitionURL)
    {
        this(reportData, strReportDefinitionURL, null);
    }

    public PrintEngineRunnableWrapper(TableModel reportData, String strReportDefinitionFileName, Hashtable reportProperties)
    {
        m_reportData = reportData;
        m_strReportDefinitionFileName = strReportDefinitionFileName;
        m_reportProperties = reportProperties;
    }

    public void setReportData(TableModel reportData)
    {
        m_reportData = reportData;
    }

    public void setReportDefinitionFileName(String strReportDefinitionFileName)
    {
        m_strReportDefinitionFileName = strReportDefinitionFileName;
    }

    public void setReportProperties(Hashtable reportProperties)
    {
        m_reportProperties = reportProperties;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        PrintEngine                 printEngine = PrintEngine.getInstance();
        String                      strReportDefinitionURL = "file:///" + new File(ApplicationMain.getAppSettings().reportDirectory, m_strReportDefinitionFileName).getAbsolutePath();

        try
        {
            printEngine.printPreview(m_reportData, strReportDefinitionURL, m_reportProperties);
        }
        catch (PrintingException ex)
        {
            printingException = ex;
        }
    }

    public void checkForException() throws PrintingException
    {
        if (printingException == null)
            return;
            
        throw printingException;
    }
}
