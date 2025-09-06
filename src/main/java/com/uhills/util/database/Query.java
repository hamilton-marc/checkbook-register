package com.uhills.util.database;

/**
 * This is a useful abstract class for creating query classes
 * for inserting and retrieving our objects into and out of
 * the database.
 *
 * @author Marc Hamilton
 * @date   June 16, 2001
 *
 */

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public abstract class Query
{
    public static final String      ORDER_BY_ASCENDING              = "ASC";
    public static final String      ORDER_BY_DESCENDING             = "DESC";

    private static final char       SPECIAL_INSERT_CHARS_ARRAY[]    = {'\\', '\'', '"'};
    private static final String     SPECIAL_INSERT_CHARS_STRING     = new String(SPECIAL_INSERT_CHARS_ARRAY);

//  private static final char       SPECIAL_SELECT_CHARS_ARRAY[]    = {'\\', '%', '_', '\'', '"'};
//  private static final String     SPECIAL_SELECT_CHARS_STRING     = new String(SPECIAL_SELECT_CHARS_ARRAY);

    private static final char       STRING_QUALIFIER                = '\'';

    private SimpleDateFormat        m_dateFormatter                 = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");
    private SimpleDateFormat        m_dateTimeFormatter             = new SimpleDateFormat("''yyyy-MM-dd''");
/*
    private SimpleDateFormat        m_dateFormatter                 = new SimpleDateFormat("'#'MM-dd-yyyy HH:mm:ss'#'");
    private SimpleDateFormat        m_dateTimeFormatter             = new SimpleDateFormat("'#'MM-dd-yyyy'#'");
*/
    private long                    m_lStartRow = 0;
    private int                     m_iMaxRows;
    private String                  m_strDBType;
    private String                  m_strSQL;
    private Vector                  m_vecOrderByCriteria;

    private boolean                 m_bDebugMode;

    protected Connection            m_dbConnection;
    protected ResultSet             m_queryResults;
    protected Statement             m_queryStatement;
    protected PreparedStatement     m_preparedStatement;

    /**
     * This is the constructor for the class.  It expects a valid
     * database connection to initialize our internal query objects.
     *
     */
    public Query(Connection dbConnection)
    {
        m_dbConnection = dbConnection;
    }

    /**
     * This version of the constructor takes a database connection
     * and the maximum number of rows the query should return.
     *
     * This is useful for data views which show 30 rows at a time
     * for example.
     *
     */
    public Query(Connection dbConnection, int iMaxRows)
    {
        this(dbConnection);
        m_iMaxRows = iMaxRows;
    }

    public void setDebugMode(boolean bNewValue)
    {
        m_bDebugMode = bNewValue;
    }

    public boolean getDebugMode()
    {
        return (m_bDebugMode);
    }

    /**
     * Sets the maximum number of rows this
     * query should produce.
     *
     * @param iNewValue - max rows
     */
    public void setMaxRows(int iNewValue)
    {
        m_iMaxRows = iNewValue;
    }

    /**
     * Gets the maximum number of rows this
     * query should produce.
     *
     * @return max rows
     */
    public int getMaxRows()
    {
        return (m_iMaxRows);
    }

    public void setStartRow(long lNewValue)
    {
        m_lStartRow = lNewValue;
    }

    public long getStartRow()
    {
        return (m_lStartRow);
    }

    /**
     * Sets the type of database we're issuing
     * queries against.  This is useful in case
     * we need to have brand-specific queries.
     *
     * @param strDBType - database type
     */
    public void setDBType(String strDBType)
    {
        m_strDBType = strDBType;
    }

    /**
     * Returns the type of database we're issuing
     * queries against.
     *
     * @return database type
     */
    public String getDBType()
    {
        return (m_strDBType);
    }

    /**
     * Returns the SQL statement to be used by this query.
     *
     * @return a SQL statement
     */
    protected String getSQLStatement()
    {
        return (m_strSQL);
    }

    /**
     * Sets the SQL statement to be used by this query.
     *
     * @param strNewValue - a SQL statement
     */
    protected void setSQLStatement(String strNewValue)
    {
        m_strSQL = strNewValue;
    }

    /**
     * Adds a field to the "ORDER BY" list.  This is
     * used when creating the ORDER BY clause of the
     * SQL statement
     *
     * @param strFieldName - a field to be added to the list
     */
    public void addOrderByField(String strFieldName)
    {
        if (m_vecOrderByCriteria == null)
            m_vecOrderByCriteria = new Vector();

        m_vecOrderByCriteria.addElement(strFieldName);
    }

    public void addOrderByField(String strFieldName, String strOrder)
    {
        addOrderByField(strFieldName + " " + strOrder);
    }

    public void addOrderByField(String strFieldName, boolean bDescending)
    {
        addOrderByField(strFieldName + " " + (bDescending ? ORDER_BY_DESCENDING : ORDER_BY_ASCENDING));
    }

    protected void setOrderByCriteria(Vector vecOrderByCriteria)
    {
        m_vecOrderByCriteria = vecOrderByCriteria;
    }

    /**
     * Retrieves a comma delimited
     *
     * @param strFieldName - a field to be added to the list
     */
    protected String getOrderByCriteria()
    {
        if (m_vecOrderByCriteria == null ||
            m_vecOrderByCriteria.size() == 0)
            return ("");

        String[]        fieldArray = new String[m_vecOrderByCriteria.size()];

        m_vecOrderByCriteria.copyInto(fieldArray);

        return (" ORDER BY " + getSQLFieldList(fieldArray));
    }

    /**
     * Given a vector of SQL criteria expressions, this method
     * appends them together into one expression separated by
     * "AND".  Useful for building a "WHERE" clause.
     *
     * @param vecCriteria - a collection of expression criteria
     * @return an expression
     *
     */
    protected String buildSQLCriteriaString(Vector vecCriteria)
    {
        if (vecCriteria == null ||
            vecCriteria.size() <= 0)
        {
            return ("");
        }

        StringBuffer        strbufSQLCriteria = new StringBuffer();

        for (Enumeration e = vecCriteria.elements(); e.hasMoreElements(); )
        {
            if (strbufSQLCriteria.length() > 0)
                strbufSQLCriteria.append(" AND ");

            strbufSQLCriteria.append(e.nextElement());
        }

        return (strbufSQLCriteria.toString());
    }

    /**
     * Runs the query and returns the object representations
     * of the rows from the result set.  If the start row is
     * greater than 1, we first seek to that row before
     * returning records.
     *
     * @param lStartRow - the row from where to start retrieving data
     * @return a Vector of Objects
     *
     */
    public Vector getList(long lStartRow) throws SQLException
    {
        Vector          objectList = new Vector(0);
        Statement       stmt;
        ResultSet       results;
        String          strSQL;
        boolean         bEndOfCursor = false;
        long            i;

        strSQL = getSQLStatement() + getOrderByCriteria();

        if (m_bDebugMode)
            System.out.println(new java.util.Date() + " " + strSQL);

        stmt = m_dbConnection.createStatement();
        results = stmt.executeQuery(strSQL);

        // move to the specific row we are looking for
        // JDBC 1.0 compatible
        for (i=1; i < lStartRow && !bEndOfCursor; i++)
        {
            bEndOfCursor = !results.next();
        }

        if (!bEndOfCursor)
        {
            boolean     bDone = false;
            i=0;

            while (results.next() && !bDone)
            {
                Object  obj = getObjectFromResultSet(results);

                objectList.addElement(obj);
                i++;

                bDone = (m_iMaxRows > 0 && i >= m_iMaxRows);
            }
        }

        results.close();
        stmt.close();

        return (objectList);
    }

    /**
     * The findFirst/findNext methods provide for querying against
     * large recordsets.  These methods can be used to iterate
     * through the recordset, one record at a time instead of
     * retrieving the entire recordset in a Vector.
     *
     * @return the first record in the ResultSet
     *
     */
    public Object findFirst() throws SQLException
    {
//      Vector          objectList = new Vector(0);
        String          strSQL;

        strSQL = getSQLStatement() + getOrderByCriteria();

        if (m_bDebugMode)
            System.out.println(new java.util.Date() + " " + strSQL);

        m_queryStatement = m_dbConnection.createStatement();
        m_queryResults = m_queryStatement.executeQuery(strSQL);

        return (findNext());
    }


    /**
     * The findFirst/findNext methods provide for querying against
     * large recordsets.  These methods can be used to iterate
     * through the recordset, one record at a time instead of
     * retrieving the entire recordset in a Vector.
     *
     * @return the next record in the ResultSet
     *
     */
    public Object findNext() throws SQLException
    {
        Object  obj = null;

        if (m_queryResults.next())
        {
            obj = getObjectFromResultSet(m_queryResults);
        }

        return (obj);
    }

    /**
     * This version of getList() is the default method which
     * returns data starting at the first row.
     *
     * @return a Vector of Objects
     *
     */
    protected Vector getList() throws SQLException
    {
        return (getList(m_lStartRow));
    }

    /**
     * Using a ResultSet from a query, we take the fields
     * and put them into our internal object representation.
     *
     */
    protected abstract void getObjectFromResultSet(ResultSet results, Object obj) throws SQLException;

    protected Object getObjectFromResultSet(ResultSet results) throws SQLException
    {
        Object obj = createObject();

        getObjectFromResultSet(results, obj);

        return (obj);
    }

    /**
     * Subclasses should implement this, specific to the objects
     * being retrieved from the database.
     *
     * @return a User object representation of the current row
     */
    protected abstract Object createObject();

    /**
     * These methods are used for formatting fields for SQL
     * INSERT or UPDATE statements.
     *
     */
    protected String formatFieldValue(String strEntry)
    {
        return (formatFieldValue(strEntry, true));
    }

    protected String formatFieldValue(String strEntry, boolean bIncludeStringQualifier)
    {
        String      strReformattedString;

        if (strEntry == null)
            return("NULL");

        strReformattedString = handleSpecialChars(strEntry);

        if (bIncludeStringQualifier)
            return (STRING_QUALIFIER + strReformattedString + STRING_QUALIFIER);
        else
            return (strReformattedString);
    }

    protected String formatFieldValue(boolean bEntry)
    {
        return (bEntry ? "1" : "0");
    }

    protected String formatFieldValue(java.util.Date dtEntry)
    {
        return (formatFieldValue(dtEntry, false));
    }

    protected String formatFieldValue(java.util.Date dtEntry, boolean bIncludeTime)
    {
        return (dtEntry == null ? "NULL" :
                (bIncludeTime ? m_dateFormatter.format(dtEntry) : m_dateTimeFormatter.format(dtEntry)));
    }

    protected String formatFieldValue(long lValue)
    {
        return (Long.toString(lValue));
    }

    protected String formatFieldValue(int iValue)
    {
        return (Integer.toString(iValue));
    }

    protected String formatFieldValue(float fValue)
    {
        return (Float.toString(fValue));
    }

    protected String formatFieldValue(double dValue)
    {
        return (Double.toString(dValue));
    }

    protected String formatFieldValue(Boolean bool)
    {
        if (bool == null)
            return ("NULL");

        return (formatFieldValue(bool.booleanValue()));
    }

    protected String formatFieldValue(char cValue)
    {
        return (STRING_QUALIFIER + String.valueOf(cValue) + STRING_QUALIFIER);
    }

    /**
     * This method takes care of any funny characters that
     * the user may have typed in.  These include apostrophes,
     * quotes, percentage signes, etc.  These are reserved tokens
     * in SQL.  A backslash escape character must be placed in
     * front of these special characters for them to be properly
     * inserted into the database.
     *
     * @param strField - String we are evaluating
     * @return String after funny chars are delt with
     */
    private String handleSpecialChars(String strField)
    {
        StringTokenizer     tokenizer = new StringTokenizer(strField, SPECIAL_INSERT_CHARS_STRING, true);
        StringBuffer        strbufResult = new StringBuffer();

        while (tokenizer.hasMoreTokens())
        {
            String      strElement = tokenizer.nextToken();

            // add the escape char if necessary
            if (isSpecialChar(strElement))
                strbufResult.append('\\');

            strbufResult.append(strElement);
        }

        return (strbufResult.toString());
    }

    /**
     * Given a String, this method will determine if that
     * String represents one of those special SQL characters.
     *
     * @param strBuffer - String we are evaluating
     * @return true if it's a special char
     */
    private boolean isSpecialChar(String strBuffer)
    {
        if (strBuffer == null ||
            strBuffer.length() != 1)
            return (false);

        for (int i=0; i < SPECIAL_INSERT_CHARS_ARRAY.length; i++)
        {
            if (strBuffer.charAt(0) == SPECIAL_INSERT_CHARS_ARRAY[i])
                return (true);
        }

        return (false);
    }

    /**
     * Creates a comma delimited String with the fields contained
     * in the field array.
     *
     * @return fields in field array
     */
    protected String getSQLFieldList(String[] fieldArray)
    {
        StringBuffer        strbufFieldList = new StringBuffer();

        for (int i=0; i < fieldArray.length; i++)
        {
            if (i > 0) strbufFieldList.append(", ");

            strbufFieldList.append(fieldArray[i]);
        }

        return (strbufFieldList.toString());
    }

    /**
     * This convenient little method allows us to retrieve
     * the next primary key id for a given table.  This is
     * quite useful for performing inserts.
     *
     * @param strTableName - the name of the table
     * @param strIdField - the field which denotes the primary key
     * @return the next number in the sequence
     */
    protected long getNextId(String strTableName, String strIdField, String strWhereClause) throws SQLException
    {
        long            lNewId = 0;
        Statement       stmt;
        ResultSet       results;
        String          strSQL;

        strSQL = "SELECT MAX(" + strIdField + ") FROM " + strTableName + " " + strWhereClause;

        stmt = m_dbConnection.createStatement();
        results = stmt.executeQuery(strSQL);

        if (results.next())
        {
            lNewId = results.getLong(1);
        }

        results.close();
        stmt.close();

        lNewId++;

        return (lNewId);
    }

    protected long getNextId(String strTableName, String strIdField) throws SQLException
    {
        return (getNextId(strTableName, strIdField, ""));
    }

    /**
     * Given a field list, a table and an optional WHERE clause,
     * this method uses the SELECT COUNT SQL command to retrieve
     * a record count of the rows in the table.
     *
     * @param strFieldList - a comma delimited set of fields
     * @param strTableName - the table qualifier
     * @param strWhereClause - query filter clause
     * @return row count
     *
     */
    protected long computeRecordCount(String strFieldList, String strTableName, String strWhereClause) throws SQLException
    {
        long            lRecordCount = 0;
        Statement       stmt;
        ResultSet       results;
        String          strSQL;

        strSQL = "SELECT COUNT(" + strFieldList + ") FROM " + strTableName + " " + strWhereClause;

        if (m_bDebugMode)
            System.out.println(new java.util.Date() + " " + strSQL);

        stmt = m_dbConnection.createStatement();
        results = stmt.executeQuery(strSQL);

        if (results.next())
        {
            lRecordCount = results.getLong(1);
        }

        results.close();
        stmt.close();

        return (lRecordCount);
    }

    /**
     * This is a clean up routine which closes any open
     * ResultSets or Statements.  This should be called
     * when done using the Query.
     *
     */
    public void close()
    {
        try
        {
            if (m_queryResults != null)
                m_queryResults.close();

            if (m_queryStatement != null)
                m_queryStatement.close();

            if (m_preparedStatement != null)
                m_preparedStatement.close();
        }
        catch (SQLException ex)
        {
            // if there is an error closing the
            // the statement or result set, there
            // are bigger problems than this...
        }

        m_queryResults = null;
        m_queryStatement = null;
        m_preparedStatement = null;
    }
}
