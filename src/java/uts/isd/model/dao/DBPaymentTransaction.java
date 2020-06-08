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
import java.util.ArrayList;
import java.util.List;
import uts.isd.model.PaymentTransaction;
import uts.isd.model.Customer;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing transactions of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class DBPaymentTransaction implements IPaymentTransaction{
    
    private Connection conn;
        
    public DBPaymentTransaction() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    //Use this to show all of the paytransactions in the database - use this for displaying in table
    @Override
    public List<PaymentTransaction> getAllPaymentTransactions() 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentTransactions");
            ResultSet rs = p.executeQuery();
            
            //Build list of payment transaction objects to return
            List<PaymentTransaction> paytransactions = new ArrayList<PaymentTransaction>();
            
            while (rs.next())
            {
                paytransactions.add(new PaymentTransaction(rs));
            }
            return paytransactions;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllPaymentTransactions", e);
            return null;
        }
    }
    
    @Override
    public List<PaymentTransaction> getAllPaymentTransactionsByCustomerId(int id)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentTransactions WHERE CustomerID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            
            //Build list of payment transaction objects to return
            List<PaymentTransaction> paytransactions = new ArrayList<PaymentTransaction>();
            
            while (rs.next())
            {
                paytransactions.add(new PaymentTransaction(rs));
            }
            return paytransactions;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllPaymentTransactionsByCustomerId", e);
            return null;
        }
    }
    
    @Override
    public List<PaymentTransaction> getAllPaymentTransactionsByOrderId(int id)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentTransactions WHERE OrderID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            
            //Build list of payment transaction objects to return
            List<PaymentTransaction> paytransactions = new ArrayList<PaymentTransaction>();
            
            while (rs.next())
            {
                paytransactions.add(new PaymentTransaction(rs));
            }
            return paytransactions;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllPaymentTransactionsByOrderId", e);
            return null;
        }
    }
    
    
    
    //Adding a new payment transaction to the database 
    @Override
    public boolean addPaymentTransaction(PaymentTransaction t, Customer customer)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("INSERT INTO APP.PaymentTransactions (CustomerID, OrderID, Amount, Description, PaymentGatewayTransaction, Status, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, t.getCustomerId());
            p.setInt(2, t.getOrderId());
            p.setDouble(3, t.getAmount());
            p.setString(4, t.getDescription());
            p.setString(5, t.getPaymentGatewayTransaction());
            p.setInt(6, t.getStatus());

            p.setTimestamp(7, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(8, customer.getId());
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addPaymentTransaction", e);
            return false;
        }
    }

    //Updating an existing payment transaction in the database - this transaction only changes the stored values, it does not check payment transaction exists 
    @Override
    public boolean updatePaymentTransaction(PaymentTransaction t, Customer customer) {
        try {
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.PaymentTransactions SET CustomerID = ?, OrderID = ?, Amount = ?, Description = ?, PaymentGatewayTransaction = ?, Status = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setInt(1, t.getCustomerId());
            p.setInt(2, t.getOrderId());
            p.setDouble(3, t.getAmount());
            p.setString(4, t.getDescription());
            p.setString(5, t.getPaymentGatewayTransaction());
            p.setInt(6, t.getStatus());

            p.setTimestamp(7, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(8, customer.getId());
            p.setInt(9, t.getId());
            //Was update successful?
            int result = p.executeUpdate();
            return (result > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update PaymentTransaction", e);
            return false;
        }
    }
    
    //Search the database for paytransactions with ID that == the search ID - Search by ID used in findPaymentTransactions and Update
    @Override
    public PaymentTransaction getPaymentTransactionById(int id) 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentTransactions WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getPaymentTransactionById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new PaymentTransaction(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getPaymentTransactionById", e);
            return null;
        }
    }

    //Delete paytransactions 
    @Override
    public boolean deletePaymentTransactionById(int id) {
        try {
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.PaymentTransactions WHERE ID = ?");
            p.setInt(1, id);
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deletePaymentTransactionById", e);
            return false;
        }
    }
}