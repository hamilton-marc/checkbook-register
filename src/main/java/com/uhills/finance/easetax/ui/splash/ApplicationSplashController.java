/*
 * Created on Feb 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.splash;

import java.util.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApplicationSplashController implements Runnable
{
    public static final int             DEFAULT_DURATION = 3; // must remain open for at least 3 seconds

    private int                         m_iDuration = DEFAULT_DURATION;
    private boolean                     m_bForceClose;
    private boolean                     m_bCanClose;
    private ApplicationSplashScreen     m_splashScreen;
    private Date                        m_dtExpirationTime;

    public ApplicationSplashController()
    {
        m_splashScreen = new ApplicationSplashScreen();
    }

    public ApplicationSplashController(int iDuration)
    {
        this();
        setDuration(iDuration);
    }

    public void open()
    {
        Calendar        expirationTime;

        m_splashScreen.open();

        expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.SECOND, m_iDuration);

        m_dtExpirationTime = expirationTime.getTime();
    }

    public void close()
    {
        m_bForceClose = true;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.splash.ISplashController#setDuration(int)
     */
    public void setDuration(int iNewValue)
    {
        m_iDuration = iNewValue;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.splash.ISplashController#getDuration()
     */
    public int getDuration()
    {
        return (m_iDuration);
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.splash.ISplashController#setCanClose(boolean)
     */
    public void setCanClose(boolean bNewValue)
    {
        m_bCanClose = bNewValue;
    }

    /* (non-Javadoc)
     * @see com.uhills.finance.easetax.ui.splash.ISplashController#getCanClose()
     */
    public boolean getCanClose()
    {
        return (m_bCanClose);
    }

    public void run()
    {
        final long      lPauseInterval = 500;
/*
        long            lDuration;
        boolean         bTimeExpired;
*/
        if (new Date().before(m_dtExpirationTime))
        {
            m_splashScreen.getShell().moveAbove(null);
            m_splashScreen.getShell().redraw();
            m_splashScreen.getShell().update();

            try
            {
                while ((new Date().before(m_dtExpirationTime) || !m_bCanClose) &&
                       !m_bForceClose)
                {
                    Thread.sleep(lPauseInterval);
                }
            }
            catch (InterruptedException ex)
            {
                System.err.println(new java.util.Date() + " ApplicationSplashController::run() - Splash screen thread interrupted");
            }
        }

        m_splashScreen.close();
    }
}
