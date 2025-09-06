package com.uhills.finance.easetax.persist;

/**
 * This class represents a factory for instantiating
 * the implementations of our persistent interfaces.
 * With this factory, we have a single point where
 * persistent data store objects get created.  If our
 * implementation changes (i.e. file vs. DB), we only
 * have to change the factory to instantiate the right
 * object(s).
 *
 * @author Marc Hamilton
 * @date   August 1, 2003
 *
 */

import com.uhills.finance.easetax.persist.file.*;

public class PersistenceFactory
{
    private static PersistenceFactory       m_instance;

    private FilePersistenceManager          m_manager;

    /**
     * We have a private constructor because this object
     * is a Singleton.
     *
     */
    private PersistenceFactory()
    {
        m_manager = new FilePersistenceManager();
    }

    /**
     * Builds and returns our Singleton instance of
     * this class.
     *
     * @return the static instance of this class
     */
    public static PersistenceFactory getInstance()
    {
        if (m_instance == null)
            m_instance = new PersistenceFactory();

        return (m_instance);
    }

    /**
     * Returns the object used to persist data.
     *
     * @return the static instance of this class
     */
    public FileDatabase getDataRepository()
    {
        return (m_manager.getDataRepository());
    }

    /**
     * Returns the object used to persist data.
     *
     * @return the static instance of this class
     */
    public FilePersistenceManager getPersistenceManager()
    {
        return (m_manager);
    }
}