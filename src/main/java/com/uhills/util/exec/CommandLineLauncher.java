package com.uhills.util.exec;

/**
 * Title:       Union Hills Java utility classes
 * Description:
 *
 * @author      Union Hills Software
 * @version     1.0
 */

import java.util.*;
import java.io.*;

public class CommandLineLauncher
{
    private String          m_strCommand;
    private Vector          m_vecParameterList;
    private String          m_strWorkingDirectory;
    private boolean         m_bRunAsynchronously;

    public CommandLineLauncher()
    {
    }

    public CommandLineLauncher(boolean bAsync)
    {
        m_bRunAsynchronously = bAsync;
    }

    public void setCommand(String strNewValue)
    {
        m_strCommand = strNewValue;
    }

    public void setWorkingDirectory(String strNewValue)
    {
        m_strWorkingDirectory = strNewValue;
    }

    public void setParameters(Vector vecParmList)
    {
        m_vecParameterList = vecParmList;
    }

    private String buildParameterString()
    {
        if (m_vecParameterList == null) return ("");

        StringBuffer        strbufParms = new StringBuffer("");

        for (Enumeration e = m_vecParameterList.elements(); e.hasMoreElements(); )
        {
            strbufParms.append(" " + e.nextElement());
        }

        return (strbufParms.toString());
    }

    public void execute(String strCommand) throws ExecutionException
    {
        m_strCommand = strCommand;

        execute();
    }

    /**
     * Launches the Executable using the given command line
     * string.  We use the exec method from the Runtime class to
     * spawn off a new process.
     *
     * @param strCommandLine - full command to launch
     */
    public void execute() throws ExecutionException
    {
        // Run the program and wait for it to finish.

        Process             proc;
        int                 iReturnCode = 0;
        String              strCommandLine = m_strCommand + buildParameterString();

        try
        {
/*
            System.out.println(new java.util.Date() +
                               " Executing \"" + strCommandLine +
                               "\" in current working directory: \"" +
                               m_strWorkingDirectory + "\"");
*/
            if (m_strWorkingDirectory != null)
                proc = Runtime.getRuntime().exec(strCommandLine, null, new File(m_strWorkingDirectory));
            else
                proc = Runtime.getRuntime().exec(strCommandLine);

            if (!m_bRunAsynchronously)
            {
                readOutput(proc);

                iReturnCode = proc.waitFor();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.err);
            throw new ExecutionException("Error running " + strCommandLine + ": " + ex.getMessage());
        }

        if (iReturnCode != 0)
        {
            String      strErrorMessage;

            strErrorMessage = "A non-zero return code was returned from the program execution" +
                              " using the command line:\n" + strCommandLine + "\n" +
                              "This indicates that an error occurred within the program itself";

            throw new ExecutionException(strErrorMessage);
        }
    }

    private void readOutput(Process proc) throws IOException
    {
        String              strBuffer;
        BufferedReader      reader[] = new BufferedReader[2];
        PrintStream         outputStream[] = new PrintStream[2];

        reader[0] = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        reader[1] = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        outputStream[0] = System.out;
        outputStream[1] = System.err;

        for (int i=0; i < reader.length; i++)
        {
            while ((strBuffer = reader[i].readLine()) != null)
            {
                outputStream[i].println(strBuffer);
            }
        }
    }
}