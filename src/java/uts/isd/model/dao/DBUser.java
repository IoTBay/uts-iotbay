/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.User;

import java.util.*;
import java.sql.*;
import uts.isd.util.Hash;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author rhys
 */

public class DBUser implements IUser {
    
    private Connection conn;
        
    public DBUser() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    @Override
    public User authenticateUser(String email, String password)
    {
        try {
            String passwordHash = Hash.SHA256(password);
            
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Users WHERE Email = ? AND Password = ?");
            p.setString(1, email);
            p.setString(2, passwordHash);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("authenticateUser returned no records for email: "+email);
                return null; //No records returned
            }
            return new User(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to authenticateUser", e);
            return null;
        }
    }

    
    @Override
    public User getUserById(int id) 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Users WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getUserById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new User(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserById", e);
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Users WHERE Email = ?");
            p.setString(1, email);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                Logging.logMessage("getUserByEmail returned no records for Email: "+email);
                return null; //No records returned
            }
            return new User(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserByEmail", e);
            return null;
        }
    }

    @Override
    public Iterable<User> getUsersByFirstName(String firstName) 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Users WHERE FirstName = ?");
            p.setString(1, firstName);
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            List<User> users = new ArrayList<User>();
            
            while (rs.next())
            {
                users.add(new User(rs));
            }
            return users;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUsersByFirstName", e);
            return null;
        }
    }

    @Override
    public Iterable<User> getAllUsers() 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Users");
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            List<User> users = new ArrayList<User>();
            
            while (rs.next())
            {
                users.add(new User(rs));
            }
            return users;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllUsers", e);
            return null;
        }
    }
    
     @Override
    public boolean addUser(User u)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.Users (CustomerID, Email, Password, AccessLevel, Biography, BirthDate, Gender, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, u.getCustomerId());
            p.setString(2, u.getEmail());
            p.setString(3, u.getPassword());
            p.setInt(4, u.getAccessLevel());
            p.setString(5, u.getBiography());
            p.setDate(6, new java.sql.Date(u.getBirthDate().getTime()));
            p.setInt(7, u.getGender());
            p.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(9, 1); //TODO: Pass in current user object
            
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addUser", e);
            return false;
        }
    }

    @Override
    public boolean updateUser(User u) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.Users SET CustomerID = ?,  Email = ?, Password = ?, AccessLevel = ?, Biography = ?, BirthDate = ?, Gender = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setInt(1, u.getCustomerId());
            p.setString(2, u.getEmail());
            p.setString(3, u.getPassword());
            p.setInt(4, u.getAccessLevel());
            p.setString(5, u.getBiography());
            p.setDate(6, new java.sql.Date(u.getBirthDate().getTime()));
            p.setInt(7, u.getGender());
            
            //Modified Date
            p.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(9, 1); //TODO: Pass in current user object
            //WHERE ID = ?
            p.setInt(10, u.getId());
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to updateUser", e);
            return false;
        }
    }

    @Override
    public boolean deleteUserById(int id) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.Users WHERE ID = ?");
            //WHERE ID = ?
            p.setInt(10, id);
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteUserById", e);
            return false;
        }
    }
}