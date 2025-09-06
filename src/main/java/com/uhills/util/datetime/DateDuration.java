package com.uhills.util.datetime;

import java.util.Date;

/**
 * Class:      DateDuration
 * Author:     Marc Hamilton
 * Date:       May 10, 2000
 *
 * Due to a limitation in the library of Java date routines,
 * we've created a class to handle the computation of
 * date and time duration.  Using this class we can compute
 * and display the duration between two points in time.
 *
 */
public class DateDuration
{
    public long     days        = 0;
    public long     hours       = 0;
    public long     minutes     = 0;
    public long     seconds     = 0;
    public long     millisecs   = 0;

    private long    m_lMillisecDuration = 0;

    public DateDuration()
    {
    }

    public DateDuration(long lNewMillisecDuration)
    {
        computeDuration(lNewMillisecDuration);
    }

    public DateDuration(Date after, Date before)
    {
        computeDuration(after, before);
    }

    private void computeDuration()
    {
        long    divisor;
        long    milliDuration = m_lMillisecDuration;

        divisor = (1000 * 60 * 60 * 24);
        days = milliDuration / divisor;
        milliDuration = milliDuration - days * divisor;

        divisor = (1000 * 60 * 60);
        hours = milliDuration / divisor;
        milliDuration = milliDuration - hours * divisor;

        divisor = (1000 * 60);
        minutes = milliDuration / divisor;
        milliDuration = milliDuration - minutes * divisor;

        divisor = 1000;
        seconds = milliDuration / divisor;
        milliDuration = milliDuration - seconds * divisor;

        millisecs = milliDuration;
    }

    public void computeDuration(long lNewMillisecDuration)
    {
        m_lMillisecDuration = lNewMillisecDuration;
        computeDuration();
    }

    public void computeDuration(Date after, Date before)
    {
        m_lMillisecDuration = after.getTime() - before.getTime();
        computeDuration();
    }

    public void add(DateDuration newDuration)
    {
        if (newDuration != null)
            computeDuration(m_lMillisecDuration + newDuration.m_lMillisecDuration);
    }

    public void add(long lNewMillisecDuration)
    {
        computeDuration(m_lMillisecDuration + lNewMillisecDuration);
    }

    public String toString()
    {
        String     strFormat = "";

        if (days > 0)
        {
            strFormat += days + " day" + (days > 1 ? "s " : " ");
        }

        if (hours > 0)
        {
            strFormat += hours + " hr" + (hours > 1 ? "s " : " ");
        }

        if (minutes > 0)
        {
            strFormat += minutes + " min" + (minutes > 1 ? "s " : " ");
        }

        if (seconds > 0)
        {
            strFormat += seconds + " sec" + (seconds > 1 ? "s " : " ");
        }

        if (millisecs > 0)
        {
            strFormat += millisecs + " millisec" + (millisecs > 1 ? "s " : " ");
        }

        if (strFormat.length() <= 0)
            strFormat = "less than 1 millisec";

        return (strFormat);
    }
}
