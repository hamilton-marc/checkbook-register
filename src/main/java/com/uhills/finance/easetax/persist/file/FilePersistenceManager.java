/*
 * Created on Aug 29, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.persist.file;

import java.io.*;
import java.text.*;

import com.uhills.util.exception.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FilePersistenceManager
{
    private FileDatabase                m_data;
    private File                        m_file;
//  private FileOutputStream            m_tmpFileOutputStream;

    public FilePersistenceManager()
    {
    }

    public FilePersistenceManager(File file)
    {
        this();
        setFile(file);
    }

    public void setFile(File file)
    {
        m_file = file;
    }

    public File getFile()
    {
        return (m_file);
    }

    public void createNewDataRepository() throws PersistenceException
    {
        m_data = new FileDatabase();
        m_data.initialize();
        createDefaultEntries();
    }

    public FileDatabase getDataRepository()
    {
        return (m_data);
    }

    private File getTmpFilePath() throws IOException
    {
        File        dir = new File(m_file.getParent());
        String      strFileName = m_file.getName();

        File        tmpFile = File.createTempFile(strFileName, null, dir);

        return (tmpFile);
    }

    public void save() throws PersistenceException
    {
        try
        {
            File                    tmpFilePath = getTmpFilePath();
            FileOutputStream        fileOutput = new FileOutputStream(tmpFilePath);
            ObjectOutputStream      objectOutput = new ObjectOutputStream(fileOutput);

            objectOutput.writeObject(m_data);

            objectOutput.flush();
            objectOutput.close();

            m_file.delete();
            tmpFilePath.renameTo(m_file);
        }
        catch (IOException ex)
        {
            throw new PersistenceException(ex.getMessage());
        }
    }

    public void load(String strFilePath) throws PersistenceException
    {
        m_file = new File(strFilePath);

        load();
    }

    public void load() throws PersistenceException
    {
        if (m_file == null)
            throw new PersistenceException("No file has been selected");

        try
        {
            FileInputStream         fileInput = new FileInputStream(m_file);
            ObjectInputStream       objectInput = new ObjectInputStream(fileInput);

            m_data = (FileDatabase) objectInput.readObject();

            objectInput.close();
        }
        catch (IOException ex)
        {
            throw new PersistenceException(ex.getMessage());
        }
        catch (ClassNotFoundException ex)
        {
            throw new PersistenceException(ex.getMessage());
        }
    }

    public void restoreFactoryDefaults() throws PersistenceException
    {
        createDefaultEntries();
    }

    private void createDefaultEntries() throws PersistenceException
    {
        try
        {
            new DefaultData(m_data).createData();
//          new SampleData(m_data).createData();
        }
        catch (ParseException ex)
        {
            throw new PersistenceException(ex.getMessage());
        }
    }
}
