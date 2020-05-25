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
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public Customer() { }
    
    public Customer(ResultSet rs)
    {
        try
        {
            this.id = rs.getInt("ID");
            this.email = rs.getString("Email");
            this.firstName = rs.getString("FirstName");
            this.lastName = rs.getString("LastName");

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

    public Customer(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addCustomer(ServletRequest request, ICustomer db)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.email = request.getParameter("email");
        this.firstName = request.getParameter("firstName");
        this.lastName = request.getParameter("lastName");

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
        
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addCustomer(this);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to addCustomer", e);
        }
        
        
        return true;
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
