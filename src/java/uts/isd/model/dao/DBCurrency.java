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
import uts.isd.model.Currency;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author rhys
 */
public class DBCurrency implements ICurrency {
    
    private Connection conn;
        
    public DBCurrency() throws SQLException, ClassNotFoundException
    { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException 
    {
        this.conn.close();
    }
    
    @Override
    public Currency getCurrencyById(int id)
    {
        try {
            //Using SQL prepared statements: https://stackoverflow.com/questions/3451269/parameterized-oracle-sql-query-in-java
            //this protects against SQL Injection attacks. Each parameter must have a ? in the query, and a corresponding parameter
            //set.
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Currencies WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getCurrencyById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new Currency(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getCurrencyById", e);
            return null;
        }
    }
}
