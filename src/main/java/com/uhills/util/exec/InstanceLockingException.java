package com.uhills.util.exec;

public class InstanceLockingException extends Exception
{
    private boolean         m_bAlreadyLocked;

    public InstanceLockingException(boolean bAlreadyLocked, String strMessage)
    {
        super(strMessage);
        m_bAlreadyLocked = bAlreadyLocked;
    }

    public boolean isLocked()
    {
    	return (m_bAlreadyLocked);
    }
}
