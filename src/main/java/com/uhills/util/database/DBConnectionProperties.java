package com.uhills.util.database;

/**
 * Class:      DBConnectionProperties
 * Project:    RetrievalEngine
 * Author:     Marc Hamilton
 * Date:       September 5, 2000
 *
 * This class contains properties necessary to make a connection to
 * a database.  It includes the name of the driver, the database URL,
 * the user id and password.
 *
 */

import java.io.Serializable;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

import com.uhills.util.io.FileUtils;

public class DBConnectionProperties implements Serializable
{
    public static final String     DATABASE_TYPE_DEFAULT       = "Default";
    public static final String     DATABASE_TYPE_MY_SQL        = "MySQL";
    public static final String     DATABASE_TYPE_POSTGRES_SQL  = "PostgresSQL";
    public static final String     DATABASE_TYPE_SQL_SERVER    = "SQLServer";
    public static final String     DATABASE_TYPE_DB2           = "DB2";
    public static final String     DATABASE_TYPE_ORACLE        = "Oracle";
    public static final String     DATABASE_TYPE_INFORMIX      = "Informix";
    public static final String     DATABASE_TYPE_SQL_ANYWHERE  = "SQLAnywhere";
    public static final String     DATABASE_TYPE_ACCESS        = "Access";

    private static final String     DEFAULT_JDBC_DRIVER         = "sun.jdbc.odbc.JdbcOdbcDriver";

    public String                   type            = DATABASE_TYPE_DEFAULT;
    public String                   JDBCDriver      = DEFAULT_JDBC_DRIVER;
    public String                   databaseURL     = "";
    public String                   userId          = "";
    public String                   password        = "";

    /**
     * The constructor for this class can optionally take all 4 property
     * values as arguments.
     *
     */
    public DBConnectionProperties()
    {
    }

    public DBConnectionProperties(String strJDBCDriver, String strDatabaseURL, String strUserId, String strPassword)
    {
        setDBConnectionParms(strJDBCDriver, strDatabaseURL, strUserId, strPassword);
    }

    /**
     * Sets all 4 property values to make a database connection.  Note that
     * this does not actually make a connection to the database however.
     *
     */
    public void setDBConnectionParms(String strJDBCDriver, String strDatabaseURL, String strUserId, String strPassword)
    {
        JDBCDriver      = strJDBCDriver;
        databaseURL     = strDatabaseURL;
        userId          = strUserId;
        password        = strPassword;
    }

    public String toString()
    {
        return("type\t\t= "         + type          + FileUtils.LINE_SEPARATOR +
               "JDBCDriver\t= "     + JDBCDriver    + FileUtils.LINE_SEPARATOR +
               "databaseURL\t= "    + databaseURL   + FileUtils.LINE_SEPARATOR +
               "userId\t\t= "       + userId        + FileUtils.LINE_SEPARATOR +
               "password\t= "       + password      + FileUtils.LINE_SEPARATOR);
    }

    /**
     * Creates a new database connection using the associated parameters.
     *
     * @return a new Connection object
     */
    public Connection createDBConnection() throws ClassNotFoundException, SQLException
    {
        Connection      dbConnection;

        Class.forName(JDBCDriver);

        dbConnection = DriverManager.getConnection(databaseURL,
                                                   userId,
                                                   password);

        return (dbConnection);
    }
}
