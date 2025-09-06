package com.uhills.util.database;

/**
 * Class:      TableRecordCount
 * Author:     Marc Hamilton
 * Date:       September 5, 2000
 *
 * This utility class contains functionality to perform a record
 * count on a table or other appropriate SQL clause.
 *
 */

import java.sql.*;

public class TableRecordCount
{
    private Connection              m_dbConnection;
    private String                  m_strTableList;
    private String                  m_strSupplementalClause;
    private String                  m_strFieldList = "*";

    public TableRecordCount(Connection dbConnection)
    {
        m_dbConnection = dbConnection;
    }

    public TableRecordCount(Connection dbConnection, String strTableList)
    {
        this(dbConnection);
        m_strTableList = strTableList;
    }

    public TableRecordCount(Connection dbConnection, String strTableName, String strSupplementalClause)
    {
        this(dbConnection, strTableName);
        m_strSupplementalClause = strSupplementalClause;
    }

    public void setFieldList(String strFieldList)
    {
        m_strFieldList = strFieldList;
    }

    public String getFieldList()
    {
        return (m_strFieldList);
    }

    private String buildSQLString()
    {
        StringBuffer            strbufSQL = new StringBuffer();

        strbufSQL.append("SELECT COUNT (" + m_strFieldList + ")");
        strbufSQL.append("FROM " + m_strTableList);

        if (m_strSupplementalClause != null)
            strbufSQL.append(m_strSupplementalClause);

        return (strbufSQL.toString());
    }

    public long getRecordCount() throws SQLException
    {
        Statement       statement;
        ResultSet       results;
        String          strSQL;
        long            lRecordCount = 0;

        strSQL = buildSQLString();

        statement = m_dbConnection.createStatement();
        results = statement.executeQuery(strSQL);

        if (results.next())
        {
            lRecordCount = results.getLong(1);
        }

        results.close();
        statement.close();

        return (lRecordCount);
    }
}
