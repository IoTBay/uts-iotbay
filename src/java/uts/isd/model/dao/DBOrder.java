/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Order;

import java.util.*;
import java.sql.*;
import uts.isd.model.Customer;
import uts.isd.model.OrderLine;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-25
 */
public class DBOrder implements IOrder {
    
    private Connection conn;
        
    public DBOrder() throws SQLException, ClassNotFoundException
    { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException 
    {
        this.conn.close();
    }
    
    @Override
    public Order getOrderById(int id)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Orders WHERE ID = ?");
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
    public OrderLine getOrderLineById(int id)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.OrderLines WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getOrderLineById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new OrderLine(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getOrderLineById", e);
            return null;
        }
    }
    
    @Override
    public Order getCartOrderByCustomer(Customer customer)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Orders WHERE CustomerID = ? AND Status = 0");
            p.setInt(1, customer.getId());
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                //No order was returned, instead create a new draft order.
                Order o = new Order();
                o.setCustomerId(customer.getId());
                this.addOrder(o, customer);
                
                return o; //Return new draft order
            }
            return new Order(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getCartOrderById", e);
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
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Orders WHERE CustomerID = ?");
            p.setInt(1, customerId);
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
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
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Orders");
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
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
    public List<OrderLine> getOrderLines(int orderId)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.OrderLines WHERE OrderID = ?");
            p.setInt(1, orderId);
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            List<OrderLine> orderLines = new ArrayList<OrderLine>();
            
            while (rs.next())
            {
                orderLines.add(new OrderLine(rs));
            }
            return orderLines;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getOrderLines", e);
            return null;
        }
    }

    
     @Override
    public boolean addOrder(Order o, Customer customer)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("INSERT INTO APP.Orders (CustomerID, UserID, CurrencyID, BillingAddressID, ShippingAddressID, PaymentMethodID, Status, TotalCost, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    //Added this to return the primary key of this new record
                    //https://db.apache.org/derby/docs/10.2/ref/crefjavstateautogen.html
                    Statement.RETURN_GENERATED_KEYS);
            p.setInt(1, o.getCustomerId());
            p.setInt(2, o.getUserId());
            p.setInt(3, o.getCurrencyId());
            p.setInt(4, o.getBillingAddressId());
            p.setInt(5, o.getShippingAddressId());
            p.setInt(6, o.getPaymentMethodId());
            p.setInt(7, o.getStatus());
            p.setDouble(8, o.getTotalCost());
            
            p.setTimestamp(9, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(10, customer.getId()); //TODO: Pass in current user object
            
            //Was insert successful?
            boolean added = (p.executeUpdate() > 0);
            
            //Now that customer is added, try and get the last inserted record ID.
            ResultSet rs = p.getGeneratedKeys();
            int id = -1;
            if(rs.next())
                id = rs.getInt(1);
            
            //https://stackoverflow.com/questions/35670858/rs-getgeneratedkeys-not-working-in-derby
            //This should set the new record's ID field on the passed in object
            o.setId(id);
            
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addOrder", e);
            return false;
        }
    }
    
    @Override
    public boolean addOrderLine(OrderLine o)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("INSERT INTO APP.OrderLines (OrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)",
                    //Added this to return the primary key of this new record
                    //https://db.apache.org/derby/docs/10.2/ref/crefjavstateautogen.html
                    Statement.RETURN_GENERATED_KEYS);
            p.setInt(1, o.getOrderId());
            p.setInt(2, o.getProductId());
            p.setInt(3, o.getQuantity());
            p.setDouble(4, o.getUnitPrice());
            
            //Was insert successful?
            boolean added = (p.executeUpdate() > 0);
                        
            //Now that customer is added, try and get the last inserted record ID.
            ResultSet rs = p.getGeneratedKeys();
            int id = -1;
            if(rs.next())
                id = rs.getInt(1);
            
            //https://stackoverflow.com/questions/35670858/rs-getgeneratedkeys-not-working-in-derby
            //This should set the new record's ID field on the passed in object
            o.setId(id);
            
            //Update TotalCost on the order.
            p = this.conn.prepareCall("UPDATE APP.Orders SET TotalCost = (TotalCost + ?) WHERE OrderID = ?");
            p.setDouble(2, (o.getPrice() * o.getQuantity()));
            p.setInt(2, o.getOrderId());
            added &= (p.executeUpdate() > 0);
            
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addOrderLine", e);
            return false;
        }
    }

    @Override
    public boolean updateOrder(Order o, Customer customer) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.Orders SET CustomerID = ?, UserID = ?, CurrencyID = ?, BillingAddressID = ?, ShippingAddressID = ?, PaymentMethodID = ?, Status = ?, TotalCost = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setInt(1, o.getCustomerId());
            p.setInt(2, o.getUserId());
            p.setInt(3, o.getCurrencyId());
            p.setInt(4, o.getBillingAddressId());
            p.setInt(5, o.getShippingAddressId());
            p.setInt(6, o.getPaymentMethodId());
            p.setInt(7, o.getStatus());
            p.setDouble(8, o.getTotalCost());
            
            //Modified Date
            p.setTimestamp(9, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(10, customer.getId()); //TODO: Pass in current user object
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
    public boolean updateOrderLine(OrderLine o) {
        try {
            //Need to find the existing order line so we can determine the
            //existing total cost.
            OrderLine existingLine = this.getOrderLineById(o.getId());
            
            if (existingLine == null)
            {
                Logging.logMessage("Failed to find existing order line for ID "+o.getId()+" that we are trying to update?!");
                return false;
            }
            
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.OrderLines SET OrderID = ?, ProductID = ?, Quantity = ?, UnitPrice = ? WHERE ID = ?");
            p.setInt(1, o.getOrderId());
            p.setInt(2, o.getProductId());
            p.setInt(3, o.getQuantity());
            p.setDouble(4, o.getUnitPrice());
            
            //WHERE ID = ?
            p.setInt(5, o.getId());
            
            //Was update successful?
            boolean updated = (p.executeUpdate() > 0);
            
            //Update TotalCost on the order.
            //Find the delta
            double existingLineTotal = (existingLine.getPrice() * existingLine.getQuantity());
            double newLineTotal = (o.getPrice() * o.getQuantity());
            double deltaLineTotal = (newLineTotal - existingLineTotal);

            p = this.conn.prepareCall("UPDATE APP.Orders SET TotalCost = (TotalCost + ?) WHERE OrderID = ?");
            p.setDouble(2, deltaLineTotal);
            p.setInt(2, o.getOrderId());
            updated &= (p.executeUpdate() > 0);
            return updated;
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
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.Orders WHERE ID = ?");
            //WHERE ID = ?
            p.setInt(1, id);
            
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteOrderById", e);
            return false;
        }
    }
    
    @Override
    public boolean deleteOrderLineById(OrderLine o) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.OrderLines WHERE ID = ?");
            //WHERE ID = ?
            p.setInt(1, o.getId());
            
            //Was update successful?
            boolean deleted = (p.executeUpdate() > 0);
            
            //Update TotalCost on the order.
            p = this.conn.prepareCall("UPDATE APP.Orders SET TotalCost = (TotalCost - ?) WHERE OrderID = ?");
            p.setDouble(2, (o.getPrice() * o.getQuantity()));
            p.setInt(2, o.getOrderId());
            deleted &= (p.executeUpdate() > 0);
            
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteOrderLineById", e);
            return false;
        }
    }
}