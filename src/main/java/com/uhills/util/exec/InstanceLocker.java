/*
 * Created on Feb 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.util.exec;

import java.io.*;
import java.nio.channels.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InstanceLocker
{
    private File                m_lockFile;
    private FileLock            m_fileLock;
    private FileChannel         m_fileChannel;
    private FileOutputStream    m_fileOutputStream;

    public InstanceLocker(String strLockFilePath)
    {
        m_lockFile = new File(strLockFilePath);
    }

    public InstanceLocker(File lockFile)
    {
        m_lockFile = lockFile;
    }

    public void lock() throws InstanceLockingException, ExecutionException
    {
        try
        {
            m_lockFile.createNewFile();

            m_fileOutputStream = new FileOutputStream(m_lockFile);
            m_fileChannel = m_fileOutputStream.getChannel();
            m_fileLock = m_fileChannel.tryLock();
        }
        catch (Exception ex)
        {
            throw new ExecutionException(ex.getMessage());
        }

        if (m_fileLock == null)
            throw new InstanceLockingException(true, "Another instance of this application is already running.");
    }

    public void unlock() throws ExecutionException
    {
        if (m_fileLock == null) return;

        try
        {
            m_fileLock.release();
            m_fileChannel.close();
            m_fileOutputStream.close();

            if (!m_lockFile.delete())
                throw new IOException("Unable to delete lock file " + m_lockFile);
        }
        catch (Exception ex)
        {
            throw new ExecutionException("An error occurred releasing the lock on the instance of this application\n" +
                                         ex.getMessage());
        }
    }

}
