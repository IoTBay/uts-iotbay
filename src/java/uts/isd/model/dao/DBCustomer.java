/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Customer;

import java.util.*;
import java.sql.*;
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
public class DBCustomer implements ICustomer {
    
    private Connection conn;
        
    public DBCustomer() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    @Override
    public Customer getCustomerById(int id)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Customers WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getCustomerById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new Customer(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserById", e);
            return null;
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Customers WHERE Email = ?");
            p.setString(1, email);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                Logging.logMessage("getUserByEmail returned no records for Email: "+email);
                return null; //No records returned
            }
            return new Customer(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserByEmail", e);
            return null;
        }
    }

    @Override
    public Iterable<Customer> getAllCustomers() 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Customers");
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            List<Customer> customers = new ArrayList<Customer>();
            
            while (rs.next())
            {
                customers.add(new Customer(rs));
            }
            return customers;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllCustomers", e);
            return null;
        }
    }
    
     @Override
    public boolean addCustomer(Customer c)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.Customers (Email, FirstName, LastName, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?)",
                    //Added this to return the primary key of this new record
                    //https://db.apache.org/derby/docs/10.2/ref/crefjavstateautogen.html
                    Statement.RETURN_GENERATED_KEYS);
            p.setString(1, c.getEmail());
            p.setString(2, c.getFirstName());
            p.setString(3, c.getLastName());
            p.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(5, 1); //TODO: Pass in current user object
            
            //Was insert successful?
            boolean added = (p.executeUpdate() > 0);
            
            //Now that customer is added, try and get the last inserted record ID.
            ResultSet rs = p.getGeneratedKeys();
            int id = -1;
            if(rs.next())
                id = rs.getInt(1);
            
            //https://stackoverflow.com/questions/35670858/rs-getgeneratedkeys-not-working-in-derby
            //This should set the new record's ID field on the passed in object
            c.setId(id);
            
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addCustomer", e);
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer c) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.Customers SET Email = ?, FirstName = ?, LastName = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setString(1, c.getEmail());
            p.setString(2, c.getFirstName());
            p.setString(3, c.getLastName());
            
            //Modified Date
            p.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(5, 1); //TODO: Pass in current user object
            //WHERE ID = ?
            p.setInt(6, c.getId());
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to updateCustomer", e);
            return false;
        }
    }

    @Override
    public boolean deleteCustomerById(int id) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.Customers WHERE ID = ?");
            //WHERE ID = ?
            p.setInt(1, id);
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteCustomerById", e);
            return false;
        }
    }
}