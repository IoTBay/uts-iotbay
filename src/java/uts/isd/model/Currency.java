/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.util.Logging;

/**
 * Currency model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class Currency {
    
    private int id;
    private String name;
    private String abbreviation;
    private double costConversionRate;
    private double retailConversionRate;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public Currency() { }
    
    public Currency(ResultSet rs)
    {
        try
        {
            this.name = rs.getString("Name");
            this.abbreviation = rs.getString("Abbreviation");
            this.costConversionRate = rs.getDouble("CostConversionRate");
            this.retailConversionRate = rs.getDouble("RetailConversionRate");
            
            this.createdDate = rs.getDate("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getDate("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");      
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load Currency from ResultSet", e);
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addCurrency(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.name = request.getParameter("name");
        this.abbreviation = request.getParameter("abbreviation");
        this.costConversionRate = Double.parseDouble(request.getParameter("costConversionRate"));
        this.retailConversionRate = Double.parseDouble(request.getParameter("retailConversionRate"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
        
        return true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public double getCostConversionRate() {
        return costConversionRate;
    }

    public double getRetailConversionRate() {
        return retailConversionRate;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setCostConversionRate(double costConversionRate) {
        this.costConversionRate = costConversionRate;
    }

    public void setRetailConversionRate(double retailConversionRate) {
        this.retailConversionRate = retailConversionRate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    
    
    
}
