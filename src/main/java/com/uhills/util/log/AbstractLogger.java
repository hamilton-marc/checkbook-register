package  com.uhills.util.log;

/**
 * Title:        Retail IDEAS: AbstractLogger
 * Description:
 * Copyright:    Copyright (c) 2000 JDA Software Group, Inc.
 * Company:      JDA Software Group, Inc.
 * @author  Jason Bertsch
 * @version 1.0 create
 *          1.1 modification to include RIMessaging Object
 *
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */



/**
 * put your documentation comment here
 */
public abstract class AbstractLogger
        implements Log {
    public static int defaultLevel = MAJOR_EVENTS_INT;
    protected boolean printLevels;
    protected int loggingLevel;

    /**
     * put your documentation comment here
     */
    AbstractLogger () {
        this(defaultLevel);
    }

    /**
     * put your documentation comment here
     * @param     int aLoggingLevel
     */
    AbstractLogger (int aLoggingLevel) {
        printLevels = true;
        loggingLevel = aLoggingLevel;
    }

    /**
     * put your documentation comment here
     * @param aMessageLevel
     * @return
     */
    final public boolean writeMessage (int aMessageLevel) {
        if (aMessageLevel >= loggingLevel)
            return  true;
        else
            return  false;
    }

    /**
     * put your documentation comment here
     * @param message
     */
    final public void write (String message) {
        write(MAJOR_EVENTS_INT, message);
    }

    /**
     * put your documentation comment here
     * @param e
     */
    final public void write (Exception e) {
        write(MAJOR_EVENTS_INT, e);
    }

    /**
     * put your documentation comment here
     * @param aMessageLevel
     * @param message
     * @return
     */
    final public LogMessage buildMessage (int aMessageLevel, String message) {
        LogMessage logMessage = new LogMessage(aMessageLevel, message);
        return logMessage;
        /*
        if (printLevels)
        {
            if (aMessageLevel == DEBUGGING_INFO_INT)
                return  DEBUGGING_INFO_STR + message;
            else if (aMessageLevel == MINOR_EVENTS_INT)
                return  MINOR_EVENTS_STR + message;
            else if (aMessageLevel == MAJOR_EVENTS_INT)
                return  MAJOR_EVENTS_STR + message;
            return  message;
        }
        return  riMessage;
        */
    }

    /**
     * put your documentation comment here
     * @return
     */
    public int getLogLevel () {
        return  this.loggingLevel;
    }

    /**
     * put your documentation comment here
     * @param logLevel
     */
    public void setLogLevel (int logLevel) {
        this.loggingLevel = logLevel;
    }
}



