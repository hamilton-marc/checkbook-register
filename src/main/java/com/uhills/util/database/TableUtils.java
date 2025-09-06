package com.uhills.util.database;

/**
 * Class:      TableUtils
 * Project:    RIUtils
 * Author:     Marc Hamilton
 * Date:       August 1, 2001
 *
 * This class contains some useful methods for manipulating database
 * tables Simple operations such as copying, deleting, and clearing
 * can be performed with these routines.
 *
 * Since SQL statements can differ depending on the database, the
 * database type should be set to ensure the correct SQL statement
 * is being used.
 *
 */

import java.sql.*;

public class TableUtils
{
    private Connection      m_dbConnection;
    private String          m_strDBType = DBConnectionProperties.DATABASE_TYPE_DEFAULT;

    public TableUtils(Connection dbConn)
    {
        m_dbConnection = dbConn;
    }

    public TableUtils(Connection dbConn, String strDBType)
    {
        m_dbConnection = dbConn;
        m_strDBType = strDBType;
    }

    public int copyRecords(String strFromTable, String strToTable) throws SQLException
    {
        return (execute("INSERT INTO " + strFromTable + " SELECT * FROM " + strToTable));
    }

    public int copyTable(String strFromTable, String strToTable) throws SQLException
    {
        return (copyTable(strFromTable, strToTable, true));
    }

    public int copyTable(String strFromTable, String strToTable, boolean bIncludeData) throws SQLException
    {
        String              strSQL;

        if (m_strDBType.equalsIgnoreCase(DBConnectionProperties.DATABASE_TYPE_ORACLE))
        {
            strSQL = "CREATE TABLE " + strToTable + " AS SELECT * FROM " + strToTable;
        }
        else
        {
            strSQL = "SELECT * INTO " + strToTable + " FROM " + strToTable;
        }

        if (!bIncludeData)
            strSQL += " WHERE 1=0";   // this clause will prevent any records
                                      // from being copied

        return (execute(strSQL));
    }

    public int clearTable(String strTableName) throws SQLException
    {
        return (execute("TRUNCATE TABLE " + strTableName));
    }

    public int deleteTable(String strTableName) throws SQLException
    {
        return (execute("DROP TABLE " + strTableName));
    }

    public int deleteIndex(String strIndexName) throws SQLException
    {
        return (execute("DROP INDEX " + strIndexName));
    }

    private int execute(String strSQL) throws SQLException
    {
        Statement           stmt;
        int                 iRowsCopied;

        stmt = m_dbConnection.createStatement();

        iRowsCopied = stmt.executeUpdate(strSQL);
        stmt.close();

        return (iRowsCopied);
    }
}