/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.DBCustomer;
import uts.isd.model.dao.ICustomer;
import uts.isd.model.dao.IPaymentTransaction;
import uts.isd.util.Logging;

/**
 * Payment Transaction model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class PaymentTransaction implements Serializable {
    private int id;
    private int customerId;
    private int orderId;
    private double amount;
    private String description;
    private String paymentGatewayTransaction;
    private int status;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public static final int PAYMENT_PENDING = 0;
    public static final int PAYMENT_SUCCESSFUL = 1;
    public static final int PAYMENT_FAILED = 2;
    
    public static final String[] PAYMENT_STATUS = {
        "Pending",
        "Successful",
        "Failed"
    };
    
    public PaymentTransaction() {
    
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
    
    }
    
 /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public PaymentTransaction(ResultSet rs) {
        try
        {
            this.id = rs.getInt("ID");
            this.customerId = rs.getInt("CustomerID");
            this.orderId = rs.getInt("OrderID");
            this.amount = rs.getDouble("Amount");
            this.description = rs.getString("Description");
            this.paymentGatewayTransaction = rs.getString("PaymentGatewayTransaction");
            this.status = rs.getInt("Status");
            
            this.createdDate = rs.getTimestamp("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getTimestamp("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load PaymentTransaction from ResultSet for ID", e);
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     */
    public void loadRequest(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        this.orderId = Integer.parseInt(request.getParameter("orderId"));
        this.amount = Double.parseDouble(request.getParameter("amount"));
        this.description = request.getParameter("description");
        this.paymentGatewayTransaction = request.getParameter("paymentGatewayTransaction");
        this.status = Integer.parseInt(request.getParameter("retailConversionRate"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;      
    }
    
    public boolean add(IPaymentTransaction db, Customer customer)
    {
        try
        {
            //Assumes the PaymentTransaction object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addPaymentTransaction(this, customer);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add payment transaction", e);
            return false;
        }        
    }
    
    public boolean update(IPaymentTransaction db, Customer customer)
    {
        try
        {
            //Assumes the PaymentTransaction object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updatePaymentTransaction(this, customer);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update payment transaction", e);
            return false;
        }        
    }
    
    public boolean delete(IPaymentTransaction db)
    {
        try
        {
            //Assumes the PaymentTransaction object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean deleted = db.deletePaymentTransactionById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete payment transaction", e);
            return false;
        }        
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getPaymentGatewayTransaction() {
        return paymentGatewayTransaction;
    }

    public int getStatus() {
        return status;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPaymentGatewayTransaction(String paymentGatewayTransaction) {
        this.paymentGatewayTransaction = paymentGatewayTransaction;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    


    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Customer getCreatedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.createdBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
    
    public Customer getModifiedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.modifiedBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
}

