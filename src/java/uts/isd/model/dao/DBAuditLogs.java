/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import uts.isd.model.*;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-03
 */
public class DBAuditLogs implements IAuditLogs {
    
    private static Connection conn;
    
    /**
     * The entity basically represents what data table
     * the event relates to.
     */
    public enum Entity {
        Addresses,
        Currencies,
        Customers,
        OrderLines,
        Orders,
        PaymentMethods,
        PaymentTransactions,
        ProductCategories,
        ProductReviews,
        Products,
        Users
    }
        
    public DBAuditLogs() throws SQLException, ClassNotFoundException { 
        
        DBAuditLogs.getInstance();

    }
    
    private static Connection getInstance()
    {
        //Using a static DB connection here so that we can make it easier to
        //add new audit log entries.
        try
        {
            if (DBAuditLogs.conn != null)
            {
                return DBAuditLogs.conn;
            }
            
            DBConnector connector = new DBConnector();
            DBAuditLogs.conn = connector.openConnection();
        }
        catch (Exception e)
        {
            Logging.logMessage("Can't find class DBConnector for DBAuditLogs", e);
        }
        return DBAuditLogs.conn;
    }
    
    /**
     * Use this static method to quickly log an event in a one-line call.
     * This will insert the event straight into the DB and bypass a model.
     * 
     * @param entity An entity enum value representing what data table the event relates to
     * @param event A string representing the event. E.g. Add, Edit, Login, Logout. Name your own events as needed.
     * @param message An optional string with added details.
     * @param customerId The customer ID of whoever performed this action (either a registered or an anonymous customer)
     * @return Returns True if the entry was added or false if it failed.
     */
    public static boolean addEntry(Entity entity, String event, String message, int customerId)
    {
        Connection connection = DBAuditLogs.getInstance();
        
        try {
            PreparedStatement p = connection.prepareStatement("INSERT INTO APP.AuditLogs (CustomerID, Entity, Event, Message, EventDate) VALUES (?, ?, ?, ?, ?)");
            p.setInt(1, customerId);
            p.setString(2, entity.toString());
            p.setString(3, event);
            p.setString(4, message);
            p.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));

            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to add AuditLogs entry", e);
            return false;
        }
    }
    
    @Override
    public List<AuditLog> getAuditLogsByCustomerId(int customerId) {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.AuditLogs WHERE CustomerID = ?");
            p.setInt(1, customerId);
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            List<AuditLog> accesslogs = new ArrayList<AuditLog>();
            
            while (rs.next())
            {
                accesslogs.add(new AuditLog(rs));
            }
            return accesslogs;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAccessLogsByCustomerID", e);
            return null;
        }
    }
    
    public List<AuditLog> searchAuditLogsByDateForCustomerId(String start, String end, int customerId)
    {
        try {
            java.util.Date startDate;
            java.util.Date endDate;
            //https://stackoverflow.com/questions/18873014/parse-string-date-in-yyyy-mm-dd-format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            try
            {
                startDate = sdf.parse(start);
                endDate = sdf.parse(end);
            }
            catch (Exception e)
            {
                startDate = new java.util.Date();
                endDate = new java.util.Date();
            }
            
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.AuditLogs WHERE EventDate >= ? AND EventDate <= ? AND CustomerID = ? ORDER BY ID DESC");
            p.setDate(1, new java.sql.Date(startDate.getTime()));
            p.setDate(2, new java.sql.Date(endDate.getTime()));
            p.setInt(3, customerId);
            
            ResultSet rs = p.executeQuery();
            
            //Build list of user objects to return
            List<AuditLog> auditLogs = new ArrayList<AuditLog>();
            
            while (rs.next())
            {
                auditLogs.add(new AuditLog(rs));
            }
            return auditLogs;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to searchAuditLogsByDateForCustomerId", e);
            return null;
        }
    }
}
