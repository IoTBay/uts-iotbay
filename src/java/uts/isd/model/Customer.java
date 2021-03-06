/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.ICustomer;
import uts.isd.model.dao.IUser;
import uts.isd.util.Logging;

/**
 *
 * @author rhys
 */
public class Customer implements Serializable {
    
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public Customer()
    { 
        this.id = 0;
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.phone = "";
        
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
    }
    
    public Customer(ResultSet rs)
    {
        try
        {
            this.id = rs.getInt("ID");
            this.email = rs.getString("Email");
            this.firstName = rs.getString("FirstName");
            this.lastName = rs.getString("LastName");
            this.phone = rs.getString("Phone");

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

    public Customer(String email, String firstName, String lastName, String phone) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     */
    public void loadRequest(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.email = request.getParameter("email");
        this.firstName = request.getParameter("firstName");
        this.lastName = request.getParameter("lastName");
        this.phone = request.getParameter("phone");
    }
    
    public boolean add(ICustomer db, Customer customer)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addCustomer(this, customer);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add customer", e);
            return false;
        }
    }
    
    public boolean update(ICustomer db, Customer customer)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and updates in DB.
            boolean updated = db.updateCustomer(this, customer);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update customer", e);
            return false;
        }
    }
    
    public boolean delete(ICustomer db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and updates in DB.
            boolean deleted = db.deleteCustomerById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete customer", e);
            return false;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String email) {
        this.phone = phone;
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
