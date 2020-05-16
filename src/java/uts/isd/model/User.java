/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;

/**
 *
 * @author rhys
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
    
    public User()
    {
        
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addUser(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        this.email = request.getParameter("email");
        this.password = request.getParameter("password");
        if (request.getParameter("accessLevel") != null)
            this.accessLevel = Integer.parseInt(request.getParameter("accessLevel"));
        else
            this.accessLevel = 1;
        
        //https://www.javatpoint.com/java-string-to-date
        String dob = request.getParameter("dob_yyyy")+"-"+request.getParameter("dob_mm")+"-"+request.getParameter("dob_dd");
        try {
            this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        this.sex = Integer.parseInt(request.getParameter("sex"));

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
