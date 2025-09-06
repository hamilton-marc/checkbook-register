package  com.uhills.util.log;

/**
 * Title:        Logger
 * Description:  Used for the logging Features
 * Copyright:    Copyright (c) 2000 JDA Software Group, Inc.
 * Company:      JDA Software Group, Inc.
 * @author JBertsch
 * Version 1.0 - create
 *         1.1 - Modifications for the logging in the New Debug Panel
 *         1.2 - added flag to write to file
 *         1.3 - use one vector for filtering
 */


import java.io.*;
/*
import com.jda.ri.AnalyticalPortal.Logging.UI.*;
import com.jda.ri.AnalyticalPortal.Util.Globals;
*/
public class Logger extends AbstractLogger
{
    private PrintWriter             m_outputWriter;

    /**
     * Calls the Super AbstractLogger
     */
    public Logger ()
    {
        super();
    }

    public Logger(Writer writer)
    {
        this();
        m_outputWriter = new PrintWriter(writer, true);
    }

/*
    public void setWriteToFile(boolean bNewValue)
    {
        m_bWriteToFile = bWriteToFile;
    }

    public boolean getWriteToFile()
    {
        return (m_bWriteToFile);
    }
*/
    /**
     * Calls the Super with the Logging Level
     *
     * @param     int aLoggingLevel
     */
    public Logger (Writer writer, int aLoggingLevel)
    {
        super(aLoggingLevel);
        m_outputWriter = new PrintWriter(writer);
    }

    /**
     * Write the message to the JList in the DebugPanel
     * @param messageLevel example Debugging
     * @param message this is contents of the message
     */
    public void write (int messageLevel, String message)
    {
    /*
        if (this.writeMessage(messageLevel))
        {
    */
//          System.out.println(buildMessage(messageLevel, message));
            m_outputWriter.println(buildMessage(messageLevel, message));
/*
            if(messageLevel <= Globals.log.getLogLevel())
            {
                message = StringUtil.replaceAll(message, "\t", "      ");
                DebugPanel.loggingMessages.add(this.buildMessage(messageLevel,
                        message));
                if(Globals.loggingModelCreated)
                {
                    DebugPanel.createData(DebugPanel.loggingMessages);
                }
            }
        }
*/
    }

    /**
     * put your documentation comment here
     * @param messageLevel
     * @param e
     */
    public void write (int messageLevel, Exception e)
    {
/*
        if (this.writeMessage(messageLevel))
        {
            String n_e = StringUtil.replaceAll(e.toString(), "\t", "      ");
*/
//          System.out.println(buildMessage(messageLevel, e.getMessage()));
            m_outputWriter.println(this.buildMessage(messageLevel, e.getMessage()));
            e.printStackTrace(m_outputWriter);
/*

            if(Globals.writeToFile)
            {
                Globals.writer.writeToFile(this.buildMessage(messageLevel, n_e));
            }

            if(messageLevel <= Globals.log.getLogLevel())
            {
                DebugPanel.loggingMessages.add(this.buildMessage(messageLevel, n_e));
                if(Globals.loggingModelCreated)
                {
                    DebugPanel.createData(DebugPanel.loggingMessages);
                }
            }
        }
*/
    }
/*
    public void close()
    {
        if (m_outputWriter != null)
        {
            m_outputWriter.flush();
            m_outputWriter.close();
        }
    }
*/
}



