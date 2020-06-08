
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
 * 
 * 
 * @author Ashley
 * 
 */
public class AuditLog implements Serializable {

    private int id;
    private String entity;
    private String event;
    private String message;
    private int customerId;
    private Date eventDate;

    public AuditLog() { 
        
        this.entity = "";
        this.event = "";
        this.message = "";
        this.eventDate = new Date();
    
    }
    
    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public AuditLog(ResultSet rs)
    {
        try
        {
            this.id = rs.getInt("ID");
            this.entity = rs.getString("Entity"); 
            this.event = rs.getString("Event");
            this.message = rs.getString("Message");
            this.customerId = rs.getInt("CustomerID");
            this.eventDate = rs.getDate("EventDate");
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load Access Log from ResultSet for ID", e);
        }
    }
    
    public int getId() {
        return id;
    }

    public String getEntity() {
        return entity;
    }

    public String getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Date getEventDate() {
        return eventDate;
    }
    
}