/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.ICategory;
import uts.isd.util.Logging;

/**
 * Category model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-04
 */
public class Category {
    
    private int id;
    private String name;
    private String description;
    private String image;
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;

    public Category() { }
    
    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public Category(ResultSet rs) {
        try
        {
            this.id = rs.getInt("ID");
            this.name = rs.getString("Name");
            this.description = rs.getString("Description");
            this.image = rs.getString("Image");
            
            this.createdDate = rs.getDate("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getDate("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load Category from ResultSet for ID", e);
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * 
     */
    public void loadRequest(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.name = request.getParameter("name");
        this.description = request.getParameter("description");
        this.image = request.getParameter("image");
        
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        
        this.createdBy = 0;
        this.modifiedBy = 0;
    }
    
    public boolean add(ICategory db, Customer customer)
    {
        try
        {
            //Assumes the Category object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addCategory(this, customer);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add category", e);
            return false;
        }        
    }
    
    public boolean update(ICategory db, Customer customer)
    {
        try
        {
            //Assumes the Category object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updateCategory(this, customer);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update category", e);
            return false;
        }        
    }
    
    public boolean delete(ICategory db)
    {
        try
        {
            //Assumes the Category object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean deleted = db.deleteCategoryById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete category", e);
            return false;
        }        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
    
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public int getCreatedBy() {
        return this.createdBy;
    }
    
    public int getModifiedBy() {
        return this.modifiedBy;
    }
}