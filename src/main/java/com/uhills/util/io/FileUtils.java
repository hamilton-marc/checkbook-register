package com.uhills.util.io;

import java.io.*;

public class FileUtils
{
    public static final String              LINE_SEPARATOR = System.getProperty("line.separator");

    public static void _copy(File source, File destination) throws IOException, InterruptedException
    {
        String      strOperatingSystem = System.getProperty("os.name");
        String      strCopyCommand;

        if (strOperatingSystem.toLowerCase().startsWith("windows"))
        {
            strCopyCommand = "cmd /c copy " + source.getAbsolutePath() + " " +
                                       destination.getAbsolutePath();
        }
        else
        {
            strCopyCommand = "cp " + source.getAbsolutePath() + " " +
                                     destination.getAbsolutePath();
        }

        Process     proc = Runtime.getRuntime().exec(strCopyCommand);

        proc.waitFor();
    }

    public static void copy(File source, File destination) throws IOException
    {
        copy(source, destination, false);
    }

    public static void copy(File source, File destination, boolean bAppend) throws IOException
    {
        BufferedInputStream     sourceStream = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream    destinationStream = new BufferedOutputStream(new FileOutputStream(destination));
        int                     iChar;

        while ((iChar = sourceStream.read()) >= 0)
        {
            destinationStream.write(iChar);
        }

        destinationStream.flush();
        destinationStream.close();
        sourceStream.close();
    }

}