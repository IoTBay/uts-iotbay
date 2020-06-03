/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class DBAuditLogs {
    
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
        
        
        //Using a static DB connection here so that we can make it easier to
        //add new audit log entries.
        if (DBAuditLogs.conn == null)
        {
            DBConnector connector = new DBConnector();
            DBAuditLogs.conn = connector.openConnection();
        }
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
        try {
            PreparedStatement p = DBAuditLogs.conn.prepareStatement("INSERT INTO APP.AuditLogs (CustomerID, Entity, Event, Message, EventDate) VALUES (?, ?, ?, ?, ?)");
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
    
    
}
