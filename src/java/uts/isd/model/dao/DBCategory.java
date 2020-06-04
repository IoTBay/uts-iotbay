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
import java.util.ArrayList;
import java.util.List;
import uts.isd.model.Category;
import uts.isd.model.Customer;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class DBCategory implements ICategory{
    
    private Connection conn;
        
    public DBCategory() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    //Use this to show all of the categories in the database - use this for displaying in table
    @Override
    public List<Category> getAllCategories() 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.ProductCategories");
            ResultSet rs = p.executeQuery();
            
            //Build list of category objects to return
            List<Category> categories = new ArrayList<Category>();
            
            while (rs.next())
            {
                categories.add(new Category(rs));
            }
            return categories;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllCategories", e);
            return null;
        }
    }
    
    //Adding a new category to the database 
    @Override
    public boolean addCategory(Category a, Customer customer)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.ProductCategories (Name, Description, Image, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?)");
            p.setString(1, a.getName());
            p.setString(2, a.getDescription());
            p.setString(3, a.getImage());

            p.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(5, customer.getId());
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addCategory", e);
            return false;
        }
    }

    //Updating an existing category in the database - this method only changes the stored values, it does not check category exists 
    @Override
    public boolean updateCategory(Category a, Customer customer) {
        try {
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.ProductCategories SET Name = ?, Description = ?, Image = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setString(1, a.getName());
            p.setString(2, a.getDescription());
            p.setString(3, a.getImage());

            p.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(5, customer.getId());
            p.setInt(6, a.getId());
            //Was update successful?
            int result = p.executeUpdate();
            return (result > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update Category", e);
            return false;
        }
    }
    
    //Search the database for categories with ID that == the search ID - Search by ID used in findCategories and Update
    @Override
    public Category getCategoryById(int id) 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.ProductCategories WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getCategoryById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new Category(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserById", e);
            return null;
        }
    }

    //Delete categories 
    @Override
    public boolean deleteCategoryById(int id) {
        try {
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.ProductCategories WHERE ID = ?");
            p.setInt(1, id);
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteCategoryById", e);
            return false;
        }
    }
}