package  com.uhills.util.log;

import  java.lang.String;
import  java.lang.Exception;

public interface Log
{
    static final String AUDIT_INFO_STR = "Audit Information";
    static final int AUDIT_INFO_INT = 3;

    static final String MINOR_EVENTS_STR = "Minor Events";
    static final int MINOR_EVENTS_INT = 2;

    static final String MAJOR_EVENTS_STR = "Major Events";
    static final int MAJOR_EVENTS_INT = 1;

    static final String NO_INFORMATION_EVENTS_STR = "No Event Information";
    static final int NO_INFORMATION_EVENTS_INT = 0;

    public void write (String message);

    public void write (Exception e);

    public void write (int aMessageLevel, String message);

    public void write (int aMessageLevel, Exception e);

    public LogMessage buildMessage (int aMessageLevel, String message);

    public int getLogLevel ();

    public void setLogLevel (int logLevel);
}



