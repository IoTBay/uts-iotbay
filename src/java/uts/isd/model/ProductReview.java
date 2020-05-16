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
public class ProductReview {
    private int id;
    private int customerId;
    private String text;
    private int rating;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public ProductReview() { }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addProductCategory(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        this.text = request.getParameter("text");
        this.rating = Integer.parseInt(request.getParameter("description"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
        
        return true;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }
}
