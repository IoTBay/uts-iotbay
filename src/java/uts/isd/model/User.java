/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import uts.isd.model.dao.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletRequest;
import uts.isd.util.Hash;
import uts.isd.util.Logging;

/**
 * User model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class User implements Serializable {
    
    private int id;
    private int customerId;
    private int defaultCurrencyId;
    private String email;
    private String password;
    private int accessLevel;
    private Date birthDate;
    private int sex;
    private String biography;
    private String passwordResetHash; 
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public User() {  }
    
    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public User(ResultSet rs)
    {
        try
        {
            this.id = rs.getInt("ID");
            this.customerId = rs.getInt("CustomerID");
            //this.defaultCurrencyId = 
            this.email = rs.getString("Email");
            this.password = rs.getString("Password");
            this.accessLevel = rs.getInt("AccessLevel");
            this.birthDate = rs.getDate("BirthDate");
            this.sex = rs.getInt("Gender");
            this.biography = rs.getString("Biography");
            //this.passwordResetHash = rs.getString("PasswordResetHash");
            
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

    public User(String email) {
        this.email = email;
    }
    
    public void loadRequest(ServletRequest request)
    {
        this.loadRequest(request, null);
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @param changedBy The customer who made this request.
     */
    public void loadRequest(ServletRequest request, Customer changedBy)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        this.email = request.getParameter("email");
        
        if (request.getParameter("password") != null)
            this.setPassword(request.getParameter("password"));

        if (request.getParameter("accessLevel") != null)
            this.accessLevel = Integer.parseInt(request.getParameter("accessLevel"));
        else
            this.accessLevel = 1;
        
        //https://www.javatpoint.com/java-string-to-date
        String dob = request.getParameter("dob_yyyy")+"-"+request.getParameter("dob_mm")+"-"+request.getParameter("dob_dd");
        try {
            this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException ex) {
            Logging.logMessage("Unable to parse Date for addUser", ex);
            return;
        }
        
        this.sex = Integer.parseInt(request.getParameter("sex"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        if (changedBy != null)
        {
            this.createdBy = changedBy.getId();
            this.modifiedBy = changedBy.getId();
        }
        else
        {
            this.createdBy = 0;
            this.modifiedBy = 0;
        }
    }

    public boolean add(IUser db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addUser(this);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add user", e);
            return false;
        }        
    }
    
    public boolean update(IUser db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updateUser(this);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update user", e);
            return false;
        }        
    }
    
    public boolean delete(IUser db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean deleted = db.deleteUserById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete user", e);
            return false;
        }        
    }
    
    /**
     * This method determines if a user has access to admin functions.
     * 
     * @return Returns ture if user is an admin.
     */
    public boolean isAdmin()
    {
        return (this.accessLevel >= 10);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() 
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = Hash.SHA256(password);
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(String s)
    {
        try {
            this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException ex) {
            Logging.logMessage("Unable to parse Date for setBirthDate", ex);
        }
    }
    
    public void setBirthDate(Date date)
    {
        this.birthDate = date;
    }
    
    public int getAge() {
        Date now = new Date();
        long diffMs = Math.abs(now.getTime() - this.birthDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffMs, TimeUnit.MILLISECONDS);
        return (int)Math.floor((double)diff / (double)365);
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public int getGender()
    {
        return this.sex;
    }
    
    public String getSex() {
        if (this.sex == 1)
            return "Male";
        
        else if (this.sex == 2)
            return "Female";
        else
            return "Unknown";
    }

    public String getPasswordResetHash() {
        return passwordResetHash;
    }

    public void setPasswordResetHash(String passwordResetHash) {
        this.passwordResetHash = passwordResetHash;
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
