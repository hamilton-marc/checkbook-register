/*
 * Created on Oct 4, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.print;

import java.util.*;
import java.net.*;

import org.jfree.report.*;
import org.jfree.report.modules.gui.base.*;
import org.jfree.report.modules.parser.base.*;
import org.jfree.ui.RefineryUtilities;

import javax.swing.table.TableModel;
import javax.swing.WindowConstants;
import javax.swing.UIManager;

/**
 * @author hamiltonm
 *
 * The PrintEngine handles the printing functionality of the application.
 * We are using JFreeReport as the software component to handle the
 * printing capability.
 *
 * This class uses the Singleton pattern to ensure we have 1 and only
 * 1 instance of the PrintEngine
 */
public class PrintEngine
{
    private static PrintEngine      m_instance;

    private PreviewFrame            m_previewFrame;

    /**
     * Default constructor.
     */
    private PrintEngine()
    {
        Boot.start();  // initialize JFreeReport 

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            System.err.println("An error occurred while trying to set the UI look and feel for JFreeReport");
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Retrieves the instance of the print engine.
     */
    public synchronized static PrintEngine getInstance()
    {
        if (m_instance == null)
        {
            m_instance = new PrintEngine();
        }

        return (m_instance);
    }

    public void printPreview(TableModel reportData, String strReportDefinitionURL) throws PrintingException
    {
        printPreview(reportData, strReportDefinitionURL, null);
    }

    /**
     * Launches the preview screen with the report data
     * and the associated report definition file.
     *
     * @param reportData - the data for the report
     * @param strReportDefinitionURL - the report definition file
     */
    public void printPreview(TableModel reportData, String strReportDefinitionURL, Hashtable reportProperties) throws PrintingException
    {
        try
        {
            if (m_previewFrame != null)
            {

                if (m_previewFrame.isShowing())
                {
                    m_previewFrame.close();
                    m_previewFrame = null;
                }
            }

            URL                     reportURL = new URL(strReportDefinitionURL);
            ReportGenerator         generator = ReportGenerator.getInstance();
            JFreeReport             report = generator.parseReport(reportURL, reportURL);

            report.setData(reportData);
            setReportProperties(report, reportProperties);

            m_previewFrame = new PreviewFrame(report);

            m_previewFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            m_previewFrame.pack();

            RefineryUtilities.centerFrameOnScreen(m_previewFrame);
            m_previewFrame.setVisible(true);
            m_previewFrame.requestFocus();
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.err);

            String strErrorMessage = "An error occurred while trying to print the report:\n\n" +
                                     ex.getMessage();

            throw new PrintingException(strErrorMessage);
        }
    }

    private void setReportProperties(JFreeReport report, Hashtable properties)
    {
        if (properties == null ||
            properties.isEmpty())
        {
            return;
        }

        for (Enumeration e = properties.keys(); e.hasMoreElements(); )
        {
            String      strKey = e.nextElement().toString();
            report.setProperty(strKey, properties.get(strKey));
        }
    }
}
