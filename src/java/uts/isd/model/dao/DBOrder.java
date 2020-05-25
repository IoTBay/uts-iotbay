/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Order;
import uts.isd.model.OrderLine;

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
public class DBOrder implements IOrder {
    
    private Connection conn;
        
    public DBOrder() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    @Override
    public Order getOrderById(int id) 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM Orders WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getOrderById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new Order(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getOrderById", e);
            return null;
        }
    }

    @Override
    public Iterable<Order> getOrdersByCustomerId(int customerId) 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM Users WHERE CustomerID = ?");
            p.setInt(1, customerId);
            ResultSet rs = p.executeQuery();
            
            //Build list of order objects to return
            List<Order> orders = new ArrayList<Order>();
            
            while (rs.next())
            {
                orders.add(new Order(rs));
            }
            return orders;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getOrdersByCustomerId", e);
            return null;
        }
    }

    @Override
    public Iterable<Order> getAllOrders() 
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM Orders");
            ResultSet rs = p.executeQuery();
            
            //Build list of order objects to return
            List<Order> orders = new ArrayList<Order>();
            
            while (rs.next())
            {
                orders.add(new Order(rs));
            }
            return orders;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllOrders", e);
            return null;
        }
    }
    
     @Override
    public boolean addOrder(Order o)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO Orders (CustomerID, UserID, BillingAddressID, ShippingAddressID, PaymentMethodID, CardName, CardNumber, CardCVV, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, o.getCustomerId());
            p.setInt(2, o.getUserId());
            p.setInt(3, o.getBillingAddressId());
            p.setInt(4, o.getShippingAddressId());
            p.setInt(5, o.getPaymentMethodId());
            p.setString(6, o.getCardName());
            p.setString(7, o.getCardNumber());
            p.setString(8, o.getCardCVV());
            p.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(10, 0); //TODO: Pass in current order object
            
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addOrder", e);
            return false;
        }
    }

    @Override
    public boolean updateOrder(Order o) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("UPDATE Orders SET CustomerID = ?,  UserID = ?, BillingAddressID = ?, ShippingAddressID = ?, PaymentMethodID = ?, CardName = ?, CardNumber = ?,  CardCVV = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setInt(1, o.getCustomerId());
            p.setInt(2, o.getUserId());
            p.setInt(3, o.getBillingAddressId());
            p.setInt(4, o.getShippingAddressId());
            p.setInt(5, o.getPaymentMethodId());
            p.setString(6, o.getCardName());
            p.setString(7, o.getCardNumber());
            p.setString(8, o.getCardCVV());
            
            //Modified Date
            p.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(10, 0); //TODO: Pass in current order object
            //WHERE ID = ?
            p.setInt(11, o.getId());
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to updateOrder", e);
            return false;
        }
    }

    @Override
    public boolean deleteOrderById(int id) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM Orders WHERE ID = ?");
            //WHERE ID = ?
            p.setInt(10, id);
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteOrderById", e);
            return false;
        }
    }
}