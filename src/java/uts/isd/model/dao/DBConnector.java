/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 2
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector extends DB {

    public DBConnector() throws ClassNotFoundException, SQLException
    {
        Class.forName(driver);
        
        //Connection object is static so we don't create a bunch of new connections.
        if (this.conn == null)
            conn = DriverManager.getConnection(URL, dbuser, dbpass);
    }

    public Connection openConnection()
    { 
        return this.conn;
    }

    public void closeConnection() throws SQLException
    {
        this.conn.close();
    }
}
