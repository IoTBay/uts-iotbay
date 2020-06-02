/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.util.Date;
import javax.servlet.ServletRequest;

/**
 * Address model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class Address {
    
    private int id;
    private int customerId;
    private int userId;
    private int defaultShippingAddress; //Is this address the customer's default shipping address?
    private int defaultBillingAddress; //Is this address the customer's default billing address?    
    private String addressPrefix1;
    private int streetNumber;
    private String streetName;
    private String streetType;
    private String suburb;
    private String state;
    private String postcode;
    private String country;
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;

    public Address() {
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addAddress(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        if (request.getParameter("userId") != null)
            this.userId = Integer.parseInt(request.getParameter("userId"));
        
        if (request.getParameter("defaultShippingAddress") != null)
            this.defaultShippingAddress = Integer.parseInt(request.getParameter("defaultShippingAddress"));
        
        if (request.getParameter("defaultBillingAddress") != null)
            this.defaultShippingAddress = Integer.parseInt(request.getParameter("defaultBillingAddress"));
        
        this.addressPrefix1 = request.getParameter("addressPrefix1");
        this.streetNumber = Integer.parseInt(request.getParameter("streetNumber"));
        this.streetName = request.getParameter("streetName");
        this.streetType = request.getParameter("streetType");
        this.suburb = request.getParameter("suburb");
        this.state = request.getParameter("state");
        this.postcode = request.getParameter("postcode");
        this.country = request.getParameter("country");
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setDefaultShippingAddress(int defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }

    public int getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    public void setDefaultBillingAddress(int defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
    }

    public String getAddressPrefix1() {
        return addressPrefix1;
    }

    public void setAddressPrefix1(String addressPrefix1) {
        this.addressPrefix1 = addressPrefix1;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
