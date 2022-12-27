/*
 * Created on Oct 4, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.print;

/**
 * @author hamiltonm
 *
 * This a general exception that is thrown in the event that
 * something goes wrong with the printing and reporting
 * engine.
 */
public class PrintingException extends Exception
{
    /**
     * The one and only constructor takes the error
     * message as its sole argument.
     *
     * @param strMessage - the exception message
     */
    public PrintingException(String strMessage)
    {
        super(strMessage);
    }
}
