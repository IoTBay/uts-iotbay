/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.DBCustomer;
import uts.isd.model.dao.ICustomer;
import uts.isd.model.dao.IProduct;
import uts.isd.util.Logging;

/**
 *
 * @author rhys
 */
public class Product implements Serializable {
    
    private int id;
    private int categoryId;
    private ProductCategory category;
    private int currencyId;
    private Currency currency;
    private String name;
    private double price;
    private String description;
    private String image;
    private int initialQuantity;
    private int currentQuantity;
    private String lastReorderDate;
    private Timestamp createdDate;
    private int createdBy;
    private Timestamp modifiedDate;
    private int modifiedBy;
    
    //Set a threshold for low stock
    public static final int LOW_STOCK = 10;

    public Product() {
        
        this.name = "";
        this.description = "";
        this.image = "";
        this.lastReorderDate = "";
        
        this.createdDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
        this.createdBy = 0;
        this.modifiedBy = 0;
        
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
            //this.currencyId = rs.getInt("CurrencyID");
            this.price = rs.getDouble("price");
            this.name = rs.getString("Name");
            this.description = rs.getString("Description");
            this.image = rs.getString("Image");
            this.initialQuantity = rs.getInt("InitialQuantity");
            this.currentQuantity = rs.getInt("CurrentQuantity");
            this.createdDate = rs.getTimestamp("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getTimestamp("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load User from ResultSet for ID", e);
        }
        
    }
    
    //load values from the fields into the object 
    public void loadRequest(ServletRequest request)
    {
        if (request.getParameter("name") != null) 
            this.name = request.getParameter("name");
        
        this.categoryId = Integer.parseInt(request.getParameter("categoryId"));
        
        if (request.getParameter("currencyId") != null)
            this.currencyId = Integer.parseInt(request.getParameter("currencyId"));
        
        this.price = Double.parseDouble(request.getParameter("price"));
        this.description = request.getParameter("description");
        this.initialQuantity = Integer.parseInt(request.getParameter("initialQuantity"));
        this.currentQuantity = Integer.parseInt(request.getParameter("initialQuantity"));
    }
    
    public boolean add(IProduct pr, Customer customer)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = pr.addProduct(this, customer);
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
    
    public boolean update(IProduct db, Customer customer)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and updates in DB.
            boolean updated = db.updateProduct(this, customer);
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
    
    public ProductCategory getCategory() {
        return category;
    }
    
    public void setCategory(ProductCategory category) {
        this.category = category;
    }
    
    public int getCurrencyId() {
        return this.currencyId;
    }
    
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

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
    
    public String getPriceFormatted(String format) {
        return String.format(format, this.price);
    }
    
    public String getPriceFormatted() {
        return String.format("$%.02f", this.price);
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
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Customer getCreatedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.createdBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
    
    public Customer getModifiedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.modifiedBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }

}