package  com.uhills.util.log;

/**
 * Title:        Retail IDEAS: RIMessage
 * Description:
 * Copyright:    Copyright (c) 2000 JDA Software Group, Inc.
 * Company:      JDA Software Group, Inc.
 * @author  Jason Bertsch
 * @version 1.0 create
 *          1.1 changed the date format
 */


import java.util.Date;
import java.text.*;

public class LogMessage implements Cloneable
{
    public int level;
    public Date dateTime;
    public String message;

    // Format the current time.
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

    public LogMessage(int level, String message)
    {
      this.level = level;
      this.dateTime = new Date();
      this.message = message;
    }

    public String toString()
    {
        /*String n_Level = new String();
        if(level == Log.AUDIT_INFO_INT)
        {
            n_Level = Log.AUDIT_INFO_STR;
        }
        else if(level == Log.MINOR_EVENTS_INT)
        {
            n_Level = Log.MINOR_EVENTS_STR;
        }
        else if(level == Log.MAJOR_EVENTS_INT)
        {
            n_Level = Log.MAJOR_EVENTS_STR;
        }*/
        return (level + " " + formatter.format(dateTime) + " " + message);
    }

    public Object clone()
    {
        try
        {
            return (super.clone());
        }
        catch (CloneNotSupportedException ex)
        {
            System.err.println(new java.util.Date() + " Error cloning " + this.getClass().getName() + "\n" + ex.getMessage());
        }

        return (null);
    }
}