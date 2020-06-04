/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.IProduct;
import uts.isd.util.Logging;

/**
 *
 * @author rhys
 */
public class Product {
    
    private int id;
    private int categoryId;
    //private int currencyId;
    private String name;
    private double price;
    private String description;
    private String image;
    private int initialQuantity;
    private int currentQuantity;
    private String lastReorderDate;
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;

    public Product() {
    }
    
    /*public Product(int id, int categoryId, String name, double price, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
    } */
    
    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    
    //For searching 
    public Product(ResultSet rs)
    {
        try
        {
            this.id = rs.getInt("ID");
            this.categoryId = rs.getInt("CategoryID");
            //this.defaultCurrencyId = 
            this.name = rs.getString("Name");
            this.description = rs.getString("Description");
            this.image = rs.getString("Image");
            this.initialQuantity = rs.getInt("InitialQuantity");
            this.currentQuantity = rs.getInt("CurrentQuantity");
            this.createdDate = rs.getDate("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getDate("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load User from ResultSet for ID", e);
        }
        
    }
    
    public void loadRequest(ServletRequest request)
    {
        this.loadRequest(request, null);
    }
    
    //load values from the fields into the object 
    public void loadRequest(ServletRequest request, User changedBy)
    {
        if (request.getParameter("name") != null) 
            this.name = request.getParameter("name");
        Logging.logMessage("the categor is " + request.getParameter("categoryId"));
        this.categoryId = Integer.parseInt(request.getParameter("categoryId"));
        this.price = Double.parseDouble(request.getParameter("price"));
        this.description = request.getParameter("description");
        this.initialQuantity = Integer.parseInt(request.getParameter("initialQuantity"));
        this.currentQuantity = Integer.parseInt(request.getParameter("initialQuantity"));
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 1;
        this.modifiedBy = 1;
        if (changedBy != null)
        {
            this.createdBy = changedBy.getId(); //Set this properly
            this.modifiedBy = changedBy.getId(); //Set this properly.
        } 
    }
    
    public boolean add(IProduct pr)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = pr.addProduct(this);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add product", e);
            return false;
        }        
    }
    
    public boolean delete(IProduct pr)
    {
        try {
            boolean deleted = pr.deleteProductById(this.id);
            return deleted; 
        }
        catch (Exception e) {
            Logging.logMessage("Failed to delete product", e);
            return false; 
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    
    public boolean addProduct(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        this.categoryId = Integer.parseInt(request.getParameter("categoryId"));
        this.price = Double.parseDouble(request.getParameter("productId"));
        this.name = request.getParameter("name");
        this.description = request.getParameter("description");
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
        return true;
    }
    
    public boolean update(IProduct db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and updates in DB.
            boolean updated = db.updateProduct(this);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update product", e);
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    /* public int getCurrencyId() {
        return this.currencyId;
    }
    
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    } */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getLastReorderDate() {
        return lastReorderDate;
    }

    public void setLastReorderDate(String lastReorderDate) {
        this.lastReorderDate = lastReorderDate;
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