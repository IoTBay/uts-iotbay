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
import uts.isd.model.Product;
import uts.isd.util.Logging;
/**
 *
 * @author C_fin
 */

public class DBManager implements IProduct{
    
    private Connection conn;
        
    public DBManager() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
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

    @Override
    public Product getProductByName(String name) 
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

    @Override
    public Iterable<Product> getAllProducts() 
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
    public boolean addProduct(Product pr)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.Products (id,categoryId,currencyId,name,price,description,image,initialQuantity,currentQuantity,lastReorderDate,createdDate,createdBy,modifiedDate,modifiedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, pr.getId());
            p.setInt(2, pr.getCategoryId());
            p.setInt(3, pr.getCurrencyId());
            p.setString(4, pr.getName());
            p.setDouble(5, pr.getPrice());
            p.setString(6, pr.getDescription());
            p.setString(7, pr.getImage());
            p.setInt(8, pr.getInitialQuantity());
            p.setInt(9, pr.getCurrentQuantity());
            p.setDate(10, new java.sql.Date(new java.util.Date().getTime()));
            p.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(12, pr.getCreatedBy());
            p.setDate(13, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(14, pr.getModifiedBy());
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addProduct", e);
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product pr) {
        try {
            //PreparedStatement p = this.conn.prepareStatement("UPDATE Users SET CustomerID = ?,  Email = ?,  ModifiedBy = ? WHERE ID = ?");
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.Products SET id = ?,categoryId = ?,currencyId = ?,name = ?,price = ?,description = ?,image = ?,initialQuantity = ?,currentQuantity = ?,lastReorderDate = ?,createdDate = ?,createdBy = ?,modifiedDate = ?,modifiedBy = ? WHERE ID = ?");
            p.setInt(1, pr.getId());
            p.setInt(2, pr.getCategoryId());
            p.setInt(3, pr.getCurrencyId());
            p.setString(4, pr.getName());
            p.setDouble(5, pr.getPrice());
            p.setString(6, pr.getDescription());
            p.setString(7, pr.getImage());
            p.setInt(8, pr.getInitialQuantity());
            p.setInt(9, pr.getCurrentQuantity());
            p.setDate(10, new java.sql.Date(new java.util.Date().getTime()));
            p.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(12, pr.getCreatedBy());
            //Modified date - Modified by
            p.setDate(13, new java.sql.Date(new java.util.Date().getTime()));
            p.setInt(14, pr.getModifiedBy());
            //Product where id = ?
            p.setInt(15, pr.getId());
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
    public boolean deleteProductById(int id) {
        try {
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.Products WHERE ID = ?");
            //WHERE ID = ?
            p.setInt(10, id);
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