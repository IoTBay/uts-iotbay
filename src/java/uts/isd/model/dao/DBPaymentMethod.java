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
import uts.isd.model.PaymentMethod;
import uts.isd.model.Customer;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class DBPaymentMethod implements IPaymentMethod{
    
    private Connection conn;
        
    public DBPaymentMethod() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    //Use this to show all of the paymethods in the database - use this for displaying in table
    @Override
    public List<PaymentMethod> getAllPaymentMethods() 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentMethods");
            ResultSet rs = p.executeQuery();
            
            //Build list of payment method objects to return
            List<PaymentMethod> paymethods = new ArrayList<PaymentMethod>();
            
            while (rs.next())
            {
                paymethods.add(new PaymentMethod(rs));
            }
            return paymethods;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllPaymentMethods", e);
            return null;
        }
    }
    
    @Override
    public List<PaymentMethod> getAllPaymentMethodsByCustomerId(int id)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentMethods WHERE CustomerID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            
            //Build list of payment method objects to return
            List<PaymentMethod> paymethods = new ArrayList<PaymentMethod>();
            
            while (rs.next())
            {
                paymethods.add(new PaymentMethod(rs));
            }
            return paymethods;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllPaymentMethodsByCustomerId", e);
            return null;
        }
    }
    
    //Adding a new payment method to the database 
    @Override
    public boolean addPaymentMethod(PaymentMethod a, Customer customer)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.PaymentMethods (CustomerID, UserID, DefaultPayment, PaymentType, CardName, CardNumber, CardCVV, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, a.getCustomerId());
            p.setInt(2, a.getUserId());
            p.setBoolean(3, a.getDefaultPayment());
            p.setInt(4, a.getPaymentType());
            p.setString(5, a.getCardName());
            p.setString(6, a.getCardNumber());
            p.setString(7, a.getCardCVV());

            p.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(9, customer.getId());
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addPaymentMethod", e);
            return false;
        }
    }

    //Updating an existing payment method in the database - this method only changes the stored values, it does not check payment method exists 
    @Override
    public boolean updatePaymentMethod(PaymentMethod a, Customer customer) {
        try {
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.PaymentMethods SET CustomerID = ?, UserID = ?, DefaultPayment = ?, PaymentType = ?, CardName = ?, CardNumber = ?, CardCVV = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setInt(1, a.getCustomerId());
            p.setInt(2, a.getUserId());
            p.setBoolean(3, a.getDefaultPayment());
            p.setInt(4, a.getPaymentType());
            p.setString(5, a.getCardName());
            p.setString(6, a.getCardNumber());
            p.setString(7, a.getCardCVV());

            p.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(9, customer.getId());
            p.setInt(10, a.getId());
            //Was update successful?
            int result = p.executeUpdate();
            return (result > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update PaymentMethod", e);
            return false;
        }
    }
    
    //Search the database for paymethods with ID that == the search ID - Search by ID used in findPaymentMethods and Update
    @Override
    public PaymentMethod getPaymentMethodById(int id) 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.PaymentMethods WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getPaymentMethodById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new PaymentMethod(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserById", e);
            return null;
        }
    }

    //Delete paymethods 
    @Override
    public boolean deletePaymentMethodById(int id) {
        try {
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.PaymentMethods WHERE ID = ?");
            p.setInt(1, id);
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deletePaymentMethodById", e);
            return false;
        }
    }
}

    