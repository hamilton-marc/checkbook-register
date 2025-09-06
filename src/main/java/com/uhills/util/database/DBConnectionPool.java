package com.uhills.util.database;

/**
 * Class:      DBConnectionPool
 * Author:     Marc Hamilton
 * Date:       July 8, 2001
 *
 * This class is a simple database connection pool provider.
 * The consumer can get and return Connections to this provider.
 * It will keep track of the connections in use and the ones
 * that are idle.
 *
 */

import java.util.Vector;
import java.util.Enumeration;
import java.sql.*;

public class DBConnectionPool
{
    private Vector                  m_vecConnectionsInUse = new Vector();
    private Vector                  m_vecIdleConnections = new Vector();
    private DBConnectionProperties  m_defaultDBParms;

    /**
     * The constructor for this class needs the appropriate
     * connections to make a connection to the database.
     *
     * @param defaultParms - database connection properties
     */
    public DBConnectionPool(DBConnectionProperties defaultParms) throws ClassNotFoundException
    {
        m_defaultDBParms = defaultParms;

        Class.forName(m_defaultDBParms.JDBCDriver);
    }

    /**
     * Gets an idle connection if available.  Otherwise, it
     * creates one and puts it in our "in use" list.
     *
     * @return an open database connection
     */
    public synchronized Connection getConnection() throws SQLException
    {
        Connection      conn;

        if (m_vecIdleConnections.size() == 0)
        {
//          System.out.println(new java.util.Date() + " Creating database connection to " + m_defaultDBParms.databaseURL);

            conn = DriverManager.getConnection(m_defaultDBParms.databaseURL,
                                               m_defaultDBParms.userId,
                                               m_defaultDBParms.password);

            m_vecConnectionsInUse.addElement(conn);
        }
        else
        {
            conn = (Connection) m_vecIdleConnections.firstElement();

            // take care of "stale" connections

            if (conn.isClosed())
            {
//              System.out.println(new java.util.Date() + " Reconnecting to database: " + m_defaultDBParms.databaseURL);

                conn = DriverManager.getConnection(m_defaultDBParms.databaseURL,
                                                   m_defaultDBParms.userId,
                                                   m_defaultDBParms.password);
            }

            m_vecConnectionsInUse.addElement(conn);
            m_vecIdleConnections.removeElementAt(0);
        }

        return (conn);
    }

    /**
     * Returns a "leased" connection to the pool.
     *
     * @param conn - an open database connection
     */
    public synchronized void returnConnection(Connection conn)
    {
        m_vecConnectionsInUse.removeElement(conn);
        m_vecIdleConnections.addElement(conn);
    }

    /**
     * Closes all of our currently opened connections.
     *
     */
    public void closeAllConnections()
    {
        try
        {
            closeConnections(m_vecIdleConnections);
            closeConnections(m_vecConnectionsInUse);
        }
        catch (SQLException ex)
        {
        }
    }

    /**
     * Given a vector of connection objects, we iterate
     * through them all to make sure they're all closed.
     *
     * @param vecConnections - a Vector of database connections
     */
    private void closeConnections(Vector vecConnections) throws SQLException
    {
        for (Enumeration e = vecConnections.elements(); e.hasMoreElements(); )
        {
            Connection      conn = (Connection) e.nextElement();

            if (conn != null &&
                !conn.isClosed())
                conn.close();
        }

        vecConnections.removeAllElements();
    }
}
