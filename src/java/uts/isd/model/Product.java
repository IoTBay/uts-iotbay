/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.util.Date;
import javax.servlet.ServletRequest;

/**
 *
 * @author rhys
 */
public class Product {
    
    private int id;
    private int categoryId;
    private int currencyId;
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
    
    public Product(int id, int categoryId, String name, double price, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
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
    
    public int getCurrencyId() {
        return this.currencyId;
    }
    
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
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
