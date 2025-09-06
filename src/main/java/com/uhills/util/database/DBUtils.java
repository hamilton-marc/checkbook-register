package com.uhills.util.database;

/**
 * This class provides general all-purpose methods for actions
 * that might be performed against a database.
 *
 * @author Marc Hamilton
 * @date   August 1, 2001
 */

import java.sql.*;

public class DBUtils
{

    public static String getString(ResultSet results, String strFieldName) throws SQLException
    {
        String          strValue;

        strValue = results.getString(strFieldName);

        if (strValue != null)
            strValue = strValue.trim();

        return (strValue);
    }

    public static String getString(ResultSet results, String strFieldName, String strDefaultValue) throws SQLException
    {
        String          strValue = getString(results, strFieldName);

        if (strValue == null)
            strValue = strDefaultValue;

        return (strValue);
    }

    public static String getString(ResultSet results, int iFieldNumber, String strDefaultValue) throws SQLException
    {
        String          strValue = getString(results, iFieldNumber);

        if (strValue == null)
            strValue = strDefaultValue;

        return (strValue);
    }

    public static String getString(ResultSet results, int iFieldNumber) throws SQLException
    {
        String          strValue;

        strValue = results.getString(iFieldNumber);

        if (strValue != null)
            strValue = strValue.trim();

        return (strValue);
    }

    public DBUtils()
    {
    }
}