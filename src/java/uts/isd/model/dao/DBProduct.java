/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uts.isd.model.Customer;
import uts.isd.model.Product;
import uts.isd.util.Logging;
/**
 *
 * @author C_fin
 */

public class DBProduct implements IProduct{
    
    private Connection conn;
        
    public DBProduct() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    //Use this to show all of the products in the database - use this for displaying in table
    @Override
    public List<Product> getAllProducts() 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Products");
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            ArrayList<Product> products = new ArrayList<Product>();
            
            while (rs.next())
            {
                products.add(new Product(rs));
            }
            return products;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllProducts", e);
            return null;
        }
    }
    
    @Override
    public List<Product> getProductsByCategoryId(int categoryId)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Products WHERE CategoryID = ?");
            p.setInt(1, categoryId);
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            ArrayList<Product> products = new ArrayList<Product>();
            
            while (rs.next())
            {
                products.add(new Product(rs));
            }
            return products;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getProductsByCategoryId", e);
            return null;
        }
    }
    
    //Adding a new product to the database 
    @Override
    public boolean addProduct(Product pr, Customer customer)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.Products (categoryId,name,price,description,image,initialQuantity,currentQuantity,lastReorderDate,createdDate,createdBy,modifiedDate,modifiedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, pr.getCategoryId());
            p.setString(2, pr.getName());
            p.setDouble(3, pr.getPrice());
            p.setString(4, pr.getDescription());
            p.setString(5, pr.getImage());
            p.setInt(6, pr.getInitialQuantity());
            p.setInt(7, pr.getCurrentQuantity());
            p.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setTimestamp(9, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(10, customer.getId());
            p.setTimestamp(11, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(12, customer.getId());
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addProduct", e);
            return false;
        }
    }

    //Updating an existing product in the database - this method only changes the stored values, it does not check product exists 
    @Override
    public boolean updateProduct(Product pr, Customer customer) {
        try {
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.Products SET categoryId = ?,price = ?,description = ?,image = ?,initialQuantity = ?,currentQuantity = ?,lastReorderDate = ?,createdDate = ?,createdBy = ?,modifiedDate = ?,modifiedBy = ?, name = ? WHERE id=?");
            p.setInt(1, pr.getCategoryId());
            p.setDouble(2, pr.getPrice());
            p.setString(3, pr.getDescription());
            p.setString(4, pr.getImage());
            p.setInt(5, pr.getInitialQuantity());
            p.setInt(6, pr.getCurrentQuantity());
            p.setTimestamp(7, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(9, customer.getId());
            //Modified date - Modified by
            p.setTimestamp(10, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(11, customer.getId());
            p.setString(12, pr.getName());
            p.setInt(13, pr.getId());
            //Was update successful?
            int result = p.executeUpdate();
            return (result > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update Product", e);
            return false;
        }
    }
    
    @Override
    public Product getProductByName(String name) //for looking through database
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Products WHERE NAME = ?");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                Logging.logMessage("getProductByname returned no records for name: "+name);
                return null; //No records returned
            }
            return new Product(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getProductByName", e);
            return null;
        }
    }
    
    //Search the database for products with ID that == the search ID - Search by ID used in findProducts and Update
    @Override
    public Product getProductById(int id) 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Products WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getProductById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new Product(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserById", e);
            return null;
        }
    } 
    
    // Search the database for products with name that == the search name
    public Product authenticateProduct(String name)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Products WHERE name = ?");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("authenticateUser returned no records for product: "+name);
                return null; //No records returned
            }
            return new Product(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("error", e);
            return null;
        }
    }

    //Delete products 
    @Override
    public boolean deleteProductById(int id) {
        try {
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.Products WHERE ID = ?");
            p.setInt(1,id);
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteProductById", e);
            return false;
        }
    }
}

    