package com.uhills.util.io;

import java.io.*;

public class FileRecordCount
{
    private String          m_strFilePath;

    public FileRecordCount(String strFilePath)
    {
        m_strFilePath = strFilePath;
    }

    public long getRecordCount() throws IOException, FileNotFoundException
    {
        BufferedReader      reader = new BufferedReader(new FileReader(m_strFilePath));
        String              strBuf = reader.readLine();
        long                lRecordCount = 0;

        while (strBuf != null)
        {
            lRecordCount++;
            strBuf = reader.readLine();
        }

        return (lRecordCount);
    }
}